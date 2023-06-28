Feature: API: Identify Benefit Status for Current & Future Timeframe

  @API-CP-8372 @API-CP-8372-01  @API-CP-10714-05 @API-CP-4514 @API-EE  @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status is Eligible;Current Enrollment= Medical, Plan =accepted ,PCP= not null, Future Enrollment= dental, Plan =accepted , PCP=not null
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
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"

  @API-CP-8372 @API-CP-8372-02 @API-CP-9832-02 @API-CP-4513 #@API-EE  @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status is Not Found;Current Enrollment= Medical, Plan =null,PCP=null, Future Enrollment= Dental, Plan =accepted , PCP=not null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | futureDate                                               |
      | eligibilityEndDate           | futureDate                                               |
      | enrollmentEndDate            | futureDate                                               |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Found"


  @API-CP-8372 @API-CP-8372-03 #@API-EE  @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status is Not Found;Current Enrollment= Behavioral, Plan =accepted , PCP=null, Future Enrollment= Medical, Plan =null , PCP=null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    When I create eligibility record for bpm process with "future" timeframe
    Given I initiated selection transaction API
    When I provide selection transaction details as below:
      | EnrollmentType | BEHAVIORAL |
      | PlanStatus     | accepted   |
      | TimeFrame      | current    |
    And I run selection transaction API
    Given I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "BEHAVIORAL", "", "" and "false"
    And I run create enrollment API
    Given I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "MEDICAL", "future", "future" and "false"
    And I run create enrollment API
    And I initiated BPM Process for the consumer ""
    When I start the BPM process
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Found"


  @API-CP-8372 @API-CP-8372-04 #@API-EE-REMOVED @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status is Not Found;Current Enrollment= Behavioral, Plan =null,PCP=null, Future Enrollment= Behavioral, Plan =accepted , PCP=not null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    When I create eligibility record for bpm process with "future" timeframe
    Given I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "BEHAVIORAL", "past", "future" and "false"
    And I run create enrollment API
#    Given I initiated selection transaction API
#    When I provide selection transaction details for "<Plan Status>" and enrollment "future"
#    When I provide selection transaction details as below:
#      |EnrollmentType |  BEHAVIORAL  |
#      |PlanStatus     |accepted      |
#      |TimeFrame      |future       |
#    And I run selection transaction API
    Given I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "BEHAVIORAL", "", "" and "false"
    And I run create enrollment API
    And I initiated BPM Process for the consumer ""
    When I start the BPM process
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Found"


  @API-CP-8372 @API-CP-8372-05 #@API-EE-REMOVED @shruti
  Scenario: Verify Benefit Status =Eligible;CurrentEnrollment =Medical,Plan= & PCP not null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | past                                                     |
      | eligibilityStartDate         | 1stdayOfNextMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated BPM Process for the consumer ""
    When I start the BPM process
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"

#failed due to Enrollment failure
  @API-CP-8372 @API-CP-8372-06 @API-EE  @shruti
  Scenario: Verify Benefit Status Eligible ,Enrollment Type =Medical,Only current Time frame and Plan= accepted & PCP= null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | past                                                     |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | txnStatus                    | null                                                     |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"

  @API-CP-8372 @API-CP-8372-08 @API-CP-73-08=1 #@API-EE-REMOVED @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status- Eligible;Enrollment Type =Behavioral,Only Future Time frame and Plan & PCP  null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 0                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | past                                                     |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | eligibilityEndDate           | future                                                   |
      | enrollmentEndDate            | past                                                     |
      | txnStatus                    | null                                                     |
      | programCode                  | M                                                        |
      | enrollmentType               | BEHAVIORAL                                               |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"

  @API-CP-8372 @API-CP-8372-07 #@API-EE-REMOVED  @shruti
  Scenario: Verify Benefit Status=Not Eligible; Enrollment Type =Behavioral,Only Future Time frame and Plan & PCP not null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    When I create eligibility record for bpm process with "future" timeframe
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 100                                                      |
      | isEnrollmentProviderRequired | no                                                       |
      | providerType                 | MEDICAL                                                  |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | eligibilityEndDate           | 2020-01-22T16:38:53.053Z                                 |
      | enrollmentEndDate            | future                                                   |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
      | enrollmentType               | MEDICAL                                                  |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Eligible"


  @API-CP-8372 @API-CP-8372-09 #@API-EE-REMOVED  @shruti
  Scenario: Verify Benefit Status- Not Eligible; Enrollment Type =Behavioral,Only Future Time frame and Plan -denied& PCP not null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 100                                                      |
      | isEnrollmentProviderRequired | yes                                                      |
      | providerType                 | BEHAVIORAL                                               |
      | enrollmentStartDate          | futureDate                                               |
      | eligibilityStartDate         | futureDate                                               |
      | eligibilityEndDate           | futureDate                                               |
      | enrollmentEndDate            | futureDate                                               |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
      | enrollmentType               | BEHAVIORAL                                               |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Eligible"

  @API-CP-8372 @API-CP-8372-11 #@API-EE-REMOVED  @eligibility-enrollment-ms-EE @shruti
  Scenario: B.Status =Not Eligible ; Current = null; Future =dental Plan & PCP not null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    When I create eligibility record for bpm process with "future" timeframe
    Given I initiated Eligibility and Enrollment Create API
    Given I initiated selection transaction API
    When I provide selection transaction details for "<Plan Status>" and enrollment "future"
    When I provide selection transaction details as below:
      | EnrollmentType | DENTAL   |
      | PlanStatus     | accepted |
      | TimeFrame      | future   |
    And I run selection transaction API
    Given I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "DENTAL", "", "" and "true"
    And I run create enrollment API
    And I initiated BPM Process for the consumer ""
    When I start the BPM process
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Eligible"


  @API-CP-8372 @API-CP-8372-10 #@API-CRM-Regression @API-EE-REMOVED  @shruti
  Scenario: B.Status =Enrolled ; Current = Non medical,Plan & PCP not null; Future =medical Plan & PCP not null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | yes               |
      | categoryCode                 | null              |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | enrollmentType               | DENTAL            |
    And I run create Eligibility and Enrollment API
    And I initiated BPM Process for the consumer ""
    When I start the BPM process
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"


  @API-CP-8372 @API-CP-8372-12 #@API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: B.Status =Enrolled ; Current = null; Future =medical end date =null; Plan & PCP not null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | yes               |
      | categoryCode                 | null              |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityEndDate           | future            |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated BPM Process for the consumer ""
    When I start the BPM process
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"


  @API-CP-8372 @API-CP-8372-13 #@API-CRM-Regression @API-EE-REMOVED  @shruti
  Scenario: B.Status =Enrolled ; Current = null; Future =medical end date =null; Plan =denied& PCP not null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | yes               |
      | categoryCode                 | null              |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | H                 |
      | providerType                 | MEDICAL           |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"


  @API-CP-10714 @API-CP-10714-01 #@API-EE-REMOVED @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status = Enrolled Current Time frame
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | yes                  |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramCode               | MEDICIAIDMCHB        |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | yes                  |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramCode               | MEDICIAIDMCHB        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"

  @API-CP-10714 @API-CP-10714-02 #@API-EE-REMOVED @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status = Eligible Current Time frame
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | yes                  |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"
#extra
  @API-CP-10714 @API-CP-10714-03 #@API-EE-REMOVED @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify Benefit Status = Not Found Current Time frame
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 0                    |
      | isEnrollmentProviderRequired | no                   |
      | categoryCode                 | null                 |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Found"


  @API-CP-10557 @API-CP-10557-01.01
  Scenario: Verify Benefit Status is Eligible, Future ,New Eligibility , No Enrollment , Program Code = MEDICAID
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
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"

  @API-CP-10557 @API-CP-10557-01.02
  Scenario: Verify Benefit Status is Eligible,Future New Eligibility , No Enrollment , Program Code = CHIP
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
      | txnStatus                    | Accepted                                                 |
      | programCode                  | P                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"

  @API-CP-10557 @API-CP-10557-01.03
  Scenario: Verify Benefit Status is Eligible,Current, Retroactive Eligibility , No Enrollment , Program Code = CHIP
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | past                                                     |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | P                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"

  @API-CP-10557 @API-CP-10557-01.04
  Scenario: Verify Benefit Status is Eligible,Current, Retroactive Eligibility , No Enrollment , Program Code = MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | past                                                     |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"

  @API-CP-10557 @API-CP-10557-01.05
  Scenario: Verify Benefit Status is Eligible , Future ,New Eligibility , Non Medical Enrollment , Program Code = MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    And I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "MEDICAL", "1stDayofNextMonth", "null" and "false"
    And I run create enrollment API
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"

  @API-CP-10557 @API-CP-10557-01.06
  Scenario: Verify Benefit Status is Eligible , Future ,New Eligibility , Non Medical Enrollment-behavioral , Program Code = MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
#    And I initiated Enrollment Create API
#    When I provide information to Enrollment Create API for "DENTAL", "1stDayofNextMonth", "null" and "false"
#    And I run create enrollment API
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 0                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | enrollmentType               | DENTAL                                                   |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"


  @API-CP-10557 @API-CP-10557-01.07
  Scenario: Verify Benefit Status is Eligible , Future ,New Eligibility , Dental Enrollment, Program Code = F, Medicaid, Foster care
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "FOSTER-CARE"
#    And I initiated Enrollment Create API
#    When I provide information to Enrollment Create API for "DENTAL", "1stDayofNextMonth", "null" and "false"
#    And I run create enrollment API
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | enrollmentType               | DENTAL            |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Eligible"


  @API-CP-10557 @API-CP-10557-02.01
  Scenario: Verify Benefit Status is Partially Enrolled, Future ,New Eligibility , Only Medical Enrollment , Program Code = MEDICAID, Newborn
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
      | enrollmentType               | MEDICAL                                                  |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Partially Enrolled"

  @API-CP-10557 @API-CP-10557-02.02
  Scenario: Verify Benefit Status is partially enrolled, Future ,New Eligibility , Dental Enrollment , Program Code = MEDICAID, Newborn
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
      | enrollmentType               | DENTAL                                                   |
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
      | programCode                  | M                                                        |
      | enrollmentType               | DENTAL                                                   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Partially Enrolled"

  @API-CP-10557 @API-CP-10557-02.03
  Scenario: Verify Benefit Status is partially enrolled, Future ,New Eligibility , Non Medical Enrollment , Program Code = MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
      | enrollmentType               | DENTAL                                                   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 2                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
      | enrollmentType               | DENTAL                                                   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Partially Enrolled"

  @API-CP-10557 @API-CP-10557-03.01
  Scenario: Verify Benefit Status is  enrolled, Future ,New Eligibility , Medical Enrollment , Program Code = MEDICAID,  Pregnant-Women
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
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
      | enrollmentType               | MEDICAL                                                  |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"

  @API-CP-10557 @API-CP-10557-03.02
  Scenario: Verify Benefit Status is  enrolled, Future ,New Eligibility , Medical Enrollment , Program Code = CHIP, Medicaid general
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
      | enrollmentType               | MEDICAL           |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"

  @API-CP-10557 @API-CP-10557-03.03
  Scenario: Verify Benefit Status is  enrolled, Current ,Retroactive Eligibility , Medical Enrollment , Program Code = MEDICAID, Medicaid general
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
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | enrollmentType               | MEDICAL           |
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
      | enrollmentType               | MEDICAL           |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"

  @API-CP-10557 @API-CP-10557-03.04
  Scenario: Verify Benefit Status is  enrolled, Current ,Retroactive Eligibility , Medical Enrollment , Program Code = CHIP, Prego
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    And I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "MEDICAL", "past", "null" and "false"
    And I provide more information for create enrollment API
      | subProgramTypeCode | MEDICAIDGF |
      | programTypeCode    | MEDICAID   |
      | planCode           | 125        |
      | txnStatus          | Pending    |
    And I run create enrollment API
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
      | programCode                  | P                 |
      | enrollmentType               | MEDICAL           |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"
#re-check population = nOne
  @API-CP-10557 @API-CP-10557-03.05
  Scenario: Verify Benefit Status is  enrolled, Future ,New Eligibility , Medical Enrollment , Program Code = CHIP, NewBorn
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
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
      | enrollmentType               | MEDICAL           |
      | programTypeCode              | CHIP              |
      | planCode                     | 95                |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"

  @API-CP-10557 @API-CP-10557-03.06
  Scenario: Verify Benefit Status is  enrolled, Future ,New Eligibility , Medical& Dental Enrollment , Program Code = MEDICAID, NewBorn
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    And I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "DENTAL", "1stDayofNextMonth", "null" and "false"
    And I provide more information for create enrollment API
      | subProgramTypeCode | MEDICAIDGF |
      | programTypeCode    | MEDICAID   |
      | txnStatus          | Accepted   |
    And I run create enrollment API
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
      | programCode                  | M                 |
      | enrollmentType               | MEDICAL           |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"

  @API-CP-10557 @API-CP-10557-03.07
  Scenario: Verify Benefit Status is  enrolled, current ,Retroactive Eligibility , Medical& Dental Enrollment , Program Code = MEDICAID, NewBorn
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    And I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "DENTAL", "1stDayofLastMonth", "null" and "false"
    And I run create enrollment API
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
      | enrollmentType               | MEDICAL           |
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
      | enrollmentType               | MEDICAL           |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"

  @API-CP-10557 @API-CP-10557-03.08
  Scenario: Verify Benefit Status is  enrolled, current ,Retroactive Eligibility , Medical& Dental Enrollment , Program Code = CHIP, NewBorn
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    And I initiated Enrollment Create API
    When I provide information to Enrollment Create API for "DENTAL", "1stDayofLastMonth", "null" and "false"
    And I provide more information for create enrollment API
      | subProgramTypeCode | PEACHCAREGF |
      | programTypeCode    | CHIP        |
      | planCode           | 90          |
      | txnStatus          | Accepted    |
    And I run create enrollment API
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
      | programCode                  | P                 |
      | enrollmentType               | MEDICAL           |
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
      | enrollmentType               | MEDICAL           |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Enrolled"


  @API-CP-10557 @API-CP-10557-04.01
  Scenario: Verify Benefit Status is Not Eligible,Future New Eligibility , Eligibility End Reason & End Date , Program Code = CHIP
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
      | eligibilityStartDate         | future                                                   |
      | eligibilityEndDate           | currentPlusOne                                           |
      | eligibilityEndReason         | SampleEndReason                                          |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | P                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Eligible"

  @API-CP-10557 @API-CP-10557-04.02
  Scenario: Verify Benefit Status is Not Eligible,Future New Eligibility , Eligibility End Reason & End Date , Program Code = MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | past                                                     |
      | eligibilityStartDate         | future                                                   |
      | eligibilityEndDate           | futureOneDayLessThanCurrent                              |
      | eligibilityEndReason         |[blank]|
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Eligible"

  @API-CP-10557 @API-CP-10557-04.03
  Scenario: Verify Benefit Status is Not Eligible,Current New Eligibility , Eligibility End Reason & End Date , Program Code = CHIP
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | past                                                     |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
      | eligibilityEndDate           | current                                                  |
      | eligibilityEndReason         | SampleEndReason                                          |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | P                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Eligible"

  @API-CP-10557 @API-CP-10557-04.04
  Scenario: Verify Benefit Status is Not Eligible,current  New Eligibility , Eligibility End Reason & End Date , Program Code = MEDICAID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | past                                                     |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
      | eligibilityEndDate           | futureOneDayLessThanCurrent                              |
      | eligibilityEndReason         |[blank]|
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with benefit status "Not Eligible"







