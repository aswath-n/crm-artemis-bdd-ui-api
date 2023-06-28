@CP-2941
Feature: Link Inbound Correspondence to CaseConsumer from CP

  @CP-2941 @CP-2941-1.0 @ui-ecms2 @Keerthi
  Scenario: Validate case and consumer links for type having caseId and consumerId field in onbase for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to edit an Inbound Correspondence Type by the name of "maersk Case + Consumer"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType | RECEIVED |
      | CaseID      | random   |
      | ConsumerID  | random   |
      | CHANNEL     | Mail     |
    When I send the request to create the Inbound Correspondence Save Event
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I validate viewing Case and consumer link to the Inbound Correspondence with "RANDOM" values in cp
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Consumer Id "RANDOM"
    Then I should see there is a link between Consumer Id "RANDOM" and Inbound Correspondence Document "previouslyCreated"
    Then I should see there is a link between Inbound Correspondence Document "previouslyCreated" and Case Id "RANDOM"
    Then I should see there is a link between Case Id "RANDOM" and Inbound Correspondence Document "previouslyCreated"

  @CP-2941 @CP-2941-2.0 @ui-ecms2 @Keerthi
  Scenario: Validate case and consumer search results for ib in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    Then I validate "CASE & CONSUMER SEARCH" is present in view inbound correspondence details page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I validate the search results for case and consumer search


  @CP-2941 @CP-2941-3.0 @ui-ecms2 @Keerthi
  Scenario: Validate Linking and viewing Case only to the Correspondence in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | fileType     | pdf                     |
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I validate Linking a "Case" to the Inbound Correspondence
    And I validate viewing Case link to the Inbound Correspondence in cp
    And I retrieve the "LINK_EVENT_CASE_IB_CORRESPONDENCE" event that is produced by trace id
    Then I should see the payload for the "LINK_EVENT_CASE_IB_CORRESPONDENCE" is as expected
    And I should see that the event mapping for "LINK_EVENT" exists
    And I should see that the "LINK_EVENT" record is produced to DPBI

  @CP-2941 @CP-2941-4.0 @ui-ecms2 @Keerthi
  Scenario: Validate Linking Case and consumer and viewing case link to the Correspondence in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | fileType     | pdf                     |
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I validate Linking a "Case and Consumer" to the Inbound Correspondence
    And I validate viewing Case and consumer link to the Inbound Correspondence with "previouslycreated" values in cp
    And I retrieve the "LINK_EVENT_CASE_IB_CORRESPONDENCE" event that is produced by trace id
    Then I should see the payload for the "LINK_EVENT_CASE_IB_CORRESPONDENCE" is as expected
    And I should see that the event mapping for "LINK_EVENT" exists
    And I should see that the "LINK_EVENT" record is produced to DPBI

  @CP-2941 @CP-2941-5.0 @ui-ecms2 @Keerthi
  Scenario: Validate Linking Case and consumer and viewing consumer link to the Correspondence in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | fileType     | pdf                     |
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I validate Linking a "Case and Consumer" to the Inbound Correspondence
    And I validate viewing Case and consumer link to the Inbound Correspondence with "previouslycreated" values in cp
    And I retrieve the "LINK_EVENT_CASECONSUMER_IB_CORRESPONDENCE" event that is produced by trace id
    Then I should see the payload for the "LINK_EVENT_CONSUMER_IB_CORRESPONDENCE" is as expected
    And I should see that the event mapping for "LINK_EVENT" exists
    And I should see that the "LINK_EVENT" record is produced to DPBI

  @CP-2941 @CP-2941-6.0 @ui-ecms2 @Keerthi
  Scenario: Validate Linking and viewing a consumer only to the Correspondence in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | fileType     | pdf                     |
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I validate Linking a "Consumer" to the Inbound Correspondence
    And I validate viewing Consumer link to the Inbound Correspondence in cp
    And I retrieve the "LINK_EVENT_CONSUMER_IB_CORRESPONDENCE" event that is produced by trace id
    Then I should see the payload for the "LINK_EVENT_CONSUMER_IB_CORRESPONDENCE" is as expected
    And I should see that the event mapping for "LINK_EVENT" exists
    And I should see that the "LINK_EVENT" record is produced to DPBI

