Feature: Baseline Plan Change Multiple Times scenarios

  @CP-29502 @CP-25423 @CP-14127 @CP-9119 @ui-ee @crm-regression @ee-UI2API @kursat
  Scenario: CP-25423 BASELINE Multiple Plan Changes Within a Change Window - Part # 1
  CP-29502 BASELINE Multiple Plan Changes Within a Change Window - Part # 2
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 29502-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29502-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDGF          |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29502-01.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29502-01a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29502-01.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 29502-01a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDGF                         |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 29502-01.consumerId                |
      | [1].enrollmentId       | 29502-01a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDGF                         |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 29502-01a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    # CREATING CONSUMER 2
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 29502-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-02.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-02.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29502-02.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDGF          |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29502-02.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29502-02a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29502-02.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 29502-02a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDGF                         |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 29502-02.consumerId                |
      | [1].enrollmentId       | 29502-02a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDGF                         |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-02.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 29502-02a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    # CREATING CONSUMER 3
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 29502-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-03.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | anniversaryDate              | anniversaryDate     |
      | subProgramTypeCode           | MEDICAIDMCHB        |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-03.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | MEDICAIDMCHB        |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29502-03.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDMCHB        |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29502-03.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29502-03a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29502-03.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 29502-03a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDMCHB                       |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 29502-03.consumerId                |
      | [1].enrollmentId       | 29502-03a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDMCHB                       |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-03.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 29502-03a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29502-01.firstName" and last name "29502-01.lastName" with data
      # CP-25423 1.0  VIEW Plan Change button on Future Enrollment Segment - Baseline
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population |
#      | CURRENT ELIGIBILITY.AID CATEGORY        | -- --                       |
#      | CURRENT ELIGIBILITY.RCP                 | No                          |
#   QA Investigate: Due to permission changes aid category and rcp fields are not visible any more in qa-rel with service account one csr role
      | CURRENT ELIGIBILITY.AID CATEGORY        | hidden                       |
      | CURRENT ELIGIBILITY.RCP                 | hidden                          |
      | CURRENT ELIGIBILITY.SERVICE REGION      | East                        |
      | CURRENT ELIGIBILITY.START DATE          | firstdayofLastMonth         |
      | CURRENT ELIGIBILITY.END DATE            | -- --                       |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | AMERIGROUP COMMUNITY CARE   |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Disenrolled                 |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration          |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                       |
      | CURRENT ENROLLMENT.START DATE           | firstdayofLastMonth         |
      | CURRENT ENROLLMENT.END DATE             | lastDayofpresentMonth       |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                      |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                      |
      #***********
      | FUTURE ENROLLMENT.PLAN NAME             | PEACH STATE                 |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Accepted                    |
      | FUTURE ENROLLMENT.CHANNEL               | System Integration          |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                       |
      | FUTURE ENROLLMENT.START DATE            | firstdayofNextMonth         |
      | FUTURE ENROLLMENT.END DATE              | highDateUIver               |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON     | is displayed                |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON    | is displayed                |
    Then I verify program & benefit info page for consumer first name "29502-03.firstName" and last name "29502-03.lastName" with data
      # CP-25423 1.0  VIEW Plan Change button on Future Enrollment Segment - Baseline
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-Pregnant Women   |
#      | CURRENT ELIGIBILITY.AID CATEGORY        | -- --                     |
#      | CURRENT ELIGIBILITY.RCP                 | No                        |
#   QA Investigate: Due to permission changes aid category and rcp fields are not visible any more in qa-rel with service account one csr role
      | CURRENT ELIGIBILITY.AID CATEGORY        | hidden                    |
      | CURRENT ELIGIBILITY.RCP                 | hidden                        |
      | CURRENT ELIGIBILITY.SERVICE REGION      | East                      |
      | CURRENT ELIGIBILITY.START DATE          | firstdayofLastMonth       |
      | CURRENT ELIGIBILITY.END DATE            | -- --                     |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | AMERIGROUP COMMUNITY CARE |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Disenrolled               |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration        |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                     |
      | CURRENT ENROLLMENT.START DATE           | firstdayofLastMonth       |
      | CURRENT ENROLLMENT.END DATE             | lastDayofpresentMonth     |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | hidden                    |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | hidden                    |
      #***********
      | FUTURE ENROLLMENT.PLAN NAME             | PEACH STATE               |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Accepted                  |
      | FUTURE ENROLLMENT.CHANNEL               | System Integration        |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                     |
      | FUTURE ENROLLMENT.START DATE            | firstdayofNextMonth       |
      | FUTURE ENROLLMENT.END DATE              | highDateUIver             |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON     | is displayed              |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON    | is displayed              |
    # CP-25423 2.0 CLICK Plan Change button on Future Enrollment Segment - Baseline
    And I click future enrollment "PLAN CHANGE" Button for a consumer first name "29502-01.firstName" and last name "29502-01.lastName"
    Then I verify I am still on "Enrollment Update" Page
    Then I verify "PCP SELECT BUTTON" is not displayed
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 29502-02 |
    When I click Add Case Members with First Name as "29502-02.firstName" and Last Name as "29502-02.lastName"
    Then I verify  the Add Case Members button is disable
    When I click Remove Button for Case Member First Name as "29502-02.firstName" and Last Name as "29502-02.lastName"
    Then I verivy consumer by First Name as "29502-02.firstName" and Last Name as "29502-02.lastName" is not in plan selection
    When I click Add Case Members Button
    When I click Add Case Members with First Name as "29502-02.firstName" and Last Name as "29502-02.lastName"
    Then I verify list of all available plans on Enrollment Update Page with data
      | AMERIGROUP COMMUNITY CARE |
      | CARESOURCE GEORGIA        |
      | PEACH STATE               |
      | WELLCARE                  |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical             |
      | START DATE | firstdayofNextMonth |
      | END DATE   | -- --               |
    Then I verify "PCP SELECT BUTTON" is not displayed
    When I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # 1.1  Update CURRENT ENROLLMENT END DATE afterSUBMIT button (has been clicked) on FUTURE Enrollment Segment - Baseline
    Then I verify program & benefit info page for consumer first name "29502-01.firstName" and last name "29502-01.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS                        | Selection Made        |
      | FUTURE ENROLLMENT.START DATE                              | firstdayofNextMonth   |
      | FUTURE ENROLLMENT.END DATE                                | -- --                 |
      | FUTURE ENROLLMENT.EDIT BUTTON                             | displayed             |
      | FUTURE ENROLLMENT.DISREGARD BUTTON                        | displayed             |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.SELECTION STATUS | Disenroll Requested   |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.START DATE       | firstdayofNextMonth   |
      | FUTURE ENROLLMENT.DiscontinuedEnrollment.END DATE         | lastDayofpresentMonth |
    And Wait for 20 seconds
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | 29502-01.consumerId   |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    Then I will verify business events are generated with data
      # 3.0 Plan Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE                          |
      | channel                                | PHONE                                |
#      | createdBy                              | 1066                                 |
      | processDate                            | current                              |
      | externalCaseId                         | 29502-01.caseIdentificationNumber    |
      | externalConsumerId                     | 29502-01.externalConsumerId          |
      | consumerId                             | 29502-01.consumerId                  |
      | consumerName                           | 29502-01                             |
      # 3.0 Plan Change Business Event Required Fields Optional Enrollment fields from “Prior Future Enrollment”
      | previousEnrollment.enrollmentStartDate | 1stDayofNextMonth                                       |
      | previousEnrollment.enrollmentEndDate   | lastDayofPresentMonth                                   |
      | previousEnrollment.planSelectionReason | null                                                    |
      | previousEnrollment.planChangeReason    | REASON_MEMBER_GAVE_IS_NOT_RELATED_TO_ANY_AVAILABLE_CODE |
      | previousEnrollment.rejectionReason     | null                                                    |
      | previousEnrollment.planCode            | 85                                                      |
      | previousEnrollment.enrollmentType      | MEDICAL                                                 |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED                                     |
      | previousEnrollment.anniversaryDate     | anniversaryDate                                         |
      | previousEnrollment.pcpNpi              | null                                                    |
      | previousEnrollment.pcpName             | null                                                    |
      # 3.0 Plan Change Business Event Required Fields Optional Enrollment fields from “Requested Future Enrollment”
      | requestedSelection.enrollmentStartDate | 1stDayofNextMonth                    |
      | requestedSelection.enrollmentEndDate   | null                                 |
      | requestedSelection.planSelectionReason | null                                 |
      | requestedSelection.planChangeReason    | null                                 |
      | requestedSelection.rejectionReason     | null                                 |
      | requestedSelection.planCode            | getFromUISelected                    |
      | requestedSelection.enrollmentType      | MEDICAL                              |
      | requestedSelection.selectionStatus     | SELECTION_MADE                       |
      | requestedSelection.anniversaryDate     | anniversaryDate                      |
      | requestedSelection.pcpNpi              | null                                 |
      | requestedSelection.pcpName             | null                                 |

  @CP-14249 @CP-23098 @ui-ee @crm-regression @ee-UI2API @sobir
  Scenario: CP-14249 BLCRM Decide Consumer Actions - Plan Change Open Enrollment
  CP-23098 BLCRM Initiate Plan Change Open Enrollment - Story #1
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 14249-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14249-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 3                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | txnStatus                    | Accepted              |
      | programCode                  | H                     |
      | anniversaryDate              | anniversaryDate       |
      | subProgramTypeCode           | MEDICAIDMCHB          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14249-1.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof3MonthsBefore |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted              |
      | programCode                  | H                     |
      | subProgramTypeCode           | MEDICAIDMCHB          |
      | planCode                     | 84                    |
      | planId                       | 145                   |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "14249-1.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with benefit status "Enrolled"
    And I update latest consumer actions by consumerId "14249-1.consumerId" with data
      | [0].consumerAction | Plan Change Pre-lockin |
      | [0].action         | delete                 |
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "14249-1.firstName" and Last Name as "14249-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "14249-1.firstName" and last name "14249-1.lastName" with data
      # CP-14249 1.0 Determine “Plan Change Open Enrollment” Consumer Action for all Program-Populations
      | CALENDAR ICON HOVER.ACTION TEXT       | OPEN ENROLLMENT - WINDOW                                                                   |
      | CALENDAR ICON HOVER.RED DOT           | according to action timeframe: openEnrollmentDayUIver - openEnrollmentDueDayUIver          |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | openEnrollmentDayUIver - openEnrollmentDueDayUIver                                         |
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | according to action timeframe (with -): openEnrollmentDayUIver - openEnrollmentDueDayUIver |
      | COUNTDOWN.ICON HOVER                  | Calendar days left to change plan for open enrollment                                      |
      # CP-14249 2.0 Display Plan Change Button on the User Interface (UI)
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | according to action timeframe: openEnrollmentDayUIver - openEnrollmentDueDayUIver          |

  @CP-23098 @ui-ee @crm-regression @ee-UI2API @sobir
  Scenario: BLCRM Initiate Plan Change Open Enrollment - Story #1 (Open enrollment action validation)
  We have to make Open enrollment action available now for tetsting
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 23098-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23098-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 3                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | txnStatus                    | Accepted              |
      | programCode                  | H                     |
      | anniversaryDate              | anniversaryDate       |
      | subProgramTypeCode           | MEDICAIDMCHB          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23098-1.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof3MonthsBefore |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted              |
      | programCode                  | H                     |
      | subProgramTypeCode           | MEDICAIDMCHB          |
      | planCode                     | 84                    |
      | planId                       | 145                   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I update latest consumer actions by consumerId "23098-1.consumerId" with data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth        |
      | [0].changeAllowedUntil   | lastDayofPresentMonth       |
      | [1].consumerAction       | Plan Change Pre-lockin      |
      | [1].action               | delete                      |
    Given I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 23098-2 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23098-2.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 3                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | txnStatus                    | Accepted              |
      | programCode                  | H                     |
      | anniversaryDate              | anniversaryDate       |
      | subProgramTypeCode           | MEDICAIDMCHB          |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 23098-2.consumerId    |
      | isEligibilityRequired        | no                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof3MonthsBefore |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted              |
      | programCode                  | H                     |
      | subProgramTypeCode           | MEDICAIDMCHB          |
      | planCode                     | 84                    |
      | planId                       | 145                   |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I update latest consumer actions by consumerId "23098-2.consumerId" with data
      | [0].action               | Available                   |
      | [0].consumerAction       | Plan Change Open Enrollment |
      | [0].planSelectionAllowed | true                        |
      | [0].changeAllowedFrom    | 1stDayofPresentMonth        |
      | [0].changeAllowedUntil   | lastDayofPresentMonth       |
      | [1].consumerAction       | Plan Change Pre-lockin      |
      | [1].action               | delete                      |
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "23098-1.firstName" and Last Name as "23098-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    # CP-23098 7.0 Removing a case member from selection dropdown
    When I click "PLAN CHANGE" Button for a consumer first name "23098-1.firstName" and last name "23098-1.lastName"
    Then I verify I am still on "Enrollment Update" Page
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 23098-2 |
    When I click Add Case Members with First Name as "23098-2.firstName" and Last Name as "23098-2.lastName"
    # CP-23098 8.0 Hiding the option to add additional clients
    Then I verify  the Add Case Members button is disable
    When I click Remove Button for Case Member First Name as "23098-2.firstName" and Last Name as "23098-2.lastName"
    Then I verivy consumer by First Name as "14249-2.firstName" and Last Name as "14249-2.lastName" is not in plan selection
    # CP-23098 9.0 Access available plans
    Then I verify list of all available plans on Enrollment Update Page with data
      | AMERIGROUP COMMUNITY CARE |
      | CARESOURCE GEORGIA        |
      | PEACH STATE               |
      | WELLCARE                  |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                 |
      | START DATE | firstDayOfNextYearUIver |
      | END DATE   | -- --                   |
    # CP-23098 10.0 Select plan
    When I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    # CP-23098 11.0 Display Program & Benefit screen with save changes after clicking “Submit” button
    Then I verify program & benefit info page for consumer first name "23098-1.firstName" and last name "23098-1.lastName" with data
      | CURRENT ENROLLMENT.PLAN NAME          | AMERIGROUP COMMUNITY CARE    |
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested          |
      | CURRENT ENROLLMENT.CHANNEL            | System Integration           |
      | CURRENT ENROLLMENT.PCP NAME           | -- --                        |
      | CURRENT ENROLLMENT.START DATE         | 1stDayof3MonthsBeforeUIver   |
      | CURRENT ENROLLMENT.END DATE           | lastDayOfThePresentYearUIver |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON  | hidden                       |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                       |
      #****************************
      | FUTURE ENROLLMENT.PLAN NAME           | selectedPlanName             |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made               |
      | FUTURE ENROLLMENT.CHANNEL             | Phone                        |
      | FUTURE ENROLLMENT.START DATE          | firstDayOfNextYearUIver      |
      | FUTURE ENROLLMENT.END DATE            | -- --                        |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed                    |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed                    |

  @CP-14249 @ui-ee @crm-regression @ee-UI2API @sobir
  Scenario: BLCRM Decide Consumer Actions - Plan Change Open Enrollment (negative)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 14249-2 |
      | age              | 25      |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14249-2.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                 |
      | recordId                     | 3                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | past               |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | anniversaryDate              | anniversaryDate    |
      | subProgramTypeCode           | MEDICAIDGF         |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14249-2.consumerId |
      | isEligibilityRequired        | no                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | recordId                     | 4                  |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | past               |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted           |
      | programCode                  | H                  |
      | subProgramTypeCode           | MEDICAIDGF         |
      | planCode                     | 84                 |
      | planId                       | 145                |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 14249-2.consumerId |
      | isEligibilityRequired        | yes                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                |
      | recordId                     | 14                 |
      | isEnrollmentProviderRequired | no                 |
      | enrollmentStartDate          | past               |
      | eligibilityStartDate         | past               |
      | eligibilityEndDate           | future             |
      | enrollmentEndDate            | future             |
      | planEndDateReason            | test               |
      | eligibilityEndReason         | test               |
      | txnStatus                    | Accepted           |
      | programCode                  | M                  |
      | subProgramTypeCode           | MEDICAIDGF         |
      | planCode                     | 84                 |
      | planId                       | 145                |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id "14249-2.consumerId"
    And I run get enrollment api
    And I verify benefit status records are displayed with benefit status "Not Eligible"
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "14249-2.firstName" and Last Name as "14249-2.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "14249-2.firstName" and last name "14249-2.lastName" with data
      # 1.0 Determine “Plan Change Open Enrollment” Consumer Action for all Program-Populations
      | CALENDAR ICON HOVER.ACTION TEXT       | -- --  |
      | CALENDAR ICON HOVER.IMPORTANT DATES   | -- --  |
      | COUNTDOWN.NUMBER OF DAYS UNTIL        | -- --  |
      | COUNTDOWN.ICON HOVER                  | -- --  |
      # 2.0 Display Plan Change Button on the User Interface (UI)
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden |

  @CP-47457 @CP-47457-02 @ui-ee-dc @crm-regression @BLCRM-UI-Regression @deepa
  Scenario: BLCRM: Create Business Event - Plan Change Override
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 47457 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                   |
      | otherSegments                | [empty]               |
      | isEnrollemntRequired         | no                    |
      | recordId                     | 3                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | [empty]               |
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | txnStatus                    | Accepted              |
      | programCode                  | M                     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                    |
      | otherSegments                | [empty]               |
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof3MonthsBefore |
      | eligibilityStartDate         | [empty]               |
      | txnStatus                    | Accepted              |
      | programCode                  | M                     |
      | planCode                     | 84                    |
      | planId                       | 1895                  |
      | anniversaryDate              | anniversaryDate       |
    And I run create Eligibility and Enrollment API
    And I update consumers benefit by consumerId "47457.consumerId" with data
      | [0].timeframe          | Active                 |
      | [0].consumerAction     | Plan Change Pre-lockin |
      | [0].changeAllowedFrom  | 1stDayof2MonthsBefore  |
      | [0].changeAllowedUntil | yesterday              |
    Given I logged into CRM with specific role "Service Account 1" and select a project "BLCRM" and select a role "Supervisor"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "<name>.firstName" and Last Name as "<name>.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PLAN CHANGE" Button for a consumer first name "47457.firstName" and last name "47457.lastName"
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I will verify business events are generated with data
      | eventName                              | PLAN_CHANGE_OVERRIDE           |
      | channel                                | PHONE                          |
      | createdBy                              | 1066                           |
      | processDate                            | current                        |
      | consumerId                             | 47457.consumerId               |
      | consumerName                           | 47457                          |
      # Prior Segment - Plan Change
      | previousEnrollment.enrollmentStartDate | 1stDayof3MonthsBefore          |
      | previousEnrollment.enrollmentEndDate   | DayBeforeSelectedPlanStartDate |
      | previousEnrollment.rejectionReason     | null                           |
      | previousEnrollment.planCode            | 84                             |
      | previousEnrollment.enrollmentType      | MEDICAL                        |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED            |
      | previousEnrollment.anniversaryDate     | anniversaryDate                |
      | previousEnrollment.pcpNpi              | null                           |
      | previousEnrollment.pcpName             | null                           |
      # Requested Segment - Plan Change
      | requestedSelection.enrollmentStartDate | getFromUISelected              |
      | requestedSelection.enrollmentEndDate   | null                           |
      | requestedSelection.enrollmentType      | MEDICAL                        |
      | requestedSelection.selectionStatus     | SELECTION_MADE                 |
      | requestedSelection.planCode            | 87                             |
      | requestedSelection.anniversaryDate     | 1stDayof10MonthsFromNow        |
      | requestedSelection.planSelectionReason | null                           |
      | requestedSelection.planChangeReason    | null                           |
      | requestedSelection.rejectionReason     | null                           |
      | requestedSelection.pcpNpi              | null                           |
      | requestedSelection.pcpName             | null                           |

  @CP-47702 @CP-47702-02 @ui-ee-dc @crm-regression @BLCRM-UI-Regression @deepa
  Scenario: BLCRM:Linked Objects Update to Plan Change Override
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 47702 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                   |
      | otherSegments                | [empty]               |
      | isEnrollemntRequired         | no                    |
      | recordId                     | 3                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | [empty]               |
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | txnStatus                    | Accepted              |
      | programCode                  | M                     |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                    |
      | otherSegments                | [empty]               |
      | isEnrollemntRequired         | yes                   |
      | recordId                     | 4                     |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          | 1stDayof3MonthsBefore |
      | eligibilityStartDate         | [empty]               |
      | txnStatus                    | Accepted              |
      | programCode                  | M                     |
      | planCode                     | 84                    |
      | planId                       | 1895                  |
      | anniversaryDate              | anniversaryDate       |
    And I run create Eligibility and Enrollment API
    And I update consumers benefit by consumerId "47702.consumerId" with data
      | [0].timeframe          | Active                 |
      | [0].consumerAction     | Plan Change Pre-lockin |
      | [0].changeAllowedFrom  | 1stDayof2MonthsBefore  |
      | [0].changeAllowedUntil | yesterday              |
    Given I logged into CRM with specific role "Service Account 1" and select a project "BLCRM" and select a role "Supervisor"
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<name>.firstName" and Last Name as "<name>.lastName"
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PLAN CHANGE" Button for a consumer first name "47702.firstName" and last name "47702.lastName"
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I will verify business events are generated with data
      | eventName                              | PLAN_CHANGE_OVERRIDE           |
      | channel                                | PHONE                          |
      | createdBy                              | 1066                           |
      | processDate                            | current                        |
      | consumerId                             | 47702.consumerId               |
      | consumerName                           | 47702                          |
      | contactId                              | NotNull                        |
      # Prior Segment - Plan Change
      | previousEnrollment.enrollmentStartDate | 1stDayof3MonthsBefore          |
      | previousEnrollment.enrollmentEndDate   | DayBeforeSelectedPlanStartDate |
      | previousEnrollment.rejectionReason     | null                           |
      | previousEnrollment.planCode            | 84                             |
      | previousEnrollment.enrollmentType      | MEDICAL                        |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED            |
      | previousEnrollment.anniversaryDate     | anniversaryDate                |
      | previousEnrollment.pcpNpi              | null                           |
      | previousEnrollment.pcpName             | null                           |
      # Requested Segment - Plan Change
      | requestedSelection.enrollmentStartDate | getFromUISelected              |
      | requestedSelection.enrollmentEndDate   | null                           |
      | requestedSelection.enrollmentType      | MEDICAL                        |
      | requestedSelection.selectionStatus     | SELECTION_MADE                 |
      | requestedSelection.planCode            | 87                             |
      | requestedSelection.anniversaryDate     | 1stDayof10MonthsFromNow        |
      | requestedSelection.planSelectionReason | null                           |
      | requestedSelection.planChangeReason    | null                           |
      | requestedSelection.rejectionReason     | null                           |
      | requestedSelection.pcpNpi              | null                           |
      | requestedSelection.pcpName             | null                           |