@DMS-35
Feature: Outbound Correspondence Request Part 2

  @API-CP-24318 @API-CP-24318-01 @API-ECMS @RuslanL
  Scenario: Verify when OC definition is created Body Data Structure Version is 1 and reflecting OC response
    Given I have random valid data for an Outbound Correspondence Definition
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I send the Outbound Correspondence Definition to the server for project: "current" with random valid data and Body Data Structure
    Then I verify the Outbound Correspondence definition response body has Body Data Structure Version is 1
    And I will add Channel "Email" to the Outbound Correspondence Definition
    And I send the request for an Outbound Correspondence to the service with created definition
    And I should see the Outbound Correspondence response body has Body Data Structure Version is 1
    Then I should see Body Data Structure Version is 1 when initiate Get Correspondence by retrieved "Correspondence ID"

  @API-CP-24318 @API-CP-24318-02 @API-ECMS @RuslanL
  Scenario: Verify when user adding new body element Body Data Structure Version increasing by 1 and reflecting OC request each time(will add steps to add body data multiple times)
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I update the Outbound Correspondence Definition and add body element
      | Last Name |
      | DOB       |
    Then I verify the Outbound Correspondence definition response body has Body Data Structure Version is 2
    And I send the request for an Outbound Correspondence to the service with created definition
    And I should see the Outbound Correspondence response body has Body Data Structure Version is 2
    Then I should see Body Data Structure Version is 2 when initiate Get Correspondence by retrieved "Correspondence ID"
    When I update the Outbound Correspondence Definition and add body element
      | SSN |
    Then I verify the Outbound Correspondence definition response body has Body Data Structure Version is 3
    And I send the request for an Outbound Correspondence to the service with created definition
    And I should see the Outbound Correspondence response body has Body Data Structure Version is 3
    Then I should see Body Data Structure Version is 3 when initiate Get Correspondence by retrieved "Correspondence ID"

  @API-CP-24318 @API-CP-24318-03 @API-ECMS @RuslanL
  Scenario: Verify when user not adding new body element Body Data Structure Version not changing
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I update the Outbound Correspondence Definition and add body element
      | null |
    Then I verify the Outbound Correspondence definition response body has Body Data Structure Version is 3
    And I send the request for an Outbound Correspondence to the service with created definition
    And I should see the Outbound Correspondence response body has Body Data Structure Version is 3
    Then I should see Body Data Structure Version is 3 when initiate Get Correspondence by retrieved "Correspondence ID"


  @API-CP-27757 @API-CP-27757-01 @API-ECMS @RuslanL
  Scenario: Verify SchemaElement is not present anymore in OC definition response body
    Given I have random valid data for an Outbound Correspondence Definition
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I send the Outbound Correspondence Definition to the server for project: "current" with random valid data and Body Data Structure
    When I update the Outbound Correspondence Definition and add body element
      | First Name |
      | Last Name  |
      | SSN        |
      | DOB        |
    Then I retrieve the previously created Outbound Correspondence Definition by correspondence ID
    Then I verify the Outbound Correspondence definition response body not contains "SchemaElement"

  @API-CP-28999 @API-CP-28999-01 @API-ECMS @RuslanL
  Scenario: Verify system store Template id, Channel Definition id and template version in the Notification record for channel Mail
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail"
    And I retrieve the Outbound Correspondence and verify Template id, Channel Definition id and Template version

  @API-CP-28999 @API-CP-28999-02 @API-ECMS @RuslanL
  Scenario: Verify system store Template id, Channel Definition id and template version in the Notification record for channel Email
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Email"
    And I retrieve the Outbound Correspondence and verify Template id, Channel Definition id and Template version

  @API-CP-28999 @API-CP-28999-03 @API-ECMS @RuslanL
  Scenario: Verify system store Template id, Channel Definition id and template version in the Notification record for channel Text
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I retrieve the Outbound Correspondence and verify Template id, Channel Definition id and Template version

  @API-CP-28999 @API-CP-28999-04 @API-ECMS @RuslanL
  Scenario: Verify system store Template id, Channel Definition id and template version in the Notification record for multiple channels
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    And I retrieve the Outbound Correspondence and verify Template id, Channel Definition id and Template version
