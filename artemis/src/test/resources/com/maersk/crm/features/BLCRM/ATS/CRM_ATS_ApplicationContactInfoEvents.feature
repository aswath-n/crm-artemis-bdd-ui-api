Feature: ATS Application Contact Info Related Events

  @CP-19882 @CP-19882-01 @CP-16833 @CP-16833-01 @vinuta @ats-events
  Scenario Outline: Verify APPLICANT_ADDRESS_UPDATE_EVENT and APPLICANT_ADDRESS_DELETE_EVENT is generated for Primary Individual - Residence Address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | B                  | RANDOM DOB  | RANDOM SSN | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | workPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 1 | random   |
      | RESIDENCE ADDRESS LINE 2 | random   |
      | RESIDENCE CITY           | Canaan   |
      | RESIDENCE STATE          | New York |
      | RESIDENCE ZIP CODE       | 12029    |
      | RESIDENCE COUNTY         | Columbia |
    And I click on Save button on Create Application Page
    Then Wait for 3 seconds
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName1>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_ADDRESS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName1>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName1>" and subscriberId for ATS
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 1 |[blank]|
      | RESIDENCE ADDRESS LINE 2 |[blank]|
      | RESIDENCE CITY           |[blank]|
      | RESIDENCE STATE          |[blank]|
      | RESIDENCE ZIP CODE       |[blank]|
      | RESIDENCE COUNTY         |[blank]|
    And I click on Save button on Create Application Page
    Then Wait for 2 seconds
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName2>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_ADDRESS_DELETE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName2>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName2>" and subscriberId for ATS
    Examples:
      | eventName1                     | eventName2                     | module      | eventType | projectName | number of events |
      | APPLICANT_ADDRESS_UPDATE_EVENT | APPLICANT_ADDRESS_DELETE_EVENT | APPLICATION |[blank]|             | 1                |

  @CP-19882 @CP-19882-02 @CP-16833 @CP-16833-02 @vinuta @ats-events
  Scenario Outline: Verify APPLICANT_ADDRESS_UPDATE_EVENT and APPLICANT_ADDRESS_DELETE_EVENT is generated for Primary Individual - Mailing Address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | workPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | MAILING ADDRESS LINE 1 | 1008 Mountain Drive |
      | MAILING ADDRESS LINE 2 | 1008 Mountain Lane  |
      | MAILING CITY           | Reston              |
      | MAILING STATE          | Virginia            |
      | MAILING ZIP CODE       | 20190               |
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName1>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_ADDRESS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName1>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName1>" and subscriberId for ATS
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | MAILING ADDRESS LINE 1 |[blank]|
      | MAILING ADDRESS LINE 2 |[blank]|
      | MAILING CITY           |[blank]|
      | MAILING STATE          |[blank]|
      | MAILING ZIP CODE       |[blank]|
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName2>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_ADDRESS_DELETE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName2>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName2>" and subscriberId for ATS
    Examples:
      | eventName1                     | eventName2                     | module      | eventType | projectName | number of events |
      | APPLICANT_ADDRESS_UPDATE_EVENT | APPLICANT_ADDRESS_DELETE_EVENT | APPLICATION |[blank]|             | 1                |

  @CP-16833 @CP-16833-03 @vinuta @ats-events
  Scenario Outline: Verify APPLICANT_ADDRESS_UPDATE_EVENT is generated for Primary Individual when Mailing Address is set same as Residence Address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    And I click on the Same As Residence Address checkbox in Primary Individual Application Create
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_ADDRESS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                      | module      | eventType | projectName | number of events |
      | APPLICANT_ADDRESS_UPDATE_EVENT | APPLICATION |[blank]|             | 1                |

  @CP-19882 @CP-19882-03 @CP-16833 @CP-16833-04 @vinuta @ats-events
  Scenario Outline: Verify APPLICANT_ADDRESS_UPDATE_EVENT and APPLICANT_ADDRESS_DELETE_EVENT is generated for Auth Rep
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Fax       | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | workPhoneNumber | RANDOM PHONE | true                  |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | accessType  | consumerType | correspondence | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate             | authorizedRepAppStartDate | consumerStatus |
      | Authfirstname     | Authlastname     | M                  | Full Access | Broker       | Receive in Place Of| VA Foundation        | true                   | CURRENT TIMESTAMP | CURRENT TIMESTAMP         | ACTIVE         |
    And I initiate save applications api consumer 1 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    Then I fill in the following Authorized Representative values
      | ADDRESS LINE 1 | ALPHA NUMERIC |
      | ADDRESS LINE 2 | ALPHA NUMERIC |
      | CITY           | Roswell       |
      | STATE          | Georgia       |
      | ZIP CODE       | 30075         |
    And I click save button to save Authorized Representative
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName1>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_ADDRESS_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName1>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName1>" and subscriberId for ATS
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    Then I fill in the following Authorized Representative values
      | ADDRESS LINE 1 |[blank]|
      | ADDRESS LINE 2 |[blank]|
      | CITY           |[blank]|
      | STATE          |[blank]|
      | ZIP CODE       |[blank]|
    And I click save button to save Authorized Representative
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName2>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_ADDRESS_DELETE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName2>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName2>" and subscriberId for ATS
    Examples:
      | eventName1                     | eventName2                     | module      | eventType | projectName | number of events |
      | APPLICANT_ADDRESS_UPDATE_EVENT | APPLICANT_ADDRESS_DELETE_EVENT | APPLICATION |[blank]|             | 1                |

  @CP-19882 @CP-19882-04 @CP-16833 @CP-16833-05 @vinuta @ats-events
  Scenario Outline: Verify APPLICANT_PHONE_UPDATE_EVENT and APPLICANT_PHONE_DELETE_EVENT is generated for Cell Phone Number
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName1>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_PHONE_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName1>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName1>" and subscriberId for ATS
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER |[blank]|
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName2>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_PHONE_DELETE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName2>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName2>" and subscriberId for ATS
    Examples:
      | eventName1                   | eventName2                   | module      | eventType | projectName | number of events |
      | APPLICANT_PHONE_UPDATE_EVENT | APPLICANT_PHONE_DELETE_EVENT | APPLICATION |[blank]|             | 1                |

  @CP-19882 @CP-19882-05 @CP-16833 @CP-16833-06 @vinuta @ats-events
  Scenario Outline: Verify APPLICANT_PHONE_UPDATE_EVENT and APPLICANT_PHONE_DELETE_EVENT is generated for Home Phone Number
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | homePhoneNumber | RANDOM PHONE | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | HOME PHONE NUMBER | Random Numeric 10 |
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName1>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_PHONE_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName1>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName1>" and subscriberId for ATS
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | HOME PHONE NUMBER |[blank]|
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName2>" and "<module>" and "<correlation>"
    Then I verify "APPLICANT_PHONE_DELETE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName2>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName2>" and subscriberId for ATS
    Examples:
      | eventName1                   | eventName2                   | module      | eventType | projectName | number of events |
      | APPLICANT_PHONE_UPDATE_EVENT | APPLICANT_PHONE_DELETE_EVENT | APPLICATION |[blank]|             | 1                |

  @CP-19882 @CP-19882-06 @CP-16833 @CP-16833-07 @vinuta @ats-events
  Scenario Outline: Verify APPLICANT_PHONE_UPDATE_EVENT and APPLICANT_PHONE_DELETE_EVENT is generated for Work Phone Number
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | workPhoneNumber | RANDOM PHONE | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | WORK PHONE NUMBER | Random Numeric 10 |
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName1>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_PHONE_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName1>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName1>" and subscriberId for ATS
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | WORK PHONE NUMBER |[blank]|
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName2>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_PHONE_DELETE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName2>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName2>" and subscriberId for ATS
    Examples:
      | eventName1                   | eventName2                   | module      | eventType | projectName | number of events |
      | APPLICANT_PHONE_UPDATE_EVENT | APPLICANT_PHONE_DELETE_EVENT | APPLICATION |[blank]|             | 1                |

  @CP-19882 @CP-19882-07 @CP-16833 @CP-16833-08 @vinuta @ats-events
  Scenario Outline: Verify APPLICANT_PHONE_UPDATE_EVENT and APPLICANT_PHONE_DELETE_EVENT is generated for Fax Number
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber  | primaryContactTypeInd |
      | faxNumber | RANDOM PHONE | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | FAX NUMBER | Random Numeric 10 |
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName1>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_PHONE_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName1>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName1>" and subscriberId for ATS
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | FAX NUMBER |[blank]|
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName2>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "APPLICANT_PHONE_DELETE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName2>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName2>" and subscriberId for ATS
    Examples:
      | eventName1                   | eventName2                   | module      | eventType | projectName | number of events |
      | APPLICANT_PHONE_UPDATE_EVENT | APPLICANT_PHONE_DELETE_EVENT | APPLICATION |[blank]|             | 1                |

  @CP-16833 @CP-16833-09 @vinuta @ats-events
  Scenario Outline: Verify APPLICANT_PHONE_UPDATE_EVENT is generated for primary phone indicator is updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType               | phoneNumber  | primaryContactTypeInd |
      | <initialPrimaryContact> | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType               | phoneNumber  | primaryContactTypeInd |
      | <updatedPrimaryContact> | RANDOM PHONE | false                 |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | SET PRIMARY CONTACT | <PhoneType> |
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    Then I verify "<updatedPrimaryContact>" has primaryIndicator set to "true"
    And I verify "<initialPrimaryContact>" has primaryIndicator set to "false"
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | initialPrimaryContact | updatedPrimaryContact | PhoneType         | eventName                    | module      | eventType | projectName | number of events |
      | cellPhoneNumber       | homePhoneNumber       | HOME PHONE NUMBER | APPLICANT_PHONE_UPDATE_EVENT | APPLICATION |[blank]|             | 2                |
      | homePhoneNumber       | workPhoneNumber       | WORK PHONE NUMBER | APPLICANT_PHONE_UPDATE_EVENT | APPLICATION |[blank]|             | 2                |
      | workPhoneNumber       | cellPhoneNumber       | CELL PHONE NUMBER | APPLICANT_PHONE_UPDATE_EVENT | APPLICATION |[blank]|             | 2                |

  @CP-19882 @CP-19882-08 @CP-16833 @CP-16833-10 @vinuta @ats-events
  Scenario Outline: Verify APPLICANT_EMAIL_UPDATE_EVENT and APPLICANT_EMAIL_DELETE_EVENT is generated for Primary Individual
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Male       | wr35ty789mj        | CHIP                   | English         | Russian        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | workPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I navigate to application tab page
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | newEmail123@maersk.com |
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName1>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    And I verify "APPLICANT_EMAIL_UPDATE_EVENT" details in the event payload
    When I initiate subscribers POST API
    And I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName1>"
    When I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName1>" and subscriberId for ATS
    When I click on the Edit button for the Primary Individual Details
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL |[blank]|
    And I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName2>" and "<module>" and "<correlation>"
    Then I verify number of events generated is "<number of events>"
    And I verify "APPLICANT_EMAIL_DELETE_EVENT" details in the event payload
    When I initiate subscribers POST API
    And I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName2>"
    When I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName2>" and subscriberId for ATS
    Examples:
      | eventName1                   | eventName2                   | module      | eventType | projectName | number of events |
      | APPLICANT_EMAIL_UPDATE_EVENT | APPLICANT_EMAIL_DELETE_EVENT | APPLICATION |[blank]|             | 1                |

  @CP-16455 @CP-16455-01 @ats-events @vinuta
  Scenario Outline: Verify APPLICANT_ADDRESS_SAVE_EVENT for Residence Address of Primary Individual is generated
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I choose "CHIP" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME       | Alphabetic 30    |
      | MIDDLE INITIAL   | Alphabetic 1     |
      | LAST NAME        | Alphabetic 30    |
      | DOB              | random past date |
      | GENDER           | Female           |
      | ARE YOU PREGNANT | No               |
      | SSN              | Numeric 9        |
    And I choose Communication Opt In Information by the following list
      | Mail |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 1 | random     |
      | RESIDENCE ADDRESS LINE 2 | random     |
      | RESIDENCE CITY           | Metropolis |
      | RESIDENCE STATE          | Illinois   |
      | RESIDENCE ZIP CODE       | 62960      |
      | RESIDENCE COUNTY         | Massac     |
    And I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICANT_ADDRESS_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                    | module      | eventType   | projectName |
      | APPLICANT_ADDRESS_SAVE_EVENT | APPLICATION | application |[blank]|

  @CP-16455 @CP-16455-02 @ats-events @vinuta
  Scenario Outline: Verify APPLICANT_ADDRESS_SAVE_EVENT for Mailing Address of Primary Individual is generated
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "LONG TERM CARE" application page
    Then I choose "HCBS" as program type
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME       | Alphabetic 15    |
      | LAST NAME        | Alphabetic 15    |
      | DOB              | random past date |
      | GENDER           | Female           |
      | ARE YOU PREGNANT | No               |
      | SSN              | Numeric 9        |
    And I choose Communication Opt In Information by the following list
      | Mail |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | MAILING ADDRESS LINE 1 | 1007 Mountain Drive |
      | MAILING ADDRESS LINE 2 | 1007 Mountain Lane  |
      | MAILING CITY           | Gotham              |
      | MAILING STATE          | New Jersey          |
      | MAILING ZIP CODE       | 53540               |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICANT_ADDRESS_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                    | module      | eventType   | projectName |
      | APPLICANT_ADDRESS_SAVE_EVENT | APPLICATION | application |[blank]|

  @CP-16455 @CP-16455-03 @ats-events @vinuta
  Scenario Outline: Verify APPLICANT_ADDRESS_SAVE_EVENT for Address of Authorized Representative is generated
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME     | RANDOM 10     |
      | LAST NAME      | RANDOM 10     |
      | ADDRESS LINE 1 | ALPHA NUMERIC |
      | ADDRESS LINE 2 | ALPHA NUMERIC |
      | CITY           | RANDOM        |
      | STATE          | RANDOM        |
      | ZIP CODE       | RANDOM        |
    Then I click on Save button on Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICANT_ADDRESS_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                    | module      | eventType        | projectName |
      | APPLICANT_ADDRESS_SAVE_EVENT | APPLICATION | consumer save ar |[blank]|

  @CP-16455 @CP-16455-04 @ats-events @vinuta
  Scenario Outline: Verify APPLICANT_PHONE_SAVE_EVENT for Phone of Primary Individual is generated
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I choose "Medicaid" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME       | Alphabetic 30    |
      | MIDDLE INITIAL   | Alphabetic 1     |
      | LAST NAME        | Alphabetic 30    |
      | DOB              | random past date |
      | GENDER           | Female           |
      | ARE YOU PREGNANT | No               |
      | SSN              | Numeric 9        |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | <PhoneType> | Random Numeric 10 |
    And I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICANT_PHONE_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                  | module      | eventType   | projectName | PhoneType         |
      | APPLICANT_PHONE_SAVE_EVENT | APPLICATION | application |[blank]| CELL PHONE NUMBER |
      | APPLICANT_PHONE_SAVE_EVENT | APPLICATION | application |[blank]| HOME PHONE NUMBER |
      | APPLICANT_PHONE_SAVE_EVENT | APPLICATION | application |[blank]| WORK PHONE NUMBER |
      | APPLICANT_PHONE_SAVE_EVENT | APPLICATION | application |[blank]| FAX NUMBER        |

  @CP-16455 @CP-16455-05 @ats-events @vinuta
  Scenario Outline: Verify APPLICANT_EMAIL_SAVE_EVENT for Email of Primary Individual is generated
    Given I logged into CRM
    And I saved logged in user ID
    When I navigate to Create "LONG TERM CARE" application page
    Then I choose "HCBS" as program type
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME       | Alphabetic 15    |
      | LAST NAME        | Alphabetic 15    |
      | DOB              | random past date |
      | GENDER           | Female           |
      | ARE YOU PREGNANT | No               |
      | SSN              | Numeric 9        |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10    |
      | EMAIL             | testevents@email.com |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICANT_EMAIL_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                  | module      | eventType   | projectName |
      | APPLICANT_EMAIL_SAVE_EVENT | APPLICATION | application |[blank]|
