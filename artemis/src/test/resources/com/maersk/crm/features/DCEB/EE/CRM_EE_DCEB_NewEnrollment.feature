Feature: New Enrollment for DCEB - UI


  @CP-39448 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @kursat
  Scenario: DC EB Decide Scenario 15 for New Enrollment Accept Response for Immigrant Children
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39448-01 |
      | newCaseCreation  | yes      |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39448-01.consumerId |
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
    Then I send API call with name "39448-01a" for update Enrollment information
      | [0].consumerId         | 39448-01.consumerId                |
      | [0].channel            | AUTO_ASSIGNMENT                    |
      | [0].planId             | 145                                |
      | [0].planCode           | 081080400                          |
      | [0].startDate          | cutoffstartdateimmigrantchildren:: |
      | [0].endDate            | highdatedc::                       |
      | [0].programTypeCode    | MEDICAID                           |
      | [0].subProgramTypeCode | ImmigrantChildren                  |
      | [0].serviceRegionCode  | Northeast                          |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39448-01a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION                 |
      | [0].startDate    | cutoffstartdateimmigrantchildren:: |
      | [0].planId       | delete::                           |
      | [0].planCode     | 081080400                          |
      | [0].consumerId   | 39448-01.consumerId                |
      | [0].enrollmentId | c.data[0].enrollmentId             |
      | [0].status       | SUBMITTED_TO_STATE                 |
      | [0].txnStatus    | SUBMITTED_TO_STATE                 |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39448-01.consumerId              |
      | isEligibilityRequired        | no                               |
      | isEnrollemntRequired         | yes                              |
      | isEnrollmentProviderRequired | no                               |
      | recordId                     | 15                               |
      | enrollmentRecordId           | 15                               |
      | enrollmentStartDate          | cutoffStartDateImmigrantChildren |
      | enrollmentEndDate            | highDate-DC                      |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID                         |
      | planCode                     | 081080400                        |
      | planId                       | 145                              |
      | subProgramTypeCode           | ImmigrantChildren                |
    And I run create Eligibility and Enrollment API
    # Ideally we should login with Service Account 2. We prefer not to since it is having logging issues. Can revert when resolved.
    # Given I logged into CRM with "Service Account 2" and select a project "DC-EB"
    Given I logged into CRM and select a project "DC-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "39448-01.firstName" and Last Name as "39448-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "39448-01.firstName" and last name "39448-01.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | PRE-LOCKIN - WINDOW, ANNIVERSARY - WINDOW                                                                                                                                                            |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | cutoffStartDateImmigrantChildrenUIver - 89DaysAfterCutoffStartDateImmigrantChildrenUIver, 1YearFromCutoffStartDateImmigrantChildrenUIver - 89DaysAfter1YearFromCutoffStartDateImmigrantChildrenUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 88DaysAfterCutoffStartDateImmigrantChildrenUIver                                                                                                                                                     |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to change plan before lock-in                                                                                                                                                     |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Immigrant Children                                                                                                                                                                                   |
      | CURRENT ELIGIBILITY.SERVICE REGION      | Northeast                                                                                                                                                                                            |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofLastMonthUIver                                                                                                                                                                               |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver-DC                                                                                                                                                                                     |
      #*********
      | FUTURE ENROLLMENT.PLAN NAME             | AMERIHEALTH CARITAS DC                                                                                                                                                                               |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Accepted                                                                                                                                                                                             |
      | FUTURE ENROLLMENT.CHANNEL               | Auto_Assignment                                                                                                                                                                                      |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                                                                                                                                                                                                |
      | FUTURE ENROLLMENT.PDP NAME              | -- --                                                                                                                                                                                                |
      | FUTURE ENROLLMENT.START DATE            | cutoffStartDateImmigrantChildrenUIver                                                                                                                                                                |
      | FUTURE ENROLLMENT.END DATE              | highDateUIver-DC                                                                                                                                                                                     |
      | FUTURE ENROLLMENT.PCP SELECT BUTTON     | hidden                                                                                                                                                                                               |
      | FUTURE ENROLLMENT.PLAN CHANGE BUTTON    | hidden                                                                                                                                                                                               |


  @CP-39448 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @kursat
  Scenario: DC EB Decide Scenario 15 for New Enrollment Accept Response for Alliance - Eligibility Start Date > (Process Date - 2 Months)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39448-02 |
      | newCaseCreation  | yes      |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39448-02.consumerId |
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
      | coverageCode                 | 460                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "39448-02a" for update Enrollment information
      | [0].consumerId         | 39448-02.consumerId |
      | [0].channel            | AUTO_ASSIGNMENT     |
      | [0].planId             | 145                 |
      | [0].planCode           | 077573200           |
      | [0].startDate          | 1stdayoflastmonth:: |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | Alliance            |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39448-02a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | 1stdayoflastmonth::    |
      | [0].planId       | delete::               |
      | [0].planCode     | 077573200              |
      | [0].consumerId   | 39448-02.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39448-02.consumerId |
      | isEligibilityRequired        | no                  |
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 15                  |
      | enrollmentRecordId           | 15                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | enrollmentEndDate            | highDate-DC         |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID            |
      | planCode                     | 077573200           |
      | planId                       | 145                 |
      | subProgramTypeCode           | Alliance            |
    And I run create Eligibility and Enrollment API
    # Ideally we should login with Service Account 2. We prefer not to since it is having logging issues. Can revert when resolved.
    # Given I logged into CRM with "Service Account 2" and select a project "DC-EB"
    Given I logged into CRM and select a project "DC-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "39448-02.firstName" and Last Name as "39448-02.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "39448-02.firstName" and last name "39448-02.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | PRE-LOCKIN - WINDOW, ANNIVERSARY - WINDOW                                                                                                |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 1stDayofLastMonthUIver - 89DayFromFirstDayOfLastMonthUIver, 1YearFrom1stDayofLastMonthUIver - 89DaysAfter1YearFrom1stDayofLastMonthUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 88DayFromFirstDayOfLastMonthUIver                                                                                                        |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to change plan before lock-in                                                                                         |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Alliance-Child                                                                                                                           |
      | CURRENT ELIGIBILITY.SERVICE REGION      | Northeast                                                                                                                                |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofLastMonthUIver                                                                                                                   |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver-DC                                                                                                                         |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | CAREFIRST COMMUNITY HEALTH PLAN DC                                                                                                       |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                                                                                                                 |
      | CURRENT ENROLLMENT.CHANNEL              | Auto_Assignment                                                                                                                          |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                                                                                                    |
      | CURRENT ENROLLMENT.PDP NAME             | -- --                                                                                                                                    |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofLastMonthUIver                                                                                                                   |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver-DC                                                                                                                         |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | is displayed                                                                                                                             |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                                                                                                             |


  @CP-39448 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @kursat
  Scenario: DC EB Decide Scenario 15 for New Enrollment Accept Response for Alliance - (Process Date - 2 Months) > Eligibility Start Date
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 39448-03 |
      | newCaseCreation  | yes      |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39448-03.consumerId   |
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
      | coverageCode                 | 470                   |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "39448-03a" for update Enrollment information
      | [0].consumerId         | 39448-03.consumerId     |
      | [0].channel            | AUTO_ASSIGNMENT         |
      | [0].planId             | 145                     |
      | [0].planCode           | 077573200               |
      | [0].startDate          | 1stdayof2monthsbefore:: |
      | [0].endDate            | highdatedc::            |
      | [0].programTypeCode    | MEDICAID                |
      | [0].subProgramTypeCode | Alliance                |
      | [0].serviceRegionCode  | Northeast               |
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "39448-03a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION      |
      | [0].startDate    | 1stdayof2monthsbefore:: |
      | [0].planId       | delete::                |
      | [0].planCode     | 077573200               |
      | [0].consumerId   | 39448-03.consumerId     |
      | [0].enrollmentId | c.data[0].enrollmentId  |
      | [0].status       | SUBMITTED_TO_STATE      |
      | [0].txnStatus    | SUBMITTED_TO_STATE      |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 39448-03.consumerId   |
      | isEligibilityRequired        | no                    |
      | isEnrollemntRequired         | yes                   |
      | isEnrollmentProviderRequired | no                    |
      | recordId                     | 15                    |
      | enrollmentRecordId           | 15                    |
      | enrollmentStartDate          | 1stDayof2MonthsBefore |
      | enrollmentEndDate            | highDate-DC           |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID              |
      | planCode                     | 077573200             |
      | planId                       | 145                   |
      | subProgramTypeCode           | Alliance              |
    And I run create Eligibility and Enrollment API
    # Ideally we should login with Service Account 2. We prefer not to since it is having logging issues. Can revert when resolved.
    # Given I logged into CRM with "Service Account 2" and select a project "DC-EB"
    Given I logged into CRM and select a project "DC-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "39448-03.firstName" and Last Name as "39448-03.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "39448-03.firstName" and last name "39448-03.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | PRE-LOCKIN - WINDOW, ANNIVERSARY - WINDOW                                                                                                                |
      | CALENDAR ICON HOVER.IMPORTANT DATES     | 1stDayof2MonthsBeforeUIver - 89DaysAfter1stDayof2MonthsBeforeUIver, 1YearFrom1stDayof2MonthsBeforeUIver - 89DaysAfter1YearFrom1stDayof2MonthsBeforeUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL          | 88DaysAfter1stDayof2MonthsBeforeUIver                                                                                                                    |
      | COUNTDOWN.ICON HOVER                    | Calendar days left to change plan before lock-in                                                                                                         |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Alliance-Adult                                                                                                                                           |
      | CURRENT ELIGIBILITY.SERVICE REGION      | Northeast                                                                                                                                                |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayof6MonthsBeforeUIver                                                                                                                               |
      | CURRENT ELIGIBILITY.END DATE            | highDateUIver-DC                                                                                                                                         |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | CAREFIRST COMMUNITY HEALTH PLAN DC                                                                                                                       |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                                                                                                                                                 |
      | CURRENT ENROLLMENT.CHANNEL              | Auto_Assignment                                                                                                                                          |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                                                                                                                                                    |
      | CURRENT ENROLLMENT.PDP NAME             | -- --                                                                                                                                                    |
      | CURRENT ENROLLMENT.START DATE           | 1stDayof2MonthsBeforeUIver                                                                                                                               |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver-DC                                                                                                                                         |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | is displayed                                                                                                                                             |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                                                                                                                                             |

