@echo off

rem �����в�����Ҫ��˫��������������Ϊ = �ᱻ�Զ��ָ����������
rem -d0 �ر���ϸ�嵥����-n���õĺܴ�ʱ���������Ļ������������ñ�����

rem ����
rem java -Dfile.encoding=UTF-8 -jar ..\build\libs\fdcmdtools-2.0.jar loadrunner -n10 -c5 -w15 url=http://47.103.38.63:38080/fdwebtest/action/hmfms/action/stress/index %*

rem ����10Сʱ�������
java -Dfile.encoding=UTF-8 -jar ..\build\libs\fdcmdtools-2.0.jar loadrunner -n100 -c50 -w36000 url=http://47.103.38.63:38080/fdwebtest/action/hmfms/action/stress/addData %*

rem ����10Сʱ��ѯ9��ͷ��1������
rem java -Dfile.encoding=UTF-8 -jar ..\build\libs\fdcmdtools-2.0.jar loadrunner -n100 -c50 -w3600 url=http://47.103.38.63:38080/fdwebtest/action/hmfms/action/stress/getDataPage10Number10000 %*

rem ����10Сʱ�����ѯǰ1000������
rem java -Dfile.encoding=UTF-8 -jar ..\build\libs\fdcmdtools-2.0.jar loadrunner -n100 -c50 -w36000 url=http://47.103.38.63:38080/fdwebtest/action/hmfms/action/stress/getRandomEntity %*

rem ����VPN
rem java -Dfile.encoding=UTF-8 -jar build\libs\fdcmdtools-2.0.jar loadrunner -n4000 -c8 url=http://10.50.130.91:17456/sdf/yyy?name=123 %*

rem 962121
rem java -Dfile.encoding=UTF-8 -jar build\libs\fdcmdtools-2.0.jar loadrunner -n200 -c8 url=http://www.962121.net

rem DB-pgsql
rem java -Dfile.encoding=UTF-8 -jar build\libs\fdcmdtools-2.0.jar loadrunner sort=threadid -n200 -c8 class=jdbc connType=conn driver=org.postgresql.Driver url=jdbc:postgresql://localhost:5432/postgres user=fd passwd=xxx123 sql="select * from test"
