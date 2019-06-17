#执行MapReduce程序
dataformat=`date +%Y-%m-%d-%H-%M-%S`
/training/hadoop-2.7.3/bin/hadoop jar log.jar $(cat mr_input_path.txt) /output/result/$dataformat
/training/hadoop-2.7.3/bin/hdfs dfs -cat /output/result/$dataformat/part-r-00000 > mr_result.txt
echo $(cat mr_result.txt)
