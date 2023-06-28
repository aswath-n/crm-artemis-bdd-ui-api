@CP-12306
Feature:Augment Outbound Correspondence Search API to include user friendly correspondence type

  @CP-12306-01  @ui-ecms1 @Prithika
  Scenario: Verify that the name of the newly created Correspondence definition is present on outbound correspondence on 'case and contact details' page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text,Email"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    Given I retrieve Outbound Correspondence Name by Type
    When I navigate to the case and contact details tab
    Then I should see the Correspondence Name that was created

  @CP-14516-01  @API-ECMS @Prithika
  Scenario: Augment outbound correspondence data structure for mailing house- test from correspondence request. Language English
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | 0                 |
      | language                        | English           |
      | CaseID                          | previouslyCreated |
      | REGARDINGCONSUMERID             | random10          |
      | channelType                     | Mail              |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78613             |
      | streetAddress                   | test lane 1       |
      | streetAddionalLine1             | test lane 2       |
      | firstName                       | testA             |
      | lastName                        | testC             |
      | emailAddress                    | test@gmail.com    |
      | faxNumber                       | 9899699699        |
      | textNumber                      | 9899699691        |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    And I initiate Get letter data by the retrieved NID from OBCorrespondence
    And I verify get letter data response contains following values
      | recipientFirstName | testA          |
      | recipientLastName  | testC          |
      | careOfLine         | null           |
      | addressLine1       | test lane 1    |
      | addressLine2       | test lane 2    |
      | addressCity        | cedar park     |
      | addressStateAbbr   | TX             |
      | addressZip         | 78613          |
      | emailAddress       | test@gmail.com |
      | faxNumber          | 9899699699     |
      | smsNumber          | 9899699691     |
    And I verify get letter data response contains section Element with following values
      | languageCode | eng |

  @CP-14516-02  @API-ECMS @Prithika
  Scenario: Augment outbound correspondence data structure for mailing house- test from correspondence request. Language Spanish
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | 0                 |
      | language                        | SPANISH           |
      | CaseID                          | previouslyCreated |
      | REGARDINGCONSUMERID             | random10          |
      | channelType                     | Mail              |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78613             |
      | streetAddress                   | test lane 1       |
      | streetAddionalLine1             | test lane 2       |
      | firstName                       | testA             |
      | lastName                        | testC             |
      | emailAddress                    | test@gmail.com    |
      | faxNumber                       | 9899699699        |
      | textNumber                      | 9899699691        |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    And I initiate Get letter data by the retrieved NID from OBCorrespondence
    And I verify get letter data response contains following values
      | recipientFirstName | testA          |
      | recipientLastName  | testC          |
      | careOfLine         | null           |
      | addressLine1       | test lane 1    |
      | addressLine2       | test lane 2    |
      | addressCity        | cedar park     |
      | addressStateAbbr   | TX             |
      | addressZip         | 78613          |
      | emailAddress       | test@gmail.com |
      | faxNumber          | 9899699699     |
      | smsNumber          | 9899699691     |
    And I verify get letter data response contains section Element with following values
      | languageCode | spa |


  @CP-39253 @CP-39253-01 @ui-ecms2 @Keerthi
  Scenario: Validate Amharic as a language in ConnectionPoint (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS      |
      | language                        | Amharic     |
      | CaseID                          | empty       |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | TX          |
      | zipcode                         | 78613       |
      | streetAddress                   | test lane 1 |
      | streetAddionalLine1             | test lane 2 |
      | firstName                       | testA       |
      | lastName                        | testC       |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Language | Amharic |
    And I initiate Get letter data by the retrieved NID from OBCorrespondence
    And I verify get letter data response contains section Element with following values
      | languageCode | amh |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence language is "Amharic"

  @CP-39253 @CP-39253-02 @ui-ecms-dceb @Keerthi
  Scenario: Validate Amharic as a language in ConnectionPoint (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS      |
      | language                        | Amharic     |
      | CaseID                          | empty       |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | TX          |
      | zipcode                         | 78613       |
      | streetAddress                   | test lane 1 |
      | streetAddionalLine1             | test lane 2 |
      | firstName                       | testA       |
      | lastName                        | testC       |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Language | Amharic |
    And I initiate Get letter data by the retrieved NID from OBCorrespondence
    And I verify get letter data response contains section Element with following values
      | languageCode | amh |
    Then I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence language is "Amharic"

  @CP-39253 @CP-39253-03 @ui-ecms-ineb @Keerthi
  Scenario: Validate Amharic as a language in ConnectionPoint (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS      |
      | language                        | Amharic     |
      | CaseID                          | empty       |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | TX          |
      | zipcode                         | 78613       |
      | streetAddress                   | test lane 1 |
      | streetAddionalLine1             | test lane 2 |
      | firstName                       | testA       |
      | lastName                        | testC       |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Language | Amharic |
    And I initiate Get letter data by the retrieved NID from OBCorrespondence
    And I verify get letter data response contains section Element with following values
      | languageCode | amh |
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence language is "Amharic"

