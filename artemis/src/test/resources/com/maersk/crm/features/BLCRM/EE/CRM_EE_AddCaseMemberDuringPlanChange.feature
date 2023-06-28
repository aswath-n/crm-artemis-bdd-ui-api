Feature:  Add Case Member during Plan Change

  @CP-13960 @CP-13960-01  @ui-ee @crm-regression @alex
  Scenario: Plan Change initiated from Enrollment segment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13960-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    # ******************** CONSUMER 2 ***************************
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13960-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    # ******************** CONSUMER 3 ***************************
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 13960-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 3                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And Save List of members
    And I view "Current Eligibility data" section for consumer number 1
    And I click "Plan Change" Button for a consumer
    When I Add Case Members Button and Select all case members
    And I select "PEACH STATE" from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
#    Then I verify I see the consumers with the same "population, service area and program" in the list
#    Then I verify I see the consumers with the same "eligibility timeframe" in the list
#    Then I verify I see the consumers with the same "enrollment timeframe" in the list
    # Below validation replaces the upper 3 lines of commented out ones
    Then I verify program & benefit info page for consumer first name "13960-01.firstName" and last name "13960-01.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | -- --                                     |
      #*********
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population               |
      | CURRENT ELIGIBILITY.SERVICE REGION      | East                                      |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofLastMonthUIver                    |
      | CURRENT ELIGIBILITY.END DATE            | -- --                                     |
      #*********
      | FUTURE ENROLLMENT.PLAN NAME             | PEACH STATE                               |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Selection Made                            |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                                     |
      | FUTURE ENROLLMENT.START DATE            | 1stDayofNextMonthUIver                    |
      | FUTURE ENROLLMENT.END DATE              | -- --                                     |
      | FUTURE ENROLLMENT.EDIT BUTTON           | is displayed                              |
      | FUTURE ENROLLMENT.DISREGARD BUTTON      | is displayed                              |
    Then I verify program & benefit info page for consumer first name "13960-02.firstName" and last name "13960-02.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | -- --                                     |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population               |
      | CURRENT ELIGIBILITY.SERVICE REGION      | East                                      |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofLastMonthUIver                    |
      | CURRENT ELIGIBILITY.END DATE            | -- --                                     |
      #*********
      | FUTURE ENROLLMENT.PLAN NAME             | PEACH STATE                               |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Selection Made                            |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                                     |
      | FUTURE ENROLLMENT.START DATE            | 1stDayofNextMonthUIver                    |
      | FUTURE ENROLLMENT.END DATE              | -- --                                     |
      | FUTURE ENROLLMENT.EDIT BUTTON           | is displayed                              |
      | FUTURE ENROLLMENT.DISREGARD BUTTON      | is displayed                              |
    Then I verify program & benefit info page for consumer first name "13960-03.firstName" and last name "13960-03.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT         | -- --                                     |
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population               |
      | CURRENT ELIGIBILITY.SERVICE REGION      | East                                      |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofLastMonthUIver                    |
      | CURRENT ELIGIBILITY.END DATE            | -- --                                     |
      #*********
      | FUTURE ENROLLMENT.PLAN NAME             | PEACH STATE                               |
      | FUTURE ENROLLMENT.SELECTION STATUS      | Selection Made                            |
      | FUTURE ENROLLMENT.PCP NAME              | -- --                                     |
      | FUTURE ENROLLMENT.START DATE            | 1stDayofNextMonthUIver                    |
      | FUTURE ENROLLMENT.END DATE              | -- --                                     |
      | FUTURE ENROLLMENT.EDIT BUTTON           | is displayed                              |
      | FUTURE ENROLLMENT.DISREGARD BUTTON      | is displayed                              |






