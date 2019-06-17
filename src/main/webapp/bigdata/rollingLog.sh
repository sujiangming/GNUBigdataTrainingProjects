#!/bin/bash

#定义日期格式
dataformat=`date +%Y-%m-%d-%H-%M-%S`

#复制access.log并重命名
cp /training/nginx/logs/access.log /training/nginx/logs/access_$dataformat.log

host=`hostname`
sed -i 's/^/'${host}',&/g' /training/nginx/logs/access_$dataformat.log
#统计日志文件行数
lines=`wc -l < /training/nginx/logs/access_$dataformat.log`

#将格式化的日志移动到flumeLogs目录下
mv /training/nginx/logs/access_$dataformat.log /training/nginx/logs/flumeLogs

#清空access.log的内容
sed -i '1,'${lines}'d' /training/nginx/logs/access.log

#重启nginx , 否则 log can not roll.
kill -USR1 `cat /training/nginx/logs/nginx.pid`

##返回给服务器信息
ls -al /training/nginx/logs/flumeLogs/
