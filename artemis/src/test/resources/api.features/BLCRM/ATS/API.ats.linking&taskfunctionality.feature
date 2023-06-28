Feature: API: ATS Task and Link Validations

  @API-CP-21313 @API-CP-21313-01 @API-CRM-Regression @API-ATS-blats2 @burak
    #needs refactoring for link verification
  Scenario: Validation of Retrieve Linked Entites associated with an application endpoint
    Given I will get the Authentication token for "BLATS2" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS    | Duplicate |
      | UPDATED BY                | 3163      |
      | CREATED BY                | 3163      |
      | DUPLICATED APPLICATION ID | -1        |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS    | Duplicate |
      | UPDATED BY                | 3163      |
      | CREATED BY                | 3163      |
      | DUPLICATED APPLICATION ID | -2        |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | status | Submitted              |
      | type   | Medical Assistance New |
      | name   | Application            |
    Then I verify entities are sorted on reverse chronological order based on link create date "reverse"

  @API-CP-16070 @API-CP-28625 @API-CP-31186 @API-CP-13947 @API-CP-16070-01 @API-CP-28625-01 @API-CP-13947-02 @API-CP-31186-01 @API-CRM-Regression @API-ATS @ozgen
  Scenario Outline: Check Minimum Clearance for Member Matching Task status based on submittedInd and interactiveInd including Camunda refactoring - Route Application from web interface
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
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    And I initiated get task by task id "getFromApplicationLinksATS"
    And I run get task by task id API
    Then I store task details response
    And I verify Member Matching task details based on created source "API"
    Examples:
      | submittedInd | interactiveInd | status                |
      | true         | false          | Targets Unidentified  |
      | true         | null           | Targets Unidentified  |

  @API-CP-16070 @API-CP-28625 @API-CP-31186 @API-CP-13947 @API-CP-16070-01 @API-CP-28625-01 @API-CP-13947-02 @API-CP-31186-01 @API-CRM-Regression @API-ATS-blats2 @ozgen
  Scenario Outline: Check Minimum Clearance for Member Matching Task status based on submittedInd and interactiveInd including Camunda refactoring - Route Application from web interface
    Given I will get the Authentication token for "BLATS2" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd   | interactiveInd   |
      | Medical Assistance | CURRENT YYYYMMDD        | <submittedInd> | <interactiveInd> |
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
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    And I initiated get task by task id "getFromApplicationLinksATS"
    And I run get task by task id API
    Then I store task details response
    And I verify Member Matching task details based on created source "API"
    Examples:
      | submittedInd | interactiveInd | status     |
      | true         | false          | Submitted  |
      | true         | null           | Submitted  |

  @API-CP-31186 @API-CP-31186-01.2 @API-CRM-Regression @API-ATS @ozgen
  Scenario Outline: Check application status based on submittedInd as false and interactiveInd as false
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd   | interactiveInd   |
      | Medical Assistance | CURRENT YYYYMMDD        | <submittedInd> | <interactiveInd> |
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
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    Examples:
      | submittedInd | interactiveInd | status        |
      | false        | false          | Entering Data |
      | false        | null           | Entering Data |

  @API-CP-16070 @API-CP-16070-03 @API-CRM-Regression @API-ATS @ozgen
  Scenario Outline: Check Minimum Clearance for Negative Member Matching and Positive Review Incomplete Application Task scenarios based on Consumer Info
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn   |
      | <FirstName>       | <LastName>       | <DOB>       | <ssn> |
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
    And I verify that there is no "Member Matching" task is created
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Insufficient" on the response
    Examples:
      | FirstName    | LastName    | DOB        | ssn   |
      | RANDOM FIRST | null        | null       | null  |
      | RANDOM FIRST | empty       | empty      | empty |
      | null         | RANDOM LAST | null       | null  |
      | empty        | RANDOM LAST | empty      | empty |
      | null         | null        | RANDOM DOB | null  |
      | empty        | empty       | RANDOM DOB | empty |
      | RANDOM FIRST | RANDOM LAST | null       | null  |
      | RANDOM FIRST | RANDOM LAST | empty      | empty |
      | null         | RANDOM LAST | RANDOM DOB | null  |
      | empty        | RANDOM LAST | RANDOM DOB | empty |

  @API-CP-16070 @API-CP-16070-04 @API-CP-21533 @API-CRM-Regression @API-ATS @ozgen
  Scenario Outline: Check Minimum Clearance for Member Matching Task status with positive scenarios based on Consumer Info for BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn   | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | <ssn> | Male       |
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
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn   | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | <ssn> | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn   |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | <ssn> |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and run Get Application Links Call
    And I verify that there is no "Review Incomplete Application" task is created
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Targets Unidentified" on the response
    Examples:
      | ssn        |
      | null       |
      | empty      |

  @API-CP-16070 @API-CP-16070-04 @API-CP-21533 @API-CRM-Regression @API-ATS-blats2 @ozgen
  Scenario Outline: Check Minimum Clearance for Member Matching Task status with positive scenarios based on Consumer Info for BLATS2
    Given I will get the Authentication token for "BLATS2" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn   |
      | <FirstName>       | <LastName>       | <DOB>       | <ssn> |
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
    And I verify that there is no "Review Incomplete Application" task is created
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Submitted" on the response
    Examples:
      | FirstName    | LastName    | DOB        | ssn        |
      | null         | null        | null       | RANDOM SSN |
      | empty        | empty       | empty      | RANDOM SSN |

 # @API-CP-16070 @API-CP-16070-04 @API-CP-21533 @API-CRM-Regression @API-ATS @ozgen
    #this is not valid scenario for BLCRM, because to be able to submit application and get links, we need to have FN and LN based on CP-21533 AC 2.0
  Scenario Outline: Check Minimum Clearance for Member Matching Task status with positive scenarios based on Consumer Info for BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           |
      | <FirstName>       | <LastName>       | <DOB>       | DUPLICATE SSN |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
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
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           |
      | <FirstName>       | <LastName>       | <DOB>       | DUPLICATE SSN |
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
    And I verify that there is no "Review Incomplete Application" task is created
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Targets Unidentified" on the response
    Examples:
      | FirstName    | LastName    | DOB        | ssn        |
      | null         | null        | null       | RANDOM SSN |
      | empty        | empty       | empty      | RANDOM SSN |

  @API-CP-16070 @API-CP-16070-05 @API-CP-21533 @API-CP-21533-01 @API-CRM-Regression @API-ATS-BLATS2 @ozgen #this scenario will be specific to BLATS2 based on CP-21533
  Scenario Outline: Check Minimum Clearance for Member Matching Task status with positive scenarios based on Two Consumer Info
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
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
    And I initiate and run Get Application Links Call
    And I verify that there is no "Review Incomplete Application" task is created
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Submitted" on the response
    Examples:
      | FirstName | LastName | DOB   | ssn   | AppMemFirstName | AppMemLastName | AppMemDOB  | AppMemSSN  | AuthRepFirstName | AuthRepLastName |
      | null      | null     | null  | null  | null            | null           | null       | Random SSN | null             | null            |
      | empty     | empty    | empty | empty | empty           | empty          | empty      | Random SSN | empty            | empty           |
      | null      | null     | null  | null  | RANDOM FIRST    | RANDOM LAST    | RANDOM DOB | null       | null             | null            |
      | empty     | empty    | empty | empty | RANDOM FIRST    | RANDOM LAST    | RANDOM DOB | empty      | empty            | empty           |

  @API-CP-16070 @API-CP-16070-06 @API-CRM-Regression @API-ATS @ozgen # empty field scenarios will fail due to CP-28943
  Scenario Outline: Check Minimum Clearance for Negative Member Matching and Positive Review Incomplete Application Task scenarios based on TWO Consumer Info
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
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
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Insufficient" on the response
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

  @API-CP-23924 @API-CP-23924-01 @API-CP-29933-01 @API-CRM-Regression @API-ATS-blats2 @burak
  Scenario: Retrieve Tasks and Correspondence entities linked to an Application Member Matching Task for BLATS2
    #Trigger Task Cancellation for Member Matching Task Outside the context of the task
    #Trigger Termination of Application for updating Submitted Status to Withdrawn
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
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Cancelled       |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I initiated bpm process get api for application data service
    Then I run bpm process get api using application id
    And I verify camunda process should be terminated
    And I initiated get task by task id "getFromApplicationLinksATS"
    And I run get task by task id API
    Then I store task details response
    Then I verify Task status and Action Taken Values for ATS created tasks
      | Action Taken | Withdrawn |
      | Status       | Cancelled |

  @API-CP-23924 @API-CP-23924-01 @API-CP-29933-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Retrieve Tasks and Correspondence entities linked to an Application Member Matching Task
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
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Cancelled       |
      | typeMM   | Member Matching |
      | nameMM   | Task            |
    And I will get "Task" ID for "Member Matching" type from the application links response
    And I initiated bpm process get api for application data service
    Then I run bpm process get api using application id
    And I verify camunda process should be terminated
    And I initiated get task by task id "getFromApplicationLinksATS"
    And I run get task by task id API
    Then I store task details response
    Then I verify Task status and Action Taken Values for ATS created tasks
      | Action Taken | Withdrawn |
      | Status       | Cancelled |

  @API-CP-23924 @API-CP-23924-02 @API-CP-29933-02 @API-CRM-Regression @API-ATS @burak @ats-smoke
  Scenario: Retrieve Tasks and Correspondence entities linked to an Application Review Incomplete Task
         #Trigger Task Cancellation for Review Incomplete Task Outside the context of the task
  #Trigger Termination of Application for updating Insufficient Status to Withdrawn
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
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusRI | Cancelled                     |
      | typeRI   | Review Incomplete Application |
      | nameRI   | Task                          |
    And I initiated bpm process get api for application data service
    Then I run bpm process get api using application id
    And I verify camunda process should be terminated

  @API-CP-23924 @API-CP-23924-03 @API-CRM-Regression @API-ATS @burak
  Scenario: Retrieve Tasks and Correspondence entities linked to an Application Inbound Correspondence (Multiple)
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
    And I have prepared default sendevent request data for ATS from file "events/ATSSendEvent.json"
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      |
      | documentId     | today: yyyy-MM-dd |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | SetId          |
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | documentId one |
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
    And I will verify the response details for get linked applications-tasks  response
      | statusRI | Created                       |
      | typeRI   | Review Incomplete Application |
      | nameRI   | Task                          |
      | statusIC | RECEIVED                      |
      | typeIC   | maersk Application           |
      | nameIC   | Inbound Correspondence        |

  @API-CP-28625 @API-CP-28625-02 @API-CRM-Regression @API-ATS-blats2 @ozgen
  Scenario Outline: Check Minimum Clearance for Application status after Camunda Refactoring Implementation for Negative Scenarios for BLATS2
    Given I will get the Authentication token for "BLATS2" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd   | interactiveInd   |
      | Medical Assistance | CURRENT YYYYMMDD        | <submittedInd> | <interactiveInd> |
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
    And I verify that there is no "Member Matching" task is created
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    Examples:
      | submittedInd | interactiveInd | status        |
      | false        | true           | Entering Data |
      | true         | true           | Submitted     |

  @API-CP-28625 @API-CP-28625-02 @API-CRM-Regression @API-ATS @ozgen
  Scenario Outline: Check Minimum Clearance for Application status after Camunda Refactoring Implementation for Negative Scenarios
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd   | interactiveInd   |
      | Medical Assistance | CURRENT YYYYMMDD        | <submittedInd> | <interactiveInd> |
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
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd   |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | <submittedInd> |
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
    And I verify that there is no "Member Matching" task is created
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    Examples:
      | submittedInd | interactiveInd | status               |
      | false        | true           | Entering Data        |
      | true         | true           | Targets Unidentified |

   @API-CP-29192 @API-CP-29192-01 @API-CRM-Regression @API-ATS @burak
   Scenario: Validation of Providing Data for Application item for Standard Link component Determining Status
      Given I will get the Authentication token for "BLCRM" in "CRM"
      And I initiated create application api for ats
      And I initiate create application api with a "APPLICATION MEMBER"
      And I initiate save applications api with application level key values
        | applicationType    | applicationReceivedDate | applicationCycle | submittedInd |
        | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | false        |
      And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
         | consumerFirstName | consumerLastName | dateOfBirth |
         | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
      And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
         | consumerFirstName | consumerLastName | dateOfBirth |
         | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
      And I initiate save applications api consumer 0 with program
         | Medicaid |
      And I initiate save applications api consumer 1 with program
         | Medicaid |
     And I initiate save applications api consumer 0 with consumerOptInInformation
       | Mail |
       | Phone |
     And I initiate save applications api consumer 0 with applicationConsumerPhone
       | phoneType       | phoneNumber  | primaryContactTypeInd |
       | cellPhoneNumber | RANDOM PHONE | true                  |
     And I POST ATS save application api
      Then I get response from application ats api with status code "200" and status "success"
      And I initiate and run get application details for link component api
      And I verify get application details for link component details response for values
         | status     | Entering Data              |
         | type       | Medical Assistance Renewal |
         | statusDate | CURRENT YYYYMMDD           |
      And I initiate and Post created application with update status API with following values
         | NEW APPLICATION STATUS | Determining |
         | UPDATED BY             | 3163        |
         | CREATED BY             | 3163        |
      And I initiate and run get application details for link component api
      And I verify get application details for link component details response for values
         | status     | Determining                |
         | type       | Medical Assistance Renewal |
         | statusDate | CURRENT YYYYMMDD           |

  @API-CP-29192 @API-CP-29192-01 @API-CRM-Regression @API-ATS-blats2 @burak
  Scenario: Validation of Providing Data for Application item for Standard Link component Determining Status for BLATS2
    Given I will get the Authentication token for "BLATS2" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run get application details for link component api
    And I verify get application details for link component details response for values
      | status     | Submitted                  |
      | type       | Medical Assistance Renewal |
      | statusDate | CURRENT YYYYMMDD           |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And I initiate and run get application details for link component api
    And I verify get application details for link component details response for values
      | status     | Determining                |
      | type       | Medical Assistance Renewal |
      | statusDate | CURRENT YYYYMMDD           |

   @API-CP-29192 @API-CP-29192-02 @API-CRM-Regression @API-ATS-blats2 @burak
   Scenario: Validation of Providing Data for Application item for Standard Link component Insufficient-->Duplicate Status
     #not valid scenario for BLCRM because you have to provide missing details to move on next app status
      Given I will get the Authentication token for "BLATS2" in "CRM"
      When I initiated create application api for ats
      And I initiate save applications api with application level key values
         | applicationType | applicationReceivedDate | submittedInd | applicationCycle |
         | Long Term Care  | CURRENT YYYYMMDD        | true         | New              |
      And I initiate save applications api consumer 0 with program
         | HCBS |
      And I POST ATS save application api
      Then I get response from application ats api with status code "200" and status "success"
      And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
      And I initiate and run get application details for link component api
      And I verify get application details for link component details response for values
         | status     | Insufficient       |
         | type       | Long Term Care New |
         | statusDate | CURRENT YYYYMMDD   |
      And I initiate and Post created application with update status API with following values
         | NEW APPLICATION STATUS | Duplicate |
         | UPDATED BY             | 3163      |
         | CREATED BY             | 3163      |
      And I initiate and run get application details for link component api
      And I verify get application details for link component details response for values
         | status     | Duplicate          |
         | type       | Long Term Care New |
         | statusDate | CURRENT YYYYMMDD   |

   @API-CP-29192 @API-CP-29192-03 @API-CRM-Regression @API-ATS @burak
   Scenario: Validation of Providing Data for Application item for Standard Link component Entering Data-->Determining Status
      Given I will get the Authentication token for "BLCRM" in "CRM"
      When I initiated create application api for ats
      And I initiate save applications api with application level key values
         | applicationType | applicationReceivedDate | submittedInd | applicationCycle | interactiveInd |
         | Long Term Care  | CURRENT YYYYMMDD        | false        | Renewal          | true           |
     And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
       | consumerFirstName | consumerLastName | dateOfBirth |
       | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
      And I initiate save applications api consumer 0 with program
         | HCBS |
      And I POST ATS save application api
      Then I get response from application ats api with status code "200" and status "success"
      And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
      And I initiate and run get application details for link component api
      And I verify get application details for link component details response for values
         | status     | Entering Data          |
         | type       | Long Term Care Renewal |
         | statusDate | CURRENT YYYYMMDD       |
      And I initiate and Post created application with update status API with following values
         | NEW APPLICATION STATUS | Determining |
         | UPDATED BY             | 3163        |
         | CREATED BY             | 3163        |
      And I initiate and run get application details for link component api
      And I verify get application details for link component details response for values
         | status     | Determining            |
         | type       | Long Term Care Renewal |
         | statusDate | CURRENT YYYYMMDD       |

   @API-CP-29192 @API-CP-29192-04 @API-CRM-Regression @API-ATS @burak
   Scenario:Validation of Providing Data for Application item for Standard Link component for Inbound Created Application
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
      And I initiate and run get application details for link component api
      And I verify get application details for link component details response for values
         | status     | Insufficient           |
         | type       | Medical Assistance New |
         | statusDate | CURRENT YYYYMMDD       |

  @API-CP-28736 @API-CP-28736-01 @API-CP-29933-03 @API-CRM-Regression @API-ATS-blats2 @burak
  Scenario: Trigger Task Completion to workflow service indicating Completion of the Task upon Send to Research for BLATS2
    #Trigger Task Cancellation for Member Matching Research Task Outside the context of the task
    #Trigger termination of the application for Submitted Status
    Given I will get the Authentication token for "BLATS2" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true        |
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
      | statusResearch | Cancelled                 |
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
