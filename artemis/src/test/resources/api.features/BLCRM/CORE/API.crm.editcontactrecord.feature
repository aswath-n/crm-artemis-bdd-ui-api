Feature: API: Edit General Contact Record Feature

  @crmCoreAPI @contact-record-api-CRMC  @shruti @API-CRM-633 @API-ContactRecords @API-CRM @API-CRM-Regression
  Scenario Outline: Verify Contact Reason /Actions can be edited for an exisiting contact record -General/Inbound
    Given I created a contact record using API
    And I initiated Create Contact Records API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I can provide general contact record details "<contactRecordType>", "<consumerType>", "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    Then I can run create contact records API
    When I initiated contact reason addition
    And I provide contact reason details to create contact reason "<contactRecordReasonType>", "<notes>" and "<contactRecordActionType>"
    Then I ran create contact record reason API
    And I initiated specific contact Record Comments "<contactRecordId>" using API
    And I run Get contact records comments using API
    And I verify contact record reason details "<contactRecordReasonType>", "<notes>" and "<contactRecordActionType>"
    When I initiated contact reason addition
    And I edit contact reason details to create contact reason "<updatedContactRecordReasonType>", "<updatedNotes>" and "<updatedContactRecordActionType>"
    Then I ran create contact record reason API
    And I initiated specific contact Record Comments "<contactRecordId>" using API
    And I run Get contact records comments using API
    And I verify edited contact record reason details "<updatedContactRecordReasonType>", "<updatedNotes>" and "<updatedContactRecordActionType>"

    Examples:
      | consumerLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId | contactRecordReasonType    | notes    | contactRecordActionType | contactRecordId | updatedContactRecordReasonType | updatedNotes | updatedContactRecordActionType |projectName|
      | English              |[blank]| Phone              | 123-456-7890        | Media        | Inbound           | Consumer    |[blank]| Enrollment - Family Dental | api auto | Enrolled - CHP          |[blank]| Materials Request              | upnote       | Re-Sent Notice                 |[blank]|

  @auxiliaryService @link-library-AS @crmCoreAPI @API-CRM-1297 @API-CRM-1297-01 @API-CRM @API-CRM-Regression @API-ContactRecords @shruti
  Scenario Outline: Verify Edits to  Third Party Contact Record
    Given I created a consumer to link contact record
    Given I created a contact record using API
    And I initiated Create Contact Records API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I can provide contact record details "<contactRecordType>", "<consumerType>", "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    Then I can run create contact records API
    Then I verify third party contact record details "<consumerType>", "<contactRecordType>", "<contactChannelType>" and "<contactChannelValue>"
    And I verify third party contact record link details "<linkRefType>" and "<linkRefId>"
    And I initiated Create Contact Records API
    When I can provide contact record edite details
    | consumerType|Agency|
    | preferredLanguageCode|Other|
    | contactReasonEditType|Updating Contact Record Disposition|
    | contactRecordStatusType|Incomplete|
    | linkRefType|consumer|
    | linkRefId|[blank]|
    Then I can run create contact records API
    And I verify edited third party contact record details
      | consumerType            | Agency                              |
      | contactReasonEditType   | Updating Contact Record Disposition |
      | contactRecordStatusType | Incomplete                          |

    Examples:
      | consumerLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId |projectName|
      | English              | Enrollment       | Phone              | 1234567890          | Media        | Inbound           | consumer    |[blank]|           |

  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @crmCoreAPI @contact-record-api-CRMC @API-CRM-1297 @API-CRM-1297-02 @API-CRM @API-CRM-Regression @API-ContactRecords @shruti
  Scenario Outline: Verify edited contact history details - contact history API
    Given I created a consumer to link contact record
    Given I created a contact record using API
    And I initiated Create Contact Records API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I can provide contact record details "<contactRecordType>", "<consumerType>", "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    Then I can run create contact records API
    Then I verify third party contact record details "<consumerType>", "<contactRecordType>", "<contactChannelType>" and "<contactChannelValue>"
    And I verify third party contact record link details "<linkRefType>" and "<linkRefId>"
    And I initiated Create Contact Records API
    When I can provide contact record edite details
      | consumerType            | Agency                              |
      | preferredLanguageCode   | Other                               |
      | contactReasonEditType   | Updating Contact Record Disposition |
      | contactRecordStatusType | Incomplete                          |
      | linkRefType             | consumer                            |
      | linkRefId               |[blank]|
    Then I can run create contact records API
    And I verify edited third party contact record details
      | consumerType            | Agency                              |
      | contactReasonEditType   | Updating Contact Record Disposition |
      | contactRecordStatusType | Incomplete                          |
    Given I initiated contact record edit history API
    When I run contact record edit history API
    Then I verify edited third party contact record history details
      | consumerType            | Agency                              |
      | contactReasonEditType   | Updating Contact Record Disposition |
      | contactRecordStatusType | Incomplete                          |
      | preferredLanguageCode   | Other                               |

    Examples:
      | consumerLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId |projectName|
      | English              | Enrollment       | Phone              | 1234567890          | Media        | Inbound           | consumer    |[blank]|           |

  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @crmCoreAPI @contact-record-api-CRMC  @API-CRM-1272  @API-CRM @API-CRM-Regression @API-ContactRecords @shruti
  Scenario Outline: Verify edited on and edited by fields are captured on contact history details
    Given I created a consumer to link contact record
    Given I created a contact record using API
    And I initiated Create Contact Records API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I can provide contact record details "<contactRecordType>", "<consumerType>", "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    Then I can run create contact records API
    Then I verify third party contact record details "<consumerType>", "<contactRecordType>", "<contactChannelType>" and "<contactChannelValue>"
    And I verify third party contact record link details "<linkRefType>" and "<linkRefId>"
    And I initiated Create Contact Records API
    When I can provide contact record edite details
      | consumerType            | Agency                              |
      | preferredLanguageCode   | Other                               |
      | contactReasonEditType   | Updating Contact Record Disposition |
      | contactRecordStatusType | Incomplete                          |
      | linkRefType             | consumer                            |
      | linkRefId               |[blank]|
    Then I can run create contact records API
    Given I initiated contact record edit history API
    When I run contact record edit history API
    Then I verify edited by and edited on third party contact record history details

    Examples:
      | consumerLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId |projectName|
      | English              | Enrollment       | Phone              | 1234567890          | Media        | Inbound           | consumer    |[blank]|           |

  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @crmCoreAPI @contact-record-api-CRMC @vinuta @API-CRM-285 @API-ContactRecords @API-CRM @API-CRM-Regression
  Scenario Outline: Verify Contact Deposition can be edited for an existing contact record - General/Inbound
  Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a contact record using API
    And I initiated Create Contact Records API
   When I will get the Authentication token for "<projectName>" in "CRM"
    And I can provide general contact record details "<contactRecordType>", "<consumerType>", "<consumerLanguageCode>", "<inboundCallQueue>", "<contactChannelType>", "<contactChannelValue>", "<linkRefType>" and "<linkRefId>"
    Then I can run create contact records API
    And I initiated Create Contact Records API
    When I can provide contact record edite details
      | consumerType            | Agency                              |
      | preferredLanguageCode   | Other                               |
      | contactReasonEditType   | Updating Contact Record Disposition |
      | contactRecordStatusType | Incomplete                          |
      | linkRefType             | consumer                            |
      | linkRefId               |[blank]|
    Then I can run create contact records API
    Given I initiated contact record edit history API
    When I run contact record edit history API
    Then I verify edited contact disposition has updated value

    Examples:
      | consumerLanguageCode | inboundCallQueue | contactChannelType | contactChannelValue | consumerType | contactRecordType | linkRefType | linkRefId |projectName|
      | English              | Enrollment         | Phone              | 1234567890        | consumer        | Inbound           | Consumer    |[blank]|           |
