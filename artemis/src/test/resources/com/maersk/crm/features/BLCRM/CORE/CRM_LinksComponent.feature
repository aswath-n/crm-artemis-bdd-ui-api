Feature: Links Component for Case, Consumer Profile, Task and Contact Record

  @CP-136 @CP-136-01 @aikanysh @crm-regression @ui-core
  Scenario: Verify Links Component for Case/Consumer linked to Contact Record
    Given I logged into CRM and click on initiate contact
    And I searched existing case where First Name as "Emma" and Last Name as "Jones"
    Then I link the contact to an existing Case
    And I verify that Links Component's uniqueID, name = "Case", type = "-- --", status date is present, and status = "-- --"

  @CP-136 @CP-136-02 @CP-45642 @aikanysh @khazar @crm-regression @ui-core
  Scenario: Verify Link Component for newly created Contact Record
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    Then I see new consumer is created and has a unique Consumer ID
    And I verify that Links Component has uniqueID, name equals to "Consumer Profile", type equals to "Consumer", status date is present, and status equals to "Active"

  @CP-136 @CP-136-03 @aikanysh @crm-regression @ui-core
  Scenario: Verify Links Component for Case/Consumer linked as "General" Task
    Given I logged into CRM and click on initiate contact
    And I searched existing case where First Name as "Emma" and Last Name as "Jones"
    Then I link the contact to an existing Case
    And I verify that Links Component's uniqueID, name = "Case", type = "-- --", status date is present, and status = "-- --"
    When I navigate to "General" task page
    And I verify that Links Components has 3 rows of data "Contact Record", "Emma Jones" and "Case"
    And I verify that "Contact Record" row  has status data equal to today's date
    And I verify that Consumer Profile row has type equal to "Consumer" and status equal to "Active"
    And I verify that Case row has type equal to "-- --" and status equal to "-- --"

  @CP-136 @CP-136-04 @aikanysh @crm-regression @ui-core
  Scenario Outline: Verify Links Component for newly created Third Party Contact
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for the third party and it is automatically linked
    And I verify that Links Component has uniqueID, name equals to "Consumer Profile", type equals to "Consumer", status date and status are present

    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Ellie      | Smith     | ABC Group         | Media         | English            |

  @CP-136 @CP-136-05 @aikanysh @crm-regression @ui-core
  Scenario Outline: Verify Links Component for newly created Third Party Contact and Task
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for the third party and it is automatically linked
    When I navigate to "General" task page
    And I verify that Links Components has 2 rows of data "Contact Record" and Consumer Profile
    And I verify that "Contact Record" row  has status data equal to today's date
    And I verify that Consumer Profile row has type equal to "Consumer" and status equal to "Active"
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Ellie      | Smith     | ABC Group         | Media         | English            |

  @CP-833 @CP-833-01 @aikanysh @ui-core @crm-regression
  Scenario: Link Contact Record to Task(s) Created During Active Contact | Verify Task Link Component
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    When I navigate to "General" task page
    And I select task type as "" priority as "" assignee as "" and I enter task info as "Task info"
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    And I verify Task Link Component has unique TaskId, name = "Task", type = "General", status date is present, and status = "Created"

  @CP-833 @CP-833-02 @aikanysh @ui-core @crm-regression
  Scenario Outline: Link Contact Record to Task(s) Created During Active Contact | Verify Contact Record Link Component
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    When I navigate to "General" task page
    And I select task type as "" priority as "" assignee as "Service" and I enter task info as "Task info"
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    And I verify Task Link Component has unique TaskId, name = "Task", type = "General", status date is present, and status = "Created"
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to "Task Search" page
    And I search Task by Task Id
    And I click on search button on task search page
    And I click on found task record
    And I verify Contact Record Component has unique ContactRecordId, name = "Contact Record", type = "General", status date is present, and status = "Complete"
    Examples:
      | contactType | contactChannelType |
      | Inbound     | Phone              |


  @CP-833 @CP-833-03 @aikanysh @ui-core @crm-regression
  Scenario Outline: Link Contact Record to Task(s) Created During Active Contact | Verify Task Link Component for ThirdParty CR
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I add New Consumer to the record for third party
    When I navigate to "General" task page
    And I select task type as "" priority as "" assignee as "" and I enter task info as "Task info"
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    And I verify Task Link Component has unique TaskId, name = "Task", type = "General", status date is present, and status = "Created"
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Jack       | Dawson    | OrgNameTest       | Media         | English            |

  @CP-833 @CP-833-04 @aikanysh @ui-core @crm-regression
  Scenario Outline: Link Contact Record to Task(s) Created During Active Contact | Verify Third Party Contact Record Link Component
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I add New Consumer to the record for third party
    When I navigate to "General" task page
    And I select task type as "" priority as "" assignee as "" and I enter task info as "Task info"
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    And I verify Task Link Component has unique TaskId, name = "Task", type = "General", status date is present, and status = "Created"
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to "Task Search" page
    And I search Task by Task Id
    And I click on search button on task search page
    And I click on found task record
    And I verify Contact Record Component has unique ContactRecordId, name = "Contact Record", type = "Third Party", status date is present, and status = "Complete"
    Examples:
      | contactType | contactChannelType | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Inbound     | Phone              | Jack       | Dawson    | OrgNameTest       | Media         | English            |
