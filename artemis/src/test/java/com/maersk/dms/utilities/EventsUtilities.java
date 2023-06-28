package com.maersk.dms.utilities;

import com.google.gson.JsonObject;
import com.maersk.crm.utilities.APIClassUtil;
import com.maersk.crm.utilities.ConfigurationReader;
import com.maersk.crm.utilities.Driver;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.maersk.crm.utilities.BrowserUtils.validNumberFilter;

public class EventsUtilities {

    /**
     * @param idType Filters logs for correlation ids or uiid
     * @return list of correlation or uiid
     */

    public static String oneloginJWTValue;

    public static List<String> getLogs(String idType) {
        LogEntries logEntries = Driver.getDriver().manage().logs().get(LogType.PERFORMANCE);
        System.out.println("Printing complete logs "+logEntries);
        List<String> list = new ArrayList<>();
        for (LogEntry entry : logEntries) {
            switch (idType.toLowerCase()) {
                //add case to parse for new
                case "correlationid":
                    if (entry.getMessage().contains("correlationId")) {
                        list.add(parseCorrelationId(entry.getMessage()));
                    }
                    break;
                //pass as formated date or as Long
                case "uiid":
                    if (entry.getMessage().contains("uiid")) {
                        list.add((parseUiid(entry.getMessage())));
                    }
                    break;
                case "traceid":
                    if (entry.getMessage().contains("traceid")) {
                        list.add(parseTraceid(entry.getMessage()));
                    }
                    break;
                case "cookie":
                    if (entry.getMessage().contains("cookie")) {
                        list.add(parseCookie(entry.getMessage()));
                    }
                    break;
                case "onelogin-jwt":
                    if (entry.getMessage().contains("onelogin-jwt")) {
                        list.add(parseCookie(entry.getMessage()));
                    }
                    break;
                default:
                    Assert.fail("No matching cases found for search value - " + idType + " | You can add a case or send a different value");
            }
        }
        System.out.println("Filterting"+filterOutDupes(list));
        return filterOutDupes(list);
    }

    //used by get logs
    private static String parseTraceid(String log) {
        Pattern pattern = Pattern.compile("traceid\":\"[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(log);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(log.substring(matcher.start(), matcher.end()));
        }
        result = new StringBuilder(validAlphanumericFilter(result.toString()));
        System.out.println("TracidPrase "+result.toString().replace("traceid", "") );
        return result.toString().replace("traceid", "");
    }

    private static String validAlphanumericFilter(String text) {
        String result = "";
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c) || Character.isLetter(c)) {
                result += String.valueOf(c);
            }
        }
        return result;
    }

    //used by getLogs
    private static List<String> filterOutDupes(List<String> withDupes) {
        List<String> withoutDupes = new ArrayList<>();
        for (String item : withDupes) {
            if (!withoutDupes.contains(item)) {
                withoutDupes.add(item);
                System.out.println(" printing dupes"+withoutDupes.add(item));
            }
        }
        return withoutDupes;
    }

    //used by getLogs
    public static String onlyOneUnique(List<String> input) {
        HashSet<String> uniqueStrings = new HashSet<>(input);
        Assert.assertEquals(1, uniqueStrings.size(), "Expected a single unique value, but found " + String.join(",", uniqueStrings));
        return uniqueStrings.stream().findFirst().orElseThrow(AssertionError::new);
    }

    /**
     * Filters logs for correlation ids and other params (ids that need parsing) added to switch cases +
     * additional params
     *
     * @param idType           will search for correlation id other cases added
     * @param additionalParams KEY WORDS MUST MATCH EXACTLY WHAT IS IN LOGS. additional params to filter for. each additonal param
     *                         will reduce more and more (or logic, but will assert fail if none are a match)
     * @return return list of correlation or uiid
     */
    public static List<String> getLogs(String idType, String... additionalParams) {
        LogEntries logEntries = Driver.getDriver().manage().logs().get(LogType.PERFORMANCE);
        System.out.println("Printing logs "+logEntries);
        List<String> additionalParamsFilteredList = new ArrayList<>();
        List<String> searchFilteredList = new ArrayList<>();
        //add cases for additional ids and create parse methods
        for (LogEntry entry : logEntries) {
            switch (idType.toLowerCase()) {
                //add case to parse for new
                case "correlationid":
                    if (entry.getMessage().contains("correlationId")) {
                        searchFilteredList.add(entry.getMessage());
                    }
                    break;
                //pass as formated date or as Long
                case "uiid":
                    if (entry.getMessage().contains("uiid")) {
                        searchFilteredList.add(entry.getMessage());
                    }
                    break;
                case "traceid":
                    if (entry.getMessage().contains("traceid")) {
                        searchFilteredList.add(entry.getMessage());
                    }
                    break;
                case "cookie":
                    if (entry.getMessage().contains("cookie")) {
                        searchFilteredList.add(entry.getMessage());
                    }
                    break;
                case "onelogin-jwt":
                    if (entry.getMessage().contains("onelogin-jwt")) {
                        //System.out.println("logs traceojwt"+ searchFilteredList.add(entry.getMessage()));
                        searchFilteredList.add(entry.getMessage());
                    }
                    break;
                    default:
                    Assert.fail("No matching cases found for search value - " + idType + " | You can add a case or send a different value");
            }
        }

        additionalParamsFilteredList = filterAdditionalParams(searchFilteredList, additionalParams);
        System.out.println("Onelogin_jwt_filter "+filterOutDupes(fiterForIds(idType, additionalParamsFilteredList)) );
        return filterOutDupes(fiterForIds(idType, additionalParamsFilteredList));
    }

    //used by getlogs
    private static String parseUiid(String log) {
        Pattern pattern = Pattern.compile("uiid\\\\\\\":\\\\\\\"([0-9a-zA-Z])*");
        Matcher matcher = pattern.matcher(log);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(log.substring(matcher.start(), matcher.end()));
        }
        result = new StringBuilder(validNumberFilter(result.toString()));
        return result.toString();
    }

    /**
     * Checks if format matches this yyyy-mm-ddThh:mm:ss.nnnnnn+|-hh:mm
     *
     * @param date date in string format
     * @return boolean if matches UTC format
     * @author Ozgen
     */
    public static boolean isValidDate(String date) {
        Pattern pattern = Pattern.compile("^[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0-9][0-9]:[0-9][0-9]");
        Matcher matcher = pattern.matcher(date);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(date.substring(matcher.start(), matcher.end()));
        }
        return date.equals(result.toString());
    }

    /**
     * Checks if format matches this yyyy-mm-ddThh:mm:ss.nnnnnnZ (Zulu Time Format)
     *
     * @param date date in string format
     * @return boolean if matches UTC format
     * @author Ozgen
     */

    public static boolean isValidDateZulu(String date) {
        Pattern pattern = Pattern.compile("^[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][a-zA-Z]");
        Matcher matcher = pattern.matcher(date);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(date.substring(matcher.start(), matcher.end()));
        }
        return date.equals(result.toString());
    }

    /**
     * Checks if format matches this yyyy-mm-ddThh:mm:ss.nnnnnn+|-hh:mm
     *
     * @param date date in string format
     * @return boolean if matches UTC format
     * @author Ozgen
     */
    public static boolean isValidOnbaseDate(String date) {
        Pattern pattern = Pattern.compile("^[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])T[0-2]?[0-9]:[0-5][0-9]:[0-6][0-9].[0-9][0-9][0-9][0-9][0-9][0-9][+,-]?[0][0-9]:[0][0]");
        Matcher matcher = pattern.matcher(date);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(date.substring(matcher.start(), matcher.end()));
        }
        return date.equals(result.toString());
    }

    //used by getLogs
    private static List<String> fiterForIds(String idType, List<String> unfilteredList) {
        List<String> filteredList = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        if ("uiid".equalsIgnoreCase(idType)) {
            for (String log : unfilteredList) {
                ids.add(parseUiid(log));
            }
        } else if ("traceid".equalsIgnoreCase(idType)) {
            for (String log : unfilteredList) {
                ids.add(parseTraceid(log));
            }
        }else if ("cookie".equalsIgnoreCase(idType)) {
            for (String log : unfilteredList) {
                ids.add(parseCookie(log));
            }
        } else {
            for (String log : unfilteredList) {
                ids.add(parseCorrelationId(log));
            }
        }
        for (String entry : ids) {
            if (!filteredList.contains(idType)) {
                filteredList.add(entry);
            }
        }
        return filteredList;
    }

    //used by getLogs
    private static List<String> filterAdditionalParams(List<String> fullList, String[] additionalParams) {
        int originalSize = fullList.size();
        List<String> reduced;
        for (String params : additionalParams) {
            reduced = new ArrayList<>();
            for (String entry : fullList) {
                if (entry.contains(params)) {
                    reduced.add(entry);
                }
            }
            if (reduced.size() > 0) {
                fullList = reduced;
            }
        }
        if (fullList.size() == originalSize) {
            Assert.fail("Did not match anything from additionalParams");
        }
        return fullList;
    }

    /**
     * @param search broad keyword to search logs
     * @param regex  send custom regex statement to parse found logs
     * @return list of parsed values from logs
     */
    public static List<String> getLogsRegex(String search, String regex) {
        LogEntries logEntries = Driver.getDriver().manage().logs().get(LogType.PERFORMANCE);
        List<String> list = new ArrayList<>();
        for (LogEntry entry : logEntries) {
            if (entry.getMessage().contains(search)) {
                list.add(parse(regex, entry.getMessage()));
            }
        }
        return list;
    }

    /**
     * @param search search keyword in logs
     * @return unparsed logs that match search
     */
    public static List<String> getRawLogs(String search) {
        LogEntries logEntries = Driver.getDriver().manage().logs().get(LogType.PERFORMANCE);
        List<String> list = new ArrayList<>();
        for (LogEntry entry : logEntries) {
            if (entry.getMessage().contains(search)) {
                list.add(entry.getMessage());
            }
        }
        return list;
    }

    /**
     * @param search search keyword in logs
     * @return unparsed logs that match search
     */
    public static List<String> getRawLogs(String search, String... additionalParams) {
        LogEntries logEntries = Driver.getDriver().manage().logs().get(LogType.PERFORMANCE);
        List<String> list = new ArrayList<>();
        for (LogEntry entry : logEntries) {
            if (entry.getMessage().contains(search)) {
                list.add(entry.getMessage());
            }
        }
        return filterAdditionalParams(list, additionalParams);
    }

    //used by getLogs
    private static String parseCorrelationId(String log) {
        Pattern pattern = Pattern.compile("correlationId\\\\\\\":\\\\\\\"([0-9a-zA-Z])*");
        Matcher matcher = pattern.matcher(log);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(log.substring(matcher.start(), matcher.end()));
        }
        result = new StringBuilder(validNumberFilter(result.toString()));
        return result.toString();
    }

    //used by getLogs
    private static String parse(String regex, String log) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(log);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(log.substring(matcher.start(), matcher.end()));
        }
        return result.toString();
    }

    public static boolean isValidTime(String time) {
        Pattern pattern = Pattern.compile("^((?=(?:\\D*\\d\\D*){6})(?:[01]\\d|2[0-3]):(?:[0-5]\\d):(?:[0-5]\\d))$");
        Matcher matcher = pattern.matcher(time);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(time.substring(matcher.start(), matcher.end()));
        }
        return time.equals(result.toString());
    }

    public static boolean genericValidator(String time, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(time.substring(matcher.start(), matcher.end()));
        }
        return time.equals(result.toString());
    }
    public static String getGenericMatch(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(input.substring(matcher.start(), matcher.end()));
        }
        return result.toString();
    }

    public void verifyEventMapping(String event) {
        APIClassUtil apiClassUtil = APIClassUtil.getApiClassUtilThreadLocal(true);
        JsonPath response;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("subscriberName", "DPBI");

               // apiClassUtil.setbaseUri("https://mars-event-api-qa.apps.non-prod.pcf-maersk.com");
              // apiClassUtil.setEndPoint("/app/crm/es/event/subscribers?size=100000000&page=0&sort=eventSubscriberMappingId,desc");

               apiClassUtil.setbaseUri(ConfigurationReader.getProperty("apiMarsEvent").split("/app")[0]);
              apiClassUtil.setEndPoint("/app/crm/es/event/subscribers?size=100000000&page=0&sort=eventSubscriberMappingId,desc");

        response =  apiClassUtil.PostAPIWithParameter(jsonObject.toString()).jsonPathEvaluator;
                Assert.assertEquals(apiClassUtil.statusCode, 200);
                List<String> mappings = response.getList("subscriberEntity[0].subscriberList.eventName");
                boolean mappingFound = false;
                int counter = 0;
                for (String json : mappings) {
                    if (event.equalsIgnoreCase(json)) {
                        mappingFound = true;
                        counter++;
                    }
                }
                Assert.assertTrue(mappingFound, "mapping not found");
                Assert.assertTrue(counter == 1);

    }

    public static boolean isOnlyValidDate(String date) {
        Pattern pattern = Pattern.compile("^[0-2][0-9][0-9][0-9]-(0?[1-9]|1[012])-([0-2][0-9]|3?[01])");
        Matcher matcher = pattern.matcher(date);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(date.substring(matcher.start(), matcher.end()));
        }
        return date.equals(result.toString());
    }

 //used by getlogs
    private static String parseCookie(String log) {
        Pattern pattern = Pattern.compile("cookie\":\"[a-zA-Z0-9$&+,:;=?_@#| -]*");
        Matcher matcher = pattern.matcher(log);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(log.substring(matcher.start(), matcher.end()));
        }
        //result = new StringBuilder(validNumberFilter(result.toString()));
        return result.toString();
    }


    /*//used by get logs
    private static String parseTraceid(String log) {
        Pattern pattern = Pattern.compile("traceid\":\"[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(log);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(log.substring(matcher.start(), matcher.end()));
        }
        result = new StringBuilder(validAlphanumericFilter(result.toString()));
        System.out.println("TracidPrase "+result.toString().replace("traceid", "") );
        return result.toString().replace("traceid", "");
    }*/


    //used by getLogs
    public static String parseOneloginJWT(String log) {
        Pattern pattern = Pattern.compile("\"onelogin-jwt\":\"[^\"]*");
        Matcher matcher = pattern.matcher(log);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(log.substring(matcher.start(), matcher.end()));
        }
        System.out.println("Log Prase jwt : "+result);
        System.out.println("Log Prase jwt 1: "+result.toString().replace("onelogin-jwt", "").substring(4));
        oneloginJWTValue = result.toString().replace("onelogin-jwt", "").substring(4);
        return oneloginJWTValue ;
    }
    public static boolean isValueEmptyString(String value) {
        boolean isEmptyString = false;
        try {
            if (value.isEmpty()) {
                isEmptyString = true;
            }
        } catch (NullPointerException ex) {
            System.out.println("NUll value is expected");
        }
        return isEmptyString;
    }
}
