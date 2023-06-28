Feature: ETL newborns file uploads

  @ETL-CP-35354 @ETL-EE-DC @DC-EB-ETL-Regression @elshan
  Scenario: Initial newborn file creation
    Given I create new file line by name "35354-1" with data
      | ProviderNumber   | 1                       |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-2" with data
      | CaseNumberNewborn | null                    |
      | RecipEligBegDate  | currentETLver           |
      | RecipEligEndDate  | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-3" with data
      | MotherMedicaidId | null                    |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-4" with data
      | MotherFirstName  | null                    |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-5" with data
      | MotherLastName   | null                    |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-6" with data
      | MotherState      | ZZ                      |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-7" with data
      | MotherQuadrant   | ZZ                      |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-8" with data
      | RecipLastName    | null                    |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-9" with data
      | RecipFirstName   | null                    |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-10" with data
      | RecipDateOfBirth | null                    |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-11" with data
      | RecipDateOfBirth | x0221212                |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-12" with data
      | RecipSex         | null                    |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-13" with data
      | RecipSex         | Z                       |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-14" with data
      | RecipRaceCode    | Z                       |
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-15" with data
      | MCOHospitalNumber | null                    |
      | RecipEligBegDate  | currentETLver           |
      | RecipEligEndDate  | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-16" with data
      | RecipEligBegDate | null                    |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    Given I create new file line by name "35354-17" with data
      | RecipEligBegDate | currentETLver        |
      | RecipEligEndDate | InvalidEndDateETLver |
    And I create "NEWBORNS" file with planCode "0055558200" and send to S-three bucket "max-etl-dceb-non-prod" to S-three folder "QE/NewBorn/input/" with names list
      | 35354-1  |
      | 35354-2  |
      | 35354-3  |
      | 35354-4  |
      | 35354-5  |
      | 35354-6  |
      | 35354-7  |
      | 35354-8  |
      | 35354-9  |
      | 35354-10 |
      | 35354-11 |
      | 35354-12 |
      | 35354-13 |
      | 35354-14 |
      | 35354-15 |
      | 35354-16 |
      | 35354-17 |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_NewBorn_FileProcessing"
    Then I validate error messages on ETL NEWBORN DCEB staging table in database with data
      | 35354-1  | Provider Number/ Plan Code value must match the value in the File Name |
      | 35354-2  | Case Number is required                                                |
      | 35354-3  | Medicaid ID is required                                                |
      | 35354-4  | Member First Name is Required                                          |
      | 35354-5  | Member Last Name is Required                                           |
      | 35354-6  | State Code must be a valid state code                                  |
      | 35354-7  | Quadrant Code must be a valid value                                    |
      | 35354-8  | Member Last Name is Required                                           |
      | 35354-9  | Member First Name is Required                                          |
      | 35354-10 | Birth Date is required                                                 |
      | 35354-11 | Invalid Birth Date                                                     |
      | 35354-12 | Sex Code is required                                                   |
      | 35354-13 | Sex Code must be a valid value                                         |
      | 35354-14 | Recipient Race Code must be a valid value                              |
      | 35354-15 | MCO Hospital Number required                                           |
      | 35354-16 | Invalid Eligibility Start Date                                         |
      | 35354-17 | Invalid Eligibility End Date                                           |

  @API-CP-44081 @API-CP-44081-01 @beka @DC-EB-ETL-Regression
  Scenario: AC 1.0 Demographic Field Mapping
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I create new file line by name "44081-01" with data
      | RecipEligBegDate | currentETLver           |
      | RecipEligEndDate | lastDayOfNextYearETLver |
    And I create "NEWBORNS" file with planCode "0055558200" and send to S-three bucket "max-etl-dceb-non-prod" to S-three folder "QE/NewBorn/input/" with names list
      | 44081-01 |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_NewBorn_FileProcessing"
    And  I initiate consumers V2 API
    When I search consumer by first name and last name getting from newborn file
    Then I verify search response case is null
