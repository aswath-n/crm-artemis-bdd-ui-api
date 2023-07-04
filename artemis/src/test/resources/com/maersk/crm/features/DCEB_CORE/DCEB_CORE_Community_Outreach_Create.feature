Feature: DC-EB Community Outreach Create

  @CP-44102 @CP-44102-01 @ui-core-dc-eb @crm-regression @sang
    # CP-44102(AC 1.0, AC 1.1)
  Scenario: Display Ward field and corresponding values in Site Information tab instead of County
    Given I logged into CRM and select a project "DC-EB"
    And I click on "CREATE SESSION" Community Outreach option from left hand menu
    And I navigate to "SITE INFORMATION" tab in create Community Outreach
    And I verify "COUNTY" field "is not" displayed
    And I verify "WARD" field "is" displayed
    And I verify the "WARD" dropdown in Community Outreach contains the following values
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |

  @CP-44102 @CP-44102-02 @ui-core-dc-eb @crm-regression @sang
    # CP-44102(AC 2.0)
  Scenario: Verify Community Organization value is included as an option for SITE TYPE dropdown in DCEB tenant
    Given I logged into CRM and select a project "DC-EB"
    And I click on "CREATE SESSION" Community Outreach option from left hand menu
    And I navigate to "SITE INFORMATION" tab in create Community Outreach
    And I verify the "DCEB SITE TYPE" dropdown in Community Outreach contains the following values
      | Agency | Community Organization | Media | Provider |

  @CP-44098 @CP-44098-01 @ui-core-dc-eb @crm-regression @sang
    # CP-44098(AC 1.0, AC 2.0, AC 3.0, AC 4.0)
  Scenario: Verify the DCEB tenant relevant dropdowns and its values for Session Details tab in Community Outreach
    Given I logged into CRM and select a project "DC-EB"
    And I click on "CREATE SESSION" Community Outreach option from left hand menu
    And I verify the "DCEB SESSION REGION" dropdown in Community Outreach contains the following values
      | Northeast | Northwest | Southeast | Southwest |
    And I verify the "DCEB CHANNEL" dropdown in Community Outreach contains the following values
      | Consumer | Community Organization | Project |
    And I verify the "DCEB SESSION TYPE" dropdown in Community Outreach contains the following values
      | Health Fair | Education/Enrollment Session | Community Stakeholders Presentation |
    And I verify the "DCEB LANGUAGES" dropdown in Community Outreach contains the following values
      | English | Spanish | Vietnamese | Korean | French | Amheric | Chinese |

  @CP-44098 @CP-44098-02 @ui-core-dc-eb @crm-regression @sang
    # CP-44098(AC 5.0 negative)
  Scenario: Verify the selection of Public Allowed to true displays the the Community Outreach Session entry in Calendar View
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "DCEB Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel  | sessionStatus | sessionRegion | createdOn         | languages | createdBy | ward |
      | Health Fair | true             | Consumer | Scheduled     | Southeast     | CURRENT DATE TIME | English   | 9547      | 1    |
    And I fill in the following data for "DCEB Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Iris      |
    And I fill in the following data for "DCEB Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 9547      |
    And I fill in the following data for "DCEB Site Contact" in Community Outreach Save API
      | contactFirstName | contactLastName | createdOn         | createdBy |
      | Clark            | Kent            | CURRENT DATE TIME | 9547      |
    And I send PUT "DCEB" Community Outreach Save API
    Given I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on "SEARCH SESSION" Community Outreach option from left hand menu
    And I search on Community Outreach page by the following field
      | SESSION ID       |
      | API AUTO CREATED |
    And I click on the "CALENDAR VIEW BUTTON" in Community Outreach functionality
    Then I validate the Community Outreach Session entry "is" displayed in the search view calendar

  @CP-44098 @CP-44098-03 @ui-core-dc-eb @crm-regression @sang
    # CP-44098(AC 5.0 happy path)
  Scenario: Verify the selection of Public Allowed to false displays the the Community Outreach Session entry in Calendar View
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "DCEB Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel  | sessionStatus | sessionRegion | createdOn         | languages | createdBy | ward |
      | Health Fair | true             | Consumer | Scheduled     | Southeast     | CURRENT DATE TIME | English   | 9547      | 1    |
    And I fill in the following data for "DCEB Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Iris      |
    And I fill in the following data for "DCEB Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 9547      |
    And I fill in the following data for "DCEB Site Contact" in Community Outreach Save API
      | contactFirstName | contactLastName | createdOn         | createdBy |
      | Clark            | Kent            | CURRENT DATE TIME | 9547      |
    And I send PUT "DCEB" Community Outreach Save API
    Given I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on "SEARCH SESSION" Community Outreach option from left hand menu
    And I search on Community Outreach page by the following field
      | SESSION ID       |
      | API AUTO CREATED |
    And I click on the "CALENDAR VIEW BUTTON" in Community Outreach functionality
    Then I validate the Community Outreach Session entry "is" displayed in the search view calendar

  @CP-38722 @CP-38722-02 @ui-core-dc-eb @crm-regression @khazar
      #CP-38722 (AC 1.0, AC 2.0, AC 3.0)
  Scenario Outline:  Display a Calendar View of the Community Event Search Results
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "DCEB Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel  | sessionStatus   | sessionRegion | createdOn         | languages | createdBy | ward |
      | Health Fair | true             | Consumer | <sessionStatus> | Southeast     | CURRENT DATE TIME | English   | 9547      | 1    |
    And I fill in the following data for "DCEB Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Iris      |
    And I fill in the following data for "DCEB Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy | notes          |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 9547      | New site notes |
    And I fill in the following data for "DCEB Site Contact" in Community Outreach Save API
      | contactFirstName | contactLastName | createdOn         | createdBy |
      | Clark            | Kent            | CURRENT DATE TIME | 9547      |
    And I send PUT "DCEB" Community Outreach Save API
    Given I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I click on "SEARCH SESSION" Community Outreach option from left hand menu
    And I search on Community Outreach page by the following field
      | SESSION ID       |
      | API AUTO CREATED |
    And I click on the "CALENDAR VIEW BUTTON" in Community Outreach functionality
    And I verify the search results date
    Then I verify the saved session will present the "DC-EB" required information in their individual calendar view
    Examples:
      | sessionStatus                                        |
      | Scheduled                                            |
      | Presented - Post Session Follow Up Screen to display |
      | Requested                                            |
      | Cancelled                                            |