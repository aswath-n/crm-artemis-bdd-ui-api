Feature: Capturing Consumer For Contact Record

  @CRM-573  @CRM-573-01 @muhabbat @crm-regression @ui-core
  Scenario: Selecting the individual Consumer from Contact Record Search Result
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FirstAuthentic" and Last Name as "LastAuthentic"
    And I expend the Case Consumer this contact relates to in search result
    Then I can select the individual "Primary Individual" from the search that is making a contact

  @CRM-573 @CRM-573-02 @CRM-833 @CRM-833-02 @muhabbat @crm-regression @ui-core
  Scenario: Linking the individual Consumer from Contact Record Search Result
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FirstAuthentic" and Last Name as "LastAuthentic"
    And I link the contact to an existing Case or Consumer Profile

  @CRM-573 @CRM-573-03 @muhabbat @crm-regression @ui-core
  Scenario: Populating Consumer Details when individual Consumer from Contact Record Search Result is linked
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FirstAuthentic" and Last Name as "LastAuthentic"
    And I link the contact to an existing Case or Consumer Profile
    Then I see Consumer Name, Type ID, SSN, DOB and Preferred Language systematically populated

  @CRM-573 @CRM-573-04 @muhabbat @crm-regression @ui-core
  Scenario: Validating change of Consumer Details after new individual Consumer is linked from Contact Record Search Result
    Given I logged into CRM and click on initiate contact
    When I should see following dropdown options for "contact reason" field displayed
      | Update Information Request |
    And  I choose "Updated Eligibility Information" option for Contact Action field
    When I searched customer have First Name as "FirstAuthentic" and Last Name as "LastAuthentic"
    And I link the contact to an existing Case or Consumer Profile
    When I unlink previously selected Consumer from Active Contact Record
    When I searched customer have First Name as "araz" and Last Name as "isma"
    And I link the contact to an existing Case or Consumer Profile
    Then I see Consumer Name, Type ID, SSN, DOB and Preferred Language systematically populated