@CP-12235
Feature: Remove dupe fields from CONSUMER events

  @CP-12235 @CP-12235-01.0 @asad @events @events-cc
  Scenario Outline: Remove the extra fields from the dataObject for Primary Individual CONSUMER_SAVE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for remove dupe fields check for "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for remove dupe fields check
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for remove dupe fields check
    And I will take correlation Id for "CONSUMER_SAVE_EVENT" for "consumerId" for remove dupe fields check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created for remove dupe fields check in payload for "consumer-UI" event
    Examples:
      |projectName|
      |[blank]|

  @CP-12235 @CP-12235-01.1 @asad @events @events-cc #Fails due to defect CP-13034
  Scenario Outline: Remove the extra fields from the dataObject for Primary Individual CONSUMER_UPDATE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for remove dupe fields check for "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for remove dupe fields check
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for remove dupe fields check
    And I update "Primary Individual" for optin information check
    And I will take correlation Id for "CONSUMER_UPDATE_EVENT" for "consumerId" for remove dupe fields check
    Then I will verify an "CONSUMER_UPDATE_EVENT" is created for remove dupe fields check in payload for "consumer-UI" event
    Examples:
      |projectName|
      |[blank]|

  @CP-12235 @CP-12235-01.2 @asad @events @events-cc #Fails due to defect CP-13034
  Scenario Outline: Remove the extra fields from the dataObject for Case Member CONSUMER_SAVE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for remove dupe fields check for "Member"
    When I click case consumer search tab
    And I search consumer with first name and last name for remove dupe fields check
    And I click on demographic tab and click on add for "CM"
    And I add "Case Member" for remove dupe fields check
    And I will take correlation Id for "CONSUMER_SAVE_EVENT" for "consumerId" for remove dupe fields check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created for remove dupe fields check in payload for "consumer-UI" event
    Examples:
      |projectName|
      |[blank]|

  @CP-12235 @CP-12235-01.3 @asad @events @events-cc
  Scenario Outline: Remove the extra fields from the dataObject for Case Member CONSUMER_UPDATE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for remove dupe fields check for "Member"
    When I click case consumer search tab
    And I search consumer with first name and last name for remove dupe fields check
    And I click on demographic tab and click on add for "CM"
    And I add "Case Member" for remove dupe fields check
    And I update "Case Member" for optin information check
    And I will take correlation Id for "CONSUMER_UPDATE_EVENT" for "consumerId" for remove dupe fields check
    Then I will verify an "CONSUMER_UPDATE_EVENT" is created for remove dupe fields check in payload for "consumer-UI" event
    Examples:
      |projectName|
      |[blank]|

  @CP-12235 @CP-12235-01.4 @asad @events @events-cc #Fails due to defect CP-13034
  Scenario Outline: Remove the extra fields from the dataObject for Authorized Representative CONSUMER_SAVE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for remove dupe fields check for "Authorized Representative"
    When I click case consumer search tab
    And I search consumer with first name and last name for remove dupe fields check
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for remove dupe fields check
    And I will take correlation Id for "CONSUMER_SAVE_EVENT" for "consumerId" for remove dupe fields check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created for remove dupe fields check in payload for "consumer-UI" event
    Examples:
      |projectName|
      |[blank]|

  @CP-12235 @CP-12235-01.5 @asad @events @events-cc
  Scenario Outline: Remove the extra fields from the dataObject for Authorized Representative CONSUMER_UPDATE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for remove dupe fields check for "Authorized Representative"
    When I click case consumer search tab
    And I search consumer with first name and last name for remove dupe fields check
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for remove dupe fields check
    And I update "Authorized Representative" for optin information check
    And I will take correlation Id for "CONSUMER_UPDATE_EVENT" for "consumerId" for remove dupe fields check
    Then I will verify an "CONSUMER_UPDATE_EVENT" is created for remove dupe fields check in payload for "consumer-UI" event
    Examples:
      |projectName|
      |[blank]|

  @CP-12235 @CP-12235-01.6 @asad @events @events-cc
  Scenario Outline: Remove the extra fields from the dataObject from Case Loader
    When I create new consumer using case loader for remove dupe fields check for "Primary Individual"
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for remove dupe fields check for "Primary Individual"
    Then I will verify an "CONSUMER_SAVE_EVENT" is created for remove dupe fields check in payload for "consumer-caseLoader" event
    Examples:
      |projectName|
      |[blank]|