Feature: Edit Consumer Initiated Plan Change (Single) - Future Enrollment Segment

  @CP-14595 @CP-14595-01 @ee-UI2API @crm-regression @alex
  Scenario: Edit button
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 14595-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14595-1.consumerId |
      | isEligibilityRequired        | yes                |
      | isEnrollemntRequired         | no                 |
      | otherSegments                |[blank]|
      | recordId                     | 1                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | eligibilityStartDate         | 1stDayofNextMonth  |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "14595-1.firstName" and Last Name as "14595-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "14595-1.firstName" and last name "14595-1.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made |
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed      |

  @CP-14595 @CP-14595-02 @ee-UI2API @crm-regression @alex
  Scenario: Click the Edit Button
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    When I click Edit button
    Then I will be navigated to the “Edit Enrollment” screen
    And I will see the consumer details, the provider panel, and the plan details panel
    Then I will see the “Remove Plan” button

  @CP-14595 @CP-14595-03 @ee-UI2API @crm-regression @alex
  Scenario: Edit One Consumer’s Plan Change Selection at a time
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 4                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofPresentMonth                                     |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
      | anniversaryDate              | anniversaryDate                                          |
    And I run create Eligibility and Enrollment API
    And I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    Then I verify “+ Add Case Member(s)” button is grayed out and not available
    Then verify I will only be able to “Edit” plan selection for one consumer at a time

  @CP-14595 @CP-14595-04 @ee-UI2API @crm-regression @alex
  Scenario: Select Plan Using Existing Logic to Identify Available Plans
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    When I click on Remove Plan Option
    Then I verify the plan selection will be removed
    And I will see “Select” button is available for each plan
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    And I click Edit button
    When I click on Remove Plan Option
    Then I verify “Select” button grayed out and not available for consumer current enrollment

  @CP-14595 @CP-14595-05 @ee-UI2API @crm-regression @alex
  Scenario: Only Edit Plan
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    And I click Edit button
    When I click on Remove Plan Option
    And I select A plan from Available Plans
    Then I verify Submit button activated
    Then I will not be able to change the enrollment dates
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And I click Edit button
    Then I will be required to enter a “Reason” for the change

  @CP-14595 @CP-14595-06 @ee-UI2API @crm-regression @alex
  Scenario: Submit Edited Plan Change Selection
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    And I click Edit button
    When I click on Remove Plan Option
    And I select A second plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify the enrollment dates for the consumer’s future enrollment selection will be the same
    Then I verify Edit & Disregard buttons are displayed on the new enrollment segment Selection Status = Selection Made

  @CP-14595 @CP-14595-07 @ee-UI2API @crm-regression @alex
  Scenario: Cancel out of the Edit process
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I navigate away by clicking on back arrow on Enrollment Update Page
    When I see warning message pop up with an option to cancel or continue
    And I will click on cancel button in the warning message
    Then I will see the consumer details, the provider panel, and the plan details panel
    And I navigate away by clicking on back arrow on Enrollment Update Page
    And I will click on continue button
    Then verify I will be navigated to the “Program & Benefits Info” screen with no change to the consumer’s enrollment

  @CP-14595 @CP-14595-08 @ee-UI2API @crm-regression @alex
  Scenario: Indicate Only the Latest Selection for Outbound Basket
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

  @CP-14595 @CP-14595-09 @ee-UI2API @crm-regression @alex
  Scenario: Create Plan Change Edit Business Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | facilityInfo         |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I click on the Contact Channel Type "Email"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    And I click Edit button
    When I initiated get enrollment by consumer id ""
    And I run get enrollment api
    Then I verify following fields are captured in the newly created Enrollment Record
      | planCode            |[blank]|
      | enrollmentStartDate | 1stDayofPresentMonth |
      | enrollmentEndDate  |[blank]|
      | enrollmentType      | MEDICAL              |
      | serviceRegionCode   | East                 |
      | createdBy           | 8369                 |
      | CreatedOn           |[blank]|
    Then I will verify an "ELIGIBILITY_UPDATE_EVENT" for "DPBI" is created with fields in payload
