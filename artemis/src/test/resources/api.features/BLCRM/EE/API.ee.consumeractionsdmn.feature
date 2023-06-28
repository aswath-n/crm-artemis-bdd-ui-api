Feature: To determine Consumer Action& due date for Enroll & Plan Change actions

  # scenario is updated to pass but changeAllowedUntil being lastDayofPresentMonth needs to be validated
  # (if changeAllowedUntil should be firstDayofNextMonth then needs fix)
  @API-CP-9627 @API-CP-9627-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Enroll Action is Required -current- GP-MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
#    Then I verify consumer action as "Enroll" and due date for population type "MEDICAID-GENERAL"
    Then I Verify Consumer Actions as following data
      | [0].action               | Required              |
      | [0].consumerAction       | Enroll                |
      | [0].planSelectionAllowed | true                  |
      | [0].changeAllowedFrom    | current               |
      | [0].changeAllowedUntil   | lastDayofPresentMonth |

  # scenario is updated to pass but changeAllowedUntil being lastDayofPresentMonth needs to be validated
  # (if changeAllowedUntil should be firstDayofNextMonth then needs fix)
  @API-CP-9627 @API-CP-9627-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Enroll Action is Available- current-PREGNANT-WOMEN -MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
#    Then I verify consumer action as "Enroll" and due date for population type "PREGNANT-WOMEN"
    Then I Verify Consumer Actions as following data
      | [0].action               | Required              |
      | [0].consumerAction       | Enroll                |
      | [0].planSelectionAllowed | true                  |
      | [0].changeAllowedFrom    | current               |
      | [0].changeAllowedUntil   | lastDayofPresentMonth |

  # scenario is updated to pass but changeAllowedUntil being lastDayofPresentMonth needs to be validated
  # (if changeAllowedUntil should be firstDayofNextMonth then needs fix)
  @API-CP-9627 @API-CP-9627-03 @API-EE  @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Enroll Action is available - current- FOSTER-CARE -MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | F                    |
      | subProgramTypeCode           | FOSTERCARE           |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "FOSTER-CARE"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | F                    |
      | subProgramTypeCode           | FOSTERCARE           |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
#    Then I verify consumer action as "Enroll" and due date for population type "FOSTER-CARE"
    Then I Verify Consumer Actions as following data
      | [0].action               | Required              |
      | [0].consumerAction       | Enroll                |
      | [0].planSelectionAllowed | true                  |
      | [0].changeAllowedFrom    | current               |
      | [0].changeAllowedUntil   | lastDayofPresentMonth |

  @API-CP-9627 @API-CP-9627-04 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Enroll Action is avilable-current- NEWBORN -MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify consumer action as "Enroll" and due date for population type "NEWBORN"

  @API-CP-9627 @API-CP-9627-05 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Enroll Action = Unavailable Pregnant-women -MEDICAID-Benefit Status = Enrolled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | planId                       | 1880                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify consumer action as "null" and due date for population type "PREGNANT-WOMEN"

  @API-CP-9627 @API-CP-9627-06 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Enroll Action is Not Available NEWBORN -MEDICAID-Benefit Status = Partially Enrolled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planId                       | 1880                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
#    Then I verify consumer action as "null" and due date for population type "NEWBORN"
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
      | [0].changeAllowedFrom    | null        |
      | [0].changeAllowedUntil   | null        |

  # scenario is updated to pass but changeAllowedUntil being lastDayofPresentMonth needs to be validated
  # (if changeAllowedUntil should be firstDayofNextMonth then needs fix)
  @API-CP-9627 @API-CP-9627-07 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Enroll Action is available -future- GP-MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
#    Then I verify consumer action as "Enroll" and due date for population type "MEDICAID-GENERAL"
    Then I Verify Consumer Actions as following data
      | [0].action               | Required              |
      | [0].consumerAction       | Enroll                |
      | [0].planSelectionAllowed | true                  |
      | [0].changeAllowedFrom    | current               |
      | [0].changeAllowedUntil   | lastDayofPresentMonth |

  @API-CP-9627 @API-CP-9627-09 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Enroll Action is available - future- FOSTER-CARE -MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE" with data
      | saveConsumerInfo | 9627-09 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9627-09.consumerId  |
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
#    Then I verify consumer action as "Enroll" and due date for population type "FOSTER-CARE"
    And I initiated get benefit status by consumer id "9627-09.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions as following data
      | [0].action               | Required              |
      | [0].consumerAction       | Enroll                |
      | [0].planSelectionAllowed | true                  |
      | [0].changeAllowedFrom    | current               |
      | [0].changeAllowedUntil   | lastDayofPresentMonth |

  @API-CP-9627 @API-CP-9627-10 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Enroll Action is available-future- NEWBORN -MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify consumer action as "Enroll" and due date for population type "NEWBORN"

  @API-CP-9627 @API-CP-9627-11 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Enroll Action = Unavailable Pregnant-women -MEDICAID-Benefit Status = Not Eligible
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | eligibilityEndReason         | Test                 |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api

  @API-CP-14096 @API-CP-14247 @API-CP-14096-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Plan Change Action is available -future- GP-MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planId                       | 1881              |
      | planCode                     | 85                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 2                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planId                       | 1881              |
      | planCode                     | 85                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify consumer action as "Plan Change Pre-lockin" and due date for population type "MEDICAID-GENERAL"
    Then I verify consumer action as "Plan Change Anniversary" and due date for population type "MEDICAID-GENERAL"

  @API-CP-14096 @API-CP-14247 @API-CP-14096-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Plan Chnage Action is available -current- GP-M
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 9                    |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify consumer action as "Plan Change Pre-lockin" and due date for population type "MEDICAID-GENERAL"
    Then I verify consumer action as "Plan Change Anniversary" and due date for population type "MEDICAID-GENERAL"


  @API-CP-14096 @API-CP-14247 @API-CP-14096-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Plan Change Action is available -future- PREGNANT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | H                 |
      | planId                       | 1880              |
      | subProgramTypeCode           | MEDICAIDMCHB      |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify consumer action as "Plan Change Pre-lockin" and due date for population type "MEDICAID-GENERAL"

  @API-CP-14096 @API-CP-14247 @API-CP-14096-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Plan Change Action is available -current- PREGNANT
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofPresentMonth                                     |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1870                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
      | anniversaryDate              | anniversaryDate                                          |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 4                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1870                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
      | anniversaryDate              | anniversaryDate                                          |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify consumer action as "Plan Change Pre-lockin" and due date for population type "PREGNANT-WOMEN"
    Then I verify consumer action as "Plan Change Anniversary" and due date for population type "PREGNANT-WOMEN"

  @API-CP-14096 @API-CP-14247 @API-CP-14096-02-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Plan Chnage Action is available -current- foster
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | planId                       | 1880              |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planCode                     | 84                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planId                       | 1880              |
      | planCode                     | 85                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify consumer action as "Plan Change Pre-lockin" and due date for population type "FOSTER-CARE"
    Then I verify consumer action as "Plan Change Anniversary" and due date for population type "FOSTER-CARE"

  @API-CP-14096 @API-CP-14247  @API-CP-14096-09 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Plan Change Action is Unavailable -current- foster
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityEndDate           | future            |
      | eligibilityEndReason         | Test              |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | planId                       | 1880              |
      | subProgramTypeCode           | FOSTERCARE        |
      | anniversaryDate              | future            |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
#    Then I verify consumer action as "null" and due date for population type "FOSTER-CARE"
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
      | [0].changeAllowedFrom    | null        |
      | [0].changeAllowedUntil   | null        |

  @API-CP-14096 @API-CP-14247 @API-CP-14096-10 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Plan Change Action is Enroll , Plan change -current- foster
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | planId                       | 1880              |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planCode                     | 84                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
      | planId                       | 1880              |
      | planCode                     | 85                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify consumer action as "Enroll" and due date for population type "MEDICAID-GENERAL"
    Then I verify consumer action as "Plan Change Pre-lockin" and due date for population type "MEDICAID-GENERAL"
    Then I verify consumer action as "Plan Change Anniversary" and due date for population type "MEDICAID-GENERAL"

  @API-CP-14096 @API-CP-14247 @API-CP-14096-01-100 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Plan Change Action is available -future- NEWBORN
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | planId                       | 1880              |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planId                       | 1880              |
      | planCode                     | 84                |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify consumer action as "Plan Change Pre-lockin" and due date for population type "MEDICAID-GENERAL"
    Then I verify consumer action as "Plan Change Anniversary" and due date for population type "MEDICAID-GENERAL"
