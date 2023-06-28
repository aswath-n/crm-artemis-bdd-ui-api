@CP-4739
#@API-ECMS
#@ui-ecms1
#@ui-ecms1
@umid

Feature: Pass action, recordType, eventCreatedOn and dataObject in all the correspondence SRE related events that are generated for DPBI

  Background: get a DMS token
    Given I will get the Authentication token for BLCRM in DMS

@API-CP-4739-01 @API-ECMS
  Scenario: Pass INBOUND_CORRESPONDENCE_SAVE_EVENT action, recordType, eventCreatedOn and dataObject in the event payload, all datetime fields
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "68"
    When I search for that document by "caseId" and value as "68"
    And I search "INBOUND_CORRESPONDENCE_SAVE_EVENT" the Get Events
    Then I should see "INBOUND_CORRESPONDENCE_SAVE_EVENT" Event was created
    And Pass all datetime fields "action" "recordType" "eventCreatedOn" and dataObject "caseId" and value as "68" for "INBOUND_CORRESPONDENCE_SAVE_EVENT"

@API-CP-4739-02 @API-ECMS
  Scenario: Pass INBOUND_CORRESPONDENCE_UPDATE_EVENT action, recordType, eventCreatedOn and dataObject in the event payload, all datetime fields
    Given that I have an Update Inbound Correspondence in Onbase with a key as "caseId" and value as "8" and "id" as "12255"
    When I search for that document by both "caseId" as "8" and "id" as "12255"
    And I search "INBOUND_CORRESPONDENCE_UPDATE_EVENT" the Get Events
    Then I should see "INBOUND_CORRESPONDENCE_UPDATE_EVENT" Event was created
    And Pass all datetime fields "action" "recordType" "eventCreatedOn" and dataObject "caseId" and value as "8" for "INBOUND_CORRESPONDENCE_UPDATE_EVENT"

#@CP-4739-03 @ui-ecms1 @API-ECMS DEPRECATED
  Scenario Outline: Pass OUTBOUND_CORRESPONDENCE_SAVE_EVENT action, recordType, eventCreatedOn and dataObject in the event payload, all datetime fields
    Given I logged into CRM and click on initiate contact
    When I populate only "InboundCorres" for an Active Contact
    And I click on Active Contact Search Button
    Then I link the active contact to an existing contact by "InboundCorres"
    When I navigate to the case and contact details tab
    Given that I have created an Outbound Correspondence through the UI "<streetAddress>" "<streetAdditionalLine1>" "<faxNum>"
    When I search "OUTBOUND_CORRESPONDENCE_SAVE_EVENT" the Get Events endpoint for UI
    Then I should see "OUTBOUND_CORRESPONDENCE_SAVE_EVENT" Event was created
    And Pass all datetime fields "action" "recordType" "eventCreatedOn" and dataObject for "OUTBOUND_CORRESPONDENCE_SAVE_EVENT"
    Examples:
    |streetAddress|streetAdditionalLine1|faxNum|
    |123 Main St  |Ext 2020             |7864531203|

#  @CP-4739-04 @ui-ecms1 @API-ECMS DEPRECATED
  Scenario: Pass OUTBOUND_CORRESPONDENCE_UPDATE_EVENT action, recordType, eventCreatedOn and dataObject in the event payload, all datetime fields
    Given I logged into CRM and click on initiate contact
    When I populate only "InboundCorres" for an Active Contact
    And I click on Active Contact Search Button
    Then I link the active contact to an existing contact by "InboundCorres"
    When I navigate to the case and contact details tab
    Given that I have update an Outbound Correspondence through the UI "Canceled"
    When I search "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" in the Get Events endpoint
    Then I should see "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" Event was created
    And Pass all datetime fields "action" "recordType" "eventCreatedOn" and dataObject for "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT"

#  @CP-4739-05 @ui-ecms1 @API-ECMS DEPRECATED
  Scenario: Pass OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT action, recordType, eventCreatedOn and dataObject in the event payload, all datetime fields
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add a new Correspondence Definition without a "Field"
    And I search "OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT" Event was created
    And Pass all datetime fields "action" "recordType" "eventCreatedOn" and dataObject for "OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT"

#  @CP-4739-06 @ui-ecms1 @API-ECMS DEPRECATED
  Scenario: Pass OUTBOUND_CORRESPONDENCE_DEFINITION_UPDATE_EVENT action, recordType, eventCreatedOn and dataObject in the event payload, all datetime fields
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I update existing Correspondence Definition without a "Field"
    And I search "OUTBOUND_CORRESPONDENCE_DEFINITION_UPDATE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_DEFINITION_UPDATE_EVENT" Event was created
    And Pass all datetime fields "action" "recordType" "eventCreatedOn" and dataObject for "OUTBOUND_CORRESPONDENCE_DEFINITION_UPDATE_EVENT"

#  @CP-4739-07 @ui-ecms1 @API-ECMS DEPRECATED
  Scenario Outline: Pass OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_SAVE_EVENT action, recordType, eventCreatedOn and dataObject in the event payload, all datetime fields
    Given I logged into Tenant Manager and set the project context "project" value "<projectName>"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I click on given CorrespondenceDefinition ID "2aEOUFcotb"
    And I add a channel "<projectId>","<projectName>","<recordType>","<channelType>","<endReason>","<senderEmailId>","<sendImmediately>","<mandatory>"
    And I search "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_SAVE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_SAVE_EVENT" Event was created
    And Pass all datetime fields "action" "recordType" "eventCreatedOn" and dataObject for "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_SAVE_EVENT"
    Examples:
      |projectId|projectName|recordType|channelType|endReason|senderEmailId|sendImmediately|mandatory|
      |6203|BLCRM|OutboundCorrespondenceDefinitionChannel|Fax|Unnessary|testin@test.test|true|true|

#@CP-4739-08 @ui-ecms1 @API-ECMS DEPRECATED
  Scenario Outline: Pass OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_UPDATE_EVENT action, recordType, eventCreatedOn and dataObject in the event payload, all datetime fields
    Given I logged into Tenant Manager and set the project context "project" value "<projectName>"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I click on given CorrespondenceDefinition ID "2aEOUFcotb"
    When I update a channel "<projectId>","<projectName>","<recordType>","<channelTypeEdit>","<channelType>","<endReason>","<senderEmailId>","<sendImmediately>","<mandatory>"
    And I search "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_UPDATE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_UPDATE_EVENT" Event was created
  And Pass all datetime fields "action" "recordType" "eventCreatedOn" and dataObject for "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_UPDATE_EVENT"
    Examples:
      |projectId|projectName|recordType|channelTypeEdit|channelType|endReason|senderEmailId|sendImmediately|mandatory|
      |6203|BLCRM|OutboundCorrespondenceDefinitionChannel|Fax|Text|Unnessary|testin@test.test|true|true|

#@CP-4739-09 @ui-ecms1 @API-ECMS DEPRECATED
  Scenario: Pass OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT action, recordType, eventCreatedOn and dataObject in the event payload, all datetime fields
    Given I logged into Tenant Manager and set the project context "project" value "EventSet4739"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I navigate to the Outbound Correspondence Configuration screen
    When I fill out OutBound Correspondence Configuration fields
    And I save changes in Configuration screen
    And I search "OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT" Event was created
    And Pass all datetime fields "action" "recordType" "eventCreatedOn" and dataObject for "OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT"

#@CP-4739-10 @ui-ecms1 @API-ECMS DEPRECATED
  Scenario: Pass OUTBOUND_CORRESPONDENCE_SETTINGS_UPDATE_EVENT action, recordType, eventCreatedOn and dataObject in the event payload, all datetime fields
    Given I logged into Tenant Manager and set the project context "project" value "DBPI4739"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I navigate to the Outbound Correspondence Configuration screen
    When I edit and fill out OutBound Correspondence Configuration fields
    And I search "OUTBOUND_CORRESPONDENCE_SETTINGS_UPDATE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_SETTINGS_UPDATE_EVENT" Event was created
  And Pass all datetime fields "action" "recordType" "eventCreatedOn" and dataObject for "OUTBOUND_CORRESPONDENCE_SETTINGS_UPDATE_EVENT"
