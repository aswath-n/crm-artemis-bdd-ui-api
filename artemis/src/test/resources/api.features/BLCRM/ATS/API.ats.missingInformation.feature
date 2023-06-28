Feature: API: Missing Information API

  @API-CP-25773 @API-CP-25773-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Validation of Retrieving  Missing Information for Primary PRIMARY INDIVIDUAL using Application Service
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType | status  | attributeValue | comments |
      | Data        | SSN           | Appeal           | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify all the response details of Retrieve Missing Information API response for "Application Service"

  @API-CP-25773 @API-CP-25773-02 @API-CRM-Regression @API-ATS @burak
  Scenario: Validation of Retrieving  Missing Information for  APPLICATION CONSUMER using Application Service
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
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
      | recordClass | attributeName | entityRecordType | status  | attributeValue | comments |
      | Data        | SSN           | Appeal           | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | APPLICATION MEMBER |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify all the response details of Retrieve Missing Information API response for "Application Service"

  @API-CP-25773 @API-CP-25773-03 @API-CRM-Regression @API-ATS @burak
  Scenario: Validation of creating Missing Information Sorting
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status      |
      | SSN           | Appeal           | DISREGARDED |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status    |
      | SSN           | Appeal           | SATISFIED |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status  |
      | SSN           | Appeal           | PENDING |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status  |
      | SSN           | Appeal           | PENDING |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status      |
      | SSN           | Appeal           | DISREGARDED |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify the sort order for Retrieve Missing Information API

  @API-CP-19963 @API-CP-19963-01 @API-CP-24036 @API-CP-24036-01 @APi-CP-24036-02 @API-CRM-Regression @API-ATS @vinuta
    # @CP-19963-01 Create MI using MI service
      # @CP-24036-01 Set scope to application, scope ID to application ID
        # @CP-24036-02 Retrieve using MI service
  Scenario: Store Missing Information Item API using MI Service
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName            | entityRecordType             | status  | attributeValue | comments |
      | Data        | Citizenship Verification | Application Consumer Program | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    Then I initiated retrieve Missing Information API for "Current" Application using "Missing Information Service"
    And I provide scope "application" with scope ID "Current" to missing information api
    When I run the Retrieve missing information API using "Missing Information Service"
    Then I verify all the response details of Retrieve Missing Information API response for "Missing Information Service"

  @API-CP-19963 @API-CP-19963-02 @API-CRM-Regression @API-ATS @vinuta
     # @CP-19963-02 Create MI using AS service
  Scenario: Store Missing Information Item API using AS Service
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "AS" service
      | recordClass | attributeName            | entityRecordType             | status  | attributeValue | comments |
      | Data        | Citizenship Verification | Application Consumer Program | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "AS" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify all the response details of Retrieve Missing Information API response for "Application Service"

  @API-CP-27042 @API-CP-27042-01 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Update Status of Missing Information Item API using MI Service
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName            | entityRecordType             | status  | attributeValue | comments |
      | Data        | Citizenship Verification | Application Consumer Program | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info Details from the "Save" API response
    And I initiate update missing information API with following values
      | COMMENTS   | random                 |
      | STATUS     | PENDING - INSUFFICIENT |
      | UPDATED BY | 3499                   |
    And I get Missing Info Details from the "Update" API response
    Then I verify all the response details of Update Missing Information API response

  @API-CP-27042 @API-CP-27042-02 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Verify updatedOn is set automatically when MI is updated
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
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
      | recordClass | attributeName            | entityRecordType             | status  | attributeValue | comments |
      | Data        | Citizenship Verification | Application Consumer Program | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info Details from the "Save" API response
    And I initiate update missing information API with following values
      | COMMENTS   | random |
      | STATUS     | REVIEW |
      | UPDATED BY | 3499   |
    When I get Missing Info Details from the "Update" API response
    Then I verify all the response details of Update Missing Information API response
    And I verify missing information has updated on automatically set
    And I verify missing information has completed on as "false"

  @API-CP-27042 @API-CP-27042-03 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Verify completedOn is set automatically when completedBy is set during MI update
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
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
      | recordClass | attributeName            | entityRecordType             | status  | attributeValue | comments |
      | Data        | Citizenship Verification | Application Consumer Program | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info Details from the "Save" API response
    And I initiate update missing information API with following values
      | COMMENTS     | random    |
      | STATUS       | Satisfied |
      | UPDATED BY   | 3499      |
      | COMPLETED BY | 2455      |
    When I get Missing Info Details from the "Update" API response
    Then I verify missing information has completed on as "true"

  @API-CP-26790 @API-CP-26790-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Validation of Updating Save MI API with new Field Completed By User and Completed Date for SATISFIED
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType | status  | attributeValue | comments |
      | Data        | SSN           | Appeal           | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    And I get Missing Info ID from the API response
    Then I get response from missing information ats api with status code "200"
    And I initiate update missing information API with following values
      | COMMENTS     | random    |
      | STATUS       | SATISFIED |
      | COMPLETED BY | 2455      |
      | UPDATED BY   | 3499      |
    When I get Missing Info Details from the "Update" API response
    Then I verify all the response details of Update Missing Information API response
    Then I verify missing information has completed on as "true"
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify the updated fields for the "Retrieve MI in the Application" API
    Then I initiated retrieve Missing Information API for "Current" Application using "Missing Information Service"
    And I provide scope "application" with scope ID "Current" to missing information api
    When I run the Retrieve missing information API using "Missing Information Service"
    Then I verify the updated fields for the "Search Missing Information" API

  @API-CP-26790 @API-CP-26790-02 @API-CRM-Regression @API-ATS @burak
  Scenario: Validation of Updating Save MI API with new Field Completed By User and Completed Date for DISREGARDED
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType | status  | attributeValue | comments |
      | Data        | SSN           | Appeal           | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    And I get Missing Info ID from the API response
    Then I get response from missing information ats api with status code "200"
    And I initiate update missing information API with following values
      | COMMENTS     | random      |
      | STATUS       | DISREGARDED |
      | COMPLETED BY | 2455        |
      | UPDATED BY   | 3499        |
    When I get Missing Info Details from the "Update" API response
    Then I verify all the response details of Update Missing Information API response
    Then I verify missing information has completed on as "true"
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify the updated fields for the "Retrieve MI in the Application" API
    Then I initiated retrieve Missing Information API for "Current" Application using "Missing Information Service"
    And I provide scope "application" with scope ID "Current" to missing information api
    When I run the Retrieve missing information API using "Missing Information Service"
    Then I verify the updated fields for the "Search Missing Information" API

  @API-CP-27014 @API-CP-27014-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Validation of the endpoint for retrieving the existence of the Missing Information
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And  I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue | comments |
      | Data        | DOB           | Application Consumer | PENDING | RANDOM         | RANDOM   |
    And I initiate retrieve existing missing information with tags api
    And I provide scope "application" with scope ID "Current" to missing information api
    And I post retrieve existing missing information with tags api
    And I verify the details of the retrieve existing missing information api
      | PENDING |

  @API-CP-26791 @API-CP-26791-01 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Validate that Missing Information Flag for Application & Application Members is false when no Missing Information is present
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    When I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Missing Information Flag is set
      | APPLICATION MISSING INFORMATION FLAG | false |
      | PI MISSING INFORMATION FLAG          | false |
      | AM MISSING INFORMATION FLAG          | false |

  @API-CP-26791 @API-CP-26791-02 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Validate that Missing Information Flag for Application is true when Missing Informations is Pending & false when they are completed
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Long Term Care  | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "APPLICATION MEMBER" using "MI" service
      | attributeName      | entityRecordType | status    |
      | Application Signed | Application      | SATISFIED |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName    | entityRecordType | status  | attributeValue | comments |
      | Data        | Residence County | Application      | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | APPLICATION MEMBER |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response
    When I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Missing Information Flag is set
      | APPLICATION MISSING INFORMATION FLAG | true  |
      | PI MISSING INFORMATION FLAG          | false |
      | AM MISSING INFORMATION FLAG          | false |
    When I initiate update missing information API with following values
      | COMMENTS | random      |
      | STATUS   | DISREGARDED |
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Missing Information Flag is set
      | APPLICATION MISSING INFORMATION FLAG | false |
      | PI MISSING INFORMATION FLAG          | false |
      | AM MISSING INFORMATION FLAG          | false |

  @API-CP-26791 @API-CP-26791-03 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Validate that Missing Information Flag Primary Individual is true when Missing Informations is Pending & false when they are completed
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Long Term Care  | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status      | attributeValue | comments |
      | Data        | First Name    | Application Consumer | DISREGARDED | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | APPLICATION MEMBER |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType     | status  |
      | SSN           | Application Consumer | PENDING |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response
    When I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Missing Information Flag is set
      | APPLICATION MISSING INFORMATION FLAG | false |
      | PI MISSING INFORMATION FLAG          | true  |
      | AM MISSING INFORMATION FLAG          | false |
    When I initiate update missing information API with following values
      | COMMENTS | random    |
      | STATUS   | SATISFIED |
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Missing Information Flag is set
      | APPLICATION MISSING INFORMATION FLAG | false |
      | PI MISSING INFORMATION FLAG          | false |
      | AM MISSING INFORMATION FLAG          | false |

  @API-CP-26791 @API-CP-26791-04 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Validate that Missing Information Flag Application Member is true when Missing Informations is Pending & when they are completed
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Long Term Care  | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "APPLICATION MEMBER" using "MI" service
      | recordClass | attributeName | entityRecordType     | status      | attributeValue | comments |
      | Data        | First Name    | Application Consumer | DISREGARDED | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "APPLICATION MEMBER" using "MI" service
      | attributeName | entityRecordType     | status  |
      | SSN           | Application Consumer | PENDING |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response
    When I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Missing Information Flag is set
      | APPLICATION MISSING INFORMATION FLAG | false |
      | PI MISSING INFORMATION FLAG          | false |
      | AM MISSING INFORMATION FLAG          | true  |
    When I initiate update missing information API with following values
      | COMMENTS | random    |
      | STATUS   | SATISFIED |
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    Then I verify Missing Information Flag is set
      | APPLICATION MISSING INFORMATION FLAG | false |
      | PI MISSING INFORMATION FLAG          | false |
      | AM MISSING INFORMATION FLAG          | false |

  @API-CP-25058 @API-CP-25058-01 @API-CRM-Regression @API-ATS @sang
  Scenario: Medical Assistance missing information due date minus SEVEN days verification
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
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
      | recordClass | attributeName | entityRecordType | status  | attributeValue | comments |
      | Data        | SSN           | Appeal           | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | MI DUE DATE | MEDICAL ASSISTANCE MI DUE DATE |

  @API-CP-25058 @API-CP-25058-02 @API-CRM-Regression @API-ATS @sang
  Scenario: Long Term Care missing information due date minus FOUR days verification
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Long Term Care  | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
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
      | recordClass | attributeName | entityRecordType | status  | attributeValue | comments |
      | Data        | SSN           | Appeal           | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | MI DUE DATE | LONG TERM CARE MI DUE DATE |

  @API-CP-30420 @API-CP-30420-01 @API-CP-29971 @API-CP-29971-01 @API-CRM-Regression @API-ATS @vinuta
  Scenario: Trigger Missing Information creation upon submission & trigger OB correspondence on Determining for PI
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "2"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "2"
    And I initiate and run Get Application Links Call
    And I will get "Outbound Correspondence" ID for "" type from the application links response
    And I verify newly created Outbound Correspondence is linked to application

  #@API-CP-30420 @API-CP-30420-02 @API-CP-29971 @API-CP-29971-02 @API-CRM-Regression @API-ATS @vinuta
  #This scenario is no longer applicable due to CP-34428
  Scenario: Trigger Missing Information creation upon submission, satisfy MI & No OB correspondence created on Determining for AM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "1"
    And I get Missing Info Details from the "Save" API response
    Then I get response from missing information ats api with status code "200"
    And I initiate update missing information API with following values
      | COMMENTS     | random    |
      | STATUS       | Satisfied |
      | COMPLETED BY | 2455      |
      | UPDATED BY   | 3499      |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationSignatureExistsInd | applicationSignatureDate |
      | created previously | true                          | CURRENT YYYYMMDD         |
    And I POST ATS save application api
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "1"
    And I initiate and run Get Application Links Call
    And I verify that there is no outbound correspondence created

  @API-CP-30420 @API-CP-30420-03 @API-CP-29971 @API-CP-29971-03 @API-CRM-Regression @API-ATS @vinuta
  Scenario: No Missing Information,OB Correspondence created for complete application upon submission, determining
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "0"
    And I initiate and run Get Application Links Call
    And I verify that there is no outbound correspondence created
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "0"
    And I initiate and run Get Application Links Call
    And I verify that there is no outbound correspondence created

  @API-CP-30420 @API-CP-30420-04 @API-CP-29971 @API-CP-29971-04 @API-CRM-Regression @API-ATS @vinuta
  Scenario: No Missing Information,OB Correspondence created for Incomplete application without programs upon submission, determining
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "0"
    And I initiate and run Get Application Links Call
    And I verify that there is no outbound correspondence created
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "0"
    And I initiate and run Get Application Links Call
    And I verify that there is no outbound correspondence created

  @API-CP-30420 @API-CP-30420-05 @API-CP-29971 @API-CP-29971-05 @API-CRM-Regression @API-ATS @vinuta
    #needs refactoring, not failing due to CP-38416
  Scenario: Trigger OB Correspondence when application is edited after submitting & has missing information
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType     | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Medical Assistance  | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    When I initiate save applications api with application level key values
      | applicationId      |
      | created previously |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | ssn  |
      | null |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "1"
    And I initiate and run Get Application Links Call
    And I will get "Outbound Correspondence" ID for "" type from the application links response
    And I verify newly created Outbound Correspondence is linked to application

  @API-CP-29202 @API-CP-29202-01 @API-CP-29203 @API-CP-29203-01 @API-CRM-Regression @API-ATS @sang
  Scenario Outline: Trigger auto create Application Signature Application Missing Information with missing info initiate
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | interactiveInd | submittedInd |
      | <appType>       | CURRENT YYYYMMDD        | true           | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | consumerMiddleName    | consumerSuffix |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate save applications api consumer 0 with program
      | <programs> |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress                     |
      | automationMIdocument@created.com |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | <programs> |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and POST Missing information initiate with "AUTO CREATED"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "1"
    Then I verify response for auto created Missing Info initiate for Application "SIGNATURE DATE"
    Examples:
      | programs             | appType            |
      | Medicaid             | Medical Assistance |
      | CHIP                 | Medical Assistance |
      | Pregnancy Assistance | Medical Assistance |
      | HCBS                 | Long Term Care     |

  @API-CP-29202 @API-CP-29202-02 @API-CP-29203 @API-CP-29203-02 @API-CRM-Regression @API-ATS @sang
  Scenario: No creation of Missing Information Application Signature Item due to no Program associated with Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | consumerMiddleName    | consumerSuffix |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress                     |
      | automationMIdocument@created.com |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and POST Missing information initiate with "AUTO CREATED"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "0"

  @API-CP-30243 @API-CP-30243-01 @API-CP-29203 @API-CP-29203-03 @API-CRM-Regression @API-ATS @sang
  Scenario Outline: Trigger auto create Application Missing Info DOB with associated programs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | interactiveInd | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | <appType>       | CURRENT YYYYMMDD        | true           | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn        | consumerMiddleName    | consumerSuffix |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM SSN | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate save applications api consumer 0 with program
      | <programs> |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress                     |
      | automationMIdocument@created.com |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | <programs> |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and POST Missing information initiate with "AUTO CREATED"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "2"
    Then I verify response for auto created Missing Info initiate for Application "MI DOB"
    Examples:
      | programs             | appType            |
      | CHIP                 | Medical Assistance |
      | Pregnancy Assistance | Long Term Care     |

  @API-CP-30243 @API-CP-30243-02 @API-CP-29203 @API-CP-29203-04 @API-CRM-Regression @API-ATS @sang
  Scenario Outline: No Trigger auto create DOB Application Missing Info item with associated programs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | interactiveInd | applicationSignatureExistsInd | applicationSignatureDate |
      | <appType>       | CURRENT YYYYMMDD        | true           | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn        | consumerMiddleName    | consumerSuffix |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM SSN | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate save applications api consumer 0 with program
      | <programs> |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | <programs> |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and POST Missing information initiate with "AUTO CREATED"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "0"
    Examples:
      | programs | appType            |
      | Medicaid | Medical Assistance |
      | HCBS     | Long Term Care     |

  @API-CP-30243 @API-CP-30243-03 @API-CP-29203 @API-CP-29203-05 @API-CRM-Regression @API-ATS @sang
  Scenario Outline: Trigger auto create Application Missing Info SSN with associated programs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | interactiveInd | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | <appType>       | CURRENT YYYYMMDD        | true           | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | consumerMiddleName    | consumerSuffix |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate save applications api consumer 0 with program
      | <programs> |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress                     |
      | automationMIdocument@created.com |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 1 with program
      | <programs> |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and POST Missing information initiate with "AUTO CREATED"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "2"
    Then I verify response for auto created Missing Info initiate for Application "MI SSN"
    Examples:
      | programs             | appType            |
      | Medicaid             | Medical Assistance |
      | CHIP                 | Medical Assistance |
      | Pregnancy Assistance | Long Term Care     |

  @API-CP-30243 @API-CP-30243-04 @API-CP-29203 @API-CP-29203-06 @API-CRM-Regression @API-ATS @sang
  Scenario: No Trigger auto create SSN Application Missing Info for HCBS LTC program
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | interactiveInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Long Term Care  | CURRENT YYYYMMDD        | true           | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | consumerMiddleName    | consumerSuffix |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 1 with program
      | HCBS |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and POST Missing information initiate with "AUTO CREATED"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "0"

  @API-CP-31228 @API-CP-31228-01 @API-CP-29203 @API-CP-29203-07 @API-CRM-Regression @API-ATS @sang
    #it is failing due to conflict, waiting for the response
  Scenario Outline: Trigger auto create First Name Application Missing Info Item with associated programs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | interactiveInd | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | <appType>       | CURRENT YYYYMMDD        | true           | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerLastName | dateOfBirth | consumerMiddleName    | consumerSuffix | ssn        |
      | RANDOM LAST      | RANDOM DOB  | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | <program> |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress                     |
      | automationMIdocument@created.com |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerLastName | dateOfBirth | ssn        |
      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | <program> |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and POST Missing information initiate with "AUTO CREATED"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "2"
    Then I verify response for auto created Missing Info initiate for Application "FIRST NAME"
    Examples:
      | program              | appType            |
      | Medicaid             | Medical Assistance |
      | CHIP                 | Medical Assistance |
      | Pregnancy Assistance | Medical Assistance |
      | HCBS                 | Long Term Care     |

  @API-CP-31228 @API-CP-31228-02 @API-CP-29203 @API-CP-29203-08 @API-CRM-Regression @API-ATS @sang
    #it is failing due to conflict, waiting for the response
  Scenario Outline: Trigger auto create Last Name Application Missing Info Item with associated programs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | interactiveInd | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | <appType>       | CURRENT YYYYMMDD        | true           | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | dateOfBirth | consumerMiddleName    | consumerSuffix | ssn        |
      | RANDOM FIRST      | RANDOM DOB  | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | <program> |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress                     |
      | automationMIdocument@created.com |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | <program> |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and POST Missing information initiate with "AUTO CREATED"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "2"
    Then I verify response for auto created Missing Info initiate for Application "LAST NAME"
    Examples:
      | program              | appType            |
      | Medicaid             | Medical Assistance |
      | CHIP                 | Medical Assistance |
      | Pregnancy Assistance | Medical Assistance |
      | HCBS                 | Long Term Care     |

  @API-CP-31228 @API-CP-31228-03 @API-CP-29203 @API-CP-29203-09 @API-CRM-Regression @API-ATS @sang
  Scenario: No creation of First Name Missing Information item due to no Program associated with Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerLastName | dateOfBirth | ssn        | consumerMiddleName    | consumerSuffix |
      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | RANDOM MIDDLE INITIAL | RANDOM SUFFIX  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerLastName | dateOfBirth | ssn        |
      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and POST Missing information initiate with "AUTO CREATED"
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "0"

  @API-CP-31419 @API-CP-31419-01 @API-CRM-Regression @API-ATS @Prithika
  Scenario: 1.0 Restrict creation of duplicate MI item for an application consumer ( application consumer level / category)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue | comments |
      | Data        | SSN           | Application Consumer | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify all the response details of Retrieve Missing Information API response for "Application Service"
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue | comments |
      | Data        | SSN           | Application Consumer | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    And I get Missing Info error message from the API response
      | The missing information record for the application member already exists, duplicate record cannot be created |

  @API-CP-31419 @API-CP-31419-02 @API-CRM-Regression @API-ATS @Prithika
  Scenario: 1.1 Allow creation of dependent for an existing MI item ( application consumer level / category)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue | comments |
      | Data        | SSN           | Application Consumer | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify all the response details of Retrieve Missing Information API response for "Application Service"
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "APPLICATION MEMBER" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue | comments |
      | Data        | SSN           | Application Consumer | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | APPLICATION MEMBER |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response

  @API-CP-31419 @API-CP-31419-03 @API-CRM-Regression @API-ATS @Prithika
  Scenario: 1.2 Allow creation of new MI item ( application consumer level / category) -Satisfied
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "AS" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue | comments |
      | Data        | SSN           | Application Consumer | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "AS" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    And I get Missing Info ID from the API response
    Then I get response from missing information ats api with status code "200"
    And I initiate update missing information API with following values
      | COMMENTS     | random    |
      | STATUS       | Satisfied |
      | UPDATED BY   | 3499      |
      | COMPLETED BY | 2455      |
    When I get Missing Info Details from the "Update" API response
    Then I verify missing information has completed on as "true"
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue | comments |
      | Data        | SSN           | Application Consumer | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response

  @API-CP-31419 @API-CP-31419-04 @API-CRM-Regression @API-ATS @Prithika
  Scenario: 1.2 Allow creation of new MI item ( application consumer level / category) -Disregarded
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "AS" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue | comments |
      | Data        | SSN           | Application Consumer | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "AS" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    And I get Missing Info ID from the API response
    Then I get response from missing information ats api with status code "200"
    And I initiate update missing information API with following values
      | COMMENTS     | random      |
      | STATUS       | Disregarded |
      | UPDATED BY   | 3499        |
      | COMPLETED BY | 2455        |
    When I get Missing Info Details from the "Update" API response
    Then I verify missing information has completed on as "true"
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue | comments |
      | Data        | SSN           | Application Consumer | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response

  @API-CP-31419 @API-CP-31419-05 @API-CRM-Regression @API-ATS @Prithika
  Scenario: 2.0 Restrict creation of duplicate MI item for an application and its dependent (application level/ category)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName      | entityRecordType | status  | attributeValue | comments |
      | Data        | Application Signed | Application      | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I update the entityId to ApplicationId for Create Missing Info request
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName      | entityRecordType | status  | attributeValue | comments |
      | Data        | Application Signed | Application      | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I update the entityId to ApplicationId for Create Missing Info request
    And I POST ATS save missing information api
    And I get Missing Info error message from the API response
      | The missing information record for the application member already exists, duplicate record cannot be created |

  @API-CP-31419 @API-CP-31419-05 @API-CRM-Regression @API-ATS @Prithika
  Scenario:  2.1 Allow creation of dependent for an existing MI item( application level / category)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName      | entityRecordType | status  | attributeValue | comments |
      | Data        | Application Signed | Application      | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I update the entityId to ApplicationId for Create Missing Info request
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "APPLICATION MEMBER" using "MI" service
      | recordClass | attributeName      | entityRecordType | status  | attributeValue | comments |
      | Data        | Application Signed | Application      | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | APPLICATION MEMBER |
    And I update the entityId to ApplicationId for Create Missing Info request
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response

  @API-CP-31419 @API-CP-31419-05 @API-CRM-Regression @API-ATS @Prithika
  Scenario: 2.2 Allow creation of new MI item( application level / category) -Satisfied
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName      | entityRecordType | status  | attributeValue | comments |
      | Data        | Application Signed | Application      | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I update the entityId to ApplicationId for Create Missing Info request
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response
    And I initiate update missing information API with following values
      | COMMENTS     | random    |
      | STATUS       | Satisfied |
      | UPDATED BY   | 3499      |
      | COMPLETED BY | 2455      |
    When I get Missing Info Details from the "Update" API response
    Then I verify missing information has completed on as "true"
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName      | entityRecordType | status  | attributeValue | comments |
      | Data        | Application Signed | Application      | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I update the entityId to ApplicationId for Create Missing Info request
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info ID from the API response

  @API-CP-31419 @API-CP-31419-06 @API-CRM-Regression @API-ATS @Prithika
  Scenario: 1.0  Verify a duplicate record is not created on change of status to 'Determining' -Application Level
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false                         | null                     | false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "1"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "1"

  @API-CP-31419 @API-CP-31419-07 @API-CRM-Regression @API-ATS @Prithika
  Scenario: 2.0  Verify a duplicate record is not created on change of status to 'Determining' -Application Consumer Level
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | null |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "1"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    When I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the number of missing information returned in the response are "1"

  @API-CP-34454 @API-ATS @API-CRM-Regression @Prithika
  Scenario: Enable Linking to Missing Information Item
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |applicationSignatureExistsInd|applicationSignatureDate |interactiveInd|
      | Medical Assistance | CURRENT YYYYMMDD        | true         |true                         | CURRENT YYYYMMDD        |false         |
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
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN  |
    And I post on ATS save application api and update following values for "PRIMARY INDIVIDUAL" consumer 0 with following data for matching application
      | writtenLanguage |
      | English         |
    Then I get response from application ats api with status code "200" and status "success"
    Then I verify the following for application 2 created above
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    And I initiated create missing information api for ats using "AS" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | comments |
      | Data        | SSN           | Application Consumer | Pending | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | APPLICATION MEMBER |
    And I update the entityId to ApplicationId for Create Missing Info request
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I retrieve Missing Info Id from create MI response
    And I initiate GET missing info links api for ATS using "APPLICATION" reference type
    And I retrieve and verify missing info for external reference type "MISSING_INFO" and internal reference type "APPLICATION" as "DO NOT EXIST"
    Then I initiate POST missing info link api for ATS
    And I POST missing info link request
    And I initiate GET missing info links api for ATS using "APPLICATION" reference type
    And I retrieve and verify missing info for external reference type "MISSING_INFO" and internal reference type "APPLICATION" as "EXPECTED"
    And I initiate GET missing info links api for ATS using "MISSING-INFO" reference type
    And I retrieve and verify missing info for external reference type "APPLICATION" and internal reference type "MISSING_INFO" as "EXPECTED"