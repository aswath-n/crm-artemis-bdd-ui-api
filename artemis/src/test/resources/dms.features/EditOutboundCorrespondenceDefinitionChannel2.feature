Feature: Edit Outbound Correspondence Channel Definition2


  @DMS-158-8 @DMS-158 @ui-ecms1 @James #fails due to CP-36109
  Scenario: Verify attempting to edit 2 channels with the same channel where one of the End Dates overlap the Start Date of another channel will bring the error message "END DATE cannot overlap the date range of another record for the same channel"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    And I create an Outbound Correspondence Definition Channel with Channel Type of "Mail",Start Date of "Current_SysDatePlusOneDay", End Date of "Current_SysDatePlusOneDay"
    And I create an Outbound Correspondence Definition Channel with Channel Type of "Mail",Start Date of "Current_SysDate", End Date of "Current_SysDate"
    When I edit the previously saved Correspondence Channel Definition to make Start Date of "Current_SysDate", End Date of "Current_SysDatePlusOneDay"
    Then I should see the save fail with the message "END DATE cannot overlap the date range of another record for the same channel"

#  @DMS-158-9 @DMS-158 @ui-ecms1 @James no longer valid
#  Scenario: Verify the user can edit the amount of Selected fields in a Channel Definition
#    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
#    And I navigate to the Correspondence Definitions Screen of Project:"current"
#    And I select an Outbound Correspondence Definition with a Channel
#    And I edit Correspondence Channel Definition
#    And I move 2 items from the All Fields Box to Selected Fields
#    When I navigate back to the previously edited Channel
#    Then I should see 2 more fields are Selected

  @DMS-158-10 @DMS-158 @ui-ecms1 @James
  Scenario: Verify End Reason is required when editing an End Date
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    And I create an Outbound Correspondence Definition Channel with Channel Type of "Mail",Start Date of "Current_SysDatePlusOneDay", End Date of "Current_SysDatePlusOneDay"
    And I navigate back to the  Outbound Correspondence Channel Definition Page
    When I edit a Correspondence Channel Definition to make the "endReason" to be "null"
    Then I should see the save fail with the message "END REASON is required when END DATE is entered"

  @DMS-158-11 @DMS-158 @ui-ecms1 @James
  Scenario: Verify that when editing a Channel, Sender Email will take a valid Email
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a "Email" Channel
    When I edit a Correspondence Channel Definition to make the "senderEmailId" to be "!#$%&'*+-/=?^_`{|}~a1.b2@maersktesting123-a1.asdf.com"
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    Then the Correspondence Definition Channel should save successfully

  @DMS-158-12 @DMS-158 @ui-ecms2 @James
  Scenario Outline: Verify editing a SenderEmailId will not take invalid emails
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a "Email" Channel
    When I edit a Correspondence Channel Definition to make the "senderEmailId" to be "<Email>"
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

  @DMS-158-13 @DMS-158 @ui-ecms2 @James
  Scenario: Verify that when editing and saving a channel, the Save Success message appears
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    And I click the "first" channel under Outbound Correspondence Definition
    When I edit a Correspondence Channel Definition to make the "endReason" to be "Edited"
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    Then the Correspondence Definition Channel should save successfully

  @DMS-158-15 @DMS-158 @ui-ecms2 @James
  Scenario: Verify that when editing and saving a channel, Updated by and Updated on are displayed
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    And I click the "first" channel under Outbound Correspondence Definition
    And I edit a Correspondence Channel Definition to make the "endReason" to be "Edited"
    Then the Correspondence Definition Channel should see the system display time stamp as Updated On plus User Id as Updated By
