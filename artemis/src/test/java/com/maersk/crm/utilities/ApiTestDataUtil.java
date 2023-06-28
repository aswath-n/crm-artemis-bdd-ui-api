package com.maersk.crm.utilities;
    /*  (PLEASE DONOT REMOVE THESE LINES. These are advance level random data solution for Cucumber)
        ${} – everything inside will be parsed, strings are comma separated
        numerical value – create random alphanumeric string
        ! – use the string as it is
        N – random numbers
        S – random alphanumeric uppercase string
        s – random alphanumeric lowercase string

        So ${!0046,N8} will produce ‘0046’ followed by 8 random numbers. ${!test,4,@,8,!.com} will generate ‘test’ followed by 4 random alphanumeric letters, symbol ‘@’, 8 random alphanumeric letters and ‘.com’.
        Implementation will be like this:
        Given I create new user named "user_1" with the following data:
        |first_name |last_name |mobile      |email               |user_name  |pass     |
        |${6}       |${8}      |${!0046,N8} |${!test,4,@,8,!.com}|${!test,8} |Success  |

        Step Definition:
            temp_hash = {}
            data_table.hashes.each do |hash|
            temp_hash["first_name"] = parse_input(hash["first_name"])
            # Continue storing rest of data to temp_hash
            # ...
     */

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

import com.google.gson.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.Random;

public class ApiTestDataUtil {

    public JsonElement rootElement;
    public JsonElement jsonElement;
    public List states;
    public String state;
    public String randomString;
    public String randomNumber;
    public String randomListValue;
    public long pastDates;
    public long futureDates;

    int lastWorkingDayOfTheCurrentMonth = getLastWorkingDayOfMonth(LocalDate.now(ZoneOffset.UTC).plusMonths(1).withDayOfMonth(1).minusDays(1)).getDayOfMonth();

    private static final String UINAMESURL = "https://uinames.com/api/?ext";

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter dfUIver = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    DateTimeFormatter dfETLver= DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final ThreadLocal<ApiTestDataUtil> apiTestDataUtilThreadLocal = ThreadLocal.withInitial(ApiTestDataUtil::new);

    private ApiTestDataUtil(){
    }

    public static ApiTestDataUtil getApiTestDataUtilThreadLocal() {
        if(apiTestDataUtilThreadLocal.get()==null) {
            synchronized (apiTestDataUtilThreadLocal){
                apiTestDataUtilThreadLocal.set(new ApiTestDataUtil());
            }
        }
        return apiTestDataUtilThreadLocal.get();
    }

    public synchronized static ApiTestDataUtil getApiTestDataUtilThreadLocal(boolean forceCreate) {
        if(forceCreate) {
          return new ApiTestDataUtil();
        }
        return apiTestDataUtilThreadLocal.get();
    }


    public ApiTestDataUtil getRandomAlphaNumeric(int stringLength) {
        boolean useLetters = true;
        boolean useNumbers = true;
        this.randomString = RandomStringUtils.random(stringLength, useLetters, useNumbers);
        return this;
    }

    public ApiTestDataUtil getRandomString(int stringLength) {
        boolean useLetters = true;
        boolean useNumbers = false;
        this.randomString = RandomStringUtils.random(stringLength, useLetters, useNumbers);
        return this;
    }

    public ApiTestDataUtil getRandomNumber(int numberLength) {
        boolean useLetters = false;
        boolean useNumbers = true;
        this.randomNumber = RandomStringUtils.random(numberLength, useLetters, useNumbers);
        return this;
    }

    public ApiTestDataUtil getJsonFromFile(String json_file_name) {
        JsonParser parser = new JsonParser();
        json_file_name = "testData/api/" + json_file_name;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(json_file_name);
        Reader reader = new InputStreamReader(inputStream);
        this.rootElement = parser.parse(reader);
        if (rootElement.isJsonArray())
            this.jsonElement = rootElement.getAsJsonArray();
        else
            this.jsonElement = rootElement.getAsJsonObject();
        return this;
    }

    public ApiTestDataUtil getArandomUSState() {
        String states = "AK,AL,AR,AS,AZ,CA,CO,CT,DC,DE,FL,FM,GA,GU,HI,IA,ID,IL,IN,KS,KY,LA,MA,MD,ME,MH,MI,MN,MO,MP,MS,MT,NC,ND,NE,NH,NJ,NM,NV,NY,OH,OK,OR,PA,PR,PW,RI,SC,SD,TN,TX,UT,VA,VI,VT,WA,WI,WV,WY";
        this.states = new ArrayList<String>(Arrays.asList(states.split(",")));
        Random randomizer = new Random();
        this.state = this.states.get(randomizer.nextInt(this.states.size())).toString();
        return this;
    }

    public ApiTestDataUtil getArandomUSStateName() {
        String states = "Alabama,Alaska,Arizona,Arkansas,California,Colorado,Connecticut,Delaware,Florida,Georgia,Hawaii,Idaho,Illinois,Indiana,Iowa,Kansas,Kentucky,Louisiana,Maine,Maryland,Massachusetts,Michigan,Minnesota,Mississippi,Missouri,Montana,Nebraska,Nevada,New Hampshire,New Jersey,New Mexico,New York,North Carolina,North Dakota,Ohio,Oklahoma,Oregon,Pennsylvania,Rhode Island,South Carolina,South Dakota,Tennessee,Texas,Utah,Vermont,Virginia,Washington,West Virginia,Wisconsin,Wyoming";
        this.states = new ArrayList<String>(Arrays.asList(states.split(",")));
        Random randomizer = new Random();
        this.state = this.states.get(randomizer.nextInt(this.states.size())).toString();
        return this;
    }

    public ApiTestDataUtil getARandomList(String stringList, Boolean isRandom, String expectedString) {
        String provisioningString = "Active,Inactive,Pending";
        ArrayList aList = new ArrayList<String>(Arrays.asList(stringList.split(",")));
        if (isRandom) {
            Random randomizer = new Random();
            this.randomListValue = aList.get(randomizer.nextInt(aList.size())).toString();
        } else if (expectedString != null && !expectedString.isEmpty()) {
            this.randomListValue = expectedString;
        } else {
            this.randomListValue = aList.get(0).toString();
        }
        return this;
    }

    public ApiTestDataUtil addProperty(JsonObject requestParams, String item, String value) {
        requestParams.addProperty(item, value);
        return this;
    }

    public ApiTestDataUtil getPastDates(int i) {
        Date myDate = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        System.out.print(cal.getTime() + " ==> ");
        cal.add(Calendar.DATE, -i);
        System.out.print(cal.getTime() + " ==> ");
        pastDates = cal.getTimeInMillis();
        System.out.println("PastDates");
        return this;
    }

    public ApiTestDataUtil getFutureDates(int i) {
        Date myDate = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        System.out.print(cal.getTime() + " ==> ");
        cal.add(Calendar.DATE, +i);
        System.out.print(cal.getTime() + " ==> ");
        System.out.println("FutureDates");
        futureDates = cal.getTimeInMillis();
        return this;
    }

    /**
     * Generate a Random CorrelationId
     */
    public String generate_random_correlation_id() {
        getRandomNumber(11);
        String apiconsumerCorrelationId = "9000" + randomNumber.toString();
        System.out.println("correlationID=" + apiconsumerCorrelationId);
        return apiconsumerCorrelationId;
    }

    /**
     * Generate a Random Uiid
     */
    public String generate_random_uiid() {
        getRandomNumber(9);
        String apiconsumerUiid = "9000" + randomNumber.toString();
        System.out.println("uiid=" + apiconsumerUiid);
        return apiconsumerUiid;
    }

    public Map getNewTestData() {
        Map<String, Object> names = new HashMap<>();
        Response response2 =
                given().accept(ContentType.JSON)
                        .and().params("amount", 1, "region", "United States")
                        .when().get(UINAMESURL);
        JsonPath json = response2.jsonPath();
        try {
            assertEquals(response2.statusCode(), 200);
        } catch (AssertionError ae) {
            names.put("name", RandomStringUtils.randomAlphabetic(8));
            return names;
        }

        return response2.body().as(Map.class);
    }

    public boolean compareHashMaps(HashMap<String, Object> mapA, HashMap<String, Object> mapB) {
        boolean matched = true;
        try {
            for (String y : mapA.keySet()) {
                if (!mapB.containsKey(y)) {
                    matched = false;
                }
            }
            for (Object k : mapB.keySet()) {
                if (!(mapA.get(k).toString()).equals((mapB.get(k).toString()))) {
                    System.out.println("mapA: " + mapA.get(k).toString() + " and the value of the key is " + k.toString());
                    System.out.println("mapB: " + mapB.get(k).toString() + " and the value of the key is " + k.toString());
                    matched = false;
                    break;
                }
            }

        } catch (NullPointerException np) {
            return matched;
        }
        return matched;
    }

    public static String generate_random_trace_id() {
        boolean useLetters = false;
        boolean useNumbers = true;
        RandomStringUtils.random(22, useLetters, useNumbers);
        String apiTraceId = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        System.out.println("Printing TraceId=" + apiTraceId);
        return apiTraceId;
    }

    public String addDaysToPresentDate(String dateFormat, int noOfDays, int noOfMonths, int noOfYears) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("UTC"));
            cal.add(Calendar.DATE, noOfDays);
            cal.add(Calendar.MONTH, noOfMonths);
            cal.add(Calendar.YEAR, noOfYears);
            return sdf.format(cal.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * created random npi number
     *
     * @return
     */
    public String genRandomNpi() {
        int npi = (int) ((Math.random() * ((200000000 - 100000000) + 1)) + 1000000000);
        System.out.println("Printing in the test class " + npi);
        return String.valueOf(npi);
    }

    public String genRandomStateProviderId() {
        int stateProviderId = (int) ((Math.random() * 100000000) + 100000000);
        System.out.println("Printing in the test class " + stateProviderId);
        return stateProviderId + "D";
    }

    public static Object calculateDateTime(String parameter, int output) {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("UTC"));

        if (!parameter.equals("0")) {
            final List<String[]> m = Value_Generator.getMatches(parameter, "^([+-]?\\d+)([smhdy])$");
            if (m.size() == 0)
                throw new IllegalArgumentException(String.format("Incorrect timestamp parameter: %s. Correct examples: -1s -5m +12h +1d -33y", parameter));

            final long shift = Long.parseLong(m.get(0)[0]);
            final String time = m.get(0)[1].toLowerCase();
            final TemporalUnit unit =
                    time.equals("s") ? ChronoUnit.SECONDS :
                            time.equals("m") ? ChronoUnit.MINUTES :
                                    time.equals("h") ? ChronoUnit.HOURS :
                                            time.equals("d") ? ChronoUnit.DAYS :
                                                    time.equals("y") ? ChronoUnit.YEARS : ChronoUnit.CENTURIES; // :)
            dateTime = dateTime.plus(shift, unit);
        }

        switch (output) {
            case 0: // 2018-06-07 21:45:01.818Z
                return dateTime.toOffsetDateTime().toString();
            case 1: // 2020-05-17 13:49:39.675053500Z
                return dateTime.toOffsetDateTime().toString().replaceFirst("T", " ");
            case 2: // 2018-06-07
                return dateTime.toLocalDate().toString();
            case 3: // 1528394013601
                return dateTime.toOffsetDateTime().toInstant().toEpochMilli();
            case 4: // 17702 (days from epoch)
                return Duration.between(ZonedDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.of("UTC")), dateTime).toDays();
            case 5: // 2018-06-07T21:45:01.818Z
                String dateUncover = dateTime.toOffsetDateTime().toString().substring(0, 23);
                String dateTType = dateUncover + "Z";
                System.out.println("Datatype " + dateTType);
                return dateTType;
            case 6: //dd/mm/yyyy
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                String dateFormat1 = dateTime.format(formatter1);
                return dateFormat1;
            default:
                throw new IllegalArgumentException("format not found: " + output);
        }




    }


    public static String getMonthFromInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    public String genRandomSSN() {
        int npi = (int) ((Math.random() * ((990000000 - 100000000) + 1)) + 100000000);
        return String.valueOf(npi);
    }

    public String genRandomFein() {
        int npi = (int) ((Math.random() * ((700000000 - 100000000) + 1)) + 100000000);
        return String.valueOf(npi);
    }


    /**
     * It returns the number of days to add to the present date so that we will get the 1st of next month
     *
     * @return
     */
    public int getNumberOfDaysToAddForNextFirstOfMonth() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String presentDate = now.format(formatter);
        String presentDayNumber = presentDate.split("-")[2];
        int numOfDaysInPresentMonth = Integer.parseInt(now.plusMonths(1).withDayOfMonth(1).minusDays(1).format(DateTimeFormatter.ofPattern("dd")));
        return numOfDaysInPresentMonth - Integer.parseInt(presentDayNumber) + 1;
    }

    /**
     * It returns the number of days to add to the present date so that we will get the 1st of next month
     *
     * @return
     */
    public int getNumberOfDaysToReduceForTheFirstOfThisMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String presentDate = now.format(formatter);
        String presentDayNumber = presentDate.split("-")[2];
        return Integer.parseInt(presentDayNumber) - 1;
    }

    public String getCurrentDateAndTime(String format) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        return sdf.format(cal.getTime());
    }

    public String getCurrentDate(String format) {
        // ex: "yyyy-MM-dd"
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return today.format(formatter);
    }

    public int getNumberOFBusinessDays(int days) {
        int daysToAdd = 0;
        DateTime result = new DateTime(getCurrentDate("yyyy-MM-dd")).plusDays(1);
        while (isWeekEnd(result)) {
            result = result.plusDays(1);
            daysToAdd++;
        }
        for (int i = 0; i < days; i++) {
            if (isWeekEnd(result)) {
                i--;
            }
            result = result.plusDays(1);
            daysToAdd++;
        }
        return daysToAdd;
    }

    private static boolean isWeekEnd(DateTime dateTime) {
        int dayOfWeek = dateTime.getDayOfWeek();
        return dayOfWeek == DateTimeConstants.SATURDAY || dayOfWeek == DateTimeConstants.SUNDAY;
    }

    public static LocalDate getLastWorkingDayOfMonth(LocalDate lastDayOfMonth) {
        LocalDate lastWorkingDayofMonth;
        switch (DayOfWeek.of(lastDayOfMonth.get(ChronoField.DAY_OF_WEEK))) {
            case SATURDAY:
                lastWorkingDayofMonth = lastDayOfMonth.minusDays(1);
                break;
            case SUNDAY:
                lastWorkingDayofMonth = lastDayOfMonth.minusDays(2);
                break;
            default:
                lastWorkingDayofMonth = lastDayOfMonth;
        }
        return lastWorkingDayofMonth;
    }


    public String getStartDate(String timeFrame) {
        int todaysDate = Integer.parseInt(addDaysToPresentDate("dd", 0, 0, 0));
        String dateToReturn = "";
        switch (timeFrame) {
            case "current":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).format(df);
                break;
            case "currentUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).format(dfUIver);
                break;
            case "currentETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).format(dfETLver);
                break;
            case "Nov1stCurrentYear":
                dateToReturn = getCurrentDate("yyyy") + "-11-01";
                break;
            case "past":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", 0, -10, 0);
                break;
            case "future":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusMonths(10).format(df);
                break;
            case "1stDayof12MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(12).format(df);
                break;
            case "1stDayof12MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(12).format(dfUIver);
                break;
            case "1stDayof6MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(6).format(df);
                break;
            case "1stDayof6MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(6).format(dfUIver);
                break;
            case "1stDayof2MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).format(df);
                break;
            case "1stDayof2MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).format(dfUIver);
                break;
            case "1stDayof3MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(3).format(df);
                break;
            case "1stDayof3MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(3).format(dfUIver);
                break;
            case "1stDayOf2MonthsFromNow":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).format(df);
                break;
            case "1stDayof10MonthsFromNow":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(10).format(df);
                break;
            case "1stDayOf2MonthsFromNowUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).format(dfUIver);
                break;
            case "1stDayOf3MonthsFromNow":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(3).format(df);
                break;
            case "1stDayOf3MonthsFromNowUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(3).format(dfUIver);
                break;
            case "1stDayOf4MonthsFromNow":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(4).format(df);
                break;
            case "HHWAnniversaryWindow":
                if(LocalDate.now(ZoneOffset.UTC).getDayOfMonth()<=25){
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(3).format(dfUIver);
                }else{
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(4).format(dfUIver);
                }
                break;
            case "1stDayofNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(df);
                break;
            case "1stDayofNextMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(dfUIver);
                break;
                // change all firstdayofNextMonth to 1stDayofNextMonthUIver and then delete below 3 lines
            case "firstdayofNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(dfUIver);
                break;
            case "firstdayofNextMonthETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(dfETLver);
                break;
            case "3rdDayofNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(2).format(df);
                break;
            case "3rdDayofNextMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(2).format(dfUIver);
                break;
            case "1stDayof2MonthsFromNow":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).format(df);
                break;
            case "1stDayof2MonthsFromNowUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).format(dfUIver);
                break;
            case "1stDayofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).format(df);
                break;
            case "1stDayofLastMonthUIver":
            case "firstdayofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).format(dfUIver);
                break;
            case "15thofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(15).minusMonths(1).format(df);
                break;
            case "15thofLastMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(15).minusMonths(1).format(dfUIver);
                break;
            case "25thofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(25).minusMonths(1).format(df);
                break;
            case "25thofLastMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(25).minusMonths(1).format(dfUIver);
                break;
            case "5daysBefore1stDayofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).minusDays(5).format(df);
                break;
            case "1stDayofPresentMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).format(df);
                break;
            case "firstDayofPresntMonth":
            case "1stDayofPresentMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).format(dfUIver);
                break;
            case "1stDayofPresentMonthETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).format(dfETLver);
                break;
            case "contiguos":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", -22, 0, 0);
                break;
            case "futureAnniversaryDate":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", 0, 11, 1);
                break;
            case "anniversaryDate":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", 0, 11, 0);
                break;
            case "anniversaryDateUIver":
                dateToReturn = addDaysToPresentDate("MM/dd/yyyy", 0, 11, 0);
                break;
            case "anniversaryDateHCCUIver":
                dateToReturn = addDaysToPresentDate("MM/dd/yyyy", 2, 8, 0);
                break;
            case "90DaysPriorAnniversaryDate":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", -91, 11, 0);
                break;
            case "planChangeAnniversary":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", -getNumberOfDaysToReduceForTheFirstOfThisMonth() + 14, 9, 0);
                break;
            case "daybeforeAnniversary":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", -1, 11, 0);
                break;
            case "futureDate":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", 0, 6, 0);
                break;
            case "nextDay":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(1).format(df);
                break;
            case "nextDayPlusOne":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(2).format(df);
                break;
            case "nextDayPlusOneUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(2).format(dfUIver);
                break;
            case "nextFuture":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", 10, 0, 0);
                break;
            case "nextMonth":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", getNumberOfDaysToReduceForTheFirstOfThisMonth(), 1, 0);
                break;
            case "firstDayOfNextYearUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(1).plusYears(1).format(dfUIver);
                break;
            case "firstDayOfNextYear":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(1).plusYears(1).format(df);
                break;
            case "firstDayOfNextYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(1).plusYears(1).format(dfETLver);
                break;
            case "yesterday":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).minusDays(1).format(df);
                break;
            case "yesterdayUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).minusDays(1).format(dfUIver);
                break;
            case "yesterdayETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).minusDays(1).format(dfETLver);
                break;
            case "openEnrollmentDayUIver":
                dateToReturn = "11/01/" + addDaysToPresentDate("yyyy", 0, 0, 0);
                break;
            case "openEnrollmentDay":
                dateToReturn = addDaysToPresentDate("yyyy", 0, 0, 0) + "-11-01";
                break;
            case "openEnrollmentDayLastYearUIver":
                dateToReturn = "11/01/" + addDaysToPresentDate("yyyy", 0, 0, -1);
                break;
            case "openEnrollmentDayLastYear":
                dateToReturn = addDaysToPresentDate("yyyy", 0, 0, -1) + "-11-01";
                break;
            case "90DaysPriorOpenEnrollmentDueDay":
                dateToReturn = addDaysToPresentDate("yyyy", 0, 0, 0) + "-09-16";
                break;
            case "90DaysPriorAnniversaryDueDateHCC":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", -getNumberOfDaysToReduceForTheFirstOfThisMonth() + 24 - 90, 10, 0);
                break;
            case "90DaysPriorAnniversaryDueDateHCCUIver":
                dateToReturn = addDaysToPresentDate("MM/dd/yyyy", -getNumberOfDaysToReduceForTheFirstOfThisMonth() + 24 - 90, 10, 0);
                break;
            case "newEnrollCutoffStartDateHCC":
                if (todaysDate <= 10) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(15).format(df);
                } else if (todaysDate <= 25) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(15).plusMonths(1).format(df);
                }
                break;
            case "newEnrollCutoffStartDateHCCUIver":
                if (todaysDate <= 10) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(15).format(dfUIver);
                } else if (todaysDate <= 25) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(15).plusMonths(1).format(dfUIver);
                }
                break;
            case "cutoffStartDateHHW":
            case "planChangeCutoffStartDateHCC":
                if (todaysDate <= 25) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).format(df);
                }
                break;
            case "cutoffStartDateHHWUIver":
            case "planChangeCutoffStartDateHCCUIver":
                if (todaysDate <= 25) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).format(dfUIver);
                }
                break;
            case "cutoffStartDateImmigrantChildren":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).format(df);
                }
                break;
            case "cutoffStartDateImmigrantChildrenUIver":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).format(dfUIver);
                }
                break;
            case "cutOffDateDCHF":
            case "cutoffStartDateImmigrantChildrenWithUIPlanSelection":
                if (todaysDate <= 15) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).format(df);
                }
                break;
            case "cutOffDateDCHFUIver":
            case "cutoffStartDateImmigrantChildrenWithUIPlanSelectionUIver":
                if (todaysDate <= 15) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).format(dfUIver);
                }
                break;
            case "nextDayAfter60DaysFromFirstDayOfMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusDays(61).format(df);
                break;
            case "90DaysBeforeLastDayofNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).minusDays(91).format(df);
                break;
            case "90DaysBeforeLastDayofNextMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).minusDays(91).format(dfUIver);
                break;
            case "InvalidStartDateETLver":
                dateToReturn = "229X9X9X";
                break;
            case "firstDayOfCurrentYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(1).plusYears(0).format(dfETLver);
                break;
            case "Mar6thCurrentYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(65).plusYears(0).format(dfETLver);
                break;
            case "Apr28thCurrentYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(118).plusYears(0).format(dfETLver);
                //plusMonths(1).minusDays(1).format(dfETLver)
                break;
            case "June1stCurrentYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(152).plusYears(0).format(dfETLver);
                break;
            case "anniversaryDateDC":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(12).format(df);
                break;
            case "anniversaryDateDCPlanChange":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(13).format(df);
                break;
            case "1YearFrom1stDayofPresentMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusYears(1).format(df);
                break;
            case "1YearFrom1stDayofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusYears(1).format(df);
                break;
            case "1YearFrom1stDayofLastMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusYears(1).format(dfUIver);
                break;
            case "1YearFrom1stDayofNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusYears(1).plusMonths(1).format(df);
                break;
            case "1YearFromCutoffStartDateImmigrantChildren":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusYears(1).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusYears(1).format(df);
                }
                break;
            case "1YearFromCutoffStartDateImmigrantChildrenUIver":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusYears(1).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusYears(1).format(dfUIver);
                }
                break;
            case "1YearFrom1stDayof2MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusYears(1).format(df);
                break;
            case "1YearFrom1stDayof2MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusYears(1).format(dfUIver);
                break;
            case "1YearFromNextDayPlusOne":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(2).plusYears(1).format(df);
                break;
            case "1stDayofNextYear":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(1).plusYears(1).format(df);
                break;
            case "1stDayofNextYearUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(1).plusYears(1).format(dfUIver);
                break;
            case "46DaysBefore1stDayofNextYear":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(1).plusYears(1).minusDays(46).format(df);
                break;
            case "46DaysBefore1stDayofNextYearUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(1).plusYears(1).minusDays(46).format(dfUIver);
                break;
            case "1YearBefore1stDayofPresentMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusYears(1).format(df);
                break;
            case "1YearBefore1stDayofPresentMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusYears(1).format(dfUIver);
                break;
            case "1stDayof4MonthsAfter":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(4).format(df);
                break;
            case "1stDayof6Months2YearsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(6).minusYears(2).format(df);
                break;
            case "1stDayof11MonthsFromNow":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(11).format(df);
                break;
        }

        return dateToReturn;
    }

    public String getEndDate(String timeFrame) {
        int todaysDate = Integer.parseInt(addDaysToPresentDate("dd", 0, 0, 0));
        String dateToReturn = "";
        switch (timeFrame) {
            case "current":
                dateToReturn = getCurrentDate("yyyy-MM-dd");
                break;
            case "currentUIver":
                dateToReturn = getCurrentDate("MM/dd/yyyy");
                break;
            case "currentETLver":
                dateToReturn = getCurrentDate("yyyyMMdd");
                break;
            case "14DaysFromNowUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(14).format(dfUIver);
                break;
            case "14DaysFromYesterdaUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(13).format(dfUIver);
                break;
            case "5DaysFromNowUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(5).format(dfUIver);
                break;
            case "Dec15thCurrentYear":
                dateToReturn = getCurrentDate("yyyy") + "-12-15";
                break;
            case "Dec15thLastYear":
                dateToReturn = addDaysToPresentDate("yyyy",0,0,-1) + "-12-15";
                break;
            case "currentPlusOne":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", 2, 0, 0);
                break;
            case "yesterday":
//                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", -1, 0, 0);
                dateToReturn = LocalDate.now(ZoneOffset.UTC).minusDays(1).format(df);
                break;
            case "yesterdayUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).minusDays(1).format(dfUIver);
                break;
            case "past":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", -5, 0, 0);
                break;
            case "pastETL":
                dateToReturn = addDaysToPresentDate("yyyyMMdd", -5, 0, 0);
                break;
            case "future":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusMonths(7).format(df);
                break;
            case "futureUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusMonths(7).format(dfUIver);
                break;
            case "planChangePrelockin":
                if (getStartDate("1stDayofPresentMonth").equals(getStartDate("current"))) {
                    dateToReturn = addDaysToPresentDate("yyyy-MM-dd", +89, 9, 0);
                } else {
                    dateToReturn = addDaysToPresentDate("yyyy-MM-dd", getNumberOfDaysToAddForNextFirstOfMonth() + 89, 0, 0);
                }
                break;
            case "futureOneDayLessThanCurrent":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", -1, 1, 0);
                break;
            case "30DaysFromTodayUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(29).format(dfUIver);
                break;
            case "29DaysFromTodayUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(29).format(dfUIver);
                break;
            case "28DaysFromTodayUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(28).format(dfUIver);
                break;
            case "1stDayofNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).format(df);
                break;
            case "1stDayofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).format(df);
                break;
            case "1stDayofPresentMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).format(df);
                break;
            case "1stDayofPresentMonthETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).format(dfETLver);
                break;
            case "14DayFromYesterday":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", 13, 0, 0);
                break;
            case "14DayFrom1stDayofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusDays(14).format(df);
                break;
            case "14DayFrom1stDayofLastMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusDays(14).format(dfUIver);
                break;
            case "60DaysFromYesterday":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(59).format(df);
                break;
            case "14DaysFromFirstDayOfLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusDays(14).format(df);
                break;
            case "14DayFromFirstDayOfMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusDays(14).format(df);
                break;
            case "14DayFromFirstDayOfNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(14).format(df);
                break;
            case "14DaysFrom1stDayof2MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(14).format(df);
                break;
            case "14DaysFrom1stDayof2MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(14).format(dfUIver);
                break;
            case "90DayFromFirstDayOfMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusDays(90).format(df);
                break;
            case "90DayFromFirstDayOfMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusDays(90).format(dfUIver);
                break;
            case "90DayFromFirstDayOfLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusDays(90).format(df);
                break;
            case "90DayFromFirstDayOfLastMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusDays(90).format(dfUIver);
                break;
            case "89DayFromFirstDayOfLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusDays(89).format(df);
                break;
            case "89DayFromFirstDayOfLastMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusDays(89).format(dfUIver);
                break;
            case "88DayFromFirstDayOfLastMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusDays(88).format(dfUIver);
                break;
            case "90DayFrom1stDayof2MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(90).format(df);
                break;
            case "90DayFrom1stDayof2MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(90).format(dfUIver);
                break;
            case "90DayFrom1stDayof3MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(3).plusDays(90).format(df);
                break;
            case "90DayFrom1stDayof3MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(3).plusDays(90).format(dfUIver);
                break;
            case "90DayFromFirstDayOfNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(90).format(df);
                break;
            case "90DayFromFirstDayOfNextMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(90).format(dfUIver);
                break;
            case "60DayFromFirstDayOfNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(60).format(df);
                break;
            case "60DayFromFirstDayOfNextMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(60).format(dfUIver);
                break;
            case "60DaysFromFirstDayOfMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusDays(60).format(df);
                break;
            case "60DaysFromFirstDayOfMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusDays(60).format(dfUIver);
                break;
            case "60DaysFrom1stDayof2MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(60).format(df);
                break;
            case "60DaysFrom1stDayof2MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(60).format(dfUIver);
                break;
            case "90DaysFrom3rdMonthFromNow":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusDays(90).format(df);
                break;
            case "90DaysFrom3rdMonthFromNowUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusDays(90).format(dfUIver);
                break;
            case "25thDayOfMonthPriorAnniversaryDate":
            case "anniversaryDueDateHCC":
                dateToReturn = LocalDate.parse(
                        addDaysToPresentDate("yyyy-MM-dd", -getNumberOfDaysToReduceForTheFirstOfThisMonth(), 10, 0)
                        , df).plusDays(24).format(df);
                break;
            case "anniversaryDueDateHCCUIver":
                dateToReturn = LocalDate.parse(
                        addDaysToPresentDate("MM/dd/yyyy", -getNumberOfDaysToReduceForTheFirstOfThisMonth(), 10, 0)
                        , dfUIver).plusDays(24).format(dfUIver);
                break;
            case "lastDayofPresentMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).minusDays(1).format(df);
                break;
            case "lastDayofpresentMonth":
            case "lastDayofPresentMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).minusDays(1).format(dfUIver);
                break;
            case "lastDayofPresentMonthETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).minusDays(1).format(dfETLver);
                break;
            case "lastDayofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusDays(1).format(df);
                break;
            case "lastDayofLastMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusDays(1).format(dfUIver);
                break;
            case "lastDayofLastMonthETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusDays(1).format(dfETLver);
                break;
            case "lastDayofNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).minusDays(1).format(df);
                break;
            case "lastDayofNextMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).minusDays(1).format(dfUIver);
                break;
            case "lastDayofNextMonthETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).minusDays(1).format(dfETLver);
                break;
            case "nextMonth":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", getNumberOfDaysToReduceForTheFirstOfThisMonth(), 1, 0);
                break;
            case "futureDate":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", 0, 6, 1);
                break;
            case "nextDay":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", 1, 0, 0);
                break;
            case "in3BusinessDays":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", getNumberOFBusinessDays(3), 0, 0);
                break;
            case "in2BusinessDays":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", getNumberOFBusinessDays(2), 0, 0);
                break;
            case "highDate":
                dateToReturn = "2299-12-31";
                break;
            case "highDateUIver":
                dateToReturn = "12/31/2299";
                break;
            case "highDateETLver":
                dateToReturn = "22991231";
                break;
            case "highDate-DC":
                dateToReturn = "9999-12-31";
                break;
            case "highDateUIver-DC":
                dateToReturn = "12/31/9999";
                break;
            case "highDateETLver-DC":
                dateToReturn = "99991231";
                break;
            case "lastDayOfThePresentYear":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withMonth(12).withDayOfMonth(31).format(df);
                break;
            case "lastDayOfThePresentYearUIver":
                dateToReturn = "12/31/" + getCurrentDate("yyyy");
                break;
            case "lastDayOfThePresentYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withMonth(12).withDayOfMonth(31).format(dfETLver);
                break;
            case "lastDayOfNextYear":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withMonth(12).withDayOfMonth(31).plusYears(1).format(df);
                break;
            case "lastDayOfNextYearUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withMonth(12).withDayOfMonth(31).plusYears(1).format(dfUIver);
                break;
            case "lastDayOfNextYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withMonth(12).withDayOfMonth(31).plusYears(1).format(dfETLver);
                break;
            case "daybeforeAnniversary":
                dateToReturn = addDaysToPresentDate("yyyy-MM-dd", -1, 11, 0);
                break;
            case "openEnrollmentDueDayUIver":
                dateToReturn = "12/15/" + addDaysToPresentDate("yyyy", 0, 0, 0);
                break;
            case "openEnrollmentDueDay":
                dateToReturn = addDaysToPresentDate("yyyy", 0, 0, 0) + "-12-15";
                break;
            case "openEnrollmentDueDayETLver":
                dateToReturn = addDaysToPresentDate("yyyy", 0, 0, 0) + "1215";
                break;
            case "lastDayOf2MonthsFromNow":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(3).minusDays(1).format(df);
                break;
            case "lastDayOf2MonthsFromNowUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(3).minusDays(1).format(dfUIver);
                break;
            case "lastDayOf3MonthsFromNow":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(4).minusDays(1).format(df);
                break;
            case "lastDayOf3MonthsFromNowUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(4).minusDays(1).format(dfUIver);
                break;
            case "lastDayOf4thMonthFromNowETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(4).minusDays(1).format(dfETLver);
                break;
            case "lastDayOf2ndMonthBeforeNowUIver":
                dateToReturn = LocalDate.parse(
                        addDaysToPresentDate("MM/dd/yyyy", getNumberOfDaysToAddForNextFirstOfMonth(), -2, 0)
                        , dfUIver).minusDays(1).format(dfUIver);
                break;
            case "lastDayOf2MonthsBefore":
            case "lastDayOf2ndMonthFromNow":
                dateToReturn = LocalDate.parse(
                        addDaysToPresentDate("yyyy-MM-dd", getNumberOfDaysToAddForNextFirstOfMonth(), -2, 0)
                        , df).minusDays(1).format(df);
                break;
            case "30DaysFromToday":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(29).format(df);
                break;
            case "29DaysFromToday":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(29).format(df);
                break;
            case "DayBeforeCutoffStartDateHHW":
            case "DayBeforePlanChangeCutoffStartDateHCC":
                if (todaysDate <= 25) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).minusDays(1).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).minusDays(1).format(df);
                }
                break;
            case "DayBeforeCutoffStartDateHHWUIver":
            case "DayBeforePlanChangeCutoffStartDateHCCUIver":
                if (todaysDate <= 25) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).minusDays(1).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).minusDays(1).format(dfUIver);
                }
                break;
            case "-- --":
                dateToReturn = "-- --";
                break;
            case "InvalidEndDateETLver":
                dateToReturn = "229X9X9X";
                break;
            case "Mar5thCurrentYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(64).plusYears(0).format(dfETLver);
                break;
            case "Apr27thCurrentYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(117).plusYears(0).format(dfETLver);
                break;
            case "May31stCurrentYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(151).plusYears(0).format(dfETLver);
                break;
            case "Oct31stCurrentYearETLver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(305).plusYears(0).format(dfETLver);
                break;
            case "89DayFromFirstDayOfMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusDays(89).format(df);
                break;
            case "90DaysAfter1YearFrom1stDayofPresentMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusYears(1).plusDays(90).format(df);
                break;
            case "90DaysAfter1YearFrom1stDayofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusYears(1).plusDays(90).format(df);
                break;
            case "90DaysAfter1YearFrom1stDayofLastMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusYears(1).plusDays(90).format(dfUIver);
                break;
            case "89DaysAfter1YearFrom1stDayofLastMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusYears(1).plusDays(89).format(df);
                break;
            case "89DaysAfter1YearFrom1stDayofLastMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(1).plusYears(1).plusDays(89).format(dfUIver);
                break;
            case "90DaysAfter1YearFrom1stDayofNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusYears(1).plusMonths(1).plusDays(90).format(df);
                break;
            case "89DaysAfter1YearFrom1stDayofNextMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusYears(1).plusMonths(1).plusDays(89).format(df);
                break;
            case "90DaysAfterCutoffStartDateImmigrantChildren":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(90).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusDays(90).format(df);
                }
                break;
            case "90DaysAfterCutoffStartDateImmigrantChildrenUIver":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(90).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusDays(90).format(dfUIver);
                }
                break;
            case "90DaysAfter1YearFromCutoffStartDateImmigrantChildren":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusYears(1).plusDays(90).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusYears(1).plusDays(90).format(df);
                }
                break;
            case "90DaysAfter1YearFromCutoffStartDateImmigrantChildrenUIver":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusYears(1).plusDays(90).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusYears(1).plusDays(90).format(dfUIver);
                }
                break;
            case "89DaysAfterCutoffStartDateImmigrantChildren":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(89).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusDays(89).format(df);
                }
                break;
            case "89DaysAfterCutoffStartDateImmigrantChildrenUIver":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(89).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusDays(89).format(dfUIver);
                }
                break;
            case "88DaysAfterCutoffStartDateImmigrantChildrenUIver":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusDays(88).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusDays(88).format(dfUIver);
                }
                break;
            case "89DaysAfter1YearFromCutoffStartDateImmigrantChildren":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusYears(1).plusDays(89).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusYears(1).plusDays(89).format(df);
                }
                break;
            case "89DaysAfter1YearFromCutoffStartDateImmigrantChildrenUIver":
                if (todaysDate <= lastWorkingDayOfTheCurrentMonth) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).plusYears(1).plusDays(89).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).plusYears(1).plusDays(89).format(dfUIver);
                }
                break;
            case "90DaysAfter1stDayof2MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(90).format(df);
                break;
            case "90DaysAfter1stDayof2MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(90).format(dfUIver);
                break;
            case "90DaysAfter1YearFrom1stDayof2MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusYears(1).plusDays(90).format(df);
                break;
            case "90DaysAfter1YearFrom1stDayof2MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusYears(1).plusDays(90).format(dfUIver);
                break;
            case "89DaysAfter1stDayof2MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(89).format(df);
                break;
            case "89DaysAfter1stDayof2MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(89).format(dfUIver);
                break;
            case "88DaysAfter1stDayof2MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusDays(88).format(dfUIver);
                break;
            case "89DaysAfter1YearFrom1stDayof2MonthsBefore":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusYears(1).plusDays(89).format(df);
                break;
            case "89DaysAfter1YearFrom1stDayof2MonthsBeforeUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).minusMonths(2).plusYears(1).plusDays(89).format(dfUIver);
                break;
            case "DayBeforeCutOffStartDateImmigrantChildrenWithUIPlanSelection":
            case "dayBeforeCutOffDateDCHF":
                if (todaysDate <= 15) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).minusDays(1).format(df);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).minusDays(1).format(df);
                }
                break;
            case "DayBeforeCutOffStartDateImmigrantChildrenWithUIPlanSelectionUIver":
            case "dayBeforeCutOffDateDCHFUIver":
                if (todaysDate <= 15) {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(1).minusDays(1).format(dfUIver);
                } else {
                    dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusMonths(2).minusDays(1).format(dfUIver);
                }
                break;
            case "nextDayPlusOne":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).plusDays(2).format(df);
                break;
            case "89DaysAfter1stDayofPresentMonth":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusDays(89).format(df);
                break;
            case "89DaysAfter1stDayofPresentMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusDays(89).format(dfUIver);
                break;
            case "88DaysAfter1stDayofPresentMonthUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfMonth(1).plusDays(88).format(dfUIver);
                break;
            case "17DaysBefore1stDayofNextYear":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(1).plusYears(1).minusDays(17).format(df);
                break;
            case "17DaysBefore1stDayofNextYearUIver":
                dateToReturn = LocalDate.now(ZoneOffset.UTC).withDayOfYear(1).plusYears(1).minusDays(17).format(dfUIver);
                break;
        }
        return dateToReturn;
    }

    public String genRandomName() {
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(7, useLetters, useNumbers);
    }

    public String genRandomEmail() {
        return genRandomName() + "@maerskqatest.com";
    }

    public String genRandomPhoneNumber() {
        return String.valueOf(RandomStringUtils.randomNumeric(10));
    }

    public String genRandomAddress() {
        int num = (int) ((Math.random() * ((9000 - 1000) + 1)) + 1000);
        return String.valueOf(num) + genRandomName() + ".st";
    }

    public String frstDyFmnth() {
//        LocalDate today = LocalDate.now();
//        return String.valueOf(today.withDayOfMonth(1).toString());
        return getStartDate("1stDayofPresentMonth");
    }

    public String frstDyNxtmnth() {
//        LocalDate today = LocalDate.now();
//        return String.valueOf(today.plusMonths(1).withDayOfMonth(1).toString());
        return getStartDate("1stDayofNextMonth");
    }

    public String today() {
        return String.valueOf(LocalDate.now().toString());
    }
    public String todayETL() {
        return String.valueOf(LocalDate.now().toString().replace("-",""));
    }

    public String lstDyFmnth() {
//        LocalDate today = LocalDate.now();
//        Calendar cal = Calendar.getInstance();
//        int lstDate = cal.getActualMaximum(Calendar.DATE);
//
//        return String.valueOf(today.withDayOfMonth(lstDate).toString());
        return getEndDate("lastDayofPresentMonth");
    }

    public String generate_random_externalId() {
        getRandomNumber(10);
        String apiexternalId = "" + randomNumber.toString();
        System.out.println("uiid=" + apiexternalId);
        return apiexternalId;
    }
    public String generate_random_CaseNumber() {
        getRandomNumber(10);
        String apicaseNumber = "" + randomNumber.toString();
        System.out.println("uiid=" + apicaseNumber);
        return apicaseNumber;
    }

    public String getCurrentDate(ZoneOffset zone, String format) {
        LocalDateTime now = LocalDateTime.now(zone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }
    public   String generateRandomDOB() {
        final int maxAge=100*12*31;

        return String.valueOf(LocalDate.now().minusDays(new Random().nextInt(maxAge))).replace("-","");
    }
    public String generateGender(){
        String gender;
        if (Math.random()>0.5)gender="M";
        else gender="F";
        return gender;
    }
    public  String genNonHipPlanCode(){
        String planCode;
        if (Math.random()>0.5)planCode="R";
        else planCode="A";
        return planCode;
    }

    public String getYesterdayDate(String s) {
       return getEndDate("yesterday");
    }

    public String generate_random_Zip() {
        getRandomNumber(5);
        String apiZip = "" + randomNumber.toString();
        System.out.println("Zip=" + apiZip);
        return apiZip;
    }

}






