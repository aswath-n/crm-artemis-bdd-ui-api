@fix2
Feature: End Date Current Active Address IF Future Address Start Date is Updated to Overlap

  @Beka @CP-24380 @CP-24380-01  @crm-regression @ui-cc
  Scenario: Edit Mailing Address - Consumer
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record with address "Mailing"
    And I navigate to Contact Info Page
    And I add new "Mailing" addresses for future
    Then verify active address is set to the Start Date of the future address

  @Beka @CP-24380 @CP-24380-02  @crm-regression @ui-cc
  Scenario: Edit Physical Address - Consumer Profile
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record with address "Physical"
    And I navigate to Contact Info Page
    And I add new "Physical" addresses for future
    Then verify active address is set to the Start Date of the future address

  @Beka @CP-24380 @CP-24380-03  @crm-regression @ui-cc
  Scenario: Edit Physical Address - Consumer Profile - Before Current Start Date
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record with address "Physical"
    And I navigate to Contact Info Page
    And I add new "Physical" addresses for future
    When I edit the future "Physical" address to have a Start Date within before the Start Date of the active address
    Then the System will throw an Error Message "The Future Address cannot have a Start Date prior to the Current Address Start Date"

  @Beka @CP-24380 @CP-24380-04  @crm-regression @ui-cc
  Scenario: Edit Mailing Address - Consumer Profile - Before Current Start Date
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record with address "Mailing"
    And I navigate to Contact Info Page
    And I add new "Mailing" addresses for future
    When I edit the future "Mailing" address to have a Start Date within before the Start Date of the active address
    Then the System will throw an Error Message "The Future Address cannot have a Start Date prior to the Current Address Start Date"

  @Beka @CP-24380 @CP-24380-05  @crm-regression @ui-cc
  Scenario: Mailing Address - Case Summary
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I add new "Mailing" addresses for future in to case
    Then verify active address is set to the Start Date of the future address

  @Beka @CP-24380 @CP-24380-06 @crm-regression @ui-cc
  Scenario:  Physical  Address - Case Summary
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I change address type in active address to Physical
    And I add new "Physical" addresses for future in to case
    Then verify active address is set to the Start Date of the future address

  @Beka @CP-24380 @CP-24380-07  @crm-regression @ui-cc
  Scenario: Edit Physical Address - Case Summary - Before Current Start Date
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I change address type in active address to Physical
    And I add new "Physical" addresses for future in to case
    When I edit the future "Physical" address to have a Start Date within before the Start Date of the active address
    Then the System will throw an Error Message "The Future Address cannot have a Start Date prior to the Current Address Start Date"

  @Beka @CP-24380 @CP-24380-08  @crm-regression @ui-cc
  Scenario: Edit Mailing Address - Case Summary - Before Current Start Date
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I add new "Mailing" addresses for future in to case
    When I edit the future "Mailing" address to have a Start Date within before the Start Date of the active address
    Then the System will throw an Error Message "The Future Address cannot have a Start Date prior to the Current Address Start Date"

  @Beka @CP-444 @CP-444-01 @crm-regression @ui-cc     #failing due to update functionality
  Scenario: Verify max 1 active Physical Address can be added to consumer on a case
    Given I logged into CRM and click on initiate contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I click on Add new address button on Contact Info Tab Page
    And I adding new "Physical" address to Contact Info
    When I update the address type to "Physical"
    Then I verify error message on add address page "Address type is already active"

  @Beka @CP-444 @CP-444-02 @crm-regression @ui-cc     #failing due to update functionality
  Scenario: Verify max 1 active Physical Address can be added to consumer not on a case
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record with address "Physical"
    And I navigate to Contact Info Page
    And I click on Add new address button on Contact Info Tab Page
    And I adding new "Physical" address to Contact Info
    Then I verify error message on add address page "Address type is already active"

  @Beka @CP-444 @CP-444-03 @crm-regression @ui-cc    #failing due to update functionality
  Scenario: Verify max 1 active Mailing Address can be added to consumer not on a case
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record with address "Mailing"
    And I navigate to Contact Info Page
    And I click on Add new address button on Contact Info Tab Page
    And I adding new "Mailing" address to Contact Info
    Then I verify error message on add address page "Address type is already active"

  @Beka @CP-444 @CP-444-04 @crm-regression @ui-cc     #failing due to update functionality
  Scenario: Verify max 1 active Mailing Address can be added to consumer on a case
    Given I logged into CRM and click on initiate contact
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Contact Info Tab
    And I click on Add new address button on Contact Info Tab Page
    And I adding new "Mailing" address to Contact Info
    Then I verify error message on add address page "Address type is already active"

  @chopa @CP-25017 @CP-25017-01 @crm-regression @ui-cc
  Scenario: Verify Start and End dates of consumers when changing roles PI to CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on first consumerID for Primary Individual
    When I change consumer role to "Member"
    Then I choose "Unknown" in Relationship dropdown
    And I click on Save button on add primary individual page
    And I click on continue button on warning message
    And I click on first consumerID for Primary Individual
    Then I should see "INACTIVE" in business unit view page
    Then I verify consumer is end dated with the day before
    Then I click on the back arrow button
    When I click on any existing Case Member
    Then I should see "ACTIVE" in business unit view page
    Then I verify consumer to have start date as current date

  @chopa @CP-25017 @CP-25017-02 @crm-regression @ui-cc
  Scenario Outline: Verify Start and End dates of consumers when changing roles CM to PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on Add Button for Case Member
    When I enter the mandatory fields details "<FirstName>", "<LastName>", "<DOB>", "<Gender>", "<StartDate>","<EndDate>" "<Language>","<Relation>","<SSN>"
    And I click on continue button on warning message
    When I click on any existing Case Member
    When I change consumer role to "Primary Individual"
    And I click on Save button on add primary individual page
    And I click on continue button on warning message
    When I click on any existing Case Member
    Then I should see "INACTIVE" in business unit view page
    Then I verify consumer is end dated with the day before
    Then I click on the back arrow button
    And I click on first consumerID for Primary Individual
    Then I should see "ACTIVE" in business unit view page
    Then I verify consumer to have start date as current date
    Examples:
      | FirstName | LastName | DOB        | Gender | StartDate | EndDate | Language | Relation | SSN         |
      | {random}  | {random} | 01/01/2015 | Female | today     |[blank]|          | Child    | 987-23-1234 |

  @chopa @CP-25017 @CP-25017-03 @crm-regression @ui-cc
  Scenario: Verify Start and End dates of consumers when changing roles PI to CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on first consumerID for Primary Individual
    When I change consumer role to "Member"
    Then I choose "Unknown" in Relationship dropdown
    And I click on Save button on add primary individual page
    And I click on continue button on warning message
    When I click on any existing Case Member
#    When I change consumer role to "Primary Individual"
#    And I click on Save button on add primary individual page
#    And I click on continue button on warning message
#    When I click on any existing Case Member
#    Then I verify consumer is end dated with the day before
#    Then I click on the back arrow button
  #    And I click on first consumerID for Primary Individual
    Then I verify consumer to have start date as current date

  @chopa @CP-402 @CP-402-01 @crm-regression @ui-cc                #failing due to CP-31237
  Scenario: Update consumer role for Case Member
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Member"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    When I click on any existing Case Member
    When I change consumer role to "Primary Individual"
    And I click on Save button on add primary individual page
    And I click on continue button on warning message
    And I click on first consumerID for Primary Individual
    Then I verify the consumer role is updated to "Primary Individual"

  @chopa @CP-402 @CP-402-02 @crm-regression @ui-cc
  Scenario: Verify Relationship to PI field becomes disabled and set to null
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Tom" and Last Name as "Bruce"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on any existing Case Member
    When I change consumer role to "Primary Individual"
    Then I verify Relationship to PI field becomes disabled

  @chopa @CP-402 @CP-402-03 @crm-regression @ui-cc @events-cc
  Scenario: Verify Consumer Role is systematically populated to Case Member and is not editable
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Tom" and Last Name as "Bruce"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on Add Button for Case Member
    Then I verify consumer role is "Member" and is disabled

#  @chopa @CP-402 @CP-402-04 @crm-regression @ui-cc @events-cc             # functionality change
#  Scenario: CONSUMER_UPDATE_EVENT check for CM End-date and PI start-end
#    Given I logged into CRM
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    When I create new consumer using case loader for case consumer date field check for "Member"
#    When I click case consumer search tab
#    And I search consumer with first name and last name for case consumer date field check
#    When I click on any existing Case Member
#    When I change consumer role to "Primary Individual"
#    And I click on Save button on add primary individual page
#    And I click on continue button on warning message
#    And I click on first consumerID for Primary Individual
#    Then I verify consumer to have start date as current date
#    And I will take correlation Id for "CONSUMER_UPDATE_EVENT" for "consumerId" for case consumer date field check
#    Then I will verify Case Member end dated

  @Beka @CP-23191 @CP-23191-01  @crm-regression @ui-cc
  Scenario Outline: Verify Warning Messages when I add or edit address
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record with address "<mailType>"
    And I navigate to Contact Info Page
    And I add new "<mailType>" addresses for future to check warning message
    Then I verify warning is displayed with message "<warning message1>"
    Examples:
      | mailType | warning message1                                                                                                   |
      | Physical | The current active Physical Address will be systematically end dated the day before the Start Date of this address |
      | Mailing  | The current active Mailing Address will be systematically end dated the day before the Start Date of this address  |

