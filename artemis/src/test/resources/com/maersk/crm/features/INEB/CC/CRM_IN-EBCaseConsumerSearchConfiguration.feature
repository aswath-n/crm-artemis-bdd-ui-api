Feature: Configured Case/Consumer Search Parameters & Search Result

  @CP-25313 @CP-25313-01 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Active Contact case consumer search - verification Email and MI are not displayed
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I click on advance search icon
    Then I don't see unexpected "fields" on IN-EB search consumer component
      | Email |
      | MI    |

  @CP-25313 @CP-25313-02 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Active Contact case consumer - validate all expected fields
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    Then I see expected "fields" on IN-EB search consumer component

  @CP-25313 @CP-25313-03 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Manual case consumer search - validate all expected fields
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to Manual Consumer search page
    Then I see expected "fields" on IN-EB search consumer component

  @CP-25313 @CP-25313-04 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Active Contact case consumer Advanced seach - validate all expected fields
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I click on advance search icon
    Then I see expected "advance fields" on IN-EB search consumer component

  @CP-25313 @CP-25313-05 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Manual case consumer Advanced search - validate all expected fields
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to Manual Consumer search page
    And I click on advance search icon
    Then I see expected "advanced fields" on IN-EB search consumer component

  @CP-25313 @CP-25313-06 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Active Contact case consumer Search result - validate all expected search result columns
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "Adel" and Last Name as ""
    Then I validate search result component has expected "columns"
      | State CASE ID            |
      | MEDICAID/RID CONSUMER ID |
      | FIRST NAME               |
      | LAST NAME                |
      | DATE OF BIRTH            |
      | SSN                      |
#      | PHONE NUMBER             | change of configuration with CP-32421


  @CP-25313 @CP-25313-07 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Manual case consumer Search result - validate all expected search result columns
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to Manual Consumer search page
    And I searched customer have First Name as "Adel" and Last Name as ""
    Then I validate search result component has expected "columns"
      | State CASE ID            |
      | MEDICAID/RID CONSUMER ID |
      | FIRST NAME               |
      | LAST NAME                |
      | DATE OF BIRTH            |
      | SSN                      |

  @CP-25313 @CP-25313-08 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Active Contact case consumer Search result - validate Email and MI is not displayed in search result columns
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "FnhPRrI" and Last Name as ""
    Then I validate search result component has does not display unexpected "columns"
      | MI    |
      | EMAIL |

  @CP-25313 @CP-25313-09 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Manual case consumer Search result - validate Email and MI is not displayed in search result columns
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to Manual Consumer search page
    And I searched customer have First Name as "FnhPRrI" and Last Name as ""
    Then I validate search result component has does not display unexpected "columns"
      | MI    |

  @CP-25077 @CP-25077-01 @chopa @crm-regression @ui-cc-in
  Scenario Outline: IN-EB: Search Results - State Case ID and Internal Case ID
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    Then I search with  Search Case "<SearchCase>" and Case ID "<CaseId>"
    Then I see the column for Case ID with the correct value "<CaseId>"
    Examples:
     | SearchCase     | CaseId   |
     | Internal       | 10       |
     | State          | 425wffs  |

  @CP-25077 @CP-25077-02 @chopa @crm-regression @ui-cc-in
  Scenario Outline: IN-EB: Search Results - Medicaid/RID Consumer ID and Internal Consumer ID
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    Then I search Consumer Type "<consumerType>" and Consumer ID "<consumerID>"
    Then I see the column for Consumer ID with the correct value "<consumerID>"
    Examples:
      | consumerType     | consumerID   |
      | Internal         | 10           |
      | MEDICAID/RID     | x98d1725     |

  @CP-25257 @CP-25257-01 @CP-25077-03 @chopa @crm-regression @ui-cc-in
  Scenario: IN-EB: Verify State Case ID is displayed in Active General Contact - Search Criteria
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    Then I see expected "State" on IN-EB Search Case component

  @CP-25257 @CP-25257-02 @CP-25077-04 @chopa @crm-regression @ui-cc-in
  Scenario: IN-EB: Verify State Case ID is displayed in Third Party Contact - Search Criteria
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    Then I navigate to Third Party page
    Then I see expected "State" on IN-EB Search Case component

  @CP-25257 @CP-25257-03 @chopa @crm-regression @ui-cc-in
  Scenario: IN-EB: Search Results - State Case ID in Active General Contact
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    Then I search with  Search Case "State" and Case ID "425wffs"
    Then I see the column for Case ID with the correct value "425wffs"

  @CP-25257 @CP-25257-04 @chopa @crm-regression @ui-cc-in
  Scenario: IN-EB: Search Results - State Case ID in Third Party Contact
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    Then I navigate to Third Party page
    Then I search with  Search Case "State" and Case ID "425wffs"
    Then I see the column for Case ID with the correct value "425wffs"

  @CP-25258 @CP-25258-01 @chopa @crm-regression @ui-cc-in
  Scenario: IN-EB: Verify State Case ID is displayed in header
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I navigate to case and consumer search page
    And I searched customer have First Name as "Adel" and Last Name as "Smith"
    And I click first consumer ID in search results on case and consumer search page
    Then I see the State Case ID value displayed underneath the Primary Individual's name

  @CP-25258 @CP-25258-02 @chopa @crm-regression @ui-cc-in
  Scenario: IN-EB: Verify State Case ID is displayed in Widged
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "Adel" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to case and consumer search page
    Then I see the State Case ID value displayed in Active Contact widget