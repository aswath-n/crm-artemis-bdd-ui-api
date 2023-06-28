package com.maersk.crm.utilities.etl_util;

import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.Map;

public class ETL_Storage {

    private static ETL_Storage instance;
    private Map<String, JsonArray> providers = new HashMap<>();

    private ETL_Storage() {
        instance = this;
    }

    public static ETL_Storage getInstance() {
        return instance == null ? new ETL_Storage() : instance;
    }

    public static void reset() {
        instance = new ETL_Storage();
    }

    public void saveProvider(String name, JsonArray providerLine) {
        providers.put(name, providerLine);
    }

    public JsonArray getProviderLines(String name) {
        return providers.get(name);
    }
}
