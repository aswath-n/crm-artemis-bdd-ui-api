Feature: Add Channel Dropdown Field In Enrollment Update Screen

  @CP-34433 @CP-34433-01 @ui-ee @kursat
  Scenario: Select and Edit Channel (from drop-down) When Completing ENROLL and PLAN CHANGE Action During Non-ACTIVE Contact ( CSR Role )
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 34433-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 34433-01.consumerId |
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
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "34433-01.firstName" and Last Name as "34433-01.lastName"
    And I click on the crm case id
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # 1.0 SELECT Channel (from drop-down) When Completing ENROLL Action During Non-ACTIVE Contact
    And I click "ENROLL" Button for a consumer first name "34433-01.firstName" and last name "34433-01.lastName"
    And I select "WELLCARE" from Available Plans
    ############################################################################################################
    # There is an issue here, channel is not displayed maybe a defect or a permission issue. Needs investigation
    ############################################################################################################
    Then I verify "Email" is the displayed Channel Dropdown option
    # 2.0 SUBMIT Channel Selection on Enrollment Update - After ENROLL Action
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "34433-01.firstName" and last name "34433-01.lastName" with data
      | FUTURE ENROLLMENT.CHANNEL | Email |
    # 3.0 EDIT Channel Selection on Enrollment Update - After ENROLL Action
    And I click future enrollment "EDIT" Button for a consumer first name "34433-01.firstName" and last name "34433-01.lastName"
    Then I verify I am still on "ENROLLMENT EDIT" Page
    When I click on Remove Plan Option
    And I select "PEACH STATE" from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click Channel Dropdown button to display options
    And I select "SMS Text" from Channel Dropdown
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "34433-01.firstName" and last name "34433-01.lastName" with data
      | FUTURE ENROLLMENT.CHANNEL | SMS Text |
    And I click "DISREGARD" Button for a consumer first name "34433-01.firstName" and last name "34433-01.lastName"
    And I click on continue for Disregard Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 34433-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And I click on the Demographic Info Tab
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # 4.0 SELECT Channel (from drop-down) When Completing PLAN CHANGE Action During Non-ACTIVE Contact
    And I click current enrollment "PLAN CHANGE" Button for a consumer first name "34433-01.firstName" and last name "34433-01.lastName"
    And I select "CARESOURCE GEORGIA" from Available Plans
    And I select a reason from drop down on Enrollment Update page
    Then I verify "Email" is the displayed Channel Dropdown option
    And I click Channel Dropdown button to display options
    And I select "Outreach" from Channel Dropdown
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "34433-01.firstName" and last name "34433-01.lastName" with data
      | FUTURE ENROLLMENT.CHANNEL | Outreach |
    # 5.0 SUBMIT Channel Selection on Enrollment Update - After PLAN CHANGE
    # 6.0 EDIT Channel Selection on Enrollment Update - After PLAN CHANGE
    And I click future enrollment "EDIT" Button for a consumer first name "34433-01.firstName" and last name "34433-01.lastName"
    Then I verify I am still on "ENROLLMENT EDIT" Page
    When I click on Remove Plan Option
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click Channel Dropdown button to display options
    And I select "State_Eligibility_System" from Channel Dropdown
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "34433-01.firstName" and last name "34433-01.lastName" with data
      | FUTURE ENROLLMENT.CHANNEL | State_Eligibility_System |


  @CP-34433 @CP-34433-02 @ui-ee @kursat
  Scenario: Channel is not displayed When Completing ENROLL and PLAN CHANGE Action During ACTIVE Contact ( CSR Role )
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 34433-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 34433-02.consumerId |
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "34433-02.firstName" and Last Name as "34433-02.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # 1.0 SELECT Channel (from drop-down) When Completing ENROLL Action During Non-ACTIVE Contact
    And I click "ENROLL" Button for a consumer first name "34433-02.firstName" and last name "34433-02.lastName"
    And I select "WELLCARE" from Available Plans
    # 2.0 SUBMIT Channel Selection on Enrollment Update - After ENROLL Action
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "34433-02.firstName" and last name "34433-02.lastName" with data
      | FUTURE ENROLLMENT.CHANNEL | Phone |
    # 3.0 EDIT Channel Selection on Enrollment Update - After ENROLL Action
    And I click future enrollment "EDIT" Button for a consumer first name "34433-02.firstName" and last name "34433-02.lastName"
    Then I verify I am still on "ENROLLMENT EDIT" Page
    When I click on Remove Plan Option
    And I select "PEACH STATE" from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "34433-02.firstName" and last name "34433-02.lastName" with data
      | FUTURE ENROLLMENT.CHANNEL | Phone |
    And I click "DISREGARD" Button for a consumer first name "34433-02.firstName" and last name "34433-02.lastName"
    And I click on continue for Disregard Enrollment
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 34433-02.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And I click on the Demographic Info Tab
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # 4.0 SELECT Channel (from drop-down) When Completing PLAN CHANGE Action During Non-ACTIVE Contact
    And I click current enrollment "PLAN CHANGE" Button for a consumer first name "34433-02.firstName" and last name "34433-02.lastName"
    And I select "CARESOURCE GEORGIA" from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "34433-02.firstName" and last name "34433-02.lastName" with data
      | FUTURE ENROLLMENT.CHANNEL | Phone |
    # 5.0 SUBMIT Channel Selection on Enrollment Update - After PLAN CHANGE
    # 6.0 EDIT Channel Selection on Enrollment Update - After PLAN CHANGE
    And I click future enrollment "EDIT" Button for a consumer first name "34433-02.firstName" and last name "34433-02.lastName"
    Then I verify I am still on "ENROLLMENT EDIT" Page
    When I click on Remove Plan Option
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "34433-02.firstName" and last name "34433-02.lastName" with data
      | FUTURE ENROLLMENT.CHANNEL | Phone |
