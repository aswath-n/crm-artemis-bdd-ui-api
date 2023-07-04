Feature: CoverVA Contact Record Configurations Part 11

  @CP-36211 @CP-36211-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: Add Facility Other as Facility Name Option to Conditionally Display Facility Type as a Text Field- free text entry
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact recordNew
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then  I save the Contact Record with facility type as facility other

  @CP-36211 @CP-36211-04 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario:  Systematically Set Facility Type when Facility Name Selected
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact recordNew
    Then I navigate to business unit "CVIU - DJJ"
    And Verify the facility name with associated facility type as text free field
      | Facility-Other |

  @CP-36211 @CP-36211-05 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: Add Facility Other as Facility Name Option to Conditionally Display Facility Type as a Text Field- active and edit page-SaveEvent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact recordNew
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then  I save the Contact Record with facility type as facility other
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page new
    Then I update the facility type
    And I select reason for edit "Correcting Contact Details" drop down and provide details to edit for facility Name
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I will take trace Id for Contact Record update Event "CONTACT_RECORD_SAVE_EVENT" and number 3
    When I will initiate Event GET API
    And I run the Event GET API and get the payload for eventName "CONTACT_RECORD_UPDATE_EVENT" and verify the payload
    Then I verify that payload contains facilityNAME "Facility-Other" and FacilityType "Testing"

  @CP-36211 @CP-36211-06 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: Add Facility Other as Facility Name Option to Conditionally Display Facility Type as a Text Field- active and edit page-UpdateEvent
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact recordNew
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then  I save the Contact Record with facility type as facility other
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page new
    Then I update the facility type
    And I select reason for edit "Correcting Contact Details" drop down and provide details to edit for facility Name
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I will take trace Id for Contact Record update Event "CONTACT_RECORD_SAVE_EVENT" and number 5
    When I will initiate Event GET API
    And I run the Event GET API and get the payload for eventName "CONTACT_RECORD_UPDATE_EVENT" and verify the payload
    Then I verify that payload contains facilityNAME "Facility-Other" and FacilityType "Demo"

  @CP-26947 @CP-26947-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA: Display Cancelled Contact Record Reason-Active Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select the disposition field "Cancelled"
    Then I will observe the value "Cancelled Contact Record" in the reason droplist for each business Unit value
      | CVCC         |
      | CVIU - DJJ   |
      | CVIU - DOC   |
      | CVIU - Jails |
      | CVIU - Other |

  @CP-26947 @CP-26947-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA:  Display Associated Action Options for “Cancelled Contact Record” Reason-Active Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select the disposition field "Cancelled"
    Then I will observe the value "Cancelled Contact Record" in the reason droplist for each business Unit value
      | CVCC         |
      | CVIU - DJJ   |
      | CVIU - DOC   |
      | CVIU - Jails |
      | CVIU - Other |
    And I will verify associated action options
      | Accidental Contact Record |
      | Invalid Phone Number      |
      | System Error              |

  @CP-26947 @CP-26947-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA: Require Cancelled Contact Record Reason-Validation Error- Active Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I fill out the active contact page and save it disposition "Cancelled" busUnit "CVCC" contactReason "Complaint" contactAction "Resolved"
    And I verify Error message is displayed without cancelled related contact reason

  @CP-26947 @CP-26947-04 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA: Require Cancelled Contact Record Reason- Active Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I fill the disposition "Cancelled" busUnit "CVCC" contactReason "Cancelled Contact Record" contactAction "System Error"
    And I verify that disposition field is disabled

  @CP-26947 @CP-26947-05 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA: Edit "Business Unit" Field - Edit Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the contact without Application ID with BusinessUnit Drop Down "CVCC"
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page
    Then I select the disposition field "Cancelled"
    And I select the associated reason for edit field "Correcting Disposition" and save it
    And I verify Error message is displayed without cancelled related contact reason

  @CP-26947 @CP-26947-06 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA: Display Cancelled Contact Record Reason-Edit Page Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the contact without Application ID with BusinessUnit Drop Down "CVCC"
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page
    And I select the disposition field "Cancelled"
    Then I am able to delete above reason&action by clicking trash icon and clicking Continue
    Then I will observe the value "Cancelled Contact Record" in the reason droplist for each business Unit value
      | CVCC         |
      | CVIU - DJJ   |
      | CVIU - DOC   |
      | CVIU - Jails |
      | CVIU - Other |

  @CP-26947 @CP-26947-07 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: VA: Display Associated Action Options for “Cancelled Contact Record” Reason-Edit Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the contact without Application ID with BusinessUnit Drop Down "CVCC"
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page
    And I select the disposition field "Cancelled"
    Then I am able to delete above reason&action by clicking trash icon and clicking Continue
    Then I will observe the value "Cancelled Contact Record" in the reason droplist for each business Unit value
      | CVCC         |
      | CVIU - DJJ   |
      | CVIU - DOC   |
      | CVIU - Jails |
      | CVIU - Other |
    And I will verify associated action options
      | Accidental Contact Record |
      | Invalid Phone Number      |
      | System Error              |
