Feature: PBI Important Date and Count Down Changes


  @CP-43601 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @kursat
  Scenario: DC EB: Program and Benefit Info Screen (changes to Important Dates Calendar and Countdown)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    # Once defect ticket CP-47747 is fixed and enhancements are done instead of using hardcoded consumer create consumer as given below
    # Create dceb alliance consumer with eligibility and enrollment starting  1 year before the beginning of present month, ending at the end of current year with coverage code 460
    # create future eligibility with coverage code 130 for same consumer starting beginning of next year and ending highDate-DC
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    # Below consumer Deion Gaylord is in qa-dev DB, similar consumer needs to be created for qa-rls
    When I searched consumer created through api with First Name as "Deion" and Last Name as "Gaylord"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "Deion" and last name "Gaylord" with data
      | CALENDAR ICON HOVER.ACTION TEXT                 | ANNIVERSARY - WINDOW, ENROLL                                                                                                            |
      | CALENDAR ICON HOVER.IMPORTANT DATES             | 1stDayofPresentMonthUIver - 89DaysAfter1stDayofPresentMonthUIver, 46DaysBefore1stDayofNextYearUIver - 17DaysBefore1stDayofNextYearUIver |
      | COUNTDOWN.NUMBER OF DAYS UNTIL                  | 88DaysAfter1stDayofPresentMonthUIver                                                                                                    |
      | COUNTDOWN.ICON HOVER                            | Calendar days left to change plan for anniversary                                                                                       |
      | CALENDAR ICON HOVER.RED DOT                     | displayed                                                                                                                               |
      ###########################################################################
      | CURRENT ELIGIBILITY.CONSUMER POPULATION         | Alliance-Child                                                                                                                          |
      | CURRENT ELIGIBILITY.COVERAGE CODE & DESCRIPTION | 460 - MA Healthcare Alliance; Child                                                                                                     |
      | CURRENT ELIGIBILITY.SERVICE REGION              | Northeast                                                                                                                               |
      | CURRENT ELIGIBILITY.START DATE                  | 1YearBefore1stDayofPresentMonthUIver                                                                                                    |
      | CURRENT ELIGIBILITY.END DATE                    | lastDayOfThePresentYearUIver                                                                                                            |
      ###########################################################################
      | CURRENT ENROLLMENT.PLAN NAME                    | AMERIHEALTH CARITAS DC                                                                                                                  |
      | CURRENT ENROLLMENT.SELECTION STATUS             | Accepted                                                                                                                                |
      | CURRENT ENROLLMENT.CHANNEL                      | System Integration                                                                                                                      |
      | CURRENT ENROLLMENT.PCP NAME                     | -- --                                                                                                                                   |
      | CURRENT ENROLLMENT.PDP NAME                     | -- --                                                                                                                                   |
      | CURRENT ENROLLMENT.START DATE                   | 1YearBefore1stDayofPresentMonthUIver                                                                                                    |
      | CURRENT ENROLLMENT.END DATE                     | lastDayOfThePresentYearUIver                                                                                                            |
      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON           | is displayed                                                                                                                            |
      | CURRENT ENROLLMENT.PCP SELECT BUTTON            | is displayed                                                                                                                            |
      ###########################################################################
      | FUTURE ELIGIBILITY.CONSUMER POPULATION          | DCHF-Adult                                                                                                                              |
      | FUTURE ELIGIBILITY.COVERAGE CODE & DESCRIPTION  | 130 - TANF Medicaid; Adult                                                                                                              |
      | FUTURE ELIGIBILITY.SERVICE REGION               | Northeast                                                                                                                               |
      | FUTURE ELIGIBILITY.START DATE                   | 1stDayofNextYearUIver                                                                                                                   |
      | FUTURE ELIGIBILITY.END DATE                     | highDateUIver-DC                                                                                                                        |

