Feature: Community Outreach Search


  @CP-9687 @CP-9687-01 @crm-regression @ui-core @sang
  Scenario: Validate Community Outreach Search page labels and clicking on session id navigates to Community Outreach Details page
    # CP-9687(AC 1.0) CP-9687(AC 3.0)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel          | sessionStatus | sessionRegion | createdOn         | languages     | createdBy |
      | PTA Meeting | true             | Consumer Request | Scheduled     | Southeast     | CURRENT DATE TIME | English,Other | 8369      |
    And I fill in the following data for "Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter      |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Outreach Staff |
    And I fill in the following data for "Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 8369      |
    And I fill in the following data for "Site Contact" in Community Outreach Save API
      | contactFirstName | contactLastName | createdOn         | createdBy |
      | Clark            | Kent            | CURRENT DATE TIME | 8369      |
    And I send PUT "BLCRM" Community Outreach Save API
    Given I logged into CRM
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    Then I verify the page header and following field labels on the community outreach search page
      | C.O. SEARCH HEADER | SESSION ID | SESSION TYPE | SITE NAME | RECURRENCE FREQUENCY | PRESENTER NAME | SESSION REGION | SESSION STATUS | ZIP | COUNTY |
    And I search on Community Outreach page by the following field
      | SESSION ID       |
      | API AUTO CREATED |
    Then I verify the search result headers for Community Outreach search page
      | SEARCH RESULT(S) | SESSION ID | SESSION TYPE | SESSION STATUS | SESSION SCHEDULE DATE | SESSION REGION | SITE NAME | RECURRENCE FREQUENCY | ZIP | COUNTY | PRESENTER NAME |
    And I click on the "API CREATED SESSION ID" from the Community search result
    And I verify that clicking on Search result Session Id navigates me to the Community Outreach page

  @CP-9687 @CP-9687-02 @crm-regression @ui-core @sang
  Scenario: Community Outreach search results are sorted by Session Id in descending order and displayed by five results with the option to use pagination
    # CP-9687(AC 1.1, AC 1.2, AC 4.0)
    Given I logged into CRM
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I search on Community Outreach page by the following field
      | SESSION TYPE |
      | Health Fair  |
    Then I verify Community Outreach search results are sorted by Session Id in descending order and displayed by five results with the option to use pagination

  @CP-9687 @CP-9687-03 @crm-regression @ui-core @sang
  Scenario: Community Outreach search result values displayed for API created Community Outreach
    # CP-9687(AC 2.0)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel          | sessionStatus | sessionRegion | createdOn         | languages     | createdBy |
      | PTA Meeting | true             | Consumer Request | Scheduled     | North         | CURRENT DATE TIME | English,Other | 8369      |
    And I fill in the following data for "Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter      |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Outreach Staff |
    And I fill in the following data for "Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 8369      |
    And I fill in the following data for "Site Contact" in Community Outreach Save API
      | contactFirstName | contactLastName | createdOn         | createdBy |
      | Clark            | Kent            | CURRENT DATE TIME | 8369      |
    And I send PUT "BLCRM" Community Outreach Save API
    Given I logged into CRM
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I search on Community Outreach page by the following field
      | SESSION ID       |
      | API AUTO CREATED |
    Then I verify the Community Outreach Search results details by the following fields and values
      | SESSION ID  | SESSION TYPE | SESSION STATUS | SESSION SCHEDULED DATE | SESSION REGION | SITE NAME   | RECURRENCE FREQUENCY | ZIP   | COUNTY   | PRESENTER NAME |
      | API CREATED | PTA Meeting  | Scheduled      | TOMORROW               | North          | RANDOM SITE | Yearly               | 80212 | Tompkins | Outreach Staff |

  @CP-9711 @CP-9711-01 @CP-44891 @CP-44891-01 @crm-regression @ui-core @sang
  Scenario: Navigate to Community Outreach Edit by EDIT button and verify all field labels
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel          | sessionStatus | sessionRegion | createdOn         | languages     | createdBy |
      | PTA Meeting | true             | Consumer Request | Scheduled     | Southeast     | CURRENT DATE TIME | English,Other | 8369      |
    And I fill in the following data for "Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter      |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Outreach Staff |
    And I fill in the following data for "Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 8369      |
    And I fill in the following data for "Site Contact" in Community Outreach Save API
      | contactFirstName | contactLastName | createdOn         | createdBy |
      | Clark            | Kent            | CURRENT DATE TIME | 8369      |
    And I send PUT "BLCRM" Community Outreach Save API
    Given I logged into CRM
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I search on Community Outreach page by the following field
      | SESSION ID       |
      | API AUTO CREATED |
    And I click on the "API CREATED SESSION ID" in Community Outreach functionality
    And I click on the "EDIT BUTTON" in Community Outreach functionality
    Then I verify following "SESSION DETAILS" labels in the edit community outreach session page
      | REASON FOR EDIT | SESSION TYPE | CHANNEL | ESTIMATED ATTENDEES | SESSION REGION | PUBLIC ALLOWED | Yes | No | SESSION STATUS | LANGUAGES | SESSION NOTES |
    Then I verify following "POST SESSION FOLLOW UP" labels in the edit community outreach session page
      | NUMBER OF ATTENDEES | ENROLLMENT UPDATES | SURVEYS COLLECTED | CALL CENTER VERIFIED |
    Then I verify following "SESSION SCHEDULE" labels in the edit community outreach session page
      | SESSION DATE | SESSION START TIME | SESSION END TIME | TRAVEL TIME | PREPARATION TIME | RECURRENCE FREQUENCY | CLIENT REGISTRATION REQUIRED | Yes | No | PRESENTER |
    Then I verify following "SITE INFORMATION" labels in the edit community outreach session page
      | SITE NAME | SITE TYPE | ADDRESS LINE 1 | ADDRESS LINE 2 | CITY | STATE | ZIP | COUNTY | ACCESSIBLE | SITE NOTES |
    Then I verify following "SITE CONTACT INFORMATION" labels in the edit community outreach session page
      | FIRST NAME | LAST NAME | EMAIL | ADDRESS LINE 1 | ADDRESS LINE 2 | CITY | STATE | ZIP | WORK PHONE | FAX PHONE | CELL PHONE |

  @CP-9711 @CP-9711-02 @CP-44891 @CP-44891-02 @crm-regression @ui-core @sang
  Scenario: Verify Community Outreach Edit by EDIT button and verify all field format requirements
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel          | sessionStatus | sessionRegion | createdOn         | languages     | createdBy |
      | PTA Meeting | true             | Consumer Request | Scheduled     | Southeast     | CURRENT DATE TIME | English,Other | 8369      |
    And I fill in the following data for "Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter      |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Outreach Staff |
    And I fill in the following data for "Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 8369      |
    And I fill in the following data for "Site Contact" in Community Outreach Save API
      | createdOn         | createdBy |
      | CURRENT DATE TIME | 8369      |
    And I send PUT "BLCRM" Community Outreach Save API
    Given I logged into CRM
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I search on Community Outreach page by the following field
      | SESSION ID       |
      | API AUTO CREATED |
    And I click on the "API CREATED SESSION ID" in Community Outreach functionality
    And I click on the "EDIT BUTTON" in Community Outreach functionality
    Then I verify the following "SESSION DETAILS" field formats for the Edit Community Outreach session page
      | REASON FOR EDIT | SESSION TYPE | CHANNEL | ESTIMATED ATTENDEES | SESSION REGION | SESSION STATUS | LANGUAGES | SESSION NOTES |
    Then I verify the following "POST SESSION FOLLOW UP" field formats for the Edit Community Outreach session page
      | NUMBER OF ATTENDEES | ENROLLMENT UPDATES | SURVEYS COLLECTED | CALL CENTER VERIFIED |
    Then I verify the following "SESSION SCHEDULE" field formats for the Edit Community Outreach session page
      | SESSION DATE | SESSION START TIME | SESSION END TIME | TRAVEL TIME | PREPARATION TIME | RECURRENCE FREQUENCY | PRESENTER |
    Then I verify the following "SITE INFORMATION" field formats for the Edit Community Outreach session page
      | SITE NAME | SITE TYPE | ADDRESS LINE 1 | ADDRESS LINE 2 | CITY | STATE | ZIP | COUNTY | ACCESSIBLE | SITE NOTES |
    Then I verify the following "SITE CONTACT INFORMATION" field formats for the Edit Community Outreach session page
      | FIRST NAME | LAST NAME | EMAIL | ADDRESS LINE 1 | ADDRESS LINE 2 | CITY | STATE | ZIP | WORK PHONE | FAX PHONE | CELL PHONE |

  @CP-9711 @CP-9711-03 @CP-44891 @CP-44891-03 @crm-regression @ui-core @sang
  Scenario: Navigate away Warning message for Community Outreach Session edit page
    Given I logged into CRM
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I search on Community Outreach page by the following field
      | SESSION TYPE |
      | Health Fair  |
    And I click on the "FIRST RESULT FROM SEARCH" in Community Outreach functionality
    And I click on the "EDIT BUTTON" in Community Outreach functionality
    And I enter the following field value for "SESSION DETAILS" in Community Outreach edit page
      | ESTIMATED ATTENDEES |
      | 1-10                |
    Then I verify the Navigate away warning functionality in Community Outreach Edit page

  @CP-9711 @CP-9711-04 @CP-44891 @CP-44891-04 @crm-regression @ui-core @sang
  Scenario: Verify Edited By Edited Date and Reason For Edit are generated and saved for Community Outreach edit
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel          | sessionStatus | sessionRegion | createdOn         | languages     | createdBy |
      | PTA Meeting | true             | Consumer Request | Scheduled     | Southeast     | CURRENT DATE TIME | English,Other | 8369      |
    And I fill in the following data for "Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter      |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Outreach Staff |
    And I fill in the following data for "Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 8369      |
    And I fill in the following data for "Site Contact" in Community Outreach Save API
      | createdOn         | createdBy |
      | CURRENT DATE TIME | 8369      |
    And I send PUT "BLCRM" Community Outreach Save API
    Given I logged into CRM
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I search on Community Outreach page by the following field
      | SESSION ID       |
      | API AUTO CREATED |
    And I click on the "API CREATED SESSION ID" in Community Outreach functionality
    And I click on the "EDIT BUTTON" in Community Outreach functionality
    And I enter the following field value for "SESSION DETAILS" in Community Outreach edit page
      | ESTIMATED ATTENDEES | REASON FOR EDIT          |
      | 1-10                | Updating Session Details |
    And I click on the "SAVE BUTTON" in Community Outreach functionality
    Then I verify the following field values in the community outreach session details page
      | EDITED BY | EDITED DATE | REASON FOR EDIT |

  @CP-9711 @CP-9711-05 @CP-44891 @CP-44891-05 @crm-regression @ui-core @sang
  Scenario: Verify Required Information Error when attempting to Save Community Outreach Edit
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel          | sessionStatus | sessionRegion | createdOn         | languages | createdBy |
      | PTA Meeting | true             | Consumer Request | Scheduled     | Southeast     | CURRENT DATE TIME | English   | 8369      |
    And I fill in the following data for "Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter      |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Outreach Staff |
    And I fill in the following data for "Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 8369      |
    And I fill in the following data for "Site Contact" in Community Outreach Save API
      | createdOn         | createdBy |
      | CURRENT DATE TIME | 8369      |
    And I send PUT "BLCRM" Community Outreach Save API
    Given I logged into CRM
    When I click on the COMMUNITY EVENT MGMT Left Menu arrow to bring up selections
    And I click on Search Session from left hand side
    And I search on Community Outreach page by the following field
      | SESSION ID       |
      | API AUTO CREATED |
    And I click on the "API CREATED SESSION ID" in Community Outreach functionality
    And I click on the "EDIT BUTTON" in Community Outreach functionality
    And I clear all values for fields that are required to save in Community Outreach Edit
    And I verify the error message has populated for required information error