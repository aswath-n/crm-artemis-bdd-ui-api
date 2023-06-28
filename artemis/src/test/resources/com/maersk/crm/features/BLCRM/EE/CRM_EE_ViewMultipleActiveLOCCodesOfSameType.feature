Feature: Baseline View Multiple Active LOC Codes of the Same Type


  @CP-31971 @CP-31971-01 @CP-31971-02 @ui-ee @crm-regression @kursat
  Scenario: View Multiple Active LOC Codes of the Same Type on the Program and Benefits Screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 31971-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 31971-1.consumerId                  |
      | isEligibilityRequired        | yes                                 |
      | otherSegments                | facilityInfo,hospiceInfo,waiverInfo |
      | isEnrollemntRequired         | no                                  |
      | recordId                     | 3                                   |
      | isEnrollmentProviderRequired | no                                  |
      | enrollmentStartDate          | 1stDayofNextMonth                   |
      | eligibilityStartDate         | 1stDayofPresentMonth                |
      | txnStatus                    | Accepted                            |
      | programCode                  | M                                   |
    And I run create Eligibility and Enrollment API
    And I add level of care with data
      | consumerId                       | 31971-1.consumerId    |
      | facilityInfo                     |[blank]|
      | facilityInfo.startDate           | 1stDayof6MonthsBefore |
      | facilityInfo.placementCode       | Facility1             |
      | facilityInfo.placementCountyCode | 111                   |
      | facilityInfo.endDate             | current               |
      | hospiceInfo                      |[blank]|
      | hospiceInfo.hospiceIndicator     | H1                    |
      | hospiceInfo.startDate            | 1stDayof6MonthsBefore |
      | hospiceInfo.endDate              | null                  |
      | waiverInfo                       |[blank]|
      | waiverInfo.waiverCode            | W01                   |
      | waiverInfo.startDate             | 1stDayof6MonthsBefore |
      | waiverInfo.endDate               | null                  |
      | waiverInfo.waiverCounty          | Oakton                |
    And I add level of care with data
      | consumerId                       | 31971-1.consumerId |
      | facilityInfo                     |[blank]|
      | facilityInfo.startDate           | 1stDayofLastMonth  |
      | facilityInfo.placementCode       | Facility2          |
      | facilityInfo.placementCountyCode | 222                |
      | facilityInfo.endDate             | null               |
      | hospiceInfo                      |[blank]|
      | hospiceInfo.hospiceIndicator     | H2                 |
      | hospiceInfo.startDate            | 1stDayofLastMonth  |
      | hospiceInfo.endDate              | current            |
      | waiverInfo                       |[blank]|
      | waiverInfo.waiverCode            | W02                |
      | waiverInfo.startDate             | 1stDayofLastMonth  |
      | waiverInfo.endDate               | yesterday          |
      | waiverInfo.waiverCounty          | Riley              |
    And I add level of care with data
      | consumerId                       | 31971-1.consumerId |
      | facilityInfo                     |[blank]|
      | facilityInfo.startDate           | 1stDayofLastMonth  |
      | facilityInfo.placementCode       | Facility3          |
      | facilityInfo.placementCountyCode | 333                |
      | facilityInfo.endDate             | yesterday          |
      | hospiceInfo                      |[blank]|
      | hospiceInfo.hospiceIndicator     | H3                 |
      | hospiceInfo.startDate            | 1stDayofLastMonth  |
      | hospiceInfo.endDate              | yesterday          |
      | waiverInfo                       |[blank]|
      | waiverInfo.waiverCode            | W03                |
      | waiverInfo.startDate             | 1stDayofLastMonth  |
      | waiverInfo.endDate               | current            |
      | waiverInfo.waiverCounty          | Watson             |
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "31971-1.firstName" and Last Name as "31971-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "31971-1.firstName" and last name "31971-1.lastName" with data
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.CODE.1        | Facility1             |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.COUNTY CODE.1 | 111                   |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.START DATE.1  | 1stDayof6MonthsBefore |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.END DATE.1    | current               |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.CODE.2        | Facility2             |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.COUNTY CODE.2 | 222                   |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.START DATE.2  | 1stDayofLastMonth     |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.END DATE.2    | -- --                 |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.CODE.3        | 123Facility           |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.COUNTY CODE.3 | -- --                 |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.START DATE.3  | 1stDayofPresentMonth  |
      | FACILITY/PLACEMENT.SPECIAL COVERAGE.END DATE.3    | -- --                 |
      | HOSPICE.SPECIAL COVERAGE.INDICATOR.1              | H1                    |
      | HOSPICE.SPECIAL COVERAGE.START DATE.1             | 1stDayof6MonthsBefore |
      | HOSPICE.SPECIAL COVERAGE.END DATE.1               | -- --                 |
      | HOSPICE.SPECIAL COVERAGE.INDICATOR.2              | H2                    |
      | HOSPICE.SPECIAL COVERAGE.START DATE.2             | 1stDayofLastMonth     |
      | HOSPICE.SPECIAL COVERAGE.END DATE.2               | current               |
      | HOSPICE.SPECIAL COVERAGE.INDICATOR.3              | test                  |
      | HOSPICE.SPECIAL COVERAGE.START DATE.3             | 1stDayofPresentMonth  |
      | HOSPICE.SPECIAL COVERAGE.END DATE.3               | -- --                 |
      | WAIVER.SPECIAL COVERAGE.CODE.1                    | W01                   |
      | WAIVER.SPECIAL COVERAGE.COUNTY.1                  | Oakton                |
      | WAIVER.SPECIAL COVERAGE.START DATE.1              | 1stDayof6MonthsBefore |
      | WAIVER.SPECIAL COVERAGE.END DATE.1                | -- --                 |
      | WAIVER.SPECIAL COVERAGE.CODE.2                    | W03                   |
      | WAIVER.SPECIAL COVERAGE.COUNTY.2                  | Watson                |
      | WAIVER.SPECIAL COVERAGE.START DATE.2              | 1stDayofLastMonth     |
      | WAIVER.SPECIAL COVERAGE.END DATE.2                | current               |
      | WAIVER.SPECIAL COVERAGE.CODE.3                    | WAC001                |
      | WAIVER.SPECIAL COVERAGE.COUNTY.3                  | Adams                 |
      | WAIVER.SPECIAL COVERAGE.START DATE.3              | 1stDayofPresentMonth  |
      | WAIVER.SPECIAL COVERAGE.END DATE.3                | -- --                 |