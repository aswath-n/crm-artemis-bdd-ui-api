@CP-10423
Feature: Store correspondence keys in external_links

  @CP-10423 @CP-10423-01.1 @asad @API-ECMS
  Scenario: verify anchor element accepted in request and caseId and regardingConsumerId not present outside of anchor
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I create a case to use for Outbound correspondence request
    And I get case id and consumer id of the created case
    When I have Outbound Correspondence Request with anchor
    When I send the request for the Outbound Correspondence for external links
    Then I should see the anchor values are saved

  @CP-10423 @CP-10423-02.1 @asad @API-ECMS
  Scenario: verify if caseId value found in anchor, then verify links in caseconsumer service
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I create a case to use for Outbound correspondence request
    And I get case id and consumer id of the created case
    When I have Outbound Correspondence Request with anchor
    When I send the request for the Outbound Correspondence for external links
    Then I should see the anchor values are saved
    Then I should see a link in caseConsumer service with the values of "case-id"

  @CP-10423 @CP-10423-02.2 @asad @API-ECMS
  Scenario: verify if consumerId value found in anchor, then verify links in caseconsumer service
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I create a case to use for Outbound correspondence request
    And I get case id and consumer id of the created case
    When I have Outbound Correspondence Request with anchor
    When I send the request for the Outbound Correspondence for external links
    Then I should see the anchor values are saved
    Then I should see a link in caseConsumer service with the values of "consumer-id"

  @CP-10423 @CP-10423-03.1 @asad @API-ECMS
  Scenario: verify if caseId value found in anchor, then verify links in correspondence order service
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I create a case to use for Outbound correspondence request
    And I get case id and consumer id of the created case
    When I have Outbound Correspondence Request with anchor
    When I send the request for the Outbound Correspondence for external links
    Then I should see the anchor values are saved
    Then I should see a link in correspondence service with the values of "case-id"

  @CP-10423 @CP-10423-03.2 @asad @API-ECMS
  Scenario: verify if consumerId value found in anchor, then verify links in correspondence service
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I create a case to use for Outbound correspondence request
    And I get case id and consumer id of the created case
    When I have Outbound Correspondence Request with anchor
    When I send the request for the Outbound Correspondence for external links
    Then I should see the anchor values are saved
    Then I should see a link in correspondence service with the values of "consumer-id"

  @CP-10423 @CP-10423-04.1 @asad @API-ECMS
  Scenario: verify anchor is in get outbound correspondence api
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I create a case to use for Outbound correspondence request
    And I get case id and consumer id of the created case
    When I have Outbound Correspondence Request with anchor
    When I send the request for the Outbound Correspondence for external links
    Then I should see the anchor values are saved
    And I send a get request for the Outbound Correspondence
    Then I should see the anchor values are retrieved


  @CP-10423 @CP-10423-04.2 @asad @UI-ECMS
  Scenario: verify if case id is in anchor then i can search for the correspondence by case id
    Given I logged into CRM
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I create a case to use for Outbound correspondence request
    And I get case id and consumer id of the created case
    When I have Outbound Correspondence Request with anchor
    When I send the request for the Outbound Correspondence for external links
    Then I should see the anchor values are saved
    And I click on the main navigation menu
    And I should see the Outbound Correspondence Search Icon
    When I click on the Outbound Correspondence Search icon
    And I search for Outbound Correspondence with the one created for links
    And I click on the Outbound Correspondence Id result
    Then I should see a Link to the Task in the Links Section for "case-id"

  @CP-10423 @CP-10423-04.3 @asad @UI-ECMS
  Scenario: verify if consumer id is in anchor then i can search for the correspondence by consumer id
    Given I logged into CRM
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I create a case to use for Outbound correspondence request
    And I get case id and consumer id of the created case
    When I have Outbound Correspondence Request with anchor
    When I send the request for the Outbound Correspondence for external links
    Then I should see the anchor values are saved
    And I click on the main navigation menu
    And I should see the Outbound Correspondence Search Icon
    When I click on the Outbound Correspondence Search icon
    And I search for Outbound Correspondence with the one created for links
    And I click on the Outbound Correspondence Id result
    Then I should see a Link to the Task in the Links Section for "consumer-id"


  @CP-10423 @CP-10423-05.1 @asad @API-ECMS
  Scenario: verify if case id is in anchor then the values can be found in the OUTBOUND_CORRESPONDENCE_SAVE_EVENT
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I create a case to use for Outbound correspondence request
    And I get case id and consumer id of the created case
    When I have Outbound Correspondence Request with anchor
    When I send the request for the Outbound Correspondence for external links
    Then I should see the anchor values are saved
    Then I should see a link in caseConsumer service with the values of "case-id"
    And I search for the "OUTBOUND_CORRESPONDENCE_SAVE_EVENT" for external links
    Then I should see "case-id" in anchor values in the "OUTBOUND_CORRESPONDENCE_SAVE_EVENT"

  @CP-10423 @CP-10423-05.2 @asad @API-ECMS
  Scenario: verify if consumer id is in anchor then the values can be found in the OUTBOUND_CORRESPONDENCE_SAVE_EVENT
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I create a case to use for Outbound correspondence request
    And I get case id and consumer id of the created case
    When I have Outbound Correspondence Request with anchor
    When I send the request for the Outbound Correspondence for external links
    Then I should see the anchor values are saved
    Then I should see a link in caseConsumer service with the values of "case-id"
    And I search for the "OUTBOUND_CORRESPONDENCE_SAVE_EVENT" for external links
    Then I should see "consumer-id" in anchor values in the "OUTBOUND_CORRESPONDENCE_SAVE_EVENT"