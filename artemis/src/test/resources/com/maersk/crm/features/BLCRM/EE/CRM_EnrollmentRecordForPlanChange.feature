Feature: Create Enrollment Record for a manually processed Plan Change

  @CP-9356 @CP-9356-01  @ui-ee @alex
  Scenario: Create or Update Plan Data
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 9356-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-1.consumerId |
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-1.consumerId |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "9356-1.firstName" and Last Name as "9356-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify below details on newly created future enrollment segment
      | Enrollment Start Date |
      | Enrollment End Date   |
      | Plan name             |


  @CP-9356 @CP-9356-02  @ui-ee @alex
  Scenario: Populate Channel for Enrollment Segment with Channel Selected for the contact record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 9356-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-2.consumerId |
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-2.consumerId |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "9356-2.firstName" and Last Name as "9356-2.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "9356-2.firstName" and last name "9356-2.lastName" with data
      | FUTURE ENROLLMENT.CHANNEL | Phone |

  @CP-9356 @CP-9356-03  @ui-ee  @alex
  Scenario:  Populate Selection Status for Enrollment Segment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 9356-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-3.consumerId |
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-3.consumerId |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "9356-3.firstName" and Last Name as "9356-3.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "9356-3.firstName" and last name "9356-3.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made      |
      | FUTURE ENROLLMENT.START DATE       | firstdayofNextMonth |

  @CP-9356 @CP-9356-04  @ui-ee @ee-UI2API @alex
  Scenario:  Record Updated On and Updated By, Created On and Created By for Current Enrollment Segment end date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 9356-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-4.consumerId |
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-4.consumerId |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "9356-4.firstName" and Last Name as "9356-4.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And I initiated get benefit status by consumer id "9356-4.consumerId"
    And I run get enrollment api
    Then I verify following fields are captured in the newly created Enrollment Record
      | updatedOn | current |
      | updatedBy |[blank]|
      | createdOn | current |
      | createdBy |[blank]|

  @CP-9356 @CP-9356-05  @ui-ee @ee-UI2API @alex
  Scenario:  Publish ENROLLMENT_SAVE_EVENT event for DP4BI to consume (Future segment)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 9356-5 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-5.consumerId |
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-5.consumerId |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "9356-5.firstName" and Last Name as "9356-5.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And I initiated get benefit status by consumer id "9356-5.consumerId"
    And I run get enrollment api
#    And I will verify an "ENROLLMENT_SAVE_EVENT" for "DPBI" is created with fields in payload
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | correlationId | null                  |
      | consumerId    | 9356-5.consumerId     |
      | consumerFound | true                  |
      | DPBI          |[blank]|
      | status        | SUCCESS               |



  @CP-9356 @CP-9356-06  @ui-ee @ee-UI2API @alex
  Scenario:  Publish ENROLLMENT_UPDATE_EVENT event for DP4BI to consume (Current segment)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 9356-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-6.consumerId |
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 9356-6.consumerId |
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "9356-6.firstName" and Last Name as "9356-6.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And I initiated get benefit status by consumer id "9356-6.consumerId"
    And I run get enrollment api
    And I will verify an "ENROLLMENT_UPDATE_EVENT" for "DPBI" is created with fields in payload


  @S3_EXP
  Scenario: Download and upload s3 bucket
    Given I connect to S3 bucket
    Then I getting list of S3 buckets
    And I download file "MAXNJSBEOCM_RESPONSE_20211026_1699_RECORD.log" from batch "max-etl-njsbe-non-prod"
    And  I upload to bucket "max-etl-njsbe-non-prod" with key "MAXNJSBEOCM_RESPONSE_20211115.csv" and file "src/test/resources/testData/ETL_data/MAXNJSBEOCM_RESPONSE_20210621.csv"
