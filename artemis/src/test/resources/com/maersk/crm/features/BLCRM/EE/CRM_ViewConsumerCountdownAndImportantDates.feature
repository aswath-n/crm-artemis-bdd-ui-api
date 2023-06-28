Feature: View Consumer Countdown and Important Dates

  @CP-13792 @CP-13792-01 @ui-ee @crm-regression @Olga
  Scenario: Verify countdown number of days are displayed
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Maura" and Last Name as "Gomez"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    Then I verify "count down number of Days" are displayed

  @CP-13792 @CP-13792-02 @ui-ee @crm-regression @Olga
  Scenario: Verify Hover text is displayed for Countdown
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Maura" and Last Name as "Gomez"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    And I hover over "Count Down"
    Then I see "Calendar days left to enroll in a plan" text displayed


  @CP-13792 @CP-13792-03 @ui-ee @crm-regression @Olga
  Scenario: Verify Important Dates are displayed on the calender text if Enroll Actions is  available
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Bob" and Last Name as "Jenner"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    And I hover over "Calendar Icon"
    Then I see "IMPORTANT DATES" text displayed


  @CP-13792 @CP-13792-04 @ui-ee @crm-regression @Olga
  Scenario: Verify Count Down number of days is calculated correct
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Bob" and Last Name as "Jenner"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    And I hover over "Calendar Icon"
    Then I see "IMPORTANT DATES" text displayed
    Then I verify "Count Down Days" number is calculated correct

  @CP-13792 @CP-13792-05 @ui-ee @crm-regression @Olga
  Scenario: Verify Countdown number of days are displayed in Red when on Due date
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Bob" and Last Name as "Jenner"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    Then I verify if the Count Days is "zero or negative" it's displayed in Red

  @CP-13792 @CP-13792-06 @ui-ee @crm-regression @Olga
  Scenario: Verify Countdown number of days are displayed in Black when on before Due date
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Maura" and Last Name as "Gomez"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    Then I verify if the Count Days is "positive" it's displayed in Black

  @CP-13792 @CP-13792-07 @ui-ee @crm-regression @Olga
  Scenario: Verify Red Dot is Displayed on the Calendar icon if Enroll Action is available
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Bob" and Last Name as "Jenner"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    Then I verify if the Count Days is "zero or negative" it's displayed in Red
    Then I verify "Red Dot on the Calendar Icon with Enroll Action" are displayed

  @CP-13792 @CP-13792-08 @ui-ee @crm-regression @Olga
  Scenario: Verify Red Dot is not displayed on the Calendar Icon if Enroll Actions is not available
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Bob" and Last Name as "Jenner"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    Then I verify if the Count Days is "zero or negative" it's displayed in Red
    Then I verify "Red Dot on the Calendar Icon with NO Enroll Action" are not-displayed

  @CP-13792 @CP-13792-09 @ui-ee @crm-regression @Olga
  Scenario: Verify Important Dates are not displayed on the Calendar hover text if Enroll Actions are not available
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13792-09 |
    Given I logged into CRM and click on initiate contact
    And I search for customer with First Name as "13792-09.firstName" and Last Name as "13792-09.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "13792-09.firstName" and last name "13792-09.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT        | -- --        |
      | CALENDAR ICON HOVER.IMPORTANT DATES    | -- --        |
      | COUNTDOWN.NUMBER OF DAYS UNTIL         | -- --        |
      | COUNTDOWN.ICON HOVER                   | -- --        |



  @CP-13792 @CP-13792-10 @ui-ee @crm-regression @Olga
  Scenario: Verify Hover text is not displayed for Count Down if the consumer doesn't have any Enroll action available
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Bob" and Last Name as "Jenner"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    When I hover over the "Count Down with No Enroll Action" I see "hover over" text is not-displayed


  @CP-14025 @CP-14025-01 @ui-ee @crm-regression @Olga
  Scenario: Verify hover over text contains Enroll if Enroll button is displayed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 14025-1 |
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "14025-1.firstName" and Last Name as "14025-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "14025-1.firstName" and last name "14025-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | ENROLL            |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to enroll in a plan   |


 # above scenario is updated and the below scenario is no longer needed
#  @CP-14025 @CP-14025-02 @ui-ee @crm-regression @Olga
#  Scenario: Verify hover over text contains Enroll and it is displayed as first in order
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    Given  I created a consumer with population type "MEDICAID-GENERAL"
#    Given I initiated Eligibility and Enrollment Create API
#    And I provide the enrollment and eligibility information to create enrollment
#      | isEligibilityRequired        | yes               |
#      | otherSegments                |[blank]|
#      | isEnrollemntRequired         | no                |
#      | recordId                     | 3                 |
#      | isEnrollmentProviderRequired | no                |
#      | enrollmentStartDate          |[blank]|
#      | eligibilityStartDate         | 1stDayofLastMonth |
#      | eligibilityStatusCode        |[blank]|
#      | txnStatus                    | Accepted          |
#      | programCode                  | M                 |
#    And I run create Eligibility and Enrollment API
#    Given  I created a consumer with population type "MEDICAID-GENERAL"
#    Given I initiated Eligibility and Enrollment Create API
#    And I provide the enrollment and eligibility information to create enrollment
#      | isEligibilityRequired        | yes               |
#      | otherSegments                |[blank]|
#      | isEnrollemntRequired         | no                |
#      | recordId                     | 3                 |
#      | isEnrollmentProviderRequired | no                |
#      | enrollmentStartDate          |[blank]|
#      | eligibilityStartDate         | 1stDayofLastMonth |
#      | eligibilityStatusCode        |[blank]|
#      | txnStatus                    | Accepted          |
#      | programCode                  | M                 |
#    And I run create Eligibility and Enrollment API
#    Given I logged into CRM and click on initiate contact
#    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
#    And I link the contact to an existing Case or Consumer Profile
#    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
#    And I click on carrot sign for each consumer section in program and benefit info page
#    And I hover over "calendar icon with enroll action"
#    Then I verify "ENROLL" text is displayed on "hover over calendar text"
#    Then I verify "ENROLL" text is displayed on "hover over calendar text as First in order"

  @CP-14025 @CP-14025-03 @ui-ee @crm-regression @Olga
  Scenario: Verify count down days are calculated correct by Enroll action dates
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click on carrot sign for each consumer section in program and benefit info page
    And I hover over "calendar icon with enroll action"
    Then I verify count down days are calculated correct

  @CP-14025 @CP-14025-04 @ui-ee @crm-regression @Olga
  Scenario: Verify countdown number of days are displayed for consumer with Plan Change Action
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    Then I verify "count down number of Days" are displayed

  @CP-14025 @CP-14025-05 @ui-ee @crm-regression @Olga
  Scenario: Verify Countdown number of days are displayed in Red when on Due date for consumer with Plan Change Action
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Greg" and Last Name as "Mavis"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    Then I verify if the Count Days is "zero or negative" it's displayed in Red

  @CP-14025 @CP-14025-06 @ui-ee @crm-regression @Olga
  Scenario: Verify Important Dates are displayed on the calender text if Enroll Actions is available for consumer with Plan Change Action
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Greg" and Last Name as "Mavis"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    And I hover over "Calendar Icon"
    Then I see "IMPORTANT DATES" text displayed


  @CP-14025 @CP-14025-07 @ui-ee @crm-regression @Olga
  Scenario: Verify Red Dot is Displayed on the Calendar icon if Plan Change Action is available
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Greg" and Last Name as "Mavis"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    Then I verify if the Count Days is "zero or negative" it's displayed in Red
    Then I verify "Red Dot on the Calendar Icon with Plan Change Action" are displayed


  @CP-14025 @CP-14025-08 @ui-ee @crm-regression @Olga
  Scenario: Verify Red Dot is not displayed on the Calendar Icon if Plan Change is not available
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Greg" and Last Name as "Mavis"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    Then I verify if the Count Days is "zero or negative" it's displayed in Red
    Then I verify "Red Dot on the Calendar Icon with NO Plan Change Action" are not-displayed

  @CP-14025 @CP-14025-09 @ui-ee @crm-regression @Olga
  Scenario: Verify Countdown number of days are displayed in Black when on before Due date for consumer with Plan Change Action
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    Then I verify if the Count Days is "positive" it's displayed in Black

  @CP-14025 @CP-14025-10 @ui-ee @crm-regression @Olga
  Scenario: Verify Important Dates are not displayed on the Calendar hover text if Plan Change Actions are not available
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Greg" and Last Name as "Mavis"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the "Count Down Days Action" for the consumer
    And I hover over "Calendar Icon with No Plan Change Action"
    Then I see "-- --" text displayed

  @CP-14025 @CP-14025-11 @ui-ee @crm-regression @Olga
  Scenario: Verify when Plan Change button is available plan change pre-lockin important dates are displayed
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I hover over the "calendar icon" I see "PRE-LOCKIN - WINDOW" text is displayed
    Then I verify "Plan Change" button is displayed
    And I hover over "Count Down"
    Then I see "Calendar days left to change plan before lock-in" text displayed

  @CP-14025 @CP-14025-12 @ui-ee @crm-regression @Olga
  Scenario:  Verify when Plan Change button is available plan change anniversary important dates are displayed
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    And I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I hover over the "calendar icon" I see "ANNIVERSARY - WINDOW" text is displayed
    Then I verify "Plan Change" button is displayed













