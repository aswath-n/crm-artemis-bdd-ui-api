Feature: Survey API - DC-EB

  @API-CP-36933 @API-CP-36933-01 @API-CP-36934 @API-CP-36934-01 @API-CP-40820 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario: Verify survey response submission - DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 36933-01 |
      | age              | 25       |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 36933-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | coverageCode                 | 231                 |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | past                |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
    And I run create Eligibility and Enrollment API
    When I initiate survey response submission API
    And I prepare survey response for submission
      | referenceType          | Consumer            |
      | referenceId            | 36933-01.consumerId |
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
      | referenceId   | 36933-01.consumerId |
      | startDate     | yesterday           |
      | endDate       | yesterday           |
      | size          | 1                   |
      | from          | 0                   |
      | status        | complete            |
    And I run fetch survey response API
    And I verify fetch survey response with data
      | referenceType | Consumer            |
      | referenceId   | 36933-01.consumerId |
      | languageCode  | EN                  |
      | surveyDate    | yesterday           |
      | status        | Complete            |
      | createTs      | current             |
      | createdBy     | 237                 |
      | updateTs      | null                |
      | updatedBy     | null                |


  @API-CP-35803 @API-CP-38949 @API-CP-38771 @API-CP-36932 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario: Verify survey template and survey details submission - DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
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


  @API-CP-38498 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario: Verify an active survey template - DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I initiate survey template and survey details submission API
    And I prepare survey template for submission
      | surveyTemplateId   | 1234567           |
      | description        | HRA               |
      | templateType       | Consumer          |
      | effectiveStartDate | 1stDayofLastMonth |
      | effectiveEndDate   | lastDayOfNextYear |
      | createdOn          | current           |
      | createdBy          | 239               |
      | updatedOn          | current           |
      | updatedBy          | 239               |
    When I run survey template and survey details submission
    When I initiate "active" survey template fetch API
    And I run fetch survey template API
    And I verify fetch survey template contains given survey template ids
      | 1234567 |

  @API-CP-38498 @API-CP-38498-02 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario: Verify a past survey template - DC-EB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    When I initiate survey template and survey details submission API
    And I prepare survey template for submission
      | surveyTemplateId   | 7654321            |
      | description        | HRA                |
      | templateType       | Consumer           |
      | effectiveStartDate | 1stDayofLastMonth  |
      | effectiveEndDate   | lastDayofLastMonth |
      | createdOn          | current            |
      | createdBy          | 239                |
      | updatedOn          | current            |
      | updatedBy          | 239                |
    When I run survey template and survey details submission
    When I initiate "past" survey template fetch API
    And I run fetch survey template API
    And I verify fetch survey template contains given survey template ids
      | 7654321 |


  @API-CP-38001 @API-CP-38001-01 @API-CP-42490 @API-CP-42490-01 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario: DC EB Initiate HRA/CAHMI Survey SR - Story # 1 - Selection Made
  AC 1.0 Selection From Members ( excluding AA Process)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 38001-01 |
      | age              | 25       |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38001-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | coverageCode                 | 231                 |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | past                |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | 38001-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highdatedc::        |
      | [0].programTypeCode    | MEDICAID            |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].consumerId     | 38001-01.consumerId    |
      | [0].planId         | delete::               |
      | [0].planCode       | 081080400              |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | SUBMITTED_TO_STATE     |
      | [0].txnStatus      | SUBMITTED_TO_STATE     |
      | [0].startDate      | fdnxtmth::             |
      | [0].endDate        | highdatedc::           |
      | [0].enrollmentType | delete::               |
    And Wait for 30 seconds
    # Saving auto assignment transaction id and service request id for this consumer after calling mars/eb/autoAssignmentTxn/find endpoint
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 38001-01.consumerId |
    Then I run auto assignment transaction get Api
    Then I verify service request id is a non zero value
    # Getting tasks for this consumer by calling mars/taskmanagement/tasks/{taksId} endpoint
    And I run service request details get Api
    # Below verification introduced with CP-38001 is removed with CP-42490
    # Verify AC 1.0 Selection From Members ( excluding AA Process)
#    Then I verify tasks for newly created service request whose id is 1 greater than the original with data
#      | taskTypeId             | 16402       |
#      | [0].taskFieldId        | 12          |
#      | [0].selectionFieldName | Channel     |
#      | [0].selectionVarchar   | PHONE       |
#      | [1].taskFieldId        | 71          |
#      | [1].selectionFieldName | Disposition |
    Then I verify no task of type id "16402" is created


  @API-CP-38001 @API-CP-38001-02 @API-CP-42490 @API-CP-42490-02 @API-CP-42490-02 @kursat @API-DC-EE @DC-EB-API-Regression
  Scenario: DC EB Initiate HRA/CAHMI Survey SR - Story # 1 - Plan Change
  AC 1.2 Plan Change
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 38001-02 |
      | age              | 25       |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38001-02.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                |[blank]|
      | coverageCode                 | 231                 |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 25                  |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | past                |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | eligibilityEndDate           | highDate-DC         |
      | programCode                  | MEDICAID            |
      | subProgramTypeCode           | DCHF                |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | 38001-02.consumerId    |
      | [0].planId             | 145                    |
      | [0].planCode           | 081080400              |
      | [0].startDate          | 1stdayofpresentmonth:: |
      | [0].endDate            | highdatedc::           |
      | [0].programTypeCode    | MEDICAID               |
      | [0].subProgramTypeCode | DCHF                   |
      | [0].serviceRegionCode  | Northeast              |
    And Wait for 5 seconds
    And I send API call with name "OU" for process Outbound Update
      | [0].consumerId     | 38001-02.consumerId    |
      | [0].planId         | delete::               |
      | [0].planCode       | 081080400              |
      | [0].enrollmentId   | c.data[0].enrollmentId |
      | [0].status         | SUBMITTED_TO_STATE     |
      | [0].txnStatus      | SUBMITTED_TO_STATE     |
      | [0].startDate      | 1stdayofpresentmonth:: |
      | [0].endDate        | highdatedc::           |
      | [0].enrollmentType | delete::               |
    And Wait for 30 seconds
    # Saving auto assignment transaction id and service request id for this consumer after calling mars/eb/autoAssignmentTxn/find endpoint
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 38001-02.consumerId |
    Then I run auto assignment transaction get Api
    Then I verify service request id is a non zero value
    # Getting tasks after selection made for this consumer by calling mars/taskmanagement/tasks/{taksId} endpoint
    And I run service request details get Api
    # Below verification introduced with CP-38001 is removed with CP-42490
#    Then I verify tasks for newly created service request whose id is 1 greater than the original with data
##      | taskTypeId             | 16402       |
#      | [0].taskFieldId        | 12          |
#      | [0].selectionFieldName | Channel     |
#      | [0].selectionVarchar   | PHONE       |
#      | [1].taskFieldId        | 71          |
#      | [1].selectionFieldName | Disposition |
    Then I verify no task of type id "16402" is created
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 38001-02.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | isEnrollmentProviderRequired | no                   |
      | recordId                     | 15                   |
      | enrollmentRecordId           | 15                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate-DC          |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | DCHF                 |
      | planCode                     | 081080400            |
      | planId                       | 26                   |
      | serviceRegionCode            | Northeast            |
      | anniversaryDate              | anniversaryDate      |
      | channel                      | SYSTEM_INTEGRATION   |
    And I run create Eligibility and Enrollment API
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 38001-02.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 081080400           |
      | [0].startDate          | fdnxtmth::          |
      | [0].subProgramTypeCode | DCHF                |
      | [0].serviceRegionCode  | Northeast           |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 30 seconds
    # Getting tasks after plan change for this consumer by calling mars/taskmanagement/tasks/{taksId} endpoint
    And I run service request details get Api
    Then I verify no task of type id "16402" is created
# Below verification introduced with CP-38001 is removed with CP-42490
#    Verify AC 1.2 Plan Change
#    And Wait for 100 seconds
#    Then I verify tasks for newly created service request whose id is 2 greater than the original with data
#      | [0].taskFieldId        | 71                 |
#      | [0].selectionFieldName | Disposition        |
#    Below validations may not be valid any more. There is only 1 record ( taskFieldId 71) created instead of 2 and no longer taskFieldId 12 is created.
#      | taskTypeId             | 16402              |
#      | taskTypeId             | 16431              |
#      | [0].taskFieldId        | 12                 |
#      | [0].selectionFieldName | Channel            |
#      | [0].selectionVarchar   | SYSTEM_INTEGRATION |
#      | [1].taskFieldId        | 71                 |
#      | [1].selectionFieldName | Disposition        |
