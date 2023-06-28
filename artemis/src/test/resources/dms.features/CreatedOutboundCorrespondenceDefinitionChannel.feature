Feature: Create Outbound Correspondence Channel

  @DMS-148-1 @DMS-148 @ui-ecms1 @James
  Scenario Outline: Verify the following fields are required to add a Channel to a Correspondence Definition; Channel, Start Date
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add a Outbound Correspondence Channel Definition without "<Field>"
    Then I should see the save fail with the message "<Message>"
    Examples:
      | Field       | Message                                         |
      | channelType | The channel cannot be left blank                |
      | startDate   | START DATE is required and cannot be left blank |

  @DMS-148-2 @DMS-148 @ECMS-SMOKE @ui-ecms1 @James
  Scenario Outline: Verify the following fields are optional to add a Channel to a Correspondence Definition; End Date, Sender Email
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add a Outbound Correspondence Channel Definition without "<Field>"
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    Then the Correspondence Definition Channel should save successfully
    Examples:
      | Field         |
      | Definition    |
      | senderEmailId |
      | endDate       |

  @DMS-148-3 @DMS-148 @ui-ecms1 @James
  Scenario: Verify Sender Email is enabled when email is the channel
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add a Correspondence Channel Definition with the channel type of "Email"
    Then I should see the field "senderEmail" is enabled

  @DMS-148-4 @DMS-148 @ui-ecms1 @James
  Scenario: Verify that adding a second Outbound Correspondence Definition Channel with the same channel as another active channel with no End Date will bring up the error message saying "START DATE cannot overlap the date range of another record for the same channel"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel with no "endDate"
    When I add a second Correspondence Channel Definition with the same "channelType"
    Then I should see the save fail with the message "START DATE cannot overlap the date range of another record for the same channel"

  @DMS-148-5 @DMS-148 @ui-ecms1 @James
  Scenario: Verify attempting to create a Channel with a Start Date to a date before the current date will bring up the error message "START DATE cannot be changed to date before today"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add an Outbound Correspondence Channel Definition with a "startDate" of "01/01/2001"
    Then I should see the save fail with the message "START DATE cannot be changed to a date before today"

  @DMS-148-6 @DMS-148 @ui-ecms2 @ui-ecms @James
  Scenario: Verify that adding a second Outbound Correspondence Definition Channel with the same channel and End Date that is prior to another active channel with an End Date will bring up the error message saying "START DATE cannot overlap the date range of another record for the same channel"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    And I add an Outbound Correspondence Channel Definition with a "endDate" of "Current_SysDatePlusOneMonth"
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    When I add a second Correspondence Channel Definition with; same channel and Start Date prior to End Date of first channel
    Then I should see the save fail with the message "START DATE cannot overlap the date range of another record for the same channel"

  @DMS-148-7 @DMS-148 @ui-ecms2 @James
  Scenario: Verify attempting to create a Channel with a End Date earlier than the Start Date will bring up the error message saying "END DATE cannot be earlier than START DATE"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add an Outbound Correspondence Channel Definition with an End Date prior to Start Date
    Then I should see the save fail with the message "END DATE cannot be earlier than START DATE"

  @DMS-148-8 @DMS-148 @ui-ecms2 @James
  Scenario: Verify attempting to create 2 channels with the same channel where one of the End Dates overlap the Start Date of another channel will bring the error message "END DATE cannot overlap the date range of another record for the same channel"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    And I add a future Correspondence Channel Definition with "startDate" at a future date of "Current_SysDatePlusOneWeek"
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    When I add a second Correspondence Channel Definition with; same channel and "endDate" of "Current_SysDatePlusOneMonth"
    Then I should see the save fail with the message "START DATE cannot overlap the date range of another record for the same channel"

#  @DMS-148-9 @DMS-148 @ui-ecms1 @James fields no longer exist
  Scenario: Verify the user can select one or more items and make them move from Selected box to Available box
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition randomly
    When I add a Outbound Correspondence Channel Definition with 2 items from the All Fields Box to Selected Fields
    Then the Correspondence Definition Channel should save successfully

  @DMS-148-10 @DMS-148 @ui-ecms2 @James
  Scenario: Verify End Reason is required when entering and End Date when saving a channel
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add an Outbound Correspondence Channel Definition with a "endReason" of "null"
    Then I should see the save fail with the message "END REASON is required when END DATE is entered"

  @DMS-148-11 @DMS-148 @ui-ecms2 @James
  Scenario: Verify that Sender Email is a valid email address
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add an Outbound Correspondence Channel Definition with a "senderEmailId" of "!#$%&'*+-/=?^_`{|}~a1.b2@maersktesting123-a1.asdf.com"
    And I click on the navigate back button from the Outbound Correspondence Channel Definition Page
    Then the Correspondence Definition Channel should save successfully

  @CP-37918 @CP-37918-1a @API-ECMS @James
  Scenario: Verify Channel Definition Save endpoint includes, notificationPurpose element containing a string and a includeOnWeb element containing a boolean
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I create a Outbound Correspondence Definition from api for "CorrespondenceRegression" project
    When I create a Outbound Correspondence Channel Definition from api for the "previouslyCreated" Outbound Correspondence Definition for "CorrespondenceRegression" project with the following values
      | notificationPurpose | random |
      | includeOnWeb        | random |
      | channel             | random |
    Then I should see the following values were saved on the "previouslyCreated" Outbound Correspondence Channel Definition
      | notificationPurpose | previouslyCreated |
      | includeOnWeb        | previouslyCreated |
      | channel             | previouslyCreated |

  @CP-37918 @CP-37918-1b @API-ECMS @James
  Scenario: Verify Updating a previously created Outbound Channel Definition will save notificationPurpose and includeOnWeb values
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I create a Outbound Correspondence Definition from api for "CorrespondenceRegression" project
    When I create a Outbound Correspondence Channel Definition from api for the "previouslyCreated" Outbound Correspondence Definition for "CorrespondenceRegression" project with the following values
      | notificationPurpose | MAIN  |
      | includeOnWeb        | false |
      | channel             | Text  |
    And I update the Outbound Correspondence Channel Definition from api for the for the "previouslyCreated" Outbound Correspondence Definition for "CorrespondenceRegression" project with the following values
      | notificationPurpose | AVAILABLE |
      | includeOnWeb        | true      |
      | channel             | Text      |
    Then I should see the following values were saved on the "previouslyUpdated" Outbound Correspondence Channel Definition
      | notificationPurpose | AVAILABLE |
      | includeOnWeb        | true      |
      | channelType         | Text      |

  @CP-37918 @CP-37918-2 @API-ECMS @James
  Scenario: Verify notification purpose enum values
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I retrieve the notification purpose values for the project
    Then I should see the following values are present in the notification purpose enum values
      | MAIN      |
      | ADVANCE   |
      | AVAILABLE |
      | ATTACHED  |

