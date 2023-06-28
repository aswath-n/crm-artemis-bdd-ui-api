@CP-43072c
Feature:Prevent Creation of Duplicate OB correspondences in DCEB

  @CP-43072 @CP-43072-1.1c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId as regarding value will not be processed(DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY   |
      | CaseId   | CaseOnly |
      | bodyData | Random   |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY |
      | CaseId   | Same   |
      | bodyData | Same   |
    Then I should see the request to create an Outbound Correspondence was ignored


  @CP-43072 @CP-43072-1.2c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same CaseId as regarding value will create a new correspondence (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY   |
      | CaseId   | CaseOnly |
      | bodyData | Random   |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type     | Different |
      | CaseId   | Same      |
      | bodyData | Same      |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-1.3c  @api-ecms-dceb  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same CaseId as regarding value (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY   |
      | CaseId   | CaseOnly |
      | bodyData | Random   |
    And I change the Correspondence status of the Outbound Correspondence to "<Status>"
    When I create Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY |
      | CaseId   | Same   |
      | bodyData | Same   |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence
    Examples:
      | Status   |
      | Canceled |
      | Error    |

  @CP-43072 @CP-43072-1.4c  @api-ecms-dceb @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same CaseId as regarding value when Force-Create-Indicator as true (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY   |
      | CaseId   | CaseOnly |
      | bodyData | Random   |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CAONLY |
      | CaseId                 | Same   |
      | bodyData               | Same   |
      | Force-Create-Indicator | true   |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence


  @CP-43072 @CP-43072-1.5c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence has same CaseId as regarding value when Force-Create-Indicator as false (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY   |
      | CaseId   | CaseOnly |
      | bodyData | Random   |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CAONLY |
      | CaseId                 | Same   |
      | bodyData               | Same   |
      | Force-Create-Indicator | false  |
    Then I should see the request to create an Outbound Correspondence was ignored

 @CP-43072 @CP-43072-1.6c  @api-ecms-dceb  @Keerthi
 Scenario: Verify Outbound Correspondences with same CaseId as regarding value will not be processed with recipients(DCEB)
   Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CAONLY            |
      | caseId                          | previouslyCreated |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    And I send the custom request OB Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CAONLY            |
      | caseId                          | previouslyCreated |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    Then I should see the request to create an Outbound Correspondence was ignored


  @CP-43072 @CP-43072-2.1c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences with same ConsumerId as regarding value will not be processed(DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY          |
      | ConsumerId | DCEBConsumerOnly |
      | bodyData   | Random           |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY      |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Same         |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-2.2c  @api-ecms-dceb @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same ConsumerId as regarding values will create a new correspondence (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY          |
      | ConsumerId | DCEBConsumerOnly |
      | bodyData   | Random           |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | Different    |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Same         |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-2.3c  @api-ecms-dceb  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same ConsumerId as regarding value (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY          |
      | ConsumerId | DCEBConsumerOnly |
      | bodyData   | Random           |
    And I change the Correspondence status of the Outbound Correspondence to "<Status>"
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY      |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Same         |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence
    Examples:
      | Status   |
      | Canceled |
      | Error    |

  @CP-43072 @CP-43072-2.4c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same ConsumerId as regarding value when Force-Create-Indicator as true (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY          |
      | ConsumerId | DCEBConsumerOnly |
      | bodyData   | Random           |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CONONLY      |
      | ConsumerId             | ConsumerOnly |
      | bodyData               | Same         |
      | Force-Create-Indicator | true         |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-2.5c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence has same ConsumerId as regarding value when Force-Create-Indicator as false (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY          |
      | ConsumerId | DCEBConsumerOnly |
      | bodyData   | Random           |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CONONLY      |
      | ConsumerId             | ConsumerOnly |
      | bodyData               | Same         |
      | Force-Create-Indicator | false        |
    Then I should see the request to create an Outbound Correspondence was ignored

 @CP-43072 @CP-43072-2.6c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences with same ConsumerId as regarding value will not be processed with recipients(DCEB)
   Given I will get the Authentication token for "DC-EB" in "CRM"
   And I initiate consumers V2 API
   When I run consumers API name "43072-01" with following payload
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
   And I stored DCEB case and consumer id to caseConsumerId variable
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CONONLY           |
      | regardingConsumerId             | previouslyCreated |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    And I send the custom request OB Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CONONLY           |
      | regardingConsumerId             | previouslyCreated |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-3.1c   @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId and ConsumerIds as regarding value will not be processed (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY |
      | CaseId     | Same    |
      | ConsumerId | Same    |
      | bodyData   | Same    |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-3.2c  @api-ecms-dceb @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same CaseId and ConsumerIds as regarding values will create a new correspondence (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | Different |
      | CaseId     | Same      |
      | ConsumerId | Same      |
      | bodyData   | Same      |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-3.3c  @api-ecms-dceb  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same CaseId and ConsumerIds as regarding value (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I change the Correspondence status of the Outbound Correspondence to "<Status>"
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY |
      | CaseId     | Same    |
      | ConsumerId | Same    |
      | bodyData   | Same    |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence
    Examples:
      | Status   |
      | Canceled |
      | Error    |


  @CP-43072 @CP-43072-3.4c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same CaseId,ConsumerIds as regarding value when Force-Create-Indicator as true (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CCSONLY |
      | CaseId                 | Same    |
      | ConsumerId             | Same    |
      | bodyData               | Same    |
      | Force-Create-Indicator | true    |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-3.5c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence same CaseId,ConsumerIds as regarding value when Force-Create-Indicator as false (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CCSONLY |
      | CaseId                 | Same    |
      | ConsumerId             | Same    |
      | bodyData               | Same    |
      | Force-Create-Indicator | false   |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-3.6c @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences with different consumer Ids will update the anchor of existing Correspondence (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY   |
      | CaseId     | Same      |
      | ConsumerId | Different |
      | bodyData   | Same      |
    Then I should see the Outbound Correspondence was updated with the following values
      | ConsumerId | Different |

  @CP-43072 @CP-43072-3.7c @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId,ConsumerIds as regarding value will not be processed with recipients(DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CCSONLY           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | 1,2               |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    And I send the custom request OB Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CCSONLY           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | 1,2               |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-4.1c   @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId and ConsumerId as regarding value will not be processed (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY            |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY |
      | CaseId     | Same   |
      | ConsumerId | Same   |
      | bodyData   | Same   |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-4.2c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same CaseId and ConsumerId as regarding values will create a new correspondence (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY            |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | Different |
      | CaseId     | Same      |
      | ConsumerId | Same      |
      | bodyData   | Same      |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-4.3c  @api-ecms-dceb  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same CaseId and ConsumerId as regarding value (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY            |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I change the Correspondence status of the Outbound Correspondence to "<Status>"
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY |
      | CaseId     | Same   |
      | ConsumerId | Same   |
      | bodyData   | Same   |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence
    Examples:
      | Status   |
      | Canceled |
      | Error    |


  @CP-43072 @CP-43072-4.4c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same CaseId,ConsumerId as regarding value when Force-Create-Indicator as true (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY            |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CCONLY |
      | CaseId                 | Same   |
      | ConsumerId             | Same   |
      | bodyData               | Same   |
      | Force-Create-Indicator | true   |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-4.5c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence same CaseId,ConsumerId as regarding value when Force-Create-Indicator as false (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY            |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CCONLY |
      | CaseId                 | Same   |
      | ConsumerId             | Same   |
      | bodyData               | Same   |
      | Force-Create-Indicator | false  |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-4.6c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId,ConsumerId as regarding value will not be processed with recipients(DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "43072-01" with following payload
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
    And I stored DCEB case and consumer id to caseConsumerId variable
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CCONLY            |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    And I send the custom request OB Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CCONLY            |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    Then I should see the request to create an Outbound Correspondence was ignored
    
  @CP-43072 @CP-43072-5c  @api-ecms-dceb  @Keerthi
  Scenario: Verify Outbound Correspondences without regarding values will always create a new correspondence (DCEB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS      |
      | channelType                     | Mail        |
      | firstName                       | test        |
      | lastName                        | test        |
      | city                            | brooklyn    |
      | state                           | NY          |
      | zipcode                         | 11202       |
      | streetAddress                   | test lane 1 |
    And I send the custom request OB Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | NOKEYS      |
      | channelType                     | Mail        |
      | firstName                       | test        |
      | lastName                        | test        |
      | city                            | brooklyn    |
      | state                           | NY          |
      | zipcode                         | 11202       |
      | streetAddress                   | test lane 1 |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence