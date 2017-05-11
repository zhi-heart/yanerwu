package com.yanerwu;

import com.yanerwu.common.Constants;
import com.yanerwu.utils.PlatCsvFileNameFilter;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.io.File;

/**
 * @Author Zuz
 * @Date 2017/5/10 14:31
 * @Description
 */
public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    private static String sparkAppName = "spark test";
    private static String sparkPath = "spark://hadoop-master:7077";

    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder()
//                .master(sparkPath)
                .appName(sparkAppName)
                .config("spark.some.config.option", "some-value")
                .getOrCreate();

        String rule = ".*";

        File[] platFiles = new File("/data/hadoop").listFiles();
        for (File platFile : platFiles) {
            if (platFile.isDirectory()) {
                File[] csvFiles = platFile.listFiles(new PlatCsvFileNameFilter(rule));
                long totleSize = 0;
                for (File f : csvFiles) {
                    totleSize += f.length() / 1024 / 1024;
                }

                String platCode = platFile.getName();
                MDC.put("plat", platCode);

                log.info("");
                log.info("");
                log.info("");
                log.info("---------------------------------开始处理---------------------------------");
                log.info(String.format("总共需要处理[%s]文件,总共[%s]G", csvFiles.length, Double.valueOf(totleSize) / 1024));
                for (File f : csvFiles) {
                    //文件名不包含平台名  跳过
                    if(!f.getName().contains(platCode)){
                        continue;
                    }
                    String fileName = f.getName().split("\\.")[0];
                    String fileDate = f.getName().split("\\.")[1];
                    //file://本地文件
                    String readPath = String.format("file://%s", f.getAbsolutePath());
//                    String readPath = f.getAbsolutePath();
                    String writePath = String.format("/data/%s/%s.parquet", platCode, f.getName());
                    long l = System.currentTimeMillis();

                    if (fileName.startsWith("repay_dist_bidder_")) {
                        readRepayBidderCsv(spark, readPath, writePath);
                    } else if (fileName.startsWith("repay_dist_")) {
                        readRepayCsv(spark, readPath, writePath);
                    } else if (fileName.startsWith("borrow_list_")) {
                        readBorrowCsv(spark, readPath, writePath);
                    } else if (fileName.startsWith("bid_record_")) {
                        readBidCsv(spark, readPath, writePath);
                    }
                    log.info(String.format("处理文件[%s]结束,耗时[%s]", f.getName(), System.currentTimeMillis() - l));
                }
                log.info("---------------------------------处理结束---------------------------------");
            }
        }

    }

    private static void readRepayCsv(SparkSession spark, String readPath, String writePath) {
        Dataset<Row> rowDataset = spark.read().format("csv")
                .option("header", "false")
                .option("sep", "\t")
                .schema(Constants.READ_REPAY_CSV_STRUCT_TYPE)
                .load(readPath);

        rowDataset.write().format("parquet").mode(SaveMode.Overwrite).save(writePath);
    }

    private static void readRepayBidderCsv(SparkSession spark, String readPath, String writePath) {
        try {
            Dataset<Row> rowDataset = spark.read().format("csv")
                    .option("header", "false")
                    .option("sep", "\t")
                    .schema(Constants.REPAY_BIDDER_STRUCT_TYPE)
                    .load(readPath);

            rowDataset.write().format("parquet").mode(SaveMode.Overwrite).save(writePath);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(String.format(readPath), e);
        }
    }

    private static void readBorrowCsv(SparkSession spark, String readPath, String writePath) {

        Dataset<Row> rowDataset = spark.read().format("csv")
                .option("header", "false")
                .option("sep", "\t")
                .schema(Constants.READ_BORROW_CSV_STRUCT_TYPE)
                .load(readPath);

        rowDataset.write().format("parquet").mode(SaveMode.Overwrite)
                .save(writePath);
    }

    private static void readBidCsv(SparkSession spark, String readPath, String writePath) {
        Dataset<Row> rowDataset = spark.read().format("csv")
                .option("header", "false")
                .option("sep", "\t")
                .schema(Constants.READ_BID_CSV_STRUCT_TYPE)
                .load(readPath);

        rowDataset.write().format("parquet").mode(SaveMode.Overwrite)
                .save(writePath);
    }

}
