Feature: Make contacts update api call used by Digital a Synchronous call


  @API-CP-27522 @API-CP-27522-01 @API-CC @API-CRM-Regression @Beka
  Scenario Outline: Verify response synchronous
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
      | caseContacts.phone.phoneNumber         | <phoneNumber>       |
      | caseContacts.email.emailAddress        | <emailAddress>      |
    Then verify the response that is returned includes the data that was saved in the database
      | caseId                                 | <caseId>            |
      | caseContacts.address.addressStreet1    | <addressStreet1>    |
      | caseContacts.address.addressStreet2    | <addressStreet2>    |
      | caseContacts.address.addressState      | <addressState>      |
      | caseContacts.address.addressCounty     | <addressCounty>     |
      | caseContacts.address.addressCountyCode | <addressCountyCode> |
      | caseContacts.address.addressCity       | <addressCity>       |
      | caseContacts.address.addressZip        | <addressZip>        |
      | caseContacts.address.addressZipFour    | <addressZip4>       |
      | caseContacts.phone.phoneNumber         | <phoneNumber>       |
      | caseContacts.email.emailAddress        | <emailAddress>      |
    When Send api call with CaseId to Contact Primary API for "<caseId>"
    Then I verify data Synchronous saved in database
    Examples:
      | phoneNumber | callName | addressStreet1 | addressStreet2 | addressState | addressCounty | addressCountyCode | addressCity | addressZip | addressZip4 | caseId | emailAddress  |
      | 7776667755  | c19      | Address 22     | yes            | Arizona      | UpdatedCounty | 12347             | UpdatedCity | 55555      | 4444        | 31449  | test@mail.com |


  @API-CP-27523 @API-CP-27523-01 @API-CC @API-Regression @Beka
  Scenario Outline:  Contacts update endpoint Return error messages for missing information empty string
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
      | caseContacts.phone.phoneNumber         | <phoneNumber>       |
      | caseContacts.email.emailAddress        | <emailAddress>      |
    Then I will verify error message "The following objects contain invalid data: [insert address, and/or phone, and/or email]." and error code "CONTACT004"
    Examples:
      | phoneNumber | addressStreet1 | addressStreet2 | addressState | addressCounty  | addressCountyCode | addressCity  | addressZip | addressZip4 | caseId | emailAddress   |
      |[blank]|                |[blank]|              |[blank]|                   |[blank]|            |[blank]| 57     |[blank]|
      | undefined   | undefined      | undefined      | undefined    | Updated County | 12344             | Updated City | 55555      | undefined   | 56     | undefined      |
      |[blank]|                |[blank]| undefined    | Updated County | 12344             | Updated City | 55555      |[blank]| 57     |[blank]|
      | 234234234   | undefined      | undefined      | undefined    | Updated County | 12344             | Updated City | 55555      | undefined   | 56     | undefined      |
      | 234234234   | undefined      | undefined      |[blank]| Updated County | 12344             | Updated City | 55555      | undefined   | 56     | test@gmail.com |
      |[blank]| Street ct 55   |[blank]| undefined    | Updated County | 12344             | Updated City | 55555      |[blank]| 57     |[blank]|

  @API-CP-27523 @API-CP-27523-02 @API-CC @API-Regression @Beka
  Scenario:  Contacts update endpoint Return error messages for missing address information is null
    Given I will get the Authentication token for "" in "CRM"
    And  I initiate the Update Primary Contact info API
    When I send api call to Update Primary Address info With Valid ID and null values for "address"
    Then I will verify error message "The following fields are required: IF address is updated, then addressStreet1, addressCity, addressState, addressZip are required." and error code "CONTACT003"

  @API-CP-27523 @API-CP-27523-03 @API-CC @API-Regression @Beka
  Scenario:  Contacts update endpoint Return error messages for missing email information is null
    Given I will get the Authentication token for "" in "CRM"
    And  I initiate the Update Primary Contact info API
    When I send api call to Update Primary Address info With Valid ID and null values for "email"
    Then I will verify error message "The following fields are required: If email is updated, then emailAddress is required." and error code "CONTACT003"

  @API-CP-27523 @API-CP-27523-04 @API-CC @API-Regression @Beka
  Scenario:  Contacts update endpoint Return error messages for missing phone information is null
    Given I will get the Authentication token for "" in "CRM"
    And  I initiate the Update Primary Contact info API
    When I send api call to Update Primary Address info With Valid ID and null values for "phone"
    Then I will verify error message "The following fields are required: If phone is updated, then phoneNumber is required." and error code "CONTACT003"

  @API-CP-28013 @API-CP-28013-01 @API-CC @API-Regression @Beka
  Scenario Outline: Verify error response with Invalid data format request
    Given I will get the Authentication token for "" in "CRM"
    And  I initiate the Update Primary Contact info API
    When I send api call to Update Primary Address info With Valid ID and invalid data
      | caseId                                 | <caseId>            |
      | caseContacts.address.addressStreet1    | <addressStreet1>    |
      | caseContacts.address.addressStreet2    | <addressStreet2>    |
      | caseContacts.address.addressState      | <addressState>      |
      | caseContacts.address.addressCounty     | <addressCounty>     |
      | caseContacts.address.addressCountyCode | <addressCountyCode> |
      | caseContacts.address.addressCity       | <addressCity>       |
      | caseContacts.address.addressZip        | <addressZip>        |
      | caseContacts.address.addressZipFour    | <addressZip4>       |
      | caseContacts.phone.phoneNumber         | <phoneNumber>       |
      | caseContacts.email.emailAddress        | <emailAddress>      |
    Then I will verify error message "The following objects contain invalid data: [insert address, and/or phone, and/or email]." and error code "CONTACT004"
    Examples:
      | phoneNumber  | addressStreet1                                      | addressStreet2                                      | addressState                     | addressCounty                                        | addressCountyCode | addressCity                      | addressZip | addressZip4 | caseId | emailAddress  |
      | 3423423      | New Addres    1                                     | Updated AS2                                         | Arizona                          | Updated County                                       | 1234              | Updated City                     | 55555      | 4444        | 31449  | test@mail.com |
      | 012345678912 | New Addres    2                                     | Updated AS2                                         | Arizona                          | Updated County                                       | 1234              | Updated City                     | 55555      | 4444        | 31449  | test@mail.com |
      | 3423423fdg   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsas  | Updated AS2                                         | Arizona                          | Updated County                                       | 1234              | Updated City                     | 55555      | 4444        | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsad2 | Updated AS2                                         | Arizona                          | Updated County                                       | 1234              | Updated City                     | 55555      | 4444        | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph2342332 | Arizona                          | Updated County                                       | 1234              | Updated City                     | 55555      | 4444        | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjher | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsas   | 1234              | Updated City                     | 55555      | 4444        | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsawer | 1234              | Updated City                     | 55555      | 4444        | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsaw   | 123wer4           | qwerttyuiopasdfghjklzxcvbnmkjh   | 555554     | 4444        | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsaw   | 123wer4           | qwerttyuiopasdfghjklzxcvbnmkjher | 55555      | 4444        | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsaw   | 123wer4           | qwerttyuiopasdfghjklzxcvbnmk23   | 55556      | 4444        | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsaw   | 123wer4           | qwerttyuiopasdfghjklzxcvbnmkjh   | 5555       | 4444        | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsaw   | 123wer4           | qwerttyuiopasdfghjklzxcvbnmkjh   | 54545      | 444         | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsaw   | 123wer4           | qwerttyuiopasdfghjklzxcvbnmkjh   | 55545      | 44443       | 31449  | test@mail.com |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsaw   | 123wer4           | qwerttyuiopasdfghjklzxcvbnmkjh   | 55577      | 4444        | 31449  | test@mail.    |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsaw   | 123wer4           | qwerttyuiopasdfghjklzxcvbnmkjh   | 55577      | 4444        | 31449  | test@mail.c   |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsaw   | 123wer4           | qwerttyuiopasdfghjklzxcvbnmkjh   | 55577      | 4444        | 31449  | test@.com     |
      | 0123456789   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuioph234233  | qwerttyuiopasdfghjklzxcvbnmkjh   | qwerttyuiopasdfghjklzxcvbnmkjhgdsqwertyuiophgfdsaw   | 123wer4           | qwerttyuiopasdfghjklzxcvbnmkjh   | 55577      | 4444        | 31449  | mail.com      |

  @API-CP-15569 @API-CP-15569-01 @API-CC @API-CC @API-CRM-Regression @Beka
  Scenario: Verify Record Updated On date captured when address record is updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API request
    And I create a case consumer with address "5 gurteen ct" type "Mailing"
    When I will update "addressSource" address record for this consumer
    Then I initiate the Consumer search API
    And I send API call to search Consumer With first name and Last name
    Then I will verify new address not created

  @API-CP-15569 @API-CP-15569-02 @API-CC @API-CC @API-CRM-Regression @Beka
  Scenario: Exact Match Found - Do Not Create New address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API request
    And I create a case consumer with address "" type ""
    When I will update "same adressStreet1" address record for this consumer
    Then I initiate the Consumer search API
    And I send API call to search Consumer With first name and Last name
    Then I will verify new address not created

  @API-CP-15569 @API-CP-15569-03 @API-CC @API-CC @API-CRM-Regression @Beka
  Scenario: Create New address Information if update request has not exact address data
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API request
    And I create a case consumer with address "" type ""
    Then I will add a new address using the existing Address Type and capture the following information
      | addressStreet1 | 66 green st |
      | addressCity    | Baltimore   |
      | addressCounty  | Timonium    |
      | addressState   | MD          |
      | addressZip     | 21093       |
    Then I initiate the Consumer search API
    And I send API call to search Consumer With first name and Last name
    And I verify Existing Address of this type for the Consumer will be updated as follows
      | updatedOn        | current time |
      | effectiveEndDate | current time |
    And I verify Effective Start Date, Created By, Created On are NOT updated for existing address
    Then I verify newly added Address of this type for the Consumer will be updated as follows
      | createdOn          | current time |
      | effectiveStartDate | current time |

  @API-CP-15569 @API-CP-15569-04 @API-CC @API-CC @API-CRM-Regression @Beka
  Scenario Outline: Null Contact Info - Do Not Create New Address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API request
    And I create a case consumer with address "" type ""
    When I will update record with null payload for address data "<Field>" to be "<Value>"
    Then I initiate the Consumer search API
    And I send API call to search Consumer With first name and Last name
    Then I will verify existing adress following "<Field>" not updated to null and no address record created
    Examples:
      | Field          | Value |
      | addressStreet1 | null  |
      | addressCity    | null  |
      | addressCounty  | null  |
      | addressState   | null  |
      | addressZip     | null  |
