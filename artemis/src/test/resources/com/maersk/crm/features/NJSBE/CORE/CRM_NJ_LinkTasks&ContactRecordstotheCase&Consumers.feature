Feature: NJ Link Tasks & Contact Records to the Case & Consumers located via Validate button

  @CP-12967 @CP-12967-01 @events @kamil
  Scenario Outline: Verify Select Validate & Link to Link General Contact
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click validate&link the contact to an existing Case or Consumer Profile for NJ-SBE
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "CRToCase" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two link event from contact record and case for NJ
    And I will get "CRToConsumer" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two link event from contact record and consumer for NJ
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName  | projectName |
      | LINK_EVENT | NJ-SBE      |


  @CP-12967 @CP-12967-01.1 @events @kamil
  Scenario Outline: Verify Select Validate & Link to Link General Contact for CONSUMER_AUTHENTICATION_SAVE_EVENT
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click validate&link the contact to an existing Case or Consumer Profile for NJ-SBE
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    And I will verify consumer authentication details for NJ
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                          | module         | eventType      | projectName |
      | CONSUMER_AUTHENTICATION_SAVE_EVENT | CONTACT_RECORD | authentication | NJ-SBE      |

  @CP-12967 @CP-12967-02 @events @kamil
  Scenario Outline: Verify Validate & Link button - 3rd Party Contact
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    And I click validate&link button for "third"
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "CRToCase" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two link event from contact record and case for NJ
    And I will get "CRToConsumer" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two link event from contact record and consumer for NJ
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName  | projectName |
      | LINK_EVENT | NJ-SBE      |

  @CP-12967 @CP-12967-03 @events @kamil
  Scenario Outline: Verify Display Validate button and then Link button - Edit Task Screen - link Consumer & Case
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    Then Creating "Review Complaint" task
    When I navigate to "Work Queue" page
    Then I navigating newly creating Task and clicking to see Task details
    Then I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I click validate and link Consumer & Case for task for edit task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "taskToCase" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to consumer for NJ
    And I will get "taskToContactRecord" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to Case for NJ
    And I will get "Task_update" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event with status "<status>"
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName  | projectName | status  |
      | LINK_EVENT | NJ-SBE      | Created |


  @CP-12967 @CP-12967-03.01 @events @kamil
  Scenario Outline: Verify TASK_UPDATE_EVENT - Edit Task Screen - link Consumer & Case
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    Then Creating "Review Complaint" task
    When I navigate to "Work Queue" page
    Then I navigating newly creating Task and clicking to see Task details
    Then I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I click validate and link Consumer & Case for task for edit task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event with with status "<status>" for Nj
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName         | correlationId | projectName | status  |
      | TASK_UPDATE_EVENT | Task_update   | NJ-SBE      | Created |


  @CP-12967 @CP-12967-03-1 @events @kamil
  Scenario Outline: Verify Display Validate button and then Link button - Edit Task Screen only Link Case
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    Then Creating "Review Complaint" task
    When I navigate to "Work Queue" page
    Then I navigating newly creating Task and clicking to see Task details
    Then I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I click validate and link only Link Case
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "caseToTask" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to Case for NJ
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName  | projectName |
      | LINK_EVENT | NJ-SBE      |


  @CP-12967 @CP-12967-03-1.1 @events @kamil
  Scenario Outline: Verify TASK_UPDATE_EVENT - Edit Task Screen only Link Case
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    Then Creating "Review Complaint" task
    When I navigate to "Work Queue" page
    Then I navigating newly creating Task and clicking to see Task details
    Then I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I click validate and link only Link Case
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event with with status "<status>" for Nj
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName         | correlationId | projectName | status  |
      | TASK_UPDATE_EVENT | Task_update   | NJ-SBE      | Created |


  @CP-12967 @CP-12967-04 @events @kamil
  Scenario Outline: Verify Display Validate button and then Link button - Create Task link Consumer & Case
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I click validate and link Consumer & Case for task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "taskToCase" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to consumer for NJ
    And I will get "taskToContactRecord" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to Case for NJ
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName  | projectName |
      | LINK_EVENT | NJ-SBE      |


  @CP-12967 @CP-12967-04.1 @events @kamil
  Scenario Outline: TASK_SAVE_EVENT - Create Task link Consumer & Case
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I click validate and link Consumer & Case for task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads has correct data for NJ
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Created       | NJ-SBE      |


  @CP-12967 @CP-12967-04-1 @events @kamil
  Scenario Outline: Verify Display Validate button and then Link button - Create Task link Case only
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I click validate and link only Link Case for Create Task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "caseToTask" from traceId for LINK Event and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify link event from task to Case for NJ
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName  | projectName |
      | LINK_EVENT | NJ-SBE      |



  @CP-12967 @CP-12967-04-1.1 @events @kamil
  Scenario Outline: TASK_SAVE_EVENT - Create Task link Case only
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click on primary individual record in search result
    Then I click validate and link only Link Case for Create Task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task save event payloads has correct data for NJ
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName       | correlationId | projectName |
      | TASK_SAVE_EVENT | Created       | NJ-SBE      |


  @CP-12967 @CP-12967-05 @events @kamil
  Scenario Outline: Verify Link button - Edit Contact Record Details
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    And I searched customer have First Name as "Harry" and Last Name as "Potter"
    And I click validate&link the contact to an existing Case or Consumer Profile for NJ-SBE
    And I unlink previously selected Consumer from Active Contact Record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "CRToCase" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two link event from contact record and case for NJ
    And I will get "CRToConsumer" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two link event from contact record and consumer for NJ
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName  | projectName |
      | LINK_EVENT | NJ-SBE      |


  @CP-12967 @CP-12967-06 @ui-cc-nj @nj-regression @kamil
  Scenario: Verify from UI Select Validate & Link to Link General Contact
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    And I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click validate&link the contact to an existing Case or Consumer Profile for NJ-SBE
    Then Verify Link event for "General Contact"


  @CP-12967 @CP-12967-07 @ui-cc-nj @nj-regression @kamil
  Scenario: Verify from UI Validate & Link button - 3rd Party Contact
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    And I searched existing case where First Name as "Queen" and Last Name as "Faith" on third party page
    And I click on primary individual record in search result
    And I click validate&link button for "third"
    Then Verify Link event for "Third party"


  @CP-12967 @CP-12967-08 @ui-cc-nj @nj-regression @kamil
  Scenario: Verify from UI Display Validate button and then Link button - Edit Task Screen
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    Then Creating "Review Complaint" task
    When I navigate to "Work Queue" page
    Then I navigating newly creating Task and clicking to see Task details
    Then I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I click validate and link Consumer & Case for task for edit task
    Then Verify Link event for "Edit Task"


  @CP-12967 @CP-12967-08-1 @ui-cc-nj @nj-regression @kamil
  Scenario: Verify from UI Display Validate button and then Link button - Edit Task Screen only Link Case
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    Then Creating "Review Complaint" task
    When I navigate to "Work Queue" page
    Then I navigating newly creating Task and clicking to see Task details
    Then I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I click validate and link only Link Case
    Then Verify Link event for "Edit Task only Link Case"


  @CP-12967 @CP-12967-09 @ui-cc-nj @nj-regression @kamil
  Scenario: Verify UI Display Validate button and then Link button - Create Task link Consumer & Case
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I click validate and link Consumer & Case for task
    When I navigate to "Work Queue" page
    Then I navigating newly creating Task and clicking to see Task details
    Then I click Edit task type button
    Then Verify Link event for "Create Task"


  @CP-12967 @CP-12967-09-1 @ui-cc-nj @nj-regression @kamil
  Scenario: Verify UI Display Validate button and then Link button - Create Task only Link Case
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    When I click on Link Case or Consumer button under LINK section
    And I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I click validate and link only Link Case for Create Task
    Then Verify Link event for "Create Task only Link Case"


  @CP-12967 @CP-12967-10 @ui-cc-nj @nj-regression @kamil
  Scenario: Verify from UI Link button - Edit Contact Record Details
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    And I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click validate&link the contact to an existing Case or Consumer Profile for NJ-SBE
    And I unlink previously selected Consumer from Active Contact Record
    Then Verify Link event for "Edit Contact Record"









