Feature: Outbound Correspondence Definition

  @DMS-164-1 @DMS-164 @ECMS-SMOKE @ui-ecms1 @James
  Scenario: Verify ID is 10 or less characters displayed as hyperlink
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I have at least 1 Outbound Correspondence Definition with 10 characters for Id
    Then I should see ID is displayed as a hyperlink

  @DMS-164-2 @DMS-164 @CP-31482 @CP-31482-01 @ui-ecms1 @James
  Scenario: Verify that Correspondence Name is 40 characters displayed and accepts Special Characters
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I have at least 1 Outbound Correspondence Definition with 40 characters for Name
    Then I should see Correspondence Name is 40 characters displayed

  @DMS-164-3 @DMS-164 @ui-ecms1 @James
  Scenario: Verify if Start Date is on or before today and End Date is on or after today or blank Status is displayed "ACTIVE"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I have at least 1 Outbound Correspondence Definition with Start Date is on or before today and End Date is on current date
    Then I should see the Outbound Correspondence Definition status displayed as "ACTIVE"

  @DMS-164-4 @DMS-164 @ui-ecms1 @James
  Scenario: Verify if End Date is earlier than today Status is displayed "INACTIVE"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I have an Outbound Correspondence Definition with End Date is earlier than today in project: "current"
    When I find the previously created Outbound Correspondence Definition
    Then I should see the Outbound Correspondence Definition status displayed as "INACTIVE"

  @DMS-164-5 @DMS-164 @ui-ecms1 @James
  Scenario: Verify if Start Date is later than today Status is displayed "FUTURE"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I have at least 1 Outbound Correspondence Definition with Start Date later than today
    Then I should see the Outbound Correspondence Definition status displayed as "FUTURE"

  @DMS-164-6 @DMS-164 @ECMS-SMOKE @ui-ecms1 @James
  Scenario Outline: Verify that the list can be sorted by each column; ID, Name, Status
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I have at least 3 Outbound Correspondence Definition
    When I sort by the "<Column>"
    Then I should see the Outbound Correspondence Definition records are sorted by that "<Column>"
    Examples:
      | Column |
      | ID     |
      | Name   |
      | Status |

  @DMS-164-7 @DMS-164 @ECMS-SMOKE @ui-ecms1 @James
  Scenario: Verify that the list is initally sorted by ID in ascending order
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I have at least 3 Outbound Correspondence Definition
    Then I should see the Outbound Correspondence Definition records are sorted by that "ID"

  @DMS-164-8 @DMS-164 @ui-ecms1 @ECMS-SMOKE @James
  Scenario: Verify that I am able to select the Correspondence definition by clicking on the ID hyperlink
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I have at least 1 Outbound Correspondence Definition
    When I click on the "ID" hyperlink
    Then I should see the Correspondence Definition details displayed

  @DMS-164-9 @DMS-164 @ECMS-SMOKE @ui-ecms1 @James
  Scenario: Verify that I have the option to add a Correspondence definition
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Correspondence Definitions Screen of Project:"current"
    Then I should see the add an Outbound Correspondence Definition button

  @DMS-164-10 @DMS-164 @ECMS-SMOKE @ui-ecms1 @James
  Scenario: Verify I can view at least 10 Outbound Correspondence Records on the screen
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I have at least 10 Outbound Correspondence Definition
    Then I should see the 10 Correspondence Definition details displayed

  @CP-36743 @CP-36743-01 @ui-ecms1 @burak
  Scenario: Verify Outbound Correspondence Definition Header Displays as Outbound Correspondence Definition
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I verify Outbound Correspondence Definition Header Displays as Outbound Correspondence Definition


  @CP-37014 @CP-37706 @CP-37014-01 @CP-37014-02 @CP-37706-01 @CP-37706-02 @API-ECMS @Keerthi
  Scenario Outline: validate documentId,templateSystem element as string in POST/GET API channeldefinition request and response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have random valid data for an Outbound Correspondence Definition
    When I send the Outbound Correspondence Definition to the server for project: "current" with random valid data and Body Data Structure
    And I will update Outbound Correspondence Definition with following values
      | correspondenceDefId | previouslycreated |
      | documentId          | random            |
      | templateSystem      | <TemplateSystem>  |
      | channel             | Email             |
    Then I verify the Outbound Correspondence Channel definition post response body with following values
      | documentId     | random |
      | templateSystem | ONBASE |
    When I have a request to retrieve an Outbound Correspondence Channel Definition by correspondenceId "previouslycreated"
    Then I verify the Outbound Correspondence Channel definition get response body with following values
      | documentId     | random           |
      | templateSystem | <TemplateSystem> |
    Examples:
      | projectName | TemplateSystem |
      | BLCRM       | ONBASE         |
      | DC-EB       | ONBASE         |

    ############################################# CP-37275 #########################################

  @CP-37275 @CP-37275-01 @ui-ecms2 @Keerthi
  Scenario: Validate Add Template button options
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "validate" Add Template button options
      | External Template ID |
      | Upload Template File |


  @CP-37275 @CP-37275-02 @ui-ecms2 @Keerthi
  Scenario: Validate Upload Template File option
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | Upload Template File |
    Then I select "MultiPageDocumenMail.pdf" file for upload and validate add template

  @CP-37275 @CP-37275-03 @ui-ecms2 @Keerthi
  Scenario: Validate Template File Name,System,External ID fields after uploading Template File
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I have active languages for the project "BLCRM"
    Then I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
   # When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | Upload Template File |
    Then I select "MultiPageDocumenMail.pdf" file for upload and validate add template
    Then I validate following fields in OB channel definition Templates grid
      | Template File Name | 1234MultiPageDocumenMail.pdf |
      | System             | CP                           |
      | External ID        | -- --                        |
      | Version            | 1                            |
      | LanguageDropdown   | default                      |

  @CP-37275 @CP-37275-04 @ui-ecms2 @Keerthi
  Scenario: Validate External Template ID option
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | External Template ID |
    Then I "validate" following fields in OB channel definition Templates grid for External Template ID option
      | External ID | Random             |
      | System      | SendGrid,Twilio,CP |
      | Add         | enable             |
      | Cancel      | enable             |

  @CP-37275 @CP-37275-5.1 @ui-ecms2 @Keerthi
  Scenario: Validate External Template ID save option
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel
    And I "click" Add Template button options
      | External Template ID |
    Then I "select" following fields in OB channel definition Templates grid for External Template ID option
      | External ID | 12345    |
      | System      | SendGrid |
      | Add         | enable   |
    Then I select following fields in OB channel definition Templates grid
      | Language  | English                     |
      | StartDate | Current_SysDatePlusOneMonth |
      | EndDate   | Current_SysDatePlusOneMonth |
    Then I validate following fields in OB channel definition Templates grid
      | Template File Name |          |
      | System             | SENDGRID |
      | External ID        | 12345    |
      | Version            | 1        |
      | Language           | English  |
    And the Correspondence Definition Channel should see the system display time stamp as Updated On plus User Id as Updated By

  @CP-37275 @CP-37275-5.2 @ui-ecms2 @Keerthi
  Scenario: Validate Upload Template File save option
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel
    And I "click" Add Template button options
      | Upload Template File |
    Then I select "MultiPageDocumenMail.pdf" file for upload and validate add template
    Then I select following fields in OB channel definition Templates grid
      | Language  | English                     |
      | StartDate | Current_SysDatePlusOneMonth |
      | EndDate   | Current_SysDatePlusOneMonth |
    Then I validate following fields in OB channel definition Templates grid
      | Template File Name | 1234MultiPageDocumenMail.pdf |
      | System             | CP                           |
      | External ID        | Random                       |
      | Version            | 1                            |
      | Language           | English                      |

    #####################################   CP-37320  ###############################################
  @CP-37320 @CP-37320-01 @ui-ecms2 @Keerthi
  Scenario: Validate Add button for External Template ID option
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I have active languages for the project "BLCRM"
    Then I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    #When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | External Template ID |
    Then I "select" following fields in OB channel definition Templates grid for External Template ID option
      | External ID | 12345    |
      | System      | SendGrid |
      | Add         | enable   |
    Then I validate following fields in OB channel definition Templates grid
      | Template File Name |          |
      | System             | SENDGRID |
      | External ID        | 12345    |
      | Version            | 1        |
      | LanguageDropdown   | default  |


  @CP-37320 @CP-37320-02 @ui-ecms2 @Keerthi
  Scenario: Validate Required fields for External Template ID option
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | External Template ID |
    Then I validate Required fields for External Template ID option

  @CP-37320 @CP-37320-3.1 @ui-ecms2 @Keerthi
  Scenario: Verify attempting to edit a channel template with a Start Date to a date before the current date will bring up the error message
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel
    And I "click" Add Template button options
      | External Template ID |
    Then I "select" following fields in OB channel definition Templates grid for External Template ID option
      | External ID | 12345    |
      | System      | SENDGRID |
      | Add         | enable   |
    Then I select following fields in OB channel definition Templates grid
      | StartDate | Current_SysDateMinusOneMonth |
    Then I should the save fail with error message "START DATE cannot be changed to a date before today."

  @CP-37320 @CP-37320-03.2 @ui-ecms2 @Keerthi
  Scenario: Verify attempting to edit a channel template with a End Date earlier than the Start Date will bring up the error message
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel
    And I "click" Add Template button options
      | External Template ID |
    Then I "select" following fields in OB channel definition Templates grid for External Template ID option
      | External ID | 12345    |
      | System      | SENDGRID |
      | Add         | enable   |
    Then I select following fields in OB channel definition Templates grid
      | EndDate | Current_SysDateMinusOneMonth |
    Then I should see the save fail with the message "END DATE cannot be earlier than START DATE"

  @CP-37320 @CP-37320-3.3 @ui-ecms2 @Keerthi
  Scenario: Validate version field increases numerically and able to save with same externalid
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with a Channel
    And I "click" Add Template button options
      | External Template ID |
    Then I "select" following fields in OB channel definition Templates grid for External Template ID option
      | External ID | 12345    |
      | System      | SENDGRID |
      | Add         | enable   |
    Then I select following fields in OB channel definition Templates grid
      | Language  | English                    |
      | StartDate | Current_SysDatePlusOneWeek |
      | EndDate   | Current_SysDatePlusOneWeek |
    Then I validate following fields in OB channel definition Templates grid
      | Template File Name |          |
      | System             | SENDGRID |
      | External ID        | 12345    |
      | Version            | 1        |
      | Language           | English  |
    And the Correspondence Definition Channel should see the system display time stamp as Updated On plus User Id as Updated By
    And I "click" Add Template button options
      | External Template ID |
    Then I "select" following fields in OB channel definition Templates grid for External Template ID option
      | External ID | 12345  |
      | System      | Twilio |
      | Add         | enable |
    Then I select following fields in OB channel definition Templates grid
      | Language  | English                     |
      | StartDate | Current_SysDatePlusOneMonth |
      | EndDate   | Current_SysDatePlusOneMonth |
    Then I validate following fields in OB channel definition Templates grid
      | Template File Name |         |
      | System             | TWILIO  |
      | External ID        | 12345   |
      | Version            | 2       |
      | Language           | English |
    And the Correspondence Definition Channel should see the system display time stamp as Updated On plus User Id as Updated By



