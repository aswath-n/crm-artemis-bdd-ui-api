Feature: Validate of JustCause SR Comunda

  @CP-25059 @CP-25059-01 @CP-25060 @CP-25060-01 @CP-25061 @CP-25061-01 @CP-25062 @CP-25062-01 @sr-camunda @ruslan
  Scenario: Conditionally Require Fields on Just Cause SR when Completing | Just Cause Follow-up Email Task | Just Cause Resolution | Just Cause State Discussion | Just Cause State Discussion |
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to case and consumer search page
    When I searched customer have First Name as "NANCY" and Last Name as "KIYAN"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I will provide following information before creating task
      | rid                 | 521521521512                                 |
      | contactName         | Alewa                                        |
      | contactPhone        | 5215215215                                   |
      | currentPlan         | Anthem                                       |
      | desiredPlanJC       | Anthem                                       |
      | programRequired     | Healthy Indiana Plan                         |
      | missingInfoRequired | No                                           |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "Just Cause Request" in Links section of "SR" page
    And I click on edit button on view task page
    And I scroll up the page
    And I scroll up the page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on id of "Just Cause Follow-up Email" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                    |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
    Then Verify I will see error message "Please provide the Date Follow-up Email Sent on the Just Cause SR before completing this task."
    When I click on continue on task details warning window
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on edit service request button
    And I will update the following information in edit task page
      | dateFollowupEmailSent | today                |
      | reasonForEdit         | Corrected Data Entry |
    And I click on save button on task edit page
    And I click on id of "Just Cause Follow-up Email" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                    |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                              |
      | reasonForEdit | Corrected Data Entry                  |
      | actionTaken   | Contacted Member                      |
      | disposition   | Contact Member - Successfully Reached |
    And I click on save button on task edit page
    Then Verify I will see error message "Please provide the MCE Decision on the Just Cause SR before completing this task."
    When I click on continue on task details warning window
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on edit service request button
    And I will update the following information in edit task page
      | decision      | Agree                |
      | reasonForEdit | Corrected Data Entry |
    And I click on save button on task edit page
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                              |
      | reasonForEdit | Corrected Data Entry                  |
      | actionTaken   | Contacted Member                      |
      | disposition   | Contact Member - Successfully Reached |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause State Discussion" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Contacted Member     |
    And I click on save button on task edit page
    Then Verify I will see error message "Please provide the Date State Notified, the Final Decision and if the consumer was satisfied on the Just Cause SR before completing this task."
    When I click on continue on task details warning window
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on edit service request button
    And I will update the following information in edit task page
      | reasonForEdit     | Corrected Data Entry |
      | consumerSatisfied | Yes                  |
      | finalDecision     | Approved             |
      | dateSateNotified  | today                |
    And I click on save button on task edit page
    When I click id of "Just Cause State Discussion" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Contacted Member     |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Decision Letter" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Sent Approval Letter |
    And I click on save button on task edit page
    Then Verify I will see error message "Please provide the Date Decision Letter Sent on the Just Cause SR before completing this task."
    When I click on continue on task details warning window
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on edit service request button
    And I will update the following information in edit task page
      | dateDecisionLetterSent | today                |
      | reasonForEdit          | Corrected Data Entry |
    And I click on save button on task edit page
    When I click id of "Just Cause Decision Letter" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Sent Approval Letter |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And Close the soft assertions

  @CP-24964 @CP-24964-01 @CP-26865 @CP-26865-01 @CP-24966 @CP-24966-01 @CP-26867 @CP-26867-01 @CP-26866 @CP-26866-03 @sr-camunda @ruslan
  Scenario: Validate Link Follow-Up Email Task | Just Cause Resolution Task | Just Cause State Discussion | Just Cause Decision Letter
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "NANCY" and Last Name as "JAKOBE"
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I can minimize Active Contact Widget
    And I will provide following information before creating task
      | rid                    | 521521521512                                 |
      | contactName            | Alewa                                        |
      | contactPhone           | 5215215215                                   |
      | currentPlan            | Anthem                                       |
      | desiredPlanJC          | Anthem                                       |
      | programRequired        | Healthy Indiana Plan                         |
      | missingInfoRequired    | No                                           |
      | decision               | Agree                                        |
      | consumerSatisfied      | Yes                                          |
      | dateDecisionLetterSent | today                                        |
      | dateFollowupEmailSent  | today                                        |
      | dateSateNotified       | today                                        |
      | finalDecision          | Approved                                     |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "Just Cause Request" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Follow-up Email,Just Cause Request" on "SR" link section
    When I click id of "Just Cause Follow-up Email" in Links section
    Then I verify "Just Cause Follow-up Email" link section has all the business object linked : "Service Request,Consumer,Case"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                    |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Follow-up Email,Just Cause Request,Just Cause Resolution" on "SR" link section
    When I click id of "Just Cause Resolution" in Links section
    Then I verify "Just Cause Resolution" link section has all the business object linked : "Service Request,Consumer,Case"
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                              |
      | reasonForEdit | Corrected Data Entry                  |
      | actionTaken   | Contacted Member                      |
      | disposition   | Contact Member - Successfully Reached |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Follow-up Email,Just Cause Request,Just Cause Resolution,Just Cause State Discussion" on "SR" link section
    When I click id of "Just Cause State Discussion" in Links section
    Then I verify "Just Cause State Discussion" link section has all the business object linked : "Service Request,Consumer,Case"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | Contacted Member     |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify "Just Cause" contains : "Just Cause Follow-up Email,Just Cause Request,Just Cause Resolution,Just Cause State Discussion,Just Cause Decision Letter" on "SR" link section
    When I click id of "Just Cause Decision Letter" in Links section
    Then I verify "Just Cause Decision Letter" link section has all the business object linked : "Service Request,Consumer,Case"
    And Close the soft assertions

  @CP-28460 @CP-28460-01 @CP-26864 @CP-26864-01 @CP-24962 @CP-24962-01 @sr-camunda @ruslan
  Scenario: Start JC Outbound Call attempts if MCE Decision is Disagree and Just Cause Resolution Task Disposition = Contact Member Did Not Reach
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    When I searched customer have First Name as "NANCY" and Last Name as "JAKOBE"
    And I link the contact to an existing Case or Consumer Profile
    And I store external case id from Demographic Info Page
    And I store external consumer id from Demographic Info Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Just Cause" service request page
    And I can minimize Active Contact Widget
    And I will provide following information before creating task
      | rid                   | 521521521512                                 |
      | contactName           | Alewa                                        |
      | contactPhone          | 5215215215                                   |
      | currentPlan           | Anthem                                       |
      | desiredPlanJC         | Anthem                                       |
      | programRequired       | Healthy Indiana Plan                         |
      | missingInfoRequired   | No                                           |
      | decision              | Disagree                                     |
      | dateFollowupEmailSent | today                                        |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I click on id of "Just Cause Request" in Links section of "SR" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete             |
      | reasonForEdit | Corrected Data Entry |
      | actionTaken   | No Action Taken      |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Follow-up Email" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                    |
      | reasonForEdit | Corrected Data Entry        |
      | actionTaken   | Sent Follow-up Email to MCE |
    And I click on save button on task edit page
    And Wait for 250 seconds
    And I click on id of "Just Cause" in Links section of "Task" page
    When I click id of "Just Cause Resolution" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                       |
      | reasonForEdit | Corrected Data Entry           |
      | actionTaken   | Contacted Member               |
      | disposition   | Contact Member - Did Not Reach |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on id of "JC Outbound Call" in Links section of "Task" page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                          |
      | reasonForEdit | Corrected Data Entry                              |
      | actionTaken   | Contacted Consumer - Did Not Reach/Left Voicemail |
      | contactName   | Alewa                                             |
      | disposition   | Did Not Reach Consumer                            |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                          |
      | reasonForEdit | Corrected Data Entry                              |
      | actionTaken   | Contacted Consumer - Did Not Reach/Left Voicemail |
      | contactName   | Alewa                                             |
      | disposition   | Did Not Reach Consumer                            |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    And I click on task id with status created in the link section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                          |
      | reasonForEdit | Corrected Data Entry                              |
      | actionTaken   | Contacted Consumer - Did Not Reach/Left Voicemail |
      | contactName   | Alewa                                             |
      | disposition   | Did Not Reach Consumer                            |
    And I click on save button on task edit page
    And I click on id of "Just Cause" in Links section of "Task" page
    Then I verify link section contains 3 "JC Outbound Call"
    When I click id of "Just Cause State Discussion" in Links section
    Then I verify "Just Cause State Discussion" link section has all the business object linked : "Service Request,Consumer,Case"
    And Close the soft assertions