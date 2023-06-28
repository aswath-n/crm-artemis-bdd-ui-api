Feature: MMIS Sends Prospective Eligibility Scenarios

  @API-CP-15737 @API-CP-15737-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @kursat
  Scenario: Decide Scenario MMIS Sends Prospective Eligibility Date Change - Record Id 9 Past Date (negative)(No Enrollment)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 15737-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15737-3.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | anniversaryDate              | anniversaryDate    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15737-3.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 9                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | eligibilityEndDate           | null               |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | anniversaryDate              | anniversaryDate    |
    Then I send API call with name "15737-3a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "15737-3.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"

  @API-CP-15737 @API-CP-15737-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @kursat
  Scenario: Decide Scenario MMIS Sends Prospective Eligibility Date Change - Record Id 9 Wrong Program Type (negative)(No Enrollment)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 15737-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15737-4.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | programTypeCode              | MEDICAID           |
      | anniversaryDate              | anniversaryDate    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15737-4.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 9                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth  |
      | eligibilityEndDate           | null               |
      | txnStatus                    | Accepted           |
      | programCode                  | P                  |
      | subProgramTypeCode           | PEACHCAREGF        |
      | programTypeCode              | CHIP               |
      | anniversaryDate              | anniversaryDate    |
    Then I send API call with name "15737-4a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "15737-4.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"

  @API-CP-15737 @API-CP-15737-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @kursat
  Scenario: Decide Scenario MMIS Sends Prospective Eligibility Date Change - Record Id 9 Future Date (positive) (Selection Made)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 15737-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15737-6.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | anniversaryDate              | anniversaryDate    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "EI" for update Enrollment information
      | [0].consumerId         | 15737-6.consumerId |
      | [0].planId             | 145                |
      | [0].planCode           | 84                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].programTypeCode    | H                  |
      | [0].subProgramTypeCode | MEDICAIDMCHB       |
      | [0].anniversaryDate    | anniversaryDate::  |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15737-6.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 9                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth  |
      | eligibilityEndDate           | null               |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | anniversaryDate              | anniversaryDate    |
    Then I send API call with name "15737-6a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "15737-6.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Prospective Eligibility Date Change","null"

  @API-CP-15737 @API-CP-15737-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @kursat
  Scenario: Decide Scenario MMIS Sends Prospective Eligibility Date Change - Record Id 9 Future Date (positive) (Submitted to State)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 15737-7 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15737-7.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | anniversaryDate              | anniversaryDate    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "EI" for update Enrollment information
      | [0].consumerId         | 15737-7.consumerId |
      | [0].planId             | 145                |
      | [0].planCode           | 84                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].programTypeCode    | H                  |
      | [0].subProgramTypeCode | MEDICAIDMCHB       |
      | [0].anniversaryDate    | anniversaryDate::  |
    And I send API call with name "PO" for process Outbound Update
      | [0].planId       | delete::               |
      | [0].planCode     | 84                     |
      | [0].consumerId   | 15737-7.consumerId     |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].startDate    | fdnxtmth::             |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15737-7.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 9                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth  |
      | eligibilityEndDate           | null               |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | anniversaryDate              | anniversaryDate    |
    Then I send API call with name "15737-7a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "15737-7.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Prospective Eligibility Date Change","null"

  @API-CP-15735 @API-CP-15736 @API-CP-15736-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: CP-15736 Create or Update Data: MMIS Sends Prospective Eligibility Date Change (No Enrollment)
  CP-15735 Create Business Event Prospective Eligibility Date Change
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "<populationType>" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 9                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth    |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Prospective Eligibility Date Change","null"
    # 5.0 Decide Consumer Program Population
    Then I verify benefit status information with data
      | programPopulation | <programPopulation> |
      | population        | <populationType>    |
      # 6.0 Decide Benefit Status
      | benefitStatus     | Eligible            |
    # 7.0 Decide Consumer Actions and Action Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Required |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    And Wait for 10 seconds
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    # 3.0 Record Updated On and Updated By for Eligibility Segment - Prospective Eligibility Date Change
    Then I verify following fields are captured in the newly created Eligibility Record
      | Updated On | current |
      | updatedBy  | 597     |
    # 4.0 Publish an ELIGIBILITY_UPDATE_EVENT event for DP4BI to consume
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                               |
      | consumerId    | <name>.consumerId                                      |
      | correlationId | <name>a.eligibilities[0].coreEligibility.correlationId |
      | consumerFound | true                                                   |
      | DPBI          |[blank]|
    # CP-15735 1.0 Business Event Required Fields
    Then I will verify business events are generated with data
      | eventName                 | PROSPECTIVE_ELIGIBILITY_DATE_CHANGE |
      | externalConsumerId        | <name>.externalConsumerId           |
#      | correlationId             | <name>a.traceid                     |
      | correlationId             | null                                |
      | channel                   | <channel>                           |
      | createdBy                 | 597                                 |
      | processDate               | current                             |
      | externalCaseId            | <name>.caseIdentificationNumber     |
      | consumerId                | <name>.consumerId                   |
      | consumerName              | <name>                              |
      #  CP-15735 2.0 Business Event Optional Fields
      | eligibilityStartDate      | 1stDayofNextMonth                   |
      | eligibilityEndDate        | null                                |
      | eligibilityEndReason      | null                                |
      | eligibilityCategoryCode   | null                                |
      | eligibilityProgramCode    | <programCode>                       |
      | eligibilitySubProgramCode | <subProgramTypeCode>                |
      | eligibilityCoverageCode   | string                              |
      | eligibilityExemptionCode  | qwer                                |
      | consumerPopulation        | <populationType>                    |
      | benefitStatus             | Eligible                            |
    Examples: examples with different populations
      | name    | populationType   | programCode | subProgramTypeCode | programPopulation           | channel            |
      | 15736-1 | PREGNANT-WOMEN   | H           | MEDICAIDMCHB       | Medicaid-Pregnant Women     | SYSTEM_INTEGRATION |
      | 15736-2 | MEDICAID-GENERAL | M           | MEDICAIDGF         | Medicaid-General Population | Auto Assignment    |

  @API-CP-15736 @API-CP-15736-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Create or Update Data: MMIS Sends Prospective Eligibility Date Change (No Enrollment) (negative)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 15736-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15736-3.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | programTypeCode              | MEDICAID           |
      | anniversaryDate              | anniversaryDate    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15736-3.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 9                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth  |
      | eligibilityEndDate           | null               |
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | programTypeCode              | CHIP               |
      | anniversaryDate              | anniversaryDate    |
    Then I send API call with name "15736-3a" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "15736-3.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","null"
    # 5.0 Decide Consumer Program Population
    Then I verify benefit status information with data
      | programPopulation | Medicaid-Pregnant Women |
      | population        | PREGNANT-WOMEN          |
      # 6.0 Decide Benefit Status
      | benefitStatus     | Eligible                |
    # 7.0 Decide Consumer Actions and Action Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Required |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    And Wait for 5 seconds
    When I initiated get eligibility by consumer id "15736-3.consumerId"
    And I run get eligibility api
    # 3.0 Record Updated On and Updated By for Eligibility Segment - Prospective Eligibility Date Change
    Then I verify following fields are captured in the newly created Eligibility Record
      | Updated On | current |
      | updatedBy  | 597     |
    # 4.0 Publish an ELIGIBILITY_UPDATE_EVENT event for DP4BI to consume
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | 15736-3.consumerId       |
      | consumerFound | false                    |

  @API-CP-15735 @API-CP-15736 @API-CP-15736-01 #@API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir @hasissues
  Scenario Outline: CP-15736 Create or Update Data: MMIS Sends Prospective Eligibility Date Change (Has Enrollment)
  CP-15735 Create Business Event Prospective Eligibility Date Change
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "<populationType>" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "EI" for update Enrollment information
      | [0].consumerId        | <name>.consumerId |
      | [0].planId            | 145               |
      | [0].planCode          | 84                |
      | [0].startDate         | fdnxtmth::        |
      | [0].programTypeCode   | M                 |
      | [0].serviceRegionCode | East              |
    And Wait for 5 seconds
    And I send API call with name "PO" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | 84                     |
      | [0].consumerId         | <name>.consumerId      |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].subProgramTypeCode | <subProgramTypeCode>   |
      | [0].status             | SUBMITTED_TO_STATE     |
      | [0].txnStatus          | SUBMITTED_TO_STATE     |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | planCode                     | 84                   |
      | planId                       | 145                  |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 9                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth    |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Prospective Eligibility Date Change","New Enrollment Accept"
    # 5.0 Decide Consumer Program Population
    Then I verify benefit status information with data
      | programPopulation | <programPopulation> |
      | population        | <populationType>    |
      # 6.0 Decide Benefit Status
      | benefitStatus     | Enrolled            |
    # 7.0 Decide Consumer Actions and Action Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available              |
      | [0].consumerAction       | <consumerAction>       |
      | [0].planSelectionAllowed | true                   |
      | [1].action               | Available              |
      | [1].consumerAction       | Plan Change Pre-lockin |
      | [1].planSelectionAllowed | true                   |
    And Wait for 5 seconds
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    # 3.0 Record Updated On and Updated By for Eligibility Segment - Prospective Eligibility Date Change
    Then I verify following fields are captured in the newly created Eligibility Record
      | Updated On | current |
      | updatedBy  | 597     |
    # 4.0 Publish an ELIGIBILITY_UPDATE_EVENT event for DP4BI to consume
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                               |
      | consumerId    | <name>.consumerId                                      |
      | correlationId | <name>a.eligibilities[0].coreEligibility.correlationId |
      | consumerFound | true                                                   |
      | DPBI          |[blank]|
    # CP-15735 1.0 Business Event Required Fields
    Then I will verify business events are generated with data
      | eventName                 | PROSPECTIVE_ELIGIBILITY_DATE_CHANGE |
      | externalConsumerId        | <name>.externalConsumerId           |
      | correlationId             | <name>a.traceid                     |
      | channel                   | SYSTEM_INTEGRATION                  |
      | createdBy                 | 597                                 |
      | processDate               | current                             |
      | externalCaseId            | <name>.caseIdentificationNumber     |
      | consumerId                | <name>.consumerId                   |
      | consumerName              | <name>                              |
      #  CP-15735 2.0 Business Event Optional Fields
      | eligibilityStartDate      | 1stDayofNextMonth                   |
      | eligibilityEndDate        | null                                |
      | eligibilityEndReason      | null                                |
      | eligibilityCategoryCode   | null                                |
      | eligibilityProgramCode    | <programCode>                       |
      | eligibilitySubProgramCode | <subProgramTypeCode>                |
      | eligibilityCoverageCode   | string                              |
      | eligibilityExemptionCode  | qwer                                |
      | consumerPopulation        | <populationType>                    |
      | benefitStatus             | Enrolled                            |
    Examples: examples with different populations
      | name    | populationType   | programCode | subProgramTypeCode | programPopulation           | consumerAction              |
      | 15736-4 | PREGNANT-WOMEN   | H           | MEDICAIDMCHB       | Medicaid-Pregnant Women     | Plan Change Open Enrollment |
      | 15736-5 | MEDICAID-GENERAL | M           | MEDICAIDGF         | Medicaid-General Population | Plan Change Anniversary     |

  @API-CP-15736 @API-CP-15736-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Create or Update Data: MMIS Sends Prospective Eligibility Date Change (Has Enrollment SUBMITTED_TO_STATE)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "<populationType>" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "EI" for update Enrollment information
      | [0].consumerId        | <name>.consumerId |
      | [0].planId            | 145               |
      | [0].planCode          | 84                |
      | [0].startDate         | fdnxtmth::        |
      | [0].programTypeCode   | M                 |
      | [0].serviceRegionCode | East              |
    And Wait for 5 seconds
    And I send API call with name "PO" for process Outbound Update
      | [0].planId             | delete::               |
      | [0].planCode           | 84                     |
      | [0].consumerId         | <name>.consumerId      |
      | [0].enrollmentId       | c.data[0].enrollmentId |
      | [0].startDate          | fdnxtmth::             |
      | [0].subProgramTypeCode | <subProgramTypeCode>   |
      | [0].status             | SUBMITTED_TO_STATE     |
      | [0].txnStatus          | SUBMITTED_TO_STATE     |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 9                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth    |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "<name>a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Prospective Eligibility Date Change","null"
    # 5.0 Decide Consumer Program Population
    Then I verify benefit status information with data
      | programPopulation | <programPopulation> |
      | population        | <populationType>    |
      # 6.0 Decide Benefit Status
      | benefitStatus     | Eligible            |
    # 7.0 Decide Consumer Actions and Action Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    And Wait for 5 seconds
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    # 3.0 Record Updated On and Updated By for Eligibility Segment - Prospective Eligibility Date Change
    Then I verify following fields are captured in the newly created Eligibility Record
      | Updated On | current |
      | updatedBy  | 597     |
    # 4.0 Publish an ELIGIBILITY_UPDATE_EVENT event for DP4BI to consume
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT                               |
      | consumerId    | <name>.consumerId                                      |
      | correlationId | <name>a.eligibilities[0].coreEligibility.correlationId |
      | consumerFound | true                                                   |
      | DPBI          |[blank]|
    Examples: examples with different populations
      | name    | populationType   | programCode | subProgramTypeCode | programPopulation           |
      | 15736-6 | PREGNANT-WOMEN   | H           | MEDICAIDMCHB       | Medicaid-Pregnant Women     |
      | 15736-7 | MEDICAID-GENERAL | M           | MEDICAIDGF         | Medicaid-General Population |