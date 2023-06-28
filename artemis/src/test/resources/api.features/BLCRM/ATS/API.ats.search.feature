Feature: Application Basic Criteria and Search API

  @API-CP-35772 @API-CP-35772-01 @API-CRM-Regression @API-ATS @vinuta
    #need to run earlier to not have more than 200 results
  Scenario Outline: Update application search API to include application received date range
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd   | interactiveInd   |
      | <applicationType> | CURRENT YYYYMMDD        | <submittedInd> | <interactiveInd> |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    When I initiated search application api for ats
    And I post application search api with application received date from "<applicationReceivedDateFrom>" to "<applicationReceivedDateTo>"
    Then I verify matching records for ats by "APPLICATION RECEIVED DATE RANGE" from the search response
    Examples:
      | submittedInd | interactiveInd | applicationType | applicationReceivedDateFrom | applicationReceivedDateTo |
      | true         | null           | Long Term Care  | TODAY                       | TODAY                     |
    #  | true         | null           | Long Term Care  | TODAY                       |[blank]|

  @API-CP-37525 @API-CP-37525-01 @API-CRM-Regression @API-ATS @vinuta
  Scenario Outline: Update application search API to include application deadline date range
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd | interactiveInd |
      | <applicationType> | CURRENT YYYYMMDD        | true         | null           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | <program> |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    When I initiated search application api for ats
    And I post application search api with application deadline date from "<applicationDeadlineDateFrom>" to "<applicationDeadlineDateTo>"
    Then I verify matching records for ats by "APPLICATION DEADLINE DATE RANGE" from the search response
    Examples:
      | applicationType    | applicationDeadlineDateFrom | applicationDeadlineDateTo | program              |
      | Long Term Care     | TODAY                       | FUTURE 90                 | HCBS                 |
      | Medical Assistance | TODAY                       | FUTURE 45                 | Pregnancy Assistance |
      | Long Term Care     |[blank]| FUTURE 90                 | HCBS                 |
      | Medical Assistance | TODAY                       |[blank]| Pregnancy Assistance |

  @API-CP-37525 @API-CP-37525-02 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Verify no response when both Application Received Date & Application Deadline Date are provided in search API
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated search application api for ats
    And I post application search api with application deadline date and application received date
    Then I get response from application ats api with status code "200" and status "INVALID"

  @API-CP-14376 @API-CP-14376-01.0 @asad @API-ATS @API-CRM-Regression
  Scenario: API Returns Search Matching Results -- name search
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
  #  And I run create application api for ats with type "null"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    When I initiated search application api for ats
    And I search for the created application using search api by "created individual" for ats
    Then I verify all the fields retrieved for ats from the search response

  @API-CP-14376 @API-CP-14376-01.1 @asad @API-ATS @API-CRM-Regression
  Scenario: API Returns Search Matching Results -- other parameters search
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I POST ATS save application api
    When I initiated search application api for ats
    And I search for the created application using search api by "other search params" for ats
    Then I verify all the fields retrieved for ats from the search response

  @API-CP-14376 @API-CP-14376-02 @asad @API-ATS @API-CRM-Regression
  Scenario: Displaying multiple individuals in a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    When I initiated search application api for ats
    And I search for the created application using search api by "multiple individuals" for ats
    Then I verify matching records for ats by "multiple individuals" from the search response

  @API-CP-14376 @API-CP-14376-03 @asad @API-ATS @API-CRM-Regression
  Scenario: Retrieving by single field criterion
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    When I initiated search application api for ats
    And I search for the created application using search api by "single field" for ats
    Then I verify matching records for ats by "single field" from the search response

  @API-CP-14376 @API-CP-14376-04 @asad @API-ATS @API-CRM-Regression
  Scenario: Retrieving by multiple field criteria
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    When I initiated search application api for ats
    And I search for the created application using search api by "multiple field" for ats
    Then I verify matching records for ats by "multiple field" from the search response

  @API-CP-14376 @API-CP-14376-05 @asad @API-ATS @API-CRM-Regression
  Scenario: Displaying by partial parameters
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    When I initiated search application api for ats
    And I search for the created application using search api by "partial parameters" for ats
    Then I verify matching records for ats by "partial parameters" from the search response

  @API-CP-14376 @API-CP-14376-06 @asad @API-ATS @API-CRM-Regression
  Scenario: Default sort
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated search application api for ats
    And I search for the created application using search api by "default sort" for ats
    Then I verify order of records for ats from the search response

  @API-CP-14376 @API-CP-14376-07 @API-CP-36056 @API-CP-36056-01 @priyal @asad @API-ATS @API-CRM-Regression
  Scenario: Displaying excessive results (less than or equals to 200)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated search application api for ats
    And I search for the created application using search api by "duplicate status" for ats
    Then I verify "excessive result" message from the search response
    Then I verify "equals" 200 records in descending order on application search page
    When I initiated search application api for ats
    And I search for the created application using search api by "closed status with first name" for ats
    Then I verify "lessThan" 200 records in descending order on application search page

  @API-CP-14376 @API-CP-14376-08 @asad @API-ATS @API-CRM-Regression
  Scenario: No results found
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated search application api for ats
    And I search for the created application using search api by "no result" for ats
    Then I verify "no result" message from the search response

  @API-CP-14376 @API-CP-14376-09 @asad @API-ATS @API-CRM-Regression
  Scenario: Search by no data fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated search application api for ats
    And I search for the created application using search api by "no search" for ats
    Then I verify "no search" message from the search response

  @API-CP-14376 @API-CP-14376-10 @asad @API-ATS @API-CRM-Regression
  Scenario: Invalid data entry
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated search application api for ats
    And I search for the created application using search api by "invalid search" for ats
    Then I verify "invalid search" message from the search response

  @API-CP-23902 @API-CP-23902-01 @burak @API-ATS @API-CRM-Regression
  Scenario: Verify External Application ID field for search api
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I set external application ID to value "random" in save API
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    When I initiated search application api for ats
    And I search for the created application using search api by "EXTERNAL APPLICATION ID" for ats
    Then I verify matching records for ats by "EXTERNAL APPLICATION ID" from the search response

  @API-CP-23902 @API-CP-23902-02 @burak @API-ATS @API-CRM-Regression
  Scenario: Verify External Application ID search returns multiple record for search API
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I set external application ID to value "random" in save API
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I set external application ID to value "123456asdfgh1234" in save API
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    When I initiated search application api for ats
    And I search for the created application using search api by "EXTERNAL APPLICATION ID" for ats
    Then I verify matching records for ats by "EXTERNAL APPLICATION ID" from the search response

  @API-CP-34252 @API-CP-34252-01 @API-CP-34252-02 @API-CP-34252-03 @API-CP-34252-04 @API-CRM-Regression @API-ATS @chandrakumar
  Scenario Outline: Search the application using search API passing application code as a search parameter when application created through API for Medical Assistance
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
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I get the application id and application type from API response
    And I get the applicaion code from the API response
    And I get the program type from the API response
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | <applicationStatus> |
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | <piStatus> |
    When I initiated search application api for ats
    And I search for the created application using search api by "APPLICATION CODE" for ats
    Then I verify matching records for ats by "APPLICATION CODE" from the search response
    Examples:
      | submittedInd | interactiveInd | applicationType    | applicationStatus | piStatus    |
      | true         | null           | Medical Assistance | Determining       | Determining |
      | false        | null           | Medical Assistance | Entering Data     | Received    |
      | true         | true           | Medical Assistance | Determining       | Determining |
      | true         | false          | Medical Assistance | Determining       | Determining |

  @API-CP-34252 @API-CP-34252-05 @API-CP-34252-06 @API-CP-34252-07 @API-CP-34252-08 @API-CRM-Regression @API-ATS @chandrakumar
  Scenario Outline: Search the application using search API passing application code as a search parameter when application created through API for Long Term Care
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd   | interactiveInd   |
      | <applicationType> | CURRENT YYYYMMDD        | <submittedInd> | <interactiveInd> |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I get the application id and application type from API response
    And I get the applicaion code from the API response
    And I get the program type from the API response
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | <applicationStatus> |
    Then I verify Primary Individual Details in the retrieved response with appId
      | PI CONSUMER STATUS | <piStatus> |
    When I initiated search application api for ats
    And I search for the created application using search api by "APPLICATION CODE" for ats
    Then I verify matching records for ats by "APPLICATION CODE" from the search response
    Examples:
      | submittedInd | interactiveInd | applicationType | applicationStatus | piStatus    |
      | true         | null           | Long Term Care  | Determining       | Determining |
      | false        | null           | Long Term Care  | Entering Data     | Received    |
      | true         | true           | Long Term Care  | Determining       | Determining |
      | true         | false          | Long Term Care  | Determining       | Determining |

  @API-CP-32401 @API-CP-32401-01 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify application search API to include ref id ref id type and ref type
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | externalConsumerId | externalConsumerIdType |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | NEW EXTERNAL ID    | CHIP                   |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I I clear the "CASE CONSUMER ID" list
    And I initiate and POST search Case Consumers with "PRIMARY INDIVIDUAL PI" Application consumer to retrieve and store ids
    When I initiated search application api for ats
    And I search for the created application using search api by "REFERENCE ID" for ats
    Then I verify matching records for ats by "REFERENCE ID" from the search response

  @API-CP-32401 @API-CP-32401-02 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify no search result for application search API with invalid Reference id
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I I clear the "CASE CONSUMER ID" list
    And I initiate and POST search Case Consumers with "PRIMARY INDIVIDUAL PI" Application consumer to retrieve and store ids
    When I initiated search application api for ats
    And I search for the created application using search api by "INVALID REFERENCE ID" for ats
    Then I verify matching records for ats by "INVALID REFERENCE ID" from the search response