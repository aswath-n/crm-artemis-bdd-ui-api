Feature: DCEB Community Outreach Create API

  @contact-record-api-CRMC @CP-40628 @API-CORE @API-CRM-Regression @sang
  Scenario: Create Community Outreach data
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I initiated Community Outreach Save API
    And I fill in the following data for "DCEB Session" in Community Outreach Save API
      | sessionType | publicAllowedInd | channel  | sessionStatus | sessionRegion | createdOn         | languages | createdBy | ward |
      | Health Fair | true             | Consumer | Scheduled     | Southeast     | CURRENT DATE TIME | English   | 9547      | 1    |
    And I fill in the following data for "DCEB Session Schedule" in Community Outreach Save API
      | sessionDate | sessionStartTime | sessionEndTime  | recurrenceFrequency | clientReqistrationRequiredInd | presenter |
      | TOMORROW    | TOMORROW AT 2PM  | TOMORROW AT 3PM | Yearly              | false                         | Harry     |
    And I fill in the following data for "DCEB Session Site" in Community Outreach Save API
      | siteName    | addressLine1   | city   | state   | county   | zip   | siteType | createdOn         | createdBy |
      | RANDOM SITE | RANDOM ADDRESS | Agawam | Alabama | Tompkins | 80212 | Agency   | CURRENT DATE TIME | 9547      |
    And I fill in the following data for "DCEB Site Contact" in Community Outreach Save API
      | contactFirstName | contactLastName | createdOn         | createdBy |
      | Clark            | Kent            | CURRENT DATE TIME | 9547      |
    And I send PUT "DCEB" Community Outreach Save API
