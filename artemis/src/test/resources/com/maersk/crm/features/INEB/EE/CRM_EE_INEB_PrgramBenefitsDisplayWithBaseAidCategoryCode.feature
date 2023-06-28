Feature: INEB Program And Benefits Screen Display With Base Aid Category Code


  @CP-30689 @CP-23933-02 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Program & Benefits Screen Display When Managed Care Status is NULL or X with a Base Aid Category Code
    #CONSUMER-1
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | X                    |
      | genericFieldText1            | null                 |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-1.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
   #CONSUMER-2
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-2.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | R                    |
      | subProgramTypeCode           | HoosierHealthwise    |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | null                 |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-2.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
  #CONSUMER-3
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-3.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | X                    |
      | genericFieldText1            | null                 |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-3.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
  #CONSUMER-4
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-4.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | A                    |
      | subProgramTypeCode           | HoosierCareConnect   |
      | eligibilityStatusCode        | V                    |
      | genericFieldText1            | null                 |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | RECIPIENT            |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-4.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
  #CONSUMER-5
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-5.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | X                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-5.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    #CONSUMER-6
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-6.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | X                    |
      | genericFieldText1            | null                 |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-6.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    #CONSUMER-7
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-7 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-7.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | null                 |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-7.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    #CONSUMER-8
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-8 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-8.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | M                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-8.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    #CONSUMER-9
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-9 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-9.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | null                 |
      | genericFieldText1            | null                 |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-9.consumerId       |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    #CONSUMER-10
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-10 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-10.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | null                 |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-10.consumerId      |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    #CONSUMER-11
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-11 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-11.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | V                    |
      | genericFieldText1            | Eligible             |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-11.consumerId      |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    #CONSUMER-12
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 30689-12 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 30689-12.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | eligibilityRecordId          | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | HealthyIndianaPlan   |
      | eligibilityStatusCode        | V                    |
      | genericFieldText1            | null                 |
      | categoryCode                 | 10                   |
      | coverageCode                 | cc01                 |
      | fileSource                   | HRECIP               |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | 30689-12.consumerId      |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    When I searched consumer created through api with First Name as "30689-1.firstName" and Last Name as "30689-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "30689-1.firstName" and last name "30689-1.lastName" with data
      | INDICATOR.CHECK CORE                    | is displayed |
      | TEXT.CHECK CORE                         | Check Core   |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | hidden       |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | HHW          |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | Excluded     |
    And I initiated get benefit status by consumer id "30689-1.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HHW-Mandatory"
    And I verify benefit status records are displayed with benefit status "Eligible"
    Then I verify program & benefit info page for consumer first name "30689-2.firstName" and last name "30689-2.lastName" with data
      | INDICATOR.CHECK CORE                    | hidden        |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HHW-Mandatory |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | hidden        |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | hidden        |
    And I initiated get benefit status by consumer id "30689-2.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HHW-Mandatory"
    And I verify benefit status records are displayed with benefit status "Eligible"
    Then I verify program & benefit info page for consumer first name "30689-3.firstName" and last name "30689-3.lastName" with data
      | INDICATOR.CHECK CORE                    | is displayed |
      | TEXT.CHECK CORE                         | Check Core   |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | hidden       |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | HCC          |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | Excluded     |
    And I initiated get benefit status by consumer id "30689-3.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HCC-Mandatory"
    And I verify benefit status records are displayed with benefit status "Eligible"
    Then I verify program & benefit info page for consumer first name "30689-4.firstName" and last name "30689-4.lastName" with data
      | INDICATOR.CHECK CORE                    | hidden        |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HCC-Voluntary |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | hidden        |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | hidden        |
    And I initiated get benefit status by consumer id "30689-4.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HCC-Voluntary"
    And I verify benefit status records are displayed with benefit status "Eligible"
    Then I verify program & benefit info page for consumer first name "30689-5.firstName" and last name "30689-5.lastName" with data
      | INDICATOR.CHECK CORE                    | is displayed |
      | TEXT.CHECK CORE                         | Check Core   |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | hidden       |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | HIP          |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | Excluded     |
    And I initiated get benefit status by consumer id "30689-5.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HIP-Mandatory"
    And I verify benefit status records are displayed with benefit status "Benefit Status Error"
    Then I verify program & benefit info page for consumer first name "30689-6.firstName" and last name "30689-6.lastName" with data
      | INDICATOR.CHECK CORE                    | is displayed |
      | TEXT.CHECK CORE                         | Check Core   |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | hidden       |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | HIP          |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | Excluded     |
    And I initiated get benefit status by consumer id "30689-6.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HIP-Mandatory"
    And I verify benefit status records are displayed with benefit status "Benefit Status Error"
    Then I verify program & benefit info page for consumer first name "30689-7.firstName" and last name "30689-7.lastName" with data
      | INDICATOR.CHECK CORE                    | is displayed  |
      | TEXT.CHECK CORE                         | Check Core    |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HIP-Mandatory |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | hidden        |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | hidden        |
    And I initiated get benefit status by consumer id "30689-7.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HIP-Mandatory"
    And I verify benefit status records are displayed with benefit status "Benefit Status Error"
    Then I verify program & benefit info page for consumer first name "30689-8.firstName" and last name "30689-8.lastName" with data
      | INDICATOR.CHECK CORE                    | hidden        |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HIP-Mandatory |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | hidden        |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | hidden        |
    And I initiated get benefit status by consumer id "30689-8.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HIP-Mandatory"
    And I verify benefit status records are displayed with benefit status "Benefit Status Error"
    Then I verify program & benefit info page for consumer first name "30689-9.firstName" and last name "30689-9.lastName" with data
      | INDICATOR.CHECK CORE                    | is displayed |
      | TEXT.CHECK CORE                         | Check Core   |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | hidden       |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | HIP          |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | -- --        |
    And I initiated get benefit status by consumer id "30689-9.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HIP-Mandatory"
    And I verify benefit status records are displayed with benefit status "Benefit Status Error"
    Then I verify program & benefit info page for consumer first name "30689-10.firstName" and last name "30689-10.lastName" with data
      | INDICATOR.CHECK CORE                    | is displayed |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | hidden       |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | HIP          |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | -- --        |
    And I initiated get benefit status by consumer id "30689-10.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HIP-Mandatory"
    And I verify benefit status records are displayed with benefit status "Benefit Status Error"
    Then I verify program & benefit info page for consumer first name "30689-11.firstName" and last name "30689-11.lastName" with data
      | INDICATOR.CHECK CORE                    | hidden        |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HIP-Voluntary |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | hidden        |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | hidden        |
    And I initiated get benefit status by consumer id "30689-11.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HIP-Voluntary"
    And I verify benefit status records are displayed with benefit status "Benefit Status Error"
    Then I verify program & benefit info page for consumer first name "30689-12.firstName" and last name "30689-12.lastName" with data
      | INDICATOR.CHECK CORE                    | is displayed  |
      | TEXT.CHECK CORE                         | Check Core    |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | HIP-Voluntary |
      | CURRENT ELIGIBILITY.CONSUMER PROGRAM    | hidden        |
      | CURRENT ELIGIBILITY.MANAGED CARE STATUS | hidden        |
    And I initiated get benefit status by consumer id "30689-12.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "HIP-Voluntary"
    And I verify benefit status records are displayed with benefit status "Benefit Status Error"     
