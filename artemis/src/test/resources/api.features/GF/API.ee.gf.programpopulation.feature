@hasissues
Feature: API: Identify Population type for Georgia-Families

  @API-CP-11053 @API-CP-11053-01 @API-CP-11053-02 @API-CP-11053-03 @API-CP-11053-04 @API-CP-11053-05 @API-CP-11053-06 # @API-EE-GF @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: GF Decide Program-Population for GF, PCK, P4HB, GF360
    Given I will get the Authentication token for "GACRM" in "CRM"
    Given I created a consumer with population type "CUSTOM" with data
      | age           | <age>           |
      | serviceRegion | <serviceRegion> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | planCode                     | 84                   |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramCode>     |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "<population>"
    Then I verify benefit status records are displayed with programpopulation as "<programpopulation>"
    And I verify benefit status records are displayed with benefit status "<benefitStatus>"
    Examples: Decide Program-Population for GF, PCK, P4HB, GF360
      | name | age | serviceRegion | programCode | subProgramCode | population    | programpopulation      | benefitStatus |
      #  @API-CP-11053-02 2.0 Decide Program-Population of GF-General
      | RN1  | 30  | Atlanta       | M           | GF             | GF-GENERAL    | Medicaid-GF general    | Enrolled      |
      #  @API-CP-11053-03 3.0 Decide Program-Population of PCK-General
      | RN2  | 12  | Atlanta       | P           | PCK            | PCK-GENERAL   | Medicaid-PCK general   | Enrolled      |
      #  @API-CP-11053-04 4.0 Decide Program-Population of P4HB-General
      | RN3  | 60  | Atlanta       | H           | P4HB           | P4HB-GENERAL  | Medicaid-P4HB general  | Enrolled      |
      #  @API-CP-11053-05 5.0 Decide Program-Population of GF360-General
      | RN4  | 50  | Atlanta       | F           | GF360          | GF360-GENERAL | Medicaid-GF360 general | Enrolled      |
      #  @API-CP-11053-06 6.0 Decide Program-Population not Found
      | RN5  | 110 | Atlanta       | P           | PCK            | NONE          | NONE                   | null          |
      | RN5  | 30  | East          | H           | P4HB           | NONE          | NONE                   | null          |

  @API-CP-11392 @API-CP-11392-01 @API-CP-11392-02 @API-CP-11392-03 #@API-EE-GF @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: GF Decide Benefit Status
    Given I will get the Authentication token for "GACRM" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 4                      |
      | isEnrollmentProviderRequired | no                     |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | eligibilityEndReason         | <eligibilityEndReason> |
      | enrollmentStartDate          | 1stDayofPresentMonth   |
      | enrollmentEndDate            | <enrollmentEndDate>    |
      | txnStatus                    | Accepted               |
      | planCode                     | 84                     |
      | programCode                  | M                      |
      | subProgramTypeCode           | GF                     |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with benefit status "<benefitStatus>"
    Examples: Decide Benefit Status value as "Enrolled", "Not Eligible", "Benefit Status Error"
      | name | eligibilityEndDate    | eligibilityEndReason | enrollmentEndDate     | benefitStatus        |
#      #  @API-CP-11392-01 1.0 Decide Benefit Status value as "Enrolled"
      | RN6  | null                  | null                 | null                  | Enrolled             |
#      #  @API-CP-11392-02 2.0 Decide Benefit Status value as "Not Eligible"
      | RN7  | lastDayofPresentMonth | Not Eligible         | lastDayofPresentMonth | Not Eligible         |
#      #  @API-CP-11392-03 3.0 Decide Benefit Status value as "Benefit Status Error" (final ELSE clause)
      | RN8  | lastDayofPresentMonth | null                 | lastDayofPresentMonth | Benefit Status Error |

  @API-CP-20171 @API-CP-20171-01 @API-CP-20171-02 @API-CP-20171-03 #@API-EE-GF @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: GF E&E Create or Update Data for New Enrollment
    Given I will get the Authentication token for "GACRM" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | planCode                     | 84                   |
      | programCode                  | M                    |
      | subProgramTypeCode           | GF                   |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RN9" for create Eligibility and Enrollment
    And Wait for 5 seconds
    # CP-20171-02 2.0 Create Enrollment Data - New Enrollment or New Retroactive Enrollment Scenario
    And I verify enrollment by consumer id "RN9.consumerId" with data
      | planCode             | 84                   |
      | medicalPlanBeginDate | 1stDayofPresentMonth |
      | medicalPlanEndDate   | null                 |
      | selectionReason      | null                 |
      | planEndDateReason    | null                 |
      | serviceRegionCode    | Atlanta              |
      | enrollmentType       | MEDICAL              |
      | channel              | SYSTEM_INTEGRATION   |
      | txnStatus            | ACCEPTED             |
      | anniversaryDate      | anniversaryDate      |
      | createdOn            | current              |
      | createdBy            | 597                  |
    And I initiated get benefit status by consumer id "RN9.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Retroactive Enrollment"
    # CP-20171-01 1.0 Create Eligibility Data - New Enrollment or New Retroactive Enrollment Scenario
    Then I verify benefit status information with data
      | programTypeCode    | MEDICAID             |
      | subProgramTypeCode | GF                   |
#      | Eligibility Category Code   | ?                    |
#      | Eligibility Coverage Code   | ?                    |
      | eligStatusCode     | Mandatory            |
      | eligStartDate      | 1stDayofPresentMonth |
      | eligEndDate        | null                 |
      | createdOn          | current              |
      | createdBy          | null                 |
      # CP-20171-02 3.0 Decide Program-Population
      | population         | GF-GENERAL           |
      | programPopulation  | Medicaid-GF general  |
      # CP-20171-04 4.0 Decide Benefit Status
      | benefitStatus      | Enrolled             |
    # CP-20171-05 5.0 Re-determine Consumer Actions and Change Window
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Pre-lockin  |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Anniversary |
      | [1].planSelectionAllowed | true                    |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | RN9.consumerId        |
      | consumerFound | true                  |
      | recordId      | 4                     |

  @API-CP-12239 @API-CP-12239-01 #@API-EE-GF @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: GF E&E Decide Scenario for New Enrollment 1.0 Decide Scenario is New Enrollment
    Given I will get the Authentication token for "GACRM" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 2                      |
      | isEnrollmentProviderRequired | no                     |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | enrollmentStartDate          | <enrollmentStartDate>  |
      | txnStatus                    | Accepted               |
      | planCode                     | 84                     |
      | programCode                  | M                      |
      | subProgramTypeCode           | GF                     |
      | anniversaryDate              | anniversaryDate        |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenario>"
    Examples:
      | name | eligibilityStartDate | enrollmentStartDate  | eligibilityScenario | enrollmentScenario |
      | RM1  | current              | current              | New Eligibility     | New Enrollment     |
      | RM2  | 1stDayofNextMonth    | 1stDayofNextMonth    | Not Found           | Not Found          |
      | RM3  | current              | 1stDayofPresentMonth | Not Found           | Not Found          |

  @API-CP-12239 @API-CP-12239-02 #@API-EE-GF @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: GF E&E Decide Scenario for New Enrollment 2.0 Decide Scenario is New Retroactive Enrollment
    Given I will get the Authentication token for "GACRM" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 4                      |
      | isEnrollmentProviderRequired | no                     |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | enrollmentStartDate          | <enrollmentStartDate>  |
      | txnStatus                    | Accepted               |
      | planCode                     | 84                     |
      | programCode                  | M                      |
      | subProgramTypeCode           | GF                     |
      | anniversaryDate              | anniversaryDate        |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "<eligibilityScenario>","<enrollmentScenario>"
    Examples:
      | name | eligibilityStartDate | enrollmentStartDate  | eligibilityScenario         | enrollmentScenario         |
      | RM4  | 1stDayofPresentMonth | 1stDayofPresentMonth | New Retroactive Eligibility | New Retroactive Enrollment |
      | RM5  | 1stDayofPresentMonth | 1stDayofLastMonth    | Not Found                   | Not Found                  |
      | RM6  | current              | current              | Not Found                   | Not Found                  |

  @API-CP-12237 @API-CP-12237-01 @API-CP-12237-02  # @API-EE-GF @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: GF Decide Consumer Plan Change Actions - Pre-Lockin, Anniversary For GF, PCK, and P4HB
    Given I will get the Authentication token for "GACRM" in "CRM"
    Given I created a consumer with population type "CUSTOM" with data
      | age | <age> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | planCode                     | 84                   |
      | programCode                  | <programCode>        |
      | subProgramTypeCode           | <subProgramCode>     |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "<population>"
    Then I verify benefit status records are displayed with programpopulation as "<programpopulation>"
    And I verify benefit status records are displayed with benefit status "<benefitStatus>"
    Then I Verify Consumer Actions as following data
    # 1.0 Determine “Plan Change Pre-lockin”
      | [0].action               | <action>                |
      | [0].consumerAction       | Plan Change Pre-lockin  |
      | [0].planSelectionAllowed | true                    |
      | [0].changeAllowedFrom    | current                 |
      | [0].changeAllowedUntil   | planChangePrelockin     |
    # 2.0 Determine “Plan Change Anniversary”
      | [1].action               | <action>                |
      | [1].consumerAction       | Plan Change Anniversary |
      | [1].planSelectionAllowed | true                    |
      | [1].changeAllowedFrom    | planChangeAnniversary   |
      | [1].changeAllowedUntil   | daybeforeAnniversary    |
    Examples: Decide Program-Population for GF, PCK, P4HB, GF360
      | name | age | programCode | subProgramCode | population    | programpopulation      | benefitStatus | action      |
      | RM7  | 30  | M           | GF             | GF-GENERAL    | Medicaid-GF general    | Enrolled      | Available   |
      | RM8  | 12  | P           | PCK            | PCK-GENERAL   | Medicaid-PCK general   | Enrolled      | Available   |
      | RM9  | 60  | H           | P4HB           | P4HB-GENERAL  | Medicaid-P4HB general  | Enrolled      | Available   |
      # 3.0 Determine both Plan Change Consumer Actions for GF360
      | RM10 | 50  | F           | GF360          | GF360-GENERAL | Medicaid-GF360 general | Enrolled      | Unavailable |

  @API-CP-12237 @API-CP-12237-01 @API-CP-12237-02 @API-CP-11053-05 @API-CP-11053-06 #@API-EE-GF @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: GF Decide Consumer Plan Change Actions - Pre-Lockin, Anniversary For GF, PCK, and P4HB (negative test)
    Given I will get the Authentication token for "GACRM" in "CRM"
    Given I created a consumer with population type "CUSTOM" with data
      | age | 110 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | planCode                     | 84                   |
      | programCode                  | H                    |
      | subProgramTypeCode           | P4HB                 |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RM11" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "RM11.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "NONE"
    Then I verify benefit status records are displayed with programpopulation as "NONE"
    And I verify benefit status records are displayed with benefit status "null"
    Then I Verify Consumer Actions as following data
    # 1.0 Determine “Plan Change Pre-lockin” and “Plan Change Anniversary” are unavailable
      | [0] | empty |

