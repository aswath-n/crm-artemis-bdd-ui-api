Feature: API: ATS CONSUMER

  @API-CP-27641 @API-CP-27641-01 @API-CRM-Regression @API-ATS @sang
  Scenario: Case Consumer Match Rule return equal non-blank ssn success
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | ssn       | personId |
      | 555551112 | 1111     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Case Consumer SSN" consumer matching API

  @API-CP-27641 @API-CP-27641-02 @API-CRM-Regression @API-ATS @sang
  Scenario: Non Case Consumer Match Rule return equal non-blank ssn success
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | ssn       | personId |
      | 555551118 | 1111     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Non Case Consumer SSN" consumer matching API

  @API-CP-27641 @API-CP-27641-03 @API-CRM-Regression @API-ATS @sang
  Scenario: Case Consumer Match Rule return equal non-blank External ID success
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | externalConsumerId | externalConsumerIdType | personId |
      | 1113februaryM      | Medicaid               | 1111     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Case Consumer External Id" consumer matching API

  @API-CP-27641 @API-CP-27641-04 @API-CRM-Regression @API-ATS @sang
  Scenario: Non Case Consumer Match Rule return equal non-blank External ID success
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | externalConsumerId | externalConsumerIdType | personId |
      | 1118decemberM      | MEDICAID               | 1111     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Non Case Consumer External Id" consumer matching API

  @API-CP-27641 @API-CP-27641-05 @API-CRM-Regression @API-ATS @sang
  Scenario: Case Consumer Match Rule return equal non-blank First name and Last name success
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | firstName | lastName | personId |
      | April     | Month    | 1111     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Case Consumer First Last Name" consumer matching API

  @API-CP-27641 @API-CP-27641-06 @API-CRM-Regression @API-ATS @sang
  Scenario: Case Consumer Match Rule return equal non-blank First Last name DOB success
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | firstName | lastName | dateOfBirth | personId |
      | January   | Month    | 1981-01-01  | 1111     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Case Consumer First Last DoB" consumer matching API

  @API-CP-27641 @API-CP-27641-07 @API-CRM-Regression @API-ATS @sang #Fails due to CP-30944
  Scenario: Non Case Consumer Match Rule return equal non-blank First name and Last name success
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | firstName | lastName | personId |
      | December  | Month    | 1111     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Non Case Consumer First Last Name" consumer matching API

  @API-CP-27641 @API-CP-27641-08 @API-CRM-Regression @API-ATS @sang
  Scenario: Non Case Consumer Match Rule return equal non-blank First Last name DOB success
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | firstName | lastName | dateOfBirth | personId |
      | December  | Month    | 2000-01-02  | 1111     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Non Case Consumer First Last DoB" consumer matching API

  @API-CP-27641 @API-CP-27641-09 @API-CRM-Regression @API-ATS @sang
  Scenario: No Duplicate Cases returned for consumer matching API success
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | firstName | lastName | dateOfBirth | personId |
      | January   | Month    | 1981-01-01  | 1111     |
    And I initiate consumers for matching API
      | ssn       | personId |
      | 555551114 | 2222     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "No Duplicate Cases" consumer matching API

  @API-CP-27641 @API-CP-27641-10 @API-CRM-Regression @API-ATS @sang
  Scenario: Consumer matches multiple persons in the request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | externalConsumerId | externalConsumerIdType | personId |
      | 1113februaryM      | Medicaid               | 3333     |
    And I initiate consumers for matching API
      | firstName | lastName | dateOfBirth | personId |
      | February  | Month    | 2000-09-04  | 1111     |
    And I initiate consumers for matching API
      | ssn       | personId |
      | 555551113 | 2222     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Multiple Matches for Single Consumer" consumer matching API

  @API-CP-27641 @API-CP-27641-11 @API-CRM-Regression @API-ATS @sang
  Scenario: Success Response with minimal occurence request rule sent
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | firstName |
      | February  |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Success Response with minimal" consumer matching API

  @API-CP-27641 @API-CP-27641-12 @API-CRM-Regression @API-ATS @sang
  Scenario: Invalid Request Response for Case Consumer matching API verify 400 status code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | firstName |
      |[blank]|
    And I send POST consumers matching API for "Unsuccessful" response
    Then I verify the response from a successful "Invalid Request Response" consumer matching API

  @API-CP-27641 @API-CP-27641-13 @API-CRM-Regression @API-ATS @sang
  Scenario: Multiple incoming consumer matches and not match consumer in existing Case in consumer API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | firstName | lastName | dateOfBirth | personId |
      | March  | Month    | 2000-09-01  | 1111     |
    And I initiate consumers for matching API
      | firstName | lastName | ssn | personId |
      | August  | Month    | 555551119  | 2222     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Multiple Matches for Multiple Consumer in a case" consumer matching API

  @API-CP-27641 @API-CP-27641-14 @API-CRM-Regression @API-ATS @sang
  Scenario: Multiple incoming consumer matches multiple case and non case consumers in matching API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate consumers for matching API
      | firstName | lastName | personId |
      | January  | Month      | 1111     |
    And I send POST consumers matching API for "Successful" response
    Then I verify the response from a successful "Multiple Matches for Multiple case/non-case consumers" consumer matching API

  @API-CP-30218 @API-CP-30218-01 @API-CRM-Regression @API-ATS @ozgen
  Scenario Outline: Retrieve benefit status for Eligible Newborn and Medicaid General data on Matching Application API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "<eligibilityType>"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"
    Then I verify benefit status records are displayed with population "<eligibilityType>"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    And I initiate applicationdata matching POST API with the "Latest ApplicationId" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response has "Eligible" benefit status with "<eligibilityType>" on the response
  Examples:
     | eligibilityType  |
     | NEWBORN          |
     | MEDICAID-GENERAL |

  @API-CP-30218 @API-CP-30218-02 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Retrieve benefit status for Eligible Pregnant-Women data on Matching Application API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with population type "PREGNANT-WOMEN"
    And  I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                |
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | yes                |
      | enrollmentStartDate          | past               |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"
    Then I verify benefit status records are displayed with population "PREGNANT-WOMEN"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    And I initiate applicationdata matching POST API with the "Latest ApplicationId" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response has "Eligible" benefit status with "PREGNANT-WOMEN" on the response

  @API-CP-30218 @API-CP-30218-03 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Retrieve benefit status for Partially Enrolled Newborn data on Matching Application API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planId                       | 1880                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Partially Enrolled"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    And I initiate applicationdata matching POST API with the "Latest ApplicationId" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response has "Partially Enrolled" benefit status with "NEWBORN" on the response

  @API-CP-30218 @API-CP-30218-04 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Retrieve benefit status for Enrolled Pregnant-Women data on Matching Application API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | yes                  |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    And I initiate applicationdata matching POST API with the "Latest ApplicationId" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response has "Enrolled" benefit status with "PREGNANT-WOMEN" on the response

  @API-CP-30218 @API-CP-30218-05 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Retrieve benefit status for Enrolled Medicaid-General data on Matching Application API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | yes                  |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    And I initiate applicationdata matching POST API with the "Latest ApplicationId" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response has "Enrolled" benefit status with "MEDICAID-GENERAL" on the response

  @API-CP-30218 @API-CP-30218-06 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Retrieve benefit status for NOT Eligible Medicaid-General data on Matching Application API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | current           |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"
    Then I verify benefit status records are displayed with benefit status "Not Eligible"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    And I initiate applicationdata matching POST API with the "Latest ApplicationId" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response has "null" benefit status with "null" on the response


