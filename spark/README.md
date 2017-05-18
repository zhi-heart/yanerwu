#spark demo
```
#进入目录
cd /Users/Zuz/Documents/workspace-idea/yanerwu
mvn clean package -pl spark -am -amd -Dmaven.text.skip=true -U
#传到spark服务器
scp spark/target/spark.jar root@192.168.8.20:/root/spark
#执行spark-submit
spark-submit --class com.yanerwu.Main --master=local ~/spark/spark.jar
```

