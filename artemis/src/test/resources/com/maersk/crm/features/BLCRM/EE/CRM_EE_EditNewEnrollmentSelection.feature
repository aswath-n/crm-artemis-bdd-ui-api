Feature: Edit Consumer Initiated New Enrollment Selection (Single)

  @CP-14603 @CP-14603-01 @CP-15306 @ui-ee @alex
  Scenario: Edit button
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 14603-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14603-1.consumerId                                       |
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "14603-1.firstName" and Last Name as "14603-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "14603-1.firstName" and last name "14603-1.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made |
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed      |

  @CP-14603 @CP-14603-02 @ee-UI2API @CP-15306-02 @crm-regression @alex
  Scenario: Click the Edit Button
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 14603-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14603-2.consumerId                                       |
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "14603-2.firstName" and Last Name as "14603-2.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    And I click "EDIT" Button for a consumer first name "14603-2.firstName" and last name "14603-2.lastName"
    Then I will be navigated to the “Edit Enrollment” screen
    And I will see the consumer details, the provider panel, and the plan details panel
    Then I will see the “Remove Plan” button

  @CP-14603 @CP-14603-03 @ee-UI2API @crm-regression @alex
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

  @CP-14603 @CP-14603-04 @ee-UI2API @crm-regression @alex
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

  @CP-14603 @CP-14603-05 @ee-UI2API @crm-regression @alex
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

  @CP-14603 @CP-14603-06 @ee-UI2API @crm-regression @alex
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
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify the enrollment dates for the consumer’s future enrollment selection will be the same
    Then I verify Edit & Disregard buttons are displayed on the new enrollment segment Selection Status = Selection Made

  @CP-14603 @CP-14603-07 @ee-UI2API @crm-regression @alex
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

  @CP-14603 @CP-14603-08 @ee-UI2API @crm-regression @alex
  Scenario: Indicate Only the Latest Selection for Outbound Basket
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
    And I initiated Enrollment status Api
    Then I verify consumer and status selection made are displayed on the Outbound basket

  @CP-14603 @CP-14603-09 @ee-UI2API @crm-regression @alex
  Scenario: Create Enrollment Edit Business Event - Required and Optional fields
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 14603-09-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14603-09-01.consumerId |
      | isEligibilityRequired        | yes                    |
      | otherSegments                | facilityInfo           |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 3                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofNextMonth      |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | txnStatus                    | Accepted               |
      | programCode                  | M                      |
    And I run create Eligibility and Enrollment API
    And I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "14603-09-01.firstName" and Last Name as "14603-09-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select "CARESOURCE GEORGIA" from Available Plans
    And I click submit button on enrollment update
    And I click Edit button
    When I click on Remove Plan Option
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And Wait for 10 seconds
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_EDIT        |
      | consumerId    | 14603-09-01.consumerId |
      | correlationId | null                   |
      | consumerFound | true                   |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 14603-09-01.consumerId  |
      | correlationId | null                    |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
    # Required Fields
      | eventName          | ENROLLMENT_EDIT                      |
      | channel            | PHONE                                |
      | processDate        | current                              |
      | externalCaseId     | 14603-09-01.caseIdentificationNumber |
      | externalConsumerId | 14603-09-01.externalConsumerId       |
      | consumerId         | 14603-09-01.consumerId               |
      | consumerName       | 14603-09-01                          |
      | createdBy          | 8369                                 |
    # Optional Fields
      | enrollmentStartDate | 1stDayofPresentMonth |
      | planChangeReason    | null              |
      | planCode            | 84                |
      | enrollmentType      | MEDICAL           |
      | selectionStatus     | SELECTION_MADE    |
      | consumerPopulation  | NEWBORN           |
      | benefitStatus       | Eligible          |

  # check if this scenario has been updated correctly - Kursat
  @CP-15066 @CP-15066-01 @ee-UI2API @crm-regression @sobir
  Scenario: Edit Medical Plan Start Date to resolve rejected selection for New Enrollment Task
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 15066-01-01 |
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
    Then I send API call with name "15066-01-01" for create Eligibility and Enrollment
    And Wait for 5 seconds
    Then I send API call with name "15066-01-01" for update Enrollment information
      | [0].consumerId | 15066-01-01.consumerId |
      | [0].planId     | 145                    |
      | [0].planCode   | 84                     |
      | [0].startDate  | fdofmnth::             |
    And I send API call with name "PO" for process Outbound Update
      | [0].planId       | 145                    |
      | [0].planCode     | 84                     |
      | [0].startDate    | fdofmnth::             |
      | [0].consumerId   | 15066-01-01.consumerId |
      | [0].enrollmentId | c.data[0].enrollmentId |
    And I send API call with name "ST" for trigger Enrollment Start
      | consumerId                         | 15066-01-01.consumerId       |
      | enrollments.[0].consumerId         | 15066-01-01.consumerId       |
      | enrollments.[0].subProgramTypeCode | c.data[0].subProgramTypeCode |
      | enrollments.[0].planId             | 145                          |
      | enrollments.[0].planCode           | 84                           |
      | enrollments.[0].rejectionReason    | Consumer is not eligible     |
      | recordId                           | int::16                      |
      | enrollments.[0].startDate          | fdofmnth::                   |
      | enrollments.[0].endDate            | null::                       |
      | enrollments.[0].createdOn          | delete::                     |
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "15066-01-01.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Enrollment Reject"
    Given I logged into CRM with Call Center Supervisor account and click on initiate contact
    When I searched consumer created through api with First Name as "15066-01-01.firstName" and Last Name as "15066-01-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A second plan from Available Plans
    And I change start date to 3 on calendar
    And I select a New Enrollment Override reason from drop down on Enrollment Edit page
    And I click submit button on enrollment update


  @CP-15066 @CP-15066-02 @ee-UI2API @crm-regression @sobir
  Scenario: Edit Medical Plan Start Date to resolve rejected selection for Plan Change Task
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 15066-02-01 |
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
    Then I send API call with name "15066-02-01" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 15066-02-01.consumerId |
      | isEligibilityRequired        | no                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                    |
      | recordId                     | 4                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofPresentMonth   |
      | eligibilityStartDate         | 1stDayofPresentMonth   |
      | txnStatus                    | Accepted               |
      | programCode                  | M                      |
      | planCode                     | 84                     |
      | planId                       | 145                    |
      | serviceRegionCode            | East                   |
      | anniversaryDate              | anniversaryDate        |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 15066-02-01.consumerId     |
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
    And I initiated get enrollment by consumer id "15066-02-01.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "15066-02-01a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 15066-02-01.consumerId                |
      | [0].planId             | delete::                              |
      | [0].planCode           | 84                                    |
      | [0].enrollmentId       | 15066-02-01a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                   |
      | [0].txnStatus          | DISENROLL_SUBMITTED                   |
      | [0].startDate          | fdofmnth::                            |
      | [0].endDate            | lstdaymnth::                          |
      | [0].enrollmentType     | delete::                              |
      | [0].subProgramTypeCode | MEDICAIDGF                            |
      | [0].serviceRegionCode  | East                                  |
      | [1].planId             | delete::                              |
      | [1].planCode           | 85                                    |
      | [1].consumerId         | 15066-02-01.consumerId                |
      | [1].enrollmentId       | 15066-02-01a.selectedEnrollmentId     |
      | [1].status             | SUBMITTED_TO_STATE                    |
      | [1].txnStatus          | SUBMITTED_TO_STATE                    |
      | [1].startDate          | fdnxtmth::                            |
      | [1].subProgramTypeCode | MEDICAIDGF                            |
      | [1].serviceRegionCode  | East                                  |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                       |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 18                       |
      | enrollmentStartDate          | 1stDayofNextMonth        |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted                 |
      | programCode                  | M                        |
      | planCode                     | 85                       |
      | planId                       | 145                      |
      | serviceRegionCode            | East                     |
      | rejectionReason              | Consumer is not eligible |
      | channel                      | SYSTEM_INTEGRATION       |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "15066-02-01.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","Plan Change Reject"
    Given I logged into CRM with Call Center Supervisor account and click on initiate contact
    When I searched consumer created through api with First Name as "15066-02-01.firstName" and Last Name as "15066-02-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PLAN CHANGE" Button for a consumer
    And I select A second plan from Available Plans
    And I change start date to 3 on calendar
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
