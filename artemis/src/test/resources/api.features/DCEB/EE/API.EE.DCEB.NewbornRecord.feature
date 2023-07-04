Feature: Newborn Record - 26

  @API-CP-42400 @API-CP-42400-01 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: A.C 1.0 Decide Scenario for the inbound internal scenario, Newborn Default Eligibility- DCHF
  AC 1.1 Compare & Match Inbound Eligibility Segment to Consumer Eligibility in ConnectionPoint
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "<age>" with data
      | saveConsumerInfo | <name> |
        # No Match Scenario - start date past
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 26                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | planCode                     | <planCode>             |
      | processingMethod             | <processingMethod>     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>                |
      | population          | <population>                |
      | benefitStatus       | <benefitStatus>             |
      | eligibilityScenario | Newborn Default Eligibility |
#      | timeframe           | Active                      |
    # ExactMatch Scenario
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 26                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | planCode                     | <planCode>             |
      | processingMethod             | <processingMethod>     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>                |
      | population          | <population>                |
      | benefitStatus       | <benefitStatus>             |
      | eligibilityScenario | Newborn Default Eligibility |
#      | timeframe           | Active                      |
    # ChangedStartDate Scenario- Start Date in the past
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>          |
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 26                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | past                    |
      | eligibilityStartDate         | <eligibilityStartDate2> |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | planCode                     | <planCode>              |
      | processingMethod             | <processingMethod>      |
    And I run create Eligibility and Enrollment API
     # Changed End Date Scenario- start date as current date
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>          |
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 26                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | past                    |
      | eligibilityStartDate         | <eligibilityStartDate2> |
      | eligibilityEndDate           | <eligibilityEndDate2>   |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | planCode                     | <planCode>              |
      | processingMethod             | <processingMethod>      |
    And I run create Eligibility and Enrollment API
    #changed coverage Code
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode2>         |
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 26                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | past                    |
      | eligibilityStartDate         | <eligibilityStartDate2> |
      | eligibilityEndDate           | <eligibilityEndDate2>   |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | planCode                     | <planCode>              |
      | processingMethod             | <processingMethod>      |
    And I run create Eligibility and Enrollment API
    # Update Eligibilty End Reason
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode2>         |
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 26                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | past                    |
      | eligibilityStartDate         | <eligibilityStartDate2> |
      | eligibilityEndDate           | <eligibilityEndDate2>   |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityEndReason         | 8X                      |
      | planCode                     | <planCode>              |
      | processingMethod             | <processingMethod>      |
    And I run create Eligibility and Enrollment API
    When I initiated get eligibility by consumer id "<name>.consumerId"
    And I run get eligibility api
    Then I verify following fields are captured in the newly created Eligibility Record
      | eligibilityStartDate | <eligibilityStartDate2> |
      | eligibilityEndDate   | <eligibilityEndDate2>   |
      | programCode          | MEDICAID                |
      | subProgramTypeCode   | <subProgramTypeCode>    |
      | coverageCode         | <coverageCode2>         |
      | createdOn            | current                 |
      | createdBy            | 597                     |
      | eligibilityEndReason | 8X                      |

    Examples:
      | name     | age                    | eligibilityEndDate | coverageCode | population | benefitStatus | eligibilityStartDate | subProgramTypeCode | eligibilityEndDate2 | eligibilityStartDate2 | coverageCode2 | planCode  | processingMethod |
      | 42400-01 | DC-EB-CONSUMER-above60 | highDate-DC        | 110Y         | DCHF-SSI   | Eligible      | current              | DCHF               | lastDayofNextMonth  | 1stDayofPresentMonth  | 130           | 081080400 | DCHF             |
      | 42400-02 | DC-EB-CONSUMER-below60 | lastDayofNextMonth | 221B         | DCHF-Child | Eligible      | 1stDayofNextMonth    | DCHF               | highDate-DC         | current               | 231           | 081080400 | DCHF             |


  @API-CP-42400 @API-CP-42400-03 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: AC 3.0 Decide Program Population, Newborn Default Eligibility- DCHF
  AC 3.1 Deceased , AC 3.3 Fee for Service - End Reason Codes
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "<age>" with data
      | saveConsumerInfo | <name> |
        # No Match Scenario - start date past
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
    And I run create Eligibility and Enrollment API
    #Exact Match -
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 26                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | planCode                     | <planCode>             |
      | processingMethod             | <processingMethod>     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation | <population>    |
      | population        | <population>    |
      | benefitStatus     | <benefitStatus> |
    #AC 3.3 Fee for Service - End Reason Codes, A.C 3.1 -Deceased codes
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 26                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityEndReason         | <eligibilityEndReason> |
      | planCode                     | <planCode>             |
      | processingMethod             | <processingMethod>     |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population2>               |
      | eligibilityScenario | Newborn Default Eligibility |

    Examples:
      | name        | age                    | eligibilityEndDate | coverageCode | population | benefitStatus | eligibilityStartDate | subProgramTypeCode | planCode  | processingMethod | eligibilityEndReason | population2          |
      | 42400-03-01 | DC-EB-CONSUMER-newborn | highDate-DC        | 110Y         | DCHF-SSI   | Eligible      | current              | DCHF               | 081080400 | DCHF             | 9N                   | DCHF-Fee For Service |
      | 42400-03-02 | DC-EB-CONSUMER-newborn | lastDayofNextMonth | 221B         | DCHF-Child | Eligible      | 1stDayofNextMonth    | DCHF               | 081080400 | DCHF             | DD                   | DCHF-Deceased        |

  @API-CP-42400 @API-CP-42400-06-01 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: A.C 6.0 Create enrollment Segment for Newborn Assignment
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "<age>" with data
      | saveConsumerInfo | <name> |
        # No Match Scenario - start date past
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
    And I run create Eligibility and Enrollment API
#    And I initiated get benefit status by consumer id "<name>.consumerId"
#    And I run get enrollment api
#    Then I verify latest benefit status information with data
#      | programPopulation   | <population>                |
#      | population          | <population>                |
#      | benefitStatus       | <benefitStatus>             |
#      | eligibilityScenario | Newborn Default Eligibility |
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode2>        |
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 26                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | planCode                     | <planCode>             |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | processingMethod             | <processingMethod>     |
    And I run create Eligibility and Enrollment API
    And I verify enrollment by consumer id "<name>.consumerId" with data
    # CP-42990 3.0 Create the consumer’s Enrollment segment - No Match
      | medicalPlanBeginDate | <medicalPlanBeginDate> |
      | medicalPlanEndDate   | <eligibilityEndDate>   |
      | planCode             | <planCode>             |
      | updatedOn            | current                |
      | updatedBy            | 597                    |
      # Below added with CP-43394 3.0 Create the consumer’s Enrollment segment - No Match
      | txnStatus            | SELECTION_MADE         |
      | selectionReason      | NEWBORN_DEFAULT        |
      | enrollmentType       | MEDICAL                |
      | channel              | AUTO_ASSIGNMENT        |
      | createdOn            | current                |
      | createdBy            | 597                    |

    Examples:
      | name        | age                    | eligibilityEndDate | coverageCode | eligibilityStartDate | subProgramTypeCode | planCode  | coverageCode2 | processingMethod | medicalPlanBeginDate |
      | 42400-06-01 | DC-EB-CONSUMER-newborn | highDate-DC        | 110Y         | 1stDayofPresentMonth | DCHF               | 081080400 | 130           | DCHF             | nextDay              |
      | 42400-06-02 | DC-EB-CONSUMER-newborn | lastDayofNextMonth | 110Y         | 1stDayofPresentMonth | DCHF               | 081080400 | 130           | DCHF             | nextDay              |
      | 42400-06-03 | DC-EB-CONSUMER-newborn | lastDayofNextMonth | 110Y         | 1stDayofPresentMonth | DCHF               | 081080400 | 130           | Alliance         | cutOffDateDCHF       |
      | 42400-06-04 | DC-EB-CONSUMER-newborn | highDate-DC        | 110Y         | 1stDayofPresentMonth | DCHF               | 055558200 | 130           | Alliance         | cutOffDateDCHF       |
      | 42400-06-05 | DC-EB-CONSUMER-newborn | highDate-DC        | 110Y         | 1stDayofPresentMonth | DCHF               | 077573200 | 130           | Alliance         | cutOffDateDCHF       |
      | 42400-06-06 | DC-EB-CONSUMER-newborn | highDate-DC        | 110Y         | 1stDayofPresentMonth | DCHF               | 077573200 | 130           | DCHF             | nextDay              |
      | 42400-06-07 | DC-EB-CONSUMER-newborn | highDate-DC        | 460          | 1stDayofPresentMonth | DCHF               | 077573200 | 130           | Alliance         | cutOffDateDCHF       |

  @API-CP-39567 @API-CP-39567-01 @burak @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Newborn is Received From MCO Newborn file after AA was triggered - Workflow Closes AASR
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "<age>" with data
      | saveConsumerInfo | <name> |
        # No Match Scenario - start date past
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
    And I run create Eligibility and Enrollment API
#    And I initiated get benefit status by consumer id "<name>.consumerId"
#    And I run get enrollment api
#    Then I verify latest benefit status information with data
#      | programPopulation   | <population>                |
#      | population          | <population>                |
#      | benefitStatus       | <benefitStatus>             |
#      | eligibilityScenario | Newborn Default Eligibility |
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | coverageCode                 | <coverageCode2>        |
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 26                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | enrollmentEndDate            | null                   |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | planCode                     | <planCode>             |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | processingMethod             | <processingMethod>     |
    And I run create Eligibility and Enrollment API
    Then I verify latest task information with name "" for consumer id "<name>.consumerId" with data
      | find_taskTypeId       | 16277          |
      | this_taskTypeId_count | 1              |
      | taskStatus            | Closed         |
      | disposition           | SELECTION_MADE |
    Examples:
      | name     | age                    | coverageCode | eligibilityEndDate | eligibilityStartDate | subProgramTypeCode | coverageCode2 | processingMethod | planCode  |
      | 39567-01 | DC-EB-CONSUMER-newborn | 110Y         | lastDayofNextMonth | 1stDayofPresentMonth | DCHF               | 130           | DCHF             | 044733300 |
      | 39567-02 | DC-EB-CONSUMER-below60 | 221B         | highDate-DC        | 1stDayofNextMonth    | DCHF               | 231           | Alliance         | 077573200 |
      | 39567-03 | DC-EB-CONSUMER-newborn | 110Y         | highDate-DC        | 1stDayofPresentMonth | DCHF               | 130           | DCHF             | 044733300 |