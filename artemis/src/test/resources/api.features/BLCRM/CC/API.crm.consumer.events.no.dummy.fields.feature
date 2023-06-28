@CP-9230
Feature: Capture Correspondece Preferences from Update Consumer Profile Info - Consumer Profile View

  @CP-9230 @CP-9230-01.0 @asad @events @events-cc #Fails due to defect CP-13034
  Scenario Outline: Pass null instead of ‘Dummy’ in the event payload for save event from consumer creation through UI
    Given I logged into CRM
    When I create new consumer for no Dummy field check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" consumer event for no Dummy field check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I verify that no dummy fields present in "CONSUMER_SAVE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-9230 @CP-9230-01.1 @asad @events @events-cc
  Scenario Outline: Pass null instead of ‘Dummy’ in the event payload for save event from consumer creation through Case Loader
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for no Dummy field check with "Primary Individual"
    Then I verify that no dummy fields present in "CONSUMER_SAVE_EVENT" from "Case Loader"
    Examples:
      |projectName|
      |[blank]|

  @CP-9230 @CP-9230-01.2 @asad @events @events-cc #Fails due to defect CP-13034
  Scenario Outline: Pass null instead of ‘Dummy’ in the event payload for save event from Primary Individual member creation
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for no Dummy field check with "Primary Individual"
    When I click case consumer search tab
    When I search for the created consumer for dummy field check for "Case Loader"
    When I created "" member from active contact page of created consumer for no dummy field check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" consumer event for no Dummy field check
    Then I verify that no dummy fields present in "CONSUMER_SAVE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-9230 @CP-9230-01.3 @asad @events @events-cc #Fails due to defect CP-13034
  Scenario Outline: Pass null instead of ‘Dummy’ in the event payload for save event from Case Member member creation
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for no Dummy field check with "Member"
    When I click case consumer search tab
    When I search for the created consumer for dummy field check for "Case Loader"
    When I created "Member" member from active contact page of created consumer for no dummy field check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" consumer event for no Dummy field check
    Then I verify that no dummy fields present in "CONSUMER_SAVE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-9230 @CP-9230-01.4 @asad @events @events-cc
  Scenario Outline: Pass null instead of ‘Dummy’ in the event payload for save event from Authorized Representative member creation
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for no Dummy field check with "Authorized Representative"
    When I click case consumer search tab
    When I search for the created consumer for dummy field check for "Case Loader"
    When I created "Authorized Representative" member from active contact page of created consumer for no dummy field check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" consumer event for no Dummy field check
    Then I verify that no dummy fields present in "CONSUMER_SAVE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-9230 @CP-9230-02.0 @asad @events @events-cc #Fails due to defect CP-13087
  Scenario Outline: Pass null instead of ‘Dummy’ in the event payload for save event from consumer creation through Case Loader
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for no Dummy field check with "Primary Individual"
    When I update new consumer using case loader for no Dummy field check
    Then I verify that no dummy fields present in "CONSUMER_UPDATE_EVENT" from "Case Loader"
    Examples:
      |projectName|
      |[blank]|

  @CP-9230 @CP-9230-02.1 @asad @events @events-cc #Fails due to defect CP-13034
  Scenario Outline: Pass null instead of ‘Dummy’ in the event payload for save event from consumer creation through UI
    Given I logged into CRM
    When I create new consumer for no Dummy field check
    When I click case consumer search tab
    When I search for the created consumer for dummy field check for "UI"
    And I edit the consumer profile for no Dummy field check
    And I will take "traceId" for "CONSUMER_UPDATE_EDIT_EVENT" for "consumerId" consumer event for no Dummy field check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I verify that no dummy fields present in "CONSUMER_UPDATE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-9230 @CP-9230-02.2 @asad @events @events-cc
  Scenario Outline: Pass null instead of ‘Dummy’ in the event payload for save event from Primary Individual member creation
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for no Dummy field check with "Primary Individual"
    When I click case consumer search tab
    When I search for the created consumer for dummy field check for "Case Loader"
    When I created "" member from active contact page of created consumer for no dummy field check
    When I updated "Primary Individual" member from active contact page of created consumer for no dummy field check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" consumer event for no Dummy field check
    Then I verify that no dummy fields present in "CONSUMER_UPDATE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-9230 @CP-9230-02.3 @asad @events @events-cc
  Scenario Outline: Pass null instead of ‘Dummy’ in the event payload for save event from Case Member member creation
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for no Dummy field check with "Member"
    When I click case consumer search tab
    When I search for the created consumer for dummy field check for "Case Loader"
    When I created "Member" member from active contact page of created consumer for no dummy field check
    When I updated "Member" member from active contact page of created consumer for no dummy field check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" consumer event for no Dummy field check
    Then I verify that no dummy fields present in "CONSUMER_UPDATE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|

  @CP-9230 @CP-9230-02.4 @asad @events @events-cc #Fails due to defect CP-13034
  Scenario Outline: Pass null instead of ‘Dummy’ in the event payload for save event from Authorized Representative member creation
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for no Dummy field check with "Authorized Representative"
    When I click case consumer search tab
    When I search for the created consumer for dummy field check for "Case Loader"
    When I created "Authorized Representative" member from active contact page of created consumer for no dummy field check
    When I updated "Authorized Representative" member from active contact page of created consumer for no dummy field check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" consumer event for no Dummy field check
    Then I verify that no dummy fields present in "CONSUMER_UPDATE_EVENT" from "UI"
    Examples:
      |projectName|
      |[blank]|