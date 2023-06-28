Feature: Comments & Additional Comments

  @CRM-636 @CRM-636-01 @shilpa @crm-regression @ui-core
  Scenario: Validate cancel button functionality for  Reasons and Comments Section
    Given I logged into CRM and click on initiate contact
    And I should see all the attributes present in the Reasons Section
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
      | Re-Sent Notice         |
    And I Enter valid data and click on the cancel button
    Then I should see a new  window with Information Lost message

  @CRM-636 @CRM-636-07 @shilpa @crm-regression @ui-core
  Scenario: Validate error is displayed when Contact Reason & Actions are left empty
    Given I logged into CRM and click on initiate contact
    And I click on the save button
    And I should be able to save the additional comments
    Then I should receive error to fill out required fields on reasons and comments
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    And I click on the save button
    Then I verify reason is not saved without action

  @CRM-636 @CRM-636-02 @CRM-1272 @CRM-1272-01 @CRM-1272-02 @CP-46042 @CP-46042-01 @crm-regression @ui-core @shruti @shilpa
  Scenario: Validate Reasons ,Actions & Comments are added successfully, Time Stamp is displayed & reason/actions are edited
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
      | Re-Sent Notice         |
    And I Enter valid data and click on the save button
    Then I should see the Time stamp should be displayed with the comments
    And I edit the saved "contact reason" and "contact action"
    Then I verify edited reason and actions are displayed

  @CRM-636 @CRM-636-05 @CRM-636-06 @CRM-1272 @CRM-1272-04 @shruti @shilpa @crm-regression @crm-smoke @ui-core
  Scenario:Validate the Additional Comments are saved, Time Stamp is displayed & can be edited
    Given I logged into CRM and click on initiate contact
    And I Enter Valid  additional Comments
    Then I should be able to save the additional comments
    Then I should see the Time stamp should be displayed with the additional comments
    And I edit the saved Additional comments
    Then I verify edited Additional comments are displayed

  @CRM-1046 @CRM-1046-01 @crm-regression @Sujoy @ui-core
  Scenario: Validate multiple actions can be saved for one reason
    Given I logged into CRM and click on initiate contact
    When I create new reason "Information Request" with actions
      | Provided Appeal Information             |
      | Provided Case Status/Information        |
      | Provided Eligibility Status/Information |
      | Provided Enrollment Status/Information  |
      | Provided Financial Information          |
      | Provided Program Information            |
    And I Enter valid data and click on the save button
    And I expand saved contact reason
    Then I verify all select actions are displayed

  @CRM-24861 @CRM-24861-01 @aikanysh @crm-regression @ui-core
  Scenario: All Tenants : Delete Reason/Action Button available
    Given I logged into CRM and click on initiate contact
    And I should see all the attributes present in the Reasons Section
    When I create new reason "Information Request" with actions
      | Provided Appeal Information             |
    And I Enter valid data and click on the save button
    Then I verify I am able to see the delete button (trash can icon) for reason&action
    And I verify when I hover over trash icon I see text "Delete Reason”

  @CRM-24861 @CRM-24861-02 @aikanysh @crm-regression @ui-core
  Scenario: All Tenants: Delete Reason/Action is possible when clicking on trash icon and Clicking on Continue
    Given I logged into CRM and click on initiate contact
    And I should see all the attributes present in the Reasons Section
    When I create new reason "Information Request" with actions
      | Provided Appeal Information             |
    And I Enter valid data and click on the save button
    Then I am able to delete above reason&action by clicking trash icon and clicking Continue
    And I verify above reason "Information Request" is deleted and not displaying on UI

  @CRM-24861 @CRM-24861-03 @aikanysh @crm-regression @ui-core
  Scenario: All Tenants : Delete Reason/Action is possible when clicking on trash icon and Clicking on Cancel
    Given I logged into CRM and click on initiate contact
    And I should see all the attributes present in the Reasons Section
    When I create new reason "Information Request" with actions
      | Provided Appeal Information |
    And I Enter valid data and click on the save button
    Then I am able to cancel the deletion of above reason&action by clicking trash icon and clicking Cancel
    And I verify above reason "Information Request" is NOT deleted and still displaying on UI

  @CRM-24861 @CRM-24861-04 @aikanysh @crm-regression @ui-core
  Scenario Outline: All Tenants : Delete Reason/Action Button Edit screen
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM
    And I get oneLoginJWT token
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone             | phone::       |
      | consumerEmail             | email::       |
      | contactType               | <contactType> |
      | consumerFirstName         | name::        |
      | consumerLastName          | name::        |
      | organizationName          | name::        |
      | consumerType              | Media         |
      | contactTranslationService | <language>    |
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    Then I am able to delete above reason&action by clicking trash icon and clicking Continue
    And I verify above reason "Complaint - Account Access" is deleted and not displaying on UI
    And I save deletion by choosing "Correcting Contact Reason/Action" from Edit Reason DropDown
    Then I verify reason/action deletion is reflected on Edit History
    Examples:
      | contactType |
      | General     |


#  @CRM-24861 @CRM-24861-05 @aikanysh @crm-regression @ui-core                 TODO Enable back after CP-42999
  Scenario Outline: All Tenants : Delete Reason/Action : Incorrect Reason For Edit
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM
    And I get oneLoginJWT token
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone             | phone::       |
      | consumerEmail             | email::       |
      | contactType               | <contactType> |
      | consumerFirstName         | name::        |
      | consumerLastName          | name::        |
      | organizationName          | name::        |
      | consumerType              | Media         |
      | contactTranslationService | <language>    |
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    When I click on edit button on contact details tab
    Then I am able to delete above reason&action by clicking trash icon and clicking Continue
    Then I will see the error message for Program Field "<IncorrectReasons>" with incorrect Reason for Edit
    Then I will see the error message for Reason&Action deletion with "<IncorrectReasons>" with incorrect Reason for Edit
    Examples:
      | contactType | IncorrectReasons                   |
      | General     | Adding Additional Comment          |
      | General     | Adding Contact Details             |
      | General     | Adding Contact Reason/Action       |
      | General     | Correcting Additional Comment      |
      | General     | Correcting Case/Consumer Link      |
      | General     | Correcting Contact Details         |
      | General     | Correcting Disposition             |
      | General     | Correcting Third Party Information |

