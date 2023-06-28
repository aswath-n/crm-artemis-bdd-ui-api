package com.maersk.crm.utilities.etl_util;

import com.google.gson.JsonParser;
import com.maersk.crm.utilities.TalendJobExecutorUtil;
import com.maersk.crm.utilities.etl_util.File_util;
import com.maersk.crm.utilities.etl_util.JDBC_Utils;
import com.maersk.crm.utilities.etl_util.S3_util;

public interface ETLBaseClass {

    JDBC_Utils db = new JDBC_Utils();
    File_util file = new File_util();
    S3_util s3 = new S3_util();
    EDIUtils edi=new EDIUtils();
    TalendJobExecutorUtil apiHelper=new TalendJobExecutorUtil();
    JsonParser parser = new JsonParser();
}
