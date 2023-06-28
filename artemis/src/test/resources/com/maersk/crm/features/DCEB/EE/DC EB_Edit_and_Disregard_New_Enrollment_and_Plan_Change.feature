Feature: DC EB: Edit and Disregard New Enrollment and Plan Change

  @CP-39008 @CP-39008-01 @CP-41990 @CP-41990-01 @CP-39454 @CP-39454-02 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario Outline: (DCHF, Alliance & Imm. Children) Verify EDIT or DISREGARD button is displaying,Click the Edit Button,
  Select Plan Using Existing Logic to Identify Available Plans, Submit Edited Plan ChangeDuring Plan Change During Pre-Lockin or Anniversary Plan Change
  Verify Cancel out of the Edit process During Pre-Lockin or Anniversary Plan Change
  Verify Only Edit Plan Pre-Lockin or Anniversary Plan Change, Plan Change Edit Business Event
  Verify Only Edit Plan Pre-Lockin or Anniversary Plan Change, Indicate Only the Latest Selection for Outbound Basket
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <consumerId> |
      | newCaseCreation  | yes |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 25                      |
      | eligibilityRecordId          | 25                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofLastMonth       |
      | eligibilityStartDate         | 1stDayofLastMonth       |
      | eligibilityEndDate           | highDate-DC             |
      | txnStatus                    | Accepted                |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | <subProgramType>        |
      | coverageCode                 | <coverageCode>          |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<consumerId>a" for update Enrollment information
      | [0].consumerId         | <consumerId>.consumerId |
      | [0].planId             | 145                     |
      | [0].planCode           | 081080400               |
#      | [0].startDate          | fdnxtmth::          |
      | [0].startDate          | fdofmnth::              |
      | [0].endDate            | highdatedc::            |
      | [0].programTypeCode    | MEDICAID                |
      | [0].subProgramTypeCode | <subProgramType>        |
      | [0].serviceRegionCode  | Northeast               |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<consumerId>a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION      |
      | [0].startDate    | fdofmnth::              |
#      | [0].startDate    | fdnxtmth::             |
      | [0].planId       | delete::                |
      | [0].planCode     | 081080400               |
      | [0].consumerId   | <consumerId>.consumerId |
      | [0].enrollmentId | c.data[0].enrollmentId  |
      | [0].status       | SUBMITTED_TO_STATE      |
      | [0].txnStatus    | SUBMITTED_TO_STATE      |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | no                      |
      | isEnrollemntRequired         | yes                     |
      | isEnrollmentProviderRequired | no                      |
      | recordId                     | 15                      |
      | enrollmentRecordId           | 15                      |
#      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate-DC             |
      | anniversaryDate              | anniversaryDate         |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID                |
      | planCode                     | 081080400               |
      | planId                       | 145                     |
      | subProgramTypeCode           | <subProgramType>        |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "<consumerId>.firstName" and Last Name as "<consumerId>.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 1.0  Display EDIT and DISREGARD Button for PLAN CHANGE Based on Role AND Change Window Countdown
    Then I verify program & benefit info page for consumer first name "<consumerId>.firstName" and last name "<consumerId>.lastName" with data
      | FUTURE ENROLLMENT.DISREGARD BUTTON | displayed|
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed|
#    2.0 Click the Edit Button
    And I click "EDIT" Button for a consumer first name "<consumerId>.firstName" and last name "<consumerId>.lastName"
    Then I will be navigated to the “Edit Enrollment” screen
    And I will see the consumer details, the provider panel, and the plan details panel
    Then I will see the “Remove Plan” button
#    4.0 Select Plan Using Existing Logic to Identify Available Plans
    When I click on Remove Plan Option
    Then I verify “Select” button grayed out and not available for consumer current enrollment
#    6.0 Submit Edited Plan Change Selection
    And I select A plan from Available Plans
#    7.0 Cancel out of the Edit process
    And I navigate away by clicking on back arrow on Enrollment Update Page
    When I see warning message pop up with an option to cancel or continue
    And I will click on cancel button in the warning message
    Then I will see the consumer details, the provider panel, and the plan details panel
#    7.0 Cancel out of the Edit process
    And I navigate away by clicking on back arrow on Enrollment Update Page
    And I will click on continue button
    Then verify I will be navigated to the “Program & Benefits Info” screen with no change to the consumer’s enrollment for DC after PlanChange
    And I click "EDIT" Button for a consumer first name "<consumerId>.firstName" and last name "<consumerId>.lastName"
    Then I will be navigated to the “Edit Enrollment” screen
    When I click on Remove Plan Option
#    5.0 Only Edit Plan
    And I select A plan from Available Plans
    Then I verify Submit button activated
    Then I will not be able to change the enrollment Start dates
   # And I Verify Reason Field Is not Required Or Not Displaying
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
#    8.0 Indicate Only the Latest Selection for Outbound Basket
    And I initiated Enrollment status Api
    Then I verify consumer and status selection made are displayed on the Outbound basket
    Then I verify the enrollment dates for the consumer’s future enrollment selection will be the same
    Then I verify program & benefit info page for consumer first name "fromCaseLoaderApi" and last name "fromCaseLoaderApi" with data
#    1.0  Display EDIT and DISREGARD Button for PLAN CHANGE Based on Role AND Change Window Countdown
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made |
      | FUTURE ENROLLMENT.DISREGARD BUTTON | displayed      |
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed      |
    And I click "EDIT" Button for a consumer first name "<consumerId>.firstName" and last name "<consumerId>.lastName"
    #Then I Verify Reason Field Is not Required Or Not Displaying
    And I select a reason from drop down on Enrollment Update page
    When I click on Remove Plan Option
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And Wait for 10 seconds
    Then I will verify business events are generated with data
# CP-39008 9.0 Free Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE_EDIT        |
      | channel                                | PHONE                   |
#      | createdBy                              | 9604                    |
      | processDate                            | current                 |
      | consumerId                             | <consumerId>.consumerId |
      | consumerName                           | <consumerId>            |
      | benefitStatus                          | Enrolled                |
      | population                             | <population>            |
# CP-39008 9.0 Free Change Business Event Optional Enrollment fields
      | previousEnrollment.enrollmentStartDate | null                    |
      | previousEnrollment.enrollmentEndDate   | null                    |
      | previousEnrollment.planSelectionReason | null                    |
      | previousEnrollment.planChangeReason    | null                    |
      | previousEnrollment.rejectionReason     | null                    |
      | previousEnrollment.planCode            | null                    |
      | previousEnrollment.enrollmentType      | null                    |
      | previousEnrollment.selectionStatus     | null                    |
      | previousEnrollment.anniversaryDate     | null                    |
# Requested Segment
      | requestedSelection.enrollmentStartDate | cutOffDateDCHF          |
      | requestedSelection.enrollmentEndDate   | highDate-DC             |
      | requestedSelection.planSelectionReason | R                    |
      | requestedSelection.planChangeReason    | null                    |
      | requestedSelection.rejectionReason     | null                    |
      | requestedSelection.planCode            | getFromUISelected       |
      | requestedSelection.enrollmentType      | MEDICAL                 |
      | requestedSelection.selectionStatus     | SELECTION_MADE          |
      | requestedSelection.anniversaryDate     | anniversaryDateDC       |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <consumerId>.consumerId |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Examples:
      | consumerId | subProgramType    | coverageCode |
      | 39008-01   | DCHF              | 130          |
      | 41990-01   | Alliance          | 460          |
      | 41990-02   | ImmigrantChildren | 420          |

  @CP-39008 @CP-39008-02 @CP-41990 @CP-41990-02 @CP-39454 @CP-39454-03 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario Outline: (DCHF, Alliance & Imm. Children) Verify Only Edit Plan Pre-Lockin or Anniversary Plan Change,  Verify Select DISREGARD button (Disregard Consumer Initiated Plan Change during Pre-Lockin or Anniversary)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <consumerId>|
      | newCaseCreation  | yes |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                      |
      | recordId                     | 25                      |
      | eligibilityRecordId          | 25                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofLastMonth       |
      | eligibilityStartDate         | 1stDayofLastMonth       |
      | eligibilityEndDate           | highDate-DC             |
      | txnStatus                    | Accepted                |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | <subProgramType>        |
      | coverageCode                 | <coverageCode>          |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<consumerId>a" for update Enrollment information
      | [0].consumerId         | <consumerId>.consumerId |
      | [0].planId             | 145                     |
      | [0].planCode           | 081080400               |
#      | [0].startDate          | fdnxtmth::          |
      | [0].startDate          | fdofmnth::              |
      | [0].endDate            | highdatedc::            |
      | [0].programTypeCode    | MEDICAID                |
      | [0].subProgramTypeCode | <subProgramType>        |
      | [0].serviceRegionCode  | Northeast               |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<consumerId>a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION      |
      | [0].startDate    | fdofmnth::              |
#      | [0].startDate    | fdnxtmth::             |
      | [0].planId       | delete::                |
      | [0].planCode     | 081080400               |
      | [0].consumerId   | <consumerId>.consumerId |
      | [0].enrollmentId | c.data[0].enrollmentId  |
      | [0].status       | SUBMITTED_TO_STATE      |
      | [0].txnStatus    | SUBMITTED_TO_STATE      |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | no                      |
      | isEnrollemntRequired         | yes                     |
      | isEnrollmentProviderRequired | no                      |
      | recordId                     | 15                      |
      | enrollmentRecordId           | 15                      |
#      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate-DC             |
      | anniversaryDate              | anniversaryDate         |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID                |
      | planCode                     | 081080400               |
      | planId                       | 145                     |
      | subProgramTypeCode           | <subProgramType>        |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <consumerIds> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerIds>.consumerId |
      | isEligibilityRequired        | yes                      |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                       |
      | recordId                     | 25                       |
      | eligibilityRecordId          | 25                       |
      | isEnrollmentProviderRequired | no                       |
      | enrollmentStartDate          | 1stDayofLastMonth        |
      | eligibilityStartDate         | 1stDayofLastMonth        |
      | eligibilityEndDate           | highDate-DC              |
      | txnStatus                    | Accepted                 |
      | programCode                  | MEDICAID                 |
      | subProgramTypeCode           | <subProgramType>         |
      | coverageCode                 | <coverageCode>           |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<consumerIds>" for update Enrollment information
      | [0].consumerId         | <consumerIds>.consumerId |
      | [0].planId             | 145                      |
      | [0].planCode           | 081080400                |
#      | [0].startDate          | fdnxtmth::          |
      | [0].startDate          | fdofmnth::               |
      | [0].endDate            | highdatedc::             |
      | [0].programTypeCode    | MEDICAID                 |
      | [0].subProgramTypeCode | <subProgramType>         |
      | [0].serviceRegionCode  | Northeast                |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<consumerIds>a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION       |
      | [0].startDate    | fdofmnth::               |
#      | [0].startDate    | fdnxtmth::             |
      | [0].planId       | delete::                 |
      | [0].planCode     | 081080400                |
      | [0].consumerId   | <consumerIds>.consumerId |
      | [0].enrollmentId | c.data[0].enrollmentId   |
      | [0].status       | SUBMITTED_TO_STATE       |
      | [0].txnStatus    | SUBMITTED_TO_STATE       |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerIds>.consumerId |
      | isEligibilityRequired        | no                       |
      | isEnrollemntRequired         | yes                      |
      | isEnrollmentProviderRequired | no                       |
      | recordId                     | 15                       |
      | enrollmentRecordId           | 15                       |
#      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentStartDate          | 1stDayofPresentMonth     |
      | enrollmentEndDate            | highDate-DC              |
      | anniversaryDate              | anniversaryDate          |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID                 |
      | planCode                     | 081080400                |
      | planId                       | 145                      |
      | subProgramTypeCode           | <subProgramType>         |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<consumerId>.firstName",Last Name as "<consumerId>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
#    3.0 Edit One Consumer’s Plan Change Selection at a Time
    Then I verify “+ Add Case Member(s)” button is grayed out and not available
    Then verify I will only be able to “Edit” plan selection for one consumer at a time
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 2.0 Disregard One Consumer at a Time
   #  3.0 Disregard Warning Message
    And I click on Disregard button on program & benefits page
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I will click on cancel button in the warning message
    Then I verify I am still on "CASE BENEFICIARY INFORMATION" Page
#     3.0 Disregard Warning Message
    And I click on Disregard button on program & benefits page
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I click on continue for Disregard Enrollment
#  4.0 Disregard the Consumer’s Plan Change Enrollment Selection
    And Edit and Disregard button are not displayed on the Enrollment section
    Then I initiated Enrollment status Api
    Then I verify consumer is not displayed on the Outbound basket
    #    1.0 View the Alliance or Immigrant Children consumer information on Program & Benefit Info tab (Regression), CP-39454
    Then I verify program & benefit info page for consumer first name "<consumerId>.firstName" and last name "<consumerId>.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT       | PRE-LOCKIN - WINDOW, ANNIVERSARY - WINDOW |
      | CURRENT ENROLLMENT.PLAN NAME          | AMERIHEALTH CARITAS DC                    |
# 6.0 Set Previous Enrollment Selection Status to ‘Accepted’
# 8.0 Remove the Outbound Indicator from “Prior” Enrollment Selection
      | CURRENT ENROLLMENT.SELECTION STATUS   | Accepted                                  |
      | CURRENT ENROLLMENT.CHANNEL            | Phone                                     |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                                     |
      | CURRENT ENROLLMENT.START DATE         | 1stDayofPresentMonthUIver                 |
      | CURRENT ENROLLMENT.END DATE           | highDateUIver-DC                          |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                                    |
# 5.0 Set Plan Change Actions back to Available
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | is displayed                              |
# 4.0 Disregard the Consumer’s Plan Change Enrollment Selection
# 7.0 Remove the Outbound Indicator from “New” Enrollment Selection
      | FUTURE ENROLLMENT.HIDDEN              |[blank]|
# 9.0 Create DISREGARDED PLAN CHANGE Business Event Required Fields
    Then I will verify business events are generated with data
      | eventName                              | DISREGARDED_PLAN_CHANGE  |
      | channel                                | CP                       |
#      | createdBy                              | 9604                     |
      | processDate                            | current                  |
      | consumerId                             | <consumerIds>.consumerId |
      | consumerName                           | <consumerIds>            |
      | benefitStatus                          | Enrolled                 |
      | population                             | <population>             |
# 9.0 Create DISREGARDED PLAN CHANGE Business Event Optional Fields Prior Segment - Plan Change
      | previousEnrollment.enrollmentStartDate | null                     |
      | previousEnrollment.enrollmentEndDate   | null                     |
      | previousEnrollment.planSelectionReason | null                     |
      | previousEnrollment.planChangeReason    | null                     |
      | previousEnrollment.rejectionReason     | null                     |
      | previousEnrollment.planCode            | null                     |
      | previousEnrollment.enrollmentType      | null                     |
      | previousEnrollment.selectionStatus     | null                     |
      | previousEnrollment.anniversaryDate     | null                     |
# Requested Segment - Plan Change
      | requestedSelection.enrollmentStartDate | cutOffDateDCHF           |
      | requestedSelection.enrollmentEndDate   | highDate-DC              |
      | requestedSelection.planSelectionReason | null                     |
      | requestedSelection.planChangeReason    | null                     |
      | requestedSelection.rejectionReason     | null                     |
      | requestedSelection.planCode            | getFromUISelected        |
      | requestedSelection.enrollmentType      | MEDICAL                  |
      | requestedSelection.selectionStatus     | DISREGARDED              |
      | requestedSelection.anniversaryDate     | anniversaryDateDC        |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <consumerId>.consumerId |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Examples:
      | consumerId | subProgramType    | coverageCode | consumerIds | population         |
      | 39008-02   | DCHF              | 130          | 39008-03    | DCHF-Adult         |
      | 41990-03   | Alliance          | 460          | 41990-04    | Alliance-Child     |
      | 41990-05   | ImmigrantChildren | 420          | 41990-06    | Immigrant Children |

  @CP-47773 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @thilak
  Scenario: Disregard Enroll Transaction auto assign status should update to null to allow consumer to Auto Assign
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 47773-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 47773-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |  [blank]            |
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
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "47773-01.firstName",Last Name as "47773-01.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "ENROLL" button is displayed
    And I click "Enroll" Button for a consumer
    And I select A plan from Available Plans
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "47773-01.firstName" and last name "47773-01.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made |
      | FUTURE ENROLLMENT.EDIT BUTTON      | displayed      |
    And I click on Disregard button on program & benefits page
    And I click on continue for Disregard Enrollment
    And Wait for 2 seconds
    And I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 47773-01.consumerId |
    When I run auto assignment transaction get Api
    Then I verify auto assignment transaction with data
      | status   | null |
      | planCode | null |