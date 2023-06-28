# This feature file has test cases for Task Template functionality

Feature: Tenant Manager - Task Template Functionality

  @CRM-1400 @CRM-1400-01 @vinuta @tm-regression @ui-tm
  Scenario: Verify task template name field specifications
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    Then I verify template name field is present & mandatory
    And Template name accepts 50 alphanumeric & special characters

  @CRM-1400 @CRM-1400-02 @vinuta @tm-regression @ui-tm
  Scenario: Verify task template description field specifications
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    Then Template description accepts 150 alphanumeric characters
    And I verify template description field is optional

  @CRM-1400 @CRM-1400-03 @CP-964 @CP-964-01 @vinuta @tm-regression @ui-tm
  Scenario: Verify default fields within 'Selected Fields' section
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    Then I verify default fields in selected fields sections


  @CRM-1400 @CRM-1400-04 @CP-964 @CP-964-02 @CP-10933 @CP-10933-01 @CP-11417 @CP-12430 @CP-11417-01 @CP-28281 @CP-28281-01 @CP-27300 @CP-27300-01 @CP-27517 @CP-27517-01 @CP-23248 @CP-23248-01 @CP-26631 @CP-26631-01 @CP-27490 @CP-27490-01 @priyal @vinuta @tm-regression @ui-tm
  Scenario: Verify additional fields within 'Additional Fields' section
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    Then I verify additional fields in all fields sections

  @CRM-1400 @CRM-1400-05 @vinuta @tm-regression @ui-tm @tm-smoke
  Scenario: Verify success message is displayed on saving task template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "RANDOM" template description as "RANDOM"
    And I click on save task template button
    Then Success message is displayed on task template list page

  @CRM-1400 @CRM-1400-06 @vinuta @tm-regression @ui-tm
  Scenario: Verify warning message is displayed on cancelling task template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "RANDOM" template description as "RANDOM"
    And I click on cancel task template button
    Then Warning message is displayed on task template details page
    And I click on cancel button on task template warning message
    When I click on cancel task template button
    And I click on continue button on task template warning message
    Then I verify task template list is displayed

  @CRM-1400 @CRM-1400-07 @vinuta @tm-regression @ui-tm
  Scenario: Verify warning message is displayed on clicking the back arrow on task template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "RANDOM" template description as "RANDOM"
    And I click on back arrow on task template button
    Then Warning message is displayed on task template details page
    And I click on cancel button on task template warning message
    When I click on back arrow on task template button
    And I click on continue button on task template warning message
    Then I verify task template list is displayed

  @CRM-1400 @CRM-1400-08 @vinuta @tm-regression @ui-tm
  Scenario: Verify optional fields can be moved to selected fields & vice versa
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I select fields from All Fields section
      | Action Taken      |
      | Notification ID   |
      | Outreach Location |
      | Provider Phone    |
    And I click on Add Fields button
    Then fields are removed from all fields and added to selected fields section
      | Action Taken      |
      | Notification ID   |
      | Outreach Location |
      | Provider Phone    |
    When I select fields from Selected Fields section
      | Action Taken      |
      | Notification ID   |
      | Outreach Location |
      | Provider Phone    |
    When I click on Remove Fields button
    Then fields are removed from selected fields and added to all fields section
      | Action Taken      |
      | Notification ID   |
      | Outreach Location |
      | Provider Phone    |

  @CRM-1401 @CRM-1401-01 @CP-3912 @CP-3912-01 @vinuta @tm_regression @tm-smoke @ui-tm
  Scenario: Verify the fields displayed on Task Template Details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I select fields from All Fields section
      | Action Taken      |
      | Notification ID   |
      | Outreach Location |
      | Provider Phone    |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page
    When I select template "random" on Task Template List page
    Then I verify template details are displayed as expected
      | Action Taken      |
      | Notification ID   |
      | Outreach Location |
      | Provider Phone    |

  @CRM-1402 @CRM-1402-01 @vinuta @tm_regression @ui-tm
  Scenario: Verify the attributes displayed on Task Template List are correct
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "RANDOM" template description as "RANDOM"
    And I click on save task template button
    And Success message is displayed on task template list page
    Then I verify attributes on the task template list are correct

  @CRM-1402 @CRM-1402-02 @vinuta @tm_regression @ui-tm
  Scenario: Verify task types are displayed on expanding task template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I expand the task template "General"
    Then I verify task types are displayed on expanding task template

  @CRM-1401 @CRM-1401-02 @vinuta @tm_regression @ui-tm
  Scenario: Verify clone template optin is displayed in Template Details Page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I click on save task template button
    Then Success message is displayed on task template list page
    When I select template "random" on Task Template List page
    Then I verify Clone option is displayed to clone Task Template


  @CP-964 @CP-964-03 @CP-17827 @CP-17827-01 @paramita @tm-regression @ui-tm
  Scenario:Verify Grouped fields get selected and de-selected on selecting a particular field  in create Task template screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I select fields from All Fields section
      | Case Worker First Name  |
      | New Address Line 1      |
      | Old Address Line 1      |
      | Provider Address Line 1 |
      | Provider First Name     |
      | First Name     |
      | Address Line 1 |
    Then I verify all other grouped fields get selected
    When I click on any selected Grouped fields,all other selected grouped fields get deselected
      | Case Worker Last Name   |
      | New Address Line 2      |
      | Old Address Line 2      |
      | Provider Address Line 2 |
      | Provider Last Name      |
      | Last Name      |
      | Address Line 2 |
    Then I verify all grouped fields get de-selected

#  @CP-964 @CP-964-04 @paramita @tm-regression @ui-tm     Functionlity change
  Scenario:Verify Unique Task Template Name with different version name
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    Then I create a task template with same name with template description and different version

  #@CP-10933 @CP-10933-02 @Basha @tm-regression @ui-tm @ui-tm
  #Covered in 10933-03 above script
  Scenario: Verify updates to optional fields on Task Template Details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I select fields from All Fields section
      | External Application ID |
      | Name                    |
      | Reason                  |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page

  @CP-10933 @CP-10933-03 @Basha @tm-regression @ui-tm
  Scenario: Verify view Task Template new fields are captured
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I select fields from All Fields section
      | External Application ID |
      | Name                    |
      | Reason                  |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page
    When I select template "random" on Task Template List page
    Then I verify template fields are displayed as expected
      | External Application ID |
      | Name                    |
      | Reason                  |

  #@CP-11417 @CP-11417-02 @Basha @tm-regression @ui-tm @ui-tm
  #covered in 11417-03
  Scenario: Verify updates to optional fields Complaint About External Case ID on Task Template Details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I select fields from All Fields section
      | Complaint About  |
      | External Case ID |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page

  @CP-11417 @CP-11417-03 @Basha @tm-regression @ui-tm @ui-tm
  Scenario: Verify view Task Template fields Complaint About External Case ID are captured
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I select fields from All Fields section
      | Complaint About  |
      | External Case ID |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page
    When I select template "random" on Task Template List page
    Then I verify template fields are displayed as expected
      | Complaint About  |
      | External Case ID |

  @CP-14373 @CP-14373-01 @umid @tm-regression @ui-tm
  Scenario: Verify Add fields to Task Template Application ID field Save View Template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I select fields from All Fields section
      | Application ID |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page
    When I select template "random" on Task Template List page
    Then I verify template fields are displayed as expected
      | Application ID |

  @CP-9076 @CP-9076-01 @basha @tm-regression @ui-tm
  Scenario:  Validate the Inquiry/Complaint Task Template  configuration
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    When I navigate to Task Template Page
    When I select template "Inquiry/Complaint" on Task Template List page
    Then I verify template fields are displayed as expected
      | Channel         |
      | Complaint       |
      | Disposition     |
      | From Email      |
      | From Phone      |
      | Message         |
      | Message Subject |

  @CP-12430 @CP-18804 @CP-18646 @CP-18818 @CP-19424 @tm-regression @ui-tm @vinuta
  Scenario: Verify new optional fields for CoverVA on add & view task template pages
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I select fields from All Fields section
      | Complaint Type                    |
      | Complaint Sub-Type                |
      | Action Taken Single               |
      | Application Received Date         |
      | Application Signature Date        |
      | Application Status                |
      | Application Type                  |
      | ConnectionPoint Contact Record ID |
      | ConnectionPoint SR ID             |
      | ConnectionPoint Task ID           |
      | Date Resent                       |
      | Decision Source                   |
      | Issue Type                        |
      | Pre-Hearing Outcome               |
      | Pre-Hearing Reason                |
      | Request Type                      |
      | Received Date                     |
      | Document Due Date                 |
      | Expedited                         |
      | Transferred Date                  |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page
    When I select template "random" on Task Template page
    Then I verify template fields are displayed as expected
      | Complaint Type                    |
      | Complaint Sub-Type                |
      | Action Taken Single               |
      | Application Received Date         |
      | Application Signature Date        |
      | Application Status                |
      | Application Type                  |
      | ConnectionPoint Contact Record ID |
      | ConnectionPoint SR ID             |
      | ConnectionPoint Task ID           |
      | Date Resent                       |
      | Decision Source                   |
      | Issue Type                        |
      | Pre-Hearing Outcome               |
      | Pre-Hearing Reason                |
      | Request Type                      |
      | Received Date                     |
      | Document Due Date                 |
      | Expedited                         |
      | Transferred Date                  |

  @CP-17827 @CP-17827-02 @vinuta @tm-regression @ui-tm
  Scenario:Verify grouped fields are added automatically if any one field is selected for CoverVA
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    When I select fields from All Fields section
      | First Name   |
      | Address City |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page
    When I select template "random" on Task Template page
    Then I verify template fields are displayed as expected
      | First Name       |
      | Last Name        |
      | Address Line 1   |
      | Address Line 2   |
      | Address City     |
      | Address State    |
      | Address Zip Code |

  @CP-24818 @CP-23604 @tm-regression @ui-tm @vinuta
  Scenario: Verify new optional fields for IN-EB on add & view task template pages
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I select fields from All Fields section
      | Date of Voicemail                  |
      | Time of Voicemail                  |
      | Start Date                         |
      | Address Type                       |
      | Address Source                     |
      | Caller Name                        |
      | CSR Name                           |
      | Current Plan                       |
      | Date Decision Letter Sent          |
      | Date Follow-up Email Sent          |
      | Date State Notified                |
      | Disenrollment Date                 |
      | Disenrollment Reason               |
      | End Date                           |
      | Escalated                          |
      | Escalation Reason                  |
      | Final Decision                     |
      | Grievance #                        |
      | Incident Date                      |
      | Invalid                            |
      | Other                              |
      | RID #                              |
      | Used Tobacco in the last 6 months? |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page
    When I select template "random" on Task Template page
    Then I verify template fields are displayed as expected
      | Date of Voicemail                  |
      | Time of Voicemail                  |
      | Start Date                         |
      | Address Type                       |
      | Address Source                     |
      | Caller Name                        |
      | CSR Name                           |
      | Current Plan                       |
      | Date Decision Letter Sent          |
      | Date Follow-up Email Sent          |
      | Date State Notified                |
      | Disenrollment Date                 |
      | Disenrollment Reason               |
      | End Date                           |
      | Escalated                          |
      | Escalation Reason                  |
      | Final Decision                     |
      | Grievance #                        |
      | Incident Date                      |
      | Invalid                            |
      | Other                              |
      | RID #                              |
      | Used Tobacco in the last 6 months? |

  @CP-24108 @tm-regression @ui-tm @vinuta
  Scenario: Verify new optional fields for IN-EB on add & view task template pages
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I select fields from All Fields section
      | Appeal Source                |
      | Conference ID                |
      | Consumer Satisfied?          |
      | CoverVA Attendance           |
      | Date Appeal Closed           |
      | Date DMAS Review Started     |
      | Date Health Plan Contacted   |
      | Date Ready for DMAS Review   |
      | Date Grievance Received      |
      | Date Sent to VCCC            |
      | Date Summary Accepted        |
      | Date Summary Returned to CVA |
      | Date Summary Sent            |
      | Decision in DMIS Date        |
      | Hearing Officer Name         |
      | Invalid Reason               |
      | Member Attendance            |
      | Phone Number Extension       |
      | Requested Date               |
      | Summary in DMIS Date         |
      | VCCC Response Date           |
      | VCCC Response                |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page
    When I select template "random" on Task Template page
    Then I verify template fields are displayed as expected
      | Appeal Source                |
      | Conference ID                |
      | Consumer Satisfied?          |
      | CoverVA Attendance           |
      | Date Appeal Closed           |
      | Date DMAS Review Started     |
      | Date Health Plan Contacted   |
      | Date Ready for DMAS Review   |
      | Date Grievance Received      |
      | Date Sent to VCCC            |
      | Date Summary Accepted        |
      | Date Summary Returned to CVA |
      | Date Summary Sent            |
      | Decision in DMIS Date        |
      | Due Date                     |
      | Hearing Officer Name         |
      | Invalid Reason               |
      | Member Attendance            |
      | Phone Number Extension       |
      | Requested Date               |
      | Summary in DMIS Date         |
      | VCCC Response Date           |
      | VCCC Response                |

  @CP-8931-01 @CP-8931 @tm-regression @ui-tm @albert
  Scenario Outline: Add/Remove Task Template fields
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Template Page
    When I click on add new task template button
    Then I will see the following "<Fields>"
    Examples:
      | Fields |
      | CP8931 |

  @CP-8931-02 @CP-8931 @tm-regression @ui-tm @albert
  Scenario Outline: Systematically Select Grouped Fields
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Template Page
    And I click on add new task template button
    And I select to include a field in a defined "<Group>"
    Then I see the other fields in the "<Group>" are systematically selected
    Examples:
      | Group        |
      | Site         |
      | Site Contact |

  @CP-8931-03 @CP-8931 @tm-regression @ui-tm @albert
  Scenario Outline: Save
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Template Page
    And I click on add new task template button
    And I add "<Fields>"
    When I populate template name as "random" template description as "random"
    And I click on save task template button
    Then I see the success message for saving the Template
    Examples:
      | Fields |
      | CP8931 |

  @CP-8931-04 @CP-8931 @tm-regression @ui-tm @albert
  Scenario Outline: View Template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Template Page
    And I click on add new task template button
    And I add "<Fields>"
    When I populate template name
    And I click on save task template button
    Then I see the success message for saving the Template
    And I see the "<Fields>" are successfully saved as part of the Task Template
    Examples:
      | Fields |
      | CP8931 |

  @CP-18822 @CP-18798 @CP-19240 @CP-22872 @CP-22932 @tm-regression @ui-tm @vinuta @tm-smoke
  Scenario:  View saved template has new optional fields added
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Template Page
    And I click on add new task template button
    When I populate template name as "random" template description as "random"
    And I select fields from All Fields section
      | Hearing Officer Name          |
      | Accessibility Needed          |
      | Remand Reason                 |
      | Remand Due Date               |
      | Remand Completion Date        |
      | Eligibility Decision          |
      | No Of Approved Applicants     |
      | No Of Applicants in Household |
      | Denial Reason                 |
      | Outcome                       |
      | MI Received Date              |
      | VCL Sent Date                 |
    And I click on Add Fields button
    And I click on save task template button
    Then Success message is displayed on task template list page
    When I select template "random" on Task Template List page
    Then I verify template has following optional fields under selected fields
      | Hearing Officer Name          |
      | Accessibility Needed          |
      | Remand Reason                 |
      | Remand Due Date               |
      | Remand Completion Date        |
      | Eligibility Decision          |
      | No Of Approved Applicants     |
      | No Of Applicants in Household |
      | Denial Reason                 |
      | Outcome                       |
      | MI Received Date              |
      | VCL Sent Date                 |

  @CP-26281-01 @CP-26281 @tm_regression @ui-tm  @shruti
  Scenario: Verify error message is displayed when min and max fields are empty
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Template Page
    And I click on add new task template button
    And I click on Add field button on task template edit page
    When I select Field Type as "alphanumeric"
    Then I verify Min and Max character fields are "displayed"
    When I click save button on add field page
    Then I verify error message for "min characters" field is displayed as "Min Characters is required"
    Then I verify error message for "max characters" field is displayed as "Max Characters is required"

  @CP-26281-02 @CP-26281 @tm_regression @ui-tm  @shruti
  Scenario: Verify edit template dropdown values for service and table
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    And I navigate to Task Template Page
    When I select template "All fields" on Task Template List page
    And I click on Edit button on task template view page
    And I click on Add field button on task template edit page
    When I select Field Type as "Dropdown"
    And  I verify Service and Table fields are "displayed"
    When I select service as "mars-contacts-blcrm"
    Then I select table as "ENUM_ADDRESS_TYPE"

  @CP-26281-03 @CP-26281 @tm_regression @ui-tm  @shruti
  Scenario: Verify edit template dropdown error message for service and table
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    And I navigate to Task Template Page
    When I select template "Address" on Task Template List page
    And I click on Edit button on task template view page
    And I click on Add field button on task template edit page
    When I select Field Type as "Dropdown"
    Then I verify Service and Table fields are "displayed"
    When I click save button on add field page
    Then I verify error message for "service" field is displayed as "Service is required and cannot be left blank."
    Then I verify error message for "table" field is displayed as "Table is required and cannot be left blank."


  @CP-25644 @CP-25644-01 @CP-25647 @mital @tm-regression @ui-tm
  Scenario: Add New Task Field in Task Template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    And I verify Add Field button is displayed and click it
    And I verify dropdown values in field type
    And I add new field and save it
    Then I verify newly added field has appreared in all fields sections
    Then I click on back arrow on task template button to return to create another template
    And I click on add new task template button
    Then I verify newly added field has appreared in all fields sections

  @CP-25644 @CP-25644-02 @mital @tm-regression @ui-tm
  Scenario: Add New Grouped Task Fields in Task Template
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    And I verify Add Field button is displayed and click it
    When I select Create grouped field
    Then I verify I am able to create multiple fields
    Then I verify newly added fields have appreared in all fields sections

  @CP-26280 @CP-26280-01 @mital @tm-regression @ui-tm
  Scenario: Verify if fieldKey is unique
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    And I verify Add Field button is displayed and click it
    And I add new field and save it
    Then I verify newly added field has appreared in all fields sections
    And I verify Add Field button is displayed and click it
    Then I add new field with same field key
    Then I verify if error message for field key is displayed

  @CP-26280 @CP-26280-02 @mital @tm-regression @ui-tm
  Scenario: Verify if fieldname is unique
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    And I verify Add Field button is displayed and click it
    And I add new field and save it
    Then I verify newly added field has appreared in all fields sections
    And I verify Add Field button is displayed and click it
    Then I add new field with same field name
    Then I verify if error message for field name is displayed

  @CP-29533 @CP-29533-01 @CP-38913 @mital @tm-regression @ui-tm
  Scenario: Verify default Task Field options for Templates
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I select fields from All Fields section
      | Action Taken      |
    And I click on Add Fields button
    Then fields are removed from all fields and added to selected fields section
      | Action Taken      |
    Then I click on edit field button for "Action Taken"
    And I verify To Create Task field is by default selected with "optional" value
    And I verify To Complete Task field is by default selected with "optional" value

  @CP-27953 @CP-27953-01 @CP-27953-02 @CP-27953-03 @CP-40276 @mital @tm-regression @ui-tm
  Scenario Outline: 1.Verify user is able to choose whether the field is required, optional, or conditional to Create the Task
            2.Verify user is able to choose whether the field is required, optional, or conditional to Complete the Task
            3.Verify user is able to choose whether the field appears on the task slider
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to Task Template Page
    And I click on add new task template button
    When I select fields from All Fields section
      | Case Status      |
    And I click on Add Fields button
    Then fields are removed from all fields and added to selected fields section
      | Case Status      |
    Then I click on edit field button for "Case Status"
    And I can verify user is able to choose whether the field is "<option>" to Create the Task
    Then I verify the selected options are saved correctly for "<option>" to Create the Task
    Then I click on edit field button for "Case Status"
    And I can verify user is able to choose whether the field is "<option>" to Complete the Task
    Then I verify the selected options are saved correctly for "<option>" to Complete the Task
    Then I click on edit field button for "Case Status"
    And I can verify user is able to choose whether the field appears on the task slider
    Examples:
      | option      |
      | required    |
      | optional    |
      | conditional |
