@CP-43072a
Feature:Prevent Creation of Duplicate OB correspondences in BLCRM

  @CP-43072 @CP-43072-1.1a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId as regarding value will not be processed(BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
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


  @CP-43072 @CP-43072-1.2a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same CaseId as regarding value will create a new correspondence (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
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

  @CP-43072 @CP-43072-1.3a @API-ECMS  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same CaseId as regarding value (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
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

  @CP-43072 @CP-43072-1.4a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same CaseId as regarding value when Force-Create-Indicator as true (BLCRM)
    And I have a consumer on a case that wants to send an Outbound Correspondence
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


  @CP-43072 @CP-43072-1.5a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence has same CaseId as regarding value when Force-Create-Indicator as false (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
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

  @CP-43072 @CP-43072-1.6a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId as regarding value will not be processed with recipients(BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
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


  @CP-43072 @CP-43072-2.1a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with same ConsumerId as regarding value will not be processed(BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY      |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Random       |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY      |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Same         |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-2.2a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same ConsumerId as regarding values will create a new correspondence (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY      |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Random       |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | Different    |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Same         |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-2.3a @API-ECMS  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same ConsumerId as regarding value (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY      |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Random       |
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

  @CP-43072 @CP-43072-2.4a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same ConsumerId as regarding value when Force-Create-Indicator as true (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY      |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Random       |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CONONLY      |
      | ConsumerId             | ConsumerOnly |
      | bodyData               | Same         |
      | Force-Create-Indicator | true         |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-2.5a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence has same ConsumerId as regarding value when Force-Create-Indicator as false (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CONONLY      |
      | ConsumerId | ConsumerOnly |
      | bodyData   | Random       |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CONONLY      |
      | ConsumerId             | ConsumerOnly |
      | bodyData               | Same         |
      | Force-Create-Indicator | false        |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-2.6a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with same ConsumerId as regarding value will not be processed with recipients(BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
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


  @CP-43072 @CP-43072-3.1a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with same ApplicationId as regarding value will not be processed(BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type          | AppId             |
      | ApplicationId | previouslyCreated |
      | bodyData      | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type          | AppId |
      | ApplicationId | same  |
      | bodyData      | Same  |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-3.2a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same ApplicationId as regarding values will create a new correspondence (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type          | AppId             |
      | ApplicationId | previouslyCreated |
      | bodyData      | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type          | Different |
      | ApplicationId | same      |
      | bodyData      | Same      |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-3.3a @API-ECMS  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same ApplicationId as regarding value (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type          | AppId             |
      | ApplicationId | previouslyCreated |
      | bodyData      | Random            |
    And I change the Correspondence status of the Outbound Correspondence to "<Status>"
    When I create Outbound Correspondence with regarding values consisting of the following
      | type          | AppId |
      | ApplicationId | same  |
      | bodyData      | Same  |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence
    Examples:
      | Status   |
      | Canceled |
      | Error    |

  @CP-43072 @CP-43072-3.4a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same ApplicationId as regarding value when Force-Create-Indicator as true (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type          | AppId             |
      | ApplicationId | previouslyCreated |
      | bodyData      | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | AppId |
      | ApplicationId          | same  |
      | bodyData               | same  |
      | Force-Create-Indicator | true  |
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-3.5a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence same ApplicationId as regarding value when Force-Create-Indicator as false (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type          | AppId             |
      | ApplicationId | previouslyCreated |
      | bodyData      | Random            |
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | AppId |
      | ApplicationId          | same  |
      | bodyData               | same  |
      | Force-Create-Indicator | false |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-3.6a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with same AppId as regarding value will not be processed with recipients(BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have request to create Application with type "Medical Assistance" and name "Application for Medical Assistance"
    Then I send a request to create Application
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | AppId             |
      | APPLICATIONID                   | previouslyCreated |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    And I send the custom request OB Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | AppId             |
      | APPLICATIONID                   | previouslyCreated |
      | channelType                     | Mail              |
      | firstName                       | test              |
      | lastName                        | test              |
      | city                            | brooklyn          |
      | state                           | NY                |
      | zipcode                         | 11202             |
      | streetAddress                   | test lane 1       |
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-4.1a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId and ConsumerId as regarding value will not be processed (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY |
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY |
      | CaseId     | Same   |
      | ConsumerId | Same   |
      | bodyData   | Same   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-4.2a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with different type and with same CaseId and ConsumerId as regarding values will create a new correspondence (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY |
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type       | Different |
      | CaseId     | Same      |
      | ConsumerId | Same      |
      | bodyData   | Same      |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-4.3a @API-ECMS  @Keerthi
  Scenario Outline: Verify Outbound Correspondences will be created when existing correspondence is cancelled/Error even if same CaseId and ConsumerId as regarding value (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY |
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
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


  @CP-43072 @CP-43072-4.4a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences will be created when existing correspondence has same CaseId,ConsumerId as regarding value when Force-Create-Indicator as true (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY |
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CCONLY |
      | CaseId                 | Same   |
      | ConsumerId             | Same   |
      | bodyData               | Same   |
      | Force-Create-Indicator | true   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see a new Outbound Correspondence has been created not updating the previous Correspondence

  @CP-43072 @CP-43072-4.5a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences will not be created when existing correspondence same CaseId,ConsumerId as regarding value when Force-Create-Indicator as false (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | type       | CCONLY |
      | CaseId     | Random |
      | ConsumerId | Random |
      | bodyData   | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    When I create Outbound Correspondence with regarding values consisting of the following
      | type                   | CCONLY |
      | CaseId                 | Same   |
      | ConsumerId             | Same   |
      | bodyData               | Same   |
      | Force-Create-Indicator | false  |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    Then I should see the request to create an Outbound Correspondence was ignored

  @CP-43072 @CP-43072-4.6a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences with same CaseId,ConsumerId as regarding value will not be processed with recipients(BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
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


  @CP-43072 @CP-43072-5a @API-ECMS  @Keerthi
  Scenario: Verify Outbound Correspondences without regarding values will always create a new correspondence (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
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
