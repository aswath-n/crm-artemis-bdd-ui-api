Feature: API: Save Submit View Application API

  @ATS-API-Smoke @ATS-API-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @API-CP-16710 @API-CP-15428 @ozgen @API-ATS @API-CRM-Regression
  Scenario: Added applicationConsumerStatus field on View API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify all consumers objects have applicationConsumerStatus field

  #Update Submit API to identify if application was submitted Interactively - API
  @API-CP-15844 @API-CP-15844-01.2.0 @API-CRM-Regression @API-ATS @asad
  Scenario: Verify Interactive flag for Created Applications from API -- interactive indicator null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify the interactive indicator from application details of the Created Application from "API" for interactive type "false"

  @API-CP-15844 @API-CP-15844-01.2.1 @API-CRM-Regression @API-ATS @asad
  Scenario: Verify Interactive flag for Created Applications from API -- interactive indicator false
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify the interactive indicator from application details of the Created Application from "API" for interactive type "false"

  @API-CP-15844 @API-CP-15844-01.2.2 @API-CRM-Regression @API-ATS @asad
  Scenario: Verify Interactive flag for Created Applications from API -- interactive indicator true
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify the interactive indicator from application details of the Created Application from "API" for interactive type "true"

  @API-CP-13516 @API-CP-13516-02 @API-CRM-Regression @API-ATS @munavvar @api-smoke-devops
  Scenario: API data intake validation with default submittedInd
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify application status as "Entering Data" on the response

  @API-CP-13516 @API-CP-13516-03 @API-CRM-Regression @API-ATS @munavvar
  Scenario: API data intake validation with false submittedInd
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I expect applicationStatus from application details of the Created Application as "Entering Data"

  @API-CP-13516 @API-CP-13516-04 @API-CRM-Regression @API-ATS @munavvar
  Scenario: API data intake validation with true submittedInd
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
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I expect applicationStatus from application details of the Created Application as "Determining"

  @API-CP-13516 @API-CP-13516-05 @API-CRM-Regression @API-ATS @munavvar
  Scenario: API data intake validation with invalid applicationCycle
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle |
      | Medical Assistance | CURRENT YYYYMMDD        | Old              |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I expected error message as response from create application api for ats with status "invalid"
      | error message                                                                |
      | Old is Invalid value for Application Cycle. Possible values are New, Renewal |

  @API-CP-13516 @API-CP-13516-06 @API-CRM-Regression @API-ATS @munavvar
  Scenario: API data intake validation with invalid applicationType
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | channelId | applicationReceivedDate | applicationCycle |
      | Phone     | CURRENT YYYYMMDD        | Renewal          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I expected error message as response from create application api for ats with status "invalid"
      | error message                                            |
      | Application Type is required when saving the Eligibility |

  @API-CP-13516 @API-CP-13516-07 @API-CRM-Regression @API-ATS @munavvar
  Scenario: API data intake validation with invalid channelId
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | Verbal    |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I expected error message as response from create application api for ats with status "invalid"
      | error message                                                                                                             |
      | Verbal is Invalid value for Application Channel Id. Possible values are Email, Fax, Interface, Mail, Phone, Web, Web Chat |

  @API-CP-14849 @API-CP-14849-01 @API-CP-13516 @API-CP-13516-01 @API-ATS @API-CRM-Regression @sang
  Scenario: View verify Primary Individual Details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | 2021-06-02              | Renewal          | Phone     | true                          | 2021-06-03               |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn       | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | John              | Doe              | Sr             | B                  | 1987-10-08  | 223335555 | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with program
      | CHIP     |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail  |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn       | genderCode | externalConsumerId | externalConsumerIdType | pregnancyInd | expectedBabies | expectedDueDate |
      | Sally             | Maxer            | Dr             | A                  | 1998-04-03  | 987654321 | Female     | 23456              | Internal               | true         | 2              | 2021-12-08      |
    And I initiate save applications api consumer 1 with program
      | CHIP                 |
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName | consumerMiddleName | accessType  | consumerType | correspondence      | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate       |
      | Authfirstname     | Authlastname     | M                  | Full Access | Broker       | Receive in Place Of | VA Foundation        | true                   | 2021-06-03T19:54:57.274000+00:00 |
    And I initiate save applications api consumer 2 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    Then I verify Primary Individual Details in the retrieved response with appId
      | FULL NAME            | John B Doe Sr    |
      | DOB                  | 1987-10-08       |
      | SSN                  | 223335555        |
      | GENDER               | Male             |
      | SPOKEN LANGUAGE      | Russian          |
      | WRITTEN LANGUAGE     | English          |
      | PROGRAMS             | Medicaid CHIP    |
      | COMM OPT-INS         | Email Mail Phone |
      | EXTERNAL CONSUMER ID | wr35ty789mj      |
      | EXTERNAL ID TYPE     | CHIP             |
    And I verify Primary Individual phone contact information in the retrieved response with appId
      | WORK PHONE              | 5789087677 |
      | PRIMARY PHONE INDICATOR | WORK       |
    And I verify Application Email "automation@created.com" contact information in the retrieved response with appId
    And I verify Application Residence and Mailing address information in the retrieved response with appId
      | RESIDENCE ADDRESS 1 | 9 Metro Ave      |
      | RESIDENCE ADDRESS 2 | 2nd apt          |
      | RESIDENCE CITY      | Herndon          |
      | RESIDENCE STATE     | Virginia         |
      | RESIDENCE ZIP       | 20171            |
      | RESIDENCE COUNTY    | Loudoun          |
      | MAIL ADDRESS 1      | 123 Auto Created |
      | MAIL ADDRESS 2      | Suite 2          |
      | MAIL CITY           | Houston          |
      | MAIL STATE          | Texas            |
      | MAIL ZIP            | 77055            |
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION CYCLE         | Renewal    |
      | APPLICATION RECEIVED DATE | 2021-06-02 |
      | CHANNEL                   | Phone      |
      | APPLICATION SIGNATURE     | true       |
      | SIGNATURE DATE            | 2021-06-03 |
    And I verify Application Members Detail in the Application with the retrieved response with appId from "View" call
      | FULL NAME            | Sally A Maxer Dr           |
      | DOB                  | 1998-04-03                 |
      | SSN                  | 987654321                  |
      | GENDER               | Female                     |
      | PROGRAMS             | Pregnancy Assistance, CHIP |
      | ARE YOU PREGNANT     | true                       |
      | NO BABIES EXPECTED   | 2                          |
      | EXPECTED DUE DATE    | 2021-12-08                 |
      | EXTERNAL CONSUMER ID | 23456                      |
      | EXTERNAL ID TYPE     | Internal                   |
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "View" call
      | FULL NAME                 | Authfirstname M Authlastname     |
      | AUTH TYPE                 | Broker                           |
      | ACCESS TYPE               | Full Access                      |
      | CORRESPONDENCE            | Receive in Place Of              |
      | AUTHORIZED                | true                             |
      | ORG NAME                  | VA Foundation                    |
      | AUTHORIZED SIGNATURE DATE | 2021-06-03T19:54:57.274000+00:00 |
    And I verify Authorized Representative address Detail in the Application with the retrieved response with appId
      | ADDRESS LINE 1 | 9 Metro Ave |
      | ADDRESS LINE 2 | 2nd apt     |
      | CITY           | Herndon     |
      | STATE          | Virginia    |
      | ZIP            | 20171       |

  @API-CP-14849 @API-CP-14849-02 @API-ATS @API-CRM-Regression @sang
  Scenario: Dynamic Data application api status 200 check
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
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
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 2 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"

  @API-CP-16681 @API-CP-16681-1 @API-CRM-Regression @API-ATS @sang
  Scenario: Update application with Withdrawn status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | APP DATA UPDATEDBY | 3163                  |
      | APP DATA UPDATEDON | today's date and hour |

  @API-CP-16677 @API-CP-16677-1 @API-CRM-Regression @API-ATS @sang
  Scenario: Update application with Withdrawn status and verify applicant consumer status is updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Withdrawn |
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | Withdrawn |
    And I verify Application Members Detail in the Application with the retrieved response with appId from "View" call
      | AM CONSUMER STATUS | Withdrawn |
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "View" call
      | AUTH REP CONSUMER STATUS | null |

  @API-CP-16680 @API-CP-16680-01 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Update Save/Submit API to include Application Member Status create and check
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I verify all consumers objects have applicationConsumerStatus field

  @API-CP-15480 @API-CP-15480-01 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Verify Application Priority for default value on Save App Response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I verify that application priority and application deadlineDate on response

  @API-CP-19983 @API-CP-19996 @API-CP-19983-01 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Verify View API for application priority and application deadline date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify that application priority and application deadlineDate on response

  @API-CP-16984 @API-CP-16984-01 @API-CRM-Regression @API-ATS @sang @ats-smoke
  Scenario: API update Application and Applicant status to Determining
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
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
    And I verify Application Members Detail in the Application with the retrieved response with appId from "View" call
      | AM CONSUMER STATUS | Determining |
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "View" call
      | AUTH REP CONSUMER STATUS | null |

  @API-CP-21372 @API-CP-21372-01 @sang @API-ATS @API-CRM-Regression
  Scenario: Successful Save for Long Term Care application with PRESENT Application Deadline Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationDeadlineDate |
      | Long Term Care  | CURRENT YYYYMMDD        | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"

  @API-CP-21372 @API-CP-21372-02 @sang @API-ATS @API-CRM-Regression
  Scenario: Successful Save for Long Term Care application with FUTURE Application Deadline Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationDeadlineDate |
      | Long Term Care  | CURRENT YYYYMMDD        | FUTURE YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"

  @API-CP-21372 @API-CP-21372-03 @sang @API-ATS @API-CRM-Regression
  Scenario: Unsuccessful Save for Long Term Care application with PAST Application Deadline Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationDeadlineDate |
      | Long Term Care  | CURRENT YYYYMMDD        | PAST YYYYMMDD           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "invalid"
    And I verify the ATS POST save application error message for applicationDeadlineDate marked before applicationReceivedDate

  @API-CP-21372 @API-CP-21372-04 @sang @API-ATS @API-CRM-Regression
  Scenario: Successful Save for Medical Assistance application with PRESENT Application Deadline Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationDeadlineDate |
      | Medical Assistance | CURRENT YYYYMMDD        | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"

  @API-CP-21372 @API-CP-21372-05 @sang @API-ATS @API-CRM-Regression
  Scenario: Successful Save for Medical Assistance application with FUTURE Application Deadline Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationDeadlineDate |
      | Medical Assistance | CURRENT YYYYMMDD        | FUTURE YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"

  @API-CP-21372 @API-CP-21372-06 @sang @API-ATS @API-CRM-Regression
  Scenario: Unsuccessful Save for Medical Assistance application with PAST Application Deadline Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationDeadlineDate |
      | Medical Assistance | CURRENT YYYYMMDD        | PAST YYYYMMDD           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "invalid"
    And I verify the ATS POST save application error message for applicationDeadlineDate marked before applicationReceivedDate

  @API-CP-23779 @API-CP-23779-01 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Retrieve Application Summary based on App ID - Determining Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | James             | Potter           |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | Lily              | Potter           | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn       | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | Severus           | Snape            | Sr             | B                  | 1990-10-08  | 223336666 | Male       | wr35t5f9mj         | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
    And I initiate the View Application Summary API for the Created Application
    When I run the View Application Summary API for the Created Application
    Then I get response from application ats api with status code "200" and status "success"
    And I verify response details has additional fields for View Application Summary API
      | APPLICATION STATUS | Determining |
    Then I verify Primary Individual Details in the retrieved response with appId
      | FIRST NAME                  | Severus            |
      | LAST NAME                   | Snape              |
      | PROGRAMS                    | Medicaid CHIP      |
      | PROGRAMS ELIGIBILITY STATUS | Medicaid Pending   |
      | CONSUMER ROLE               | Primary Individual |
      | CONSUMER STATUS             | Determining        |
    And I verify Primary Individual Details in the retrieved response with appId
      | PROGRAMS ELIGIBILITY STATUS | CHIP Pending |
    And I verify Application Members Detail in the Application with the retrieved response with appId from "Summary" call
      | FIRST NAME      | Lily               |
      | LAST NAME       | Potter             |
      | CONSUMER ROLE   | Application Member |
      | CONSUMER STATUS | Determining        |
      | PROGRAMS        | Medicaid           |
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "Summary" call
      | FIRST NAME      | James          |
      | LAST NAME       | Potter         |
      | CONSUMER ROLE   | Authorized Rep |
      | CONSUMER STATUS | null           |

  @CP-22374  @CP-22374-04 @burak @ats-events
  Scenario: Validation of Application Consumer Status Save Event for Primary Individual and Auth. Rep with Submitted
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                              |
      | APPLICATION_CONSUMER_STATUS_SAVE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify The "APPLICATION_CONSUMER_STATUS_SAVE_EVENT" details for the "Primary Individual"
    And I verify The "APPLICATION_CONSUMER_STATUS_SAVE_EVENT" is not  generated for "Authorized Rep"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "APPLICATION_CONSUMER_STATUS_SAVE_EVENT"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "APPLICATION_CONSUMER_STATUS_SAVE_EVENT" and subscriberId for ATS

  @CP-22374  @CP-22374-05 @burak @ats-events
  Scenario: Validation of Application Consumer Status Save Event for Application Members with Submitted
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                              |
      | APPLICATION_CONSUMER_STATUS_SAVE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify The "APPLICATION_CONSUMER_STATUS_SAVE_EVENT" details for the "Application Member"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "APPLICATION_CONSUMER_STATUS_SAVE_EVENT"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "APPLICATION_CONSUMER_STATUS_SAVE_EVENT" and subscriberId for ATS

  @API-CP-19995 @API-CP-19995-01 @API-ATS @API-CRM-Regression @burak
  Scenario: Validation of Setting the Application Deadline Date during Creation for Medical Assistance
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Then I verify The response includes deadline date and set to end of the calculated date for "Medical Assistance"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName              |
      | APPLICATION_SAVE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify The "APPLICATION_SAVE_EVENT" includes deadline date and set to end of the calculated date for "Medical Assistance"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "APPLICATION_SAVE_EVENT"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "APPLICATION_SAVE_EVENT" and subscriberId for ATS

  @API-CP-19995 @API-CP-19995-02 @API-ATS @API-CRM-Regression @burak
  Scenario: Validation of Setting the Application Deadline Date during Creation for Long Term Care
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Then I verify The response includes deadline date and set to end of the calculated date for "Long Term Care"
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName              |
      | APPLICATION_SAVE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify The "APPLICATION_SAVE_EVENT" includes deadline date and set to end of the calculated date for "Long Term Care"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "APPLICATION_SAVE_EVENT"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "APPLICATION_SAVE_EVENT" and subscriberId for ATS

  @API-CP-22989 @API-CP-22989-04 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify Application Members Eligibility Status is Pending When Applicant Status is set to "Determining" API for Medical Assistance
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName | authorizedRepAppStartDate | authorizedRepAppEndDate |
      | RANDOM FIRST      | RANDOM LAST      | PAST TIMESTAMP            | FUTURE TIMESTAMP        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
      | Pregnancy Assistance |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Medicaid |
      | Pregnancy Assistance |
    And I POST ATS save application api
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    Then I verify all the eligibility status for the programs set to "Pending" for Application Consumers
    Then I verify there is no eligibility status set for "Authorized Rep"

  @API-CP-22989 @API-CP-22989-05 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify There is No Application Members Eligibility Status Set for Application Members API for Medical Assistance
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName | authorizedRepAppStartDate | authorizedRepAppEndDate |
      | RANDOM FIRST      | RANDOM LAST      | PAST TIMESTAMP            | FUTURE TIMESTAMP        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    Then I verify there is no eligibility status set for "Primary Individual"
    Then I verify there is no eligibility status set for "Authorized Rep"
    Then I verify there is no eligibility status set for "Application Member"

  @API-CP-23899 @API-CP-23899-01 @API-CP-23898 @API-CP-23898-01 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Update Save/View Application API to include External Application ID with default null value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify that external application ID is included in API response with value ""
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify that external application ID is included in API response with value ""

  @API-CP-23899 @API-CP-23899-02 @API-CP-23898 @API-CP-23898-02 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Update Save/View Application API to include External Application ID with some valid value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate |
      | Long Term Care  | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I set external application ID to value "random" in save API
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I verify that external application ID is included in API response with value "random"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify that external application ID is included in API response with value "random"

  @API-CP-23899 @API-CP-23899-03 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Update Save Application API to fail with error when External Application ID > 36 characters
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate |
      | Long Term Care  | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I set external application ID to value "1265656536 gdfg yujshduy dyydyytytyudy sdbnxncx" in save API
    And I POST ATS save application api
    Then I expected error message as response from create application api for ats with status "error"
      | error message                                                                                                                  |
      | could not execute statement; SQL [n/a]; nested exception is org.hibernate.exception.DataException: could not execute statement |

  @API-CP-22373 @API-CP-22373-01 @ats-events @burak
  Scenario: Validation of Application Consumer Status Update Event for Determining Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
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
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                                |
      | APPLICATION_CONSUMER_STATUS_UPDATE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify The "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" details for the "Primary Individual"
    Then I verify The "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" details for the "Application Member"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" and subscriberId for ATS

  @API-CP-22373 @API-CP-22373-02 @ats-events @burak
  Scenario: Validation of Application Consumer Status Update Event for Withdrawn Status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
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
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                                |
      | APPLICATION_CONSUMER_STATUS_UPDATE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify The "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" details for the "Primary Individual"
    Then I verify The "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" details for the "Application Member"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" and subscriberId for ATS

  @API-CP-22373 @API-CP-22373-03 @ats-events @burak
  Scenario: Validation of Application Consumer Status Update Event for Duplicate Status
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
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Duplicate |
      | UPDATED BY             | 3163      |
      | CREATED BY             | 3163      |
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                                |
      | APPLICATION_CONSUMER_STATUS_UPDATE_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify The "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" details for the "Primary Individual"
    Then I verify The "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" details for the "Application Member"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "APPLICATION_CONSUMER_STATUS_UPDATE_EVENT" and subscriberId for ATS

  @API-CP-26080 @API-CP-26080-01 @vinuta @API-ATS @API-CRM-Regression
  Scenario: Added notApplyingIndicator field on Save API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | notApplying |
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify all consumers objects have notApplyingIndicator field in the response

  @API-CP-26079 @API-CP-26079-01 @vinuta @API-ATS @API-CRM-Regression
  Scenario: Member Status set to Not Applying
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | notApplying |
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
      | notApplying |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | Not Applying |
    And I verify Application Members Detail in the Application with the retrieved response with appId from "View" call
      | AM CONSUMER STATUS | Not Applying |
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "View" call
      | AUTH REP CONSUMER STATUS | null |

  @API-CP-26079 @API-CP-26079-02 @vinuta @API-ATS @API-CRM-Regression
  Scenario: Member Status set to Received when Consumer does not choose any option for programs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | Received |
    And I verify Application Members Detail in the Application with the retrieved response with appId from "View" call
      | AM CONSUMER STATUS | Received |
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "View" call
      | AUTH REP CONSUMER STATUS | null |

  @API-CP-26079 @API-CP-26079-03 @vinuta @API-ATS @API-CRM-Regression
  Scenario: Member Status set to Received when Consumer Applying for programs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | Received |
    And I verify Application Members Detail in the Application with the retrieved response with appId from "View" call
      | AM CONSUMER STATUS | Received |
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "View" call
      | AUTH REP CONSUMER STATUS | null |

  @API-CP-26079 @API-CP-26079-04 @vinuta @API-ATS @API-CRM-Regression
  Scenario: Check notApplyingIndicator for Long Term Care Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate |
      | Long Term Care  | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 1 with program
      | notApplying |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | Received |
    And I verify Application Members Detail in the Application with the retrieved response with appId from "View" call
      | AM CONSUMER STATUS | Not Applying |
    And I verify Authorized Representative Detail in the Application with the retrieved response with appId from "View" call
      | AUTH REP CONSUMER STATUS | null |

  @API-CP-22999 @API-CP-22999-01 @API-CRM-Regression @API-ATS @ozgen
  Scenario: API update Program Eligibility for Eligible Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
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
      | eligibilityStatus | updatedBy | createdBy | startDate | endDate    | determinationDate | subProgramId |
      | Eligible          | 3163      | 2492      | Future    | Future End | Today             | 20           |
    And I POST ATS Update Eligibility status application api with "success" status
    Then I verify that eligibility status is updated as expected for "Eligible"

  @API-CP-22999 @API-CP-22999-02 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Verification of Error Messages for Eligible Program Eligibility Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
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
      | eligibilityStatus | updatedBy | createdBy | subProgramId |
      | Eligible          | 3163      | 2492      | 10           |
    Then I provide denial reason for eligibility
      | Over Age |
    And I POST ATS Update Eligibility status application api with "invalid" status
    Then I verify expected error messages for "Eligible" status

  @API-CP-22999 @API-CP-22999-03 @API-CRM-Regression @API-ATS @ozgen
  Scenario: API update Program Eligibility for NOT Eligible Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
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
    And I initiate eligibility status API for ats with following values for "Not Eligible" status
      | eligibilityStatus | updatedBy | createdBy | startDate | endDate    | determinationDate |
      | Not Eligible      | 3163      | 2492      | Future    | Future End | Today             |
    Then I provide denial reason for eligibility
      | Other Insurance   |
      | Over Age          |
      | Medicaid Eligible |
      | Over Income       |
      | Already Eligible  |
    And I POST ATS Update Eligibility status application api with "success" status
    Then I verify that eligibility status is updated as expected for "Not Eligible"

  @API-CP-22999 @API-CP-22999-04 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Verification of Error Messages for Not Eligible Program Eligibility Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
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
    And I initiate eligibility status API for ats with following values for "Not Eligible" status
      | eligibilityStatus | updatedBy | createdBy |
      | Not Eligible      | 3163      | 2492      |
    And I POST ATS Update Eligibility status application api with "invalid" status
    Then I verify expected error messages for "Not Eligible" status

  @API-CP-22999 @API-CP-22999-05 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Verification of Error Messages for Update Wrong Denial Reasons values for Not Eligible Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
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
    And I initiate eligibility status API for ats with following values for "Not Eligible" status
      | eligibilityStatus | updatedBy | createdBy | determinationDate |
      | Not Eligible      | 3163      | 2492      | Today             |
    Then I provide denial reason for eligibility
      | Unexpected Reason |
    And I POST ATS Update Eligibility status application api with "invalid" status
    Then I verify expected error messages for "Unexpected Reason" status

  @API-CP-22999 @API-CP-22999-06 @API-CRM-Regression @API-ATS @ozgen
  Scenario: API update Program Eligibility for Pending Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
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
    And I initiate eligibility status API for ats with following values for "Pending" status
      | eligibilityStatus | updatedBy | createdBy | startDate | endDate    |
      | Pending           | 3163      | 2492      | Future    | Future End |
    And I POST ATS Update Eligibility status application api with "success" status
    Then I verify that eligibility status is updated as expected for "Pending"

  @API-CP-22999 @API-CP-22999-07 @API-CRM-Regression @API-ATS @ozgen
  Scenario: API update Program Eligibility for Wait List Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
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
    And I initiate eligibility status API for ats with following values for "Wait List" status
      | eligibilityStatus | updatedBy | createdBy | startDate | endDate    |
      | Wait List         | 3163      | 2492      | Future    | Future End |
    And I POST ATS Update Eligibility status application api with "success" status
    Then I verify that eligibility status is updated as expected for "Wait List"

  @API-CP-22999 @API-CP-22999-08 @API-CRM-Regression @API-ATS @ozgen
  Scenario: API update Program Eligibility for Unexpected Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
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
    And I initiate eligibility status API for ats with following values for "Unexpected" status
      | eligibilityStatus | updatedBy | createdBy | startDate | endDate    |
      | Unexpected        | 3163      | 2492      | Future    | Future End |
    And I POST ATS Update Eligibility status application api with "invalid" status
    Then I verify expected error messages for "Unexpected" status

  @API-CP-22999 @API-CP-22999-09 @API-CRM-Regression @API-ATS @ozgen
    #need refactoring for this one PRIORITY
  Scenario: Verification of Errors when wrong format of date fields are provided on update Program Eligibility payload
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
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
    And I initiate eligibility status API for ats with following values for "Unexpected" status
      | eligibilityStatus | startDate  | endDate    | determinationDate |
      | Eligible          | 12-09-2020 | 34-89-9876 | 2021-qa-sb        |
    And I POST ATS Update Eligibility status application api with "" status
    Then I verify the response status code "400"


