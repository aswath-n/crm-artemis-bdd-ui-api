Feature: Edit Outbound Correspondence Definition

  @DMS-157-1 @DMS-157 @ECMS-SMOKE @ui-ecms1 @James
  Scenario: Verify when editing a Correspondence Definition, the Edit Correspondence Definition Screen message contents
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    When edit the previously created Outbound Correspondence Definition
    Then I should see a message that says "For a Correspondence Definition to be complete, it must have an active Channel with an active Language Template."

  @DMS-157-2 @DMS-157 @ui-ecms1 @James
  Scenario Outline: Verify that when editing a Correspondence Definition, ID and Name are Unique within a project when editing an Outbound Correspondence Definition
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values with "<Field>" with "<Value>"
    And I add another Outbound Correspondence Definition with Random Values
    And edit the previously created Outbound Correspondence Definition
    When I Edit the Outbound Correspondence Definition with the same "<Field>" with the same value as another Outbound Correspondence Definition
    Then I should fail attempting to add a Correspondence Definition
    Examples:
      | Field | Value    |
      | ID    | SameId   |
      | Name  | SameName |

  @DMS-157-3 @DMS-157 @ui-ecms1 @James
  Scenario Outline: Verify when editing a Correspondence Definition, the following fields in the Correspondence Definition Screen are required within a project; ID, Name, Description, Start Date
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    And edit the previously created Outbound Correspondence Definition
    When I Edit the Correspondence Definition "<Field>" to be blank
    Then I should see a failure message for the required : "<Field>"
    Examples:
      | Field       |
      | Id          |
      | Name        |
      | Description |
      | startDate   |

  @DMS-157-4 @DMS-157 @ui-ecms1 @James
  Scenario Outline: Verify that when editing ID and State ID can only accommodate 10 alphanumeric characters
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    And edit the previously created Outbound Correspondence Definition
    When I Edit values to the following Fields in the Correspondence Definition screen; "<ID>", "<State_ID>"
    Then I should only the following values was allowed in each field; "<ID_Stored>", "<State_ID_Stored>"
    Examples:
      | ID           | State_ID     | ID_Stored  | State_ID_Stored |
      | 123456a89112 | 123456a89112 | 123456a891 | 123456a891      |
      | !23456789!   | 23456789     | 23456789   | 23456789        |

  @DMS-157-5 @DMS-157 @ui-ecms1 @James
  Scenario: Verify that when editing a Correspondence Definition, navigating away from the Outbound Correspondence Definition page will bring a warning message
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    And edit the previously created Outbound Correspondence Definition
    And I Edit the Correspondence Definition "stateId" to be blank but don't save
    When I attempt to navigate away from Outbound Correspondence Definition page
    Then I should see a warning message allowing me to either discard or cancel changes - DMS

  @DMS-157-6 @DMS-157 @ui-ecms1 @James
  Scenario Outline: Verify that when editing a Correspondence Definition, State ID and End Date are not required to add a Correspondence Definition
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    And edit the previously created Outbound Correspondence Definition
    When I Edit the new Correspondence Definition without a "<Field>"
    Then I should the Correspondence Definition was successfully updated
    Examples:
      | Field   |
      | endDate |
      | stateId |

#  @DMS-157-7 @DMS-157 @ui-ecms1 @James
#  Scenario: Verify that when editing a Correspondence Definition, the Description in the Correspondence Definition Screen can accommodate 500 characters
#    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
#    And I navigate to the Correspondence Definitions Screen of Project:"current"
#    And I add a new Outbound Correspondence Definition with Random Values
#    And edit the previously created Outbound Correspondence Definition
#    When I Edit a Description to the Correspondence Definition containing "500" free text characters
#    Then I should be able to all "500" characters accommodated in the "Description" field

  @DMS-157-8 @DMS-157 @ui-ecms2 @James
  Scenario Outline: Verify when editing a Correspondence Definition, Start Date and End Date cannot be prior to current Date
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    And edit the previously created Outbound Correspondence Definition
    When I edit "<Start_Date>" and "<End_Date>" to Correspondence Definition
    Then I should see the following "<Message_StartDate>" appear for the Start Date field and "<Message_EndDate>" for End Date
    Examples:
      | Start_Date | End_Date   | Message_StartDate                                    | Message_EndDate                             |
      | 01/02/1999 | 01/01/1999 | START DATE cannot be changed to a date before today. | END DATE cannot be earlier than START DATE. |

  @DMS-157-9 @DMS-157 @ui-ecms2 @James
  Scenario Outline: Verify that when editing a Correspondence Definition, End Date is greater than or equal to Start Date
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    And edit the previously created Outbound Correspondence Definition
    When I edit "<Start_Date>" and "<End_Date>" in Correspondence Definition
    Then I should see the "<Message>" appear
    Examples:
      | Start_Date      | End_Date               | Message              |
      | Current_SysDate | Current_SysDate-1Month | endDateCannotBePrior |
      | Current_SysDate | Current_SysDate        | null                 |

  @DMS-157-10 @DMS-157 @ui-ecms2 @James
  Scenario: Verify that when editing a Correspondence Definition, End reason is Required if End Date is entered
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    And edit the previously created Outbound Correspondence Definition
    When I edit an "endReason" of the Correspondence Definition to be blank
    Then I should see that "endReasonRequired" is required message

  @DMS-157-11 @DMS-157 @ui-ecms2 @James
  Scenario: Verify that when editing a Correspondence Definition, End Reason can accommodate 100 free text characters
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add an "endReason" to the Correspondence Definition with "100" free text characters
    Then I should be able to all "100" characters accommodated in the "End Reason" field

  @DMS-157-12 @DMS-157 @ui-ecms2 @ECMS-SMOKE @James
  Scenario: Verify that when editing a Correspondence Definition, Add a Channel button is present in Correspondence Definition Screen
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I select an Outbound Correspondence Definition randomly
    Then I should see the Add Channel button present

  @DMS-157-13 @DMS-157 @ui-ecms2 @James
  Scenario: Verify that a successful save will trigger the system to capture and display the current time stamp as Updated On and User ID as Updated By
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    And edit the previously created Outbound Correspondence Definition
    When I edit an "endReason" of the Correspondence Definition to be "random"
    Then Then I should see valid data has been saved, Updated by and Updated On has been saved and displayed