Feature: Create Inbound Correspondence Definition with Task Rule

      ################################################## @CP-4429, CP-37012 and CP-35191 ###################################################

      @CP-35191  @CP-35191-1.1 @ui-ecms1 @Keerthi
      Scenario: Verify ADD RULE button in Add mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I click add button on Inbound Correspondence Definition
      Then I validate and click Add Rule button on Inbound Correspondence Definition

      @CP-35191 @CP-37012 @CP-35191-1.2 @ui-ecms1 @Keerthi
      Scenario: Verify ADD RULE button in Edit mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I view "first" IB Correspondence Definition type
      And I validate Edit Definition button removed from Inbound Correspondence Definition details screen
      Then I validate and click Add Rule button on Inbound Correspondence Definition

      @CP-35191 @CP-35191-2.1 @ui-ecms1 @Keerthi
      Scenario: Verify Add Task Behavior in add mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I click add button on Inbound Correspondence Definition
      Then I validate and click Add Rule button on Inbound Correspondence Definition
      And I validate new empty row for Add Task Behavior

      @CP-35191 @CP-37012 @CP-35191-2.2 @ui-ecms1 @Keerthi
      Scenario: Verify Add Task Behavior in Edit mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I view "first" IB Correspondence Definition type
      And I validate Edit Definition button removed from Inbound Correspondence Definition details screen
      Then I validate and click Add Rule button on Inbound Correspondence Definition
      And I validate new empty row for Add Task Behavior


      @CP-35191 @CP-4429  @CP-35191-3.1 @ui-ecms1 @Keerthi
      Scenario: Verify Task Row Content in Add mode of IB correspondence Definition
      Given  I will get the Authentication token for "<projectName>" in "CRM"
      Then I initiated get task types API for project ""
      When I run get task type API
      Given I logged into Tenant Manager Project list page
      When I search for a project by "project" value "SelectBLCRMConfig"
#      When I search for a project by "program" value "Baseline Program"
      When I expend a Project to view the details
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I click add button on Inbound Correspondence Definition
      Then I validate and click Add Rule button on Inbound Correspondence Definition
      And I validate new empty row for Add Task Behavior
      And I validate Task Row Content

      @CP-35191 @CP-4429  @CP-37012 @CP-35191-3.2 @ui-ecms1 @Keerthi
      Scenario: Verify Task Row Content in Edit mode of IB correspondence Definition
      Given  I will get the Authentication token for "<projectName>" in "CRM"
      Then I initiated get task types API for project ""
      When I run get task type API
      Given I logged into Tenant Manager Project list page
      When I search for a project by "project" value "SelectBLCRMConfig"
#      When I search for a project by "program" value "Baseline Program"
      When I expend a Project to view the details
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I view "first" IB Correspondence Definition type
      And I validate Edit Definition button removed from Inbound Correspondence Definition details screen
      Then I validate and click Add Rule button on Inbound Correspondence Definition
      And I validate new empty row for Add Task Behavior
      And I validate Task Row Content

      @CP-35191 @CP-4429  @CP-35191-4.1 @ui-ecms1 @Keerthi
      Scenario: Verify Task Rank Order by rank numerical value in Add mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I add a new Inbound Correspondence Definition with Random Values
      And I add a new Inbound Correspondence Definition Task Rules with given Values
      | RANK      | 25             |
      | TASK TYPE | General Two SR |
      And I validate the Inbound Correspondence Definition update success message
      And I add a new Inbound Correspondence Definition Task Rules with given Values
      | RANK      | 99             |
      | TASK TYPE | General Two SR |
      And I validate the Inbound Correspondence Definition update success message
      And I add a new Inbound Correspondence Definition Task Rules with given Values
      | RANK      | 1              |
      | TASK TYPE | General Two SR |
      And I validate the Inbound Correspondence Definition update success message
      And I validate the Task Rank in Ascending Order

      @CP-35191 @CP-4429  @CP-35191-4.2 @ui-ecms2 @Keerthi
      Scenario: Verify Task Rank Order by rank numerical value in Edit mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I view "first" IB Correspondence Definition type
      And I validate Delete all Task Type Rules
      And I add a new Inbound Correspondence Definition Task Rules with given Values
      | RANK      | 25             |
      | TASK TYPE | General Two SR |
      And I validate the Inbound Correspondence Definition update success message
      And I add a new Inbound Correspondence Definition Task Rules with given Values
      | RANK      | 99             |
      | TASK TYPE | General Two SR |
      And I validate the Inbound Correspondence Definition update success message
      And I add a new Inbound Correspondence Definition Task Rules with given Values
      | RANK      | 1              |
      | TASK TYPE | General Two SR |
      And I validate the Inbound Correspondence Definition update success message
      And I validate the Task Rank in Ascending Order


      @CP-35191 @CP-4429  @CP-35191-4.3 @ui-ecms2 @Keerthi
      Scenario: Verify Task Rank Order by  primary key(Task_Rule_ID) in Add mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I add a new Inbound Correspondence Definition with Random Values
      And I add a new Inbound Correspondence Definition Task Rules with given Values
      | RANK      | 25             |
      | TASK TYPE | General Two SR |
      And I validate the Inbound Correspondence Definition update success message
      And I add a new Inbound Correspondence Definition Task Rules with given Values
      | RANK      | 25            |
      | TASK TYPE | General Three |
      And I validate the Inbound Correspondence Definition update success message
      And I validate the Task Rank in Ascending Order
      And I validate Inbound Correspondence Definition Task Rules displayed in UI
      | TASK TYPE | General Two SR |


      @CP-35191 @CP-4429  @CP-35191-5.1 @ui-ecms2 @Keerthi
      Scenario:Verify Delete Task Type Rule in add mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I click add button on Inbound Correspondence Definition
      Then I validate and click Add Rule button on Inbound Correspondence Definition
      And I validate new empty row for Add Task Behavior
      And I validate Delete Task Type Rule

      @CP-35191 @CP-4429 @CP-37012  @CP-35191-5.2 @ui-ecms2 @Keerthi
      Scenario: Verify Deleting multiple Task Type Rules in Edit mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I view "first" IB Correspondence Definition type
      And I validate Edit Definition button removed from Inbound Correspondence Definition details screen
      Then I validate and click Add Rule button on Inbound Correspondence Definition
      Then I validate and click Add Rule button on Inbound Correspondence Definition
      And I validate Delete all Task Type Rules

      @CP-35191 @CP-4429  @CP-35191-6.1 @ui-ecms2 @Keerthi
      Scenario: Verify Save Task Type Rule in add mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I add a new Inbound Correspondence Definition with Random Values
      And I add a new Inbound Correspondence Definition Task Rules with given Values
      | RANK      | 25             |
      | TASK TYPE | General Two SR |
      And I validate the Inbound Correspondence Definition update success message
      And I validate Inbound Correspondence Definition Task Rules displayed in UI
      | RANK      | 25             |
      | TASK TYPE | General Two SR |

      @CP-35191 @CP-4429 @CP-37012 @CP-35191-6.2 @ui-ecms2 @Keerthi
      Scenario: Verify Save Task Type Rule in edit mode of IB correspondence Definition
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I view "first" IB Correspondence Definition type
      And I validate Delete all Task Type Rules
      And I validate Edit Definition button removed from Inbound Correspondence Definition details screen
      And I add a new Inbound Correspondence Definition Task Rules with given Values
      | RANK                                        | 2                                                                            |
      | TASK TYPE                                   | General Two SR                                                               |
      | CREATE ONLY ONE TASK PER SET                | true                                                                         |
      | REQUIRED DATA ELEMENTS                      | Case ID,Consumer ID,Application ID                                           |
      | SUPPRESS IF SET HAS DOCUMENT OF THESE TYPES | maersk Case + Consumer Test,maersk Case + Consumer Requiring Metadata Test |
      And I validate the Inbound Correspondence Definition update success message
      And I validate Inbound Correspondence Definition Task Rules displayed in UI
      | RANK                                        | 2                                                                            |
      | TASK TYPE                                   | General Two SR                                                               |
      | CREATE ONLY ONE TASK PER SET                | true                                                                         |
      | REQUIRED DATA ELEMENTS                      | Case ID,Consumer ID,Application ID                                           |
      | SUPPRESS IF SET HAS DOCUMENT OF THESE TYPES | maersk Case + Consumer Test,maersk Case + Consumer Requiring Metadata Test |


      @CP-35191 @CP-4429  @CP-35191-7.1 @ui-ecms2 @Keerthi
      Scenario: Verify Cancel without Save Task Type Rule
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I click add button on Inbound Correspondence Definition
      Then I validate and click Add Rule button on Inbound Correspondence Definition
      And I try to exit without Save Task Type Rule
      And I should see a warning message allowing to cancel or discard changes
      And I click "Cancel" when given the option to confirm cancel or continue in warning popup
      Then I verify I navigate to Inbound Correspondence Definition Details Screen

      @CP-35191 @CP-4429  @CP-35191-7.2 @ui-ecms2 @Keerthi
      Scenario: Verify Continue without Save Task Type Rule
      Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
      When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
      And I click add button on Inbound Correspondence Definition
      Then I validate and click Add Rule button on Inbound Correspondence Definition
      And I try to exit without Save Task Type Rule
      And I should see a warning message allowing to cancel or discard changes
      And I click "Continue" when given the option to confirm cancel or continue in warning popup
      Then I verify I navigate to Inbound Correspondence Definition Screen