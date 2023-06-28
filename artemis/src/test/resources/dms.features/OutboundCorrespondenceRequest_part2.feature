Feature: Outbound Correspondence Requests part2

  @CP-3185-01 @CP-3185 @ui-ecms1 @Sang
  Scenario: Verify Canceled Option in Status dropdown when editing a Requested status Outbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on edit button on view Outbound Correspondence details
    Then I verify "Canceled" is in the Outbound Correspondence status dropdown

  @CP-3185-02 @CP-3185 @ui-ecms1 @Sang
  Scenario: Verify the values are correct in the status reason dropdown in Outbound Correspondence case and contact details edit
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on edit button on view Outbound Correspondence details
    And I select "Canceled" value from "Correspondence status" dropdown
    And I verify the dropdown values in the status reason of Outbound correspondence details edit for Canceled Status

  @CP-3185-03 @CP-3185 @ui-ecms1 @Sang
  Scenario: Verify the error message is shown when saving editied Outbound Correspondence without selecting an option in status reason
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on edit button on view Outbound Correspondence details
    And I select "Canceled" value from "Correspondence status" dropdown
    And I click on Save button in outbound correspondence edit
    Then I verify the status reason is required message is dispalyed

  @CP-3185-04 @CP-3185 @ui-ecms1 @Sang
  Scenario: Verify the Confirm message is shown when saving editied Outbound Correspondence with valid field values
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on edit button on view Outbound Correspondence details
    And I select "Canceled" for Status and "Requested in error" for status reason in Outbound Correspondence edit
    And I click on Save button in outbound correspondence edit
    Then I verify Consumers Correspondence Preference dropdown option for confirm or not confirm is given

  @CP-3185-05 @CP-3185 @ui-ecms1 @Sang
  Scenario: Verify values are save in the system when saving status to canceled with status reason in Outbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I saved logged in user ID
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I searched consumer created by api script
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Case Contact Details Tab
    And I select on the Outbound Correspondence by "previouslyCreated" CId in the case and contacts details tab
    And I click on edit button on view Outbound Correspondence details
    And I select "Canceled" for Status and "Requested in error" for status reason in Outbound Correspondence edit
    And I click on Save button in outbound correspondence edit
    And I click yes when given the option to confirm cancel or not cancel
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status        | Canceled              |
      | Status Reason | Requested in error    |
      | Updated By    | UI User               |
      | Updated Date  | current date and hour |

  @CP-12907-01 @CP-12907  @ui-ecms2 @Sang
  Scenario: When I cancel an outbound correspondence an event is published
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate post outbound correspondence with four recipient
    And I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search for the Outbound Correspondence with the new created CId
    And I click on the correspondence id of the search result
    And I click on edit button on view Outbound Correspondence details
    And I select "Canceled" for Status and "Requested in error" for status reason in Outbound Correspondence edit
    And I click on Save button in outbound correspondence edit
    And I click yes when given the option to confirm cancel or not cancel
    Then I verify Outbound correspondence is published for the canceled Outbound Correspondence

  @CP-12907-02 @CP-12907  @ui-ecms2 @Sang
  Scenario: When I cancel an outbound correspondence Requested, On Hold, Error, Assembled Notification status is canceled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate post outbound correspondence with four recipient
    And I change the status of the created notification status to
      | Requested |
      | On Hold   |
      | Error     |
      | Assembled |
    And I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search for the Outbound Correspondence with the new created CId
    And I click on the correspondence id of the search result
    And I click on edit button on view Outbound Correspondence details
    And I select "Canceled" for Status and "Requested in error" for status reason in Outbound Correspondence edit
    And I click on Save button in outbound correspondence edit
    And I click yes when given the option to confirm cancel or not cancel
    Then I verify notification status has changed for each status notification for following
      | Canceled |
      | Canceled |
      | Canceled |
      | Canceled |

  @CP-12907-03 @CP-12907  @ui-ecms2 @Sang
  Scenario: When I cancel an outbound correspondence Notification status Sent, Returned is unchanged while Exported is Canceled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate post outbound correspondence with four recipient
    And I change the status of the created notification status to
      | Sent     |
      | Returned |
      | Exported |
      | Canceled |
    And I update the created Outbound Correspondence to "Requested"
    And I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search for the Outbound Correspondence with the new created CId
    And I click on the correspondence id of the search result
    And I click on edit button on view Outbound Correspondence details
    And I select "Canceled" for Status and "Requested in error" for status reason in Outbound Correspondence edit
    And I click on Save button in outbound correspondence edit
    And I click yes when given the option to confirm cancel or not cancel
    Then I verify notification status has changed for each status notification for following
      | Sent     |
      | Returned |
      | Canceled |
      | Canceled |

  @CP-12907-04 @CP-12907  @ui-ecms2 @Sang
  Scenario: When I cancel an outbound correspondence status Precluded notification status remains unchanged
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate post outbound correspondence with four recipient
    And I change the status of the created notification status to Precluded
    And I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I search for the Outbound Correspondence with the new created CId
    And I click on the correspondence id of the search result
    And I click on edit button on view Outbound Correspondence details
    And I select "Canceled" for Status and "Requested in error" for status reason in Outbound Correspondence edit
    And I click on Save button in outbound correspondence edit
    And I click yes when given the option to confirm cancel or not cancel
    Then I verify created outbound correspondence notification status is unchanged