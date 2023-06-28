Feature: API-CRM: Contact Record Advance Search

  @contact-record-api-CRMC @API-CP-2622 @API-CP-2622-01 @API-CORE @API-CRM-Regression @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : PhoneNumber
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I can provide advanced contact search criteria information based on PhoneNumber:
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on PhoneNumber
    Examples:
      | projectName |
      ||

  @contact-record-api-CRMC @API-CP-2622 @API-CP-2622-02 @API-CORE @API-CRM-Regression @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : Email
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I can provide advanced contact search criteria information based on Email:
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on EmailAddress
    Examples:
      | projectName |
      ||

  @contact-record-api-CRMC @API-CP-2622 @API-CP-2622-03 @API-CORE @API-CRM-Regression @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : Inbound Call Queue, OutcomeOfContact and callCampaign
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I can provide advanced contact search criteria information based on Inbound Call Queue:
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on InboundCallQueue, callCampaign, contactOfOutcome
    Examples:
      | projectName |
      ||

  @contact-record-api-CRMC @API-CP-2622 @API-CP-2622-04 @API-CORE @API-CRM-Regression @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : CreatedById (UserID and CreatedBy)
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I can provide advanced contact search criteria information based on CreatedById:
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on CreatedById
    Examples:
      | projectName |
      ||

  @contact-record-api-CRMC @API-CP-2622 @API-CP-2622-05 @API-CORE @API-CRM-Regression @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : Third Party Organization
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Create Contact Records API
    And I can provide contact record details for Third Party Contact Record creation:
      | contactRecordType     | Inbound     |
      | contactType           | Third Party |
      | preferredLanguageCode | English     |
      | consumerLanguageCode  | English     |
      | contactChannelType    | Phone       |
      | linkRefType           | consumer    |
      | consumerType          | Media       |
    Then I can run create contact records API
    And I initiated Contact Records Advance Search API
    And I can provide advanced contact search criteria information based on ThirdPartyOrganization:
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on ThirdPartyOrganization
    Examples:
      | projectName |
      ||

  @contact-record-api-CRMC @API-CP-2409 @API-CP-2409-01 @API-CRM-Regression @API-CORE @aikanysh
  Scenario: Verify Manual Advanced Contact Record Search through API : Contact Record ID
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I can provide advanced contact search criteria information based on ContactRecordID
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on ContactRecordID

  @contact-record-api-CRMC @API-CP-2409 @API-CP-2409-02  @API-CRM-Regression @API-CORE  @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : Date
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I search for an existing contact record with api by "<criteria>"
    Then I can verify that search result of contact record search is in excess
    Examples:
      | criteria  |
      | createdOn |

  @contact-record-api-CRMC @API-CP-2409 @API-CP-2409-03 @API-CRM-Regression @API-CORE @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : First Name
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I search for an existing contact record with api by "<criteria>"
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on "<criteria>"
    Examples:
      | criteria          |
      | consumerFirstName |

  @contact-record-api-CRMC @API-CP-2409 @API-CP-2409-04 @API-CRM-Regression @API-CORE @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : Last Name
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I search for an existing contact record with api by "<criteria>"
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on "<criteria>"
    Examples:
      | criteria         |
      | consumerLastName |


  @contact-record-api-CRMC @API-CP-2409 @API-CP-2409-05 @API-CP-2409-06 @API-CRM-Regression @API-CORE  @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : Internal CASE ID
    Given I initiated Case Loader API for Create New Case for Contacts Record Validation
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run Case Loader API for Create New Case and Customer with below detail for Contacts Record Validations:
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip | consumerRole |
      | Consumer     | {random}          | {random}         | 1999-02-11          | {random}    | Male       | today-50           || Child        | Member       |
    Then I will create a new case for case loader case creation for Contacts Record Validations
    And I initiated Create Contact Records API
    And I can provide advanced contact details information:
      | contactRecordType     | random   |
      | contactType           | General  |
      | preferredLanguageCode | English  |
      | inboundCallQueue      | random   |
      | contactChannelType    | Phone    |
      | linkRefType           | case     |
      | consumerType          | consumer |
    Then I can run create contact records API
    And I initiated Contact Records Advance Search API
    And I search for an existing contact record with api by "<criteria>"
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on "<criteria>"
    Examples:
      | criteria |
      | caseId   |

  @contact-record-api-CRMC @API-CP-2409 @API-CP-2409-06 @API-CP-2409-08  @API-CRM-Regression @API-CORE  @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : Internal Consumer Id
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I search for an existing contact record with api by "<criteria>"
    And I run advanced contact search API
    Then I can verify that correct contact record was found based on "<criteria>"
    Examples:
      | criteria   |
      | consumerId |

  @contact-record-api-CRMC @API-CP-2409 @API-CP-2409-07 @API-CP-2409-09 @API-CRM-Regression @API-CORE @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API : Contact Type
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I search for an existing contact record with api by "<criteria>"
    Then I can verify that search result of contact record search is in excess or found
    Examples:
      | criteria          |
      | contactRecordType |

  @contact-record-api-CRMC @API-CP-2409 @API-CP-2409-10 @API-CRM-Regression @API-CORE @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API: Type
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
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
    And I initiated Contact Records Advance Search API
    And I search for an existing contact record with api by "<criteria>"
    Then I can verify that search result of contact record search is in excess or found
    Examples:
      | criteria    |
      | contactType |

  @contact-record-api-CRMC @API-CP-2409 @API-CP-2409-12 @API-CRM-Regression @API-CORE  @aikanysh
  Scenario Outline: Verify Manual Advanced Contact Record Search through API: Disposition
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Create Contact Records API
    And I can provide advanced contact details information:
      | contactRecordType       | random   |
      | contactType             | General  |
      | preferredLanguageCode   | English  |
      | inboundCallQueue        | random   |
      | contactChannelType      | Phone    |
      | linkRefType             | consumer |
      | consumerType            | consumer |
      | contactRecordStatusType | random   |
    Then I can run create contact records API
    And I initiated Contact Records Advance Search API
    And I search for an existing contact record with api by "<criteria>"
    Then I can verify that search result of contact record search is in excess or found
    Examples:
      | criteria                |
      | contactRecordStatusType |
