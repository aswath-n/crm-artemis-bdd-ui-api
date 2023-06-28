Feature: Community Outreach Feature

  @CP-36516  @CP-36516-01 @aikanysh @crm-regression @ui-core
  Scenario: Navigation to "Create Outreach Session"
    Given I logged into CRM
    When I click on the Hamburger Menu
    Then I should see Create Outreach Session option

  @CP-36584  @CP-36584-01 @aikanysh @crm-regression @ui-core
  Scenario: Cancel or Continue Warning PopUp: Cancel
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    And I input session notes and estimated attendees
    Then I navigate away to contact record search screen
    And I am able to see Warning pop-up message with cancel and continue buttons
    When I click cancel button on warning pop-up
    Then I remain on Create Community Outreach Session page

  @CP-36584  @CP-36584-02 @aikanysh @crm-regression @ui-core
  Scenario: Cancel or Continue Warning PopUp: Continue
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    And I input session notes and estimated attendees
    Then I navigate away to contact record search screen
    And I am able to see Warning pop-up message with cancel and continue buttons
    When I click continue button on warning pop-up
    Then I am navigated to contact record search screen

  @CP-38519 @CP-9689  @CP-9689-01 @aikanysh @crm-regression @ui-core
  Scenario: Create New Community Outreach Session - Session Details : All elements are present
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    Then I verify all elements on Session Details screen are displayed

  @CP-38519 @CP-9689  @CP-9689-02 @aikanysh @crm-regression @ui-core
  Scenario: Create New Community Outreach Session - Session Details : verify input elements
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    Then I verify input elements
      | Session Notes        |
      | Number of Attendees  |
      | Enrollment Updates   |
      | Surveys Collected    |
      | Call Center Verified |

  @CP-38519  @CP-9689  @CP-9689-03 @aikanysh @crm-regression @ui-core
  Scenario: Create New Community Outreach Session - Session Details : verify required elements
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    Then I verify required elements
      | Session Type   |
      | Public Allowed |
      | Session Status |
      | Channel        |
      | Languages      |

  @CP-10320  @CP-10320-01 @aikanysh @crm-regression @ui-core
  Scenario: Create New Community Outreach Session - Site Information : All elements are present
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    And I click on Site Information  Page from stepping header
    Then I verify all elements on Site Information screen are displayed

  @CP-10320  @CP-10320-02 @aikanysh @crm-regression @ui-core
  Scenario: Create New Community Outreach Session - Site Information :verify input elements
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    And I click on Site Information  Page from stepping header
    Then I verify input elements for Session - Site Information Screen
      | Site Notes |

  @CP-10320  @CP-10320-03 @aikanysh @crm-regression @ui-core
  Scenario: Create New Community Outreach Session - Site Information :verify all required elements
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    And I click on Site Information  Page from stepping header
    Then I verify required elements for Session - Site Information screen

  @CP-38220  @CP-38220-01 @aikanysh @crm-regression @ui-core
  Scenario: Front End Navigation- Create New Community Outreach Session - Session Details
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    And I verify Next button is displayed and functioning
    And I verify Required Information Icon is displayed
    And I verify Navigattion via Stepping Header is working
    And I verify data entry is retained when I navigate to next page

  @CP-10319 @CP-10319-01 @aikanysh @crm-regression @ui-core
  Scenario: Create New Community Outreach Session - Session Schedule: verify all elements are displayed
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    And I click on Session Schedule Page from stepping header
    Then I verify all elements on Session Schedule screen are displayed

  @CP-10319 @CP-10319-02 @aikanysh @crm-regression @ui-core
  Scenario: Create New Community Outreach Session - Session Schedule: verify all required elements
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    And I click on Session Schedule Page from stepping header
    Then I verify required elements on Session Schedule screen

  @CP-9692 @CP-9692-01 @crm-regression @ui-core @sang
  Scenario: Verify Community Outreach Mgmt option and corresponding selections to the Left Hand Navigation
    Given I logged into CRM
    And I verify "COMMUNITY EVENT CALENDER" option is available in the Left Hand Navigation
    And I verify "COMMUNITY EVENT MGMT" option is available in the Left Hand Navigation
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    Then I verify the following selections are available to choose from the Community Outreach Mgmt
      | CREATE SESSION | SEARCH SESSION |

  @CP-10321 @CP-10321-01 @aikanysh @crm-regression @ui-core
  Scenario: Create New Community Outreach Session - Site Contact Information: verify all elements displayed
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    And I click on Site Contact Information page from stepping header
    Then I verify all elements on Site Contact Information screen are displayed

  @CP-39120 @CP-39120-01 @aikanysh @crm-regression @ui-core
  Scenario: Connect Save API Community Outreach Session
    Given I logged into CRM
    When I click on the Hamburger Menu
    And I click on Create Outreach Session option
    And I input all required information on Session Details Screen
    And I input all required information on Session Schedule Screen
    And I input all required information on Site Information Screen
    And I input all required information on Site Contact Info Screen
    And I verify I am able to save community outreach sessions

  @CP-9713 @CP-9713-01 @CP-41089 @aikanysh @crm-regression @ui-core
  Scenario: Perform Manual Community Outreach Search
    Given I logged into CRM
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I verify all elements are displayed for community outreach search screen

  @CP-9712 @CP-9712-01 @aikanysh @crm-regression @ui-core
  Scenario: View Community Outreach Session Details
    Given I initiated community outreach API
    And I can provide following information for community outreach creation
    And I can run create community outreach API
    Given I logged into CRM
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I search community outreach session with ID from API response
    And I click on first found community outreach session
    And I verify all displayed fields for View Screen

  @CP-9688 @CP-9688-01 @crm-regression @ui-core @khazar
  Scenario: Export Button in Community Event Search Results is Displayed
    Given I initiated community outreach API
    And I can provide following information for community outreach creation
    And I can run create community outreach API
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I search community outreach session with ID from API response
    Then I click on Calendar button and able to see the Export button

  @CP-38722 @CP-38722-01 @crm-regression @ui-core @khazar
      #CP-38722 (AC 1.0, AC 2.0, AC 3.0)
  Scenario Outline:  Display a Calendar View of the Community Event Search Results
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel          | sessionStatus   | sessionRegion | createdOn         | languages | createdBy |
      | PTA Meeting | true             | Consumer Request | <sessionStatus> | Southeast     | CURRENT DATE TIME | English   | 8369      |
    And I fill in the following data for "Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Harry     |
    And I fill in the following data for "Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy | notes          |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 8369      | New site notes |
    And I fill in the following data for "Site Contact" in Community Outreach Save API
      | contactFirstName | contactLastName | createdOn         | createdBy |
      | Clark            | Kent            | CURRENT DATE TIME | 8369      |
    And I send PUT "BLCRM" Community Outreach Save API
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I search on Community Outreach page by the following field
      | SESSION ID       |
      | API AUTO CREATED |
    And I click on the "CALENDAR VIEW BUTTON" in Community Outreach functionality
    And I verify the search results date
    Then I verify the saved session will present the "BLCRM" required information in their individual calendar view
    Examples:
      | sessionStatus                                        |
      | Scheduled                                            |
      | Presented - Post Session Follow Up Screen to display |
      | Requested                                            |
      | Cancelled                                            |