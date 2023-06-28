@CP-975 @ui-ee  @crm-regression @Olga
Feature: Eligibility Info Section - View Case Eligibility and Enrollment Information

  @CP-975 @CP-975-01  @ui-ee @crm-regression @Olga
  Scenario:Verification Eligibility section is displayed for all consumers
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joey" and Last Name as "Ross"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Eligibility section" is displayed for all cunsumers on Program & Benefit Info Page


  @CP-975 @CP-975-02 @ui-ee @crm-regression @Olga
  Scenario: Verification Eligibility Section is collapsed by default
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joey" and Last Name as "Ross"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Eligibility section" is displayed for all cunsumers on Program & Benefit Info Page
    Then I verify "Eligibility Section" is collapsed by default

  @CP-975 @CP-975-03 @ui-ee @crm-regression @Olga
  Scenario: Verify Eligibility Section Labels - Current Eligibility if start date is past
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joey" and Last Name as "Ross"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Eligibility section" is displayed for all cunsumers on Program & Benefit Info Page
    Then I verify "Start date" on the "Current Eligibility" section is in the past

  @CP-975 @CP-975-04 @ui-ee @crm-regression @Olga
  Scenario: Verify Carrot to Collapse/Expand Current Eligibility Section
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joey" and Last Name as "Ross"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Carrot symbol" is displayed for all cunsumers on Program & Benefit Info Page

  @CP-975 @CP-975-05 @ui-ee @crm-regression @Olga
  Scenario: Verify Future Eligibility section is displayed for all consummers
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joey" and Last Name as "Ross"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Future Eligibility" is displayed for all cunsumers on Program & Benefit Info Page


  @CP-975 @CP-975-06 @ui-ee @crm-regression @Olga
  Scenario: Verify Eligibility Section Labels - Future Eligibility expand option not available
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joey" and Last Name as "Ross"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Future Eligibility" is displayed for all cunsumers on Program & Benefit Info Page
    Then I verify "Expand option" is not displayed on "Future Eligibility" section


  @CP-975 @CP-975-07 @ui-ee @crm-regression @Olga
  Scenario: Verify Eligibility Section Sort Order
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joey" and Last Name as "Ross"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Eligibility section" is displayed for all cunsumers on Program & Benefit Info Page
    Then I verify "Future Eligibility" is displayed for all cunsumers on Program & Benefit Info Page
    Then I verify "Order of Eligibility" is displayed for all cunsumers on Program & Benefit Info Page


  @CP-975 @CP-975-08 @ui-ee @crm-regression @Olga
  Scenario: Verify -Eligibility section- Data Elements
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joey" and Last Name as "Ross"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Eligibility section" is displayed for all cunsumers on Program & Benefit Info Page
    Then I verify bellow fields are displayed on "Eligibility" section:
      | CONSUMER POPULATION |
      | SERVICE REGION      |
      | START DATE          |
      | END DATE            |


  @CP-975 @CP-975-09 @ui-ee @crm-regression @Olga
  Scenario: Verify -Expanded - Data Elements - Eligibility Section
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Joey" and Last Name as "Ross"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Eligibility section" is displayed for all cunsumers on Program & Benefit Info Page
    Then I verify bellow fields are displayed on "PRIOR ELIGIBILITY DETAILS" section:
      | PROGRAM NAME           |
      | ELIGIBILITY START DATE |
      | ELIGIBILITY END DATE   |
      | ELIGIBILITY END REASON |

  @CP-974 @CP-974-01 @CP-43525 @CP-43525-04 @ui-ee @crm-regression @Olga
  Scenario: Verification Current Enrollment Section is displayed for consumer
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | CP-974-01 |
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
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | CP-974-01.consumerId |
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
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify program & benefit info page for consumer first name "CP-974-01.firstName" and last name "CP-974-01.lastName" with data
      | CURRENT ENROLLMENT.SEGMENT | is displayed |
    And I verify "PRIOR ELIGIBILITY CARROT" is not displayed
    And I verify "PRIOR ENROLLMENT CARROT" is not displayed

  @CP-974 @CP-974-02 @ui-ee @crm-regression @Olga
  Scenario: Verification Enrollment Section is collapsed by default
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | CP-974-02 |
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
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | CP-974-02.consumerId |
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
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify "Current Enrollment" is collapsed by default

  @CP-974 @CP-974-03 @ui-ee @crm-regression @Olga
  Scenario: Verify Carrot to Collapse/Expand Current Enrollment Section
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Hugo" and Last Name as "First"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify "Current Enrollment" Section is displayed
    And I verify "Carrot Button on Current Enrollment" is displayed for all cunsumers on Program & Benefit Info Page

  @CP-974 @CP-974-04 @ui-ee @crm-regression @Olga
  Scenario: Verify Enrollment Section Labels - Current Enrollment if start date is in the past
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Hugo" and Last Name as "First"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Start date" on the "Current Enrollment" section is in the past
    Then I verify "End date" on the "Current Enrollment" section is in the feature

  @CP-974 @CP-974-05 @ui-ee @crm-regression @Olga
  Scenario: Verification Future Enrollment Section is displayed for consumer
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Hugo" and Last Name as "First"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify "Future Enrollment" Section is displayed

  @CP-974 @CP-974-06 @ui-ee @crm-regression @Olga
  Scenario: Verify Enrollment Section - Future Enrollment expand option not available
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Hugo" and Last Name as "First"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Expand option" is not displayed on "Future Enrollment" section

  @CP-974 @CP-974-07 @ui-ee @crm-regression @Olga
  Scenario:Verify Enrollment Section Sort Order
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Hugo" and Last Name as "First"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Order of Enrollment" is displayed for all cunsumers on Program & Benefit Info Page

  @CP-974 @CP-974-08 @ui-ee @crm-regression @Olga
  Scenario:  Verify -Enrollment section- Data Elements
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Hugo" and Last Name as "First"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify bellow fields are displayed on "Enrollment" section:
      | PLAN NAME        |
      | SELECTION STATUS |
      | CHANNEL          |
      | PCP NAME         |
      | START DATE       |
      | END DATE         |

  @CP-974 @CP-974-09 @ui-ee @crm-regression @Olga
  Scenario:  Verify -Expanded - Data Elements - Enrollment Section
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Hugo" and Last Name as "First"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify bellow fields are displayed on "PRIOR ENROLLMENT DETAILS" section:
      | PLAN NAME             |
      | ENROLLMENT START DATE |
      | ENROLLMENT END DATE   |
      | ENROLLMENT END REASON |


  @CP-11055 @CP-11055-01 @ui-ee @crm-regression @Olga
  Scenario: Case Consumer Search Verification Eligibility Section is collapsed by default
    Given I logged into the CRM Application
    When I click case consumer search tab
    When I searched customer have First Name as "Ann" and Last Name as "Evans"
    And I click "CRM Case Id" Button for a consumer
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Eligibility section" is displayed for all cunsumers on Program & Benefit Info Page
    Then I verify "Eligibility Section" is collapsed by default

  @CP-11055 @CP-11055-02 @ui-ee @crm-regression @Olga
  Scenario:Case Consumer Search Verification Future Enrollment Section is displayed for consumer
    Given I logged into the CRM Application
    When I click case consumer search tab
    When I searched customer have First Name as "Ann" and Last Name as "Evans"
    And I click "CRM Case Id" Button for a consumer
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify "Future Enrollment" Section is displayed

  @CP-43525 @CP-43525-01 @ui-ee @crm-regression @burak
  Scenario: Verify Prior Eligibility and Enrollment Details (BLCRM)
    ## Verify able to expand carrots when there is Prior Eligibility and Enrollment
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "Lauretta" and Last Name as "Skiles"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I expand "PRIOR ELIGIBILITY" section for every consumer
    And I expand "PRIOR ENROLLMENT" section for every consumer
    Then I verify program & benefit info page for consumer first name "Lauretta" and last name "Skiles" with data
#     3.0 Prior Eligibility and Enrollment segment display
      | PRIOR ELIGIBILITY DETAILS.CONSUMER POPULATION    | Medicaid-General Population                               |
      | PRIOR ELIGIBILITY DETAILS.ELIGIBILITY START DATE | 01/01/2022                                                |
      | PRIOR ELIGIBILITY DETAILS.ELIGIBILITY END DATE   | 12/31/2022                                                |
      | PRIOR ELIGIBILITY DETAILS.ELIGIBILITY END REASON | OPEN_ENROLLMENT_PLAN_CHANGE - Plan Change Open Enrollment |
      | PRIOR ENROLLMENT DETAILS.PLAN NAME               | AMERIGROUP COMMUNITY CARE                                 |
      | PRIOR ENROLLMENT DETAILS.ENROLLMENT END REASON   | test                                                      |
      | PRIOR ENROLLMENT DETAILS.ENROLLMENT START DATE   | 01/01/2022                                                |
      | PRIOR ENROLLMENT DETAILS.ENROLLMENT END DATE     | 12/31/2022                                                |





