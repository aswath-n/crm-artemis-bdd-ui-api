Feature: Manually Update an enrollment from UI for Enroll or plan change scenarios

  @API-CP-11444 @API-CP-11444-01 @ee-UI2API
  Scenario: Create a consumer for enrolling for current time frame , enroll consumer and identify elements are captured
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 11444-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "11444-1.firstName" and Last Name as "11444-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    And I verify "Program & Benefit Info" is displayed
#    Then I verify below details on newly created current enrollment segment
#      | Enrollment Start Date |
#      | Enrollment End Date   |
#      | Selection Status      |
#      | Channel               |
#      | Plan name             |
    Then I verify program & benefit info page for consumer first name "11444-1.firstName" and last name "11444-1.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-Newborn          |
      | CURRENT ELIGIBILITY.SERVICE REGION      | East                      |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver |
      | CURRENT ELIGIBILITY.END DATE            | -- --                     |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | selectedPlanName          |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Selection Made            |
      | CURRENT ENROLLMENT.CHANNEL              | Phone                     |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                     |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver |
      | CURRENT ENROLLMENT.END DATE             | -- --                     |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |
    Then I verify latest enrollment by consumer id "11444-1.consumerId" with data
    # For new-born, enrollment start date may be different than medicaid-general
      | enrollmentStartDate | 1stDayofPresentMonth |
      | enrollmentEndDate  | null                 |
      | status              | SELECTION_MADE       |
      | channel             | PHONE                |
      | planCode            | getFromUISelected    |


  @API-CP-11444 @API-CP-11444-02 @ee-UI2API
  Scenario: Create a consumer for enrolling for future time frame , enroll consumer and identify elements are captured
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 11444-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | isEnrollemntRequired         | no                |
      | otherSegments                |[blank]|
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "11444-2.firstName" and Last Name as "11444-2.lastName"
    And I click on the Contact Channel Type "Email"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    And I verify "Program & Benefit Info" is displayed
#    Then I verify below details on newly created future enrollment segment
#      | Enrollment Start Date |
#      | Enrollment End Date   |
#      | Selection Status      |
#      | Channel               |
#      | Plan name             |
    Then I verify program & benefit info page for consumer first name "11444-2.firstName" and last name "11444-2.lastName" with data
      | FUTURE ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population |
      | FUTURE ELIGIBILITY.SERVICE REGION      | East                        |
      | FUTURE ELIGIBILITY.START DATE          | 1stDayofNextMonthUIver      |
      | FUTURE ELIGIBILITY.END DATE            | -- --                       |
      #*********
      | FUTURE ENROLLMENT.PLAN NAME            | selectedPlanName            |
      | FUTURE ENROLLMENT.SELECTION STATUS     | Selection Made              |
      | FUTURE ENROLLMENT.CHANNEL              | Email                       |
      | FUTURE ENROLLMENT.PCP NAME             | -- --                       |
      | FUTURE ENROLLMENT.START DATE           | 1stDayofNextMonthUIver      |
      | FUTURE ENROLLMENT.END DATE             | -- --                       |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON    | hidden                      |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON   | hidden                      |


  @API-CP-11444 @API-CP-11444-03 @ee-UI2API
  Scenario: Create a consumer for enrolling for future time frame , enrollment record Created By , Created On are captured
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 11444-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I click on the Contact Channel Type "Email"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    When I initiated get enrollment by consumer id ""
    And I run get enrollment api
    Then I verify following fields are captured in the newly created Enrollment Record
      | planCode            |[blank]|
      | enrollmentStartDate | 1stDayofNextMonth |
      | enrollmentType      | MEDICAL           |
      | serviceRegionCode   | East              |
      | createdBy           | 8369              |
#      | CreatedOn           |[blank]|
#    Then I will verify an "ENROLLMENT_SAVE_EVENT" for "DPBI" is created with fields in payload
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 11444-3.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|
