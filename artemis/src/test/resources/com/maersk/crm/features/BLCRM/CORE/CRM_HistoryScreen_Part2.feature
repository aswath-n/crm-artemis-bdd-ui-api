Feature: History Screen Part 2


  @CP-33755 @CP-33755-01 @ui-core @crm-regression @sang
  Scenario: Verify Application related values in the business events filter in the history tab
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    And I create "NEW_RETROACTIVE_ENROLLMENT" enrollment event for use in history tab
    Given I logged into CRM
    When I click case consumer search tab
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I click first consumer ID in search results for case and consumer search page
    When I click on "History" Tab on Contact Dashboard Page
    And I verify "BUSINESS EVENTS" dropdown contains the following values on History screen
      | Application  |
      | Contact Info |
      | Consumer     |

  @CP-14250 @CP-14250-01 @ui-core @crm-regression @sang
  Scenario: Verify (New/Edit/Disregard/Reject) Enrollment link objects for consumer in History Tab
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "NEWBORN" with data
      | saveConsumerInfo | 15063-01 |
    And I create "NEW_ENROLLMENT_REJECT_RESPONSE" enrollment event for use in history tab
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I store "TASK ID" for use in verification
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I perform "ENROLL" event save on Program & Benefit Info Tab
    And I perform "ENROLL EDIT" event save on Program & Benefit Info Tab
    And I perform "ENROLL DISREGARD" event save on Program & Benefit Info Tab
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I complete active contact by filling out all minimal required fields
    When I navigate to case and consumer search page
    And I enter First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi" in case consumer search page
    And I click on the contact Id from the search result
    When I click on "History" Tab on Contact Dashboard Page
    And I verify "New Enrollment" Event "CONTACT RECORD" object displays the following list of labels and values
      | NAME OF CONTACT | CONTACT START | WRAP UP TIME WITH VIEW BUTTON |
    And I verify "Enrollment Edit" Event "CONTACT RECORD" object displays the following list of labels and values
      | NAME OF CONTACT | CONTACT START | WRAP UP TIME WITH VIEW BUTTON |
    And I verify "Disregarded New Enrollment" Event "CONTACT RECORD" object displays the following list of labels and values
      | NAME OF CONTACT | CONTACT START | WRAP UP TIME WITH VIEW BUTTON |
    And I verify "New Enrollment Reject Response" Event "TASK" object displays the following list of labels and values
      | TASK | STATUS | STATUS DATE | VIEW TASK BUTTON |

  @CP-14250 @CP-14250-02 @ui-core @crm-regression @sang
  Scenario: Verify (Plan Change/ Plan Change Edit/ Plan Disregard/ Plan Reject) link objects for consumer in History Tab
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" with data
      | saveConsumerInfo | QW1 |
    And I create "PLAN_CHANGE_REJECT_RESPONSE" enrollment event for use in history tab
    Given I logged into CRM and click on initiate contact
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I store "TASK ID" for use in verification
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I perform "PLAN CHANGE" event save on Program & Benefit Info Tab
    And I perform "PLAN EDIT" event save on Program & Benefit Info Tab
    And I perform "PLAN DISREGARD" event save on Program & Benefit Info Tab
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I complete active contact by filling out all minimal required fields
    When I navigate to case and consumer search page
    And I enter First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi" in case consumer search page
    And I click on the contact Id from the search result
    When I click on "History" Tab on Contact Dashboard Page
    And I verify "Plan Change" Event "CONTACT RECORD" object displays the following list of labels and values
      | NAME OF CONTACT | CONTACT START | WRAP UP TIME WITH VIEW BUTTON |
    And I verify "Plan Change Edit" Event "CONTACT RECORD" object displays the following list of labels and values
      | NAME OF CONTACT | CONTACT START | WRAP UP TIME WITH VIEW BUTTON |
    And I verify "Disregarded Plan Change" Event "CONTACT RECORD" object displays the following list of labels and values
      | NAME OF CONTACT | CONTACT START | WRAP UP TIME WITH VIEW BUTTON |
    And I verify "New Enrollment Reject Response" Event "TASK" object displays the following list of labels and values
      | TASK | STATUS | STATUS DATE | VIEW TASK BUTTON |
