Feature: Create and Edit Permission Group - Version2

  @CP-36341 @CP-36341-01 @mital @tm-regression @ui-tm
  Scenario Outline:Verify pages and sections and Permission Details Grid - display by default
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    When I navigate to new permission settings
    And Verify Permission Details grid
    And Verify each page has a caret
    And No Permission is selected by default to all pages
    Then Verify checked Apply to All checkbox displayed for the selected permissions
    Then Verify Filter By Page dropdown
    Then Verify selected the caret, then the fields and grids are displayed
    Then Verify Clear filter by page button
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 |
      | {random} | New role created | today      | +1       |

  @CP-36341 @CP-36341-02 @mital @tm-regression @ui-tm
  Scenario Outline:Verify Configure Permission for Page(s) are set and displayed for the selected permissions
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    When I navigate to new permission settings
    And I select page "activeContact" from Filter by Page dropdown
    And I checked "<permission>" Permission for "activeContact" page
    And Click on Save button and saving Permission
    And I select page "activeContact" from Filter by Page dropdown
    Then I verify "<permission>" is set to "activeContact" page
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 | permission |
      | {random} | New role created | today      | +1       | {random}   |

  @CP-36341 @CP-36341-03 @mital @tm-regression @ui-tm
  Scenario Outline: Verify Configure Permission for Template(s) are set and displayed for the selected permissions
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    When I navigate to new permission settings
    And I select page "activeContact" from Filter by Page dropdown
    And I click on caret sign to select template permission
    And I checked "<permission>" Permission for "tempKey66" template in page "activeContact"
    And Click on Save button and saving Permission
    Then I verify "<permission>" is set to "tempKey66" template in page "activeContact"
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 | permission |
      | {random} | New role created | today      | +1       | {random}   |

  @CP-36341 @CP-36341-04 @mital @tm-regression @ui-tm
  Scenario Outline: Verify Configure Permission for Fields(s) are set and displayed for the selected permissions
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    When I navigate to new permission settings
    And I select page "activeContact" from Filter by Page dropdown
    And I click on caret sign to select template permission
    And I click on "tempKey66" template caret sign to select field or grid permission
    And I checked "<permission>" Permission for field "key25" in "tempKey66" template in page "activeContact"
    And Click on Save button and saving Permission
    Then I verify "<permission>" is set to field "key25" in "tempKey66" template in page "activeContact"
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 | permission |
      | {random} | New role created | today      | +1       | {random}   |

  @CP-36341 @CP-36341-05 @mital @tm-regression @ui-tm
  Scenario Outline: Verify Configure Permission for Grid(s) are set and displayed for the selected permissions
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    When I navigate to new permission settings
    And I select page "activeContact" from Filter by Page dropdown
    And I click on caret sign to select template permission
    And I click on "tempKey77" template caret sign to select field or grid permission
    And I checked "<permission>" Permission for grid "GridKeyRegression" in "tempKey77" template in page "activeContact"
    And Click on Save button and saving Permission
    Then I verify "<permission>" is set to grid "GridKeyRegression" in "tempKey77" template in page "activeContact"
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 | permission |
      | {random} | New role created | today      | +1       | {random}   |

  @CP-36341 @CP-36341-06 @mital @tm-regression @ui-tm
  Scenario Outline: Verify Configure Permission for Grid Column(s) are set and displayed for the selected permissions
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    When I navigate to project role details page
    And I populate data on project role page "<roleName>","<roleDesc>","<startDate1>","<endDate1>"
    And I click on Save button on project role page
    When I selected newly created role
    When I navigate to new permission settings
    And I select page "activeContact" from Filter by Page dropdown
    And I click on caret sign to select template permission
    And I click on "rgressionGridTemplate" template caret sign to select field or grid permission
    And I click on "RegressionGrid" grid caret sign to select column permission
    And I checked "<permission>" Permission for grid column "key0N3" in grid "RegressionGrid" in "rgressionGridTemplate" template in page "activeContact"
    And Click on Save button and saving Permission
    Then I verify "<permission>" is set to grid column "key0N3" in grid "RegressionGrid" in "rgressionGridTemplate" template in page "activeContact"
    Examples:
      | roleName | roleDesc         | startDate1 | endDate1 | permission |
      | {random} | New role created | today      | +1       | {random}   |
