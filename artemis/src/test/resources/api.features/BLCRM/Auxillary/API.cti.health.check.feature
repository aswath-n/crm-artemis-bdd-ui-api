Feature: API: CTI Health Check Feature

  @crmCoreAPI @cti-api-CRMC @API-IVR-CTI_HealthCheck @Shruti @API-CRM-Regression @API-CRM-SmokeTest
  Scenario Outline: CTI Screen Pop Up -- POST
    Given I initiated cti screen pop API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I can provide cti screen pop information "<SSN>", "<DOB>" and "<ciscoAgentId>"
    Then I verify the response status code and status message
    Examples:
      | SSN       | DOB        | ciscoAgentId | projectName |
      | 121533665 | 11/16/1990 | 1111115      |[blank]|




  #https://mars-cti-api-qa.apps.non-prod.pcf-maersk.com/mars/cti/call/pop?apikey=BvRmuodYbL23mAWIwfv4CESavHzOMbuW


  @api-smoke-devops @kamil
  Scenario: Verify Advanced Contact Record Create and Search
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer to link contact record
    And I initiated Create Contact Records API
    And I can provide advanced contact details information:
      | contactRecordType     | random   |
      | contactType           | General  |
      | preferredLanguageCode | English  |
      | inboundCallQueue      | random   |
      | contactChannelType    | Phone    |
      | linkRefType           | consumer |
      | consumerType          | consumer |
    Then I can run create contact records API
    And I initiated Contact Records Advance Search API
    And I can provide advanced contact search criteria information based on ContactRecordID
    Then Wait for 7 seconds
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on ContactRecordID


  @api-smoke-devops @kamil
  Scenario: Verify MedChat to Create Contact Record
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated Med Chat Create Contact Records API
    And User send Api call with name "md" payload "medChatApiCreateContactRecord" for Med Chat
      | transcriptId       | c6dca9be-ca1b-c201-c36b-39fa19a69379 |
      | contactChannelType | Web Chat                             |
      | consumerEmail      | email::                              |
      | consumerPhone      | null::                               |
    Then Verify that "c.contactRecordId != null::"


  @api-smoke-devops @kamil
  Scenario: Verify CTI Call Pop Api
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated CTI Call Pop API
    And User send Api call with name "ct" payload "ctiCallPopApi" for CTI CALL Pop
      | ani | phone:: |
    Then Verify that "c.status == success"