# This feature file has test cases for Add Business Unit functionality
Feature: Tenant Manager - Business Unit Functionality

  @CP-653 @CP-653-01 @CP-1909 @CP-1909-01 @vidya @tm-regression @ui-tm
  Scenario: Verify all fields are displayed and mandatory fields verification
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    Then I verify all the fields are displayed
    And I click on Save button on business unit details page
    Then I see "The Business Unit Name is required and cannot be left blank." message under the "BusinessUnitName" field on business unit details page
    And I see "The Start date is required and cannot be left blank." message under the "StartDate" field on business unit details page

  @CP-653 @CP-653-02 @CP-1909 @CP-1909-02 @vidya @tm-regression @ui-tm
  Scenario: Validate Business Unit Name and Description fields
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    Then I verify business unit name accepts 50 alphanumeric spaces are allowed
    And I verify description accepts 150 alphanumeric spaces are allowed

  @CP-653 @CP-653-03 @CP-1909 @CP-1909-03 @vidya @tm-regression @ui-tm
  Scenario Outline: Validate End Date and Description and task type fields are optional
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    Then verify end date and description are optional
    Examples:
      | businessUnitName | Desc | startDate | endDate |
      | {random}         |[blank]| today     |[blank]|

  @CP-653 @CP-653-04 @CP-1909 @CP-1909-04 @vidya @tm-regression @ui-tm
  Scenario Outline: Verification of Save All changes functionality
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I associate randomly one more TaskType
    And I click on Save button on business unit details page
    Then I verify Business Unit Successfully Created message is displayed and I go back to Business Unit List page
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | today     | today   |
      | {random}         | {random} | today     | +1      |
      | {random}         | {random} | +1        | +1      |
      | {random}         | {random} | +1        | +2      |

  @CP-653 @CP-653-05 @vidya @tm-regression @ui-tm @tm
  Scenario Outline: Verify Business Unit Name must be unique
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    Then I verify Business Unit Successfully Created message is displayed and I go back to Business Unit List page
    When I click on Add button
    When I populate data on business unit details page "<businessUnitName1>","<Desc>","<startDate1>","<endDate1>"
    And I click on Save button on business unit details page
    Then I verify Business Unit Successfully Created message is displayed and I go back to Business Unit List page
    Examples:
      | businessUnitName | Desc     | startDate | endDate | businessUnitName1 | startDate1 | endDate1 |
      | {random}         | {random} | today     | +1      |[blank]| +2         | +2       |

  @CP-653 @CP-653-06 @CP-1909 @CP-1909-05 @vidya @tm-regression @ui-tm
  Scenario Outline: Validate Warning Message
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I select associate task type "General"
    And I click on "<Button>"
    Then I verify Warning message and Warning text is displayed with Continue and Cancel button
    Examples:
      | Button     | businessUnitName | Desc | startDate | endDate |
      | Cancel     | {random}         |[blank]| today     |[blank]|
      | Back Arrow | {random}         |[blank]| today     |[blank]|
      | Team Icon  | {random}         |[blank]| today     |[blank]|

  @CP-653 @CP-653-07 @CP-1909 @CP-1909-06 @vidya @tm-regression @ui-tm
  Scenario Outline: Validate Cancel button, Navigate away and Back Arrow without doing any action
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    And I click on "<Button>"
    Then I verify it should display "<Page>"
    Examples:
      | Button     | Page                    |
      | Cancel     | Business Unit List page |
      | Back Arrow | Business Unit List page |
      | Team Icon  | Team List page          |

  @CP-653 @CP-653-08 @CP-1909 @CP-1909-07 @vidya @tm-regression @ui-tm
  Scenario Outline: Validate cancel button present on warning message
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I select associate task type "General"
    And I click on "<Button>"
    And I click on cancel button on warning message in TM App
    Then I verify it should remain on the same page and information should not save
    Examples:
      | Button     | businessUnitName | Desc | startDate | endDate |
      | Cancel     | {random}         |[blank]| today     |[blank]|
      | Back Arrow | {random}         |[blank]| today     |[blank]|

  @CP-653 @CP-1909 @CP-653-09 @CP-1909-08 @vidya @tm-regression @ui-tm
  Scenario Outline: Validate continue button present on warning message
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I associate randomly one more TaskType
    And I click on "<Button>"
    And I click on continue button on warning message in TM APP
    Then I verify it should display "<Page>"
    Examples:
      | Button     | Page                    | businessUnitName | Desc | startDate | endDate |
      | Cancel     | Business Unit List page | {random}         |[blank]| today     |[blank]|
      | Back Arrow | Business Unit List page | {random}         |[blank]| today     |[blank]|
      | Team Icon  | Team List page          | {random}         |[blank]| today     |[blank]|

  @CP-653 @CP-653-10 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify Business Unit Name must be unique
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    When I click on Add button
    When I populate data on business unit details page "<businessUnitName1>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    Then I see "Business Unit Name must be unique. Please enter a different Name" message under the "BusinessUnitName" field on business unit details page
    Examples:
      | businessUnitName | Desc     | startDate | endDate | businessUnitName1 |
      | {random}         | {random} | today     |[blank]|                   |

  @CP-653 @CP-653-11 @vidya @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Verify Business Unit Name must be unique
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    When I click on Add button
    When I populate data on business unit details page "<businessUnitName1>","<Desc>","<startDate1>","<endDate1>"
    And I click on Save button on business unit details page
    Then I see "Business Unit Name must be unique. Please enter a different Name" message under the "BusinessUnitName" field on business unit details page
    Examples:
      | businessUnitName | Desc     | startDate | endDate | startDate1 | endDate1 | businessUnitName1 |
      | {random}         | {random} | today     | +2      | +1         |[blank]|                   |

  @CP-653 @CP-653-12 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date,end date are non-existing dates
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    Then I see "The date entered does not exist. Please enter a valid date." message under the "StartDate" field on business unit details page
    And I see "The date entered does not exist. Please enter a valid date." message under the "EndDate" field on business unit details page
    Examples:
      | businessUnitName | Desc     | startDate  | endDate    |
      | {random}         | {random} | 02/32/2019 | 13/13/3000 |

  @CP-653 @CP-653-13 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date,end date are invalid
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    Then I see "Invalid date format" message under the "StartDate" field on business unit details page
    And I see "Invalid date format" message under the "EndDate" field on business unit details page
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | 09/02     | 09/02   |

  @CP-653 @CP-653-14 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date and end date both < current date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    Then I see "The Start Date cannot be in the past" message under the "StartDate" field on business unit details page
    Then I see "The end date cannot be in the past" message under the "EndDate" field on business unit details page
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | -1        | -1      |

  @CP-653 @CP-653-15 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date > end date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    Then I see "The End Date must be greater than the Start Date" message under the "EndDate" field on business unit details page
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | +1        | today   |

  @CP-1909 @CP-1909-09 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify associate and de-associate Task Type when creating New Business Unit
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I select multiple associate task type
    And I de-associate a single task type
    And I click on Save button on business unit details page
    Then I verify Business Unit Successfully Created message is displayed and I go back to Business Unit List page
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | today     | +5      |

  @CP-1909 @CP-1909-10 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify creating new Business unit after de-associating all Task Type value
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I select multiple associate task type
    And de-associate all selected Task type
    And I click on Save button on business unit details page
    Then I verify Business Unit Successfully Created message is displayed and I go back to Business Unit List page
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | +2        | +2      |

  @CP-1909 @CP-1909-11 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify associate Task Type when creating New Business Unit
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I select multiple associate task type
    And I click on Save button on business unit details page
    Then I verify Business Unit Successfully Created message is displayed and I go back to Business Unit List page
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | today     | +5      |

  @CP-1909 @CP-1909-12 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify value populated in Task Type dropdown are active and future task type
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Task Type Page
    And I select a task type of active and future status
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Associate Task Type dropdown
    Then I should see the dropdown values are displayed based on active and future task type values
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | today     | +5      |

#Feature: Tenant Manager Business Unit List

  @CP-799 @CP-799-01 @vidya @tm-regression @ui-tm
  Scenario: Verification of Business Unit List columns header name
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    Then I verify all columns headers and add button and back arrow are displayed

  @CP-799 @CP-799-02 @vidya @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Verification of Business Unit List columns Values
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    And I navigate to newly created business unit
    Then I verify Business Unit List columns Values
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | today     | today   |

  @CP-799 @CP-799-03 @vidya @tm-regression @ui-tm
  Scenario Outline: Verification of description and end date fields are optional
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    And I navigate to newly created business unit
    Then I verify Business Unit List columns Values
    Examples:
      | businessUnitName | Desc | startDate | endDate |
      | {random}         |[blank]| today     |[blank]|

  @CP-799 @CP-799-04 @vidya @tm-regression @ui-tm
  Scenario Outline: Verification of Business Unit Status value
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I click on Save button on business unit details page
    And I navigate to newly created business unit
    Then I verify business unit list status as "<status>"
    Examples:
      | businessUnitName | Desc     | startDate | endDate | status |
      | {random}         | {random} | today     |[blank]| Active |
      | {random}         | {random} | today     | today   | Active |
      | {random}         | {random} | today     | +2      | Active |
      | {random}         | {random} | +1        | +1      | FUTURE |
      | {random}         | {random} | +2        |[blank]| FUTURE |
      | {random}         | {random} | +1        | +2      | FUTURE |

  @CP-799 @CP-799-05 @vidya @tm-regression @ui-tm
  Scenario Outline: Validate Cancel button, Navigate away and Back Arrow without doing any action
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I click on "<Button>" on business unit list page
    Then I verify it is display "<Page>"
    Examples:
      | Button     | Page                 |
      | Back Arrow | Project Details Page |
      | Team Icon  | Team List page       |

  @CP-799 @CP-799-06 @vidya @tm-regression @ui-tm
  Scenario: Verification of Business Unit List sorting order
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has 5 business units, if not, I add 5 business units
    Then I verify business unit records are in sorted order

#Feature: Tenant Manager View Business Unit

  #AC 1.0
  @CP-2183 @CP-2183-01 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify should I navigate to business unit view page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    And I will click on business unit name
    Then I should navigated to business unit details view page
    Examples:
      | buName   | buDesc   | buStartDate | buEndDate |
      | {random} | {random} | today       | +5        |

    #AC 1.0
  @CP-2183 @CP-2183-02 @vidya @tm-regression @ui-tm @tm-smoke
  Scenario Outline:Verify all fields displayed in business unit view page are read only type
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    And I will click on business unit name
    Then I should see all the fields in ready only type in business unit view page
    Examples:
      | buName   | buDesc   | buStartDate | buEndDate |
      | {random} | {random} | today       | +5        |

    #AC 2.0
  @CP-2183 @CP-2183-03 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify Edit button is displayed in business unit vie page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    And I will click on business unit name
    Then I should see edit button in business unit view page
    Examples:
      | buName   | buDesc   | buStartDate | buEndDate |
      | {random} | {random} | today       | +5        |

    #AC 3.0
  @CP-2183 @CP-2183-04 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify status displayed in business unit view page is as per business unit list page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    And I will click on business unit name
    Then I should see "<status>" in business unit view page
    Examples:
      | buName   | buDesc   | buStartDate | buEndDate | status |
      | {random} | {random} | today       | +5        | ACTIVE |
      | {random} | {random} | +1          | +5        | FUTURE |

    #AC 3.0
  @CP-2183 @CP-2183-05 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify INACTIVE status in business unit view page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit with status Inactive
    And I will click on business unit name
    Then I should see "<status>" in business unit view page
    Examples:
      | status   |
      | INACTIVE |

    #Adding CP-3088 test scripts below By:-Vidya, Date:-23/10/2019
    #AC 1.0
  @CP-3088 @CP-3088-01 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify View Associated Task Type(s)
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I select associate task type "General"
    And I click on Save button on business unit details page
    And I will click on business unit name
    Then I should navigated to business unit details view page
    And I verify Associated Task Type section has task type "General"
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | today     | today   |

    #AC 2.0
  @CP-3088 @CP-3088-02 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify View Permission Group for each Task Type via Hover Over
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit details page
    When I populate data on business unit details page "<businessUnitName>","<Desc>","<startDate>","<endDate>"
    And I select associate task type "General"
    And I click on Save button on business unit details page
    And I will click on business unit name
    When I hover my mouse over "General" Task Type
    Then Verify should I see the associated Permission Groups configured for that task types
    Examples:
      | businessUnitName | Desc     | startDate | endDate |
      | {random}         | {random} | today     | today   |

  @CP-3089 @CP-3089-01 @paramita @tm-regression @ui-tm
  Scenario Outline: Verification of Associated Team(s) section in Business Unit detail page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    And I select newly created business unit
    Then I verify Associated Team section in Business Unit detail page
    Examples:
      | buName   | buDesc | buStartDate | buEndDate |
      | {random} |[blank]| today       | +5        |

  @CP-3089 @CP-3089-02 @paramita @tm-regression @ui-tm
  Scenario Outline:Validate Team associated with a business unit
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    Then I check for a team associate with business unit "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         |[blank]|        |

  @CP-3089 @CP-3089-03 @paramita @tm-regression @ui-tm
  Scenario Outline:Validate Team Start and End date of a associated Team on mouse hover
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check for a team associate with business unit "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I verify Team Start and End Date on mouse hover over associated Team status
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         |[blank]|        |

  @CP-3089 @CP-3089-04 @paramita @tm-regression @ui-tm @tm-smoke
  Scenario:Validate User photo icon for multiple associated team users
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I add multiple users for a team and select a business unit from business unit list page
    Then I verify user photo is displayed for multiple associated team users

  @CP-3089 @CP-3089-05 @paramita @tm-regression @ui-tm @tm-smoke
  Scenario:Validate "+" icon is displayed for more than 7 user and display username on click in business unit details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I add multiple users for a team and select a business unit from business unit list page
    Then I see plus icon is displayed for more than 7 user in Associated section
    And I see username on click plus icon

  @CP-3089 @CP-3089-06 @paramita @tm-regression @ui-tm
  Scenario Outline:Validate color code based on active status of associated team users in business unit details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I add teams with multiple users with "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>" and select a business unit from business unit list page
    Then I verify the color code of the associated Team based on "<status>" status
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} | {random} | today         |[blank]|        | ACTIVE |

  @CP-3089 @CP-3089-07 @paramita @tm-regression @ui-tm
  Scenario:Validate first and last name of the associated team users on click plus icon in business unit details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    Then I see first and last name of the associated team users on click plus icon in business unit page

  @CP-3089 @CP-3089-08 @paramita @tm-regression @ui-tm
  Scenario Outline:Validate color code based on future status of associated team users in business unit details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I add teams with multiple users with "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>" and select a business unit from business unit list page
    Then I verify the color code of the associated Team based on "<status>" status
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} | {random} | +5            |[blank]|        | FUTURE |

  @CP-3089 @CP-3089-9 @paramita @tm-regression @ui-tm
  Scenario Outline:Validate active teams displaying in first position in business unit details page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check for a team associate with business unit "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I verify  active teams displaying in first position
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         |[blank]|        |

#Feature: Tenant Manager Edit Business Unit

  @CP-1683 @CP-1683-01 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify Business Unit Name, Start Date fields are read-only In BU Edit screen for ACTIVE Business Unit record
    Given I have logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to business unit list page
    When I select "<status>" BU record or populate new BU record with "<businessUnitName>","<Desc>","<startDate>","<endDate>" if record not exists
    And I click on Edit button
    Then I verify BU Name and Start Date fields are in read only mode
    Examples:
      |status|businessUnitName|Desc     |startDate|endDate|
      |ACTIVE|{random}        |{random}  |today    |today  |

  @CP-1683 @CP-1683-02 @paramita @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Verify Description , End date , TaskType field is editable In BU Edit screen for ACTIVE  Business Unit record
    Given I have logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to business unit list page
    When I select "<status>" BU record or populate new BU record with "<businessUnitName>","<Desc>","<startDate>","<endDate>" if record not exists
    And I click on Edit button
    And I edit "<updateDesc>","<updateEndDate>" field
    And I associate one more TaskType or de-associate Tasktype field
    Then I see BU record get updated succesfully and navigate to Businesss Unit list page
    Examples:
      |status|businessUnitName|Desc      |startDate|endDate|updateDesc        |updateEndDate|
      |ACTIVE|{random}        |{random}  |today    |today  |{random}          |+2           |

  @CP-1683 @CP-1683-03  @paramita @tm-regression @ui-tm
  Scenario Outline: Validate alphanumeric 150 character for Description field in BU Edit screen
    Given I have logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to business unit list page
    When I select "<status>" BU record or populate new BU record with "<businessUnitName>","<Desc>","<startDate>","<endDate>" if record not exists
    And I click on Edit button
    Then I verify description field accepts 150 alphanumeric character and spaces are allowed
    Examples:
      |status|businessUnitName|Desc      |startDate|endDate|
      |ACTIVE|{random}        |{random}  |today    |+2     |

  @CP-1683 @CP-1683-04 @paramita @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Verify All fields are editable In BU Edit screen for FUTURE Business Unit record
    Given I have logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to business unit list page
    When I select "<status>" BU record or populate new BU record with "<businessUnitName>","<Desc>","<startDate>","<endDate>" if record not exists
    And I click on Edit button
    And I edit "<updateBusinessUnitName>","<updateDesc>","<updateStartDate>","<updateEndDate>" fields
    And I associate one more TaskType or de-associate Tasktype field
    Then I see BU record get updated succesfully and navigate to Businesss Unit list page
    Examples:
      |status|businessUnitName|Desc      |startDate|endDate|updateBusinessUnitName|updateDesc        |updateStartDate|updateEndDate|
      |FUTURE|{random}        |{random}  |+2       |+3     |{random}             |{random}           |+2             |+3           |

  @CP-1683 @CP-1683-05 @paramita @tm-regression @ui-tm
  Scenario Outline: Validate End Date and Description are optional in BU edit screen
    Given I have logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to business unit list page
    When I select "<status>" BU record or populate new BU record with "<businessUnitName>","<Desc>","<startDate>","<endDate>" if record not exists
    And I click on Edit button
    And I clear "<updateEndDate>","<updateDesc>" field
    Then I see BU record get updated succesfully and navigate to Businesss Unit list page
    Examples:
      |status|businessUnitName|Desc      |startDate|endDate|updateDesc        |updateEndDate|
      |FUTURE|{random}        |{random}  |+2       |+3     |[blank]|             |

  @CP-1683 @CP-1683-06 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify Description , End date , TaskType field is editable In BU Edit screen for INACTIVE Business Unit record
    Given I have logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to business unit list page
    Then I edit "<updateDesc>","<updateEndDate>",TaskType field of "<status>" BU record
    Examples:
      |updateDesc        |updateEndDate|status  |
      |{random}          |+2           |INACTIVE|