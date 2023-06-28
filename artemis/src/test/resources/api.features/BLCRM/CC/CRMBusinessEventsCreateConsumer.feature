Feature: Business Events: Create Consumer Profile, PI,CM,AR

  @CP-26090 @CP-26090-01 @ui-cc @muhabbat @events-cc @events-bs-cc
  Scenario: Consumer Business Events New Consumer not on case
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I retrieve the "NEW_CONSUMER_PROFILE" business event that is produced by trace id
    Then I should see the payload for the business "NEW_CONSUMER_PROFILE" is as expected

  @CP-26090 @CP-26090-01 @ui-cc @muhabbat @events-cc @events-bs-cc
  Scenario Outline: Consumer Business Events New Case Consumer
    Given I will get the Authentication token for "" in "CRM"
    When I initiated Case Loader API for Create New Consumer for Digital Integration
    And I set up test data for a new "<status>" caseConsumer with "<role>" created by caseloader with "<primary>", "<contactType>", "<channel>"
    And I run the CaseLoader PUT API to Create New Consumer for Digital Integration
    When I get a case id and consumer id created from Consumer Search API
    Then I should see the payload for the business "NEW_CONSUMER_PROFILE" created by caseloader is as expected
    Examples:
      | status | role | primary | contactType | channel |
      |[blank]| PI   |[blank]|             |[blank]|
      |[blank]| CM   |[blank]|             |[blank]|
      |[blank]| AR   |[blank]|             |[blank]|


