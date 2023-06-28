Feature: Verify Add Case Members Button and Delete consumer button is enabled
  after removing the selected plan  for Enroll action

 @CP-11954 @CP-11954-01  @ui-ee @crm-regression @Olga @refbyshruti
 Scenario: Verify Add Case Members Button and Delete consumer button is disabled after selecting plan for ENroll action
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
 And I Add Case Members Button and Select all case members
 When I select A plan from Available Plans
 Then I verify  the Add Case Members button is disable
 Then I verify Delete button for Case members selected is disabled

  @CP-11954 @CP-11954-02  @ui-ee @crm-regression @Olga @refbyshruti
  Scenario: Verify Add Case Members Button and Delete consumer button is enabled after removing the selected plan  for ENroll action
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
    And I Add Case Members Button and Select all case members
    When I select A plan from Available Plans
    Then I verify  the Add Case Members button is disable
    Then I verify Delete button for Case members selected is disabled
    When I click on Remove Plan Option
    Then I verify Add Case Members button is disabled & Delete button is enabled for consumer

  @CP-11954 @CP-11954-03  @ui-ee @crm-regression @Olga @refbyshruti
  Scenario: Verify Add Case Members Button and Delete consumer button is disabled after selecting plan for plan change action
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
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
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
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
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
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I Add Case Members Button and Select all case members
    When I select A plan from Available Plans
    Then I verify  the Add Case Members button is disable
    Then I verify Delete button for Case members selected is disabled

  @CP-11954 @CP-11954-04  @ui-ee @crm-regression @Olga @refbyshruti
  Scenario: Verify Add Case Members Button and Delete consumer button is enabled after removing the selected plan  for Plan change action
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
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
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
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
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
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | no                |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 4                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofLastMonth |
      | eligibilityStartDate         |[blank]|
      | txnStatus                    | Accepted          |
      | programCode                  | M                 |
      | planCode                     | 84                |
      | planId                       | 1895              |
      | anniversaryDate              | anniversaryDate   |
    And I run create Eligibility and Enrollment API
    When I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I Add Case Members Button and Select all case members
    When I select A plan from Available Plans
    Then I verify  the Add Case Members button is disable
    Then I verify Delete button for Case members selected is disabled
    When I click on Remove Plan Option
    Then I verify Add Case Members button is disabled & Delete button is enabled for consumer

