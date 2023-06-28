Feature: History Screen Part 1

  @CP-11056 @CP-11056-01 @ui-core @crm-regression @kamil @sang
  Scenario: Navigate and display History of >1 consumer on a case: Case / Consumer Search Screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Member |
      | saveConsumerInfo | QW2    |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Member |
      | saveConsumerInfo | QW3    |
    Given I logged into CRM
    When I click case consumer search tab
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I click first consumer ID in search results for case and consumer search page
    And I store "PRIMARY AND MEMBERS NAMES" for use in verification
    When I click on "History" Tab on Contact Dashboard Page
    Then Verify I will see the default values for History screen for "MULTIPLE" consumer in a case
    And I verify Primary Individual consumer is first then all case members listed alphabetically by First Name in the History Tab consumer dropdown
    Then I verify the values for Channel dropdown on History screen
      | All                |
      | IVR                |
      | Fax                |
      | Mail               |
      | Phone              |
      | System Integration |
      | Web                |

  @CP-11056 @CP-11056-02 @ui-core @crm-regression @kamil @sang
  Scenario: Navigate and display History of >1 consumer on a case : Active Contact Screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Member |
      | saveConsumerInfo | QW2    |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Member |
      | saveConsumerInfo | QW3    |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I store "PRIMARY AND MEMBERS NAMES" for use in verification
    When I click on "History" Tab on Contact Dashboard Page
    Then Verify I will see the default values for History screen for "MULTIPLE" consumer in a case
    And I verify Primary Individual consumer is first then all case members listed alphabetically by First Name in the History Tab consumer dropdown
    Then I verify the values for Channel dropdown on History screen
      | All                |
      | IVR                |
      | Fax                |
      | Mail               |
      | Phone              |
      | System Integration |
      | Web                |

  @CP-11056 @CP-11056-03 @ui-core @crm-regression @kamil @sang
  Scenario: Verify Enrollment Business Events AND  Eligibility Business Events on history screen: >1 consumer on case: Case Consumer Search Screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Member |
      | saveConsumerInfo | QW2    |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given I logged into CRM
    When I click case consumer search tab
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I click first consumer ID in search results for case and consumer search page
    When I click on "History" Tab on Contact Dashboard Page
    And I select Consumer Name dropdown value at "FIRST" position in the History Tab
    Then I verify "Eligibility" Business Events has required data elements
    Then I verify "Enrollment" Business Events has required data elements
    And I select Consumer Name dropdown value at "SECOND" position in the History Tab
    Then I verify "Eligibility" Business Events has required data elements
    Then I verify "Enrollment" Business Events has required data elements

  @CP-11056 @CP-11056-04 @ui-core @crm-regression @kamil
  Scenario: Verify Enrollment Business Events AND  Eligibility Business Events on history screen: >1 consumer on case: Active Contact Screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Member |
      | saveConsumerInfo | QW2    |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "History" Tab on Contact Dashboard Page
    And I select Consumer Name dropdown value at "FIRST" position in the History Tab
    Then I verify "Eligibility" Business Events has required data elements
    Then I verify "Enrollment" Business Events has required data elements
    And I select Consumer Name dropdown value at "SECOND" position in the History Tab
    Then I verify "Eligibility" Business Events has required data elements
    Then I verify "Enrollment" Business Events has required data elements

  @CP-11056 @CP-11056-05 @CP-45831 @CP-45831-01 @ui-core @crm-regression @kamil @sang
  Scenario: Verify I will see the default screen display the consumer’s 20 most recent events and load more events when user scrolls to the bottom
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "MegawkxO" and Last Name as "TeaXeoAD"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "History" Tab on Contact Dashboard Page
    And I select Consumer Name dropdown value at "FIRST" position in the History Tab
    Then Verify I will see screen display the consumer’s 20 most recent events
    And I navigate to the bottom of the History tab to load more existing Events
    Then Verify I will see screen display the consumer’s 21 most recent events

  @CP-11057 @CP-11057-01 @ui-core @crm-regression @aikanysh @sang
  Scenario: Navigate and display History of 1 consumer on a case: Case/Consumer Search Screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given I logged into CRM
    When I click case consumer search tab
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I click first consumer ID in search results for case and consumer search page
    And I store "PRIMARY INDIVIDUAL" for use in verification
    When I click on "History" Tab on Contact Dashboard Page
    Then I verify Primary Individual name is selected in the Consumer Name dropdown for a case with only one consumer
    Then Verify I will see the default values for History screen for "SINGLE" consumer in a case
    Then I verify the values for Channel dropdown on History screen
      | All                |
      | IVR                |
      | Fax                |
      | Mail               |
      | Phone              |
      | System Integration |
      | Web                |

  @CP-11057 @CP-11057-02 @ui-core @crm-regression @aikanysh @sang
  Scenario: Navigate and display History of 1 consumer on a case: Active Contact Screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I store "PRIMARY INDIVIDUAL" for use in verification
    When I click on "History" Tab on Contact Dashboard Page
    Then I verify Primary Individual name is selected in the Consumer Name dropdown for a case with only one consumer
    Then Verify I will see the default values for History screen for "SINGLE" consumer in a case
    Then I verify the values for Channel dropdown on History screen
      | All                |
      | IVR                |
      | Fax                |
      | Mail               |
      | Phone              |
      | System Integration |
      | Web                |

  @CP-11057 @CP-11057-03 @ui-core @crm-regression @aikanysh @sang
  Scenario: Verify Enrollment Business Events AND  Eligibility Business Events on history screen: 1 consumer on case: Case/Consumer Search Screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given I logged into CRM
    When I click case consumer search tab
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I click first consumer ID in search results for case and consumer search page
    When I click on "History" Tab on Contact Dashboard Page
    Then I verify "Eligibility" Business Events has required data elements
    Then I verify "Enrollment" Business Events has required data elements

  @CP-11057 @CP-11057-04 @ui-core @crm-regression @aikanysh @sang
  Scenario: Verify Enrollment Business Events AND  Eligibility Business Events on history screen: 1 consumer on case: Active Contact Screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "History" Tab on Contact Dashboard Page
    Then I verify "Eligibility" Business Events has required data elements
    Then I verify "Enrollment" Business Events has required data elements

  @CP-45831 @CP-45831-02 @ui-core @crm-regression @sang
  Scenario: Verify Selection of Business Unit Enrollment and Channel Phone filters Events shown on History Tab
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "MegawkxO" and Last Name as "TeaXeoAD"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "History" Tab on Contact Dashboard Page
    And I select Consumer Name dropdown value at "FIRST" position in the History Tab
    And I select "BUSINESS EVENTS" dropdown and select the following options in the History Tab
    | Enrollment |
    And I select "CHANNEL" dropdown and select the following options in the History Tab
      | Phone |
    Then I verify events displayed by the selected dropdown values
    | Enrollment | Phone |