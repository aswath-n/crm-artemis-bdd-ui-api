Feature: API: Define Rule Matching Member based on filters

  @API-CP-14368 @API-CP-14368-01 @API-CP-22637 @API-CP-22637-01 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario: Verify Response structure for POST Matching API with a single MEDICAL ASSISTANCE Duplicate Application With a match score of 100 for First Last DoB and SSN
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I POST 2 "MEDICAL ASSISTANCE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | consumerSuffix | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | API            | A                  |
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "SINGLE" match response

  @API-CP-14368 @API-CP-14368-02 @API-CP-22637 @API-CP-22637-02 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario: Verify Response structure for POST Matching API with a single Duplicate Long Term Care Application With a match score of 100 for First Last DoB and SSN
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I POST 2 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | consumerSuffix | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | API            | A                  |
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "SINGLE" match response

  @API-CP-14368 @API-CP-14368-03 @API-CP-22637 @API-CP-22637-03 @API-CP-16375 @API-CP-16375-01 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario: Verify Response structure for POST Matching API with a Multiple SSN Duplicate Application With a match score of 50 for SSN match
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | ssn           |
      | RANDOM FIRST      | RANDOM LAST      | DUPLICATE SSN |
    And I POST ATS save submit application api and store appId and response in list
    And I POST 3 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | ssn           | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | DUPLICATE SSN | RANDOM DOB  |
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "MULTIPLE SSN RULE" match response

  @API-CP-14368 @API-CP-14368-04 @API-CP-22637 @API-CP-22637-04 @API-CP-14374 @API-CP-14374-01 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario: Verify Response structure for POST Matching API with a Multiple First Last DOB Duplicate Application With a match score of 50 or 40 without DOB
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | DUPLICATE FIRST   | DUPLICATE LAST   |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | dateOfBirth |
      | RANDOM FIRST      | RANDOM DOB  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save submit application api and store appId and response in list
    And I POST 2 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "MULTIPLE NAME RULE" match response

  @API-CP-14368 @API-CP-14368-05 @API-CP-22637 @API-CP-22637-05 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario: Verify Response structure for POST Matching API for a matching Application Member to an existing Application with a score of 100
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | consumerSuffix | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | API            | A                  |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Pregnancy Assistance |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName |
      | RANDOM FIRST      |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | consumerSuffix | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | API            | A                  |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName |
      | RANDOM FIRST      |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | consumerSuffix | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | API            | A                  |
    And I POST ATS save submit application api and store appId and response in list
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "APPLICATION MEMBER SINGLE" match response

  @API-CP-14368 @API-CP-14368-07 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario: Validate 404 and status of NOT FOUND for no application Id in Data Base
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate applicationdata matching POST API with the "NOT FOUND" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "NOT FOUND" match response

  @API-CP-14368 @API-CP-14368-08 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario: Validate 400 and status of applicationId must be supplied for null appId in request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiate applicationdata matching POST API with the "NULL APP ID" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "NULL APP ID" match response

  @API-CP-25612 @API-CP-25612-01 @API-CRM-Regression @API-ATS-blats2 @sang #fails till CP-25612 story is in qa-rls
  Scenario: No additional match score for null matches for First Name Last Name and DOB
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I POST 2 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | ssn           |
      | DUPLICATE SSN |
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "NULL FIRST LAST DOB" match response

  @API-CP-25612 @API-CP-25612-02 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario: No additional match score for null matches for SSN
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I POST 2 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "NULL SSN" match response

  @API-CP-22637 @API-CP-22637-06 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario: Sorting matching applications by matchScore in descending order and then appId in ascending order
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I POST 2 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | ssn           |
      | DUPLICATE SSN |
    And I POST 2 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE SSN |
    And I POST 2 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "MATCHING APP SORTING" match response

  @API-CP-22637 @API-CP-22637-07 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario: Sorting matching application members by matchScore in descending order and then application consumer in ascending order
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE SSN |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 2
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 3
      | consumerFirstName | consumerLastName | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE SSN |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 4
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 5
      | consumerFirstName | consumerLastName |
      | DUPLICATE FIRST   | DUPLICATE LAST   |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I POST ATS save submit application api and store appId and response in list
    And I initiate applicationdata matching POST API with the "FIRST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "MATCHING APP MEMBERS SORTING" match response

  @API-CP-28737 @API-CP-28737-01 @API-CRM-Regression @API-ATS @sang
  Scenario: Application send to research Initiate Action API success
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save submit application api and store appId and response in list
    When I initiate Application send to research Initiate Action API
      | action           | reasons          | notes          |
      | Send To Research | Multiple Matches | 250 CHARACTERS |
    And I Post Application send to research Initiate Action API
    Then I verify Application send to research Initiate Action API
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications for initiate actions

  @API-CP-28737 @API-CP-28737-02 @API-CRM-Regression @API-ATS @sang
  Scenario: Application send to research Initiate Action API failure due to more than 250 characters
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save submit application api and store appId and response in list
    When I initiate Application send to research Initiate Action API
      | action           | reasons          | notes          |
      | Send To Research | Multiple Matches | 251 CHARACTERS |
    And I Post Application send to research Initiate Action API
    Then I verify Application send to research Initiate Action API "NOTES" Failure

  @API-CP-28737 @API-CP-28737-03 @API-CRM-Regression @API-ATS @sang
  Scenario: Application send to research Initiate Action API failure due Wrong action value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save submit application api and store appId and response in list
    When I initiate Application send to research Initiate Action API
      | action          | reasons          | notes          |
      | end To Research | Multiple Matches | 250 CHARACTERS |
    And I Post Application send to research Initiate Action API
    Then I verify Application send to research Initiate Action API "ACTIONS" Failure

  @API-CP-28737 @API-CP-28737-04 @API-CRM-Regression @API-ATS @sang
  Scenario: Application send to research Initiate Action API failure due to wrong reasons value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save submit application api and store appId and response in list
    When I initiate Application send to research Initiate Action API
      | action           | reasons         | notes      |
      | Send To Research | ultiple Matches | CHARACTERS |
    And I Post Application send to research Initiate Action API
    Then I verify Application send to research Initiate Action API "REASONS" Failure

  @API-CP-28113 @API-CP-28113-01 @API-CRM-Regression @API-ATS @sang
  Scenario: Application to Consumer matching score result for First and Last Name with DOB
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    And I I clear the "CASE CONSUMER ID" list
    And I initiate and POST search Case Consumers with "PRIMARY INDIVIDUAL DUPLICATE" Application consumer to retrieve and store ids
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "CASE CONSUMERS FLD RULE" match response

  @API-CP-28113 @API-CP-28113-02 @API-CRM-Regression @API-ATS @sang
  Scenario: Application to Consumer matching score result for SSN
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | RANDOM DOB  | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I I clear the "CASE CONSUMER ID" list
    And I initiate and POST search Case Consumers with "PRIMARY INDIVIDUAL DUPLICATE" Application consumer to retrieve and store ids
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "CASE CONSUMERS SSN RULE" match response

  @API-CP-28113 @API-CP-28113-03 @API-CRM-Regression @API-ATS @sang  #Will Fail in qa-rls untill to CP-34481 story is deployed (C&C regression functionality change)
  Scenario: Application to Consumer matching score result for external consumer type and Id
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | RANDOM DOB  | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I I clear the "CASE CONSUMER ID" list
    And I initiate and POST search Case Consumers with "PRIMARY INDIVIDUAL DUPLICATE" Application consumer to retrieve and store ids
    And I initiate and PUT consumer request to update "PRIMARY CONSUMER" with following
      | identificationNumberType | externalConsumerId | consumerSSN   |
      | CHIP                     | RANDOM ID          | DUPLICATE SSN |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | externalConsumerId       | externalConsumerIdType |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | AUTO CREATED EXTERNAL ID | CHIP                   |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "CASE CONSUMERS EXTERNAL ID RULE" match response

  @API-CP-28113 @API-CP-28113-04 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify Multiple matching Case Consumer matches returned are sorted by matchScore descending order
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
  # Case 2: Duplicate First Last name (40)
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | DUPLICATE FIRST   | DUPLICATE LAST   | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    # Case 3 Dupluicate ssn (50)
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
       # Case 1 Duplicate Eternal Id (60)
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I I clear the "CASE CONSUMER ID" list
    And I initiate and POST search Case Consumers with "PRIMARY INDIVIDUAL PI" Application consumer to retrieve and store ids
    And I initiate and PUT consumer request to update "PRIMARY CONSUMER" with following
      | identificationNumberType | externalConsumerId | consumerSSN |
      | CHIP                     | RANDOM ID          | RANDOM SSN  |
    # Inbound Application
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | externalConsumerId       | externalConsumerIdType | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | AUTO CREATED EXTERNAL ID | CHIP                   | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Entering Data" on the response
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "INBOUND APP MATCHING MULTIPLE CASE" match response

  @API-CP-28113 @API-CP-28113-05 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify Multiple matching Case Consumer matches returned with same score are in application ID ascending order
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    # Case 1
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | DUPLICATE FIRST   | DUPLICATE LAST   | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    # Case 2
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | DUPLICATE FIRST   | DUPLICATE LAST   | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    # Case 3
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | DUPLICATE FIRST   | DUPLICATE LAST   | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
     # Inbound Application
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn        |
      | DUPLICATE FIRST   | DUPLICATE LAST   | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Entering Data" on the response
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "SAME SCORE MATCHING SORTING MULTIPLE CASE" match response

  @API-CP-28113 @API-CP-28113-06 @API-CRM-Regression @API-ATS @sang
  Scenario: Multiple matching Application members to a case consumer is sorted by matchScore descending order
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn           | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE SSN | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I I clear the "CASE CONSUMER ID" list
    And I initiate and POST search Case Consumers with "PRIMARY INDIVIDUAL DUPLICATE" Application consumer to retrieve and store ids
    And I initiate and PUT consumer request to update "PRIMARY CONSUMER" with following
      | identificationNumberType | externalConsumerId | consumerSSN   |
      | CHIP                     | RANDOM ID          | DUPLICATE SSN |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false        |
    # External Id 60pts
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | externalConsumerId       | externalConsumerIdType | consumerMiddleName |
      | RANDOM FIRST      | RANDOM LAST      | AUTO CREATED EXTERNAL ID | CHIP                   | B                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    # First Last name 40pts
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | D                  |
     # SSN 50pts
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 2
      | consumerFirstName | consumerLastName | ssn           | consumerMiddleName |
      | RANDOM FIRST      | RANDOM LAST      | DUPLICATE SSN | C                  |
    # First Last name DoB SSN 100pts
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 3
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | A                  |
    And I POST ATS save submit application api and store appId and response in list
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Entering Data" on the response
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "MULTIPLE APP MEM MATCHING TO A SINGLE CONSUMER" match response

  @API-CP-28113 @API-CP-28113-07 @API-CRM-Regression @API-ATS @sang
  Scenario: Multiple  same matching Application members to a case consumer is sorted by application consumer ID in ascending order
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn           | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE SSN | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 2
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 3
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 4
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save submit application api and store appId and response in list
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Entering Data" on the response
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "MULTIPLE APP MEM SAME SCORE MATCHING TO A SINGLE CONSUMER" match response

  @API-CP-33954 @API-CP-33954-01 @API-CRM-Regression @API-ATS @sang  #Will Fail in qa-rls untill to CP-34481 story is deployed (C&C regression functionality change)
  Scenario Outline: Verify Non Active Case Consumer Future and Inactive is not matched and returned in the Application to Case Consumer match API
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I I clear the "CASE CONSUMER ID" list
    And I initiate and POST search Case Consumers with "PRIMARY INDIVIDUAL DUPLICATE" Application consumer to retrieve and store ids
    And I initiate and PUT consumer request to update "PRIMARY CONSUMER" with following
      | effectiveStartDate | effectiveEndDate | consumerSSN |
      | <Start Date>       | <End Date>       | <ssn>       |
    # Inbound Application
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn        |
      | DUPLICATE FIRST   | DUPLICATE LAST   | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "RETRIEVE NON ACTIVE CONSUMER" match response
    # Scenario Scenario Outline 1 is for Inactive
    # Scenario Scenario Outline 2 is for Future
    Examples:
      | Start Date           | End Date             | ssn           |
      | 2021-04-20T00:00:00Z | 2021-04-29T00:00:00Z | DUPLICATE SSN |
      | 2023-04-29T00:00:00Z | 2023-04-29T00:00:00Z | DUPLICATE SSN |

  @API-CP-34640 @API-CP-34640-01 @API-CRM-Regression @API-ATS @sang
  Scenario: Filter out Inbound Application Authorized Representative role in Matching API in caseConsumer Match
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName | consumerMiddleName | accessType  | consumerType | correspondence      | authorizedRepOrgName | authorizedRepSignature | authorizedRepSignatureDate       |
      | DUPLICATE FIRST   | DUPLICATE LAST   | M                  | Full Access | Broker       | Receive in Place Of | VA Foundation        | true                   | 2021-06-03T19:54:57.274000+00:00 |
    And I initiate save applications api consumer 1 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Mailing     | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "AUTH REP FILTER POSITIVE" match response
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | false        | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate applicationdata matching POST API with the "LAST" created applicationId
    And I POST ATS applicationdata matching API
    Then I verify the matching response for "AUTH REP FILTER NEGATIVE" match response

  @API-CP-33807 @API-CP-33807-01 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify existing application linked to the case consumer for potential matches in flight for First Last Name DoB rule
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
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
    And I initiate and POST search Case Consumers with "PRIMARY INDIVIDUAL DUPLICATE" Application consumer to retrieve and store ids
    And I initiate consumers for matching API
      | firstName       | lastName       | dateOfBirth   | personId |
      | DUPLICATE FIRST | DUPLICATE LAST | DUPLICATE DOB | 1111     |
    And I send POST consumers matching API for "LinksInfoTrue" response
    Then I verify the response from a successful "LINKS INFO TRUE FLD RULE" consumer matching API

  @API-CP-33807 @API-CP-33807-02 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify existing application linked to the case consumer for potential matches in flight for SSN rule
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL STATIC DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE SSN |
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
    And I initiate and POST search Case Consumers with "PRIMARY INDIVIDUAL DUPLICATE" Application consumer to retrieve and store ids
    And I initiate consumers for matching API
      | ssn           | personId |
      | DUPLICATE SSN | 1111     |
    And I send POST consumers matching API for "LinksInfoTrue" response
    Then I verify the response from a successful "LINKS INFO TRUE SSN RULE" consumer matching API

  @API-CP-33807 @API-CP-33807-03 @API-CRM-Regression @API-ATS @sang
  Scenario: Verify existing application linked to the case consumer for potential matches in flight for External Type and Id rule
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
    And I initiate and PUT consumer request to update "PRIMARY CONSUMER WITH EXISTING EXID AND TYPE" with following
      | identificationNumberType | externalConsumerId       |
      | CHIP                     | EXID SAME AS APPLICATION |
    And I initiate consumers for matching API
      | personId | AUTO externalConsumerId | externalConsumerIdType |
      | 1111     | SAME AS APPLICATION     | CHIP                   |
    And I send POST consumers matching API for "LinksInfoTrue" response
    Then I verify the response from a successful "LINKS INFO TRUE EXID RULE" consumer matching API