Feature: ETL DCEB Provider file upload

  @ETL-CP-41500 @ETL-EE-DC @DC-EB-ETL-Regression @elshan
  Scenario: Initial provider file creation
    Given I create new file line by name "41500-1" with data
      | ProviderNumber | 1 |
    Given I create new file line by name "41500-2" with data
      | ProviderType | null |
    Given I create new file line by name "41500-3" with data
      | ProviderType | 9 |
    Given I create new file line by name "41500-4" with data
      | ProviderLastName | null |
    Given I create new file line by name "41500-5" with data
      | ProviderFirstName | null |
    Given I create new file line by name "41500-6" with data
      | ProviderAddressLine1 | null |
    Given I create new file line by name "41500-7" with data
      | ProviderCity | null |
    Given I create new file line by name "41500-8" with data
      | ProviderStateCode | null |
    Given I create new file line by name "41500-9" with data
      | ProviderZipCode | null |
    Given I create new file line by name "41500-10" with data
      | ProviderPhoneNumber | null |
    Given I create new file line by name "41500-11" with data
      | ProviderLimitation | null |
    Given I create new file line by name "41500-12" with data
      | ProviderLimitation | W |
    Given I create new file line by name "41500-13" with data
      | PrimarySpeciality1 | null |
    Given I create new file line by name "41500-14" with data
      | PrimarySpeciality1 | XYZ |
    Given I create new file line by name "41500-15" with data
      | Speciality2 | XYZ |
    Given I create new file line by name "41500-16" with data
      | Speciality3 | XYZ |
    Given I create new file line by name "41500-17" with data
      | Language1 | XYZ |
    Given I create new file line by name "41500-18" with data
      | Language2 | XYZ |
    Given I create new file line by name "41500-19" with data
      | Language3 | XYZ |
    Given I create new file line by name "41500-20" with data
      | Language4 | XYZ |
    Given I create new file line by name "41500-21" with data
      | Language5 | XYZ |
    Given I create new file line by name "41500-22" with data
      | HospitalAffl1 | 123456 |
    Given I create new file line by name "41500-23" with data
      | HospitalAffl2 | 123456 |
    Given I create new file line by name "41500-24" with data
      | HospitalAffl3 | 123456 |
    Given I create new file line by name "41500-25" with data
      | HospitalAffl4 | 123456 |
    Given I create new file line by name "41500-26" with data
      | HospitalAffl5 | 123456 |
    Given I create new file line by name "41500-27" with data
      | NPINumber | null |
    And I create "PROVIDERDATA" file with planCode "0055558200" and send to S-three bucket "max-etl-dceb-non-prod" to S-three folder "QE/Provider/to_MAX/" with names list
      | 41500-1  |
      | 41500-2  |
      | 41500-3  |
      | 41500-4  |
      | 41500-5  |
      | 41500-6  |
      | 41500-7  |
      | 41500-8  |
      | 41500-9  |
      | 41500-10 |
      | 41500-11 |
      | 41500-12 |
      | 41500-13 |
      | 41500-14 |
      | 41500-15 |
      | 41500-16 |
      | 41500-17 |
      | 41500-18 |
      | 41500-19 |
      | 41500-20 |
      | 41500-21 |
      | 41500-22 |
      | 41500-23 |
      | 41500-24 |
      | 41500-25 |
      | 41500-26 |
      | 41500-27 |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_planProvider"
    Then I validate error messages on ETL PROV STG DCEB table in database with data
      | 41500-1  | Provider Number/ Plan Code value must be a valid value                 |
      | 41500-2  | Provider Type required,Provider Type must be a valid value             |
      | 41500-3  | Provider Type must be a valid value                                    |
      | 41500-4  | Provider Last Name required                                            |
      | 41500-5  | Provider First Name required                                           |
      | 41500-6  | Address Line 1 required                                                |
      | 41500-7  | City is required                                                       |
      | 41500-8  | Required StateCode missing                                             |
      | 41500-9  | Required fields are missing Zip,Required fields are missing Zip        |
      | 41500-10 | Required Provider Phone Number missing                                 |
      | 41500-11 | Provider Limitation required,Provider Limitation must be a valid value |
      | 41500-12 | Provider Limitation must be a valid value                              |
      | 41500-13 | Primary Specialty required                                             |
      | 41500-14 | Primary Specialty must be a valid value                                |
      | 41500-15 | Specialty 2 must be a valid value                                      |
      | 41500-16 | Specialty 3 must be a valid value                                      |
      | 41500-17 | Language 1 must be a valid value                                       |
      | 41500-18 | Language 2 must be a valid value                                       |
      | 41500-19 | Language 3 must be a valid value                                       |
      | 41500-20 | Language 4 must be a valid value                                       |
      | 41500-21 | Language 5 must be a valid value                                       |
      | 41500-22 | Hospital Affl 1 must be a valid value                                  |
      | 41500-23 | Hospital Affl 2 must be a valid value                                  |
      | 41500-24 | Hospital Affl 3 must be a valid value                                  |
      | 41500-25 | Hospital Affl 4 must be a valid value                                  |
      | 41500-26 | Hospital Affl 5 must be a valid value                                  |
      | 41500-27 | Required fields are missing NPI                                        |
