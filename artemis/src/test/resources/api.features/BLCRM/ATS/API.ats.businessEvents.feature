Feature: Bussiness events for ATS

  @CP-31925 @CP-31925-01 @CP-31925-02 @ats-events @chandrakumar
  Scenario Outline: Validation of NEW_APPLICATION event for an application
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
    And I get the application id and application type from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName       |
      | NEW_APPLICATION |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 20   | 0    | eventId,desc |
    Then I received "20" events from search event API
    Then I verify the response includes "NEW_APPLICATION" events for "<applicationType>" appplication
    Examples:
      | submittedInd | interactiveInd | applicationType    |
      | false        | null           | Medical Assistance |
      | true         | true           | Medical Assistance |
      | false        | null           | Long Term Care     |
      | true         | true           | Long Term Care     |
      | true         | false          | Medical Assistance |
      | true         | false          | Long Term Care     |

  @CP-32130 @CP-32130-01 @CP-32130-02 @CP-32130-03 @CP-32130-04 @CP-32130-05 @CP-32130-06 @ats-events @chandrakumar
  Scenario Outline: Validation of APPLICATION_CLEARED event for an application created through API
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
    And I get the application id and application type from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName           |
      | APPLICATION_CLEARED |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 10   | 0    | eventId,desc |
    Then I verify the response includes "APPLICATION_CLEARED" events for "<applicationType>" appplication
    Examples:
      | submittedInd | interactiveInd | applicationType    |
      | true         | null           | Medical Assistance |
      | true         | null           | Long Term Care     |
      | false        | null           | Medical Assistance |
      | false        | null           | Long Term Care     |
      | true         | true           | Medical Assistance |
      | true         | true           | Long Term Care     |

  @CP-32130 @CP-32130-07 @CP-32130-08 @ats-events @chandrakumar
  Scenario Outline: Validation of APPLICATION_CLEARED event for an application created through API
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
    And I get the application id and application type from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName           |
      | APPLICATION_CLEARED |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 05   | 0    | eventId,desc |
    Then I verify the response includes "APPLICATION_CLEARED" events for "<applicationType>" appplication
    Examples:
      | submittedInd | interactiveInd | applicationType    |
      | true         | false          | Medical Assistance |
      | true         | false          | Long Term Care     |

  @CP-32140 @CP-32140-01 @CP-32140-02 @CP-32140-03 @CP-32140-04 @CP-32140-05 @CP-32140-06 @CP-32140-07 @CP-32140-08 @ats-events @chandrakumar
  Scenario Outline: Validation of APPLICATION_WITHDRAWN event for an application created through API
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
    And I get the application id and application type from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3136                       |
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName             |
      | APPLICATION_WITHDRAWN |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 10   | 0    | eventId,desc |
    Then I verify "APPLICATION_WITHDRAWN" details in the event payload
    Examples:
      | submittedInd | interactiveInd | applicationType    |
      | true         | null           | Medical Assistance |
      | true         | null           | Long Term Care     |
      | false        | null           | Medical Assistance |
      | false        | null           | Long Term Care     |
      | true         | true           | Medical Assistance |
      | true         | true           | Long Term Care     |
      | true         | false          | Medical Assistance |
      | true         | false          | Long Term Care     |

  @CP-32135 @CP-32135-01 @CP-32135-02 @CP-32135-03 @ats-events @chandrakumar
    #if app is not submitted, we can not expect eligibility, commented received status
  Scenario Outline: Validation of APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE event for an application created through API for Medical Assistance
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd   | interactiveInd   |
      | <applicationType> | CURRENT YYYYMMDD        | <submittedInd> | <interactiveInd> |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
      | CHIP |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I get the application id and application type from API response
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | <applicationStatus> |
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | <piStatus> |
    And I store program details for updating eligibility status
    And I initiated ats eligibility save application api
    And I initiate eligibility status API for ats with following values for "Eligible" status
      | eligibilityStatus | startDate | determinationDate | subProgramId |
      | Eligible          | Future    | Today             | 10           |
    And I initiate eligibility status API for ats with following values for "Not Eligible" status
      | eligibilityStatus | startDate | determinationDate |
      | Not Eligible      | Future    | Today             |
    Then I provide denial reason for eligibility
      | Other Insurance |
      | Over Age        |
    And I POST ATS Update Eligibility status application api with "success" status
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                                     |
      | APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 5    | 0    | eventId,desc |
    Then I verify "APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE" details in the event payload
    Examples:
      | submittedInd | interactiveInd | applicationType    | applicationStatus | piStatus    |
      | true         | null           | Medical Assistance | Determining       | Determining |
   #   | false        | null           | Medical Assistance | Entering Data     | Received    |
      | true         | true           | Medical Assistance | Determining       | Determining |
      | true         | false          | Medical Assistance | Determining       | Determining |

  @CP-32135 @CP-32135-04 @CP-32135-05 @CP-32135-06 @ats-events @chandrakumar
    #if app is not submitted, we can not expect eligibility, commented received status
  Scenario Outline: Validation of APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE event for an application created through API for Long Term Care
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd   | interactiveInd   |
      | <applicationType> | CURRENT YYYYMMDD        | <submittedInd> | <interactiveInd> |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I get the application id and application type from API response
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | <applicationStatus> |
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | <piStatus> |
    And I store program details for updating eligibility status
    And I initiated ats eligibility save application api
    And I initiate eligibility status API for ats with following values for "Eligible" status
      | eligibilityStatus | startDate | determinationDate | subProgramId |
      | Eligible          | Future    | Today             | 50           |
    And I POST ATS Update Eligibility status application api with "success" status
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                                     |
      | APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 5    | 0    | eventId,desc |
    Then I verify "APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE" details in the event payload
    Examples:
      | submittedInd | interactiveInd | applicationType | applicationStatus | piStatus    |
      | true         | null           | Long Term Care  | Determining       | Determining |
   #   | false        | null           | Long Term Care  | Entering Data     | Received    |
      | true         | true           | Long Term Care  | Determining       | Determining |
      | true         | false          | Long Term Care  | Determining       | Determining |

  @CP-32141 @CP-32141-01 @CP-32141-02 @ats-events @vinuta
  Scenario Outline:Verify APPLICATION_EXPIRATON business event is generated for an application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | <Application Type> | <Received Date>         | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
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
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | applicationId      | applicationDeadlineDate |
      | Medical Assistance | <Received Date>         | true         | created previously | <DeadlineDate>          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | applicationConsumerId   |
      | applicationConsumerIdPI |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | applicationConsumerId   |
      | applicationConsumerIdAM |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | applicationConsumerId   |
      | applicationConsumerIdAP |
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
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName              |
      | APPLICATION_EXPIRATION |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 3    | 0    | eventId,desc |
    Then I received "3" events from search event API
    Then I verify "APPLICATION_EXPIRATION" details in the event payload
    Then I verify The "APPLICATION_EXPIRATION" details for the "Primary Individual"
    And I verify The "APPLICATION_EXPIRATION" details for the "Application Member"
    And I verify The "APPLICATION_EXPIRATION" details for the "Authorized Rep"
    And I verify "<numberOfEvents>" "<eventName>" generated and verify details

    Examples:
      | Application Type   | Received Date | DeadlineDate | status  | eventName              | module      | eventType | projectName | numberOfEvents |
      | Medical Assistance | PRIOR 45      | PRIOR 1      | Expired | APPLICATION_EXPIRATION | APPLICATION | expired   |[blank]| 3              |
      | Long Term Care     | PRIOR 90      | PRIOR 1      | Expired | APPLICATION_EXPIRATION | APPLICATION | expired   |[blank]| 3              |

  @API-CP-32134 @API-CP-32134-01 @ats-events @vinuta
  Scenario: Trigger Application Missing Information Business Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | consumerMiddleName    | consumerSuffix |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And Wait for 5 seconds
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                       |
      | APPLICATION_MISSING_INFORMATION |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 1    | 0    | eventId,desc |
    Then I received "1" events from search event API
    Then I verify "APPLICATION_MISSING_INFORMATION" details in the event payload

  @API-CP-32134 @API-CP-32134-02 @ats-events @vinuta
  Scenario: Do not Trigger Application Missing Information Business Event for a host if MI already exists
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | consumerMiddleName    | consumerSuffix |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And Wait for 5 seconds
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                       |
      | APPLICATION_MISSING_INFORMATION |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 3    | 0    | eventId,desc |
    Then I received "3" events from search event API
    Then I verify "APPLICATION_MISSING_INFORMATION" details in the event payload
    And I verify "1" "APPLICATION_MISSING_INFORMATION" generated and verify details

  @API-CP-32134 @API-CP-32134-03 @ats-events @vinuta
  Scenario: Do not Trigger Application Missing Information Business Event if app status is not Determining
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | interactiveInd | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true           | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerMiddleName    | consumerSuffix |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | HCBS |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                       |
      | APPLICATION_MISSING_INFORMATION |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 3    | 0    | eventId,desc |
    Then I received "3" events from search event API
    Then I verify "APPLICATION_MISSING_INFORMATION" details in the event payload
    And I verify "0" "APPLICATION_MISSING_INFORMATION" generated and verify details

  @API-CP-32134 @API-CP-32134-04 @ats-events @vinuta
  Scenario: Trigger Application Missing Information Business Event for each host if new MI created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | consumerMiddleName    | consumerSuffix |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And Wait for 5 seconds
    And I initiated mars search events api
    And I can provide event information to search event as
      | eventName                       |
      | APPLICATION_MISSING_INFORMATION |
    When I can run search event API with query parameters
      | size | page | sort         |
      | 3    | 0    | eventId,desc |
    Then I received "3" events from search event API
    Then I verify "APPLICATION_MISSING_INFORMATION" details in the event payload
    And I verify "2" "APPLICATION_MISSING_INFORMATION" generated and verify details