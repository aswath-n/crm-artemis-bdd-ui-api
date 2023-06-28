Feature: Outbound Correspondence Links part2

######################## CP-37551 ########################################################

  @CP-37551  @CP-37551-01  @ui-ecms2 @Keerthi
  Scenario: Verify warning messages if OB Correspondence Endpoint excludes External Links RefType or RefId
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I validate warning message for missing "externalRefType" for external OB links
      | externallink | empty,1234 |
    And I validate warning message for missing "externalRefId" for external OB links
      | externallink | SERVICE_REQUEST,empty |
    And I validate warning message for missing "externalRefId_and_externalRefType" for external OB links
      | externallink | empty,empty |


  @CP-37551  @CP-37551-02  @ui-ecms2 @Keerthi
  Scenario: Verify OB Correspondence Endpoint to Allow Requester to Include External Links to Create
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General Two SR" for project ""
    And I will provide required information to create external sr with "8834" "" "" "" ""
      | Disposition |
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId        | 132898                        |
      | ConsumerId    | 226229                        |
      | type          | CCONLY                        |
      | externallink1 | SERVICE_REQUEST,CRMTaskId     |
      | externallink2 | TASK,60133                    |
      | externallink3 | INBOUND_CORRESPONDENCE,221395 |
      | externallink4 | OUTBOUND_CORRESPONDENCE,8928  |
      | externallink5 | APPLICATION,68304             |
      | externallink6 | MISSING_INFO,221392           |
      | externallink7 | CASE,656                      |
      | externallink8 | CONSUMER,283688                |
      | externallink9 | CONTACT_RECORD,375132         |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate navigation back to "52890" in OB details page
    And I validate navigation back to "60133" in OB details page
    And I validate navigation back to "221395" in OB details page
    And I validate navigation back to "8928" in OB details page
    And I validate navigation back to "68304" in OB details page
    And I validate navigation back to "221392" in OB details page
    And I validate navigation back to "656" in OB details page
    And I validate navigation back to "283688" in OB details page
    And I validate navigation back to "375132" in OB details page
    And I validate navigation back to "previouslyCreatedConsumerId" in OB details page
    And I validate navigation back to "previouslyCreatedCaseId" in OB details page

  @CP-37551  @CP-37551-03  @ui-ecms2 @Keerthi
  Scenario: Verify LINK_EVENT for OB External Links
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | type         | CCONLY                 |
      | externallink | SERVICE_REQUEST,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I validate "LINK_EVENT" for OB external links with internal_externalId value as "random"

    ######################## CP-35144 ########################################################

  @CP-35144 @CP-35144-01  @ui-ecms2 @burak
  Scenario: Verify Viewing Display Column Details of SR linked to OB
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General Two SR" for project ""
    And I will provide required information to create external sr with "8834" "" "" "" ""
      | Disposition |
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                    |
      | ConsumerId   | Random                    |
      | type         | CCONLY                    |
      | externallink | SERVICE_REQUEST,CRMTaskId |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the service request has the proper values
      | serviceRequestType | General Two SR |

  @CP-35144 @CP-35144-02  @ui-ecms2 @burak
  Scenario: Verify Viewing Display Column Details of SR linked to OB after Closing the SR
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General Service Request" for project ""
    And I will provide required information to create external sr with "8834" "" "" "" ""
      | Disposition |
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                    |
      | ConsumerId   | Random                    |
      | type         | CCONLY                    |
      | externallink | SERVICE_REQUEST,CRMTaskId |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on the link to the Service Request that is linked to the Inbound Document
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed                  |
      | reasonForEdit | Entered Additional Info |
      | disposition   | General SR Closed       |
    And I click on save button on task edit page
    And I will click on back arrow on view sr page
    Then I should see the Link to the service request has the proper values
      | serviceRequestType | General Service Request |

  @CP-35144 @CP-35144-03  @ui-ecms2 @burak
  Scenario: Verify Viewing Display Column Details of SR which has special characters and max length
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                |
      | ConsumerId   | Random                |
      | type         | CCONLY                |
      | externallink | SERVICE_REQUEST,92311 |
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the service request has the proper values
      | serviceRequestType |ServiceRequestWWW!./';WWWWWWWqw123124124124./;1-`=|