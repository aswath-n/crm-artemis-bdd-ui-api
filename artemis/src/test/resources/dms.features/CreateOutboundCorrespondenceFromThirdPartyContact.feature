@CP-23257
Feature: Outbound Correspondence Request from Third party Contact

  @CP-23257-01  @ui-ecms1 @Keerthi
  Scenario: Validate the Send To section while creating Outbound correspondence from Third Party Contact
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I clicked on Third Party Contact
    Then I entered required data in Third party contact page
      | FirstName | Random |
      | LastName  | Random |
    And I have selected an Outbound Correspondence through the UI "No Keys - NOKEYS" as type
    And I validate Send To section in correpondence request page
      | FirstName    | Random |
      | LastName     | Random |
      | Checkbox     | true   |
      | AddressLine1 | Random |
      | AddressLine2 | Random |
      | City         | Random |
      | State        | Random |
      | Zipcode      | Random |

  @CP-23257-02  @ui-ecms1 @Keerthi
  Scenario: Validate the error messages for Send To section required fields while creating Outbound correspondence from Third Party Contact
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I clicked on Third Party Contact
    Then I entered required data in Third party contact page
      | FirstName | Random |
      | LastName  | Random |
    And I have selected an Outbound Correspondence through the UI "No Keys - NOKEYS" as type
    And I validate error messages for Send To section required fields

  @CP-23257-03  @ui-ecms1 @Keerthi
  Scenario: Validate the auto populate of recipient first and lastname while creating Outbound correspondence from Third Party Contact
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I clicked on Third Party Contact
    Then I entered required data in Third party contact page
      | FirstName | Random |
      | LastName  | Random |
    And I have selected an Outbound Correspondence through the UI "No Keys - NOKEYS" as type
    And I validate the auto populate of recipient first and lastname

  @CP-23257-04  @ui-ecms2 @Keerthi
  Scenario: Validate the Regarding section while creating Outbound correspondence from Third Party Contact
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I clicked on Third Party Contact
    Then I entered required data in Third party contact page
      | FirstName | Random |
      | LastName  | Random |
    And I have selected an Outbound Correspondence through the UI "No Keys - NOKEYS" as type
    And I validate the Regarding section is not displayed

  @CP-23257-05  @ui-ecms2 @Keerthi #Fails due to the CP-36237
  Scenario: Validate the ob definition type while creating Outbound correspondence from Third Party Contact
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I will capture all active correspondences for the project
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I clicked on Third Party Contact
    Then I entered required data in Third party contact page
      | FirstName | Random |
      | LastName  | Random |
    And I validate outbound definition type while creating from Third Party Contact

  @CP-23257-06  @ui-ecms2 @Keerthi
  Scenario: Validate the save functionality while creating Outbound correspondence from Third Party Contact
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I clicked on Third Party Contact
    Then I entered required data in Third party contact page
      | FirstName | Random |
      | LastName  | Random |
    And I have selected an Outbound Correspondence through the UI "No Keys - NOKEYS" as type
    And I enter data in send To section
      | AddressLine1 | Random |
      | AddressLine2 | Random |
      | City         | Random |
      | State        | Random |
      | Zipcode      | Random |
    Then I validate Save functionality

