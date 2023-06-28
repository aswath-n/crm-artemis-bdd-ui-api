Feature: API: Save Submit View Application API Second

  @API-CP-22990 @API-CP-22990-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Include Applicant Eligibility Outcome in Response for View Application Details API Wait List & Eligible
    #needs to be refactored due to changes on Sub-Program
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
      | CHIP |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | Determining |
    And I store program details for updating eligibility status
    And I initiated ats eligibility save application api
    And I initiate eligibility status API for ats with following values for "Eligible" status
      | eligibilityStatus | updatedBy | createdBy | startDate  | endDate    | determinationDate | subProgramId |
      | Eligible          | 3163      | 2492      | 2021-10-04 | 2199-10-04 | Today             | 10           |
      | Eligible          | 3163      | 2492      | 2021-10-04 | 2199-10-04 | Today             | 10           |
      | Wait List         | 3163      | 2492      | 2021-10-04 | 2199-10-04 | Today             | null         |
      | Wait List         | 3163      | 2492      | 2021-10-04 | 2199-10-04 | Today             | null         |
    And I POST ATS Update Eligibility status application api with "success" status
    And I verify the response outcome has following Eligibility Outcome for Each Member and Each Program
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    Then I verify View Application Response has Eligibility details for Each Applying Members

  @API-CP-22990 @API-CP-22990-02 @API-CRM-Regression @API-ATS @burak
  Scenario: Include Applicant Eligibility Outcome in Response for View Application Details API Pending & Not Eligible
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
      | CHIP |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | Determining |
    And I store program details for updating eligibility status
    And I initiated ats eligibility save application api
    And I initiate eligibility status API for ats with following values for "Eligible" status
      | eligibilityStatus | updatedBy | createdBy | startDate  | endDate    | determinationDate |
      | Pending           | 3163      | 2492      | 2021-10-04 | 2199-10-04 | Today             |
      | Pending           | 3163      | 2492      | 2021-10-04 | 2199-10-04 | Today             |
      | Not Eligible      | 3163      | 2492      | 2021-10-04 | 2199-10-04 | Today             |
      | Not Eligible      | 3163      | 2492      | 2021-10-04 | 2199-10-04 | Today             |
    Then I provide denial reason for eligibility
      | Other Insurance   |
      | Over Age          |
      | Medicaid Eligible |
      | Over Income       |
      | Already Eligible  |
    And I POST ATS Update Eligibility status application api with "success" status
    And I verify the response outcome has following Eligibility Outcome for Each Member and Each Program
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    Then I verify View Application Response has Eligibility details for Each Applying Members

  @API-CP-25434 @API-CP-25434-01 @API-CRM-Regression @API-ATS @burak
  Scenario:Verifying additional fields for retrieving Application Summary for Application ID query
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | interactiveInd | externalApplicationId  |
      | Long Term Care  | CURRENT YYYYMMDD        | true           | RANDOM EXTERNAL APP ID |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    When I initiate and run the View Application Summary API for the Created Application with "ApplicationID" query
    Then I verify response details has additional fields for View Application Summary API
      | APPLICATION CYCLE         | New            |
      | APPLICATION RECEIVED DATE | Today          |
      | APPLICATION TYPE          | Long Term Care |
    Then I verify the details of the retrieve Application Summary API

  @API-CP-25434 @API-CP-25434-02 @CP-24479-01 @API-CRM-Regression @API-ATS @burak
  Scenario:Verifying  additional fields for retrieving Application Summary External Application ID query
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | externalApplicationId  |
      | Long Term Care  | CURRENT YYYYMMDD        | RANDOM EXTERNAL APP ID |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify that external application ID is included in API response with value "random"
    When I initiate and run the View Application Summary API for the Created Application with "ExternalApplicationID" query
    Then I verify response details has additional fields for View Application Summary API
      | APPLICATION CYCLE         | New            |
      | APPLICATION RECEIVED DATE | Today          |
      | APPLICATION TYPE          | Long Term Care |
      | EXTERNAL APPLICATION ID   |[blank]|
    Then I verify the details of the retrieve Application Summary API

  @API-CP-24479 @API-CP-24479-02  @API-CRM-Regression @API-ATS @burak
  Scenario:Verifying error message of externalApplicationID missing in Request for View API Summary Endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | externalApplicationId  |
      | Long Term Care  | CURRENT YYYYMMDD        | RANDOM EXTERNAL APP ID |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify that external application ID is included in API response with value "random"
    When I initiate and run the View Application Summary API for the Created Application with "EMPTY EXTERNAL APP ID" query
    Then I verify the error message for invalid Application ID for view API summary as "Application Id or External Application ID must not be null or empty"

  @API-CP-24479 @API-CP-24479-02 @API-CRM-Regression @API-ATS @burak
  Scenario:Verifying error message of applicationId missing in Request for View API Summary Endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | externalApplicationId  |
      | Long Term Care  | CURRENT YYYYMMDD        | RANDOM EXTERNAL APP ID |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify that external application ID is included in API response with value "random"
    When I initiate and run the View Application Summary API for the Created Application with "EMPTY APPLICATION ID" query
    Then I verify the error message for invalid Application ID for view API summary as "Application Id or External Application ID must not be null or empty"

  @API-CP-24479 @API-CP-24479-03 @API-CRM-Regression @API-ATS @burak
  Scenario:Verifying returning multiple records of given External Application ID for View Application Summary Endpoint
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate |
      | Long Term Care  | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I set external application ID to value "random" in save API
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate |
      | Long Term Care  | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I set external application ID to value "duplicate" in save API
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate |
      | Long Term Care  | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I set external application ID to value "duplicate" in save API
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify that external application ID is included in API response with value "random"
    When I initiate and run the View Application Summary API for the Created Application with "ExternalApplicationID" query
    Then I verify View Application Summary API returns "3" multiple records

  @API-CP-22513 @API-CP-22513-01 @API-CRM-Regression @API-ATS @burak
  Scenario Outline:Verify Set Application Status as Expired/Closed for Targets Unidentified Status and Verify Member Matching Task is Cancelled
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | <Application Type> | <Received Date>         | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn        | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
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
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Targets Unidentified" on the response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    And I will get "Task" ID for "Member Matching" type from the application links response
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | applicationId      | applicationDeadlineDate |
      | Medical Assistance | <Received Date>         | true         | created previously | <DeadlineDate>          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Cancelled       |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I initiated get task by task id "getFromApplicationLinksATS"
    And I run get task by task id API
    Then I store task details response
    Then I verify Task status and Action Taken Values for ATS created tasks
      | Action Taken | Expired   |
      | Status       | Cancelled |
    Examples:
      | Application Type   | Received Date | DeadlineDate | status  |
      | Medical Assistance | PRIOR 45      | PRIOR 1      | Expired |
      | Medical Assistance | PRIOR 45      | PRIOR 11     | Closed  |
      | Medical Assistance | PRIOR 45      | PRIOR 10     | Expired |
      | Long Term Care     | PRIOR 90      | PRIOR 1      | Expired |

  @API-CP-22513 @API-CP-22513-02 @API-CRM-Regression @API-ATS @burak
  Scenario Outline:Verify Set Application Status as Expired/Closed for Entering Data Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | <Application Type> | <Received Date>         | false        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
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
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    Examples:
      | Application Type   | Received Date | status        |
      | Medical Assistance | PRIOR 46      | Expired       |
      | Medical Assistance | PRIOR 56      | Closed        |
      | Medical Assistance | PRIOR 45      | Entering Data |
      | Medical Assistance | PRIOR 55      | Expired       |
      | Long Term Care     | PRIOR 91      | Expired       |
      | Long Term Care     | PRIOR 101     | Closed        |
      | Long Term Care     | PRIOR 90      | Entering Data |
      | Long Term Care     | PRIOR 100     | Expired       |

  @API-CP-22513 @API-CP-22513-03 @API-CRM-Regression @API-ATS @burak
  Scenario Outline:Verify Setting Application Status as Expired/Closed for Insufficient Status and Verify Task Status updated as Cancelled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | <Application Type> | <Received Date>         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
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
    Then Wait for 7 seconds
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Insufficient" on the response
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | applicationId      | applicationDeadlineDate |
      | <Application Type> | <Received Date>         | false        | created previously | <DeadlineDate>          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
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
    Then Wait for 7 seconds
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusRI | Cancelled                     |
      | typeRI   | Review Incomplete Application |
      | nameRI   | Task                          |
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I initiated get task by task id "getFromApplicationLinksATS"
    And I run get task by task id API
    Then I store task details response
    Then I verify Task status and Action Taken Values for ATS created tasks
      | Action Taken | Expired   |
      | Status       | Cancelled |
    Examples:
      | Application Type   | Received Date | status  | DeadlineDate |
      | Medical Assistance | PRIOR 45      | Expired | PRIOR 1      |
      | Medical Assistance | PRIOR 45      | Closed  | PRIOR 11     |
      | Long Term Care     | PRIOR 90      | Expired | PRIOR 1      |
      | Long Term Care     | PRIOR 90      | Closed  | PRIOR 11     |

  @API-CP-22513 @API-CP-22513-04 @API-CRM-Regression @API-ATS @burak
  Scenario Outline: Verify Setting  Application Status as Expired/Closed for Determining Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | <Application Type> | <Received Date>         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
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
    Then Wait for 10 seconds
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd | applicationId      | applicationDeadlineDate |
      | <Application Type> | <Received Date>         | true         | false          | created previously | <DeadlineDate>          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
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
    Then Wait for 10 seconds
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    Examples:
      | Application Type   | Received Date | DeadlineDate | status  |
      | Medical Assistance | PRIOR 45      | PRIOR 1      | Expired |
      | Medical Assistance | PRIOR 45      | PRIOR 11     | Closed  |
      | Medical Assistance | PRIOR 44      | PRIOR 10     | Expired |
      | Long Term Care     | PRIOR 90      | PRIOR 1      | Expired |

  @API-CP-31671 @API-CP-31671-01 @API-ATS @API-CRM-Regression @chandrakumar
  Scenario Outline: Validation of Setting the Application received langauage during Creation for Medical Assistance application
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | applicationReceivedLanguage     |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | <Application Received Language> |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Then I verify the response includes "<Application Received Language>" application received language for "Medical Assistance" appplication
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName              |
      | APPLICATION_SAVE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify The "APPLICATION_SAVE_EVENT" includes application received language for "Medical Assistance" application
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "APPLICATION_SAVE_EVENT"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "APPLICATION_SAVE_EVENT" and subscriberId for ATS
    Examples:
      | Application Received Language |
      | Braille English               |
      | Braille Spanish               |
      | English                       |
      | Other                         |
      | Russian                       |
      | Spanish                       |
      | Vietnamese                    |

  @API-CP-30365 @API-CP-30365-01 @API-ATS @API-CRM-Regression @chandrakumar
  Scenario Outline: Validation of Application GUID number field value is displayed in the response during Creation of application
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd   | interactiveInd   |
      | <applicationType> | CURRENT YYYYMMDD        | <submittedInd> | <interactiveInd> |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I verify the application GUID from the response for "<applicationType>" application
    Examples:
      | applicationType    | submittedInd | interactiveInd |
      | Medical Assistance | false        | false          |
      | Long Term Care     | false        | false          |
      | Medical Assistance | true         | null           |
      | Long Term Care     | true         | null           |
      | Medical Assistance | true         | true           |
      | Long Term Care     | true         | true           |

  @API-CP-30365 @API-CP-30365-02 @API-ATS @API-CRM-Regression @chandrakumar
  Scenario Outline: Validation of Application GUID number field value is displayed in the response For an application with Withdrawn Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd |
      | <applicationType> | CURRENT YYYYMMDD        | false        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I get the application GUID from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I get the application GUID from API response after updating status
    Then I verify the application GUID from the response for "<applicationType>" application
    Examples:
      | applicationType    |
      | Medical Assistance |
      | Long Term Care     |

  @API-CP-30365 @API-CP-30365-03 @API-ATS @API-CRM-Regression @chandrakumar
  Scenario Outline: Validation of Application GUID number field value is displayed in the response For an application with Determining Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd |
      | <applicationType> | CURRENT YYYYMMDD        | false        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I get the application GUID from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I get the application GUID from API response after updating status
    Then I verify the application GUID from the response for "<applicationType>" application
    Examples:
      | applicationType    |
      | Medical Assistance |
      | Long Term Care     |

  @API-CP-30365 @API-CP-30365-04 @API-ATS @API-CRM-Regression @chandrakumar
  Scenario Outline: Validation of Application GUID number field value is displayed in the response For an application with Duplicate Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate |
      | <applicationType> | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I get the application GUID from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Duplicate |
      | UPDATED BY             | 3163      |
      | CREATED BY             | 3163      |
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I get the application GUID from API response after updating status
    Then I verify the application GUID from the response for "<applicationType>" application
    Examples:
      | applicationType    |
      | Medical Assistance |
      | Long Term Care     |

  @API-CP-21533 @API-CP-21533-01 @API-CP-33090-01 @API-CRM-Regression @API-ATS @sang
  Scenario Outline: Successful submission of application with Primary Individual with First name Last name DoB and address
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType    | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | <ADDRESS TYPE> | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    Then Wait for 10 seconds
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    Examples:
      | ADDRESS TYPE | status      |
      | Residential  | Determining |
      | Mailing      | Determining |

  @API-CP-21533 @API-CP-21533-02 @API-CRM-Regression @API-ATS @sang
  Scenario Outline: Successful submission of application with Primary Individual with First name Last name DoB and Phone Type
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber  |
      | <phone>   | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    Then Wait for 7 seconds
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    Examples:
      | phone           | status       |
      | cellPhoneNumber | Determining  |
      | homePhoneNumber | Determining  |
      | workPhoneNumber | Determining  |
      | faxNumber       | Insufficient |

  @API-CP-21533 @API-CP-21533-03 @API-CRM-Regression @API-ATS @sang
  Scenario: Successful submission of application with Primary Individual with First name Last name DoB and EMAIL
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response

  @API-CP-21533 @API-CP-21533-04 @API-CP-33090-02 @API-CRM-Regression @API-ATS @sang
  Scenario: Unsuccessful submission of application with Primary Individual with First name Last name DoB and No Contact Info
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Insufficient" on the response

  @API-CP-21533 @API-CP-21533-05 @API-CRM-Regression @API-ATS @sang
  Scenario: Unsuccessful submission of application with an Application with no Primary Individual
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | consumerRoleType   |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | Application Member |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    Then Wait for 7 seconds
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Insufficient" on the response

  @API-CP-21533 @API-CP-21533-06 @API-CRM-Regression @API-ATS @sang
  Scenario Outline: Unsuccessful submission of application to Submitted status with First name or Last name only for Primary Individual and App Mem with valid critical data
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 0
      | <name type>  | dateOfBirth |
      | RANDOM FIRST | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    Then Wait for 7 seconds
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Insufficient" on the response
    Examples:
      | name type         |
      | consumerFirstName |
      | consumerLastName  |

  @API-CP-34281 @API-CP-34281-01 @API-CRM-Regression @API-ATS @vinuta
  Scenario Outline: Verify error response when the field validation fails for Phone Number
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType   | phoneNumber   | primaryContactTypeInd |
      | <phoneType> | <phoneNumber> | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 435679     | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 435679     | Suite 2        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "400" and status ""
    Then I expected error message as response from create application api for ats with status ""
      | error message                                  |
      | Phone number should be digit with length of 10 |
    Examples:
      | phoneType       | phoneNumber  |
      | homePhoneNumber | 2736svga-*   |
      | faxNumber       | 123456789    |
      | cellPhoneNumber | 12345678901  |
      | workPhoneNumber | "          " |

  @API-CP-34281 @API-CP-34281-02 @API-CRM-Regression @API-ATS @vinuta
  Scenario Outline: Verify error response when the field validation fails for SSN
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn   |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | <ssn> |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 7863456789  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 876543     | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 876543     | Suite 2        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "400" and status ""
    Then I expected error message as response from create application api for ats with status ""
      | error message                            |
      | SSN should be digit with the length of 9 |
    Examples:
      | ssn         |
      | 2736sv%$-   |
      | 12345678    |
      | 1234567890  |
      | "         " |

  @API-CP-34281 @API-CP-34281-03 @API-CRM-Regression @API-ATS @vinuta
  Scenario Outline: Verify error response when the field validation fails for Zip Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 7863456789  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | <zipCode>  | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | <zipCode>  | Suite 2        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "400" and status ""
    Then I expected error message as response from create application api for ats with status ""
      | error message                                      |
      | Zip Code should be digit with length between 5 - 9 |
    Examples:
      | zipCode      |
      | 2736sv%$     |
      | 1234         |
      | 12345678900  |
      | "          " |

  @API-CP-34281 @API-CP-34281-04 @API-CRM-Regression @API-ATS @vinuta
  Scenario Outline: Verify error response when the field validation fails for Application Member SSN
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the application id and application type from API response
    And I initiated create application member api for ats
    And I initiate create "APPLICATION MEMBER" api for existing application
      | consumerFirstName | consumerLastName | ssn   |
      | RANDOM FIRST      | RANDOM LAST      | <ssn> |
    And I POST ATS save application api
    Then I get response from application ats api with status code "400" and status ""
    Then I expected error message as response from create application api for ats with status ""
      | error message                            |
      | SSN should be digit with the length of 9 |

    Examples:
      | ssn         |
      | 2736sv%$-   |
      | 12345678    |
      | 1234567890  |
      | "         " |

  @API-CP-34281 @API-CP-34281-05 @API-CRM-Regression @API-ATS @vinuta
  Scenario Outline: Verify error response when the field validation fails for Auth Rep Zip Code
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the application id and application type from API response
    And I initiated create application member api for ats
    And I initiate create "AUTH REP" api for existing application
      | consumerFirstName | consumerLastName | addressZip |
      | RANDOM FIRST      | RANDOM LAST      | <zipCode>  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "400" and status ""
    Then I expected error message as response from create application api for ats with status ""
      | error message                                      |
      | Zip Code should be digit with length between 5 - 9 |
    Examples:
      | zipCode      |
      | 2736sv%$     |
      | 1234         |
      | 12345678900  |
      | "          " |

  @API-CP-36119 @API-CP-36119-03 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Verification of being required application signature date when app signature ind is set to Yes from API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | correspondence      | authorizedRepAppStartDate | authorizedRepAppEndDate | consumerType | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | accessType  |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Receive in Place Of | PAST TIMESTAMP            | FUTURE TIMESTAMP        | Broker       | VA Foundation        | true                   | CURRENT TIMESTAMP          | Full Access |
    And I initiate save applications api consumer 1 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "invalid"
    Then I expected error message as response from create application api for ats with status ""
      | error message                                       |
      | Application Signature Date field should be required |

  @API-CP-17381 @API-CP-17381-01 @API-CRM-Regression @API-ATS @priyal
  Scenario: Verify authorized is True when we give Auth Signature Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | correspondence | authorizedRepAppStartDate | authorizedRepAppEndDate | consumerType | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | accessType  |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Do Not Receive | PAST TIMESTAMP            | FUTURE TIMESTAMP        | Broker       | VA Foundation        | false                  | PAST TIMESTAMP             | Full Access |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "View" call
      | AUTHORIZED | true |

  @API-CP-17381 @API-CP-17381-02 @API-CRM-Regression @API-ATS @priyal
  Scenario: Verify error message is displaying for Auth Signature Date if set authorized is True and not giving Auth Signature Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | correspondence | authorizedRepAppStartDate | authorizedRepAppEndDate | consumerType | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | accessType  |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Do Not Receive | PAST TIMESTAMP            | FUTURE TIMESTAMP        | Broker       | VA Foundation        | true                   |[blank]| Full Access |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "invalid"
    Then I expected error message as response from create application api for ats with status "invalid"
      | error message                                          |
      | Authorized Rep Signature Date field should be required |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | correspondence | authorizedRepAppStartDate | authorizedRepAppEndDate | consumerType | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | accessType  |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Do Not Receive | PAST TIMESTAMP            | FUTURE TIMESTAMP        | Broker       | VA Foundation        | true                   | PAST TIMESTAMP             | Full Access |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "View" call
      | AUTH TYPE      | Broker         |
      | ACCESS TYPE    | Full Access    |
      | CORRESPONDENCE | Do Not Receive |
      | AUTHORIZED     | true           |

  @API-CP-17381 @API-CP-17381-04 @API-CRM-Regression @API-ATS @priyal
  Scenario: Verify authorized is false if we not give Auth Signature Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | correspondence | authorizedRepAppStartDate | authorizedRepAppEndDate | consumerType | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | accessType  |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Do Not Receive | PAST TIMESTAMP            | FUTURE TIMESTAMP        | Broker       | VA Foundation        | false                  |[blank]| Full Access |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "View" call
      | AUTH TYPE      | Broker         |
      | ACCESS TYPE    | Full Access    |
      | CORRESPONDENCE | Do Not Receive |
      | AUTHORIZED     | false          |
      | ORG NAME       | VA Foundation  |

  @API-CP-17381 @API-CP-17381-05 @API-CRM-Regression @API-ATS @priyal
  Scenario: Verify error message is displaying for Address if we give correspondence as "Receive in Place Of"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | correspondence      | authorizedRepAppStartDate | authorizedRepAppEndDate | consumerType | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | accessType  |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Receive in Place Of | PAST TIMESTAMP            | FUTURE TIMESTAMP        | Broker       | VA Foundation        | false                  |[blank]| Full Access |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "invalid"
    Then I expected error message as response from create application api for ats with status "invalid"
      | error message                                     |
      | Address for the Auth Rep field should be required |
    And I initiate save applications api consumer 1 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify Authorized Representative address Detail in the Application with the retrieved response with appId
      | ADDRESS LINE 1 | 9 Metro Ave |
      | ADDRESS LINE 2 | 2nd apt     |
      | CITY           | Herndon     |
      | STATE          | Virginia    |
      | ZIP            | 20171       |

  @API-CP-17381 @API-CP-17381-06 @API-CRM-Regression @API-ATS @priyal
  Scenario: Verify error message is not displaying for Address if we give correspondence as "Do Not Receive"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | correspondence | authorizedRepAppStartDate | authorizedRepAppEndDate | consumerType | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | accessType  |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Do Not Receive | PAST TIMESTAMP            | FUTURE TIMESTAMP        | Broker       | VA Foundation        | false                  |[blank]| Full Access |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"

  @API-CP-17381 @API-CP-17381-07 @API-CRM-Regression @API-ATS @priyal
  Scenario: Verify error message is displaying for Address if we give correspondence as "Receive in Addition To"
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | correspondence         | authorizedRepAppStartDate | authorizedRepAppEndDate | consumerType | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | accessType  |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Receive in Addition To | PAST TIMESTAMP            | FUTURE TIMESTAMP        | Broker       | VA Foundation        | false                  |[blank]| Full Access |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "invalid"
    Then I expected error message as response from create application api for ats with status "invalid"
      | error message                                     |
      | Address for the Auth Rep field should be required |
    And I initiate save applications api consumer 1 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify Authorized Representative address Detail in the Application with the retrieved response with appId
      | ADDRESS LINE 1 | 9 Metro Ave |
      | ADDRESS LINE 2 | 2nd apt     |
      | CITY           | Herndon     |
      | STATE          | Virginia    |
      | ZIP            | 20171       |

  @API-CP-36195 @API-CP-36154 @API-CP-36195-01 @API-CP-36154-01 @API-CRM-Regression @API-ATS @ozgen
  Scenario Outline: Saving and Getting Notes for the application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | 2021-06-03               |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerMiddleName | dateOfBirth | ssn        | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | B                  | RANDOM DOB  | RANDOM SSN | English         | Russian        |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | A                  | RANDOM DOB  | RANDOM SSN | Female     |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName | consumerMiddleName | accessType  | consumerType | correspondence      | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate       |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Full Access | Broker       | Receive in Place Of | VA Foundation        | true                   | 2022-06-03T19:54:57.274000+00:00 |
    And I initiate save applications api consumer 2 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 95 Metro St    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated Save Notes API for ats
    And I run Save Notes API for "<saveType>" level
    Then I initiated POST Notes API to retrieve note details
    And I run POST Notes API for "<saveType>" level
    And Wait for 2 seconds
    Then I verify Save Notes response includes "<saveType>" level details
    Examples:
      | saveType              |
      | application           |
      | applicationConsumerPI |
      | applicationConsumerAM |
      | applicationConsumerAR |

  @API-CP-36195 @API-CP-36195-02 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Verification of error message when note text has more than 1000 characters
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | 2021-06-02              | Renewal          | Phone     | true                          | 2021-06-03               |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerMiddleName | dateOfBirth | ssn        | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | B                  | RANDOM DOB  | RANDOM SSN | English         | Russian        |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | A                  | RANDOM DOB  | RANDOM SSN | Female     |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName | consumerMiddleName | accessType  | consumerType | correspondence      | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate       |
      | RANDOM FIRST      | RANDOM LAST      | M                  | Full Access | Broker       | Receive in Place Of | VA Foundation        | true                   | 2022-06-03T19:54:57.274000+00:00 |
    And I initiate save applications api consumer 2 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 95 Metro St    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated Save Notes API for ats
    And I run Save Notes API for "unexpected length" level
    Then I verify Save Notes response includes error message for length

  @API-CP-35548 @API-CP-35548-02 @API-CRM-Regression @API-ATS @vinuta
  Scenario Outline: API update Program Eligibility for Sub-Program for configured programs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd |
      | <applicationType> | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | genderCode | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | Male       | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | <program> |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Then Wait for 7 seconds
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | Determining |
    And I store program details for updating eligibility status
    And I get the program type from the API response
    And I initiated ats eligibility save application api
    And I initiate eligibility status API for ats with following values for "Eligible" status
      | eligibilityStatus | updatedBy | createdBy | startDate | endDate    | determinationDate | subProgramId   |
      | Eligible          | 3163      | 2492      | Future    | Future End | Today             | <subProgramId> |
    And I POST ATS Update Eligibility status application api with "success" status
    Then I verify that eligibility status is updated as expected for "Eligible"
    Examples:
      | applicationType    | program              | subProgramId |
      | Medical Assistance | Medicaid             | 10           |
      | Medical Assistance | Medicaid             | 20           |
      | Medical Assistance | Medicaid             | 30           |
      | Medical Assistance | CHIP                 | 10           |
      | Medical Assistance | CHIP                 | 40           |
      | Long Term Care     | HCBS                 | 50           |
      | Medical Assistance | Pregnancy Assistance |              |

  @API-CP-35548 @API-CP-35548-03 @API-CRM-Regression @API-ATS @vinuta
  Scenario Outline: API update Program Eligibility for Sub-Program when no subprograms configured & when no required subprogram not provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd |
      | <applicationType> | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | genderCode | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | Male       | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | <program> |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I store program details for updating eligibility status
    And I initiated ats eligibility save application api
    And I initiate eligibility status API for ats with following values for "Eligible" status
      | eligibilityStatus | updatedBy | createdBy | startDate | endDate    | determinationDate | subProgramId   |
      | Eligible          | 3163      | 2492      | Future    | Future End | Today             | <subProgramId> |
    And I POST ATS Update Eligibility status application api with "invalid" status
    Then I verify expected error messages for "<program>" status
    Examples:
      | applicationType    | program              | subProgramId |
      | Medical Assistance | Pregnancy Assistance | 40           |
      | Long Term Care     | HCBS                 |              |

  @API-CP-33090 @API-CP-33090-03 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Successful submission of application with no contact info for LTC
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Long Term Care  | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response