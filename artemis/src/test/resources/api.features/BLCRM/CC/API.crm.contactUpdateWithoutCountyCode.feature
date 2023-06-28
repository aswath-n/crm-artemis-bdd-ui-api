Feature: Digital web-portal to NOT require countyCode when saving an Address

  @API-CP-23644 @API-CP-23644-01 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Do not return error when county code is null
    Given I will get the Authentication token for "" in "CRM"
    And  I initiate the Update Primary Contact info API
    When I send api call to Update Primary Address info With Valid ID
      | caseId                                 | <caseId>            |
      | caseContacts.address.addressStreet1    | <addressStreet1>    |
      | caseContacts.address.addressStreet2    | <addressStreet2>    |
      | caseContacts.address.addressState      | <addressState>      |
      | caseContacts.address.addressCounty     | <addressCounty>     |
      | caseContacts.address.addressCountyCode | <addressCountyCode> |
      | caseContacts.address.addressCity       | <addressCity>       |
      | caseContacts.address.addressZip        | <addressZip>        |
      | caseContacts.address.addressZipFour    | <addressZip4>       |
    Then I verify the response that is returned without County Code does not have error
      | caseId                                 | <caseId>            |
      | caseContacts.address.addressStreet1    | <addressStreet1>    |
      | caseContacts.address.addressStreet2    | <addressStreet2>    |
      | caseContacts.address.addressState      | <addressState>      |
      | caseContacts.address.addressCounty     | <addressCounty>     |
      | caseContacts.address.addressCountyCode | <addressCountyCode> |
      | caseContacts.address.addressCity       | <addressCity>       |
      | caseContacts.address.addressZip        | <addressZip>        |
      | caseContacts.address.addressZipFour    | <addressZip4>       |
    When Send api call with CaseId to Contact Primary API for "<caseId>"
    Then I verify record has been saved and response does not have an error
    Examples:
      | addressStreet1  | addressStreet2 | addressState | addressCounty  | addressCountyCode | addressCity  | addressZip | addressZip4 | caseId |
      | 123 Updated AS1 | Updated AS2    | Arizona      | Updated County | null              | Updated City | 55555     | 4444       | 31449  |

