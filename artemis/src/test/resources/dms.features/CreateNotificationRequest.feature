Feature: Create Correspondence Notification Requests

  @CP-3032 @API-CP-3032-01 @API-ECMS @burak
  Scenario Outline: Verify Error message for Create Correspondence Notification Requests when requested channel is not active(W recipients)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | OnlyMail                |
      | channelType                     | <CorrespondenceChannel> |
      | language                        | English                 |
      | caseId                          | previouslyCreated       |
      | requesterId                     | 2425                    |
      | requesterType                   | ConnectionPoint         |
      | address                         | random                  |
    When I send the request for an Outbound Correspondence to the service
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR          |
      | message | <ErrorMessage> |
    Examples:
      | CorrespondenceChannel | ErrorMessage                                                        |
      | Text                  | Text Channel is not active for the given Correspondence Definition  |
      | Fax                   | Fax Channel is not active for the given Correspondence Definition   |
      | Email                 | Email Channel is not active for the given Correspondence Definition |

  @CP-3032 @API-CP-3032-02 @API-ECMS @burak
  Scenario: Verify Successfull Creation of Correspondence Notification Requests when requested channel is  active(W recipients)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | OnlyMail          |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |

  @CP-3032 @API-CP-3032-03 @API-ECMS @burak
  Scenario Outline:  Verify Error message for Create Correspondence Notification Requests when requested channel is not active(Without recipients)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | OnlyMail                |
      | caseId                          | previouslyCreated       |
      | channelType                     | <CorrespondenceChannel> |
      | <ChannelDestinationType>        | <ChannelDestination>    |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR          |
      | message | <ErrorMessage> |
    Examples:
      | CorrespondenceChannel | ErrorMessage                                                        | ChannelDestinationType | ChannelDestination |
      | Text                  | Text Channel is not active for the given Correspondence Definition  | textNumber             | 9899699699         |
      | Fax                   | Fax Channel is not active for the given Correspondence Definition   | faxNumber              | 9899699612         |
      | Email                 | Email Channel is not active for the given Correspondence Definition | emailAddress           | test@maersk.com   |

  @CP-3032 @API-CP-3032-04 @API-ECMS @burak
  Scenario: Verify Successful Creation of Correspondence Notification Requests when requested channel is  active(Without recipients)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | OnlyMail          |
      | caseId                          | previouslyCreated |
      | channelType                     | Mail              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |

  @CP-3032 @CP-3032-05 @ui-ecms1 @burak
  Scenario: Verify OB Correspondence is not created in IN-EB Project
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | OMCC              |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | random10          |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Mail              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I will get the Authentication token for "IN-EB" in "CRM"
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | regardingConsumerId | random10created       |
      | caseId              | previouslyCreatedCase |
    Then I should see an Error Alert Message
      | No search results found |

  @CP-3032 @CP-3032-06 @ui-ecms2  @burak
  Scenario: Verify OB Correspondence is not created in BLCRM Project
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | OnlyMail          |
      | regardingConsumerId             | random10          |
      | projectId                       | 8861              |
      | caseId                          | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Mail              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    And I should see the Outbound Correspondence Notification is created with the following values
      | status | Requested |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | regardingConsumerId | random10created       |
      | caseId              | previouslyCreatedCase |
    Then I should see an Error Alert Message
      | No search results found |