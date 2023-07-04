Feature: IN-EB Display Aid Categories


  @CP-29756 @crm-regression @CP-23933-01 @ui-ee-in @ui-ee-in-smoke @IN-EB-UI-Regression @kursat
  Scenario: IN-EB Display Aid Categories on Program & Benefits Screen
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 29756-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29756-1.consumerId    |
      | isEligibilityRequired        | yes                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                    |
      | recordId                     | 21                    |
      | eligibilityRecordId          | 21                    |
      | isEnrollmentProviderRequired | no                    |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayof2MonthsBefore |
      | eligibilityEndDate           | highDate              |
      | programCode                  | A                     |
      | subProgramTypeCode           | HoosierCareConnect    |
      | eligibilityStatusCode        | M                     |
      | categoryCode                 | 10                    |
      | genericFieldText1            | Eligible              |
      | coverageCode                 | cc01                  |
      | fileSource                   | RECIPIENT             |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | highDate                |
      | segmentDetailValue1 | 5                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | highDate::              |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    And User provide other eligibility segments details
      | startDate           | 1stDayof2MonthsBefore   |
      | endDate             | lastDayofLastMonth      |
      | segmentDetailValue1 | 4                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | lastDayofLastMonth::    |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    And User provide other eligibility segments details
      | startDate           | 1stDayof3MonthsBefore   |
      | endDate             | futureDate              |
      | segmentDetailValue1 | E                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | 1stDayof2MonthsBefore:: |
      | segmentDetailValue4 | highDate::              |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    And User provide other eligibility segments details
      | startDate           | 1stDayof3MonthsBefore |
      | endDate             | futureDate            |
      | segmentDetailValue1 | WV24                  |
      | segmentDetailValue2 | null                  |
      | segmentDetailValue3 | null                  |
      | segmentDetailValue4 | null                  |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY          |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth            |
      | endDate             | 90DayFromFirstDayOfNextMonth |
      | segmentDetailValue1 | PASMR                        |
      | segmentDetailValue2 | null                         |
      | segmentDetailValue3 | null                         |
      | segmentDetailValue4 | null                         |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY                 |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth       |
      | endDate             | lastDayOf2MonthsFromNow |
      | segmentDetailValue1 | L                       |
      | segmentDetailValue2 | null                    |
      | segmentDetailValue3 | null                    |
      | segmentDetailValue4 | null                    |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY            |
    And User provide other eligibility segments details
      | startDate           | 1stDayof3MonthsBefore     |
      | endDate             | 60DaysFromFirstDayOfMonth |
      | segmentDetailValue1 | 590                       |
      | segmentDetailValue2 | null                      |
      | segmentDetailValue3 | null                      |
      | segmentDetailValue4 | null                      |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY              |
    And User provide other eligibility segments details
      | startDate           | 1stDayof3MonthsBefore |
      | endDate             | yesterday             |
      | segmentDetailValue1 | ESRD                  |
      | segmentDetailValue2 | null                  |
      | segmentDetailValue3 | null                  |
      | segmentDetailValue4 | null                  |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY          |
    And User provide other eligibility segments details
      | startDate           | 1stDayofNextMonth |
      | endDate             | futureDate        |
      | segmentDetailValue1 | MRA3              |
      | segmentDetailValue2 | null              |
      | segmentDetailValue3 | null              |
      | segmentDetailValue4 | null              |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY      |
    And User provide other eligibility segments details
      | startDate           | 1stDayOf3MonthsFromNow |
      | endDate             | futureDate             |
      | segmentDetailValue1 | WL11                   |
      | segmentDetailValue2 | null                   |
      | segmentDetailValue3 | null                   |
      | segmentDetailValue4 | null                   |
      | segmentDetailValue5 |[blank]|
      | segmentTypeCode     | AID_CATEGORY           |
    Then I send API call with name "29756-1a" for create Eligibility and Enrollment
    Given I logged into CRM and select a project "IN-EB"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "29756-1.firstName" and Last Name as "29756-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "29756-1.firstName" and last name "29756-1.lastName" with data
      | ADDED AID CATEGORY.SPECIAL COVERAGE.CODE.1       | 590 590 Program                                         |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.START DATE.1 | 1stDayof3MonthsBefore                                   |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.END DATE.1   | 60DaysFromFirstDayOfMonth                               |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.CODE.2       | E Family Planning Services (eff 1/1/2013)               |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.START DATE.2 | 1stDayof3MonthsBefore                                   |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.END DATE.2   | futureDate                                              |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.CODE.3       | WV24 CIH Waiver; HCBS; Conversion MSDC                  |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.START DATE.3 | 1stDayof3MonthsBefore                                   |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.END DATE.3   | futureDate                                              |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.CODE.4       | 5 ARCH for Aged                                         |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.START DATE.4 | 1stDayof2MonthsBefore                                   |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.END DATE.4   | highDate                                                |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.CODE.5       | L Qualified Medicare Beneficiary (QMB)                  |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.START DATE.5 | 1stDayofNextMonth                                       |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.END DATE.5   | lastDayOf2MonthsFromNow                                 |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.CODE.6       | PASMR PASRR Individuals with Intellectual Disability MR |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.START DATE.6 | 1stDayofNextMonth                                       |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.END DATE.6   | 90DayFromFirstDayOfNextMonth                            |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.CODE.7       | MRA3 MRO Adult with Level of Need = 3                   |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.START DATE.7 | 1stDayofNextMonth                                       |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.END DATE.7   | futureDate                                              |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.CODE.8       | WL11 TBI Wvr; Deinst ICF/IID from in-state plcmt (HCBS) |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.START DATE.8 | 1stDayOf3MonthsFromNow                                  |
      | ADDED AID CATEGORY.SPECIAL COVERAGE.END DATE.8   | futureDate                                              |