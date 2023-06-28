Feature: Tenant Manager ENUM Details
  #muting due creating random test in lookup

  #@CP-1359-01 @CP-1359 @sean @tm-regression @ui-aux
  Scenario: 1.0 Select Database
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-consumer" database

  #@CP-1359-02 @CP-1359 @sean @tm-regression @ui-aux
  Scenario: 2. Select Look up table
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-consumer" database
    Then I verify the following options displayed for Enum field
      | ENUM_ACCESS_TYPE             |
      | ENUM_CASE_ID_NUMBER_TYPE     |
      | ENUM_CASE_SEARCH_BY          |
      | ENUM_CASE_STATUS             |
      |ENUM_CITIZENSHIP              |
      | ENUM_CONSUMER_CONSENT_TYPE   |
      | ENUM_CONSUMER_ID_NUMBER_TYPE |
      | ENUM_CONSUMER_RELATIONSHIP   |
      | ENUM_CONSUMER_ROLE_TYPE      |
      | ENUM_CONSUMER_SEARCH_BY      |
      | ENUM_CONSUMER_STATUS         |
      | ENUM_CONSUMER_SUFFIX         |
      | ENUM_CONSUMER_TYPE           |
      | ENUM_ETHINIC_CODE            |
      | ENUM_GENDER_TYPE             |
      | ENUM_HOW_CONSENT_PROVIDED    |
      | ENUM_INCIDENCE_TYPE          |
      | ENUM_LANGUAGE                |
      | ENUM_LINK_REF_TYPE           |
      | ENUM_NOTICE_TYPE             |
      | ENUM_PREFERENCE_SELECTION    |
      | ENUM_PREFERENCE_TYPE         |
      | ENUM_PROGRAM_NAME            |
      | ENUM_RACE_CODE               |
      | ENUM_US_RESIDENTIAL_CODE     |

  #@CP-1359-03 @CP-1359 @sean @tm-regression @ui-aux
  Scenario Outline: 2.1 Display existing Look up table records
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-consumer" database
    When I select any ENUM "<Enums>"  table
    When Click at search button
    Then the existing records for that ENUM are displayed in a grid with the following fields populated:
      | VALUE            |
      | DESCRIPTION      |
      | REPORT LABEL     |
      | SCOPE            |
      | ORDER BY DEFAULT |
      | START DATE       |
      | END DATE         |
      |[blank]|
    Examples:
      | Enums    |
      |  {random} |

  #@CP-1359-04 @CP-1359 @sean @tm-regression @ui-aux
  Scenario Outline: Populate the ENUM record
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-consumer" database
    When I select any ENUM "<Enums>"  table
    When Click at search button
    When Click at add lookup button
    Then Validate the all fields on the ENUM by selecting the ‘Add Look up’ button
    Examples:
      | Enums    |
      | {random} |

  #@CP-1359-05 @CP-1359 @sean @tm-regression
  Scenario Outline: 3.1 Disable ‘Add Look up’ button
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-consumer" database
    When I select any ENUM "<Enums>"  table
    When Click at search button
    When Click at add lookup button
    Then the ‘Add Look up’ button becomes disabled while I am performing data entry
    Examples:
      | Enums    |
      | {random} |

 #@CP-1359-06 @CP-1359 @sean @tm-regression @ui-aux
  Scenario Outline: Save the ENUM record & re-enable Add Look up button
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-consumer" database
    When I select any ENUM "<Enums>"  table
    When Click at search button
    When Click at add lookup button
    Then I enter "<Value>", "<Description>","<ReportLabel>" and "<StartDate>"
    When Click at add lookup record button
    Then Then the "Look Up Value Successfully Created" Success Message is displayed
    When Click to close dialog box
    And Verify that lookup button is enabled again
    Examples:
      | Enums    | Value    | Description | ReportLabel | StartDate |
      | {random} | {random} | {random}    | {random}    | today     |

  #@CP-1359-07 @CP-1359 @sean @tm-regression @ui-aux
  Scenario Outline: 4.2 Missing Required Info - Error Message
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-consumer" database
    When I select any ENUM "<Enums>"  table
    When Click at search button
    When Click at add lookup button
    When Click at add lookup record button
    Then the following required fields error messages should appear
      | Value is required and cannot be left blank.             |
      | Description is required and cannot be left blank.       |
      | Report Label is required and cannot be left blank.      |
      | The Selected date is required and cannot be left blank. |
    Examples:
      | Enums    | Value    | Description | ReportLabel | StartDate |
      | {random} | {random} | {random}    | {random}    | today     |

  #@CP-1359-08 @CP-1359 @sean @tm-regression @ui-aux
  Scenario Outline: 5.1 Cancel - unsaved data entry
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-consumer" database
    When I select any ENUM "<Enums>"  table
    When Click at search button
    When Click at add lookup button
    Then I enter "<Value>", "<Description>","<ReportLabel>" and "<StartDate>"
    When Click at clear button
    And Verify that lookup button is enabled again
    Examples:
      | Enums    | Value    | Description | ReportLabel | StartDate |
      | {random} | {random} | {random}    | {random}    | today     |

 #@CP-1359-09 @CP-1359 @sean @tm-regression @ui-aux
  Scenario Outline: 6.0. Back Arrow - No unsaved data
    # warning alert is showing and it should not appear w.r.t test scenario
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-consumer" database
    When I select any ENUM "<Enums>"  table
    When Click at search button
    #When Click at add lookup button
    #Then I enter "<Value>", "<Description>","<ReportLabel>" and "<StartDate>"
    When Click at Back arrow
    And Verify that user is navigated to project detail page
    Examples:
      | Enums    | Value    | Description | ReportLabel | StartDate |
      | {random} | {random} | {random}    | {random}    | +2        |

  #@CP-1359-10 @CP-1359 @sean @tm-regression @ui-aux
  Scenario Outline: Navigate Away - No unsaved data
    # warning alert is showing and it should not appear w.r.t test scenario
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-consumer" database
    When I select any ENUM "<Enums>"  table
    When Click at search button
    #When Click at add lookup button
    #Then I enter "<Value>", "<Description>","<ReportLabel>" and "<StartDate>"
    When I choose to Navigate away and I do not have unsaved selections
    Then I am navigated wherever I chose to Navigate and am not presented a warning message
    Examples:
      | Enums    | Value    | Description | ReportLabel | StartDate |
      | {random} | {random} | {random}    | {random}    | today     |