Feature: API: ATS Task and Link Validations Second

  @API-CP-28736 @API-CP-28736-01 @API-CP-29933-03 @API-CRM-Regression @API-ATS @burak
  Scenario: Trigger Task Completion to workflow service indicating Completion of the Task upon Send to Research
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
    Then I get response from application ats api with status code "200" and status "success"
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
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM       | Complete                 |
      | typeMM         | Member Matching          |
      | nameMM         | Task                     |
      | statusResearch | Cancelled                |
      | typeResearch   | Member Matching Research |
      | nameResearch   | Task                     |
    And I will get "Task" ID for "Member Matching Research" type from the application links response
    And I initiated bpm process get api for application data service
    Then I run bpm process get api using application id
    And I verify camunda process should be terminated
    And I initiated get task by task id "getFromApplicationLinksATS"
    And I run get task by task id API
    Then I store task details response
    Then I verify Task status and Action Taken Values for ATS created tasks
      | Action Taken | Withdrawn |
      | Status       | Cancelled |

  @API-CP-28736 @API-CP-28736-02 @API-CRM-Regression @API-ATS-blats2 @burak
  Scenario: Create Member Matching Research Task upon Send to Research for BLATS2
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
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
    When I initiate Application send to research Initiate Action API
      | action           | reasons                        | notes          |
      | Send To Research | Document/Insufficient/Multiple | 250 CHARACTERS |
    And I Post Application send to research Initiate Action API
    And I verify Application send to research Initiate Action API
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusResearch | Created                  |
      | typeResearch   | Member Matching Research |
      | nameResearch   | Task                     |
      | statusMM       | Complete                 |
      | typeMM         | Member Matching          |
      | nameMM         | Task                     |

  @API-CP-28736 @API-CP-28736-02 @API-CRM-Regression @API-ATS @burak
  Scenario: Create Member Matching Research Task upon Send to Research
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
    When I initiate Application send to research Initiate Action API
      | action           | reasons                        | notes          |
      | Send To Research | Document/Insufficient/Multiple | 250 CHARACTERS |
    And I Post Application send to research Initiate Action API
    And I verify Application send to research Initiate Action API
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusResearch | Created                  |
      | typeResearch   | Member Matching Research |
      | nameResearch   | Task                     |
      | statusMM       | Complete                 |
      | typeMM         | Member Matching          |
      | nameMM         | Task                     |

  @API-CP-29933 @API-CP-29933-04 @API-CRM-Regression @API-ATS @burak
  Scenario: Trigger Termination of Application for updating Entering Data Status to Withdrawn
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false        | true           |
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
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiated bpm process get api for application data service
    Then I run bpm process get api using application id
    And I verify camunda process should be terminated

  @API-CP-29933 @API-CP-29933-05 @API-CRM-Regression @API-ATS-blats2 @burak
  Scenario: Trigger Termination of Application for updating Determining Status to Withdrawn
    Given I will get the Authentication token for "BLATS2" in "CRM"
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
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiated bpm process get api for application data service
    Then I run bpm process get api using application id
    And I verify camunda process should be terminated

  @API-CP-29933 @API-CP-29933-05 @API-CRM-Regression @API-ATS @burak
  Scenario: Trigger Termination of Application for updating Determining Status to Withdrawn
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
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiated bpm process get api for application data service
    Then I run bpm process get api using application id
    And I verify camunda process should be terminated

  @API-CP-13947 @API-CP-13947-02 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Route Application created via ECMS to create Member Matching Task
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
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | documentId one |
    And I have prepared Authorized Representative keywords for ATS sendevent
      | AR First Name | AR Middle Initial | AR Last Name | AR Address Line 1 | AR Address City | AR Address State | AR Address Zip | AR Organization Name | AR ID Number | AR Authorization Signature Date |
      | Random First  | Random Mi         | Random Last  | Random Address    | Random City     | Random State     | Random Zip     | Random Org name      | Random Id    | today: yyyy-MM-dd               |
    And I have prepared keywordRecord Primary Individual member dynamic data values with "Case"
    And I have prepared keywordRecord Application member one dynamic data values with "F"
    Then I submit ATS sendevent request
    Given I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType        |
      | documentId | APPLICATION     | INBOUND_CORRESPONDENCE |
    And I store inbound correspondence Application Id found through link event with id "base"
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I initiated View application api for API created applications
    And I initiated get task by task id "getFromApplicationLinks"
    And I run get task by task id API
    Then I store task details response
    And I verify Member Matching task details based on created source "ECMS"

  @API-CP-31067 @API-CP-31067-01 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Retrieve Outbound Correspondence entities linked to an Application with expected order
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
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create outbound correspondence api for ATS
    Then I provide application details for POST Create Outbound Correspondence api
    And I run POST Outbound Correspondence Call API
    Then I verify new Outbound Correspondence is created and stored id
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusOC   | Requested                     |
      | typeOC     | AppId Application ID Required |
      | nameOC     | Outbound Correspondence       |
      | statusMIOC | Requested                     |
      | typeMIOC   | BL MI Missing Information     |
      | nameMIOC   | Outbound Correspondence       |
      | nameCase   | Case                          |
    And I will get "Outbound Correspondence" ID for "" type from the application links response

  @API-CP-31186 @API-CP-31186-02 @API-CRM-Regression @API-ATS @ozgen
  Scenario Outline: Setting App Status to Entering Data regardless of missing consumer details if submittedInd and interactiveInd are false
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false        | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn   |
      | <FirstName>       | <LastName>       | <DOB>       | <ssn> |
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
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | <AppMemFirstName> | <AppMemLastName> | <AppMemDOB> | <AppMemSSN> |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName  | consumerLastName  |
      | <AuthRepFirstName> | <AuthRepLastName> |
    And I initiate save applications api consumer 2 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Entering Data" on the response
    Examples:
      | FirstName | LastName | DOB   | ssn   | AppMemFirstName | AppMemLastName | AppMemDOB  | AppMemSSN | AuthRepFirstName | AuthRepLastName |
      | null      | null     | null  | null  | RANDOM FIRST    | null           | null       | null      | null             | null            |
      | empty     | empty    | empty | empty | RANDOM FIRST    | empty          | empty      | empty     | empty            | empty           |
      | null      | null     | null  | null  | null            | RANDOM LAST    | null       | null      | null             | null            |
      | empty     | empty    | empty | empty | empty           | RANDOM LAST    | empty      | empty     | empty            | empty           |
      | null      | null     | null  | null  | null            | null           | RANDOM DOB | null      | null             | null            |
      | empty     | empty    | empty | empty | empty           | empty          | RANDOM DOB | empty     | empty            | empty           |
      | null      | null     | null  | null  | RANDOM FIRST    | RANDOM LAST    | null       | null      | null             | null            |
      | empty     | empty    | empty | empty | RANDOM FIRST    | RANDOM LAST    | empty      | empty     | empty            | empty           |
      | null      | null     | null  | null  | null            | RANDOM LAST    | RANDOM DOB | null      | null             | null            |
      | empty     | empty    | empty | empty | empty           | RANDOM LAST    | RANDOM DOB | empty     | empty            | empty           |
      | null      | null     | null  | null  | RANDOM FIRST    | null           | RANDOM DOB | null      | null             | null            |
      | empty     | empty    | empty | empty | RANDOM FIRST    | empty          | RANDOM DOB | empty     | empty            | empty           |
      | null      | null     | null  | null  | null            | null           | null       | null      | RANDOM FIRST     | null            |
      | empty     | empty    | empty | empty | empty           | empty          | empty      | empty     | RANDOM FIRST     | empty           |
      | null      | null     | null  | null  | null            | null           | null       | null      | null             | RANDOM LAST     |
      | empty     | empty    | empty | empty | empty           | empty          | empty      | empty     | empty            | RANDOM LAST     |
      | null      | null     | null  | null  | null            | null           | null       | null      | RANDOM FIRST     | RANDOM LAST     |
      | empty     | empty    | empty | empty | empty           | empty          | empty      | empty     | RANDOM FIRST     | RANDOM LAST     |

  @API-CP-31186 @API-CP-31186-03 @API-CRM-Regression @API-ATS-blats2 @ozgen
  Scenario: Verification of app status as Submitted and creation of Member Matching Task after Review Incomplete Application task is completed for BLATS2
    Given I will get the Authentication token for "BLATS2" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Insufficient |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusRI | Created                       |
      | typeRI   | Review Incomplete Application |
      | nameRI   | Task                          |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationReceivedDate | applicationType    | submittedInd | interactiveInd |
      | created previously | CURRENT YYYYMMDD        | Medical Assistance | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | null |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | null |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Submitted |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created                       |
      | typeMM   | Member Matching               |
      | nameMM   | Task                          |
      | statusRI | Complete                      |
      | typeRI   | Review Incomplete Application |
      | nameRI   | Task                          |

  @API-CP-31186 @API-CP-31186-03 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Verification of app status as Determining after Review Incomplete Application task is completed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Insufficient |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusRI | Created                       |
      | typeRI   | Review Incomplete Application |
      | nameRI   | Task                          |
  #  And I initiate save applications api using previous request
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationReceivedDate | applicationType    | submittedInd | interactiveInd |
      | created previously | CURRENT YYYYMMDD        | Medical Assistance | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | null |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | null |
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusRI | Complete                      |
      | typeRI   | Review Incomplete Application |
      | nameRI   | Task                          |