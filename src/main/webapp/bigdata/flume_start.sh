#!/bin/bash
/training/apache-flume-1.7.0-bin/bin/flume-ng agent -c /training/apache-flume-1.7.0-bin/conf/ -f /training/apache-flume-1.7.0-bin/conf/flume-hdfs.conf -n a1 -Dflume.root.logger=INFO,console
