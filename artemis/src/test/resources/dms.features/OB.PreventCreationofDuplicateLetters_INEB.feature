@CP-43072b
Feature:Prevent Creation of Duplicate OB correspondences in INEB

  @CP-43072 @CP-43072-1.1b  @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId as regarding value will not be processed(INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY   |
      | CaseId   | CaseOnly |
      | bodyData | Random   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY |
      | CaseId   | Same   |
      | bodyData | Same   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored


  @CP-43072 @CP-43072-1.2b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same CaseId as regarding value will create a new correspondence (INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY   |
      | CaseId   | CaseOnly |
      | bodyData | Random   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type     | Different |
      | CaseId   | Same      |
      | bodyData | Same      |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-1.3b @api-ecms-ineb  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same CaseId as regarding value (INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY   |
      | CaseId   | CaseOnly |
      | bodyData | Random   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I change the Correspondence status of the Outbound Correspondence to "<Status>"
    When I create Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY |
      | CaseId   | Same   |
      | bodyData | Same   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence
    Examples:
      | Status   |
      | Canceled |
      | Error    |

  @CP-43072 @CP-43072-1.4b @api-ecms-ineb @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same CaseId as regarding value when Force-Create-Indicator as true (INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY   |
      | CaseId   | CaseOnly |
      | bodyData | Random   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CAONLY |
      | CaseId                 | Same   |
      | bodyData               | Same   |
      | Force-Create-Indicator | true   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence


  @CP-43072 @CP-43072-1.5b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence has same CaseId as regarding value when Force-Create-Indicator as false (INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type     | CAONLY   |
      | CaseId   | CaseOnly |
      | bodyData | Random   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CAONLY |
      | CaseId                 | Same   |
      | bodyData               | Same   |
      | Force-Create-Indicator | false  |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-1.6b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId as regarding value will not be processed with recipients(INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
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

  @CP-43072 @CP-43072-2.1b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with same ConsumerId as regarding value will not be processed(INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY          |
      | ConsumerId | INEBConsumerOnly |
      | bodyData   | Random           |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY      |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Same         |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-2.2b @api-ecms-ineb @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same ConsumerId as regarding values will create a new correspondence (INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY          |
      | ConsumerId | INEBConsumerOnly |
      | bodyData   | Random           |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | Different    |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Same         |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-2.3b @api-ecms-ineb  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same ConsumerId as regarding value (INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY          |
      | ConsumerId | INEBConsumerOnly |
      | bodyData   | Random           |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I change the Correspondence status of the Outbound Correspondence to "<Status>"
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY      |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Same         |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence
    Examples:
      | Status   |
      | Canceled |
      | Error    |

  @CP-43072 @CP-43072-2.4b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same ConsumerId as regarding value when Force-Create-Indicator as true (INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY          |
      | ConsumerId | INEBConsumerOnly |
      | bodyData   | Random           |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CONONLY      |
      | ConsumerId             | ConsumerOnly |
      | bodyData               | Same         |
      | Force-Create-Indicator | true         |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-2.5b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence has same ConsumerId as regarding value when Force-Create-Indicator as false (INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY          |
      | ConsumerId | INEBConsumerOnly |
      | bodyData   | Random           |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CONONLY      |
      | ConsumerId             | ConsumerOnly |
      | bodyData               | Same         |
      | Force-Create-Indicator | false        |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-2.6b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with same ConsumerId as regarding value will not be processed with recipients(INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
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


  @CP-43072 @CP-43072-3.1b  @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId and ConsumerIds as regarding value will not be processed (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY |
      | CaseId     | Same    |
      | ConsumerId | Same    |
      | bodyData   | Same    |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-3.2b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same CaseId and ConsumerIds as regarding values will create a new correspondence (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | Different |
      | CaseId     | Same      |
      | ConsumerId | Same      |
      | bodyData   | Same      |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-3.3b @api-ecms-ineb  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same CaseId and ConsumerIds as regarding value (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I change the Correspondence status of the Outbound Correspondence to "<Status>"
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY |
      | CaseId     | Same    |
      | ConsumerId | Same    |
      | bodyData   | Same    |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence
    Examples:
      | Status   |
      | Canceled |
      | Error    |


  @CP-43072 @CP-43072-3.4b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same CaseId,ConsumerIds as regarding value when Force-Create-Indicator as true (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CCSONLY |
      | CaseId                 | Same    |
      | ConsumerId             | Same    |
      | bodyData               | Same    |
      | Force-Create-Indicator | true    |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-3.5b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence same CaseId,ConsumerIds as regarding value when Force-Create-Indicator as false (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CCSONLY |
      | CaseId                 | Same    |
      | ConsumerId             | Same    |
      | bodyData               | Same    |
      | Force-Create-Indicator | false   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-3.6b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with different consumer Ids will update the anchor of existing Correspondence (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY           |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCSONLY   |
      | CaseId     | Same      |
      | ConsumerId | Different |
      | bodyData   | Same      |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the Outbound Correspondence was updated with the following values
      | ConsumerId | Different |

  @CP-43072 @CP-43072-3.7b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId,ConsumerIds as regarding value will not be processed with recipients(INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
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


  @CP-43072 @CP-43072-4.1b  @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId and ConsumerId as regarding value will not be processed (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY            |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY |
      | CaseId     | Same   |
      | ConsumerId | Same   |
      | bodyData   | Same   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-4.2b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same CaseId and ConsumerId as regarding values will create a new correspondence (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY            |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | Different |
      | CaseId     | Same      |
      | ConsumerId | Same      |
      | bodyData   | Same      |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-4.3b @api-ecms-ineb  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same CaseId and ConsumerId as regarding value (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY            |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I change the Correspondence status of the Outbound Correspondence to "<Status>"
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY |
      | CaseId     | Same   |
      | ConsumerId | Same   |
      | bodyData   | Same   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence
    Examples:
      | Status   |
      | Canceled |
      | Error    |


  @CP-43072 @CP-43072-4.4b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same CaseId,ConsumerId as regarding value when Force-Create-Indicator as true (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY            |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CCONLY |
      | CaseId                 | Same   |
      | ConsumerId             | Same   |
      | bodyData               | Same   |
      | Force-Create-Indicator | true   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-4.5b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence same CaseId,ConsumerId as regarding value when Force-Create-Indicator as false (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY            |
      | CaseId     | previouslyCreated |
      | ConsumerId | previouslyCreated |
      | bodyData   | Random            |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CCONLY |
      | CaseId                 | Same   |
      | ConsumerId             | Same   |
      | bodyData               | Same   |
      | Force-Create-Indicator | false  |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-4.6b @api-ecms-ineb  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId,ConsumerId as regarding value will not be processed with recipients(INEB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
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


  @CP-43072 @CP-43072-5b @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences without regarding values will always create a new correspondence (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
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
