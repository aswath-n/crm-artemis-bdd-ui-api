@CP-24608
Feature: Outbound Correspondence Requests API Updates Part A

  @CP-24608-01  @API-ECMS @Prithika
  Scenario: 1.0 A Correspondence Type must be provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence request without a Correspondence Type
    When I send the request for an Outbound Correspondence to the service with no correspondence type
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                       |
      | message | CorrespondenceDefinitionMMSCode is required |

  @CP-24608-02 @API-ECMS @Prithika
  Scenario: 1.1 The Correspondence Type must be active for the project PASS
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | CP24608F |
    When I send the request for an Outbound Correspondence to the service
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                 |
      | message | The CorrespondenceDefinitionMMSCode is set for FUTURE |

  @CP-24608-03 @API-ECMS @Prithika
  Scenario: 2.0 The Regarding values defined as being required for the Correspondence Type must be provided in the anchor element
  2.1 If the Correspondence Type is defined to be regarding a Case ID, then one Case ID must be provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | CAONLY |
    When I send the request for an Outbound Correspondence to the service
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR               |
      | message | Case ID is required |

  @CP-24608-04  @API-ECMS @Prithika
  Scenario: 2.0 The Regarding values defined as being required for the Correspondence Type must be provided in the anchor element
  2.2 If the Correspondence Type is defined to be regarding a Consumer ID, then one Consumer ID must be provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY |
    When I send the request for an Outbound Correspondence to the service
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                   |
      | message | Consumer ID is required |

  @CP-24608-05  @API-ECMS @Prithika
  Scenario: 2.0 The Regarding values defined as being required for the Correspondence Type must be provided in the anchor element
  2.3 If the Correspondence Type is defined to be regarding one or more Consumer IDs, then one or more distinct (non-duplicated) Consumer IDs must be provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CONSONLY |
      | CaseID                          | empty    |
      | regardingConsumerId             | 1,4,3    |
    Then I should see a success status in the response
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CCONLY |
      | CaseID                          | empty  |
      | regardingConsumerId             | 1,1,1  |
    Then I should see failed status and the reason for the failure in the response

  @CP-24608-06 @API-ECMS @Prithika
  Scenario: 2.0 The Regarding values defined as being required for the Correspondence Type must be provided in the anchor element
  2.4 If the Correspondence Type is defined to be regarding an Application ID, then one Application ID must be provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | appIdReq    |
      | CaseID                          | empty       |
      | applicationId                   | 123         |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | TX          |
      | zipcode                         | 78641       |
      | streetAddress                   | test lane 1 |
    Then I should see a success status in the response
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | appIdReq          |
      | CaseID                          | empty             |
      | applicationId                   | previouslyCreated |
      | channelType                     | Mail              |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78641             |
      | streetAddress                   | test lane 1       |
    Then I should see failed status and the reason for the failure in the response

  @CP-24608-07  @API-ECMS @Prithika
  Scenario: 2.5 If the Correspondence Type is defined to have no required keys, then the anchor element is optional
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS      |
      | anchor                          | empty       |
      | firstName                       | test        |
      | lastName                        | test        |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | VA          |
      | zipcode                         | 78613       |
      | streetAddress                   | test lane 1 |
    Then I should see a success status in the response

  @CP-24608-08  @API-ECMS @Prithika
  Scenario: 3.0 A preferred Language may optionally be provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS      |
      | anchor                          | empty       |
      | firstName                       | test        |
      | lastName                        | test        |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | VA          |
      | zipcode                         | 78613       |
      | streetAddress                   | test lane 1 |
      | language                        | empty       |
    Then I should see a success status in the response

  @CP-24608-09  @API-ECMS @Prithika
  Scenario: 3.1 The Language must be a valid language for the project PASS
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS      |
      | anchor                          | empty       |
      | firstName                       | test        |
      | lastName                        | test        |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | VA          |
      | zipcode                         | 78613       |
      | streetAddress                   | test lane 1 |
      | language                        | Hello       |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                 |
      | message | The Language must be a valid language for the project |


  @CP-24608-10 @API-ECMS @Prithika
  Scenario: 4.1 If no recipient Consumer ID is provided, either a First Name and Last Name must be provided or the regarding values must include a Case ID or a
  Consumer ID or an Application ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS      |
      | anchor                          | empty       |
      | lastName                        | test        |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | VA          |
      | zipcode                         | 78613       |
      | streetAddress                   | test lane 1 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                                                                       |
      | message | Please provide at least one of these: caseId \|consumerId\|applicaitonId in anchor object OR (First Name, Last Name and destination detail) |

  @CP-24608-11  @API-ECMS @Prithika
  Scenario: 4.0 One or more recipients to which it is to be sent may optionally be provided
  4.1 If no recipient Consumer ID is provided, either a First Name and Last Name must be provided or the regarding values must include a Case ID or a
  Consumer ID or an Application ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYALL       |
      | anchor                          | empty          |
      | channelType                     | Fax,Email      |
      | faxNumber                       | 9899699699     |
      | emailAddress                    | test@gmail.com |
      | consumerId                      | 123            |
      | firstName                       | test           |
      | lastName                        | test           |
    Then I should see a success status in the response

  @CP-24608-12 @API-ECMS @Prithika
  Scenario: 4.1 If no recipient Consumer ID is provided, either a First Name and Last Name must be provided or the regarding values must include a Case ID or a
  Consumer ID or an Application ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS      |
      | anchor                          | empty       |
      | firstName                       | test        |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | VA          |
      | zipcode                         | 78613       |
      | streetAddress                   | test lane 1 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                                                                       |
      | message | Please provide at least one of these: caseId \|consumerId\|applicaitonId in anchor object OR (First Name, Last Name and destination detail) |

  @CP-24608-13  @API-ECMS @Prithika
  Scenario: 5.0 If one or two recipient Consumer IDs are provided, at least one channel to be used must be provided
  4.0 One or more recipients to which it is to be sent may optionally be provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send a request for Outbound Correspondence with multiple consumer Ids and channels
      | correspondenceDefinitionMMSCode | BL01 |
      | consumerId                      | 1,2  |
    Then I should see a Correspondence is created in response

  @CP-24608-14  @API-ECMS @Prithika
  Scenario: 5.1 Each Channel provided must be associated with the Correspondence Type,5.2 Each Channel provided must be active for the Correspondence Type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to retrieve a Outbound Correspondence Definition by created mmsCode "CP24608"
    And I verify the correspondence definition has a required key as "" for mmsCode "CP24608"
    And I add channels to created Outbound Correspondence Definition
      | Mail |
    And I verify Channel Exists as active on correspondence definition
      | Mail |
    And I verify Channel Does Not Exists as active on correspondence definition
      | Email |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608    |
      | anchor                          | empty      |
      | firstName                       | test       |
      | lastName                        | test       |
      | channelType                     | Text       |
      | textNumber                      | 9899699699 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                              |
      | message | Text Channel is not active for the given Correspondence Definition |

  @CP-24608-15  @API-ECMS @Prithika
  Scenario: 5.3 There cannot more than one instance of the same Channel provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase  |
      | CaseID                          | 802        |
      | channelType                     | Fax,Fax    |
      | faxNumber                       | 9899998888 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                                                     |
      | message | Request should not have duplicate channel types AND Fax: There cannot more than one instance of the same Channel provided |

  @CP-24608-16  @API-ECMS @Prithika
  Scenario: Channel Type cannot be an empty String, emptyJson, null, Invalid values
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to retrieve a Outbound Correspondence Definition by created mmsCode "CP24608"
    And I verify the correspondence definition has a required key as "" for mmsCode "CP24608"
    And I add channels to Outbound Correspondence Definition with mmsCode "CP24608"
      | Fax |
    And I verify Channel Exists as active on correspondence definition
      | Fax |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608    |
      | CaseID                          | 802        |
      | channelType                     |            |
      | faxNumber                       | 9899998888 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                |
      | message | channelType cannot be blank or empty |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608    |
      | CaseID                          | 802        |
      | channelType                     | null       |
      | faxNumber                       | 9899998888 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                |
      | message | channelType cannot be blank or empty |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608    |
      | CaseID                          | 802        |
      | channelType                     | emptyjson  |
      | faxNumber                       | 9899998888 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                |
      | message | channelType cannot be blank or empty |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608     |
      | CaseID                          | 802         |
      | channelType                     | invalidTest |
      | faxNumber                       | 9899998888  |
    Then I should see failed status and the reason for the failure in the response

  @CP-24608-17 @API-ECMS @Prithika
  Scenario: 6.0 If a Channel is provided, a destination must be provided for that Channel
  6.4 If the Channel is for the Email Channel, an email address must be provided
  6.6 If the Channel is for the Text Channel, a text phone number must be provided
  6.8 If the Channel is for the Fax Channel, a fax phone number must be provided
  6.1 If the Channel is for the Mail Channel, a delivery line (street, PO Box, etc.), a city, a state, and a zip code must all be provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to retrieve a Outbound Correspondence Definition by created mmsCode "CP24608"
    And I verify the correspondence definition has a required key as "" for mmsCode "CP24608"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608 |
      | CaseID                          | 802     |
      | channelType                     | Fax     |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                      |
      | message | If the Channel is for the Fax Channel, a fax phone number must be provided |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608 |
      | CaseID                          | 802     |
      | channelType                     | Email   |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                      |
      | message | If the Channel is for the Email Channel, an email address must be provided |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608 |
      | CaseID                          | 802     |
      | channelType                     | Text    |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                        |
      | message | If the Channel is for the Text Channel, a text phone number must be provided |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608 |
      | CaseID                          | 802     |
      | channelType                     | Mail    |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                                                                |
      | message | If the Channel is for the Mail Channel, a delivery line (street, PO Box, etc.), a city, a state, and a zip code must all be provided |

