Feature: Automation Assignment for DCEB

  @API-CP-39439 @API-CP-39439-01 @API-CP-39606 @API-CP-39606-01 @API-CP-39441 @API-CP-39441-01 @priyal @API-DC-EE @DC-EB-API-Regression @kursat
  Scenario: DC EB Identify Program-Populations & Auto Assign Capture New Enrollment (Auto-Assignment) Business Event and Create Auto Assignment Selections  (Immigrant Children)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39439-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "39439-01a" for update Enrollment information
      | [0].consumerId         | 39439-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39439-01a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 39439-01.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-01.consumerId  |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID             |
      | planCode                     | 081080400            |
      | planId                       | 145                  |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39439-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-02.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "39439-02a" for update Enrollment information
      | [0].consumerId         | 39439-02.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39439-02a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 39439-02.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-02.consumerId  |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID             |
      | planCode                     | 081080400            |
      | planId                       | 145                  |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39439-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-03.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | ImmigrantChildren   |
      | coverageCode                 | 420                 |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Immigrant Children
    When I run auto assignment plan batch process Api
    And Wait for 60 seconds
    And I verify enrollment by consumer id "39439-03.consumerId" with data
      | medicalPlanBeginDate | 1stDayofNextMonth |
      | medicalPlanEndDate   | highDate-DC       |
      | createdOn            | current           |
      | planCode             | 081080400         |
      | enrollmentType       | MEDICAL           |
      | channel              | AUTO_ASSIGNMENT   |
      | status               | SELECTION_MADE    |
    Then I will verify business events are generated with data
      #    CP-39606 CA 1.0 Capture New Enrollment (Auto-Assignment) Business Event - Required Fields for Alliance
      | eventName           | NEW_ENROLLMENT      |
      | channel             | AUTO_ASSIGNMENT     |
      | createdBy           | 9122                |
      | processDate         | current             |
      | consumerId          | 39439-03.consumerId |
      | consumerName        | 39439-03            |
     #    CP-39606 CA 2.0 Capture New Enrollment (Auto-Assignment) Business Event - Optional Fields for Alliance
      | enrollmentStartDate | 1stDayofNextMonth   |
      | enrollmentEndDate   | highDate-DC         |
      | planSelectionReason | Family Plan         |
      | planChangeReason    | null                |
      | rejectionReason     | null                |
      | planCode            | 081080400           |
      | enrollmentType      | MEDICAL             |
      | selectionStatus     | SELECTION_MADE      |
      | pcpNpi              | null                |
      | pcpName             | null                |
    #    CP-39441 CA 1.0 Create enrollment Segment for Auto Assignment for Alliance
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 39439-03.consumerId   |
      | consumerFound | true                  |
      | DPBI          |[blank]|


  @API-CP-39439 @API-CP-39439-02 @API-CP-39606 @API-CP-39606-02 @API-CP-39441 @API-CP-39441-02 @priyal @API-DC-EE @DC-EB-API-Regression @kursat
  Scenario: DC EB Identify Program-Populations & Auto Assign Capture New Enrollment (Auto-Assignment) Business Event and Create Auto Assignment Selections  (Alliance Children)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39439-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-04.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "39439-04a" for update Enrollment information
      | [0].consumerId         | 39439-04.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39439-04a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 39439-04.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-04.consumerId  |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID             |
      | planCode                     | 081080400            |
      | planId                       | 145                  |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39439-05 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-05.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "39439-05a" for update Enrollment information
      | [0].consumerId         | 39439-05.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39439-05a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | 39439-05.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-05.consumerId  |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID             |
      | planCode                     | 081080400            |
      | planId                       | 145                  |
      | subProgramTypeCode           | DCHF                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39439-06 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-06.consumerId   |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 25                    |
      | eligibilityRecordId          | 25                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof6MonthsBefore |
      | eligibilityStartDate         | 1stDayof6MonthsBefore |
      | eligibilityEndDate           | highDate-DC           |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | Alliance              |
      | coverageCode                 | 460                   |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39439-07 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39439-07.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | Alliance            |
      | coverageCode                 | 470                 |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Alliance
    When I run auto assignment plan batch process Api
    And Wait for 60 seconds
    And I verify enrollment by consumer id "39439-06.consumerId" with data
      | medicalPlanBeginDate | 1stDayof2MonthsBefore |
      | medicalPlanEndDate   | highDate-DC           |
      | createdOn            | current               |
      | planCode             | 081080400             |
      | enrollmentType       | MEDICAL               |
      | channel              | AUTO_ASSIGNMENT       |
      | status               | SELECTION_MADE        |
    And Wait for 10 seconds
    And I verify enrollment by consumer id "39439-07.consumerId" with data
      | medicalPlanBeginDate | 1stDayofLastMonth |
      | medicalPlanEndDate   | highDate-DC       |
      | createdOn            | current           |
      | planCode             | 081080400         |
      | enrollmentType       | MEDICAL           |
      | channel              | AUTO_ASSIGNMENT   |
      | status               | SELECTION_MADE    |
    Then I will verify business events are generated with data
      #    CP-39606 CA 1.0 Capture New Enrollment (Auto-Assignment) Business Event - Required Fields for Alliance
      | eventName           | NEW_ENROLLMENT      |
      | channel             | AUTO_ASSIGNMENT     |
      | createdBy           | 9122                |
      | processDate         | current             |
      | consumerId          | 39439-07.consumerId |
      | consumerName        | 39439-07            |
     #    CP-39606 CA 2.0 Capture New Enrollment (Auto-Assignment) Business Event - Optional Fields for Alliance
      | enrollmentStartDate | 1stDayofLastMonth   |
      | enrollmentEndDate   | highDate-DC         |
      | planSelectionReason | Family Plan         |
      | planChangeReason    | null                |
      | rejectionReason     | null                |
      | planCode            | 081080400           |
      | enrollmentType      | MEDICAL             |
      | selectionStatus     | SELECTION_MADE      |
      | pcpNpi              | null                |
      | pcpName             | null                |
    #    CP-39441 CA 1.0 Create enrollment Segment for Auto Assignment for Alliance
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 39439-07.consumerId   |
      | consumerFound | true                  |
      | DPBI          |[blank]|

  @API-CP-39440 @API-CP-39440-01 @API-CP-43523 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: DC EB- Auto Assign-Market Share-(Immigrant Children)- AASR not created
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I initiated auto assignment plan batch process Api for Immigrant Children
    When I run auto assignment plan batch process Api
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA1 |
      | newCaseCreation  | yes |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA1.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 25                |
      | eligibilityRecordId          | 25                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate-DC       |
      | txnStatus                    | Accepted          |
      | programCode                  | MEDICAID          |
      | subProgramTypeCode           | ImmigrantChildren |
      | coverageCode                 | 420F              |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA2 |
      | newCaseCreation  | yes |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA2.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 25                |
      | eligibilityRecordId          | 25                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate-DC       |
      | txnStatus                    | Accepted          |
      | programCode                  | MEDICAID          |
      | subProgramTypeCode           | ImmigrantChildren |
      | coverageCode                 | 420               |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA3 |
      | newCaseCreation  | yes |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA3.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 25                |
      | eligibilityRecordId          | 25                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate-DC       |
      | txnStatus                    | Accepted          |
      | programCode                  | MEDICAID          |
      | subProgramTypeCode           | ImmigrantChildren |
      | coverageCode                 | 420               |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Immigrant Children
    When I run auto assignment plan batch process Api
    And Wait for 100 seconds
    And I verify enrollment by consumer id "RA1.consumerId" with data
      | planCode | not null |
    And I verify enrollment by consumer id "RA2.consumerId" with data
      | planCode | not null |
    And I verify enrollment by consumer id "RA3.consumerId" with data
      | planCode | not null |
    Then I verify equal auto plan assignment
      | 055558200 |
      | 081080400 |
      | 044733300 |

  @API-CP-39440 @API-CP-39440-02 @API-CP-43523 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: DC EB- Auto Assign-Market Share-(Alliance Children)
  CP-43523 - 6.0 Equal Percentage Distribution - Assign Single Consumer on Case until Equal Plan Distribution
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I initiated auto assignment plan batch process Api for Alliance
    When I run auto assignment plan batch process Api
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA4 |
      | newCaseCreation  | yes |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA4.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 25                |
      | eligibilityRecordId          | 25                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate-DC       |
      | txnStatus                    | Accepted          |
      | programCode                  | MEDICAID          |
      | subProgramTypeCode           | Alliance          |
      | coverageCode                 | 470               |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA5 |
      | newCaseCreation  | yes |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA5.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 25                |
      | eligibilityRecordId          | 25                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate-DC       |
      | txnStatus                    | Accepted          |
      | programCode                  | MEDICAID          |
      | subProgramTypeCode           | Alliance          |
      | coverageCode                 | 460               |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA6 |
      | newCaseCreation  | yes |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA6.consumerId    |
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 25                |
      | eligibilityRecordId          | 25                |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         | 1stDayofLastMonth |
      | eligibilityEndDate           | highDate-DC       |
      | txnStatus                    | Accepted          |
      | programCode                  | MEDICAID          |
      | subProgramTypeCode           | Alliance          |
      | coverageCode                 | 470Q              |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Alliance
    When I run auto assignment plan batch process Api
    And Wait for 100 seconds
    And I verify enrollment by consumer id "RA4.consumerId" with data
      | planCode          | not null                |
      | selectionReason   | Round Robin             |
      | enrollmentEndDate | lastDayOfThePresentYear |
    And I verify enrollment by consumer id "RA5.consumerId" with data
      | planCode          | not null                |
      | selectionReason   | Round Robin             |
      | enrollmentEndDate | lastDayOfThePresentYear |
    And I verify enrollment by consumer id "RA6.consumerId" with data
      | planCode          | not null           |
      | selectionReason   | Round Robin        |
      | enrollmentEndDate | lastDayofNextMonth |
    Then I verify equal auto plan assignment
      | 077573200 |
      | 078222800 |
      | 087358900 |


  @API-CP-43523 @API-CP-43523-05 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: Market Share - Round Robin: Assign Multiple Consumers on Case to Same Plan- Alliance
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I initiated auto assignment plan batch process Api for Alliance
    When I run auto assignment plan batch process Api
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 43523-05-01 |
      | newCaseCreation  | yes         |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 43523-05-01.consumerId |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |                        |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofLastMonth      |
      | eligibilityStartDate         | 1stDayofLastMonth      |
      | eligibilityEndDate           | highDate-DC            |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | Alliance               |
      | coverageCode                 | 470                    |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 43523-05-02 |
      | newCaseCreation  | no          |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 43523-05-02.consumerId  |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |                         |
      | isEnrollemntRequired         | no                      |
      | recordId                     | 25                      |
      | eligibilityRecordId          | 25                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofLastMonth       |
      | eligibilityStartDate         | 1stDayofLastMonth       |
      | eligibilityEndDate           | lastDayOfThePresentYear |
      | txnStatus                    | Accepted                |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | Alliance                |
      | coverageCode                 | 460                     |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 43523-05-03 |
      | newCaseCreation  | no          |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 43523-05-03.consumerId |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |                        |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofLastMonth      |
      | eligibilityStartDate         | 1stDayofLastMonth      |
      | eligibilityEndDate           | lastDayofNextMonth     |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | Alliance               |
      | coverageCode                 | 470Q                   |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 43523-05-04 |
      | newCaseCreation  | no          |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 43523-05-04.consumerId |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |                        |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofLastMonth      |
      | eligibilityStartDate         | 1stDayofLastMonth      |
      | eligibilityEndDate           | lastDayofNextMonth     |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | Alliance               |
      | coverageCode                 | 470Q                   |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Alliance
    When I run auto assignment plan batch process Api
    And Wait for 100 seconds
    And I verify enrollment by consumer id "43523-05-01.consumerId" with data
      | planCode          | not null    |
      | selectionReason   | Round Robin |
      | enrollmentEndDate | highDate-DC |
    And I verify enrollment by consumer id "43523-05-02.consumerId" with data
      | planCode          | not null                |
      | selectionReason   | Round Robin             |
      | enrollmentEndDate | lastDayOfThePresentYear |
    And I verify enrollment by consumer id "43523-05-03.consumerId" with data
      | planCode          | not null           |
      | selectionReason   | Round Robin        |
      | enrollmentEndDate | lastDayofNextMonth |
    And I verify enrollment by consumer id "43523-05-04.consumerId" with data
      | planCode          | not null           |
      | selectionReason   | Round Robin        |
      | enrollmentEndDate | lastDayofNextMonth |
    Then I verify equal auto plan assignment
      | 077573200 |
      | 078222800 |
      | 077573200 |

  @API-CP-43523 @API-CP-43523-04 @API-DC-EE @DC-EB-API-Regression @shruti
  Scenario: DC EB- Auto Assign-Family Plan(Immigrant Children)-
  4.0 Family Plan - Assignment with Family Member Already Enrolled
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I initiated auto assignment plan batch process Api for Immigrant Children
    When I run auto assignment plan batch process Api
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA43523-04-01 |
      | newCaseCreation  | yes           |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA43523-04-01.consumerId |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |                          |
      | isEnrollemntRequired         | yes                      |
      | recordId                     | 25                       |
      | eligibilityRecordId          | 25                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          | 1stDayofLastMonth        |
      | eligibilityStartDate         | 1stDayofLastMonth        |
      | eligibilityEndDate           | highDate-DC              |
      | txnStatus                    | Accepted                 |
      | programCode                  | MEDICAID                 |
      | subProgramTypeCode           | ImmigrantChildren        |
      | coverageCode                 | 420F                     |
      | enrollmentEndDate            | highDate-DC              |
      | planCode                     | 044733300                |
      | anniversaryDate              | anniversaryDate          |
      | channel                      | SYSTEM_INTEGRATION       |
      | selectionReason              | selectionReason1         |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA43523-04-02 |
      | newCaseCreation  | no            |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA43523-04-02.consumerId |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |                          |
      | isEnrollemntRequired         | no                       |
      | recordId                     | 25                       |
      | eligibilityRecordId          | 25                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | eligibilityStartDate         | 1stDayofNextMonth        |
      | eligibilityEndDate           | lastDayofNextMonth       |
      | txnStatus                    | Accepted                 |
      | programCode                  | MEDICAID                 |
      | subProgramTypeCode           | ImmigrantChildren        |
      | coverageCode                 | 420                      |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | RA43523-04-03 |
      | newCaseCreation  | no            |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | RA43523-04-03.consumerId |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |                          |
      | isEnrollemntRequired         | no                       |
      | recordId                     | 25                       |
      | eligibilityRecordId          | 25                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          | 1stDayofLastMonth        |
      | eligibilityStartDate         | 1stDayof3MonthsBefore    |
      | eligibilityEndDate           | highDate-DC              |
      | txnStatus                    | Accepted                 |
      | programCode                  | MEDICAID                 |
      | subProgramTypeCode           | ImmigrantChildren        |
      | coverageCode                 | 420                      |
    And I run create Eligibility and Enrollment API
    Given I initiated auto assignment plan batch process Api for Immigrant Children
    When I run auto assignment plan batch process Api
    And Wait for 100 seconds
  # A.C 4.0- 3.0 Auto Assignment Enrollment Start and End Dates NON DCHF Crtieria 2
    And I verify enrollment by consumer id "RA43523-04-02.consumerId" with data
      | planCode            | 044733300          |
      | selectionReason     | Family Plan        |
      | enrollmentEndDate   | lastDayofNextMonth |
      | enrollmentStartDate | 1stDayofNextMonth  |
  # A.C 4.0- 3.0 Auto Assignment Enrollment Start and End Dates-NON DCHF- Crtieria 1
    And I verify enrollment by consumer id "RA43523-04-03.consumerId" with data
      | planCode            | 044733300             |
      | selectionReason     | Family Plan           |
      | enrollmentEndDate   | highDate-DC           |
      | enrollmentStartDate | 1stDayof2MonthsBefore |

  @API-CP-43523 @shruti @API-DC-EE @DC-EB-API-Regression
  Scenario Outline: Verify consumer population DCEB - Deceassed and FFS- NO AA created-2
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                    |
      | otherSegments                |                        |
      | coverageCode                 | <coverageCode>         |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | past                   |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | eligibilityEndDate           | <eligibilityEndDate>   |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | eligibilityEndReason         | <eligibilityEndReason> |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "<name>.consumerId"
    And I run get enrollment api
    Then I verify latest benefit status information with data
      | programPopulation   | <population>        |
      | population          | <population>        |
      | eligibilityScenario | Default Eligibility |
      | benefitStatus       | <benefitStatus>     |
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | <name>.consumerId |
    Then I run auto assignment transaction get Api
    Examples:
      | name     | eligibilityEndDate | coverageCode | population                         | subProgramTypeCode | benefitStatus | eligibilityEndReason |
      | 43523-01 | highDate-DC        | 130          | DCHF-Deceased                      | DCHF               | Eligible      | 8X                   |
      | 43523-02 | lastDayofNextMonth | 470Z         | Alliance-Deceased                  | Alliance           | Eligible      | 8X                   |
      | 43523-03 | highDate-DC        | 420          | Immigrant Children-Deceased        | ImmigrantChildren  | Eligible      | 8X                   |
      | 43523-31 | highDate-DC        | 130          | DCHF-Fee for Service               | DCHF               | Eligible      | 9N                   |
      | 43523-32 | lastDayofNextMonth | 470Q         | Alliance-Fee for Service           | Alliance           | Eligible      | 9N                   |
      | 43523-33 | highDate-DC        | 420          | Immigrant Children-Fee for Service | ImmigrantChildren  | Eligible      | 9N                   |

