Feature: Edit Outbound Correspondence Channel Definition


  @DMS-158-1 @DMS-158 @ui-ecms1 @James
  Scenario: Verify the following fields are required when you edit a Channel in Correspondence Definition to be blank; Start Date
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    And I edit Correspondence Channel Definition
    When I edit a Correspondence Channel Definition to make the "startDate" to be "null"
    Then I should the save fail with error message "START DATE is required and cannot be left blank"

  @DMS-158-2 @DMS-158 @ui-ecms1 @James
  Scenario Outline: Verify the following fields are optional to edit a Channel in Correspondence Definition to be blank; End Date, Sender Email
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a "Email" Channel
    When I edit a Correspondence Channel Definition to make the "<Field>" to be "<Value>"
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    Then the Correspondence Definition Channel should save successfully
    Examples:
      | Field         | Value |
      | endDate       | null  |
      | senderEmailId | null  |

  @DMS-158-3 @DMS-158 @ui-ecms1 @James
  Scenario: Verify Sender Email is enabled when channel is changed to email
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a "Text" Channel
    When I edit a Correspondence Channel Definition to make the "channelType" to be "Email" and don't save
    Then I should see the endReason field is enabled

  @DMS-158-4 @DMS-158 @ui-ecms1 @James #fails due to CP-36109
  Scenario: Verify that editing a second Outbound Correspondence Definition Channel with the same channel as another active channel with no End Date will bring up the error message saying "START DATE cannot overlap the date range of another record for the same channel"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I edit "Current_SysDate" and "Current_SysDate+2Month" in Correspondence Definition
    And I create an Outbound Correspondence Definition Channel with Channel Type of "Mail",Start Date of "Current_SysDate", End Date of "Current_SysDatePlusOneDay"
    And I create an Outbound Correspondence Definition Channel with Channel Type of "Mail",Start Date of "Current_SysDatePlusTwoDays", End Date of "Current_SysDatePlusOneMonth"
    When I edit the previously saved Correspondence Channel Definition to make Start Date of "Current_SysDate", End Date of "Current_SysDatePlusOneMonth"
    Then I should see the save fail with the message "START DATE cannot overlap the date range of another record for the same channel"

  @DMS-158-5 @DMS-158 @ui-ecms2 @James
  Scenario: Verify attempting to edit a Channel with a Start Date to a date before the current date will bring up the error message "START DATE cannot be changed to date before today"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    And I edit Correspondence Channel Definition
    When I edit the previously saved Correspondence Channel Definition to make Start Date of last month, End Date of "Current_SysDatePlusOneMonth"
    Then I should the save fail with error message "START DATE cannot be changed to a date before today."

  @DMS-158-6 @DMS-158 @ui-ecms2 @James #fails due to CP-36109
  Scenario: Verify that editing a second Outbound Correspondence Definition Channel to remove an End Date to make it overlap another active channel will bring up the error message saying "END DATE cannot be removed if there is a later record for the same channel"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    And I create an Outbound Correspondence Definition Channel with Channel Type of "Mail",Start Date of "Current_SysDatePlusOneMonth", End Date of "Current_SysDatePlusOneMonth"
    And I create an Outbound Correspondence Definition Channel with Channel Type of "Mail",Start Date of "Current_SysDate", End Date of "Current_SysDatePlusTwoDays"
    When I edit the previously saved Correspondence Channel Definition to make "endDate" of "null"
    Then I should see the save fail with the message "END DATE cannot be removed if there is a later record for the same channel"

  @DMS-158-7 @DMS-158 @ui-ecms2 @James
  Scenario: Verify attempting to edit a Channel with a End Date earlier than the Start Date will bring up the error message saying "END DATE cannot be earlier than START DATE"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    And I click the "first" channel under Outbound Correspondence Definition
    When I edit the Correspondence Channel Definition to make Start Date of "Current_SysDate", End Date of "Current_SysDateMinusOneMonth"
    Then I should see the save fail with the message "END DATE cannot be earlier than START DATE"
