Feature: Capture Opt-In/Out Authorized Representative

  @CP-271 @CP-271-01 @asad @crm-regression @ui-cc
  Scenario: Opt-in/out Check Boxes for AR
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
#    I link the contact to an Consumer Profile for demographic member
    And I click on demographic tab and click on add for "AR"
    Then I verify that all the required checkboxes are present

  @CP-271 @CP-271-02 @asad @crm-regression @ui-cc
  Scenario: Default Selections for AR
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "AR"
    Then I will see the default configured channels of Opted-in as Checked

  @CP-271 @CP-271-03 @asad @crm-regression @ui-cc
  Scenario: Check or Uncheck for AR
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "AR"
    Then I will be able to check or uncheck any of the Opt-in check boxes

  @CP-271 @CP-271-04.1 @asad @events @CP-11786 @CP-11786-17 @events-cc
  Scenario Outline: Publish an CONSUMER_SAVE_EVENT for DPBI to Consumer for AR
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for optin information check with "empty" End Date
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" optin information check
    Then I will verify the "SAVED" in the "CONSUMER_SAVE_EVENT" for optin information check
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for optin information check
    Examples:
      |projectName|
      |[blank]|

  @CP-271 @CP-271-04.2 @asad @events @CP-11786 @CP-11786-18 @events-cc #Fails due to defect CP-13593
  Scenario Outline: Publish an CONSUMER_UPDATE_EVENT for DPBI to Consumer for AR
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for optin information check with "empty" End Date
    And I update "Authorized Representative" for optin information check
    And I will take "traceId" for "CONSUMER_UPDATE_EVENT" for "consumerId" optin information check
    Then I will verify the "UPDATED" in the "CONSUMER_UPDATE_EVENT" for optin information check
    Then I will verify subscriber received "CONSUMER_UPDATE_EVENT" event for "DPBI" is created for optin information check
    Examples:
      |projectName|
      |[blank]|

  @CP-271 @CP-271-05 @asad @events @CP-11786 @CP-11786-19 @events-cc
  Scenario Outline: check dates for CONSUMER_SAVE_EVENT for AR
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for optin information check with "empty" End Date
    And I will take "traceId" for "CONSUMER_SAVE_EVENT" for "consumerId" optin information check
    Then I will verify the "DATES_CHECK" in the "CONSUMER_SAVE_EVENT" for optin information check
    Then I will verify subscriber received "CONSUMER_SAVE_EVENT" event for "DPBI" is created for optin information check
    Examples:
      |projectName|
      |[blank]|

  @CP-24993 @CP-24993-01 @chopa @ui-cc-in @crm-regression
  Scenario: IN-EB Verify the fields displayed on Add Authorized Representative Page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on the Add button for Authorized Representative
    Then I verify the fields displayed on Add Authorized Representative Page

  @CP-24993 @CP-24993-02 @chopa @ui-cc-in @crm-regression
  Scenario: IN-EB Verify No Communication Opt-In Preferences component on Add Authorized Representative Page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "LnHEWEY" and Last Name as "XrRdpda"
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on the Add button for Authorized Representative
    Then I verify no Communication Opt-In Preferences component displayed

  @CP-28620 @CP-28620-01 @CP-34298 @chopa @ui-cc @crm-regression
  Scenario: Verify Authorized Rep Type Field - Create AR
    Given I logged into CRM
    And I click on initiate a contact button
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "AR"
    Then I verify Authorized Ref Type has following options
    |Assister|
    | Broker       |
    |Court Guardianship      |
    |Notarized Medical Proxy |
    |Nursing Facility Rep    |
    |Other                   |
    |Power of Attorney       |

  @CP-28620 @CP-28620-02 @chopa @ui-cc @crm-regression
  Scenario: Verify Authorized Rep Type Value - Create AR
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for optin information check with "empty" End Date
    When I click on existing Authorized Representative Record
    Then I verify Authorized Ref Type value for the case is "Power of Attorney"