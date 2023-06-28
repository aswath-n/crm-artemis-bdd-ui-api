Feature: Create API for ATS to consume the benefit status for case consumers


  @API-CP-33509 @API-EE @API-CRM-Regression @kursat
  Scenario: Call EB service to retrieve consumer benefit status (Single Consumer)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 33509-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33509-01.consumerId  |
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
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    Then I verify benefit statues and program population for multiple consumers
      | [0].consumerId     | 33509-01.consumerId  |
      | [0].eligStatusCode | Mandatory            |
      | [0].eligStartDate  | 1stDayofPresentMonth |
      | [0].eligEndDate    | null                 |
      | [0].benefitStatus  | Eligible             |
      | [0].population     | PREGNANT-WOMEN    |




  @API-CP-33509 @API-EE @API-CRM-Regression @kursat
  Scenario: Call EB service to retrieve consumer benefit status (Multiple consumers)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 33509-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33509-02.consumerId  |
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
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    # Consumer 2
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 33509-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33509-03.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    # Consumer 3
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 33509-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33509-04.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33509-04.consumerId  |
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
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    Then I verify benefit statues and program population for multiple consumers
      | [0].consumerId     | 33509-02.consumerId  |
      | [0].eligStatusCode | Mandatory            |
      | [0].eligStartDate  | 1stDayofPresentMonth |
      | [0].eligEndDate    | null                 |
      | [0].benefitStatus  | Eligible             |
      | [0].population     | PREGNANT-WOMEN    |
      | [1].consumerId     | 33509-03.consumerId  |
      | [1].eligStatusCode | Mandatory            |
      | [1].eligStartDate  | 1stDayofPresentMonth |
      | [1].eligEndDate    | null                 |
      | [1].benefitStatus  | Eligible             |
      | [1].population     | MEDICAID-GENERAL     |
      | [2].consumerId     | 33509-04.consumerId  |
      | [2].eligStatusCode | Mandatory            |
      | [2].eligStartDate  | 1stDayofPresentMonth |
      | [2].eligEndDate    | null                 |
      | [2].benefitStatus  | Enrolled             |
      | [2].population     | MEDICAID-GENERAL     |