Feature: Inbound Links to Applications and Missing Items

  @CP-33865 @CP-33865-1 @API-ECMS @James
  Scenario: Verify Missing Item status is updated when Inbound Document is Received
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
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType                | maersk Eligibility    |
      | Language                    | SPANISH                |
      | Channel                     | EMAIL                  |
      | Status                      | COMPLETE               |
      | Missing Information Item ID | previouslyCreated      |
      | ProcessType                 | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I initiated retrieve Missing Information API for "Current" Application using "Application Service"
    And I run the Retrieve missing information API using "Application Service"
    Then I verify the Missing Item status has been updated to "Review"

  @CP-33865 @CP-33865-2 @API-ECMS @James
  Scenario: Verify Inbound Document with Invalid Document Type is rejected
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType                | maersk Invalid        |
      | Language                    | SPANISH                |
      | Channel                     | EMAIL                  |
      | Status                      | COMPLETE               |
      | ProcessType                 | INBOUND CORRESPONDENCE |
    When I send the invalid request for the Inbound Correspondence using the endpoint for Digital
    Then I should see the Inbound Document with an Invalid Document Type has been rejected

  @CP-33987 @CP-33987-1 @API-ECMS @James
  Scenario: Verify Missing Item is linked to the Inbound Document with the Missing Item
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
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType                | maersk Eligibility    |
      | Language                    | SPANISH                |
      | Channel                     | EMAIL                  |
      | Status                      | COMPLETE               |
      | Missing Information Item ID | previouslyCreated      |
      | ProcessType                 | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have an Inbound Correspondence Save event Request with the following values
      | ProcessType                 | INBOUND CORRESPONDENCE   |
      | CHANNEL                     | Mail                     |
      | documentHandle              | InboundDocumentIdDigital |
      | documentType                | maersk Eligibility      |
      | Missing Information Item ID | previouslyCreated        |
    And I send the request to create the Inbound Correspondence Save Event
    Then I should see the "MissingInformationItemId" has been linked to the Inbound Correspondence
    And I retrieve the "LINK_EVENT_MISSING_ITEM" event that is produced by trace id
    And I should see the payload for the "LINK_EVENT_MISSING_ITEM" is as expected

