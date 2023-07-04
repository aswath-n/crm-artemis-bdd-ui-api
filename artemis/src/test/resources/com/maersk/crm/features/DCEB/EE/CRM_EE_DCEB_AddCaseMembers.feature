Feature: Add Case Members DCHF-DCEB

  @CP-37899 @CP-37899-01  @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @kursat
  Scenario: CP-37899 DC EB Add case members, view plans available, and select plan
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37899-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37899-01.consumerId |
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
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37899-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37899-02.consumerId |
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
      | coverageCode                 | 271                 |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37899-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37899-03.consumerId |
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
      | coverageCode                 | 710Y                |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37899-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37899-04.consumerId |
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
      | coverageCode                 | 741Y                |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37899-05 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37899-05.consumerId |
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
      | coverageCode                 | 222                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "DC-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "37899-01.firstName" and Last Name as "37899-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "37899-01.firstName" and last name "37899-01.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                 |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | currentUIver - 29DaysFromTodayUIver    |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 28DaysFromTodayUIver                   |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | DCHF-Adult                             |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofLastMonthUIver                 |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver-DC                       |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden                                 |
    Then I verify program & benefit info page for consumer first name "37899-02.firstName" and last name "37899-02.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                 |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | currentUIver - 29DaysFromTodayUIver    |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 28DaysFromTodayUIver                   |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | DCHF-Adult                             |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofLastMonthUIver                 |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver-DC                       |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden                                 |
    Then I verify program & benefit info page for consumer first name "37899-03.firstName" and last name "37899-03.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                 |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | currentUIver - 29DaysFromTodayUIver    |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 28DaysFromTodayUIver                   |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | DCHF-Aged                              |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofLastMonthUIver                 |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver-DC                       |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden                                 |
    Then I verify program & benefit info page for consumer first name "37899-04.firstName" and last name "37899-04.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                 |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | currentUIver - 29DaysFromTodayUIver    |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 28DaysFromTodayUIver                   |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | DCHF-Blind                             |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofLastMonthUIver                 |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver-DC                       |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden                                 |
    Then I verify program & benefit info page for consumer first name "37899-05.firstName" and last name "37899-05.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL                                 |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | currentUIver - 29DaysFromTodayUIver    |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 28DaysFromTodayUIver                   |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | DCHF-Child                             |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofLastMonthUIver                 |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver-DC                       |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON   | hidden                                 |
    And I click "ENROLL" Button for a consumer first name "37899-01.firstName" and last name "37899-01.lastName"
    Then I verify I am still on "Enrollment Update" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 37899-02 |
      | 37899-03 |
      | 37899-04 |
      | 37899-05 |
      | 37899-06 |
    When I Add Case Members Button and Select all case members
    Then I verify  the Add Case Members button is disable
    When I click Remove Button for Case Member First Name as "37899-05.firstName" and Last Name as "37899-05.lastName"
    Then I verivy consumer by First Name as "37899-05.firstName" and Last Name as "37899-05.lastName" is not in plan selection
    When I click Remove Button for Case Member First Name as "37899-02.firstName" and Last Name as "37899-02.lastName"
    Then I verivy consumer by First Name as "37899-02.firstName" and Last Name as "37899-02.lastName" is not in plan selection
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 37899-02 |
      | 37899-05 |
    When I click Add Case Members with First Name as "37899-05.firstName" and Last Name as "37899-05.lastName"
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "37899-02.firstName" and Last Name as "37899-02.lastName"
    Then I verify  the Add Case Members button is disable

  @CP-37981 @CP-37981-06 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario: Verify Edit button is displaying and Click the Edit Button, Select Plan Using Existing Logic to Identify Available Plans, Submit Edited Plan Change Selection for CSR Role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-06 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-06.consumerId |
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
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
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
    Then I verify program & benefit info page for consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi" with data
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed      |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | displayed      |
    Then I verify I see the consumers with the same "ENROLLMENT TIMEFRAME" in the list

  @CP-37981 @CP-37981-07 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario: Edit One Consumer’s Plan Change Selection at a time for CSR Role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-07 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-07.consumerId |
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
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-08|
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-08.consumerId |
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
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "37981-07.firstName",Last Name as "37981-07.lastName" in name
    And I click on the crm case id
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    Then I verify “+ Add Case Member(s)” button is grayed out and not available
    Then verify I will only be able to “Edit” plan selection for one consumer at a time

  @CP-37981 @CP-37981-08 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario: Cancel out of the Edit process for CSR Role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-11 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-11.consumerId |
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
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I navigate away by clicking on back arrow on Enrollment Update Page
    When I see warning message pop up with an option to cancel or continue
    And I will click on cancel button in the warning message
    Then I will see the consumer details, the provider panel, and the plan details panel
    And I navigate away by clicking on back arrow on Enrollment Update Page
    And I will click on continue button
    Then verify I will be navigated to the “Program & Benefits Info” screen with no change to the consumer’s enrollment

  @CP-37981 @CP-37981-09 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario: Indicate Only the Latest Selection for Outbound Basket for CSR Role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-09 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-09.consumerId |
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
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
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

  @CP-37981 @CP-37981-10 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario: Only Edit Plan for CSR Role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 37981-10 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37981-10.consumerId |
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
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
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
    Then I will not be able to change the enrollment Start dates
    #And I Verify Reason Field Is not Required Or Not Displaying
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And I click "EDIT" Button for a consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi"
    #Then I Verify Reason Field Is not Required Or Not Displaying

  @CP-42396 @CP-42396-01 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario Outline: Display Coverage Code and Coverage Description - UI
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <consumerId> |
      | newCaseCreation  | yes          |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 25                      |
      | eligibilityRecordId          | 25                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofLastMonth       |
      | eligibilityStartDate         | 1stDayofLastMonth       |
      | eligibilityEndDate           | highDate-DC             |
      | txnStatus                    | Accepted                |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | DCHF                    |
      | coverageCode                 | <coverageCode>          |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<consumerId>.firstName",Last Name as "<consumerId>..lastName" in name
    And I click on the crm case id
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "<consumerId>.firstName" and last name "<consumerId>.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION         | <consumerPopulation>         |
      | CURRENT ELIGIBILITY.COVERAGE CODE & DESCRIPTION | <coverageCode & discription> |
      | CURRENT ELIGIBILITY.SERVICE REGION              | Northeast                    |
      | CURRENT ELIGIBILITY.START DATE                  | 1stDayofLastMonthUIver       |
      | CURRENT ELIGIBILITY.END DATE                    | highDateUIver-DC             |
    Examples:
      | coverageCode | coverageCode & discription                        | consumerPopulation  | consumerId |
      | 130          | 130 - TANF Medicaid; Adult                        | DCHF-Adult          | 42396-01   |
      | 910Y         | 910Y - Medically Needy - Aged                     | DCHF-Aged           | 42396-02   |
      | 741Y         | 741Y - MRT Blind                                  | DCHF-Blind          | 42396-03   |
      | 161N         | 161N - Alternate Svcs Psychiatric - N             | DCHF-Child          | 42396-04   |
      | 930          | 930 - Medically Needy MA TANF Income < SSI; Adult | DCHF-Disabled       | 42396-05   |
      | 242          | 242 - Pregnant Women (Title 21)                   | DCHF-Pregnant Women | 42396-06   |
      | 110Y         | 110Y - Aged SSI                                   | DCHF-SSI            | 42396-07   |

  @CP-47200 @CP-47200-01  @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @thilak
  Scenario: DC EB Display Enroll Button displayed on the Program and Benefit Screen when on or within the enroll window
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47200-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47200-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | [blank]             |
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
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "47200-01.firstName",Last Name as "47200-01.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "ENROLL" button is displayed


  @CP-47200 @CP-47200-02  @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @thilak
  Scenario Outline: DC EB Display Enroll Button not displayed Program and Benefit Screen  when not within the enroll window
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47200-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47200-02.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | [blank]             |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | <subProgramType>    |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "47200-02.firstName",Last Name as "47200-02.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "ENROLL BUTTON" is not displayed
    Examples:
      | subProgramType    |
      | Alliance          |
      | ImmigrantChildren |

  @CP-47200 @CP-47200-03 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @thilak
  Scenario Outline: Plan change button is displayed for past enrollment start date, prelockin and anniversary window
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                | [blank]                |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofLastMonth      |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | highDate-DC            |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | coverageCode                 | <coverageCode>         |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<name>" for update Enrollment information
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 145                  |
      | [0].planCode           | <planCode>           |
      | [0].startDate          | <startDate>          |
      | [0].endDate            | highdatedc::         |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Northeast            |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<name>" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | <startDate>            |
      | [0].planId       | delete::               |
      | [0].planCode     | <planCode>             |
      | [0].consumerId   | <name>.consumerId      |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | <planStartDate>      |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         | [blank]              |
      | programCode                  | MEDICAID             |
      | planCode                     | <planCode>           |
      | planId                       | 145                  |
      | subProgramTypeCode           | <subProgramTypeCode> |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<name>.firstName",Last Name as "<name>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "PLAN CHANGE" button is displayed
    And I click "PLAN CHANGE" Button for a consumer first name "<name>.firstName" and last name "<name>.lastName"
    And I select "MEDSTAR FAMILY CHOICE" from Available Plans
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | <reason> |
    Examples:
      | name     | eligibilityStartDate   | subProgramTypeCode | coverageCode | startDate                | planCode  | planStartDate          | reason                          |
      | 47200-01 | 1stDayofLastMonth      | ImmigrantChildren  | 420          | fdofmnth::               | 081080400 | 1stDayofPresentMonth   | 90 day transfer                 |
      | 47200-02 | 1stDayof12MonthsBefore | ImmigrantChildren  | 420          | 1stdayof12monthsbefore:: | 081080400 | 1stDayof12MonthsBefore | Annual right to change transfer |


  @CP-47200 @CP-47200-04 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @thilak
  Scenario Outline: Plan change button not displayed for future enrollment start date
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                | [blank]             |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofNextMonth      |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | highDate-DC            |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | coverageCode                 | <coverageCode>         |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<name>a" for update Enrollment information
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 145                  |
      | [0].planCode           | <planCode>           |
      | [0].startDate          | <startDate>          |
      | [0].endDate            | highdatedc::         |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Northeast            |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<name>a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | <startDate>            |
      | [0].planId       | delete::               |
      | [0].planCode     | <planCode>             |
      | [0].consumerId   | <name>.consumerId      |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | <planStartDate>      |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         | [blank]              |
      | programCode                  | MEDICAID             |
      | planCode                     | <planCode>           |
      | planId                       | 145                  |
      | subProgramTypeCode           | <subProgramTypeCode> |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<name>.firstName",Last Name as "<name>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "PLAN CHANGE BUTTON" is not displayed
    Examples:
      | name     | eligibilityStartDate | subProgramTypeCode | coverageCode | startDate  | planCode  | planStartDate     |
      | 47200-04 | 1stDayofNextMonth    | Alliance           | 460          | fdofmnth:: | 081080400 | 1stDayofNextMonth |

  @CP-47200 @CP-47200-05  @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @thilak
  Scenario:@CP-47200 DC EB Add case members button displayed only for multiple consumers with same elgibilty end date
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47200-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47200-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | [blank]             |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | enrollmentEndDate            | lastDayofPresentMonth|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | lastDayofPresentMonth|
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47200-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47200-02.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | [blank]             |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | enrollmentEndDate            | highDate-DC          |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 271                 |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47200-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47200-03.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | [blank]             |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | enrollmentEndDate            | lastDayofNextMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | lastDayofNextMonth  |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 710Y                |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47200-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47200-04.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | [blank]             |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | enrollmentEndDate            | lastDayofNextMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | lastDayofNextMonth  |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 741Y                |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47200-05 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47200-05.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | [blank]             |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | enrollmentEndDate            | lastDayofNextMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | lastDayofNextMonth  |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 222                 |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "47200-01.firstName",Last Name as "47200-01.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "ENROLL" Button for a consumer first name "47200-01.firstName" and last name "47200-01.lastName"
    Then I verify "ADD CASE MEMBERS BUTTON" is not displayed
    And I navigate away by clicking on back arrow on Enrollment Update Page
    And I click "ENROLL" Button for a consumer first name "47200-02.firstName" and last name "47200-02.lastName"
    Then I verify "ADD CASE MEMBERS BUTTON" is not displayed
    And I navigate away by clicking on back arrow on Enrollment Update Page
    And I click "ENROLL" Button for a consumer first name "47200-03.firstName" and last name "47200-03.lastName"
    Then I verify "ADD CASE MEMBERS" button is displayed
    And I navigate away by clicking on back arrow on Enrollment Update Page
    And I click "ENROLL" Button for a consumer first name "47200-05.firstName" and last name "47200-05.lastName"
    Then I verify "ADD CASE MEMBERS" button is displayed

  @CP-47200 @CP-47200-06 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @thilak
  Scenario Outline: CP-47200 DC-EB verify the channel dropdown with multiple role
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47200-06 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47200-06.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                | [blank]                    |
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
    Given I logged into CRM with specific role "Service Account 1" and select a project "DC-EB" and select a role "<role>"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "47200-06.firstName",Last Name as "47200-06.lastName" in name
    And Wait for 3 seconds
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And Wait for 3 seconds
    And I select "AMERIHEALTH CARITAS DC" from Available Plans
    Then I verify "<Defaultchannel>" is the displayed Channel Dropdown option
    And I click Channel Dropdown button to display options
    Then I validate "<channel1>" from Channel Dropdown not displayed
    Then I validate "<channel2>" from Channel Dropdown not displayed
    Then I will see the “Remove Plan” button
    When I click on Remove Plan Option
    Examples:
      | role                | channel1        | channel2   | Defaultchannel  |
      | Outreach Specialist | Mail            | Web Portal | Community Event |
      | Mailroom Specialist | Community Event | Web Portal | Mail            |
      | Csr                 | Mail            | Web Portal | Phone           |







