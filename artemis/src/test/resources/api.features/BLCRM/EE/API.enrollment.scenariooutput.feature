@scenarioOutlineDMN
Feature: API: Identify Scenario Type Feature

  @API-CP-8699 @API-CP-8699-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =New Eligibilty, No Enrollmment , recordId =1
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "New Eligibility","null"


  @API-CP-8699 @API-CP-8699-02 @CP-12911 @CP-12911-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =New Enrollment, No Eligibility , recordId =2
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "New Eligibility","New Enrollment"


  @API-CP-8699 @API-CP-8699-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =New Eligibilty, No Enrollmment , recordId =3
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
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","null"

  @API-CP-8699 @API-CP-8699-04 @Cp-12911 @CP-12911-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =New Retroactive Enrollment,New Eligibilty, new Enrollmment , recordId =4
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    When I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         |[blank]|
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Retroactive Enrollment"

  @API-CP-8699 @API-CP-8699-05 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Retroactive Eligibility Date Change Record Id 5
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
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
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 5                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","New Retroactive Enrollment"

  @API-CP-8699 @API-CP-8699-06 @CP-12911 @CP-12911-03 @API-CP-10730 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Retroactive Enrollment Date Change Record Id 6
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
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
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | planCode                     | 84                   |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 5                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 6                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","Retroactive Enrollment Date Change"

  # CP-8699 AC 7.0 states that it has been de-prioritized
  @API-CP-8699 @API-CP-8699-07 @API-EE-ignore @API-CRM-Regression-ignore @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output = Enrollment Renewal Record Id 7
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
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
      | anniversaryDate              | anniversaryDate      |
      | planCode                     | 84                   |
      | planId                       |[blank]|

    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayoflastMonth |
      | eligibilityStartDate         | anniversaryDate   |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | anniversaryDate              | futureAnniversaryDate |
      | recordId                     | 7                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | anniversaryDate       |
      | eligibilityStartDate         | anniversaryDate       |
      | txnStatus                    | Accepted              |
      | programCode                  | M                     |
      | planCode                     | 84                    |
      | planId                       |[blank]|
      | planEndDateReason            | test                  |
    And I run create Eligibility and Enrollment API
    And Wait for 10 seconds
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "null","Enrollment Renewal"


  @API-CP-8699 @API-CP-8699-08 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output = Enrollment Change Record Id 8
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
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
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
      | planCode                     | 84                |
      | planId                       |[blank]|
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
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
      | planCode                     | 84                |
      | planId                       | 1                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 8                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | ACCEPTED          |
      | programCode                  | M                 |
      | planCode                     | 85                |
      | planId                       | 2                 |
      | planEndDateReason            | Test              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Plan Change"

    #removing from regression since it is already covered in another feature
  @API-CP-8699 @API-CP-8699-09 @API-EE-ignore @API-CRM-Regression-ignore @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Reinstatement of Eligibility Record Id 9
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                                                                                           |
      | otherSegments                | facilityInfo,financialInfo,hospiceInfo,longTermCareInfo,medicareInfo,specialPopulationInfo,thirdPartyInsuranceInfo,waiverInfo |
      | isEnrollemntRequired         | no                                                                                                                            |
      | recordId                     | 1                                                                                                                             |
      | isEnrollmentProviderRequired | no                                                                                                                            |
      | enrollmentStartDate          | past                                                                                                                          |
      | eligibilityStartDate         | past                                                                                                                          |
      | eligibilityEndDate           | 1stDayofPresentMonth                                                                                                          |
      | txnStatus                    | Accepted                                                                                                                      |
      | programCode                  | M                                                                                                                             |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                                                                                           |
      | otherSegments                | facilityInfo,financialInfo,hospiceInfo,longTermCareInfo,medicareInfo,specialPopulationInfo,thirdPartyInsuranceInfo,waiverInfo |
      | isEnrollemntRequired         | no                                                                                                                            |
      | recordId                     | 11                                                                                                                            |
      | isEnrollmentProviderRequired | no                                                                                                                            |
      | enrollmentStartDate          | contiguos                                                                                                                     |
      | eligibilityStartDate         | contiguos                                                                                                                     |
      | eligibilityEndDate           | future                                                                                                                        |
      | txnStatus                    | Accepted                                                                                                                      |
      | programCode                  | M                                                                                                                             |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Reinstatement of Eligibility","null"
#removing as this scenario is not applicable
  @API-CP-8699 @API-CP-8699-10  @CP-12911-07 @API-EE-ignore @API-CRM-Regression-ignore @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Reinstatement of Enrollment Record Id 10
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                                                                                           |
      | otherSegments                | facilityInfo,financialInfo,hospiceInfo,longTermCareInfo,medicareInfo,specialPopulationInfo,thirdPartyInsuranceInfo,waiverInfo |
      | isEnrollemntRequired         | yes                                                                                                                           |
      | recordId                     | 0                                                                                                                             |
      | isEnrollmentProviderRequired | no                                                                                                                            |
      | enrollmentStartDate          | past                                                                                                                          |
      | eligibilityStartDate         | past                                                                                                                          |
      | eligibilityEndDate           | 1stDayofPresentMonth                                                                                                          |
      | enrollmentEndDate            | 1stDayofPresentMonth                                                                                                          |
      | txnStatus                    | Accepted                                                                                                                      |
      | programCode                  | M                                                                                                                             |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                                                                                           |
      | otherSegments                | facilityInfo,financialInfo,hospiceInfo,longTermCareInfo,medicareInfo,specialPopulationInfo,thirdPartyInsuranceInfo,waiverInfo |
      | isEnrollemntRequired         | yes                                                                                                                           |
      | recordId                     | 10                                                                                                                            |
      | isEnrollmentProviderRequired | no                                                                                                                            |
      | enrollmentStartDate          | contiguos                                                                                                                     |
      | eligibilityStartDate         | contiguos                                                                                                                     |
      | eligibilityEndDate           | future                                                                                                                        |
      | enrollmentEndDate            | future                                                                                                                        |
      | txnStatus                    | Accepted                                                                                                                      |
      | programCode                  | M                                                                                                                             |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "null","Reinstatement of Enrollment"

  @API-CP-8699 @API-CP-8699-11 #@API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Loss of Eligibility Current  Enrollmment , recordId =11
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
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 13                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | currentPlusOne       |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | eligibilityEndReason         | testend              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"


  @API-CP-8699 @API-CP-8699-12 @CP-12911 @CP-12911-04 #@API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =MEDICAL Disenrol, Current  Enrollment , recordId =12
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
      | eligibilityEndDate           | future            |
      | enrollmentEndDate            | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | future            |
      | enrollmentEndDate            | future            |
      | planId                       | 1                 |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 11                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | currentPlusOne       |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | eligibilityEndReason         | 31                   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 12                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | currentPlusOne    |
      | enrollmentEndDate            | currentPlusOne    |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planEndDateReason            | 32                |
      | planId                       | 1870              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","Medical Disenroll"


  @API-CP-8778 @API-CP-8778-01 @CP-12911-06 #@API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Mark as processed , recordId =1
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
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
      | planCode                     | 84                |
      | planId                       |[blank]|
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 2                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       |[blank]|
    And I run create Eligibility and Enrollment API
#    And I provide the enrollment and eligibility information to create enrollment
#      |isEligibilityRequired|yes|
#      |otherSegments        |[blank]|
#      |isEnrollemntRequired |yes |
#      |recordId             |0  |
#      |isEnrollmentProviderRequired|no|
#      |enrollmentStartDate         |1stDayofNextMonth|
#      |eligibilityStartDate        |1stDayofNextMonth|
#      |txnStatus                  |Accepted|
#      |programCode     |M       |
#      |planCode        | 84     |
#      |planId          |[blank]|
#    And I run create Eligibility and Enrollment API
#    And I provide the enrollment and eligibility information to create enrollment
#      |isEligibilityRequired|no|
#      |otherSegments        |[blank]|
#      |isEnrollemntRequired |yes |
#      |recordId             |2  |
#      |isEnrollmentProviderRequired|no|
#      |enrollmentStartDate         |1stDayofNextMonth|
#      |eligibilityStartDate        |1stDayofNextMonth|
#      |eligibilityEndDate        |future|
#      |txnStatus                  |Accepted|
#      |programCode     |M       |
#      |planId          |1    |
#    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Mark As Processed","Mark as Processed"


  @API-CP-8778 @API-CP-8778-02  @CP-12911-05 #@API-EE-removed @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Not Found , recordId =1
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 1                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"


  @API-CP-15324 @API-CP-15324-01 #@API-EE-removed @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Not Found , recordId =15 , invalid program code
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
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 2                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Submitted_to_state   |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 1835                 |
    And I run create Eligibility and Enrollment API
#    And I provide the enrollment and eligibility information to create enrollment
#      |isEligibilityRequired|no|
#      |otherSegments        |[blank]|
#      |isEnrollemntRequired |yes |
#      |recordId             |15  |
#      |isEnrollmentProviderRequired|no|
#      |enrollmentStartDate         |1stDayofPresentMonth|
#      |eligibilityStartDate        |1stDayofPresentMonth|
#      |eligibilityEndDate        |future|
#      |txnStatus                  |Accepted|
#      |programCode     |M       |
#      |planCode        |95      |
#    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I verify scenario output in the benefit status records are displayed as "Not Found","New Enrollment Accept"

  @API-CP-15761 @API-CP-15761-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Loss of Eligibility Current  Enrollmment Enrolled, recordId =13
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
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
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 13                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | current              |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | eligibilityEndReason         | testend              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","New Retroactive Enrollment"

  @API-CP-15761 @API-CP-15761-01 #@API--removed @API-CRM-Regression-removed @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Scenario output =Loss of Eligibility  Eligible, recordId =13
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
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
#    And I provide the enrollment and eligibility information to create enrollment
#      |isEligibilityRequired|no|
#      |otherSegments        |[blank]|
#      |isEnrollemntRequired |yes |
#      |recordId             |4  |
#      |isEnrollmentProviderRequired|no|
#      |enrollmentStartDate         |1stDayofLastMonth|
#      |eligibilityStartDate        |1stDayofLastMonth|
#      |eligibilityEndDate        |future|
#      |txnStatus                  |Accepted|
#      |programCode     |M       |
#    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 13                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | currentPlusOne       |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | eligibilityEndReason         | testend              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Loss of Eligibility","null"

  @API-CP-15761 @API-CP-15761-02 #@API-EE-removed @API-CRM-Regression-removed @eligibility-enrollment-ms-EE @shruti
  Scenario: Inavlis scenario for Loss of Eligibility Current  Enrollment Eligible, recordId =13
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
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
      | programCode                  | M                 |
#      |eligibilityEndReason|  testend     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | anniversaryDate              | anniversaryDate   |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 13                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | current              |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | eligibilityEndReason         | testend              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","null"

  @API-CP-17820 @API-CP-15753 @API-CP-15753-01 @API-CP-15747 @API-CP-15747-01 @API-CP-15747-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: CP-15753 Decide Scenario MMIS Sends Retroactive Enrollment Date Change recordId = 6 positive test
  CP-15747 Create Business Event Retroactive Enrollment Date Change
  CP-17820 Updates to Create or Update Data Based on MMIS sends Retroactive Enrollment Date Change recordId = 6 status of accepted
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | RC1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC1.consumerId       |
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
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC1.consumerId       |
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
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC1.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 5                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC1.consumerId    |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 6                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 145               |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "RC1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","Retroactive Enrollment Date Change"
    And Wait for 20 seconds
    # @API-CP-15747-01
    Then I will verify business events are generated with data
      | eventName           | RETRO_ACTIVE_ENROLLMENT_DATE_CHANGE |
      | channel             | SYSTEM_INTEGRATION                  |
      | createdBy           | 597                                 |
      | processDate         | current                             |
      | externalCaseId      | RC1.caseIdentificationNumber        |
      | externalConsumerId  | RC1.externalConsumerId              |
      | consumerId          | RC1.consumerId                      |
      | consumerName        | RC1                                 |
      #  @API-CP-15747-02
      | enrollmentStartDate | 1stDayofLastMonth                   |
      | enrollmentEndDate   |[blank]|
      | planChangeReason    | null                                |
      | planCode            | 84                                  |
      | enrollmentType      | MEDICAL                             |
      | selectionStatus     | ACCEPTED                            |
      | consumerPopulation  | MEDICAID-GENERAL                    |
      | benefitStatus       | Enrolled                            |

  @API-CP-15753 @API-CP-15753-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: CP-15747 Decide Scenario MMIS Sends Retroactive Enrollment Date Change recordId = 6 negative test
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
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RC2" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC2.consumerId       |
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
#    And Wait for 5 seconds
#    And I provide the enrollment and eligibility information to create enrollment
#      | consumerId                   | RC2.consumerId    |
#      | isEligibilityRequired        | yes               |
#      | otherSegments                |[blank]|
#      | isEnrollemntRequired         | no                |
#      | recordId                     | 5                 |
#      | isEnrollmentProviderRequired | no                |
#      | enrollmentStartDate          | 1stDayofLastMonth |
#      | eligibilityStartDate         | 1stDayofLastMonth |
#      | txnStatus                    | Accepted          |
#      | programCode                  | M                 |
#    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC2.consumerId               |
      | isEligibilityRequired        | no                           |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                          |
      | recordId                     | 6                            |
      | isEnrollmentProviderRequired | no                           |
      | enrollmentStartDate          | 5daysBefore1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth            |
      | eligibilityEndDate           | future                       |
      | txnStatus                    | Accepted                     |
      | programCode                  | M                            |
      | planCode                     | 84                           |
      | planId                       | 145                          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "RC2.consumerId"
    And I run get enrollment api
#    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","Not Found"
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"

  @API-CP-17820 @API-CP-17820-01 @API-CP-17820-02 @API-CP-17820-03 @API-CP-17820-05 @API-CP-17820-06 @API-CP-17820-07 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Updates to Create or Update Data Based on MMIS sends Retroactive Enrollment Date Change recordId = 6 status of Disenroll Request
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
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RC3" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC3.consumerId       |
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
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update Enrollments
      | [0].planId            | 145                 |
      | [0].planCode          | 84                  |
      | [0].consumerId        | RC3.consumerId      |
      | [0].status            | DISENROLL_REQUESTED |
      | [0].txnStatus         | DISENROLL_REQUESTED |
      | [0].startDate         | fdofmnth::          |
      | [0].endDate           | lstdaymnth::        |
      | [0].serviceRegionCode | East                |
      | [0].channel           | SYSTEM_INTEGRATION  |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC3.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 5                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC3.consumerId    |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 6                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 145               |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    #  @API-CP-17820-02
    And I verify enrollment by consumer id "RC3.consumerId" with data
      | status    | ACCEPTED |
      | updatedOn | current  |
      | updatedBy | 597      |
    And I initiated get benefit status by consumer id "RC3.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","Retroactive Enrollment Date Change"
    # @API-CP-17820-05
    Then I verify latest benefit status records are displayed with benefit status "Enrolled"
    #  @API-CP-17820-04
    And I verify benefit status records are displayed with programpopulation as "Medicaid-General Population"
    # @API-CP-17820-06
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Pre-lockin  |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Anniversary |
      | [1].planSelectionAllowed | true                    |
    # @API-CP-17820-03
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | RC3.consumerId           |
      | consumerFound | true                     |
      | recordId      | 5                        |
    # @API-CP-17820-07
  #removed this validation
#    Then I verify if tasks for consumer id "RC3.consumerId" is created "false"

  @API-CP-17820 @API-CP-17820-01 @API-CP-17820-02 @API-CP-17820-03 @API-CP-17820-05 @API-CP-17820-06 @API-CP-17820-07 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Updates to Create or Update Data Based on MMIS sends Retroactive Enrollment Date Change recordId = 6 status of Disenroll Submitted
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
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RC4" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC4.consumerId       |
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
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | RC4.consumerId     |
      | [0].planId             | 145                |
      | [0].planCode           | 85                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "RC4.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "RC4a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | RC4.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | RC4a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED           |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | RC4.consumerId                |
      | [1].enrollmentId       | RC4a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC4.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 5                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC4.consumerId    |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 6                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 145               |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    #  @API-CP-17820-02
    And I verify enrollment by consumer id "RC4.consumerId" with data
      | status    | DISENROLL_SUBMITTED |
      | updatedOn | current             |
      | updatedBy | 597                 |
    And I initiated get benefit status by consumer id "RC4.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","Retroactive Enrollment Date Change"
    # @API-CP-17820-05
    Then I verify latest benefit status records are displayed with benefit status "Enrolled"
    #  @API-CP-17820-04
    And I verify benefit status records are displayed with programpopulation as "Medicaid-General Population"
    # @API-CP-17820-06
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable             |
      | [0].consumerAction       | Plan Change Pre-lockin  |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Unavailable             |
      | [1].consumerAction       | Plan Change Anniversary |
      | [1].planSelectionAllowed | true                    |
    # @API-CP-17820-03
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | RC4.consumerId           |
      | consumerFound | true                     |
      | recordId      | 5                        |
    # @API-CP-17820-07
#    Then I verify if tasks for consumer id "RC4.consumerId" is created "false"


  @API-CP-15752 @API-CP-15752-01 @API-CP-15752-02 @API-CP-15751 @API-CP-15751-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: CP-15751 Decide Scenario MMIS Sends Retroactive Eligibility Date Change recordId = 5 status of accepted
  CP-15752 Create Business Event: Retroactive Eligibility Date Change
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | RC5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC5.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC5.consumerId    |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 145               |
      | serviceRegionCode            | East              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC5.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 5                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | past              |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I send API call with name "RCa" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I verify enrollment by consumer id "RC5.consumerId" with data
      | status | ACCEPTED |
    And I initiated get benefit status by consumer id "RC5.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","New Retroactive Enrollment"
    And Wait for 20 seconds
    #  @API-CP-15752-01
    Then I will verify business events are generated with data
      | eventName                 | RETRO_ACTIVE_ELIGIBILITY_DATE_CHANGE                |
#      | correlationId             | RCa.eligibilities.[0].coreEligibility.correlationId |
      | channel                   | SYSTEM_INTEGRATION                                  |
      | createdBy                 | 597                                                 |
      | processDate               | current                                             |
      | externalCaseId            | RC5.caseIdentificationNumber                        |
      | externalConsumerId        | RC5.externalConsumerId                              |
      | consumerId                | RC5.consumerId                                      |
      | consumerName              | RC5                                                 |
      #  @API-CP-15752-02
#      | eligibilityStartDate      | 1stDayofLastMonth                                   |
      | eligibilityStartDate      | past                                   |
      | eligibilityEndDate        | null                                                |
      | eligibilityEndReason      | null                                                |
      | eligibilityCategoryCode   | null                                                |
      | eligibilityProgramCode    | M                                                   |
      | eligibilitySubProgramCode | MEDICAIDGF                                          |
      | eligibilityCoverageCode   | string                                              |
      | eligibilityExemptionCode  | qwer                                                |
      | consumerPopulation        | MEDICAID-GENERAL                                    |
      | benefitStatus             | Enrolled                                            |

  @API-CP-15751 @API-CP-15751-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Decide Scenario MMIS Sends Retroactive Eligibility Date Change recordId = 5 status of accepted negative-2
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
    Then I send API call with name "RC6" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC6.consumerId       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC6.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 5                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofNextMonth    |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I verify enrollment by consumer id "RC6.consumerId" with data
      | status | ACCEPTED |
    And I initiated get benefit status by consumer id "RC6.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","Not Found"

  @API-CP-17562 @API-CP-17562-01 @API-CP-17562-03 @API-CP-17562-04 @API-CP-17562-06 @API-CP-17562-07 @API-CP-17562-08 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Updates to Create or Update Data Based on MMIS sends Retroactive Eligibility Date Change Scenario AC 1.0
  1.0 Create or Update Data - Retroactive Eligibility Date Change Scenario (No Enrollment Segment)
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
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RC7" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC7.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 5                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "RC7.consumerId"
    And I run get enrollment api
    # @API-CP-17562-03
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","null"
    # @API-CP-17562-06
    Then I verify latest benefit status records are displayed with benefit status "Eligible"
    #  @API-CP-17562-05
    And I verify benefit status records are displayed with programpopulation as "Medicaid-General Population"
    # @API-CP-17562-07
    Then I Verify Consumer Actions as following data
      | [0].action               | Required |
      | [0].consumerAction       | Enroll   |
      | [0].planSelectionAllowed | true     |
    # @API-CP-17562-04
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | RC7.consumerId           |
      | consumerFound | true                     |
      | recordId      | 5                        |
    # @API-CP-17562-08
#    Then I verify if tasks for consumer id "RC7.consumerId" is created "false"

  @API-CP-15751 @API-CP-15751-02 @API-CP-17562 @API-CP-17562-01 @API-CP-17562-03 @API-CP-17562-04 @API-CP-17562-06 @API-CP-17562-07 @API-CP-17562-08 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: CP-15751 Decide Scenario MMIS Sends Retroactive Eligibility Date Change recordId = 5 status of disenroll requested
  CP-17562 Updates to Create or Update Data Based on MMIS sends Retroactive Eligibility Date Change Scenario AC 2.0
  2.0 Create or Update Data - Retroactive Eligibility Date Change Scenario (Has Enrollment Segment)
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
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RC8" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC8.consumerId       |
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
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update Enrollments
      | [0].planId            | 145                 |
      | [0].planCode          | 84                  |
      | [0].consumerId        | RC8.consumerId      |
      | [0].status            | DISENROLL_REQUESTED |
      | [0].txnStatus         | DISENROLL_REQUESTED |
      | [0].startDate         | fdofmnth::          |
      | [0].endDate           | lstdaymnth::        |
      | [0].serviceRegionCode | East                |
      | [0].channel           | SYSTEM_INTEGRATION  |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC8.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 5                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I verify enrollment by consumer id "RC8.consumerId" with data
      | status | ACCEPTED |
    And I initiated get benefit status by consumer id "RC8.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","New Retroactive Enrollment"
    # @API-CP-17562-06
    Then I verify latest benefit status records are displayed with benefit status "Enrolled"
    #  @API-CP-17562-05
    And I verify benefit status records are displayed with programpopulation as "Medicaid-General Population"
    # @API-CP-17562-07
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Pre-lockin  |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Anniversary |
      | [1].planSelectionAllowed | true                    |
    # @API-CP-17562-04
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ELIGIBILITY_UPDATE_EVENT |
      | consumerId    | RC8.consumerId           |
      | consumerFound | true                     |
      | recordId      | 5                        |
    # @API-CP-17562-08
    # Should task be created or not?. Work on this and previous scenario. Fix automation if needed.
    Then I verify if tasks for consumer id "RC8.consumerId" is created "false"

  @API-CP-15751 @API-CP-15751-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Decide Scenario MMIS Sends Retroactive Eligibility Date Change recordId = 5 status of disenroll submitted
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
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RC9" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC9.consumerId       |
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
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | RC9.consumerId     |
      | [0].planId             | 145                |
      | [0].planCode           | 85                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | East               |
      | [0].selectionReason    | delete::           |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
      | [0].planEndDateReason  | PLAN_TERMINATED    |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "RC9.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "RC9a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | RC9.consumerId                |
      | [0].planId             | delete::                      |
      | [0].planCode           | 84                            |
      | [0].enrollmentId       | RC9a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED           |
      | [0].txnStatus          | DISENROLL_SUBMITTED           |
      | [0].startDate          | fdofmnth::                    |
      | [0].endDate            | lstdaymnth::                  |
      | [0].enrollmentType     | delete::                      |
      | [0].subProgramTypeCode | MEDICAIDGF                    |
      | [0].serviceRegionCode  | East                          |
      | [1].planId             | delete::                      |
      | [1].planCode           | 85                            |
      | [1].consumerId         | RC9.consumerId                |
      | [1].enrollmentId       | RC9a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                    |
      | [1].subProgramTypeCode | MEDICAIDGF                    |
      | [1].serviceRegionCode  | East                          |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC9.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 5                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I verify enrollment by consumer id "RC9.consumerId" with data
      | status | DISENROLL_SUBMITTED |
    And I initiated get benefit status by consumer id "RC9.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","New Retroactive Enrollment"

  @API-CP-15751 @API-CP-15751-03 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Decide Scenario MMIS Sends Retroactive Eligibility Date Change recordId = 5 status of Selection Made
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
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RC10" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId      | RC10.consumerId |
      | [0].planId          | 145             |
      | [0].planCode        | 85              |
      | [0].startDate       | fdnxtmth::      |
      | [0].programTypeCode | M               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC10.consumerId   |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 5                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I verify enrollment by consumer id "RC10.consumerId" with data
      | status | SELECTION_MADE |
    And I initiated get benefit status by consumer id "RC10.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","null"

  @API-CP-15751 @API-CP-15751-04 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: Decide Scenario MMIS Sends Retroactive Eligibility Date Change recordId = 5 status of Submitted to State
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
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RC11" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId      | RC11.consumerId |
      | [0].planId          | 145             |
      | [0].planCode        | 85              |
      | [0].startDate       | fdnxtmth::      |
      | [0].programTypeCode | M               |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].consumerId     | RC11.consumerId        |
      | [0].planId         | delete::               |
      | [0].planCode       | 85                     |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | SUBMITTED_TO_STATE     |
      | [0].txnStatus      | SUBMITTED_TO_STATE     |
      | [0].startDate      | fdnxtmth::             |
      | [0].enrollmentType | delete::               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RC11.consumerId   |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 5                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I verify enrollment by consumer id "RC11.consumerId" with data
      | status | SUBMITTED_TO_STATE |
    And I initiated get benefit status by consumer id "RC11.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Retroactive Eligibility Date Change","null"

  @API-CP-15756 @API-CP-15755 @API-CP-15754 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: API-CP-15756 Decide Scenario MMIS Sends Plan Change (Updates) positive test
  API-CP-15755 Create or Update Data MMIS Sends Plan Change (Updates)
  API-CP-15754 Create Business Event Plan Change (from MMIS)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | RB1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RB1.consumerId       |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
      | planCode                     | 84                   |
      | planId                       |[blank]|
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RB1.consumerId       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
      | planCode                     | 84                   |
      | planId                       | 5192                 |
      | serviceRegionCode            | East                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RB1.consumerId       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 8                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | ACCEPTED             |
      | programCode                  | M                    |
      | planCode                     | 85                   |
      | planId                       | 5193                 |
      | planEndDateReason            | PLAN_HAS_TERMINATED  |
      | serviceRegionCode            | East                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    # @API-CP-15755-01 1.0 Create or Update Plan Data - Prior Segment
    And I verify enrollment by consumer id "RB1.consumerId" with data
      | txnStatus         | DISENROLLED         |
      | planEndDateReason | PLAN_HAS_TERMINATED |
    # @API-CP-15755-02 2.0 Create or Update Plan Data - Requested Segment
    And I verify latest enrollment by consumer id "RB1.consumerId" with data
      | medicalPlanBeginDate | 1stDayofNextMonth |
      | planCode             | 85                |
      | medicalPlanEndDate   | null              |
      | txnStatus            | ACCEPTED          |
    And I initiated get benefit status by consumer id "RB1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Plan Change"
    # @API-CP-15755-03
    Then I verify benefit status records are displayed with benefit status "Enrolled"
    # @API-CP-15755-04
    Then I Verify Consumer Actions as following data
      | [0].action               | Available               |
      | [0].consumerAction       | Plan Change Anniversary |
      | [0].planSelectionAllowed | true                    |
      | [1].action               | Available               |
      | [1].consumerAction       | Plan Change Pre-lockin  |
      | [1].planSelectionAllowed | true                    |
    And Wait for 20 seconds
    # @API-CP-15755-01 1.0 Create or Update Plan Data - Prior Segment
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | RB1.consumerId          |
      | consumerFound | true                    |
      | recordId      | 4                       |
    # @API-CP-15755-02 2.0 Create or Update Plan Data - Requested Segment
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | RB1.consumerId        |
      | consumerFound | true                  |
      | recordId      | 8                     |
    # API-CP-15754 Create Business Event Plan Change (from MMIS)
    Then I will verify business events are generated with data
      #  @API-CP-15754-01
      | eventName                              | PLAN_CHANGE                  |
      #  @API-CP-15754-02
      | channel                                | SYSTEM_INTEGRATION           |
      | createdBy                              | 597                          |
      | processDate                            | current                      |
      | externalCaseId                         | RB1.caseIdentificationNumber |
      | externalConsumerId                     | RB1.externalConsumerId       |
      | consumerId                             | RB1.consumerId               |
      | consumerName                           | RB1                          |
      #  @API-CP-15754-03
      | consumerPopulation                     | MEDICAID-GENERAL             |
      | benefitStatus                          | Enrolled                     |
      #  @API-CP-15754-04
      | previousEnrollment.enrollmentStartDate | 1stDayofPresentMonth         |
      | previousEnrollment.enrollmentEndDate   | lastDayofPresentMonth        |
      | previousEnrollment.planChangeReason    | PLAN_HAS_TERMINATED          |
      | previousEnrollment.rejectionReason     | null                         |
      | previousEnrollment.planCode            | 84                           |
      | previousEnrollment.enrollmentType      | MEDICAL                      |
      | previousEnrollment.selectionStatus     | DISENROLLED                  |
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth            |
      | requestedSelection.enrollmentEndDate   | null                         |
      | requestedSelection.planChangeReason    | null                         |
      | requestedSelection.rejectionReason     | null                         |
      | requestedSelection.planCode            | 85                           |
      | requestedSelection.enrollmentType      | MEDICAL                      |
      | requestedSelection.selectionStatus     | ACCEPTED                     |

  @API-CP-15756 @API-CP-16474-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: Decide Scenario MMIS Sends Plan Change (Updates) negative test
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
      | planCode                     | 84                   |
      | planId                       |[blank]|
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
      | planCode                     | 84                   |
      | planId                       | 5192                 |
      | serviceRegionCode            | Atlanta              |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 8                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | <enrollmentStartDate> |
      | eligibilityStartDate         | 1stDayofPresentMonth  |
      | txnStatus                    | ACCEPTED              |
      | programCode                  | M                     |
      | planCode                     | <planCode>            |
      | planId                       | 2                     |
      | planEndDateReason            | PLAN_HAS_TERMINATED   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | txnStatus          | <txnStatus> |
      | medicalPlanEndDate | null        |
      | planEndDateReason  | null        |
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "Not Found","<enrollmentScenario>"
    Examples:
      | name | enrollmentStartDate | planCode | txnStatus | enrollmentScenario |
      | RB2  | 1stDayofLastMonth   | 85       | ACCEPTED  | Not Found          |
      | RB3  | 1stDayofNextMonth   | 100      | ACCEPTED  | Not Found          |

  @API-CP-8779 @API-CP-10283 @API-CP-16189 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: API-CP-8779 Create or Update Data Based on MMIS sends Enrollment Change Scenario
  API-CP-10283 Create or Update Data Based on MMIS Sends Enrollment Renewal Scenario
  API-CP-16189 Updates to: Capture business events: User, Consumer Initiated Plan Change
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | RB4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RB4.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
      | planCode                     | 84                |
      | planId                       |[blank]|
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RB4.consumerId       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | anniversaryDate              | anniversaryDate      |
      | planCode                     | 84                   |
      | planId                       | 1                    |
      | serviceRegionCode            | Atlanta              |
    Then I send API call with name "RB4" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RB4.consumerId       |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 8                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | ACCEPTED             |
      | programCode                  | M                    |
      | planCode                     | 85                   |
      | planId                       | 2                    |
      | planEndDateReason            | PLAN_HAS_TERMINATED  |
      | serviceRegionCode            | Atlanta              |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    # @API-CP-8779-01 1.0 Create or Update Data - MMIS sends Enrollment Change Scenario - Prior Segment
    # @API-CP-10283-01 1.0 Create or Update Data - MMIS Sends Enrollment Renewal Scenario - Prior Segment
    And I verify enrollment by consumer id "RB4.consumerId" with data
      | planCode                | 84                                        |
      | medicalPlanBeginDate    | 1stDayofLastMonth                         |
      | medicalPlanEndDate      | lastDayofPresentMonth                     |
      | openEnrollmentStartDate | null                                      |
      | openEnrollmentEndDate   | null                                      |
      | openEnrollmentStatus    | null                                      |
      | enrollmentType          | MEDICAL                                   |
      | planEndDateReason       | PLAN_HAS_TERMINATED                       |
      | anniversaryDate         | anniversaryDate                           |
      | serviceRegionCode       | East                                      |
#      | lockInStartDate         | RB4.enrollments.[0].lockInStartDate       |
#      | lockInEndDate           | RB4.enrollments.[0].lockInEndDate         |
#      | lockInStatusCode        | RB4.enrollments.[0].lockInStatusCode      |
#      | lockInExemptionReason   | RB4.enrollments.[0].lockInExemptionReason |
      # @API-CP-8779-02 and @API-CP-10283-02
      | channel                 | SYSTEM_INTEGRATION                        |
      # @API-CP-8779-04
      | txnStatus               | DISENROLLED                               |
      # @API-CP-8779-05 and @API-CP-10283-04
      | updatedOn               | current                                   |
      | updatedBy               | 597                                       |
    # @API-CP-8779-01 1.0 Create or Update Data - MMIS sends Enrollment Change Scenario - Requested Segment
    # @API-CP-10283-01 1.0 Create or Update Data - MMIS Sends Enrollment Renewal Scenario - Requested Segment
    And I verify latest enrollment by consumer id "RB4.consumerId" with data
      # @API-CP-8779-03
      | txnStatus            | ACCEPTED          |
      # @API-CP-8779-06 and @API-CP-10283-05
      | updatedOn            | current           |
      | updatedBy            | 597               |
      | medicalPlanBeginDate | 1stDayofNextMonth |
      | planCode             | 85                |
      | medicalPlanEndDate   | null              |
    And I initiated get benefit status by consumer id "RB4.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Plan Change"
    And Wait for 20 seconds
    # @API-CP-8779-07 and @API-CP-10283-06 - Prior Segment
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | RB4.consumerId          |
      | consumerFound | true                    |
      | recordId      | 4                       |
    # @API-CP-8779-08 and @API-CP-10283-07 2.0 - Requested Segment
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | RB4.consumerId        |
      | consumerFound | true                  |
      | recordId      | 8                     |
    # API-CP-16189 Updates to Capture business events: User, Consumer Initiated Plan Change
    Then I will verify business events are generated with data
      #  @API-CP-16189-01
      | eventName                              | PLAN_CHANGE                  |
      #  @API-CP-16189-02
      | channel                                | SYSTEM_INTEGRATION           |
      | createdBy                              | 597                          |
      | processDate                            | current                      |
      | externalCaseId                         | RB4.caseIdentificationNumber |
      | externalConsumerId                     | RB4.externalConsumerId       |
      | consumerId                             | RB4.consumerId               |
      | consumerName                           | RB4                          |
      #  @API-CP-16189-03
      | consumerPopulation                     | MEDICAID-GENERAL             |
      | benefitStatus                          | Enrolled                     |
      #  @API-CP-16189-04
      | previousEnrollment.enrollmentStartDate | 1stDayofLastMonth            |
      | previousEnrollment.enrollmentEndDate   | lastDayofPresentMonth        |
      | previousEnrollment.planChangeReason    | PLAN_HAS_TERMINATED          |
      | previousEnrollment.rejectionReason     | null                         |
      | previousEnrollment.planCode            | 84                           |
      | previousEnrollment.enrollmentType      | MEDICAL                      |
      | previousEnrollment.selectionStatus     | DISENROLLED                  |
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth            |
      | requestedSelection.enrollmentEndDate   | null                         |
      | requestedSelection.planChangeReason    | null                         |
      | requestedSelection.rejectionReason     | null                         |
      | requestedSelection.planCode            | 85                           |
      | requestedSelection.enrollmentType      | MEDICAL                      |
      | requestedSelection.selectionStatus     | ACCEPTED                     |
