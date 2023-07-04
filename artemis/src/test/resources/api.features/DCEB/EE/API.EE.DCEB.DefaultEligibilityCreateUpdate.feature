Feature: Default Eligibility Decide Create and Update


  @API-CP-40299 @API-CP-40301 @API-CP-40301-01 @API-CP-40301-03 @API-CP-42309 @shruti  @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Default Eligibility Decide Create Update No Match - Exact Match - Eligibility Update
  CP-3.0 Enroll Action - DCHF , 4.0 Enroll Action - Alliance and Immigrant Children
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    # CP-40301 3.0 Create the Consumer’s eligibility segment - No Match - Past Eligibility cannot be created
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | lastDayofLastMonth   |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityEndReason         | test                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | empty |[blank]|
    Then I Verify Consumer Actions as following data
      | empty |[blank]|
    Given I initiated Eligibility and Enrollment Create API
    # CP-40301 3.0 Create the Consumer’s eligibility segment - No Match
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityEndReason         | test2                |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-40299 1.0 Decide Scenario is MMIS Sends Default Eligibility
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | true                 |
      | [0].changeAllowedFrom    | <changeAllowedFrom>  |
      | [0].changeAllowedUntil   | <changeAllowedUntil> |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | 1stDayofPresentMonth |
      | eligibilityEndDate   | highDate-DC          |
      | programCode          | MEDICAID             |
      | subProgramTypeCode   | <subProgramTypeCode> |
      | coverageCode         | <coverageCode>       |
      | eligibilityEndReason | test2                |
      | createdOn            | current              |
      | createdBy            | 597                  |
    When Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                         |
      | consumerId    | <name>.consumerId                                              |
      | correlationId | <name><event1>.eligibilities.[0].coreEligibility.correlationId |
      | status        | SUCCESS                                                        |
      | consumerFound | true                                                           |
      | DPBI          |[blank]|
    Given I initiated Eligibility and Enrollment Create API
    # CP-40301 4.0 Do Not Create or Update Eligibility Data - Exact Match
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityEndReason         | exact match          |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-40299 1.0 Decide Scenario is MMIS Sends Default Eligibility
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | true                 |
      | [0].changeAllowedFrom    | <changeAllowedFrom>  |
      | [0].changeAllowedUntil   | <changeAllowedUntil> |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | 1stDayofPresentMonth |
      | eligibilityEndDate   | highDate-DC          |
      | programCode          | MEDICAID             |
      | subProgramTypeCode   | <subProgramTypeCode> |
      | coverageCode         | <coverageCode>       |
      | eligibilityEndReason | exact match          |
      | createdOn            | current              |
      | createdBy            | 597                  |
    Given I initiated Eligibility and Enrollment Create API
    # CP-40301 1.0 Create the Consumer’s eligibility segment - Start Date Change to en earlier date
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>                       |
      | consumerId                   | <name>.consumerId                    |
      | isEligibilityRequired        | yes                                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>                       |
      | isEnrollemntRequired         | no                                   |
      | recordId                     | 25                                   |
      | isEnrollmentProviderRequired | no                                   |
      | enrollmentStartDate          | past                                 |
      | eligibilityStartDate         | 1stDayofLastMonth                    |
      | eligibilityEndDate           | highDate-DC                          |
      | programCode                  | MEDICAID                             |
      | subProgramTypeCode           | <subProgramTypeCode>                 |
      | eligibilityEndReason         | start date change to an earlier date |
      | requestedBy                  | 1                                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-40299 1.0 Decide Scenario is MMIS Sends Default Eligibility
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | true                 |
      | [0].changeAllowedFrom    | <changeAllowedFrom>  |
      | [0].changeAllowedUntil   | <changeAllowedUntil> |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | 1stDayofLastMonth                    |
      | eligibilityEndDate   | highDate-DC                          |
      | programCode          | MEDICAID                             |
      | subProgramTypeCode   | <subProgramTypeCode>                 |
      | coverageCode         | <coverageCode>                       |
      | eligibilityEndReason | start date change to an earlier date |
      | updatedOn            | current                              |
      | updatedBy            | 1                                    |
    When Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                       |
      | consumerId    | <name>.consumerId                                              |
      | correlationId | <name><event1>.eligibilities.[0].coreEligibility.correlationId |
      | status        | SUCCESS                                                        |
      | consumerFound | true                                                           |
      | DPBI          |[blank]|
    Given I initiated Eligibility and Enrollment Create API
    # CP-40301 1.0 Create the Consumer’s eligibility segment - End Date Change to en earlier date
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>                     |
      | consumerId                   | <name>.consumerId                  |
      | isEligibilityRequired        | yes                                |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>                     |
      | isEnrollemntRequired         | no                                 |
      | recordId                     | 25                                 |
      | isEnrollmentProviderRequired | no                                 |
      | enrollmentStartDate          | past                               |
      | eligibilityStartDate         | 1stDayofLastMonth                  |
      | eligibilityEndDate           | highDate                           |
      | programCode                  | MEDICAID                           |
      | subProgramTypeCode           | <subProgramTypeCode>               |
      | eligibilityEndReason         | end date change to an earlier date |
      | requestedBy                  | 1                                  |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-40299 1.0 Decide Scenario is MMIS Sends Default Eligibility
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | true                 |
      | [0].changeAllowedFrom    | <changeAllowedFrom>  |
      | [0].changeAllowedUntil   | <changeAllowedUntil> |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | 1stDayofLastMonth                  |
      | eligibilityEndDate   | highDate                           |
      | programCode          | MEDICAID                           |
      | subProgramTypeCode   | <subProgramTypeCode>               |
      | coverageCode         | <coverageCode>                     |
      | eligibilityEndReason | end date change to an earlier date |
      | updatedOn            | current                            |
      | updatedBy            | 1                                  |
    When Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                       |
      | consumerId    | <name>.consumerId                                              |
      | correlationId | <name><event1>.eligibilities.[0].coreEligibility.correlationId |
      | status        | SUCCESS                                                        |
      | consumerFound | true                                                           |
      | DPBI          |[blank]|
    Given I initiated Eligibility and Enrollment Create API
    # CP-40299 3.0 Add Fee-for-service or Deceased Reason Code when Enrollment Ending (no Plan Code)
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityEndReason         | 1F                   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-40299 1.0 Decide Scenario is MMIS Sends Default Eligibility
    Then I verify latest benefit status information with data
      | programPopulation   | <population2>       |
      | population          | <population2>       |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | 1stDayofLastMonth    |
      | eligibilityEndDate   | highDate             |
      | programCode          | MEDICAID             |
      | subProgramTypeCode   | <subProgramTypeCode> |
      | coverageCode         | <coverageCode>       |
      | eligibilityEndReason | 1F                   |
      | createdOn            | current              |
      | createdBy            | 597                  |
    When Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                       |
      | consumerId    | <name>.consumerId                                              |
      | correlationId | <name><event1>.eligibilities.[0].coreEligibility.correlationId |
      | status        | SUCCESS                                                        |
      | consumerFound | true                                                           |
      | DPBI          |[blank]|
    Given I initiated Eligibility and Enrollment Create API
    # CP-40299 3.0 Add Fee-for-service or Deceased Reason Code when Enrollment Ending (no Plan Code)
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityEndReason         | 8X                   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    # CP-40299 1.0 Decide Scenario is MMIS Sends Default Eligibility
    Then I verify latest benefit status information with data
      | programPopulation   | <population3>       |
      | population          | <population3>       |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | 1stDayofLastMonth    |
      | eligibilityEndDate   | highDate             |
      | programCode          | MEDICAID             |
      | subProgramTypeCode   | <subProgramTypeCode> |
      | coverageCode         | <coverageCode>       |
      | eligibilityEndReason | 8X                   |
      | createdOn            | current              |
      | createdBy            | 597                  |
    When Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                       |
      | consumerId    | <name>.consumerId                                              |
      | correlationId | <name><event1>.eligibilities.[0].coreEligibility.correlationId |
      | status        | SUCCESS                                                        |
      | consumerFound | true                                                           |
      | DPBI          |[blank]|

    Examples:
      | name     | coverageCode | subProgramTypeCode | population         | changeAllowedFrom | changeAllowedUntil | event1       | population2                        | population3                 |
      | 40299-01 | 130          | DCHF               | DCHF-Adult         | current           | 30DaysFromToday    | eligibility1 | DCHF-Fee for Service               | DCHF-Deceased               |
      | 40299-02 | 420          | ImmigrantChildren  | Immigrant Children | yesterday         | yesterday          | eligibility1 | Immigrant Children-Fee for Service | Immigrant Children-Deceased |
      | 40299-03 | 460          | Alliance           | Alliance-Child     | yesterday         | yesterday          | eligibility1 | Alliance-Fee for Service           | Alliance-Deceased           |


  @API-CP-40299 @API-CP-40301 @API-CP-40301-02 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Default Eligibility Decide Create Update - Program Change
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    # CP-40301 3.0 Create the Consumer’s eligibility segment - No Match
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event1>       |
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | eligibilityEndReason         | test2                |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required             |
      | [0].consumerAction       | Enroll               |
      | [0].planSelectionAllowed | true                 |
      | [0].changeAllowedFrom    | <changeAllowedFrom>  |
      | [0].changeAllowedUntil   | <changeAllowedUntil> |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | 1stDayofPresentMonth |
      | eligibilityEndDate   | highDate-DC          |
      | programCode          | MEDICAID             |
      | subProgramTypeCode   | <subProgramTypeCode> |
      | coverageCode         | <coverageCode>       |
      | eligibilityEndReason | test2                |
      | createdOn            | current              |
      | createdBy            | 597                  |
    When Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                         |
      | consumerId    | <name>.consumerId                                              |
      | correlationId | <name><event1>.eligibilities.[0].coreEligibility.correlationId |
      | status        | SUCCESS                                                        |
      | consumerFound | true                                                           |
      | DPBI          |[blank]|
    Given I initiated Eligibility and Enrollment Create API
        # CP-40301 3.0 Create the Consumer’s eligibility segment - Program Change
    And I provide the enrollment and eligibility information to create enrollment
      | saveEligibilityEventName     | <name><event2>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode2>       |
      | isEnrollemntRequired         | no                    |
      | recordId                     | 25                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | past                  |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | eligibilityEndDate           | highDate-DC           |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode2> |
      | eligibilityEndReason         | program change        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population2>       |
      | population          | <population2>       |
      | benefitStatus       | Eligible            |
      | eligibilityScenario | Default Eligibility |
      | timeframe           | Active              |
    Then I Verify Consumer Actions as following data
      | [0].action               | Required              |
      | [0].consumerAction       | Enroll                |
      | [0].planSelectionAllowed | true                  |
      | [0].changeAllowedFrom    | <changeAllowedFrom2>  |
      | [0].changeAllowedUntil   | <changeAllowedUntil2> |
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | 1stDayofPresentMonth  |
      | eligibilityEndDate   | highDate-DC           |
      | programCode          | MEDICAID              |
      | subProgramTypeCode   | <subProgramTypeCode2> |
      | coverageCode         | <coverageCode2>       |
      | eligibilityEndReason | program change        |
      | createdOn            | current               |
      | createdBy            | 597                   |
    When Wait for 5 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_SAVE_EVENT                                         |
      | consumerId    | <name>.consumerId                                              |
      | correlationId | <name><event2>.eligibilities.[0].coreEligibility.correlationId |
      | status        | SUCCESS                                                        |
      | consumerFound | true                                                           |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                                       |
      | consumerId    | <name>.consumerId                                              |
      | correlationId | <name><event1>.eligibilities.[0].coreEligibility.correlationId |
      | status        | SUCCESS                                                        |
      | consumerFound | true                                                           |
      | DPBI          |[blank]|

    Examples:
      | name     | coverageCode | subProgramTypeCode | population         | changeAllowedFrom | changeAllowedUntil | event1       | event2       | subProgramTypeCode2 | coverageCode2 | population2        | changeAllowedFrom2 | changeAllowedUntil2 |
      | 40299-04 | 130          | DCHF               | DCHF-Adult         | current           | 30DaysFromToday    | eligibility1 | eligibility2 | ImmigrantChildren   | 420           | Immigrant Children | yesterday          | yesterday           |
      | 40299-05 | 420          | ImmigrantChildren  | Immigrant Children | yesterday         | yesterday          | eligibility1 | eligibility2 | Alliance            | 460           | Alliance-Child     | yesterday          | yesterday           |
      | 40299-06 | 460          | Alliance           | Alliance-Child     | yesterday         | yesterday          | eligibility1 | eligibility2 | DCHF                | 130           | DCHF-Adult         | current            | 30DaysFromToday     |

  @API-CP-43458 @CP-43209 @shruti @burak @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify Not Eligible and Deceassed/FFS
  CP-43209- 6.0 All Actions Unavailable- Deceasead / FFS
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>       |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate-DC          |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
#      | eligibilityEndReason         | <eligibilityEndReason> |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions as following data
      | [0].action               | Required |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityEndReason         | <eligibilityEndReason> |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | eligibilityScenario | Default Eligibility |
      | benefitStatus       | <benefitStatus>     |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    Examples:
      | name     | eligibilityEndDate | coverageCode | population                  | subProgramTypeCode | benefitStatus | eligibilityEndReason | eligibilityStartDate |
      | 4-17     | nextDay            | 460          | Alliance-Fee for Service    | Alliance           | NotEligible   | 2J                   | nextDay              |
      | 43458-09 | nextDay            | 420F         | Immigrant Children-Deceased | ImmigrantChildren  | NotEligible   | DD                   | nextDayPlusOne       |
      | 43458-22 | nextDay            | 130          | DCHF-Fee for Service        | DCHF               | NotEligible   | LT                   | nextDay              |