Feature: Survey API - BLCRM

  @API-CP-36933 @API-CP-36933-02 @API-CP-36934 @API-CP-36934-02 @API-CP-40820 @kursat @API-EE @API-CRM-Regression
  Scenario: Verify survey response submission - BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "CUSTOM" with data
      | saveConsumerInfo | 36933-02 |
      | age              | 25       |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 36933-02.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    When I initiate survey response submission API
    And I prepare survey response for submission
      | referenceType          | Consumer            |
      | referenceId            | 36933-02.consumerId |
      | languageCode           | EN                  |
      | surveyDate             | yesterday           |
      | surveyResponse         | yes                 |
      | numberOfQuestions      | 2                   |
      | 1.surveyDetailsId      | random              |
      | 1.questionNumber       | 1                   |
      | 1.questionText         | Test question 7a?   |
      | 1.parentSurveyDetailId | 890                 |
      | 2.surveyDetailsId      | random              |
      | 2.questionNumber       | 2                   |
      | 2.questionText         | Test question 7b?   |
      | 2.parentSurveyDetailId | 890                 |
      | status                 | Complete            |
      | createdOn              | current             |
      | createdBy              | 237                 |
    When I run survey response submission
    When I initiate survey response fetch API
    And I prepare survey response for fetch
      | referenceType | consumer            |
      | referenceId   | 36933-02.consumerId |
      | startDate     | yesterday           |
      | endDate       | yesterday           |
      | size          | 1                   |
      | from          | 0                   |
      | status        | complete            |
    And I run fetch survey response API
    And I verify fetch survey response with data
      | referenceType | Consumer            |
      | referenceId   | 36933-02.consumerId |
      | languageCode  | EN                  |
      | surveyDate    | yesterday           |
      | status        | Complete            |
      | createTs      | current             |
      | createdBy     | 237                 |
      | updateTs      | null                |
      | updatedBy     | null                |


  @API-CP-35803 @API-CP-38949 @API-CP-38771 @API-CP-36932 @kursat @API-EE @API-CRM-Regression
  Scenario: Verify survey template and survey details submission - BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate survey template and survey details submission API
    And I prepare survey template and survey details for submission
      | surveyDetailsId      | random              |
      | surveyTemplateId     | random              |
      | questionNumber       | 78                  |
      | questionTextForm     | questionTextForm?   |
      | questionText         | questionText?       |
      | answerText           | Yes, No, Don't Know |
      | parentSurveyDetailId | 156                 |
      | answerTrigger        | Yes                 |
      | questionType         | TEXT                |
      | description          | HRA                 |
      | templateType         | Consumer            |
      | effectiveStartDate   | 1stDayofLastMonth   |
      | effectiveEndDate     | lastDayOfNextYear   |
      | createdOn            | current             |
      | createdBy            | 238                 |
      | updatedOn            | current             |
      | updatedBy            | 238                 |
    When I run survey template and survey details submission
    When I initiate survey details fetch API
    And I run fetch survey details API
    And I verify fetch survey details with data
      | surveyDetailsId      | random              |
      | surveyTemplateId     | random              |
      | questionNumber       | 78                  |
      | questionTextForm     | questionTextForm?   |
      | questionText         | questionText?       |
      | parentSurveyDetailId | 156                 |
      | answerTrigger        | Yes                 |
      | answerText           | Yes, No, Don't Know |
      | answerFieldSize      | null                |
      | scope                | null                |
      | questionType         | TEXT                |
      | createTs             | current             |
      | createdBy            | 238                 |
      | updateTs             | current             |
      | updatedBy            | 238                 |


  @API-CP-38498 @API-CP-38498-01 @kursat @API-EE @API-CRM-Regression
  Scenario: Verify an active survey template - BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate survey template and survey details submission API
    And I prepare survey template for submission
      | surveyTemplateId     | 1234567             |
      | description          | HRA                 |
      | templateType         | Consumer            |
      | effectiveStartDate   | 1stDayofLastMonth   |
      | effectiveEndDate     | lastDayOfNextYear   |
      | createdOn            | current             |
      | createdBy            | 239                 |
      | updatedOn            | current             |
      | updatedBy            | 239                 |
    When I run survey template and survey details submission
    When I initiate "active" survey template fetch API
    And I run fetch survey template API
    And I verify fetch survey template contains given survey template ids
      | 1234567 |


  @API-CP-38498 @API-CP-38498-02 @kursat @API-EE @API-CRM-Regression
  Scenario: Verify a past survey template - BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate survey template and survey details submission API
    And I prepare survey template for submission
      | surveyTemplateId     | 7654321             |
      | description          | HRA                 |
      | templateType         | Consumer            |
      | effectiveStartDate   | 1stDayofLastMonth   |
      | effectiveEndDate     | lastDayofLastMonth  |
      | createdOn            | current             |
      | createdBy            | 239                 |
      | updatedOn            | current             |
      | updatedBy            | 239                 |
    When I run survey template and survey details submission
    When I initiate "past" survey template fetch API
    And I run fetch survey template API
    And I verify fetch survey template contains given survey template ids
      | 7654321 |