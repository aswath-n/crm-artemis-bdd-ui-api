@API-CP-3256
@API-ECMS
Feature: API: Outbound Correpondence Updates Feature

  @asad @API-CP-3256 @API-CP-3256-01.0
  Scenario: Outbound Correspondence Request API Updates -- No Correspondence type
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "without correspondence type"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "empty correspondence type" is required

  @asad @API-CP-3256 @API-CP-3256-01.1.0
  Scenario: Outbound Correspondence Request API Updates -- No active Correspondence type
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "without active correspondence type"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "no active correspondence type" is required

  @asad @API-CP-3256 @API-CP-3256-01.1.1
  Scenario: Outbound Correspondence Request API Updates -- Inactive Correspondence type
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "with Inactive correspondence type"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "active correspondence type" is required

  @asad @API-CP-3256 @API-CP-3256-01.1.2
  Scenario: Outbound Correspondence Request API Updates -- future Correspondence type
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "The CorrespondenceDefinitionMMSCode is set for FUTURE"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "The CorrespondenceDefinitionMMSCode is set for FUTURE" is required

#  @asad @API-CP-3256 @API-CP-3256-02.0 no longer a valid scenario
#  Scenario: Outbound Correspondence Request API Updates -- No Regarding values
#    Given I initiated outbound Correspondence API
#    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
#    And I create outbound correspondence request "without Regarding values"
#    And I run the outbound Correspondence API
#    Then I validate that response contains error message of "Regarding values" is required

  @asad @API-CP-3256 @API-CP-3256-02.1
  Scenario: Outbound Correspondence Request API Updates -- No Case Id
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "without Case Id"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "Case Id" is required

  @asad @API-CP-3256 @API-CP-3256-02.2.0
  Scenario: Outbound Correspondence Request API Updates -- No Consumer Id
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "without Consumer Id"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "Consumer Id" is required

  @asad @API-CP-3256 @API-CP-3256-02.2.1
  Scenario: Outbound Correspondence Request API Updates -- No Consumer Ids
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "without Consumer Ids"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "no Consumer Ids" is required

  @asad @API-CP-3256 @API-CP-3256-02.3
  Scenario: Outbound Correspondence Request API Updates -- Without one or more Consumer Ids
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "with 2 consumer ids"
    And I run the outbound Correspondence API
    Then I validate that response contains success message

  @asad @API-CP-3256 @API-CP-3256-02.3.1
  Scenario: Outbound Correspondence Request API Updates -- distinct Consumer Ids
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "without distinct Consumer Ids"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "distinct Consumer-Ids" is required

  @asad @API-CP-3256 @API-CP-3256-03.0
  Scenario: Outbound Correspondence Request API Updates -- Optional preferred Language
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "with optional preferred Language"
    And I run the outbound Correspondence API
    Then I validate that response contains success message

  @asad @API-CP-3256 @API-CP-3256-03.1
  Scenario: Outbound Correspondence Request API Updates -- No valid language
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "without valid language"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "valid language" is required

  @asad @API-CP-3256 @API-CP-3256-04.0
  Scenario: Outbound Correspondence Request API Updates --  jointly addressed consumer ids
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "with jointly addressed consumer ids"
    And I run the outbound Correspondence API
    Then I validate that response contains success message

  @asad @API-CP-3256 @API-CP-3256-04.1
  Scenario: Outbound Correspondence Request API Updates -- regarding values for no recipient Case or Consumer ID
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "without regarding values for no recipient Case or Consumer ID"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "Case Id" is required

  @asad @API-CP-3256 @API-CP-3256-05.0
  Scenario: Outbound Correspondence Request API Updates -- at least one channel for provided consumers ids
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without channel for provided consumers ids"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "channel details" is required

  @asad @API-CP-3256 @API-CP-3256-05.1
  Scenario: Outbound Correspondence Request API Updates -- Channel associated with Correspondence Type
    Given I initiated outbound Correspondence API
    When I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    And I create outbound correspondence request "which does not have channel mail"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "Channel association with Correspondence Type" is required

  @asad @API-CP-3256 @API-CP-3256-05.2
  Scenario: Outbound Correspondence Request API Updates -- future Channel for Correspondence Type
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "with future Channel for Correspondence Type"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "Channel active for Correspondence Type" is required

  @asad @API-CP-3256 @API-CP-3256-05.3
  Scenario: Outbound Correspondence Request API Updates -- No more than one instance of the same Channel provided
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "with more than one instance of the same Channel provided"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "more than one instance of the same Channel" is required

  @asad @API-CP-3256 @API-CP-3256-06.0
  Scenario: Outbound Correspondence Request API Updates -- destination must be provided for that Channel
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "with destination for the Channel"
    And I run the outbound Correspondence API
    Then I validate that response contains success message

  @asad @API-CP-3256 @API-CP-3256-06.1.0
  Scenario: Outbound Correspondence Request API Updates -- no delivery line for Mail Channel
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without delivery line for Mail Channel"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "delivery line for Mail Channel" is required

  @asad @API-CP-3256 @API-CP-3256-06.1.1
  Scenario: Outbound Correspondence Request API Updates -- no city for Mail Channel
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without city for Mail Channel"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "city for Mail Channel" is required

  @asad @API-CP-3256 @API-CP-3256-06.1.2
  Scenario: Outbound Correspondence Request API Updates -- no state for Mail Channel
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without state for Mail Channel"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "state for Mail Channel" is required

  @asad @API-CP-3256 @API-CP-3256-06.1.3
  Scenario: Outbound Correspondence Request API Updates -- no zip code for Mail Channel
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without zip code for Mail Channel"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "zip code for Mail Channel" is required

  @asad @API-CP-3256 @API-CP-3256-06.2
  Scenario: Outbound Correspondence Request API Updates -- valid 2 character postal state
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without valid 2 character postal state"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "valid 2 character postal state" is required

  @asad @API-CP-3256 @API-CP-3256-06.3
  Scenario: Outbound Correspondence Request API Updates -- No valid 5 digit zip code
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without valid 5 digit zip code"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "valid 5 digit zip code" is required

  @asad @API-CP-3256 @API-CP-3256-06.4
  Scenario: Outbound Correspondence Request API Updates -- No email address for email channel
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without email address for email channel"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "email address for email channel" is required

  @asad @API-CP-3256 @API-CP-3256-06.5
  Scenario: Outbound Correspondence Request API Updates -- correct email address format
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without correct email address format"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "correct email address format" is required

  @asad @API-CP-3256 @API-CP-3256-06.6
  Scenario: Outbound Correspondence Request API Updates -- No text phone number for text channel
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without text phone number for text channel"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "text phone number for text channel" is required

  @asad @API-CP-3256 @API-CP-3256-06.7
  Scenario: Outbound Correspondence Request API Updates -- No text phone number is 10 digit number that begins with 2 thru 9
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without text phone number is 10 digit number that begins with 2 thru 9"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "text phone number is 10 digit number that begins with 2 thru 9" is required

  @asad @API-CP-3256 @API-CP-3256-06.8
  Scenario: Outbound Correspondence Request API Updates -- No fax phone number for fax channel
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without fax phone number for fax channel"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "fax phone number for fax channel" is required

  @asad @API-CP-3256 @API-CP-3256-06.9
  Scenario: Outbound Correspondence Request API Updates -- No fax phone number is 10 digit number that begins with 2 thru 9
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without fax phone number is 10 digit number that begins with 2 thru 9"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "fax phone number is 10 digit number that begins with 2 thru 9" is required

  @asad @API-CP-3256 @API-CP-3256-07.0
  Scenario: Outbound Correspondence Request API Updates -- Body data may be provided optionally
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without Body data"
    And I run the outbound Correspondence API
    Then I validate that response contains success message

  @asad @API-CP-3256 @API-CP-3256-07.1
  Scenario: Outbound Correspondence Request API Updates -- provided body data must conform to the structure
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "with structured body data"
    And I run the outbound Correspondence API
    Then I validate that response contains success message

  @asad @API-CP-3256 @API-CP-3256-08.0
  Scenario: Outbound Correspondence Request API Updates -- No user ID submitting the request must be provided
    Given I will get the Authentication token for "SelectBLCRMConfig" in "CRM"
    When I initiated outbound Correspondence API
    And I create outbound correspondence request "without user ID"
    And I run the outbound Correspondence API
    Then I validate that response contains error message of "no user ID" is required