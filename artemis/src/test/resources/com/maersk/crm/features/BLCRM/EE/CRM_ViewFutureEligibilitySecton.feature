Feature: View Eligibility Details For Future Segment

  @CP-13122 @CP-13122-01 @shruti @ui-ee @crm-regression @Olga
  Scenario: View Benefit Status on Future Eligibility Section
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      |isEligibilityRequired|yes|
      |otherSegments        |[blank]|
      |isEnrollemntRequired |no |
      |recordId             |3  |
      |isEnrollmentProviderRequired|no|
      |enrollmentStartDate         ||
      |eligibilityStartDate        |1stDayofPresentMonth|
      |txnStatus                  |Accepted|
      |programCode     |H       |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      |isEligibilityRequired|yes|
      |otherSegments        |[blank]|
      |isEnrollemntRequired |no |
      |recordId             |3  |
      |isEnrollmentProviderRequired|no|
      |enrollmentStartDate         ||
      |eligibilityStartDate        |1stDayofPresentMonth|
      |txnStatus                  |Accepted|
      |programCode     |H       |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the Future Eligibility Section
    Then I see the "Benefit Status" for all the consumers


  @CP-13122 @CP-13122-02 @shruti @ui-ee @crm-regression @CP-12238 @Olga
  Scenario: List of all  Benefit Status on Future Eligibility Section
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      |isEligibilityRequired|yes|
      |otherSegments        |[blank]|
      |isEnrollemntRequired |no |
      |recordId             |3  |
      |isEnrollmentProviderRequired|no|
      |enrollmentStartDate         ||
      |eligibilityStartDate        |1stDayofPresentMonth|
      |txnStatus                  |Accepted|
      |programCode     |H       |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      |isEligibilityRequired|yes|
      |otherSegments        |[blank]|
      |isEnrollemntRequired |no |
      |recordId             |3  |
      |isEnrollmentProviderRequired|no|
      |enrollmentStartDate         ||
      |eligibilityStartDate        |1stDayofPresentMonth|
      |txnStatus                  |Accepted|
      |programCode     |H       |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the Future Eligibility Section
    Then Future Eligibility records contains following Benefit Statuses:
      |PENDING ELIGIBILITY|
      |NOT ELIGIBLE       |
      |PARTIALLY ENROLLED |
      |ENROLLED           |
      |ELIGIBLE           |


  @CP-13122 @CP-13122-03 @shruti @ui-ee @crm-regression @Olga
  Scenario: View Benefit Status on Future Eligibility Section
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      |isEligibilityRequired|yes|
      |otherSegments        |[blank]|
      |isEnrollemntRequired |no |
      |recordId             |3  |
      |isEnrollmentProviderRequired|no|
      |enrollmentStartDate         ||
      |eligibilityStartDate        |1stDayofPresentMonth|
      |txnStatus                  |Accepted|
      |programCode     |H       |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      |isEligibilityRequired|yes|
      |otherSegments        |[blank]|
      |isEnrollemntRequired |no |
      |recordId             |3  |
      |isEnrollmentProviderRequired|no|
      |enrollmentStartDate         ||
      |eligibilityStartDate        |1stDayofPresentMonth|
      |txnStatus                  |Accepted|
      |programCode     |H       |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the Future Eligibility Section
    Then I see the "Eligibility Status" for all the consumers


  @CP-13122 @CP-13122-04 @shruti @ui-ee @crm-regression @Olga
  Scenario: View Benefit Status on Future Eligibility Section
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      |isEligibilityRequired|yes|
      |otherSegments        |[blank]|
      |isEnrollemntRequired |no |
      |recordId             |3  |
      |isEnrollmentProviderRequired|no|
      |enrollmentStartDate         ||
      |eligibilityStartDate        |1stDayofPresentMonth|
      |txnStatus                  |Accepted|
      |programCode     |H       |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      |isEligibilityRequired|yes|
      |otherSegments        |[blank]|
      |isEnrollemntRequired |no |
      |recordId             |3  |
      |isEnrollmentProviderRequired|no|
      |enrollmentStartDate         ||
      |eligibilityStartDate        |1stDayofPresentMonth|
      |txnStatus                  |Accepted|
      |programCode     |H       |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the Future Eligibility Section
    Then I see the "Benefit Status" for all the consumers
    Then I hover over the "Benefit Status" I see "Benefit Status" text is displayed


  @CP-13122 @CP-13122-05 @shruti @ui-ee @crm-regression @Olga
  Scenario: View Benefit Status on Future Eligibility Section
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      |isEligibilityRequired|yes|
      |otherSegments        |[blank]|
      |isEnrollemntRequired |no |
      |recordId             |3  |
      |isEnrollmentProviderRequired|no|
      |enrollmentStartDate         ||
      |eligibilityStartDate        |1stDayofPresentMonth|
      |txnStatus                  |Accepted|
      |programCode     |H       |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      |isEligibilityRequired|yes|
      |otherSegments        |[blank]|
      |isEnrollemntRequired |no |
      |recordId             |3  |
      |isEnrollmentProviderRequired|no|
      |enrollmentStartDate         ||
      |eligibilityStartDate        |1stDayofPresentMonth|
      |txnStatus                  |Accepted|
      |programCode     |H       |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I view the Future Eligibility Section
    Then I see the "Eligibility Status" for all the consumers
    Then I hover over the "Eligibility Status" I see "Eligibility Status" text is displayed

