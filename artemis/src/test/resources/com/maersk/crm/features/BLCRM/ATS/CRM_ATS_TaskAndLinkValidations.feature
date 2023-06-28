Feature: ATS Task and Link Validations

  @CP-19127 @CP-19127-01 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Member Matching Task when application is created from inbound correspondence call
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I have prepared default sendevent request data for ATS from file "events/ATSSendEvent.json"
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      |
      | documentId     | today: yyyy-MM-dd |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | Mailing is Same As Residence Address | SetId          |
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | checkbox: NO                         | documentId one |
    And I have prepared keywordRecord Primary Individual member dynamic data values with "M"
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "Inbound Correspondence"
    And I click on search button on task search page
    And I verified that task type is "Member Matching"
    Then I click on initiate button in task search page
    Then Verify PDF viewer is initiated for linked Correspondence ID
    Then I verified that user is navigated to "Member Matching" page
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify task save success message

  @CP-19127 @CP-19127-02 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Member Matching Task when application is created from API call
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    And I verified that task type is "Member Matching"
    Then I click on initiate button in task search page
    And Verify PDF viewer is not initiated
    Then I verified that user is navigated to "Member Matching" page
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify task save success message

  @CP-19127 @CP-19127-03 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Member Matching Task when application is created from Create Task Page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Member Matching" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-19127-03 |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify PDF viewer is not initiated
    Then I verified that user is navigated to "View Task" page
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify task save success message

  @CP-21572 @CP-21572-01 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Review Incomplete Application Task when application is created from inbound correspondence call
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I have prepared default sendevent request data for ATS from file "events/ATSSendEvent.json"
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      |
      | documentId     | today: yyyy-MM-dd |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | Mailing is Same As Residence Address | SetId          |
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | checkbox: NO                         | documentId one |
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "Inbound Correspondence"
    And I click on search button on task search page
    And I verified that task type is "Review Incomplete Application"
    Then I click on initiate button in task search page
    Then Verify PDF viewer is initiated for linked Correspondence ID
    Then I verified that user is navigated to "Application" page
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify task save success message

  @CP-21572 @CP-21572-02 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Review Incomplete Application Task when application is created from API call
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    And I verified that task type is "Review Incomplete Application"
    Then I click on initiate button in task search page
    And Verify PDF viewer is not initiated
    Then I verified that user is navigated to "Application" page
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify task save success message

  @CP-21572 @CP-21572-03 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Review Incomplete Application Task when application is created from Create Task Page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Review Incomplete Application" task page
    And I will provide following information before creating task
      | taskInfo | Test CP-19127-03 |
    And I click on save button in create task page
    Then I verify Success message is displayed for task
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created task in Task & Service Request Tab
    And I click on initiate randomly
    And Verify PDF viewer is not initiated
    Then I verified that user is navigated to "View Task" page
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify task save success message

  @CP-25694 @CP-25694-01 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Refresh button and Links Component on Application Tracking Page for two objects
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I have prepared default sendevent request data for ATS from file "events/ATSSendEvent.json"
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      |
      | documentId     | today: yyyy-MM-dd |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | Mailing is Same As Residence Address | SetId          |
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | checkbox: NO                         | documentId one |
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter inbound correspondence created application id in the application search page
    When I click on search button in search application page
    And I click on the inbound correspondence search Id found in the result
    And I see application Status as "INSUFFICIENT" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Inbound Correspondence"
      | Task   | Review Incomplete Application |
      | Status | Created                       |
      | Source | Inbound Correspondence        |

  @CP-25694 @CP-25694-02 @crm-regression @ui-ats @ozgen
  Scenario: Verification of Refresh button and Links Component on Application Tracking Page for one objects
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Submitted" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Created         |
      | Source | API             |

  @CP-27147 @CP-27145 @CP-27147-01 @crm-regression @ui-ats @ozgen
  Scenario: Camunda Update: Auto Completion of Review Incomplete Application Task from API Call with Created Task Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "INSUFFICIENT" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Review Incomplete Application |
      | Status | Created                       |
      | Source | API                           |
    And I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5         |
      | LAST NAME  | Random 5         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
    And I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    And I click on member matching page back arrow to navigate to create application page
    And I verify "Submitted" application status in the top right corner in the Application details tab
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Review Incomplete Application |
      | Status | Complete                      |
      | Source | API                           |
    And I click id of "Task" in ATS Links section
      | Type | Review Incomplete Application |
    Then I verify action taken value as "Submitted" on View Task Page

  @CP-27147 @CP-27145 @CP-27147-02 @crm-regression @ui-ats @ozgen
  Scenario: Camunda Update: Auto Completion of Review Incomplete Application Task from ECMS Call with Escalated Task Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I have prepared default sendevent request data for ATS from file "events/ATSSendEvent.json"
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      |
      | documentId     | today: yyyy-MM-dd |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | Mailing is Same As Residence Address | SetId          |
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | checkbox: NO                         | documentId one |
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter inbound correspondence created application id in the application search page
    When I click on search button in search application page
    And I click on the inbound correspondence search Id found in the result
    And I see application Status as "INSUFFICIENT" in the application information
    And I verify refresh button is displayed and clicked on it
    And I click id of "Task" in ATS Links section
      | Type | Review Incomplete Application |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | reasonForEdit | Corrected Data Entry       |
      | status        | Escalated                  |
      | taskInfo      | CP-27145-02 edit task page |
    And I click on save button on task edit page
    And I  click on the BACK button
    Then I verify application links component has all the business object linked : "Task,Inbound Correspondence"
      | Task   | Review Incomplete Application |
      | Status | Escalated                     |
      | Source | ECMS                          |
    And I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5         |
      | LAST NAME  | Random 5         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
    And I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    And I click on member matching page back arrow to navigate to create application page
    And I verify "Submitted" application status in the top right corner in the Application details tab
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Inbound Correspondence"
      | Task   | Review Incomplete Application |
      | Status | Complete                      |
      | Source | ECMS                          |
    And I click id of "Task" in ATS Links section
      | Type | Review Incomplete Application |
    Then I verify action taken value as "Submitted" on View Task Page
    And Close the soft assertions

  @CP-27147 @CP-27145 @CP-27147-03 @crm-regression @ui-ats @ozgen
  Scenario: Camunda Update: Auto Completion of Review Incomplete Application Task from API Call with Onhold Task Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "INSUFFICIENT" in the application information
    And I click id of "Task" in ATS Links section
      | Type | Review Incomplete Application |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | OnHold                     |
      | assignee        | Service AccountOne         |
      | reasonForOnHold | State Directed             |
      | taskInfo        | CP-27145-03 edit task page |
    And I click on save button on task edit page
    And I  click on the BACK button
    Then I verify refresh button is displayed and clicked on it
    And I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5         |
      | LAST NAME  | Random 5         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
    And I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    And I click on member matching page back arrow to navigate to create application page
    And I verify "Submitted" application status in the top right corner in the Application details tab
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Review Incomplete Application |
      | Status | Complete                      |
      | Source | API                           |
    And I click id of "Task" in ATS Links section
      | Type | Review Incomplete Application |
    Then I verify action taken value as "Submitted" on View Task Page

  @CP-27147 @CP-27145 @CP-27147-04 @crm-regression @ui-ats @ozgen
  Scenario: Camunda Update: Auto Completion of Review Incomplete Application Task from ECMS Call with Cancelled Task Status (Negative Condition)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I have prepared default sendevent request data for ATS from file "events/ATSSendEvent.json"
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      |
      | documentId     | today: yyyy-MM-dd |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | Mailing is Same As Residence Address | SetId          |
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | checkbox: NO                         | documentId one |
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter inbound correspondence created application id in the application search page
    When I click on search button in search application page
    And I click on the inbound correspondence search Id found in the result
    And I see application Status as "INSUFFICIENT" in the application information
    And I click id of "Task" in ATS Links section
      | Type | Review Incomplete Application |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Cancelled                  |
      | taskInfo        | CP-27145-04 edit task page |
      | reasonForCancel | Created Incorrectly        |
    And I click on save button on task edit page
    And I  click on the BACK button
    Then I verify application links component has all the business object linked : "Task,Inbound Correspondence"
      | Task   | Review Incomplete Application |
      | Status | Cancelled                     |
      | Source | ECMS                          |
    And I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5         |
      | LAST NAME  | Random 5         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
    And I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    And I click on member matching page back arrow to navigate to create application page
    And I verify "Submitted" application status in the top right corner in the Application details tab
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Inbound Correspondence"
      | Task   | Review Incomplete Application |
      | Status | Cancelled                     |
      | Source | ECMS                          |
    And I click id of "Task" in ATS Links section
      | Type | Review Incomplete Application |
    And I verify task's status is Cancelled

  @CP-27148 @CP-27145 @CP-27148-01 @crm-regression @ui-ats-blats2 @ozgen
  Scenario: Camunda Update: Auto Completion of Member Matching Task from API Call with Created Task Status with New Action Taken
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Submitted" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Created         |
      | Source | API             |
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    When I click on the New button in the Application Member Matching Page
    And I see application Status as "Determining" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Complete        |
      | Source | API             |
    And I click id of "Task" in ATS Links section
      | Type | Member Matching |
    Then I verify action taken value as "New" on View Task Page

  @CP-27148 @CP-27145 @CP-27148-02 @crm-regression @ui-ats-blats2 @ozgen
  Scenario: Camunda Update: Auto Completion of Member Matching Task from API Call with Created Task Status with Duplicated Action Taken
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I POST ATS save submit application api and store appId and response in list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I POST ATS save submit application api and store appId and response in list
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I see application Status as "Submitted" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Created         |
      | Source | API             |
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on the "0" select box for matching application in Member Matching page
    And I store linked application id and status from "0" row
    When I click on the Duplicate button on Member Matching page & Continue
    And I see application Status as "Duplicate" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Application"
      | Task   | Member Matching |
      | Status | Complete        |
      | Source | API             |
    And I click id of "Task" in ATS Links section
      | Type | Member Matching |
    Then I verify action taken value as "Duplicate" on View Task Page

  @CP-27148 @CP-27145 @CP-27148-03 @crm-regression @ui-ats-blats2 @ozgen
  Scenario: Camunda Update: Auto Completion of Member Matching Task from API Call with Escalated Task Status with New Action Taken
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Submitted" in the application information
    And I click id of "Task" in ATS Links section
      | Type | Member Matching |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status   | Escalated                  |
      | taskInfo | CP-27148-03 edit task page |
    And I click on save button on task edit page
    And I  click on the BACK button
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Escalated       |
      | Source | API             |
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    When I click on the New button in the Application Member Matching Page
    And I see application Status as "Determining" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Complete        |
      | Source | API             |
    And I click id of "Task" in ATS Links section
      | Type | Member Matching |
    Then I verify action taken value as "New" on View Task Page

  @CP-27148 @CP-27145 @CP-27148-04 @crm-regression @ui-ats-blats2 @ozgen
  Scenario: Camunda Update: Auto Completion of Member Matching Task from API Call with New Action Taken for Cancelled Task Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Submitted" in the application information
    And I click id of "Task" in ATS Links section
      | Type | Member Matching |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Cancelled                  |
      | taskInfo        | CP-27148-04 edit task page |
      | reasonForCancel | Created Incorrectly        |
    And I click on save button on task edit page
    And I  click on the BACK button
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Cancelled       |
      | Source | API             |
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    When I click on the New button in the Application Member Matching Page
    And I see application Status as "Determining" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Cancelled       |
      | Source | API             |
    And I click id of "Task" in ATS Links section
      | Type | Member Matching |
    And I verify task's status is Cancelled

  @CP-27148 @CP-27145 @CP-27148-05 @crm-regression @ui-ats-blats2 @ozgen
  Scenario: Camunda Update: Auto Completion of Member Matching Task from API Call with Onhold Task Status with Duplicated Action Taken
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I POST ATS save submit application api and store appId and response in list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I POST ATS save submit application api and store appId and response in list
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I see application Status as "Submitted" in the application information
    And I click id of "Task" in ATS Links section
      | Type | Member Matching |
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | OnHold                     |
      | assignee        | Service AccountOne         |
      | reasonForOnHold | State Directed             |
      | taskInfo        | CP-27148-05 edit task page |
    And I click on save button on task edit page
    And I  click on the BACK button
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | OnHold          |
      | Source | API             |
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on the "0" select box for matching application in Member Matching page
    And I store linked application id and status from "0" row
    When I click on the Duplicate button on Member Matching page & Continue
    And I see application Status as "Duplicate" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Application"
      | Task   | Member Matching |
      | Status | Complete        |
      | Source | API             |
    And I click id of "Task" in ATS Links section
      | Type | Member Matching |
    Then I verify action taken value as "Duplicate" on View Task Page

  @CP-30325 @CP-30325-01 @crm-regression @ui-ats @burak
  Scenario: Initiate Member Matching Task from application tracking screen and Check for the Status Update Created / In-Progress / Cancelled
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Submitted" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Created         |
      | Source | API             |
    And I click initiate button on Links Panel
    When I click Continue button inside the warning message
    Then I verified that user is navigated to "Member Matching" page
    And I click on member matching page back arrow to navigate to create application page
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | In-Progress     |
      | Source | API             |
    And I will update the following information in task slider
      | status          | Cancel                  |
      | reasonForCancel | Task No Longer Required |
    And I click on save in Task Slider
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Cancelled       |
      | Source | API             |

  @CP-30325 @CP-30325-02 @crm-regression @ui-ats @burak
  Scenario: Initiate Member Matching Research Task application tracking screen and Check for the Status Update Created / In-Progress / Open
    #Verify User is able to Re-initiate Task
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Submitted" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I click on the Research button in the Application Member Matching Page
    And I click save for sending application to Research
    And I verify the Reason is required message is display on Member Matching Page
    And I select following reason for sending application to Research
      | Insufficient Information |
    And I click save for sending application to Research
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Created                  |
      | ResearchSource | UI                       |
      | Task           | Member Matching          |
      | Status         | Complete                 |
      | Source         | API                      |
    And I click initiate button on Links Panel
    Then I verified that user is navigated to "Member Matching" page
    And I click on member matching page back arrow to navigate to create application page
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify application links component has all the business object linked : "Task,Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | In-Progress              |
      | ResearchSource | UI                       |
      | Task           | Member Matching          |
      | Status         | Complete                 |
      | Source         | API                      |
    And I click on cancel button on task slider
    When I click Continue button inside the warning message
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Open                     |
      | ResearchSource | UI                       |
      | Task           | Member Matching          |
      | Status         | Complete                 |
      | Source         | API                      |

  @CP-30325 @CP-30325-03 @crm-regression @ui-ats @burak
  Scenario: Initiate Review Incomplete Application Task on  application tracking screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I have prepared default sendevent request data for ATS from file "events/ATSSendEvent.json"
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      |
      | documentId     | today: yyyy-MM-dd |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | Mailing is Same As Residence Address | SetId          |
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | checkbox: NO                         | documentId one |
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter inbound correspondence created application id in the application search page
    When I click on search button in search application page
    And I click on the inbound correspondence search Id found in the result
    And I see application Status as "INSUFFICIENT" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Inbound Correspondence"
      | Task   | Review Incomplete Application |
      | Status | Created                       |
      | Source | Inbound Correspondence        |
    And I click initiate button on Links Panel
    When I click Continue button inside the warning message
    Then I verified that user is navigated to "Application" page
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5         |
      | LAST NAME  | Random 5         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
    And I click on Save button on Create Application Page
    Then I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    And I click on member matching page back arrow to navigate to create application page
    And I verify "Submitted" application status in the top right corner in the Application details tab
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Inbound Correspondence"
      | Task   | Review Incomplete Application |
      | Status | Complete                      |
      | Source | Inbound Correspondence        |
    And I verify initiate button is not displayed on links component

  @CP-30325 @CP-30325-04 @crm-regression @ui-ats @burak
  Scenario:Verify User is unable to Initiate task in the links section of the Application tracking tab if the task is already assigned
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | MMTOA             | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Submitted" in the application information
    Then I verify refresh button is displayed and clicked on it
    And I click initiate button on Links Panel
    When I click Continue button inside the warning message
    Then I verified that user is navigated to "Member Matching" page
    And I click on save in Task Slider

  @CP-30325 @CP-30325-05 @crm-regression @ui-ats @burak
  Scenario:Verify User is unable to Initiate task in the links section of the Application tracking tab if the task is already assigned2
    Given I logged into CRM with "Service Account 8" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | FIRST NAME |
      | MMTOA      |
    When I click on search button in search application page
    And I click on first APPLICATION ID from search Results
    Then I verify refresh button is displayed and clicked on it
    And I verify initiate button is not displayed on links component


  @CP-34432 @crm-regression @ui-ats @Prithika
  Scenario: Remove member matching task delegate from the insufficient app flow-Medical Assistance
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "INSUFFICIENT" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Review Incomplete Application |
      | Status | Created                       |
      | Source | API                           |
    And I verify that links section does not include "Member matching" task
    And I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5         |
      | LAST NAME  | Random 5         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
    And I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    And I click on member matching page back arrow to navigate to create application page
    And I verify "Submitted" application status in the top right corner in the Application details tab
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    And I verify that links section does not include "Member matching" task

  @CP-27377 @CP-27377-01 @crm-regression @ui-ats @vinuta
  Scenario: View Application Header information within ‘Member Matching' task from Work Queue, My Task, Task Search & Links sections
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn        | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    When I logged into CRM
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I will get the index of "Member Matching" task and click on initiate button for that
    Then I verified that user is navigated to "Member Matching" page
    And I verify application header for "Member Matching" initiated applications
    And I click on cancel button on task slider
    And I click Continue button inside the warning message
    When I click on My task tab
    And I navigate to newly created task by clicking on TaskID column header
    And I will get the index of "Member Matching" task and click on initiate button for that
    Then I verified that user is navigated to "Member Matching" page
    And I verify application header for "Member Matching" initiated applications
    And I click on cancel button on task slider
    And I click Continue button inside the warning message
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Targets Unidentified" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Open            |
      | Source | API             |
    And I click initiate button on Links Panel
    Then I verified that user is navigated to "Member Matching" page
    And I verify application header for "Member Matching" initiated applications
    And I click on cancel button on task slider
    And I click Continue button inside the warning message
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    Then I click on initiate button in task search page
    Then I verified that user is navigated to "Member Matching" page
    And I verify application header for "Member Matching" initiated applications
    And I click on cancel button on task slider
    And I click Continue button inside the warning message

  @CP-27377 @CP-27377-02 @crm-regression @ui-ats @vinuta
  Scenario: View Application Header information within ‘Review Incomplete' task from Work Queue, My Task, Task Search & Links sections
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    When I logged into CRM
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Work Queue" page
    And I click task id to get the results in descending order
    And I will get the index of "Review Incomplete Application" task and click on initiate button for that
    Then I verified that user is navigated to "Application" page
    And I verify application header for "Review Incomplete Application" initiated applications
    And I click on cancel button on task slider
    And I click Continue button inside the warning message
    When I click on My task tab
    And I navigate to newly created task by clicking on TaskID column header
    And I will get the index of "Review Incomplete Application" task and click on initiate button for that
    Then I verified that user is navigated to "Application" page
    And I verify application header for "Review Incomplete Application" initiated applications
    And I click on application tracking tab to navigate to Application Tracking page
    And I verify application header for "Review Incomplete Application" initiated applications
    When I click on missing information tab to navigate to Missing Information page
    And I verify application header for "Review Incomplete Application" initiated applications
    And I click on cancel button on task slider
    And I click Continue button inside the warning message
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "INSUFFICIENT" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Review Incomplete Application |
      | Status | Open            |
      | Source | API             |
    And I click initiate button on Links Panel
    Then I verified that user is navigated to "Application" page
    And I verify application header for "Review Incomplete Application" initiated applications
    And I click on cancel button on task slider
    And I click Continue button inside the warning message
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    Then I click on initiate button in task search page
    Then I verified that user is navigated to "Application" page
    And I verify application header for "Review Incomplete Application" initiated applications
    And I click on cancel button on task slider
    And I click Continue button inside the warning message