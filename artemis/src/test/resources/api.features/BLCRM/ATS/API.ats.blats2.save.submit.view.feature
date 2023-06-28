Feature: API: BLATS2 Save Submit View Application API

  @API-CP-29191 @CP-29191-01 @API-CP-21533 @API-CP-21533-07 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario:Application minimum submit error message and successful submit with ssn rule for Primary Individual
    Given I will get the Authentication token for "BLATS2" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Insufficient |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationReceivedDate | applicationType    | submittedInd | interactiveInd |
      | created previously | CURRENT YYYYMMDD        | Medical Assistance | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | ssn  |
      | null |
    And I POST ATS save submit application api and store appId and response in list
    Then I verify the error message in the response when submitting application without minimum information
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationReceivedDate | applicationType    | submittedInd | interactiveInd |
      | created previously | CURRENT YYYYMMDD        | Medical Assistance | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | ssn        |
      | RANDOM SSN |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Submitted |

  @API-CP-29191 @API-CP-29191-02 @API-CP-21533 @API-CP-21533-08 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario:Application minimum submit error message and successful submit with ssn rule for Application Member
    Given I will get the Authentication token for "BLATS2" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Insufficient |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationReceivedDate | applicationType    | submittedInd | interactiveInd |
      | created previously | CURRENT YYYYMMDD        | Medical Assistance | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I POST ATS save submit application api and store appId and response in list
    Then I verify the error message in the response when submitting application without minimum information
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationReceivedDate | applicationType    | submittedInd | interactiveInd |
      | created previously | CURRENT YYYYMMDD        | Medical Assistance | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | null              | null             | null        | RANDOM SSN |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Submitted |

  @API-CP-29191 @API-CP-29191-03 @API-CP-21533 @API-CP-21533-09 @API-CRM-Regression @API-ATS-blats2 @sang
  Scenario:Application minimum submit error message and successful submit with first last name dob rule
    Given I will get the Authentication token for "BLATS2" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Insufficient |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationReceivedDate | applicationType    | submittedInd | interactiveInd |
      | created previously | CURRENT YYYYMMDD        | Medical Assistance | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | RANDOM FIRST      | RANDOM LAST      | null        | null |
    And I POST ATS save submit application api and store appId and response in list
    Then I verify the error message in the response when submitting application without minimum information
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationReceivedDate | applicationType    | submittedInd | interactiveInd |
      | created previously | CURRENT YYYYMMDD        | Medical Assistance | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | null |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Submitted |

  @API-CP-29191 @API-CP-29191-04 @API-CP-21533 @API-CP-21533-10  @API-CRM-Regression @API-ATS-blast2 @sang
  Scenario:Application minimum submit error message and successful submit with first last name dob rule for Application Member
    Given I will get the Authentication token for "BLATS2" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Insufficient |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationReceivedDate | applicationType    | submittedInd | interactiveInd |
      | created previously | CURRENT YYYYMMDD        | Medical Assistance | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | RANDOM LAST      | RANDOM DOB  | null |
    And I POST ATS save submit application api and store appId and response in list
    Then I verify the error message in the response when submitting application without minimum information
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationReceivedDate | applicationType    | submittedInd | interactiveInd |
      | created previously | CURRENT YYYYMMDD        | Medical Assistance | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | null              | null             | null        | null |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn  |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | null |
    And I POST ATS save submit application api and store appId and response in list
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Submitted |

  @API-CP-34008 @API-CP-34008-01 @API-CRM-Regression @API-ATS-blast2 @vinuta
  Scenario: System clears incoming application from API to Determining & Target Unidentified respectively
    Given I will get the Authentication token for "BLATS2" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        |false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | ESQ            | D                  | RANDOM DOB  | RANDOM SSN | Male       | English         | Spanish        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | New          | Phone     | true                          | CURRENT YYYYMMDD         | false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | DUPLICATE FIRST   | DUPLICATE LAST   | Sr             | B                  | DUPLICATE DOB | DUPLICATE SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Targets Unidentified |