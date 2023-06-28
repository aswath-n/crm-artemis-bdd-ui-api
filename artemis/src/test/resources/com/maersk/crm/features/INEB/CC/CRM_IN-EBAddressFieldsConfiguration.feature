Feature: IN-EB Configured Address fields

  @CP-25310 @CP-25310-02 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Active Contact Advanced search - Verification City field has all expected options
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I click on advance search icon
    And I see "searchCity" field has all expected options on IN-EB

  @CP-25310 @CP-25310-03 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Active Contact Advanced search - Verification County field has all expected options
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I click on advance search icon
    And I see "searchCounty" field has all expected options on IN-EB

  @CP-25310 @CP-25310-04 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification City field has all expected options
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see "City" field has all expected options on IN-EB

  @CP-25310 @CP-25310-05 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification County field has all expected options
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I see "County" field has all expected options on IN-EB

  @CP-25310 @CP-25310-06 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Manual Advanced search - Verification City field has all expected options
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to Manual Consumer search page
    And I click on advance search icon
    And I see "searchCity" field has all expected options on IN-EB

  @CP-25310 @CP-25310-07 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Manual advanced search - Verification County field has all expected options
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to Manual Consumer search page
    And I click on advance search icon
    And I see "searchCounty" field has all expected options on IN-EB

  @CP-25310 @CP-25310-08 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification City field has all expected options Add address within a case
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "Something" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab for contact and click on add for "address"
    And I see "City" field has all expected options on IN-EB

  @CP-25310 @CP-25310-09 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification County field has all expected options Add address within a case
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "Something" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab for contact and click on add for "address"
    And I see "County" field has all expected options on IN-EB

  @CP-25310 @CP-25310-10 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification City field has all expected options edit address within a case
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Adel" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I expend the Address Record on Contact Info Page
    And I see "City" field has all expected options on IN-EB

  @CP-25310 @CP-25310-11 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification County field has all expected options edit address within a case
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Adel" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I expend the Address Record on Contact Info Page
    And I see "County" field has all expected options on IN-EB

  @CP-25310 @CP-25310-12 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification City field has all expected options edit Address for consumer
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "Georg" and Last Name as "Washington"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I expend the Address Record on Contact Info Page
    And I see "City" field has all expected options on IN-EB

  @CP-25310 @CP-25310-13 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Verification County field has all expected options edit Address for consumer
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    And I searched customer have First Name as "Georg" and Last Name as "Washington"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I expend the Address Record on Contact Info Page
    And I see "County" field has all expected options on IN-EB

