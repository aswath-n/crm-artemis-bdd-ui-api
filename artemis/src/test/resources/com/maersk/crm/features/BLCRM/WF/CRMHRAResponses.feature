Feature: Validation of HRA Survey

  @CP-35773 @CP-35773-01 @crm-regression @ui-wf @priyal
  Scenario: Verify following questions must be captured as part of the survey for HRA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 29502-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-03.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                ||
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          ||
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | anniversaryDate              | anniversaryDate     |
      | subProgramTypeCode           | MEDICAIDMCHB        |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select "PEACH STATE" from Available Plans
    And I click submit button on enrollment update
    And I identify eligible consumers for "CAHMI" survey and store in the list
    And I click "HRA" survey
    Then I click on new survey button
    And I select eligible consumer on the survey slider
    And I click on start survey
    Then Verify following questions must be captured as part of the survey for HRA
      | Do you or a family member have any doctors appointments in the next month|
      | Do you or a family member take any medicines that have been prescribed by a doctor?|
      | Do you or a family member get home-based care?|
      | Are you or a family member pregnant?|
      | When was the last time you and your family members saw a doctor?|
      | When was the last time you and your family members saw a dentist?|
      | Tell us about any health problems or treatment plans that you or your family member’s have?|
    And Close the soft assertions
