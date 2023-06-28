Feature: Edit Contact Record History: Part 4

  @CP-8290 @CP-8290-1 @CP-23391 @ui-core @crm-regression @umid @ui-core-smoke
  Scenario: Verify Reason for Edit dropdown BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I am able to search using the following basic search parameters for Contact Record
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I verify Reason for Edit dropdown populated with values
      | Adding Additional Comment          |
      | Adding Contact Details             |
      | Adding Contact Reason/Action       |
      | Correcting Additional Comment      |
      | Correcting Case/Consumer Link      |
      | Correcting Contact Reason/Action   |
      | Correcting Contact Details         |
      | Correcting Disposition             |
      | Correcting Third Party Information |

  @CP-8290 @CP-8290-1.1 @ui-core @crm-regression @umid
  Scenario: Verify Reason for Edit dropdown for Unidentified Contact Record BLCRM
    Given I logged into CRM
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "135124"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Case/Consumer Link Unidentified |
    And I verify the "Correcting Case/Consumer Link is not a valid reason for edit for an Unidentified Contact Record" error message

  @CP-8290 @CP-8290-1.2 @ui-core @crm-regression @umid
  Scenario: Verify Reason for Edit dropdown for General or Unidentified Contact Record BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I am able to search using the following basic search parameters for Contact Record
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I verify Reason for Edit dropdown populated with values
      | Correcting Third Party Information |
    And I click save button Active contact
    And I verify the "Correcting Third Party Information is not a valid reason for edit for this Contact Record" error message

  @CP-8290 @CP-8290-2 @ui-core @crm-regression @umid
  Scenario: Verify Contact Record with a Reason for Edit of “Correcting Case/Consumer link“ BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I am able to search using the following basic search parameters for Contact Record
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I link consumer if not linked for "Emma" "Jones"
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Case/Consumer Link BLCRM |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Case/Consumer Link"

  @CP-8290 @CP-8290-3 @ui-core @crm-regression @umid
  Scenario: Verify Contact Record with a Reason for Edit of “Correcting Contact Details“ BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I am able to search using the following basic search parameters for Contact Record
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Contact Details |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Contact Details"

  @CP-8290 @CP-8290-4 @ui-core @crm-regression @umid
  Scenario: Verify Contact Record with a Reason for Edit of “Adding Contact Reason/Action“ BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I am able to search using the following basic search parameters for Contact Record
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Adding Contact Reason/Action |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Adding Contact Reason/Action"

  @CP-8290 @CP-8290-5 @ui-core @crm-regression @umid
  Scenario: Verify Contact Record with a Reason for Edit of “Correcting Contact Reason/Action“ BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I am able to search using the following basic search parameters for Contact Record
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I link consumer if not linked for "Emma" "Jones"
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Contact Reason/Action BLCRM |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Contact Reason/Action"

  @CP-8290 @CP-8290-6 @ui-core @crm-regression @umid @ui-core-smoke
  Scenario: Verify Contact Record with a Reason for Edit of “Adding Additional Comment“ BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I am able to search using the following basic search parameters for Contact Record
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Adding Additional Comment |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Adding Additional Comment"


    #correcting additional comment is not going to show up on edit history
  @CP-8290 @CP-8290-7 @ui-core @crm-regression @umid
  Scenario: Verify Contact Record with a Reason for Edit of “Correcting Additional Comment“ BLCRM
    Given I logged into CRM
    When I navigate to Contact Record search
    And I searched customer have First Name as "Emma" and Last Name as "Jones" with todays date
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Additional Comment |
    And I edit the saved Additional comments
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Additional Comment"


  @CP-8290 @CP-8290-8 @ui-core @crm-regression @umid
  Scenario: Verify Contact Record with a Reason for Edit of “Correcting Third Party Information“ BLCRM
    Given I logged into CRM
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "145647"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I choose Reason for Edit dropdown populated with value
      | Correcting Third Party Information |
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab
    And Verify EDIT History with Reason for Edit "Correcting Third Party Information BLCRM"

  @CP-8290 @CP-8290-9 @ui-core @crm-regression @umid
  Scenario: Verify Edit History is descending order BLCRM
    Given I logged into CRM
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "196504"
    When I click on first Contact Record ID on Contact Record
    Then I navigate to the Edit History page
    Then Verify in Edit History page EditedOn is descending order



