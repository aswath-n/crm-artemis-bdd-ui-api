Feature: Start with Plan - View Search Results and Provider Details

  @CP-86 @CP-86-01  @ui-ee @crm-regression @alex
  Scenario: View PROVIDER DETAILS - Plan change
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
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
      | planId                       |[blank]|
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PCP Select" Button for a consumer
    And I search with provider First name "John" and Last Name "Hill"
    When I click on a selected provider
    Then I verify I will be presented with the PROVIDER DETAILS

  @CP-86 @CP-86-02  @ui-ee @crm-regression @alex
  Scenario: View PROVIDER DETAILS - New Enrollment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofPresentMonth                                     |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
      | anniversaryDate              | anniversaryDate                                          |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PCP Select" Button for a consumer
    And I search with provider First name "John" and Last Name "Hill"
    When I click on a selected provider
    Then I verify I will be presented with the PROVIDER DETAILS