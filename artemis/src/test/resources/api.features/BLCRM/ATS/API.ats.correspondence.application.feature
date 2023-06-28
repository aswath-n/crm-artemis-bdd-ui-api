Feature: Inbound correspondence entity and application entity

  @API-CP-13329 @API-CP-13329-01 @API-CP-39360 @API-CRM-Regression @API-ATS @munavvar @sang
  Scenario: There is a link (EXTERNAL_LINK record) created between the resulting Application and the Inbound Correspondence item in the Application microservice data store
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
    And I have prepared keywordRecord Primary Individual member dynamic data values with "U"
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
    And there is event in search event results for "ECMS" with values
      | internalId | internalRefType        | externalRefType |
      | documentId | INBOUND_CORRESPONDENCE | APPLICATION     |
    When I initiated application api for ats with url id "base"
    And I send request to application api for ats
    Then I verify application data response from ats application api
      | applicationId    | applicationStatus | channelId | applicationReceivedDate | applicationCycle | applicationType    | applicationSignatureExistsInd | applicationSignatureDate         |
      | base: internalId | Determining       | Email     | base: documentDate      | New              | Medical Assistance | base: Application Signature   | base: Application Signature Date |

  @API-CP-13329 @API-CP-13329-02 @API-CRM-Regression @API-ATS @munavvar @sang
  Scenario: There is a link (EXTERNAL_LINK record) created with no sign data between the resulting Application and the Inbound Correspondence item in the Application microservice data store
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
      | Fax     | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | documentId one |
    And I have prepared keywordRecord Primary Individual member dynamic data values with "M"
    And I have prepared keywordRecord Application member one dynamic data values with "F"
    Then I submit ATS sendevent request
    Given I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId | internalRefType | externalRefType        |
      | documentId | APPLICATION     | INBOUND_CORRESPONDENCE |
    And there is event in search event results for "ECMS" with values
      | internalId | internalRefType        | externalRefType |
      | documentId | INBOUND_CORRESPONDENCE | APPLICATION     |
    When I initiated application api for ats with url id "base"
    And I send request to application api for ats
    Then I verify application data response from ats application api
      | applicationId    | applicationStatus | channelId | applicationReceivedDate | applicationCycle | applicationType    | applicationSignatureExistsInd | applicationSignatureDate         |
      | base: internalId | Determining       | Fax       | base: documentDate      | New              | Medical Assistance | base: Application Signature   | base: Application Signature Date |

  @API-CP-16545 @API-CP-16545-01 @API-CRM-Regression @API-ATS @munavvar @sang
  Scenario: A link event is published for DPBI informing of the link from Application to Inbound Correspondence
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
    And there is event in search event results for "ECMS" with values
      | internalId | internalRefType        | externalRefType |
      | documentId | INBOUND_CORRESPONDENCE | APPLICATION     |
    Then I should see that the "LINK_EVENT" record is produced to DPBI for "ATS" event
    Then I should see that the "LINK_EVENT" record is produced to DPBI for "ECMS" event

  @API-CP-16585 @API-CP-16585-01 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify the Authorized Representative values for Application created through Inbound Correspondence
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
      | Channel | ProcessType            | Application Type   | SetId          |
      | Mail    | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | documentId one |
    And I have prepared Authorized Representative keywords for ATS sendevent
      | AR First Name | AR Middle Initial | AR Last Name | AR Address Line 1 | AR Address City | AR Address State | AR Address Zip | AR Organization Name | AR ID Number | AR Authorization Signature Date |
      | Random First  | Random Mi         | Random Last  | Random Address    | Random City     | Random State     | Random Zip     | Random Org name      | Random Id    | today: yyyy-MM-dd               |
    And I have prepared keywordRecord Primary Individual member dynamic data values with "M"
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
    When I initiated application api for ats with url id "base"
    And I send request to application api for ats
    Then I verify Authorized Representative data response from ats application api

  @API-CP-16584 @API-CP-16584-01 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify the Consumer Applicant Male and Female values for Application created through Inbound Correspondence
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
    When I initiated application api for ats with url id "base"
    And I send request to application api for ats
    And I verify Primary Individual Applicant Consumer values incoming from Inbound Correspondence
    And I verify Application Member one Applicant Consumer values incoming from Inbound Correspondence
    And I verify Application Contact Information values incoming from Inbound Correspondence

  @API-CP-16584 @API-CP-16584-02 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify the Consumer Applicant Unknown gender and Same as Residence checkbox for Application created through Inbound Correspondence
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
      | Email   | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | checkbox: YES                        | documentId one |
    And I have prepared keywordRecord Primary Individual member dynamic data values with "U"
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
    When I initiated application api for ats with url id "base"
    And I send request to application api for ats
    And I verify Primary Mailing and Residence Contact Information is the same values from Inbound Correspondence application
    And I verify Primary Individual Applicant Consumer values incoming from Inbound Correspondence

  @API-CP-26576 @API-CP-26576-01 @API-CP-29273 @CP-29273-01 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify Recipient Data for Outbound Correspondence Regarding Mail Channel
     #CP-29273-01 when "includeNonDefaultRecipients" : false , only PI should be returned in the recipient response
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
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "false" for including non default recipients
      | mail |
    And I send POST application data services recipients
    Then I verify the "SUCCESS" status of the POST application recipients api
    And I verify "Primary Individual" received data for recipients api with the following channel data
      | mail |
    And I verify the "Only PI" status of the POST application recipients api

  @API-CP-26584 @API-CP-26584-01 @API-CRM-Regression @API-ATS @sang
     #Refactored to add Opt-in step as per CP-29273
  Scenario: Verify Recipient Data for Outbound Correspondence Regarding Fax Channel
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
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType | phoneNumber  | primaryContactTypeInd |
      | faxNumber | RANDOM PHONE | false                 |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Fax |
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "false" for including non default recipients
      | fax |
    And I send POST application data services recipients
    Then I verify the "SUCCESS" status of the POST application recipients api
    And I verify "Primary Individual" received data for recipients api with the following channel data
      | fax |

  @API-CP-26581 @API-CP-26581-01 @API-CRM-Regression @API-ATS @sang
    #Refactored to add Opt-in step as per CP-29273
  Scenario: Verify Recipient Data for Outbound Correspondence Regarding Email Channel
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
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress     |
      | autoAPI@test.com |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      |  Mail |
      | Phone |
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "false" for including non default recipients
      | email |
    And I send POST application data services recipients
    Then I verify the "SUCCESS" status of the POST application recipients api
    And I verify "Primary Individual" received data for recipients api with the following channel data
      | email |

  @API-CP-26581 @API-CP-26581-02 @API-CP-26576 @API-CP-26576-02 @API-CP-26584 @API-CP-26584-02 @API-CRM-Regression @API-ATS @sang
  Scenario: Recipient API missing channel in request payload validation
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
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "false" for including non default recipients
      | null |
    And I send POST application data services recipients
    Then I verify the "No Channel invalid" status of the POST application recipients api

  @API-CP-26581 @API-CP-26581-03 @API-CP-26576 @API-CP-26576-03 @API-CP-26584 @API-CP-26584-03 @API-CRM-Regression @API-ATS @sang
  Scenario: Recipient API missing Application Id in request payload validation
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
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "no appId" following list of channels and "false" for including non default recipients
      | mail |
    And I send POST application data services recipients
    Then I verify the "No AppId invalid" status of the POST application recipients api

  @API-CP-26581 @API-CP-26581-04 @API-CP-26576 @API-CP-26576-04 @API-CP-26584 @API-CP-26584-04 @API-CRM-Regression @API-ATS @sang
  Scenario: Recipient API with non existing Application Id in request payload validation
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
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "999999" following list of channels and "false" for including non default recipients
      | mail |
    And I send POST application data services recipients
    Then I verify the "Non Existing AppId invalid" status of the POST application recipients api

  @API-CP-26581 @API-CP-26581-05 @API-CP-26576 @API-CP-26576-05 @API-CP-26584 @API-CP-26584-05 @API-CRM-Regression @API-ATS @sang
  Scenario: Recipient API with unrecognized channel value in request payload validation
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
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "false" for including non default recipients
      | nail | tax | snail |
    And I send POST application data services recipients
    Then I verify the "Unrecognized invalid" status of the POST application recipients api

  @API-CP-26581 @API-CP-26581-06 @API-CP-26576 @API-CP-26576-06 @API-CP-26584 @API-CP-26584-06 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify Recipient Data for Outbound Correspondence Regarding Mail Email and Fax Channel
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
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Fax |
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "false" for including non default recipients
      | email |
      | mail |
      | fax |
    And I send POST application data services recipients
    Then I verify the "SUCCESS" status of the POST application recipients api
    And I verify "Primary Individual" received data for recipients api with the following channel data
      | email |
      | mail |
      | fax |

  @API-CP-22716 @API-CP-22716-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify Creating Link between Correspondence w/Application ID keyword and Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate request of documentId from Put ECMS documentencoded services with following values
      | documentType | maersk Application    |
      | Language     | SPANISH                |
      | Channel      | MAIL                   |
      | Status       | RECEIVED               |
      | ProcessType  | INBOUND CORRESPONDENCE |
      | setId        | random                 |
      | StatusSetOn  | current                |
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I have prepared default sendevent request data for ATS from file "events/ATSInbCorrespondenceSaveEvent.json"
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      | documentType    | dateStored         | documentStatus |
      | documentId     | today: yyyy-MM-dd | maersk Unknown | today: yyyy-MM-ddT | Received       |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | ApplicationID      | statusSetOn        |
      | mail    | INBOUND_CORRESPONDENCE | API application ID | today: yyyy-MM-ddT |
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
    Then I should see that the "LINK_EVENT" record is produced to DPBI for "ATS" event
    Given I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId    | internalRefType        | externalRefType |
      | applicationId | INBOUND_CORRESPONDENCE | APPLICATION     |
    Then I should see that the "LINK_EVENT" record is produced to DPBI for "ATS" event

  @API-CP-28862 @API-CP-28862-01 @API-CRM-Regression @API-ATS @ozgen
  Scenario: Link creation between outbound correspondence and Application
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
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create outbound correspondence api for ATS
    Then I provide application details for POST Create Outbound Correspondence api
    And I run POST Outbound Correspondence Call API
    Then I verify new Outbound Correspondence is created and stored id
    And I initiate and run Get Application Links Call
    And I will get "Outbound Correspondence" ID for "" type from the application links response
    And I verify newly created Outbound Correspondence is linked to application

  @API-CP-29303 @API-CP-29303-01 @API-CP-31655 @API-CP-31655-01 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Create outbound correspondence when application has missing information of type as application
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false        | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | comments |
      | Data        | SSN           | Application Consumer | Pending | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "AS" service
      | dependentType        |
      | Application Consumer |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    When I initiated create outbound correspondence api for Missing Information
    And I provide application details for POST Create Outbound Correspondence api for Missing Information
    Then I run POST Outbound Correspondence Call API for Missing Information
    When I initiate and run Get Application Links Call
    And I will get "Outbound Correspondence" ID for "" type from the application links response
    And I initiated GET outbound correspondence api
    Then I verify outbound correspondence response has expected missing information details
      | applicationType    | attributeName | entityRecordType | dependentType      |
      | Medical Assistance | SSN           | Application      | PRIMARY INDIVIDUAL |

  @API-CP-29303 @API-CP-29303-02  @API-CP-31655 @API-CP-31655-02 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Create outbound correspondence when application has missing information of type as application consumer
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate |
      | Long Term Care  | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "APPLICATION MEMBER" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue | comments |
      | Data        | Last Name     | Application Consumer | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "AS" service
      | dependentType      |
      | APPLICATION MEMBER |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    When I initiated create outbound correspondence api for Missing Information
    And I provide application details for POST Create Outbound Correspondence api for Missing Information
    Then I run POST Outbound Correspondence Call API for Missing Information
    When I initiate and run Get Application Links Call
    And I will get "Outbound Correspondence" ID for "" type from the application links response
    And I initiated GET outbound correspondence api
    Then I verify outbound correspondence response has expected missing information details
      | applicationType | attributeName | entityRecordType     | dependentType      |
      | Long Term Care  | Last Name     | Application Consumer | APPLICATION MEMBER |

  @API-CP-29303 @API-CP-29303-03 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Outbound correspondence is not created when application has no pending missing information
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false        | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    When I initiated create outbound correspondence api for Missing Information
    And I provide application details for POST Create Outbound Correspondence api for Missing Information
    Then I run POST Outbound Correspondence Call API for Missing Information
    Then I verify outbound correspondence cannot be created when no missing information on application
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status      | comments |
      | Data        | SSN           | Application Consumer | Disregarded | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "AS" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    When I initiated create outbound correspondence api for Missing Information
    And I provide application details for POST Create Outbound Correspondence api for Missing Information
    Then I run POST Outbound Correspondence Call API for Missing Information
    Then I verify outbound correspondence cannot be created when no missing information on application

  @API-CP-29273 @API-CP-29273-02 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Verify Recipient Data Includes Non-Default Recipients When Requested & isUsable=true when opted-in and has channel values
    # when "includeNonDefaultRecipients" : true, recipient response includes AM & AR
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
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 2 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Fax |
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "true" for including non default recipients
      | mail |
      | fax |
      | email |
    And I send POST application data services recipients
    Then I verify the "SUCCESS" status of the POST application recipients api
    And I verify "Primary Individual" received data for recipients api with the following channel data
      | mail |
      | fax |
      | email |
    And I verify "Application Member" received data for recipients api with the following channel data
      | mail |
      | fax |
      | email |
    And I verify "Authorized Representative" received data for recipients api with the following channel data
      | mail |
      | fax |
      | email |

  @API-CP-29273 @API-CP-29273-03 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Verify Mail Channel is set to Not Usable When No Active Mailing Address
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
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Fax |
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "true" for including non default recipients
      | mail |
      | fax |
      | email |
    And I send POST application data services recipients
    Then I verify the "SUCCESS" status of the POST application recipients api
    Then I verify "Primary Individual" has following values in the recipients api response for "mail" channel
      | isUsable | unusableReason              | addresses |
      | false    | No active destination found |[blank]|
    Then I verify "Primary Individual" has following values in the recipients api response for "email" channel
      | isUsable | unusableReason              | emailAddresses |
      | false    | No active destination found |[blank]|
    Then I verify "Primary Individual" has following values in the recipients api response for "fax" channel
      | isUsable | unusableReason              | faxNumbers |
      | false    | No active destination found |[blank]|

  @API-CP-29273 @API-CP-29273-04 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Verify Recipient Data when Mailing address does not exists for Auth Rep
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | channelId |
      | Long Term Care  | CURRENT YYYYMMDD        | Mail      |
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
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "true" for including non default recipients
      | mail | fax | email |
    And I send POST application data services recipients
    Then I verify the "SUCCESS" status of the POST application recipients api
    Then I verify "Authorized Representative" has following values in the recipients api response for "mail" channel
      | isUsable | unusableReason              | addresses |
      | false    | No active destination found |[blank]|

  @API-CP-29273 @API-CP-29273-05 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Verify Recipient Data Sets Channel to Not Usable When Not Opted Into Mail,Email,Fax & has channel values
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
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "true" for including non default recipients
      | mail | fax | email |
    And I send POST application data services recipients
    Then I verify the "SUCCESS" status of the POST application recipients api
    Then I verify "Primary Individual" has following values in the recipients api response for "mail" channel
      | isUsable | unusableReason     | addresses | isDefault | isPrimary |
      | false    | Consumer opted out | true      | true      | true      |
    Then I verify "Primary Individual" has following values in the recipients api response for "email" channel
      | isUsable | unusableReason     | emailAddresses | isDefault | isPrimary |
      | false    | Consumer opted out | true           | true      | true      |
    Then I verify "Primary Individual" has following values in the recipients api response for "fax" channel
      | isUsable | unusableReason     | faxNumbers | isDefault | isPrimary |
      | false    | Consumer opted out | true       | true      | true      |

  @API-CP-29273 @API-CP-29273-06 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Verify Recipient Data Sets Channel to Not Usable When Not Opted Into Mail,Email,Fax & has no channel values
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
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | false                 |
    And I clear application save submit "RESPONSE" list
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate POST Application recipients with "created save API appID" following list of channels and "true" for including non default recipients
      | mail | fax | email |
    And I send POST application data services recipients
    Then I verify the "SUCCESS" status of the POST application recipients api
    Then I verify "Primary Individual" has following values in the recipients api response for "mail" channel
      | isUsable | unusableReason     | addresses |
      | false    | Consumer opted out |[blank]|
    Then I verify "Primary Individual" has following values in the recipients api response for "email" channel
      | isUsable | unusableReason     | emailAddresses |
      | false    | Consumer opted out |[blank]|
    Then I verify "Primary Individual" has following values in the recipients api response for "fax" channel
      | isUsable | unusableReason     | faxNumbers |
      | false    | Consumer opted out |[blank]|

  @API-CP-24197 @API-CP-24197-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify all documents under the same SetId linked to the same application
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
      | Fax     | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | documentId one |
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
    When I send PUT ECMS documentencoded services and store documentId "one" in a list
    And I have prepared default sendevent request data for ATS from file "events/ATSSendEvent.json" for second time
    And I have prepared main properties(override if exist) for ATS sendevent endpoint as
      | documentHandle | documentDate      |
      | documentId2    | today: yyyy-MM-dd |
    And I have prepared keywordFields(override if exist) for ATS sendevent endpoint as
      | Channel | ProcessType            | Application Type   | Application Signature | Application Signature Date | SetId          |
      | Fax     | INBOUND CORRESPONDENCE | MEDICAL ASSISTANCE | YES                   | today: yyyy-MM-dd          | documentId one |
    And I have prepared keywordRecord Primary Individual member dynamic data values with "M"
    Then I submit ATS sendevent request
    And I initiate and run Get Inbound Correspondence Links Call
    Then I will get applicationId and taskId from the response
    And I save the application Id from Inbound document creation for fetching application details
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName  |
      | LINK_EVENT |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 100  | 0    | eventId,desc |
    Then I received "100" events from search event API
    And there is event in search event results for "ATS" with values
      | externalId  | internalRefType | externalRefType        |
      | documentId1 | APPLICATION     | INBOUND_CORRESPONDENCE |
    And there is event in search event results for "ECMS" with values
      | internalId  | internalRefType        | externalRefType |
      | documentId1 | INBOUND_CORRESPONDENCE | APPLICATION     |
    Then I should see that the "LINK_EVENT" record is produced to DPBI for "ATS" event
    Then I should see that the "LINK_EVENT" record is produced to DPBI for "ECMS" event
    And there is event in search event results for "ATS" with values
      | externalId  | internalRefType | externalRefType        |
      | documentId2 | APPLICATION     | INBOUND_CORRESPONDENCE |
    And there is event in search event results for "ECMS" with values
      | internalId  | internalRefType        | externalRefType |
      | documentId2 | INBOUND_CORRESPONDENCE | APPLICATION     |
    Then I should see that the "LINK_EVENT" record is produced to DPBI for "ATS" event
    Then I should see that the "LINK_EVENT" record is produced to DPBI for "ECMS" event
    And I initiate and run Get Application Links Call
    And I will verify there are 2 linked "Inbound Correspondence" for link application response with following values
      | status | RECEIVED               |
      | type   | maersk Application    |
      | name   | Inbound Correspondence |