Feature: IN-EB CRM CORE Contact Record Search

  @CP-25078 @CP-25078-01 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Contact Record Search Parameters - Search Case Dropdown
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to Contact Record Search Page
    Then I should see the following dropdown options for "Search Case" field displayed in Contact Record page
      | Internal |
      | State    |
    Then I should see the following dropdown options for "Search Consumer" field displayed in Contact Record page
      | Internal     |
      | MEDICAID/RID |


  @CP-25078 @CP-25078-03 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Contact Record Search Parameters - Default Values
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to Contact Record Search Page
    Then I see "searchCase" field has default "State" value captured on Contact Record Search page
    Then I see "searchConsumer" field has default "MEDICAID/RID" value captured on Contact Record Search page


  @CP-25078 @CP-25078-02 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario Outline: IN-EB: Search Results - State Case ID
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to Contact Record Search Page
    Then I search with Search Case "<SearchCase>" and Case ID "<CaseId>"
    Then I see the column for Case ID with the correct value "<CaseId>" Contact Record Search page
    Examples:
      | SearchCase | CaseId  |
      | State      | 425wffs |


  @CP-25078 @CP-25078-04 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario Outline: IN-EB: Search Results - Medicaid/RID Consumer ID
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    Then I navigate to Contact Record Search Page
    Then I searched with "<consumerType>" Consumer Id "<consumerID>"
    Then Verify "<consumerType>" and Consumer Id "<consumerID>" is visible
    Examples:
      | consumerType | consumerID |
      | MEDICAID/RID | 123456     |

  @CP-30728 @CP-30728-01 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Hide Email Field on Contact search
    Given I logged into CRM with "Service Tester 3" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I verify Email Search Field is NOT displayed in Manual Contact Record Search