@CP-9095
Feature: Update back end to save/update a Consumer on a Case

  @CP-9095 @CP-9095-01.0 @asad @events @events-cc
  Scenario Outline: New PI - Start date populated, Null End Date
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for case consumer date field check with "empty" End Date
    Then I will verify effective start date and end date for "New-NoEndDate" case consumer date field check
    And I will take correlation Id for "CONSUMER_SAVE_EVENT" for "consumerId" for case consumer date field check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created for case consumer date field check in payload for "New-NoEndDate" event
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-01.1 @asad @events @events-cc #Fails due to defect CP-13600
  Scenario Outline: New CM - Start date populated, Null End Date
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Member"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "CM"
    And I add "Case Member" for case consumer date field check with "empty" End Date
    Then I will verify effective start date and end date for "New-NoEndDate" case consumer date field check
    And I will take correlation Id for "CONSUMER_SAVE_EVENT" for "consumerId" for case consumer date field check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created for case consumer date field check in payload for "New-NoEndDate" event
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-01.2 @asad @events @events-cc #Fails due to defect CP-13600
  Scenario Outline: New AR - Start date populated, Null End Date
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Authorized Representative"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for case consumer date field check with "empty" End Date
    Then I will verify effective start date and end date for "New-NoEndDate" case consumer date field check
    And I will take correlation Id for "CONSUMER_SAVE_EVENT" for "consumerId" for case consumer date field check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created for case consumer date field check in payload for "New-NoEndDate" event
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-02.0 @asad @events @events-cc #Fails due to defect CP-13601
  Scenario Outline: New PI - Start date populated, End date populated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for case consumer date field check with "populated" End Date
    Then I will verify effective start date and end date for "New-YesEndDate" case consumer date field check
    And I will take correlation Id for "CONSUMER_SAVE_EVENT" for "consumerId" for case consumer date field check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created for case consumer date field check in payload for "New-YesEndDate" event
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-02.1 @asad @events @events-cc #Fails due to defect CP-13018,CP-13600
  Scenario Outline: New CM - Start date populated, End date populated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Member"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "CM"
    And I add "Case Member" for case consumer date field check with "populated" End Date
    Then I will verify effective start date and end date for "New-YesEndDate" case consumer date field check
    And I will take correlation Id for "CONSUMER_SAVE_EVENT" for "consumerId" for case consumer date field check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created for case consumer date field check in payload for "New-YesEndDate" event
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-02.2 @asad @events #Fails due to defect CP-13018,CP-13600
  Scenario Outline: New AR - Start date populated, End date populated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Authorized Representative"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for case consumer date field check with "populated" End Date
    Then I will verify effective start date and end date for "New-YesEndDate" case consumer date field check
    And I will take correlation Id for "CONSUMER_SAVE_EVENT" for "consumerId" for case consumer date field check
    Then I will verify an "CONSUMER_SAVE_EVENT" is created for case consumer date field check in payload for "New-YesEndDate" event
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-03.0 @asad @ui-cc #Fails due to defect CP-13601
  Scenario Outline: Update PI - Start date, End Date updated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for case consumer date field check with "empty" End Date
    And I update added "Primary Individual" for case consumer date field check
    Then I will verify effective start date and end date for "New-updatedDates" case consumer date field check
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-03.1 @asad @ui-cc #Fails due to defect CP-13600,CP-13601
  Scenario Outline: Update CM - Start date, End Date updated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Member"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "CM"
    And I add "Case Member" for case consumer date field check with "empty" End Date
    And I click save button Active contact
    And I update added "Case Member" for case consumer date field check
    Then I will verify effective start date and end date for "New-updatedDates" case consumer date field check
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-03.2 @asad @ui-cc #Fails due to defect CP-13600,CP-13601
  Scenario Outline: Update AR - Start date, End Date updated
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Authorized Representative"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for case consumer date field check with "empty" End Date
    And I update added "Authorized Representative" for case consumer date field check
    Then I will verify effective start date and end date for "New-updatedDates" case consumer date field check
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-04.0 @asad @events @events-cc #Fails due to defect CP-13601
  Scenario Outline: Update PI - CONSUMER_UPDATE_EVENT check for PI
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Primary Individual"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "PI"
    And I add "Primary Individual" for case consumer date field check with "empty" End Date
    And I update added "Primary Individual" for case consumer date field check
    And I will take correlation Id for "CONSUMER_UPDATE_EVENT" for "consumerId" for case consumer date field check
    Then I will verify an "CONSUMER_UPDATE_EVENT" is created for case consumer date field check in payload for "consumer" event
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-04.1 @asad @events @events-cc #Fails due to defect CP-13600,CP-13601
  Scenario Outline: Update CM - CONSUMER_UPDATE_EVENT check for CM
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Member"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "CM"
    And I add "Case Member" for case consumer date field check with "empty" End Date
    And I update added "Case Member" for case consumer date field check
    And I will take correlation Id for "CONSUMER_UPDATE_EVENT" for "consumerId" for case consumer date field check
    Then I will verify an "CONSUMER_UPDATE_EVENT" is created for case consumer date field check in payload for "consumer" event
    Examples:
      | projectName |
      |[blank]|

  @CP-9095 @CP-9095-04.2 @asad @events @events-cc #Fails due to defect CP-13600,CP-13601
  Scenario Outline: Update AR - CONSUMER_UPDATE_EVENT check for AR
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for case consumer date field check for "Authorized Representative"
    When I click case consumer search tab
    And I search consumer with first name and last name for case consumer date field check
    And I click on demographic tab and click on add for "AR"
    And I add "Authorized Representative" for case consumer date field check with "empty" End Date
    And I update added "Authorized Representative" for case consumer date field check
    And I will take correlation Id for "CONSUMER_UPDATE_EVENT" for "consumerId" for case consumer date field check
    Then I will verify an "CONSUMER_UPDATE_EVENT" is created for case consumer date field check in payload for "consumer" event
    Examples:
      | projectName |
      |[blank]|

  @CP-16069 @CP-16069-1 @Beka @events @ui-cc @events-cc
  Scenario Outline: Verify consumer save event capture optIn/out update for PI
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "ADfaUJp" and Last Name as "hCJOBMy"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    And I click on first Primary Individual
    And I check all optIn checkbox
    And I initiate Consumer Events API to check from "CONSUMER_SAVE_EVENT" Consumer Opt in
    Then I verify Consumer "<OptType>" Opt in details from "CONSUMER_SAVE_EVENT" for the Consumer
    Examples:
      |OptType|
      | Mail  |
      | Fax   |
      | Phone |
      | Email |
      | Text  |