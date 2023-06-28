@CP-269
Feature: Capture Opt-In/Out Primary Individual

  @CP-269 @CP-269-01 @asad @crm-regression @ui-cc
  Scenario: Opt-in/out Check Boxes for PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "PI"
    Then I verify that all the required checkboxes are present

  @CP-269 @CP-269-02 @asad @crm-regression @ui-cc
  Scenario: Default Selections for PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "PI"
    Then I will see the default configured channels of Opted-in as Checked

  @CP-269 @CP-269-03 @asad @crm-regression @ui-cc
  Scenario: Check or Uncheck for PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "PI"
    Then I will be able to check or uncheck any of the Opt-in check boxes

  @CP-269 @CP-269-04.1 @asad @events @CP-11786 @CP-11786-20 @events-cc
  Scenario Outline: Publish an CONSUMER_SAVE_EVENT for DPBI to Consumer for PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for optin information check with "empty" End Date
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" optin information check
    Then I will verify the "SAVED" in the "CONSUMER_SAVE_EVENT" for optin information check
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for optin information check
    Examples:
      |projectName|
      |[blank]|

  @CP-269 @CP-269-04.2 @asad @events @events_smoke_level_two @CP-11786 @CP-11786- @events-cc #Fails due to defect CP-13593
  Scenario Outline: Publish an CONSUMER_UPDATE_EVENT for DPBI to Consumer for PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for optin information check with "empty" End Date
    And I update "Primary Individual" for optin information check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" optin information check
    Then I will verify the "UPDATED" in the "CONSUMER_UPDATE_EVENT" for optin information check
    Then I will verify subscriber received "CONSUMER_UPDATE_EVENT" event for "DPBI" is created for optin information check
    Examples:
      |projectName|
      |[blank]|

  @CP-269 @CP-269-05 @asad @events @CP-11786 @CP-11786-22 @events-cc
  Scenario Outline: check dates for CONSUMER_SAVE_EVENT for PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for optin information check with "empty" End Date
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" optin information check
    Then I will verify the "DATES_CHECK" in the "CONSUMER_SAVE_EVENT" for optin information check
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for optin information check
    Examples:
      |projectName|
      |[blank]|

  #@CP-24454 @CP-24454-01 @chopa @ui-cc-in @crm-regression          #muted due to changes in CP-30248 and CP-30249
  Scenario: IN-EB Verify pregnancy fields displayed on Add Primary Individual Page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on the Add button for primary individual
    Then I verify pregnancy start and end dates are displayed on Add Primary Individual Page

  #@CP-24454 @CP-24454-02 @chopa @ui-cc-in @crm-regression           #muted due to changes in CP-30248 and CP-30249
  Scenario: IN-EB Verify Member Ward Type values displayed on Add Primary Individual Page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on the Add button for primary individual
    Then Verify "Member Ward Type" dropDown has options
      | CHINS          |
      | Court Order    |
      | No             |
      | Parent Term    |
      | Yes            |

  #@CP-24454 @CP-24454-03 @chopa @ui-cc-in @crm-regression         #muted due to changes in CP-30248 and CP-30249
  Scenario: IN-EB Verify No Communication Opt-In Preferences component on Add Primary Individual Page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on the Add button for primary individual
    Then I verify no Communication Opt-In Preferences component displayed