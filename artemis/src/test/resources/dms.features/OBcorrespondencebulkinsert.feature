Feature: OB Correspondence Data Migration Bulk

  @CP-39537 @CP-39537-01 @API-ECMS @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk with valid full request (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |

  @CP-39537 @CP-39537-02 @CP-40397 @CP-40397-05 @API-ECMS @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk with statusDateTime, createdDatetime, updatedDatetime as empty (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
      | Created Date                    | current   |
      | Status Date                     | current   |
      | Updated Date                    | current   |

  @CP-39537 @CP-39537-03 @CP-40397 @CP-40397-6 @API-ECMS @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk with statusDateTime, createdDatetime, updatedDatetime (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                           |
      | language                        | English                          |
      | status                          | Requested                        |
      | createdDatetime                 | 2021-11-23T20:24:44.000000-05:00 |
      | statusDatetime                  | 2021-11-23T19:24:44.000000-04:00 |
      | updatedDatetime                 | 2021-11-23T23:24:44.000000-06:00 |
      | responseDueDate                 | 2021-11-23T17:24:44.000000-04:00 |
      | caseId                          | previouslyCreated                |
      | regardingConsumerId             | previouslyCreated                |
      | createdBy                       | 2492                             |
      | notificationLanguage            | English                          |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested                        |
      | Language                        | English                          |
      | CorrespondenceDefinitionMMSCode | CCONLY                           |
      | Created Date                    | 2021-11-24T01:24:44.000000+00:00 |
      | Status Date                     | 2021-11-23T23:24:44.000000+00:00 |
      | Updated Date                    | 2021-11-24T05:24:44.000000+00:00 |
      | ResponseDue Date                | 2021-11-23T00:00:00.000000+00:00 |

  @CP-39537 @CP-39537-04 @CP-40397 @CP-40397-7 @API-ECMS @Keerthi
  Scenario: Validate auto-populate of updatedBy with the value from createdBy if updatedBy is not passed (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | createdBy                       | 1067              |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
      | Created By                      | 1067      |
      | Updated By                      | 1067      |

  @CP-39537 @CP-39537-05 @CP-40397 @CP-40397-8 @API-ECMS @Keerthi
  Scenario: Validate auto-populate of requesterId with the value from createdBy if requesterId is not passed (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | createdBy                       | 1067              |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
      | Created By                      | 1067      |
      | Requester Id                    | 1067      |

  @CP-39537 @CP-39537-06 @API-ECMS @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk without MMS Code,Status fields (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | caseId               | previouslyCreated |
      | regardingConsumerId  | previouslyCreated |
      | createdBy            | 2492              |
      | notificationLanguage | English           |
    And I validate the error message for Bulk Outbound Correspondence for missing "mmscode"
    And I validate the error message for Bulk Outbound Correspondence for missing "Status"

  @CP-39537 @CP-39537-07 @API-ECMS @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk with MMS Code,Status as null (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | empty             |
      | language                        | empty             |
      | status                          | empty             |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the error message for Bulk Outbound Correspondence for missing "mmscode"
    And I validate the error message for Bulk Outbound Correspondence for missing "Status"

  @CP-39537 @CP-39537-08 @API-ECMS @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk with MMS Code,Status as empty (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | empty             |
      | status                          | empty             |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the error message for Bulk Outbound Correspondence for missing "mmscode"
    And I validate the error message for Bulk Outbound Correspondence for missing "Status"

  @CP-39537 @CP-39537-1.1  @api-ecms-dceb @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk with valid full request (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |


  @CP-39537 @CP-39537-2.1 @CP-40397 @CP-40397-05.1 @api-ecms-dceb @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk with statusDateTime, createdDatetime, updatedDatetime as empty (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
      | Created Date                    | current   |
      | Status Date                     | current   |
      | Updated Date                    | current   |

  @CP-39537 @CP-39537-3.1 @CP-40397 @CP-40397-6.1 @api-ecms-dceb @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk with statusDateTime, createdDatetime, updatedDatetime (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                           |
      | language                        | English                          |
      | status                          | Requested                        |
      | createdDatetime                 | 2021-11-23T20:24:44.000000-05:00 |
      | statusDatetime                  | 2021-11-23T19:24:44.000000-04:00 |
      | updatedDatetime                 | 2021-11-23T23:24:44.000000-06:00 |
      | responseDueDate                 | 2021-11-23T17:24:44.000000-04:00 |
      | caseId                          | previouslyCreated                |
      | regardingConsumerId             | previouslyCreated                |
      | createdBy                       | 2492                             |
      | notificationLanguage            | English                          |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested                        |
      | Language                        | English                          |
      | CorrespondenceDefinitionMMSCode | CCONLY                           |
      | Created Date                    | 2021-11-24T01:24:44.000000+00:00 |
      | Status Date                     | 2021-11-23T23:24:44.000000+00:00 |
      | Updated Date                    | 2021-11-24T05:24:44.000000+00:00 |
      | ResponseDue Date                | 2021-11-23T00:00:00.000000+00:00 |

  @CP-39537 @CP-39537-4.1 @CP-40397 @CP-40397-7.1 @api-ecms-dceb @Keerthi
  Scenario: Validate auto-populate of updatedBy with the value from createdBy if updatedBy is not passed (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 1067              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
      | Created By                      | 1067      |
      | Updated By                      | 1067      |

  @CP-39537 @CP-39537-5.1 @CP-40397 @CP-40397-08.1 @api-ecms-dceb @Keerthi
  Scenario: Validate auto-populate of requesterId with the value from createdBy if requesterId is not passed (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 1067              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
      | Created By                      | 1067      |
      | Requester Id                    | 1067      |

  @CP-39537 @CP-39537-6.1  @api-ecms-dceb @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk without MMS Code,Status fields (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | caseId               | previouslyCreated |
      | regardingConsumerId  | previouslyCreated |
      | createdBy            | 2492              |
      | notificationLanguage | English           |
    And I validate the error message for Bulk Outbound Correspondence for missing "mmscode"
     #And I validate the error message for Bulk Outbound Correspondence for missing "Status"

  @CP-39537 @CP-39537-7.1  @api-ecms-dceb @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk with MMS Code,Status  null (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | null              |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the error message for Bulk Outbound Correspondence for missing "mmscode"
    #And I validate the error message for Bulk Outbound Correspondence for missing "Status"

  @CP-39537 @CP-39537-8.1  @api-ecms-dceb @Keerthi
  Scenario: Validate OB Correspondence Data Migration Bulk with MMS Code,Status as empty (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | empty             |
      | status                          | empty             |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the error message for Bulk Outbound Correspondence for missing "mmscode"
    #And I validate the error message for Bulk Outbound Correspondence for missing "Status"


     ############################################# CP-41578 ############################################################################

  @CP-41578 @CP-41578-01 @API-ECMS @burak
  Scenario: Validate OB Correspondence Data Migration Bulk Error Messages for CreatedBy and Notification Language Fields as empty (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | empty             |
      | notificationLanguage            | empty             |
    And I validate the error message for Bulk Outbound Correspondence for missing "createdBy"
    And I validate the error message for Bulk Outbound Correspondence for missing "NotificationLanguage"
    And I validate only 2 ErrorMessages Returned for used Bulk Correspondence Response

  @CP-41578 @CP-41578-02 @API-ECMS @burak
  Scenario: Validate OB Correspondence Data Migration Bulk Error Messages for CreatedBy and Notification Language Fields as null (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | empty           |
      | notificationLanguage            | empty           |
    And I validate the error message for Bulk Outbound Correspondence for missing "createdBy"
    And I validate the error message for Bulk Outbound Correspondence for missing "NotificationLanguage"
    And I validate only 2 ErrorMessages Returned for used Bulk Correspondence Response

  @CP-41578 @CP-41578-03 @API-ECMS @burak
  Scenario: Validate OB Correspondence Data Migration Bulk Creation without Language on Correspondence Level (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
      | Created By                      | 2492      |

  @CP-41578 @CP-41578-04 @api-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk Error Messages for CreatedBy and Notification Language Fields as empty (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | nowAvail1         |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | empty             |
      | notificationLanguage            | empty             |
    And I validate the error message for Bulk Outbound Correspondence for missing "createdBy"
    And I validate the error message for Bulk Outbound Correspondence for missing "NotificationLanguage"
    And I validate only 2 ErrorMessages Returned for used Bulk Correspondence Response

  @CP-41578 @CP-41578-05 @api-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk Error Messages for CreatedBy and Notification Language Fields as null (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | nowAvail1         |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | empty           |
      | notificationLanguage            | empty           |
    And I validate the error message for Bulk Outbound Correspondence for missing "createdBy"
    And I validate the error message for Bulk Outbound Correspondence for missing "NotificationLanguage"
    And I validate only 2 ErrorMessages Returned for used Bulk Correspondence Response

  @CP-41578 @CP-41578-06 @api-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk Creation without Language on Correspondence Level (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | nowAvail1         |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | CorrespondenceDefinitionMMSCode | nowAvail1 |
      | Created By                      | 2492      |

    ############################################# CP-40401 ############################################################################

  @CP-40401 @CP-40401-01 @ui-ecms1 @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Service Request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And Get the task type information of "General Service Request" for project ""
    And I will provide required information to create external sr with "8834" "" "" "" ""
      | Disposition |
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                    |
      | language                        | English                   |
      | status                          | Requested                 |
      | caseId                          | previouslyCreated         |
      | regardingConsumerId             | previouslyCreated         |
      | externallink                    | SERVICE_REQUEST,CRMTaskId |
      | createdBy                       | 1067                      |
      | notificationLanguage            | English                   |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Service Request" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Service Request" "previouslyCreated"
    And I shouldn't see a link between Outbound Correspondence "previouslyCreated" and "Case" "previouslyCreated"
    And I shouldn't see a link between Outbound Correspondence "previouslyCreated" and "Consumer Profile" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the service request has the proper values
      | serviceRequestType | General Service Request |

  @CP-40401 @CP-40401-02 @ui-ecms1 @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | externallink                    | Case,caseId       |
      | createdBy                       | 1067              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Case" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Case" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the case has the proper values for "PreviouslyCreated" Case

  @CP-40401 @CP-40401-03 @ui-ecms1 @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Consumer
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY              |
      | language                        | English             |
      | status                          | Requested           |
      | caseId                          | previouslyCreated   |
      | regardingConsumerId             | previouslyCreated   |
      | externallink                    | Consumer,consumerId |
      | createdBy                       | 1067                |
      | notificationLanguage            | English             |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Consumer Profile" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Consumer Profile" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the consumer has the proper values for "PreviouslyCreated" Consumer

  @CP-40401 @CP-40401-04 @ui-ecms2 @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Inbound Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | ConsumerID  | random   |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                                                 |
      | language                        | English                                                |
      | status                          | Requested                                              |
      | caseId                          | previouslyCreated                                      |
      | regardingConsumerId             | previouslyCreated                                      |
      | externallink                    | Inbound_Correspondence,InboundCorrespondenceDocumentId |
      | createdBy                       | 1067                                                   |
      | notificationLanguage            | English                                                |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Inbound Correspondence" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Inbound Correspondence" "previouslyCreated"
    And I shouldn't see a link between Outbound Correspondence "previouslyCreated" and "Case" "previouslyCreated"
    And I shouldn't see a link between Outbound Correspondence "previouslyCreated" and "Consumer Profile" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Outbound Correspondence "previouslyCreated" link to Inbound Correspondence "previouslyCreated" in the links section

  @CP-40401 @CP-40401-05 @ui-ecms2 @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                    |
      | language                        | English                   |
      | status                          | Requested                 |
      | caseId                          | previouslyCreated         |
      | regardingConsumerId             | previouslyCreated         |
      | externallink                    | Application,ApplicationId |
      | createdBy                       | 1067                      |
      | notificationLanguage            | English                   |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Application" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Application" "previouslyCreated"
    And I shouldn't see a link between Outbound Correspondence "previouslyCreated" and "Case" "previouslyCreated"
    And I shouldn't see a link between Outbound Correspondence "previouslyCreated" and "Consumer Profile" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Outbound Correspondence "previouslyCreated" link to Application "previouslyCreated" in the links section

  @CP-40401 @CP-40401-06 @ui-ecms2 @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Missing Information
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | false                         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                            |
      | language                        | English                           |
      | status                          | Requested                         |
      | caseId                          | previouslyCreated                 |
      | regardingConsumerId             | previouslyCreated                 |
      | externallink                    | Missing_Info,missingInformationId |
      | createdBy                       | 1067                              |
      | notificationLanguage            | English                           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Missing Information" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Missing Information" "previouslyCreated"
    And I shouldn't see a link between Outbound Correspondence "previouslyCreated" and "Case" "previouslyCreated"
    And I shouldn't see a link between Outbound Correspondence "previouslyCreated" and "Consumer Profile" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Outbound Correspondence "previouslyCreated" link to Missing Information "previouslyCreated" in the links section

  @CP-40401 @CP-40401-07 @ui-ecms2 @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
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
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                         |
      | language                        | English                        |
      | status                          | Requested                      |
      | caseId                          | previouslyCreated              |
      | regardingConsumerId             | previouslyCreated              |
      | externallink                    | Contact_Record,contactRecordId |
      | createdBy                       | 1067                           |
      | notificationLanguage            | English                        |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Contact Record" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Contact Record" "previouslyCreated"
    And I shouldn't see a link between Outbound Correspondence "previouslyCreated" and "Case" "previouslyCreated"
    And I shouldn't see a link between Outbound Correspondence "previouslyCreated" and "Consumer Profile" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Outbound Correspondence "previouslyCreated" link to Contact Record "previouslyCreated" in the links section

  @CP-40401 @CP-40401-08 @ui-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Service Request (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And Get the task type information of "Auto Assignment SR" for project ""
    And I will provide required information to create external sr with "16277" "" "" "" ""
      | Disposition |
    When I initiated external create sr api
    And I run the create external sr API and check the status code is "200"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                    |
      | language                        | English                   |
      | status                          | Requested                 |
      | externallink                    | SERVICE_REQUEST,CRMTaskId |
      | createdBy                       | 1067                      |
      | notificationLanguage            | English                   |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Service Request" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Service Request" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the service request has the proper values
      | serviceRequestType | Auto Assignment SR |

  @CP-40401 @CP-40401-09 @ui-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Case (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | externallink                    | Case,421          |
      | createdBy                       | 1067              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Case" "421" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Case" "421"
    Then I logged into CRM with "Service User 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the case has the proper values for "421" Case

  @CP-40401 @CP-40401-10 @ui-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Consumer (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | status                          | Requested         |
      | externallink                    | Consumer,2564     |
      | createdBy                       | 1067              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Consumer Profile" "2564" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Consumer Profile" "2564"
    Then I logged into CRM with "Service User 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Link to the consumer has the proper values for "2564" Consumer

  @CP-40401 @CP-40401-11 @ui-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Inbound Correspondence (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | ConsumerID  | random   |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                                                 |
      | language                        | English                                                |
      | status                          | Requested                                              |
      | externallink                    | Inbound_Correspondence,InboundCorrespondenceDocumentId |
      | createdBy                       | 1067                                                   |
      | notificationLanguage            | English                                                |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Inbound Correspondence" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Inbound Correspondence" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Outbound Correspondence "previouslyCreated" link to Inbound Correspondence "previouslyCreated" in the links section

  @CP-40401 @CP-40401-12 @ui-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Application (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                    |
      | language                        | English                   |
      | status                          | Requested                 |
      | externallink                    | Application,ApplicationId |
      | createdBy                       | 1067                      |
      | notificationLanguage            | English                   |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Application" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Application" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Outbound Correspondence "previouslyCreated" link to Application "previouslyCreated" in the links section

  @CP-40401 @CP-40401-13 @ui-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk API stores external Link Records for Contact Record (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
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
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                         |
      | language                        | English                        |
      | caseId                          | previouslyCreated              |
      | status                          | Requested                      |
      | externallink                    | Contact_Record,contactRecordId |
      | createdBy                       | 1067                           |
      | notificationLanguage            | English                        |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                          | Requested |
      | Language                        | English   |
      | CorrespondenceDefinitionMMSCode | CCONLY    |
    Then I should see there is a link between "Contact Record" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Contact Record" "previouslyCreated"
    Then I logged into CRM with "Service User 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I should see the Outbound Correspondence "previouslyCreated" link to Contact Record "previouslyCreated" in the links section

  ############################################# CP-41773 ############################################################################

  @CP-41773 @CP-41773-01 @API-ECMS @burak
  Scenario Outline: Verify warning messages if OB Correspondence Endpoint excludes External Links RefId (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY              |
      | status                          | Requested           |
      | externallink                    | <externallinkValue> |
      | createdBy                       | 1067                |
      | notificationLanguage            | English             |
    And I validate the error message for Bulk Outbound Correspondence for missing "externalRefId"
    And I validate only 1 ErrorMessages Returned for used Bulk Correspondence Response
    Examples:
      | externallinkValue |
      | Consumer,null     |
      | Consumer,empty    |

  @CP-41773 @CP-41773-02 @API-ECMS @burak
  Scenario Outline: Verify warning messages if OB Correspondence Endpoint excludes External Links RefType (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY              |
      | status                          | Requested           |
      | externallink                    | <externallinkValue> |
      | createdBy                       | 1067                |
      | notificationLanguage            | English             |
    And I validate the error message for Bulk Outbound Correspondence for missing "externalRefType"
    And I validate only 1 ErrorMessages Returned for used Bulk Correspondence Response
    Examples:
      | externallinkValue |
      | null,432          |
      | empty,561         |

  @CP-41773 @CP-41773-03 @API-ECMS @burak
  Scenario Outline: Verify warning messages if OB Correspondence Endpoint excludes both External Links RefType and External Links RefId (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY              |
      | status                          | Requested           |
      | externallink                    | <externallinkValue> |
      | createdBy                       | 1067                |
      | notificationLanguage            | English             |
    And I validate the error message for Bulk Outbound Correspondence for missing "externalRefId"
    And I validate the error message for Bulk Outbound Correspondence for missing "externalRefType"
    And I validate only 2 ErrorMessages Returned for used Bulk Correspondence Response
    Examples:
      | externallinkValue |
      | null,null         |
      | empty,empty       |

  @CP-41773 @CP-41773-04 @api-ecms-dceb @burak
  Scenario Outline: Verify warning messages if OB Correspondence Endpoint excludes External Links RefId (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | nowAvail1           |
      | status                          | Requested           |
      | externallink                    | <externallinkValue> |
      | createdBy                       | 1067                |
      | notificationLanguage            | English             |
    And I validate the error message for Bulk Outbound Correspondence for missing "externalRefId"
    And I validate only 1 ErrorMessages Returned for used Bulk Correspondence Response
    Examples:
      | externallinkValue |
      | Case,null         |
      | Case,empty        |

  @CP-41773 @CP-41773-05 @api-ecms-dceb @burak
  Scenario Outline: Verify warning messages if OB Correspondence Endpoint excludes External Links RefType (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | nowAvail1           |
      | status                          | Requested           |
      | externallink                    | <externallinkValue> |
      | createdBy                       | 1067                |
      | notificationLanguage            | English             |
    And I validate the error message for Bulk Outbound Correspondence for missing "externalRefType"
    And I validate only 1 ErrorMessages Returned for used Bulk Correspondence Response
    Examples:
      | externallinkValue |
      | null,432          |
      | empty,561         |

  @CP-41773 @CP-41773-06 @api-ecms-dceb @burak
  Scenario Outline: Verify warning messages if OB Correspondence Endpoint excludes both External Links RefType and External Links RefId (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | nowAvail1           |
      | status                          | Requested           |
      | externallink                    | <externallinkValue> |
      | createdBy                       | 1067                |
      | notificationLanguage            | English             |
    And I validate the error message for Bulk Outbound Correspondence for missing "externalRefId"
    And I validate the error message for Bulk Outbound Correspondence for missing "externalRefType"
    And I validate only 2 ErrorMessages Returned for used Bulk Correspondence Response
    Examples:
      | externallinkValue |
      | null,null         |
      | empty,empty       |

    ########################################################CP-40397#####################################################################

  @CP-40397 @CP-40397-01 @API-ECMS @burak
  Scenario: Validate OB Correspondence Data Migration Bulk with valid full request with links (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
      | externallink                    | Case,caseId       |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | CorrespondenceDefinitionMMSCode                 | CCONLY              |
      | Status                                          | Requested           |
      | Language                                        | English             |
      | recipients[0].recipientInfo.consumerId          | 17500               |
      | recipients[0].recipientInfo.firstName           | Bruce               |
      | recipients[0].recipientInfo.lastName            | Wayne               |
      | recipients[0].recipientInfo.role                | Primary Individual  |
      | recipients[0].recipientInfo.streetAddress       | 12 address st       |
      | recipients[0].recipientInfo.streetAddionalLine1 | 5 Travis Ln         |
      | recipients[0].recipientInfo.city                | Canaan              |
      | recipients[0].recipientInfo.state               | NY                  |
      | recipients[0].recipientInfo.zipcode             | 12029               |
      | recipients[0].recipientInfo.emailAddress        | kljasdf@liausdf.com |
      | recipients[0].recipientInfo.faxNumber           | 5552959484          |
      | recipients[0].recipientInfo.textNumber          | 3232959484          |
    Then I should see there is a link between "Case" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Case" "previouslyCreated"

  @CP-40397 @CP-40397-02 @API-ECMS @burak
  Scenario: Validate OB Correspondence Data Migration Bulk Notification Array statusDateTime, createdDatetime, updatedDatetime as empty (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | recipients[0].notifications[0].channelType                     | Mail                  |
      | recipients[0].notifications[0].language                        | English               |
      | Status                                                         | Requested             |
      | createdDateTime                                                | current date and hour |
      | statusChangedDate                                              | current date and hour |
      | updated date                                                   | current date and hour |
      | recipients[0].notifications[0].notificationStatus.returnedDate | null                  |
      | recipients[0].notifications[0].objectParentFileId              | null                  |
      | recipients[0].notifications[0].objectReceivedOn                | null                  |
      | recipients[0].notifications[0].notificationStatus.statusReason | null                  |
      | recipients[0].notifications[0].notificationStatus.errorDetail  | null                  |

  @CP-40397 @CP-40397-03 @API-ECMS @burak
  Scenario: Validate OB Correspondence Data Migration Bulk Notification statusDateTime, updatedDatetime as empty (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                           |
      | language                        | English                          |
      | status                          | Requested                        |
      | caseId                          | previouslyCreated                |
      | regardingConsumerId             | previouslyCreated                |
      | createdBy                       | 2492                             |
      | notificationLanguage            | English                          |
      | notificationCreatedDateTime     | 2021-11-23T20:24:44.000000-05:00 |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | createdDateTime   | 2021-11-24T01 |
      | statusChangedDate | 2021-11-24T01 |
      | updated date      | 2021-11-24T01 |

  @CP-40397 @CP-40397-04 @API-ECMS @burak
  Scenario: Validate auto-populate of Notification updatedBy with the value from createdBy if updatedBy is not passed (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
      | notificationCreatedBy           | 1067              |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                                   | Requested |
      | Language                                 | English   |
      | CorrespondenceDefinitionMMSCode          | CCONLY    |
      | recipients[0].notifications[0].createdBy | 1067      |
      | recipients[0].notifications[0].updatedBy | 1067      |

  @CP-40397 @CP-40397-1.1 @api-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk with valid full request with links (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
      | externallink                    | Case,caseId       |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | CorrespondenceDefinitionMMSCode                 | CCONLY              |
      | Status                                          | Requested           |
      | Language                                        | English             |
      | recipients[0].recipientInfo.consumerId          | 17500               |
      | recipients[0].recipientInfo.firstName           | Bruce               |
      | recipients[0].recipientInfo.lastName            | Wayne               |
      | recipients[0].recipientInfo.role                | Primary Individual  |
      | recipients[0].recipientInfo.streetAddress       | 12 address st       |
      | recipients[0].recipientInfo.streetAddionalLine1 | 5 Travis Ln         |
      | recipients[0].recipientInfo.city                | Canaan              |
      | recipients[0].recipientInfo.state               | NY                  |
      | recipients[0].recipientInfo.zipcode             | 12029               |
      | recipients[0].recipientInfo.emailAddress        | kljasdf@liausdf.com |
      | recipients[0].recipientInfo.faxNumber           | 5552959484          |
      | recipients[0].recipientInfo.textNumber          | 3232959484          |
    Then I should see there is a link between "Case" "previouslyCreated" and Outbound Correspondence "previouslyCreated"
    Then I should see there is a link between Outbound Correspondence "previouslyCreated" and "Case" "previouslyCreated"

  @CP-40397 @CP-40397-2.1 @api-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk Notification Array statusDateTime, createdDatetime, updatedDatetime as empty (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | recipients[0].notifications[0].channelType                     | Mail                  |
      | recipients[0].notifications[0].language                        | English               |
      | Status                                                         | Requested             |
      | createdDateTime                                                | current date and hour |
      | statusChangedDate                                              | current date and hour |
      | updated date                                                   | current date and hour |
      | recipients[0].notifications[0].notificationStatus.returnedDate | null                  |
      | recipients[0].notifications[0].objectParentFileId              | null                  |
      | recipients[0].notifications[0].objectReceivedOn                | null                  |
      | recipients[0].notifications[0].notificationStatus.statusReason | null                  |
      | recipients[0].notifications[0].notificationStatus.errorDetail  | null                  |

  @CP-40397 @CP-40397-3.1 @api-ecms-dceb @burak
  Scenario: Validate OB Correspondence Data Migration Bulk Notification statusDateTime, updatedDatetime as empty(DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY                           |
      | language                        | English                          |
      | status                          | Requested                        |
      | caseId                          | previouslyCreated                |
      | regardingConsumerId             | previouslyCreated                |
      | createdBy                       | 2492                             |
      | notificationLanguage            | English                          |
      | notificationCreatedDateTime     | 2021-11-23T20:24:44.000000-05:00 |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | createdDateTime   | 2021-11-24T01 |
      | statusChangedDate | 2021-11-24T01 |
      | updated date      | 2021-11-24T01 |

  @CP-40397 @CP-40397-4.1 @api-ecms-dceb @burak
  Scenario: Validate auto-populate of Notification updatedBy with the value from createdBy if updatedBy is not passed(DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I have specified the following values in the request for a Bulk Outbound Correspondence
      | correspondenceDefinitionMMSCode | CCONLY            |
      | language                        | English           |
      | status                          | Requested         |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | createdBy                       | 2492              |
      | notificationLanguage            | English           |
      | notificationCreatedBy           | 1067              |
    And I validate the Bulk Outbound Correspondence is created
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence response
      | Status                                   | Requested |
      | Language                                 | English   |
      | CorrespondenceDefinitionMMSCode          | CCONLY    |
      | recipients[0].notifications[0].createdBy | 1067      |
      | recipients[0].notifications[0].updatedBy | 1067      |