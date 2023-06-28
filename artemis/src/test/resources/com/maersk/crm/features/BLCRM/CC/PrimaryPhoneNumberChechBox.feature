Feature: Update Consumer Demographic Info on Case Summary - Edit Phone Details

  @CP-11087 @CP-11087-01 @Beka @crm-regression @ui-cc
  Scenario: Verification of Primary Case Phone checkbox
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I click on existing phone number
    Then Verify primary number checkbox "enable" when following field true
      | phoneTypeHome    | Home    |
      | phoneTypeCell    | Cell    |
      | phoneTypeWork    | Work    |
      | StartDatePresent | Present |
      | StartDatePast    | Past    |
      | EndDateNull      | null    |

  @CP-11087 @CP-11087-02 @Beka @crm-regression @ui-cc
  Scenario: Verification of Warning when Primary Indicator is already populated for this Case for a Phone Number
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I click on the Add Phone Number button on Contact Info Page
    When I add new phone number and make it primary to capture warning message
    Then I verify warning message "Are you sure you want to update the Primary Phone for this Case? This action will update the existing Primary Phone for the Case to no longer be the Primary"

  @CP-11087 @CP-11087-03 @Beka @crm-regression @ui-cc
  Scenario: Verification of Select Continue to Save existing Case level Primary Indicator is removed from the existing Phone Number
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I click on the Add Phone Number button on Contact Info Page
    When I add new phone number and make it primary to capture warning message
    Then I verify warning message "Are you sure you want to update the Primary Phone for this Case? This action will update the existing Primary Phone for the Case to no longer be the Primary"
    Then I see existing "number" has no Primary Indicator

  @CP-11087 @CP-11087-04 @Beka @crm-regression @ui-cc
  Scenario:  Edit Phone Number - Consumer dropdown and verify phone Number’s owner is saved as the Consumer Id selected
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on add button Case Member
    And I create case member with end date "" and DoD "present"
    And I click on the Contact Info Tab
    And I click on existing phone number
    And change consumer dropdown value
    Then I see "phone Number’s" owner is saved as the Consumer Id selected

  @CP-30252 @CP-30252-01 @chopa @crm-regression @ui-cc-in
  Scenario: IN-EB: Verify Phone source is displayed for Consumer on case Address as "Consumer Reported" on Add Phone Page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Emma" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add Phone Number button on Contact Info Page
    Then I see Phone Source field label
    Then I verify Phone Source is "Consumer Reported"

  @CP-30252 @CP-30252-02 @chopa @crm-regression @ui-cc-in
  Scenario: IN-EB: Verify Phone source is displayed for Consumer on case Address as "Consumer Reported" on Edit Phone Page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "emma" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on existing phone number
    Then I see Phone Source field label
    Then I verify Phone Source is "Consumer Reported"

  @CP-30252 @CP-30252-03 @chopa @crm-regression @ui-cc-in
  Scenario: IN-EB: Verify Phone source is displayed for Consumers Address as "Consumer Reported" on Add Phone Page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I see Phone Source field label
    Then I verify Phone Source is "Consumer Reported"

  @CP-30252 @CP-30252-04 @chopa @crm-regression @ui-cc-in
  Scenario: IN-EB: Verify Phone source is displayed for Consumers Address as "Consumer Reported" on Edit Phone Page
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Austin" and Last Name as "Belly"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on existing phone number
    Then I see Phone Source field label
    Then I verify Phone Source is "Consumer Reported"

  @CP-30252 @CP-30252-05 @chopa @crm-regression @ui-cc
  Scenario: BLCRM: Verify Phone source is displayed for Consumer on case Address as "Consumer Reported" on Add Phone Page
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "John" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on the Add Phone Number button on Contact Info Page
    Then I see Phone Source field label
    Then I verify Phone Source is "Consumer Reported"

  @CP-30252 @CP-30252-06 @chopa @crm-regression @ui-cc
  Scenario: BLCRM: Verify Phone source is displayed for Consumer on case Address as "Consumer Reported" on Edit Phone Page
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "John" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    And I click on existing phone number
    Then I see Phone Source field label
    Then I verify Phone Source is "Consumer Reported"

  @CP-30252 @CP-30252-07 @chopa @crm-regression @ui-cc
  Scenario: BLCRM: Verify Phone source is displayed for Consumers Address as "Consumer Reported" on Add Phone Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I see Phone Source field label
    Then I verify Phone Source is "Consumer Reported"

  @CP-30252 @CP-30252-08 @chopa @crm-regression @ui-cc
  Scenario: BLCRM: Verify Phone source is displayed for Consumers Address as "Consumer Reported" on Edit Phone Page
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lomoro" and Last Name as "Dash"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on existing phone number
    Then I see Phone Source field label
    Then I verify Phone Source is "Consumer Reported"

  @CP-30253 @CP-30253-01 @chopa @crm-regression @ui-cc
  Scenario: BLCRM: Verify Phone source "State Reported" is not editable in Profile Details
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Pav-kishore" and Last Name as "Kishore"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    Then I verify Phone Source is State Reported
    And I very Phone number is not editable

  @CP-30253 @CP-30253-02 @chopa @crm-regression @ui-cc
  Scenario: BLCRM: Verify Phone source "State Reported" is not editable in Contact Info
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Zeinnn" and Last Name as "Rickkk"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    Then I verify Phone Source is State Reported
    And I very Phone number is not editable

  @CP-30253 @CP-30253-03 @chopa @crm-regression @ui-cc
  Scenario: BLCRM: Verify Phone source "State Reported" is end dated for new phone with the same type in Contact Info
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Zeinnn" and Last Name as "Rickkk"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I navigate to Contact Info Tab
    Then I verify status is "INACTIVE" for State Reported Phone
    And I verify status is "ACTIVE" for Consumer Reported Phone with the same type