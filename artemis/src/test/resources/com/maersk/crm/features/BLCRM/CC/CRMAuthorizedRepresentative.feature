Feature: Authorized Representative

  @CP-333 @CP-333-01 @asad @crm-regression  @ui-cc
  Scenario: Add New AR
    Given I logged into CRM
    When I click case consumer search tab
    When I am a user who has access to add authorized representative
    Then I am able to add the following Authorized Representative Information fields

  @CP-333 @CP-333-01.1 @asad @crm-regression @ui-cc
  Scenario: Required Information Validation
    Given I logged into CRM
    When I click case consumer search tab
    When I am a user who has access to add authorized representative
    Then I am able to validate the required Authorized Representative Information fields

  @CP-333 @CP-333-01.2 @CP-23126 @asad @crm-regression @ui-cc
  Scenario: AR Field Level Validation
    Given I logged into CRM
    When I click case consumer search tab
    When I am a user who has access to add authorized representative
    Then I see "firstName" field accept only 50 characters in authorized representative
    Then I see "middleName" field accept only 1 characters in authorized representative
    Then I see "lastName" field accept only 50 characters in authorized representative
    Then I see "startDate" field accept only "**/**/****" format
    Then I see "endDate" field accept only "**/**/****" format
    Then I see "dob" field accept only "**/**/****" format
    Then I see SSN 9 digits value

  @CP-333 @CP-333-013 @asad @crm-regression @ui-cc
  Scenario: Save Button
    Given I logged into CRM
    When I click case consumer search tab
    When I am a user who has access to add authorized representative
    Then I am able to add the following Authorized Representative Information fields

  @CP-333 @CP-333-014 @asad @crm-regression @ui-cc
  Scenario: Cancel Button
    Given I logged into CRM
    When I click case consumer search tab
    When I am a user who has access to add authorized representative
    Then I select the Cancel Button and I will get the following Warning Message "If you navigate away, your information will not be saved"

  @CP-333 @CP-333-20 @asad @crm-regression @ui-cc
  Scenario Outline: AR Status
    Given I logged into CRM
    When I navigate to Case Consumer Search Page
    And I am a user who has access to add authorized representative
    Then I verify the AR Status in UI based on "<startDate>" and "<endDate>"
    Examples:
      | startDate  | endDate |
      | 08/01/2021 |[blank]|
#      | Future     | Future     |
#      | 09/15/2019 | Future     |
#      | 01/01/2019 | 01/01/2019 |

  @CP-333 @CP-333-3.0 @asad @crm-regression @ui-cc
  Scenario: AR Age
    Given I logged into CRM
    When I click case consumer search tab
    When I am a user who has access to view authorized representative
    Then I verify the age of Authorized Representative

  #@CP-333 @CP-333-4.0 @asad @crm-regression @ui-cc  #no source, createdBy, and createdOn on UI
  Scenario: Capture Update Information
    Given I logged into CRM
    When I click case consumer search tab
    When I am a user who has access to view authorized representative
    Then I capture the updated information of Authorized Representative

  @CP-275 @CP-275-1.0 @ozgen @crm-regression @ui-cc
  Scenario:View Consumer's Correspondence Preference not Selected - Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Aliam" and Last Name as "Grayson"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on existing Authorized Representative Record
    Then I verify consumer's correspondence preference based on "nonselected"

  #@CP-275 @CP-275-1.1 @ozgen @crm-regression @ui-cc            ------------------>>>>>>>>>>>>>>>>>>>>  muted due to covered on @CP-275-3.2
  #Scenario:View Consumer's Correspondence Preference Selected Paperless- Authorized Representative
  #  Given I logged into CRM and click on initiate contact
   # When I searched customer have First Name as "Michaella" and Last Name as "Pindiv"
  #  And I link the contact to an existing Case or Consumer Profile
  #  And I click on the Demographic Info Tab
  #  When I click on existing Authorized Representative Record
   # Then I verify consumer's correspondence preference based on "selected"

  @CP-275 @CP-275-1.2 @ozgen @crm-regression @ui-cc
  Scenario:Verify Consumer's Correspondence Preference isnt marked Mandatory- Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Michaella" and Last Name as "Pindiv"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on existing Authorized Representative Record
    And I verify that correspondence preference isnt marked mandatory

  @CP-275 @CP-275-2.0 @ozgen @crm-regression @ui-cc
  Scenario:Verification of Update Consumer's Correspondence Preference Options - Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Michaella" and Last Name as "Pindiv"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on existing Authorized Representative Record
    Then I verify correspondence preference options for baseline
      | Paperless |

  @CP-275 @CP-275-2.1 @ozgen @crm-regression @ui-cc
  Scenario:Verification of Update Consumer's Correspondence Preference  - Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Michaella" and Last Name as "Pindiv"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on existing Authorized Representative Record
    And I populate consumer's correspondence preference as "Paperless" from multi select
    Then I verify consumer's updated correspondence preference

  @CP-275 @CP-275-2.2 @ozgen @crm-regression @ui-cc  #no warning message
  Scenario:Verification of Deselecting Consumer's Correspondence Preference  - Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Michaella" and Last Name as "Pindiv"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on existing Authorized Representative Record
    And I populate consumer's correspondence preference as "Paperless" from multi select
    And I click on Save button for Authorized Representative
    When I click on existing Authorized Representative Record
    And I deselect consumer's correspondence preference
    Then I verify consumer's correspondence preference based on "nonselected"
    And I click on Save button for Authorized Representative

  @CP-275 @CP-275-3.0 @ozgen @crm-regression @ui-cc
  Scenario:Capture Consumer's Correspondence Preference Options to Add new record- Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Michaella" and Last Name as "Pindiv"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I click on the Add button for Authorized Representative
    And I populate Authorized Representative field for new record
    Then I verify correspondence preference options for baseline
      | Paperless |

  @CP-275 @CP-275-3.1 @ozgen @crm-regression @ui-cc
  Scenario:Capture Consumer's Correspondence Preference for Adding new record- Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Michaella" and Last Name as "Pindiv"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I click on the Add button for Authorized Representative
    And I populate Authorized Representative field for new record
    And I populate consumer's correspondence preference as "Paperless" from multi select
    Then I verify consumer's updated correspondence preference

  @CP-275 @CP-275-3.2 @ozgen @crm-regression @ui-cc
  Scenario:Verification of Deselecting Consumer's Correspondence Preference for Adding new record- Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Michaella" and Last Name as "Pindiv"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    Then I click on the Add button for Authorized Representative
    And I populate Authorized Representative field for new record
    And I populate consumer's correspondence preference as "Paperless" from multi select
    Then I verify consumer's updated correspondence preference
    Then I deselect consumer's correspondence preference
    And I verify consumer's correspondence preference based on "nonselected"
    And I click on Save button for Authorized Representative

  @CP-7453 @CP-7453-01 @umid @ui-cc @crm-regression
  Scenario: Verify Split Authorized Representative Component into 2 sections, Mandatory fields
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    Then I click on the Add button for Authorized Representative
    Then I will see information split into 2 sections
    And I verify consumer role "Authorized Representative"
    Then I verify Mandatory fields
      | Access Type            |
      | Start Date             |
      | Receive Correspondence |
      | First Name             |
      | Last Name              |
      | DOB                    |
      | Gender                 |
      | Spoken Language        |
      | Written Language       |

  @CP-7453 @CP-7453-02 @umid @ui-cc @crm-regression
  Scenario: Verify error messages for mandatory fields and ACCESS TYPE
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    Then I click on the Add button for Authorized Representative
    And I click on Save button on add primary individual page
    Then I verify "ACCESS TYPE" is required and cannot be left blank
    Then I see error message populated below each field on Add AR Page

  @CP-7453 @CP-7453-03 @umid @ui-cc @crm-regression
  Scenario: Verify All of the dropdown's for Authorized Representative Page
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    Then I click on the Add button for Authorized Representative
    And I verify consumer role "Authorized Representative"
    Then I verify the dropdown values for "Gender"
      | Female  |
      | Male    |
      | Unknown |
      | Neutral |
    Then I verify the dropdown values for "receiveCorrespondence"
      | Yes |
      | No  |
    Then I verify the dropdown values for "spokenLanguage"
      | English    |
      | Vietnamese |
      | Other      |
      | Russian    |
      | Spanish    |
    Then I verify the dropdown values for "writtenLanguage"
      | English    |
      | Vietnamese |
      | Other      |
      | Russian    |
      | Spanish    |
    Then I verify the dropdown values for "Correspondence"
      | Paperless |
    Then I verify the dropdown values for "Access Type"
      | Full Access    |
      | Partial Access |

  @CP-7453 @CP-7453-04 @umid @ui-cc @crm-regression @CP-1824 @CP-1824-01 @CP-1824-04
  Scenario: Authorized Representative Cancel & Back Arrow & Navigate to Contact search without editing
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    Then I click on the Add button for Authorized Representative
    And I click on cancel button on create task type screen
    Then I verify primary individual section displayed
    Then I click on the Add button for Authorized Representative
    When I navigate away by clicking the back arrow without saving changes on this page
    Then I verify primary individual section displayed
    Then I click on the Add button for Authorized Representative
    When I navigate to Contact Record Search Page
    When I am navigated back to Contact Record UI page

  @CP-7453 @CP-7453-05 @umid @ui-cc @crm-regression
  Scenario: Authorized Representative Cancel & Back Arrow & Navigate to Contact search with editing warning message
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    Then I click on the Add button for Authorized Representative
    Then I am able to update each checkbox
    And I click on cancel button on create task type screen
    And I see "All changes will be lost" alert displayed
    And I click on Cancel option on warning message
    Then I will see information split into 2 sections
    When I navigate away by clicking the back arrow without saving changes on this page
    And I see "All changes will be lost" alert displayed
    And I click on Cancel option on warning message
    Then I will see information split into 2 sections
    When I navigate to Contact Record Search Page
    And I see "All changes will be lost" alert displayed

  @CP-7453 @CP-7453-06 @umid @ui-cc @crm-regression
  Scenario Outline: Profile Information fields Save, check format Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Fhkghkhgk" and Last Name as "Fhkfhkfh"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify primary individual section displayed
    When I click on existing Authorized Representative Record
    And I provide invalid data for <iteration>,<firstName>,<middleName>,<lastName>,<DOB>,<age>,<startDate>,<endDate>,<SSN>
    Then New Authorized Representative should be added to the PI list
    Examples:
      | iteration | firstName         | middleName | lastName          | DOB          | age | startDate    | endDate | SSN         |
      | 1         | 'ADASDIOAJdsSJDK' | ''         | 'DASDIOAasHISJDA' | '01/01/1945' | ''  | '10/19/2020' | ''      | '121212212' |


  @CP-347 @CP-347-01 @umid @ui-cc @crm-regression
  Scenario: View Information Authorized Representative
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Gala" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I am able to see AR information In View Only Format

  @CP-347 @CP-347-02 @umid @ui-cc @crm-regression
  Scenario: View Start and End Dates
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Gala" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I hover my mouse over the Status of the "AR" view the Start and End Date

  @CP-347 @CP-347-03 @umid @ui-cc @crm-regression
  Scenario: View Derived AR DOB
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Fhkghkhgk" and Last Name as "Fhkfhkfh"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I will see the Age for the AR that is derived from the DOB of the AR

  @CP-347 @CP-347-04 @umid @ui-cc @crm-regression
  Scenario: Display 2 records
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Harry" and Last Name as ""
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I will see Authorized Representative displays 2 records per page