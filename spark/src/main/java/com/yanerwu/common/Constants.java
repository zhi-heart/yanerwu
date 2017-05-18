package com.yanerwu.common;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * @Author Zuz
 * @Date 2017/5/11 10:17
 * @Description
 */
public class Constants {
    public static StructType READ_REPAY_BIDDER_CSV_STRUCT_TYPE = new StructType(new StructField[]{
            new StructField("loan_id", DataTypes.StringType, false, Metadata.empty()),
            new StructField("bidder", DataTypes.StringType, false, Metadata.empty()),
            new StructField("success_date", DataTypes.DateType, false, Metadata.empty()),
            new StructField("repay_date", DataTypes.DateType, false, Metadata.empty()),
            new StructField("repay_amount", DataTypes.createDecimalType(20, 4), false, Metadata.empty()),
            new StructField("repay_principal", DataTypes.createDecimalType(20, 4), false, Metadata.empty()),
            new StructField("loan_period", DataTypes.createDecimalType(20, 4), false, Metadata.empty()),
    });


    public static StructType READ_BORROW_CSV_STRUCT_TYPE = new StructType(new StructField[]{
            new StructField("loan_id", DataTypes.StringType, false, Metadata.empty()),
            new StructField("loan_name", DataTypes.StringType, true, Metadata.empty()),
            new StructField("amount", DataTypes.createDecimalType(20, 4), true, Metadata.empty()),
            new StructField("interest_rate", DataTypes.createDecimalType(8, 4), true, Metadata.empty()),
            new StructField("reward", DataTypes.StringType, false, Metadata.empty()),
            new StructField("loan_period", DataTypes.createDecimalType(20, 4), true, Metadata.empty()),
            new StructField("repayment_type", DataTypes.StringType, true, Metadata.empty()),
            new StructField("loan_type", DataTypes.StringType, false, Metadata.empty()),
            new StructField("loan_url", DataTypes.StringType, false, Metadata.empty()),
            new StructField("borrower", DataTypes.StringType, false, Metadata.empty()),
            new StructField("success_time_date", DataTypes.DateType, true, Metadata.empty()),
            new StructField("image", DataTypes.StringType, false, Metadata.empty()),
            new StructField("use_for", DataTypes.StringType, false, Metadata.empty()),
            new StructField("province", DataTypes.StringType, false, Metadata.empty()),
            new StructField("city", DataTypes.StringType, false, Metadata.empty()),
            new StructField("revenue", DataTypes.StringType, false, Metadata.empty()),
            new StructField("practice_type", DataTypes.IntegerType, false, Metadata.empty()),
            new StructField("plate_type", DataTypes.StringType, false, Metadata.empty()),
            new StructField("guarantors_type", DataTypes.StringType, false, Metadata.empty()),
            new StructField("is_agency", DataTypes.StringType, false, Metadata.empty())
    });

    public static StructType READ_BID_CSV_STRUCT_TYPE = new StructType(new StructField[]{
            new StructField("loan_id", DataTypes.StringType, true, Metadata.empty()),
            new StructField("bidder_name", DataTypes.StringType, true, Metadata.empty()),
            new StructField("amount_bid", DataTypes.createDecimalType(20, 4), true, Metadata.empty()),
            new StructField("bid_time_date", DataTypes.DateType, true, Metadata.empty()),
            new StructField("no", DataTypes.IntegerType, false, Metadata.empty()),
            new StructField("amount_orig", DataTypes.createDecimalType(20, 4), false, Metadata.empty()),
            new StructField("bid_type", DataTypes.IntegerType, false, Metadata.empty()),
            new StructField("status", DataTypes.IntegerType, false, Metadata.empty()),
            new StructField("source_type", DataTypes.IntegerType, false, Metadata.empty())
    });

    public static StructType READ_REPAY_CSV_STRUCT_TYPE = new StructType(new StructField[]{
            new StructField("loan_id", DataTypes.StringType, false, Metadata.empty()),
            new StructField("borrower", DataTypes.StringType, false, Metadata.empty()),
            new StructField("success_date", DataTypes.DateType, false, Metadata.empty()),
            new StructField("repay_date", DataTypes.DateType, false, Metadata.empty()),
            new StructField("repay_amount", DataTypes.createDecimalType(20, 4), false, Metadata.empty()),
            new StructField("principal_percentage", DataTypes.createDecimalType(20, 4), false, Metadata.empty()),
            new StructField("repay_percentage", DataTypes.createDecimalType(20, 4), false, Metadata.empty()),
            new StructField("share_of_principal", DataTypes.createDecimalType(20, 4), false, Metadata.empty()),
            new StructField("loan_period", DataTypes.createDecimalType(20, 4), false, Metadata.empty()),
            new StructField("loan_type", DataTypes.StringType, false, Metadata.empty()),
    });
}
