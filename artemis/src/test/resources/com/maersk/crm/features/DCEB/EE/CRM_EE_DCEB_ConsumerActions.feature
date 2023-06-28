Feature: Consumer Actions DCHF-DCEB

  @CP-35997 @CP-35997-01  @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @shruti
  Scenario: CP-35997 DC-EB DCHF- Enroll window View Consumer on Program and Benefit Info Screen
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 35997-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 35997-01.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 25                    |
      | eligibilityRecordId          | 25                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate-DC           |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | DCHF                  |
      | categoryCode                 | 10                    |
      | coverageCode                 | 130                   |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 35997-04000 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 35997-04000.consumerId |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayof6MonthsBefore  |
      | eligibilityStartDate         | 1stDayof6MonthsBefore  |
      | eligibilityEndDate           | highDate-DC            |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | DCHF                   |
      | categoryCode                 | 10                     |
      | coverageCode                 | 130                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "DC-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "35997-01.firstName" and Last Name as "35997-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "35997-01.firstName" and last name "35997-01.lastName" with data
      # CP-35997 2.0 Display Calendar between Pre-lockin & Anniversary
      | CALENDAR ICON HOVER.ACTION TEXT     | ENROLL                                 |
      | CALENDAR ICON HOVER.IMPORTANT DATES | currentUIver - 30DaysFromTodayUIver    |
      | COUNTDOWN.NUMBER OF DAYS UNTIL      | 30DaysFromTodayUIver                   |
      | COUNTDOWN.ICON HOVER                | Calendar days left to enroll in a plan |

  @CP-37898 @CP-37898-01 @CP-37900-01 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @shruti
  Scenario: CP-37898 DC-EB View selected plan and submit enroll selection -CSR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37898-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37898-01.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 25                    |
      | eligibilityRecordId          | 25                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate-DC           |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | DCHF                  |
      | categoryCode                 | 10                    |
      | coverageCode                 | 130                   |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "37898-01.firstName",Last Name as "37898-01.lastName" in name
    And I click on the crm case id
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "ENROLL" Button for a consumer first name "37898-01.firstName" and last name "37898-01.lastName"
    And Wait for 3 seconds
    And I select "AMERIHEALTH CARITAS DC" from Available Plans
    Then I verify "Phone" is the displayed Channel Dropdown option
    And I click Channel Dropdown button to display options
    And I verify no other channels are displayed in channel dropdown
    Then I will click submit button
    And I verify program & benefit info page for consumer first name "37898-01.firstName" and last name "37898-01.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made              |
      | FUTURE ENROLLMENT.START DATE       | cutOffDateDCHFUIver         |
      | FUTURE ENROLLMENT.END DATE         | highDateUIver-DC            |
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed                   |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | displayed                   |
      | FUTURE ENROLLMENT.CHANNEL          | Phone                       |


  @CP-37898 @CP-37898-02 @CP-37900-02 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @shruti
  Scenario: CP-37898 DC-EB View selected plan and submit enroll selection for Supervisor
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37898-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37898-02.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 25                    |
      | eligibilityRecordId          | 25                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate-DC           |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | DCHF                  |
      | categoryCode                 | 10                    |
      | coverageCode                 | 130                   |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 2" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "37898-02.firstName",Last Name as "37898-02.lastName" in name
    And I click on the crm case id
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "ENROLL" Button for a consumer first name "37898-02.firstName" and last name "37898-02.lastName"
    And I select "AMERIHEALTH CARITAS DC" from Available Plans
    And I click Channel Dropdown button to display options
    Then I verify "Phone" is the displayed Channel Dropdown option
    Then I will click submit button
    And I verify program & benefit info page for consumer first name "37898-02.firstName" and last name "37898-02.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made              |
      | FUTURE ENROLLMENT.START DATE       | cutOffDateDCHFUIver         |
      | FUTURE ENROLLMENT.END DATE         | highDateUIver-DC            |
      | FUTURE ENROLLMENT.CHANNEL          | Phone                       |
      | FUTURE ENROLLMENT.PLAN NAME        | AMERIHEALTH CARITAS DC      |

  @CP-37981 @CP-37981-01 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @priyal
  Scenario: Verify Edit button is displaying and Click the Edit Button, Select Plan Using Existing Logic to Identify Available Plans, Submit Edited Plan Change Selection for Supervisor Role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 2" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi" with data
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made |
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed      |
    And I click "EDIT" Button for a consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi"
    Then I will be navigated to the “Edit Enrollment” screen
    And I will see the consumer details, the provider panel, and the plan details panel
    Then I will see the “Remove Plan” button
    When I click on Remove Plan Option
    Then I verify “Select” button grayed out and not available for consumer current enrollment
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify I see the consumers with the same "ENROLLMENT TIMEFRAME" in the list
    Then I verify program & benefit info page for consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi" with data
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed      |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | displayed      |

  @CP-37981 @CP-37981-02 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @priyal
  Scenario: Edit One Consumer’s Plan Change Selection at a time for Supervisor Role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId            | 37981-02.consumerId |
      | isEligibilityRequired | yes                 |
      | otherSegments         |   [blank]     |
      | isEnrollemntRequired  | no                  |
      | recordId              | 25                  |
      | eligibilityRecordId   | 25                  |
      | enrollmentStartDate  | 1stDayofLastMonth   |
      | eligibilityStartDate  | 1stDayofLastMonth   |
      | eligibilityEndDate    | highDate-DC         |
      | txnStatus             | Accepted            |
      | programCode           | MEDICAID            |
      | subProgramTypeCode    | DCHF                |
      | coverageCode          | 130                 |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-03|
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-03.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 2" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "37981-03.firstName" and Last Name as "37981-03.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    Then I verify “+ Add Case Member(s)” button is grayed out and not available
    Then verify I will only be able to “Edit” plan selection for one consumer at a time

  @CP-37981 @CP-37981-03 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @priyal
  Scenario: Cancel out of the Edit process for Supervisor role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-12 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-12.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 2" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I navigate away by clicking on back arrow on Enrollment Update Page
    And Wait for 3 seconds
    When I see warning message pop up with an option to cancel or continue
    And I will click on cancel button in the warning message
    Then I will see the consumer details, the provider panel, and the plan details panel
    And I navigate away by clicking on back arrow on Enrollment Update Page
    And I will click on continue button
    Then verify I will be navigated to the “Program & Benefits Info” screen with no change to the consumer’s enrollment

  @CP-37981 @CP-37981-04 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @priyal
  Scenario: Indicate Only the Latest Selection for Outbound Basket for Supervisor role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-04.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 2" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    And I click "EDIT" Button for a consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi"
    When I click on Remove Plan Option
    And I select A second plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And I initiated Enrollment status Api
    Then I verify consumer and status selection made are displayed on the Outbound basket

  @CP-37981 @CP-37981-05 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @priyal
  Scenario: Only Edit Plan for supervisor role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-05 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-05.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 2" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    And I click "EDIT" Button for a consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi"
    When I click on Remove Plan Option
    And I select A plan from Available Plans
    Then I verify Submit button activated
    And I change start date to 13 on calendar
    #And I Verify Reason Field Is not Required Or Not Displaying
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And I click "EDIT" Button for a consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi"
    #Then I Verify Reason Field Is not Required Or Not Displaying


  @CP-39438 @CP-39435 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @kursat
    #updated the population types to DCHF because Enroll button not displayed for Alliance
  Scenario: CP-39438 DC EB Decide Consumer Action Window DCHF Adult and DCHF Aged
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39438-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39438-01.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | eligibilityRecordId          | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | coverageCode                 | 130                  |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39438-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39438-02.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | eligibilityRecordId          | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | categoryCode                 | 10                   |
      | coverageCode                 | 710Y                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "39438-01.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation | DCHF-Adult   |
    Then I Verify Consumer Actions as following data
      | [0].action         | Required |
      | [0].consumerAction | Enroll   |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT |
      | consumerId    | 39438-01.consumerId    |
      | correlationId | null                   |
      | consumerFound | true                   |
      | DPBI          |[blank]|
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "39438-02.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation | DCHF-Aged  |
    Then I Verify Consumer Actions as following data
      | [0].action         | Required |
      | [0].consumerAction | Enroll   |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT |
      | consumerId    | 39438-02.consumerId    |
      | correlationId | null                   |
      | consumerFound | true                   |
      | DPBI          |[blank]|
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "39438-01.firstName" and Last Name as "39438-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    And I minimize active contact gadget popup if populates
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify "PRIOR ELIGIBILITY CARROT" is not displayed
    And I verify "PRIOR ENROLLMENT CARROT" is not displayed
    Then I verify program & benefit info page for consumer first name "39438-01.firstName" and last name "39438-01.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                 |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | currentUIver - 29DaysFromTodayUIver    |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 28DaysFromTodayUIver                   |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan |
      | CALENDAR ICON HOVER.RED DOT             | displayed                              |
      # If user enrolls with Service Mars User 1 which has Supervisor role then Enroll Button should be visible
      | CURRENT ELIGIBILITY.ENROLL BUTTON       | hidden                                 |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver              |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver-DC                       |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | DCHF-Adult                             |
      | CURRENT ELIGIBILITY.SERVICE REGION      | Northeast                              |
    Then I verify program & benefit info page for consumer first name "39438-02.firstName" and last name "39438-02.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                 |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | currentUIver - 29DaysFromTodayUIver    |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 28DaysFromTodayUIver                   |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan |
      | CALENDAR ICON HOVER.RED DOT             | displayed                              |
      # If user enrolls with Service Mars User 1 which has Supervisor role then Enroll Button should be visible
      | CURRENT ELIGIBILITY.ENROLL BUTTON       | hidden                                 |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver              |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver-DC                       |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | DCHF-Aged                              |
      | CURRENT ELIGIBILITY.SERVICE REGION      | Northeast                              |

  @CP-47139 @CP-47140 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @thilak
  Scenario: DC EB Create or Update for eligibility and enrollment - consumer not on monthly file
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47139-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47139-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |  [blank]            |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the skeletal enrollment and eligibility information to create enrollment
      | consumerId               | 47139-01.consumerId |
      | recordId                 | 25                  |
      | eligibilityRecordId      | 25                  |
      | coreEligibilityStartDate | null                |
      | coreEligibilityEndDate   | null                |
      | genericFieldText2        | 1                   |
      | requestedBy              | 597                 |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "47139-01.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | genericFieldText2     | 1             |
    And I initiated get benefit status by consumer id "47139-01.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | benefitStatus | Not Eligible |
    Then I will verify business events are generated with data
      | eventName  | DEFAULT_ELIGIBILITY |
      | consumerId | 47139-01.consumerId |
    And I initiated get benefit status by consumer id "47139-01.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | null       |


  @CP-38987 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @kursat
  Scenario: DC-EB Enrollment: MMIS Sends Current and Prior Medicaid IDs
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 38987-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | 38987-01event1      |
      | consumerId                   | 38987-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |  [blank]                   |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | DEFAULT_ELIGIBILITY                                            |
      | consumerId    | 38987-01.consumerId                                            |
      | correlationId | 38987-01event1.eligibilities.[0].coreEligibility.correlationId |
      | channel       | SYSTEM_INTEGRATION                                             |
      | createdBy     | 597                                                            |
      | processDate   | current                                                        |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the skeletal enrollment and eligibility information to create enrollment
#      | saveEligibilityEventName     | 38987-01event2      |
      | consumerId               | 38987-01.consumerId |
      | recordId                 | 25                  |
      | eligibilityRecordId      | 25                  |
      | coreEligibilityStartDate | null                |
      | coreEligibilityEndDate   | null                |
      | genericFieldText2        | 1                   |
      | requestedBy              | 597                 |
#    And I run create Eligibility and Enrollment API
    Then I send API call with name "38987-01a" for create Eligibility and Enrollment
    And Wait for 3 seconds
    And I initiated get benefit status by consumer id "38987-01.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | DCHF-Adult          |
      | population          | DCHF-Adult          |
      | benefitStatus       | Not Eligible        |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | null        |
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | DEFAULT_ELIGIBILITY |
      | consumerId    | 38987-01.consumerId |
#      | correlationId | 38987-01a.traceid        |
      | correlationId | null                |
      | consumerFound | true                |
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "38987-01.firstName" and Last Name as "38987-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    And I minimize active contact gadget popup if populates
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "38987-01.firstName" and last name "38987-01.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT                 | -- --                      |
      | CALENDAR ICON HOVER.IMPORTANT DATES             | -- --                      |
      | COUNTDOWN.NUMBER OF DAYS UNTIL                  | -- --                      |
      | COUNTDOWN.ICON HOVER                            | -- --                      |
      | INDICATOR.CHECK CORE                            | is displayed               |
      | MMIS.CHECK CORE                                 | Check MMIS                 |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION         | DCHF-Adult                 |
      | CURRENT ELIGIBILITY.COVERAGE CODE & DESCRIPTION | 130 - TANF Medicaid; Adult |
      | CURRENT ELIGIBILITY.SERVICE REGION              | Northeast                  |
      | CURRENT ELIGIBILITY.START DATE                  | 1stDayofLastMonthUIver     |
      | CURRENT ELIGIBILITY.END DATE                    | highDateUIver-DC           |
      | CURRENT ELIGIBILITY.ENROLL BUTTON               | hidden                     |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON           | hidden                     |

  @CP-47814 @CP-47814-01 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @deepa
  Scenario: DC EB View Check MMIS Indicator on P&BI Screen
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47814-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47814-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | [blank]                   |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the skeletal enrollment and eligibility information to create enrollment
      | consumerId               | 47814-01.consumerId |
      | recordId                 | 25                  |
      | eligibilityRecordId      | 25                  |
      | coreEligibilityStartDate | null                |
      | coreEligibilityEndDate   | null                |
      | genericFieldText2        | 1                   |
      | requestedBy              | 597                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<consumerId>.firstName",Last Name as "<consumerId>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I will see a red stop indicator on the consumer row
    Then verify Hex color is red "#EF5350" icon mmis stop indicator
    When I hover my mouse over the red icon in the program and benifit page
    Then I will see the following "Check MMIS" text on mmis indicator
