#!/bin/bash
/training/sqoop-1.4.6/bin/sqoop export \
--connect jdbc:mysql://192.168.215.131:3306/university \
--username root \
--password 123456 \
--input-fields-terminated-by '\t' \
--table t_mr_result \
--export-dir $(cat result_path.txt)
