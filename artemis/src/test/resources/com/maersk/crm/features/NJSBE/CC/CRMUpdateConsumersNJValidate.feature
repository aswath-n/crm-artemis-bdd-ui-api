Feature: NJ Update consumers via Validate button

  @CP-12966 @CP-12966-01 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Consumer Update event check for Validate & Link for a case that was previously saved - General Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on first user radio button and "DOB" "SSN" check boxes
    And I click validate&link button for "general"
    And I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "CONSUMER_UPDATE_EVENT" is as expected


  @CP-12966 @CP-12966-02 @ui-cc-nj @nj-regression @muhababt
  Scenario: Consumer Update event check for Validate & Link for a case that was previously saved - 3rd Party Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched customer have First Name as "Queen" and Last Name as "Faith" in third party
    And I click on primary individual record in search result
    And I click validate&link button for "third"
    And I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "CONSUMER_UPDATE_EVENT" is as expected

    #
  @CP-12966 @CP-12966-03 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Consumer Update event check for Validate & Link for a case that was previously saved - Manual Case/Consumer Search
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on Case Consumer Search page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click to expand on primary individual
    And I click validate&link and contact details tab
    And I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "CONSUMER_UPDATE_EVENT" is as expected


  @CP-12966 @CP-12966-04 @ui-cc-nj @nj-regression @muhabbat
  Scenario: Consumer Update event check for Validate & Link for a case that was previously saved - Edit Contact Record Details
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    And I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    And I click validate&link button for "createTask"
    And I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "CONSUMER_UPDATE_EVENT" is as expected


  @CP-12966 @CP-12966-05 @events @nj-regression @muhabbat @ui-cc-nj
  Scenario: Consumer Update event check for Validate & Link for a case that was previously saved - Edit Task Screen
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    When I search Task by current status date for record
    When I click on second Task ID on Task Search
    When I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    And I click validate&link button for "taskSearch"
    And I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "CONSUMER_UPDATE_EVENT" is as expected

  @CP-12966 @CP-12966-06 @events @nj-regression @muhabbat @ui-cc-nj
  Scenario: Consumer Update event check for Validate & Link for a case that was previously saved - manual case/consumer search (from Manual Contact record Search screen)
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record Search Page
    When I can search Contact Record by status date and id "NJ100002908"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I unlink consumer from contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    And I click validate&link button for "search"
    And I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "CONSUMER_UPDATE_EVENT" is as expected


  @CP-12966 @CP-12966-07 @events @nj-regression @muhabbat @ui-cc-nj
  Scenario: Consumer Update event check for Validate & Link for a case that was previously saved - manual case/consumer search (from Active Contact record Search screen)
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on first user radio button and "DOB" "SSN" check boxes
    And I click validate&link and contact details tab
    When I click on first Contact Record ID on Contact Record Edit record Screen
    When I click on edit button on contact details tab
    When I unlink consumer from contact record
    When I searched customer have First Name as "Nova" and Last Name as "Pablo"
    And I click on primary individual record in search result
    And I click validate&link button for "search"
    And I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "CONSUMER_UPDATE_EVENT" is as expected


  @CP-9229 @CP-9229-04 @events @nj-regression @kamil @ui-cc-nj
  Scenario: Verify createdOn,createdBy,updatedOn,updatedBy fields for CONSUMER_UPDATE_EVENT - General Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on first user radio button and "DOB" "SSN" check boxes
    And I click validate&link button for "general"
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see updatedOn and updatedBy matches the Event createdOn and createdBy values "CONSUMER_UPDATE_EVENT"


  @CP-9229 @CP-9229-05 @events @nj-regression @kamil @ui-cc-nj
  Scenario:Verify createdOn,createdBy,updatedOn,updatedBy fields for CONSUMER_UPDATE_EVENT - 3rd Party Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    Then I click on third party contact record type radio button
    And I click on primary individual record in search result
    And I click validate&link button for "third"
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see updatedOn and updatedBy matches the Event createdOn and createdBy values "CONSUMER_UPDATE_EVENT"


  @CP-9229 @CP-9229-06 @events @nj-regression @kamil @ui-cc-nj
  Scenario: Verify createdOn,createdBy,updatedOn,updatedBy fields for CONSUMER_UPDATE_EVENT - Manual Case/Consumer Search
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on Case Consumer Search page
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click to expand on primary individual
    And I click validate&link and contact details tab
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see updatedOn and updatedBy matches the Event createdOn and createdBy values "CONSUMER_UPDATE_EVENT"


  @CP-9229 @CP-9229-07 @events @nj-regression @kamil @ui-cc-nj
  Scenario:Verify createdOn,createdBy,updatedOn,updatedBy fields for CONSUMER_UPDATE_EVENT - Edit Contact Record Details
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    And I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    And I click validate&link button for "createTask"
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see updatedOn and updatedBy matches the Event createdOn and createdBy values "CONSUMER_UPDATE_EVENT"


  @CP-9229 @CP-9229-08 @events @nj-regression @kamil @ui-cc-nj
  Scenario:Verify createdOn,createdBy,updatedOn,updatedBy fields for CONSUMER_UPDATE_EVENT - Edit Task Screen
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    When I search Task by current status date for record
    When I click on second Task ID on Task Search
    When I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    And I click validate&link button for "taskSearch"
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see updatedOn and updatedBy matches the Event createdOn and createdBy values "CONSUMER_UPDATE_EVENT"


  @CP-9229 @CP-9229-09 @events @nj-regression @kamil @ui-cc-nj
  Scenario: Verify createdOn,createdBy,updatedOn,updatedBy fields for CONSUMER_UPDATE_EVENT - case/consumer search from Active Contact record Search screen
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Nova" and Last Name as "Pablo"
    And I click on first user radio button and "DOB" "SSN" check boxes
    And I click validate&link and contact details tab
    When I click on first Contact Record ID on Contact Record Edit record Screen
    When I click on edit button on contact details tab
    And I unlink consumer from contact record
    When I searched customer have First Name as "Queen" and Last Name as "Faith"
    And I click on primary individual record in search result
    And I click validate&link button for "search"
    When I will get the Authentication token for "NJ-SBE" in "CRM"
    And I retrieve the "CONSUMER_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see updatedOn and updatedBy matches the Event createdOn and createdBy values "CONSUMER_UPDATE_EVENT"