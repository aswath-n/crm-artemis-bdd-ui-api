Feature:Split processnotifications Endpoint

  @CP-36147 @CP-36147-01 @ECMS-LONG @API-ECMS @Keerthi
  Scenario: Validate unprovisioned endpoint-Correspondences without recipients in the Requested status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
    And I invoke unprovisioned endpoint to return a list of OBcorrespondence orders that are in Requested status and have no recipients
    And I validate Outbound Correspondence order "available" in the unprovisioned list
      | previouslyCreated |


  @CP-36147 @CP-36147-02 @ECMS-LONG @API-ECMS @Keerthi
  Scenario: Validate unprovisioned endpoint-Correspondence with recipients in the Requested status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    And I invoke unprovisioned endpoint to return a list of OBcorrespondence orders that are in Requested status and have no recipients
    And I validate Outbound Correspondence order "unavailable" in the unprovisioned list
      | previouslyCreated |


  @CP-36147 @CP-36147-03 @ECMS-LONG @API-ECMS @Keerthi
  Scenario: Validate unprovisioned endpoint-Correspondence without recipients in the Sent status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
    And I update Outbound Correspondence CId "previouslyCreated" to status "Sent"
    And I invoke unprovisioned endpoint to return a list of OBcorrespondence orders that are in Requested status and have no recipients
    And I validate Outbound Correspondence order "unavailable" in the unprovisioned list
      | previouslyCreated |


  @CP-36147 @CP-36147-04 @API-ECMS @Keerthi
  Scenario: Validate provision endpoint-processes list of 2 correspondence order IDs in the Requested status with mail as default channel
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
    And I store "previouslyCreated" outbound correspondence id in random map as "previouslyCreated1"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
    And I store "previouslyCreated" outbound correspondence id in random map as "previouslyCreated2"
    And I invoke provision endpoint accepts and processes list of outbound correspondence order IDs
      | previouslyCreated1 |
      | previouslyCreated2 |
    And I validate Outbound Correspondence order "processed" in the provisioned list
      | previouslyCreated1 |
      | previouslyCreated2 |


  @CP-36147 @CP-36147-05 @API-ECMS @Keerthi
  Scenario: Validate provision endpoint-processes single correspondence order ID in the Requested status with mail as default channel
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
    And I invoke provision endpoint accepts and processes list of outbound correspondence order IDs
      | previouslyCreated |
    And I validate Outbound Correspondence order "processed" in the provisioned list
      | previouslyCreated |
    Then I should see a "Mail" notification that has the most updated contact information with the following values
      | firstName    | from caseCorrespondence API |
      | lastName     | from caseCorrespondence API |
      | role         | from caseCorrespondence API |
      | consumerId   | from caseCorrespondence API |
      | addressLine  | from caseCorrespondence API |
      | addressLine2 | from caseCorrespondence API |
      | city         | from caseCorrespondence API |
      | state        | from caseCorrespondence API |
      | zip          | from caseCorrespondence API |
      | language     | from request                |
      | templateId   | ID of template used         |

  @CP-36147 @CP-36147-06 @API-ECMS @Keerthi
  Scenario: Validate provision endpoint-processes single correspondence order ID in the Requested status with mail,email,fax,text as default channels
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole     | Primary Individual            |
      | mailDestination  | random active mailing address |
      | emailDestination | random active email address   |
      | faxDestination   | random active fax address     |
      | textDestination  | random active text address    |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
    And I invoke provision endpoint accepts and processes list of outbound correspondence order IDs
      | previouslyCreated |
    And I validate Outbound Correspondence order "processed" in the provisioned list
      | previouslyCreated |
    Then I should see a "Mail" notification that has the most updated contact information with the following values
      | firstName    | from caseCorrespondence API |
      | lastName     | from caseCorrespondence API |
      | role         | from caseCorrespondence API |
      | consumerId   | from caseCorrespondence API |
      | addressLine  | from caseCorrespondence API |
      | addressLine2 | from caseCorrespondence API |
      | city         | from caseCorrespondence API |
      | state        | from caseCorrespondence API |
      | zip          | from caseCorrespondence API |
      | language     | from request                |
      | templateId   | ID of template used         |
    Then I should see a "Email" notification that has the most updated contact information with the following values
      | firstName    | from caseCorrespondence API |
      | lastName     | from caseCorrespondence API |
      | role         | from caseCorrespondence API |
      | consumerId   | from caseCorrespondence API |
      | language     | from request                |
      | templateId   | ID of template used         |
      | emailAddress | from caseCorrespondence API |
    Then I should see a "Text" notification that has the most updated contact information with the following values
      | firstName    | from caseCorrespondence API |
      | lastName     | from caseCorrespondence API |
      | role         | from caseCorrespondence API |
      | consumerId   | from caseCorrespondence API |
      | language     | from request                |
      | templateId   | ID of template used         |
      | phoneNumber | from caseCorrespondence API |
    Then I should see a "Fax" notification that has the most updated contact information with the following values
      | firstName    | from caseCorrespondence API |
      | lastName     | from caseCorrespondence API |
      | role         | from caseCorrespondence API |
      | consumerId   | from caseCorrespondence API |
      | language     | from request                |
      | templateId   | ID of template used         |
      | phoneNumber | from caseCorrespondence API |

  @CP-36147 @CP-36147-07 @API-ECMS @Keerthi
  Scenario: Validate provision endpoint-Correspondence without recipients in the Canceled status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual            |
      | mailDestination | random active mailing address |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | default           |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
    And I update Outbound Correspondence CId "previouslyCreated" to status "Canceled"
    And I invoke provision endpoint accepts and processes list of outbound correspondence order IDs
      | previouslyCreated |
    And I validate Outbound Correspondence order "unprocessed" in the provisioned list
      | previouslyCreated |



