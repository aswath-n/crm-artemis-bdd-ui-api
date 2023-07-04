Feature: IN-EB Contact Record Configurations Part Two

  @CP-34034 @CP-24991 @CP-24991-01 @CP-23482 @CP-23482-06 @aikanysh @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario Outline: IN-EB: Configure Contact Record Reason&Action
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select All program types
    And I verify Contact Reason for IN-EB "<ContactReasons>" and associated Contact Action
    Examples:
      | ContactReasons                                 |
      | Complaint                                      |
      | Enrollment                                     |
      | HCC Outbound                                   |
      | HIP 2.0                                        |
      | Inquiry AE/OE                                  |
      | Inquiry Application/Eligibility                |
      | Inquiry Billing                                |
      | Inquiry Covered Benefits                       |
      | Inquiry Disenrollment                          |
      | Inquiry Guardianship/Authorized Representative |
      | Inquiry General Information                    |
      | Inquiry Health Plan Contact Information        |
      | Inquiry PMP                                    |
      | Inquiry Replacement Card                       |
      | Just Cause                                     |
      | Member Information Update                      |
      | Other                                          |
      | Right Choices Program                          |

  @CP-30370 @CP-30370-01 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB:  Hide Reason/Action Comments Field - Active Contact- Inbound Contact Records
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify that comments field is invisible below the Actions
    When  I select "THIRD PARTY CONTACT" Contact Record Type
    Then I verify that comments field is invisible below the Actions
    When  I select "UNIDENTIFIED CONTACT" Contact Record Type
    Then I verify that comments field is invisible below the Actions


  @CP-30370 @CP-30370-02 @araz @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: IN-EB:  Hide Reason/Action Comments Field - Active Contact- Outbound Contact Records
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I click on the Contact Type "Outbound"
    Then I verify that comments field is invisible below the Actions
    When  I select "THIRD PARTY CONTACT" Contact Record Type
    Then I verify that comments field is invisible below the Actions
    When  I select "UNIDENTIFIED CONTACT" Contact Record Type
    Then I verify that comments field is invisible below the Actions

  @CP-25411 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @kamil
  Scenario: IN-EB: Configure Contact Record not display Claim ID from Contact Record
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched customer have First Name as "FnVbSnA" and Last Name as "LnfUvIU"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    Then Verify ClaimId not visible on Active Contact
    When I save the contact with Reason "Complaint" and Action "State"
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    And I verify there is NO field named "Claim ID"