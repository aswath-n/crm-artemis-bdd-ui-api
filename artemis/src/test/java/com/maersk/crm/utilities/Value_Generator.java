package com.maersk.crm.utilities;

//import jdk.nashorn.internal.runtime.regexp.RegExp;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/26/2020
 */
public class Value_Generator {


    public static Object createValue(String value) {

        ApiTestDataUtil data = ApiTestDataUtil.getApiTestDataUtilThreadLocal();

        List<String[]> matches = getMatches(value, "^(?:(\\w*)::)?(.*)");
        if (matches.isEmpty() || matches.get(0)[0] == null) {
            return value;
        }
        String format = matches.get(0)[0].toLowerCase();
        String parameter = matches.get(0)[1];

        if (StringUtils.isEmpty(parameter)) {
            parameter = "0";
        }
        switch (format) {
            case "default":
            case "def":
                return TemplateAction.DO_NOT_CHANGE;
            case "delete":
            case "deleted":
                return TemplateAction.DELETE;
            case "l":
            case "long":
                return Long.parseLong(parameter);
            case "d":
            case "double":
                return Double.parseDouble(parameter);
            case "b":
            case "bool":
            case "boolean":
                return Boolean.parseBoolean(parameter);
            case "i":
            case "int":
                return Integer.parseInt(parameter);
            case "random":
                return RandomStringUtils.randomNumeric(Integer.parseInt(parameter));
            case "randomstring":
                return RandomStringUtils.randomAlphanumeric(Integer.parseInt(parameter));
            case "npi":
                return data.genRandomNpi();
            case "stateproviderid":
            case "providergroupid":
                return data.genRandomStateProviderId();
            case "dateOfBirth":
                return data.generateRandomDOB();
            case "fdofmnth":
                return data.frstDyFmnth();
            case "ssn":
                return data.genRandomSSN();
            case "recipientId":
                return data.generate_random_externalId();
            case "caseNumber":
                return data.generate_random_CaseNumber();
            case "name":
                return data.genRandomName();
            case "email":
                return data.genRandomEmail();
            case "phone":
                return data.genRandomPhoneNumber();
            case "fein":
                return data.genRandomFein();
            case "uiid":
                return data.generate_random_uiid();
            case "state":
                return data.getArandomUSState();
            case "address":
                return data.genRandomAddress();
            case "timestamp":
                return Long.parseLong(data.calculateDateTime(parameter, 3).toString());
            case "datetimet":
            case "dtt": //2020-06-05T22:78:01.818Z
                return data.calculateDateTime(parameter, 0).toString();
            case "datetime":
            case "dt": //2020-06-05 22:78:01.818Z
                return data.calculateDateTime(parameter, 1).toString();
            case "date": //2020-06-05
                return data.calculateDateTime(parameter, 2).toString();
            case "days": //225 (days)
                return data.calculateDateTime(parameter, 4).toString();
            case "dayOf":// 1528394013601
                return data.calculateDateTime(parameter, 3).toString();
            case "todayzone":// mm/dd/yyyy
                return data.calculateDateTime(parameter, 6).toString();
            case "null":
                return null;
            case "addressLine1":
                return "";
            case "fdnxtmth":
                return data.frstDyNxtmnth();
            case "lstdaymnth":
                return data.lstDyFmnth();
            case "today":
                return data.today();
            case "anniversarydate":
                return data.getStartDate("anniversaryDate");
            case "anniversarydatedc":
                return data.getStartDate("anniversaryDateDC");
            case "1stdayofnextmonth":
                return data.getStartDate("1stDayofNextMonth");
            case "firstdayofnextyear":
                return data.getStartDate("firstDayOfNextYear");
            case "1stdayofpresentmonth":
                return data.getStartDate("1stDayofPresentMonth");
            case "1stdayoflastmonth":
                return data.getStartDate("1stDayofLastMonth");
            case "lastdayofthepresentyear":
                return data.getEndDate("lastDayOfThePresentYear");
            case "lastdayofpresentmonth":
                return data.getEndDate("lastDayofPresentMonth");
            case "highdate":
                return data.getEndDate("highDate");
            case "highdatedc":
                return data.getEndDate("highDate-DC");
            case "highdateETLver":
                return data.getEndDate("highDateETLver");
            case "future":
                return data.getEndDate("future");
            case "1stdayof2monthsfromnow":
                return data.getStartDate("1stDayOf2MonthsFromNow");
            case "lastdayoflastmonth":
                return data.getEndDate("lastDayofLastMonth");
            case "1stdayof2monthsbefore":
                return data.getStartDate("1stDayof2MonthsBefore");
            case "1stdayof3monthsbefore":
                return data.getStartDate("1stDayof3MonthsBefore");
            case "1stdayof12monthsbefore":
                return data.getStartDate("1stDayof12MonthsBefore");
            case "lastdayof3monthsfromnow":
                return data.getEndDate("lastDayOf3MonthsFromNow");
            case "cutoffstartdatehhw":
                return data.getStartDate("cutoffStartDateHHW");
            case "daybeforecutoffstartdatehhw":
                return data.getEndDate("DayBeforeCutoffStartDateHHW");
            case "cutoffstartdateimmigrantchildren":
                return data.getStartDate("cutoffStartDateImmigrantChildren");
            case "nextday":
                return data.getStartDate("nextDay");
            case "nextdayplusone":
                return data.getStartDate("nextDayPlusOne");
            default:
                return format + RandomStringUtils.randomNumeric(Integer.parseInt(parameter));
        }
    }

    public static List<String[]> getMatches(String text, String reg) {
        List<String[]> res = new ArrayList<>();
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = pattern.matcher(text);

        while (m.find()) {
            String[] s = new String[m.groupCount()];
            for (int i = 1; i <= m.groupCount(); i++) {
                s[i - 1] = m.group(i);
            }
            res.add(s);
        }
        return res;
    }

    public enum TemplateAction {
        DELETE(), DO_NOT_CHANGE()
    }

}
