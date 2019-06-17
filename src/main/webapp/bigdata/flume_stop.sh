#!/bin/bash

JAR="flume"

#停止flume函数
echo "begin stop flume process.."
num=`ps -ef|grep java|grep $JAR|wc -l`
echo "当前已经启动的flume进程数：$num"
if [ "$num" != "0" ];then
#正常停止flume
ps -ef|grep java|grep $JAR|awk '{print $2;}'|xargs kill
echo "进程已经关闭..."
else
echo "服务未启动，无须停止..."
fi
