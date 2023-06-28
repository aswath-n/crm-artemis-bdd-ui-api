Feature: API: API Application Submit to Auto Clear Process

  @API-CP-32173 @API-CP-32173-01 @API-CP-34436 @API-CP-34436-04 @API-ATS @API-CRM-Regression @sang @ats-smoke
  Scenario:Verify member matching task is created through auto clear process when multiple inbound application consumers matches to a single case consumer
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL DUPLICATES DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Targets Unidentified" on the response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |

  @API-CP-32173 @API-CP-32173-02 @API-CP-34436 @API-CP-34436-05 @API-ATS @API-CRM-Regression @sang
  Scenario:Verify member matching task is created through auto clear process when inbound application consumer matches to multiple case consumers
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL DUPLICATES DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Targets Unidentified" on the response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |

  @API-CP-32173 @API-CP-32173-03 @API-ATS @API-CRM-Regression @sang
  Scenario:Verify member matching task is created through auto clear process when there are more consumers in case then inbound application consumers
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL DUPLICATES DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    Then I clear application save submit "REQUEST" list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Targets Unidentified" on the response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |

  @API-CP-32174 @API-CP-32174-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Auto Create Case and Consumer when there is no potential matching
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |interactiveInd|submittedInd|
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |false         | true       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | ESQ            | D                  | RANDOM DOB  | RANDOM SSN | Male       | English         | Spanish        |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    Then I verify all the eligibility status for the programs set to "Pending" for Application Consumers
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | nameCase | Case |

  @API-CP-32174 @API-CP-32174-02 @API-CP-34436 @API-CP-34436-06 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify application is not auto cleared and member matching task created when there is at least one potential match
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL DUPLICATES DATA" for duplicates
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        |false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I POST ATS save application api
    Then I clear application save submit "REQUEST" list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         | false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | DUPLICATE FIRST   | DUPLICATE LAST   | Sr             | B                  | DUPLICATE DOB | DUPLICATE SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | cellPhoneNumber | 5478902318  | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Targets Unidentified |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusMM | Created         |
      | typeMM   | Member Matching |
      | nameMM   | Task            |

  @API-CP-32174 @API-CP-32174-03 @API-CRM-Regression @API-ATS @burak
  Scenario: Auto Create Case and Consumer when there is no potential matching for insufficient flow
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         | false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      |[blank]| Sr             | B                  | RANDOM DOB  |[blank]| Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Insufficient |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate | interactiveInd | submittedInd |
      | created previously | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         | false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    Then I verify all the eligibility status for the programs set to "Pending" for Application Consumers
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | nameCase   | Case                          |
      | statusMIOC | Requested                     |
      | typeMIOC   | BL MI Missing Information     |
      | nameMIOC   | Outbound Correspondence MI    |
      | statusRI   | Complete                      |
      | typeRI     | Review Incomplete Application |
      | nameRI     | Task                          |

  @API-CP-32174 @API-CP-32174-04 @API-CP-34436 @API-CP-34436-07 @API-CRM-Regression @API-ATS @burak
  Scenario: Verify application is not auto cleared and member matching task created when there is at least one potential match for insufficient flow
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        |false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode | writtenLanguage | spokenLanguage |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       | English         | Spanish        |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I POST ATS save application api
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         | false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn           | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      |[blank]| Sr             | B                  | RANDOM DOB  |[blank]| Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Insufficient |
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationId      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate | interactiveInd | submittedInd |
      | created previously | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         | false          | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn           | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | DUPLICATE SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Targets Unidentified |
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | statusRI | Complete                      |
      | typeRI   | Review Incomplete Application |
      | nameRI   | Task                          |

  @API-CP-34436 @API-CP-34436-01 @API-CRM-Regression @API-ATS @burak
  Scenario: Auto Create Case and Consumer when there is no potential matching for interativeInd = true flow MA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |interactiveInd|submittedInd|
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |true         | true       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | ESQ            | D                  | RANDOM DOB  | RANDOM SSN | Male       | English         | Spanish        |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Pregnancy Assistance |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    Then I verify all the eligibility status for the programs set to "Pending" for Application Consumers
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | nameCase | Case |

  @API-CP-34436 @API-CP-34436-02 @API-CRM-Regression @API-ATS @burak
  Scenario: Auto Create Case and Consumer when there is no potential matching for interativeInd = true flow LTC
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |interactiveInd|submittedInd|
      | LONG TERM CARE      | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | CURRENT YYYYMMDD         |true         | true       |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage | pregnancyInd | expectedBabies | expectedDueDate |
      | RANDOM FIRST      | RANDOM LAST      | Sr             | B                  | RANDOM DOB  | RANDOM SSN | Female     | Russian         | Spanish        | True         | 2              | 2022-12-05      |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn        | genderCode | writtenLanguage | spokenLanguage |
      | RANDOM FIRST      | RANDOM LAST      | ESQ            | D                  | RANDOM DOB  | RANDOM SSN | Male       | English         | Spanish        |
    And I initiate save applications api consumer 1 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    Then I verify all the eligibility status for the programs set to "Pending" for Application Consumers
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | nameCase | Case |

  @API-CP-34436 @API-CP-34436-03 @API-CRM-Regression @API-ATS @burak
  Scenario:Verify Application Moved to Targets Unidentified Status when there is a potential match and interactiveInd=true
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I reinitialize Application data for "ALL DUPLICATES DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | true           | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Targets Unidentified" on the response

  @API-CP-32176 @API-CP-32176-01 @API-ATS @API-CRM-Regression @Prithika
  Scenario: 1.0 Auto-Link Application to Case, Auto-Link Consumers
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |applicationSignatureExistsInd|applicationSignatureDate |interactiveInd|
      | Medical Assistance | CURRENT YYYYMMDD        | true         |true                         | CURRENT YYYYMMDD        |false         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
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
    Then I verify the following for application 1 created above
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    And I will verify the response details for get linked applications-tasks  response
      | nameCase | Case |
    And I save the case Id linked to the application
    Then I initiate and run GET consumers on a case
    And I verify number of consumerIds linked to the case are 2
    Then I verify the following for application 2 created above
    And I initiated View application api for API created applications
    When I run the View Application Details API for the Created Application
    And I verify Application Info for the Application with the retrieved response with appId
      | APPLICATION STATUS | Determining |
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    And I will verify the response details for get linked applications-tasks  response
      | nameCase | Case |
    And I save the case Id linked to the application
    Then I initiate and run GET consumers on a case
    And I verify application is linked to previous case
    Then I initiate and run GET consumers on a case
    And I verify number of consumerIds linked to the case are 2

  @API-CP-32176 @API-CP-32176-02 @API-ATS @API-CRM-Regression @Prithika
  Scenario: 2.0 Auto-Link Application to Case, Auto-Create Consumers, Auto-Link Consumers
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I reinitialize Application data for "ALL DUPLICATES DATA" for duplicates
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |applicationSignatureExistsInd|applicationSignatureDate |interactiveInd|
      | Medical Assistance | CURRENT YYYYMMDD        | true         |true                         | CURRENT YYYYMMDD        |false         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
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
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | nameCase | Case |
    And I save the case Id linked to the application
    Then I initiate and run GET consumers on a case
    And I verify number of consumerIds linked to the case are 2
    When I initiated create application api for ats
    And I initiate save applications api using previous request
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 2
      | consumerFirstName | consumerLastName | dateOfBirth | ssn         |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN  |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    And I initiate and run Get Application Links Call
    And I will verify the response details for get linked applications-tasks  response
      | nameCase | Case |
    And I save the case Id linked to the application
    Then I initiate and run GET consumers on a case
    And I verify number of consumerIds linked to the case are 3

