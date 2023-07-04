Feature: IN-EB Recalculate PI for case when consumer is added or removed from case or a DOB changes

  @CP-30258 @CP-30258-01 @CP-35310 @crm-regression @ui-cc-in @Beka
  Scenario: PI is evaluated
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I click on initiate contact record
     When I will get the Authentication token for "IN-EB" in "CRM"
    And I create a case in INEB using Case Loader API with data
      | First Name    | RandomName         |
      | Last Name     | RandomName         |
      | caseID        |[blank]|
      | case role     | Primary individual |
      | Date of Birth | 2001-12-28         |
    When I add new member older then PI for this case
      | First Name    | RandomName |
      | Last Name     | RandomName |
      | case role     | Member     |
      | Date of Birth | 2000-12-27 |
    When I run reevaluatepi API
    And I search existing case by caseID
    And I link the contact to an existing Case or Consumer Profile
    Then consumer on the case who was already the PI, will be removed as PI and will marked as a Case Member

  @CP-30258 @CP-30258-02 @crm-regression @ui-cc-in @Beka
  Scenario: There are 2 or more People on the Case who are oldest - and have same DOB
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I click on initiate contact record
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I create a case in INEB using Case Loader API with data
      | First Name    | RandomName |
      | Last Name     | RandomName |
      | caseID        |[blank]|
      | case role     | Member     |
      | Date of Birth | 2000-12-28 |
    When I add new member older then PI for this case
      | First Name    | RandomName |
      | Last Name     | RandomName |
      | case role     | Member     |
      | Date of Birth | 2000-12-28 |
    When I run reevaluatepi API
    And I search existing case by caseID
    And I link the contact to an existing Case or Consumer Profile
    Then verify in PI table only one consumer has active status

  @CP-30602 @CP-30602-01 @crm-regression @ui-cc-in @Beka
  Scenario: Systematically Set End Date When Date of Death is Populated
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I click on initiate contact record
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I create a case in INEB using Case Loader API with data
      | First Name    | RandomName |
      | Last Name     | RandomName |
      | caseID        |[blank]|
      | case role     | Member     |
      | Date of Birth | 2000-12-28 |
    When I run reevaluatepi API
    And I update existing member and populate DoD "2021-12-13"
    And I search existing case by caseID
    And I link the contact to an existing Case or Consumer Profile
    Then I verify existing member End Date and inactivated "INACTIVE"

  @CP-30602 @CP-30602-02 @crm-regression @ui-cc-in @Beka
  Scenario:  PI is RE-evaluated  When Date of Death is Populated for active PI
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I click on initiate contact record
    And I create a case in INEB using Case Loader API with data
      | First Name    | RandomName |
      | Last Name     | RandomName |
      | caseID        |[blank]|
      | case role     | Member     |
      | Date of Birth | 1988-01-01 |
    When I add new member older then PI for this case
      | First Name    | RandomName |
      | Last Name     | RandomName |
      | case role     | Member     |
      | Date of Birth | 2000-01-01 |
    And I update existing member and populate DoD "2021-12-12"
    When I run reevaluatepi API
    And I search existing case by caseID
    And I link the contact to an existing Case or Consumer Profile
    Then I will see existing PI inactivate and next old member RE-evaluated and become a PI

  @CP-30602 @CP-30602-03 @crm-regression @ui-cc-in @Beka @ui-cc-smoke
  Scenario:  There are 2 or more People on the Case who are oldest - and have same DOB
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I click on initiate contact record
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I create a case in INEB using Case Loader API with data
      | First Name    | RandomName |
      | Last Name     | RandomName |
      | caseID        |[blank]|
      | case role     | Member     |
      | Date of Birth | 2000-01-01 |
    When I add new member older then PI for this case
      | First Name    | RandomName |
      | Last Name     | RandomName |
      | case role     | Member     |
      | Date of Birth | 2000-01-01 |
    When I run reevaluatepi API
    And I search existing case by caseID
    And I link the contact to an existing Case or Consumer Profile
    Then verify in PI table only one consumer has active status

  @CP-9833 @CP-9833-01 @crm-regression @ui-cc-in @Beka
  Scenario:  New consumer received by case loader who does not have any consumer consent records
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I click on initiate contact record
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I create a case whith cunsumerConsent null in INEB using Case Loader
    And I search existing case by caseID
    And I link the contact to an existing Case or Consumer Profile
    Then I verify consuber OptIn by default true for mail and phone

  @CP-33203 @CP-33203-01 @crm-regression @ui-cc-in @chopa
  Scenario: IN-EB: Systematically update default Opt In values for updated consumers
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I click on initiate contact record
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I create a case whith cunsumerConsent null in INEB using Case Loader
    And I search existing case by caseID
    And I link the contact to an existing Case or Consumer Profile
    Then I verify the opt-in channels consumed in the response are not null

  @CP-30601 @CP-34481 @CP-34481-01 @CP-37326-01 @crm-regression @ui-cc-in @Beka
  Scenario: Verify PI recalculation after triggering reevaluatepi API
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I click on initiate contact record
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I create a case in INEB using Case Loader API with data
      | First Name    | RandomName         |
      | Last Name     | RandomName         |
      | caseID        |[blank]|
      | case role     | Member             |
      | Date of Birth | 2001-12-28         |
    When I add new member older then PI for this case
      | First Name    | RandomName |
      | Last Name     | RandomName |
      | case role     | Member     |
      | Date of Birth | 2000-12-27 |
    And I search existing case by caseID
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I verify case not have PI
    When I run reevaluatepi API
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I click on the Demographic Info Tab
    Then I verify older member became a PI on this case
    Then verify in PI table only one consumer has active status

  @CP-30601 @CP-34481 @CP-34481-02 @CP-37326-02 @crm-regression @ui-cc-in @Beka
  Scenario: Verify PI in the other cases will be end dated when I add this PI to new case
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I click on initiate contact record
    When I will get the Authentication token for "IN-EB" in "CRM"
    And I create a case in INEB using Case Loader API with data
      | First Name    | RandomName         |
      | Last Name     | RandomName         |
      | caseID        |[blank]|
      | case role     | Primary individual |
      | Date of Birth | 2001-12-28         |
    And I add same consumer to new case
    And I search existing case by caseID
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I hover over the status and verify end date is current date

