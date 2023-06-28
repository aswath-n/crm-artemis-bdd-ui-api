Feature: API: ATS Case&Consumer Related Functionality

  @API-CP-30093 @API-CP-30093-01 @API-CP-30639 @API-CP-30639-01 @API-CP-31590-01 @API-CP-30806-01 @API-CRM-Regression @API-ATS @burak @ats-smoke
  Scenario: Invoke ATS endpoint to Create New Case, Create New Consumers , Verify case linked to app
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
      | Fax |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | cellPhoneNumber | 3213121231  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber | primaryContactTypeInd |
      | faxNumber | 4213121234  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | homePhoneNumber | 5213121265  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 | addressCounty |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        | Loudoun       |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | ESQ            | D                  | RANDOM DOB  | RANDOM SSN | Male       | English         | Spanish        |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
   And I post on ATS save application api and update following values for "APPLICATION MEMBER" consumer 1 with following data for matching application
     | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage |
     | RANDOM FIRST      | RANDOM LAST      | ESQ            | D                  | RANDOM DOB  | RANDOM SSN | Male       | English         | Spanish        |
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
    And I verify the details of link case api response for "PRIMARY INDIVIDUAL"
      | consumerMiddleName | B                    |
      | consumerSuffix     | Sr                   |
      | genderCode         | Female               |
      | pregnancyInd       | true                 |
      | pregnancyDueDate   | 2022-12-05           |
      | SPOKEN LANGUAGE    | SPANISH              |
      | WRITTEN LANGUAGE   | RUSSIAN              |
      | CONSENT TYPE       | Phone,Fax,Mail,Email |
    And I verify the details of link case api response for "APPLICATION MEMBER"
      | consumerMiddleName | D       |
      | consumerSuffix     | ESQ     |
      | genderCode         | Male    |
      | pregnancyInd       | false   |
      | SPOKEN LANGUAGE    | SPANISH |
      | WRITTEN LANGUAGE   | ENGLISH |
    And I verify the details of link case api response for "CONTACT INFORMATION"
      | P.addressType          | Physical               |
      | P.addressStreet1       | 9 Metro Ave            |
      | P.addressStreet2       | 2nd apt                |
      | P.addressCity          | Herndon                |
      | P.addressState         | Virginia               |
      | P.addressZip           | 20171                  |
      | P.addressCounty        | Loudoun                |
      | M.addressType          | Mailing                |
      | M.addressStreet1       | 123 Auto Created       |
      | M.addressStreet2       | Suite 2                |
      | M.addressCity          | Houston                |
      | M.addressState         | Texas                  |
      | M.addressZip           | 77055                  |
      | M.addressCounty        | Loudoun                |
      | emails[0].emailAddress | automation@created.com |
      | emails[0].emailType    | OFFICE                 |
      | W.phoneNumber          | 2789087677             |
      | W.phoneType            | Work                   |
      | W.primaryIndicator     | true                   |
      | H.phoneNumber          | 5213121265             |
      | H.primaryIndicator     | false                  |
      | H.phoneType            | Home                   |
      | C.phoneNumber          | 3213121231             |
      | C.primaryIndicator     | false                  |
      | C.phoneType            | Cell                   |
      | F.phoneNumber          | 4213121234             |
      | F.primaryIndicator     | false                  |
      | F.phoneType            | Fax                    |
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    Then I verify all the eligibility status for the programs set to "Pending" for Application Consumers
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | nameCase | Case |
    Given I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |

  @API-CP-30639 @API-CP-30639-02 @API-CP-31590-02 @API-CP-30806-02 @API-CP-30819 @API-CP-30819-02 @API-CRM-Regression @API-ATS @burak
  Scenario: Invoke ATS endpoint to Create New Consumers, add Created Consumers to the existing case and Verify case linked to app
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
      | Fax |
      | Text |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | cellPhoneNumber | 3213121231  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber | primaryContactTypeInd |
      | faxNumber | 4213121234  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | homePhoneNumber | 5213121265  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 | addressCounty |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        | Loudoun       |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | ESQ            | D                  | RANDOM DOB  | RANDOM SSN | Male       | English         | Spanish        |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I send API CALL for Create CaseConsumer
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
      | caseId     | CASE/CONSUMER      |
    And I run the link case api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I verify the details of link case api response for "PRIMARY INDIVIDUAL"
      | consumerMiddleName | B                         |
      | consumerSuffix     | Sr                        |
      | genderCode         | Female                    |
      | pregnancyInd       | true                      |
      | pregnancyDueDate   | 2022-12-05                |
      | SPOKEN LANGUAGE    | SPANISH                   |
      | WRITTEN LANGUAGE   | RUSSIAN                   |
      | CONSENT TYPE       | Phone,Fax,Mail,Email,Text |
    And I verify the details of link case api response for "APPLICATION MEMBER"
      | consumerMiddleName | D       |
      | consumerSuffix     | ESQ     |
      | genderCode         | Male    |
      | pregnancyInd       | false   |
      | SPOKEN LANGUAGE    | SPANISH |
      | WRITTEN LANGUAGE   | ENGLISH |
    And I verify the details of link case api response for "CONTACT INFORMATION"
      | P.addressType          | Physical               |
      | P.addressStreet1       | 9 Metro Ave            |
      | P.addressStreet2       | 2nd apt                |
      | P.addressCity          | Herndon                |
      | P.addressState         | Virginia               |
      | P.addressZip           | 20171                  |
      | P.addressCounty        | Loudoun                |
      | M.addressType          | Mailing                |
      | M.addressStreet1       | 123 Auto Created       |
      | M.addressStreet2       | Suite 2                |
      | M.addressCity          | Houston                |
      | M.addressState         | Texas                  |
      | M.addressZip           | 77055                  |
      | M.addressCounty        | Loudoun                |
      | emails[0].emailAddress | automation@created.com |
      | emails[0].emailType    | OFFICE                 |
      | W.phoneNumber          | 2789087677             |
      | W.phoneType            | Work                   |
      | W.primaryIndicator     | true                   |
      | H.phoneNumber          | 5213121265             |
      | H.primaryIndicator     | false                  |
      | H.phoneType            | Home                   |
      | C.phoneNumber          | 3213121231             |
      | C.primaryIndicator     | false                  |
      | C.phoneType            | Cell                   |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | nameCase | Case |
    Given I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |

  @API-CP-30639 @API-CP-30639-03 @API-CRM-Regression @API-ATS @burak
  Scenario: Create new Case, Add Existing Consumers to the Case
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
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
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE              | PRIMARY INDIVIDUAL |
      | actionType        | ADD_EXISTING       |
      | matchedConsumerId | PRIOR              |
    Then I initiate link case api 1
      | ROLE              | APPLICATION MEMBER |
      | actionType        | ADD_EXISTING       |
      | matchedConsumerId | PRIOR              |
    And I run the link case api
    And I capture the case id from link case api
    And I verify the details of link case api response for "PRIMARY INDIVIDUAL"
      | genderCode       | Male    |
      | pregnancyInd     | false   |
      | SPOKEN LANGUAGE  | SPANISH |
      | WRITTEN LANGUAGE | ENGLISH |
    And I verify the details of link case api response for "APPLICATION MEMBER"
      | pregnancyInd     | false   |
      | SPOKEN LANGUAGE  | RUSSIAN |
      | WRITTEN LANGUAGE | ENGLISH |

  @API-CP-30639 @API-CP-30639-04 @API-CP-30806-03 @API-CP-30806-02 @API-CRM-Regression @API-ATS @burak
  Scenario:  Add Existing Consumers to  Existing  Case , Verify app linked to the case , and status goes to Determining
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Medicaid |
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
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE              | PRIMARY INDIVIDUAL |
      | actionType        | ADD_EXISTING       |
      | matchedConsumerId | PRIOR              |
      | caseId            | CASE/CONSUMER      |
    Then I initiate link case api 1
      | ROLE              | APPLICATION MEMBER |
      | actionType        | ADD_EXISTING       |
      | matchedConsumerId | PRIOR              |
    And I run the link case api
    And I capture the case id from link case api
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    Then I verify all the eligibility status for the programs set to "Pending" for Application Consumers
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | nameCase | Case |
    Given I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |

  @API-CP-30639 @API-CP-30639-05 @CP-31590-03 @API-CRM-Regression @API-ATS @burak
  Scenario: Create PI, Add Existing AM to the Existing Case
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
      | Fax |
      | Text |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | cellPhoneNumber | 3213121231  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber | primaryContactTypeInd |
      | faxNumber | 4213121234  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | homePhoneNumber | 5213121265  | false                 |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | RANDOM DOB  | DUPLICATE SSN | Male       | English         | Spanish        |
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
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | RANDOM DOB  | DUPLICATE SSN | Male       | English         | Spanish        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I send API CALL for Create CaseConsumer
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE              | APPLICATION MEMBER |
      | actionType        | ADD_EXISTING       |
      | matchedConsumerId | PRIOR              |
      | caseId            | CASE/CONSUMER      |
    And I run the link case api
    And I capture the case id from link case api
    And I verify the details of link case api response for "PRIMARY INDIVIDUAL"
      | genderCode       | Male    |
      | pregnancyInd     | false   |
      | SPOKEN LANGUAGE  | SPANISH |
      | WRITTEN LANGUAGE | ENGLISH |
    And I verify the details of link case api response for "APPLICATION MEMBER"
      | genderCode       | Male    |
      | pregnancyInd     | false   |
      | SPOKEN LANGUAGE  | SPANISH |
      | WRITTEN LANGUAGE | ENGLISH |
    And I verify the details of link case api response for "CONTACT INFORMATION"
      | P.addressType          | Physical               |
      | P.addressStreet1       | 9 Metro Ave            |
      | P.addressStreet2       | 2nd apt                |
      | P.addressCity          | Herndon                |
      | P.addressState         | Virginia               |
      | P.addressZip           | 20171                  |
      | P.addressCounty        | Loudoun                |
      | emails[0].emailAddress | automation@created.com |
      | emails[0].emailType    | OFFICE                 |
      | W.phoneNumber          | 2789087677             |
      | W.phoneType            | Work                   |
      | W.primaryIndicator     | true                   |
      | H.phoneNumber          | 5213121265             |
      | H.primaryIndicator     | false                  |
      | H.phoneType            | Home                   |
      | C.phoneNumber          | 3213121231             |
      | C.primaryIndicator     | false                  |
      | C.phoneType            | Cell                   |

  @API-CP-31632 @API-CP-31632-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify Creation of Auth Rep using link/case API for Active Status
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
      | RANDOM FIRST      | RANDOM LAST      | M                  | Receive in Place Of | PAST TIMESTAMP            | FUTURE TIMESTAMP        | Broker       | VA Foundation        | true                   | CURRENT TIMESTAMP          | Full Access |
    And I initiate save applications api consumer 1 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | AUTHORIZED REP |
      | actionType | CREATE_NEW     |
    And I run the link case api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I verify the details of link case api response for "AUTHORIZED REPRESENTATIVE"
      | effectiveStartDate     | PAST TIMESTAMP   |
      | effectiveEndDate       | FUTURE TIMESTAMP |
      | consumerMiddleName     | M                |
      | RECEIVE CORRESPONDENCE | YES              |
      | consumerStatus         | Active           |

  @API-CP-31632 @API-CP-31632-02 @API-CRM-Regression @API-ATS @burak
    #based on CP-36852, we dont expect to see error message for inactive auth rep status, commented last lane by referring this ticket
  Scenario: Verify Creation of Auth Rep using link/case API for Inactive Status
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
      | RANDOM FIRST      | RANDOM LAST      | B                  | Do Not Receive | PAST TIMESTAMP            | PAST TIMESTAMP          | Broker       | VA Foundation        | true                   | CURRENT TIMESTAMP          | Full Access |
    And I initiate save applications api consumer 1 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | AUTHORIZED REP |
      | actionType | CREATE_NEW     |
  #  And I run the link case api to verify expected error message for Inactive Status

  @API-CP-31632 @API-CP-31632-03 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify Creation of Auth Rep using link/case API for Future Status
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
      | consumerFirstName | consumerLastName | consumerMiddleName | correspondence | authorizedRepAppStartDate        | authorizedRepAppEndDate          | consumerType | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate | accessType  |
      | RANDOM FIRST      | RANDOM LAST      | B                  | Do Not Receive | 2029-03-16T10:54:57.274000+00:00 | 2029-05-16T10:54:57.274000+00:00 | Broker       | VA Foundation        | true                   | CURRENT TIMESTAMP          | Full Access |
    And I initiate save applications api consumer 1 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | AUTHORIZED REP |
      | actionType | CREATE_NEW     |
    And I run the link case api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I verify the details of link case api response for "AUTHORIZED REPRESENTATIVE"
      | consumerMiddleName     | B      |
      | RECEIVE CORRESPONDENCE | NO     |
      | consumerStatus         | Future |

  @API-CP-32080 @API-CP-32080-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Link Consumers to Application Consumers for Create_New Action
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Medicaid |
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
    Given I initiated mars search events api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdPI | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdPI | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdAM | APPLICATION_CONSUMER | CONSUMER        |
    And I verify 6 events found for "ATS" with above values

  @API-CP-32080 @API-CP-32080-02 @API-CRM-Regression @API-ATS @burak
  Scenario: Link Consumers to Application Consumers for Create_New Action on Existing Case
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I send API CALL for Create CaseConsumer
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
      | caseId     | CASE/CONSUMER      |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    Given I initiated mars search events api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdPI | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdPI | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdAM | APPLICATION_CONSUMER | CONSUMER        |
    And I verify 6 events found for "ATS" with above values

  @API-CP-32080 @API-CP-32080-03 @API-CRM-Regression @API-ATS @burak
  Scenario: Link Consumers to Application Consumers for ADD_EXISTING Action
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
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
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE              | PRIMARY INDIVIDUAL |
      | actionType        | ADD_EXISTING       |
      | matchedConsumerId | PRIOR              |
    Then I initiate link case api 1
      | ROLE              | APPLICATION MEMBER |
      | actionType        | ADD_EXISTING       |
      | matchedConsumerId | PRIOR              |
    And I run the link case api
    Given I initiated mars search events api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdPI | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdPI | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdAM | APPLICATION_CONSUMER | CONSUMER        |
    And I verify 6 events found for "ATS" with above values

  @API-CP-32080 @API-CP-32080-04 @API-CRM-Regression @API-ATS @burak
  Scenario: Link Consumers to Application Consumers for LINK Action
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
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
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Russian        |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE              | PRIMARY INDIVIDUAL |
      | actionType        | LINK               |
      | matchedConsumerId | PRIOR              |
    Then I initiate link case api 1
      | ROLE              | APPLICATION MEMBER |
      | actionType        | LINK               |
      | matchedConsumerId | PRIOR              |
    And I run the link case api
    When I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType |
      | caseId     | APPLICATION     | CASE            |
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType | externalRefType |
      | applicationId | CASE            | APPLICATION     |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdPI | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId              | internalRefType | externalRefType      |
      | applicationConsumerIdAM | CONSUMER        | APPLICATION_CONSUMER |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdPI | APPLICATION_CONSUMER | CONSUMER        |
    And there is event in search event results for "ATS" with values
      | externalId   | internalRefType      | externalRefType |
      | consumerIdAM | APPLICATION_CONSUMER | CONSUMER        |
    And I verify 6 events found for "ATS" with above values

  @API-CP-33848 @API-CP-33848-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify Matching Consumer Links API captures to the Application in Determining Status
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
    And I initiate and run the matching Consumer Links API
    And I verify matching Consumer Links API with the following values
      | applicationStatus0 | Determining |
  #  And I clear the application ID list

  @API-CP-33848 @API-CP-33848-02 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify Matching Consumer Links API ignores to the Application in Withdrawn Status
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
    And I initiate and run the matching Consumer Links API
    And I verify matching Consumer Links API with the following values
      | applicationStatus0 | Determining |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Withdrawn" on the response
    And I initiate and run the matching Consumer Links API
    And I verify matching Consumer Links API ignores the application
  #  And I clear the application ID list

  @API-CP-33848 @API-CP-33848-03 @API-CRM-Regression @API-ATS @burak
  Scenario Outline: Verify Matching Consumer Links API ignores to the Application in Expired/Closed Status
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
    And I initiate and run the matching Consumer Links API
    And I verify matching Consumer Links API with the following values
      | applicationStatus0 | Determining |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd | applicationId      | applicationDeadlineDate |
      | <Application Type> | <Received Date>         | true         | false          | created previously | <DeadlineDate>          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail | Phone |
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
    And I initiate and run the matching Consumer Links API
    And I verify matching Consumer Links API ignores the application
  #  And I clear the application ID list
    Examples:
      | Application Type   | Received Date | DeadlineDate | status  |
      | Medical Assistance | PRIOR 45      | PRIOR 1      | Expired |
      | Medical Assistance | PRIOR 45      | PRIOR 11     | Closed  |

  @API-CP-33848 @API-CP-33848-04 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify Matching Consumer Links API ignores to the Application in Complete Status
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
    And I initiate and run the matching Consumer Links API
    And I verify matching Consumer Links API with the following values
      | applicationStatus0 | Determining |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "SUB-PROGRAM" dropdown and select the following values
      | 10 - Sub-Program a |
    And I click on save button for the Members Info Panel
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Complete" on the response
    And I initiate and run the matching Consumer Links API
    And I verify matching Consumer Links API ignores the application
