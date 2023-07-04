Feature: UI: Plan Change Feature for Georgia-Families

  @CP-12237 @CP-12237-04 @ee-UI2API-GF @ui-ee-gf @crm-regression @sobir
  Scenario: GF Decide Consumer Plan Change Actions - Pre-Lockin, Anniversary AC 4.0 (positive)
  4.0 Display Plan Change Button and PCP Select Button on the User Interface (UI)
    Given I will get the Authentication token for "GACRM" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | planCode                     | 84                   |
      | programCode                  | M                    |
      | subProgramTypeCode           | GF                   |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "TO1" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "TO1.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Retroactive Enrollment"
    Given I logged into CRM and select a project "GFCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify current eligibilty status is "ENROLLED"
    Then I verify "PLAN CHANGE" button is displayed

  @CP-12237 @CP-12237-04 @ee-UI2API-GF @ui-ee-gf @crm-regression @sobir
  Scenario: GF Decide Consumer Plan Change Actions - Pre-Lockin, Anniversary AC 4.0 (negative)
  4.0 Display Plan Change Button and PCP Select Button on the User Interface (UI)
    Given I will get the Authentication token for "GACRM" in "CRM"
    Given I created a consumer with population type "CUSTOM" with data
      | age | 110 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | planCode                     | 84                   |
      | programCode                  | H                    |
      | subProgramTypeCode           | P4HB                   |
      | anniversaryDate              | anniversaryDate      |
    Then I send API call with name "TO2" for create Eligibility and Enrollment
    And Wait for 5 seconds
    And I initiated get benefit status by consumer id "TO2.consumerId"
    And I run get enrollment api
    Then I verify scenario output in the benefit status records are displayed as "New Retroactive Eligibility","New Retroactive Enrollment"
    Given I logged into CRM and select a project "GFCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify current eligibilty status is "-- --"
    Then I verify "Plan Change Button" is not displayed
    Then I verify "Edit Button" is not displayed
    Then I verify "Disregard Button" is not displayed
