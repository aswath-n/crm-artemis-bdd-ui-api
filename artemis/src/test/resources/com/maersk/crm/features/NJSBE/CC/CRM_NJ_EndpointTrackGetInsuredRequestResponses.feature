Feature: NJ New table and Endpoint to track GetInsured request/responses

  @CP-13152 @CP-13152-01 @ui-cc-nj @nj-regression @crm-regression @umid
  Scenario Outline: NJ: track GetInsured request/responses - General Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "<FirstName>" and Last Name as "<LastName>"
    And I link the contact to an Consumer Profile with CSR Role
    And I save GetInsured Case Id and GetInsured Consumer Id
    And I run GetInsured search request/response event
    And I verify GetInsure search response contains info "<FirstName>" "<LastName>"
    Examples:
      | FirstName | LastName |
      | Nova      | Pablo    |

  @CP-13152 @CP-13152-02 @ui-cc-nj @nj-regression @umid
  Scenario Outline: NJ: track GetInsured request/responses - 3rd Party Contact
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I click on third party contact record type radio button
    When I searched customer have First Name as "<FirstName>" and Last Name as "<LastName>" in third party
    And I click on primary individual record in search result
    Then I verify validate&link is displayed
    And I click validate and link button
    And I save GetInsured Case Id and GetInsured Consumer Id
    And I run GetInsured search request/response event
    And I verify GetInsure search response contains info "<FirstName>" "<LastName>"
    Examples:
      | FirstName | LastName |
      | Nova      | Pablo    |

  @CP-13152 @CP-13152-03 @ui-cc-nj @nj-regression @umid
  Scenario Outline: NJ: track GetInsured request/responses - Manual Case/Consumer Search
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on Case Consumer Search page
    When I searched customer have First Name as "<FirstName>" and Last Name as "<LastName>"
    And I click to expand on primary individual
    Then I verify validate&link is displayed
    And I save GetInsured Case Id and GetInsured Consumer Id in case consumer search
    And I click validate and link button
    And I run GetInsured search request/response event
    And I verify GetInsure search response contains info "<FirstName>" "<LastName>"
    Examples:
      | FirstName  | LastName |
      | Blackberry | Moonstar |

  @CP-13152 @CP-13152-04 @ui-cc-nj @nj-regression @umid
  Scenario Outline: NJ: track GetInsured request/responses - Edit Contact Record Details
    Given I logged into CRM and select a project "NJ-SBE"
    When I Navigate to create task link
    And I select "Review Complaint" to create a Task
    And I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "<FirstName>" and Last Name as "<LastName>"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    And I save GetInsured Case Id and GetInsured Consumer Id in case consumer search
    And I run GetInsured search request/response event
    And I verify GetInsure search response contains info "<FirstName>" "<LastName>"
    Examples:
      | FirstName | LastName |
      | Nova      | Pablo    |

  @CP-13152 @CP-13152-05 @ui-cc-nj @nj-regression @umid
  Scenario Outline: NJ: track GetInsured request/responses - Edit Task Screen
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to "Task Search" page
    When I search Task by current status date for record
    When I click on second Task ID on Task Search
    When I click Edit task type button
    When I click on Link Case or Consumer button under LINK section
    When I searched customer have First Name as "<FirstName>" and Last Name as "<LastName>"
    And I click on primary individual record in search result
    Then I verify validate is displayed
    And I save GetInsured Case Id and GetInsured Consumer Id in case consumer search
    And I run GetInsured search request/response event
    And I verify GetInsure search response contains info "<FirstName>" "<LastName>"
    Examples:
      | FirstName | LastName |
      | Nova      | Pablo    |
