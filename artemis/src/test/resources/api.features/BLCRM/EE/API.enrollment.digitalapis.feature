Feature: API: EE digital API's

  @API-CP-9043 @API-CP-9043-01 @API-CP-42764 @shruti @API-EE @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario: Enrollment Provider Digital are displayed for Future & Current
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 9043-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9043-01.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | eligibilityRecordId          | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
#      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | MEDICAIDGF         |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "9043-01a" for update Enrollment information
      | [0].consumerId         | 9043-01.consumerId |
      | [0].planId             | 145                |
      | [0].planCode           | random             |
      | [0].startDate          | fdofmnth::         |
#      | [0].endDate            | highdatedc::       |
      | [0].programTypeCode    | M                  |
      | [0].subProgramTypeCode | MEDICAIDGF         |
      | [0].serviceRegionCode  | Atlanta            |
      | enrollmentProviders    | random             |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "9043-01a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 84                     |
      | [0].consumerId   | 9043-01.consumerId     |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9043-01.consumerId   |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         |[blank]|
      | anniversaryDate              | anniversaryDate      |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | subProgramTypeCode           | MEDICAIDGF           |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | Atlanta              |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | saveDetailsWithName                           | 9043-01-planTransferDetails                    |
      | [0].consumerId                                | 9043-01.consumerId                             |
      | [0].planId                                    | 26                                             |
#      | existingPlanCode                              | 9043-01a-enrollmentDetails.planCode            |
      | [0].planCode                                  | 85                                             |
      | [0].startDate                                 | fdnxtmth::                                     |
#      | [0].endDate                                   | highdatedc::                                   |
      | [0].subProgramTypeCode                        | MEDICAIDGF                                     |
      | [0].serviceRegionCode                         | Atlanta                                        |
      | [0].selectionReason                           | 02                                             |
      | [0].anniversaryDate                           | anniversaryDate::                              |
      | [0].channel                                   | SYSTEM_INTEGRATION                             |
      | enrollmentProviders                           | random                                         |
      | [0].enrollmentProviders[0].consumerId         | 9043-01.consumerId                             |
      | [0].enrollmentProviders[0].providerFirstName  | 9043-01-planTransferDetails.providerFirstName  |
      | [0].enrollmentProviders[0].providerMiddleName | 9043-01-planTransferDetails.providerMiddleName |
      | [0].enrollmentProviders[0].providerLastName   | 9043-01-planTransferDetails.providerLastName   |
      | [0].enrollmentProviders[0].providerNpi        | 9043-01-planTransferDetails.providerNpi        |
      | [0].enrollmentProviders[0].providerType       | 9043-01-planTransferDetails.providerType       |
    Given I initiated get digital enrollment provider by consumer ids ""
    When I run get digital enrollment provider API
    Then I verify digital enrollment provider details for "currentEnrollment"
      | providerId | 9043-01a.[0].enrollmentProviders[0].providerId |
    Then I verify digital enrollment provider details for "futureEnrollment"
      | providerId | 9043-01-planTransferDetails.providerId |

#Removed as both current and Future are covered in above scenario
  @API-CP-9043 @API-CP-9043-02 @shruti @API-EE-removed @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario: Enrollment Provider Digital are displayed for Future
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 9043-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9043-02.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | eligibilityRecordId          | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
#      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | MEDICAIDGF         |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9043-02.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | recordId                     | 4                  |
      | eligibilityRecordId          | 4                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
#      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | MEDICAIDGF         |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | saveDetailsWithName                           | 9043-02-planTransferDetails                    |
      | [0].consumerId                                | 9043-02.consumerId                             |
      | [0].planId                                    | 26                                             |
#      | existingPlanCode                              | 9043-01a-enrollmentDetails.planCode            |
      | [0].planCode                                  | 85                                             |
      | [0].startDate                                 | fdnxtmth::                                     |
#      | [0].endDate                                   | highdatedc::                                   |
      | [0].subProgramTypeCode                        | MEDICAIDGF                                     |
      | [0].serviceRegionCode                         | Atlanta                                        |
      | [0].selectionReason                           | 02                                             |
      | [0].anniversaryDate                           | anniversaryDate::                              |
      | [0].channel                                   | SYSTEM_INTEGRATION                             |
      | enrollmentProviders                           | random                                         |
      | [0].enrollmentProviders[0].consumerId         | 9043-02.consumerId                             |
      | [0].enrollmentProviders[0].providerFirstName  | 9043-02-planTransferDetails.providerFirstName  |
      | [0].enrollmentProviders[0].providerMiddleName | 9043-02-planTransferDetails.providerMiddleName |
      | [0].enrollmentProviders[0].providerLastName   | 9043-02-planTransferDetails.providerLastName   |
      | [0].enrollmentProviders[0].providerNpi        | 9043-02-planTransferDetails.providerNpi        |
      | [0].enrollmentProviders[0].providerType       | 9043-02-planTransferDetails.providerType       |
    Given I initiated get digital enrollment provider by consumer ids ""
    When I run get digital enrollment provider API
    Then I verify digital enrollment provider details for "futureEnrollment"

  @API-CP-9040 @CP-9040-01 @shruti @API-EE @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario: View Enrollment Status  Digital API-2
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
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
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
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    Given I initiated get digital enrollment status by consumer ids ""
    When I run get digital enrollment status API
    Then I verify digital enrollment status details
      | consumerIds    |[blank]|
      | benefitStatus  | Enrolled               |
      | startDate      | 1stDayofLastMonth      |
      | consumerAction | Plan Change Pre-lockin |
      | eligStatusCode | Mandatory              |

  @API-CP-9042 @API-CP-9042-01 @API-CP-13764 @API-CP-39979 @API-CP-39979-01 @API-CP-13764-01 @shruti @API-EE @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario: View Enrollment Plans Digital API-3, View Plan Code Instead of Plan ID
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
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
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
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    Given I initiated get digital enrollment plans by consumer ids ""
    When I run get digital enrollment plans API
    Then I verify digital enrollment plans details
      | consumerIds | timeframe |
      |[blank]| Current   |

  @API-CP-9042 @API-CP-9042-02 @shruti @API-EE
  Scenario: View Enrollment Plans Digital API-4
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "PREGNANT-WOMEN"
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
      | programCode                  | H                 |
      | subprogramTypeCode           | MEDICAIDHCMB      |
      | planId                       | 1880              |
      | planCode                     | 84                |
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
      | programCode                  | H                 |
      | planId                       | 1880              |
      | planCode                     | 84                |
    And I run create Eligibility and Enrollment API
#    And I initiated Enrollment Create API
#    When I provide information to Enrollment Create API for "MEDICAL", "future", "Null" and "true"
#    And I run create enrollment API
    Given I initiated get digital enrollment plans by consumer ids ""
    When I run get digital enrollment plans API
    Then I verify digital enrollment plans details
      | consumerIds | timeframe |
      |[blank]| Future    |

  @API-CP-9046 @API-CP-9046-01 @shruti @API-EE @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario: View Eligible Plans Digital API
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 9046-01 |
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9046-01.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | yes                |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         | 1stDayofLastMonth  |
      | txnStatus                    | Accepted           |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | programCode                  | H                  |
      | anniversaryDate              | anniversaryDate    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9046-01.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | recordId                     | 4                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofLastMonth  |
      | eligibilityStartDate         |[blank]|
      | enrollmentEndDate            | future             |
      | txnStatus                    | Accepted           |
      | subProgramTypeCode           | MEDICAIDMCHB       |
      | programCode                  | H                  |
      | planId                       | 1880               |
      | planCode                     | 84                 |
    And I run create Eligibility and Enrollment API
    Given I initiated get consumer eligible plans for consumer "9046-01.consumerId"
    When I run get consumer eligible plans API
    Then I verify consumer eligible plans for consumer "9046-01.consumerId"

  @API-CP-9047 @API-CP-9047-01 @shruti @API-EE
  Scenario: Plan Transfer API -verify sucess
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "NEWBORN"
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
      | programCode                  | M                 |
      | planId                       | 1881              |
      | planCode                     | 85                |
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
      | programCode                  | M                 |
      | planId                       | 1880              |
      | planCode                     | 84                |
    And I run create Eligibility and Enrollment API


  @API-CP-9047 @API-CP-9047-02 @shruti @API-EE
  Scenario: Plan Transfer API- Validate error is displayed for invalid providerID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "MEDICAID-GENERAL"
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
      | programCode                  | M                 |
      | planId                       | 1881              |
      | planCode                     | 85                |
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
      | programCode                  | M                 |
      | planId                       | 1880              |
      | planCode                     | 84                |
    And I run create Eligibility and Enrollment API
#    When I create eligibility record for bpm process with "1stDayofNextMonth" timeframe
#    And I initiated Enrollment Create API
#    When I provide information to Enrollment Create API for "MEDICAL", "past", "future" and "true"
#    And I run create enrollment API
    Given I initiated digital plan transfer API
    When I provide details for digital plan transfer API "84", "PlanChangeSampleTest", "1072332322", ""
    And I run digital plan transfer API with expected status "fail"
    Then I validate error message "Given Provider id doesn't contain provider details" in digital plan transfer api response

  @API-CP-9047 @API-CP-9047-03 @shruti @API-EE
  Scenario: Plan Transfer API- Validate error is displayed for invalid planID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "MEDICAID-GENERAL"
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
      | programCode                  | M                 |
      | planId                       | 1881              |
      | planCode                     | 85                |
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
      | programCode                  | M                 |
      | planId                       | 1880              |
      | planCode                     | 84                |
    And I run create Eligibility and Enrollment API
    Given I initiated digital plan transfer API
    When I provide details for digital plan transfer API "82323234", "PlanChangeSampleTest", "1072", ""
    And I run digital plan transfer API with expected status "fail"
    Then I validate error message "Plan id is not unique for given Plan Code or Plan code doesn't exist" in digital plan transfer api response

  @API-CP-9047 @API-CP-9047-04 @shruti @API-EE
  Scenario: Plan Transfer API- Validate error is displayed for invalid consumer id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated digital plan transfer API
    When I provide details for digital plan transfer API "84", "PlanChangeSampleTest", "1072", "12345678"
    And I run digital plan transfer API with expected status "fail"
    Then I validate error message "Consumer does not have any Current Enrollment" in digital plan transfer api response

  @API-CP-9047 @API-CP-9047-05 @shruti @API-EE
  Scenario: Plan Transfer - Enrollment details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "NEWBORN"
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planId                       | 3146              |
      | planCode                     | 85                |
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
      | programCode                  | M                 |
      | planCode                     | 85                |
    And I run create Eligibility and Enrollment API
#    When I create eligibility record for bpm process with "1stDayofNextMonth" timeframe
#    And I initiated Enrollment Create API
#    When I provide information to Enrollment Create API for "MEDICAL", "past", "future" and "true"
#    And I run create enrollment API
    Given I initiated digital plan transfer API
    When I provide details for digital plan transfer API "84", "PlanChangeSampleTest", "3", ""
    And I run digital plan transfer API with expected status "success"
    When I initaite get enrollments for consumerID ""
    And I run get enrollments for consumer with expected status "success"
    Then I verify previous enrollment is ended with current date and with end reason "PlanChangeSampleTest"
    And I verify present enrollment details

  @API-CP-9486 @API-CP-9486-01 @shruti @API-EE
  Scenario: New Enrollment plan change - Verifies Plans displayed and Eligibility Segment Start Date & End Date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated get eligible and enrollment plans by consumer ids "2089"
    And I run get eligible and enrollment plans API
    Then I verify eligible and enrollment plans details
      | planName                  |
      | AMERIGROUP COMMUNITY CARE |
      | CARESOURCE GEORGIA        |
      | PEACH STATE               |
      | WELLCARE                  |


  @API-CP-13764 @API-CP-13764-06 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Plan Id variable Plan COde
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 85                |
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
      | programCode                  | M                 |
      | planCode                     | 85                |
    And I run create Eligibility and Enrollment API
    Given I initiated get digital enrollment plans by consumer ids ""
    When I run get digital enrollment plans API
    Then I verify digital enrollment plans details
      | consumerIds | timeframe |
      |[blank]| Current   |


  @API-CP-13764 @API-CP-13764-02 @shruti @API-EE @eligibility-enrollment-ms-EE @API-CRM-Regression
  Scenario: View Eligible Plans Digital API- ChangePlan ID to Plan Code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
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
      | planCode                     | 85                |
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
      | programCode                  | H                 |
      | subProgramTypeCode           | MEDICAIDMCHB      |
      | planCode                     | 85                |
    And I run create Eligibility and Enrollment API
    Given I initiated get consumer eligible plans for consumer ""
    When I run get consumer eligible plans API
    Then I verify consumer eligible plans for consumer ""

  @API-CP-14612 @API-CP-8780 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario Outline: MMIS Sends New Enrollment Accept Response for system processing
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
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "EI" for update Enrollment information
      | [0].consumerId      | <name>.consumerId |
      | [0].planId          | 145               |
      | [0].planCode        | 84                |
      | [0].startDate       | fdnxtmth::        |
      | [0].programTypeCode | M                 |
    And I send API call with name "PO" for process Outbound Update
      | [0].planId       | delete::               |
      | [0].planCode     | 84                     |
      | [0].consumerId   | <name>.consumerId      |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].startDate    | fdnxtmth::             |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | isEnrollmentProviderRequired | no                |
      | recordId                     | 15                |
      | enrollmentStartDate          | <startDate>       |
      | anniversaryDate              | anniversaryDate   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | <planCode>        |
      | planId                       | 145               |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","<enrollmentScenario>"
    Examples: I will decide the enrollment scenario is MMIS Sends "New Enrollment Accept" or "Not Found" Responses
      | name    | startDate         | planCode | enrollmentScenario    |
      | 14612-1 | 1stDayofNextMonth | 84       | New Enrollment Accept |
      | 14612-2 | current           | 84       | Not Found             |
      | 14612-3 | 1stDayofNextMonth | 85       | Not Found             |

  @API-CP-19116 @API-CP-17593 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: CP-19116 planTransfer API to include contactRecordId and channel as *optional*
  CP-17593 API - POST Plan Transfer for Multiple Consumers
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
    Then I send API call with name "RA1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA1.consumerId       |
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
    Then I send API call with name "RA2" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA2.consumerId       |
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
    Given I created a consumer with population type "MEDICAID-GENERAL"
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
    Then I send API call with name "RA3" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA3.consumerId       |
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
    When I run Digital Plan Transfer API with or without "with" random contactRecordId and channel "SYSTEM_INTEGRATION" with data
#    Map<String, List<String>> please use this sequence:
#   | Map key consumerID | disenrollmentReason | planCode | planProviderID | consumerAction |
      | RA1.consumerId | Reason1 | 85 | null | Enroll |
      | RA2.consumerId | Reason1 | 85 | null | Enroll |
      | RA3.consumerId | Reason1 | 85 | null | Enroll |
    Then I verify digital enrollment plans details with data
      | [0].consumerId    | RA1.consumerId    |
      | [0].effectiveDate | 1stDayofNextMonth |
      | [0].message       | SELECTION_MADE    |
      | [0].successInd    | Y                 |
      | [1].consumerId    | RA2.consumerId    |
      | [1].effectiveDate | 1stDayofNextMonth |
      | [1].message       | SELECTION_MADE    |
      | [1].successInd    | Y                 |
      | [2].consumerId    | RA3.consumerId    |
      | [2].effectiveDate | 1stDayofNextMonth |
      | [2].message       | SELECTION_MADE    |
      | [2].successInd    | Y                 |

  @API-CP-17591 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @sobir
  Scenario: API - GET Available Plans for Multiple Consumers
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
    Then I send API call with name "RA4" for create Eligibility and Enrollment
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
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "RA5" for create Eligibility and Enrollment
    Given  I created a consumer with population type "NEWBORN"
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
    Then I send API call with name "RA6" for create Eligibility and Enrollment
    When I initiated get consumer eligible plans for consumer "RA4.consumerId,RA5.consumerId,RA6.consumerId"
    And I run get eligible and enrollment plans API
    Then I verify consumer eligible plans for consumer "RA4.consumerId,RA5.consumerId,RA6.consumerId"

  @API-CP-38998 @API-CP-38998-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: CP-38998 planTransfer API to include selected providers multiple providers
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | RA1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA1.consumerId       |
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
    Then I send API call with name "RA1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA1.consumerId       |
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
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | RA2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA2.consumerId       |
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
    Then I send API call with name "RA2" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA2.consumerId       |
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
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | RA3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA3.consumerId       |
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
    Then I send API call with name "RA3" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA3.consumerId       |
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
    When I run Digital Plan Transfer API with or without "with" random contactRecordId and channel "SYSTEM_INTEGRATION" with data
#    Map<String, List<String>> please use this sequence:
#   | Map key consumerID | disenrollmentReason | planCode | planProviderID | consumerAction |
      | RA1.consumerId | Reason1 | 85 | 164,165 | Plan Change Prelockin |
      | RA2.consumerId | Reason1 | 85 | null    | Plan Change Prelockin |
      | RA3.consumerId | Reason1 | 85 | 170,171 | Plan Change Prelockin |
    Then I verify digital enrollment plans details with data
      | [0].consumerId    | RA1.consumerId    |
      | [0].effectiveDate | 1stDayofNextMonth |
      | [0].message       | SELECTION_MADE    |
      | [0].successInd    | Y                 |
      | [1].consumerId    | RA2.consumerId    |
      | [1].effectiveDate | 1stDayofNextMonth |
      | [1].message       | SELECTION_MADE    |
      | [1].successInd    | Y                 |
      | [2].consumerId    | RA3.consumerId    |
      | [2].effectiveDate | 1stDayofNextMonth |
      | [2].message       | SELECTION_MADE    |
      | [2].successInd    | Y                 |




