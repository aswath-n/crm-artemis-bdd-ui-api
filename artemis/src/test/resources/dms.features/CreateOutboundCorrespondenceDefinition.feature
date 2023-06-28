Feature: Create Outbound Correspondence Definition

  @DMS-147-1 @DMS-147 @ui-ecms1 @James
  Scenario: Verify the Correspondence Definition Screen message contents
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Correspondence Definitions Screen of Project:"current"
    And I begin to add a Correspondence Definition
    Then I should see a message that says "For a Correspondence Definition to be complete, it must have an active Channel with an active Language Template."

  @DMS-147-2 @DMS-147 @ui-ecms1 @James
  Scenario Outline: Verify that ID and Name are Unique within a project
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    And I navigate away from the Outbound Correspondence Definition
    When I add another Outbound Correspondence Definition with the same "<Field>" with the same "<Value>"
    Then I should fail attempting to add a Correspondence Definition
    Examples:
      | Field | Value    |
      | ID    | SameId   |
      | Name  | SameName |

  @DMS-147-3 @DMS-147 @ui-ecms1 @James
  Scenario Outline:  Verify the following fields in the Correspondence Definition Screen are required within a project; ID, Name, Description, Start Date
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add a new Correspondence Definition without a "<Field>"
    Then I should see a failure message for the required : "<Field>"
    Examples:
      | Field       |
      | Id          |
      | Name        |
      | Description |
      | startDate   |

  @DMS-147-4 @DMS-147 @ui-ecms1 @James
  Scenario Outline:  Verify that ID and State ID are only accommodate 10 alphanumeric characters
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add values to the following Fields in the add Correspondence Definition screen; "<ID>", "<State_ID>"
    Then I should only the following values was allowed in each field; "<ID_Stored>", "<State_ID_Stored>"
    Examples:
      | ID           | State_ID     | ID_Stored  | State_ID_Stored |
      | 123456a89112 | 123456a89112 | 123456a891 | 123456a891      |
      | !23456789!   | 23456789     | 23456789   | 23456789        |

  @DMS-147-5 @DMS-147 @ECMS-SMOKE @ui-ecms1 @James
  Scenario Outline: Verify that State ID and End Date are not required to add a Correspondence Definition
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add a new Correspondence Definition without a "<Field>"
    Then I should the Correspondence Definition was successfully added
    Examples:
      | Field   |
      | endDate |
      | stateId |

  @DMS-147-6 @DMS-147 @CP-31482 @CP-31482-02 @ui-ecms1 @James
  Scenario: Verify that Description in the Correspondence Definition Screen can accommodate 500 characters and accepts Special Characters
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add a Description to the Correspondence Definition containing "500" free text characters
    Then I should be able to all "500" characters accommodated in the "Description" field

  @DMS-147-7 @DMS-147 @ui-ecms1 @James
  Scenario Outline: Verify Start Date and End Date cannot be prior to current Date
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add "<Start_Date>" and "<End_Date>" to Correspondence Definition
    Then I should see the following "<Message_StartDate>" appear for the Start Date field and "<Message_EndDate>" for End Date
    Examples:
      | Start_Date | End_Date   | Message_StartDate                                    | Message_EndDate                             |
      | 01/02/1999 | 01/01/1999 | START DATE cannot be changed to a date before today. | END DATE cannot be earlier than START DATE. |

  @DMS-147-8 @DMS-147 @ui-ecms2 @James
  Scenario Outline: Verify that End Date is greater than or equal to Start Date
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add "<Start_Date>" and "<End_Date>" to Correspondence Definition
    Then I should see the "<Message>" appear
    Examples:
      | Start_Date      | End_Date               | Message              |
      | Current_SysDate | Current_SysDate-1Month | endDateCannotBePrior |
      | Current_SysDate | Current_SysDate        | null                 |

  @DMS-147-9 @DMS-147 @ui-ecms2 @James
  Scenario: Verify that End reason is Required if End Date is entered
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add an "endDate" to the Correspondence Definition
    Then I should see that "endReasonRequired" is required message

  @DMS-147-10 @DMS-147 @ui-ecms2 @James
  Scenario: Verify that End Reason can accommodate 100 free text characters
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add an "endReason" to the Correspondence Definition with "100" free text characters
    Then I should be able to all "100" characters accommodated in the "End Reason" field

  @DMS-147-11 @DMS-147 @ui-ecms2 @James
  Scenario: Verify that Add a Channel button is present in Correspondence Definition Screen
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Correspondence Definitions Screen of Project:"current"
    And I add a new Outbound Correspondence Definition with Random Values
    When edit the previously created Outbound Correspondence Definition
    Then I should see the Add Channel button present

  @DMS-147-12 @DMS-147 @API-ECMS @James
  Scenario: Verify that a successful save will trigger the system to assign a Correspondence Definition ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Outbound Correspondence Definition with random values for just the required values
    When I send the request to create an Outbound Correspondence Definition
    Then I should see a Correspondence ID is generated, valid data has been saved, Created by has been saved, and Created On has been saved

  @DMS-147-13 @API-ECMS @ECMS-SMOKE @api-smoke-devops @James
    Scenario: Verify Add Outbound Correspondence Definition API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Outbound Correspondence Definition with random values for just the required values
    When I send the request to create an Outbound Correspondence Definition
    Then I should see the request to create an Outbound Correspondence Definition was successful

  @CP-7400 @CP-7400-1 @API-ECMS @Sang
  Scenario: Verify that Inbound Correspondence definition Id is added to storing Outbound Correspondence Definition
    Given I have random valid data for an Outbound Correspondence Definition
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I send the Outbound Correspondence Definition to the server for project: "current"
    Then I should see Inbound Correspondence definition ID in the response of outbound Correspondence definition POST

  @CP-7400 @CP-7400-2 @API-ECMS @Sang
  Scenario: Verify that Inbound Correspondence definition Id is contained in Get Outbound Correspondence Definition
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate Get Outbound Correspondence definition
    Then I verify Outbound Correspondence contains Inbound Correspondence Definition ID

  @CP-10594 @CP-10594-01 @API-ECMS @James
  Scenario: Verify type approval added to create correspondence definition
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Outbound Correspondence Definition with random values for just the required values
    When I send the request to create an Outbound Correspondence Definition
    Then I should see the request to create an Outbound Correspondence Definition was successful
    And I should see that the approvalRequired in the response for the Outbound Correspondence Definition

  @CP-10594 @CP-10594-02 @API-ECMS @James
  Scenario: Verify type approval added to retrieve correspondence definition
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Outbound Correspondence Definition with random values for just the required values
    And I send the request to create an Outbound Correspondence Definition
    When I retrieve the previously created Outbound Correspondence Definition
    Then I should see that the approvalRequired value was saved on the Outbound Correspondence Definition

  @CP-10594 @CP-10594-03 @API-ECMS @James
  Scenario: Verify type approval added to Outbound Correspondence Definition by mmsCode
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to retrieve a Outbound Correspondence Definition by mmsCode
    Then I should see approvalRequired value is returned in the response Outbound Correspondence Definition by mmsCode

  @CP-10594 @CP-10594-04 @API-ECMS @James
  Scenario: Verify type approval added to Outbound Correspondence Definition list by project
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to retrieve a Outbound Correspondence list by project
    Then I should see approvalRequired value is returned in the response Outbound Correspondence Definition list by project

  @CP-10594 @CP-10594-05 @ui-ecms2 @James
  Scenario: Verify that approvalRequired is present and saved
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I add an "approvalRequired" to the Correspondence Definition in addition to all required fields
    Then I should the Correspondence Definition was successfully added

  @CP-25021-01 @ui-ecms2 @Keerthi
  Scenario:Validate the Inbound Correspondence Type field is not Mandatory
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "BLCRM"
    #When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    Then I validate the Inbound Correspondence Type field is not Mandatory

  @API-CP-24281 @API-CP-24281-01 @API-ECMS @RuslanL
  Scenario: Verify when OC definition is created Body Data Structure Version is 1
    Given I have random valid data for an Outbound Correspondence Definition
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I send the Outbound Correspondence Definition to the server for project: "current" with random valid data and Body Data Structure
    Then I verify the Outbound Correspondence definition response body has Body Data Structure Version is 1

  @API-CP-24281 @API-CP-24281-02 @API-ECMS @RuslanL
  Scenario: Verify when user adding new body element Body Data Structure Version increasing by 1 each time
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I update the Outbound Correspondence Definition and add body element
      |Last Name|
      |DOB|
    Then I verify the Outbound Correspondence definition response body has Body Data Structure Version is 2
    When I update the Outbound Correspondence Definition and add body element
      |SSN|
    Then I verify the Outbound Correspondence definition response body has Body Data Structure Version is 3


  @API-CP-24281 @API-CP-24281-03 @API-ECMS @RuslanL
  Scenario: Verify when user not adding new body element Body Data Structure Version not changing
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I update the Outbound Correspondence Definition and add body element
      |null|
    Then I verify the Outbound Correspondence definition response body has Body Data Structure Version is 3
