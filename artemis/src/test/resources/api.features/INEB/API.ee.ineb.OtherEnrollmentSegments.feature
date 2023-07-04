@CP-24297-test
Feature: Create Update LILO and BU event for LILO


  @API-CP-24297-01 @API-CP-24298-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline:Verify new Lockin Lock out segment is created with different provider ID and event triggered (Non HIP)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayof2MonthsBefore   |
      | eligibilityStartDate         | 1stDayof2MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    And User provide other enrollment segments details
      | recordId          | 21                             |
      | consumerId        | <name>.consumerId              |
      | startDate         | 1stDayof2MonthsBefore          |
      | endDate           | 90DayFrom1stDayof2MonthsBefore |
      | genericFieldText1 | O                              |
      | genericFieldDate1 | 1stDayofPresentMonth           |
      | segmentTypeCode   | OPEN_ENROLLMENT                |
    And User provide other enrollment segments details
      | recordId          | 21                |
      | consumerId        | <name>.consumerId |
      | startDate         | <startDate1>      |
      | endDate           | <endDate1>        |
      | genericFieldText1 | I                 |
      | genericFieldDate1 | openEnrollmentDay |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO              |
      | genericFieldText2 | <provider1>       |
      | genericFieldText5 | ERIC SCHALLET     |
      | genericFieldText3 | Medical           |
    And I run create Eligibility and Enrollment API
    Then I will verify business events are generated with data
      # 1.0 Business Event Required Fields
      | eventName                               | LOCKIN_LOCKOUT                  |
      | channel                                 | SYSTEM_INTEGRATION              |
      | createdBy                               | 597                             |
      | processDate                             | current                         |
      | externalCaseId                          | <name>.caseIdentificationNumber |
      | externalConsumerId                      | <name>.externalConsumerId       |
      | consumerId                              | <name>.consumerId               |
      | consumerName                            | <name>                          |
      # 2.0 Business Event Optional Enrollment fields from “lockinout”
      | lockInLockOut.[0].medicaidId            | <name>.externalConsumerId       |
      | lockInLockOut.[0].memberDateEffective   | <startDate1>                    |
      | lockInLockOut.[0].memberDateEnd         | <endDate1>                      |
      | lockInLockOut.[0].providerDateEffective | openEnrollmentDay               |
      | lockInLockOut.[0].providerDateEnd       | null                            |
      | lockInLockOut.[0].providerId            | 300029354                       |
      | lockInLockOut.[0].providerName          | ERIC SCHALLET                   |
      | lockInLockOut.[0].providerSpecialty     | null                            |
      | lockInLockOut.[0].providerTypeCode      | Medical                         |
      | lockInLockOut.[0].restrictionIndicator  | I                               |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayof2MonthsBefore   |
      | eligibilityStartDate         | 1stDayof2MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | fileSource                   | RECIPIENT               |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | <endDate1>            |
      | genericFieldText1 | I                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO                  |
      | genericFieldText2 | <provider1>           |
      | genericFieldText5 | ERIC SCHALLET         |
      | genericFieldText3 | Medical               |
    And User provide other enrollment segments details
      | recordId          | 21                |
      | consumerId        | <name>.consumerId |
      | startDate         | <startDate2>      |
      | endDate           | <endDate2>        |
      | genericFieldText1 | I                 |
      | genericFieldDate1 | openEnrollmentDay |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO              |
      | genericFieldText2 | <provider2>       |
      | genericFieldText5 | BO  ASANTE      M |
      | genericFieldText3 | RCP-Physician     |
    And I run create Eligibility and Enrollment API
    Then I verify other enrollment segment for coma separated consumerIds "<name>.consumerId" with data
      | [0].consumerId        | <name>.consumerId              |
      | [0].segmentTypeCode   | LILO                           |
      | [0].startDate         | <startDate1>                   |
      | [0].endDate           | <endDate1>                     |
      | [0].genericFieldText1 | I                              |
      | [0].genericFieldText2 | <provider1>                    |
      | [0].genericFieldText3 | Medical                        |
      | [0].genericFieldText5 | ERIC SCHALLET                  |
      | [0].genericFieldDate1 | openEnrollmentDay              |
      | [0].genericFieldDate2 | null                           |
      | [0].createdOn         | current                        |
      | [0].createdBy         | 597                            |
      | [0].updatedOn         | current                        |
      | [0].updatedBy         | 597                            |
      | [0].timeframe         | Active                         |
      # * * *  * * *
      | [1].consumerId        | <name>.consumerId              |
      | [1].segmentTypeCode   | OPEN_ENROLLMENT                |
      | [1].startDate         | 1stDayof2MonthsBefore          |
      | [1].endDate           | 90DayFrom1stDayof2MonthsBefore |
      | [1].enrollmentId      | null                           |
      | [1].genericFieldText1 | O                              |
      | [1].genericFieldText2 | null                           |
      | [1].genericFieldText3 | null                           |
      | [1].genericFieldText5 | null                           |
      | [1].genericFieldDate1 | 1stDayofPresentMonth           |
      | [1].createdOn         | current                        |
      | [1].createdBy         | 597                            |
      | [1].updatedOn         | current                        |
      | [1].updatedBy         | 597                            |
      | [1].timeframe         | Active                         |
      # * * *  * * *
      | [2].consumerId        | <name>.consumerId              |
      | [2].segmentTypeCode   | LILO                           |
      | [2].startDate         | <startDate2>                   |
      | [2].endDate           | <endDate2>                     |
      | [2].enrollmentId      | null                           |
      | [2].genericFieldText1 | I                              |
      | [2].genericFieldText2 | <provider2>                    |
      | [2].genericFieldText3 | RCP-Physician                  |
      | [2].genericFieldText5 | BO  ASANTE      M              |
      | [2].genericFieldDate1 | openEnrollmentDay              |
      | [2].createdOn         | current                        |
      | [2].createdBy         | 597                            |
      | [2].updatedOn         | current                        |
      | [2].updatedBy         | 597                            |
      | [2].timeframe         | Active                         |
    Then I will verify business events are generated with data
      # 1.0 Business Event Required Fields
      | eventName                               | LOCKIN_LOCKOUT                  |
      | channel                                 | SYSTEM_INTEGRATION              |
      | createdBy                               | 597                             |
      | processDate                             | current                         |
      | externalCaseId                          | <name>.caseIdentificationNumber |
      | externalConsumerId                      | <name>.externalConsumerId       |
      | consumerId                              | <name>.consumerId               |
      | consumerName                            | <name>                          |
      | consumerPopulation                      | <population>                    |
  # 2.0 Business Event Optional Enrollment fields from “lockinout ”
      | lockInLockOut.[0].medicaidId            | <name>.externalConsumerId       |
      | lockInLockOut.[0].memberDateEffective   | <startDate1>                    |
      | lockInLockOut.[0].memberDateEnd         | <endDate1>                      |
      | lockInLockOut.[0].providerDateEffective | openEnrollmentDay               |
      | lockInLockOut.[0].providerDateEnd       | null                            |
      | lockInLockOut.[0].providerId            | 300029354                       |
      | lockInLockOut.[0].providerName          | ERIC SCHALLET                   |
      | lockInLockOut.[0].providerSpecialty     | null                            |
      | lockInLockOut.[0].providerTypeCode      | Medical                         |
      | lockInLockOut.[0].restrictionIndicator  | I                               |
      # * * * * * *
      | lockInLockOut.[1].medicaidId            | <name>.externalConsumerId       |
      | lockInLockOut.[1].memberDateEffective   | <startDate2>                    |
      | lockInLockOut.[1].memberDateEnd         | <endDate2>                      |
      | lockInLockOut.[1].providerDateEffective | openEnrollmentDay               |
      | lockInLockOut.[1].providerDateEnd       | null                            |
      | lockInLockOut.[1].providerId            | 30002                           |
      | lockInLockOut.[1].providerName          | BO  ASANTE      M               |
      | lockInLockOut.[1].providerSpecialty     | null                            |
      | lockInLockOut.[1].providerTypeCode      | RCP-Physician                   |
      | lockInLockOut.[1].restrictionIndicator  | I                               |
    Examples:
      | name    | programCode | subProgramCode     | eligibilityStatusCode | population    | startDate1            | startDate2            | endDate1              | endDate2 | provider1 | provider2 |
      | 24298-1 | R           | HoosierHealthwise  | M                     | HHW-Mandatory | 1stDayof2MonthsBefore | 1stDayof2MonthsBefore | lastDayofPresentMonth | highDate | 300029354 | 30002     |
      | 24298-3 | A           | HoosierCareConnect | V                     | HCC-Voluntary | 1stDayof2MonthsBefore | 1stDayof2MonthsBefore | lastDayofPresentMonth | highDate | 300029354 | 30002     |


  @API-CP-24297-01 @API-CP-24298-01 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline:Verify new Lockin Lock out segment is created with different provider ID and event triggered (HIP)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayof2MonthsBefore   |
      | eligibilityStartDate         | 1stDayof2MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | Eligible                |
      | fileSource                   | HRECIP                  |
    And User provide other enrollment segments details
      | recordId          | 21                             |
      | consumerId        | <name>.consumerId              |
      | startDate         | 1stDayof2MonthsBefore          |
      | endDate           | 90DayFrom1stDayof2MonthsBefore |
      | genericFieldText1 | O                              |
      | genericFieldDate1 | 1stDayofPresentMonth           |
      | segmentTypeCode   | OPEN_ENROLLMENT                |
    And User provide other enrollment segments details
      | recordId          | 21                |
      | consumerId        | <name>.consumerId |
      | startDate         | <startDate1>      |
      | endDate           | <endDate1>        |
      | genericFieldText1 | I                 |
      | genericFieldDate1 | openEnrollmentDay |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO              |
      | genericFieldText2 | <provider1>       |
      | genericFieldText5 | ERIC SCHALLET     |
      | genericFieldText3 | Medical           |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | eligibilityStartDate         | 1stDayof2MonthsBefore   |
      | eligibilityEndDate           | null                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore   |
      | enrollmentEndDate            | null                    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | 499254630               |
      | planId                       | 33                      |
      | serviceRegionCode            | Statewide               |
      | anniversaryDate              | anniversaryDate         |
      | channel                      | SYSTEM_INTEGRATION      |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And I run create Eligibility and Enrollment API
    Then I will verify business events are generated with data
      # 1.0 Business Event Required Fields
      | eventName                               | LOCKIN_LOCKOUT                  |
      | channel                                 | SYSTEM_INTEGRATION              |
      | createdBy                               | 597                             |
      | processDate                             | current                         |
      | externalCaseId                          | <name>.caseIdentificationNumber |
      | externalConsumerId                      | <name>.externalConsumerId       |
      | consumerId                              | <name>.consumerId               |
      | consumerName                            | <name>                          |
      # 2.0 Business Event Optional Enrollment fields from “lockinout”
      | lockInLockOut.[0].medicaidId            | <name>.externalConsumerId       |
      | lockInLockOut.[0].memberDateEffective   | <startDate1>                    |
      | lockInLockOut.[0].memberDateEnd         | <endDate1>                      |
      | lockInLockOut.[0].providerDateEffective | openEnrollmentDay               |
      | lockInLockOut.[0].providerDateEnd       | null                            |
      | lockInLockOut.[0].providerId            | 300029354                       |
      | lockInLockOut.[0].providerName          | ERIC SCHALLET                   |
      | lockInLockOut.[0].providerSpecialty     | null                            |
      | lockInLockOut.[0].providerTypeCode      | Medical                         |
      | lockInLockOut.[0].restrictionIndicator  | I                               |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayof2MonthsBefore   |
      | eligibilityStartDate         | 1stDayof2MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
      | genericFieldText1            | Eligible                |
      | fileSource                   | HRECIP                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | <name>.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | <endDate1>            |
      | genericFieldText1 | I                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO                  |
      | genericFieldText2 | <provider1>           |
      | genericFieldText5 | ERIC SCHALLET         |
      | genericFieldText3 | Medical               |
    And User provide other enrollment segments details
      | recordId          | 21                |
      | consumerId        | <name>.consumerId |
      | startDate         | <startDate2>      |
      | endDate           | <endDate2>        |
      | genericFieldText1 | I                 |
      | genericFieldDate1 | openEnrollmentDay |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO              |
      | genericFieldText2 | <provider2>       |
      | genericFieldText5 | BO  ASANTE      M |
      | genericFieldText3 | RCP-Physician     |
    And I run create Eligibility and Enrollment API
    Then I verify other enrollment segment for coma separated consumerIds "<name>.consumerId" with data
      | [0].consumerId        | <name>.consumerId              |
      | [0].segmentTypeCode   | LILO                           |
      | [0].startDate         | <startDate1>                   |
      | [0].endDate           | <endDate1>                     |
      | [0].genericFieldText1 | I                              |
      | [0].genericFieldText2 | <provider1>                    |
      | [0].genericFieldText3 | Medical                        |
      | [0].genericFieldText5 | ERIC SCHALLET                  |
      | [0].genericFieldDate1 | openEnrollmentDay              |
      | [0].genericFieldDate2 | null                           |
      | [0].createdOn         | current                        |
      | [0].createdBy         | 597                            |
      | [0].updatedOn         | current                        |
      | [0].updatedBy         | 597                            |
      | [0].timeframe         | Active                         |
      # * * *  * * *
      | [1].consumerId        | <name>.consumerId              |
      | [1].segmentTypeCode   | OPEN_ENROLLMENT                |
      | [1].startDate         | 1stDayof2MonthsBefore          |
      | [1].endDate           | 90DayFrom1stDayof2MonthsBefore |
      | [1].enrollmentId      | null                           |
      | [1].genericFieldText1 | O                              |
      | [1].genericFieldText2 | null                           |
      | [1].genericFieldText3 | null                           |
      | [1].genericFieldText5 | null                           |
      | [1].genericFieldDate1 | 1stDayofPresentMonth           |
      | [1].createdOn         | current                        |
      | [1].createdBy         | 597                            |
      | [1].updatedOn         | current                        |
      | [1].updatedBy         | 597                            |
      | [1].timeframe         | Active                         |
      # * * *  * * *
      | [2].consumerId        | <name>.consumerId              |
      | [2].segmentTypeCode   | LILO                           |
      | [2].startDate         | <startDate2>                   |
      | [2].endDate           | <endDate2>                     |
      | [2].enrollmentId      | null                           |
      | [2].genericFieldText1 | I                              |
      | [2].genericFieldText2 | <provider2>                    |
      | [2].genericFieldText3 | RCP-Physician                  |
      | [2].genericFieldText5 | BO  ASANTE      M              |
      | [2].genericFieldDate1 | openEnrollmentDay              |
      | [2].createdOn         | current                        |
      | [2].createdBy         | 597                            |
      | [2].updatedOn         | current                        |
      | [2].updatedBy         | 597                            |
      | [2].timeframe         | Active                         |
    Then I will verify business events are generated with data
      # 1.0 Business Event Required Fields
      | eventName                               | LOCKIN_LOCKOUT                  |
      | channel                                 | SYSTEM_INTEGRATION              |
      | createdBy                               | 597                             |
      | processDate                             | current                         |
      | externalCaseId                          | <name>.caseIdentificationNumber |
      | externalConsumerId                      | <name>.externalConsumerId       |
      | consumerId                              | <name>.consumerId               |
      | consumerName                            | <name>                          |
      | consumerPopulation                      | <population>                    |
  # 2.0 Business Event Optional Enrollment fields from “lockinout ”
      | lockInLockOut.[0].medicaidId            | <name>.externalConsumerId       |
      | lockInLockOut.[0].memberDateEffective   | <startDate1>                    |
      | lockInLockOut.[0].memberDateEnd         | <endDate1>                      |
      | lockInLockOut.[0].providerDateEffective | openEnrollmentDay               |
      | lockInLockOut.[0].providerDateEnd       | null                            |
      | lockInLockOut.[0].providerId            | 300029354                       |
      | lockInLockOut.[0].providerName          | ERIC SCHALLET                   |
      | lockInLockOut.[0].providerSpecialty     | null                            |
      | lockInLockOut.[0].providerTypeCode      | Medical                         |
      | lockInLockOut.[0].restrictionIndicator  | I                               |
      # * * * * * *
      | lockInLockOut.[1].medicaidId            | <name>.externalConsumerId       |
      | lockInLockOut.[1].memberDateEffective   | <startDate2>                    |
      | lockInLockOut.[1].memberDateEnd         | <endDate2>                      |
      | lockInLockOut.[1].providerDateEffective | openEnrollmentDay               |
      | lockInLockOut.[1].providerDateEnd       | null                            |
      | lockInLockOut.[1].providerId            | 30002                           |
      | lockInLockOut.[1].providerName          | BO  ASANTE      M               |
      | lockInLockOut.[1].providerSpecialty     | null                            |
      | lockInLockOut.[1].providerTypeCode      | RCP-Physician                   |
      | lockInLockOut.[1].restrictionIndicator  | I                               |
    Examples:
      | name    | programCode | subProgramCode     | eligibilityStatusCode | population    | startDate1            | startDate2            | endDate1              | endDate2 | provider1 | provider2 |
      | 24298-5 | H           | HealthyIndianaPlan | V                     | HIP-Voluntary | 1stDayof2MonthsBefore | 1stDayof2MonthsBefore | lastDayofPresentMonth | highDate | 300029354 | 30002     |
      | 24298-6 | H           | HealthyIndianaPlan | M                     | HIP-Mandatory | 1stDayof2MonthsBefore | 1stDayof2MonthsBefore | lastDayofPresentMonth | highDate | 300029354 | 30002     |


  @API-CP-24298-02  @API-CP-24297-02 @API-EE-IN @IN-EB-API-Regression @shruti
  Scenario Outline: Verify Lockin Lock out information is updated for same provider ID and event triggered (Update LILO)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayof2MonthsBefore   |
      | eligibilityStartDate         | 1stDayof2MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And User provide other enrollment segments details
      | recordId          | 21                             |
      | consumerId        | <name>.consumerId              |
      | startDate         | 1stDayof2MonthsBefore          |
      | endDate           | 90DayFrom1stDayof2MonthsBefore |
      | genericFieldText1 | O                              |
      | genericFieldDate1 | 1stDayofPresentMonth           |
      | segmentTypeCode   | OPEN_ENROLLMENT                |
    And User provide other enrollment segments details
      | recordId          | 21                |
      | consumerId        | <name>.consumerId |
      | startDate         | <startDate1>      |
      | endDate           | <endDate1>        |
      | genericFieldText1 | I                 |
      | genericFieldDate1 | openEnrollmentDay |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO              |
      | genericFieldText2 | <provider1>       |
      | genericFieldText5 | ERIC SCHALLET     |
      | genericFieldText3 | Medical           |
    And I run create Eligibility and Enrollment API
    Then I will verify business events are generated with data
      # 1.0 Business Event Required Fields
      | eventName                               | LOCKIN_LOCKOUT                  |
      | channel                                 | SYSTEM_INTEGRATION              |
      | createdBy                               | 597                             |
      | processDate                             | current                         |
      | externalCaseId                          | <name>.caseIdentificationNumber |
      | externalConsumerId                      | <name>.externalConsumerId       |
      | consumerId                              | <name>.consumerId               |
      | consumerName                            | <name>                          |
  # 2.0 Business Event Optional Enrollment fields from “lockinout ”
      | lockInLockOut.[0].medicaidId            | <name>.externalConsumerId       |
      | lockInLockOut.[0].memberDateEffective   | <startDate1>                    |
      | lockInLockOut.[0].memberDateEnd         | <endDate1>                      |
      | lockInLockOut.[0].providerDateEffective | openEnrollmentDay               |
      | lockInLockOut.[0].providerDateEnd       | null                            |
      | lockInLockOut.[0].providerId            | 300029354                       |
      | lockInLockOut.[0].providerName          | ERIC SCHALLET                   |
      | lockInLockOut.[0].providerSpecialty     | null                            |
      | lockInLockOut.[0].providerTypeCode      | Medical                         |
      | lockInLockOut.[0].restrictionIndicator  | I                               |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayof2MonthsBefore   |
      | eligibilityStartDate         | 1stDayof2MonthsBefore   |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And User provide other enrollment segments details
      | recordId          | 21                |
      | consumerId        | <name>.consumerId |
      | startDate         | <startDate2>      |
      | endDate           | <endDate2>        |
      | genericFieldText1 | I                 |
      | genericFieldDate1 | openEnrollmentDay |
      | genericFieldDate2 |[blank]|
      | segmentTypeCode   | LILO              |
      | genericFieldText2 | <provider1>       |
      | genericFieldText5 | BO  ASANTE      M |
      | genericFieldText3 | RCP-Physician     |
    And I run create Eligibility and Enrollment API
    Then I verify other enrollment segment for coma separated consumerIds "<name>.consumerId" with data
      | [0].consumerId        | <name>.consumerId              |
      | [0].segmentTypeCode   | LILO                           |
      | [0].startDate         | <startDate2>                   |
      | [0].endDate           | <endDate2>                     |
      | [0].genericFieldText1 | I                              |
      | [0].genericFieldText2 | <provider1>                    |
      | [0].genericFieldText3 | RCP-Physician                  |
      | [0].genericFieldText5 | BO  ASANTE      M              |
      | [0].genericFieldDate1 | openEnrollmentDay              |
      | [0].genericFieldDate2 | null                           |
      | [0].createdOn         | current                        |
      | [0].createdBy         | 597                            |
      | [0].updatedOn         | current                        |
      | [0].updatedBy         | 597                            |
      | [0].timeframe         | Active                         |
      # * * *  * * *
      | [1].consumerId        | <name>.consumerId              |
      | [1].segmentTypeCode   | OPEN_ENROLLMENT                |
      | [1].startDate         | 1stDayof2MonthsBefore          |
      | [1].endDate           | 90DayFrom1stDayof2MonthsBefore |
      | [1].enrollmentId      | null                           |
      | [1].genericFieldText1 | O                              |
      | [1].genericFieldText2 | null                           |
      | [1].genericFieldText3 | null                           |
      | [1].genericFieldText5 | null                           |
      | [1].genericFieldDate1 | 1stDayofPresentMonth           |
      | [1].createdOn         | current                        |
      | [1].createdBy         | 597                            |
      | [1].updatedOn         | current                        |
      | [1].updatedBy         | 597                            |
      | [1].timeframe         | Active                         |
    Then I will verify business events are generated with data
      # 1.0 Business Event Required Fields
      | eventName                               | LOCKIN_LOCKOUT                  |
      | channel                                 | SYSTEM_INTEGRATION              |
      | createdBy                               | 597                             |
      | processDate                             | current                         |
      | externalCaseId                          | <name>.caseIdentificationNumber |
      | externalConsumerId                      | <name>.externalConsumerId       |
      | consumerId                              | <name>.consumerId               |
      | consumerName                            | <name>                          |
      | consumerPopulation                      | <population>                    |
  # 2.0 Business Event Optional Enrollment fields from “lockinout ”
      | lockInLockOut.[0].medicaidId            | <name>.externalConsumerId       |
      | lockInLockOut.[0].memberDateEffective   | <startDate2>                    |
      | lockInLockOut.[0].memberDateEnd         | <endDate2>                      |
      | lockInLockOut.[0].providerDateEffective | openEnrollmentDay               |
      | lockInLockOut.[0].providerDateEnd       | null                            |
      | lockInLockOut.[0].providerId            | 300029354                       |
      | lockInLockOut.[0].providerName          | BO  ASANTE      M               |
      | lockInLockOut.[0].providerSpecialty     | null                            |
      | lockInLockOut.[0].providerTypeCode      | RCP-Physician                   |
      | lockInLockOut.[0].restrictionIndicator  | I                               |
    Examples:
      | name    | programCode | subProgramCode    | eligibilityStatusCode | population    | startDate1            | startDate2            | endDate1              | endDate2 | provider1 |
      | 24298-1 | R           | HoosierHealthwise | M                     | HHW-Mandatory | 1stDayof2MonthsBefore | 1stDayof2MonthsBefore | lastDayofPresentMonth | highDate | 300029354 |

  