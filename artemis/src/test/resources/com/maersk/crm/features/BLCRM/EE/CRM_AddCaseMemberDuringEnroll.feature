Feature: Add case member during Enroll

  @CP-12695 @CP-12695-01  #@ui-ee @crm-regression @Olga
  Scenario: New Enrollment initiated from Current Eligibility segment - verify case members has the same population
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view "Current Eligibility data" section for consumer number 1
    And I click "Enroll" Button for a consumer
    When I Add Case Members Button and Select all case members
    Then I verify I see the consumers with the same "population, service area and program" in the list

  @CP-12695 @CP-12695-04  @ui-ee @crm-regression @Olga
  Scenario:New Enrollment initiated from Current Eligibility segment - verify case members has a required enroll action
  where the current system date is before the due date
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
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view "Current Eligibility data" section for consumer number 1
    And I click "Enroll" Button on "current eligibility" section for a consumer number 1
    When I Add Case Members Button and Select all case members
    Then I verify I see the consumers with the same "Enroll Action available" in the list

   # below commented out scenario is covered by the following scenario after that so it will be deleted if no objections by 11/01/2022

#  @CP-12695 @CP-12695-05  @ui-ee @crm-regression @Olga
#  Scenario: New Enrollment initiated from Future Eligibility segment - verify case members has the same population
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    Given  I created a consumer with population type "NEWBORN" with data
#      | saveConsumerInfo | 12695-05-01 |
#    Given I initiated Eligibility and Enrollment Create API
#    And I provide the enrollment and eligibility information to create enrollment
#      | consumerId                   | 12695-05-01.consumerId |
#      | isEligibilityRequired        | yes                    |
#      | otherSegments                |[blank]|
#      | isEnrollemntRequired         | no                     |
#      | recordId                     | 1                      |
#      | isEnrollmentProviderRequired | no                     |
#      | enrollmentStartDate          | 1stDayofNextMonth      |
#      | eligibilityStartDate         | 1stDayofNextMonth      |
#      | txnStatus                    | Accepted               |
#      | programCode                  | M                      |
#    And I run create Eligibility and Enrollment API
#    Given  I created a consumer with population type "NEWBORN" with data
#      | saveConsumerInfo | 12695-05-02 |
#    Given I initiated Eligibility and Enrollment Create API
#    And I provide the enrollment and eligibility information to create enrollment
#      | consumerId                   | 12695-05-02.consumerId |
#      | isEligibilityRequired        | yes                    |
#      | otherSegments                |[blank]|
#      | isEnrollemntRequired         | no                     |
#      | recordId                     | 1                      |
#      | isEnrollmentProviderRequired | no                     |
#      | enrollmentStartDate          | 1stDayofNextMonth      |
#      | eligibilityStartDate         | 1stDayofNextMonth      |
#      | txnStatus                    | Accepted               |
#      | programCode                  | M                      |
#    And I run create Eligibility and Enrollment API
#    Given  I created a consumer with population type "NEWBORN" with data
#      | saveConsumerInfo | 12695-05-03 |
#    Given I initiated Eligibility and Enrollment Create API
#    And I provide the enrollment and eligibility information to create enrollment
#      | consumerId                   | 12695-05-03.consumerId |
#      | isEligibilityRequired        | yes                    |
#      | otherSegments                |[blank]|
#      | isEnrollemntRequired         | no                     |
#      | recordId                     | 1                      |
#      | isEnrollmentProviderRequired | no                     |
#      | enrollmentStartDate          | 1stDayofNextMonth      |
#      | eligibilityStartDate         | 1stDayofNextMonth      |
#      | txnStatus                    | Accepted               |
#      | programCode                  | M                      |
#    And I run create Eligibility and Enrollment API
#    Given I logged into CRM and click on initiate contact
##    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
#    When I searched consumer created through api with First Name as "12695-05-01.firstName" and Last Name as "12695-05-01.lastName"
#    And I link the contact to an existing Case or Consumer Profile
#    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
#    Then I verify program & benefit info page for consumer first name "12695-05-01.firstName" and last name "12695-05-01.lastName" with data
#      | FUTURE ELIGIBILITY.CONSUMER POPULATION | Medicaid-Newborn       |
#      | FUTURE ELIGIBILITY.SERVICE REGION      | East                   |
#      | FUTURE ELIGIBILITY.START DATE          | 1stDayofNextMonthUIver |
#      | FUTURE ELIGIBILITY.END DATE            | -- --                  |
##    And I view "Future Eligibility data" section for consumer number 1
##    And I click "Enroll" Button on "future eligibility" section for a consumer number 1
#    And I click "ENROLL" Button in "FUTURE ELIGIBILITY" segment for a consumer first name "12695-05-01.firstName" and last name "12695-05-01.lastName"
#    When I Add Case Members Button and Select all case members
#    Then I verify I see the consumers with the same "population, service area and program" in the list

  @CP-12695 @CP-12695-08  @ui-ee @crm-regression @Olga
  Scenario: New Enrollment initiated from Future Eligibility segment - verify case members has a required enroll action and same population
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 12695-08-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 12695-08-01.consumerId |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 1                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofNextMonth      |
      | eligibilityStartDate         | 1stDayofNextMonth      |
      | txnStatus                    | Accepted               |
      | programCode                  | M                      |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 12695-08-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 12695-08-02.consumerId |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 1                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofNextMonth      |
      | eligibilityStartDate         | 1stDayofNextMonth      |
      | txnStatus                    | Accepted               |
      | programCode                  | M                      |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 12695-08-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 12695-08-03.consumerId |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 1                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofNextMonth      |
      | eligibilityStartDate         | 1stDayofNextMonth      |
      | txnStatus                    | Accepted               |
      | programCode                  | M                      |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 12695-08-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 12695-08-04.consumerId |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 1                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofNextMonth      |
      | eligibilityStartDate         | 1stDayofNextMonth      |
      | txnStatus                    | Accepted               |
      | programCode                  | M                      |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 12695-08-05 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 12695-08-05.consumerId |
      | isEligibilityRequired        | yes                    |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                     |
      | recordId                     | 1                      |
      | isEnrollmentProviderRequired | no                     |
      | enrollmentStartDate          | 1stDayofNextMonth      |
      | eligibilityStartDate         | 1stDayofNextMonth      |
      | txnStatus                    | Accepted               |
      | programCode                  | H                      |
      | subProgramTypeCode           | MEDICAIDMCHB           |
    And I run create Eligibility and Enrollment API
    When I searched consumer created through api with First Name as "12695-08-01.firstName" and Last Name as "12695-08-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "12695-08-01.firstName" and last name "12695-08-01.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT        | ENROLL                 |
      | FUTURE ELIGIBILITY.CONSUMER POPULATION | Medicaid-Newborn       |
      | FUTURE ELIGIBILITY.SERVICE REGION      | East                   |
      | FUTURE ELIGIBILITY.START DATE          | 1stDayofNextMonthUIver |
      | FUTURE ELIGIBILITY.END DATE            | -- --                  |
    Then I verify program & benefit info page for consumer first name "12695-08-02.firstName" and last name "12695-08-02.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT        | ENROLL                 |
      | FUTURE ELIGIBILITY.CONSUMER POPULATION | Medicaid-Newborn       |
      | FUTURE ELIGIBILITY.SERVICE REGION      | East                   |
      | FUTURE ELIGIBILITY.START DATE          | 1stDayofNextMonthUIver |
      | FUTURE ELIGIBILITY.END DATE            | -- --                  |
    Then I verify program & benefit info page for consumer first name "12695-08-03.firstName" and last name "12695-08-03.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT        | ENROLL                 |
      | FUTURE ELIGIBILITY.CONSUMER POPULATION | Medicaid-Newborn       |
      | FUTURE ELIGIBILITY.SERVICE REGION      | East                   |
      | FUTURE ELIGIBILITY.START DATE          | 1stDayofNextMonthUIver |
      | FUTURE ELIGIBILITY.END DATE            | -- --                  |
    Then I verify program & benefit info page for consumer first name "12695-08-04.firstName" and last name "12695-08-04.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT        | ENROLL                      |
      | FUTURE ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population |
      | FUTURE ELIGIBILITY.SERVICE REGION      | East                        |
      | FUTURE ELIGIBILITY.START DATE          | 1stDayofNextMonthUIver      |
      | FUTURE ELIGIBILITY.END DATE            | -- --                       |
    Then I verify program & benefit info page for consumer first name "12695-08-05.firstName" and last name "12695-08-05.lastName" with data
      | CALENDAR ICON HOVER.ACTION TEXT        | ENROLL                  |
      | FUTURE ELIGIBILITY.CONSUMER POPULATION | Medicaid-Pregnant Women |
      | FUTURE ELIGIBILITY.SERVICE REGION      | East                    |
      | FUTURE ELIGIBILITY.START DATE          | 1stDayofNextMonthUIver  |
      | FUTURE ELIGIBILITY.END DATE            | -- --                   |
    And I click "ENROLL" Button in "FUTURE ELIGIBILITY" segment for a consumer first name "12695-08-01.firstName" and last name "12695-08-01.lastName"
    When I click Add Case Members Button
    Then I verify I only see next other consumers in the dropdown list
      | 12695-08-02 |
      | 12695-08-03 |
    When I Add Case Members Button and Select all case members
    Then I verify details of consumer first name "12695-08-01.firstName" and last name "12695-08-01.lastName" on Selected Consumers Segment on Enrollment Update Page
      | AGE/GENDER          | 0/Female                           |
      | POPULATION          | NEWBORN                            |
      | RESIDENTIAL ADDRESS | 22 main st , Augusta GA 30917-4345 |
      | CURRENT PLAN        | -- --                              |
      | CURRENT PROVIDER    | -- --                              |
      | ELIGIBILITY DETAILS | 1stDayofNextMonthUIver - -- --     |
    Then I verify details of consumer first name "12695-08-02.firstName" and last name "12695-08-02.lastName" on Selected Consumers Segment on Enrollment Update Page
      | AGE/GENDER          | 0/Female                           |
      | POPULATION          | NEWBORN                            |
      | RESIDENTIAL ADDRESS | 22 main st , Augusta GA 30917-4345 |
      | CURRENT PLAN        | -- --                              |
      | CURRENT PROVIDER    | -- --                              |
      | ELIGIBILITY DETAILS | 1stDayofNextMonthUIver - -- --     |
    Then I verify details of consumer first name "12695-08-03.firstName" and last name "12695-08-03.lastName" on Selected Consumers Segment on Enrollment Update Page
      | AGE/GENDER          | 0/Female                           |
      | POPULATION          | NEWBORN                            |
      | RESIDENTIAL ADDRESS | 22 main st , Augusta GA 30917-4345 |
      | CURRENT PLAN        | -- --                              |
      | CURRENT PROVIDER    | -- --                              |
      | ELIGIBILITY DETAILS | 1stDayofNextMonthUIver - -- --     |