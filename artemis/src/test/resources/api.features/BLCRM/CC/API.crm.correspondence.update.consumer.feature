@CP-278
Feature: Capture Correspondence Preferences from Update Consumer Profile Info - Consumer Profile View

  @CP-278 @CP-278-03.0 @asad @events @events-cc
  Scenario Outline: Verify Consumer Update Event
    Given I logged into CRM
    When I create a consumer for Correspondence preference with "" preferences from consumer profile for API
    When I click case consumer search tab
    And I search consumer with first name and last name for Correspondence preference from consumer profile for API
    And I edit the consumer profile to change correspondence preferences to "Paperless" in consumer profile
    And I will take "traceId" for "EDIT_EVENT" for "consumerId" consumer event check for correspondence prefs check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I verify that Consumers Correspondence Preference is "Paperless" in "CONSUMER_UPDATE_EVENT"
    Examples:
      |projectName|
      |[blank]|

  @CP-278 @CP-278-03.1 @asad @events #Fails due to defect CP-12642
  Scenario Outline: Verify Consumer Update Event
    Given I logged into CRM
    When I create a consumer for Correspondence preference with "Paperless" preferences from consumer profile for API
    When I click case consumer search tab
    And I search consumer with first name and last name for Correspondence preference from consumer profile for API
    And I edit the consumer profile to change correspondence preferences to "" in consumer profile
    And I will take "traceId" for "EDIT_EVENT" for "consumerId" consumer event check for correspondence prefs check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I verify that Consumers Correspondence Preference is "" in "CONSUMER_UPDATE_EVENT"
    Examples:
      |projectName|
      |[blank]|