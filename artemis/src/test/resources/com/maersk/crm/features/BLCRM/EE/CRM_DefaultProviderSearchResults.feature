Feature: Start with Plan - Default Provider Search Results - # 2

  @CP-16884 @CP-16884-01  @ui-ee @crm-regression @alex
  Scenario Outline: Present Provider Search Results with Defaulted Values (populated)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
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
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click on search provider
    Then I verify the following fields "<Street Address>", "<City>", "<Zip>","<Distance>", "<Speciality>", "<Language>", "<Provider Gender>", "<Age Range>" populated with defaulted value
    Examples:
      | Street Address | City  | Zip   | Distance | Speciality | Language | Provider Gender | Age Range |
      | 22 main st     | Evans | 30809 | 15       |[blank]|          |[blank]|           |


  @CP-16884 @CP-16884-02  @ui-ee @crm-regression @alex
  Scenario Outline: Present Provider Search Results in Expanded View
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
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
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click on search provider button
    When I search with speciality, language, provider gender and age range
    Then  I verify I will be presented with the “expanded view” which contains "<Speciality>", "<Language>", "<Provider Gender>", "<Age Range>"
    Examples:
      | Speciality      | Language | Provider Gender | Age Range |
      | Family Practice | ENGLISH  | Male            | 0 - 120   |

  @CP-16884 @CP-16884-03  @ui-ee @crm-regression @alex
  Scenario:  Search by Age Range
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofLastMonth                                        |
      | eligibilityStartDate         | 1stDayofLastMonth                                        |
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
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click on search provider button
    When I select Age Range
    Then I expect to see a drop-down with 4 options "0 - 120", "0 - 19", "20 - 64", "65+"

  @CP-16383 @CP-16383-01  @ui-ee @crm-regression @alex
  Scenario:   Search and Select Plan
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A second plan from Available Plans
    And I click on search provider button
    And I click on a selected provider
    Then I verify I will be presented with the PROVIDER DETAILS

  @CP-16383 @CP-16383-02  @ui-ee @crm-regression @alex
  Scenario Outline:   View Default Provider Search Results
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
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click on search provider
    Then I verify the following fields "<Street Address>", "<City>", "<Zip>","<Distance>" populated with defaulted value
    Then  I verify I will be presented with the “expanded view” which contains Provider Name, Provider Address, Provider Telephone Number, DISTANCE, SPECIALTY, LANGUAGE, LAST UPDATED
    Examples:
      | Street Address | City  | Zip   | Distance |
      | 22 main st     | Evans | 30809 | 15       |

  @CP-16383 @CP-16383-03  @ui-ee @crm-regression @alex
  Scenario: Only Return PCP = Yes Providers
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
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click on search provider
    And I click on a selected provider
    Then I validate providers have a PCP Indicator of Yes

  @CP-16383 @CP-16383-04  @ui-ee @crm-regression @alex
  Scenario Outline: Only Return Providers from Selected Plan
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
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click on search provider
    And I click on a selected provider
    Then I validate that providers participate in the selected "<Plan>"
    Examples:
      | Plan                      |
      | AMERIGROUP COMMUNITY CARE |






