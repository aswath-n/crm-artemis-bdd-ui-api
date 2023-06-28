Feature: Disregard New Enroll for consumer - current &future segments


  @CP-14593 @CP-14591-01 @CP-14593-02 @ui-ee @ui-ee-smoke @crm-regression @ee-UI2API @kamil
  Scenario Outline: Verify Click on disregard button warning is displayed --> click cancel on warning
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
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
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    Then I will click submit button
    And I click on Disregard button on program & benefits page
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I click on continue for Disregard Enrollment
    And New enrollment selection is not displayed on the program & benefits info screen
    Examples:
      | projectName |
      |[blank]|


  @CP-14593 @CP-14594 @CP-14593-03 @ui-ee @crm-regression @ee-UI2API @kamil
  Scenario Outline: Verify Disregard a New Enrollment --> Edit & Disregard button is not displayed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
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
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    Then I will click submit button
    And I click on Disregard button on program & benefits page
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I click on continue for Disregard Enrollment
    And Edit and Disregard button are not displayed on the Enrollment section
    Examples:
      | projectName |
      |[blank]|


  @CP-14593 @CP-14594 @CP-14593-04 @ui-ee @crm-regression @ee-UI2API @kamil
  Scenario: Disregard New Enrollment ,Consumer Actions are set back to available
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 14594-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14594-2.consumerId  |
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
    When I searched consumer created through api with First Name as "14594-2.firstName" and Last Name as "14594-2.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    Then I will click submit button
    And I click on Disregard button on program & benefits page
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I click on continue for Disregard Enrollment
    Then I verify program & benefit info page for consumer first name "14594-2.firstName" and last name "14594-2.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT        | ENROLL    |
      | CALENDAR ICON HOVER.RED DOT            | displayed |



  @CP-14593 @CP-14594 @CP-14593-05 @ui-ee @crm-regression @ee-UI2API @kamil
  Scenario Outline: Disregard New Enrollment ,Verify Consumer is not displayed in the Outbound basket
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
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
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    Then I will click submit button
    And I click on Disregard button on program & benefits page
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I click on continue for Disregard Enrollment
    Then I initiated Enrollment status Api
    Then I verify consumer is not displayed on the Outbound basket
    Examples:
      | projectName |
      |[blank]|

  @CP-14593 @CP-14594 @CP-14593-06 @ui-ee @crm-regression @ee-UI2API @kamil
  Scenario Outline: Disregard New Enrollment ,Verify Disregarded Business Event is triggered & Enrollment Update DPBI event is triggered
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 14594-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14594-01.consumerId  |
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
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I click on the Contact Channel Type "Email"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    Then I will click submit button
    And I click on Disregard button on program & benefits page
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I click on continue for Disregard Enrollment
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | channel       | EMAIL                   |
      | consumerId    | 14594-01.consumerId     |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Examples:
      | projectName |
      |[blank]|
