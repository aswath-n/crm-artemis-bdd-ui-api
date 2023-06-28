Feature: Create Outbound Correspondence with same Definition ID for different tenants

  @API-CP-31164 @API-CP-31164-01 @api-ecms-ineb @RuslanL
  Scenario: Verify system using correct OB definition, when creating OB correspondence for IN-EB and BLCRM has same ID definition, but different channels
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | IA              |
      | language                        | English         |
      | caseId                          | Random          |
      | regardingConsumerId             | Random          |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
      | channelType                     | Email            |
    When I send the request for an Outbound Correspondence to the service
    Then I should see failed status and the reason for the failure in the response

  @API-CP-31164 @API-CP-31164-02 @API-ECMS @RuslanL
  Scenario: Verify system using correct OB definition, when creating OB correspondence for BLCRM and IN-EB has same ID definition, but different channels
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | IA              |
      | language                        | English         |
      | caseId                          | Random          |
      | regardingConsumerId             | Random          |
      | requesterId                     | 2425            |
      | requesterType                   | ConnectionPoint |
      | channelType                     | Mail            |
    When I send the request for an Outbound Correspondence to the service
    Then I should see failed status and the reason for the failure in the response

  @API-CP-31164 @API-CP-31164-03 @API-ECMS @RuslanL
  Scenario: Verify system using correct channel, when creating OB correspondence for IN-EB and BLCRM has same ID definition and same channel
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an Outbound Correspondence with a notification for "Email"
    And I retrieve the Outbound Correspondence and verify Template id, Channel Definition id and Template version

  @API-CP-31164 @API-CP-31164-04 @API-ECMS @RuslanL
  Scenario: Verify system using correct channel, when creating OB correspondence for BLCRM and IN-EB has same ID definition and same channel
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Email"
    And I retrieve the Outbound Correspondence and verify Template id, Channel Definition id and Template version