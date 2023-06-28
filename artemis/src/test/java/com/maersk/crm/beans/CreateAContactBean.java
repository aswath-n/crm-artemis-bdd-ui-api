package com.maersk.crm.beans;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.maersk.crm.utilities.World.getWorld;

public class CreateAContactBean {

    private static String firstName;
    private static String lastName;
    private String middleInitial;
    private String gender;
    private String phoneNumber;
    private String ssn;
    private long dateOfBirthMilliseconds;

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    private String correlationID;

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    private String county;

    public Map<String, String> getRandomConsumer() {
        if (randomConsumer == null) {
            createRandomValues();
        }
        return randomConsumer;
    }

    public Map<String, String> getRandomConsumerRequiredFieldsOnly() {
        if (randomConsumer == null) {
            createRandomValuesForRequiredFields();
        }
        return randomConsumer;
    }

    private String email;
    private String dateOfBirth;
    private String mediCareNum;
    private String medicAidNum;
    private String zipCode;
    private String uniqueID;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;
    private String addressType;
    private String consumerID;


    public String getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(String consumerID) {
        this.consumerID = consumerID;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType.getAddressType();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(Language language) {
        this.preferredLanguage = language.getLang();
    }

    private String preferredLanguage;
    private Map<String, String> randomConsumer = null;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender.getGender();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSsn() {
        return ssn;
    }

    public String getSsnWithDashes() {
        return ssn.substring(0, 3) + "-" + ssn.substring(3, 5) + "-" + ssn.substring(5);
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String convertMilliSecondsToDateFormatMMddyyyy(long milliseconds) {
        DateFormat simple = new SimpleDateFormat("MM/dd/yyyy");
        return simple.format(new Date(milliseconds));
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public long getDateOfBirthMilliseconds() {
        return this.dateOfBirthMilliseconds;
    }

    public String getDateOfBirthWithSlashes() {
        return this.dateOfBirth.substring(0, 2) + "/" + this.dateOfBirth.substring(2, 4) + "/" + this.dateOfBirth.substring(4);
    }

    public void setDateOfBirth(long dateOfBirthMilliseconds) {
        this.dateOfBirthMilliseconds = dateOfBirthMilliseconds;
        this.dateOfBirth = convertMilliSecondsToDateFormatMMddyyyy(dateOfBirthMilliseconds);
    }

    public String getMediCareNum() {
        return mediCareNum;
    }

    public void setMediCareNum(String mediCareNum) {
        this.mediCareNum = mediCareNum;
    }

    public String getMedicAidNum() {
        return medicAidNum;
    }

    public void setMedicAidNum(String medicAidNum) {
        this.medicAidNum = medicAidNum;
    }


    public enum Language {
        ENGLISH("English"), SPANISH("Spanish");
        private String Lang;

        public String getLang() {
            return Lang;
        }

        private Language(String lang) {
            this.Lang = lang;
        }
    }

    public enum Gender {
        MALE("Male"), FEMALE("Female");
        private String gender;

        public String getGender() {
            return gender;
        }

        private Gender(String gender) {
            this.gender = gender;
        }
    }

    public enum County {
        ALBANY("Albany"), ALLEGHENY("Allegheny"), APACHECOUNTY("Apache County"), BRONX("Bronx"), BROOME("Broome"), CATTARAUGUS("Cattaraugus"), CAYUGA("Cayuga"), CHAUTAUQUA("Chautauqua"), CHEMUNG("Chemung"), CHEMANGO("Chenango"), CLINTON("Clinton"), COCHISECOUNTY("Cochise County"), COCONOCOUNTY("Coconino County"), COLUMBIA("Columbia"), CORTLAND("Cortland"), DELAWARE("Delaware"), DUTCHESS("Dutchess"), ERIE("Erie"), ESSEX("Essex"), FRANKLIN("Franklin"), FULTON("Fulton"), GENESEE("Genesee"), GILACOUNTY("Gila County"), GRAHAMCOUNTY("Graham County"), GREENE("Greene"), GREENLEECOUNTY("Greenlee County"), HAMILTON("Hamilton"), HERKIMER("Herkimer"), JEFFERSON("Jefferson"), LEWIS("Lewis"), LIVINGSTON("Livingston"), MADISON("Madison"), MARICOPACOUNTY("Maricopa County"), MOHAVECOUNTY("Mohave County"), MONROE("Monroe"), MONTGOMERY("Montgomery"), NASSAU("Nassau"), NAVAJOCOUNTY("Navajo County"), NEWYORK("New York"), MANAHATTAN("New York (Manahattan)"), NIAGARA("Niagara"), ONEIDA("Oneida"), ONONDAGA("Onondaga"), ONTARIO("Ontario"), ORANGE("Orange"), ORLEANS("Orleans"), OSWEGO("Oswego"), OTSEGO("Otsego"), PIMACOUNTY("Pima County"), PINALCOUNTY("Pinal County"), PUTNAM("Putnam"), QUEENS("Queens"), RENSSELAER("Rensselaer"), RICHMOND("Richmond (Staten Island)"), ROCKLAND("Rockland"), SANTACRUZCOUNTY("Santa Cruz County"), SARATOGA("Saratoga"), SCHENECTADY("Schenectady"), SCHOHAIRIE("Schoharie"), SCHUYLER("Schuyler");
        private String county;

        public String getCounty() {
            return county;
        }

        private County(String county) {
            this.county = county;
        }
    }

    public enum AddressType {
        MAILING("Mailing"), PHYSICAL("Physical");
        private String addressType;

        public String getAddressType() {
            return addressType;
        }

        private AddressType(String addressType) {
            this.addressType = addressType;
        }
    }

    private void createRandomValues() {
        Faker faker = new Faker();
        this.firstName = faker.name().firstName() + RandomStringUtils.random(5, true, false);
        this.middleInitial = RandomStringUtils.random(1, true, false).toUpperCase();
        this.lastName = faker.name().lastName() + RandomStringUtils.random(5, true, false);
        this.gender = System.currentTimeMillis() % 2 == 0 ? Gender.MALE.getGender() : Gender.FEMALE.getGender();
        try {
            this.dateOfBirthMilliseconds = LocalDateTime.now().minusYears(faker.random().nextInt(18, 65)).atZone(ZoneId.of("US/Eastern")).toInstant().toEpochMilli();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dateOfBirth = convertMilliSecondsToDateFormatMMddyyyy(this.dateOfBirthMilliseconds).replace("/", "");
        this.phoneNumber = RandomStringUtils.random(10, false, true);
        this.email = RandomStringUtils.random(5, true, false) + "@maerskTest" + RandomStringUtils.random(7, true, false) + ".com";
        this.gender = System.currentTimeMillis() % 2 == 0 ? Gender.MALE.getGender() : Gender.FEMALE.getGender();
        this.addressLine1 = RandomStringUtils.random(4, false, true) + faker.address().streetAddress();
        this.addressLine2 = "Apt " + RandomStringUtils.random(5, false, true);
        this.state = faker.address().state();
        this.zipCode = RandomStringUtils.random(5, false, true);
        this.city = faker.address().city();
        County[] counties = County.values();
        this.county = "Albany";//counties[faker.random().nextInt(0, counties.length - 1)].getCounty();
        this.addressType = System.currentTimeMillis() % 2 == 0 ? AddressType.MAILING.getAddressType() : AddressType.PHYSICAL.getAddressType();
        this.ssn = RandomStringUtils.random(9, false, true);
        this.uniqueID = RandomStringUtils.random(12, false, true);
        this.correlationID = RandomStringUtils.random(16, false, true);
        this.preferredLanguage = Language.ENGLISH.getLang();
        randomConsumer = new HashMap<String, String>();
        Field[] randomValues = getWorld().contactBean.get().getClass().getDeclaredFields();
        for (Field field : randomValues) {
            try {
                randomConsumer.put(field.getName(), field.get(this).toString());
            } catch (NullPointerException nool) {
                randomConsumer.put(field.getName(), "null");
            } catch (Exception e) {
                continue;
            }
        }
    }


    private void createRandomValuesForRequiredFields() {
        randomConsumer = new HashMap<String, String>();
        randomConsumer.put("firstName", firstName = RandomStringUtils.random(7, true, false));
        randomConsumer.put("lastName", lastName = RandomStringUtils.random(7, true, false));
        randomConsumer.put("phoneNumber", phoneNumber = RandomStringUtils.random(10, false, true));
        randomConsumer.put("zipCode", zipCode = RandomStringUtils.random(5, false, true));


    }

    public void getAllFields() {
        Field[] randomValues = getWorld().contactBean.get().getClass().getDeclaredFields();
        List<String> list = new ArrayList<>();
        for (Field field : randomValues) {
            list.add(field.getName());
        }
    }

}
