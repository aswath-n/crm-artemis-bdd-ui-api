package com.maersk.crm.utilities;

import com.maersk.crm.api_step_definitions.*;
import com.maersk.crm.pages.crm.*;
import com.maersk.crm.step_definitions.*;
import com.maersk.dms.step_definitions.PublishDPBIEventsOutboundCorrespondenceChangesStepDefs;
import com.maersk.dms.utilities.APIAutoUtilities;
import com.maersk.dms.utilities.EventsUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertTrue;

public class CRMUtilities extends BrowserUtils {
    protected CRMConsumerSearchResultPage consumerSearchResult = new CRMConsumerSearchResultPage();
    public static final String UINAMESURL = "https://uinames.com/api/?ext";


    private String firstName;
    private String lastName;
    private String ssn;
    private String dateOfBirth;
    private String phoneNumber;
    private String gender;
    private String email;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String county;
    private String zipCode;

    CRMContactRecordUIPage crmContactRecordUIPage = new CRMContactRecordUIPage();



    /*
      Author:Shilpa P
    * This method is used to click the Hamburger Menu and
    * select the options from the drop down
    * */

    public void navigateToHamBurgerMenu(String linkName) {
        crmContactRecordUIPage.hamBurgerMenu.click();
        highLightElement(crmContactRecordUIPage.hamBurgerMenu);
        switch (linkName.toUpperCase()) {
            case "CREATEATASK":
                highLightElement(crmContactRecordUIPage.createATask);
                crmContactRecordUIPage.createATask.click();
                break;

            case "MOREOPTION":
                highLightElement(crmContactRecordUIPage.moreOption);
                crmContactRecordUIPage.moreOption.click();

            default:
        }


    }

    /*
     * Author:Shilpa p
     * This method is used to click on the contact drop down  from the
     * contact Reason
     * and Select the contact reason
     *
     * */

    public void contactReason(String contactReason) {
        switch (contactReason) {
            case "Information Request":
                highLightElement(crmContactRecordUIPage.getElementsContactReason(2));
                crmContactRecordUIPage.getElementsContactReason(2).click();
                break;
            case "Materials Request":
                highLightElement(crmContactRecordUIPage.getElementsContactReason(3));
                crmContactRecordUIPage.getElementsContactReason(3).click();
                break;
            case "Missing Information Request":
                highLightElement(crmContactRecordUIPage.getElementsContactReason(4));
                crmContactRecordUIPage.getElementsContactReason(4).click();
                break;

            case "Other":
                highLightElement(crmContactRecordUIPage.getElementsContactReason(5));
                crmContactRecordUIPage.getElementsContactReason(5).click();
                break;

            case "Update Information Request":
                highLightElement(crmContactRecordUIPage.getElementsContactReason(6));

            default:
        }

    }

    /*
     * Author:Shilpa p
     * This method is used to click the contact Action  from the
     * contact Action drop down
     * and Select the contact Action
     *
     * */


    public void contactAction(String contactAction) {
        switch (contactAction) {
            case "Requested and Updated Authorized Representative Information":
                waitForVisibility(crmContactRecordUIPage.getElementsContactAction(3), 2);
                highLightElement(crmContactRecordUIPage.getElementsContactAction(3));
                crmContactRecordUIPage.getElementsContactAction(3).click();
                break;
            case "Requested and Updated Case Member Information":
                highLightElement(crmContactRecordUIPage.getElementsContactAction(4));
                crmContactRecordUIPage.getElementsContactAction(4).click();
                break;
            case "Requested and Updated Demographic Information":
                highLightElement(crmContactRecordUIPage.getElementsContactAction(5));
                crmContactRecordUIPage.getElementsContactAction(5).click();
                break;


        }

    }

    /*
     * Author:Shilpa P
     * This method is used to create the Reasons and Comments
     * */

    //TODO Shilpa this method have to be REFACTORED ASAP
    public boolean createReasonsAndComments(String comments, String contactsReasons, String contactAction) {
        //crmContactRecordUIPage.expendReasonButton.click();
        // hover(crmContactRecordUIPage.expendReasonButton);
        staticWait(5000);
        waitForVisibility(crmContactRecordUIPage.contactReason, 2);
        crmContactRecordUIPage.contactReason.click();
        hover(crmContactRecordUIPage.contactReason);
        contactReason(contactsReasons);
        System.out.print("Contacts Selected ");
        waitForVisibility(crmContactRecordUIPage.contactAction, 10);
        System.out.print(" before Clicked");
        staticWait(100);
        crmContactRecordUIPage.contactAction.click();
        System.out.print("After Clicked");
        hover(crmContactRecordUIPage.contactAction);
        contactAction(contactAction);
        waitForVisibility(crmContactRecordUIPage.reasonsComments, 2);
        clearAndFillText(crmContactRecordUIPage.reasonsComments, comments);
        crmContactRecordUIPage.saveReasonButton.click();
        return true;
    }

    /*
     * Author:Muhabbat
     * This method is used to create all digit string of any length
     * */
    public String getAllDigitString(int i) {
        StringBuilder sb = new StringBuilder();
        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(list);
        for (int j : list)
            sb.append(j);
        return sb.toString().substring(0, i);
    }

    /*
     * Author:Muhabbat
     * This method is used to clear a txt in field
     * */
    public void clearFiled(WebElement el) {
        el.click();
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        waitFor(1);
    }

    @Override
    public void clearAndFillText2(WebElement element, String text) {
        super.clearAndFillText2(element, text);
    }

    /*
     * Author:Muhabbat
     * This method is used to clear any single select dropdown options
     * */
    public void clearDropDown(WebElement el) {
        el.click();
        waitFor(1);
        hover(el);
        WebElement single = Driver.getDriver().findElement(By.xpath("(//li[@tabindex = -1])"));
        scrollToElement(single);
        single.click();
    }
    /* Added two new cases future2 and past1 - Aswath*/

    public String getDesireDate(String time) {
        String desireDate = "";
        switch (time) {
            case "future":
                desireDate = getGreaterDate(2);
                break;
            case "past":
                desireDate = getPriorDate(4);
                break;
            case "past2":
                desireDate = getPriorDate(2);
                break;
            case "past1":
                desireDate = getPriorDate(1);
                break;
            case "present":
                desireDate = getCurrentDate();
                break;
            case "future2":
                desireDate = getGreaterDate(4);
                break;
            case "":
                break;
        }
        return desireDate;
    }

    /*
     * Author:Muhabbat
     * This method is used to verify if mandatory field is marked and has "*"
     * */
    public static boolean markedMandatory(WebElement el) {
        if (el.getText().endsWith("*")) {
            return true;
        }
        return false;
    }

    public void linkSearchedConsumer(String id, String fName) {
        for (int i = 1; i < consumerSearchResult.FirstNames.size(); i++) {
            System.out.println(id);
            waitFor(1);
            if (consumerSearchResult.FirstNames.get(i).getText().equalsIgnoreCase(fName) && consumerSearchResult.ConsumerIDs.get(i).getText().equals(id)) {
//                scrollToElement(consumerSearchResult.ExpandSearchResults.get(i));
                System.out.println(consumerSearchResult.ConsumerIDs.get(i).getText());
                waitFor(2);
                Driver.getDriver().findElement(By.xpath("//table[contains(@class, 'mt-4')]/tbody/tr[" + i + "]/td[1]//button")).click();
                waitFor(2);
                int autIndex = i + 1;
                consumerSearchResult.consumerNameRadioButton.click();
                waitFor(2);
                consumerSearchResult.unableToAuthenticateCheckBox.click();
                //wait for warning snackbar to disappear, until which page will not be clickable
                waitForClickablility(crmContactRecordUIPage.linkRecordButton, 10);
                crmContactRecordUIPage.linkRecordButton.click();
                assertTrue(crmContactRecordUIPage.unLink.isDisplayed());

            }

        }
    }

    /*
     * Author:Muhabbat
     * This method is used to select Contact Record Type during Active Contact Record
     * */
    public void clickOnContactRecordType(String contactRecordType) {
        WebElement e = Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + contactRecordType + "')]"));
        e.click();
        waitFor(1);

    }

    /*
     * Author:Muhabbat
     * This method is used to validate if min length error message is displayed
     * */
    public boolean hasMinLengthErrorMessage(WebElement el, String fieldName, int length) {
        String actualErrorMessage = el.getText();
        String expected = fieldName + " must be " + length + " characters";
        return (expected.equals(actualErrorMessage));

    }

    /*
     * Author:Muhabbat
     * This method is used to validate if min length error message is displayed
     * */
    public boolean hasLeastLengthErrorMessage(WebElement el, String fieldName, int length) {
        String actualErrorMessage = el.getText();
        String expected = fieldName + " must be at least " + length + " characters";
        return (expected.equals(actualErrorMessage));

    }

    /*
     * Author:Muhabbat
     * This method is used to validate if max length error message is displayed
     * */
    public boolean hasMaxLengthErrorMessage(WebElement el, String fieldName, int length) {
        String actualErrorMessage = el.getText();
        String expected = fieldName + " cannot exceed " + length + " characters";
        return (expected.equals(actualErrorMessage));

    }

    /*
     * Author:Muhabbat
     * This method is used to validate if Incorrect format error message is displayed
     * */
    public boolean hasFormatErrorMessage(WebElement el, String fieldName) {
        String actualErrorMessage = el.getText();
        String expected = fieldName + " is not in the correct format";
        return (expected.equals(actualErrorMessage));

    }

    /*
     * Author:Muhabbat
     * This method is used to validate if mandatory field error message is displayed
     * */
    public boolean isReqErrorMessage(WebElement el, String fieldName) {
        String actualErrorMessage = el.getText();
        String expected = fieldName + " is required and cannot be left blank";
        return (expected.equals(actualErrorMessage));

    }

    /*
     * Author:Muhabbat
     * This method is used to validate if date before min or after max date error message is displayed
     * */
    public boolean hasMinOrMaxDateErrorMessage(WebElement el, String dateType) throws ParseException {
        String actualErrorMessage = el.getText();
        String expected = "";
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cMin = Calendar.getInstance();
        cMin.set(Calendar.MONTH, 01);
        cMin.set(Calendar.DATE, 01);
        cMin.set(Calendar.YEAR, 1900);
        Calendar cMax = Calendar.getInstance();
        cMax.set(Calendar.MONTH, 01);
        cMax.set(Calendar.DATE, 12);
        cMax.set(Calendar.YEAR, 2050);
        Date minDate = cMin.getTime();
        Date maxDate = cMax.getTime();
        Date date = formatter.parse(dateType);

        if (date.before(minDate) || date.equals(minDate)) {
            expected = "Date cannot be before mindate";
        } else if (date.after(maxDate) || date.equals(maxDate)) {
            expected = "Date cannot be after maxdate";
        }
        return (expected.equals(actualErrorMessage));
    }

    /*
     * Author:Muhabbat
     * This method is used to validate if past or future date error message is displayed
     * */
    public boolean hasPastOrFutureDateErrorMessage(WebElement el, String date, String dateType) throws ParseException {
        String actualErrorMessage = el.getText();
        String expected = "Date cannot be in the ";
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date d = formatter.parse(date);
        Date currentDate = new Date();
        SimpleDateFormat sdf = formatter;
        switch (dateType) {
            case "past":
                if (d.before(currentDate)) {
                    expected.concat(dateType);
                }
                break;
            case "future":
                if (d.after(currentDate)) {
                    expected.concat(dateType);
                }
                break;
        }
        return (expected.equals(actualErrorMessage));
    }

    /*
     * Author:Muhabbat
     * This method is used to validate if invalid date format error message is displayed
     * */
    public boolean hasInvalidDateErrorMessage(WebElement el) {
        String actualErrorMessage = el.getText();
        String expected = "Invalid date format";
        return (expected.equals(actualErrorMessage));

    }

    public static String getRandomStringFromList(List<String> stringList) {
        Random r = new Random();
        int randomitem = r.nextInt(stringList.size());
        String randomString = stringList.get(randomitem);
        return randomString;
    }

    public String getServiceAccountUserName(String serviceAccount) {
        String endsWith = serviceAccount.substring(14);
        System.out.println("ends with is " + endsWith);
        String userName = "";
        switch (endsWith) {
            case "1":
                userName = "Service AccountOne";
                break;
            case "2":
                userName = "Service AccountTwo";
                break;
        }
        return userName;
    }

    public static String getRandomValueFromArray(String[] values) {
        Random r = new Random();
        int randomNumber = r.nextInt(values.length);
        return values[randomNumber];
    }


    public void clearFieldCharByChar(WebElement element) {
        waitFor(1);
        while (!element.getAttribute("value").equals("") || element.getAttribute("value").equals(null)) {
            System.out.println("value of field ==== "+element.getAttribute("value"));
            element.sendKeys(Keys.BACK_SPACE);
        }
        waitFor(1);
    }

    public static List<String> inebCityOptions() {
        List<String> allInebCities = Arrays.asList("Unknown", "Berne", "Decatur", "Geneva", "Linn Grove", "Monroe", "Pleasant Mills",
                "Preble", "Arcola", "Fort Wayne", "Grabill", "Harlan", "Hoagland", "Huntertown", "Leo", "Monroeville", "New Haven",
                "Spencerville", "Woodburn", "Yoder", "Zanesville", "Clifford", "Columbus", "Elizabethtown", "Grammer", "Hartsville",
                "Hope", "Jonesville", "Taylorsville", "Ambia", "Boswell", "Earl Park", "Fowler", "Otterbein", "Oxford", "Talbot",
                "Templeton", "Hartford City", "Montpelier", "Advance", "Jamestown", "Lebanon", "Thorntown", "Whitestown", "Zionsville",
                "Helmsburg", "Nashville", "Bringhurst", "Burlington", "Burrows", "Camden", "Cutler", "Delphi", "Flora", "Rockfield",
                "Yeoman", "Galveston", "Lake Cicott", "Logansport", "Lucerne", "New Waverly", "Onward", "Royal Center", "Twelve Mile",
                "Walton", "Young America", "Bethlehem", "Borden", "Charlestown", "Clarksville", "Henryville", "Jeffersonville",
                "Marysville", "Memphis", "Nabb", "New Washington", "Otisco", "Sellersburg", "Bowling Green", "Brazil", "Carbon",
                "Centerpoint", "Clay City", "Coalmont", "Cory", "Harmony", "Knightsville", "Staunton", "Colfax", "Forest", "Frankfort",
                "Kirklin", "Michigantown", "Mulberry", "Rossville", "Sedalia", "Eckerty", "English", "Grantsburg", "Leavenworth",
                "Marengo", "Milltown", "Sulphur", "Taswell", "Cannelburg", "Elnora", "Montgomery", "Odon", "Plainville", "Washington",
                "Aurora", "Dillsboro", "Guilford", "Lawrenceburg", "Moores Hill", "West Harrison", "Clarksburg", "Greensburg", "Millhousen",
                "New Point", "Saint Paul", "Westport", "Ashley", "Auburn", "Butler", "Corunna", "Garrett", "Saint Joe", "Waterloo",
                "Albany", "Daleville", "Eaton", "Gaston", "Muncie", "Oakville", "Selma", "Yorktown", "Birdseye", "Celestine", "Dubois",
                "Ferdinand", "Holland", "Huntingburg", "Ireland", "Jasper", "Saint Anthony", "Schnellville", "Bristol", "Elkhart",
                "Goshen", "Middlebury", "Millersburg", "Nappanee", "New Paris", "Wakarusa", "Bentonville", "Connersville", "Glenwood",
                "Floyds Knobs", "Georgetown", "Greenville", "Mount Saint Francis", "New Albany", "Attica", "Covington", "Hillsboro",
                "Kingman", "Mellott", "Newtown", "Veedersburg", "Wallace", "Bath", "Brookville", "Cedar Grove", "Laurel", "Metamora",
                "New Trenton", "Oldenburg", "Akron", "Athens", "Delong", "Fulton", "Grass Creek", "Kewanna", "Leiters Ford", "Rochester",
                "Buckskin", "Fort Branch", "Francisco", "Haubstadt", "Hazleton", "Mackey", "Oakland City", "Owensville", "Patoka",
                "Princeton", "Somerville", "Fairmount", "Fowlerton", "Gas City", "Jonesboro", "Marion", "Matthews", "Swayzee",
                "Sweetser", "Upland", "Van Buren", "Bloomfield", "Jasonville", "Koleen", "Linton", "Lyons", "Midland", "Newberry",
                "Owensburg", "Scotland", "Solsberry", "Switz City", "Worthington", "Arcadia", "Atlanta", "Carmel", "Cicero", "Fishers",
                "Indianapolis", "Noblesville", "Sheridan", "Westfield", "Charlottesville", "Finly", "Fortville", "Greenfield",
                "Maxwell", "Mc Cordsville", "New Palestine", "Wilkinson", "Bradford", "Central", "Corydon", "Crandall", "Depauw",
                "Elizabeth", "Laconia", "Lanesville", "Mauckport", "New Middletown", "New Salisbury", "Palmyra", "Ramsey", "Amo",
                "Avon", "Brownsburg", "Clayton", "Danville", "Lizton", "North Salem", "Pittsboro", "Plainfield", "Stilesville",
                "Dunreith", "Greensboro", "Kennard", "Knightstown", "Lewisville", "Middletown", "Mooreland", "Mount Summit",
                "New Castle", "New Lisbon", "Shirley", "Spiceland", "Springport", "Straughn", "Sulphur Springs", "Greentown",
                "Hemlock", "Kokomo", "Oakford", "Russiaville", "West Middleton", "Andrews", "Bippus", "Huntington", "Roanoke",
                "Warren", "Brownstown", "Cortland", "Crothersville", "Freetown", "Kurtz", "Medora", "Norman", "Seymour", "Vallonia",
                "Demotte", "Fair Oaks", "Remington", "Rensselaer", "Tefft", "Wheatfield", "Bryant", "Dunkirk", "Pennville",
                "Portland", "Redkey", "Salamonia", "Canaan", "Deputy", "Dupont", "Hanover", "Madison", "Butlerville", "Commiskey",
                "Hayden", "North Vernon", "Paris Crossing", "Scipio", "Vernon", "Bargersville", "Edinburgh", "Franklin", "Greenwood",
                "Needham", "Nineveh", "Trafalgar", "Whiteland", "Bicknell", "Bruceville", "Decker", "Edwardsport", "Freelandville",
                "Monroe City", "Oaktown", "Ragsdale", "Sandborn", "Vincennes", "Westphalia", "Wheatland", "Atwood", "Burket", "Claypool",
                "Etna Green", "Leesburg", "Mentone", "Milford", "North Webster", "Pierceton", "Silver Lake", "Syracuse", "Warsaw",
                "Winona Lake", "Howe", "Lagrange", "Mongo", "Shipshewana", "South Milford", "Stroh", "Topeka", "Wolcottville",
                "Cedar Lake", "Crown Point", "Dyer", "East Chicago", "Gary", "Griffith", "Hammond", "Highland", "Hobart", "Lake Station",
                "Leroy", "Lowell", "Merrillville", "Munster", "Saint John", "Schererville", "Schneider", "Shelby", "Whiting", "Hanna",
                "Kingsbury", "Kingsford Heights", "La Crosse", "La Porte", "Michigan City", "Mill Creek", "Rolling Prairie", "Union Mills",
                "Wanatah", "Westville", "Avoca", "Bedford", "Fort Ritner", "Heltonville", "Huron", "Mitchell", "Oolitic", "Springville",
                "Tunnelton", "Williams", "Alexandria", "Anderson", "Elwood", "Frankton", "Ingalls", "Lapel", "Markleville", "Orestes",
                "Pendleton", "Summitville", "Beech Grove", "West Newton", "Argos", "Bourbon", "Bremen", "Culver", "Donaldson", "Lapaz",
                "Plymouth", "Tippecanoe", "Tyner", "Crane", "Loogootee", "Shoals", "Amboy", "Bunker Hill", "Converse", "Deedsville",
                "Denver", "Grissom Arb", "Macy", "Mexico", "Miami", "Peru", "Bloomington", "Clear Creek", "Ellettsville", "Harrodsburg",
                "Smithville", "Stanford", "Stinesville", "Unionville", "Alamo", "Crawfordsville", "Darlington", "Ladoga", "Linden",
                "New Market", "New Richmond", "New Ross", "Waveland", "Waynetown", "Wingate", "Brooklyn", "Camby", "Eminence",
                "Martinsville", "Monrovia", "Mooresville", "Morgantown", "Paragon", "Brook", "Goodland", "Kentland", "Lake Village",
                "Morocco", "Mount Ayr", "Roselawn", "Sumava Resorts", "Thayer", "Albion", "Avilla", "Cromwell", "Kendallville",
                "Kimmell", "Laotto", "Ligonier", "Rome City", "Wawaka", "Wolflake", "Rising Sun", "French Lick", "Orleans", "Paoli",
                "West Baden Springs", "Coal City", "Freedom", "Gosport", "Patricksburg", "Poland", "Quincy", "Spencer", "Bellmore",
                "Bloomingdale", "Bridgeton", "Judson", "Marshall", "Mecca", "Montezuma", "Rockville", "Rosedale", "Branchville",
                "Bristow", "Cannelton", "Derby", "Leopold", "Rome", "Saint Croix", "Tell City", "Otwell", "Petersburg", "Spurgeon",
                "Stendal", "Velpen", "Winslow", "Beverly Shores", "Boone Grove", "Chesterton", "Hebron", "Kouts", "Portage", "Valparaiso",
                "Wheeler", "Cynthiana", "Griffin", "Mount Vernon", "New Harmony", "Poseyville", "Wadesville", "Francesville", "Medaryville",
                "Monterey", "Star City", "Winamac", "Bainbridge", "Cloverdale", "Coatesville", "Fillmore", "Greencastle", "Putnamville",
                "Reelsville", "Roachdale", "Russellville", "Farmland", "Losantville", "Lynn", "Modoc", "Parker City", "Ridgeville",
                "Saratoga", "Union City", "Winchester", "Batesville", "Cross Plains", "Friendship", "Holton", "Milan", "Morris",
                "Napoleon", "Osgood", "Pierceville", "Sunman", "Versailles", "Arlington", "Carthage", "Falmouth", "Homer", "Manilla",
                "Mays", "Milroy", "Rushville", "Granger", "Lakeville", "Mishawaka", "New Carlisle", "North Liberty", "Notre Dame",
                "Osceola", "South Bend", "Walkerton", "Wyatt", "Austin", "Lexington", "Scottsburg", "Underwood", "Boggstown", "Fairland",
                "Flat Rock", "Fountaintown", "Gwynneville", "Morristown", "Shelbyville", "Waldron", "Chrisney", "Dale", "Evanston",
                "Fulda", "Gentryville", "Grandview", "Hatfield", "Lamar", "Lincoln City", "Mariah Hill", "Richland", "Rockport",
                "Saint Meinrad", "Santa Claus", "Troy", "Grovertown", "Hamlet", "Knox", "North Judson", "Ora", "San Pierre", "Angola",
                "Fremont", "Hamilton", "Hudson", "Orland", "Pleasant Lake", "Carlisle", "Dugger", "Fairbanks", "Farmersburg",
                "Graysville", "Hymera", "Merom", "New Lebanon", "Paxton", "Shelburn", "Sullivan", "Bennington", "East Enterprise",
                "Florence", "Patriot", "Vevay", "Battle Ground", "Buck Creek", "Clarks Hill", "Dayton", "Lafayette", "Montmorenci",
                "Romney", "Stockwell", "West Lafayette", "Westpoint", "Goldsmith", "Hobbs", "Kempton", "Sharpsville", "Tipton",
                "Windfall", "Brownsville", "Liberty", "West College Corner", "Evansville", "Inglefield", "Blanford", "Cayuga",
                "Clinton", "Dana", "Hillsdale", "Newport", "Perrysville", "Saint Bernice", "Universal", "Fontanet", "Lewis",
                "New Goshen", "Pimento", "Prairie Creek", "Prairieton", "Riley", "Saint Mary Of The Woods", "Seelyville",
                "Shepardsville", "Terre Haute", "West Terre Haute", "La Fontaine", "Lagro", "Laketon", "Liberty Mills",
                "North Manchester", "Roann", "Servia", "Somerset", "Urbana", "Wabash", "Pine Village", "State Line", "West Lebanon",
                "Williamsport", "Boonville", "Chandler", "Elberfeld", "Folsomville", "Lynnville", "Newburgh", "Tennyson", "Campbellsburg",
                "Fredericksburg", "Hardinsburg", "Little York", "Pekin", "Salem", "Boston", "Cambridge City", "Centerville", "Dublin",
                "Economy", "Fountain City", "Greens Fork", "Hagerstown", "Milton", "Pershing", "Richmond", "Webster", "Williamsburg",
                "Bluffton", "Craigville", "Keystone", "Liberty Center", "Markle", "Ossian", "Petroleum", "Poneto", "Uniondale", "Brookston",
                "Buffalo", "Burnettsville", "Chalmers", "Idaville", "Monon", "Monticello", "Reynolds", "Wolcott", "Churubusco",
                "Columbia City", "Larwill", "South Whitley", "Louisville", "Other", "Cincinnati", "Harrison", "Watseka");

        System.out.println("=========================================================================");
        Collections.sort(allInebCities);
        allInebCities.forEach(System.out::println);
        return allInebCities;
    }


    public static List<String> inebCountyOptions() {
        List<String> allInebCounties = Arrays.asList("Not Ward", "Adams", "Allen", "Bartholomew", "Benton", "Blackford",
                "Boone", "Brown", "Carroll", "Cass", "Clark", "Clay", "Clinton", "Crawford", "Daviess", "Dearborn", "Decatur",
                "Dekalb", "Delaware", "Dubois", "Elkhart", "Fountain", "Fayette", "Floyd", "Franklin", "Fulton", "Gibson", "Grant", "Greene",
                "Hamilton", "Hancock", "Harrison", "Hendricks", "Henry", "Howard", "Huntington", "Jackson", "Jasper", "Jay", "Jefferson",
                "Jennings", "Johnson", "Knox", "Kosciusko", "Lagrange", "Lake", "La Porte", "Lawrence", "Madison", "Marion", "Marshall",
                "Martin", "Miami", "Monroe", "Montgomery", "Morgan", "Newton", "Noble", "Ohio", "Orange", "Owen", "Parke", "Perry", "Pike",
                "Porter", "Posey", "Pulaski", "Putnam", "Randolph", "Ripley", "Rush", "St Joseph", "Scott", "Shelby", "Spencer",
                "Starke", "Steuben", "Sullivan", "Switzerland", "Tippecanoe", "Tipton", "Union", "Vanderburgh", "Vermillion", "Vigo",
                "Wabash", "Warren", "Warrick", "Washington", "Wayne", "Wells", "White", "Whitley", "Ifssa", "Cinncinnati, Ohio",
                "Harrison, Ohio", "Watseka, Illinois");
        System.out.println("=========================================================================");
        Collections.sort(allInebCounties);
        allInebCounties.forEach(System.out::println);
        System.out.println("=========================================================================");
        return allInebCounties;
    }

    public static List<String> inebAddressSourceOptions() {
//        List<String> addressSource = Arrays.asList("Consumer Reported");
//        List<String> addressSource = Arrays.asList("Consumer Reported", "State Reported");
        List<String> addressSource = Arrays.asList("Consumer Reported");
        System.out.println("=========================================================================");
        Collections.sort(addressSource);
        addressSource.forEach(System.out::println);
        return addressSource;
    }

    public static List<String> getAllCaseMembersFullNameFromCase(){
        List<String> consumers = new ArrayList<>();
        List<WebElement> consumersNames = Driver.getDriver().findElements(By.xpath("//tr[@class='MuiTableRow-root']"));
        for (int i =1; i<=consumersNames.size(); i++){
            String name = Driver.getDriver().findElement(By.xpath("((//tr[@class='MuiTableRow-root'])["+i+"]/td)[3]")).getText();
            consumers.add(name);
        }
        return consumers;
    }

}


