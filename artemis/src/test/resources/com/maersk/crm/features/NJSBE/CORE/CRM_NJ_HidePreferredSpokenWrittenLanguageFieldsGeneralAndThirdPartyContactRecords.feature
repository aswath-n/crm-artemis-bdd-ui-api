Feature: NJ: Hide Preferred Language Fields on General & Third Party Contact Records

  @CP-11948 @CP-11948-01 @umid @ui-core-nj @nj-regression
  Scenario: Verify preferred language is not displayed on Active/Saved General Contact - Consumer in Contact
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I make sure preferred language is not displayed
    When  I should see following dropdown options for "contact reason" field displayed
      | Appeal |
    And  I choose "Unable to Resolve Issue" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When I enter contact phone number "1327894561"
    And I select contact program type as "Other"
    Then I click on the contact dispotions "Complete"
    When I click End Contact
    And I click save button Active contact
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the latest contact record with type "General"
    And I make sure preferred language is not displayed

  @CP-11948 @CP-11948-02 @umid @ui-core-nj @nj-regression
  Scenario: Verify preferred language is not displayed on Active/Saved Third Patry Contact - Consumer in Contact
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I click on primary individual record in search result
    And I click validate and link button
    And I make sure preferred language is not displayed
    When  I should see following dropdown options for "contact reason" field displayed
      | Appeal |
    And  I choose "Unable to Resolve Issue" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When I enter contact phone number "1327894561"
    And I select contact program type as "Other"
    Then I click on the contact dispotions "Complete"
    And I Enter mandatory Third Party Details "asdiaf", "asdoihaf", "sdufhsidf", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I click End Contact
    And I click save button Active contact
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the latest contact record with type "Third Party"
    And I make sure preferred language is not displayed

  @CP-14023 @CP-14023-01 @ui-core-nj @nj-regression @crm-regression @aikanysh
  Scenario: NJ: Hide Spoken/Written Language fields on General & Third Party Contact Records: Active Contact: General
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I make sure spoken/written language is not displayed

  @CP-14023 @CP-14023-02 @ui-core-nj @nj-regression @crm-regression @aikanysh
  Scenario: NJ: Hide Spoken/Written Language fields on General & Third Party Contact Records: Active Contact: Third Party
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I click on primary individual record in search result
    And I click validate and link button
    And I make sure spoken/written language is not displayed

  @CP-14023 @CP-14023-03 @ui-core-nj @nj-regression @crm-regression @aikanysh
  Scenario: NJ: Hide Spoken/Written Language fields on General & Third Party Contact Records: Active Contact: General-Saved
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    And I make sure spoken/written language is not displayed
    When  I should see following dropdown options for "contact reason" field displayed
      | Appeal |
    And  I choose "Unable to Resolve Issue" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When I enter contact phone number "1327894561"
    And I select contact program type as "Other"
    Then I click on the contact dispotions "Complete"
    When I click End Contact
    And I click save button Active contact
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the latest contact record with type "General"
    And I make sure spoken/written language is not displayed

  @CP-14023 @CP-14023-04 @ui-core-nj @nj-regression @crm-regression @aikanysh
  Scenario: NJ: Hide Spoken/Written Language fields on General & Third Party Contact Records: Active Contact: Third Party-Saved
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I click on primary individual record in search result
    And I click validate and link button
    And I make sure spoken/written language is not displayed
    When  I should see following dropdown options for "contact reason" field displayed
      | Appeal |
    And  I choose "Unable to Resolve Issue" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When I enter contact phone number "1327894561"
    And I select contact program type as "Other"
    Then I click on the contact dispotions "Complete"
    And I Enter mandatory Third Party Details "asdiaf", "asdoihaf", "sdufhsidf", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I click End Contact
    And I click save button Active contact
    When I click on initiate contact record
    When I searched customer have First Name as "Tom" and Last Name as "Wills"
    And I link the contact to an existing Case or Consumer Profile in NJ-SBE
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the latest contact record with type "Third Party"
    And I make sure spoken/written language is not displayed



