# This feature file has test cases for Add Team functionality

Feature: Tenant Manager - Teams Functionality

  #AC 1.0 and 5.0
  @CP-739 @CP-739-01 @CP-3559 @CP-3559-01 @vidya @tm-regression @ui-tm
  Scenario: Verify all fields are displayed and mandatory fields verification
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team details page
    Then I verify all the fields are displayed in team details page
    And I click on Save button on team details page
    Then I see "The Team Name is required and cannot be left blank." message under the "TeamName" field on team details page
    And I see "The Business Unit Name is required and cannot be left blank." message under the "BusinessUnit" field on team details page
    And I see "The Start date is required and cannot be left blank." message under the "StartDate" field on team details page

    #AC 2.0
  @CP-739 @CP-739-02 @vidya @tm-regression @ui-tm
  Scenario: Validate Team Name and Description fields
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team details page
    Then I verify team name accepts 50 alphanumeric spaces are allowed
    And I verify team description accepts 150 alphanumeric spaces are allowed

    #AC 2.0
  @CP-739 @CP-739-03 @vidya @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Validate End Date and Description fields are optional
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on Save button on team details page
    Then I verify end date and description are optional in team details page
    Examples:
      | buName   | buDesc | buStartDate | buEndDate | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} |[blank]| today       | +5        | {random} |[blank]| today         |[blank]|        |

    #AC 2.1 and 3.0
  @CP-739 @CP-739-04 @CP-3559 @CP-3559-02 @vidya @tm-regression @ui-tm
  Scenario Outline: Verification of Save All changes functionality
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on Save button on team details page
    Then I verify Team Successfully Created message is displayed and I go back to Team List page
    And I verify Team Details section displays as Read Only
    And I verify the "<status>" for the Team is displayed
    And I verify Edit Team button is displayed
    And I verify Add User button on User List section is enabled
    Examples:
      | buName   | buDesc | buStartDate | buEndDate | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} |[blank]| today       | +5        | {random} | {random} | today         | today       |[blank]| ACTIVE |
      | {random} |[blank]| today       | +5        | {random} | {random} | +2            | +3          |[blank]| FUTURE |
      | {random} |[blank]| +1          | +5        | {random} | {random} | +1            | +1          |[blank]| FUTURE |
      | {random} |[blank]| +1          | +5        | {random} | {random} | +2            | +3          |[blank]| FUTURE |
      | {random} |[blank]| +1          | +5        | {random} |[blank]| +4            | +5          |[blank]| FUTURE |


  #AC 2.0
  @CP-739 @CP-739-05 @vidya @tm-regression @ui-tm
  Scenario: Validate Business Unit dropdown Box value
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has 5 business units, if not, I add 5 business units
    And I will take all business unit which has End Date null or => Current Date
    And I navigate to team details page
    Then I verify business unit dropdown box values are sorted

  #AC 6.0
  @CP-739 @CP-739-06 @vidya @tm-regression @ui-tm
  Scenario Outline: Validate Warning Message
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on "<Button>" on team details page
    Then I verify Warning message and Warning text is displayed with Continue and Cancel button on team details page
    Examples:
      | Button     | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | Cancel     | {random} |[blank]| today         |[blank]| null   |
      | Back Arrow | {random} |[blank]| today         |[blank]| null   |
      | BU Icon    | {random} |[blank]| today         |[blank]| null   |

    #AC 6.1
  @CP-739 @CP-739-07 @vidya @tm-regression @ui-tm
  Scenario Outline: Validate Cancel button, Navigate away and Back Arrow without doing any action
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team details page
    And I click on "<Button>" on team details page
    Then I verify it should display "<Page>" page
    Examples:
      | Button     | Page                    |
      | Cancel     | Team List page          |
      | Back Arrow | Team List page          |
      | BU Icon    | Business Unit List page |

    #AC 6.0
  @CP-739 @CP-739-08 @vidya @tm-regression @ui-tm
  Scenario Outline: Validate cancel button present on warning message
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on "<Button>" on team details page
    And I click on cancel button on warning message of team details page
    Then I verify it should remain on the team details page and information should not save
    Examples:
      | Button     | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | Cancel     | {random} |[blank]| today         |[blank]| null   |
      | Back Arrow | {random} |[blank]| today         |[blank]| null   |
      | BU Icon    | {random} |[blank]| today         |[blank]| null   |

    #AC 6.0
  @CP-739 @CP-739-09 @vidya @tm-regression @ui-tm
  Scenario Outline: Validate continue button present on warning message
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on "<Button>" on team details page
    And I click on continue button on warning message of team details page
    Then I verify it should display "<Page>" page
    Examples:
      | Button     | Page                    | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | Cancel     | Team List page          | {random} |[blank]| today         |[blank]| null   |
      | Back Arrow | Team List page          | {random} |[blank]| today         |[blank]| null   |
      | BU Icon    | Business Uint List page | {random} |[blank]| today         |[blank]| null   |

  @CP-739 @CP-739-10 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date,end date are non-existing dates
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on Save button on team details page
    Then I see "The date entered does not exist. Please enter a valid date." message under the "StartDate" field on team details page
    And I see "The date entered does not exist. Please enter a valid date." message under the "EndDate" field on team details page
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | 02/32/2019    | 13/13/3000  | null   |

  @CP-739 @CP-739-11 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date,end date are invalid
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on Save button on team details page
    Then I see "Invalid date format" message under the "StartDate" field on team details page
    And I see "Invalid date format" message under the "EndDate" field on team details page
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | 09/02         | 09/02       | null   |

  @CP-739 @CP-739-12 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when start date and end date both < current date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on Save button on team details page
    Then I see "The Start Date cannot be in the past" message under the "StartDate" field on team details page
    Then I see "The end date cannot be in the past" message under the "EndDate" field on team details page
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | -1            | -1          | null   |

  @CP-739 @CP-739-13 @vidya @tm-regression @ui-tm
  Scenario Outline: Verify error displayed when end date > Bu end date and start date < BU start date
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on Save button on team details page
    Then I see "Start date of Team should be greater than or equal to Corresponding Business Unit Start Date" message under the "StartDate" field on team details page
    Then I see "End date of Team should be lesser than or equal to Corresponding Business Unit End Date" message under the "EndDate" field on team details page
    Examples:
      | buName   | buDesc | buStartDate | buEndDate | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} |[blank]| +1          | +5        | {random} |[blank]| today         | +6          |[blank]|

  @CP-3559 @CP-3559-03 @vidya @tm-regression @ui-tm
  Scenario: Verify Add User Button in the User List section is disabled
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team details page
    Then I verify Add User Button in the User List section is disabled

  @CP-1684 @CP-1684-01 @paramita @tm-regression @ui-tm
  Scenario Outline: Verification of available fields on select Add User button in User Details Screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    Then I verify all the fields are displayed in User details screen
    Examples:
      | buName   | buDesc | buStartDate | buEndDate | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} |[blank]| today       | +5        | {random} | {random} | today         |[blank]|        | ACTIVE |


  @CP-1684 @CP-1684-02 @paramita @tm-regression @ui-tm
  Scenario Outline: Validate autopopulate other fields on select single field in team userlist fields
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    Then I select any dropdown "<field>" value other remaining fields get autopopulated
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status | field      |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE | User Name  |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE | Email      |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE | maersk ID |


  @CP-1684 @CP-1684-03 @paramita @tm-regression @ui-tm
  Scenario Outline: Associate single user to the Team
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    And I select any dropdown value other remaining fields get autopopulated
    And I click on team user save button
    Then I see single user is associated with a Team
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE |

  @CP-1684 @CP-1684-04 @paramita @tm-regression @ui-tm
  Scenario Outline: Associate multiple user to the Team
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    And I select any dropdown value other remaining fields get autopopulated
    When I click on Add User button second time
    And I select any dropdown value other remaining fields get autopopulated
    And I click on team user save button
    Then I see multiple user is associated with a Team
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE |

  @CP-1684 @CP-1684-05 @paramita @tm-regression @ui-tm
  Scenario: Validate empty user record is not saved in Team User screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I click on User List Menu
    And I check whether it has 5 users, if not, I add users
    And I select any team value from team userlist
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    And I click on team user save button without adding data
    Then I see empty record is not saved


  @CP-1684 @CP-1684-06 @paramita @tm-regression @ui-tm
  Scenario Outline: Validate team user dropdown fields value are sorted in alphabetical order
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    Then I verify "<field>" dropdown box values are sorted alphabetically in user list section
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status | field      |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE | User Name  |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE | Email      |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE | maersk ID |

#Feature: Tenant Manager Add Team User

  @CP-3564 @CP-3564-01 @paramita @tm-regression @ui-tm
  Scenario Outline: Validate Cancel record before Save in Team User list section
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    And I select any dropdown value other remaining fields get autopopulated
    And I click on X icon on the record level
    Then I see the record is cancelled
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE |

  @CP-3564 @CP-3564-02 @paramita @tm-regression @ui-tm
  Scenario Outline: Validate Cancel record do not get saved in Team User list section
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    And I select any dropdown value other remaining fields get autopopulated
    When I click on Add User button second time
    And I select any dropdown value other remaining fields get autopopulated
    And I click on X icon on the record level
    And I click on team user save button
    Then I see single user is associated with a Team
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE |

  @CP-3564 @CP-3564-03 @paramita @tm-regression @ui-tm
  Scenario Outline: Cancel button functionality on Team User details page when some data is entered
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    And I select any dropdown value other remaining fields get autopopulated
    And I click on Cancel button on Team User Page
    And I see "Your changes will not be saved" alert message displayed
    When I click on Cancel option and I am navigated back to Team User page and see all previously entered unsaved data
    And I click on Cancel button on Team User Page
    And I see "Your changes will not be saved" alert message displayed
    Then I click on Continue option and navigated back to any selected List Page
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE |

  @CP-3564 @CP-3564-04 @paramita @tm-regression @ui-tm
  Scenario Outline: Validate Back arrow functionality on Team User details page when some data is entered
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    When I click on Add User button
    And I select any dropdown value other remaining fields get autopopulated
    When I click on Back arrow on Team User Page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    When I click on Cancel option and I am navigated back to Team User page and see all previously entered unsaved data
    When I click on Back arrow on Team User Page
    And I see "If you navigate away, your information will not be saved" alert message displayed
    Then I click on Continue option and I am navigated back to Team User List Page
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} | {random} | today         | +6          |[blank]| ACTIVE |

  #Feature: Edit a Team and associate and disassociate users to/from an existing Team

  @CP-1998-01.0 @Umid @tm-regression @ui-tm
  Scenario: Edit Team - Future
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to the TEAM Section of the content
    When I select the Edit button on the for a "FUTURE" Team
    Then the following fields become editable "TeamName" "BusinessUnit" "startDate" "endDate" "Description"

  @CP-1998-02.0 @Umid @tm-regression @ui-tm
  Scenario: Edit Team - ACTIVE update
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to the TEAM Section of the content
    When I select the Edit button on the for a "ACTIVE" Team
    Then the following fields become editable "TeamName" "endDate" "Description"

  @CP-1998-02.1 @Umid @tm-regression @ui-tm
  Scenario: Edit Team - INACTIVE update
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to the TEAM Section of the content
    When I select the Edit button on the for a "INACTIVE" Team
    Then the following fields become editable "TeamName" "Description" "endDate"

  @CP-1998-03.0 @Umid @tm-regression @ui-tm
  Scenario: Edit Team - Required Fields
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to the TEAM Section of the content
    When I click on "add" button
    And I click on "Save" button
    Then the system does not save, and displays "The Team Name is required and cannot be left blank."
    And the system does not save, and displays "The Business Unit Name is required and cannot be left blank."
    And the system does not save, and displays "The Start Date is required and cannot be left blank."

  @CP-1998-04.0 @Umid @tm-regression @ui-tm
  Scenario: Edit Team - Cancel Away edit Warning message
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to the TEAM Section of the content
    When I select the Edit button on the for a "ACTIVE" Team
    And I click on "Cancel" button
    Then I will be navigated back to team units
    When I select the Edit button on the for a "ACTIVE" Team
    Then the following fields become editable "TeamName" "endDate" "Description"
    When I click on "Cancel" button
    Then I will receive a Warning Message: "If you navigate away, your information will not be saved"
    # changed message "If you continue all information captured will be lost"

  #@CP-1998-04.1 @Umid @tm-regression @ui-tm
  #Covered in above script CP-1998-04.1
  Scenario: Edit Team - Cancel Away edit not change values
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to the TEAM Section of the content
    When I select the Edit button on the for a "ACTIVE" Team
    And I click on "Cancel" button
    Then I will be navigated back to team units

  @CP-1998-05.0 @Umid @tm-regression @ui-tm
  Scenario: Edit Team - Future Successfully Saves verify data on UI side
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to the TEAM Section of the content
    When I select the Edit button on the for a "ACTIVE" Team
    Then the following fields become editable "TeamName" "Description"
    And I click on save button and it successfully saves
#    And I will be navigated back to team units
    And Data that was given is saved

  @CP-1998-06.0 @events @Umid
  Scenario: Edit Team - Future Successfully Saves verify data on UI side
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to the TEAM Section of the content
    When I select the Edit button on the for a "ACTIVE" Team
    Then the following fields become editable "TeamName" "Description"
    And I click on save button and it successfully saves
    And I will be navigated back to team units
    And Data that was given is saved
    And I search "TEAM_UPDATE_EVENT" in the Get Events endpoint for TM
    Then I should see "TEAM_UPDATE_EVENT" Event was created

  @CP-1998-07.0 @events @Umid
  Scenario: Edit Team - Future Successfully Saves verify data on UI side
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to the TEAM Section of the content
    When I select the Edit button on the for a "FUTURE" Team
    Then the following fields become editable "TeamName" "BusinessUnit" "startDate" "endDate" "Description"
    And I click on save button and it successfully saves
    And I will be navigated back to team units
    And Data that was given is saved
    And I search "TEAM_UPDATE_EVENT" in the Get Events endpoint for TM
    Then I should see "TEAM_UPDATE_EVENT" Event was created

#Feature: Tenant Manager Team View Details

  #AC 1.0 - View Team fields in Read only State
  @CP-2182 @CP-2182-01 @CP-2182-02 @CP-2182-06 @CP-2182-07 @CP-2182-08 @CP-2182-09 @CP-2182-11 @CP-2182-12 @paramita @tm-regression @ui-tm @tm-smoke
  Scenario Outline:Verify all fields displayed in Team view page are read only type, edit button displayed
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    #When I navigate to business unit list  page
    #And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    When I click on Team icon from left menu bar
    And I navigate to team "RegressionBaseline"
    #And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I should see all the fields as Ready only in Team view page
    And I should see edit button in Team view page
    And I verify fields of each team user contact records are ready only
    And I verify color contact card of Supervisor in View Team screen
    And I verify color contact card of Non Supervisor in View Team screen
    And I verify Supervisor User is listed first
    And I verify Add User button on User List section is enabled
    And I should see Back arrow icon in Team view page and navigate to team list page on click Back button
    Examples:
      | buName   | buDesc | buStartDate | buEndDate | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} |[blank]| today       | +5        | {random} | {random} | today         |[blank]|        |

  #AC 4.0 - Edit button in View Team screen
  #@CP-2182 @CP-2182-02 @paramita @tm-regression @ui-tm
  #Merging into above script to avoid lot of data
  Scenario Outline:Verify Edit button displayed in Team view page screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I should see edit button in Team view page
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         | +6          |[blank]|

  #AC 6.0 Active Status verification in View Team screen
  @CP-2182 @CP-2182-03 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify status displayed in business unit view page is as per business unit list page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I should see "<status>" in Team view page
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} | {random} | today         |[blank]|        | ACTIVE |

  #AC 6.1 Future Status verification in View Team screen
  @CP-2182 @CP-2182-04 @paramit @tm-regression @ui-tm
  Scenario Outline: Verify FUTURE  status in Team view page
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value with status Future , if not will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I should see "<status>" in Team view page
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU | status |
      | {random} | {random} | +5            |[blank]|        | FUTURE |

  #AC 6.1 Inactive Status verification in View Team screen
  @CP-2182 @CP-2182-05 @paramita @tm-regression @ui-tm
  Scenario Outline: Verify INACTIVE status in Team view page
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value with status Inactive
    Then I should see "<status>" in Team view page
    Examples:
      | status   |
      | INACTIVE |

  #AC 7.0 Back Arrow verification in View Team screen
  #@CP-2182 @CP-2182-06 @paramita @tm-regression @ui-tm
  #Merging into above script to avoid lot of data
  Scenario Outline: Verify Back Arrow verification in View Team screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I should see Back arrow icon in Team view page and navigate to team list page on click Back button
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         |[blank]|        |

  #AC 3.0 Validate user records are in Read only state in View Team screen
  #@CP-2182 @CP-2182-07 @paramita @tm-regression @ui-tm
  #Merging into above script to avoid lot of data
  Scenario Outline: Verify fields of each user contact record card in ready only
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify fields of each team user contact records are ready only
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         |[blank]|        |

  #AC 3.1 Validate Red color contact card of Supervisor users in View Team screen
  #@CP-2182 @CP-2182-08 @paramita @tm-regression @ui-tm
  #Merging into above script to avoid lot of data
  Scenario Outline: Validate color contact card of Supervisor users in View Team screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I verify color contact card of Supervisor in View Team screen
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         |[blank]|        |

  #AC 3.2 Validate blue color contact card for Non Supervisor users in View Team screen
  #@CP-2182 @CP-2182-09 @paramita @tm-regression @ui-tm
  #Merging into above script to avoid lot of data
  Scenario Outline: Validate color contact card of Non Supervisor users in View Team screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I verify color contact card of Non Supervisor in View Team screen
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         |[blank]|        |

  #AC 3.3 Validate Team Users display in alphabetical order, asc in View Team screen
  @CP-2182 @CP-2182-10 @paramita @tm-regression @ui-tm
  ## muted due to infinite loop issue with  ln 122 'Then I verify Users are sorted in alphabetical order'
  #Vinuta - Modified to validate users are sorted by not creating new data
  Scenario Outline: Validate Users are sorted in alphabetical order, asc in View Team screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    When I click on Team icon from left menu bar
    And I navigate to team "RegressionBaseline"
    #And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I verify Users are sorted in alphabetical order
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         |[blank]|        |

  #AC 3.3 Validate Supervisor’s card is listed first, asc in View Team screen
  #@CP-2182 @CP-2182-11 @paramita @tm-regression @ui-tm
  #Merging into above script to avoid lot of data
  Scenario Outline: Validate Supervisor’s card listed first in View Team screen
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    Then I verify Supervisor User is listed first
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         |[blank]|        |

  #AC 5.0  Verify Add User button in View Team screen
  #@CP-2182 @CP-2182-12 @paramita @tm-regression @ui-tm
  #Merging into above script to avoid lot of data
  Scenario Outline: Verify Add User button on Team User List section
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I click on Team icon from left menu bar
    And I check whether it has team value, if not, I will create a team "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I verify Add User button on User List section is enabled
    Examples:
      | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} | {random} | today         |[blank]|        |

#Feature: Tenant Manager Team List

  @CP-800 @CP-800-01 @vidya @tm-regression @ui-tm
  Scenario: Verification of Team List columns header name
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to team list page
    Then I verify all columns headers and add button and back arrow are displayed in team list page

  @CP-800 @CP-800-02 @vidya @tm-regression @ui-tm @tm-smoke
  Scenario Outline: Verification of Business Unit List columns Values
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to business unit list  page
    And I check whether it has business unit, if not, I will create business unit "<buName>","<buDesc>","<buStartDate>","<buEndDate>"
    When I navigate to team details page
    When I populate data on team details page "<teamName>","<teamDesc>","<teamStartDate>","<teamEndDate>","<teamBU>"
    And I click on Save button on team details page
    And I navigate to newly created team
    Then I verify Team List columns Values
    Examples:
      | buName   | buDesc | buStartDate | buEndDate | teamName | teamDesc | teamStartDate | teamEndDate | teamBU |
      | {random} |[blank]| today       | +5        | {random} | {random} | today         | today       |[blank]|
