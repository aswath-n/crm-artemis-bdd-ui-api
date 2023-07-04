Feature: Survey UI - DC-EB

  @CP-41394 @CP-41394-01 @ui-ee-dc @crm-regression  @DC-EB-UI-Regression @priyal
  Scenario Outline: Verify Update Survey Status to Withdrawn for Plan Change Disregard
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-above60" with data
      | saveConsumerInfo | <consumerId> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | eligibilityRecordId          | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | txnStatus                    | Accepted            |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
      | coverageCode                 | 130                 |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<consumerId>a" for update Enrollment information
      | [0].consumerId         | <consumerId>.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
#      | [0].startDate          | fdnxtmth::          |
      | [0].startDate          | fdofmnth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<consumerId>a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION     |
      | [0].startDate    | fdofmnth::             |
#      | [0].startDate    | fdnxtmth::             |
      | [0].planId       | delete::               |
      | [0].planCode     | 081080400              |
      | [0].consumerId   | <consumerId>.consumerId    |
      | [0].enrollmentId | c.data[0].enrollmentId |
      | [0].status       | SUBMITTED_TO_STATE     |
      | [0].txnStatus    | SUBMITTED_TO_STATE     |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | no                  |
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 15                  |
      | enrollmentRecordId           | 15                  |
#      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentStartDate          | 1stDayofPresentMonth|
      | enrollmentEndDate            | highDate-DC         |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         |[blank]|
      | programCode                  | MEDICAID            |
      | planCode                     | 081080400           |
      | planId                       | 145                 |
      | subProgramTypeCode           | DCHF                |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "<userRole>" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<consumerId>.firstName",Last Name as "<consumerId>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And Wait for 10 seconds
    And I click "Plan Change" Button for a consumer
    And I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click on Disregard button on program & benefits page
    Then I verify Warning message is displayed "Are you sure you want to disregard the enrollment selection for this consumer?"
    And I click on continue for Disregard Enrollment
    When I initiate survey response submission API
    #    CP-41394 AC 1.0 Updating Survey Status for CSR/Supervisor Role
    And I prepare survey response for submission
      | referenceType          | Consumer                |
      | referenceId            | <consumerId>.consumerId |
      | surveyDate             | yesterday               |
      | surveyResponse         | yes                     |
      | numberOfQuestions      | 2                       |
      | 1.surveyDetailsId      | random                  |
      | 1.questionNumber       | 1                       |
      | 1.questionText         | Test question 7a?       |
      | 1.parentSurveyDetailId | 890                     |
      | 2.surveyDetailsId      | random                  |
      | 2.questionNumber       | 2                       |
      | 2.questionText         | Test question 7b?       |
      | 2.parentSurveyDetailId | 890                     |
      | status                 | WITHDRAWN               |
      | createdOn              | current                 |
      | createdBy              | 9547                    |
    When I run survey response submission
    When I initiate survey response fetch API
    #    CP-41394 AC 1.0 Updating Survey Status for CSR/Supervisor Role
    And I prepare survey response for fetch
      | referenceType | consumer                |
      | referenceId   | <consumerId>.consumerId |
      | startDate     | yesterday               |
      | endDate       | yesterday               |
      | size          | 1                       |
      | from          | 0                       |
      | status        | WITHDRAWN               |
    And I run fetch survey response API
    #    CP-41394 AC 1.0 Updating Survey Status for CSR/Supervisor Role
    And I verify fetch survey response with data
      | referenceType | Consumer                |
      | referenceId   | <consumerId>.consumerId |
      | surveyDate    | yesterday               |
      | status        | WITHDRAWN               |
      | createTs      | current                 |
      | createdBy     | 9547                    |
      | updateTs      | null                    |
      | updatedBy     | null                    |
    Examples:
      |userRole         |consumerId|createdBy|
      |Service Account 1|39009-01  |9547     |
      |SVC_mars_tester_2|39009-02  |9604     |