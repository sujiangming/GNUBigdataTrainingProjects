##开始

#(shuf -i 1-10000 -n 1) > requests.log

#(shuf -i 1-500 -n 1) > concurrent.log

for i in $(cat url.txt)  ##循环读取url.txt中的地址

do

(shuf -i 1-10000 -n 1) > requests.log
(shuf -i 1-500 -n 1) > concurrent.log
                                                         ##将测试的结果写入到test_ab.log
ab -n $(cat requests.log) -c $(cat concurrent.log) $i    ###>> test_ab.log &

done

#结束
