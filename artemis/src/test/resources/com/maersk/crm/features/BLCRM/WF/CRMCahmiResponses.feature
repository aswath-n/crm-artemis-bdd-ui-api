Feature: Validation of CAHMI/HRA Survey

  @CP-37598 @CP-37598-01 @CP-35800 @CP-35800-01 @crm-regression @ui-wf @ruslan
  Scenario Outline: Verify new survey button is disabled if there no eligible consumers for "<survey>" Survey
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-03.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | MEDICAIDMCHB        |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "<survey>" survey
    Then I verify "<survey>" survey label
    And I verify no records available on the survey slider
    And I verify new survey button is disabled
    And Close the soft assertions
    Examples:
    | survey |
    | CAHMI  |
    | HRA    |

  @CP-37598 @CP-37598-02 @CP-35800 @CP-35800-02 @crm-regression @ui-wf @ruslan
  Scenario: Verify only eligible consumers presented on the list and user is able to start survey for CAHMI/HRA Survey
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "FOSTER-CARE" with data
      | saveConsumerInfo | 29502-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | facilityInfo        |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | planId                       | 1880                |
      | programCode                  | F                   |
      | subProgramTypeCode           | FOSTERCARE          |
      | planCode                     | 84                  |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29502-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDGF          |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29502-01.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29502-01a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29502-01.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 29502-01a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDGF                         |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 29502-01.consumerId                |
      | [1].enrollmentId       | 29502-01a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDGF                         |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 29502-01a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
# CREATING CONSUMER 2
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-03.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | MEDICAIDMCHB        |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29502-03.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDMCHB        |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29502-03.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29502-03a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29502-03.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 29502-03a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDMCHB                       |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 29502-03.consumerId                |
      | [1].enrollmentId       | 29502-03a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDMCHB                       |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-03.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 29502-03a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click submit button on enrollment update
    And I identify eligible consumers for "CAHMI" survey and store in the list
    And I click "CAHMI" survey
    Then I click on new survey button
    And I verify only "CAHMI" eligible consumers presented on the drop down list
    And I select eligible consumer on the survey slider
    And I click on start survey
    And I click on close survey
    And I click future enrollment "PLAN CHANGE" Button for a consumer first name "29502-03.firstName" and last name "29502-03.lastName"
    When I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And I identify eligible consumers for "HRA" survey and store in the list
    And I click "HRA" survey
    Then I click on new survey button
    And I verify only "HRA" eligible consumers presented on the drop down list
    And I select eligible consumer on the survey slider
    And I click on start survey
    And Close the soft assertions


  @CP-35774 @CP-35774-01 @crm-regression @ui-wf @kamil
  Scenario: Verify following questions must be captured as part of the survey for CAHMIs
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "FOSTER-CARE" with data
      | saveConsumerInfo | 29502-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | facilityInfo        |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | planId                       | 1880                |
      | programCode                  | F                   |
      | subProgramTypeCode           | FOSTERCARE          |
      | planCode                     | 84                  |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29502-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDGF          |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29502-01.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29502-01a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29502-01.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 29502-01a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDGF                         |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 29502-01.consumerId                |
      | [1].enrollmentId       | 29502-01a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDGF                         |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 29502-01a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
# CREATING CONSUMER 2
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-03.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | MEDICAIDMCHB        |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29502-03.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDMCHB        |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29502-03.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29502-03a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29502-03.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 29502-03a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDMCHB                       |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 29502-03.consumerId                |
      | [1].enrollmentId       | 29502-03a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDMCHB                       |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-03.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 29502-03a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click submit button on enrollment update
    And I identify eligible consumers for "CAHMI" survey and store in the list
    And I click "CAHMI" survey
    Then I click on new survey button
    And I verify only "CAHMI" eligible consumers presented on the drop down list
    And I select eligible consumer on the survey slider
    And I click on start survey
    Then Verify following questions must be captured as part of the survey for CAHMI
      | Does this child have any health problems or medical treatments your health plan should know about?                                                                         |
      | Is this child pregnant?                                                                                                                                                    |
      | Does your child currently need or use medicine prescribed by a doctor (other than vitamins)?                                                                               |
      | Does your child need or use medical care, mental health, or educational services than is usual for most children of the same age?                                          |
      | Is your child limited or prevented in any way (his/her) ability to do things most children the same age can do?                                                            |
      | Does your child need or get special therapy, such as physical, occupational, or speech therapy?                                                                            |
      | Does your child have any kind of emotional, developmental, or behavioral problem?                                                                                          |
      | Does your child have any special medical procedures that have already been scheduled? Examples include chemotherapy, surgery, allergy shots, or other therapy of any kind? |
    And Close the soft assertions

  @CP-37599 @CP-37599-01 @crm-regression @ui-wf @ruslan
  Scenario: Verify On Save display CAHMI responses
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "FOSTER-CARE" with data
      | saveConsumerInfo | 29502-07 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-07.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | facilityInfo        |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | planId                       | 1880                |
      | programCode                  | F                   |
      | subProgramTypeCode           | FOSTERCARE          |
      | planCode                     | 84                  |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-07.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29502-07.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDGF          |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29502-07.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29502-07a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29502-07.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 29502-07a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDGF                         |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 29502-07.consumerId                |
      | [1].enrollmentId       | 29502-07a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDGF                         |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-07.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 29502-07a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Enroll" Button for a consumer
    And I select "AMERIGROUP COMMUNITY CARE" from Available Plans
    And I click submit button on enrollment update
    And I identify eligible consumers for "CAHMI" survey and store in the list
    And I click "CAHMI" survey
    Then I click on new survey button
    And I verify only "CAHMI" eligible consumers presented on the drop down list
    And I select eligible consumer on the survey slider
    And I click on start survey
    And I will provide answers with following questions for CAHMI survey
      | Does this child have any health problems or medical treatments your health plan should know about?                                                                         | NO            |
      | Does your child need or use medical care, mental health, or educational services than is usual for most children of the same age?                                          | NO            |
      | Does your child have any special medical procedures that have already been scheduled? Examples include chemotherapy, surgery, allergy shots, or other therapy of any kind? | I DO NOT KNOW |
    And I click on save button on survey response
    Then I verify survey slider is collapsed
    And I verify icon is displayed for "CAHMI" survey
    And I click "CAHMI" survey
    And I verify survey response is presented on the survey slider
    And Close the soft assertions

  @CP-35804 @CP-35804-01 @crm-regression @ui-wf @ruslan
  Scenario: Verify On Save display HRA responses
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 29502-10 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-10.consumerId |
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-10.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | MEDICAIDMCHB        |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 29502-10.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDMCHB        |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "29502-10.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "29502-10a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 29502-10.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 29502-10a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDMCHB                       |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 29502-10.consumerId                |
      | [1].enrollmentId       | 29502-10a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDMCHB                       |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 29502-10.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 29502-10a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click future enrollment "PLAN CHANGE" Button for a consumer first name "29502-10.firstName" and last name "29502-10.lastName"
    When I select A plan from Available Plans
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
    And I identify eligible consumers for "HRA" survey and store in the list
    And I click "HRA" survey
    Then I click on new survey button
    And I verify only "HRA" eligible consumers presented on the drop down list
    And I select eligible consumer on the survey slider
    And I click on start survey
    And I will provide answers with following questions for HRA survey
      | Do you or a family member have any doctors appointments in the next month?                  | NO            |
      | Are you or a family member pregnant?                                                        | NO            |
      | Tell us about any health problems or treatment plans that you or your family member’s have? | I DO NOT KNOW |
    And I click on save button on survey response
    Then I verify survey slider is collapsed
    And I verify icon is displayed for "HRA" survey
    And I click "HRA" survey
    And I verify survey response is presented on the survey slider
    And Close the soft assertions


  @API-CP-37438 @API-CP-37601 @API-CP-37438-01 @API-CP-37438-02 @API-WF @API-CRM @kamil @task-manag-ms-WM @API-CRM-Regression
  Scenario: HRA/CAHMI Survey Response API validation
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "FOSTER-CARE" with data
      | saveConsumerInfo | 37438-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37438-01.consumerId |
      | isEligibilityRequired        | yes                 |
      | otherSegments                | facilityInfo        |
      | isEnrollemntRequired         | no                  |
      | recordId                     | 3                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         | 1stDayofLastMonth   |
      | txnStatus                    | Accepted            |
      | planId                       | 1880                |
      | programCode                  | F                   |
      | subProgramTypeCode           | FOSTERCARE          |
      | planCode                     | 84                  |
      | anniversaryDate              | anniversaryDate     |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37438-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 37438-01.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDGF          |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "37438-01.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "37438-01a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 37438-01.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 37438-01a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDGF                         |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 37438-01.consumerId                |
      | [1].enrollmentId       | 37438-01a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDGF                         |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37438-01.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | M                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 37438-01a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
# CREATING CONSUMER 2
    Given  I created a consumer with population type "PREGNANT-WOMEN" with data
      | saveConsumerInfo | 37438-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37438-03.consumerId |
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
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37438-03.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | recordId                     | 4                   |
      | isEnrollmentProviderRequired | no                  |
      | enrollmentStartDate          | 1stDayofLastMonth   |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | subProgramTypeCode           | MEDICAIDMCHB        |
      | planCode                     | 84                  |
      | planId                       | 145                 |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I perform plan transfer via API to new plan with data
      | [0].consumerId         | 37438-03.consumerId |
      | [0].planId             | 145                 |
      | [0].planCode           | 85                  |
      | [0].startDate          | fdnxtmth::          |
      | [0].endDate            | highDate::          |
      | [0].subProgramTypeCode | MEDICAIDMCHB        |
      | [0].serviceRegionCode  | East                |
      | [0].selectionReason    | delete::            |
      | [0].anniversaryDate    | anniversaryDate::   |
      | [0].channel            | SYSTEM_INTEGRATION  |
      | [0].planEndDateReason  | PLAN_TERMINATED     |
    And Wait for 5 seconds
    And I initiated get enrollment by consumer id "37438-03.consumerId"
    When I run get enrollment api
    And I save enrollment ids by discontinuedEnrollmentId and selectedEnrollmentId with name "37438-03a"
    And I send API call with name "OU" for Rejected Selection Task process Outbound Update
      | [0].consumerId         | 37438-03.consumerId                |
      | [0].planId             | delete::                           |
      | [0].planCode           | 84                                 |
      | [0].enrollmentId       | 37438-03a.discontinuedEnrollmentId |
      | [0].status             | DISENROLL_SUBMITTED                |
      | [0].txnStatus          | DISENROLL_SUBMITTED                |
      | [0].startDate          | 1stdayoflastmonth::                |
      | [0].endDate            | lstdaymnth::                       |
      | [0].enrollmentType     | delete::                           |
      | [0].subProgramTypeCode | MEDICAIDMCHB                       |
      | [0].serviceRegionCode  | East                               |
      | [1].planId             | delete::                           |
      | [1].planCode           | 85                                 |
      | [1].consumerId         | 37438-03.consumerId                |
      | [1].enrollmentId       | 37438-03a.selectedEnrollmentId     |
      | [1].startDate          | fdnxtmth::                         |
      | [1].endDate            | highDate::                         |
      | [1].status             | SUBMITTED_TO_STATE                 |
      | [1].txnStatus          | SUBMITTED_TO_STATE                 |
      | [1].subProgramTypeCode | MEDICAIDMCHB                       |
      | [1].serviceRegionCode  | East                               |
    And Wait for 5 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37438-03.consumerId |
      | isEligibilityRequired        | no                  |
      | otherSegments                ||
      | isEnrollemntRequired         | yes                 |
      | isEnrollmentProviderRequired | no                  |
      | recordId                     | 17                  |
      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentEndDate            | highDate            |
      | anniversaryDate              | anniversaryDate     |
      | eligibilityStartDate         ||
      | txnStatus                    | Accepted            |
      | programCode                  | H                   |
      | planCode                     | 85                  |
      | planId                       | 145                 |
      | serviceRegionCode            | East                |
    And User provide other enrollment segments details
      | startDate         | 1stDayofNextMonth              |
      | endDate           | highDate                       |
      | genericFieldText1 | 37438-03a.selectedEnrollmentId |
      | segmentTypeCode   | OTHER_TXN                      |
    And I run create Eligibility and Enrollment API
    And Wait for 5 seconds
    And I will get the Authentication token for proxy API with projectId "92" roleId "629"
    Then I initiated Survey Response API
    Then I run Survey Response API call with data
      |[0].referenceId|37438-03.consumerId|
    And Validate Survey Response API payload response
