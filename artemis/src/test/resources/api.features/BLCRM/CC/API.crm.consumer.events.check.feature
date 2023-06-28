@CP-4744
Feature: API: Consumer Events check Feature

  @CP-4744 @CP-4744-01 @asad @events @events_smoke_level_two
  Scenario Outline: verify CONSUMER_SAVE_EVENT from create consumer
    Given I logged into CRM
    When I create a consumer for consumer events check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" consumer event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerId" events
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with datetime fields in UTC format in payload
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with date fields in date format in payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|

  @CP-4744 @CP-4744-02 @asad @events @events_smoke_level_two @events_smoke_level_one @events-cc
  Scenario Outline: verify CONSUMER_UPDATE_EVENT from update consumer
    Given I logged into CRM
    When I create a consumer for consumer events check
    And I click case consumer search tab
    And I search created consumer for consumer events check
    And I update consumer for consumer events check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" consumer event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "CONSUMER_UPDATE_EVENT" is created with fields in payload for "consumerId" events
    Then I will verify an "CONSUMER_UPDATE_EVENT" is created with datetime fields in UTC format in payload
    Then I will verify an "CONSUMER_UPDATE_EVENT" is created with date fields in date format in payload
    Then I will verify subscriber received "CONSUMER_UPDATE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|


  @CP-4744 @CP-4744-03 @CP-6004-02 @asad @events @events_smoke_level_two @events-cc
  Scenario Outline: verify CASE_SAVE_EVENT from case loader
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "case-Primary Individual"
    Then I will verify an "CASE_SAVE_EVENT" is created with fields in payload for "case" events
    Then I will verify an "CASE_SAVE_EVENT" is created with datetime fields in UTC format in payload
    Then I will verify an "CASE_SAVE_EVENT" is created with date fields in date format in payload
    Then I will verify subscriber received "CASE_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|

  @CP-4744 @CP-4744-04.1 @asad @events @CP-11786 @CP-11786-11
  Scenario Outline: verify CONSUMER_SAVE_EVENT from case loader for consumer role Primary Individual
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "correlation-Primary Individual"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerRole-Primary Individual" events
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with datetime fields in UTC format in payload
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with date fields in date format in payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|

  @CP-4744 @CP-4744-04.2 @asad @events @CP-11786 @CP-11786-12
  Scenario Outline: verify CONSUMER_SAVE_EVENT from case loader for consumer role Case Member
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "correlation-Member"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerRole-Member" events
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with datetime fields in UTC format in payload
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with date fields in date format in payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|

  @CP-4744 @CP-4744-04.3 @asad @events @CP-11786 @CP-11786-13
  Scenario Outline: verify CONSUMER_SAVE_EVENT from case loader for consumer role Authorized Representative
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "correlation-Authorized Representative"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerRole-Authorized Representative" events
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with datetime fields in UTC format in payload
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with date fields in date format in payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|

  @CP-4744 @CP-4744-04.4 @asad @events @events_smoke_level_two @CP-11786 @CP-11786-14
  Scenario Outline: verify CONSUMER_SAVE_EVENT from UI for consumer role Primary Individual
    Given I logged into CRM
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "UI-Primary Individual"
    And I click case consumer search tab
    And I search for created case using consumer details
    And I create "" member from active contact page of created consumer
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "Primary Individual" consumer event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerUI-Primary Individual" events
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with datetime fields in UTC format in payload
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with date fields in date format in payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|

  @CP-4744 @CP-4744-04.5 @asad @events @CP-11786 @CP-11786-15 #Fails due to defect CP-10548
  Scenario Outline: verify CONSUMER_SAVE_EVENT from UI for consumer role Case Member
    Given I logged into CRM
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "UI-Member"
    And I click case consumer search tab
    And I search for created case using consumer details
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I create "Member" member from active contact page of created consumer
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "Member" consumer event check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerUI-Member" events
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with datetime fields in UTC format in payload
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with date fields in date format in payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|

  @CP-4744 @CP-4744-04.6 @asad @events @CP-11786 @CP-11786-16 #Fails due to defect CP-10549
  Scenario Outline: verify CONSUMER_SAVE_EVENT from UI for consumer role Authorized Representative
    Given I logged into CRM
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "UI-Authorized Representative"
    And I click case consumer search tab
    And I search for created case using consumer details
    And I create "Authorized Representative" member from active contact page of created consumer
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "Authorized Representative" consumer event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerUI-Authorized Representative" events
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with datetime fields in UTC format in payload
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with date fields in date format in payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|

  @CP-9456 @CP-9456-04 @mark @events
  Scenario: Validating CONSUMER_SAVE_EVENT payload for Spoken and Written language
    Given I logged into CRM
    When I create a consumer for consumer events check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" consumer event check
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    And I will check the response has event Subscriber Mapping ID for "CONSUMER_SAVE_EVENT"
    And I will get the "CONSUMER_SAVE_EVENT" by traceId
    And I validate in the "CONSUMER_SAVE_EVENT" the following fields
      | valuePairIdCommPref | effectiveStartDate | effectiveEndDate | createdBy | createdOn | updatedOn | updatedBy |
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events


  @CP-9456 @CP-9456-05 @mark @events
  Scenario: Validating no communicationPreference record created for correspondence preference when none selected
    Given I logged into CRM
    When I create a consumer for consumer events check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" consumer event check
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    And I will check the response has event Subscriber Mapping ID for "CONSUMER_SAVE_EVENT"
    And I will get the "CONSUMER_SAVE_EVENT" by traceId
    And I validate there is no communicationPreferences record created for correspondence preference in the payload
    And I validate there are only communicationPreference records for Spoken and Written Language in the payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events


  @CP-12264 @CP-12264-01 @CP-6004-01 @kamil @events @events-cc
  Scenario Outline: verify createdOn,createdBy,updatedOn,updatedBy fields CASE_SAVE_EVENT from case loader
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will send Case Loader API for checking events payload with consumer role "Primary Individual"
    Then I will send create CASE_SAVE_EVENT is created with fields in payload for case events
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CASE_SAVE_EVENT" payload
    Examples:
      | projectName |
      |[blank]|


  @CP-12264 @CP-12264-02 @kamil @events
  Scenario Outline: verify createdOn,createdBy,updatedOn,updatedBy fields CONSUMER_SAVE_EVENT from create consumer
    Given I logged into CRM
    When I create a consumer for consumer events check
    And I will take taceId for CONSUMER_SAVE_EVENT for consumer event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerId" events
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CONSUMER_SAVE_EVENT" payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|


  @CP-12264 @CP-12264-03 @kamil @events
  Scenario Outline: verify createdOn,createdBy,updatedOn,updatedBy fields CONSUMER_SAVE_EVENT from case loader for consumer role Primary Individual
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "correlation-Primary Individual"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerRole-Primary Individual" events
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CONSUMER_SAVE_EVENT" payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|


  @CP-12264 @CP-12264-04 @kamil @events
  Scenario Outline: verify createdOn,createdBy,updatedOn,updatedBy fields CONSUMER_SAVE_EVENT from case loader for consumer role Case Member
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will send Case Loader API for checking events payload with consumer role "Member"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerRole-Member" events
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CONSUMER_SAVE_EVENT" payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|


  @CP-12264 @CP-12264-05 @kamil @events
  Scenario Outline: verify createdOn,createdBy,updatedOn,updatedBy fields CONSUMER_SAVE_EVENT from case loader for consumer role Authorized Representative
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "correlation-Authorized Representative"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerRole-Authorized Representative" events
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CONSUMER_SAVE_EVENT" payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|


  @CP-12264 @CP-12264-06 @kamil @events #failing caseConsumer  updateOn return null
  Scenario Outline: verify createdOn,createdBy,updatedOn,updatedBy fields CONSUMER_SAVE_EVENT from UI for consumer role Primary Individual
    Given I logged into CRM
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "UI-Primary Individual"
    And I click case consumer search tab
    And I search for created case using consumer details
    And I create "" member from active contact page of created consumer
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "Primary Individual" consumer event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerUI-Primary Individual" events
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CONSUMER_SAVE_EVENT" payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|


  @CP-12264 @CP-12264-07 @kamil @events  # Failing when I trying save case member.CP-14529
  Scenario Outline: verify createdOn,createdBy,updatedOn,updatedBy fields CONSUMER_SAVE_EVENT from UI for consumer role Case Member
    Given I logged into CRM
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "UI-Member"
    And I click case consumer search tab
    And I search for created case using consumer details
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I create "Case Member" member from active contact page of created consumer
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "Member" consumer event check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerUI-Member" events
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CONSUMER_SAVE_EVENT" payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|


  @CP-12264 @CP-12264-08 @kamil @events #Fails in page Authorized Rep Access Type dropdown
  Scenario Outline: verify createdOn,createdBy,updatedOn,updatedBy fields CONSUMER_SAVE_EVENT from UI for consumer role Authorized Representative
    Given I logged into CRM
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "UI-Authorized Representative"
    And I click case consumer search tab
    And I search for created case using consumer details
    And I create "Authorized Representative" member from active contact page of created consumer
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "Authorized Representative" consumer event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerUI-Authorized Representative" events
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CONSUMER_SAVE_EVENT" payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|


  @CP-12264 @CP-12264-09 @kamil @events # Failing when I trying save case member.CP-14529
  Scenario Outline: Verify createdOn,createdBy,updatedOn,updatedBy fields CONSUMER_SAVE_EVENT for DPBI to Consumer for CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "CM"
    And I add "Case Member" for optin information check with "empty" End Date
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" optin information check
    Then I will verify the "SAVED" in the "CONSUMER_SAVE_EVENT" for optin information check
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CONSUMER_SAVE_EVENT" payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for optin information check
    Examples:
      | projectName |
      |[blank]|


  @CP-12264 @CP-12264-10 @kamil @events # Failing when I trying save case member.CP-14529
  Scenario Outline: Verify createdOn,createdBy,updatedOn,updatedBy fields for CONSUMER_SAVE_EVENT of adding CM for CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "CM"
    And I add "Case Member" for optin information check with "empty" End Date
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" optin information check
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CONSUMER_SAVE_EVENT" payload
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for optin information check
    Examples:
      | projectName |
      |[blank]|


  @CP-12264 @CP-12264-11 @kamil @events
  Scenario Outline: Verify createdOn,createdBy,updatedOn,updatedBy fields for CONSUMER_SAVE_EVENT of adding consumer for case
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to case and consumer search page
    And I enter Manual Search criteria fields for previously created consumer
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will take taceId for CONSUMER_SAVE_EVENT for consumer event check
    And I will initiate get "CONSUMER_SAVE_EVENT" for optin information check
    Then I will verify createdOn,createdBy,updatedOn,updatedBy fields in "CONSUMER_SAVE_EVENT" payload
    Examples:
      | projectName |
      |[blank]|


  @CP-9229 @CP-9229-01 @kamil @events
  Scenario Outline: Verify updatedOn/updatedBy matches the createdon/createdBy values for CONSUMER_SAVE_EVENT
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run the Case Loader API for checking events payload with consumer role "correlation-Authorized Representative"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created with fields in payload for "consumerRole-Authorized Representative" events
    Then I will verify updatedOn and updatedBy matches the createdon and createdBy values for "CONSUMER_SAVE_EVENT" event
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for consumer events
    Examples:
      | projectName |
      |[blank]|


  @CP-9229 @CP-9229-02 @kamil @events @events-cc
  Scenario Outline: Verify updatedOn/updatedBy matches the createdon/createdBy values for CASE_SAVE_EVENT
    Given I initiated Case Loader API for checking events
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will send Case Loader API for checking events payload with consumer role "Primary Individual"
    Then I will send create CASE_SAVE_EVENT is created with fields in payload for case events
    Then I will verify updatedOn and updatedBy matches the createdon and createdBy values for "CASE_SAVE_EVENT" event
    Examples:
      | projectName |
      |[blank]|


  @CP-9229 @CP-9229-03 @API-CP-11311 @CP-11311-03 @kamil @events
  Scenario Outline: Verify updatedOn/updatedBy matches the createdon/createdBy values for CONSUMER_UPDATE_EVENT
    Given I logged into CRM
    When I create a consumer for consumer events check
    And I click case consumer search tab
    And I search created consumer for consumer events check
    And I update consumer for consumer events check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" consumer event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will initiate api call for CONSUMER_UPDATE_EVENT
    Then I will verify updatedOn and updatedBy matches the createdon and createdBy values for "CONSUMER_UPDATE_EVENT" event
    Examples:
      | projectName |
      |[blank]|


