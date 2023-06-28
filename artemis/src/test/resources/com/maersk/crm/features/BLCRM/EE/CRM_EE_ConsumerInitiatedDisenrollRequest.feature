Feature: Consumer Initiated Disenroll Request


  @CP-15053 @CP-15053-01  @ui-ee @shruti
  Scenario: Verify Disenroll Button is displayed
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given  I created a consumer with population type "FOSTER-CARE"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | isEnrollemntRequired         | no                |
      | otherSegments                |[blank]|
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | subProgramCode                  | FOSTERCARE     |
    And I run create Eligibility and Enrollment API