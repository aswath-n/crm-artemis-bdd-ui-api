Feature: API: benefit status DMN-current

  @API-CP-10714 @API-CP-10714-01-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status =Eligible ; current Eligibility no current enrollment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | past                                                     |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
#      |eligibilityEndDate        |future|
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"


  @API-CP-10714 @API-CP-10714-01-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status =Enrolled ; current Eligibility yes current enrollment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | past              |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 2                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | providerNpi                  | 1                                                        |
      | providerStartDate            | 1stDayofPresentMonth                                     |
      | providerEndDate              | 90DayFromFirstDayOfMonth                                 |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | programCode                  | M                                                        |
      | anniversaryDate              | anniversaryDate                                          |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"


  @API-CP-10714 @API-CP-10714-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status =Enrolled ; ; current Eligibility yes current enrollment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 4                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | providerNpi                  | 1                                                        |
      | providerStartDate            | 1stDayofPresentMonth                                     |
      | providerEndDate              | 90DayFromFirstDayOfMonth                                 |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
      | programCode                  | M                                                        |
      | txnStatus                    | Accepted                                                 |
      | anniversaryDate              | anniversaryDate                                          |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"