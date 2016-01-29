package com.isoftstone.paperetl.datacheck.mr;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.log4j.Logger;

import com.isoftstone.paperetl.datacheck.checker.Checker;
import com.isoftstone.paperetl.datacheck.domain.AuditcolumnDomain;
import com.isoftstone.paperetl.datacheck.domain.CheckResult;
import com.isoftstone.paperetl.datacheck.domain.CheckfunctionDomain;
import com.isoftstone.paperetl.datacheck.domain.DataCheckDomain;
import com.isoftstone.paperetl.datacheck.domain.ExecuteResult;
import com.isoftstone.paperetl.datacheck.domain.Result;
import com.isoftstone.paperetl.datacheck.fetcher.DataFetcher;
import com.isoftstone.paperetl.datacheck.xml.CheckXMLTool;

public class DataCheckCore {

	private static Logger logger = Logger.getLogger(DataCheckCore.class);

	public static class DataCheckMapper extends
			Mapper<LongWritable, Text, Text, NullWritable> {

		private DataCheckDomain domain = null;
		private DataFetcher dataFetcher = null;
		private Map<String, Checker> checkPool = null;
		private DataCheckAuxiliary dca = null;
		private Map<Integer, AuditcolumnDomain> column_check_mapping = null;
		private Text correctData = null;
		private Text errorData = null;

		private MultipleOutputs<Text, NullWritable> multipleOutputs = null;

		@Override
		protected void setup(
				Mapper<LongWritable, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			super.setup(context);
			correctData = new Text();
			errorData = new Text();
			multipleOutputs = new MultipleOutputs<Text, NullWritable>(context);
			String xml = context.getConfiguration().get("xml");
			logger.info("xml:::::" + xml);
			domain = CheckXMLTool.getDataCheckDomain(xml);
			logger.info("domain:::::" + domain.getTaskId() + "");
			checkPool = new HashMap<String, Checker>();
			dca = new DataCheckAuxiliary(context.getConfiguration());
			column_check_mapping = new HashMap<Integer, AuditcolumnDomain>();
			try {
				dataFetcher = (DataFetcher) Class.forName(
						domain.getAuditInfo().getTaskParam().getCheckType())
						.newInstance();
				checkPool = dca.getColumnCheckTypeObject(domain);
				if(domain.getAuditInfo().getCheckColumns().getAuditcolumn() != null && domain.getAuditInfo().getCheckColumns().getAuditcolumn().size() > 0)
				{
					for (AuditcolumnDomain columnDomain : domain.getAuditInfo().getCheckColumns().getAuditcolumn()) {
						column_check_mapping.put(columnDomain.getColumnIndex(),
								columnDomain);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} 

		}

		@Override
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String data = value.toString();
			String inputPattern = domain.getAuditInfo().getTaskParam()
					.getInputPattern();
			String outputSplitPattern = domain.getAuditInfo().getTaskParam()
					.getOutputSplitPattern();
			ExecuteResult executeResult = null;
			CheckResult checkResult = null;
			executeResult = dataFetcher.fetcher(value.toString(), domain.getAuditInfo().getTaskParam());
			if (!executeResult.isFlag()) {
				errorData.set(data);
				context.getCounter("data", "error").increment(1);
				multipleOutputs.write("error", errorData, NullWritable.get(), "error/");
				return;
			}

			List<String> dataList = executeResult.getDomains();

			if(column_check_mapping != null && column_check_mapping.size() > 0)
			{
				for (int i = 0; i < dataList.size(); i++) {
					if (column_check_mapping.containsKey(i)) {
						AuditcolumnDomain auditColumnDomain = column_check_mapping.get(i);
						for (CheckfunctionDomain check : auditColumnDomain
								.getChecks().getCheckfunction()) {
							checkResult = checkPool.get(
									auditColumnDomain.getColumnIndex() + "_"
											+ check.getCheckerClassName()).check(dataList.get(i), check.getFuncFormula());
							if (checkResult != null && !checkResult.isFlag()) {
								errorData.set(data);
								context.getCounter("data", "error").increment(1);
								multipleOutputs.write("error", errorData, NullWritable.get(), "error/");
								return;
							}
						}
					}
				}
			}

			if (inputPattern != outputSplitPattern)
				data = dca.generateNewData(dataList, outputSplitPattern);

			correctData.set(data);
			context.getCounter("data", "correct").increment(1);
			multipleOutputs.write("data", correctData, NullWritable.get(), "correct/");
		}

		@Override
		protected void cleanup(
				Mapper<LongWritable, Text, Text, NullWritable>.Context context)
				throws IOException, InterruptedException {
			super.cleanup(context);
			multipleOutputs.close();
		}
	}

	public static Result startCheckData(Configuration conf, DataCheckDomain dataCheckDomain, String dataInput, String dataOutput)
			throws Exception {
		conf.set("xml", CheckXMLTool.toXML(dataCheckDomain));
		conf.setBoolean("mapreduce.app-submission.cross-platform", true);// 配置使用跨平台提交任务

		Job job = Job.getInstance(conf, dataCheckDomain.getTaskId());
		job.setJarByClass(DataCheckCore.class);
		job.setMapperClass(DataCheckMapper.class);
		DataCheckAuxiliary auxiliary = new DataCheckAuxiliary(conf);
		auxiliary.addJarToClassPath(job, dataCheckDomain.getDependJarPath());
		job.setMapOutputKeyClass(Text.class);
		//去除reduce
		job.setNumReduceTasks(0);
		job.setMapOutputValueClass(NullWritable.class);
		FileInputFormat.addInputPath(job, new Path(dataInput));
		FileOutputFormat.setOutputPath(job, new Path(dataOutput));

		MultipleOutputs.addNamedOutput(job, "error", TextOutputFormat.class,
				Text.class, NullWritable.class);
		MultipleOutputs.addNamedOutput(job, "data", TextOutputFormat.class,
				Text.class, NullWritable.class);
		job.waitForCompletion(true);
		
		long success = job.getCounters().findCounter("data", "correct").getValue();
		long error = job.getCounters().findCounter("data", "error").getValue();
		System.out.println(success);
		System.out.println(error);
		
		return new Result(success + error, success, error, dataOutput+"correct/",  dataOutput+"error/").setFlag(true).setMessage("success");
	}

}
