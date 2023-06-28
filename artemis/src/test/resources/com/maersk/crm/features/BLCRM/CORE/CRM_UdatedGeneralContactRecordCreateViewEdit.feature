Feature:  Update General Contact Record Design - Create/View/Edit

  @CP-342 @CP-342-01 @muhabbat @crm-regression @ui-core
  Scenario: General Consumer in Contact header validation
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by "FirstName" and value "Harry"
    And I search for an existing contact by "LastName" and value "Grant"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    When I should see Linked Contact in the Header
    Then I see General Consumer in Contact header is displayed

  @CP-342 @CP-342-02 @muhabbat @crm-regression @ui-core
  Scenario: Verification Associations/Links section is displayed as section og General Contact Record
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by "FirstName" and value "Harry"
    And I search for an existing contact by "LastName" and value "Grant"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    Then I see Associations/Links section is displayed

  @CP-342 @CP-342-03 @muhabbat @crm-regression @ui-core
  Scenario: Verification Linked Case is added to the Link section
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by "FirstName" and value "harry"
    And I search for an existing contact by "LastName" and value "grant"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    Then I see "harry" consumer information is added to the Link section

  @CP-342 @CP-342-04 @CP-344-07 @muhabbat @crm-regression @ui-core
  Scenario Outline: Validation General, Third Party, Unidentified Radio Buttons.
    Given I logged into CRM and click on initiate contact
    Then I verify Contact Type radio buttons "<type>" is displayed
    Examples:
      | type                  |
      | GENERAL CONTACT       |
      | THIRD PARTY CONTACT   |
      | UNIDENTIFIED CONTACT  |