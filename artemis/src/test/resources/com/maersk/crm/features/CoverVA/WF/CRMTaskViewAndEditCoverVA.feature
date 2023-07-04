Feature: Validate task in view and edit page

  @CP-19295 @CP-19295-02 @CP-34498 @CP-34498-21 @CP-38195 @CP-38195-02-09 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario Outline: Validate DMAS To CVIU Eligibility Escalation task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | VACMS CASE ID                     |
      | MMIS MEMBER ID                    |
      | APPLICATION ID                    |
      | CONNECTIONPOINT CONTACT RECORD ID |
      | CONNECTIONPOINT SR ID             |
      | CONNECTIONPOINT TASK ID           |
      | CONTACT REASON                    |
      | DUE DATE                          |
      | ACTION TAKEN                      |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Complete |
      | assignee              ||
      | priority              ||
      | taskInfo              | ABC      |
      | documentDueDate       ||
      | contactRecordSingle   ||
      | externalApplicationId ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    And I verify minimum lenght error message "TASK INFORMATION"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify "actionTakenSingle" single select drop down value
      | Disputed RICKI (maersk)             |
      | Escalated to CoverVA (DMAS)          |
      | Request Complete (maersk)           |
      | Request in Progress (maersk)        |
      | Returning - Sent in Error (maersk)  |
      | Unable to Complete Request (maersk) |
    Then I verify "contactReason" single select drop down value
      | Consumer Complaint     |
      | Customer Service Issue |
      | HIPPA                  |
      | Other                  |
      | PINK                   |
      | SLA Not Met            |
      | Worker Error           |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | MMIS Member ID       |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    And Close the soft assertions
    Examples:
      | taskId | taskType                            | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || DMAS to CVIU Eligibility Escalation ||        ||          ||            ||                || false         ||            ||          ||           ||

  @CP-19158 @CP-19158-02 @CP-34498 @CP-34498-23 @CP-38195 @CP-38195-02-11 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario Outline: Validate DMAS to QA Escalation task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | VACMS CASE ID                     |
      | MMIS MEMBER ID                    |
      | APPLICATION ID                    |
      | CONNECTIONPOINT CONTACT RECORD ID |
      | CONNECTIONPOINT SR ID             |
      | CONNECTIONPOINT TASK ID           |
      | CONTACT REASON                    |
      | DUE DATE                          |
      | ACTION TAKEN                      |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status                | Complete |
      | assignee              ||
      | priority              ||
      | taskInfo              | ABC      |
      | documentDueDate       ||
      | contactRecordSingle   ||
      | externalApplicationId ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    And I verify minimum lenght error message "TASK INFORMATION"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify "actionTakenSingle" single select drop down value
      | Disputed RICKI (maersk)             |
      | Escalated to CoverVA (DMAS)          |
      | Request Complete (maersk)           |
      | Request in Progress (maersk)        |
      | Returning - Sent in Error (maersk)  |
      | Unable to Complete Request (maersk) |
    Then I verify "contactReason" single select drop down value
      | HIPPA        |
      | Other        |
      | SLA Not Met  |
      | Worker Error |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | MMIS Member ID       |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    And Close the soft assertions
    Examples:
      | taskId | taskType              | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || DMAS to QA Escalation ||        ||          ||            ||                || false         ||            ||          ||           ||

  @CP-19144 @CP-19144-02 @CP-34498 @CP-34498-24 @CP-38195 @CP-38195-02-13 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario Outline: Validate DMAS to Mailroom Escalation task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | VACMS CASE ID                     |
      | MMIS MEMBER ID                    |
      | APPLICATION ID                    |
      | CONNECTIONPOINT CONTACT RECORD ID |
      | CONNECTIONPOINT SR ID             |
      | CONNECTIONPOINT TASK ID           |
      | CONTACT REASON                    |
      | DUE DATE                          |
      | ACTION TAKEN                      |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority              ||
      | businessUnitAssigneeTo||
      | assignee              ||
      | status                | Complete |
      | taskInfo              | ABC      |
      | documentDueDate       ||
      | contactRecordSingle   ||
      | externalApplicationId ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    And I verify minimum lenght error message "TASK INFORMATION"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify "actionTakenSingle" single select drop down value
      | Escalated to CoverVA (DMAS)          |
      | Request Complete (maersk)           |
      | Request in Progress (maersk)        |
      | Returning - Sent in Error (maersk)  |
      | Unable to Complete Request (maersk) |
    Then I verify "contactReason" single select drop down value
      | HIPPA        |
      | Late Mailing |
      | Other        |
      | SLA Not Met  |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | MMIS Member ID       |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    And Close the soft assertions
    Examples:
      | taskId | taskType                    | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || DMAS to Mailroom Escalation ||        ||          ||            ||                || false         ||            ||          ||           ||

  @CP-19280 @CP-19280-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Newborn Inquiries task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | VACMS CASE ID  |
      | MMIS MEMBER ID |
      | CONTACT REASON |
      | ACTION TAKEN   |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority            ||
      | status              | Complete |
      | taskInfo            | ABC      |
      | contactRecordSingle ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify minimum lenght error message "TASK INFORMATION"
    And I verify "actionTaken" multi select drop down value
      | Escalated to DMAS          |
      | Other                      |
      | Reclassified Document Type |
      | Uploaded to DMIS           |
    Then I verify "contactReason" single select drop down value
      | Eligibility Status |
      | Enrollment Status  |
      | Other              |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID  |
      | MMIS Member ID |
    And Close the soft assertions
    Examples:
      | taskId | taskType          | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Newborn Inquiries ||        ||          ||            ||                || false         ||            ||          ||           ||

  @CP-18989 @CP-18989-02 @CP-34498 @CP-34498-25 @CP-38195 @CP-38195-02-06 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario Outline: Validate CVIU Eligibility to DMAS Escalation task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | VACMS CASE ID                     |
      | MMIS MEMBER ID                    |
      | APPLICATION ID                    |
      | CONNECTIONPOINT CONTACT RECORD ID |
      | CONNECTIONPOINT SR ID             |
      | CONNECTIONPOINT TASK ID           |
      | CONTACT REASON                    |
      | DUE DATE                          |
      | ACTION TAKEN                      |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority              ||
      | assignee              ||
      | status                | Complete |
      | taskInfo              | ABC      |
      | documentDueDate       ||
      | contactRecordSingle   ||
      | externalApplicationId ||
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "DUE DATE "
    And I verify minimum lenght error message "TASK INFORMATION"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify "actionTakenSingle" single select drop down value
      | Escalated to DMAS (maersk)       |
      | Request Complete (DMAS)           |
      | Request in Progress (DMAS)        |
      | Returning - Sent in Error (DMAS)  |
      | Unable to Complete Request (DMAS) |
    Then I verify "contactReason" single select drop down value
      | Coverage Correction (DMAS) |
      | Coverage Correction (HPE)  |
      | ECPR Review                |
      | Emergency Services         |
      | Medicare Review            |
      | Other                      |
      | SAVE/SVES Request          |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | MMIS Member ID       |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    And Close the soft assertions
    Examples:
      | taskId | taskType                            | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || CVIU Eligibility to DMAS Escalation ||        ||          ||            ||                || false         ||            ||          ||           ||

  @CP-18023 @CP-18023-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate HPE Inquiries task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | VACMS CASE ID  |
      | MMIS MEMBER ID |
      | CONTACT REASON |
      | ACTION TAKEN   |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority            ||
      | status              | Complete |
      | taskInfo            | ABC      |
      | contactRecordSingle ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "PRIORITY"
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify minimum lenght error message "TASK INFORMATION"
    And I verify "actionTaken" multi select drop down value
      | Escalated to DMAS          |
      | Other                      |
      | Reclassified Document Type |
      | Uploaded to DMIS           |
    Then I verify "contactReason" single select drop down value
      | Eligibility Status |
      | Enrollment Status  |
      | Other              |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID  |
      | MMIS Member ID |
    And Close the soft assertions
    Examples:
      | taskId | taskType      | srType | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || HPE Inquiries ||        ||          ||            ||                || false         ||            ||          ||           ||

  @CP-19193 @CP-19193-02 @CP-19544 @CP-19544-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario Outline: Validate Review Appeal Notification task in edit/view page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    Then I verify task following fields are present on view page
      | VACMS CASE ID |
      | RECEIVED DATE |
      | DISPOSITION   |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | priority     ||
      | status       | Complete |
      | taskInfo     | ABC      |
      | receivedDate ||
    And I click on save button on task edit page
    And I verify task mandatory fields error message "PRIORITY"
    And I verify minimum lenght error message "TASK INFORMATION"
    Then I verify task mandatory fields error message "RECEIVED DATE "
    Then I verify "disposition" single select drop down value
      | Appeal Request         |
      | Appeal Withdrawal      |
      | Fair Hearing Decision  |
      | Other                  |
      | Pre-Hearing Conference |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID |
      | Received Date |
    And Close the soft assertions
    Examples:
      | taskId | taskType                   | srType | status   | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      || Review Appeal Notification || Complete ||          ||            ||                || false         ||            ||          ||           ||
