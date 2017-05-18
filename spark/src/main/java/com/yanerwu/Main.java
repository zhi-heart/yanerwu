package com.yanerwu;

import com.yanerwu.common.Constants;
import com.yanerwu.utils.PlatCsvFileNameFilter;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

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
    private static String localFilePath = "/data";

    public static void main(String[] args) {

        run();

    }

    /**
     * 读取本地csv文件,转换为parquet 存入hdfs
     */
    private static void run() {
        SparkSession spark = SparkSession.builder()
//                .master(sparkPath)
                .appName(sparkAppName)
                .config("spark.some.config.option", "some-value")
                .getOrCreate();

        String rule = ".*";

        File[] platFiles = new File(localFilePath).listFiles();
        for (File platFile : platFiles) {
            if (platFile.isDirectory()) {
                String platCode = platFile.getName();
                try {
                    File[] csvFiles = platFile.listFiles(new PlatCsvFileNameFilter(rule));
                    long totalSize = 0;
                    for (File f : csvFiles) {
                        totalSize += f.length() / 1024 / 1024;
                    }

                    MDC.put("plat", platCode);
                    long pl = System.currentTimeMillis();

                    log.info("");
                    log.info("");
                    log.info("");
                    log.info("---------------------------------开始处理---------------------------------");
                    log.info(String.format("总共需要处理[%s]文件,总共[%s]G", csvFiles.length, Double.valueOf(totalSize) / 1024));
                    for (File f : csvFiles) {
                        try {
                            //文件名不包含平台名  跳过
                            if (!f.getName().contains(platCode)) {
                                continue;
                            }

                            int type;

                            if (f.getName().startsWith("repay_dist_bidder_")) {
                                type = 4;
                            } else if (f.getName().startsWith("repay_dist_")) {
                                type = 3;
                            } else if (f.getName().startsWith("bid_record_")) {
                                type = 2;
                            } else if (f.getName().startsWith("borrow_list_")) {
                                type = 1;
                            } else {
                                log.info(String.format("非规则文件[%s]跳过", f.getName()));
                                continue;
                            }

                            String fileName = f.getName().split("\\.")[0];
                            String fileDate = f.getName().split("\\.")[1];
                            //file://本地文件
                            String readPath = String.format("file://%s", f.getAbsolutePath());
                            //                    String readPath = f.getAbsolutePath();
                            String writePath = String.format("/data/%s/%s.parquet", platCode, f.getName());
                            long l = System.currentTimeMillis();

                            readCsv(spark, type, readPath, writePath);

                            FileUtils.moveFile(f, new File(String.format("%s/_bak/%s", platFile.getAbsolutePath(), f.getName())));

                            log.info(String.format("处理文件[%s]结束,耗时[%s]毫秒", f.getName(), System.currentTimeMillis() - l));
                        } catch (Exception e) {
                            log.error(String.format("处理文件异常[%s]!", f.getAbsolutePath()), e);
                        }
                    }
                    log.info(String.format("处理平台[%s]结束,耗时[%s]分钟", platCode, (System.currentTimeMillis() - pl) / 1000 / 60));
                    log.info("---------------------------------处理结束---------------------------------");
                } catch (Exception e) {
                    log.error(String.format("处理平台异常[%s]!", platCode), e);
                }
            }
        }
    }

    /**
     * 读取csv文件,转换为parquet存入hdfs
     *
     * @param spark
     * @param type      1:borrow,2:bidder,3:repay,4:repay_bidder
     * @param readPath
     * @param writePath
     */
    private static void readCsv(SparkSession spark, int type, String readPath, String writePath) {
        StructType st = null;
        switch (type) {
            case 1:
                st = Constants.READ_BORROW_CSV_STRUCT_TYPE;
                break;
            case 2:
                st = Constants.READ_BID_CSV_STRUCT_TYPE;
                break;
            case 3:
                st = Constants.READ_REPAY_CSV_STRUCT_TYPE;
                break;
            case 4:
                st = Constants.READ_REPAY_BIDDER_CSV_STRUCT_TYPE;
                break;
        }
        Dataset<Row> rowDataset = spark.read().format("csv")
                .option("header", "false")
                .option("sep", "\t")
                .schema(st)
                .load(readPath);

        rowDataset.write().format("parquet").mode(SaveMode.Overwrite).save(writePath);
    }

}
