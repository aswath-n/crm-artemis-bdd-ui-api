Feature:Validate MiddleName and NameSuffix in Correspondence Recipient

  @CP-38036 @CP-38036-01 @API-ECMS @Keerthi
  Scenario: Validate with missing MiddleName and SuffixName in POST correspondencebulkinsert API (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | NOKEYS    |
      | language                        | English   |
      | status                          | Requested |
      | createdBy                       | 1067      |
      | notificationLanguage            | English   |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | MiddleName | null |
      | NameSuffix | null |

  @CP-38036 @CP-38036-02 @API-ECMS @Keerthi
  Scenario: Validate MiddleName and SuffixName with values in POST correspondencebulkinsert API (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | createdBy                       | 1067              |
      | notificationLanguage            | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | middleName                      | middleName_test   |
      | nameSuffix                      | Jr                |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | MiddleName | middleName_test |
      | NameSuffix | Jr              |

  @CP-38036 @CP-38036-03 @API-ECMS @Keerthi
  Scenario: Validate MiddleName and SuffixName in POST correspondences API (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | MiddleName | middleName_test |
      | NameSuffix | Jr              |

  @CP-38036 @CP-38036-04 @API-ECMS @Keerthi
  Scenario: Validate MiddleName and SuffixName in POST create notifications API (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    When I have specified the following values in the Create Outbound Notification request
      | correspondenceId | previouslyCreated |
      | notificationId   | previouslyCreated |
      | recipient        | previouslyCreated |
      | middleName       | middleName_edit   |
      | nameSuffix       | Sr                |
    And I validate following values in Create Outbound Notification response
      | middleName | middleName_edit |
      | nameSuffix | Sr              |

  @CP-38036 @CP-38036-05 @API-ECMS @Keerthi
  Scenario: Validate MiddleName and SuffixName in GET notifications API (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    When I have specified the following values in the Create Outbound Notification request
      | correspondenceId | previouslyCreated |
      | notificationId   | previouslyCreated |
      | recipient        | previouslyCreated |
      | middleName       | middleName_edit   |
      | nameSuffix       | Sr                |
    And I initiated Get Notifications retrieved from "previouslyCreated" CID
    And I validate following values in GET Outbound Notification response
      | middleName | middleName_test |
      | nameSuffix | Jr              |

  @CP-38036 @CP-38036-06 @API-ECMS @Keerthi
  Scenario: Validate MiddleName and SuffixName in POST outboundcorrespondence global search API (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    When I have a request to search Outbound Correspondence for the following values
      | correspondenceId | previouslyCreated |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | middleName | middleName_test |
      | nameSuffix | Jr              |

  @CP-38036 @CP-38036-07 @API-ECMS @Keerthi
  Scenario: Validate MiddleName and SuffixName in GET searchcorrespondence by caseId API (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CAONLY            |
      | caseId                          | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | middleName                      | middleName_test   |
      | nameSuffix                      | Jr                |
      | channelType                     | Mail              |
      | streetAddress                   | test lane 1       |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78613             |
    And I retrieve the Outbound Correspondence by "previouslyCreated" Case ID
    And I should validate following attributes in the obcorrespondencesearch by caseId
      | middleName | middleName_test |
      | nameSuffix | Jr              |

    ###########################   IN-EB #############################################
  @CP-38036 @CP-38036-1.1 @api-ecms-ineb @Keerthi
  Scenario: Validate with missing MiddleName and SuffixName in POST correspondencebulkinsert API (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | NOKEYS    |
      | language                        | English   |
      | status                          | Requested |
      | createdBy                       | 1067      |
      | notificationLanguage            | English   |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | MiddleName | null |
      | NameSuffix | null |

  @CP-38036 @CP-38036-2.1 @api-ecms-ineb @Keerthi
  Scenario: Validate MiddleName and SuffixName with values in POST correspondencebulkinsert API (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | language                        | English         |
      | status                          | Requested       |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | createdBy                       | 1067            |
      | notificationLanguage            | English         |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | MiddleName | middleName_test |
      | NameSuffix | Jr              |

  @CP-38036 @CP-38036-3.1 @api-ecms-ineb @Keerthi
  Scenario: Validate MiddleName and SuffixName in POST correspondences API (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | MiddleName | middleName_test |
      | NameSuffix | Jr              |

  @CP-38036 @CP-38036-4.1 @api-ecms-ineb @Keerthi
  Scenario: Validate MiddleName and SuffixName in POST create notifications API (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    When I have specified the following values in the Create Outbound Notification request
      | correspondenceId | previouslyCreated |
      | notificationId   | previouslyCreated |
      | recipient        | previouslyCreated |
      | middleName       | middleName_edit   |
      | nameSuffix       | Sr                |
    And I validate following values in Create Outbound Notification response
      | middleName | middleName_edit |
      | nameSuffix | Sr              |

  @CP-38036 @CP-38036-5.1 @api-ecms-ineb @Keerthi
  Scenario: Validate MiddleName and SuffixName in GET notifications API (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    When I have specified the following values in the Create Outbound Notification request
      | correspondenceId | previouslyCreated |
      | notificationId   | previouslyCreated |
      | recipient        | previouslyCreated |
      | middleName       | middleName_edit   |
      | nameSuffix       | Sr                |
    And I initiated Get Notifications retrieved from "previouslyCreated" CID
    And I validate following values in GET Outbound Notification response
      | middleName | middleName_test |
      | nameSuffix | Jr              |

  @CP-38036 @CP-38036-6.1 @api-ecms-ineb @Keerthi
  Scenario: Validate MiddleName and SuffixName in POST outboundcorrespondence global search API (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    When I have a request to search Outbound Correspondence for the following values
      | correspondenceId | previouslyCreated |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | middleName | middleName_test |
      | nameSuffix | Jr              |

  @CP-38036 @CP-38036-7.1 @api-ecms-ineb @Keerthi
  Scenario: Validate MiddleName and SuffixName in GET searchcorrespondence by caseId API (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CAONLY          |
      | caseId                          | previouslyCreated             |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    And I retrieve the Outbound Correspondence by "previouslyCreated" Case ID
    And I should validate following attributes in the obcorrespondencesearch by caseId
      | middleName | middleName_test |
      | nameSuffix | Jr              |

    ###################### DC-EB ######################

  @CP-38036 @CP-38036-1.2 @api-ecms-dceb @Keerthi
  Scenario: Validate with missing MiddleName and SuffixName in POST correspondencebulkinsert API (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | NOKEYS    |
      | language                        | English   |
      | status                          | Requested |
      | createdBy                       | 2492      |
      | notificationLanguage            | English   |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | MiddleName | null |
      | NameSuffix | null |

  @CP-38036 @CP-38036-2.2 @api-ecms-dceb @Keerthi
  Scenario: Validate MiddleName and SuffixName with values in POST correspondencebulkinsert API (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | language                        | English         |
      | status                          | Requested       |
      | createdBy                       | 2492            |
      | notificationLanguage            | English         |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | MiddleName | middleName_test |
      | NameSuffix | Jr              |

  @CP-38036 @CP-38036-3.2 @api-ecms-dceb @Keerthi
  Scenario: Validate MiddleName and SuffixName in POST correspondences API (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | MiddleName | middleName_test |
      | NameSuffix | Jr              |

  @CP-38036 @CP-38036-4.2 @api-ecms-dceb @Keerthi
  Scenario: Validate MiddleName and SuffixName in POST create notifications API (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    When I have specified the following values in the Create Outbound Notification request
      | correspondenceId | previouslyCreated |
      | notificationId   | previouslyCreated |
      | recipient        | previouslyCreated |
      | middleName       | middleName_edit   |
      | nameSuffix       | Sr                |
    And I validate following values in Create Outbound Notification response
      | middleName | middleName_edit |
      | nameSuffix | Sr              |

  @CP-38036 @CP-38036-5.2 @api-ecms-dceb @Keerthi
  Scenario: Validate MiddleName and SuffixName in GET notifications API (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    When I have specified the following values in the Create Outbound Notification request
      | correspondenceId | previouslyCreated |
      | notificationId   | previouslyCreated |
      | recipient        | previouslyCreated |
      | middleName       | middleName_edit   |
      | nameSuffix       | Sr                |
    And I initiated Get Notifications retrieved from "previouslyCreated" CID
    And I validate following values in GET Outbound Notification response
      | middleName | middleName_test |
      | nameSuffix | Jr              |

  @CP-38036 @CP-38036-6.2 @api-ecms-dceb @Keerthi
  Scenario: Validate MiddleName and SuffixName in POST outboundcorrespondence global search API (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | anchor                          | empty           |
      | firstName                       | test            |
      | lastName                        | test            |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
      | channelType                     | Mail            |
      | streetAddress                   | test lane 1     |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
    When I have a request to search Outbound Correspondence for the following values
      | correspondenceId | previouslyCreated |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | middleName | middleName_test |
      | nameSuffix | Jr              |

  @CP-38036 @CP-38036-7.2 @api-ecms-dceb @Keerthi
  Scenario: Validate MiddleName and SuffixName in GET searchcorrespondence by caseId API (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | J        |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I build an Outbound Correspondence request with a recipient containing the following values
      | correspondenceDefinitionMMSCode | CAONLY                       |
      | caseId                          | DCEBcaseid                   |
      | recipients                      | random                       |
      | channel                         | Mail                         |
      | language                        | English                      |
    When I send the request "previouslyCreated" to create an Outbound Correspondence
    And I retrieve the Outbound Correspondence by "previouslyCreated" Case ID
    And I should validate following attributes in the obcorrespondencesearch by caseId
      | middleName | J   |
      | nameSuffix | DDS |
    ##################################################### CP-41596 ###########################################################

  @CP-41596 @CP-41596-01 @API-ECMS @Keerthi
  Scenario: Validate MiddleName and SuffixName in letterdata by notifications When provisioning Correspondence(BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole       | Primary Individual            |
      | consumerSuffix     | Jr                            |
      | consumerMiddleName | middleName_test               |
      | mailDestination    | random active mailing address |
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | type       | CCONLY            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I initiate Get letter data by the retrieved NID from OBCorrespondence
    And I verify get letter data response contains following values
      | recipientNameSuffix | Jr              |
      | recipientMiddleName | middleName_test |

  @CP-41596 @CP-41596-02 @API-ECMS @Keerthi
  Scenario: Validate MiddleName and SuffixName in letterdata by notifications When Creating Correspondence(BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | language                        | English         |
      | channelType                     | Mail            |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
      | streetAddress                   | test lane 1     |
      | streetAddionalLine1             | test lane 2     |
      | firstName                       | testA           |
      | lastName                        | testC           |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
    And I initiate Get letter data by the retrieved NID from OBCorrespondence
    And I verify get letter data response contains following values
      | recipientNameSuffix | Jr              |
      | recipientMiddleName | middleName_test |

  @CP-41596 @CP-41596-03 @API-ECMS @Keerthi
  Scenario: Validate MiddleName and SuffixName in letterdata by notifications for Resend notification(BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | language                        | English         |
      | channelType                     | Mail            |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
      | streetAddress                   | test lane 1     |
      | streetAddionalLine1             | test lane 2     |
      | firstName                       | testA           |
      | lastName                        | testC           |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
    When I have specified the following values in the Create Outbound Notification request
      | correspondenceId | previouslyCreated |
      | notificationId   | previouslyCreated |
      | recipient        | previouslyCreated |
    And I validate following values in Create Outbound Notification response
      | notificationid | created |
    And I initiate Get letter data by notificationId for "resendnotificationid"
    And I verify get letter data response contains following values
      | recipientNameSuffix | Jr              |
      | recipientMiddleName | middleName_test |

  @CP-41596 @CP-41596-2.1 @api-ecms-ineb @Keerthi
  Scenario: Validate MiddleName and SuffixName in letterdata by notifications When Creating Correspondence(INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | language                        | English         |
      | channelType                     | Mail            |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
      | streetAddress                   | test lane 1     |
      | streetAddionalLine1             | test lane 2     |
      | firstName                       | testA           |
      | lastName                        | testC           |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
    And I initiate Get letter data by the retrieved NID from OBCorrespondence
    And I verify get letter data response contains following values
      | recipientNameSuffix | Jr              |
      | recipientMiddleName | middleName_test |

  @CP-41596 @CP-41596-3.1 @api-ecms-ineb @Keerthi
  Scenario: Validate MiddleName and SuffixName in letterdata by notifications for Resend notification(INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | language                        | English         |
      | channelType                     | Mail            |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
      | streetAddress                   | test lane 1     |
      | streetAddionalLine1             | test lane 2     |
      | firstName                       | testA           |
      | lastName                        | testC           |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
    When I have specified the following values in the Create Outbound Notification request
      | correspondenceId | previouslyCreated |
      | notificationId   | previouslyCreated |
      | recipient        | previouslyCreated |
    And I validate following values in Create Outbound Notification response
      | notificationid | created |
    And I initiate Get letter data by notificationId for "resendnotificationid"
    And I verify get letter data response contains following values
      | recipientNameSuffix | Jr              |
      | recipientMiddleName | middleName_test |

  @CP-41596 @CP-41596-2.2 @api-ecms-dceb @Keerthi
  Scenario: Validate MiddleName and SuffixName in letterdata by notifications When Creating Correspondence(DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | language                        | English         |
      | channelType                     | Mail            |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
      | streetAddress                   | test lane 1     |
      | streetAddionalLine1             | test lane 2     |
      | firstName                       | testA           |
      | lastName                        | testC           |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
    And I initiate Get letter data by the retrieved NID from OBCorrespondence
    And I verify get letter data response contains following values
      | recipientNameSuffix | Jr              |
      | recipientMiddleName | middleName_test |

  @CP-41596 @CP-41596-3.2 @api-ecms-dceb @Keerthi
  Scenario: Validate MiddleName and SuffixName in letterdata by notifications for Resend notification(DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS          |
      | language                        | English         |
      | channelType                     | Mail            |
      | city                            | cedar park      |
      | state                           | TX              |
      | zipcode                         | 78613           |
      | streetAddress                   | test lane 1     |
      | streetAddionalLine1             | test lane 2     |
      | firstName                       | testA           |
      | lastName                        | testC           |
      | middleName                      | middleName_test |
      | nameSuffix                      | Jr              |
    When I have specified the following values in the Create Outbound Notification request
      | correspondenceId | previouslyCreated |
      | notificationId   | previouslyCreated |
      | recipient        | previouslyCreated |
    And I validate following values in Create Outbound Notification response
      | notificationid | created |
    And I initiate Get letter data by notificationId for "resendnotificationid"
    And I verify get letter data response contains following values
      | recipientNameSuffix | Jr              |
      | recipientMiddleName | middleName_test |
