Feature: Disregard Consumer Initiated Disenroll Request

  @CP-15052 @CP-15052-01  @ui-ee @crm-regression @alex
  Scenario: Plan Change initiated from Enrollment segment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | planId                       | 1880              |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planCode                     | 84                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planId                       | 1880              |
      | planCode                     | 85                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Disenroll" Button for a consumer
    And I select a reason from drop down on Program and Benefit info page
    And I change effective date to 2 month later to day 15 on calendar
    And I click "Disenroll submit" Button for a consumer
    Then I verify current enrollment segment is in “DISENROLL REQUESTED” status
    Then I will see an option to DISREGARD the action taken on the disenroll segment

  @CP-15052 @CP-15052-02  @ui-ee @crm-regression @alex
  Scenario: DISREGARD One Consumer at a Time
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | planId                       | 1880              |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planCode                     | 84                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planId                       | 1880              |
      | planCode                     | 85                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "FOSTER-CARE"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | planId                       | 1880              |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planCode                     | 84                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planId                       | 1880              |
      | planCode                     | 85                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Disenroll" Button for a consumer
    And I select a reason from drop down on Program and Benefit info page
    And I change effective date to 2 month later to day 15 on calendar
    And I click "Disenroll submit" Button for a consumer
    Then verify I will only be able to “Disregard” plan selection for one consumer at a time

  @CP-15052 @CP-15052-03 @Cp-15048-01 @ui-ee @crm-regression @alex
  Scenario: DISREGARD the Consumer’s Initiated Disenroll Request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | planId                       | 1880              |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planCode                     | 84                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planId                       | 1880              |
      | planCode                     | 85                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Disenroll" Button for a consumer
    And I select a reason from drop down on Program and Benefit info page
    And I change effective date to 2 month later to day 15 on calendar
    And I click "Disenroll submit" Button for a consumer
    And I click on Disregard button on program & benefits page
    And I click on continue for Disregard Enrollment
    And New enrollment selection is not displayed on the program & benefits info screen
    Then I will not see an option to DISREGARD on the disenroll segment

  @CP-15052 @CP-15052-04  @ui-ee @crm-regression @alex
  Scenario: Set Disenroll Action back to Available
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE" with data
      | saveConsumerInfo | 15052-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15052-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | facilityInfo        |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | planId                       | 1880                |
      | programCode                  | F                   |
      | subProgramTypeCode           | FOSTERCARE          |
      | planCode                     | 84                  |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15052-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | F                   |
      | subProgramTypeCode           | FOSTERCARE          |
      | planId                       | 1880                |
      | planCode                     | 85                  |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "15052-01.firstName" and Last Name as "15052-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Disenroll" Button for a consumer
    And I select a reason from drop down on Program and Benefit info page
    And I change effective date to 2 month later to day 15 on calendar
    And I click "Disenroll submit" Button for a consumer
    And I click on Disregard button on program & benefits page
    And I click on continue for Disregard Enrollment
#    Then I verify the consumer actions are available
    Then I verify program & benefit info page for consumer first name "15052-01.firstName" and last name "15052-01.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT     | PRE-LOCKIN - WINDOW, ANNIVERSARY - WINDOW |
      | COUNTDOWN.ICON HOVER                | Calendar days left to change plan before lock-in |
    Then I verify Disenroll button is displayed on the enrollment segment Selection Status = Accepted


  @CP-15052 @CP-15052-05 @CP-15048-02  @ui-ee @crm-regression @Shruti
  Scenario: Create DISREGARD DISENROLL Request Business Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE" with data
      | saveConsumerInfo | 15052-05 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15052-05.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | facilityInfo        |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | planId                       | 1880                |
      | programCode                  | F                   |
      | subProgramTypeCode           | FOSTERCARE          |
      | planCode                     | 84                  |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15052-05.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | F                   |
      | subProgramTypeCode           | FOSTERCARE          |
      | planId                       | 1880                |
      | planCode                     | 85                  |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with Call Center Supervisor account and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Disenroll" Button for a consumer
    And I select a reason from drop down on Program and Benefit info page
    And I change effective date to 2 month later to day 15 on calendar
    And I click "Disenroll submit" Button for a consumer
    And I click on Disregard button on program & benefits page
    And I click on continue for Disregard Enrollment
    And Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | DISREGARDED_DISENROLL_REQUEST |
      | processDate   | current                       |
      | consumerName  | 15052-05                      |
      | correlationId | null                          |
      | createdBy     | 3631                          |
      | caseId        | 15052-05.caseId               |
      | consumerId    | 15052-05.consumerId           |

  # this scenario will be muted until defect CP-37055 is fixed
  @CP-14609 @CP-14609-01 @CP-14609-04 @ui-ee-removed @ee-UI2API-removed @crm-regression @sobir
  Scenario: Decide Enrollment Impacts & Save Changes Accepted Enrollments + Disenroll requests
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | requestedBy      | 142      |
      | saveConsumerInfo | 14609-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14609-01.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | requestedBy                  | 142                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14609-01.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
      | requestedBy                  | 142                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I change Physical Address by consumerId "EC1.consumerId" with data
      | addressStreet1 | 1225 lasalle |
      | addressStreet2 |[blank]|
      | addressCity    | Aragon       |
      | addressState   | CT           |
      | addressCounty  | Polk         |
      | addressZip     | 30104        |
      | updatedBy      | 142          |
    And Wait for 5 seconds
    Then I verify out of state address record for consumerId "14609-01.consumerId" is not empty by API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify current enrollment status is "DISENROLL REQUESTED"
    Then I verify "Plan Change Button" is not displayed
    Then  I verify "Edit Button" is not displayed
    Then  I verify "Disregard Button" is not displayed
      #  @API-CP-14609-04 and @API-CP-14611-04
    Then I will verify business events are generated with data
      | eventName           | DISENROLL_REQUESTED               |
      | channel             | SYSTEM_INTEGRATION                |
      | createdBy           | 142                               |
      | processDate         | current                           |
      | externalCaseId      | 14609-01.caseIdentificationNumber |
      | externalConsumerId  | 14609-01.externalConsumerId       |
      | consumerId          | 14609-01.consumerId               |
      | consumerName        | 14609-01                          |
      #  optional fields
      | linkedObjects       |[blank]|
      | enrollmentStartDate | 1stDayofPresentMonth              |
      | enrollmentEndDate   |[blank]|
      | enrollmentEndReason | null                              |
      | planCode            | 84                                |
      | enrollmentType      | MEDICAL                           |
      | selectionStatus     | ACCEPTED                          |
      | consumerPopulation  | MEDICAID-GENERAL                  |
      | benefitStatus       | Enrolled                          |

  # this scenario will be muted until defect CP-37055 is fixed
  @CP-14614 @CP-14614-03 @CP-14611 @CP-14611-01 @API-CP-14611-03 @ui-ee-removed @ee-UI2API-removed @crm-regression @sobir
  Scenario: Decide Enrollment Impacts & Save Changes In-flight New Enrollments + Disenroll requests 1.0 and 4.0
  1.0 Create Disenroll Request for New Enrollment not sent to MMIS
  3.0 Create Disregard Business Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | requestedBy      | 142      |
      | saveConsumerInfo | 14614-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14614-03.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | requestedBy                  | 142                  |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "14614-03.firstName" and Last Name as "14614-03.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A first plan from Available Plans
    Then I will click submit button
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And Wait for 5 seconds
    And I change Physical Address by consumerId "14614-03.consumerId" with data
      | addressStreet1 | 1225 lasalle |
      | addressStreet2 |[blank]|
      | addressCity    | Aragon       |
      | addressState   | CT           |
      | addressCounty  | Polk         |
      | addressZip     | 30104        |
      | updatedBy      | 142          |
    And Wait for 5 seconds
    Then I verify out of state address record for consumerId "14614-03.consumerId" is not empty by API
    And I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then  I verify "Plan Change Button" is not displayed
    Then  I verify "Edit Button" is not displayed
    Then  I verify "Disregard Button" is not displayed
    # @CP-14611-03 @CP-14614-03
    Then I will verify business events are generated with data
      | eventName           | DISREGARDED_NEW_ENROLLMENT         |
      | channel             | CP                                 |
#      | createdBy           | 142                                |
      | createdBy           | 3455                               |
      | processDate         | current                            |
      | externalCaseId      | 14614-03.caseIdentificationNumber  |
      | externalConsumerId  | 14614-03.externalConsumerId        |
      | consumerId          | 14614-03.consumerId                |
      | consumerName        | 14614-03                           |
      #  optional fields
      | linkedObjects       |[blank]|
      | enrollmentStartDate | 1stDayofNextMonth                  |
      | enrollmentEndDate   | lastDayofPresentMonth              |
      | planChangeReason    | Case Physical Address Out Of State |
      | planCode            | 84                                 |
      | enrollmentType      | MEDICAL                            |
      | selectionStatus     | DISREGARDED                        |
      | consumerPopulation  | MEDICAID-GENERAL                   |
      | benefitStatus       | Eligible                           |

  # this scenario will be muted until defect CP-37055 is fixed
  @CP-14611 @CP-14611-02 @ui-ee-removed @ee-UI2API-removed @crm-regression @sobir
  Scenario: Decide Enrollment Impacts & Save Changes In-flight New Enrollments + Disenroll requests 2.0 and 3.0
  2.0 Create Disenroll Request for New Enrollment sent to MMIS
  4.0 Create Disenroll Requested Business Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | requestedBy | 142 |
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
      | requestedBy                  | 142                  |
    Then I send API call with name "EB1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "EU" for update Enrollment information
      | [0].consumerId | EB1.consumerId     |
      | [0].planId     | 145                |
      | [0].planCode   | 84                 |
      | [0].startDate  | fdnxtmth::         |
      | [0].channel    | SYSTEM_INTEGRATION |
      | [0].createdBy  | 142                |
      | [0].updatedBy  | 142                |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].consumerId     | EB1.consumerId         |
      | [0].channel        | SYSTEM_INTEGRATION     |
      | [0].startDate      | fdnxtmth::             |
      | [0].planId         | delete::               |
      | [0].planCode       | 84                     |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | SUBMITTED_TO_STATE     |
      | [0].txnStatus      | SUBMITTED_TO_STATE     |
      | [0].enrollmentType | delete::               |
    And Wait for 5 seconds
    And I change Physical Address by consumerId "EB1.consumerId" with data
      | addressStreet1 | 1225 lasalle |
      | addressStreet2 |[blank]|
      | addressCity    | Aragon       |
      | addressState   | CT           |
      | addressCounty  | Polk         |
      | addressZip     | 30104        |
      | updatedBy      | 142          |
    And Wait for 5 seconds
    Then I verify out of state address record for consumerId "EB1.consumerId" is not empty by API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then  I verify "Plan Change Button" is not displayed
    Then  I verify "Edit Button" is not displayed
    Then  I verify "Disregard Button" is not displayed
    Then I will verify business events are generated with data
      | eventName     | DISENROLL_REQUESTED |
      | consumerId    | EB1.consumerId      |
      | consumerFound | false               |

  # this scenario will be muted until defect CP-37055 is fixed
  @CP-14611 @CP-14611-04 @CP-14614 @CP-14614-01 @CP-14614-03 @ui-ee-removed @ee-UI2API-removed @crm-regression @sobir
  Scenario: Decide Enrollment Impacts & Save Changes In-flight Plan Changes + Disenroll requests
  1.0 Create Disenroll Request for Plan Change not sent to MMIS
  3.0 Create Disregard Business Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 14611-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14611-04.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | requestedBy                  | 142                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14611-04.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
      | requestedBy                  | 142                  |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A second plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And Wait for 5 seconds
    And I change Physical Address by consumerId "14611-04.consumerId" with data
      | addressStreet1 | 1225 lasalle |
      | addressStreet2 |[blank]|
      | addressCity    | Aragon       |
      | addressState   | CT           |
      | addressCounty  | Polk         |
      | addressZip     | 30104        |
      | updatedBy      | 142          |
    And Wait for 5 seconds
    Then I verify out of state address record for consumerId "14611-04.consumerId" is not empty by API
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify current enrollment status is "DISENROLL REQUESTED"
    Then  I verify "Plan Change Button" is not displayed
    Then  I verify "Edit Button" is not displayed
    Then  I verify "Disregard Button" is not displayed
    # @CP-14611-04 @CP-14614-04
    Then I will verify business events are generated with data
      | eventName           | DISENROLL_REQUESTED                |
      | channel             | SYSTEM_INTEGRATION                 |
      | createdBy           | 142                                |
      | processDate         | current                            |
      | externalCaseId      | 14611-04.caseIdentificationNumber  |
      | externalConsumerId  | 14611-04.externalConsumerId        |
      | consumerId          | 14611-04.consumerId                |
      | consumerName        | 14611-04                           |
      #  optional fields
      | linkedObjects       |[blank]|
      | enrollmentStartDate | 1stDayofPresentMonth               |
      | enrollmentEndDate   | lastDayofPresentMonth              |
      | enrollmentEndReason | Case Physical Address Out Of State |
      | planCode            | 84                                 |
      | enrollmentType      | MEDICAL                            |
      | selectionStatus     | ACCEPTED                           |
      | consumerPopulation  | MEDICAID-GENERAL                   |
      | benefitStatus       | Enrolled                           |
    
  # this scenario will be muted until defect CP-37055 is fixed
  @CP-14614 @CP-14614-02 @CP-14614-04 @ui-ee-removed @ee-UI2API-removed @crm-regression @sobir
  Scenario: Decide Enrollment Impacts & Save Changes In-flight Plan Changes + Disenroll requests 2.0 and 3.0
  2.0 Create Disenroll Request for Plan Changes sent to MMIS
  4.0 Create Disenroll Requested Business Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | requestedBy | 142 |
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
      | requestedBy                  | 142                  |
    Then I send API call with name "EM1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | EM1.consumerId       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
      | requestedBy                  | 142                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update Enrollments
      | [0].consumerId        | EM1.consumerId      |
      | [0].channel           | SYSTEM_INTEGRATION  |
      | [0].planId            | 145                 |
      | [0].planCode          | 84                  |
      | [0].txnStatus         | DISENROLL_REQUESTED |
      | [0].startDate         | fdofmnth::          |
      | [0].endDate           | lstdaymnth::        |
      | [0].serviceRegionCode | East                |
      | [0].planEndDateReason | PLAN_HAS_TERMINATED |
      | [0].selectionReason   | test                |
      | [0].createdBy         | 142                 |
      | [0].updatedBy         | 142                 |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].consumerId     | EM1.consumerId         |
      | [0].planId         | delete::               |
      | [0].planCode       | 84                     |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | ACCEPTED               |
      | [0].txnStatus      | DISENROLL_SUBMITTED    |
      | [0].startDate      | fdofmnth::             |
      | [0].enrollmentType | delete::               |
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId | EM1.consumerId     |
      | [0].planId     | 145                |
      | [0].planCode   | 85                 |
      | [0].startDate  | fdnxtmth::         |
      | [0].channel    | SYSTEM_INTEGRATION |
      | [0].createdBy  | 142                |
      | [0].updatedBy  | 142                |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].consumerId     | EM1.consumerId         |
      | [0].channel        | SYSTEM_INTEGRATION     |
      | [0].startDate      | fdnxtmth::             |
      | [0].planId         | delete::               |
      | [0].planCode       | 85                     |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | SUBMITTED_TO_STATE     |
      | [0].txnStatus      | SUBMITTED_TO_STATE     |
      | [0].enrollmentType | delete::               |
    And Wait for 5 seconds
    And I change Physical Address by consumerId "EM1.consumerId" with data
      | addressStreet1 | 1225 lasalle |
      | addressStreet2 |[blank]|
      | addressCity    | Aragon       |
      | addressState   | CT           |
      | addressCounty  | Polk         |
      | addressZip     | 30104        |
      | updatedBy      | 142          |
    And Wait for 5 seconds
    Then I verify out of state address record for consumerId "EM1.consumerId" is not empty by API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify current enrollment status is "DISENROLL SUBMITTED"
    Then  I verify future enrollment "Plan Change Button" is not displayed
    Then  I verify future enrollment "Edit Button" is not displayed
    Then  I verify future enrollment "Disregard Button" is not displayed
    Then I will verify business events are generated with data
      | eventName     | DISENROLL_REQUESTED |
      | consumerId    | EM1.consumerId      |
      | consumerFound | false               |

  @CP-15051 @CP-15051-01  @ui-ee @crm-regression @alex @kursat
  Scenario: Do Not Create DISENROLL REQUEST after PLAN CHANGE has been requested
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE" with data
      | saveConsumerInfo | 15051-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15051-1.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                | facilityInfo       |
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | planId                       | 1880               |
      | programCode                  | F                  |
      | subProgramTypeCode           | FOSTERCARE         |
      | planCode                     | 84                 |
      | anniversaryDate              | anniversaryDate    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15051-1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | recordId                     | 4                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | F                  |
      | subProgramTypeCode           | FOSTERCARE         |
      | planId                       | 1880               |
      | planCode                     | 85                 |
      | anniversaryDate              | anniversaryDate    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "15051-1.firstName" and last name "15051-1.lastName" with data
      | CURRENT ENROLLMENT.END DATE           | lastDayofpresentMonth |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested   |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                |
      | CURRENT ENROLLMENT.DISENROLL BUTTON   | hidden                |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed             |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed             |
    And I initiated get benefit status by consumer id "15051-1.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable             |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Unavailable             |
      | [1].consumerAction       | Disenroll               |
      | [1].planSelectionAllowed | true                    |
      | [2].action               | Unavailable             |
      | [2].consumerAction       | Plan Change Pre-lockin  |
      | [2].planSelectionAllowed | true                    |

  @CP-15051 @CP-15051-02  @ui-ee @crm-regression @alex @kursat
  Scenario: Do Not Create DISENROLL REQUEST after PLAN CHANGE has been submitted
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15051-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15051-2.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "15051-2" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15051-2.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 15051-2.consumerId |
      | [0].planId             | 145                |
      | [0].planCode           | 85                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "15051-2.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "15051-2a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 15051-2.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 84                                |
      | [0].enrollmentId       | 15051-2a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | fdofmnth::                        |
      | [0].endDate            | lstdaymnth::                      |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | MEDICAIDGF                        |
      | [0].serviceRegionCode  | East                              |
      | [1].planId             | delete::                          |
      | [1].planCode           | 85                                |
      | [1].consumerId         | 15051-2.consumerId                |
      | [1].enrollmentId       | 15051-2a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE                |
      | [1].txnStatus          | SUBMITTED_TO_STATE                |
      | [1].startDate          | fdnxtmth::                        |
      | [1].subProgramTypeCode | MEDICAIDGF                        |
      | [1].serviceRegionCode  | East                              |
    And Wait for 5 seconds
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "15051-2.firstName" and last name "15051-2.lastName" with data
      | CURRENT ENROLLMENT.END DATE           | lastDayofpresentMonth |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Submitted   |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                |
      | CURRENT ENROLLMENT.DISENROLL BUTTON   | hidden                |
      | FUTURE ENROLLMENT.EDIT BUTTON         | hidden                |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | hidden                |
    And I initiated get benefit status by consumer id "15051-2.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable             |
      | [0].consumerAction       | Plan Change Pre-lockin  |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Unavailable             |
      | [1].consumerAction       | Plan Change Anniversary |
      | [1].planSelectionAllowed | true                    |