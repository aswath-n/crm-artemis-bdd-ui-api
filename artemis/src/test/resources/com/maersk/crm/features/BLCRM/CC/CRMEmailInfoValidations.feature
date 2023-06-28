Feature: Email details validations

  @CP-15742 @CP-15742-01 @crm-regression @ui-cc @jp @ui-cc-smoke
  Scenario: Verify updated Consumer Demographic Info on Case Summary - View consumer and primary case email check box in Add Email page
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    And I verify consumer field in the Add email page
    And I verify primary case email check box is available and disabled

  @CP-15742 @CP-15742-02 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - View consumer and primary case email enabled check box in Add Email page
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    Then I verify primary case email check box is enabled by entering test data
      | startDate | endDate |
      | P2        |[blank]|

  @CP-15742 @CP-15742-02.1 @CP-15742-02.2 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - check consumer primary case email to view error message
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    And I add new Email with Active, Future and Inactive status with primary case checked
      | startDate | endDate |
      | P2        |[blank]|
    And I click on the Add button for Email Address
    And I add new Email with Active, Future and Inactive status with primary case checked
      | startDate | endDate |
      | P2        |[blank]|

  @CP-15742 @CP-15742-02.3 @CP-15742-03.0 @crm-regression @ui-cc @jp @events-cc
  Scenario Outline: Verify updated Consumer Demographic Info on Case Summary - check consumer primary case email EMAIL_UPDATE_EVENT
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    And I add new Email with Active, Future and Inactive status with primary case checked
      | startDate | endDate |
      | P2        |[blank]|
    And I will capture required trace ID out of multiple matching Event Type "<eventType>" and Event Name "<eventName>"
    When I click on the Add button for Email Address
    And I add new Email with Active, Future and Inactive status with primary case checked
      | startDate | endDate |
      | P2        |[blank]|
    And I will capture required trace ID out of multiple matching Event Type "<eventType>" and Event Name "<eventName>"
    Then I will verify "email" save and update events for the captured trace IDs
    Examples:
      | eventName        | eventType |
      | EMAIL_SAVE_EVENT | contact   |

  @CP-15743 @CP-15743-01 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - Update Contact Info tab fields - Email
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    Then I verify newly added Email contact info fields
      | Fields       |
      | CONSUMER     |
      | LAST UPDATED |

  @CP-15743 @CP-15743-01.1 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - Primary Email Indicator and Sort Order
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    And I add new Email with Active, Future and Inactive status with primary case checked
      | startDate | endDate |
      | P2        |[blank]|
      | P15       | P10     |
      | F2        | F20     |
    Then I verify primary email should be first one with primary indicator
    And I verify Email addresses are displayed in sorted order
      | sorted_order           |
      | ACTIVE,FUTURE,INACTIVE |

  @CP-15743 @CP-15743-02 @crm-regression @ui-cc @jp
  Scenario: Verify updated Consumer Demographic Info on Case Summary - Primary Email Indicator mouse hover text
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    And I add new Email with Active, Future and Inactive status with primary case checked
      | startDate | endDate |
      | P2        |[blank]|
      | P15       | P10     |
      | F2        | F20     |
    Then I verify primary email alt text should have Primary case email address on mouse hover
      | Indicator_text             |
      | Primary Case Email Address |

  @CP-15742 @CP-15742-3.0 @crm-regression @events @chopa @events-cc
  Scenario Outline: Verify the data is saved and the Email’s owner is saved as the Consumer Id selected And the EMAIL_SAVE_EVENT is generated
    Given I logged into CRM and click on initiate contact
    When I search for an existing Case ID "30174"
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    And I add new Email with Active, Future and Inactive status with primary case checked
      | startDate | endDate |
      | P2        |[blank]|
    And I select first consumer with email
    And I select another consumer from dropdown in edit email page
    And I will capture required externalRefId and externalRefType out of Event Type "<eventType>" and Event Name "<eventName>"
    Examples:
      | eventName        | eventType |
      | EMAIL_SAVE_EVENT | contact   |

  @CP-10904 @CP-10904-01 @crm-regression @ui-cc @Beka
  Scenario: Primary Case Email checkbox - Edit Email and verify Primary Case Email checkbox is disabled
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I select first consumer with email
    Then Verify primary email checkbox "enable" when following field true
      | phoneTypeHome    | Home    |
      | phoneTypeCell    | Cell    |
      | phoneTypeWork    | Work    |
      | StartDatePresent | Present |
      | StartDatePast    | Past    |
      | EndDateNull      | null    |

  @CP-10904 @CP-10904-02 @crm-regression @ui-cc @Beka
  Scenario: Verification of Warning when Primary Indicator is already populated for this Case for a email
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    When I populate all field and check the primary email checkbox
    Then I verify warning message "Are you sure you want to update the Primary Email for this Case? This action will update the existing Primary Email for the Case to no longer be the Primary"

  @CP-10904 @CP-10904-03 @crm-regression @ui-cc @Beka
  Scenario: Verification of existing email has no Primary Indicator
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add button for Email Address
    When I populate all field and check the primary email checkbox
    Then I verify warning message "Are you sure you want to update the Primary Email for this Case? This action will update the existing Primary Email for the Case to no longer be the Primary"
    Then I see existing "email" has no Primary Indicator

  @CP-10904 @CP-10904-04 @crm-regression @ui-cc @Beka
  Scenario: Edit Email - Consumer dropdown and verify Email's owner is saved as the Consumer Id selected
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on add button Case Member
    And I create case member with end date "" and DoD "present"
    And I click on the Contact Info Tab
    And I select first consumer with email
    And change consumer dropdown value
    Then I see "email" owner is saved as the Consumer Id selected

  @CP-31570 @CP-31570-01 @crm-regression @ui-cc @Beka
  Scenario: Verify Email Source - Consumer Profile
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "TestConsume"
    And I click on first consumerID of consumer profile on manual search result
    And I click on the Demographic Info Tab
    And I click on the Add button for Email Address
    Then I verify email source dropdown is displayed and has only one value Consumer Reported

  @CP-31570 @CP-31570-02 @crm-regression @ui-cc @Beka
  Scenario: Verify Email Source edit Email page - Consumer Profile
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "TestConsume"
    And I click on first consumerID of consumer profile on manual search result
    And I click on the Demographic Info Tab
    When I select the first Email listed
    Then I verify email source dropdown is displayed and has only one value Consumer Reported

  @CP-31570 @CP-31570-03 @crm-regression @ui-cc @Beka
  Scenario: Verify Email Source - Case Summary
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "Goldber"
    And I click first CRM Case ID in search results on case and consumer search page
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I click on the Add button for Email Address
    Then I verify email source dropdown is displayed and has only one value Consumer Reported

  @CP-31570 @CP-31570-04 @crm-regression @ui-cc @Beka
  Scenario: Verify Email Source edit Email pag - Case Summary
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "Goldber"
    And I click first CRM Case ID in search results on case and consumer search page
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    When I select the first Email listed
    Then I verify email source dropdown is displayed and has only one value Consumer Reported

  @CP-31570 @CP-31570-05 @crm-regression @ui-cc @Beka
  Scenario: Verify Email Source on Create Consumer page
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "Goldber"
    And I click on Add Consumer button
    Then I verify email source dropdown is displayed and has only one value Consumer Reported

  @CP-31570 @CP-31570-06 @crm-regression @ui-cc @Beka
  Scenario: Verify Email Source column in the Email component between Email Address and Status - Case Summary
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "Goldber"
    And I click first CRM Case ID in search results on case and consumer search page
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    Then I verify newly added Email contact info fields
      | Fields       |
      | EMAIL SOURCE |

  @CP-31570 @CP-31570-07 @crm-regression @ui-cc @Beka
  Scenario: Verify Email Source column in the Email component between Email Address and Status - Consumer Profile
    Given I logged into CRM
    And I navigate to Case Consumer search
    When I search for record by value "TestConsume"
    And I click on first consumerID of consumer profile on manual search result
    And I click on the Demographic Info Tab
    Then I verify newly added Email contact info fields
      | Fields       |
      | EMAIL SOURCE |
