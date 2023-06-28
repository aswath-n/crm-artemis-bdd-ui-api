Feature: DCEB bodyDataSource Endpoint to Capture Body Data Elements

  @CP-39622 @CP-39622-01 @api-ecms-dceb @Keerthi
  Scenario: Verify Body Data Source endpoint for DCHF Enrollment Packet letter(100) with one consumer
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39622-1" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | Jr       |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I initiate Case Correspondence API
      | caseId                      | DCEBcaseid |
      | includeNonDefaultRecipients | true       |
    And I retrieve Website pin for "DCEBcaseid" caseId
    And I initiate body data source api
      | correspondenceDefinitionMMSCode | 100           |
      | caseId                          | DCEBcaseid    |
      | regardingConsumerId             | consumerId[0] |
    And I validate body data source api
      | statusCode | 200                        |
      | websitePin | previouslycreatedcaseidpin |
      | firstName  | consumerId[0].firstName    |
      | lastName   | consumerId[0].lastName     |
      | middleName | consumerId[0].middleName   |
      | suffixName | consumerId[0].suffixName   |


  @CP-39622 @CP-39622-02 @api-ecms-dceb @Keerthi
  Scenario: Verify Body Data Source endpoint for DCHF Enrollment Packet letter(100) with two consumers
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39622-1" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | Jr       |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I run consumers API name "39622-2" with following payload
      | consumerRequests[0].uuid                       | random                                      |
      | consumerRequests[0].dataSource                 | MMIS                                        |
      | consumerRequests[0].firstName                  | random                                      |
      | consumerRequests[0].lastName                   | random                                      |
      | consumerRequests[0].middleName                 | random                                      |
      | consumerRequests[0].suffix                     | Sr                                          |
      | consumerRequests[0].ssn                        | random                                      |
      | consumerRequests[0].dateOfBirth                | random                                      |
      | consumerRequests[0].consumerProfile.type       | MEDICAID                                    |
      | consumerRequests[0].consumerProfile.externalId | random                                      |
      | consumerRequests[0].case.type                  | MEDICAID                                    |
      | consumerRequests[0].case.externalId            | 39622-1.consumerRequests[0].case.externalId |
    And I initiate Case Correspondence API
      | caseId                      | DCEBcaseid |
      | includeNonDefaultRecipients | true       |
    And I retrieve Website pin for "DCEBcaseid" caseId
    And I initiate body data source api
      | correspondenceDefinitionMMSCode | 100                         |
      | caseId                          | DCEBcaseid                  |
      | regardingConsumerId             | consumerId[0],consumerId[1] |
    And I validate body data source api
      | statusCode | 200                        |
      | websitePin | previouslycreatedcaseidpin |
      | firstName  | consumerId[0].firstName    |
      | lastName   | consumerId[0].lastName     |
      | middleName | consumerId[0].middleName   |
      | suffixName | consumerId[0].suffixName   |
    And I validate body data source api
      | firstName  | consumerId[1].firstName  |
      | lastName   | consumerId[1].lastName   |
      | middleName | consumerId[1].middleName |
      | suffixName | consumerId[1].suffixName |

  @CP-39622 @CP-39622-03 @api-ecms-dceb @Keerthi
  Scenario: Verify Body Data Source endpoint for DCHF Enrollment Packet letter(100) without correspondenceDefinitionMMSCode
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate body data source api
      | caseId              | 1484 |
      | regardingConsumerId | 2754 |
    And I validate body data source api
      | statusCode   | 400                                                                              |
      | errorMessage | correspondenceDefinitionMMSCode is a required field and can not be null or empty |

  @CP-39622 @CP-39622-04 @api-ecms-dceb @Keerthi
  Scenario: Verify Body Data Source endpoint for DCHF Enrollment Packet letter(100) without caseId
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate body data source api
      | correspondenceDefinitionMMSCode | 100  |
      | regardingConsumerId             | 2754 |
    And I validate body data source api
      | statusCode         | 200  |
      | caseIderrorMessage | FAIL |

  @CP-39622 @CP-39622-05 @api-ecms-dceb @Keerthi
  Scenario: Verify Body Data Source endpoint for DCHF Enrollment Packet letter(100) without regardingConsumerId
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate body data source api
      | correspondenceDefinitionMMSCode | 100  |
      | caseId                          | 1484 |
    And I validate body data source api
      | statusCode   | 400                                                                  |
      | errorMessage | regardingConsumerId is a required field and can not be null or empty |

  @CP-39622 @CP-39622-06 @api-ecms-dceb @Keerthi
  Scenario: Verify Body Data Source endpoint for DCHF Enrollment Packet letter(100) without pinvalue
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate body data source api
      | correspondenceDefinitionMMSCode | 100  |
      | caseId                          | 1484 |
      | regardingConsumerId             | 111  |
    And I validate body data source api
      | statusCode   | 400                |
      | errorMessage | No Pin value found |


################################################ CP-39624 ##########################################################

  @CP-39624 @CP-39624-01 @api-ecms-dceb @Keerthi
  Scenario: Verify Body Data Source endpoint for  DCHF Reminder Notice letter(102) with one consumer
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39624-1" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | Jr       |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I initiate Case Correspondence API
      | caseId                      | DCEBcaseid |
      | includeNonDefaultRecipients | true       |
    And I have a request to create a new Enrollment for "consumerId[0]"
    And I send the request to create a new Enrollment
    And Wait for 10 seconds
    And I initiate get benefit status for consumer id "consumerId[0]"
    And I initiate body data source api
      | correspondenceDefinitionMMSCode | 102           |
      | caseId                          | DCEBcaseid    |
      | regardingConsumerId             | consumerId[0] |
    And I validate body data source api
      | statusCode | 200                              |
      | dueDate    | changeAllowedUntil_consumerId[0] |
      | firstName  | consumerId[0].firstName          |
      | lastName   | consumerId[0].lastName           |
      | middleName | consumerId[0].middleName         |
      | suffixName | consumerId[0].suffixName         |

  @CP-39624 @CP-39624-02 @api-ecms-dceb @Keerthi
  Scenario: Verify Body Data Source endpoint for  DCHF Reminder Notice letter(102) with two consumer
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39624-1" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | Jr       |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I run consumers API name "39624-2" with following payload
      | consumerRequests[0].uuid                       | random                                      |
      | consumerRequests[0].dataSource                 | MMIS                                        |
      | consumerRequests[0].firstName                  | random                                      |
      | consumerRequests[0].lastName                   | random                                      |
      | consumerRequests[0].middleName                 | random                                      |
      | consumerRequests[0].suffix                     | Sr                                          |
      | consumerRequests[0].ssn                        | random                                      |
      | consumerRequests[0].dateOfBirth                | random                                      |
      | consumerRequests[0].consumerProfile.type       | MEDICAID                                    |
      | consumerRequests[0].consumerProfile.externalId | random                                      |
      | consumerRequests[0].case.type                  | MEDICAID                                    |
      | consumerRequests[0].case.externalId            | 39624-1.consumerRequests[0].case.externalId |
    And I initiate Case Correspondence API
      | caseId                      | DCEBcaseid |
      | includeNonDefaultRecipients | true       |
    And I have a request to create a new Enrollment for "consumerId[0]"
    And I send the request to create a new Enrollment
    And Wait for 10 seconds
    And I initiate get benefit status for consumer id "consumerId[0]"
    And I have a request to create a new Enrollment for "consumerId[1]"
    And I send the request to create a new Enrollment
    And Wait for 10 seconds
    And I update following data in consumer actions for consumerId "consumerId[1]"
      | changeAllowedUntil | 2999-04-17 |
    And Wait for 10 seconds
    And I initiate get benefit status for consumer id "consumerId[1]"
    And I initiate body data source api
      | correspondenceDefinitionMMSCode | 102                         |
      | caseId                          | DCEBcaseid                  |
      | regardingConsumerId             | consumerId[0],consumerId[1] |
    And I validate body data source api
      | statusCode | 200                             |
      | dueDate    | changeAllowedUntil_earliestdate |
      | firstName  | consumerId[0].firstName         |
      | lastName   | consumerId[0].lastName          |
      | middleName | consumerId[0].middleName        |
      | suffixName | consumerId[0].suffixName        |
    And I validate body data source api
      | firstName  | consumerId[1].firstName  |
      | lastName   | consumerId[1].lastName   |
      | middleName | consumerId[1].middleName |
      | suffixName | consumerId[1].suffixName |

  @CP-39624 @CP-39624-03 @api-ecms-dceb @Keerthi
  Scenario: Verify Body Data Source endpoint for DCHF Reminder Notice letter(102) without duedate
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39624-1" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | Jr       |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I initiate Case Correspondence API
      | caseId                      | DCEBcaseid |
      | includeNonDefaultRecipients | true       |
    And I initiate body data source api
      | correspondenceDefinitionMMSCode | 102           |
      | caseId                          | DCEBcaseid    |
      | regardingConsumerId             | consumerId[0] |
    And I validate body data source api
      | statusCode   | 400                                      |
      | errorMessage | No Enroll changeAllowedUntil value found |


