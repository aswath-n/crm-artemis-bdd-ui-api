Feature: Verify Barcode Look Up

  @CP-4777 @CP-4777-01 @API-ECMS @James
  Scenario: Verify barcodes 1 digit will return invalid response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a barcode with "1" value
    When I send the barcode request to the service
    Then I should see an "Invalid Request" response from barcode look up

  @CP-4777 @CP-4777-02 @API-ECMS @James
  Scenario: Verify barcodes less than 20 digit will return invalid response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a barcode with "123456789012345678901" value
    When I send the barcode request to the service
    Then I should see an "Invalid Request" response from barcode look up

  @CP-4777 @CP-4777-03 @API-ECMS @James
  Scenario: Verify barcodes more than 20 digit will return invalid response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a barcode with "21" value
    When I send the barcode request to the service
    Then I should see an "Invalid Request" response from barcode look up

  @CP-4777 @CP-4777-04 @API-ECMS @James
  Scenario: Verify barcodes that doesn't exist will return not found message
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a barcode with "00000000999999999999" value
    When I send the barcode request to the service
    Then I should see an "Barcode Not Found" response from barcode look up

  @CP-4777 @CP-4777-05 @API-ECMS @James
  Scenario: Verify barcodes can look up a notification with case id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a barcode value with the "previouslyCreated" notification
    When I send the barcode request to the service
    Then I should see barcode response has the case Id

  @CP-4777 @CP-4777-06 @API-ECMS @James
  Scenario: Verify barcodes can look up a notification both with case id and consumer id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a barcode value with the "previouslyCreated" notification
    When I send the barcode request to the service
    Then I should see barcode response has the case Id and consumer Id

  @CP-4777 @CP-4777-07 @API-ECMS @James
  Scenario: Verify barcodes doesnt find a notification will return notification not found message
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a barcode with "00000009999901019879" value
    When I send the barcode request to the service
    Then I should see an "Notification Not Found" response from barcode look up

  @CP-4022 @CP-4022-01 @API-ECMS @James
  Scenario: Verify Onbase 1 zero digit will return invalid response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a onbase barcode with "1" value
    When I send the onbase barcode request to the service
    Then I should see an "Invalid Request" response from onbase barcode look up

  @CP-4022 @CP-4022-01 @API-ECMS @James
  Scenario: Verify Onbase barcodes zero digit will return invalid response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a onbase barcode with "0" value
    When I send the onbase barcode request to the service
    Then I should see an "Invalid Request" response from onbase barcode look up

  @CP-4022 @CP-4022-02 @API-ECMS @James
  Scenario: Verify Onbase barcodes less than 20 digit will return invalid response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a onbase barcode with "123456789012345678901" value
    When I send the onbase barcode request to the service
    Then I should see an "Invalid Request" response from onbase barcode look up

  @CP-4022 @CP-4022-03 @API-ECMS @James
  Scenario: Verify Onbase barcodes more than 20 digit will return invalid response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a onbase barcode with "21" value
    When I send the onbase barcode request to the service
    Then I should see an "Invalid Request" response from onbase barcode look up

  @CP-4022 @CP-4022-04 @API-ECMS @James
  Scenario: Verify Onbase barcodes that doesn't exist will return not found message
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a onbase barcode with "00000000999999999999" value
    When I send the onbase barcode request to the service
    Then I should see an "BarCode not found" response from onbase barcode look up

  @CP-4022 @CP-4022-05 @API-ECMS @James
  Scenario: Verify Onbase barcodes can look up a notification with case id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a onbase barcode value with the "previouslyCreated" notification
    When I send the onbase barcode request to the service
    Then I should see onbase barcode response has the case Id

  @CP-4022 @CP-4022-06 @API-ECMS @James
  Scenario: Verify Onbase barcodes can look up a notification both with case id and consumer id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a onbase barcode value with the "previouslyCreated" notification
    When I send the onbase barcode request to the service
    Then I should see onbase barcode response has the case Id and consumer Id

  @CP-4022 @CP-4022-07 @API-ECMS @James
  Scenario: Verify Onbase barcodes doesnt find a notification will return Barcode Not Found
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a onbase barcode with "00000009999901010004" value
    When I send the onbase barcode request to the service
    Then I should see an "BarCode not found" response from onbase barcode look up

    ################################ CP-24222 #################################

  @CP-24222 @CP-24222-1 @API-ECMS @Keerthi
  Scenario: Verify barcodes can look up a notification with ApplicationID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I POST ATS save submit application api and store appId and response in list
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | AppId                |
      | channelType                     | Mail                 |
      | language                        | English              |
      | requesterId                     | 2425                 |
      | requesterType                   | ConnectionPoint      |
      | address                         | random               |
      | applicationID                   | previouslyCreatedATS |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I have a barcode value with the "previouslyCreated" notification
    When I send the barcode request to the service
    Then I should see barcode response has the "previouslyCreatedATS" application Id

  @CP-24222 @CP-24222-2 @API-ECMS @Keerthi
  Scenario: Verify Onbase barcodes can look up a notification with ApplicationID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save submit application api and store appId and response in list
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | AppId                |
      | channelType                     | Mail                 |
      | language                        | English              |
      | requesterId                     | 2425                 |
      | requesterType                   | ConnectionPoint      |
      | address                         | random               |
      | applicationID                   | previouslyCreatedATS |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I have a onbase barcode value with the "previouslyCreated" notification
    When I send the onbase barcode request to the service
    Then I should see onbase barcode response has the "previouslyCreatedATS" application Id
