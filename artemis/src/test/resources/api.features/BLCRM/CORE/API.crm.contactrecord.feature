Feature: API-CRM: Contact Record Controller

  @contact-record-api-CRMC @API-CORE @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"


#  @API-CRM-300 @API-CRM @API-CRM-Regression @API-ContactRecords @Sujoy
#  Scenario Outline: Create Consumer Contact Records using API
#      Given I initiated consumer search API for Contact
#      When I get correlationId id from consumer search "<consumerFirstName>" and "<consumerLastName>"
#      And I initiated Create Contact Records API
#      And I can provide contact records information with "<consumerFirstName>" and "<consumerLastName>"
#      And I can provide correlation id
#      And I can run create contact records API
#      Then I can verify consumer contact by consumerLastName with value "<consumerLastName>" on API response
#      Examples:
#          | consumerFirstName | consumerLastName |
#          | Graham            | Gooch            |

#  @API-CRM-300 @API-CRM @API-CRM-Regression @API-ContactRecords @Sujoy
#  Scenario Outline: Create Consumer Contact Records with varius Consumer Type using API
#    Given I initiated consumer search API for Contact
#    When I get correlationId id from consumer search "<consumerFirstName>" and "<consumerLastName>"
#    And I initiated Create Contact Records API
#    And I can provide contact records with "<consumerFirstName>", "<consumerLastName>" and "<consumerType>"
#    And I can provide correlation id
#    And I can run create contact records API
#    Then I can verify consumer contact by consumerLastName with value "<consumerLastName>" on API response
#    Examples:
#      | consumerFirstName | consumerLastName  | consumerType              |
#      |    Graham         | Gooch             | Member                    |
#      |    Graham         | Gooch             | Non-Member                |
#      |    Graham         | Gooch             | Authorized Representative |

  #Contact Record Search  not implemented  yet

#  @API-CRM-231 @API-CRM-234 @API-CRM @API-CRM-Regression @API-ContactRecords @Sujoy
#    Scenario Outline: Search Contact Record using API
#        Given I initiated search contact records API
#        When I can search Contact Record by "<Node>" with value "<value>"
#        And I run the search contact records API
#        Then I can verify contact records search response is "<Success>"
#        Examples:
#            | Node              | value  | Success |
#            | consumerFirstName | Graham | True    |
#            | consumerLastName  | Gooch  | True    |

  @contact-record-api-CRMC @API-CRM-225 @API-CORE @API-CRM-Regression @Sujoy
  Scenario Outline: Get Contact Record history using API
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get correlationId id from consumer search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated Contact Records Customers API
    And I can set link consumer Id as reference Id
    And I run the post contact records API with Query parameters with "<page>","<size>" and "<sort>"
    Then I can verify contact records search response is "<Success>"
    And I can verify contact record Size is less than or equal of "<size>"
    And I can verify contact record ID are descending
    Examples:
      | consumerFirstName | consumerLastName | page | size | sort                 | Success | projectName |
      | James             | Riley            | 0    | 5    | contactRecordId,DESC | True    |[blank]|


#  @API-CRM-231 @API-CRM-234 @API-CRM @API-CRM-Regression @API-ContactRecords @Sujoy
#  Scenario Outline: Search Blank Contact Record using API
#      Given I initiated search contact records API
#      When I can search Contact Record by "<Node>" with value "<value>"
#      And I run the search blank contact records API
#      Examples:
#          | Node                  | value    |
#          | consumerFirstName     |[blank]|
#
#  @API-CRM-232 @API-CRM @API-CRM-Regression @API-ContactRecords @Sujoy
#  Scenario Outline: Search Contact Record Create link using API
#    Given I get correlationId id from consumer search "<consumerFirstName>" and "<consumerLastName>"
#    And I initiated Create Contact Records API
#    And I can provide contact records information for link
#    And I can run update contact records API
#    And I initiated specific contact Search ""
#    And I run Get contact records API
#    Then I can verify contact records search response is "True"
#    Examples:
#        | consumerFirstName | consumerLastName  |
#        | Graham            | Gooch             |

#  @API-CRM-232 @API-CRM @API-CRM-Regression @API-ContactRecords @Sujoy
#  Scenario Outline: Search Contact Record Create Unlink using API
#    Given I created a consumer to link contact record
#    Given I get correlationId id from consumer search "<consumerFirstName>" and "<consumerLastName>"
#    And I initiated Create Contact Records API
#    And I can provide contact records information for unlink
#    And I can run update contact records API
#    And I initiated correlation contact Search ""
#    And I run Get contact records API
#    Then I can verify contact records search response is "True"
#    Examples:
#      | consumerFirstName | consumerLastName  |
#      | {random}            | {random}             |

  @contact-record-api-CRMC @API-CRM-232 @API-CORE @API-CRM-Regression @Sujoy
  Scenario Outline: Search Contact Record Create Unlink using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer to link contact record
    When I get correlationId id from consumer search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated Create Contact Records API
    And I can provide contact records information for unlink
    And I can run update contact records API
    And I initiated specific contact Search ""
    And I run Get contact records API
    Then I verify consumer details on contact record created
    Examples:
      | consumerFirstName | consumerLastName | projectName |
      | {random}          | {random}         |[blank]|


  @contact-record-api-CRMC @API-CRM-304 @API-CORE @API-CRM-Regression @Sujoy
  Scenario Outline: Close Contact Record using API
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Create Contact Records API
    And I can provide general contact record details "<contactRecordType>", "<consumerType>", "<preferredLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    Then I can run create contact records API
    Then I verify general contact record details "<consumerType>", "<contactRecordType>", "<contactChannelType>" and "<contactChannelValue>"
    Examples:
      | preferredLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId | projectName |
      | English               | Enrollment       | Phone              | 1234567898          | consumer     | Inbound           | consumer    |[blank]|             |



#  #@API-CRM-300 @API-CRM @API-CRM-Regression @API-ContactRecords @Sujoy
#  #Scenario Outline: Create Consumer Contact Records using API
#    #Given I initiated consumer search API for Contact
#    #When I get correlationId id from consumer search "<consumerFirstName>" and "<consumerLastName>"
#    #And I initiated Create Contact Records API
#   # And I can provide contact records with "<consumerFirstName>", "<consumerLastName>" and "<consumerType>"
#    And I can provide correlation id
#    And I can run create contact records API
#    Then I can verify consumer contact by consumerLastName with value "<consumerLastName>" on API response
#    Examples:
#      | consumerFirstName | consumerLastName  | consumerType              |
#      |    Graham         | Gooch             | Member                    |
#      |    Graham         | Gooch             | Non-Member                |
#      |    Graham         | Gooch             | Authorized Representative |

  @contact-record-api-CRMC @API-CRM-691 @API-CORE @API-CRM-Regression @Sujoy
  Scenario Outline: Create Consumer Contact Records for Unidentified Contact using API
    Given I created a contact record using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Contact Records API
    And I can provide contact records as anonymous "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>" and "<contactChannelValue>"
    Then I can run create contact records API
    Examples:
      | consumerLanguageCode | inboundCallQueue          | contactChannelType | contactChannelValue | projectName |
      | English              | Enrollment                | Phone              | 123-456-7890        |[blank]|
      | Spanish              | Enrollment                | SMS Text           | 123-456-7890        |[blank]|
      | Russian              | Enrollment                | Email              | abc@test.com        |[blank]|
      | Vietnamese           | Enrollment                | Web Chat           | abc@test.com        |[blank]|
      | English              | Eligibility               | Phone              | 123-456-7890        |[blank]|
      | Spanish              | Eligibility               | SMS Text           | 123-456-7890        |[blank]|
      | Russian              | Eligibility               | Email              | abc@test.com        |[blank]|
      | Vietnamese           | Eligibility               | Web Chat           | abc@test.com        |[blank]|
      | English              | General Program Questions | Phone              | 123-456-7890        |[blank]|
      | Spanish              | General Program Questions | SMS Text           | 123-456-7890        |[blank]|
      | Russian              | General Program Questions | Email              | abc@test.com        |[blank]|
      | Vietnamese           | General Program Questions | Web Chat           | abc@test.com        |[blank]|

  @contact-record-api-CRMC @API-CRM-705 @API-CRM-705-01 @API-CORE @API-CRM-Regression @James
  Scenario Outline: Verify Details of Previously Completed Inbound Contact Record through API
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get correlationId id from consumer search "James" and "Riley"
    And I initiated Create Contact Records API
    And I can provide following contact record information for phone:
      | contactRecordType       | Inbound              |
      | contactType             | General              |
      | contactOutcome          | Reached Successfully |
      | contactRecordStatusType | Complete             |
      | inboundCallQueue        | Enrollment           |
      | contactChannelType      | Phone                |
    And I can run create contact records API
    And I initiated specific contact Search ""
    And I run Get contact records API
    Then I can verify following inbound contact record information:
      | contactRecordType       | Inbound              |
      | contactType             | General              |
      | contactOutcome          | Reached Successfully |
      | contactRecordStatusType | Complete             |
      | inboundCallQueue        | Enrollment           |
      | contactChannelType      | Phone                |
    Examples:
      | projectName |
      |[blank]|

  @contact-record-api-CRMC @API-CRM-705 @API-CRM-705-02 @API-CORE @API-CRM-Regression @James
  Scenario Outline: Verify Details of Previously Completed Inbound Contact Record Reason through API
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get correlationId id from consumer search "James" and "Riley"
    And I initiated Create Contact Records API
    And I can provide following contact record information for phone:
      | contactRecordType       | Inbound              |
      | contactType             | General              |
      | contactOutcome          | Reached Successfully |
      | contactRecordStatusType | Complete             |
      | inboundCallQueue        | Enrollment           |
      | contactChannelType      | Phone                |
    And I can run create contact records API
    And I can add the contact record reason with the following values:
      | contactReason  | Information Request         |
      | contactAction  | Provided Appeal Information |
      | reasonComments | No Comment                  |
    Then I verify contact reason actions by contact with following values:
      | contactAction | Provided Appeal Information |
    Examples:
      | projectName |
      |[blank]|

  @contact-record-api-CRMC @API-CRM-705 @API-CRM-705-03 @API-CORE @API-CRM-Regression @James
  Scenario Outline: Verify Details of Previously Completed OutBound Contact Record through API
    Given I initiated consumer search API for Contact
    When I get correlationId id from consumer search "James" and "Riley"
    And I initiated Create Contact Records API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I can provide following contact record information for phone:
      | contactRecordType       | Outbound             |
      | contactType             | General              |
      | contactRecordStatusType | Complete             |
      | contactCampaignType     | Program Information  |
      | contactChannelType      | Phone                |
      | contactOutcome          | Reached Successfully |
    And I can run create contact records API
    And I initiated specific contact Search ""
    And I run Get contact records API
    Then I can verify following inbound contact record information:
      | contactRecordType       | Outbound             |
      | contactType             | General              |
      | contactRecordStatusType | Complete             |
      | contactCampaignType     | Program Information  |
      | contactChannelType      | Phone                |
      | contactOutcome          | Reached Successfully |
    Examples:
      | projectName |
      |[blank]|


  @contact-record-api-CRMC @API-CRM-705 @API-CRM-705-04 @API-CORE @API-CRM-Regression @James
  Scenario Outline: Verify Details of Previously Completed Outbound Contact Record Reason through API
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get correlationId id from consumer search "James" and "Riley"
    And I initiated Create Contact Records API
    And I can provide following contact record information for phone:
      | contactRecordType       | Outbound             |
      | contactType             | General              |
      | contactRecordStatusType | Complete             |
      | contactCampaignType     | Program Information  |
      | contactChannelType      | Phone                |
      | contactOutcome          | Reached Successfully |
    And I can run create contact records API
    And I can add the contact record reason with the following values:
      | contactReason  | Information Request         |
      | contactAction  | Provided Appeal Information |
      | reasonComments | No Comment                  |
    Then I verify contact reason actions by contact with following values:
      | contactAction | Provided Appeal Information |
    Examples:
      | projectName |
      |[blank]|

  @contact-record-api-CRMC @API-CRM-1046 @API-CRM-Regression @API-CORE  @Sujoy @api-smoke-devops
  Scenario Outline: Adding multiple  contact actions to a contact reason via API
    Given I created a contact record using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated contact reason addition
    When I add new reason "Information Request" for Contact Record Id "" with actions using API
      | Provided Appeal Information             |
      | Provided Case Status/Information        |
      | Provided Eligibility Status/Information |
      | Provided Enrollment Status/Information  |
      | Provided Financial Information          |
      | Provided Program Information            |
    And I run add contact record reason using API
    Then I verify all selected actions found on API response by ""
    Examples:
      | projectName |
      |[blank]|

  @contact-record-api-CRMC @API-CRM-1087 @API-CORE @API-CRM-Regression @Sujoy
  Scenario Outline: Verify Details of Previously Completed Inbound Contact Record Preferred Language through API
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get correlationId id from consumer search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated Contact Records Customers API
    And I can set link consumer Id as reference Id
    And I run the post contact records API with Query parameters with "<page>","<size>" and "<sort>"
    Then I can verify contact records search response is "<Success>"
    And I can verify contact record preferred language equal to "<Language>"
    Examples:
      | consumerFirstName | consumerLastName | page | Language | sort                 | Success | size | projectName |
      | James             | Riley            | 0    | English  | contactRecordId,DESC | True    | 5    |[blank]|


  @contact-record-api-CRMC @API-CRM-1188 @API-CRM-1187 @API-CORE @API-CRM-Regression @shruti
  Scenario Outline: Create Consumer Contact Records for Third Party Contact using API - inbound
    Given I created a contact record using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Create Contact Records API
    And I can provide contact record details "<contactRecordType>", "<consumerType>", "<preferredLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    Then I can run create contact records API
    Then I verify third party contact record is created by calling get contact record api
    Examples:
      | preferredLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId | projectName |
      | English               | Enrollment       | Phone              | 123-456-7890        | Media        | Inbound           |[blank]|           |[blank]|


  @contact-record-api-CRMC @API-CRM-1187 @API-CRM-1188-01 @API-CORE @API-CRM-Regression @shruti @API-CRM-1188
  Scenario Outline: Verify Third Party Contact Record Details using API - inbound
    Given I created a contact record using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Create Contact Records API
    And I can provide contact record details "<contactRecordType>", "<consumerType>", "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    Then I can run create contact records API
    Then I verify third party contact record details "<consumerType>", "<contactRecordType>", "<contactChannelType>" and "<contactChannelValue>"
    Examples:
      | consumerLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId | projectName |
      | English              | Enrollment       | Phone              | 123-456-7890        | Media        | Inbound           |[blank]|           |[blank]|


  @contact-record-api-CRMC  @API-CRM-1187 @API-CRM-1188 @API-CORE @API-CRM-Regression @shruti
  Scenario Outline: Verify Case is linked to Third Party Contact Record
    #Given I created a consumer to link contact record
    #Given I created a case required for validation
    Given I initiated Case Loader API for Create New Case for Contacts Record Validation
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run Case Loader API for Create New Case and Customer with below detail for Contacts Record Validations:
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip | consumerRole | projectName |
      | Consumer     | {random}          | {random}         | 1999-02-11          | {random}    | Male       | today-50           |[blank]| Child        | Member       |[blank]|
    Then I will create a new case for case loader case creation for Contacts Record Validations
    Given I created a contact record using API
    And I initiated Create Contact Records API
    And I can provide contact record details "<contactRecordType>", "<consumerType>", "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    Then I can run create contact records API
    Then I verify third party contact record details "<consumerType>", "<contactRecordType>", "<contactChannelType>" and "<contactChannelValue>"
    And I verify third party contact record link details "<linkRefType>" and "<linkRefId>"
    Examples:
      | consumerLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId | projectName |
      | English              | Enrollment       | Phone              | 1234567890          | Media        | Inbound           | case        |[blank]|             |


  @contact-record-api-CRMC @API-CRM-1187 @API-CORE @API-CRM-Regression  @shruti @API-CRM-1188
  Scenario Outline: Verify Third Party Contact Record Details using API - inbound
    Given I initiated Create Contact Records API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I can provide contact record details "<contactRecordType>", "<consumerType>", "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    Then I can run create contact records API
    Then I verify third party contact record details "<consumerType>", "<contactRecordType>", "<contactChannelType>" and "<contactChannelValue>"
    Examples:
      | consumerLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId | projectName |
      | English              | Enrollment       | Phone              | 123-456-7890        | Media        | Inbound           |[blank]|           |[blank]|


  @contact-record-api-CRMC @API-CRM-1187 @API-CORE @API-CRM-Regression @shruti @API-CRM-1188
  Scenario Outline: Verify Third Party Contact Record Details using API - outbound
    Given I created a contact record using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Create Contact Records API
    And I can provide contact record details "<contactRecordType>", "<consumerType>", "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    #When I can provide additional contact record details  "<contactCampaignType>",  "<contactRecordStatusType>",  "<Program Type>" and "<contactOutcome>"
    Then I can run create contact records API
    Then I verify third party contact record details "<consumerType>", "<contactRecordType>", "<contactChannelType>" and "<contactChannelValue>"
    Examples:
      | consumerLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId | contactCampaignType | contactRecordStatusType | Program Type | contactOutcome               | projectName |
      | English              |[blank]| Phone              | 123-456-7890        | Media        | Outbound          |[blank]|           | Enrollment Reminder | Outbound Incomplete     | Program A    | Did Not Reach/Left Voicemail |[blank]|


  @contact-record-api-CRMC @API-CRM-1186 @API-CRM-1186-01 @API-CORE @API-CRM-Regression @Muhabbat
  Scenario Outline: Viewing Details from Contact History for Previously Completed Inbound Contact Record through API
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get correlationId id from consumer search "James" and "Riley"
    And I initiated Create Contact Records API
    And I can provide following contact record information for phone:
      | contactRecordType       | Inbound                   |
      | contactType             | General                   |
      | contactRecordStatusType | Requested Call Back       |
      | inboundCallQueue        | General Program Questions |
      | contactChannelType      | Phone                     |
    And I can run create contact records API
    And I initiated specific contact Search ""
    And I run Get contact records API
    Then I can verify following inbound contact record information:
      | contactRecordType       | Inbound                   |
      | contactType             | General                   |
      | contactRecordStatusType | Requested Call Back       |
      | inboundCallQueue        | General Program Questions |
      | contactChannelType      | Phone                     |
    Examples:
      | projectName |
      |[blank]|

  @contact-record-api-CRMC @API-CRM-1186 @API-CRM-1186-02 @API-CORE @API-CRM-Regression @Muhabbat
  Scenario Outline: Viewing Details from Contact History for Previously Completed OutBound Contact Record through API
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get correlationId id from consumer search "James" and "Riley"
    And I initiated Create Contact Records API
    And I can provide following contact record information for phone:
      | contactRecordType       | Outbound             |
      | contactType             | General              |
      | contactRecordStatusType | Complete             |
      | contactCampaignType     | Payment Reminder     |
      | contactChannelType      | Web Chat             |
      | contactOutcome          | Reached Successfully |
    And I can run create contact records API
    And I initiated specific contact Search ""
    And I run Get contact records API
    Then I can verify following inbound contact record information:
      | contactRecordType       | Outbound             |
      | contactType             | General              |
      | contactRecordStatusType | Complete             |
      | contactCampaignType     | Payment Reminder     |
      | contactChannelType      | Web Chat             |
      | contactOutcome          | Reached Successfully |

    Examples:
      | projectName |
      |[blank]|


  @contact-record-api-CRMC @API-EB-339 @API-EB-339-01 @API-CORE @API-CRM-Regression  @shruti
  Scenario Outline: Verify Wrap-up time is captured in hh:mm:ss format for Third Party Contact Record
    Given I created a contact record using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Create Contact Records API
    And I can provide contact record details "<contactRecordType>", "<consumerType>", "<preferredLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    When I provide the wrapup time for the contact
    And I can run create contact records API
    Then I verify wrapup time is captured in hh:mm:ss format
    Examples:
      | preferredLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId | projectName |
      | English               | Enrollment       | Phone              | 123-456-7890        | Media        | Inbound           |[blank]|           |[blank]|

  @contact-record-api-CRMC @API-EB-339 @API-EB-339-02 @API-CORE @API-CRM-Regression @shruti
  Scenario Outline: Verify Wrap-up time is captured in hh:mm:ss format for General Contact Record
    Given I created a consumer to link contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Create Contact Records API
    And I can provide general contact record details "<contactRecordType>", "<consumerType>", "<preferredLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    When I provide the wrapup time for the contact
    And I can run create contact records API
    Then I verify wrapup time is captured in hh:mm:ss format
    Examples:
      | preferredLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId |
      | English               | Enrollment       | Phone              | 123-456-7890        | Consumer     | Inbound           | Consumer    |[blank]|


  @contact-record-api-CRMC @API-EB-339 @API-EB-339-03 @API-CORE @API-CRM-Regression @Shruti
  Scenario Outline: Verify Wrap-up time is captured in hh:mm:ss format for Unidentified Contact Record
    Given I created a contact record using API
    Given I initiated Create Contact Records API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I can provide contact records as anonymous "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>" and "<contactChannelValue>"
    When I provide the wrapup time for the contact
    And I can run create contact records API
    Then I verify wrapup time is captured in hh:mm:ss format
    Examples:
      | consumerLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | projectName |
      | English              | Enrollment       | Phone              | 123-456-7890        |[blank]|


  @API-CP-132 @API-CP-135 @API-CP-132 @API-CP-135 @API-CP-143 @asad @API-CORE @API-CRM-Regression
  Scenario Outline: Search Criteria for Contact Record API
    Given I create consumer and link Contact Record through api
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run contact search api for Contact Record
    Then I validate the search results for Contact Record
    Examples:
      | projectName |
      |[blank]|

  @contact-record-api-CRMC @API-CRM-2656 @API-CRM-2656-01 @API-CORE @API-CRM-Regression @basha
  Scenario Outline: Verify contact outcome is successfull with contact recoed status type Incomplete
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get correlationId id from consumer search "Graham" and "Gooch"
    And I initiated Create Contact Records API
    And I can provide following contact record information for phone:
      | contactRecordType       | Outbound             |
      | contactType             | General              |
      | contactRecordStatusType | Incomplete           |
      | contactCampaignType     | Program Information  |
      | contactChannelType      | Phone                |
      | contactOutcome          | Reached Successfully |
    And I can run create contact records API
    And I initiated specific contact Search ""
    And I run Get contact records API
    Then I can verify following inbound contact record information:
      | contactRecordType       | Outbound             |
      | contactType             | General              |
      | contactRecordStatusType | Incomplete           |
      | contactCampaignType     | Program Information  |
      | contactChannelType      | Phone                |
      | contactOutcome          | Reached Successfully |
    Examples:
      | projectName |
      |[blank]|

  @API-CRM-17642 @API-CRM-17642-01 @aikanysh #need to refactor
  Scenario Outline: Contact Record API for Med Chat Integration: API/UI Scenario
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Med Chat Create Contact Records API
    And I can provide following contact record information for Med Chat contact record creation:
      | contactRecordType       | Inbound  |
      | contactRecordStatusType | Complete |
      | contactChannelType      | Phone    |
    And I can run create contact records API
    Examples:
      | projectName |
      | CoverVA     |

  @contact-record-api-CRMC @API-CRM-15078 @API-CRM-15079 @API-CORE @API-CRM-Regression @aikanysh
  Scenario Outline: Systematically Create Contact Record for Plan Changes & New Enrollment Received via External API
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get correlationId id from consumer search "Graham" and "Gooch"
    And I initiated Create Contact Records API
    And I can provide following contact record information for systematic CR creation:
      | contactRecordType       | Inbound  |
      | contactType             | General  |
      | contactRecordStatusType | Complete |
      | contactChannelType      | Phone    |
    And I can run create contact records API
    And I link consumer to the above contact record via api
    And I can add the contact record reason with the following values:
      | contactReason  | Information Request         |
      | contactAction  | Provided Appeal Information |
      | reasonComments | Systematic Creation         |
    Then I verify contact reason actions by contact with following values:
      | contactAction | Provided Appeal Information |
    And I initiated specific contact Search ""
    And I run Get contact records API
    Then I can verify following inbound contact record information:
      | contactRecordType       | Inbound  |
      | contactType             | General  |
      | contactRecordStatusType | Complete |
      | contactChannelType      | Phone    |
    Examples:
      | projectName |
      |[blank]|

  @contact-record-api-CRMC @API-CRM-22097-02 @API-CORE-COVER-VA @API-CRM-Regression @aikanysh
  Scenario Outline: Do not require Email Address for Web Chat Contact Records
    Given I initiated consumer search API for Contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I get correlationId id from consumer search "Harry" and "Potter"
    And I initiated Create Contact Records API
    And I can provide following contact record information for WebChat creation with no email and no phone:
      | contactRecordType       | Inbound  |
      | contactType             | General  |
      | contactRecordStatusType | Complete |
      | contactChannelType      | Web Chat |
    And I can run create contact records API
    And I initiated specific contact Search ""
    And I run Get contact records API
    Then I can verify following inbound contact record information:
      | contactRecordType       | Inbound  |
      | contactType             | General  |
      | contactRecordStatusType | Complete |
      | contactChannelType      | Web Chat |
    Examples:
      | projectName |
      | CoverVA     |

