Feature: API: Identify Population type for INEB
#Removing Regression Tag
  @API-CP-22234 @API-CP-22234-01 @API-CP-22296 #@API-CP-23589  @API-EE-IN @IN-EB-API-Regression
  Scenario Outline: Verify Population & Benefit Status =Enrolled for HIP,HHW,HCC M,V&X (positive)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 3                       |
      | eligibilityRecordId          | 3                       |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | genericFieldText1            | Eligible                |
      | categoryCode                 | 10                      |
    And I run create Eligibility and Enrollment API
    And Wait for 10 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate                |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | 300119960               |
      | anniversaryDate              | anniversaryDate         |
    And I run create Eligibility and Enrollment API
    And Wait for 10 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with population "<programpopulation>"
    And I verify benefit status records are displayed with benefit status "<benefitStatus>"

    Examples:
      | name     | programCode | subProgramCode     | eligibilityStatusCode | programpopulation | benefitStatus |
      | 22234-01 | H           | HealthyIndianaPlan | M                     | HIP-Mandatory     | Enrolled      |
      | 22234-02 | H           | HealthyIndianaPlan | V                     | HIP-Voluntary     | Enrolled      |
      | 22234-03 | H           | HealthyIndianaPlan | X                     | HIP-Mandatory     | Enrolled      |
      | 22234-04 | R           | HoosierHealthwise  | M                     | HHW-Mandatory     | Enrolled      |
      | 22234-05 | R           | HoosierHealthwise  | V                     | HHW-Voluntary     | Enrolled      |
      | 22234-06 | R           | HoosierHealthwise  | X                     | HHW-Mandatory     | Enrolled      |
      | 22234-07 | A           | HoosierCareConnect | M                     | HCC-Mandatory     | Enrolled      |
      | 22234-08 | A           | HoosierCareConnect | V                     | HCC-Voluntary     | Enrolled      |
      | 22234-09 | A           | HoosierCareConnect | X                     | HCC-Mandatory     | Enrolled      |
      | 22234-10 | A           | HealthyIndianaPlan | M                     | NONE              | null          |
      | 22234-11 | A           | HealthyIndianaPlan | V                     | NONE              | null          |
      | 22234-12 | A           | HealthyIndianaPlan | X                     | NONE              | null          |
      | 22234-13 | A           | HoosierHealthwise  | M                     | NONE              | null          |
      | 22234-14 | A           | HoosierHealthwise  | V                     | NONE              | null          |
      | 22234-15 | A           | HoosierHealthwise  | X                     | NONE              | null          |
      | 22234-16 | R           | HoosierCareConnect | M                     | NONE              | null          |
      | 22234-17 | R           | HoosierCareConnect | V                     | NONE              | null          |
      | 22234-18 | R           | HoosierCareConnect | X                     | NONE              | null          |


  @API-CP-22234-02 # @API-EE-IN @IN-EB-API-Regression
  Scenario Outline: Verify Population & Benefit Status =Eligible for HHW,HCC M,V&X
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 3                       |
      | eligibilityRecordId          | 3                       |
      | isEnrollmentProviderRequired | yes                     |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | 10                      |
    Then I send API call with name "<name>" for create Eligibility and Enrollment
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with population "<programpopulation>"
    And I verify benefit status records are displayed with benefit status "<benefitStatus>"

    Examples:
      | name     | programCode | subProgramCode     | eligibilityStatusCode | programpopulation | benefitStatus |
      | 22234-19 | R           | HoosierHealthwise  | M                     | HHW-Mandatory     | Eligible      |
      | 22234-20 | R           | HoosierHealthwise  | V                     | HHW-Voluntary     | Eligible      |
      | 22234-21 | R           | HoosierHealthwise  | X                     | HHW-Mandatory     | Eligible      |
      | 22234-22 | A           | HoosierCareConnect | M                     | HCC-Mandatory     | Eligible      |
      | 22234-23 | A           | HoosierCareConnect | V                     | HCC-Voluntary     | Eligible      |
      | 22234-24 | A           | HoosierCareConnect | X                     | HCC-Mandatory     | Eligible      |

  @API-CP-23348  #@API-EE-IN @IN-EB-API-Regression
  Scenario Outline: Get cut off dates for HCC and HHW population
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I initiated get cut off dates by programTypeCode "<programTypeCode>" and transactionType "<transactionType>"
    When I run get cut off dates api
    Then I verify cut off dates by programTypeCode "<programTypeCode>" and transactionType "<transactionType>"
    Examples:
      | programTypeCode | transactionType |
      | A               | NewEnrollment   |
      | A               | PlanChange      |
      | R               | NewEnrollment   |
      | R               | PlanChange      |

  # Defect CP-33743 is created since Benefit Status is Enrolled but should be Not Eligible
  @API-CP-23447 # @API-EE-IN @IN-EB-API-Regression
  Scenario Outline: IN-EB Decide Benefit Status is Not Eligible - HIP (HIP status is Denied)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 3                       |
      | eligibilityRecordId          | 3                       |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | genericFieldText1            | Denied                  |
      | categoryCode                 | 10                      |
    And I run create Eligibility and Enrollment API
    And Wait for 10 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | no                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 4                       |
      | enrollmentRecordId           | 4                       |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | lastDayOfThePresentYear |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | txnStatus                    | Accepted                |
      | programCode                  | H                       |
      | subProgramTypeCode           | HealthyIndianaPlan      |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | planCode                     | 300119960               |
      | anniversaryDate              | anniversaryDate         |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "<programpopulation>"
    And I verify benefit status records are displayed with benefit status "Not Eligible"

    Examples:
      | name    | eligibilityStatusCode | programpopulation |
      | 23447-1 | M                     | HIP-Mandatory     |
      | 23447-2 | V                     | HIP-Voluntary     |


  @API-CP-30688 @API-EE-IN @IN-EB-API-Regression @kursat
  Scenario Outline: IN-EB Decide Program-Population When Managed Care Status is NULL or X With a Base Aid Category Code
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth    |
      | eligibilityEndDate           | highDate                |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramCode>        |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | genericFieldText1            | <HIPstatus>             |
      | categoryCode                 | 10                      |
      | coverageCode                 | cc01                    |
    And User provide other enrollment segments details
      | recordId          | 21                       |
      | consumerId        | <name>.consumerId        |
      | startDate         | 1stDayofPresentMonth     |
      | endDate           | 90DayFromFirstDayOfMonth |
      | genericFieldText1 | O                        |
      | genericFieldDate1 | 1stDayofPresentMonth     |
      | segmentTypeCode   | OPEN_ENROLLMENT          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with population "<programpopulation>"
    And I verify benefit status records are displayed with benefit status "<benefitStatus>"
    Examples:
      | name    | programCode | subProgramCode     | eligibilityStatusCode | programpopulation | benefitStatus        | HIPstatus |
      | 30688-1 | R           | HoosierHealthwise  | X                     | HHW-Mandatory     | Eligible             | null      |
      | 30688-2 | A           | HoosierCareConnect | X                     | HCC-Mandatory     | Eligible             | null      |
      | 30688-3 | H           | HealthyIndianaPlan | null                  | HIP-Mandatory     | Benefit Status Error | Eligible  |
      | 30688-4 | A           | HoosierCareConnect |[blank]| NONE              | Benefit Status Error | Eligible  |
#      | 30688-5  | R           | HoosierHealthwise  | M                     | HHW-Mandatory     | Eligible             | null      |
#      | 30688-6  | A           | HoosierCareConnect | M                     | HCC-Mandatory     | Eligible             | null      |
#      | 30688-7  | A           | HoosierCareConnect | V                     | HCC-Voluntary     | Eligible             | null      |
#      | 30688-8  | H           | HealthyIndianaPlan | M                     | HIP-Mandatory     | Benefit Status Error | Eligible  |
#      | 30688-9  | H           | HealthyIndianaPlan | V                     | HIP-Voluntary     | Benefit Status Error | Eligible  |
#      | 30688-10 | H           | HealthyIndianaPlan | X                     | HIP-Mandatory     | Benefit Status Error | Eligible  |
