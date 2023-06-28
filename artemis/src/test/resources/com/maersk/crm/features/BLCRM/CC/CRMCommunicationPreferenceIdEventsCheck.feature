@CP-11398
Feature: Capture Correspondece Preferences from Update Consumer Profile Info - Consumer Profile View

  @CP-11398 @CP-11398-01.0 @asad @events @events-cc #Fails due to defects CP-13034, CP-12642
  Scenario Outline: update preferenceId to communicationPreferencesID check from creating Consumer on CONSUMER_SAVE_EVENT
    Given I logged into CRM
    When I create a consumer for communication PreferencesID check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" for Communication Preference Id check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I verify that Consumers Communication Preference Id is present in the "CONSUMER_SAVE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-11398 @CP-11398-01.1 @asad @events @events-cc #Fails due to defect CP-13034
  Scenario Outline: update preferenceId to communicationPreferencesID check from creating Primary Individual on CONSUMER_SAVE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "Case Loader"
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for communication PreferencesID check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" for Communication Preference Id check
    Then I verify that Consumers Communication Preference Id is present in the "CONSUMER_SAVE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-11398 @CP-11398-01.2 @asad @events @events-cc #Fails due to defects CP-13034, CP-12642
  Scenario Outline: update preferenceId to communicationPreferencesID check from creating Case Member on CONSUMER_SAVE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Member"
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "Case Loader"
    And I click on demographic tab and click on add for "CM"
    And I add "Case Member" for communication PreferencesID check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" for Communication Preference Id check
    Then I verify that Consumers Communication Preference Id is present in the "CONSUMER_SAVE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-11398 @CP-11398-01.3 @asad @events @events-cc #Fails due to defects CP-13034, CP-12642
  Scenario Outline: update preferenceId to communicationPreferencesID check from creating Authorized Representative on CONSUMER_SAVE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Authorized Representative"
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "Case Loader"
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for communication PreferencesID check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" for Communication Preference Id check
    Then I verify that Consumers Communication Preference Id is present in the "CONSUMER_SAVE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-11398 @CP-11398-01.4 @asad @events @events-cc
  Scenario Outline: update preferenceId to communicationPreferencesID check from Case Loader on CONSUMER_SAVE_EVENT
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Primary Individual"
    Then I verify that Consumers Communication Preference Id is present in the "CONSUMER_SAVE_EVENT" from "Case Loader"
    Examples:
      |projectName|
      |[blank]|

  @CP-11398 @CP-11398-01.5 @asad @events @events-cc #Fails due to defect CP-13034
  Scenario Outline: update preferenceId to communicationPreferencesID from creating Consumer on CONSUMER_UPDATE_EVENT
    Given I logged into CRM
    When I create a consumer for communication PreferencesID check
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "UI"
    And I edit the consumer profile for communication PreferencesID check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" for Communication Preference Id check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I verify that Consumers Communication Preference Id is present in the "CONSUMER_UPDATE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-11398 @CP-11398-01.6 @asad @events @events-cc #Fails due to defect CP-12642
  Scenario Outline: update preferenceId to communicationPreferencesID check from creating Primary Individual on CONSUMER_UPDATE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "Case Loader"
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for communication PreferencesID check
    And I update "Primary Individual" for optin information check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" for Communication Preference Id check
    Then I verify that Consumers Communication Preference Id is present in the "CONSUMER_UPDATE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-11398 @CP-11398-01.7 @asad @events @events-cc #Fails due to defect CP-12642
  Scenario Outline: update preferenceId to communicationPreferencesID check from creating Case Member on CONSUMER_UPDATE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Member"
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "Case Loader"
    And I click on demographic tab and click on add for "CM"
    And I add "Case Member" for communication PreferencesID check
    And I update "Case Member" for optin information check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" for Communication Preference Id check
    Then I verify that Consumers Communication Preference Id is present in the "CONSUMER_SAVE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-11398 @CP-11398-01.8 @asad @events @events-cc
  Scenario Outline: update preferenceId to communicationPreferencesID check from creating Authorized Representative on CONSUMER_UPDATE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Authorized Representative"
    When I click case consumer search tab
    And I search consumer with first name and last name for Communication preference from consumer profile for Id check for "Case Loader"
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for communication PreferencesID check
    And I update "Authorized Representative" for optin information check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" for Communication Preference Id check
    Then I verify that Consumers Communication Preference Id is present in the "CONSUMER_UPDATE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-11398 @CP-11398-01.9 @API-CP-97 @API-CP-97-21 @asad @umid @events
  Scenario Outline: update preferenceId to communicationPreferencesID check from Case Loader on CONSUMER_UPDATE_EVENT
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for communication PreferencesID check with "Primary Individual"
    When I update new consumer using case loader for communication PreferencesID check
    Then I verify that Consumers Communication Preference Id is present in the "CONSUMER_UPDATE_EVENT" from "Case Loader"
    Examples:
      |projectName|
      |[blank]|