package com.maersk.crm.utilities;

import com.fasterxml.jackson.databind.node.NullNode;
import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import static org.hamcrest.number.OrderingComparison.*;
import static org.junit.Assert.*;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/19/2020
 */
public class JsUtill {
    private static JsUtill instance = new JsUtill();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private LinkedHashMap<String, Object> data = new LinkedHashMap<>();


    public static JsUtill getInstance() {
        return instance;
    }

    private static DocumentContext parseJson(String json) {
        Configuration CONFIGURATION = Configuration.builder()
                .jsonProvider(new JacksonJsonNodeJsonProvider())
                .mappingProvider(new JacksonMappingProvider())
                .options(Option.SUPPRESS_EXCEPTIONS)
                .build();
        return com.jayway.jsonpath.JsonPath.using(CONFIGURATION).parse(json);
    }

    public static <T> T getJsonValue(String json, String jsonPath, Class<T> clazz, boolean... dieIfElementNotExist) {
        if (dieIfElementNotExist.length > 0 && dieIfElementNotExist[0]) {
            //for assert is not exist
        }
        return parseJson(json).read(jsonPath, clazz);
    }

    public static <T> T getJsonValue(String json, String jsonPath, boolean... dieIfElementNotExist) {
        if (dieIfElementNotExist.length > 0 && dieIfElementNotExist[0]) {
            //for assert is not exist
        }
        return parseJson(json).read(jsonPath);
    }

    public static boolean isJsonPathExists(String json, String jsonPath) {
        try {
            DocumentContext dc = com.jayway.jsonpath.JsonPath.using(Configuration.defaultConfiguration()).parse(json);
            dc.read(jsonPath);
        } catch (PathNotFoundException e) {
            return false;
        }
        return true;
    }

    public static String setJsonAttribute(String json, String jsonPath, Object value) {
        DocumentContext dc = parseJson(json);
        if (value == Value_Generator.TemplateAction.DELETE) {
            dc.delete(jsonPath);
        } else if (value == Value_Generator.TemplateAction.DO_NOT_CHANGE) {

        } else if (isJsonPathExists(json, jsonPath)) {
            dc.set(jsonPath, value);
        } else {
            final String[] elements = jsonPath.split("\\.");
            int i = 0;
            boolean delByMe = false;
            while (i < elements.length) {
                String path = String.join(".", Arrays.copyOfRange(elements, 0, i + 1));
                String pathPrev = i > 0 ? String.join(".", Arrays.copyOfRange(elements, 0, i)) : "$";
                final String[] e = Value_Generator.getMatches(elements[i], "(.*?)(?:\\[(\\d+)\\])?$").get(0);
                if (!isJsonPathExists(dc.jsonString(), path) || delByMe) {
                    if (e[1] == null) {
                        try {
                            if (getJsonValue(dc.jsonString(), pathPrev) instanceof NullNode) {
                                throw new InvalidModificationException("found null node");
                            }
                            dc.put(pathPrev, elements[i], OBJECT_MAPPER.createObjectNode());
                        } catch (InvalidModificationException e1) {
                            dc.delete(pathPrev);
                            delByMe = true;
                            --i;
                            continue;
                        }
                    } else {
                        if (!isJsonPathExists(dc.jsonString(), pathPrev + "." + e[0]) ||
                                !(getJsonValue(dc.jsonString(), pathPrev + "." + e[0]) instanceof ArrayNode)
                        ) {
                            dc.put(pathPrev, e[0], OBJECT_MAPPER.createArrayNode());
                            continue;
                        }
                        dc.add(pathPrev + "." + e[0], OBJECT_MAPPER.createObjectNode());
                    }
                }
                ++i;
            }
            dc.set(jsonPath, OBJECT_MAPPER.valueToTree(value));
        }
        return dc.jsonString();
    }

    public Object unmask(String key) {
        return data.getOrDefault(key, key);
    }

    public static void compare(Object val1, String compareSign, Object val2) {
        if (val1 == null || val2 == null) {
            verifyNull(val1, compareSign, val2);
        } else if (val1 instanceof String) {
            verifyEquals(val1, compareSign, val2);
        } else if (val1 instanceof Boolean) {
            verifyEquals(val1, compareSign, Boolean.parseBoolean(val2.toString()));
        } else {
            verifyAsBigDecimal(toBigDecimal(val1), compareSign, toBigDecimal(val2));
        }
    }

    private static void verifyNull(Object val1, String compSign, Object val2) {
        String msg = String.format("Value %s is not equal(%s) to value %s", val1, compSign, val2);
        boolean r = val1 == null && val2 == null;
        switch (compSign) {
            case "=":
            case "==":
            case "equal":
            case "equals":
                assertTrue(msg, r);
                break;
            default:
                assertTrue(msg, !r);
        }
    }

    private static void verifyEquals(Object val1, String compareSign, Object val2) {
        switch (compareSign) {
            case "=":
            case "==":
            case "equal":
            case "equals":
                assertEquals(val1, val2);
                break;
            case "!=":
            case "not_equal":
            case "not_equals":
                assertNotEquals(val1, val2);
                break;
            default:
                throw new RuntimeException(" Not valid comparison sing: " + compareSign);
        }
    }

    public static BigDecimal toBigDecimal(Object val) {
        int sc = 5;
        return val == null ? null : BigDecimal.valueOf(Double.parseDouble(val.toString()))
                .setScale(sc, BigDecimal.ROUND_HALF_UP);
    }

    private static void verifyAsBigDecimal(BigDecimal toBigDecimal, String compareSign, BigDecimal toBigDecimal1) {
        switch (compareSign){
            case "<":
                assertThat(toBigDecimal,lessThan(toBigDecimal1));
                break;
            case "<=":
                assertThat(toBigDecimal,lessThanOrEqualTo(toBigDecimal1));
                break;
            case ">":
                assertThat(toBigDecimal,greaterThan(toBigDecimal1));
                break;
            case ">=":
                assertThat(toBigDecimal,greaterThanOrEqualTo(toBigDecimal1));
                break;
            case "=":
            case "==":
                assertEquals(toBigDecimal,toBigDecimal1);
                break;
            case "!=":
                assertNotEquals(toBigDecimal,toBigDecimal1);
                break;
            default:
                throw new RuntimeException(" Unknown comparison sign:  "+ compareSign);
        }
    }
}
