Feature: UI Override New Enrollment Feature for Baseline


  @CP-25949 @CP-30844 @ui-ee @crm-regression @kursat
  Scenario: Baseline Override New Enrollment for all Programs, Subprograms, and Populations
  Baseline Override New Enrollment for all Programs, Subprograms, and Populations - Business Events
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 25949-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25949-1.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "25949-1.consumerId" with data
      | [0].action               | Available          |
      | [0].consumerAction       | Enroll             |
      | [0].planSelectionAllowed | true               |
      | [0].changeAllowedFrom    | 1stDayofLastMonth  |
      | [0].changeAllowedUntil   | lastDayofLastMonth |
    Given I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 25949-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25949-2.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "25949-2.consumerId" with data
      | [0].action               | Available          |
      | [0].consumerAction       | Enroll             |
      | [0].planSelectionAllowed | true               |
      | [0].changeAllowedFrom    | 1stDayofLastMonth  |
      | [0].changeAllowedUntil   | lastDayofLastMonth |
    Given I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 25949-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25949-3.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "25949-3.consumerId" with data
      | [0].action               | Available          |
      | [0].consumerAction       | Enroll             |
      | [0].planSelectionAllowed | true               |
      | [0].changeAllowedFrom    | 1stDayofLastMonth  |
      | [0].changeAllowedUntil   | lastDayofLastMonth |
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 25949-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25949-4.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "25949-4.consumerId" with data
      | [0].action               | Available          |
      | [0].consumerAction       | Enroll             |
      | [0].planSelectionAllowed | true               |
      | [0].changeAllowedFrom    | 1stDayofLastMonth  |
      | [0].changeAllowedUntil   | lastDayofLastMonth |
    Given I logged into CRM with "Service Tester 1" and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "25949-1.firstName" and Last Name as "25949-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # CP-25949 1.0 Display ENROLL Button for Supervisor After the NEW ENROLLMENT Change Window HAS Expired OR is Unavailable
    # CP-25949 2.0  Display Important Calendar Dates
    Then I verify program & benefit info page for consumer first name "25949-1.firstName" and last name "25949-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT       | ENROLL                                        |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | firstdayofLastMonth - lastDayofLastMonthUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | lastDayofLastMonthUIver                       |
      | COUNTDOWN.ICON HOVER                  | Calendar days left to enroll in a plan        |
      | CURRENT ELIGIBILITY.PCP SELECT BUTTON | is displayed                                  |
      | CURRENT ELIGIBILITY.EDIT BUTTON       | is displayed                                  |
    And I click "ENROLL" Button for a consumer first name "25949-1.firstName" and last name "25949-1.lastName"
    When I click Add Case Members Button
    # CP-25949 3.0 Adding a Case Member to a Consumer New Enrollment Action (everyone WITHOUT a plan)
    Then I verify I only see next other consumers in the dropdown list
      | 25949-4 |
    When I click Add Case Members with First Name as "25949-4.firstName" and Last Name as "25949-4.lastName"
    Then I verify  the Add Case Members button is disable
    When I click Remove Button for Case Member First Name as "25949-4.firstName" and Last Name as "25949-4.lastName"
    Then I verivy consumer by First Name as "25949-4.firstName" and Last Name as "25949-4.lastName" is not in plan selection
    # CP-25949 4.0 Identify Available Plans for Single and Multiple Consumers
    Then I verify list of all available plans on Enrollment Update Page with data
      | AMERIGROUP COMMUNITY CARE |
      | CARESOURCE GEORGIA        |
      | PEACH STATE               |
      | WELLCARE                  |
    # CP-25949 5.0 Calculate Plan Start Date and Plan End Date for Consumer Enrollment Actions
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | firstDayOfNextYearUIver |
      | END DATE   | -- --                   |
    # CP-25949 6.0 Select Plan
    And I will see “Select” button is available for each plan
    When I select A plan from Available Plans
    # CP-25949 7.0 Remove Plan
    Then I verify "Remove Plan" button is displayed
    And I click submit button on enrollment update
    # CP-30844 1.0 Create NEW ENROLLMENT OVERRIDE Business Event
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I save first contactId with name "25949-1a"
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I will verify business events are generated with data
      # Required Fields
      | eventName           | NEW_ENROLLMENT_OVERRIDE          |
      | channel             | PHONE                            |
#      | createdBy           | 185                              |
      | processDate         | current                          |
      | externalCaseId      | 25949-1.caseIdentificationNumber |
      | externalConsumerId  | 25949-1.externalConsumerId       |
      | consumerId          | 25949-1.consumerId               |
      | consumerName        | 25949-1                          |
      # Optional Fields
      | contactId           | 25949-1a.contactId               |
      | enrollmentStartDate | 1stDayofNextMonth                |
      | enrollmentEndDate   | null                             |
      | planSelectionReason | null                             |
      | planChangeReason    | null                             |
      | rejectionReason     | null                             |
      | planCode            | getFromUISelected                |
      | enrollmentType      | MEDICAL                          |
      | selectionStatus     | SELECTION_MADE                   |
      | anniversaryDate     | null                             |
      | pcpNpi              | null                             |
      | pcpName             | null                             |
    # CP-25949 8.0 Display EDIT Button
    Then I verify program & benefit info page for consumer first name "25949-1.firstName" and last name "25949-1.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population |
      | CURRENT ELIGIBILITY.SERVICE REGION      | East                        |
      | CURRENT ELIGIBILITY.START DATE          | firstdayofLastMonth         |
      | CURRENT ELIGIBILITY.END DATE            | -- --                       |
      | CURRENT ELIGIBILITY.ENROLL BUTTON       | hidden                      |
      | FUTURE ENROLLMENT.PLAN NAME             | selectedPlanName            |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Selection Made              |
      | FUTURE ENROLLMENT.CHANNEL               | Phone                       |
      | FUTURE ENROLLMENT.START DATE            | 1stDayofNextMonthUIver      |
      | FUTURE ENROLLMENT.END DATE              | -- --                       |
      | FUTURE ENROLLMENT.EDIT BUTTON           | displayed                   |
      | FUTURE ENROLLMENT.DISREGARD BUTTON      | displayed                   |
    And I click "EDIT" Button for a consumer first name "25949-1.firstName" and last name "25949-1.lastName"
    And I click on Remove Plan Option
    When I select A plan from Available Plans
    # CP-25949 9.0 EDIT Enrollment Start Date
    And I change start date to 3 on calendar
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # CP-25949 10.0 SAVE and VIEW new PLAN and/or edited ENROLLMENT START DATE
    Then I verify program & benefit info page for consumer first name "25949-1.firstName" and last name "25949-1.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made         |
      | FUTURE ENROLLMENT.START DATE       | 3rdDayofNextMonthUIver |
      | FUTURE ENROLLMENT.END DATE         | -- --                  |
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed              |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | displayed              |
    And I initiated get enrollment by consumer id "25949-1.consumerId"
    When I run get enrollment api
    Then I verify latest enrollment by consumer id "25949-1.consumerId" with data
      | medicalPlanEndDate   | null                  |
      | planEndDateReason    | null                  |
#      | updatedBy            | 185                   |
      | updatedOn            | current               |
      | txnStatus            | SELECTION_MADE        |
      | medicalPlanBeginDate | 3rdDayofNextMonth     |
      | enrollmentType       | MEDICAL               |
      | selectionReason      | CONSUMER_CHANGED_MIND |
      | channel              | PHONE                 |
    # CP-30844 2.0 Create NEW ENROLLMENT EDIT OVERRIDE Business Event: IN-EB (When EDIT functionality is invoked)
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I save first contactId with name "25949-1a"
    Then I will verify business events are generated with data
      # Required Fields
      | eventName           | NEW_ENROLLMENT_EDIT_OVERRIDE     |
      | channel             | PHONE                            |
#      | createdBy           | 185                              |
      | processDate         | current                          |
      | externalCaseId      | 25949-1.caseIdentificationNumber |
      | externalConsumerId  | 25949-1.externalConsumerId       |
      | consumerId          | 25949-1.consumerId               |
      | consumerName        | 25949-1                          |
      # Optional Fields
      | contactId           | 25949-1a.contactId               |
      | enrollmentStartDate | 3rdDayofNextMonth                |
      | enrollmentEndDate   | null                             |
      | planSelectionReason | CONSUMER_CHANGED_MIND            |
      | planChangeReason    | null                             |
      | rejectionReason     | null                             |
      | planCode            | getFromUISelected                |
      | enrollmentType      | MEDICAL                          |
      | selectionStatus     | SELECTION_MADE                   |
      | anniversaryDate     | null                             |
      | pcpNpi              | null                             |
      | pcpName             | null                             |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 25949-1.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|






















