# datacheck

���ݼ��
1.����ṹ��
a)XML�����ļ�����
	1.Datacheck�����ڵ����
	2.Taskid��MR����ID
	3.DependJarPath��MR��������jar�����·��
	4.AuditInfo:������ƶ��󣬰����������ݼ�顢�������Ķ���
	5.TaskParam���˶����ж����˶�������fecher�Ĺ���
	6.CheckType��fecher���󣨿�ʵ��DataFecher�ӿڽ�����չ��
	7.InputPattern�����ݲ�ֹ�����֧�ָ����ַ��������֣�
	8.OutputSplitPattern�����¶��������зָ�����Ĭ��Ϊ��\001��
	9.ColumnNum���涨����ֶθ��� 
	10.CheckColumns���涨�����ֶμ�����
	11.AuditColume���涨�����ֶμ�����
	12.ColumnIndex�����ݲ�ֺ��±꣬��������ֶ���������0��ʼ��
	13.ColumeName���ֶ�����
	14.Checks�������ֶμ����򼯺϶���
	15.CheckFunction�������ֶμ��������
	16.FuncForMula�������ֶμ����򣨽�֧������
	17.CheckType���ݲ�֧��
	18.CheckerClassName��check���󣨿�ʵ��Checker�ӿڽ�����չ��
b)DataFecher�ӿڶ���
	1.���ڶ����ݽ��в��
	2.ʵ�ִ˽ӿڿ���չ��ֹ���
	3.����RegexFetcher�����������ƥ�������ݣ�SplitFetcher��������ַ���������ݣ�WGLZFecher��ɻ��Ķ���access log��־��ֹ���
c)Check�ӿڶ���
	1.���ڶ����ݲ���ֶν��й������
	2.ʵ�ִ˽ӿڿ���չ�ֶι������
	3.����RegexChecker�����������ƥ�����
d)DataCheckBoot����
	1.�������
	2.֧��XML���������ַ�������
	3.�������ͣ�Configuration����XML��������������ַ�����dataInput��������·����dataOutput�����������
2.���ݼ����������
	a)����������jar���ϴ��ƶ�Ŀ¼
	b)��ó������DataCheckBoot���󣬴����������
	c)����MR����
	d)ͨ��XML������Fecher����check����
	e)ͨ��Fecher��������ݽ��в��
	f)ͨ��Check����Բ�ֺ�������ֶν��м���
	g)���ȫ��ͨ����ʹ��ָ���ָ�������ֺõ��ֶ��������
	h)�����Ϲ������ݺʹ������ݷֱ��������ͬĿ¼


3.������չ
a)Fecher��չ:
	1.ʵ��DataFecher�ӿڣ�ʵ������fecher�������÷������������ݽ��в�֣�����ExecuteResult����
	2.ExecuteResult����flag��boolean���ͣ����ڷ��سɹ�����ʧ�ܵı�ʶ��messageΪ��ʾ��Ϣ��domains���϶������ڴ�Ų�ֺ���ֶ���Ϣ
	3.��XML�����ļ���checktype �������������ȫ·��
b)	check ��չ��
	1.ʵ��Checker�ӿڣ�ʵ�����е�check�������÷������������ݲ�ֺ���ֶν��м�飬����ChekResult����
	2.CheckResult����flag��boolean���ͣ����ڷ��سɹ�����ʧ�ܵı�ʶ��messageΪ��ʾ��Ϣ
	3.��XML�����ļ���checkClassName���������������ȫ·��





1.datacheck.xml��������
	a)����datacheck.xml��taskID��ǩָ��MR����ID���Ǳ��룩
	b)����datacheck.xml��checkType��ǩ����������Ϊ 
	com.isoftstone.paperetl.datacheck.fetcher.RegexFetcher
	c)����datacheck.xml �� inputPattern��ǩ���˱�ǩΪ��־�����������
	���磺
	^(\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3})\s(-)\s(.*)\s\[(.*)\]\s"(.*)"\s(\d{3})\s(\S+)
	10.16.124.202 - - [23/Nov/2015:10:02:34 +0800] "GET /covers HTTP/1.1" 302 - 

	81.144.138.34 - - [12/Feb/2014:03:08:25 +0800] "GET /tag/bug HTTP/1.1" 200 14498 "-" "Wotbox/2.01 (+http://www.wotbox.com/bot/)" - 
	^(\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3})\s(-)\s(.*)\s\[(.*)\]\s\"([\w\W]+?\s)(.*)\"\s(\d{3,})\s(\d+)\s\"([^\s]*)\"\s\"(.*?)\"\s(.*)

	d)����datacheck.xml��outputSplitPattern��ǩ��Ĭ������ָ���Ϊ��\001��
	e)����datacheck.xml��columnNum��ǩ���˱�ǩΪ��ֺ��ֶθ���
	f)����Ҫ�Բ�ֺ��ֶν��м����������datacheck.xml ��checkColumns��ǩ�����粻��Ҫ�������ô˱�ǩ����������Ҳ�ɺ��ԣ�
	g)columnIndex��ǩΪ��ֺ���ֶ���������0��ʼ
	h)columnName��ǩΪ��ֺ���ֶ��������ɺ��ԣ�
	i)funcFormula��ǩΪ�ֶ�����ƥ�������\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3}
	j)checkType��ǩΪƥ�����ͣ�����ƥ��ΪRegexChecker
	k)checkerClassName��ǩΪ�������ù������
	com.isoftstone.paperetl.datacheck.checker.RegexChecker

2.Jar���滻
	a)��HADOOP��������jar�滻��app/libsĿ¼�£�Ĭ��Ϊhadoop2.7����һ������Ҫ�޸ģ�
3.�޸�app/bin/start_auth.sh (start_auth.shΪ��ΪFI����Ȩ��������start.sh Ϊ��Ȩ����������)��APP_HOMEĿ¼����APP_HOME=/opt/isoftstone/app������Ŀ¼Ϊapp��Ŀ¼
4.��hdfs.keytab��krb5.conf����������б仯���޸�app/config/datacheck.properties�б����������ļ�������app/config/authĿ¼��
5.����Ⱥhadoop�����ļ���core-site.xml�� hdfs-site.xml�� mapred-site.xml�� yarn-site.xml������app/config/fiĿ¼��
6.��hdfs�ϴ���Ŀ¼/user/root/paper-lib/������app/wglz/�����jar��wglz.jar  xmlpull-1.1.3.1.jar  xpp3_min-1.1.4c.jar  xstream-1.4.7.jar���ϴ���ȥ��
