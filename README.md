# datacheck

数据检查
1.整体结构：
a)XML配置文件对象
	1.Datacheck：根节点对象
	2.Taskid：MR任务ID
	3.DependJarPath：MR运行所需jar包存放路径
	4.AuditInfo:数据审计对象，包含对于数据检查、处理规则的定义
	5.TaskParam：此对象中定义了对于数据fecher的规则
	6.CheckType：fecher对象（可实现DataFecher接口进行扩展）
	7.InputPattern：数据拆分规则（现支持根据字符和正则拆分）
	8.OutputSplitPattern：重新定义数据列分隔符，默认为’\001’
	9.ColumnNum：规定拆分字段个数 
	10.CheckColumns：规定数据字段检查规则
	11.AuditColume：规定具体字段检查规则
	12.ColumnIndex：数据拆分后下标，即待检查字段索引（从0开始）
	13.ColumeName：字段列名
	14.Checks：数据字段检查规则集合对象
	15.CheckFunction：数据字段检查规则对象
	16.FuncForMula：数据字段检查规则（仅支持正则）
	17.CheckType：暂不支持
	18.CheckerClassName：check对象（可实现Checker接口进行扩展）
b)DataFecher接口对象
	1.用于对数据进行拆分
	2.实现此接口可扩展拆分规则
	3.现有RegexFetcher对象完成正则匹配拆分数据；SplitFetcher对象完成字符串拆分数据；WGLZFecher完成华夏定制access log日志拆分规则
c)Check接口对象
	1.用于对数据拆分字段进行规则检验
	2.实现此接口可扩展字段规则检验
	3.现有RegexChecker对象完成正则匹配检验
d)DataCheckBoot对象
	1.程序入口
	2.支持XML对象、流和字符串调用
	3.参数解释：Configuration对象；XML对象或者流或者字符串；dataInput数据输入路径；dataOutput数据输出对象
2.数据检查整体流程
	a)将程序所需jar包上传制定目录
	b)获得程序入口DataCheckBoot对象，传递所需参数
	c)启动MR任务
	d)通过XML对象获得Fecher对象，check对象
	e)通过Fecher对象对数据进行拆分
	f)通过Check对象对拆分后的数据字段进行检验
	g)检查全都通过则使用指定分隔符将拆分好的字段重新组合
	h)将符合规则数据和错误数据分别输出到不同目录


3.功能扩展
a)Fecher扩展:
	1.实现DataFecher接口，实现其中fecher方法，该方法用来对数据进行拆分，返回ExecuteResult对象
	2.ExecuteResult对象：flag是boolean类型，用于返回成功或者失败的标识；message为提示信息；domains集合对象用于存放拆分后的字段信息
	3.在XML配置文件中checktype 填入扩建对象的全路径
b)	check 扩展：
	1.实现Checker接口，实现其中的check方法，该方法用来对数据拆分后的字段进行检查，返回ChekResult对象
	2.CheckResult对象：flag是boolean类型，用于返回成功或者失败的标识；message为提示信息
	3.在XML配置文件中checkClassName中填入扩建对象的全路径





1.datacheck.xml操作配置
	a)配置datacheck.xml中taskID标签指定MR任务ID（非必须）
	b)配置datacheck.xml中checkType标签，正则配置为 
	com.isoftstone.paperetl.datacheck.fetcher.RegexFetcher
	c)配置datacheck.xml 中 inputPattern标签，此标签为日志正则解析规则，
	例如：
	^(\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3})\s(-)\s(.*)\s\[(.*)\]\s"(.*)"\s(\d{3})\s(\S+)
	10.16.124.202 - - [23/Nov/2015:10:02:34 +0800] "GET /covers HTTP/1.1" 302 - 

	81.144.138.34 - - [12/Feb/2014:03:08:25 +0800] "GET /tag/bug HTTP/1.1" 200 14498 "-" "Wotbox/2.01 (+http://www.wotbox.com/bot/)" - 
	^(\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3})\s(-)\s(.*)\s\[(.*)\]\s\"([\w\W]+?\s)(.*)\"\s(\d{3,})\s(\d+)\s\"([^\s]*)\"\s\"(.*?)\"\s(.*)

	d)配置datacheck.xml中outputSplitPattern标签，默认输出分隔符为’\001’
	e)配置datacheck.xml中columnNum标签，此标签为拆分后字段个数
	f)如需要对拆分后字段进行检查则需配置datacheck.xml 中checkColumns标签，（如不需要则不用配置此标签，以下配置也可忽略）
	g)columnIndex标签为拆分后的字段索引，从0开始
	h)columnName标签为拆分后的字段列名（可忽略）
	i)funcFormula标签为字段正则匹配规则，如\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3}
	j)checkType标签为匹配类型，正则匹配为RegexChecker
	k)checkerClassName标签为正则配置规则对象，
	com.isoftstone.paperetl.datacheck.checker.RegexChecker

2.Jar包替换
	a)将HADOOP所依赖的jar替换到app/libs目录下，默认为hadoop2.7（如一致则不需要修改）
3.修改app/bin/start_auth.sh (start_auth.sh为华为FI配有权限启动，start.sh 为无权限限制启动)中APP_HOME目录（如APP_HOME=/opt/isoftstone/app），此目录为app根目录
4.将hdfs.keytab和krb5.conf（如果名字有变化请修改app/config/datacheck.properties中变量）两个文件放置于app/config/auth目录下
5.将集群hadoop配置文件（core-site.xml， hdfs-site.xml， mapred-site.xml， yarn-site.xml）放入app/config/fi目录下
6.在hdfs上创建目录/user/root/paper-lib/，并将app/wglz/下面的jar（wglz.jar  xmlpull-1.1.3.1.jar  xpp3_min-1.1.4c.jar  xstream-1.4.7.jar）上传上去，
