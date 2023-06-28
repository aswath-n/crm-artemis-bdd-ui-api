Feature: API: Enrollment Events check Feature

  @API-CP-4741 @API-CP-4741-01.1 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression @events_smoke_level_two
  Scenario Outline: Pass action, recordType, eventCreatedOn and dataObject in the event payload for all the Enrollment save events
   Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "NEWBORN"
    And I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "MEDICAL", "past", "future" and "true"
    And I run create enrollment API
    Then I will verify an "ENROLLMENT_SAVE_EVENT" for "DPBI" is created with fields in payload
     Examples:
      |projectName|
      ||

  @API-CP-4741 @API-CP-4741-01.2 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression @events_smoke_level_two
  Scenario Outline: Pass action, recordType, eventCreatedOn and dataObject in the event payload for all the Enrollment update events
   Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "NEWBORN"
    And I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "MEDICAL", "past", "future" and "true"
    And I run create enrollment API
    And I provide information to Enrollment update API
    |anniversaryDate|{random}       |
    And I run update enrollment API
   # When I run the "ENROLLMENT_UPDATE" API for "DENTAL" for checking events payload
    Then I will verify an "ENROLLMENT_UPDATE_EVENT" for "DPBI" is created with fields in payload
    Examples:
      |projectName|
      ||

  @API-CP-4741 @API-CP-4741-02.1 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Pass all datetime fields in UTC format  for all the Enrollment save events
   Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "NEWBORN"
    And I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "MEDICAL", "past", "future" and "true"
    And I run create enrollment API
    Then I will verify an "ENROLLMENT_SAVE_EVENT" for DPBI is created with datetime fields in UTC format in payload
    Examples:
      |projectName|
      ||

  @API-CP-4741 @API-CP-4741-02.2 @API-CRM @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario Outline: Pass all datetime fields in UTC format  for all the Enrollment update events
   Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "NEWBORN"
    And I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "MEDICAL", "past", "future" and "true"
    And I run create enrollment API
    And I provide information to Enrollment update API
      |anniversaryDate|{random}       |
    And I run update enrollment API
    Then I will verify an "ENROLLMENT_UPDATE_EVENT" for DPBI is created with datetime fields in UTC format in payload
    Examples:
      |projectName|
      ||


  @API-CP-4741 @API-CP-4741-06
  Scenario: EE Events -Eligibility Save
   Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    When I create eligibility record for bpm process with "future" timeframe
    Then I will verify an "ELIGIBILITY_SAVE_EVENT" for "DPBI" is created with fields in payload

  @API-CP-4741 @API-CP-4741-06
  Scenario: EE Events -Eligibility Update
   Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
     Given I initiated Eligibility Create API
    When I run the Eligibility Create API
    And I provide information to eligibility update API
    |eligibilityStatusCode|M|
    |consumerId           |[blank]|
    And I run update eligibility API
    Then I will verify an "ELIGIBILITY_UPDATE_EVENT" for "DPBI" is created with fields in payload
