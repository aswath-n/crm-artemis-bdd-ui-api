Feature: Search for Consumer Profile

  @CRM-234 @CRM-234-01 @CRM-236-02 @muhabbat @crm-regression @crm-smoke @ui-cc
  Scenario: Search for Consumer by first name only
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by first name "Ethan"
    And I click on Search Button on Search Consumer Page
    Then I see all search results according to the first name "Ethan"

  @CRM-234 @CRM-234-08 @CRM-236-02 @muhabbat @crm-regression @ui-cc
  Scenario: Search for Consumer by last name only
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by last name "Hunt"
    And I click on Search Button on Search Consumer Page
    Then I see all search results according to the last name "Hunt"

  @CRM-236 @CRM-236-02 @muhabbat @crm-regression @ui-cc
  Scenario: Verify no search result record is available
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by last name "jkshdfkhsdjkh"
    And I click on Search Button on Search Consumer Page
    Then I see No Records Available message on Search Consumer page is displayed

  @CRM-234 @CRM-234-02 @CRM-236-02 @muhabbat @crm-regression @ui-cc
  Scenario: Search for Consumer by SSN
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by SSN "222333665"
    And I click on Search Button on Search Consumer Page
    When I and click on unmasking button
    Then I see all search results according to SSN "222-33-3665"


  @CRM-234 @CRM-234-03 @CRM-236-02 @muhabbat @crm-regression @ui-cc
  Scenario: Search for Consumer by DOB
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by DOB "12/31/1974"
    And I click on Search Button on Search Consumer Page
    When I and click on unmasking button on Contact Record UI Page
    Then I see all search results according to DOB "12/31/1974"


 # retired @CRM-234 @CRM-234-04 @CRM-236-02 @muhabbat 10/25/18 - removing crm, regression, crm-regression, smoke tags till <Unique ID>not being displayed on consumer UI clarified
  Scenario: Search for Consumer by Unique ID
    Given I logged into CRM and click on initiate contact
    When I search for an existing Case ID "56701968"
    And I click on Search Button on Search Consumer Page
    Then I see all search results according to Unique ID "56701968"

  @CRM-234 @CRM-234-05 @CRM-236-02 @CRM-236-02 @muhabbat @crm-regression @ui-cc
  Scenario: Search for Consumer with blank fields
    Given I logged into CRM and click on initiate contact
    When I search for a Consumer with blank fields
    And I click on Search Button on Search Consumer Page
    Then I see warning to enter search parameter


  @CRM-234 @CRM-234-06 @muhabbat @crm-regression @ui-cc
  Scenario: Search for Consumer not in database
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I see No Records Available message on Search Consumer page is displayed

  @CRM-234 @CRM-234-07 @CRM-236-03 @muhabbat @crm-regression @ui-cc
  Scenario: Hit Create Consumer Button (Duplicate of CRM-300-01)
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI

  @CRM-722 @CRM-722-01
  Scenario: Validate error message for date field when date is non-existing
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by DOB "02/32/2000"
    And I click on Search Button on Search Consumer Page
    Then I see the error that date does not exists

  @CRM-722 @CRM-722-02
  Scenario: Validate error message for date field when DOB is in future
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by DOB "02/02/2020"
    And I click on Search Button on Search Consumer Page
    Then I see the error that DOB is in future

  @CRM-83 @CRM-83-0e2e @aswath @crm-regression @ui-cc
  Scenario:  Validate authenticated message after user is enter information
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jacob" and Last Name as "Janes"
    And I expend the Case Consumer this contact relates to in search result
    When I can select the individual "Primary Individual" from the search that is making a contact
    Then I am able to select radio buttons for "Primary Individual"
    Then I verify consumer authentication message
    Then I link the consumer and verify the active contact

  @CRM-833 @CRM-833-01 @aswath @crm-regression @ui-cc
  Scenario:  Validate all check boxes are unchecked
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jacob" and Last Name as "Susan"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the case is linked to contact
    And I verify all check boxes are unchecked

  @CRM-573 @CRM-573-02 @CRM-833 @CRM-833-02 @muhabbat @crm-regression @ui-cc
  Scenario: Linking the individual Consumer from Contact Record Search Result
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jacob" and Last Name as "Janes"
    And I expend the Case Consumer this contact relates to in search result
    When I can select the individual "Primary Individual" from the search that is making a contact
    Then I am able to select radio buttons for "Primary Individual" and link to a Consumer Profile

  @CRM-833 @CRM-833-03 @aswath @crm-regression @ui-cc
  Scenario:  Validating the caller information and other consumer information
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jacob" and Last Name as "Janes"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the case is linked to contact
    And I verify all check boxes are unchecked
    When I can select the individual from the search that is making a contact
    Then I am able to select radio buttons for indivdual
    When I can select the individual "Primary Individual" from the search that is making a contact
    Then I am able to select radio buttons for "Primary Individual" and link to a Consumer Profile

  @CRM-833 @CRM-833-04 @aswath @crm-regression @ui-cc
  Scenario:  Validate authenticated message after user is enter information
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jacob" and Last Name as "Janes"
    And I expend the Case Consumer this contact relates to in search result
    When I can select the individual "Primary Individual" from the search that is making a contact
    Then I am able to select radio buttons for "Primary Individual"
    Then I verify consumer authentication message
    Then I link the consumer and verify the active contact

  @CRM-833 @CRM-833-05 @aswath @crm-regression @ui-cc
  Scenario:  Validate the Unable to authenticated check box
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jacob" and Last Name as "Susan"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the unable to authenticate checkbox displayed


  @CRM-833 @CRM-833-06 @aswath @crm-regression @ui-cc
  Scenario:  Validate the Unable to authenticated Consumer details
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jacob" and Last Name as "Susan"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the unable to authenticate checkbox displayed
    Then I click on unable to authenticate consumer
    Then I link the consumer and verify the active contact
    Then I see system returns to the Edit Contact Record Page

  @CRM-833 @CRM-833-07 @aswath @crm-regression @ui-cc
  Scenario:  Validate the Unable to authenticated alert message
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jacob" and Last Name as "Janes"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the unable to authenticate checkbox displayed
    Then I click on unable to authenticate consumer
    Then I verify unable to authenticate alert message

  @CRM-833 @CRM-833-08 @aswath @crm-regression @ui-cc
  Scenario:  Validate the user is able link consumer without unauthenticated information
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jacob" and Last Name as "Susan"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the unable to authenticate checkbox displayed
    Then I click on unable to authenticate consumer
    Then I link the consumer and verify the active contact

  @CRM-833 @CRM-833-09 @aswath @crm-regression @ui-cc
  Scenario:  Validate the Unable to authenticated check box
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Jacob" and Last Name as "Susan"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the unable to authenticate checkbox displayed

  @CRM-1561 @CRM-1561-01 @aswath
  Scenario: Verification of individual records are displayed in search
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by first name "Gr"
    And I click on Search Button on Search Consumer Page
    Then I see all search results according to the first name "Gr"
    Then I verify the only individual records are listed

  @CRM-1561 @CRM-1561-02 @aswath
  Scenario: I verify the Search results for Consumer (Authorized Representative) and all records displayed
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by first name "Gra"
    When I search for an existing contact by last name "Uuuuu"
    And I click on Search Button on Search Consumer Page
    Then I see all search results according to the first name "Gra"
    And I verify the case is linked to consumer for Authorized record

  @CRM-1561 @CRM-1561-03 @aswath
  Scenario: I verify the Search results for Consumer (Primary Individual) and all records displayed
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by first name "Prithvi"
    When I search for an existing contact by last name "Shaw"
    And I click on Search Button on Search Consumer Page
    Then I see all search results according to the first name "Prithvi"
    And I verify the case is linked to consumer


  @CRM-1897 @CRM-1897-01
  Scenario: Search for Consumer by last name with one char
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by last name "G"
    And I click on Search Button on Search Consumer Page
    Then I see all search results according to the last name "G"


  @CRM-1897 @CRM-1897-02
  Scenario: Search for Consumer by first name with
    #Given I create consumer data for consumer search criteria validation
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by first name "Martha"
    When I search for an existing contact by SSN "212-45-6789"
    And I click on Search Button on Search Consumer Page
    Then I see all Search results with first name as "Martha", last name as "", ssn as "212-45-6789" and dob as ""

  @CRM-1897 @CRM-1897-03
  Scenario: Search for Consumer by first name and DOB
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by first name "Martha"
    And I search for an existing contact by DOB "02/28/1987"
    And I click on Search Button on Search Consumer Page
    Then I see all Search results with first name as "Martha", last name as "", ssn as "" and dob as "02/28/1987"

  @CP-447 @CP-447-01 @muhabbat @crm-regression @ui-cc
  Scenario: Verification Search Case , Case ID, Search Consumer, Consumer ID fields are displayed
    Given I logged into CRM and click on initiate contact
    Then I see updated basic search parameters fields

  @CP-447 @CP-447-02 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of Search Case drop-down options
    Given I logged into CRM and click on initiate contact
    Then I should see following dropdown options for "Search Case" field displayed
      | Internal |
      | Medicaid |
      | CHIP     |

  @CP-447 @CP-447-03 @muhabbat @crm-regression @ui-cc
  Scenario: Verification of Search Consumer drop-down options
    Given I logged into CRM and click on initiate contact
    Then I should see following dropdown options for "Search Consumer" field displayed
      | Internal |
      | Medicaid |
      | CHIP     |


  @CP-447 @CP-447-04 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Validation of boundaries of Case ID and Consumer ID fields
    Given I logged into CRM and click on initiate contact
    And I see "<id>" field accept only 30 characters
    Then I see "<id>" basic search field accepts only alphanumeric characters
    Examples:
      | id         |
      | caseID     |
      | consumerID |


  @CP-447 @CP-447-05 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Verify Search Case/Search Consumer Fields default value
    Given I logged into CRM and click on initiate contact
    Then I see "<field>" field has default "<value>" value captured on basic search
    Examples:
      | field          | value    |
      | searchCase     | Internal |
      | searchConsumer | Internal |

  @CP-447 @CP-447-06 @muhabbat @crm-regression @ui-cc
  Scenario Outline:  Verify Search Case ID/Search Consumer ID Fields default value
    Given I logged into CRM and click on initiate contact
    Then I see "<field>" field has default "<value>" value captured on basic search
    Examples:
      | field      | value                |
      | caseID     | Internal CASE ID     |
      | consumerID | Internal CONSUMER ID |

  @CP-447 @CP-447-07 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Verification the Consumer ID field is populated based on the ID I have selected to search by
    Given I logged into CRM and click on initiate contact
    Then I should see following dropdown options for "Search Consumer" field displayed
      | Medicaid |
    Then I see "consumerID" field has default "<value>" value captured on basic search
    Examples:
      | value                |
      | Medicaid CONSUMER ID |

  @CP-447 @CP-447-07 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Verification the Consumer ID field is populated based on the ID I have selected to search by
    Given I logged into CRM and click on initiate contact
    Then I should see following dropdown options for "Search Consumer" field displayed
      | CHIP |
    Then I see "consumerID" field has default "<value>" value captured on basic search
    Examples:
      | value            |
      | CHIP CONSUMER ID |

  @CP-447 @CP-447-08 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Verification the Case ID field is populated based on the ID I have selected to search by
    Given I logged into CRM and click on initiate contact
    Then I should see following dropdown options for "Search Case" field displayed
      | CHIP |
    Then I see "caseID" field has default "<value>" value captured on basic search
    Examples:
      | value        |
      | CHIP CASE ID |

  @CP-447 @CP-447-08 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Verification the Case ID field is populated based on the ID I have selected to search by
    Given I logged into CRM and click on initiate contact
    Then I should see following dropdown options for "Search Case" field displayed
      | Medicaid |
    Then I see "caseID" field has default "<value>" value captured on basic search
    Examples:
      | value            |
      | Medicaid CASE ID |

  @CP-38661 @CP-38661-1 @beka @crm-regression @ui-cc
  Scenario Outline: Verify search circle is display when search result is loading manual search
    Given I logged into CRM with "Service Account 1" and select a project "<project>"
    And I click on Case Consumer Search page
    When I search for an existing contact by first name "As"
    And I click on Search Button on Search Consumer Page
    Then I verify search circle is display
    Examples:
      | project |
      | BLCRM   |
      | CoverVA |
      | IN-EB   |

  @CP-38661 @CP-38661-2 @beka @crm-regression @ui-cc
  Scenario Outline: Verify search circle is display when search result is loading from active contact search page
    Given I logged into CRM with "Service Account 1" and select a project "<project>"
    And I click on initiate a contact button
    When I search for an existing contact by first name "As"
    And I click on Search Button on Search Consumer Page
    Then I verify search circle is display
    Examples:
      | project |
      | BLCRM   |
      | CoverVA |
      | IN-EB   |