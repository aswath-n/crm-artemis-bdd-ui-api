Feature: Inbound Correspondence Definition

  @CP-4428 @CP-4428-01 @ui-ecms2 @burak
  Scenario: Verify that I have the option to add a Inbound Correspondence definition
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    Then I should see the add an Inbound Correspondence Definition button

  @CP-4428 @CP-4428-02 @CP-36281 @CP-36281-01 @ui-ecms2 @burak #Fails due to the CP-36700
  Scenario: Verify that Inbound Correspondence Definition Name Displays as 50 character
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    When I have at least 1 Inbound Correspondence Definition with 50 characters for Name
    Then I should see Inbound Correspondence Name is 50 characters displayed

  @CP-4428 @CP-4428-03 @ui-ecms2 @burak #Fails due to the CP-36700
  Scenario: Verify that Inbound Correspondence Definition Barcode Displays as 4 character
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    Then I should see the add an Inbound Correspondence Definition button
    When I have at least 1 Inbound Correspondence Definition with 4 characters for Barcode
    Then I should see Inbound Correspondence Barcode is "4" characters displayed

  @CP-4428 @CP-4428-04 @ui-ecms2 @burak #Fails due to the CP-36700
  Scenario: Barcode left padded with zeroes
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    Then I should see the add an Inbound Correspondence Definition button
    When I have at least 1 Inbound Correspondence Definition with 3 characters for Barcode
    Then I should see Inbound Correspondence Barcode left padded with zeroes

  @CP-4428 @CP-4428-05 @ui-ecms2 @burak
  Scenario: Verify that I am able to select the Inbound Correspondence definition by clicking on the Name hyperlink
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    When I click on the "Name" hyperlink
    Then I should see the Inbound Correspondence Definition details displayed

  @CP-4428 @CP-4428-06 @ui-ecms2 @burak
  Scenario: Verify I can view at least 20 Inbound Correspondence Records on the screen
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    When I have at least 20 Inbound Correspondence Definition
    Then I should see the 20 Inbound Correspondence Definition details displayed

  @CP-4428 @CP-4428-07 @ui-ecms2 @burak #Fails due to the CP-36700
  Scenario Outline: Verify that Null fields display as "--" on the Inbound Correspondence Definition List Page
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    And I click add button on Inbound Correspondence Definition
    When I add a "<Field>" to the Inbound Correspondence Definition containing "0" free text characters
    And I fill fields in create Inbound Correspondence screen
    And I click save button on create Inbound Correspondence screen
    And I click on the back arrow button on Inbound Correspondence details
    Then I verify "<Field>" field displays as "--" on the Inbound Correspondence Definition List Page
    Examples:
      | Field      |
      | Task Types |
      | Barcode    |

  @CP-4428 @CP-4428-08 @ui-ecms2 @burak
  Scenario: Verify that Inbound Correspondence Definition Task Rules Displays as 200 character
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    And I find the Inbound Correspondence with Definition Name value of "TaskExceedTest"
    And I verify the Task Types Field displays as the following text.
      | Process Verification Document,Process Verification Document,Process Verification Document,Process Verification Document,Process Verification Document,Process Verification Document,Process Verificat... |

  @CP-37012 @CP-37012-01 @ui-ecms2 @burak
  Scenario: Verify there is no edit button on Inbound Correspondence Definition Screen
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    When I click on the "Name" hyperlink
    And I verify there is no edit button on Inbound Correspondence Definition