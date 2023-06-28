@autoAssignment
Feature: Configure Automation Assignment for MEDICAID GENERAL

  @API-CP-20622 @API-CP-20633 @API-CP-22043 @API-CP-20622-01 @API-CP-20619 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Identify Auto Assignment for Family Plan and auto assignment is created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    And I initiated auto assignment configuration Api
    And I provided auto assignment configuration details
      | priorPlan.isMostRecentPlan           | false |
      | priorPlan.isLrgerDurationPlan        | false |
      | priorPlan.priorEnrollmentNoOfMonths  | 12    |
      | familyPlan.isPendingPlan             | false |
      | familyPlan.isEnrolledAndPendingPlan  | true  |
      | familyPlan.isEnrolledPlan            | false |
      | familyPlan.priorEnrollmentNoOfMonths | 12    |
    And I run auto assignment configuration post API
    And I initiated auto assignment configuration get Api
    And I run auto assignment configuration get API
    Then I validate auto assignment configuration values updated
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId |[blank]|
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId |[blank]|
    And I run auto assignment transaction update Api
    Given I initiated auto assignment plan batch process Api
    When I run auto assignment plan batch process Api
    And Wait for 7 seconds
    Given I initiated get enrollment by consumer id ""
    When I run get enrollment api


  @API-CP-20622 @API-CP-20624 @API-CP-20622-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Identify Auto Assignment for Prior Plan
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | past                 |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | past                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofNextMonth |
      | eligibilityStatusCode        |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    And I initiated auto assignment configuration Api
    And I provided auto assignment configuration details
      | priorPlan.isMostRecentPlan           | true  |
      | priorPlan.isLrgerDurationPlan        | false |
      | priorPlan.priorEnrollmentNoOfMonths  | 12    |
      | familyPlan.isPendingPlan             | false |
      | familyPlan.isEnrolledAndPendingPlan  | false |
      | familyPlan.isEnrolledPlan            | false |
      | familyPlan.priorEnrollmentNoOfMonths | 12    |
    And I run auto assignment configuration post API
    And I initiated auto assignment configuration get Api
    And I run auto assignment configuration get API
    Then I validate auto assignment configuration values updated
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId |[blank]|
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId |[blank]|
    And I run auto assignment transaction update Api
    Given I initiated auto assignment plan batch process Api
    When I run auto assignment plan batch process Api
    And Wait for 7 seconds
    Given I initiated get enrollment by consumer id ""
    When I run get enrollment api

  @API-CP-20620 @API-CP-20620-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Update the consumer’s eligibility segment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated auto assignment configuration Api
    And I remove "marketShare" from the request
    And I run auto assignment configuration post API
    And I initiated auto assignment configuration get Api
    And I run auto assignment configuration get API


  @API-CP-20623 @API-CP-20623-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Check Service Request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated auto assignment transaction plan assignment Api
    When I provided auto assignment transaction plan assignment details
    And I run auto assignment transaction plan assignment API

  @API-CP-20632  @API-CP-20632-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Capture New Eligibility Requiring Autoassignment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API


  @API-CP-22220 @API-EE @API-CRM-Regression @kursat
  Scenario: Auto assign plan (Market Share) - Medicaid-General
  CP-22220 1.0 Plan Percentage Assignment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    # *************** CONSUMER 01 **********************
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 22220-01 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 22220-01.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 22220-01.consumerId |
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId | 22220-01.consumerId |
    And I run auto assignment transaction update Api
    # *************************************************
    # *************** CONSUMER 02 **********************
    And Wait for 2 seconds
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 22220-02 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 22220-02.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 22220-02.consumerId |
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId | 22220-02.consumerId |
    And I run auto assignment transaction update Api
    # *************************************************
    # *************** CONSUMER 03 **********************
    And Wait for 2 seconds
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 22220-03 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 22220-03.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 22220-03.consumerId |
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId | 22220-03.consumerId |
    And I run auto assignment transaction update Api
    # *************************************************
    # *************** CONSUMER 04 **********************
    And Wait for 2 seconds
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 22220-04 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 22220-04.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 22220-04.consumerId |
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId | 22220-04.consumerId |
    And I run auto assignment transaction update Api
    # *************************************************
    # *************** CONSUMER 05 **********************
    And Wait for 2 seconds
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 22220-05 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 22220-05.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 22220-05.consumerId |
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId | 22220-05.consumerId |
    And I run auto assignment transaction update Api
    # *************************************************
    # *************** CONSUMER 06 **********************
    And Wait for 2 seconds
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 22220-06 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 22220-06.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 22220-06.consumerId |
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId | 22220-06.consumerId |
    And I run auto assignment transaction update Api
    # *************************************************
    # *************** CONSUMER 07 **********************
    And Wait for 2 seconds
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 22220-07 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 22220-07.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 22220-07.consumerId |
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId | 22220-07.consumerId |
    And I run auto assignment transaction update Api
    # *************************************************
    # *************** CONSUMER 08 **********************
    And Wait for 2 seconds
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 22220-08 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 22220-08.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 22220-08.consumerId |
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId | 22220-08.consumerId |
    And I run auto assignment transaction update Api
    # *************************************************
    # *************** CONSUMER 09 **********************
    And Wait for 2 seconds
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 22220-09 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 22220-09.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 22220-09.consumerId |
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId | 22220-09.consumerId |
    And I run auto assignment transaction update Api
    # *************************************************
    # *************** CONSUMER 10 **********************
    And Wait for 2 seconds
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 22220-10 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 22220-10.consumerId  |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 22220-10.consumerId |
    Then I run auto assignment transaction get Api
    Given I initiated update auto assignment transaction Api
    When I provide details for updating auto assignment transaction api
      | consumerId | 22220-10.consumerId |
    And I run auto assignment transaction update Api
    # *************************************************
    And I get existing auto assignment plan configuration details
    And I run below provided auto assignment plan configuration details
#      | 1.planCode                       | 84   |
#      | 1.planPercentage                 | 25.0 |
#      | 2.planCode                       | 85   |
#      | 2.planPercentage                 | 25.0 |
#      | 3.planCode                       | 86   |
#      | 3.planPercentage                 | 25.0 |
#      | 4.planCode                       | 87   |
#      | 4.planPercentage                 | 25.0 |
      | 1.planCode       | 84   |
      | 1.planPercentage | 60.0 |
      | 2.planCode       | 85   |
      | 2.planPercentage | 20.0 |
      | 3.planCode       | 86   |
      | 3.planPercentage | 10.0 |
      | 4.planCode       | 87   |
      | 4.planPercentage | 10.0 |
    Given I initiated auto assignment plan batch process Api
    When I run auto assignment plan batch process Api
    And Wait for 60 seconds
    And I get existing auto assignment plan configuration details
    And I save the consumer ids and plan codes auto assigned to below consumers
      | 22220-01.consumerId |
      | 22220-02.consumerId |
      | 22220-03.consumerId |
      | 22220-04.consumerId |
      | 22220-05.consumerId |
      | 22220-06.consumerId |
      | 22220-07.consumerId |
      | 22220-08.consumerId |
      | 22220-09.consumerId |
      | 22220-10.consumerId |
    Then I verify the plans in market share are assigned according to plan percentage distribution
#    | 84.Min | 1 |
#    | 84.Max | 3 |
#    | 85.Min | 1 |
#    | 85.Max | 3 |
#    | 86.Min | 1 |
#    | 86.Max | 3 |
#    | 87.Min | 1 |
#    | 87.Max | 3 |
      | 84.Min | 5 |
      | 84.Max | 7 |
      | 85.Min | 1 |
      | 85.Max | 3 |
      | 86.Min | 0 |
      | 86.Max | 2 |
      | 87.Min | 0 |
      | 87.Max | 2 |

  @API-CP-36158 @API-CP-36158-01 @API-CP-37593 @API-CP-37593-01 @API-EE @API-CRM-Regression @shruti
  Scenario: Trigger Service
  Request-MEDICAID GENERAL-ELIGIBLE
  CP-22220 1.0 Plan Percentage Assignment
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 36158-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 36158-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 36158-1.consumerId |
    Then I run auto assignment transaction get Api
    Then I verify service request id is a non zero value
    When I initiated get task list by staff assigned to
    And I provide staff assigned to as "36158-1.consumerId"
    And I run get task list by staff assigned to
    Then I verify task record details retrieved by get api
    And I run service request details get Api
    And I initiated get benefit status by consumer id "36158-1.consumerId"
    And I run get enrollment api
    And I save changeAllowedUntil date in benefit status
    Then I verify auto assignment due date is correct
    Then I verify task default due date in service request details is correct


  @API-CP-36158 @API-CP-36158-02-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify current Eligibility non Medicaid general population
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 36158-2-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | yes                                                      |
      | enrollmentStartDate          | past                                                     |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 36158-2-1.consumerId |
    Then I run auto assignment transaction get Api
    And I verify auto assignment transaction is not generated


  @API-CP-34576 @API-CP-34576-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @kursat
  Scenario: Baseline Enrollment Receives Choice Selection from Consumer
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 34576-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 34576-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    And I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 34576-1.consumerId |
    When I run auto assignment transaction get Api
    Then I verify auto assignment transaction with data
      | resolution | null |
      | status     | null |
      | planCode   | null |
    And I run service request details get Api
    Then I verify task management tasks with data
      | Status      | Open |
      | Disposition | null |
    Then I send API call with name "OU" for update Enrollment information
      | [0].consumerId         | 34576-1.consumerId |
      | [0].planId             | 145                |
      | [0].planCode           | 84                 |
      | [0].startDate          | fdnxtmth::         |
      | [0].programTypeCode    | M                  |
      | [0].subProgramTypeCode | MEDICAIDGF         |
    And Wait for 5 seconds
    And I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 34576-1.consumerId |
    When I run auto assignment transaction get Api
    Then I verify auto assignment transaction with data
      | resolution | null           |
      | status     | SELECTION_MADE |
      | planCode   | null           |
    And I run service request details get Api
    Then I verify task management tasks with data
      | Status      | Closed         |
      | Disposition | SELECTION_MADE |


  @API-CP-34577 @API-CP-34577-01 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @priyal
  Scenario: Verify Auto Assignment SR is closed when we select Plan
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 36158-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 36158-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 1                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofNextMonth    |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 36158-1.consumerId |
    Then I run auto assignment transaction get Api
    Then I verify service request id is a non zero value
    When I initiated get task list by staff assigned to
    And I provide staff assigned to as "36158-1.consumerId"
    And I run get task list by staff assigned to
    Then I verify task record details retrieved by get api
    And I run service request details get Api
    And I initiated View application api for API created applications
    And I initiated get task by task id "getSRIdFromAPI"
    And I run get task by task id API
    Then I store task details response
    Then I verify Task status and Action Taken Values
      | Status       | Open |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 2                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofNextMonth    |
      | eligibilityStartDate         | 1stDayofNextMonth    |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    And Wait for 2 seconds
    Given I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 36158-1.consumerId |
    Then I run auto assignment transaction get Api
    Then I verify service request id is a non zero value
    When I initiated get task list by staff assigned to
    And I provide staff assigned to as "36158-1.consumerId"
    And I run get task list by staff assigned to
    Then I verify task record details retrieved by get api
    And I run service request details get Api
    And I initiated get task by task id "getSRIdFromAPI"
    And I run get task by task id API
    Then I store task details response
    Then I verify Task status and Action Taken Values
      | Status       | Closed|
      | Disposition  | SELECTION_ACCEPTED|


  # Below scenario is for no selection status and requires manual testing so should not be run on Jenkins
  # 2 more manual scenarios for disregarded and disenrolled can be added as part of CP-37696
  @API-CP-37696 @API-CP-37696-01 @kursat
  Scenario: Baseline Auto assign consumers after timeout of enrollment window - No Selection Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 37696-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 37696-1.consumerId   |
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Selected             |
      | programCode                  | M                    |
      | planId                       | 1740                 |
      | anniversaryDate              | anniversaryDate      |
    And I run create Eligibility and Enrollment API
    # During waiting period below QA-tester has to manually go to `mars-enrollment`.AUTO_ASSIGNMENT_TRANSACTION table
    # and change the DUE_DATE ( last column ) to a past date for this consumer only otherwise auto assignment
    # will not trigger for this consumer and the validations will fail
    And Wait for 100 seconds
    Given I initiated auto assignment plan batch process Api
    When I run auto assignment plan batch process Api
    And Wait for 100 seconds
    And I initiated get auto assignment transaction Api
    And I provide details to get auto assignment transaction api
      | consumerId | 37696-1.consumerId |
    When I run auto assignment transaction get Api
    Then I verify auto assignment transaction with data
      | resolution | Selection Not Made |
      | status     | SELECTION_MADE     |
#      | planCode   | not null           |
    Then I verify latest enrollment by consumer id "37696-1.consumerId" with data
      | planCode  | not null        |
      | status    | SELECTION_MADE  |
      | txnStatus | SELECTION_MADE  |
      | channel   | AUTO_ASSIGNMENT |

