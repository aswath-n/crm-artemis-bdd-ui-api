Feature: Start with Plan - Search Providers by Doctor Attributes

  @CP-16472 @CP-16472-01  @ui-ee @crm-regression @alex
  Scenario Outline: Search for Provider by Doctor Attribute
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
    And I click "Enroll" Button for a consumer
    And I select A second plan from Available Plans
    And I click on search provider button
    When I search with speciality, language, provider gender and age range
    Then  I verify I will be presented with the “expanded view” which contains "<Speciality>", "<Language>", "<Provider Gender>", "<Age Range>" for all search results
    Examples:
      | Speciality | Language | Provider Gender | Age Range |
      | Family Practice | English | Male        | 0 - 120     |

  @CP-16472 @CP-16472-02  @ui-ee @crm-regression @alex
  Scenario: Filter on search for Doctor Attribute- Language
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
    And I minimize Genesys popup if populates
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A second plan from Available Plans
    And I click on search provider button
    When I search with "language"
    And I filter on the "Language" column
    Then the results should filter using the approach currently implemented for specialty "Language"

  @CP-16472 @CP-16472-03  @ui-ee @crm-regression @alex
  Scenario: Filter on search for Doctor Attribute- Speciality
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
    And I minimize Genesys popup if populates
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select A second plan from Available Plans
    And I click on search provider button
    When I search with "speciality"
    And I filter on the "Speciality" column
    Then the results should filter using the approach currently implemented for specialty "Family Practice"
