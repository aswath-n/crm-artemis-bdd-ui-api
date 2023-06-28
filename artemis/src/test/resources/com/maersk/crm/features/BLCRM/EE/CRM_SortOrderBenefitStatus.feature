@CP-13118
Feature: View Sort Order by Benefit Status


  @CP-13118 @CP-13118-01 @ui-ee @crm-regression @Olga
  Scenario: Verify Sort order is correct for consumers with only current benefit status
  View Beneficiary segments in Order
    Given I will get the Authentication token for "BLCRM" in "CRM"
    # CREATE CONSUMER 1 CURRENT "-- --" STATUS
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-01.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | eligibilityRecordId          | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 2 CURRENT "BENEFIT STATUS ERROR" STATUS
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-02.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | null                 |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | subProgramTypeCode           | MEDICAIDGF           |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-02.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 21                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | 1stDayofNextMonth    |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | subProgramTypeCode           | MEDICAIDGF           |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "13118-02a" for create Eligibility and Enrollment
    # CREATE CONSUMER 3 CURRENT "NOT ELIGIBLE" STATUS
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-03.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-03.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 13                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | current             |
      | eligibilityEndReason         | test                |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 4 CURRENT "ENROLLED" STATUS
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-04.consumerId  |
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
    Then I send API call with name "13118-04a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-04.consumerId  |
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
    # CREATE CONSUMER 5 CURRENT "PARTIALLY ENROLLED" STATUS
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 13118-05 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-05.consumerId  |
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
      | consumerId                   | 13118-05.consumerId  |
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
    # CREATE CONSUMER 6 CURRENT "ELIGIBLE" STATUS
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-06 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-06.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify the sort of the consumers "with only current benefit status" by benefit status is:
      | -- --                |
      | BENEFIT STATUS ERROR |
      | ELIGIBLE             |
      | PARTIALLY ENROLLED   |
      | ENROLLED             |
      | NOT ELIGIBLE         |


  @CP-13118 @CP-13118-02 @ui-ee @crm-regression @Olga
  Scenario: Verify Sort order is correct for consumers with only future benefit status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    # CREATE CONSUMER 1 FUTURE "-- --" STATUS
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-07 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-07.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | facilityInfo        |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 1                   |
      | eligibilityRecordId          | 1                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | eligibilityEndDate           | highDate            |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 2 FUTURE "BENEFIT STATUS ERROR" STATUS
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-08 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-08.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 1                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | eligibilityEndDate           | null                |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | subProgramTypeCode           | MEDICAIDGF          |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-08.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | eligibilityEndDate           | nextMonth           |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | subProgramTypeCode           | MEDICAIDGF          |
      | anniversaryDate              | anniversaryDate     |
    Then I send API call with name "13118-08a" for create Eligibility and Enrollment
    # CREATE CONSUMER 3 FUTURE "ELIGIBLE" STATUS
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-09 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-09.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 1                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 4 FUTURE "ENROLLED" STATUS
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-10 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-10.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 1                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    Then I send API call with name "13118-10a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-10.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 2                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify the sort of the consumers "with only future benefit status" by benefit status is:
      | -- --                |
      | BENEFIT STATUS ERROR |
      | ELIGIBLE             |
      | ENROLLED             |


  @CP-13118 @CP-13118-03 @ui-ee @crm-regression @Olga
  Scenario: Verify Sort order is correct for consumers with both future and current benefit status
    Given I will get the Authentication token for "BLCRM" in "CRM"
    # CREATE CONSUMER 1 CURRENT "ELIGIBLE" STATUS
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-11 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-11.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 1 FUTURE "ENROLLED" STATUS
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-11.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 1                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    Then I send API call with name "13118-11a" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-11.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 2                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | eligibilityStartDate         | 1stDayofNextMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 2 CURRENT "ELIGIBLE" STATUS
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13118-12 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-12.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 3 CURRENT "PARTIALLY ENROLLED" STATUS
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 13118-13 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-13.consumerId  |
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
      | consumerId                   | 13118-13.consumerId  |
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify the sort of the consumers "with both future and current status" by benefit status is:
      | ELIGIBLE           |
      | PARTIALLY ENROLLED |
      | ELIGIBLE           |
      | ENROLLED           |


  @CP-13118 @CP-13118-04 @ui-ee @crm-regression @Olga
  Scenario: Scenario: Verify Sort order when 2 consumers have same benefit status - PI displayed first
    Given I will get the Authentication token for "BLCRM" in "CRM"
    # CREATE CONSUMER 1 CURRENT "ELIGIBLE" STATUS
    Given I created a consumer with population type "CUSTOM" with data
      | saveConsumerInfo | 13118-14 |
      | age              | 45       |
      | serviceRegion    | East     |
      | consumerRole     | Member   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-14.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
   # CREATE CONSUMER 2 CURRENT "ELIGIBLE" STATUS
    Given I created a consumer with population type "CUSTOM" with data
      | saveConsumerInfo | 13118-15 |
      | age              | 34       |
      | serviceRegion    | East     |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-15.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 3 CURRENT "ELIGIBLE" STATUS
    Given I created a consumer with population type "CUSTOM" with data
      | saveConsumerInfo | 13118-16 |
      | age              | 70       |
      | serviceRegion    | East     |
      | consumerRole     | Member   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-16.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the sort of the consumers by "name"
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I view the sort of the consumers by "primary individuals and case members"
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Primary Individual" displays first


  @CP-13118 @CP-13118-05 @ui-ee @crm-regression @Olga
  Scenario: Verify Sort order when 5 consumers have same benefit status and same role (case member) - sorted by age
    Given I will get the Authentication token for "BLCRM" in "CRM"
    # CREATE CONSUMER 1 CURRENT "ELIGIBLE" STATUS PRIMARY INDIVIDUAL
    Given I created a consumer with population type "CUSTOM" with data
      | saveConsumerInfo | 13118-17 |
      | age              | 48       |
      | serviceRegion    | East     |
      | consumerRole     | Member   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-17.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
   # CREATE CONSUMER 2 CURRENT "ELIGIBLE" STATUS CASE MEMBER
    Given I created a consumer with population type "CUSTOM" with data
      | saveConsumerInfo | 13118-18 |
      | age              | 33       |
      | serviceRegion    | East     |
      | consumerRole     | Member   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-18.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 3 CURRENT "ELIGIBLE" STATUS CASE MEMBER
    Given I created a consumer with population type "CUSTOM" with data
      | saveConsumerInfo | 13118-19 |
      | age              | 40       |
      | serviceRegion    | East     |
      | consumerRole     | Member   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-19.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 4 CURRENT "ELIGIBLE" STATUS CASE MEMBER
    Given I created a consumer with population type "CUSTOM" with data
      | saveConsumerInfo | 13118-20 |
      | age              | 25       |
      | serviceRegion    | East     |
      | consumerRole     | Member   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-20.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    # CREATE CONSUMER 5 CURRENT "ELIGIBLE" STATUS CASE MEMBER
    Given I created a consumer with population type "CUSTOM" with data
      | saveConsumerInfo | 13118-21 |
      | age              | 60       |
      | serviceRegion    | East     |
      | consumerRole     | Member   |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 13118-21.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the sort of the consumers by "Age"
    Then I verify "Oldest" displays first