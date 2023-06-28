@AutoAssignmentSREvents
Feature: Validation of Auto Assignment SR Event

  @CP-34267 @CP-34267-03 @priyal @ruslan @events-wf
  Scenario Outline: Validate in LINK_EVENT for AA SR
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I navigate to case and consumer search page
    When I searched customer have First Name as "Jessi" and Last Name as "Robwe"
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Auto Assignment SR" service request page
    And I will provide following information before creating task
      | priority         | 1|
    And I click on save button in create service request page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    And I initiate Event get api for trace id "<correlationId>"
    And I will run the Event GET API and get the payload
    And I verify link events are generated for a "SR" from "CUSTOM_PROVIDED,srToConsumer,consumerToSR,caseToSR,srToCase"
    And I will check "<eventName>" is mapping with "DPBI" Subscriber ID
    Then I will verify "<eventName>" publish to DPBI
    Examples:
      | eventName  | correlationId |
      | LINK_EVENT | Open          |