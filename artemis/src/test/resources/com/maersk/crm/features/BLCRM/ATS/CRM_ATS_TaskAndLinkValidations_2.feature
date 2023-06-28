Feature: ATS Task and Other Objects Link Validations 2

  @CP-27353 @CP-27353-01 @crm-regression @ui-ats @burak
  Scenario: Verify Research Button , Options and Member Matching Research Task on Link Panel
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I click on the Research button in the Application Member Matching Page
    And I click save for sending application to Research
    And I verify the Reason is required message is display on Member Matching Page
    And I select following reason for sending application to Research
      | Insufficient Information |
      | Document Misclassified   |
      | Multiple Matches         |
    And I enter notes as "RANDOM 250" Characters for Send Application For Research Panel
    And I click save for sending application to Research
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Created                  |
      | ResearchSource | UI                       |

  @CP-27353 @CP-27353-04 @crm-regression @ui-ats @burak
  Scenario: Verify Member Matching Task Status is completed and Member Matching Research Task is created after sending application to Research on UI
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
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Created         |
      | Source | API             |
    And I click application id under Application Tracking tab in the Application Information panel
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I click on the Research button in the Application Member Matching Page
    And I select following reason for sending application to Research
      | Insufficient Information |
      | Document Misclassified   |
    And I enter notes as "RANDOM 250" Characters for Send Application For Research Panel
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

  @CP-20738 @CP-20738-01 @crm-regression @ui-ats-blats2 @ozgen
  Scenario: Camunda: Termination of flow for Duplicated status applications
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
    And I initiated bpm process get api for application data service
    And I run bpm process get api using application id
    Then I verify camunda process should be terminated

  @CP-23922 @CP-23922-01 @CP-23922-04 @crm-regression @ui-ats @burak
  Scenario: Verifying details of Correspondence and Task Links on Application Tracking Tab
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
    And I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Inbound Correspondence"
      | Task   | Review Incomplete Application |
      | Status | Created                       |
      | Source | ECMS                          |
    And I click id of "Inbound Correspondence" in ATS Links section
      | Type | maersk Application |
    Then I verify I navigated to "Inbound Correspondence Details" after clicking linked Entity

  @CP-23922 @CP-23922-02 @crm-regression @ui-ats @burak
  Scenario: Verifying pagination of Correspondence and Task Links on Application Tracking Tab
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
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
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
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | SetId          |
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | documentId one |
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter inbound correspondence created application id in the application search page
    When I click on search button in search application page
    And I click on the inbound correspondence search Id found in the result
    And I verify refresh button is displayed and clicked on it
    And I verify I see 5 linked entities on link panel

  @CP-23922 @CP-23922-03 @CP-19642 @CP-19642-01 @crm-regression @ui-ats @burak
  Scenario: Verifying user Navigates to Task Details Page after Clicking Test ID on Links Panel
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | Phone     |
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
    And I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Created         |
      | Source | API             |
    And I click id of "Task" in ATS Links section
      | Type | Member Matching |
    Then I verify I navigated to "TASK DETAILS" after clicking linked Entity
    And I verify read only fields for "Member Matching API" task
      | Channel | Phone |

  @CP-23922 @CP-23922-05 @crm-regression @ui-ats @burak
  Scenario: Verifying inbound correspondence image is opened in a new browser.
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
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | SetId          |
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | documentId one |
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter inbound correspondence created application id in the application search page
    When I click on search button in search application page
    And I click on the inbound correspondence search Id found in the result
    And I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Inbound Correspondence"
      | Task   | Review Incomplete Application |
      | Status | Created                       |
      | Source | Inbound Correspondence        |
    And I click on "first" Inbound Correspondence application pdf view icon
    Then Verify PDF viewer is initiated for linked Correspondence ID

  @CP-19642 @CP-19642-02 @crm-regression @ui-ats @ozgen
  Scenario: Validation of Template Member Matching Task when application is created from inbound correspondence call
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
    And In search result click on task id to navigate to view page
    And I verify read only fields for "Member Matching ECMS" task
      | documentType | maersk Application |
      | Channel      | Email               |

  @CP-26835 @CP-26835-01 @crm-regression @ui-ats-blats2 @vinuta
    # TC-01 Initiate MMR task, User is navigated to Member matching page
    # TC-02 Initiate MMR task, Verify 1 reason & max notes are displayed on task info on task slider
  Scenario: Verify Member Matching Research Task navigates to Member Matching UI when initiated & the Reason and Notes will display in Task Information Panel
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | DOB    | random past date |
      | GENDER | Female           |
      | SSN    | Numeric 9        |
    And I choose "HCBS" as program type
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I click on the Research button in the Application Member Matching Page
    And I select following reason for sending application to Research
      | Insufficient Information |
    And I enter notes as "RANDOM 250" Characters for Send Application For Research Panel
    And I click save for sending application to Research
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Created                  |
      | ResearchSource | UI                       |
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "UI - Research"
    And I click on search button on task search page
    And I verified that task type is "Member Matching Research"
    Then I click on initiate button in task search page
    And I will update the following information in task slider
      | status          | Cancel                  |
      | reasonForCancel | Task No Longer Required |
    Then I verified that user is navigated to "Member Matching" page
    And I verify Reason and Notes associated to the Member Matching Research task will display in the Task Information Panel
    When I click on the New button in the Application Member Matching Page
    When I click on save in Task Slider
    Then I verify Success message is displayed for task update

  @CP-26835 @CP-26835-02 @crm-regression @ui-ats-blats2 @vinuta
    # TC-03 Initiate MMR task & Verify all reasons & NO notes displayed on task info on task slider
    # TC-04 Initiate MMR task, select application as New & Verify MMR task is auto-complete
    # TC-05 Verify research button is disabled if there is already one MMR task on the application
  Scenario: Verify Member Matching Research Task is auto-completed when user clicks on New in Member Matching Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | DOB | random past date |
    And I choose "PREGNANCY ASSISTANCE" as program type
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I click on the Research button in the Application Member Matching Page
    And I select following reason for sending application to Research
      | Insufficient Information |
      | Document Misclassified   |
      | Multiple Matches         |
    And I enter notes as "" Characters for Send Application For Research Panel
    And I click save for sending application to Research
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Created                  |
      | ResearchSource | UI                       |
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "UI - Research"
    And I click on search button on task search page
    And I verified that task type is "Member Matching Research"
    Then I click on initiate button in task search page
    And I will update the following information in task slider
      | status          | Cancel                  |
      | reasonForCancel | Task No Longer Required |
    Then I verified that user is navigated to "Member Matching" page
    And I verify Reason and Notes associated to the Member Matching Research task will display in the Task Information Panel
    Then I verify Research button is disabled for inbound application in Member Matching page
    When I click on the New button in the Application Member Matching Page
    And I see application Status as "DETERMINING" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Complete                 |
      | ResearchSource | UI                       |
    When I click on save in Task Slider
    Then I verify Success message is displayed for task update

  @CP-26835 @CP-26835-03 @crm-regression @ui-ats-blats2 @vinuta
    # TC-06 Initiate MMR task, select application as New & Verify MMR task is auto-complete
    # TC-05 Verify research button is disabled if there is already one MMR task on the application
  Scenario: Verify Member Matching Research Task is auto-completed when user clicks on Duplicate in Member Matching Page
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    When If any In Progress task present then update that to Cancelled
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    Then I verify I am on the Application Tracking Page
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I create duplicate application to land on member matching page
    And I click on Save button on Create Application Page
    Then I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    Then I click on the Research button in the Application Member Matching Page
    And I select following reason for sending application to Research
      | Document Misclassified |
      | Multiple Matches       |
    And I enter notes as "Automation Notes" Characters for Send Application For Research Panel
    And I click save for sending application to Research
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Created                  |
      | ResearchSource | UI                       |
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "UI - Research"
    And I click on search button on task search page
    And I verified that task type is "Member Matching Research"
    Then I click on initiate button in task search page
    And I will update the following information in task slider
      | status          | Cancel                  |
      | reasonForCancel | Task No Longer Required |
    Then I verified that user is navigated to "Member Matching" page
    And I click on the "0" select box for matching application in Member Matching page
    And I store linked application id and status from "0" row
    Then I verify Research button is disabled for inbound application in Member Matching page
    When I click on the Duplicate button on Member Matching page & Continue
    And I see application Status as "Duplicate" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Application"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Complete                 |
      | ResearchSource | UI                       |
    When I click on save in Task Slider
    Then I verify Success message is displayed for task update

  @CP-29933 @CP-29933-06 @crm-regression @ui-ats @burak
  Scenario: Terminate Workflow and Close Tasks for withdrawn Applications in the context of a Task for Review Incomplete Application Task
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
    And I will verify the response details for get linked applications-tasks  response
      | statusRI | Created                       |
      | typeRI   | Review Incomplete Application |
      | nameRI   | Task                          |
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Insufficient" in the application information
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Review Incomplete Application |
      | Status | Cancelled                     |
      | Source | API                           |
    And I click id of "Task" in ATS Links section
      | Type | Review Incomplete Application |
    Then I verify action taken value as "Withdrawn" on View Task Page
    And I initiated bpm process get api for application data service
    Then I run bpm process get api using application id
    And I verify camunda process should be terminated

  @CP-29933 @CP-29933-07 @crm-regression @ui-ats @burak
  Scenario: Close Tasks for withdrawn Applications in the context of a Task for Member Matching Research Task
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I will get the Authentication token for "BLCRM" in "CRM"
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    Then I click on the Research button in the Application Member Matching Page
    And I select following reason for sending application to Research
      | Document Misclassified |
    And I enter notes as "RANDOM 250" Characters for Send Application For Research Panel
    And I click save for sending application to Research
    Then I navigate to Application Tracking
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Created                  |
      | ResearchSource | UI                       |
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Cancelled                |
      | ResearchSource | UI                       |
    And I click id of "Task" in ATS Links section
      | Type | Member Matching Research |
    Then I verify action taken value as "Withdrawn" on View Task Page

  @CP-29933 @CP-29933-08 @crm-regression @ui-ats @burak
  Scenario: Terminate Workflow and Close Tasks for withdrawn Applications in the context of a Task for Member Matching Task
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
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Submitted" in the application information
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    When I click Continue button inside the warning message
    And I see application Status as "WITHDRAWN" in the application information
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task"
      | Task   | Member Matching |
      | Status | Cancelled       |
      | Source | API             |
    And I click id of "Task" in ATS Links section
      | Type | Member Matching |
    Then I verify action taken value as "Withdrawn" on View Task Page
    And I initiated bpm process get api for application data service
    Then I run bpm process get api using application id
    And I verify camunda process should be terminated

  @CP-31027 @CP-31027-01 @crm-regression @ui-ats @burak
  Scenario: Retrieve Outbound Correspondence entities linked to an Application with expected order
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | Mail      |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | writtenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | Russian         |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress     |
      | autoAPI@test.com |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber  | primaryContactTypeInd |
      | faxNumber | RANDOM PHONE | false                 |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated create outbound correspondence api for ATS
    Then I provide application details for POST Create Outbound Correspondence api
    And I run POST Outbound Correspondence Call API
    Then I verify new Outbound Correspondence is created and stored id
    And I initiated create outbound correspondence api for ATS
    Then I provide application details for POST Create Outbound Correspondence api
    And I run POST Outbound Correspondence Call API
    Then I verify new Outbound Correspondence is created and stored id
    And I initiated create outbound correspondence api for ATS
    Then I provide application details for POST Create Outbound Correspondence api
    And I run POST Outbound Correspondence Call API
    Then I verify new Outbound Correspondence is created and stored id
    And I initiated create outbound correspondence api for ATS
    Then I provide application details for POST Create Outbound Correspondence api
    And I run POST Outbound Correspondence Call API
    Then I verify new Outbound Correspondence is created and stored id
    And I initiated create outbound correspondence api for ATS
    Then I provide application details for POST Create Outbound Correspondence api
    And I run POST Outbound Correspondence Call API
    Then I verify new Outbound Correspondence is created and stored id
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I verify refresh button is displayed and clicked on it
    And I verify there is "5" linked entity displays on Link Panel
    And I verify all linked entities are in sort order

  @CP-31027 @CP-31027-02 @crm-regression @ui-ats @burak
  Scenario: Retrieve Outbound Correspondence entities linked to an Application ,verify access to OB details and document
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | Mail      | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | writtenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | Russian         |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress     |
      | autoAPI@test.com |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber  | primaryContactTypeInd |
      | faxNumber | RANDOM PHONE | false                 |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I initiated create outbound correspondence api for ATS
    Then I provide application details for POST Create Outbound Correspondence api
    And I run POST Outbound Correspondence Call API
    Then I verify new Outbound Correspondence is created and stored id
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Outbound Correspondence"
      | Task   | Member Matching |
      | Status | Created         |
      | Source | API             |
    And I click id of "Outbound Correspondence" in ATS Links section
      | Type | AppId Application ID Required |
    Then I verify I navigated to "Outbound Correspondence Details" after clicking linked Entity
    And I click on the back arrow button on Outbound Correspondence details
    Then I verify I am on the Application Tracking Page
    And I click icon of "Outbound Correspondence" in ATS link section
    Then Verify PDF viewer is initiated for linked Correspondence ID

  @CP-29000 @CP-29000-01 @crm-regression @ui-ats @ozgen
  Scenario: Closed Task Slider when Review Incomplete Application Task Status is completed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
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
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    When If any In Progress task present then update that to Cancelled
    And I verified that task type is "Review Incomplete Application"
    Then I click on initiate button in task search page
    Then I verified that user is navigated to "Application" page
    And I click on the priority in dashboard
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5         |
      | LAST NAME  | Random 5         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
    And I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I verify task slider is closed

  @CP-29000 @CP-29000-02 @crm-regression @ui-ats @ozgen
  Scenario: Closed Task Slider when Member Matching Task Status is completed
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
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    When If any In Progress task present then update that to Cancelled
    And I verified that task type is "Member Matching"
    Then I click on initiate button in task search page
    Then I click on the Research button in the Application Member Matching Page
    And I select following reason for sending application to Research
      | Insufficient Information |
    And I enter notes as "RANDOM 250" Characters for Send Application For Research Panel
    And I click save for sending application to Research
    Then I verify task slider is closed

  @CP-31608 @CP-31608-01 @crm-regression @ui-ats @ozgen
  Scenario: View Link Application in View Task Details Page, from Task Links Component
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType     | applicationReceivedDate | submittedInd | applicationCycle |
      | Medical Assistance  | CURRENT YYYYMMDD        | true         | New              |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I logged into CRM
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    When If any In Progress task present then update that to Cancelled
    And I verified that task type is "Review Incomplete Application"
    And In search result click on task id to navigate to view page
    Then I verify "Application" link section has all the business object linked
    And I click on "Application" id on View Task Page
    And I see columns displayed in this order in the Application Information panel
      | APPLICATION ID | APPLICATION CODE | EXTERNAL APP ID | PRIORITY | DEADLINE DATE | SIGNATURE DATE | APPLICATION TYPE | CYCLE | RECEIVED DATE | RECEIVED LANGUAGE | CHANNEL | CREATE DATE | LAST UPDATED DATE |
    Then I click on back arrow button on create application page
    Then I verify should I navigated to view task page

  @CP-33218 @CP-33218-01 @crm-regression @ui-ats @ozgen
  Scenario: View Task from Application Links Component
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | applicationCycle |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | New              |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusRI | Created                       |
      | typeRI   | Review Incomplete Application |
      | nameRI   | Task                          |
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Insufficient" in the application information
    And I click id of "Task" in ATS Links section
      | Type | Review Incomplete Application |
    Then I verify "Application" link section has all the business object linked
    Then I click on back arrow button on create application page
    And I see columns displayed in this order in the Application Information panel
      | APPLICATION ID | APPLICATION CODE | EXTERNAL APP ID | PRIORITY | DEADLINE DATE | SIGNATURE DATE | APPLICATION TYPE | CYCLE | RECEIVED DATE | RECEIVED LANGUAGE | CHANNEL | CREATE DATE | LAST UPDATED DATE |

  @CP-24758 @API-CP-24758-01 @crm-regression @ui-ats @burak
  Scenario: Verify user is able to view the image of the application inbound correspondence for multiple linked Inbound Correspondences
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
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | SetId          |
      | Fax     | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | No                    | null                       | documentId one |
    And I have prepared keywordRecord Primary Individual member dynamic data values with "M"
    Then I submit ATS sendevent request
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | duplicate              |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "two" in a list
    And I have prepared default sendevent request data for ATS from file "events/ATSSendEvent.json" for second time
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      |
      | documentId2    | today: yyyy-MM-dd |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | SetId          |
      | Fax     | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | No                    | null                       | documentId one |
    And I have prepared keywordRecord Primary Individual member dynamic data values with "M"
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I save the application Id from Inbound document creation for fetching application details
    And I initiate and run Get Application Links Call
    And I will verify there are 2 linked "Inbound Correspondence" for link application response with following values
      | status | RECEIVED               |
      | type   | maersk Application    |
      | name   | Inbound Correspondence |
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter inbound correspondence created application id in the application search page
    When I click on search button in search application page
    And I click on the inbound correspondence search Id found in the result
    And I verify refresh button is displayed and clicked on it
    And I click on "first" Inbound Correspondence application pdf view icon
    Then I check to see Inbound Document Id "one" received is viewable in pdf
    And I click on "second" Inbound Correspondence application pdf view icon
    Then I check to see Inbound Document Id "one" received is viewable in pdf