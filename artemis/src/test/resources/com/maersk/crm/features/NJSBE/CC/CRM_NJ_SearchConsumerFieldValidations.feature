Feature: Consumer Search screens fields validations

  @CP-11276 @CP-11276-09 @ui-cc-nj @nj-regression @muhabbat
  Scenario Outline: Verify Active Contact Search GI Case Fields default value
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I see "<field>" field has default "<value>" value captured on basic search
    Examples:
      | field      | value              |
      | searchCase | GetInsured         |
      | caseID     | GetInsured CASE ID |

  @CP-11276 @CP-11276-10 @ui-cc-nj @nj-regression @muhabbat
  Scenario Outline: Verify Manual Search GI Case Fields default value
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Manual Consumer search page
    Then I see "<field>" field has default "<value>" value captured on basic search
    Examples:
      | field      | value              |
      | searchCase | GetInsured         |
      | caseID     | GetInsured CASE ID |


  @CP-11276 @CP-11276-11 @ui-cc-nj @nj-regression @muhabbat
  Scenario Outline: NJ-SBE: Active Contact case consumer search - fields validations
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    And I click on advance search icon
    And I search for consumer have First Name as "<FIRST NAME>" and Last Name as "<LAST NAME>"
    When I search for an existing contact by SSN "<SSN>"
    And I search for consumer by Phone and enter "<PHONE>"
    And I click on Search Button on Search Consumer Page
    Then I see error message for "<fieldName>" with <MINChar>
    Examples:
      | fieldName | FIRST NAME | LAST NAME | SSN | PHONE | MINChar |
      | FIRST NAME | Ll         |           |     |       | 3       |
      | LAST NAME |            | Jj        |     |       | 3       |
      | SSN       |            |           | 777 |       | 4       |
      | PHONE     |            |           |     | 77777 | 10      |

  @CP-11276 @CP-11276-12 @ui-cc-nj @nj-regression @muhabbat
  Scenario Outline: NJ-SBE: Manual case consumer search - fields validations
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Manual Consumer search page
    And I click on advance search icon
    And I search for consumer have First Name as "<FIRST NAME>" and Last Name as "<LAST NAME>"
    When I search for an existing contact by SSN "<SSN>"
    And I search for consumer by Phone and enter "<PHONE>"
    And I click on Search Button on Search Consumer Page
    Then I see error message for "<fieldName>" with <MINChar>
    Examples:
      | fieldName  | FIRST NAME | LAST NAME | SSN | PHONE | MINChar |
      | FIRST NAME | Ll         |           |     |       | 3       |
      | LAST NAME  |            | Jj        |     |       | 3       |
      | SSN        |            |           | 777 |       | 4       |
      | PHONE      |            |           |     | 77777 | 10      |


  @CP-11276 @CP-11276-13 @ui-cc-nj @nj-regression @muhabbat
  Scenario: NJ-SBE: Active Contact case consumer search - DOB filed validations
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    And I search for an existing contact by DOB "09"
    And I click on Search Button on Search Consumer Page
    Then I invalid format date error message for "DOB"

  @CP-11276 @CP-11276-14 @ui-cc-nj @nj-regression @muhabbat
  Scenario: NJ-SBE: Manual case consumer search - DOB filed validations
    Given I logged into CRM and select a project "NJ-SBE"
    When  I navigate to Manual Consumer search page
    And I search for an existing contact by DOB "09"
    And I click on Search Button on Search Consumer Page
    Then I invalid format date error message for "DOB"