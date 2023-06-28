@API-CP-2694
@umid

Feature: Obtain Correspondence Item(s) From ECMS On Request

  Background: get a DMS token
    Given I will get the Authentication token for BLCRM in DMS
    And I will get the Authentication token for "<projectName>" in "CRM"

  @API-CP-2694-01 @API-ECMS
  Scenario: Verify that I am able to search a specific value by "id"
    When I search for that document with a key as "id" and value as "40629" to get credentials
    And I search Inbound Correspondence by document Id
    Given that I have an Inbound Correspondence in Onbase with a key as "documentId" and value as "40629"
    When I search for that document with a key as "id" and value as "40629"
    Then I should see "id" and value as "40629" have been retrieved

#  @API-CP-2694-02 @API-ECMS need refactor CP-22376
  Scenario: Verify that I am able to search a specific value by "notificationId"
    When I search for that document with a key as "notificationId" and value as "9114" to get credentials
    And I search Inbound Correspondence by document Id
    Given that I have an Outbound Correspondence in Onbase with a key as "notificationId" and value as "9114"
    When I search for that document by "notificationId" and value as "9114"
    Then I should see "returnedNotificationId" and value as "9114" have been retrieved

#  @API-CP-2694-03 @API-ECMS need refactor CP-22376
  Scenario: Verify that I am able to add a specific value by "notificationId" using "and" logic
    When I search for that document by both "notificationId" as "2143" and "correspondenceType" as "maersk Outbound Correspondence"
    Given that I have an Outbound Correspondence in Onbase with a key as "notificationId" and value as "2143"
    And that I have an Outbound Correspondence in Onbase with a key as "correspondenceType" and value as "maersk Outbound Correspondence" 2
    And I have an Outbound Correspondence in Onbase with a "notificationId" and a "correspondenceType"
    When I search for that document by both "notificationId" as "2143" and "correspondenceType" as "maersk Outbound Correspondence"
    Then I should see that documentId and "correspondenceType" Outbound documents retrieved

  @API-CP-2694-04 @API-ECMS
  Scenario: Verify that I am able to search a specific value by "caseId"
    When I search for that document with a key as "caseId" and value as "4478" to get credentials
    And I search Inbound Correspondence by document Id
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4478"
    When I search for that document by "caseId" and value as "4478"
    Then I should see "caseId" and value as "4478" have been retrieved

  @API-CP-2694-05 @API-ECMS
  Scenario: Verify that I am able to add a specific value by "caseId" using "and" logic
    When I search for that document by both "caseId" as "4945" and "consumerId" as "38948"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4945"
    And that I have an Inbound Correspondence in Onbase with a key as "consumerId" and value as "38948" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "consumerId"
    When I search for that document by both "caseId" as "4945" and "consumerId" as "38948"
    Then I should see that "caseId" and "consumerId" Inbound Correspondence have retrieved

  @API-CP-2694-06 @API-ECMS
  Scenario: Verify that I am able to search a specific value by "consumerId"
    When I search for that document with a key as "consumerId" and value as "38948" to get credentials
    And I search Inbound Correspondence by document Id
    Given that I have an Inbound Correspondence in Onbase with a key as "consumerId" and value as "38948"
    When I search for that document by "consumerId" and value as "38948"
    Then I should see "consumerId" and value as "38948" have been retrieved

#  @API-CP-2694-07 @API-ECMS no longer a valid scenario
  Scenario: Verify that I am able to search a specific value by "setId"
    When I search for that document with a key as "originSetId" and value as "76564534525" to get credentials
    And I search Inbound Correspondence by document Id
    Given that I have an Inbound Correspondence in Onbase with a key as "originSetId" and value as "76564534525"
    When I search for that document by "originSetId" and value as "76564534525"
    Then I should see "originSetId" and value as "76564534525" have been retrieved

#  @API-CP-2694-08 @API-ECMS no longer a valid scenario
  Scenario: Verify that I am able to add a specific value by "originSetId" using "and" logic
    When I search for that document by both "caseId" as "4945" and "originSetId" as "76564534525"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4945"
    And that I have an Inbound Correspondence in Onbase with a key as "originSetId" and value as "76564534525" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "originSetId"
    When I search for that document by both "caseId" as "4945" and "originSetId" as "76564534525"
    Then I should see that documentId and "caseId" Inbound documents retrieved

  @API-CP-2694-09 @API-ECMS
  Scenario: Verify that I am able to add a specific value by "Type" using "and" logic
    When I search for that document by both "caseId" as "4478" and "type" as "maersk Case + Consumer"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4478"
    And that I have an Inbound Correspondence in Onbase with a key as "type" and value as "maersk Case + Consumer" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "type"
    When I search for that document by both "caseId" as "4478" and "type" as "maersk Case + Consumer"
    Then I should see that "caseId" and "type" Inbound Correspondence have retrieved

  @API-CP-2694-10 @API-ECMS
  Scenario: Verify that I am able to add a specific value by "Status" using "and" logic
    When I search for that document by both "caseId" as "4478" and "status" as "Received"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4478"
    And that I have an Inbound Correspondence in Onbase with a key as "status" and value as "Received" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "status"
    When I search for that document by both "caseId" as "4478" and "status" as "Received"
    Then I should see that "caseId" and "status" Inbound Correspondence have retrieved

  @API-CP-2694-11 @API-ECMS
  Scenario: Verify that I am able to add a specific value by "channel" using "and" logic
    When I search for that document by both "caseId" as "4478" and "channel" as "Email"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4478"
    And that I have an Inbound Correspondence in Onbase with a key as "channel" and value as "Email" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "channel"
    When I search for that document by both "caseId" as "4478" and "channel" as "Email"
    Then I should see that "caseId" and "channel" Inbound Correspondence have retrieved

  @API-CP-2694-12 @API-ECMS
  Scenario: Verify that I am able to search a specific value by "fromEmailAddress"
    When I search for that document with a key as "fromEmailAddress" and value as "test@test.com" to get credentials
    And I search Inbound Correspondence by document Id
    Given that I have an Inbound Correspondence in Onbase with a key as "fromEmailAddress" and value as "test@test.com"
    When I search for that document by "fromEmailAddress" and value as "test@test.com"
    Then I should see "fromEmailAddress" and value as "test@test.com" have been retrieved

  @API-CP-2694-13 @API-ECMS
  Scenario: Verify that I am able to add a specific value by "fromEmailAddress" using "and" logic
    When I search for that document by both "caseId" as "4945" and "fromEmailAddress" as "jamesmlacson@maersk.com"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4945"
    And that I have an Inbound Correspondence in Onbase with a key as "fromEmailAddress" and value as "jamesmlacson@maersk.com" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "fromEmailAddress"
    When I search for that document by both "caseId" as "4945" and "fromEmailAddress" as "jamesmlacson@maersk.com"
    Then I should see that documentId and "caseId" Inbound documents retrieved

  @API-CP-2694-14 @API-ECMS
  Scenario: Verify that I am able to add a specific value by "fromPhoneNumber" using "and" logic
    When I search for that document by both "caseId" as "4945" and "fromPhoneNumber" as "7034552939"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4945"
    And that I have an Inbound Correspondence in Onbase with a key as "fromPhoneNumber" and value as "7034552939" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "fromPhoneNumber"
    When I search for that document by both "caseId" as "4945" and "fromPhoneNumber" as "7034552939"
    Then I should see that documentId and "caseId" Inbound documents retrieved

  @API-CP-2694-15 @API-ECMS
  Scenario: Verify that I am able to search a specific value by "fromName"
    When I search for that document with a key as "fromName" and value as "MoreType" to get credentials
    And I search Inbound Correspondence by document Id
    Given that I have an Inbound Correspondence in Onbase with a key as "fromName" and value as "MoreType"
    When I search for that document by "fromName" and value as "MoreType"
    Then I should see "fromName" and value as "MoreType" have been retrieved

  @API-CP-2694-16 @API-ECMS
  Scenario: Verify that I am able to add a specific value by "fromName" using "and" logic
    When I search for that document by both "caseId" as "4945" and "fromName" as "NoType"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4945"
    And that I have an Inbound Correspondence in Onbase with a key as "fromName" and value as "NoType" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "fromName"
    When I search for that document by both "caseId" as "4945" and "fromName" as "NoType"
    Then I should see that "caseId" and "fromName" Inbound Correspondence have retrieved

#  @API-CP-2694-17 @API-ECMS no longer a valid scenario
  Scenario: Verify that I am able to search a specific value by "originItemId"
    When I search for that document with a key as "originItemId" and value as "22031" to get credentials
    And I search Inbound Correspondence by document Id
    Given that I have an Inbound Correspondence in Onbase with a key as "originItemId" and value as "22031"
    When I search for that document by "originItemId" and value as "22031"
    Then I should see "originItemId" and value as "22031" have been retrieved

#  @API-CP-2694-18 @API-ECMS no longer a valid scenario
  Scenario: Verify that I am able to add a specific value by "originItemId" using "and" logic
    When I search for that document by both "caseId" as "4945" and "originItemId" as "22031"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4945"
    And that I have an Inbound Correspondence in Onbase with a key as "originItemId" and value as "22031" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "originItemId"
    When I search for that document by both "caseId" as "4945" and "originItemId" as "22031"
    Then I should see that documentId and "caseId" Inbound documents retrieved

#  @API-CP-2694-19 @API-ECMS no longer a valid scenario
  Scenario: Verify that I am able to search a specific value by "origin"
    When I search for that document with a key as "origin" and value as "testOrigin" to get credentials
    And I search Inbound Correspondence by document Id
    Given that I have an Inbound Correspondence in Onbase with a key as "origin" and value as "testOrigin"
    When I search for that document by "origin" and value as "testOrigin"
    Then I should see "origin" and value as "testOrigin" have been retrieved

#  @API-CP-2694-20 @API-ECMS no longer a valid scenario
  Scenario: Verify that I am able to add a specific value by "origin" using "and" logic
    When I search for that document by both "caseId" as "4945" and "origin" as "testOrigin"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4945"
    And that I have an Inbound Correspondence in Onbase with a key as "origin" and value as "testOrigin" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "origin"
    When I search for that document by both "caseId" as "4945" and "origin" as "testOrigin"
    Then I should see that documentId and "caseId" Inbound documents retrieved

#  @API-CP-2694-21 @API-ECMS no longer a valid scenario
  Scenario: Verify that I am able to search a specific value by "originSetId"
    When I search for that document with a key as "originSetId" and value as "76564534525" to get credentials
    And I search Inbound Correspondence by document Id
    Given that I have an Inbound Correspondence in Onbase with a key as "originSetId" and value as "76564534525"
    When I search for that document by "originSetId" and value as "76564534525"
    Then I should see "originSetId" and value as "76564534525" have been retrieved

#  @API-CP-2694-22 @API-ECMS no longer a valid scenario
  Scenario: Verify that I am able to add a specific value by "originSetId" using "and" logic
    When I search for that document by both "caseId" as "4945" and "originSetId" as "76564534525"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "4945"
    And that I have an Inbound Correspondence in Onbase with a key as "originSetId" and value as "76564534525" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "originSetId"
    When I search for that document by both "caseId" as "4945" and "originSetId" as "76564534525"
    Then I should see that documentId and "caseId" Inbound documents retrieved

  @API-CP-2694-23 @API-ECMS
  Scenario: Verify that I am able to add a specific value by "receivedDate" using "and" logic
    When I search for that document by both "caseId" as "60795" and "dateReceivedMin" as "2022-02-09"
    Given that I have an Inbound Correspondence in Onbase with a key as "caseId" and value as "60795"
    And that I have an Inbound Correspondence in Onbase with a key as "receivedDate" and value as "2022-02-09" 2
    And I have an Inbound Correspondence in Onbase with a "caseId" and a "receivedDate"
    When I search for that document by both "caseId" as "60795" and "receivedDate" as "2022-02-09"
    Then I should see that "caseId" and "receivedDate" Inbound Correspondence have retrieved

  @API-CP-2694-24 @API-ECMS
  Scenario: Verify that the api will return a "No records found" message with 500 error code when no records are matching
    When I search for that document by both "dateReceivedMin" as "2030-01-01" and "dateReceivedMax" as "2030-01-01"
    Given that I have a Correspondence in Onbase
    When I search for that document by both "dateReceivedMin" as "2030-01-01" and "dateReceivedMax" as "2030-01-01"
    Then I should see api will return a "No Matching records found for given Search Criteria"

  @API-CP-2694-25 @API-ECMS
  Scenario: Verify that the api will return a "Too many records to return" when it is matching over 500 documents
    When I search for that document by both "dateReceivedMin" as "2020-03-25" and "dateReceivedMax" as "2020-12-26"
    Given that I have a Correspondence in Onbase
    When I search for that document by both "dateReceivedMin" as "2020-03-25" and "dateReceivedMax" as "2020-12-26"
    Then I should see api will return a "Search results in excess, enter additional search criteria to limit search results"

@API-CP-2694-26 @API-ECMS
  Scenario: Verify that I am able to search a specific value by "receivedDate"
    When I search for that document by both "dateReceivedMin" as "2022-02-09" and "dateReceivedMax" as "2022-02-09"
    Given that I have a Correspondence in Onbase
    When I search for that document by both "dateReceivedMin" as "2022-02-09" and "dateReceivedMax" as "2022-02-09"
    Then I should see the "2022-02-09" and "2022-02-09" from default Inbound Correspondence response

#@API-CP-2694-27 @API-ECMS
  Scenario: Verify that I am able to search a specific value by "Type"
    When I search for that document by "type" and value as "maersk Case + Consumer"
    Given that I have a Correspondence in Onbase
    When I search for that document by "type" and value as "maersk Case + Consumer"
    Then I should see api will return a "Too many records to return"

#  @API-CP-2694-28 @API-ECMS
  Scenario: Verify that I am able to search a specific value by "Status"
    When I search for that document by "status" and value as "Received"
    Given that I have a Correspondence in Onbase
    When I search for that document by "status" and value as "Received"
    Then I should see api will return a "Too many records to return"

#@API-CP-2694-29 @API-ECMS
  Scenario: Verify that I am able to search a specific value by "channel"
    When I search for that document by "channel" and value as "Email"
    Given that I have a Correspondence in Onbase
    When I search for that document by "channel" and value as "Email"
    Then I should see api will return a "Too many records to return"

@API-CP-2694-30 @API-ECMS
  Scenario: Verify that I am able to search a specific value by "fromPhoneNumber"
  When I search for that document with a key as "fromPhoneNumber" and value as "7034845939" to get credentials
  And I search Inbound Correspondence by document Id
  Given that I have an Inbound Correspondence in Onbase with a key as "fromPhoneNumber" and value as "7034845939"
  When I search for that document by "fromPhoneNumber" and value as "7034845939"
  Then I should see "fromPhoneNumber" and value as "7034845939" have been retrieved

