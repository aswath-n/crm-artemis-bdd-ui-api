package com.maersk.crm.api_step_definitions;


import com.maersk.crm.step_definitions.CRMSearchProvidersResultsView;
import com.maersk.crm.utilities.Api_CommonSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/19/2020
 */
public class APIProviderSearch extends Api_CommonSteps {


    public final ThreadLocal<String> getNPI = ThreadLocal.withInitial(String::new);
    final ThreadLocal<List<String>> distanceList = ThreadLocal.withInitial(ArrayList::new);
    CRMSearchProvidersResultsView crmSearchProvidersResultsView = new CRMSearchProvidersResultsView();

    @And("Response status return  for {string}")
    public void iSfirstName(String name) {
        assertEquals(asStr("status"), name);
    }

    @And("Verify response return first name {string}")
    public void verifResponseReturnFirstName(String name) {
        assertEquals(asStr("c.providerSearchResponse.content[0].firstName"), name);
    }

    @And("Verify response return first name contains {string}")
    public void verifResponseReturnFirstNamecontains(String name) {
        float count = Float.parseFloat(asStr("c.providerSearchResponse.numberOfElements"));
        for (int i = 0; i < count; i++) {
            assertTrue(asStr("c.providerSearchResponse.content[" + i + "].firstName").contains(name));
        }
    }

    @Then("I verify same number of records from Warning Message got displayed")
    public void i_verify_correct_number_of_records_got_displayed() {
        assertEquals((asStr("c.totalRecords")).split("\\.")[0], crmSearchProvidersResultsView.recordCount.get(), "");
    }

    @And("Verify response return last name contains {string}")
    public void verifResponseReturnLastNamecontains(String name) {
        float count = Float.parseFloat(asStr("c.providerSearchResponse.numberOfElements"));
        for (int i = 0; i < count; i++) {
            assertTrue(asStr("c.providerSearchResponse.content[" + i + "].lastName").contains(name));
        }
    }

    @And("Verify response return group name contains {string}")
    public void verifResponseReturnGroupNamecontains(String name) {
        float count = Float.parseFloat(asStr("c.providerSearchResponse.numberOfElements"));
        for (int i = 0; i < count; i++) {
            assertTrue(asStr("c.providerSearchResponse.content[" + i + "].groupName").contains(name));
        }
    }

    @Then("I Verify response return age ranges")
    public void i_verify_response_return_age_ranges(DataTable arg1) throws Throwable {
        List<String> lst1 = arg1.asList();
        for (int i = 0; i < lst1.size(); i++) {
            String temp = asStr("c.ENUM_AGE_RANGE[" + i + "].description");
            System.out.println(temp);
            assertEquals(lst1.get(i), temp);
        }
    }


    @And("Verify response First name {string} Last name {string} and Plan Name is {string}")
    public void verifyResponseFirstNameLastNameAndGroupNameIs(String frstName, String lstName, String plnName) {
        assertEquals(asStr("c.providerSearchResponse.content[0].firstName"), frstName);
        assertEquals(asStr("c.providerSearchResponse.content[0].lastName"), lstName);
        assertEquals(asStr("c.providerSearchResponse.content[0].planName"), plnName);
    }

    @And("Verify response return Last Name {string}")
    public void verifyResponseReturnLastName(String lastName) {
        int len = lastName.length();
        assertTrue(asStr("c.providerSearchResponse.content[0].lastName").substring(0, len).equalsIgnoreCase(lastName));
        assertNotNull(asList("c.providerSearchResponse.content[*].lastName"));
    }

    @And("Verify response return Group Name {string}")
    public void verifyResponseReturnGroupName(String GroupName) {
        assertEquals(asStr("c.providerSearchResponse.content[0].groupName"), GroupName);
        assertTrue(isValueExist("c.providerSearchResponse.content[*].groupName"));
    }

    @And("Verify response return Plan Name {string}")
    public void verifyResponseReturnPlanName(String planName) {
        assertEquals(asStr("c.providerSearchResponse.content[0].planName"), planName);
        assertTrue(isValueExist("c.providerSearchResponse.content[*].planName"));
    }

    @And("Verify response return Provider Type {string}")
    public void verifyResponseReturnProviderType(String providerType) {
        assertEquals(asStr("c.providerSearchResponse.content[0].providerType[0].providerTypeCd"), providerType);
        assertTrue(isValueExist("c.providerSearchResponse.content[*].providerType[0].providerTypeCd"));
    }

    @And("Verify response Provider Specialities are exist")
    public void verifyResponseReturnProviderSpeciality() {
        assertTrue(isValueExist("c.providerSearchResponse.content[*].providerSpeciality[0].specialityCd"));
    }

    @And("Verify the {int} record results are returning in the response")
    public void verifyTheRecordResultsAreReturningInTheResponse(Integer rec) {
        assertEquals(asInt("c.providerSearchResponse.numberOfElements"), rec);
    }

    @And("Verify response First names start with letter {string}")
    public void verifyResponseFirstNamesStartWithLetter(String frstLet) {
        assertTrue(String.valueOf(defineValue("c.providerSearchResponse.content[0].firstName")).contains(frstLet));
        assertTrue(isValueExist("c.providerSearchResponse.content[*].firstName"));
    }

    @Then("I verify response provider name values {string} search parameters {string} in First Name and {string} in Last Name")
    public void i_verify_response_provider_name_values_something_search_parameters_something_in_first_name_and_something_in_last_name(String operator, String fname, String lname) throws Throwable {
        float count = Float.parseFloat(asStr("c.providerSearchResponse.numberOfElements"));
        if (operator.equals("startsWith")) {
            for (int i = 0; i < count; i++) {
                assertTrue(asStr("c.providerSearchResponse.content[" + i + "].firstName").startsWith(fname), "FAIL!..FIRSTNAME doesn't startwith parameter!");
                assertTrue(asStr("c.providerSearchResponse.content[" + i + "].lastName").startsWith(lname), "FAIL!..LASTNAME doesn't startwith parameter!");
            }
        } else {
            for (int i = 0; i < count; i++) {
                assertTrue(asStr("c.providerSearchResponse.content[" + i + "].firstName").contains(fname), "FAIL!..FIRSTNAME doesn't contain parameter!");
                assertTrue(asStr("c.providerSearchResponse.content[" + i + "].lastName").contains(lname), "FAIL!..LASTNAME doesn't contain parameter!");
            }
        }

    }

    @Then("I verify response provider name values {string} search parameters {string} in Group Name")
    public void i_verify_response_provider_name_values_something_search_parameters_something_in_group_name(String operator, String gname) throws Throwable {
        float count = Float.parseFloat(asStr("c.providerSearchResponse.numberOfElements"));
        if (operator.equals("startsWith")) {
            for (int i = 0; i < count; i++) {
                assertTrue(asStr("c.providerSearchResponse.content[" + i + "].groupName").startsWith(gname), "FAIL!..GROUPNAME doesn't startwith parameter!");
            }
        } else {
            for (int i = 0; i < count; i++) {
                assertTrue(asStr("c.providerSearchResponse.content[" + i + "].groupName").contains(gname), "FAIL!..GROUPNAME doesn't contain parameter!");
            }
        }

    }


    @And("Verify provider details PCP is {string}")
    public void verifyProviderDetailsPCPIs(String pcp) {
        assertTrue(isValueExist("c.providerSearchResponse.content[*].pcpFlag"));
    }

    @And("Verify response Phone Number are exist")
    public void verifyResponsePhoneNumberAreExist() {
        assertTrue(isValueExist("c.providerSearchResponse.content[*].phoneNumber"));
    }

    @And("Verify response language type Description is {string}")
    public void verifyResponseLanguageTypeDescriptionIs(String lang) {
        assertEquals(asStr("c.languageCdList[0].languagetypeDescription"), lang);
    }

    @And("^Verify that \"([^ ]+)\\s+([^ ]+)\\s+(.+)\"$")
    public void verifyInResponse(String k1, String comparSign, String k2) {
        verifyThat(k1, comparSign, k2);
    }

    @And("I verify provider search response with values given below")
    public void i_verify_provider_search_response_with_values_given_below(Map<String, String> fields) {
        String temp = null;
        for (String key : fields.keySet()) {

            for (int i = 0; i < Integer.parseInt(asStr("c.totalRecords").split("\\.")[0]); i++) {
                temp = fields.get(key);
                if (fields.get(key).isEmpty())
                    temp = null;
                if (key.equals("ageHighLimit")) {
                    temp = temp.split("\\.")[0];
                    assertTrue(Double.parseDouble(asStr("c.providerSearchResponse.content[" + i + "]." + key)) <= Double.parseDouble(temp));
                } else if (key.equals("ageLowLimit")) {
                    assertTrue(Double.parseDouble(asStr("c.providerSearchResponse.content[" + i + "]." + key)) >= Double.parseDouble(temp));
                } else {
                    assertEquals(asStr("c.providerSearchResponse.content[" + i + "]." + key), temp);
                }
            }
        }

    }

    @And("Verify response for Search with {string}, {string} and {string}")
    public void verifyResponseForSearchWithAnd(String adds, String city, String zipC) {
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].addressLine1"), adds);
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].city"), city);
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].zipCode"), zipC);
    }


    @And("I verify speciality {string} and provider type {string}")
    public void i_verify_speciality_something_and_provider_type_something(String provSpCode, String provType) throws Throwable {
        assertEquals(asStr("c.providerSearchResponse.content[0].providerType[0].providerTypeCd"), provType);
        assertEquals(asStr("c.providerSearchResponse.content[0].providerSpeciality[0].specialityCd"), provSpCode);
    }

    @And("Verify provider city distance")
    public void verifyProviderCityDistance() {
        assertNotNull(asStr("c.providerSearchResponse.content[0].providerAddress[0].normalizedPhysicalAddress.distance"));
    }

    @And("Verify provider city distance within {int} miles")
    public void verifyProviderCityDistanceWithinMiles(int distance) {
        String dis = asStr("c.providerSearchResponse.content[0].providerAddress[0].normalizedPhysicalAddress.distance");
        double disLon = Double.parseDouble(dis);
        assertTrue(disLon <= distance);
    }

    @And("Verify response for Provider Search with multi parameters")
    public void verifyResponseForProviderSearchWithMultiParameters() {
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].addressLine1"), "4791 S Main St", "Provider Address is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].city"), "Acworth", "Provider City is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].zipCode"), "30101", "Provider ZipCode is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].firstName"), "David");
        assertEquals(asStr("c.providerSearchResponse.content[0].lastName"), "Wonder");
        assertEquals(asStr("c.providerSearchResponse.content[0].planName"), "AMERIGROUP COMMUNITY CARE");
        assertEquals(asStr("c.providerSearchResponse.content[0].genderCd"), "M");
    }

    @Then("Verify response return provider details")
    public void verifyResponseReturnProviderDetails() {
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].addressLine1"), "4791 S Main St", "Provider Address is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].city"), "Acworth", "Provider City is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].zipCode"), "30101", "Provider ZipCode is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].firstName"), "Colin", "Provider FirstName is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].lastName"), "Dave", "Provider LastName is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].planName"), "AMERIGROUP COMMUNITY CARE", "Provider PlanName is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].providerLanguages[0].languageTypeCd"), "TA", "Provider language is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].genderCd"), "M", "Provider Gender is Fail");
    }


    @Then("Verify search result response return provider details {string}")
    public void verifySearchResultResponseReturnProviderDetails(String nameEvent) {
        getNPI.set(asStr(nameEvent + ".providers[0].npi"));
        System.out.println("GetNPI " + getNPI.get());
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].addressLine1"), asStr(nameEvent + ".providers[0].providerAddress[1].addressLine1"), "Provider Address is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].city"), asStr(nameEvent + ".providers[0].providerAddress[1].city"), "Provider City is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].zipCode"), asStr(nameEvent + ".providers[0].providerAddress[1].zipCode"), "Provider ZipCode is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].firstName"), asStr(nameEvent + ".providers[0].firstName"), "Provider FirstName is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].lastName"), asStr(nameEvent + ".providers[0].lastName"), "Provider LastName is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].planName"), asStr(nameEvent + ".providers[0].planName"), "Provider PlanName is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].providerLanguages[2].languageTypeCd"), asStr(nameEvent + ".providers[0].providerLanguages[1].languageTypeCd"), "Provider language is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].genderCd"), asStr(nameEvent + ".providers[0].genderCd"), "Provider Gender is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].npi"), asStr(nameEvent + ".providers[0].npi"), "Provider NPI is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].providerAddress[0].state"), asStr(nameEvent + ".providers[0].providerAddress[0].state"), "Provider State is Fail");
        assertEquals(asStr("c.providerSearchResponse.content[0].effectiveStartDate"), asStr(nameEvent + ".providers[0].effectiveStartDate"), "Provider effectiveStartDate is Fail");
    }

    @And("Verify response Standardize and Geo Code for Provider Search")
    public void verifyResponseStandardizeAndGeoCodeForProviderSearch() {
//        Assert.assertNotNull(asStr("c.providerSearchResponse.content[0].providerNormalizedAddress[0].lattitude"));
//        Assert.assertNotNull(asStr("c.providerSearchResponse.content[0].providerNormalizedAddress[0].longitude"));

        assertNotNull(asStr("c.providerSearchResponse.content[0].providerAddress[0].normalizedPhysicalAddress.lattitude"));
        assertNotNull(asStr("c.providerSearchResponse.content[0].providerAddress[0].normalizedPhysicalAddress.longitude"));
    }

    @And("I verify distance value in payload with one digit after decimal point")
    public void i_verify_distance_value_in_payload_with_one_digit_after_decimal_point() {
        int recordCount = Integer.parseInt(asStr("c.providerSearchResponse.numberOfElements").split("\\.")[0]);
        for (int i = 0; i < recordCount; i++) {
            String temp = asStr("c.providerSearchResponse.content[" + i + "].providerAddress[0].normalizedPhysicalAddress.distance");
            if (temp == null)
                break;
            distanceList.get().add(temp);
            if (temp.contains("."))
                assertTrue(temp.split("\\.")[1].length() == 1, "Distance value has more than 1 digits after decimal point");
        }
    }

    @And("I verify in payload distance values are in ascending order")
    public void i_verify_in_payload_distance_values_are_in_ascending_order() {
        List<String> ascending = distanceList.get();
        Collections.sort(distanceList.get());
        assertEquals(distanceList.get(), ascending, "Distances are not in expected order");
    }
}
