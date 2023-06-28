Feature: Baseline Program & Benefits Screen: Update for "PCP Name" field on Enrollment Segment


  @CP-33459 @CP-33459-01 @CP-33459-02 @CP-33459-03 @ui-ee @crm-regression @kursat
  Scenario: Baseline Program & Benefits Screen: Update for "PCP Name" field on Enrollment Segment
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 33459-10 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId            | 33459-10.consumerId  |
      | isEligibilityRequired | yes                  |
      | otherSegments         |[blank]|
      | isEnrollemntRequired  | no                   |
      | recordId              | 21                   |
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | enrollmentStartDate   |[blank]|
      | txnStatus             | Accepted             |
      | programCode           | M                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-10.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
      | requestedBy                  | 142                  |
      | isEnrollmentProviderRequired | yes                  |
      | providerNpi                  | 1003071242           |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 33459-11 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId            | 33459-11.consumerId  |
      | isEligibilityRequired | yes                  |
      | otherSegments         |[blank]|
      | isEnrollemntRequired  | no                   |
      | recordId              | 21                   |
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | enrollmentStartDate   |[blank]|
      | txnStatus             | Accepted             |
      | programCode           | M                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-11.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
      | requestedBy                  | 142                  |
      | isEnrollmentProviderRequired | yes                  |
      | providerNpi                  | 123456789            |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | 33459-12 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId            | 33459-12.consumerId  |
      | isEligibilityRequired | yes                  |
      | otherSegments         |[blank]|
      | isEnrollemntRequired  | no                   |
      | recordId              | 21                   |
      | eligibilityStartDate  | 1stDayofPresentMonth |
      | enrollmentStartDate   |[blank]|
      | txnStatus             | Accepted             |
      | programCode           | M                    |
    And I run create Eligibility and Enrollment API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33459-12.consumerId  |
      | isEligibilityRequired        | no                   |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                  |
      | recordId                     | 4                    |
      | enrollmentStartDate          | 1stDayofPresentMonth |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | enrollmentEndDate            | highDate             |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
      | planCode                     | 84                   |
      | planId                       | 145                  |
      | serviceRegionCode            | East                 |
      | anniversaryDate              | anniversaryDate      |
      | requestedBy                  | 142                  |
      | isEnrollmentProviderRequired | no                   |
    And I run create Eligibility and Enrollment API
    #***********************************************************
    Given I logged into CRM and select a project "BLCRM"
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "33459-10.firstName" and Last Name as "33459-10.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "33459-10.firstName" and last name "33459-10.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population |
#      | CURRENT ELIGIBILITY.AID CATEGORY        | hidden                      |
#      | CURRENT ELIGIBILITY.RCP                 | hidden                      |
#      | CURRENT ELIGIBILITY.SERVICE REGION      | East                        |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver   |
      | CURRENT ELIGIBILITY.END DATE            | -- --                       |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | AMERIGROUP COMMUNITY CARE   |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                    |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration          |
      | CURRENT ENROLLMENT.PCP NAME             | RAMI ARROUK                 |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver   |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver               |
#      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | is displayed                |
#      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                |
    Then I verify program & benefit info page for consumer first name "33459-11.firstName" and last name "33459-11.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population |
#      | CURRENT ELIGIBILITY.AID CATEGORY        | hidden                      |
#      | CURRENT ELIGIBILITY.RCP                 | hidden                      |
#      | CURRENT ELIGIBILITY.SERVICE REGION      | East                        |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver   |
      | CURRENT ELIGIBILITY.END DATE            | -- --                       |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | AMERIGROUP COMMUNITY CARE   |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                    |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration          |
      | CURRENT ENROLLMENT.PCP NAME             | 123456789                   |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver   |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver               |
#      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | is displayed                |
#      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                |
    Then I verify program & benefit info page for consumer first name "33459-12.firstName" and last name "33459-12.lastName" with data
      | CURRENT ELIGIBILITY.CONSUMER POPULATION | Medicaid-General Population |
#      | CURRENT ELIGIBILITY.AID CATEGORY        | hidden                      |
#      | CURRENT ELIGIBILITY.RCP                 | hidden                      |
#      | CURRENT ELIGIBILITY.SERVICE REGION      | East                        |
      | CURRENT ELIGIBILITY.START DATE          | 1stDayofPresentMonthUIver   |
      | CURRENT ELIGIBILITY.END DATE            | -- --                       |
      #*********
      | CURRENT ENROLLMENT.PLAN NAME            | AMERIGROUP COMMUNITY CARE   |
      | CURRENT ENROLLMENT.SELECTION STATUS     | Accepted                    |
      | CURRENT ENROLLMENT.CHANNEL              | System Integration          |
      | CURRENT ENROLLMENT.PCP NAME             | -- --                       |
      | CURRENT ENROLLMENT.START DATE           | 1stDayofPresentMonthUIver   |
      | CURRENT ENROLLMENT.END DATE             | highDateUIver               |
#      | CURRENT ENROLLMENT.PCP SELECT BUTTON    | is displayed                |
#      | CURRENT ENROLLMENT.PLAN CHANGE BUTTON   | is displayed                |