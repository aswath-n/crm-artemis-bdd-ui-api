Feature: IN-EB Validate all potential match functionality

  @CP-33556 @CP-33556-02 @Beka @ui-cc-in @crm-regression
  Scenario: Verify potential matches header from create consumer page INEB
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I click on Case Consumer Search page
    And I search for consumer have First Name as "Umesh" and Last Name as "Kumar"
    And I click on Search Button
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I populate email and phone number and click on create consumer button INEB
    Then I see list of Potential Match consumers table with header
      | CONSUMER ID          |
      | EXTERNAL CONSUMER ID |
      | CASE ID              |
      | EXTERNAL CASE ID     |
      | ROLE                 |
      | FULL NAME            |
      | GENDER               |
      | DATE OF BIRTH        |
      | SSN                  |
      | STATUS               |

  @CP-30136 @CP-30136-04 @Beka @crm-regression @ui-cc-in
  Scenario: Apply Potential Match to Third Party C&C Search - Create Consumer for IN-EB
    Given I logged into CRM and select a project "IN-EB"
    And I click on initiate contact record
    And I click on third party contact record in active contact
    When I searched existing from third party search case where First Name as "Jon" and Last Name as "Nicholas"
    And I populate "12021923" and "997779900" for existing consumer
    And I click on Add Consumer button
    When I populate the rest fields and try to create consumer "INEB" to trigger potential match functionality
    Then  I will be presented with Consumer options that match the data I entered per

  @CP-30136 @CP-30136-05 @Beka @crm-regression @ui-cc-in
  Scenario: Permit Linking of Potential Match for  IN-EB
    Given I logged into CRM and select a project "IN-EB"
    And I click on initiate contact record
    And I click on third party contact record in active contact
    When I searched existing from third party search case where First Name as "Jon" and Last Name as "Nicholas"
    And I populate "12021923" and "997779900" for existing consumer
    And I click on Add Consumer button
    When I populate the rest fields and try to create consumer "INEB" to trigger potential match functionality
    Then I am able to link the selected matching Consumer to the Contact Record "INEB"

  @CP-30136 @CP-30136-06 @Beka @crm-regression @ui-cc-in
  Scenario: Verify ability to create consumer if not exact match consumer exist - Create Consumer for  IN-EB
    Given I logged into CRM and select a project "IN-EB"
    And I click on initiate contact record
    And I click on third party contact record in active contact
    When I searched existing from third party search case where First Name as "Jon" and Last Name as "Nicholas"
    And I populate "12021923" and "" for existing consumer
    And I click on Add Consumer button
    When I populate the rest fields and try to create consumer "INEB" to trigger potential match functionality
    Then I verify "CONTINUE AND ADD NEW CONSUMER" is enabled in Create Consumer Page


  @CP-30605 @CP-30605-01 @Beka @crm-regression @ui-cc-in
  Scenario: Apply Potential Match manual C&C Search - Create Consumer for IN-EB
    Given I logged into CRM and select a project "IN-EB"
    And I click case consumer search tab
    When I searched existing case where First Name as "Jon" and Last Name as "Nicholas"
    And I populate "12021923" and "997779900" for existing consumer
    And I minimize Genesys popup if populates
    And I click on Add Consumer button
    When I populate the rest fields and try to create consumer "INEB" to trigger potential match functionality
    Then  I will be presented with Consumer options that match the data I entered per

  @CP-30605 @CP-30605-02 @Beka @crm-regression @ui-cc-in
  Scenario: Permit Linking of Potential Match for  IN-EB from manual search create consumer
    Given I logged into CRM and select a project "IN-EB"
    And I click case consumer search tab
    When I searched existing case where First Name as "Jon" and Last Name as "Nicholas"
    And I populate "12021923" and "997779900" for existing consumer
    And I minimize Genesys popup if populates
    And I click on Add Consumer button
    When I populate the rest fields and try to create consumer "INEB" to trigger potential match functionality
    Then I am able to click on consumer id to expand Potential Match consumer

  @CP-30605 @CP-30605-03 @Beka @crm-regression @ui-cc-in
  Scenario: Verify ability to create consumer if not exact match consumer exist - from manual search create consumer  IN-EB
    Given I logged into CRM and select a project "IN-EB"
    And I click case consumer search tab
    When I searched existing case where First Name as "Jon" and Last Name as "Nicholas"
    And I populate "12021923" and "" for existing consumer
    And I minimize Genesys popup if populates
    And I click on Add Consumer button
    When I populate the rest fields and try to create consumer "INEB" to trigger potential match functionality
    Then I verify "CONTINUE AND ADD NEW CONSUMER" is enabled in Create Consumer Page
