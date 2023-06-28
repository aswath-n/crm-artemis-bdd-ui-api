package com.maersk.crm.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import com.maersk.crm.api_step_definitions.APIConsumerSearchController;
import io.cucumber.core.exception.CucumberException;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

import static com.maersk.crm.utilities.JsUtill.compare;
import static com.maersk.crm.utilities.Value_Generator.createValue;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/22/2020
 */
public class Api_CommonSteps extends BrowserUtils{
    Api_Body api = Api_Body.getInstance();
    JsUtill js = JsUtill.getInstance();
    Api_Storage stg = Api_Storage.getInstance();

  public String asStr(String key) {
        return Optional.ofNullable(defineValue(key)).map(o -> {
            if (o instanceof ArrayNode) {
                if (((ArrayNode) o).size() != 1) {
                    throw new CucumberException("expecting element: " + o);
                } else if (((ArrayNode) o).get(0) instanceof NullNode) {
                    return null;
                } else {
                    ((ArrayNode) o).get(0).asText();
                }
            }
            return o.toString();
        }).orElse(null);
    }

    public Object defineValue(String key) {
        if (null == key) return null;
        String uuid = UUID.randomUUID().toString();
        Object jsonValue = uuid;
        Object dsValue;
        String[] x = key.split("\\.", 2);
        if (x.length > 1) {
            try {
                jsonValue = convert(js.getJsonValue(stg.findEvent(x[0]).getBody(), x[1]));
            } catch (Exception ignore) {

            }
        }
        dsValue = js.unmask(key);
        return jsonValue != uuid ? jsonValue : dsValue != key ? dsValue : createValue(key);
    }

    public static Object convert(Object value) {
        if (value instanceof TextNode) {
            return ((TextNode) value).asText();
        } else if (value instanceof IntNode) {
            return ((IntNode) value).asDouble();
        } else if (value instanceof DoubleNode) {
            return ((DoubleNode) value).asDouble();
        } else if (value instanceof LongNode) {
            return ((LongNode) value).asDouble();
        } else if (value instanceof BooleanNode) {
            return ((BooleanNode) value).asBoolean();
        } else if (value instanceof NullNode) {
            return null;
        } else {
            return value;
        }
    }

    public List<JsonNode> asList(String path) {
        final Object o = defineValue(path);
        if (o instanceof ArrayNode) {
            List<JsonNode> res = new ArrayList<>();
            ((ArrayNode) o).forEach(res::add);
            return res;
        } else {
            throw new CucumberException("List not created >>>>  " + o.toString());

        }
    }

    public boolean isValueExist(String path) {
        Object k = defineValue(path);
        return k == null
                || !(k.toString().equals(path)
                || ((path.contains("[?(@") || path.contains("..")) && k.toString().equals("[]")));
    }

    public Integer asInt(String key) {
        String val = asStr(key);
        if (val == null) return null;
        if (val.matches("\\d+\\.0+$")) {
            val = val.replaceFirst("\\.0+$", "");
        }
        if (val.matches("\\d+")) {
            return Integer.parseInt(val);
        } else
            throw new CucumberException("Value cannot be converted to Integer");
    }

    public Long asLong(String key) {
        return Optional.ofNullable(defineValue(key))
                .map(e -> Long.parseLong(e.toString()))
                .orElse(null);
    }

    public String updateJson(String json, Map<String, String> data) {
        for (String k : data.keySet()) {
            String key = asStr(k);
            Object value = defineValue(data.get(k));
            json = JsUtill.setJsonAttribute(json, key, value);
        }
        return json;
    }

    public void verifyThat(String k1,String comparisonSign,String k2){
        Object o1 = defineValue(k1);
        Object o2 = defineValue(k2);
        compare(o1,comparisonSign,o2);
    }

}
