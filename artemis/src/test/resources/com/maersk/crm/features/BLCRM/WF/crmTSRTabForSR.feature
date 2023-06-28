Feature: TSR Tab Page for SR

  @CP-9838 @CP-9838-02 @CP-15986 @CP-15986-01 @CP-9134 @CP-9134-01 @CP-9839 @CP-9839-01 @CP-2389 @CP-2389-01 @CP-15323 @CP-15323-01 @vidya @priyal @elvin @crm-regression @ui-wf
  Scenario: Verify Service request does not have initiate button in case/consumer profile
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "Ryan" and Last Name as "Tim"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "General Two SR" service request page
    And I will provide following information before creating task
      | taskInfo                 |Test-CP-8938 $ allow Special Character &%|
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    Then I Verify "General Two SR" SR with "Open" status does not have Initiate button in TSR tab
    Then I verify all fields are displayed on Service Request Summary page
    Then I verify all fields SR values on Task & Service Request Tab
    When I expand the first row in SR list
    Then I verify all headers are displayed in SR service request tab
    Then I verify the SR details are displayed when expanded
    And I click on first SR ID
    Then I verify the SR details displayed on view SR page
    And I verify SR id and edit SR button are displayed
    And I verify the link ids are displayed
    And I click on edit service request button
    And I will update the following information in edit task page
      | taskInfo      | CP-2389  $ allow Special Character &% in edit task page|
      | reasonForEdit | Corrected Data Entry |
      | priority      | 5                    |
    And I click on save button on task edit page
    And I verify SR id and edit SR button are displayed
    Then I verify the SR details displayed on view SR page
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page
    And Close the soft assertions

  @CP-9838 @CP-9838-03 @CP-15986 @CP-15986-02 @CP-9838 @CP-9838-01 @vidya @crm-regression @ui-wf
  Scenario: Verify Service request does not have initiate button in consumer profile
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General Service Request" service request page
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "ChristaBLYwz" and Last Name as "McKenzieoVVum"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    And I select the record whose Case ID Blank
    When I click on Link Record Consumer button
    Then I verify task is linked with Consumer ID
    And I click on save button in create service request page
    #Then I verify Success message is displayed for SR
    And I verify user navigate back to home page
    When I click on initiate contact record
    When I searched customer have First Name as "ChristaBLYwz" and Last Name as "McKenzieoVVum"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    Then I Verify "General Service Request" SR with "Open" status does not have Initiate button in TSR tab
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    Then I verify service request id which I get from response is does not appear in work queue
    And Close the soft assertions

  @CP-20720 @CP-20720-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Validate CaseId filed is not present in SR list on TSR Tab
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "MKYra" and Last Name as "Zxczxaa"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created SR in Task & Service Request Tab
    When I expand the first row in SR list
    Then I will check CaseId field is not present in TSR tab
    And Close the soft assertions

  @CP-9134 @CP-9134-02 @priyal @crm-regression @ui-wf
  Scenario: Verify Service request does not have initiate button in case/consumer profile
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on initiate a contact button
    When I searched customer have First Name as "TIMScPv" and Last Name as "gmXTZfN"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I Verify user is navigate back to Task and service Request Page
    Then I verify the pagination in SR List present in TSR tab
    And Close the soft assertions

  @CP-9134 @CP-9134-03 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
 Scenario Outline: Validate CaseId filed is not present in SR list on TSR Tab
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Vally" and Last Name as "AAAAA"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I will not see the Service Request records in the Task List grid "<serviceRequest>"
    And Close the soft assertions
    Examples:
      | serviceRequest |
      | App Renewal SR V1|
      | Appeals SR|
      | App SR V1|
      | Renewal SR|
      | Application Renewal|
      | Complaint SR|

  @CP-9134 @CP-9134-04 @CP-33898 @CP-33898-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate CaseId filed is not present in SR list on TSR Tab
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Vally" and Last Name as "AAAAA"
    And I select records count in pagination dropdown as "show 50" in "Case Consumer Search" page
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I will verify default sorting order in SR list present on TSR tab
    And Close the soft assertions

  @CP-9134 @CP-9134-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario: Validate CaseId filed is not present in SR list on TSR Tab
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Vally" and Last Name as "AAAAA"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I will verify default sorting order of closed SR present in SR list on TSR tab
    And Close the soft assertions

  @CP-9134 @CP-9134-06 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario Outline: Validation of sort order based on different colum names by default
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Markell" and Last Name as "Raphael"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I click on "<columnTitle>" column on SR List
    Then I verify SR records are displayed in ascending order of their "<columnTitle>"
    And Close the soft assertions
    Examples:
      | columnTitle |
      | SR ID|
      | TYPE|
      | PRIORITY|
      | STATUS|
      | STATUSDATE|
      | CONSUMER NAME|

  @CP-9134 @CP-9134-07 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal
  Scenario Outline: Validation of sort order based on different colum names after double clicking
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Vally" and Last Name as "AAAAA"
    When I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I double click on "<columnTitle>" column on SR List
    Then I verify SR records are displayed in descending order of their "<columnTitle>"
    And Close the soft assertions
    Examples:
      | columnTitle |
   #   | SR ID|
      | TYPE|
   #   | PRIORITY|
   #   | STATUS|
   #   | STATUSDATE|
   #   | CONSUMER NAME|