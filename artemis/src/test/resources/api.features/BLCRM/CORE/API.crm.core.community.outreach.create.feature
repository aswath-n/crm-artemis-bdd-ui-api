Feature: API-CRM-CORE: Community Outreach Feature

  @contact-record-api-CRMC @CP-36222 @API-CORE @API-CRM-Regression @aikanysh
  Scenario: Create and Search Community Outreach
    Given I initiated community outreach API
    And I can provide following information for community outreach creation
    And I can run create community outreach API
    And I initiated specific community outreach search ""
    And I can provide following information for community outreach search
    And I run search community outreach API
    Then I can verify following community outreach information:
      | sessionType | Education/Enrollment Session |

  @contact-record-api-CRMC @CP-40628 @API-CORE @API-CRM-Regression @sang
  Scenario: Create Community Outreach data
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel          | sessionStatus | sessionRegion | createdOn         | languages     | createdBy |
      | PTA Meeting | true             | Consumer Request | Scheduled     | Southeast     | CURRENT DATE TIME | English,Other | 8369      |
    And I fill in the following data for "Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Harry     |
    And I fill in the following data for "Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 8369      |
    And I fill in the following data for "Site Contact" in Community Outreach Save API
      | contactFirstName | contactLastName | createdOn         | createdBy |
      | Clark            | Kent            | CURRENT DATE TIME | 8369      |
    And I send PUT "BLCRM" Community Outreach Save API


