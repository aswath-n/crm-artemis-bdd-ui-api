Feature: UI Field dependency rules and validations

  @CP-36335 @CP-40548 @CP-40549 @CP-36335-01 @mital @tm-regression @ui-tm
  Scenario Outline: Verify creation UI Field dependency rules and validations - Textfield to Textfield
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    When I expand the project "BLCRM" to view the details
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create or select template in it
    And I verify list of templates is displayed
    And I select template "regressionTemplate" to add fields in it
    And I click on Configure Rules section
    Then I click on Add Rule button
    And I provide details to add rule with source field "<srcfieldName>" as "<fieldType>" and source condition "<srcCondition>"
    And I provide details to add rule with destination field "<destfieldName>" as "<fieldType>" and destination condition "<destCondition>"
    Then I click on save button to save rule
    And I verify the success message got displayed
    Examples:
      | srcfieldName | fieldType | srcCondition | destfieldName | destCondition |
      | Text1        | textfield | Not Null     | text2         | Not Null      |
      | Text1        | textfield | Disabled     | text2         | Disabled      |

  @CP-36335 @CP-40548 @CP-40549 @CP-36335-02 @mital @tm-regression @ui-tm
  Scenario Outline: Verify creation UI Field dependency rules and validations - Dropdown to Dropdown
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    When I expand the project "BLCRM" to view the details
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create or select template in it
    And I verify list of templates is displayed
    And I select template "regressionTemplate" to add fields in it
    And I click on Configure Rules section
    Then I click on Add Rule button
    And I provide details to add rule with source field "<srcfieldName>" as "<fieldType>" and source condition "<srcCondition>"
    And I provide details to add rule with destination field "<destfieldName>" as "<fieldType>" and destination condition "<destCondition>"
    Then I click on save button to save rule
    And I verify the success message got displayed
    Examples:
      | srcfieldName | fieldType | srcCondition | destfieldName | destCondition |
      | dropdown1    | dropdown  | Not Null     | dropdown2     | Not Null      |
      | dropdown1    | dropdown | Not Null     | dropdown2     | Disabled      |

  @CP-36335 @CP-40548 @CP-40549 @CP-36335-03 @mital @tm-regression @ui-tm
  Scenario Outline: Verify creation UI Field dependency rules and validations - textfield to Dropdown/Dropdown to textfield
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    When I expand the project "BLCRM" to view the details
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create or select template in it
    And I verify list of templates is displayed
    And I select template "regressionTemplate" to add fields in it
    And I click on Configure Rules section
    Then I click on Add Rule button
    And I provide details to add rule with source field "<srcfieldName>" as "<srcfieldType>" and source condition "<srcCondition>"
    And I provide details to add rule with destination field "<destfieldName>" as "<destfieldType>" and destination condition "<destCondition>"
    Then I click on save button to save rule
    And I verify the success message got displayed
    Examples:
      | srcfieldName | srcfieldType | srcCondition | destfieldName | destfieldType | destCondition |
      | Text1        | textfield    | Not Null     | dropdown2     | dropdown      | Disabled      |
      | dropdown1    | dropdown     | Not Null     | Text1         | textfield     | Not Null      |

  @CP-36335 @CP-40548 @CP-40549 @CP-36335-04 @mital @tm-regression @ui-tm
  Scenario Outline: Verify creation UI Field dependency rules and validations - Verify adding more than one dependency rules
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    When I expand the project "BLCRM" to view the details
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create or select template in it
    And I verify list of templates is displayed
    And I select template "testTemplate3" to add fields in it
    And I click on Configure Rules section
    Then I click on Add Rule button
    And I provide details to add rule with source field "<srcfieldName>" as "<srcfieldType>" and source condition "<srcCondition>"
    And I provide details to add rule with destination field "<destfieldName>" as "<destfieldType>" and destination condition "<destCondition>"
    Then I click on Add Rule button
    And I provide details to add rule with source field "<srcfieldName1>" as "<srcfieldType1>" and source condition "<srcCondition1>"
    And I provide details to add rule with destination field "<destfieldName1>" as "<destfieldType1>" and destination condition "<destCondition1>"
    Then I click on save button to save rule
    And I verify the success message got displayed
    Examples:
      | srcfieldName | srcfieldType | srcCondition | destfieldName | destfieldType | destCondition | srcfieldName1 | srcfieldType1 | srcCondition1 | destfieldName1 | destfieldType1 | destCondition1 |
      | text1        | textfield    | Not Null     | dropdown1     | dropdown      | Disabled      | text1         | textfield     | Disabled      | dropdown1      | dropdown       | Disabled       |

  @CP-36335 @CP-40548 @CP-40549 @CP-36335-05 @mital @tm-regression @ui-tm
  Scenario: Verify creation UI Field dependency rules and validations - Cancel saving validation rules
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    When I expand the project "BLCRM" to view the details
    And I navigate to project designer page
    When I verify list of pages is displayed
    Then I click on page "activeContact" to create or select template in it
    And I verify list of templates is displayed
    And I select template "testTemplate3" to add fields in it
    And I click on Configure Rules section
    Then I click on Add Rule button
    And I provide details to add rule with source field "text1" as "textfield" and source condition "Not Null"
    And I click on cancel button and verify warning message is displayed on cofigure rule page
