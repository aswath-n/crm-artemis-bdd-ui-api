Feature: Create Outbound Correspondence Channel 2

  @DMS-148-12 @DMS-148 @ui-ecms1 @James
  Scenario Outline: Verify that Sender Email is not a valid email address
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add an Outbound Correspondence Channel Definition with a "senderEmailId" of "<Email>" plus "channelType" of "Email"
    Then I should see the save fail with the message "Email Address format is invalid. Please enter it in format xx@xx.xx."
    Examples:
      | Email                               |
      | .false@maersktesting1.com          |
      | false.@maersktesting1.com          |
      | false1....false@maersktesting1.com |
      | false1@.maersktesting1.com         |
      | false1@maersktesting1.com.         |
      | false1@-maersktesting1.com         |
      | false1@maersktesting1.com-         |

  @DMS-148-13 @DMS-148 @ECMS-SMOKE @ui-ecms1 @ECMS-SMOKE @James
  Scenario: Verify that when saving a channel, the Save Success message appears
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add a Outbound Correspondence Channel Definition
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    Then the Correspondence Definition Channel save successfully pop up message should appear

  @DMS-148-14 @DMS-148  @ui-ecms2 @James
  Scenario: Verify that there is a warning before navigating away from Channel screen which will discard data
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add a Outbound Correspondence Channel Definition without saving
    When I attempt to navigate away
    Then I should see a warning message allowing to cancel or discard changes

#  @DMS-148-15 @DMS-148 @James
  Scenario: Verify that saving a valid Correspondence Channel Definition will trigger the system to assign a Correspondence Channel Definition Id save time stamp as Created On and User Id as Created By
    Given I have random valid data for an Outbound Correspondence Definition Channel
    And I create an Outbound Correspondence Definition through the API for project: "current"
    When I send the Outbound Correspondence Definition Channel to the server for project for the previously created project: "current"
    Then I should see a Correspondence Channel Definition Id is generated, valid data has been saved, Created by has been saved, and Created On save time stamp as Created On and User Id as Created By has been saved in the response

  @DMS-148-16 @DMS-148 @ui-ecms2 @James
  Scenario: Verify that saving a valid Correspondence Channel Definition will trigger the system to display time stamp as Created On and User Id as Created By
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add a Outbound Correspondence Channel Definition
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    Then the Correspondence Definition Channel should see the system display time stamp as Created On and User Id as Created By

  @CP-11005 @CP-11005-01 @ui-ecms2 @James
  Scenario: Verify Notification Purpose is disabled when channel is Mail
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I select the following values on the Outbound Correspondence Channel Definition Page
      | channelType | Mail       |
      | startDate   | currentDay |
    Then I should see the following values on the Outbound Correspondence Channel Definition Page
      | notificationPurposeIsEnabled | disabled     |
      | notificationPurpose          | Main Content |
      | includeOnWeb                 | true         |

  @CP-11005 @CP-11005-02 @ui-ecms2 @James
  Scenario Outline: Verify Notification Purpose is enabled when channel is NOT Mail
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I select the following values on the Outbound Correspondence Channel Definition Page
      | channelType | <Channel>  |
      | startDate   | currentDay |
    Then I should see the following values on the Outbound Correspondence Channel Definition Page
      | notificationPurposeIsEnabled | <Status>  |
      | includeOnWeb                 | <boolean> |
    Examples:
      | Channel | Status  | boolean |
      | Fax     | enabled | false   |
      | Text    | enabled | false   |
      | Email   | enabled | false   |

  @CP-11005 @CP-11005-03 @ui-ecms2 @James
  Scenario: Verify Notification Purpose and Include on Web labels are correct
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I select an Outbound Correspondence Definition with no Channels
    Then I verify the following labels on the  Correspondence Channel Definition
      | Notification Purpose |
      | Include On Web       |

  @CP-11005 @CP-11005-04 @ui-ecms2 @James
  Scenario: Verify Notification Purpose is disabled when channel is Mail
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I select the following values on the Outbound Correspondence Channel Definition Page
      | channelType | Mail       |
      | startDate   | currentDay |
    And I click on the save button Outbound Correspondence Channel Definition Page
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    And I navigate back to the  Outbound Correspondence Channel Definition Page
    Then I should see the following values on the Outbound Correspondence Channel Definition Page
      | selectChannel                | Mail         |
      | notificationPurposeIsEnabled | disabled     |
      | notificationPurpose          | Main Content |


  @CP-11005 @CP-11005-05 @ui-ecms2 @James
  Scenario: Verify Notification Purpose and Include on Web are saved when channel is Mail
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I select the following values on the Outbound Correspondence Channel Definition Page
      | channelType | Mail       |
      | startDate   | currentDay |
    And I click on the save button Outbound Correspondence Channel Definition Page
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    And I navigate back to the  Outbound Correspondence Channel Definition Page
    Then I should see the following values on the Outbound Correspondence Channel Definition Page
      | selectChannel                | Mail         |
      | notificationPurposeIsEnabled | disabled     |
      | notificationPurpose          | Main Content |

  @CP-11005 @CP-11005-06 @ui-ecms2 @James
  Scenario Outline: Verify Notification Purpose and Include on Web are saved when channel is NOT Mail
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I select the following values on the Outbound Correspondence Channel Definition Page
      | channelType                 | <Channel>  |
      | startDate                   | currentDay |
      | notificationPurposeDropdown | <Value>    |
    And I click on the save button Outbound Correspondence Channel Definition Page
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    And I navigate back to the  Outbound Correspondence Channel Definition Page
    Then I should see the following values on the Outbound Correspondence Channel Definition Page
      | selectChannel                | <Channel> |
      | notificationPurposeIsEnabled | <Status>  |
      | notificationPurpose          | <Value>   |
    Examples:
      | Channel | Status  | Value          |
      | Fax     | enabled | Advance Notice |
      | Text    | enabled | Now Available  |
      | Email   | enabled | See Attached   |

  @CP-11005 @CP-11005-07 @ui-ecms2 @James
  Scenario: Verify Notification Purpose is disabled when channel is Mail
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I select the following values on the Outbound Correspondence Channel Definition Page
      | channelType | Text       |
      | startDate   | currentDay |
    And I click on the save button Outbound Correspondence Channel Definition Page
    Then I should see the save fail with the message "The Notification Purpose cannot be left blank."