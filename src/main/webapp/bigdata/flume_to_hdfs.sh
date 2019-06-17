#!/bin/bash

#先停止正在启动的flume
./flume_stop.sh

#用法：nohup ./start-dishi.sh >output 2>&1 &
nohup ./flume_start.sh > nohup_output.log 2>&1 &
echo "启动flume成功……"
