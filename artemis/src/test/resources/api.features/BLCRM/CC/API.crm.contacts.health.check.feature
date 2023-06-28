Feature: API-CRM: Contacts Controller

  @auxiliaryService @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @caseConsumerAPI @contacts-api-CC @API-HealthCheck @API-CRM-SmokeTest @Sujoy
  Scenario Outline: Contacts Health Check -- PUT mars/crm/contact
    Given I get uiid and correlationId id from contacts search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated add new Contact using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    Then I run get contacts detail using API
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | associatedCaseMember | emailAddress      | startDate | endDate | projectName |
      | Mia               | Jones            | Consumer        | Mia Jones            | AASSADWE@AASW.com |[blank]|         |[blank]|

  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @caseConsumerAPI  @contacts-api-CC  @API-HealthCheck @API-CRM-SmokeTest @Sujoy @api-smoke-devops
  Scenario Outline: Get Contacts Details By ExternalRefType and ExternalRefId Health Check -- GET mars/crm/contacts/{externalType}/{externalRefid}
    Given I initiated consumer search API for Contacts
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get uiid and correlationId id from contacts search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated Consumer Type Search vai API with "<externalRefType>" type and id "<externalRefid>"
    And I run get contacts detail using API
    Then I can verify consumer phone detail "<success>" and count greater than zero using API
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | externalRefid | success | projectName |
      | Lola              | Zcxc             | Consumer        |[blank]| True    |[blank]|

