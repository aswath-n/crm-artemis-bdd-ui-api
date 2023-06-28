package com.maersk.crm.utilities;

import org.apache.commons.codec.binary.Base64;

public class EncodingUtil {
    /** @author aika
     *
     */

    //encode a string using Apache Commons Base 64 api

    public static String encode(String property){
        String encodedStr=null;
        encodedStr= Base64.encodeBase64String(property.getBytes());
        return  encodedStr;
    }

    //decode a string using Apache Commons Base 64 api
    public static String decode(String property){
        String decodedStr=null;
       if(org.apache.commons.lang3.StringUtils.isNotBlank(property) && Base64.isBase64(property))
           decodedStr=new String(Base64.decodeBase64(property));
        return  decodedStr;
    }
}
