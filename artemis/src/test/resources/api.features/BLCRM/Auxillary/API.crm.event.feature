Feature: UI-API-CRM: Events

  @CP-658 @CP-658-1 @CP-808 @CP-808-1 @CP-4907 @CP-4907-01 @CP-3138-01 @CP-12204 @CP-12204-02 @aswath @Vidya @events @events_smoke_level_two
  Scenario Outline: Validation of LINK_EVENT payload
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "fnMGpjs" and Last Name as "lnhSLAW"
    Then I link the contact to an existing Case or Consumer Profile
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two link event from contact record and case payloads has correct data
    And I will get "CRToConsumer" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two link event from contact record and consumer payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName  | correlationId | projectName |
      | LINK_EVENT | CRToCase      |[blank]|

  @CP-658 @CP-658-2 @CP-808 @CP-808-2 @CP-4907 @CP-4907-02 @CP-3138-01 @CP-12204 @CP-12204-03 @CP-11700-04 @CP-11700  @kamil @events @events_smoke_level_two
  Scenario Outline: Validation of UNLINK_EVENT payload
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "fnMGpjs" and Last Name as "lnhSLAW"
    When I link the contact to an existing Case or Consumer Profile
    And I unlink previously selected Consumer from Active Contact Record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two unlink event from contact record and case payloads has correct data
    And I will get "UNLINKCRToConsumer" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two unlink event from contact record and consumer payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName    | correlationId  | projectName |
      | UNLINK_EVENT | UNLINKCRToCase |[blank]|

  @CP-658 @CP-658-3 @CP-808 @CP-808-3 @kamil @events
  Scenario Outline: Validation of LINK_EVENT payload after relinking the other case/consumer to same contact record
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "fnMGpjs" and Last Name as "lnhSLAW"
    When I link the contact to an existing Case or Consumer Profile
    And I unlink previously selected Consumer from Active Contact Record
    And I searched customer have First Name as "Emma" and Last Name as "Jones"
    When I link the contact to an existing Case or Consumer Profile
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two link event from contact record and case payloads has correct data
    And I will get "CRToConsumer" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two link event from contact record and consumer payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName  | correlationId | projectName |
      | LINK_EVENT | CRToCase      |[blank]|

  @CP-808 @CP-808-4  @kamil @events @events_smoke_level_two
  Scenario Outline: Validation of CONSUMER_AUTHENTICATION_SAVE_EVENT payload
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Emma" and Last Name as "Jones"
    Then I link the consumer for general contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify consumer authentication event payloads has correct data
    Then Verify genericFields are removed
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName                          | correlationId            | projectName |
      | CONSUMER_AUTHENTICATION_SAVE_EVENT | consumerAuthenticatedInd |[blank]|

  @CP-808 @CP-808-5 @CP-11998 @kamil @events
  Scenario Outline: Validation of CONSUMER_AUTHENTICATION_SAVE_EVENT payload after re-linking other case/consumer
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Emma" and Last Name as "Jones"
    Then I link the consumer for general contact
    And I unlink previously selected Consumer from Active Contact Record
    And I searched customer have First Name as "fnMGpjs" and Last Name as "lnhSLAW"
    Then I link the consumer for general contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify consumer authentication event payloads has correct data
    Then Verify genericFields are removed
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName                          | correlationId            | projectName |
      | CONSUMER_AUTHENTICATION_SAVE_EVENT | consumerAuthenticatedInd |[blank]|

  @CP-6042 @CP-6042-1 @kamil @events @events_smoke_level_two
  Scenario Outline:CONSUMER_AUTHENTICATION_SAVE_EVENT-authentications array has been replaced with consumerAuthentications
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on Unlink Contact Button on Active Contact Page
    And I search for a consumer that I just created
    And I link the consumer to authenticate the consumer
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify authentications array has been replaced with consumerAuthentications and response
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                          | module         | eventType      | projectName |
      | CONSUMER_AUTHENTICATION_SAVE_EVENT | CONTACT_RECORD | authentication |[blank]|

  @CP-11393 @CP-11393-03 @events @umid
  Scenario Outline:Validation Contact Record Save event to include spoken & written language
    Given I logged into CRM and click on initiate contact
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will verify spoken & written languages are included in the "<eventName>" payload
    Examples:
      | eventName                 | module         | eventType     | projectName |
      | CONTACT_RECORD_SAVE_EVENT | CONTACT_RECORD | contactrecord |[blank]|

  @CP-11393 @CP-11393-04 @umid @events
  Scenario Outline:Validation Contact Record Update event to include spoken & written language
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    And I select call campaign for outbound contact type
    When I select outcome of contact "Reached Successfully" for outbound
    And I close the current Contact Record and re-initiate a new Contact Record for Outbound
    And I search for a consumer that I just created
    And I link the consumer to authenticate the consumer
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I click on first Contact Record ID on Contact Record
    And I click on edit button on contact history page
    And I will take trace Id for Contact Record update Event for editing "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    And I will verify common contact record update event details
    And I will verify spoken & written languages are included in the "<eventName>" payload
    Examples:
      | eventName                   | module         | eventType     | projectName |
      | CONTACT_RECORD_UPDATE_EVENT | CONTACT_RECORD | contactrecord |[blank]|

  @CP-11700 @CP-11700-01 @kamil @events
  Scenario Outline: Verify UNLINK_EVENT in Contact Record Details accessed via Manual contact record search
    Given I logged into CRM and click on initiate contact
    When I searched existing case where First Name as "Lisa" and Last Name as "John"
    And I link the contact to an existing Case
    And  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And I choose "Provided Financial Information" option for Contact Action field
    And I close active contact after selecting all mandatory fields
    When I navigate to manual contact record search page
    And I search and select contact record created above with phone number
    When I click on edit button on contact details tab
    And And I unlink existing consumer and linking consumer fn "Harry" and ln ""
    And I click on Save button in Edit screen
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two unlink event from contact record and case payloads has correct data
    And I will get "UNLINKCRToConsumer" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two unlink event from contact record and consumer payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName    | correlationId  | projectName | InternalRefType | ExternalRefType | linkTo    |
      | UNLINK_EVENT | UNLINKCRToCase |[blank]| contact_record  | consumer        | Cont2Cons |


  @CP-11700 @CP-11700-02 @kamil @events
  Scenario Outline: Verify UNLINK_EVENT in Contact Record Details accessed via Manual Case/Consumer search
    Given I logged into CRM and click on initiate contact
    When I searched existing case where First Name as "fnUVuGx" and Last Name as "lnZJsbA"
    And I link the contact to an existing Case
    And  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And I choose "Provided Financial Information" option for Contact Action field
    And I close active contact after selecting all mandatory fields
    When I navigate to case and consumer search page
    And I search for record by value "fnUVuGx"
    And I click first contact record in search results on case and consumer search page
    And I navigate to case details tab
    And I click on the first contact id
    And I click on edit icon the Contact Details page
    And And I unlink existing consumer and linking consumer fn "Bruce" and ln "Wayne"
    And I click on Save button in Edit screen
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two unlink event from contact record and case payloads has correct data
    And I will get "UNLINKCRToConsumer" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two unlink event from contact record and consumer payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName    | correlationId  | projectName |
      | UNLINK_EVENT | UNLINKCRToCase |[blank]|


  @CP-11700 @CP-11700-03 @kamil @events-wf
  Scenario Outline: Verify UNLINK_EVENT when case/consumer is unlinked from a Task
    Given I logged into CRM
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo | CP2141             |
      | assignee | Service AccountOne |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnEIXno" and Last Name as "LnLaKwh"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I navigate to "Task Search" page
    And I populate required fields to do task search "","General","","today","","","","","","","","","","","","","",""
    And I click on search button on task search page
    And I navigate to latest task
    And I click on found task record for contact record and edit task
    Then I verify unlink button is displayed
    When I click on unlink button
    And I will check weather case or consumer is unlinked from task
    Then I save Link changes in Task
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I send GET Event API and get the payload
    Then I verify two unlink event from task and consumer payloads has correct data
    And I will get "UNLINKCRToConsumer" from traceId and pass it as end point
    And I initiated Event GET API
    And I send GET Event API and get the payload
    Then I verify two unlink event from task and case payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    Then I send Subscribers Record GET API and Verify response has eventId and "<eventName>" and subscriberId
    Examples:
      | eventName    | correlationId  | projectName |
      | UNLINK_EVENT | UNLINKCRToCase |             |

  @CP-2141 @CP-2141-08 @priyal @moldir @events-wf
  Scenario Outline: Validate LINK_EVENT for Manually Link a case/consumer to a task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo | cp-2141            |
      | assignee | Service AccountOne |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I navigate to View Task details
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnCCFZu" and Last Name as "Lnhjhcu"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button on task edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "General" task : "TASK TO CASE,CASE TO TASK"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    And I initiate Event get api for trace id "manually_link_consumer_to_task"
    And I will run the Event GET API and get the payload
    And I verify "LINK_EVENT" generated for "General" task : "TASK TO CONSUMER,CONSUMER TO TASK"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | projectName | eventName  | correlationId              |
      |             | LINK_EVENT | manually_link_case_to_task |

  @CP-2141 @CP-2141-09 @priyal @moldir @events-wf
  Scenario Outline: Validate UNLINK_EVENT for Manually Link a case/consumer to a task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I will provide following information before creating task
      | taskInfo | cp-2141            |
      | assignee | Service AccountOne |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnCCFZu" and Last Name as "Lnhjhcu"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    When I navigate to "My Task" page
    And I navigate to newly created task by clicking on TaskID column header
    And I navigate to View Task details
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    Then I verify unlink button is displayed
    When I click on unlink button
    And I will check weather case or consumer is unlinked from task
    And I click on save button on task edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify "UNLINK_EVENT" generated for "General" task : "TASK TO CASE,CASE TO TASK"
    And I will check "UNLINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "UNLINK_EVENT" publish to DPBI
    And I initiate Event get api for trace id "manually_unlink_consumer_from_task"
    And I will run the Event GET API and get the payload
    And I verify "UNLINK_EVENT" generated for "General" task : "TASK TO CONSUMER,CONSUMER TO TASK"
    And I will check "UNLINK_EVENT" is mapping with "DPBI" Subscriber ID
    Then I will verify "UNLINK_EVENT" publish to DPBI
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | projectName | eventName    | correlationId                  |
      |             | UNLINK_EVENT | manually_unlink_case_from_task |

  @CP-2141 @CP-2141-10 @priyal @moldir @events-wf
  Scenario Outline: Validate Task_Update_Event after Manually Link_Unlink a case/consumer to a task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I navigate to "General" task page
    And I click on save button in create task page
    #Then I verify Success message is displayed for task
    When I navigate to "Work Queue" page
    And I navigate to newly created task by clicking on TaskID column header
    And I navigate to View Task details
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    And I click on Link Case or Consumer button under LINK section
    Then I see Case& Consumer Search section display
    When I search for consumer have First Name as "FnCCFZu" and Last Name as "Lnhjhcu"
    And I click on Search option
    Then I should able to Manually Link the Task to a Case or Consumer by search operation
    When I expand the first record of the search result
    When I select Consumer radio button
    When I click on Link Record Consumer button
    And I click on save button on task edit page
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry |
    Then I verify unlink button is displayed
    When I click on unlink button
    And I will check weather case or consumer is unlinked from task
    And I click on save button on task edit page
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify task update event payloads has correct data
    Examples:
      | projectName | eventName         | correlationId           |
      |             | TASK_UPDATE_EVENT | Task_update_Link_Unlink |

  @API-CP-31858 @API-CP-31858-01 @Beka @events @API-CC-IN @API-CRM-Regression @events-cc
  Scenario Outline:Verification project fields object in Consumer Save Event
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated Case Loader API request
    And User send Api call with name "cc" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | randomstring::7 |
      | caseLoaderRequest[0].case.caseIdentificationNumberType                                          | State           |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | MEDICAID        |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | name::          |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | name::          |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                              | randomstring::7 |
    When I initiate Event POST API to get only 1 page
    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify response containse following data
      | projectFields[0] | DO_NOT_CALL                        |
      | projectFields[1] | MEDICALLY_FRAIL_CONFIRMATION_CD    |
      | projectFields[2] | MEDICALLY_FRAIL_LAST_ASSESSMNET_DT |
      | projectFields[3] | PMP_PAPER                          |
      | projectFields[4] | PREGNANCY_END_DATE                 |
      | projectFields[5] | PREGNANCY_START_DATE               |
      | projectFields[6] | RECIPIENT_INDICATOR_ACTIVE         |
      | projectFields[7] | RECIPIENT_WARD_COUNTY              |
      | projectFields[8] | RECIPIENT_WARD_TYPE                |
      | projectFields[9] | SPOUSE_MEMBER_ID                   |
    Examples:
      | eventName               | module   |
      | NEW_CONSUMER_PROFILE    | Consumer |
      | CONSUMER_PROFILE_CHANGE | Consumer |

  @CP-29181 @CP-29181-01 @Beka @events @CC-IN @CRM-Regression
  Scenario Outline: Capture update Consumer Business Even
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I click on initiate contact record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields with no county for a new consumer for IN-EB
    When I click on Create Consumer Button
    And I click on the Demographic Info Tab
    And I click on edit button
    And I add "423623462346234" external ID to update consumer profile
    When I initiate Event POST API to get only 1 page
    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify events response capture updated data
      | consumerIdExternal[0].consumerIdExternal | 423623462346234                        |
    Examples:
      | eventName               | module   |
      | CONSUMER_PROFILE_CHANGE | CONSUMER |

