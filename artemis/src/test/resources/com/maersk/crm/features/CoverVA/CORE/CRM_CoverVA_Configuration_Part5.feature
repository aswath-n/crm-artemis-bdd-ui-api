Feature: CoverVA Contact Record Configurations Part 5


  @CP-40099 @CP-40099-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: Facility Name Field after selection of CVIU-Other as Business Unit-Active Page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the Contact Record with facility name text free "Escalated to DMAS"

  @CP-40099 @CP-40099-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: Facility Type Dropdown Field after Facility Name Field Text Entry
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I select CVUI other to observe the facility name text free and facility type fields dropdown
      | Department of Corrections      |
      | Department of Juvenile Justice |
      | Local Jail                     |
      | Regional Jail                  |

  @CP-40099 @CP-40099-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: Facility Name Field after selection of CVIU-Other as Business Unit-Edit
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the Contact Record with facility name text free "Escalated to DMAS"
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page new
    Then I updated the saved facility name text free field and facility type in edit page
      | Department of Corrections      |
      | Department of Juvenile Justice |
      | Local Jail                     |
      | Regional Jail                  |

  @CP-22097 @CP-22097-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil @aikanysh
  Scenario:Email field will be optional to save and complete the Contact record on an Active Contact or the Edit Contact screen
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    Then I will verify Email field will be optional to save with Channel is set to "Web Chat"
    When I navigate to manual contact record search page
    Then I searching for contact with Application ID
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    Then I will verify Email field will be optional for the Edit Contact screen


  @CP-21195 @CP-21195-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario:Verify Application ID to Expanded Contact Search Result
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    Then I click on Call Managment button
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with Application ID
    Then I will expand the contact record and will see a read only version of the Application ID field

  @CP-22536 @CP-22536-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh
  Scenario: Add Optional Phone Field to Web Chat Contact to save and complete the Contact:
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    Then I will verify Phone field will be optional to save with Channel is set to "Web Chat"
    When I navigate to manual contact record search page
    Then I searching for contact with Application ID
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    Then I will verify Phone field will be optional for the Edit Contact screen

  @CP-22536 @CP-22536-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh #fails due to CP-24721
  Scenario: Phone Field for Web Chat Contact Record is saved/displayed
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    Then I will save Phone field for "Web Chat" channel
    When I navigate to manual contact record search page
    Then I searching for contact with Application ID
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    Then I will verify Phone field is displayed for saved contact record

