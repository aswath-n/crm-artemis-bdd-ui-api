Feature: Update Enrollment Info during a manual Plan Change

  @CP-10425 @CP-10425-01  @ui-ee @crm-regression @Olga
  Scenario: Update Enrollment Info for Plan Change
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
    And I select A second plan from Available Plans
    And I click "Reason" Button for a consumer
    Then I verify bellow fields are displayed on "drop down reason" section:
      | Caller will like family on same plan               |
      | Plan has terminated                                |
      | Reason Member Gave is not related to any available |
      | Plan Change Open Enrollment                        |

  @CP-10425 @CP-10425-01  @ui-ee @crm-regression @Olga
  Scenario:Verify plan change reason is mandatory
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
    And I select A second plan from Available Plans
    And I verify I can't click submit button and update plan without choosing the reason


  @CP-10425 @CP-10425-03  @ui-ee @crm-regression @Olga
  Scenario:  Continue to plan details screen during a Plan change
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
    And I select A second plan from Available Plans
    And I navigate away by clicking on back arrow on Enrollment Update Page
    Then I see warning message pop up with an option to cancel or continue
    And I click on "cancel" on the warning message
    Then I verify I am still on "Enrollment Update" Page
    And I navigate away by clicking on back arrow on Enrollment Update Page
    Then I see warning message pop up with an option to cancel or continue
    And I click on "continue" on the warning message
    Then I verify I am navigated to "Program & Benefit Info" Page

  @CP-47814 @CP-47814-02 @ui-ee @crm-regression  @deepa
  Scenario: BLCRM View Check MMIS Indicator on P&BI Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 47814 |
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47814.consumerId                                         |
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
      | planId                       | 3146                                                     |
      | genericFieldText2            | 1                                                        |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And Wait for 3 seconds
    Then I will see a red stop indicator on the consumer row
    Then verify Hex color is red "#EF5350" icon mmis stop indicator
    When I hover my mouse over the red icon in the program and benifit page
    Then I will see the following "Check MMIS" text on mmis indicator









