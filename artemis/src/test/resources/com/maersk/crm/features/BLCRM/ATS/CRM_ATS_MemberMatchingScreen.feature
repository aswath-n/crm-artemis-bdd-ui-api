Feature: Member Matching Page

  @CP-29764 @CP-29764-01 @crm-regression @ui-ats @ozgen @ats-smoke
  Scenario: Verify Inbound Application Information in Member Matching page for Medical Assistance -> BLCRM
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
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 | addressCounty |
      | Residential | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        | Loudoun       |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify following details on Member Matching Page for "API Created Application Details"
      | Application ID | Application Status | Address Line 1 and 2 | City State Zip |
    And I click on member matching page back arrow to navigate to create application page
    Then I verify "MEDICAL ASSISTANCE" Application Page header is displayed

  @CP-29764 @CP-29764-02 @crm-regression @ui-ats @ozgen
  Scenario: Verify Inbound Application Information for null address details in Member Matching page for Medical Assistance -> BLCRM
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
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | workPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify "Address" details displayed as '-- --' on Member Matching Page
    And I click on member matching page back arrow to navigate to create application page
    Then I verify "MEDICAL ASSISTANCE" Application Page header is displayed

  @CP-29764 @CP-29764-03 @crm-regression @ui-ats @ozgen
    #needs to be checked one more time
  Scenario: Verify Inbound Application Information in Member Matching page when Member Matching task is initiated -> BLCRM
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
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    When If any In Progress task present then update that to Cancelled
    And I logged into CRM
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    And I verified that task type is "Member Matching"
    Then I click on initiate button in task search page
    And Verify PDF viewer is not initiated
    Then I verified that user is navigated to "Member Matching" page
    Then I verify following details on Member Matching Page for "MEMBER MATCHING from API"
      | NAME | APPLICANT STATUS | ADDRESS LINE ONE | ADDRESS LINE TWO | APP ID | SSN | LTC PROGRAMS |
    And I will update the following information in task slider
      | status          | Cancel              |
      | reasonForCancel | Created Incorrectly |
    And I click on save in Task Slider
    Then I verify task save success message
    
  @CP-29764 @CP-29764-04 @crm-regression @ui-ats @ozgen
  Scenario: Verify Inbound Application Information in Member Matching page when Review Incomplete task is initiated and resubmitted -> BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
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
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    And I verified that task type is "Review Incomplete Application"
    Then I click on initiate button in task search page
    And I click on the priority in dashboard
    And I click on the Edit button for the Primary Individual Details
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME     | Random 5    |
      | MIDDLE INITIAL | Alphabetic 1     |
      | SUFFIX         | Alphabetic 3     |
      | LAST NAME      | Random 5    |
      | DOB            | random past date |
      | SSN            | Numeric 9        |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 1 | random 41  |
      | RESIDENCE ADDRESS LINE 2 | random 41  |
      | RESIDENCE CITY           | Metropolis |
      | RESIDENCE STATE          | Illinois   |
      | RESIDENCE ZIP CODE       | 62960      |
      | RESIDENCE COUNTY         | Massac     |
    Then I choose "Medicaid" as program type
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I click on Save button on Create Application Page
    And I verify the save successfully updated message
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5       |
      | MI         | RANDOM 1         |
      | LAST NAME  | Random 5       |
      | SUFFIX     | RANDOM 3         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
    And Select the "Medicaid" Program(s) for application member
    And click on save button for add application member
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I get application id from the Create Application Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I Verify the following from the GET Application with ApplicationId
      | PI ID  | applicationConsumerId |
      | APP ID | applicationConsumerId |
    Then I verify following details on Member Matching Page for "Application Details"
      | Application ID | Application Status | Address Line 1 and 2 | City State Zip |
    Then I verify following details on Member Matching Page for "PI Details"
      | FULL NAME | SSN | DOB |
    Then I verify following details on Member Matching Page for "Application Member Details"
      | FULL NAME | SSN | DOB |
    Then I verify following Program(s) displayed in Alphabetical order on the Member Matching Consumer Details for "PI"
      | Medicaid |
    Then I verify following Program(s) displayed in Alphabetical order on the Member Matching Consumer Details for "Application Member"
      | Medicaid |
    Then I verify I see the Primary Individual Indicator for the associated Primary Individual Record
    And I click on the priority in dashboard
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify task save success message

  @CP-29764 @CP-29764-05 @crm-regression @ui-ats @ozgen
  Scenario: Verify Inbound Application Information in Member Matching page when Member Matching Research task is initiated -> BLCRM
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
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    When I initiate Application send to research Initiate Action API
      | action           | reasons                        | notes          |
      | Send To Research | Document/Insufficient/Multiple | 250 CHARACTERS |
    And I Post Application send to research Initiate Action API
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM       | Complete                 |
      | typeMM         | Member Matching          |
      | nameMM         | Task                     |
      | statusResearch | Created                  |
      | typeResearch   | Member Matching Research |
      | nameResearch   | Task                     |
    And I will get "Task" ID for "Member Matching Research" type from the application links response
    And I logged into CRM
    When If any In Progress task present then update that to Cancelled
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    And I verified that task type is "Member Matching Research"
    Then I click on initiate button in task search page
    Then I verified that user is navigated to "Member Matching" page
    And I verify following details on Member Matching Page for "Member Matching Research"
      | APPLICATION ID | APPLICATION STATUS | ADDRESS LINE 1 AND 2 | CITY STATE ZIP | APPLICANT ID | FULL NAME | SSN | DOB | PROGRAMS |
    And I will update the following information in task slider
      | status          | Cancel         |
      | reasonForCancel | Duplicate Task |
    And I click on save in Task Slider
    Then I verify task save success message

  @CP-30099 @CP-30099-01 @crm-regression @ui-ats @ozgen #will fail due to CP-34410
    #IMPORTANT: creates Member Matching task and navigates to Member Matching Page
  Scenario: Verification of Research button and creation of Member Matching Research task when matching type is Case/Consumer
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
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    And I will get "Task" ID for "Member Matching" type from the application links response
    Then I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
  #  And I see I navigated to Member Matching page
    Then I click on the Research button in the Application Member Matching Page
    And I enter notes as "RANDOM 250" Characters for Send Application For Research Panel
    And I click on member matching page back arrow to navigate to create application page
    And I verify Back Arrow warning warning message displayed on Member Matching Page
    And I click "CANCEL" button inside the warning message on Member Matching Page
    And I see I navigated to Member Matching page
    And I select following reason for sending application to Research
      | Insufficient Information |
      | Document Misclassified   |
    And I click on member matching page back arrow to navigate to create application page
    And I verify Back Arrow warning warning message displayed on Member Matching Page
    And I click "Continue" button inside the warning message on Member Matching Page
    Then I verify "MEDICAL ASSISTANCE" Application Page header is displayed
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I click on the Research button in the Application Member Matching Page
    And I enter notes as "RANDOM 250" Characters for Send Application For Research Panel
    And I select following reason for sending application to Research
      | Insufficient Information |
    Then I click save for sending application to Research
    Then I verify refresh button is displayed and clicked on it
    Then I verify application links component has all the business object linked : "Task,Task"
      | ResearchTask   | Member Matching Research |
      | ResearchStatus | Created                  |
      | ResearchSource | UI                       |
      | Task           | Member Matching          |
      | Status         | Complete                 |
      | Source         | API                      |

   @CP-36075 @CP-36075-01 @crm-regression @ui-ats @ozgen
   Scenario: Verification of View Application Image button for ECMS Created Applications on Member Matching Page
     When I will get the Authentication token for "<projectName>" in "CRM"
     Given I initiated Case Loader API for Create New Case
    When I run Case Loader API for Create New Case and Customer with below details:
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip |
      | Consumer     | {random}          | {random}         | 1999-02-11          | {random}    | Male       | today-50           |[blank]| Spouse       |
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
       | Channel | ProcessType            | Application Type   | SetId          |
       | Mail    | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | documentId one |
     And I have prepared Authorized Representative keywords for ATS sendevent
       | AR First Name | AR Middle Initial | AR Last Name | AR Address Line 1 | AR Address City | AR Address State | AR Address Zip | AR Organization Name | AR ID Number | AR Authorization Signature Date |
       | Random First  | Random Mi         | Random Last  | Random Address    | Random City     | Random State     | Random Zip     | Random Org name      | Random Id    | today: yyyy-MM-dd               |
     And I have prepared keywordRecord Primary Individual member dynamic data values with "Case"
     And I have prepared keywordRecord Application member one dynamic data values with "F"
     Then I submit ATS sendevent request
     And I initiate and run Get Inbound Correspondence Links Call
     Then I will get applicationId and taskId from the response
     And I logged into CRM
     And I minimize Genesys popup if populates
     And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
     And I enter inbound correspondence created application id in the application search page
     When I click on search button in search application page
     And I click on the inbound correspondence search Id found in the result
     And I see application Status as "TARGETS UNIDENTIFIED" in the application information
     And I navigate to application tab page
     Then I click on Submit button only in Create Application Page
     And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
     Then I verify that there is an Inbound Correspondence application pdf view icon
     And I click on "first" Inbound Correspondence application pdf view icon
     Then I check to see Inbound Document Id "one" received is viewable in pdf

  @CP-36074 @CP-36074-01 @crm-regression @ui-ats @ozgen
  Scenario: Navigate back to Application Tab from Member Matching Screen when user click on Edit button on Member Matching Screen
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
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify Edit icon is displayed for "Application" on Member Matching Page
    And I click on Edit button for "Application" on Member Matching Page
    Then I see Application tab is selected
    And I select Primary Individual Application information with the following
      | CHANNEL           | random  |
      | RECEIVED LANGUAGE | Russian |
      | CYCLE             | Renewal |
    When I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify Edit icon is displayed for "Application" on Member Matching Page

  @CP-36074 @CP-36074-02 @crm-regression @ui-ats @ozgen
  Scenario: Navigate to Application Tab when user edits application and auto cleared application with Determining status
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
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    And I will get "Task" ID for "Member Matching" type from the application links response
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify Edit icon is displayed for "Application" on Member Matching Page
    And I click on Edit button for "Application" on Member Matching Page
    Then I see Application tab is selected
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | SSN | Numeric 9        |
      | DOB | random past date |
    When I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    Then Wait for 3 seconds
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I see application Status as "DETERMINING" in the application information
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |

  @CP-36150 @CP-36150-01 @crm-regression @ui-ats @vinuta
  Scenario: Navigate back to Application Member Details from Member Matching Screen when user click on Edit button on Member Matching Screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
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
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify Edit icon is displayed for "1st Application Member" on Member Matching Page
    And I click on Edit button for "1st Application Member" on Member Matching Page
    Then I verify Application Member header on create Application Member Page
    Then I fill in the following application member values
      | SUFFIX               | RANDOM 3         |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify Edit icon is displayed for "1st Application Member" on Member Matching Page

  @CP-36150 @CP-36150-02 @crm-regression @ui-ats @vinuta
  Scenario: Verify auto-clearance when application member details are edited when user click on Edit button on Member Matching Screen
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
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
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify Edit icon is displayed for "1st Application Member" on Member Matching Page
    And I click on Edit button for "1st Application Member" on Member Matching Page
    Then I verify Application Member header on create Application Member Page
    Then I fill in the following application member values
      | SSN | Edit Numeric 9        |
      | DOB | random past date |
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    Then Wait for 3 seconds
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I see application Status as "DETERMINING" in the application information

  @CP-32906 @CP-32906-01 @crm-regression @ui-ats @ozgen
  Scenario Outline: Retrieve benefit status when it is Eligible Newborn and Medicaid General on Member Matching Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "<eligibilityType>"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"
    Then I verify benefit status records are displayed with population "<eligibilityType>"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify that benefit status is displayed "<benefitStatus>" on incoming data
    Examples:
      | eligibilityType  | benefitStatus     |
      | NEWBORN          | Newborn Eligible  |
      | MEDICAID-GENERAL | Medicaid Eligible |

  @CP-32906 @CP-32906-02 @crm-regression @ui-ats @ozgen
  Scenario: Retrieve benefit status when it is Eligible Pregnant Woman on Member Matching Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with population type "PREGNANT-WOMEN"
    And  I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                |
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | yes                |
      | enrollmentStartDate          | past               |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"
    Then I verify benefit status records are displayed with population "PREGNANT-WOMEN"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify that benefit status is displayed "Pregnant Woman Eligible" on incoming data

  @CP-32906 @CP-32906-03 @crm-regression @ui-ats @ozgen
  Scenario: Retrieve benefit status when it is Partially Enrolled Newborn data on Member Matching Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planId                       | 1880                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Partially Enrolled"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify that benefit status is displayed "Newborn Partially Enrolled" on incoming data

  @CP-32906 @CP-32906-04 @crm-regression @ui-ats @ozgen
  Scenario: Scenario: Retrieve benefit status when it is Enrolled Pregnant-Woman data on Member Matching Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | yes                  |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify that benefit status is displayed "Pregnant Women Enrolled" on incoming data

  @CP-32906 @CP-32906-05 @crm-regression @ui-ats @ozgen
  Scenario: Scenario: Retrieve benefit status when it is Medicaid-General data on Member Matching Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | yes                  |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify that benefit status is displayed "Medicaid-General Enrolled" on incoming data

  @CP-32906 @CP-32906-06 @crm-regression @ui-ats @ozgen
  Scenario: Retrieve benefit status for NOT Eligible Medicaid-General data on Member Matching Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 13                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | current           |
      | eligibilityEndReason         | test              |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"
    Then I verify benefit status records are displayed with benefit status "Not Eligible"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | true         | Fax       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | SSN from EE |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    Then I verify that benefit status is displayed "No Benefit" on incoming data
