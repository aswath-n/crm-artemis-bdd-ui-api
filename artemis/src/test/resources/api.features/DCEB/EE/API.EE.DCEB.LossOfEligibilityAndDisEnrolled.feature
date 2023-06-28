Feature: Loss of Eligibility and Disenrolled


  @API-CP-39396 @API-CP-39396-01 @API-CP-39397 @API-CP-39397-01 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Decide Scenario- Loss of Eligibility
    #A.C 1.0- 1.0 Decide Scenario is MMIS Sends Default Eligibility
    #CP-39397-A.C 1.0 Update the consumer’s eligibility segment - Change of Eligibility Start/End Date
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | 130                  |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | <eligibilityEndDate> |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | DCHF-Adult          |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode2>      |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | <eligibilityEndDate> |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | <benefitStatus>     |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |

    Examples:
      | name       | eligibilityEndDate | coverageCode2 | population          | benefitStatus | consumerAction | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil | action   |
      | 39396-01-2 | highDate-DC        | 740Y          | DCHF-Blind          | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required |
      | 39396-01-3 | highDate-DC        | 120           | DCHF-Child          | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required |
      | 39396-01-4 | highDate-DC        | 751Y          | DCHF-Disabled       | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required |
      | 39396-01-5 | highDate-DC        | 241           | DCHF-Pregnant Women | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required |

  @API-CP-39396 @API-CP-39396-02 @API-CP-39392 @API-CP-39397-01 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Decide Scenario- Loss of Eligibility- Start Date /End Date change
    #A.C 1.0- 1.0 Decide Scenario is MMIS Sends Default Eligibility
    #CP-39397-A.C 1.0 Update the consumer’s eligibility segment - Change of Eligibility Start/End Date
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | 130                  |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | DCHF-Adult          |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required               |
      | [0].consumerAction       | Enroll                 |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |

    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode2>        |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | DCHF                   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | <benefitStatus>     |
      | eligibilityScenario | Default Eligibility |
#      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | coverageCode         | <coverageCode2>        |
      | eligibilityStartDate | <eligibilityStartDate> |
      | eligibilityEndDate   | <eligibilityEndDate>   |
      | updatedOn            | current                |
      | updatedBy            | 597                    |
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|


    Examples:
      | name       | eligibilityEndDate | coverageCode2 | population          | benefitStatus        | consumerAction | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil | action   | eligibilityStartDate |
      | 39396-01-2 | highDate-DC        | 740Y          | DCHF-Blind          | Eligible             | Enroll         | true                 | current           | 30DaysFromToday    | Required | 1stDayofPresentMonth |
#      | 39396-01-3 | highDate-DC        | 120           | DCHF-Child          | Eligible             | Enroll         | true                 | current           | 30DaysFromToday    | Required | 1stDayofLastMonth    |
#      | 39396-01-4 | highDate-DC        | 751Y          | DCHF-Disabled       | Eligible             | Enroll         | true                 | current           | 30DaysFromToday    | Required | 1stDayofNextMonth    |

  @API-CP-39396 @API-CP-39396-03 @API-CP-39392 @API-CP-39397-03 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Decide Scenario- Loss of Eligibility- Program change
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
      | newCaseCreation  | yes    |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | <eligibilityStartDate> |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | eligibilityEndDate           | highDate-DC            |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | DCHF                   |
      | coverageCode                 | 130                    |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | DCHF-Adult          |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | eligibilityRecordId          | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | coverageCode                 | <coverageCode2>      |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | <benefitStatus>     |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | subProgramTypeCode   | <subProgramTypeCode> |
      | eligibilityStartDate | 1stDayofPresentMonth |
      | eligibilityEndDate   | <eligibilityEndDate> |
    Then I verify following fields are captured in previously created Eligibility Record
      | subProgramTypeCode   | DCHF                 |
      | eligibilityStartDate | 1stDayofPresentMonth |
      | eligibilityEndDate   | lastDayofLastMonth   |
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                  |
      | consumerId    | <name>.consumerId                                       |
      | correlationId | <name>a.eligibilities.[0].coreEligibility.correlationId |
      | consumerFound | true                                                    |
      | DPBI          |[blank]|

    Examples:
      | name       | eligibilityEndDate | coverageCode2 | population         | benefitStatus | consumerAction | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil | action   | eligibilityStartDate | subProgramTypeCode |
      | 39396-03-1 | highDate-DC        | 460           | Alliance-Child     | Eligible      | Enroll         | true                 | yesterday         | yesterday          | Required | 1stDayofPresentMonth | Alliance           |
      | 39396-03-2 | highDate-DC        | 420           | Immigrant Children | Eligible      | Enroll         | true                 | yesterday         | yesterday          | Required | 1stDayofLastMonth    | ImmigrantChildren  |

  @API-CP-39396 @API-CP-39396-04 @API-CP-39397 @API-CP-39397-01 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Decide Scenario- Loss of Eligibility- No match
    #A.C 1.0- 1.0 Decide Scenario is MMIS Sends Default Eligibility
    #CP-39397-A.C 1.0 Update the consumer’s eligibility segment - Change of Eligibility Start/End Date
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | 130                  |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | <eligibilityEndDate> |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | DCHF-Adult          |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode2>      |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | <eligibilityEndDate> |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | population          | <population>        |
      | benefitStatus       | <benefitStatus>     |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | <action>               |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | <planSelectionAllowed> |
      | [0].changeAllowedFrom    | <changeAllowedFrom>    |
      | [0].changeAllowedUntil   | <changeAllowedUntil>   |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | coverageCode         | <coverageCode2>        |
      | eligibilityStartDate | <eligibilityStartDate> |
      | eligibilityEndDate   | <eligibilityEndDate>   |
      | updatedOn            | current                |
      | updatedBy            | 597                    |

    Examples:
      | name       | eligibilityEndDate | coverageCode2 | population          | benefitStatus | consumerAction | planSelectionAllowed | changeAllowedFrom | changeAllowedUntil | action   | eligibilityStartDate |
      | 39396-04-2 | highDate-DC        | 740Y          | DCHF-Blind          | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required | 1stDayofPresentMonth |
      | 39396-04-3 | highDate-DC        | 120           | DCHF-Child          | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required | 1stDayofPresentMonth |
      | 39396-04-4 | highDate-DC        | 751Y          | DCHF-Disabled       | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required | 1stDayofPresentMonth |
      | 39396-04-5 | highDate-DC        | 241           | DCHF-Pregnant Women | Eligible      | Enroll         | true                 | current           | 30DaysFromToday    | Required | 1stDayofPresentMonth |














