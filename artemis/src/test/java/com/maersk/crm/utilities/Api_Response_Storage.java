package com.maersk.crm.utilities;

import com.fasterxml.jackson.databind.node.NullNode;


/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/19/2020
 */
public class Api_Response_Storage {
    private static Api_Response_Storage instance;
    private String body;
    public Api_Response_Storage(){
        instance = this;
    }

    public static Api_Response_Storage getInstance(){
        return instance == null ? new Api_Response_Storage() :instance;
    }

    public Api_Response_Storage(String body) {
        this.body = body;
    }

    public  String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public  <T> T getBodyAttribute(String key) {
        T value = JsUtill.getJsonValue(getBody(), key);
        return value instanceof NullNode ? null : value;
    }


    public <T> T getBodyAttribute(String key, Class<T> aClass) {
        return JsUtill.getJsonValue(getBody(), key, aClass);
    }
}
