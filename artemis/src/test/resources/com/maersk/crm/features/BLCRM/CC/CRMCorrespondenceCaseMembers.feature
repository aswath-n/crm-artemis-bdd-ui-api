@CP-276 @CP-277
Feature: Capture Correspondence Preferences for Primary Individual and Case Member

  @CP-276 @CP-276-01 @asad @crm-regression @ui-cc
  Scenario: View Correspondence Preference from View PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on add "Primary Individual" for Correspondence preference
    Then I verify that Consumers Correspondence Preference is present

  @CP-276 @CP-276-02.0 @asad @crm-regression @ui-cc
  Scenario: Update Correspondence Preference - Existing PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on existing "Primary Individual" for Correspondence preference
    Then I verify Consumers Correspondence Preference dropdown option based on "selected" for ""

  @CP-276 @CP-276-02.1 @asad @crm-regression @ui-cc
  Scenario: Multi-Select - Update
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on existing "Primary Individual" for Correspondence preference
    Then I verify Consumers Correspondence Preference multi select dropdown option for "Primary Individual"

  @CP-276 @CP-276-02-2 @asad @crm-regression @ui-cc
  Scenario: Update Correspondence Preference de-selecting option - Existing PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on existing "Primary Individual" for Correspondence preference
    Then I verify Consumers Correspondence Preference dropdown option based on "nonselected" for ""

  @CP-276 @CP-276-03.0 @asad @crm-regression @ui-cc
  Scenario: Capture Correspondence Preference - Add New PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on add "Primary Individual" for Correspondence preference
    Then I verify Consumers Correspondence Preference dropdown option based on "selected" for ""

  @CP-276 @CP-276-03.1 @asad @crm-regression @ui-cc
  Scenario: Multi-Select - Add New for PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on add "Primary Individual" for Correspondence preference
    Then I verify Consumers Correspondence Preference multi select dropdown option for "Primary Individual"

  @CP-276 @CP-276-03.2 @asad @crm-regression @ui-cc
  Scenario: Capture Correspondence Preference de-selecting option - Add New PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on add "Primary Individual" for Correspondence preference
    And I add "Primary Individual" for Correspondence Preference check
    And I deselect correspondence preference option for "Primary Individual"
    Then I verify Consumers Correspondence Preference dropdown option based on "nonselected" for ""

  @CP-276 @CP-276-04.1 @asad @events @CP-11786 @CP-11786-26 @events-cc
  Scenario Outline: Generate Event to DPBI  - CONSUMER_SAVE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add "Primary Individual" for Correspondence preference
    And I add "Primary Individual" for Correspondence Preference check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" correspondence preferences check
    Then I will verify the "SAVED" in the "CONSUMER_SAVE_EVENT" for Correspondence Preference check
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for Correspondence Preference check
    Examples:
      |projectName|
      |[blank]|

  @CP-276 @CP-276-04.2 @asad @events @CP-11786 @CP-11786-27 #Fails due to defect CP-12642
  Scenario Outline: Generate Event to DPBI - CONSUMER_UPDATE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add "Primary Individual" for Correspondence preference
    And I add "Primary Individual" for Correspondence Preference check
    And I update "Primary Individual" for Correspondence Preference check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" correspondence preferences check
    Then I will verify the "UPDATED" in the "CONSUMER_UPDATE_EVENT" for Correspondence Preference check
    Then I will verify subscriber received "CONSUMER_UPDATE_EVENT" event for "DPBI" is created for Correspondence Preference check
    Examples:
      |projectName|
      |[blank]|

  @CP-277 @CP-277-01 @asad @crm-regression @ui-cc
  Scenario: View Correspondence Preference for CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on add "Member" for Correspondence preference
    Then I verify that Consumers Correspondence Preference is present

  @CP-277 @CP-277-02.0 @asad @crm-regression @ui-cc
  Scenario: Update Correspondence Preference - Existing CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "Member" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on existing "Member" for Correspondence preference
    Then I verify Consumers Correspondence Preference dropdown option based on "selected" for "Case Member"

  @CP-277 @CP-277-02.1 @asad @crm-regression @ui-cc
  Scenario: Multi-Select - Update CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "Member" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on existing "Member" for Correspondence preference
    Then I verify Consumers Correspondence Preference multi select dropdown option for "Case Member"

  @CP-277 @CP-277-02.2 @asad @crm-regression @ui-cc
  Scenario: Update Correspondence Preference de-selecting option - Existing CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "Member" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on existing "Member" for Correspondence preference
    Then I verify Consumers Correspondence Preference dropdown option based on "nonselected" for "Case Member"

  @CP-277 @CP-277-03.0 @asad @crm-regression @ui-cc
  Scenario: Capture Correspondence Preference - Add New CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on add "Member" for Correspondence preference
    Then I verify Consumers Correspondence Preference dropdown option based on "selected" for "Case Member"

  @CP-277 @CP-277-03.1 @asad @crm-regression @ui-cc
  Scenario: Multi-Select - Add New for CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on add "Member" for Correspondence preference
    Then I verify Consumers Correspondence Preference multi select dropdown option for "Case Member"

  @CP-277 @CP-277-03.2 @asad @crm-regression @ui-cc
  Scenario: Capture Correspondence Preference de-selecting option - Add New CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an Consumer Profile for Correspondence preference
    And I click on demographic tab and click on add "Member" for Correspondence preference
    And I add "Case Member" for Correspondence Preference check
    And I deselect correspondence preference option for "Member"
    Then I verify Consumers Correspondence Preference dropdown option based on "nonselected" for "Case Member"

  @CP-277 @CP-277-04.1 @asad @events @CP-11786 @CP-11786-28
  Scenario Outline: Generate Event to DPBI  - CONSUMER_SAVE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add "Member" for Correspondence preference
    And I add "Case Member" for Correspondence Preference check
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" correspondence preferences check
    Then I will verify the "SAVED" in the "CONSUMER_SAVE_EVENT" for Correspondence Preference check
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for Correspondence Preference check
    Examples:
      |projectName|
      |[blank]|

  @CP-277 @CP-277-04.2 @asad @events @CP-11786 @CP-11786-29 #Fails due to defect CP-12642
  Scenario Outline: Generate Event to DPBI - CONSUMER_UPDATE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API with Consumer Role "" for Correspondence preference
    And I search consumer with first name and last name for Correspondence preference
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add "Member" for Correspondence preference
    And I add "Case Member" for Correspondence Preference check
    And I update "Case Member" for Correspondence Preference check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" correspondence preferences check
    Then I will verify the "UPDATED" in the "CONSUMER_UPDATE_EVENT" for Correspondence Preference check
    Then I will verify subscriber received "CONSUMER_UPDATE_EVENT" event for "DPBI" is created for Correspondence Preference check
    Examples:
      |projectName|
      |[blank]|