# @CP-2990 - DEPRECATED
# @umid
# @events_smoke_level_two
# @events

Feature: Publish DPBI Events for Outbound Correspondence Configuration Changes

#  @CP-2990-01 @ui-ecms1 DEPRECATED
  Scenario: Verify that when Outbound Correspondence Settings are updated, the attributes are captured in OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add a new Correspondence Definition without a "Field"
    And I search "OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_DEFINITION_SAVE_EVENT" Event was created

#  @CP-2990-02 @ui-ecms1 DEPRECATED
  Scenario: Verify that when Outbound Correspondence Settings are updated, the attributes are captured in OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I update existing Correspondence Definition without a "Field"
    And I search "OUTBOUND_CORRESPONDENCE_DEFINITION_UPDATE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_DEFINITION_UPDATE_EVENT" Event was created

#  @CP-2990-03 @ui-ecms1 DEPRECATED
  Scenario Outline: Verify that when Outbound Correspondence Settings are created, the attributes are captured in OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT
    Given I logged into Tenant Manager and set the project context "project" value "<projectName>"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I click on given CorrespondenceDefinition ID "k4pKChNMEY"
    And I add a channel "<projectId>","<projectName>","<recordType>","<channelType>","<endReason>","<senderEmailId>","<sendImmediately>","<mandatory>"
    And I search "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_SAVE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_SAVE_EVENT" Event was created
    Examples:
      |projectId|projectName|recordType|channelType|endReason|senderEmailId|sendImmediately|mandatory|
      |6203|BLCRM|OutboundCorrespondenceDefinitionChannel|Fax|Unnessary|testin@test.test|true|true|

#  @CP-2990-04 @ui-ecms2 DEPRECATED
  Scenario Outline: Verify that when Outbound Correspondence Settings are updated, the attributes are captured in OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT
    Given I logged into Tenant Manager and set the project context "project" value "<projectName>"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I click on given CorrespondenceDefinition ID "k4pKChNMEY"
    When I update a channel "<projectId>","<projectName>","<recordType>","<channelTypeEdit>","<channelType>","<endReason>","<senderEmailId>","<sendImmediately>","<mandatory>"
    And I search "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_UPDATE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_DEFINITION_CHANNEL_UPDATE_EVENT" Event was created
    Examples:
      |projectId|projectName|recordType|channelTypeEdit|channelType|endReason|senderEmailId|sendImmediately|mandatory|
      |6203|BLCRM|OutboundCorrespondenceDefinitionChannel|Fax|Text|Unnessary|testin@test.test|true|true|

#  @CP-2990-05 @ui-ecms2 DEPRECATED
  Scenario: Verify that when Outbound Correspondence Settings are updated, the attributes are captured in OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT
    Given I logged into Tenant Manager and set the project context "project" value "DBPI299005"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I navigate to the Outbound Correspondence Configuration screen
    When I fill out OutBound Correspondence Configuration fields
    And I save changes in Configuration screen
    And I search "OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT" Event was created

#  @CP-2990-06 @ui-ecms2 DEPRECATED
  Scenario: Verify that when Outbound Correspondence Settings are updated, the attributes are captured in OUTBOUND_CORRESPONDENCE_SETTINGS_SAVE_EVENT
    Given I logged into Tenant Manager and set the project context "project" value "DBPI2990"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I navigate to the Outbound Correspondence Configuration screen
    When I edit and fill out OutBound Correspondence Configuration fields
    And I search "OUTBOUND_CORRESPONDENCE_SETTINGS_UPDATE_EVENT" in the Get Events endpoint for DBPI
    Then I should see "OUTBOUND_CORRESPONDENCE_SETTINGS_UPDATE_EVENT" Event was created