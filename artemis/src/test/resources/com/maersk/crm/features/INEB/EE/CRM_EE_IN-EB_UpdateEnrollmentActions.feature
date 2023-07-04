Feature: INEB-Update Enrollment Actions


  @CP-29556 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Updates to Enrollment Actions & Decide Windows for PCPL/PCFC - HHW-M (Positive)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29556-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29556-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 29556-1.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29556-1.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29556-1.firstName" and Last Name as "29556-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29556-1.firstName" and last name "29556-1.lastName" with data
      # Consumer row information display
      | CALENDAR ICON HOVER.ACTION TEXT       | PRE-LOCKIN - WINDOW                                |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | 1stDayof2MonthsBeforeUIver - lastDayofpresentMonth |
      # Current eligibility segment display
      | CURRENT ELIGIBILITY.AID CATEGORY      | Children age 0-18                                  |
      | CURRENT ELIGIBILITY.RCP               | No                                                 |
      | CURRENT ELIGIBILITY.START DATE        | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ELIGIBILITY.END DATE          | highDateUIver                                      |
      # RCP indicator within the Open Enrollment window
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | displayed                                          |
      # Current enrollment segment display
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                                             |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Accepted                                           |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration                                 |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                                              |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ENROLLMENT.END DATE           | highDateUIver                                      |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                                             |
    And I initiated get benefit status by consumer id "29556-1.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions as following data
      | [0].action               | Available              |
      | [0].consumerAction       | Plan Change Pre-lockin |
      | [0].planSelectionAllowed | true                   |
      | [0].changeAllowedFrom    | 1stDayof2MonthsBefore  |
      | [0].changeAllowedUntil   | lastDayofPresentMonth  |
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29556-1.consumerId |
      | [0].planId             | 26                 |
      | [0].planCode           | 700410350          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HoosierHealthwise  |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29556-1.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29556-1a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29556-1.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 400752220                         |
      | [0].enrollmentId       | 29556-1a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | lastDayofPresentMonth::           |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierHealthwise                 |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 700410350                         |
      | [1].consumerId         | 29556-1.consumerId                |
      | [1].enrollmentId       | 29556-1a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                        |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierHealthwise                 |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29556-1.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | HoosierHealthwise  |
      | planCode                     | 700410350          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth             |
      | endDate           | highDate                      |
      | genericFieldText1 | 29556-1a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29556-1.firstName" and last name "29556-1.lastName" with data
      # Consumer row information display
#      | CALENDAR ICON HOVER.ACTION TEXT       | FREE CHANGE - WINDOW                               |
#      | CALENDAR ICON HOVER.IMPORTANT DATES   | 1stDayof2MonthsBeforeUIver - lastDayofpresentMonth |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | lastDayofpresentMonth             |
      | COUNTDOWN.ICON HOVER                  | Calendar days left to plan Change |
      # Current eligibility segment display
      | CURRENT ELIGIBILITY.AID CATEGORY      | Children age 0-18                 |
      | CURRENT ELIGIBILITY.RCP               | No                                |
      | CURRENT ELIGIBILITY.START DATE        | 1stDayof2MonthsBeforeUIver        |
      | CURRENT ELIGIBILITY.END DATE          | highDateUIver                     |
      # RCP indicator within the Open Enrollment window
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                            |
      # Current enrollment segment display
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                            |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenrolled                       |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration                |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                             |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver        |
      | CURRENT ENROLLMENT.END DATE           | lastDayofpresentMonth             |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                            |
      # Future enrollment segment display
      | FUTURE ENROLLMENT.PLAN NAME           | CARESOURCE                        |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Accepted                          |
      | FUTURE ENROLLMENT.CHANNEL             | System Integration                |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                             |
      | FUTURE ENROLLMENT.START DATE          | firstdayofNextMonth               |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                     |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                            |


  @CP-29556 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Updates to Enrollment Actions & Decide Windows for PCPL/PCFC - HCC-V (Positive)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29556-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29556-2.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | V                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 29556-2.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29556-2.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | V                     |
      | planCode                     | 499254630             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29556-2.firstName" and Last Name as "29556-2.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29556-2.firstName" and last name "29556-2.lastName" with data
      # Consumer row information display
      | CALENDAR ICON HOVER.ACTION TEXT       | PRE-LOCKIN - WINDOW                                |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | 1stDayof2MonthsBeforeUIver - lastDayofpresentMonth |
      # Current eligibility segment display
      | CURRENT ELIGIBILITY.AID CATEGORY      | Children age 0-18                                  |
      | CURRENT ELIGIBILITY.RCP               | No                                                 |
      | CURRENT ELIGIBILITY.START DATE        | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ELIGIBILITY.END DATE          | highDateUIver                                      |
      # RCP indicator within the Open Enrollment window
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | displayed                                          |
      # Current enrollment segment display
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM HCC                                         |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Accepted                                           |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration                                 |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                                              |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ENROLLMENT.END DATE           | highDateUIver                                      |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                                             |
    And I initiated get benefit status by consumer id "29556-2.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions as following data
      | [0].action               | Available              |
      | [0].consumerAction       | Plan Change Pre-lockin |
      | [0].planSelectionAllowed | true                   |
      | [0].changeAllowedFrom    | 1stDayof2MonthsBefore  |
      | [0].changeAllowedUntil   | lastDayofPresentMonth  |
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29556-2.consumerId |
      | [0].planId             | 26                 |
      | [0].planCode           | 399243310          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HoosierCareConnect |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29556-2.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29556-2a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29556-2.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 499254630                         |
      | [0].enrollmentId       | 29556-2a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | lastDayofPresentMonth::           |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierCareConnect                |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 399243310                         |
      | [1].consumerId         | 29556-2.consumerId                |
      | [1].enrollmentId       | 29556-2a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                        |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierCareConnect                |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29556-2.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | HoosierCareConnect |
      | planCode                     | 399243310          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth             |
      | endDate           | highDate                      |
      | genericFieldText1 | 29556-2a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29556-2.firstName" and last name "29556-2.lastName" with data
      # Consumer row information display
      | CALENDAR ICON HOVER.ACTION TEXT       | FREE CHANGE - WINDOW                               |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | 1stDayof2MonthsBeforeUIver - lastDayofpresentMonth |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | lastDayofpresentMonth                              |
      | COUNTDOWN.ICON HOVER                  | Calendar days left to plan Change                  |
      # Current eligibility segment display
      | CURRENT ELIGIBILITY.AID CATEGORY      | Children age 0-18                                  |
      | CURRENT ELIGIBILITY.RCP               | No                                                 |
      | CURRENT ELIGIBILITY.START DATE        | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ELIGIBILITY.END DATE          | highDateUIver                                      |
      # RCP indicator within the Open Enrollment window
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                                             |
      # Current enrollment segment display
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM HCC                                         |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenrolled                                        |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration                                 |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                                              |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ENROLLMENT.END DATE           | lastDayofpresentMonth                              |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                                             |
      # Future enrollment segment display
      | FUTURE ENROLLMENT.PLAN NAME           | MANAGED HEALTH SERVICES HCC                        |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Accepted                                           |
      | FUTURE ENROLLMENT.CHANNEL             | System Integration                                 |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                                              |
      | FUTURE ENROLLMENT.START DATE          | firstdayofNextMonth                                |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                                      |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                                             |


  @CP-29556 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Updates to Enrollment Actions & Decide Windows for PCPL/PCFC - HHW-M (Negative)(Record Id 3 instead of 21 with O Open Enrollment)
  Implementation changed
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29556-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29556-3.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
      | genericFieldText1            | Eligible              |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29556-3.firstName" and Last Name as "29556-3.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29556-3.firstName" and last name "29556-3.lastName" with data
      # Consumer row information display
      | CALENDAR ICON HOVER.ACTION TEXT       | -- --                      |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | -- --                      |
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | -- --                      |
      | COUNTDOWN.ICON HOVER                  | -- --                      |
      # Current eligibility segment display
      | CURRENT ELIGIBILITY.AID CATEGORY      | Children age 0-18          |
      | CURRENT ELIGIBILITY.RCP               | No                         |
      | CURRENT ELIGIBILITY.START DATE        | 1stDayof2MonthsBeforeUIver |
      | CURRENT ELIGIBILITY.END DATE          | highDateUIver              |
      # RCP indicator within the Open Enrollment window
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                     |
      # Current enrollment segment display
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                     |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Accepted                   |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration         |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                      |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver |
      | CURRENT ENROLLMENT.END DATE           | highDateUIver              |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                     |
    And I initiated get benefit status by consumer id "29556-3.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions as following data
      | [0].action               | Unavailable |
      | [0].consumerAction       | null        |
      | [0].planSelectionAllowed | false       |
      | [0].changeAllowedFrom    | null        |
      | [0].changeAllowedUntil   | null        |

  @CP-29556 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Updates to Enrollment Actions & Decide Windows for PCPL/PCFC - HHW-M (Negative)(Not yet enrolled but only Selection Made)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29556-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29556-4.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 21                 |
      | eligibilityRecordId          | 21                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | yesterday          |
      | eligibilityStartDate         | yesterday          |
      | eligibilityEndDate           | highDate           |
      | txnStatus                    | Accepted           |
      | programCode                  | R                  |
      | subProgramTypeCode           | HoosierHealthwise  |
      | eligibilityStatusCode        | M                  |
      | categoryCode                 | 10                 |
      | coverageCode                 | cc01               |
    And User provide other enrollment segments details
      | recordId          | 21                 |
      | consumerId        | 29556-4.consumerId |
      | startDate         | yesterday          |
      | endDate           | future             |
      | genericFieldText1 | O                  |
      | genericFieldDate1 | openEnrollmentDay  |
      | segmentTypeCode   | OPEN_ENROLLMENT    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29556-4.firstName" and Last Name as "29556-4.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "ENROLL" Button for a consumer first name "29556-4.firstName" and last name "29556-4.lastName"
    When I select A plan from Available Plans
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "29556-4.firstName" and last name "29556-4.lastName" with data
      # Consumer row information display
    # implementation change
      | CALENDAR ICON HOVER.ACTION TEXT      | -- --                   |
      | CALENDAR ICON HOVER.IMPORTANT DATES  | -- --                   |
      | COUNTDOWN.NUMBER OF DAYS UNTIL       | -- --                   |
      | COUNTDOWN.ICON HOVER                 | -- --                   |
      # Current eligibility segment display
      | CURRENT ELIGIBILITY.AID CATEGORY     | Children age 0-18       |
      | CURRENT ELIGIBILITY.RCP              | No                      |
      | CURRENT ELIGIBILITY.START DATE       | yesterdayUIver          |
      | CURRENT ELIGIBILITY.END DATE         | highDateUIver           |
      # FUTURE ENROLLMENT segment display
      | FUTURE ENROLLMENT.PLAN NAME          | selectedPlanName        |
      | FUTURE ENROLLMENT.SELECTION STATUS   | Selection Made          |
      | FUTURE ENROLLMENT.CHANNEL            | Phone                   |
      | FUTURE ENROLLMENT.PCP NAME           | -- --                   |
      | FUTURE ENROLLMENT.START DATE         | cutoffStartDateHHWUIver |
      | FUTURE ENROLLMENT.END DATE           | highDateUIver           |
      # RCP indicator within the Open Enrollment window
      | FUTURE ENROLLMENT.PCP SELECT BUTTON  | hidden                  |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON | hidden                  |
      | FUTURE ENROLLMENT.EDIT BUTTON        | id displayed            |
      | FUTURE ENROLLMENT.DISREGARD BUTTON   | id displayed            |
    And I initiated get benefit status by consumer id "29556-4.consumerId"
    And I run get enrollment api
    Then I Verify Consumer Actions are "Unavailable"


  @CP-29556 @ui-ee-in @crm-regression @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Updates to Enrollment Actions & Decide Windows for PCPL/PCFC - HHW-M (Negative)(Record Id 3 Free Change Action)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29556-6 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29556-6.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
      | genericFieldText1            | Eligible              |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29556-6.consumerId |
      | [0].planId             | 26                 |
      | [0].planCode           | 700410350          |
      | [0].startDate          | fdnxtmth::         |
      | [0].endDate            | highDate::         |
      | [0].subProgramTypeCode | HoosierHealthwise  |
      | [0].serviceRegionCode  | Statewide          |
      | [0].selectionReason    | 02                 |
      | [0].anniversaryDate    | anniversaryDate::  |
      | [0].channel            | SYSTEM_INTEGRATION |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29556-6.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29556-6a"
    And I send API call with name "" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29556-6.consumerId                |
      | [0].planId             | delete::                          |
      | [0].planCode           | 400752220                         |
      | [0].enrollmentId       | 29556-6a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED               |
      | [0].txnStatus          | DISENROLL_SUBMITTED               |
      | [0].startDate          | 1stDayof2MonthsBefore::           |
      | [0].endDate            | lastDayofPresentMonth::           |
      | [0].enrollmentType     | delete::                          |
      | [0].subProgramTypeCode | HoosierHealthwise                 |
      | [0].serviceRegionCode  | Statewide                         |
      | [1].planId             | delete::                          |
      | [1].planCode           | 700410350                         |
      | [1].consumerId         | 29556-6.consumerId                |
      | [1].enrollmentId       | 29556-6a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                        |
      | [1].endDate            | highDate::                        |
      | [1].subProgramTypeCode | HoosierHealthwise                 |
      | [1].serviceRegionCode  | Statewide                         |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29556-6.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | isEnrollmentProviderRequired | no                 |
      | recordId                     | 17                 |
      | enrollmentRecordId           | 17                 |
      | enrollmentStartDate          | 1stDayofNextMonth  |
      | enrollmentEndDate            | highDate           |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | MEDICAID           |
      | subProgramTypeCode           | HoosierHealthwise  |
      | planCode                     | 700410350          |
      | planId                       | 26                 |
      | serviceRegionCode            | Statewide          |
      | channel                      | SYSTEM_INTEGRATION |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth             |
      | endDate           | highDate                      |
      | genericFieldText1 | 29556-6a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29556-6.firstName" and Last Name as "29556-6.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29556-6.firstName" and last name "29556-6.lastName" with data
      # Consumer row information display
      | CALENDAR ICON HOVER.ACTION TEXT       | -- --                      |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | null                       |
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | -- --                      |
      | COUNTDOWN.ICON HOVER                  | -- --                      |
      # Current eligibility segment display
      | CURRENT ELIGIBILITY.AID CATEGORY      | Children age 0-18          |
      | CURRENT ELIGIBILITY.RCP               | No                         |
      | CURRENT ELIGIBILITY.START DATE        | 1stDayof2MonthsBeforeUIver |
      | CURRENT ELIGIBILITY.END DATE          | highDateUIver              |
      # RCP indicator within the Open Enrollment window
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                     |
      # Current enrollment segment display
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                     |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested        |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration         |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                      |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver |
      | CURRENT ENROLLMENT.END DATE           | lastDayofpresentMonth      |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                     |
      # Future enrollment segment display
      | FUTURE ENROLLMENT.PLAN NAME           | CARESOURCE                 |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made             |
      | FUTURE ENROLLMENT.CHANNEL             | System Integration         |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                      |
      | FUTURE ENROLLMENT.START DATE          | firstdayofNextMonth        |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver              |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                     |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON  | hidden                     |


  @CP-29965 @CP-43525 @CP-43525-05 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: IN-EB Edit Plan Changes for all Programs, Subprograms, and Populations
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29965-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29965-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore     |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide other enrollment segments details
      | recordId          | 21                             |
      | consumerId        | 29965-1.consumerId             |
      | startDate         | 1stDayof2MonthsBefore          |
      | endDate           | 90DayFrom1stDayof2MonthsBefore |
      | genericFieldText1 | O                              |
      | genericFieldDate1 | openEnrollmentDay              |
      | segmentTypeCode   | OPEN_ENROLLMENT                |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29965-1.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29965-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29965-2.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore     |
      | endDate             | highDate                  |
      | segmentDetailValue1 | 1                         |
      | segmentDetailValue2 | 6                         |
      | segmentDetailValue3 | 1stDayofPresentMonth::    |
      | segmentDetailValue4 | lastDayOfThePresentYear:: |
      | segmentTypeCode     | OTHER                     |
    And User provide other enrollment segments details
      | recordId          | 21                             |
      | consumerId        | 29965-2.consumerId             |
      | startDate         | 1stDayof2MonthsBefore          |
      | endDate           | 90DayFrom1stDayof2MonthsBefore |
      | genericFieldText1 | O                              |
      | genericFieldDate1 | openEnrollmentDay              |
      | segmentTypeCode   | OPEN_ENROLLMENT                |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29965-2.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29965-1.firstName" and Last Name as "29965-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify "PRIOR ELIGIBILITY CARROT" is not displayed
    And I verify "PRIOR ENROLLMENT CARROT" is not displayed
    And I click "PLAN CHANGE" Button for a consumer first name "29965-1.firstName" and last name "29965-1.lastName"
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "29965-2.firstName" and Last Name as "29965-2.lastName"
    When I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "29965-1.firstName" and last name "29965-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT       | -- --                            |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | -- --                            |
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | -- --                            |
      | COUNTDOWN.ICON HOVER                  | -- --                            |
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                           |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested              |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration               |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                            |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver       |
      | CURRENT ENROLLMENT.END DATE           | DayBeforeCutoffStartDateHHWUIver |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                           |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                           |
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName                 |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                   |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                            |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                            |
      | FUTURE ENROLLMENT.START DATE          | cutoffStartDateHHWUIver          |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                    |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                           |
      # 1.0  Display EDIT Button PLAN CHANGE Based on Role AND Change Window Countdown:   IN-EB
      | FUTURE ENROLLMENT.EDIT BUTTON         | is displayed                     |
    # 3.0 Click the EDIT Button:   IN-EB
    And I click "EDIT" Button for a consumer first name "29965-1.firstName" and last name "29965-1.lastName"
    Then I verify I am still on "ENROLLMENT EDIT" Page
    # 4.0 Edit One Consumer’s Plan Selection at a Time:   IN-EB
    Then I verify "Add Case Members button" is not displayed
    And I click on Remove Plan Option
    Then I verify I am still on "ENROLLMENT EDIT" Page
    # 5.0 Select Plan Using Existing Logic to Identify Available Plans:   IN-EB
    # 6.0 Only Edit Plan:   IN-EB
    # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
#    Then I verify list of all available plans on Enrollment Update Page with data
#      | ANTHEM                  |
#      | CARESOURCE              |
#      | MANAGED HEALTH SERVICES |
#      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                           |
      | START DATE | planChangeCutoffStartDateHCCUIver |
      | END DATE   | highDateUIver                     |
    When I select A plan from Available Plans
    # 8.0 Cancel out of the Edit process:   IN-EB
    When I navigate away by clicking on back arrow on Enrollment Update Page
    When I click on continue for Disregard Enrollment
    Then I verify I am still on "CASE BENEFICIARY INFORMATION" Page
    And I click "EDIT" Button for a consumer first name "29965-1.firstName" and last name "29965-1.lastName"
    And I click on Remove Plan Option
    When I select A plan from Available Plans
    # 7.0 Submit Edited Plan Selection:   IN-EB
    And I click submit button on enrollment update
    # 7.1 Save Edited Selections:   IN-EB
    # 9.0 Indicate Only the Latest Selection for Outbound Basket:   IN-EB
    Then I verify program & benefit info page for consumer first name "29965-1.firstName" and last name "29965-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT       | -- --                            |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | -- --                            |
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | -- --                            |
      | COUNTDOWN.ICON HOVER                  | -- --                            |
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                           |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested              |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration               |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                            |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver       |
      | CURRENT ENROLLMENT.END DATE           | DayBeforeCutoffStartDateHHWUIver |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                           |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                           |
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName                 |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                   |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                            |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                            |
      | FUTURE ENROLLMENT.START DATE          | cutoffStartDateHHWUIver          |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                    |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                           |
      | FUTURE ENROLLMENT.EDIT BUTTON         | is displayed                     |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 29965-1.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      # CP-23955 1.0 Free Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE_EDIT                 |
      | channel                                | PHONE                            |
      | createdBy                              | 7450                             |
      | processDate                            | current                          |
      | externalCaseId                         | 29965-1.caseIdentificationNumber |
      | externalConsumerId                     | 29965-1.externalConsumerId       |
      | consumerId                             | 29965-1.consumerId               |
      | consumerName                           | 29965-1                          |
      # 2.0 Free Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | null                             |
      | previousEnrollment.enrollmentEndDate   | null                             |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | null                             |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | null                             |
      | previousEnrollment.enrollmentType      | null                             |
      | previousEnrollment.selectionStatus     | null                             |
      | previousEnrollment.anniversaryDate     | null                             |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # CP-23955 3.0 Free Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | requestedSelection.enrollmentStartDate | cutoffStartDateHHW               |
      | requestedSelection.enrollmentEndDate   | highDate                         |
      | requestedSelection.planSelectionReason | 01                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
#      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |


  @CP-29966 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: IN-EB Disregard Plan Changes for all Programs, Subprograms, and Populations
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29966-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29966-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 29966-1.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29966-1.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29966-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29966-2.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 29966-2.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29966-2.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Tester 7" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29966-1.firstName" and Last Name as "29966-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PLAN CHANGE" Button for a consumer first name "29966-1.firstName" and last name "29966-1.lastName"
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "29966-2.firstName" and Last Name as "29966-2.lastName"
    When I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "29966-1.firstName" and last name "29966-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT       | PRE-LOCKIN - WINDOW                                     |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | 1stDayof2MonthsBeforeUIver - lastDayofPresentMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | lastDayofPresentMonthUIver                              |
      | COUNTDOWN.ICON HOVER                  | Calendar days left to change plan before lock-in        |
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                                                  |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested                                     |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration                                      |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                                                   |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver                              |
      | CURRENT ENROLLMENT.END DATE           | lastDayofpresentMonth                                   |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                                                  |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                                                  |
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName                                        |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                                          |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                                                   |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                                                   |
      | FUTURE ENROLLMENT.START DATE          | selectedPlanStartDate                                   |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                                           |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                                                  |
      | FUTURE ENROLLMENT.EDIT BUTTON         | is displayed                                            |
      # 1.0  Display DISREGARD Button for PLAN CHANGE Based on Role AND Change Window Countdown:   IN-EB
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | is displayed                                            |
    # 2.0 Disregard One Consumer at a Time
    And I click "DISREGARD" Button for a consumer first name "29966-1.firstName" and last name "29966-1.lastName"
    # 3.0 Disregard Warning Message
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I click on continue for Disregard Enrollment
    Then I verify program & benefit info page for consumer first name "29966-2.firstName" and last name "29966-2.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT       | PRE-LOCKIN - WINDOW                                     |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | 1stDayof2MonthsBeforeUIver - lastDayofPresentMonthUIver |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | lastDayofPresentMonthUIver                              |
      | COUNTDOWN.ICON HOVER                  | Calendar days left to change plan before lock-in        |
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                                                  |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested                                     |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration                                      |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                                                   |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver                              |
      | CURRENT ENROLLMENT.END DATE           | lastDayofpresentMonth                                   |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                                                  |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                                                  |
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName                                        |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                                          |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                                                   |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                                                   |
      | FUTURE ENROLLMENT.START DATE          | selectedPlanStartDate                                   |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                                           |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                                                  |
      | FUTURE ENROLLMENT.EDIT BUTTON         | is displayed                                            |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | is displayed                                            |
    Then I verify program & benefit info page for consumer first name "29966-1.firstName" and last name "29966-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT       | PRE-LOCKIN - WINDOW                                |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | 1stDayof2MonthsBeforeUIver - lastDayofpresentMonth |
     #Removed comment out below line which failed due to defect CP-33274 before
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | lastDayofpresentMonth                              |
      | COUNTDOWN.ICON HOVER                  | Calendar days left to change plan before lock-in   |
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                                             |
      # 6.0 Set Previous Enrollment Selection Status to ‘Accepted’
      # 8.0 Remove the Outbound Indicator from “Prior” Enrollment Selection
      | CURRENT ENROLLMENT.SELECTION STATUS   | Accepted                                           |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration                                 |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                                              |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver                         |
      | CURRENT ENROLLMENT.END DATE           | highDateUIver                                      |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                                             |
      # 5.0 Set Plan Change Actions back to Available
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | is displayed                                       |
      # 4.0 Disregard the Consumer’s Plan Change Enrollment Selection
      # 7.0 Remove the Outbound Indicator from “New” Enrollment Selection
      | FUTURE ENROLLMENT.HIDDEN              |[blank]|
      # 9.0 Create DISREGARDED PLAN CHANGE Business Event
    Then I will verify business events are generated with data
      | eventName                              | DISREGARDED_PLAN_CHANGE          |
      | channel                                | PHONE                            |
      | createdBy                              | 8234                             |
      | processDate                            | current                          |
      | externalCaseId                         | 29966-1.caseIdentificationNumber |
      | externalConsumerId                     | 29966-1.externalConsumerId       |
      | consumerId                             | 29966-1.consumerId               |
      | consumerName                           | 29966-1                          |
      # Prior Segment - Plan Change
      | previousEnrollment.enrollmentStartDate | null                             |
      | previousEnrollment.enrollmentEndDate   | null                             |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | null                             |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | null                             |
      | previousEnrollment.enrollmentType      | null                             |
      | previousEnrollment.selectionStatus     | null                             |
      | previousEnrollment.anniversaryDate     | null                             |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # Requested Segment - Plan Change
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth                |
      | requestedSelection.enrollmentEndDate   | highDate                         |
      | requestedSelection.planSelectionReason | 01                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
            # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
#      | requestedSelection.planCode            | getFromUISelected                        |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | DISREGARDED                      |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 29966-1.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|


  @CP-31971 @CP-31971-01 @CP-31971-02 @CP-24301 @CP-24300 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir @elshan
  Scenario: CP-24301 IN-EB Enrollment Create or Update Data Level of Care - Special Coverage
  CP-24300 IN-EB Enrollment Create Business Event: Level of Care - Special Coverage
  AC 1.3 Create or Update Enrolment Data for: Level of Care - Special Coverage File (INEB)
  CP-31971 IN-EB View Multiple Active LOC Codes of the Same Type
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 24301-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24301-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | uiid                | uiid::                                          |
      | createdBy           | 7450                                            |
      | updatedBy           | 7450                                            |
      | createdOn           | current                                         |
      | updatedOn           | current                                         |
      | startDate           | 1stDayofLastMonth                               |
      | endDate             | highDate                                        |
      | segmentDetailValue1 | 51H Hospice Program; Auth for 1st 90 day period |
      | segmentDetailValue2 | 4129105074                                      |
      | segmentTypeCode     | HOSPICE                                         |
    And User provide other eligibility segments details
      | uiid                | uiid::                                          |
      | createdBy           | 7450                                            |
      | updatedBy           | 7450                                            |
      | createdOn           | current                                         |
      | updatedOn           | current                                         |
      | startDate           | 1stDayof6MonthsBefore                           |
      | endDate             | highDate                                        |
      | segmentDetailValue1 | 52H Hospice Program; Auth for 2nd 90 day period |
      | segmentDetailValue2 | 4129105074                                      |
      | segmentTypeCode     | HOSPICE                                         |
    And User provide other eligibility segments details
      | uiid                | uiid::                                                 |
      | createdBy           | 7450                                                   |
      | updatedBy           | 7450                                                   |
      | createdOn           | current                                                |
      | updatedOn           | current                                                |
      | startDate           | 1stDayof2MonthsBefore                                  |
      | endDate             | current                                                |
      | segmentDetailValue1 | 53H Hospice Pgm; Auth for 3rd period; unlimited 60 day |
      | segmentDetailValue2 | 4129105074                                             |
      | segmentTypeCode     | HOSPICE                                                |
    And User provide other eligibility segments details
      | uiid                | uiid::                                        |
      | createdBy           | 7450                                          |
      | updatedBy           | 7450                                          |
      | createdOn           | current                                       |
      | updatedOn           | current                                       |
      | startDate           | 1stDayof2MonthsBefore                         |
      | endDate             | yesterday                                     |
      | segmentDetailValue1 | 54H Hospice Program; Authorization open ended |
      | segmentDetailValue2 | 4129105074                                    |
      | segmentTypeCode     | HOSPICE                                       |
    And User provide other eligibility segments details
      | uiid                | uiid::                  |
      | createdBy           | 7450                    |
      | updatedBy           | 7450                    |
      | createdOn           | current                 |
      | updatedOn           | current                 |
      | startDate           | 1stDayofLastMonth       |
      | endDate             | lastDayOfThePresentYear |
      | segmentDetailValue1 | AUWA Autism Waiver      |
      | segmentDetailValue2 | 4129105074              |
      | segmentTypeCode     | WAIVER                  |
    And User provide other eligibility segments details
      | uiid                | uiid::                        |
      | createdBy           | 7450                          |
      | updatedBy           | 7450                          |
      | createdOn           | current                       |
      | updatedOn           | current                       |
      | startDate           | 1stDayof6MonthsBefore         |
      | endDate             | current                       |
      | segmentDetailValue1 | ADWA Aged and Disabled Waiver |
      | segmentDetailValue2 | 4129105074                    |
      | segmentTypeCode     | WAIVER                        |
    And User provide other eligibility segments details
      | uiid                | uiid::                        |
      | createdBy           | 7450                          |
      | updatedBy           | 7450                          |
      | createdOn           | current                       |
      | updatedOn           | current                       |
      | startDate           | 1stDayof2MonthsBefore         |
      | endDate             | highDate                      |
      | segmentDetailValue1 | MFPAD MFP Demonstration Grant |
      | segmentDetailValue2 | 4129105074                    |
      | segmentTypeCode     | WAIVER                        |
    And User provide other eligibility segments details
      | uiid                | uiid::                              |
      | createdBy           | 7450                                |
      | updatedBy           | 7450                                |
      | createdOn           | current                             |
      | updatedOn           | current                             |
      | startDate           | 1stDayof2MonthsBefore               |
      | endDate             | yesterday                           |
      | segmentDetailValue1 | MFPTR MFP PRTF Transition from PRTF |
      | segmentDetailValue2 | 4129105074                          |
      | segmentTypeCode     | WAIVER                              |
    And User provide other eligibility segments details
      | uiid                | uiid::                                     |
      | createdBy           | 7450                                       |
      | updatedBy           | 7450                                       |
      | createdOn           | current                                    |
      | updatedOn           | current                                    |
      | startDate           | 1stDayofLastMonth                          |
      | endDate             | highDate                                   |
      | segmentDetailValue1 | NHI10 General Intermediate Care in AIDS NF |
      | segmentDetailValue2 | 4129105074                                 |
      | segmentTypeCode     | FACILITY                                   |
    And User provide other eligibility segments details
      | uiid                | uiid::                             |
      | createdBy           | 7450                               |
      | updatedBy           | 7450                               |
      | createdOn           | current                            |
      | updatedOn           | current                            |
      | startDate           | 1stDayof6MonthsBefore              |
      | endDate             | highDate                           |
      | segmentDetailValue1 | NHN Nursing Facility Level of Care |
      | segmentDetailValue2 | 4129105074                         |
      | segmentTypeCode     | FACILITY                           |
    And User provide other eligibility segments details
      | uiid                | uiid::                             |
      | createdBy           | 7450                               |
      | updatedBy           | 7450                               |
      | createdOn           | current                            |
      | updatedOn           | current                            |
      | startDate           | 1stDayof2MonthsBefore              |
      | endDate             | current                            |
      | segmentDetailValue1 | NHS13 AIDs Skilled Care Unit in NF |
      | segmentDetailValue2 | 4129105074                         |
      | segmentTypeCode     | FACILITY                           |
    And User provide other eligibility segments details
      | uiid                | uiid::                                |
      | createdBy           | 7450                                  |
      | updatedBy           | 7450                                  |
      | createdOn           | current                               |
      | updatedOn           | current                               |
      | startDate           | 1stDayof2MonthsBefore                 |
      | endDate             | yesterday                             |
      | segmentDetailValue1 | NHS10 General Skilled Care in AIDS NF |
      | segmentDetailValue2 | 4129105074                            |
      | segmentTypeCode     | FACILITY                              |
    Then I send API call with name "24301-1a" for create Eligibility and Enrollment
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "24301-1.firstName" and Last Name as "24301-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "24301-1.firstName" and last name "24301-1.lastName" with data
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.CODE.1       | NHN Nursing Facility Level of Care                     |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.START DATE.1 | 1stDayof6MonthsBefore                                  |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.END DATE.1   | highDate                                               |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.CODE.2       | NHS13 AIDs Skilled Care Unit in NF                     |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.START DATE.2 | 1stDayof2MonthsBefore                                  |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.END DATE.2   | current                                                |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.CODE.3       | NHI10 General Intermediate Care in AIDS NF             |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.START DATE.3 | 1stDayofLastMonth                                      |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.END DATE.3   | highDate                                               |
      | HOSPICE.SPECIAL COVERAGE.CODE.1                  | 52H Hospice Program; Auth for 2nd 90 day period        |
      | HOSPICE.SPECIAL COVERAGE.START DATE.1            | 1stDayof6MonthsBefore                                  |
      | HOSPICE.SPECIAL COVERAGE.END DATE.1              | highDate                                               |
      | HOSPICE.SPECIAL COVERAGE.CODE.2                  | 53H Hospice Pgm; Auth for 3rd period; unlimited 60 day |
      | HOSPICE.SPECIAL COVERAGE.START DATE.2            | 1stDayof2MonthsBefore                                  |
      | HOSPICE.SPECIAL COVERAGE.END DATE.2              | current                                                |
      | HOSPICE.SPECIAL COVERAGE.CODE.3                  | 51H Hospice Program; Auth for 1st 90 day period        |
      | HOSPICE.SPECIAL COVERAGE.START DATE.3            | 1stDayofLastMonth                                      |
      | HOSPICE.SPECIAL COVERAGE.END DATE.3              | highDate                                               |
      | WAIVER.SPECIAL COVERAGE.CODE.1                   | ADWA Aged and Disabled Waiver                          |
      | WAIVER.SPECIAL COVERAGE.START DATE.1             | 1stDayof6MonthsBefore                                  |
      | WAIVER.SPECIAL COVERAGE.END DATE.1               | current                                                |
      | WAIVER.SPECIAL COVERAGE.CODE.2                   | MFPAD MFP Demonstration Grant                          |
      | WAIVER.SPECIAL COVERAGE.START DATE.2             | 1stDayof2MonthsBefore                                  |
      | WAIVER.SPECIAL COVERAGE.END DATE.2               | highDate                                               |
      | WAIVER.SPECIAL COVERAGE.CODE.3                   | AUWA Autism Waiver                                     |
      | WAIVER.SPECIAL COVERAGE.START DATE.3             | 1stDayofLastMonth                                      |
      | WAIVER.SPECIAL COVERAGE.END DATE.3               | lastDayOfThePresentYear                                |
    # CP-24300
    Then I will verify business events are generated with data
      # AC 1.0 Special Coverage Business Event Required Fields
      | eventName                             | SPECIAL_COVERAGE                                         |
      | consumerId                            | 24301-1.consumerId                                       |
      | correlationId                         | 24301-1a.eligibilities.[0].coreEligibility.correlationId |
      | channel                               | SYSTEM_INTEGRATION                                       |
      | createdBy                             | 597                                                      |
      | processDate                           | current                                                  |
      | externalCaseId                        | 24301-1.caseIdentificationNumber                         |
      | externalConsumerId                    | 24301-1.externalConsumerId                               |
      | consumerName                          | 24301-1                                                  |
      # AC 1.1 Special Coverage Business Event Optional Fields
      | specialCoverage.[0].locDateEffective  | 1stDayofLastMonth                                        |
      | specialCoverage.[0].locDateEnd        | highDate                                                 |
      | specialCoverage.[0].providerId        | 4129105074                                               |
      | specialCoverage.[0].codeLevelOfCare   | 51H                                                      |
      # *************
      | specialCoverage.[1].locDateEffective  | 1stDayof6MonthsBefore                                    |
      | specialCoverage.[1].locDateEnd        | highDate                                                 |
      | specialCoverage.[1].providerId        | 4129105074                                               |
      | specialCoverage.[1].codeLevelOfCare   | 52H                                                      |
      # *************
      | specialCoverage.[2].locDateEffective  | 1stDayof2MonthsBefore                                    |
      | specialCoverage.[2].locDateEnd        | current                                                  |
      | specialCoverage.[2].providerId        | 4129105074                                               |
      | specialCoverage.[2].codeLevelOfCare   | 53H                                                      |
      # *************
      | specialCoverage.[3].locDateEffective  | 1stDayof2MonthsBefore                                    |
      | specialCoverage.[3].locDateEnd        | yesterday                                                |
      | specialCoverage.[3].providerId        | 4129105074                                               |
      | specialCoverage.[3].codeLevelOfCare   | 54H                                                      |
      # *************
      | specialCoverage.[4].locDateEffective  | 1stDayofLastMonth                                        |
      | specialCoverage.[4].locDateEnd        | lastDayOfThePresentYear                                  |
      | specialCoverage.[4].providerId        | 4129105074                                               |
      | specialCoverage.[4].codeLevelOfCare   | AUWA                                                     |
      # *************
      | specialCoverage.[5].locDateEffective  | 1stDayof6MonthsBefore                                    |
      | specialCoverage.[5].locDateEnd        | current                                                  |
      | specialCoverage.[5].providerId        | 4129105074                                               |
      | specialCoverage.[5].codeLevelOfCare   | ADWA                                                     |
      # *************
      | specialCoverage.[6].locDateEffective  | 1stDayof2MonthsBefore                                    |
      | specialCoverage.[6].locDateEnd        | highDate                                                 |
      | specialCoverage.[6].providerId        | 4129105074                                               |
      | specialCoverage.[6].codeLevelOfCare   | MFPAD                                                    |
      # *************
      | specialCoverage.[7].locDateEffective  | 1stDayof2MonthsBefore                                    |
      | specialCoverage.[7].locDateEnd        | yesterday                                                |
      | specialCoverage.[7].providerId        | 4129105074                                               |
      | specialCoverage.[7].codeLevelOfCare   | MFPTR                                                    |
      # *************
      | specialCoverage.[8].locDateEffective  | 1stDayofLastMonth                                        |
      | specialCoverage.[8].locDateEnd        | highDate                                                 |
      | specialCoverage.[8].providerId        | 4129105074                                               |
      | specialCoverage.[8].codeLevelOfCare   | NHI10                                                    |
      # *************
      | specialCoverage.[9].locDateEffective  | 1stDayof6MonthsBefore                                    |
      | specialCoverage.[9].locDateEnd        | highDate                                                 |
      | specialCoverage.[9].providerId        | 4129105074                                               |
      | specialCoverage.[9].codeLevelOfCare   | NHN                                                      |
      # *************
      | specialCoverage.[10].locDateEffective | 1stDayof2MonthsBefore                                    |
      | specialCoverage.[10].locDateEnd       | current                                                  |
      | specialCoverage.[10].providerId       | 4129105074                                               |
      | specialCoverage.[10].codeLevelOfCare  | NHS13                                                    |
      # *************
      | specialCoverage.[11].locDateEffective | 1stDayof2MonthsBefore                                    |
      | specialCoverage.[11].locDateEnd       | yesterday                                                |
      | specialCoverage.[11].providerId       | 4129105074                                               |
      | specialCoverage.[11].codeLevelOfCare  | NHS10                                                    |

  @CP-24301 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: IN-EB Enrollment Create or Update Data Level of Care - Special Coverage (NOT displayed field)
  AC 1.4 Do Not display special coverage segment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 24301-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 24301-2.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | uiid                | uiid::                        |
      | createdBy           | 7450                          |
      | updatedBy           | 7450                          |
      | createdOn           | current                       |
      | updatedOn           | current                       |
      | startDate           | 1stDayof2MonthsBefore         |
      | endDate             | current                       |
      | segmentDetailValue1 | ADWA Aged and Disabled Waiver |
      | segmentDetailValue2 | 4129105074                    |
      | segmentTypeCode     | WAIVER                        |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "24301-2.firstName" and Last Name as "24301-2.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "24301-2.firstName" and last name "24301-2.lastName" with data
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.HIDDEN |[blank]|
      | HOSPICE.SPECIAL COVERAGE.HIDDEN            |[blank]|
      | WAIVER.SPECIAL COVERAGE.CODE.1             | ADWA Aged and Disabled Waiver |
      | WAIVER.SPECIAL COVERAGE.START DATE.1       | 1stDayof2MonthsBefore         |
      | WAIVER.SPECIAL COVERAGE.END DATE.1         | current                       |


  @CP-25945 @CP-30848 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: CP-25945 IN-EB Override Plan Changes for all Programs, Subprograms, and Populations for HHW
  CP-30848 IN-EB Override Plan Changes for all Programs, Subprograms, and Populations - Business Events
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 25945-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25945-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 25945-1.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25945-1.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "25945-1.consumerId" with data
      | [0].action               | Available              |
      | [0].consumerAction       | Plan Change Pre-lockin |
      | [0].planSelectionAllowed | true                   |
      | [0].changeAllowedFrom    | 1stDayof2MonthsBefore  |
      | [0].changeAllowedUntil   | lastDayofLastMonth     |
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 25945-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25945-2.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 25945-2.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25945-2.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | R                     |
      | subProgramTypeCode           | HoosierHealthwise     |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 400752220             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "25945-2.consumerId" with data
      | [0].action               | Available              |
      | [0].consumerAction       | Plan Change Pre-lockin |
      | [0].planSelectionAllowed | true                   |
      | [0].changeAllowedFrom    | 1stDayof2MonthsBefore  |
      | [0].changeAllowedUntil   | lastDayofLastMonth     |
    Given I logged into CRM with "Service Tester 7" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "25945-1.firstName" and Last Name as "25945-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # 2.0 Display Important Calendar Dates
    Then I verify program & benefit info page for consumer first name "25945-1.firstName" and last name "25945-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT      | PRE-LOCKIN - WINDOW                                  |
      | CALENDAR ICON HOVER.IMPORTANT DATES  | 1stDayof2MonthsBeforeUIver - lastDayofLastMonthUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL       | -- --                                                |
      | COUNTDOWN.ICON HOVER                 | -- --                                                |
    # 1.0 Display PLAN CHANGE Button for Limited Roles
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON | is displayed                                         |
    And I click "PLAN CHANGE" Button for a consumer first name "25945-1.firstName" and last name "25945-1.lastName"
    # 3.0 Adding and Removing a Case Member
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 25945-2 |
    When I click Add Case Members with First Name as "25945-2.firstName" and Last Name as "25945-2.lastName"
    Then I verify  the Add Case Members button is disable
    When I click Remove Button for Case Member First Name as "25945-2.firstName" and Last Name as "25945-2.lastName"
    Then I verivy consumer by First Name as "25945-2.firstName" and Last Name as "25945-2.lastName" is not in plan selection
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "25945-2.firstName" and Last Name as "25945-2.lastName"
    # 4.0 Identify Available Plans for Single and Multiple Consumers
    # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
#    Then I verify list of all available plans on Enrollment Update Page with data
#      | ANTHEM                  |
#      | CARESOURCE              |
#      | MANAGED HEALTH SERVICES |
#      | MDWISE HH               |
    # 5.0 Calculate Plan Start Date and Plan End Date
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | cutoffStartDateHHWUIver |
      | END DATE   | highDateUIver           |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # 6.0 Select Plan
    And I select A plan from Available Plans
    # 7.0 Remove Plan
    And I click on Remove Plan Option
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    When I select A plan from Available Plans
    And I add 4 days from selected day on calendar
    # 8.0 Do NOT Display Override Reason Dropdown
    And I select a reason from drop down on Enrollment Update page
    Then I verify dropdown "OVERRIDE REASON" is not displayed
    And I click submit button on enrollment update
    # 9.0 Display EDIT Button:
    Then I verify program & benefit info page for consumer first name "25945-1.firstName" and last name "25945-1.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT       | PRE-LOCKIN - WINDOW                                  |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | 1stDayof2MonthsBeforeUIver - lastDayofLastMonthUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | -- --                                                |
      | COUNTDOWN.ICON HOVER                  | -- --                                                |
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                                               |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested                                  |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration                                   |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                                                |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver                           |
      | CURRENT ENROLLMENT.END DATE           | DayBeforeSelectedPlanStartDate                       |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                                               |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                                               |
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName                                     |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                                       |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                                                |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                                                |
      | FUTURE ENROLLMENT.START DATE          | selectedPlanStartDate                                |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                                        |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                                               |
      # 1.0  Display EDIT Button PLAN CHANGE Based on Role AND Change Window Countdown:   IN-EB
      | FUTURE ENROLLMENT.EDIT BUTTON         | is displayed                                         |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | is displayed                                         |
    # CP-30848 1.0 Create PLAN CHANGE OVERRIDE Business Event IN-EB
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I save first contactId with name "25945-1a"
    Then I will verify business events are generated with data
      | eventName                              | PLAN_CHANGE_OVERRIDE             |
      | channel                                | PHONE                            |
      | createdBy                              | 8234                             |
      | processDate                            | current                          |
      | externalCaseId                         | 25945-1.caseIdentificationNumber |
      | externalConsumerId                     | 25945-1.externalConsumerId       |
      | consumerId                             | 25945-1.consumerId               |
      | consumerName                           | 25945-1                          |
      # Prior Segment - Plan Change
      | previousEnrollment.enrollmentStartDate | 1stDayof2MonthsBefore            |
      | previousEnrollment.enrollmentEndDate   | DayBeforeSelectedPlanStartDate   |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | 01                               |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | 400752220                        |
      | previousEnrollment.enrollmentType      | MEDICAL                          |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED              |
      | previousEnrollment.anniversaryDate     | anniversaryDate                  |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # Requested Segment - Plan Change
      | contactId                              | 25945-1a.contactId               |
      | requestedSelection.enrollmentStartDate | getFromUISelected                |
      | requestedSelection.enrollmentEndDate   | highDate                         |
      | requestedSelection.planSelectionReason | 01                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
          # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
#      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 25945-1.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # 10.0 EDIT Enrollment Start Date
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click future enrollment "EDIT" Button for a consumer first name "25945-1.firstName" and last name "25945-1.lastName"
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    Then I verify "Add Case Members button" is not displayed
    And I add 4 days from selected day on calendar
    Then I verify dropdown "REASON" is not displayed
    Then I verify dropdown "OVERRIDE REASON" is not displayed
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "25945-1.firstName" and last name "25945-1.lastName" with data
    # 12.0 Update Each Consumer’s Prior Selection [Regression]
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                         |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested            |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration             |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                          |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver     |
      | CURRENT ENROLLMENT.END DATE           | DayBeforeSelectedPlanStartDate |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                         |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                         |
      # 11.0 Create Each Consumer’s Requested Selection [Regression]
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName               |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                 |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                          |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                          |
    # 13.0 VIEW updated PLAN and/or ENROLLMENT START DATE
      | FUTURE ENROLLMENT.START DATE          | selectedPlanStartDate          |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                  |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                         |
      | FUTURE ENROLLMENT.EDIT BUTTON         | is displayed                   |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | is displayed                   |
    And I click future enrollment "EDIT" Button for a consumer first name "25945-2.firstName" and last name "25945-2.lastName"
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    Then I verify "Add Case Members button" is not displayed
    And I click on Remove Plan Option
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    # There is a defect CP-34087 in plans (ADVANTAGE HEALTH SOLUTIONS INC, MDWISE CS, OPT IN DECLINED should not be displayed). Commented out until defect is fixed
#    Then I verify list of all available plans on Enrollment Update Page with data
#      | ANTHEM                  |
#      | CARESOURCE              |
#      | MANAGED HEALTH SERVICES |
#      | MDWISE HH               |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | cutoffStartDateHHWUIver |
      | END DATE   | highDateUIver           |
    When I select A plan from Available Plans
    And I add 4 days from selected day on calendar
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "25945-2.firstName" and last name "25945-2.lastName" with data
    # 12.0 Update Each Consumer’s Prior Selection [Regression]
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM                         |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested            |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration             |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                          |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver     |
      | CURRENT ENROLLMENT.END DATE           | DayBeforeSelectedPlanStartDate |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                         |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                         |
      # 11.0 Create Each Consumer’s Requested Selection [Regression]
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName               |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                 |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                          |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                          |
    # 13.0 VIEW updated PLAN and/or ENROLLMENT START DATE
      | FUTURE ENROLLMENT.START DATE          | selectedPlanStartDate          |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                  |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                         |
      | FUTURE ENROLLMENT.EDIT BUTTON         | is displayed                   |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | is displayed                   |
    # CP-30848 2.0 Create PLAN CHANGE EDIT OVERRIDE Business Event IN-EB (When EDIT functionality is invoked)
    Then I will verify business events are generated with data
      | eventName                              | PLAN_CHANGE_EDIT_OVERRIDE        |
      | channel                                | PHONE                            |
      | createdBy                              | 8234                             |
      | processDate                            | current                          |
      | externalCaseId                         | 25945-2.caseIdentificationNumber |
      | externalConsumerId                     | 25945-2.externalConsumerId       |
      | consumerId                             | 25945-2.consumerId               |
      | consumerName                           | 25945-2                          |
      # Prior Segment - Plan Change
      | previousEnrollment.enrollmentStartDate | null                             |
      | previousEnrollment.enrollmentEndDate   | null                             |
      | previousEnrollment.planSelectionReason | null                             |
      | previousEnrollment.planChangeReason    | null                             |
      | previousEnrollment.rejectionReason     | null                             |
      | previousEnrollment.planCode            | null                             |
      | previousEnrollment.enrollmentType      | null                             |
      | previousEnrollment.selectionStatus     | null                             |
      | previousEnrollment.anniversaryDate     | null                             |
      | previousEnrollment.pcpNpi              | null                             |
      | previousEnrollment.pcpName             | null                             |
      # Requested Segment - Plan Change
      | contactId                              | 25945-1a.contactId               |
      | requestedSelection.enrollmentStartDate | getFromUISelected                |
      | requestedSelection.enrollmentEndDate   | highDate                         |
      | requestedSelection.planSelectionReason | 01                               |
      | requestedSelection.planChangeReason    | null                             |
      | requestedSelection.rejectionReason     | null                             |
      | requestedSelection.planCode            | getFromUISelected                |
      | requestedSelection.enrollmentType      | MEDICAL                          |
      | requestedSelection.selectionStatus     | SELECTION_MADE                   |
      | requestedSelection.anniversaryDate     | anniversaryDate                  |
      | requestedSelection.pcpNpi              | null                             |
      | requestedSelection.pcpName             | null                             |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | 25945-2.consumerId      |
      | consumerFound | true                    |
      | DPBI          |[blank]|


  @CP-25945 @CP-34087 @ui-ee-in @crm-regression @IN-EB-UI-Regression @sobir
  Scenario: IN-EB Override Plan Changes for all Programs, Subprograms, and Populations for HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 25945-3 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25945-3.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 25945-3.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25945-3.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 499254630             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "25945-3.consumerId" with data
      | [0].action               | Available              |
      | [0].consumerAction       | Plan Change Pre-lockin |
      | [0].planSelectionAllowed | true                   |
      | [0].changeAllowedFrom    | 1stDayof2MonthsBefore  |
      | [0].changeAllowedUntil   | lastDayofLastMonth     |
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 25945-4 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25945-4.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 1                       |
      | segmentDetailValue2 | 6                       |
      | segmentDetailValue3 | 1stDayofPresentMonth    |
      | segmentDetailValue4 | lastDayOfThePresentYear |
      | segmentTypeCode     | OTHER                   |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 25945-4.consumerId    |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | openEnrollmentDay     |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 25945-4.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | enrollmentRecordId           | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate              |
      | txnStatus                    | Accepted              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | planCode                     | 499254630             |
      | planId                       | 33                    |
      | serviceRegionCode            | Statewide             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | categoryCode                 | 10                    |
      | coverageCode                 | cc01                  |
    And I run create Eligibility and Enrollment API
    And I update latest consumer actions by consumerId "25945-4.consumerId" with data
      | [0].action               | Available              |
      | [0].consumerAction       | Plan Change Pre-lockin |
      | [0].planSelectionAllowed | true                   |
      | [0].changeAllowedFrom    | 1stDayof2MonthsBefore  |
      | [0].changeAllowedUntil   | lastDayofLastMonth     |
    Given I logged into CRM with "Service Tester 7" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "25945-3.firstName" and Last Name as "25945-3.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # 2.0 Display Important Calendar Dates
    Then I verify program & benefit info page for consumer first name "25945-3.firstName" and last name "25945-3.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT      | PRE-LOCKIN - WINDOW                                  |
      | CALENDAR ICON HOVER.IMPORTANT DATES  | 1stDayof2MonthsBeforeUIver - lastDayofLastMonthUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL       | -- --                                                |
      | COUNTDOWN.ICON HOVER                 | -- --                                                |
    # 1.0 Display PLAN CHANGE Button for Limited Roles
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON | is displayed                                         |
    And I click "PLAN CHANGE" Button for a consumer first name "25945-3.firstName" and last name "25945-3.lastName"
    # 3.0 Adding and Removing a Case Member
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 25945-4 |
    When I click Add Case Members with First Name as "25945-4.firstName" and Last Name as "25945-4.lastName"
    Then I verify  the Add Case Members button is disable
    When I click Remove Button for Case Member First Name as "25945-4.firstName" and Last Name as "25945-4.lastName"
    Then I verivy consumer by First Name as "25945-4.firstName" and Last Name as "25945-4.lastName" is not in plan selection
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "25945-4.firstName" and Last Name as "25945-4.lastName"
    # 4.0 Identify Available Plans for Single and Multiple Consumers
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM HCC                  |
      | MANAGED HEALTH SERVICES HCC |
      | UNITED HEALTHCARE HCC       |
#      | MDWISE HCC                  |
    # 5.0 Calculate Plan Start Date and Plan End Date
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | cutoffStartDateHHWUIver |
      | END DATE   | highDateUIver           |
    Then I verify "PCP SELECT BUTTON" is not displayed
    # 6.0 Select Plan
    And I select A plan from Available Plans
    # 7.0 Remove Plan
    And I click on Remove Plan Option
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    When I select A plan from Available Plans
    And I add 4 days from selected day on calendar
    # 8.0 Do NOT Display Override Reason Dropdown
    And I select a reason from drop down on Enrollment Update page
    Then I verify dropdown "OVERRIDE REASON" is not displayed
    And I click submit button on enrollment update
    # 9.0 Display EDIT Button:
    Then I verify program & benefit info page for consumer first name "25945-3.firstName" and last name "25945-3.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT       | PRE-LOCKIN - WINDOW                                  |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | 1stDayof2MonthsBeforeUIver - lastDayofLastMonthUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | -- --                                                |
      | COUNTDOWN.ICON HOVER                  | -- --                                                |
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM HCC                                           |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested                                  |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration                                   |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                                                |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver                           |
      | CURRENT ENROLLMENT.END DATE           | DayBeforeSelectedPlanStartDate                       |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                                               |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                                               |
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName                                     |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                                       |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                                                |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                                                |
      | FUTURE ENROLLMENT.START DATE          | selectedPlanStartDate                                |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                                        |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                                               |
      # 1.0  Display EDIT Button PLAN CHANGE Based on Role AND Change Window Countdown:   IN-EB
      | FUTURE ENROLLMENT.EDIT BUTTON         | is displayed                                         |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | is displayed                                         |
    # 10.0 EDIT Enrollment Start Date
    And I click future enrollment "EDIT" Button for a consumer first name "25945-3.firstName" and last name "25945-3.lastName"
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    Then I verify "Add Case Members button" is not displayed
    And I add 4 days from selected day on calendar
    Then I verify dropdown "REASON" is not displayed
    Then I verify dropdown "OVERRIDE REASON" is not displayed
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "25945-3.firstName" and last name "25945-3.lastName" with data
    # 12.0 Update Each Consumer’s Prior Selection [Regression]
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM HCC                     |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested            |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration             |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                          |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver     |
      | CURRENT ENROLLMENT.END DATE           | DayBeforeSelectedPlanStartDate |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                         |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                         |
      # 11.0 Create Each Consumer’s Requested Selection [Regression]
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName               |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                 |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                          |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                          |
    # 13.0 VIEW updated PLAN and/or ENROLLMENT START DATE
      | FUTURE ENROLLMENT.START DATE          | selectedPlanStartDate          |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                  |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                         |
      | FUTURE ENROLLMENT.EDIT BUTTON         | is displayed                   |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | is displayed                   |
    And I click future enrollment "EDIT" Button for a consumer first name "25945-4.firstName" and last name "25945-4.lastName"
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    Then I verify "Add Case Members button" is not displayed
    And I click on Remove Plan Option
    Then I verify I am still on "ENROLLMENT OVERRIDE" Page
    Then I verify list of all available plans on Enrollment Update Page with data
      | ANTHEM HCC                  |
      | MANAGED HEALTH SERVICES HCC |
      | UNITED HEALTHCARE HCC       |
#      | MDWISE HCC                  |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                           |
      | START DATE | planChangeCutoffStartDateHCCUIver |
      | END DATE   | highDateUIver                     |
    When I select A plan from Available Plans
    And I add 4 days from selected day on calendar
    And I click submit button on enrollment update
    Then I verify program & benefit info page for consumer first name "25945-4.firstName" and last name "25945-4.lastName" with data
    # 12.0 Update Each Consumer’s Prior Selection [Regression]
      | CURRENT ENROLLMENT.PLAN NAME          | ANTHEM HCC                     |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested            |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration             |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                          |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof2MonthsBeforeUIver     |
      | CURRENT ENROLLMENT.END DATE           | DayBeforeSelectedPlanStartDate |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                         |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                         |
      # 11.0 Create Each Consumer’s Requested Selection [Regression]
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName               |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made                 |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                          |
      | FUTURE ENROLLMENT.PCP NAME            | -- --                          |
    # 13.0 VIEW updated PLAN and/or ENROLLMENT START DATE
      | FUTURE ENROLLMENT.START DATE          | selectedPlanStartDate          |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver                  |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON   | hidden                         |
      | FUTURE ENROLLMENT.EDIT BUTTON         | is displayed                   |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | is displayed                   |
