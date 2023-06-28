Feature: Start with Plan - Select the Provider

  @CP-8020 @CP-8020-01  @ui-ee @crm-regression @alex
  Scenario: Selecting a provider from at-a-glance
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 4                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
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
    And  I click the Select Provider button
    Then I verify the following panels will be displayed: "Selected Consumers", "Selected Plans", "Selected Providers"

  @CP-8020 @CP-8020-02  @ui-ee @crm-regression @alex
  Scenario:  Selecting a provider from provider details
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 4                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
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
    And I click the Select Provider button
    Then I verify the following panels will be displayed: "Selected Consumers", "Selected Plans", "Selected Providers"

  @CP-8020 @CP-8020-03  @ui-ee @crm-regression @alex
  Scenario: Viewing details of a selected provider
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                                                       |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 4                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | planId                       | 1944                                                     |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
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
    When I click the Select Provider button
    Then I verify the following fields will be displayed: Provider Name, First Name, Last Name, NPI


  @CP-10082 @CP-10082-01 @ui-ee @crm-regression @kursat
  Scenario: Baseline Start with Plan - Provider Search Enhancements
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 10082-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 10082-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "10082-01.firstName" and Last Name as "10082-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click on search provider
    # 1.0  PROVIDER SEARCH After Plan Selection
    Then I verify fields on provider search page with data
      | STREET ADDRESS      | 22 main st |
      | CITY                | Evans      |
      | ZIP                 | 30809      |
      | DISTANCE (mi)       | 15         |
      | SPECIALTY           |[blank]|
      | LANGUAGE            | ENGLISH    |
      | PROVIDER GENDER     |[blank]|
      | AGE RANGE           |[blank]|
      | PROVIDER TYPE       | MEDICAL    |
      | HANDICAP ACCESSIBLE |[blank]|
    Then I verify search and cancel buttons are displayed in provider search page
    Then I verify provider type dropdown has below options
      | Medical |
      | Dental  |
    And I select "Dental" option from provider type dropdown
    And I click provider type to display its options
    And I select "MEDICAL" option from provider type dropdown
    And I clear and fill provider search form with data
#      | city          | Evans                     |
#      | zip           | 30809                     |
#      | distance      | 15                        |
#      | streetAddress | 516 Farmington Circle Cir |
      | city          | Bristol                    |
      | zip           | 31518                      |
      | distance      | 90                         |
      | streetAddress | 4141 South Lightsey Avenue |
    And I click on search button
    # 2.0 Display Provider Search Results (After Plan Has Been Selected)
    Then I verify warning message is displayed for returning providers found
    Then I verify provider search results table fields with data
      | PROVIDER NAME    | is displayed    |
      | PROVIDER ADDRESS | is displayed    |
      | LANGUAGE         | is displayed    |
      | SPECIALTY        | is displayed    |
      | LAST UPDATED     | is displayed    |
      | CHECKMARK        | is displayed    |
      | DISTANCE         | ascending order |
    # 3.0 Select How Many Providers to Show
    And I verify below are the options to display more providers in a page
      | show 20 |
      | show 40 |
      | show 60 |
    Then I select "60" as the number of returned providers to be displayed on each page
    Then I verify the number of returned providers matches the number in warning message
    And I click the first checkmark button
    # 4.0 Viewing details of a selected provider
    Then I verify the following panels will be displayed: "Selected Consumers", "Selected Plans", "Selected Providers"
    Then I verify the following fields under Selected Providers will be displayed: "Provider Name", "Provider Type", "NPI"


  @CP-11182 @CP-11182-01 @ui-ee @crm-regression @mital
  Scenario: Provider Search Enhancements - PROVIDER SEARCH Before Plan Selection - Search with FIRST NAME and LAST NAME
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 11182-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 11182-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "11182-01.firstName" and Last Name as "11182-01.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PCP SELECT" Button for a consumer
     # 1.0  PROVIDER SEARCH Before Plan Selection
    Then I verify fields on provider search page with data
      | FIRST NAME          |[blank]|
      | LAST NAME           |[blank]|
      | GROUP NAME          |[blank]|
      | PHONE NUMBER        |[blank]|
      | PLAN NAME           |[blank]|
      | PROVIDER TYPE       | MEDICAL |
      | HANDICAP ACCESSIBLE |[blank]|
    Then I verify provider type dropdown has below options
      | Medical |
      | Dental  |
    And I select "MEDICAL" option from provider type dropdown
    Then I verify search and cancel buttons are displayed in provider search page
#    2. Search providers with setting up Search By operator- 2.1 Contains
    And I set Search By operator to "Contains"
    And I search with provider First name "AN" and Last Name "IN"
    Then I verify provider search results table fields with data
      | NAME         | is displayed |
      | ADDRESS      | is displayed |
      | PLAN NAME    | is displayed |
      | LANGUAGE     | is displayed |
      | SPECIALTY    | is displayed |
      | LAST UPDATED | is displayed |
      | CHECKMARK    | is displayed |
#    3.0 Select How Many Providers to Show
    And I verify below are the options to display more providers in a page
      | show 20 |
      | show 40 |
      | show 60 |
    Then I select "60" as the number of returned providers to be displayed on each page
    And I verify provider name values "Contains" search parameters "AN" in First Name and "IN" in Last Name
    And I click the first checkmark button
    Then I verify the following panels will be displayed: "Selected Consumers", "Selected Plans", "Selected Providers"
    Then I verify the following fields under Selected Providers will be displayed: "Provider Name", "Provider Type", "NPI"
    And I  click on the BACK button
    And I click "PCP SELECT" Button for a consumer

    # 2. Search providers with setting up Search By operator- 2.2 Starts with
    And I set Search By operator to "Starts with"
    And I search with provider First name "ANA" and Last Name "MON"
    Then I verify provider search results table fields with data
      | NAME             | is displayed    |
      | ADDRESS          | is displayed    |
      | PLAN NAME         | is displayed   |
      | LANGUAGE         | is displayed    |
      | SPECIALTY        | is displayed    |
      | LAST UPDATED     | is displayed    |
      | CHECKMARK        | is displayed    |
    And I verify provider name values "Start with" search parameters "ANA" in First Name and "MON" in Last Name
    And I click the first checkmark button
    Then I verify the following panels will be displayed: "Selected Consumers", "Selected Plans", "Selected Providers"
    Then I verify the following fields under Selected Providers will be displayed: "Provider Name", "Provider Type", "NPI"

  @CP-11182 @CP-11182-02 @ui-ee @crm-regression @mital
  Scenario: Provider Search Enhancements - PROVIDER SEARCH Before Plan Selection - Search with GROUP NAME
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 11182-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 11182-02.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 21                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "11182-02.firstName" and Last Name as "11182-02.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "PCP SELECT" Button for a consumer
    # 1. Search providers with setting up Search By operator- 1.1 Contains
    And I set Search By operator to "Contains"
    And I search provider with groupname parameter "ST"
    Then I verify provider search results table fields with data
      | NAME             | is displayed    |
      | ADDRESS          | is displayed    |
      | PLAN NAME        | is displayed    |
      | LANGUAGE         | is displayed    |
      | SPECIALTY        | is displayed    |
      | LAST UPDATED     | is displayed    |
      | CHECKMARK        | is displayed    |
    And I verify Provider Name i.e. Group name field "Contains" value "ST"
    And I click the first checkmark button
    Then I verify the following panels will be displayed: "Selected Consumers", "Selected Plans", "Selected Providers"
    Then I verify the following fields under Selected Providers will be displayed: "Provider Name", "Provider Type", "NPI"
    And I  click on the BACK button
    And I click "PCP SELECT" Button for a consumer

    #1. Search providers with setting up Search By operator- 1.2 Start with
    And I set Search By operator to "Start with"
    And I search provider with groupname parameter "REID"
    Then I verify provider search results table fields with data
      | NAME             | is displayed    |
      | ADDRESS          | is displayed    |
      | PLAN NAME        | is displayed   |
      | LANGUAGE         | is displayed    |
      | SPECIALTY        | is displayed    |
      | LAST UPDATED     | is displayed    |
      | CHECKMARK        | is displayed    |
    And I verify Provider Name i.e. Group name field "Start with" value "REID"
    And I click the first checkmark button
    Then I verify the following panels will be displayed: "Selected Consumers", "Selected Plans", "Selected Providers"
    Then I verify the following fields under Selected Providers will be displayed: "Provider Name", "Provider Type", "NPI"