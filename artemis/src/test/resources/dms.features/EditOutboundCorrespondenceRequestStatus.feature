@CP-2934
Feature: Edit Outbound Correspondence Request status

  @CP-2934 @CP-2934-01 @ui-ecms2 @Keerthi
  Scenario: Verify OB correspondence status dropdown values for Requested status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence status is "Requested"
    And I click on edit button on view Outbound Correspondence details
    Then I validate the "Correspondence status" dropdown values in outbound correspondence
      | On Hold  |
      | Canceled |

  @CP-2934 @CP-2934-02 @ui-ecms2 @Keerthi
  Scenario: Verify OB correspondence status dropdown values for On Hold status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I update Outbound Correspondence CId "previouslyCreated" to status "On Hold"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence status is "On Hold"
    And I click on edit button on view Outbound Correspondence details
    Then I validate the "Correspondence status" dropdown values in outbound correspondence
      | Requested |
      | Canceled  |

  @CP-2934 @CP-2934-03 @ui-ecms2 @Keerthi
  Scenario: Verify OB correspondence status dropdown values for Canceled status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I update Outbound Correspondence CId "previouslyCreated" to status "Canceled"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence status is "Canceled"
    And I click on edit button on view Outbound Correspondence details
    Then I validate the "Correspondence status" dropdown values in outbound correspondence
      |  empty |



  @CP-2934 @CP-2934-04 @ui-ecms2 @Keerthi
  Scenario: Verify OB correspondence On Hold status Reason values and update status to on hold
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence status is "Requested"
    And I click on edit button on view Outbound Correspondence details
    And I select "On Hold" value from "Correspondence status" dropdown
    And I click on Save button in outbound correspondence edit
    Then I verify the status reason is required message is dispalyed
    Then I validate the "Correspondence Reason" dropdown values in outbound correspondence
      | State requested    |
      | User requested     |
      | Volume control     |
      | Channel issues     |
      | Destination issues |
      | Template issues    |
      | Vendor issues      |
    And I select "State requested" value from "Correspondence Reason" dropdown
    And I click on Save button in outbound correspondence edit
    Then I verify the correspondence status is "On Hold"


  @CP-2934 @CP-2934-05 @ui-ecms2 @Keerthi
  Scenario: Verify OB correspondence Canceled status Reason values update status to canceled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence status is "Requested"
    And I click on edit button on view Outbound Correspondence details
    And I select "Canceled" value from "Correspondence status" dropdown
    And I click on Save button in outbound correspondence edit
    Then I verify the status reason is required message is dispalyed
    Then I validate the "Correspondence Reason" dropdown values in outbound correspondence
      | No longer appropriate |
      | Requested in error    |
      | Unresolvable error    |
    And I select "Unresolvable error" value from "Correspondence Reason" dropdown
    And I click on Save button in outbound correspondence edit
    Then I should see a warning message when navigating away "Are you sure, you wish to cancel this correspondence?"
    And I click yes when given the option to confirm cancel or not cancel
    Then I verify the correspondence status is "Canceled"


  @CP-2934 @CP-2934-06 @ui-ecms2 @Keerthi
  Scenario: Verify OB correspondence Requested status Reason should be disable mode and update status to requested
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I update Outbound Correspondence CId "previouslyCreated" to status "On Hold"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence status is "On Hold"
    And I click on edit button on view Outbound Correspondence details
    And I select "Requested" value from "Correspondence status" dropdown
    Then I verify correspondence status reason is in disable mode
    And I click on Save button in outbound correspondence edit
    Then I verify the correspondence status is "Requested"

  @CP-2934 @CP-2934-07 @ui-ecms2 @Keerthi
  Scenario: Verify Status,Status Reason,Updated By,Updated Date for updated correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on edit button on view Outbound Correspondence details
    And I select "On Hold" value from "Correspondence status" dropdown
    And I select "State requested" value from "Correspondence Reason" dropdown
    And I click on Save button in outbound correspondence edit
    Then I verify the correspondence status is "On Hold"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | On Hold               |
      | Status Reason | State requested       |
      | updated by    | current user          |
      | updated date  | current date and hour |


  @CP-2934 @CP-2934-08 @ui-ecms2 @Keerthi
  Scenario: Verify correspondence update event is produced while updating notification status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId       | Random                 |
      | ConsumerId   | Random                 |
      | externallink | externalRefType,random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I click on edit button on view Outbound Correspondence details
    And I select "On Hold" value from "Correspondence status" dropdown
    And I select "Volume control" value from "Correspondence Reason" dropdown
    And I click on Save button in outbound correspondence edit
    Then I verify the correspondence status is "On Hold"
    And I retrieve the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" event that is produced by trace id
    Then I should see the payload for the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" is as expected
    And I should see that the event mapping for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" exists
    And I should see that the "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" record is produced to DPBI




