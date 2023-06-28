Feature: API- Enroll & Plan Change Consumer Actions- Available plans

  @API-CP-9288 @API-CP-9288-01 @API-EE  @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Available plans for Plan Chang- Pregnant Women- MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 9288 |
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9288.consumerId                                          |
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
      | planId                       | 3146                                                     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9288.consumerId                                          |
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 4                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
      | planId                       | 1880                                                     |
      | serviceRegionCode            | East                                                     |
    And I run create Eligibility and Enrollment API
    When I initiated get available plans  for Plan Transfer by consumer ids "9288.consumerId"
    And I run get eligible and enrollment plans API
    Then I verify eligible and enrollment plans details
      | planName           |
      | AMERIGROUP COMMUNITY CARE |
      | CARESOURCE GEORGIA |
      | PEACH STATE        |
      | WELLCARE           |


  @API-CP-11377 @API-CP-11377-02 @API-EE  @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Available plans for Enroll Action -Newborn population- MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | nextDay                                                  |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
      | planId                       | 3146                                                     |
    And I run create Eligibility and Enrollment API
    When I initiated get eligible and enrollment plans by consumer ids ""
    And I run get eligible and enrollment plans API
    Then I verify eligible and enrollment plans details
      | planName                  | populationType |
      | AMERIGROUP COMMUNITY CARE | NEWBORN        |
      | CARESOURCE GEORGIA        | NEWBORN        |
      | PEACH STATE               | NEWBORN        |
      | WELLCARE                  | NEWBORN        |

  # CHIP scenarios are muted and will be revised later. Regression tags API-EE and eligibility-enrollment-ms-EE are removed
  @API-CP-12749 @API-CP-12749-01 @shruti
  Scenario: Verify Available plans for Plan Change -Newborn population-CHIP
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | nextDay                                                  |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | P                                                        |
      | programTypeCode              | CHIP                                                     |
      | subProgramTypeCode           | PEACHCAREGF                                              |
      | planId                       | 2935                                                     |
    And I run create Eligibility and Enrollment API
    And I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "MEDICAL", "nextDay", "null" and "false"
    And I run create enrollment API
    When I initiated get eligible and enrollment plans by consumer ids ""
    And I run get eligible and enrollment plans API
    Then I verify eligible and enrollment plans details
      | planName                | populationType |
      | CARESOURCE GEORGIA CHIP | NEWBORN        |
      | PEACH STATE CHIP        | NEWBORN        |


  @API-CP-9486 @API-CP-9486-01 @API-EE  @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Available plans for Enroll action- pregnant Women
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with population type "PREGNANT-WOMEN"
    And  I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | H                 |
      | subProgramTypeCode           | MEDICAIDMCHB      |
      | planId                       | 1880              |
    And I run create Eligibility and Enrollment API
    When I initiated get eligible and enrollment plans by consumer ids ""
    And I run get eligible and enrollment plans API
    Then I verify eligible and enrollment plans details
      | planName                  |
      | AMERIGROUP COMMUNITY CARE |
      | CARESOURCE GEORGIA        |
      | PEACH STATE               |
      | WELLCARE                  |

 # CHIP scenarios are muted and will be revised later. Regression tags API-EE and eligibility-enrollment-ms-EE are removed
  @API-CP-10799 @API-CP-10799-01 @shruti
  Scenario: Verify Available plans for Plan Change- CHIP- PREGNANT-WOMEN
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with population type "PREGNANT-WOMEN"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | P                                                        |
      | planId                       | 3150                                                     |
      | programTypeCode              | CHIP                                                     |
      | subProgramTypeCode           | PEACHCAREGF                                              |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 4                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | P                                                        |
      | programTypeCode              | CHIP                                                     |
      | subProgramTypeCode           | PEACHCAREGF                                              |
      | planId                       | 1884                                                     |
    And I run create Eligibility and Enrollment API
    And I initiated get available plans  for Plan Transfer by consumer ids ""
    And I run get eligible and enrollment plans API
    Then I verify eligible and enrollment plans details
      | planName                |
      | CARESOURCE GEORGIA CHIP |
      | PEACH STATE CHIP        |

 # CHIP scenarios are muted and will be revised later. Regression tags API-EE and eligibility-enrollment-ms-EE are removed
  @API-CP-10799 @API-CP-10799-02 @shruti
  Scenario: Verify Available plans for Plan Change- CHIP- MEDICAID-GENERAL
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with population type "MEDICAID-GENERAL"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | P                 |
      | planId                       | 2870              |
      | programTypeCode              | CHIP              |
      | subProgramTypeCode           | PEACHCAREGF       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | P                 |
      | planId                       | 1884              |
      | programTypeCode              | CHIP              |
      | subProgramTypeCode           | PEACHCAREGF       |
    And I run create Eligibility and Enrollment API
    And I initiated get available plans  for Plan Transfer by consumer ids ""
    And I run get eligible and enrollment plans API
    Then I verify eligible and enrollment plans details
      | planName                |
      | CARESOURCE GEORGIA CHIP |
      | PEACH STATE CHIP        |

  # CHIP scenarios are muted and will be revised later. Regression tags API-EE and eligibility-enrollment-ms-EE are removed
  @API-CP-10799 @API-CP-10799-3 @shruti
  Scenario: Verify Available plans for Enroll action- chip-pregnant women
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 0                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | P                                                        |
      | planId                       | 1884                                                     |
      | programTypeCode              | CHIP                                                     |
      | subProgramTypeCode           | PEACHCAREGF                                              |
    And I run create Eligibility and Enrollment API
    When I initiated get eligible and enrollment plans by consumer ids ""
    And I run get eligible and enrollment plans API
    Then I verify eligible and enrollment plans details
      | planName                |
      | CARESOURCE GEORGIA CHIP |
      | PEACH STATE CHIP        |

 # CHIP scenarios are muted and will be revised later. Regression tags API-EE and eligibility-enrollment-ms-EE are removed
  @API-CP-10799 @API-CP-10799-4 @shruti
  Scenario: Verify Available plans for Enroll action- chip-MEDICAID-GENERAL
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | P                 |
      | planCode                     | 87                |
      | planId                       | 1885              |
      | programTypeCode              | CHIP              |
      | subProgramTypeCode           | PEACHCAREGF       |
    And I run create Eligibility and Enrollment API
    When I initiated get eligible and enrollment plans by consumer ids ""
    And I run get eligible and enrollment plans API
    Then I verify eligible and enrollment plans details
      | planName                |
      | CARESOURCE GEORGIA CHIP |
      | PEACH STATE CHIP        |

# below scenario is the same as @API-CP-9486-01 so removing regression tags API-EE  eligibility-enrollment-ms-EE. This scenario should be deleted.
  @API-CP-9486 @API-CP-9486-02 @shruti
  Scenario: Verify Available plans for Enroll-Medicaid- Pregnant Women
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with population type "PREGNANT-WOMEN"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 0                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1880                                                     |
    And I run create Eligibility and Enrollment API
    When I initiated get eligible and enrollment plans by consumer ids ""
    And I run get eligible and enrollment plans API
    Then I verify eligible and enrollment plans details
      | planName                  |
      | AMERIGROUP COMMUNITY CARE |
      | CARESOURCE GEORGIA        |
      | PEACH STATE               |
      | WELLCARE                  |







