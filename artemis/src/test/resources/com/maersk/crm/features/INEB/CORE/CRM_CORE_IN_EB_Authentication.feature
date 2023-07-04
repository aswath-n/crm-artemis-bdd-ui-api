Feature: IN-EB: Configure Contact Record Authentication

  @CP-23011 @CP-23011-01 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @ui-core-smoke
  Scenario: Update Authentication Checkboxes - Home Address is Above the Grid - General CR
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I click on the "FIRST" Case Consumer this contact relates to in search result
    And I verify that "Unable to Authenticate" checkbox is available
    Then I verify system displays the Home Address above the grid, DOB and SSN with Authentication checkboxes

  @CP-23011 @CP-23011-02 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: Business Rules for Authentication Boxes- General CR
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I click on the "FIRST" Case Consumer this contact relates to in search result
    Then I verify display of "CONSUMER AUTHENTICATED" message by selecting FullName, HomeAddress, DOB and SSN checkboxes for IN-EB

  @CP-23011 @CP-23011-03 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: Business Rules for Authentication Boxes displays double dashes for null address value- General CR
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Mark" and Last Name as "Inman"
    And I click on the expand carrot for the "SECOND" Case Consumer in the search result
    Then I verify "-- --" data is displayed for the following in Contact Record Authentication
      | HOME ADDRESS |
    Then Verify in Authentication Grid - consumerID and program consumerID are not displayed

  @CP-23373 @CP-23373-01 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Add Authentication Grid to Third Party Contact : Consumer with Case part one
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I navigate to Third Party page
    When I Enter the following data in the Third Party Contact tab
      | SEARCH FIRST NAME | Jolie |
      | SEARCH LAST NAME  | Jones |
    And I click on the "FIRST" Case Consumer this contact relates to in search result
    Then I verify system displays the Home Address above the grid, DOB and SSN with Authentication checkboxes
    And I verify that StateCaseID and Medicaid RID are displayed with auth checkboxes
    And I verify that phone number of primary individual is displayed with auth checkbox

  @CP-23373 @CP-23373-02 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Add Authentication Grid to Third Party Contact: : Consumer with Case part two
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I navigate to Third Party page
    When I Enter the following data in the Third Party Contact tab
      | SEARCH FIRST NAME | Something |
      | SEARCH LAST NAME  | Someone   |
    And I click on the "FIRST" Case Consumer this contact relates to in search result
    Then I verify display of "CONSUMER AUTHENTICATED" message by selecting FullName, HomeAddress, DOB and SSN checkboxes for IN-EB

  @CP-23373 @CP-23373-03 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Add Authentication Grid to Third Party Contact: : Consumer with Case part three
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I navigate to Third Party page
    When I Enter the following data in the Third Party Contact tab
      | SEARCH FIRST NAME | Mark  |
      | SEARCH LAST NAME  | Inman |
    And I click on the expand carrot for the "SECOND" Case Consumer in the search result
    Then I verify "-- --" data is displayed for the following in Contact Record Authentication
      | HOME ADDRESS |
    Then Verify in Authentication Grid - consumerID and program consumerID are not displayed

  @CP-23373 @CP-23373-04 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Add Authentication Grid to Third Party Contact : Consumer without Case part four
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I navigate to Third Party page
    When I Enter the following data in the Third Party Contact tab
      | SEARCH FIRST NAME | Aika |
      | SEARCH LAST NAME  | Begi |
    And I click on the expand carrot for the "FIRST" Case Consumer in the search result
    Then I verify system displays the Home Address above the grid, DOB and SSN with Authentication checkboxes
    And I verify that Medicaid RID is displayed
    And I verify that phone number of primary individual is displayed with auth checkbox
    Then I verify display of "CONSUMER AUTHENTICATED" message by selecting FullName, HomeAddress, DOB and SSN checkboxes for IN-EB

  @CP-23373 @CP-23373-05 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Add Authentication Grid to Third Party Contact : Consumer without Case part five
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I navigate to Third Party page
    When I Enter the following data in the Third Party Contact tab
      | SEARCH FIRST NAME | ConsumerNOCaseNoAddress         |
      | SEARCH LAST NAME  | ConsumerNOCaseNoAddressLastName |
    And I click on the expand carrot for the "FIRST" Case Consumer in the search result
    Then I verify "-- --" data is displayed for the following in Contact Record Authentication
      | HOME ADDRESS |
    Then Verify in Authentication Grid - consumerID and program consumerID are not displayed

  @CP-26944 @CP-26944-01 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario Outline: IN_EB Display State Reported Physical Address in Auth Grid-Mailing
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Araz" and Last Name as "Ism"
    And I click on the "FIRST" Case Consumer this contact relates to in search result
    Then I verify display of "<addressType>"
    Examples:
      | addressType |
      | Mailing     |

  @CP-26944 @CP-26944-02 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario Outline: IN_EB Display State Reported Physical Address in Auth Grid-Physical
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "AikaPhysical" and Last Name as "BegiPhysical"
    And I click on the "FIRST" Case Consumer this contact relates to in search result
    Then I verify display of "<addressType>"
    Examples:
      | addressType |
      | Physical    |

  @CP-24752 @CP-24752-01 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Business Rules for Authentication Boxes - At Least 1 ID (State Case ID, Medicaid/RID or SSN) in Active Third Party Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I navigate to Third Party page
    When I Enter the following data in the Third Party Contact tab
      | SEARCH FIRST NAME | Jolie |
      | SEARCH LAST NAME  | Jones |
    And I click on the expand carrot for the "FIRST" Case Consumer in the search result
    Then I verify system displays the Home Address above the grid, DOB and SSN with Authentication checkboxes
    And I verify that StateCaseID and Medicaid RID are displayed with auth checkboxes
    Then I click on FullName, HomeAddress, DOB checkboxes and verify that Link Record not displayed for IN-EB
    And I verify display of Consumer Authenticated message by selecting FullName, HomeAddress, DOB and one of the following checkboxes for IN-EB
      | State Case ID |
      | Medicaid/RID  |
      | SSN           |

  @CP-24752 @CP-24752-02 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Business Rules for Authentication Boxes - At Least 1 ID (State Case ID, Medicaid/RID or SSN) in Active General Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    When I searched customer have First Name as "Jolie" and Last Name as "Jones"
    And I click on the "FIRST" Case Consumer this contact relates to in search result
    Then I verify system displays the Home Address above the grid, DOB and SSN with Authentication checkboxes
    Then I click on FullName, HomeAddress, DOB checkboxes and verify that Link Record not displayed for IN-EB
    And I verify display of Consumer Authenticated message by selecting FullName, HomeAddress, DOB and one of the following checkboxes for IN-EB
      | State Case ID |
      | Medicaid/RID  |
      | SSN           |

  @CP-28617 @CP-28617-01 @khazar @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Verify Medicaid/RID As Default Consumer ID in link section
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Jolie" and Last Name as "Jones"
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer for IN-EB
    Then I add Medicaid and save it
    When I click on Create Consumer Button
    Then I verify Medicaid ID displayed in link section as ID for the consumer

  @CP-32421 @CP-32421-01 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Hide Phone Number displayed in Case/Consumer Search - Active Contact - General Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Jolie" and Last Name as "Jones"
    Then I verify system does not display the Phone Number column on search result before expanding arrow

  @CP-32421 @CP-32421-02 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Hide Phone Number displayed in Case/Consumer Search - Active Contact - Third Party Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Jolie" and Last Name as "Jones"
    Then I verify system does not display the Phone Number column on search result before expanding arrow

  @CP-9483 @CP-9483-03 @CP-6042 @CP-6042-03 @moldir @ui-core-in-eb @crm-regression #Fails due to CP-48272
  Scenario Outline: Validate CONSUMER_AUTHENTICATION_SAVE_EVENT is generated and sent to DPBI when Consumer is successfully authenticated in INEB
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing CaseConsumer in "<projectName>"
    And Wait for 5 seconds
    When I navigate to the case and contact details tab
    And I will save contact record id from CASE & CONTACT DETAILS tab Contact History
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify CONSUMER_AUTHENTICATION_SAVE_EVENT response
    And Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      |eventName                          | module         | eventType      | projectName |
      |CONSUMER_AUTHENTICATION_SAVE_EVENT | CONTACT_RECORD | authentication | IN-EB        |