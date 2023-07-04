Feature: DC-EB View Consumer Profile

  @CP-36632 @CP-36632-01 @ui-cc-dc @muhabbat
  Scenario: DC-EB: Navigation to view Consumer Profile by caseID
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I search for consumer by "StateID" with value "MK9876545678"
    And I navigate to consumer profile page by clicking on caseId

  @CP-36632 @CP-36632-02 @ui-cc-dc @muhabbat
  Scenario: DC-EB: Navigation to view Consumer Profile by Consumer Name
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "MK9876545678"
    And I navigate to DC consumer profile page by clicking on name

  @CP-36632 @CP-36632-03 @ui-cc-dc @muhabbat
  Scenario: DC-EB: General Consumer Profile view tabs
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "MK9876545678"
    And I navigate to consumer profile page by clicking on stateId
    Then I validate all configured tabs from View profile screen

  @CP-36632 @CP-36632-04 @ui-cc-dc @muhabbat
  Scenario: DC-EB: General Consumer Profile view fields
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "MK9876545678"
    And I navigate to consumer profile page by clicking on stateId
    Then I validate all the fields on View profile screen

  @CP-36632 @CP-36632-05 @ui-cc-dc @muhabbat
  Scenario: DC-EB: General Consumer Profile view Masked SSN and DOB
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "MK9876545678"
    And I navigate to consumer profile page by clicking on stateId
    Then I see unmasking DOB and SSN buttons is on View Consumer Profile Page

  @CP-36632 @CP-36632-06 @ui-cc-dc @muhabbat
  Scenario: DC-EB: General Consumer Profile view unmasked DOB format
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "MK9876545678"
    And I navigate to consumer profile page by clicking on stateId
    When I and click on unmasking SSN and DOB buttons on View Consumer Profile Page
    Then I see DOB in expected format unmasked View Consumer Profile Page

  @CP-36632 @CP-36632-07 @ui-cc-dc @muhabbat
  Scenario: DC-EB: Consumer Name on Profile view header
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "MK9876545678"
    And I navigate to consumer profile page by clicking on stateId
    Then I view the Icon and I will see text display that indicates a user is viewing the 'Consumer Profile View'
    And I will see the Consumer Name next to the consumer icon

  @CP-40691 @CP-40691-01 @ui-cc-dc @beka
  Scenario: Add Consumer reported Mailing Address
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40691-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I minimize Genesys popup if populates
    When I search just created consumer by external consumer ID
    And I navigate to consumer profile page by clicking on stateId
    When I click on add address button on consumer profile page
    Then Verify All fields, buttons and labels is display on edit consumer reported screen
    When I input all required fields for "Mailing" address
      | Address1 | random |
      | Address2 | random |
      | City     | random |
      | State    | random |
      | ZipCode  | random |
    And I click on save button
    Then I verify message "Consumer successfully updated" is display
    Then I verify "Mailing" consumer reported address record got saved and display on page

  @CP-40691 @CP-40691-02 @ui-cc-dc @beka
  Scenario: Add Consumer reported Physical Address
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40691-02" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I minimize Genesys popup if populates
    When I search just created consumer by external consumer ID
    And I navigate to consumer profile page by clicking on stateId
    When I click on add address button on consumer profile page
    When I input all required fields for "Physical" address
      | Address1 | random |
      | Address2 | random |
      | City     | random |
      | State    | random |
      | ZipCode  | random |
    And I click on save button
    Then I verify "Physical" consumer reported address record got saved and display on page

  @CP-40691 @CP-40691-03 @ui-cc-dc @beka
  Scenario: Add 2 Consumer reported address same time and edit
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40691-03" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I minimize Genesys popup if populates
    When I search just created consumer by external consumer ID
    And I navigate to consumer profile page by clicking on stateId
    When I click on add address button on consumer profile page
    When I input all required fields for "Mailing and Physical" address
      | Address1 | random |
      | Address2 | random |
      | City     | random |
      | State    | random |
      | ZipCode  | random |
    And I click on save button
    Then I verify "Mailing" consumer reported address record got saved and display on page
    When I click on edit "Language" button on consumer profile page
    Then I verify all address fields has a value on edit address page
    And I click on delete "Mailing" address button
    When I click on save button
    Then I will verify "Mailing" address record is deleted

  @CP-40691 @CP-40691-04 @ui-cc-dc @beka
  Scenario: Error message validation
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "40691-04" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I minimize Genesys popup if populates
    When I search just created consumer by external consumer ID
    And I navigate to consumer profile page by clicking on stateId
    When I click on add address button on consumer profile page
    When I start input address fields
    And I click on save button
    Then I verify inline error message
      | Address Street 1 | Address Street 1  is required and cannot be left blank  |
      | City             | CITY is required and cannot be left blank              |
      | State            | STATE is required and cannot be left blank             |
      | ZipCode          | ZIP CODE is required and cannot be left blank          |
    And I click on delete "Mailing" address button
    When I input invalid data in to City and ZIP fields
    Then I verify following errors is displayed
      | Address1 | ADDRESS STREET 1  must contain both numeric and alphabetic characters to be valid |
      | City     | Invalid Format Text                                                               |
      | ZipCode  | ZIP CODE must be at least 5 characters                                            |


  @CP-41454 @CP-41454-01 @ui-cc-dc @chopa
  Scenario: DC-EB: View Phone Number Details fields
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "M5297"
    And I navigate to consumer profile page by clicking on stateId
    When I click on add phone button on consumer profile page
    Then I validate all the fields on Phone Number Details screen

  @CP-41454 @CP-41454-02 @ui-cc-dc @chopa
  Scenario: DC-EB: Add Consumer-reported Phone
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41454-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I minimize Genesys popup if populates
    When I search just created consumer by external consumer ID
    And I navigate to consumer profile page by clicking on stateId
    When I click on add phone button on consumer profile page
    And I am able to add a consumer-reported phone in the editable fields
    And I click on Save Button
    Then I verify phone numbers are displayed in Consumer-Reported Communication Panel

  @CP-41454 @CP-41454-03 @ui-cc-dc @chopa                              #  failing due to defect CP-47054
  Scenario: DC-EB: Edit existing Consumer-reported Phone
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41454-02" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I minimize Genesys popup if populates
    When I search just created consumer by external consumer ID
    And I navigate to consumer profile page by clicking on stateId
    When I click on add phone button on consumer profile page
    And I am able to add a consumer-reported phone in the editable fields
    And I click on Save Button
    Then I verify phone numbers are displayed in Consumer-Reported Communication Panel
    When I click on Edit button on phone number field
    And I verify updated phone value after editing existing cell phone number
    When I click on Edit button on phone number field
    Then I verify cell phone number is not displayed after clearing existing cell phone value
    When I click on Edit button on phone number field
    And I clear existing phone numbers
    And I click on Save Button

  @CP-41454 @CP-41454-04 @ui-cc-dc @chopa
  Scenario: DC-EB: Verify warning messages for “Back Arrow” or “Cancel Button” on Phone Details Screen
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41454-03" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I minimize Genesys popup if populates
    When I search just created consumer by external consumer ID
    And I navigate to consumer profile page by clicking on stateId
    When I click on add phone button on consumer profile page
    And I am able to add a consumer-reported phone in the editable fields
    And I click on Save Button
    When I click on Edit button on phone number field
    And I clear existing phone numbers
    Then I click on the back arrow button on add application member page
    And verify the "If you continue, all the captured information will be lost" warning message
    And I click on the Cancel Button
    Then I will remain in Phone Number Details Page
    Then I click on the back arrow button on add application member page
    And verify the "If you continue, all the captured information will be lost" warning message
    And I click on continue button on warning message
    Then I will be navigated to Consumer-reported Communication Page
    When I click on Edit button on phone number field
    And I clear existing phone numbers
    And I click on Save Button

  @CP-40422 @CP-40422-01 @ui-cc-dc @chopa
  Scenario: DC-EB: View Cases Panel
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "M4378"
    And I navigate to consumer profile page by clicking on caseId
    Then I verify fields in Cases Panel is displayed

  @CP-40422 @CP-40422-02 @ui-cc-dc @chopa
  Scenario: DC-EB: Navigate to Medicaid Case View and back to Consumer Profile Info Tab
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "M4378"
    And I navigate to consumer profile page by clicking on stateId
    When I click on Medicaid Case ID
    Then I will be navigated to Medicaid Case Details screen
    Then I click on the back arrow button on add application member page
    Then I will be navigated to Consumer-reported Communication Page

  @CP-36578 @CP-36578-0.1 @ui-cc-dc @chopa
  Scenario: DC-EB: View Consumer-Reported Communication Panel
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "M4378"
    And I navigate to consumer profile page by clicking on stateId
    Then I verify fields in Consumer-Reported Communication Panel are displayed
    Then I verify Do Not Call field label with checkbox is displayed

  @CP-36578 @CP-36578-1.1 @ui-cc-dc @chopa
  Scenario: DC-EB: Add Address for Consumer-Reported Communication Panel
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "M4378"
    And I navigate to consumer profile page by clicking on stateId
    When I click on add "Language" button on consumer profile page
    Then I input all required fields for "Address" in Correspondence Preference Page
    And I click on save button
    Then I verify correspondence preference "Address" is saved and displayed in communication panel
    When I click on edit "Language" button on consumer profile page
    When I click on uncheck "MAIL" checkbox
    And I click on save button

  @CP-36578 @CP-36578-1.2 @ui-cc-dc @chopa
  Scenario: DC-EB: Add Email for Consumer-Reported Communication Panel
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "M4378"
    And I navigate to consumer profile page by clicking on stateId
    When I click on add "Language" button on consumer profile page
    Then I input all required fields for "Email" in Correspondence Preference Page
    And I click on save button
    Then I verify correspondence preference "Email" is saved and displayed in communication panel
    When I click on edit "Language" button on consumer profile page
    When I click on uncheck "EMAIL" checkbox
    And I click on save button

  @CP-36578 @CP-36578-1.3 @ui-cc-dc @chopa
  Scenario: DC-EB: Add Phone Number for Consumer-Reported Communication Panel
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "M4378"
    And I navigate to consumer profile page by clicking on stateId
    When I click on add "Language" button on consumer profile page
    Then I input all required fields for "Text" in Correspondence Preference Page
    And I click on save button
    Then I verify correspondence preference "Text" is saved and displayed in communication panel
    When I click on edit "Language" button on consumer profile page
    When I click on uncheck "TEXT" checkbox
    And I click on save button

  @CP-38983 @CP-38983-01 @ui-cc-dc @beka
  Scenario: Navigate to child MCO profile from mother MMIS profile
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "45052267test"
    And I navigate to consumer profile page by clicking on stateId
    Then I should see "child" name on relationships module
    When I click on child name
    Then I should see "mother" name on relationships module

  @CP-38983 @CP-38983-02 @ui-cc-dc @beka
  Scenario: Navigate to child MMIS profile from mother MMIS profile
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "11739988test"
    And I navigate to consumer profile page by clicking on stateId
    Then I should see "child" name on relationships module
    When I click on child name
    Then I should see "mother" name on relationships module


  @CP-38983 @CP-38983-03 @ui-cc-dc @beka
  Scenario: Navigate to mother MMIS profile from child MCO profile
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "ConsumerID" with value "9558"
    And I navigate to consumer profile page by clicking on stateId
    Then I should see "mother" name on relationships module
    When I click on mother name
    Then I should see "child" name on relationships module

  @CP-38983 @CP-38983-04 @ui-cc-dc @beka
  Scenario: Navigate to mother MMIS profile from child MMIS profile
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "StateID" with value "11739988MMIS"
    And I navigate to consumer profile page by clicking on stateId
    Then I should see "mother" name on relationships module
    When I click on mother name
    Then I should see "child" name on relationships module

  @CP-48439 @CP-48439-01 @ui-cc-dc @chopa
  Scenario: Verify Languages in Communication Preferences are listed in Alphabetical order
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "48439-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I minimize Genesys popup if populates
    When I search just created consumer by external consumer ID
    And I navigate to consumer profile page by clicking on stateId
    When I click on add "Language" button on consumer profile page
    Then I should see language dropdown is in alphabetical order
