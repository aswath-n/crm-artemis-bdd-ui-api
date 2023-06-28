Feature: Update Authorized Representative Information

  @CP-340 @CP-340-1.0 @crm-regression @ui-cc @JP
  Scenario Outline: Update AR Information and verify all fields are editable
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I am able to add the following Authorized Representative Information fields
    And I navigate to active Authorized Representative record edit page
    And I am able to edit following fields
      | Firstname   | Middleinitial   | Lastname   | DOB   | Gender   | SSN   | WrittenLanguage   | SpokenLanguage   | AuthorizationType   | StartDate   | EndDate   |
      | <Firstname> | <Middleinitial> | <Lastname> | <DOB> | <Gender> | <SSN> | <WrittenLanguage> | <SpokenLanguage> | <AuthorizationType> | <StartDate> | <EndDate> |
    And I modify the Authorized Representative record edit page
      | startDate | endDate |
      | P12       | P2      |
    Examples:
      | Firstname | Middleinitial | Lastname  | DOB        | Gender | SSN       | WrittenLanguage | SpokenLanguage | AuthorizationType | StartDate  | EndDate    |
      | UpdateGN  | U             | UpdatedLN | 01/01/1900 | Female | 111222333 | Other           | Other          | Partial Access    | 01/01/1900 | 12/12/2022 |

  @CP-340 @CP-340-1.1 @crm-regression @ui-cc @JP
  Scenario: Update AR Information to verify error messages with no data
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I am able to add the following Authorized Representative Information fields
    And I navigate to active Authorized Representative record edit page
    And I save without filling following fields
      | Firstname | Lastname | DOB | AuthorizationType | StartDate |
      |[blank]|          |[blank]|                   |[blank]|
    Then I should see every following field is required
      | Firstname  | Lastname  | DOB | AuthorizationType | StartDate  |
      | FIRST NAME | LAST NAME | DOB |[blank]| START DATE |

  @CP-340 @CP-340-1.2 @crm-regression @ui-cc @JP
  Scenario: Update AR Information with field level validations
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I am able to add the following Authorized Representative Information fields
    And I navigate to active Authorized Representative record edit page
    And I am able to edit following fields with invalid data
      | Firstname | Lastname | DOB | SSN  | StartDate |
      |[blank]|          | 12  | 1234 | 12        |
    Then I should see every following field data invalid  format
      | Firstname  | Lastname  | DOB | AuthorizationType | StartDate  |
      | FIRST NAME | LAST NAME | DOB |[blank]| START DATE |

  @CP-340 @CP-340-1.3 @crm-regression @ui-cc @JP
  Scenario Outline: AR Information Save.
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I am able to add the following Authorized Representative Information fields
    And I navigate to active Authorized Representative record edit page
    And I am able to edit following fields
      | SpokenLanguage   | AuthorizationType   |
      | <SpokenLanguage> | <AuthorizationType> |
    And I save all details
    Then I should see suuccessfully updated message
    Examples:
      | SpokenLanguage | AuthorizationType |
      | Other          | Partial Access    |

  @CP-340 @CP-340-1.4 @crm-regression @ui-cc @JP
  Scenario: Verify Cancel button with back arrow and navigate away in AR Page
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I am able to add the following Authorized Representative Information fields
    And I navigate to active Authorized Representative record edit page
    And I edit the AR details to check cancel operations
    And I click on cancel button on Authorized Representative page
    Then I verify the  warning message on dialog box
    When I click on keyboard backspace button
    Then I verify the  warning message on dialog box
    When I try to navigate to different page without saving changes
    Then I verify the  warning message on dialog box

  @CP-340 @CP-340-1.6 @crm-regression @ui-cc @JP
  Scenario: Update AR Information - Inactivate Immediately Button
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I am able to add the following Authorized Representative Information fields
    And I navigate to active Authorized Representative record edit page
    And I modify the Authorized Representative record edit page
      | startDate | endDate |
      | P15       |[blank]|
    And I navigate to active Authorized Representative record edit page
    And I click on inactivate button
    Then I verify the Authorized Representative have following status
      | Status   |
      | INACTIVE |

  @CP-340 @CP-340-2.0 @crm-regression @ui-cc @JP
  Scenario: Update AR Information and verify status
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I am able to add the following Authorized Representative Information fields
    And I navigate to active Authorized Representative record edit page
    And I modify the Authorized Representative record edit page
      | startDate | endDate |
      | P0        |[blank]|
    Then I verify the Authorized Representative have following status
      | Status |
      | ACTIVE |
    When I navigate to active Authorized Representative record edit page
    And I modify the Authorized Representative record edit page
      | startDate | endDate |
      | F5        | F15     |
    Then I verify the Authorized Representative have following status
      | Status |
      | FUTURE |
    When I navigate to active Authorized Representative record edit page
    And I modify the Authorized Representative record edit page
      | startDate | endDate |
      | P12       | P2      |
    Then I verify the Authorized Representative have following status
      | Status   |
      | INACTIVE |

  @CP-340 @CP-340-3.0 @crm-regression @ui-cc @JP
  Scenario: Update AR Information and verify age updated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I am able to add the following Authorized Representative Information fields
    And I navigate to active Authorized Representative record edit page
    Then I verify age field value is according to Date Of Birth

  @CP-340 @CP-340-4.0 @crm-regression @ui-cc @JP
  Scenario: Capture Update AR Information with Save event
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I am able to add the following Authorized Representative Information fields
    And I navigate to active Authorized Representative record edit page
    And I modify the AR details to check update event
    Then I will see the following updated fields
      | Field1    | Field2    | Field3     |
      | createdOn | createdBy | genderCode |