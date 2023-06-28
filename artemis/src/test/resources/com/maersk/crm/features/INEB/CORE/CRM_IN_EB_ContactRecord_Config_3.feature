Feature: IN-EB Contact Record Configurations Part Three

  @CP-25408 @CP-25408-01 @CP-26087 @CP-26087-01 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @khazar
  Scenario: Verification of Call Summary Field Label displayed for Active Contact Record
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify Call Summary field label is displayed

  @CP-25408 @CP-25408-02 @CP-26087 @CP-26087-02 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @khazar
  Scenario: Verification of Call Summary Field Label displayed for View/Edit Contact Record
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "2242"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I verify Call Summary field label is displayed

  @CP-25408 @CP-25408-03 @CP-26087 @CP-26087-03 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @khazar
  Scenario: Verification of Call Summary Field Input Requirements for Active Contact Record
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I click on save Call Summary
    And I verify mandatory fields error message "CALL SUMMARY REQUIRED" in active contact page
    Then I verify Call Summary field input requirements

  @CP-25408 @CP-25408-04 @CP-26087 @CP-26087-04 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @khazar
  Scenario: Verification of Call Summary Field Input Requirements for View/Edit Contact Record
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "2242"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I verify Call Summary field input requirements

  @CP-25408 @CP-25408-05 @CP-26087 @CP-26087-05 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @khazar
  Scenario: Verification of Call Summary is Stackable in Active Contact Record
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I fill Call Summary field with "First call summary" and save
    Then I verify Call Summary 1 saved as "First call summary" for "active contact"
    And I fill Call Summary field with "Second call summary" and save
    Then I verify Call Summary 2 saved as "Second call summary" for "active contact"
    And I fill Call Summary field with "Third call summary" and save
    Then I verify Call Summary 3 saved as "Third call summary" for "active contact"
    Then I verify 3 Call Summaries were stacked

  @CP-25408 @CP-25408-06 @CP-26087 @CP-26087-06 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @khazar
  Scenario: Verification of Call Summary is Stackable in Edit Contact Record
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "2242"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I fill Call Summary field with "First call summary" and save
    Then I verify Call Summary 1 saved as "First call summary" for "Edit"
    And I fill Call Summary field with "Second call summary" and save
    Then I verify Call Summary 2 saved as "Second call summary" for "Edit"
    And I fill Call Summary field with "Third call summary" and save
    Then I verify Call Summary 3 saved as "Third call summary" for "Edit"
    Then I verify 3 Call Summaries were stacked

  @CP-25408 @CP-25408-07 @CP-26087 @CP-26087-07 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @khazar
  Scenario: Verification of Call Summary Field Is Required for Saving Active Contact Record
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched customer have First Name as "Khazar" and Last Name as "Mahmud"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Inbound Call queue "UAT_KIDS"
    And I enter contact phone number "3332223311"
    Then I select "HIP" as contact program type
    Then I click on the contact dispotions "Complete"
    When I click End Contact
    And I select "Inquiry Covered Benefits" from contact reason with "Referral - Health Plan" Contact Action for ineb and save
    And I click save button Active contact
    Then I verify "Please provide a Call Summary" error message

  @CP-25408 @CP-25408-08 @CP-26087 @CP-26087-08 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @khazar
  Scenario: Save Contact Record if at least 1 Call Summary Exists
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched customer have First Name as "Khazar" and Last Name as "Mahmud"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Inbound Call queue "UAT_KIDS"
    And I enter contact phone number "3332223311"
    Then I select "HIP" as contact program type
    Then I click on the contact dispotions "Complete"
    When I click End Contact
    And I select "Inquiry Covered Benefits" from contact reason with "Referral - Health Plan" Contact Action for ineb and save
    And I fill Call Summary field with "First call summary" and save
    And I click save button Active contact
    Then I should return to dashboard page

  @CP-29207 @CP-29207-01 @khazar @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Verifying Action Options Dependent On Program Selection
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I select "HHW" as contact program type
    And I select "Inquiry Covered Benefits" from contact reason with "Referral - Health Plan" Contact Action for ineb and save
    Then I select "HHW" as contact program type
    Then I verify action&reason dependence from program error message
    Then I delete above reason&action by clicking trash icon and clicking Continue
    Then I select "HIP" as contact program type
    Then I verify "HHW" and "HIP" are selected as Program

  @CP-30371 @CP-30371-01 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario:  Hide Reason/Action Comments Field -Active - View Contact - Edit Contact- Inbound Contact Type-General Contact1
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify that comment is not visible on active contact
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case with consumer id for IN-EB
    When I save the contact record for IN_EB with random order for "Inbound" and "UAT_KIDS"
    When I navigate to manual contact record search page
    And I search contact record with phone number and click on the first record
    Then I verify that comment is not visible under reason on view contact page
    And I verify that comment is not visible on edit contact page

  @CP-30371 @CP-30371-02 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario:  Hide Reason/Action Comments Field - Active - View Contact - Edit Contact- Outbound Contact Type-General Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify that comment is not visible on active contact
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case with consumer id for IN-EB
    When I save the contact record for IN_EB with random order for "Outbound" and "Outbound Calls - English"
    When I navigate to manual contact record search page
    And I search contact record with phone number and click on the first record
    Then I verify that comment is not visible under reason on view contact page
    And I verify that comment is not visible on edit contact page

  @CP-30371 @CP-30371-03 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario:  Hide Reason/Action Comments Field - Active - View Contact - Edit Contact-Inbound Contact Type-ThirdParty Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I click on third party contact record in active contact
    Then I verify that comment is not visible on active contact
    When I searched existing from third party search case where First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case with consumer id for IN-EB
    And I save the contact record third party IN_EB with random order for "Inbound" and "UAT_KIDS"
    When I navigate to manual contact record search page
    And I search contact record with phone number and click on the first record
    Then I verify that comment is not visible under reason on view contact page
    And I verify that comment is not visible on edit contact page

  @CP-30371 @CP-30371-04 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario:  Hide Reason/Action Comments Field - Active - View Contact - Edit Contact-Outbound Contact Type-ThirdParty Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I clicked on Third Party Contact
    Then I verify that comment is not visible on active contact
    When I searched existing from third party search case where First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case with consumer id for IN-EB
    And I save the contact record third party IN_EB with random order for "Outbound" and "Outbound Calls - English"
    When I navigate to manual contact record search page
    And I search contact record with phone number and click on the first record
    Then I verify that comment is not visible under reason on view contact page
    And I verify that comment is not visible on edit contact page

  @CP-30371 @CP-30371-05 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario:  Hide Reason/Action Comments Field - Active - View Contact - Edit Contact - Inbound Contact Type - Unidentified Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I navigate to unidentified page
    Then I verify that comment is not visible on active contact
    And I create new unidentified contact record for "Inbound" and "UAT_KIDS"
    When I navigate to manual contact record search page
    And I search contact record with phone number and click on the first record
    Then I verify that comment is not visible under reason on view contact page
    And I verify that comment is not visible on edit contact page

  @CP-30371 @CP-30371-06 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario:  Hide Reason/Action Comments Field - Active - View Contact - Edit Contact - Outbound Contact Type - Unidentified Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I navigate to unidentified page
    Then I verify that comment is not visible on active contact
    And I create new unidentified contact record for "Outbound" and "Outbound Calls - English"
    When I navigate to manual contact record search page
    And I search contact record with phone number and click on the first record
    Then I verify that comment is not visible under reason on view contact page
    And I verify that comment is not visible on edit contact page

  @CP-33435 @CP-33435-01 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: INEB: Configure Default Program Value Based on Inbound Call Queue Selection
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    When I click on initiate contact record
    And I select "UAT_CARE_ENG_Q" option from inbound call queue
    And I verify "HCC" Program is selected
    Then I verify I am able to unselect "HCC" program

  @CP-35360 @CP-35360-01 @sang @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Verify HCC and HWW programs contact reason Inquiry Covered Benefits has the associated Education-Health Plan and other programs do not
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select "HCC" program types
    And I verify Contact Reason for IN-EB "Inquiry Covered Benefits - Education-Health Plan" and associated Contact Action
    And I select "HHW" program types
    And I verify Contact Reason for IN-EB "Inquiry Covered Benefits - Education-Health Plan" and associated Contact Action
    And I select "HIP" program types
    And I verify Contact Reason for IN-EB "Inquiry Covered Benefits - NO Education-Health Plan" and associated Contact Action
    And I select "Traditional Medicaid" program types
    And I verify Contact Reason for IN-EB "Inquiry Covered Benefits - NO Education-Health Plan" and associated Contact Action

  @CP-35359 @CP-35359-01 @sang @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB: Verify Transfer Contact Reason and associated Contact Action
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select "HCC" program types
    And I verify Contact Reason for IN-EB "Transfer" and associated Contact Action
    And I select "HHW" program types
    And I verify Contact Reason for IN-EB "Transfer" and associated Contact Action
    And I select "HIP" program types
    And I verify Contact Reason for IN-EB "Transfer" and associated Contact Action
    And I select "Traditional Medicaid" program types
    And I verify Contact Reason for IN-EB "Transfer" and associated Contact Action