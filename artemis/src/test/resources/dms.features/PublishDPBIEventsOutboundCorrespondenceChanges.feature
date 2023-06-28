#@CP-2991 - DEPRECATED
#@umid
  Feature: publish event for DPBI when outbound correspondence data is added or updated

#  @CP-2991-01 @CP-2991 @API-ECMS @events_smoke_level_two DEPRECATED
  Scenario Outline: Verify creating a new Correspondence from API will trigger an OUTBOUND_CORRESPONDENCE_SAVE_EVENT
    Given that I have created an Outbound Correspondence through the Post Correspondence endpoint "<caseId>" "<MMSCode>" "<language>" "<channelType>" "<createdBy>" "<firstName>" "<lastName>" "<role>" "<streetAddress>" "<streetAddionalLine1>" "<city>" "<state>" "<zipcode>"
    When I search "OUTBOUND_CORRESPONDENCE_SAVE_EVENT" in the Get Events endpoint
    Then I should see "OUTBOUND_CORRESPONDENCE_SAVE_EVENT" Event was created
    Examples:
    |caseId|MMSCode|language|channelType|createdBy|firstName|lastName|role|streetAddress|streetAddionalLine1|city|state|zipcode|
    |244|newName|English|Mail|1908|Hank|Basket|Member|123 Main st|apt 569|Reston|VA|20230|

#  @CP-2991-02 @CP-2991 @ui-ecms1 DEPRECATED
  Scenario Outline: Verify creating a new Correspondence from UI will trigger an OUTBOUND_CORRESPONDENCE_SAVE_EVENT
    Given I logged into CRM and click on initiate contact
    When I populate only "InboundCorres" for an Active Contact
    And I click on Active Contact Search Button
    Then I link the active contact to an existing contact by "InboundCorres"
    When I navigate to the case and contact details tab
    Given that I have created an Outbound Correspondence through the UI "<streetAddress>" "<streetAdditionalLine1>" "<faxNum>"
    When I search "OUTBOUND_CORRESPONDENCE_SAVE_EVENT" the Get Events endpoint for UI
    Then I should see "OUTBOUND_CORRESPONDENCE_SAVE_EVENT" Event was created
    Examples:
      |streetAddress|streetAdditionalLine1|faxNum|
      |123 Main St  |Ext 2020             |7864531203|

#    @CP-2991-03 @CP-2991 @ui-ecms1 @events_smoke_level_two @events_smoke_level_one DEPRECATED
  Scenario: Verify updating a Correspondence will trigger an OUTBOUND_CORRESPONDENCE_UPDATE_EVENT
    Given I logged into CRM and click on initiate contact
    When I populate only "InboundCorres" for an Active Contact
    And I click on Active Contact Search Button
    Then I link the active contact to an existing contact by "InboundCorres"
    When I navigate to the case and contact details tab
    Given that I have update an Outbound Correspondence through the UI "Canceled"
    When I search "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" in the Get Events endpoint
    Then I should see "OUTBOUND_CORRESPONDENCE_UPDATE_EVENT" Event was created

#  @CP-2991-04 @CP-2991 @ui-ecms1 @events_smoke_level_two # blocked by CP- DEPRECATED
  Scenario: Verify that creating a notification will trigger a NOTIFICATION_SAVE_EVENT
    Given I logged into CRM and click on initiate contact
    When I populate only "InboundCorres" for an Active Contact
    And I click on Active Contact Search Button
    Then I link the active contact to an existing contact by "InboundCorres"
    When I navigate to the case and contact details tab
    Given that I have an Outbound Correspondence marked as "Resend"
    When I search "NOTIFICATION_SAVE_EVENT" in the Get Events endpoint
    Then I should see "NOTIFICATION_SAVE_EVENT" Event was created

