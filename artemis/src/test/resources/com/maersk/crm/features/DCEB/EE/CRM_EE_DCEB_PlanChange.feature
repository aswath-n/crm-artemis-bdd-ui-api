Feature: DC-EB Plan Change

  @CP-41972 @CP-41991-01 @CP-41992 @CP-39454 @CP-39454-01 @priyal @ui-ee-dc @crm-regression @DC-EB-UI-Regression @kursat
  Scenario Outline: DC EB Alliance & Imm. Children: View, Select and Submit Plan Change Reason for Pre-lockin & Anniversary
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofLastMonth      |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | highDate-DC            |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | coverageCode                 | <coverageCode>         |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<name>a" for update Enrollment information
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 145                  |
      | [0].planCode           | <planCode>           |
      | [0].startDate          | <startDate>          |
      | [0].endDate            | highdatedc::         |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Northeast            |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<name>a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | <startDate>            |
      | [0].planId       | delete::               |
      | [0].planCode     | <planCode>             |
      | [0].consumerId   | <name>.consumerId      |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | <planStartDate>      |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID             |
      | planCode                     | <planCode>           |
      | planId                       | 145                  |
      | subProgramTypeCode           | <subProgramTypeCode> |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "<name>.firstName" and Last Name as "<name>.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PLAN CHANGE" Button for a consumer first name "<name>.firstName" and last name "<name>.lastName"
#    AC1.0  View Alliance or Immigrant Children Plans
#    2.0 View Alliance or Immigrant Children Plans, CP-39454
    Then I verify list of all available plans on Enrollment Update Page with data
      | AMERIHEALTH CARITAS DC             |
      | CAREFIRST COMMUNITY HEALTH PLAN DC |
      | MEDSTAR FAMILY CHOICE              |
    Then I verify below details on Plans Available segment on Enrollment Update Page
      | PLAN TYPE  | Medical                                                  |
      | START DATE | cutoffStartDateImmigrantChildrenWithUIPlanSelectionUIver |
      | END DATE   | highDateUIver-DC                                         |
    And I select "<updatedPlan>" from Available Plans
    And I click on Remove Plan Option
    Then I verify I am still on "Enrollment Update" Page
    And I select "<updatedPlan>" from Available Plans
    # Require Reason for Plan Change
#    5.0 Select a Plan Change Reason, CP-39454
    Then I verify "SUBMIT" button is not clickable
    Then I will be required to enter a “Reason” for the change
    And I click "Reason" Button for a consumer
    And I verify bellow fields are displayed on "drop down reason" section:
      | <reason> |
    And I click "Reason" Button for a consumer
#     Display Selected Plan(s)
    Then I verify below details on newly created current enrollment segment on Enrollment Update Page
      | PLAN NAME      | selectedPlanName                                         |
      | PLAN TYPE      | Medical                                                  |
      | SERVICE REGION | Northeast                                                |
      | START DATE     | cutoffStartDateImmigrantChildrenWithUIPlanSelectionUIver |
      | END DATE       | highDateUIver-DC                                         |
    Then I verify "PCP SELECT BUTTON" is not displayed
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    Then I verify I am still on "PROGRAM & BENEFIT INFO" Page
    Then I verify program & benefit info page for consumer first name "<name>.firstName" and last name "<name>.lastName" with data
      | CURRENT ENROLLMENT.SELECTION STATUS   | Disenroll Requested      |
      | CURRENT ENROLLMENT.END DATE           | <planEndDateUI>          |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON | hidden                   |
      | FUTURE ENROLLMENT.SELECTION STATUS    | Selection Made           |
      | FUTURE ENROLLMENT.START DATE          | <updatedPlanStartDateUI> |
      | FUTURE ENROLLMENT.END DATE            | highDateUIver-DC         |
      | FUTURE ENROLLMENT.EDIT BUTTON         | displayed                |
      | FUTURE ENROLLMENT.DISREGARD BUTTON    | displayed                |
#    # CP-41992  AC1.0 Update each consumer's prior enrollment segment
    And I verify enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | <planStartDate>     |
      | medicalPlanEndDate   | <planEndDate>       |
      | planEndDateReason    | <planEndDateReason> |
      | enrollmentProviders  | null                |
      | updatedOn            | current             |
#      | updatedBy            | 9056                |
      | txnStatus            | DISENROLL_REQUESTED |
      | enrollmentType       | MEDICAL             |
      | channel              | PHONE               |
      | status               | DISENROLL_REQUESTED |
#    9.0 Update each consumer's prior enrollment segment, CP-39454
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_UPDATE_EVENT |
      | consumerId    | <name>.consumerId       |
      | correlationId | null                    |
      | consumerFound | true                    |
      | DPBI          |[blank]|
    # CP-41992 AC2.0 Create each consumer’s new (requested) enrollment segment
    And I verify latest enrollment by consumer id "<name>.consumerId" with data
      | medicalPlanBeginDate | <updatedPlanStartDate> |
      | medicalPlanEndDate   | highDate-DC            |
      | enrollmentType       | MEDICAL                |
      | channel              | PHONE                  |
      | planCode             | getFromUISelected      |
      | txnStatus            | SELECTION_MADE         |
      | status               | SELECTION_MADE         |
      | createdOn            | current                |
      | updatedOn            | current                |
#      | createdBy            | 9056                   |
#   10.0 Create each consumer’s new (requested) enrollment segment, CP-39454
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT |
      | consumerId    | <name>.consumerId     |
      | correlationId | null                  |
      | consumerFound | true                  |
      | DPBI          |[blank]|
    And Wait for 5 seconds
    Then I will verify business events are generated with data
    # CP-41991 1.0 Plan Change Business Event Required Fields
      | eventName                              | PLAN_CHANGE            |
      | channel                                | PHONE                  |
#      | createdBy                              | 9056                   |
      | processDate                            | current                |
      | consumerId                             | <name>.consumerId      |
      | consumerName                           | <name>                 |
    # CP-41991 2.0 Plan Change Business Event Optional Enrollment fields from “Prior Enrollment”
      | previousEnrollment.enrollmentStartDate | <planStartDate>        |
      | previousEnrollment.enrollmentEndDate   | <planEndDate>          |
      | previousEnrollment.planSelectionReason | null                   |
      | previousEnrollment.planChangeReason    | <planEndDateReason>    |
      | previousEnrollment.rejectionReason     | null                   |
      | previousEnrollment.planCode            | <planCode>             |
      | previousEnrollment.enrollmentType      | MEDICAL                |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED    |
      | previousEnrollment.anniversaryDate     | <anniversaryDate>      |
      | previousEnrollment.pcpNpi              | null                   |
      | previousEnrollment.pcpName             | null                   |
      | previousEnrollment.pdpNpi              | null                   |
      | previousEnrollment.pdpName             | null                   |
      # CP-41991 3.0 Plan Change Business Event Optional Enrollment fields from “Requested Enrollment”
      | requestedSelection.enrollmentStartDate | <updatedPlanStartDate> |
      | requestedSelection.enrollmentEndDate   | highDate-DC            |
      | requestedSelection.planSelectionReason | null                   |
      | requestedSelection.planChangeReason    | null                   |
      | requestedSelection.rejectionReason     | null                   |
      | requestedSelection.planCode            | getFromUISelected      |
      | requestedSelection.enrollmentType      | MEDICAL                |
      | requestedSelection.selectionStatus     | SELECTION_MADE         |
      | requestedSelection.anniversaryDate     | <anniversaryDate>      |
      | requestedSelection.pcpNpi              | null                   |
      | requestedSelection.pcpName             | null                   |
      | requestedSelection.pdpNpi              | null                   |
      | requestedSelection.pdpName             | null                   |
    Examples:
      | name     | eligibilityStartDate   | subProgramTypeCode | coverageCode | startDate                | planCode  | updatedPlan                        | planStartDate          | planEndDate                                                  | planEndDateUI                                                     | updatedPlanStartDate                                | updatedPlanStartDateUI                                   | planEndDateReason | reason                          | anniversaryDate      |
      | 41972-01 | 1stDayofLastMonth      | ImmigrantChildren  | 420          | fdofmnth::               | 081080400 | CAREFIRST COMMUNITY HEALTH PLAN DC | 1stDayofPresentMonth   | DayBeforeCutOffStartDateImmigrantChildrenWithUIPlanSelection | DayBeforeCutOffStartDateImmigrantChildrenWithUIPlanSelectionUIver | cutoffStartDateImmigrantChildrenWithUIPlanSelection | cutoffStartDateImmigrantChildrenWithUIPlanSelectionUIver | 90_DAY_TRANSFER   | 90 day transfer                 | anniversaryDateDC    |
      | 41972-02 | 1stDayof12MonthsBefore | ImmigrantChildren  | 420F         | 1stdayof12monthsbefore:: | 081080400 | CAREFIRST COMMUNITY HEALTH PLAN DC | 1stDayof12MonthsBefore | DayBeforeCutOffStartDateImmigrantChildrenWithUIPlanSelection | DayBeforeCutOffStartDateImmigrantChildrenWithUIPlanSelectionUIver | cutoffStartDateImmigrantChildrenWithUIPlanSelection | cutoffStartDateImmigrantChildrenWithUIPlanSelectionUIver | ANNUAL_TRANSFER   | Annual right to change transfer | 1stDayofPresentMonth |
      | 41972-03 | 1stDayofLastMonth      | Alliance           | 460          | fdofmnth::               | 087358900 | CAREFIRST COMMUNITY HEALTH PLAN DC | 1stDayofPresentMonth   | DayBeforeCutOffStartDateImmigrantChildrenWithUIPlanSelection | DayBeforeCutOffStartDateImmigrantChildrenWithUIPlanSelectionUIver | cutoffStartDateImmigrantChildrenWithUIPlanSelection | cutoffStartDateImmigrantChildrenWithUIPlanSelectionUIver | 90_DAY_TRANSFER   | 90 day transfer                 | anniversaryDateDC    |
      | 41972-04 | 1stDayof12MonthsBefore | Alliance           | 470          | 1stdayof12monthsbefore:: | 087358900 | CAREFIRST COMMUNITY HEALTH PLAN DC | 1stDayof12MonthsBefore | DayBeforeCutOffStartDateImmigrantChildrenWithUIPlanSelection | DayBeforeCutOffStartDateImmigrantChildrenWithUIPlanSelectionUIver | cutoffStartDateImmigrantChildrenWithUIPlanSelection | cutoffStartDateImmigrantChildrenWithUIPlanSelectionUIver | ANNUAL_TRANSFER   | Annual right to change transfer | 1stDayofPresentMonth |

  @CP-47418  @ui-ee-dc @crm-regression @DC-EB-UI-Regression @thilak
  Scenario Outline: DC EB: UI - For Cause Plan Selection Reasons and Codes
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId      |
      | isEligibilityRequired        | yes                    |
      | otherSegments                | [blank]                |
      | isEnrollemntRequired         | no                     |
      | recordId                     | 25                     |
      | eligibilityRecordId          | 25                     |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofLastMonth      |
      | eligibilityStartDate         | <eligibilityStartDate> |
      | eligibilityEndDate           | highDate-DC            |
      | txnStatus                    | Accepted               |
      | programCode                  | MEDICAID               |
      | subProgramTypeCode           | <subProgramTypeCode>   |
      | coverageCode                 | <coverageCode>         |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<name>" for update Enrollment information
      | [0].consumerId         | <name>.consumerId    |
      | [0].planId             | 145                  |
      | [0].planCode           | <planCode>           |
      | [0].startDate          | <startDate>          |
      | [0].endDate            | highdatedc::         |
      | [0].programTypeCode    | MEDICAID             |
      | [0].subProgramTypeCode | <subProgramTypeCode> |
      | [0].serviceRegionCode  | Northeast            |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<name>" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | <startDate>            |
      | [0].planId       | delete::               |
      | [0].planCode     | <planCode>             |
      | [0].consumerId   | <name>.consumerId      |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <name>.consumerId    |
      | isEligibilityRequired        | no                   |
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | <planStartDate>      |
      | enrollmentEndDate            | highDate-DC          |
      | anniversaryDate              | anniversaryDate      |
      | eligibilityStartDate         | [blank]              |
      | programCode                  | MEDICAID             |
      | planCode                     | <planCode>           |
      | planId                       | 145                  |
      | subProgramTypeCode           | <subProgramTypeCode> |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<name>.firstName",Last Name as "<name>..lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "PLAN CHANGE" button is displayed
    And I click "PLAN CHANGE" Button for a consumer first name "<name>.firstName" and last name "<name>.lastName"
    And I select "MEDSTAR FAMILY CHOICE" from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And I click "EDIT" Button for a consumer first name "<name>.firstName" and last name "<name>.lastName"
    Then I verify dropdown values available for reason dropdown
      | Open Enrollment Transfer                      |
      | Poor Relationship with MCO                    |
      | Not Satisfied with MCO                        |
      | Lack of Access to covered Services            |
      | Dissatisfied with Services Provided by PCP    |
      | Lack of Access to Quality Providers           |
      | Preferred PCP in other MCO                    |
      | Lack of Experienced Providers with Specialty  |
      | Problems with MCO Transportation              |
      | Prescription/Pharmacy Related Problems        |
      | Unable to get Specialist Referral             |
      | Preferred Providers not taking New Patients   |
      | Preferred Providers not in MCO's Network      |
      | Available PCPs too far from residence         |
      | Long Wait for Appointments                    |
      | Language Barriers with Provider               |
      | PCP Does Not Treat Condition                  |
      | Changing Auto Assignment                      |
      | Hospital/Clinic Does Not Participate with MCO |
      | Other                                         |
      | Auto Enroll                                   |
      | Provider continuity                           |
      | Newborn                                       |
      | Voluntary                                     |
      | Retro Newborn                                 |
    And I select "Not Satisfied with MCO" from reason Dropdown
    And I click submit button on enrollment update
    Examples:
      | name     | eligibilityStartDate   | subProgramTypeCode | coverageCode | startDate                | planCode  | planStartDate          | reason                          |
      | 47418-01 | 1stDayofLastMonth      | ImmigrantChildren  | 420          | fdofmnth::               | 081080400 | 1stDayofPresentMonth   | 90 day transfer                 |
      | 47418-02 | 1stDayof12MonthsBefore | ImmigrantChildren  | 420          | 1stdayof12monthsbefore:: | 081080400 | 1stDayof12MonthsBefore | Annual right to change transfer |

  @CP-47457 @CP-47457-01 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @deepa
  Scenario Outline: DC EB: Create Business Event - Plan Change Override
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                | [empty]               |
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate-DC           |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | null                  |
    And I run create Eligibility and Enrollment API
    And I update consumers benefit by consumerId "<name>.consumerId " with data
      | [0].timeframe          | Active                 |
      | [0].consumerAction     | Plan Change Pre-lockin |
      | [0].changeAllowedFrom  | 1stDayof2MonthsBefore  |
      | [0].changeAllowedUntil | yesterday              |
    Given I initiated enrollment letter API
    Given I logged into CRM with specific role "Service Account 1" and select a project "DC-EB" and select a role "Supervisor"
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<consumerId>.firstName",Last Name as "<consumerId>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PLAN CHANGE" Button for a consumer first name "<name>.firstName" and last name "<name>.lastName"
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName                              | PLAN_CHANGE_OVERRIDE           |
      | channel                                | PHONE                          |
      | createdBy                              | 9547                           |
      | processDate                            | current                        |
      | consumerId                             | <name>.consumerId              |
      | consumerName                           | <name>                         |
      # Prior Segment - Plan Change
      | previousEnrollment.enrollmentStartDate | 1stDayof2MonthsBefore          |
      | previousEnrollment.enrollmentEndDate   | DayBeforeSelectedPlanStartDate |
      | previousEnrollment.planChangeReason    | 8V                             |
      | previousEnrollment.rejectionReason     | null                           |
      | previousEnrollment.planCode            | <planCode>                     |
      | previousEnrollment.enrollmentType      | MEDICAL                        |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED            |
      | previousEnrollment.anniversaryDate     | 1stDayof10MonthsFromNow        |
      | previousEnrollment.pcpNpi              | null                           |
      | previousEnrollment.pcpName             | null                           |
      | previousEnrollment.pdpNpi              | null                           |
      | previousEnrollment.pdpName             | null                           |
      # Requested Segment - Plan Change
      | requestedSelection.enrollmentStartDate | getFromUISelected              |
      | requestedSelection.enrollmentEndDate   | highDate-DC                    |
      | requestedSelection.planSelectionReason | null                           |
      | requestedSelection.planChangeReason    | null                           |
      | requestedSelection.rejectionReason     | null                           |
      | requestedSelection.planCode            | <planCode2>                    |
      | requestedSelection.enrollmentType      | MEDICAL                        |
      | requestedSelection.selectionStatus     | SELECTION_MADE                 |
      | requestedSelection.anniversaryDate     | 1stDayof10MonthsFromNow        |
      | requestedSelection.pcpNpi              | null                           |
      | requestedSelection.pcpName             | null                           |
      | requestedSelection.pdpNpi              | null                           |
      | requestedSelection.pdpName             | null                           |


    Examples:
      | name     | coverageCode | subProgramTypeCode | planCode  | planCode2 |
      | 47457-01 | 130          | DCHF               | 081080400 | 055558200 |
      | 47457-02 | 420          | ImmigrantChildren  | 081080400 | 055558200 |
      | 47457-03 | 460          | Alliance           | 087358900 | 077573200 |

  @CP-47702 @CP-47702-01 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @deepa
  Scenario Outline: DC EB:Linked Objects Update to Plan Change Override
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <name> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | coverageCode                 | <coverageCode>        |
      | consumerId                   | <name>.consumerId     |
      | isEligibilityRequired        | yes                   |
      | otherSegments                | [empty]               |
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 25                    |
      | eligibilityStartDate         | 1stDayof3MonthsBefore |
      | eligibilityEndDate           | highDate-DC           |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate-DC           |
      | txnStatus                    | Accepted              |
      | programCode                  | MEDICAID              |
      | subProgramTypeCode           | <subProgramTypeCode>  |
      | categoryCode                 | 10                    |
      | planCode                     | <planCode>            |
      | planId                       | 104                   |
      | serviceRegionCode            | Northeast             |
      | anniversaryDate              | anniversaryDate       |
      | channel                      | SYSTEM_INTEGRATION    |
      | selectionReason              | null                  |
    And I run create Eligibility and Enrollment API
    And I update consumers benefit by consumerId "<name>.consumerId " with data
      | [0].timeframe          | Active                 |
      | [0].consumerAction     | Plan Change Pre-lockin |
      | [0].changeAllowedFrom  | 1stDayof2MonthsBefore  |
      | [0].changeAllowedUntil | yesterday              |
    Given I initiated enrollment letter API
    Given I logged into CRM with specific role "Service Account 1" and select a project "DC-EB" and select a role "Supervisor"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "<name>.firstName" and Last Name as "<name>.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PLAN CHANGE" Button for a consumer first name "<name>.firstName" and last name "<name>.lastName"
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And Wait for 3 seconds
    Then I will verify business events are generated with data
      | eventName                              | PLAN_CHANGE_OVERRIDE           |
      | channel                                | PHONE                          |
      | createdBy                              | 9056                           |
      | processDate                            | current                        |
      | consumerId                             | <name>.consumerId              |
      | consumerName                           | <name>                         |
      | contactId                              | NotNull                        |
      # Prior Segment - Plan Change
      | previousEnrollment.enrollmentStartDate | 1stDayof2MonthsBefore          |
      | previousEnrollment.enrollmentEndDate   | DayBeforeSelectedPlanStartDate |
      | previousEnrollment.planChangeReason    | 8V                             |
      | previousEnrollment.rejectionReason     | null                           |
      | previousEnrollment.planCode            | <planCode>                     |
      | previousEnrollment.enrollmentType      | MEDICAL                        |
      | previousEnrollment.selectionStatus     | DISENROLL_REQUESTED            |
      | previousEnrollment.anniversaryDate     | 1stDayof10MonthsFromNow        |
      | previousEnrollment.pcpNpi              | null                           |
      | previousEnrollment.pcpName             | null                           |
      | previousEnrollment.pdpNpi              | null                           |
      | previousEnrollment.pdpName             | null                           |
      # Requested Segment - Plan Change
      | requestedSelection.enrollmentStartDate | getFromUISelected              |
      | requestedSelection.enrollmentEndDate   | highDate-DC                    |
      | requestedSelection.planSelectionReason | null                           |
      | requestedSelection.planChangeReason    | null                           |
      | requestedSelection.rejectionReason     | null                           |
      | requestedSelection.planCode            | <planCode2>                    |
      | requestedSelection.enrollmentType      | MEDICAL                        |
      | requestedSelection.selectionStatus     | SELECTION_MADE                 |
      | requestedSelection.anniversaryDate     | 1stDayof10MonthsFromNow        |
      | requestedSelection.pcpNpi              | null                           |
      | requestedSelection.pcpName             | null                           |
      | requestedSelection.pdpNpi              | null                           |
      | requestedSelection.pdpName             | null                           |


    Examples:
      | name     | coverageCode | subProgramTypeCode | planCode  | planCode2 |
      | 47702-01 | 130          | DCHF               | 081080400 | 055558200 |
      | 47702-02 | 420          | ImmigrantChildren  | 081080400 | 055558200 |
      | 47702-03 | 460          | Alliance           | 087358900 | 077573200 |