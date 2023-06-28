Feature: Start with Plan - Submit Selected Plan and Selected Provider

  @CP-16611 @CP-16611-01  @ui-ee @crm-regression @ee-UI2API @alex
  Scenario: Submitting a Selected Plan and Selected Provider - New Enrollment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 16611-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 16611-1.consumerId |
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofPresentMonth                                     |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
      | anniversaryDate              | anniversaryDate                                          |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "16611-1.firstName" and Last Name as "16611-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click on search provider button
    And  I click the Select Provider button
    And I verify the following fields will be displayed: Provider Name, First Name, Last Name, NPI
    And I click submit button on enrollment update
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | correlationId | null                  |
      | consumerId    | 16611-1.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|

  @CP-16611 @CP-16611-02  @ui-ee @crm-regression @ee-UI2API @alex
  Scenario: Submitting a Selected Plan and Selected Provider - Plan Change
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 16611-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 16611-2.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | anniversaryDate              | anniversaryDate    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 16611-2.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | recordId                     | 4                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | planCode                     | 85                 |
      | planId                       | 145                |
      | serviceRegionCode            | East               |
      | anniversaryDate              | anniversaryDate    |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "16611-2.firstName" and Last Name as "16611-2.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click on search provider button
    And  I click the Select Provider button
    And I verify the following fields will be displayed: Provider Name, First Name, Last Name, NPI
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | correlationId | null                  |
      | consumerId    | 16611-2.consumerId    |
      | consumerFound | true                  |
      | DPBI          |[blank]|