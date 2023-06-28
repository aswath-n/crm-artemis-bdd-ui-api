Feature: Additional Steps Needed for Consumer Initiated Plan Change (All Channels) - Current Enrollment Segment

  @CP-14592 @CP-14592-01 @ui-ee @crm-regression @alex
  Scenario:  Update the Old Enrollment to a Selection Status of “Disenroll Requested”
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
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify previously accepted enrollment segment with an end date and selection status of “Disenroll Requested”

  @CP-14592 @CP-14592-02 @ui-ee @crm-regression @alex
  Scenario:  Open Plan Change Enrollment for Editing and Disregarding
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
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi" with data
      | CURRENT ENROLLMENT.SELECTION STATUS | Disenroll Requested |
      | FUTURE ENROLLMENT.EDIT BUTTON       | is displayed        |
      | FUTURE ENROLLMENT.DISREGARD BUTTON  | is displayed        |

  @CP-14592 @CP-14592-03 @ee-UI2API @crm-regression @alex
  Scenario:  The Plan Change Actions should be set to unavailable
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 14592-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14592-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14592-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 1895                |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "14592-01.firstName" and Last Name as "14592-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify previously accepted enrollment segment with an end date and selection status of “Disenroll Requested”
#    Then I verify consumer actions are not available
    Then I verify program & benefit info page for consumer first name "14592-01.firstName" and last name "14592-01.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT     | -- -- |
      | CALENDAR ICON HOVER.IMPORTANT DATES | -- -- |
      | COUNTDOWN.NUMBER OF DAYS UNTIL      | -- -- |
      | COUNTDOWN.ICON HOVER                | -- -- |
    And I initiated get benefit status by consumer id "14592-01.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer "14592-01.consumerId"
    Then I Verify Consumer Actions are "Unavailable"

  @CP-14592 @CP-14592-04 @ee-UI2API @crm-regression @alex
  Scenario:  Indicate Plan Change Enrollment Selection for Outbound Basket
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
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I initiated Enrollment status Api
    Then I verify consumer and status selection made are displayed on the Outbound basket

  @CP-14592 @CP-14592-05 @ee-UI2API @crm-regression @alex
  Scenario: Indicate Disenroll Request for Previous Selection for Outbound Basket
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
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I initiated Enrollment status Api
    Then I verify consumer and status selection made are displayed on the Outbound basket