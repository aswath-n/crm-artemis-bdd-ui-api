@lookup2
Feature: Tenant Manager - ENUM Details

  @CP-34414 @CP-34414-01 @mital @tm-regression @ui-tm
  Scenario: 1.0 List database and enum tables
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    When I expand the project "BLCRM" to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-missing-information" database
    Then I verify the following options displayed for Enum field
      | ENUM_MISSING_INFORMATION_STATUS |
      | ENUM_MISSING_INFO_TYPE          |
      | ENUM_MI_HELP_TEXT               |
      | ENUM_RECORD_CLASS               |
      | ENUM_RECORD_TYPE                |

  @CP-34414 @CP-34414-02 @mital @tm-regression @ui-tm
  Scenario Outline: 1.1 Display existing Look up table records
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    When I expand the project "BLCRM" to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-contact-record-blcrm" database
    When I select any ENUM "<Enums>"  table
    When Click at search button
    Then the existing records for that ENUM are displayed in a grid with the following fields populated:
      | VALUE            |
      | DESCRIPTION      |
      | REPORT LABEL     |
      | SCOPE            |
      | ORDER BY DEFAULT |
      | START DATE       |
      | END DATE         |
      |[blank]|

    Examples:
      | Enums    |
      | {random} |

  @CP-34414 @CP-34414-03 @mital @tm-regression @ui-tm
  Scenario Outline: 2.0 Add the ENUM record
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    When I expand the project "BLCRM" to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-contact-record-blcrm" database
    When I select given ENUM "<Enums>"  table
    When Click at search button
    When Click at add lookup button
    Then I enter values for "<Value>", "<Description>","<Scope>","<ReportLabel>","<OrderByDefault>" and "<StartDate>"
    When Click at add lookup record button
    Then Then the "Look Up Value Successfully Created" Success Message is displayed
    Examples:
      | Enums      | Value  | Description | ReportLabel | Scope        | OrderByDefault | StartDate |
      | ENUM_DUMMY | valABC | Desc12345   | labelABC    | test1, test2 | 10             | today     |

  @CP-34414 @CP-34414-04 @CP-42308 @mital @tm-regression @ui-tm
  Scenario Outline: 3.0 Update the ENUM record
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    When I expand the project "BLCRM" to view the details
    And I click on the configuration icon for the project
    And Navigate to lookup configuration page
    Then Verify that usr is able to select the "mars-contact-record-blcrm" database
    When I select given ENUM "ENUM_DUMMY"  table
    When Click at search button
    When Click at add lookup button
    Then I enter values for "<Value>", "<Description>","<ReportLabel>","<Scope>","<OrderByDefault>" and "<StartDate>"
    When Click at add lookup record button
    Then Then the "Look Up Value Successfully Created" Success Message is displayed
    And I click on edit button to edit look up entry
    And I verify the description field is editable
    Then I enter "<ReportLabelUpdate>","<ScopeUpdate>","<OrderByDefaultUpdate>","<DescriptionUpdate>" to update enum record
    When Click at add lookup record button
    Then Then the "Look Up Value Successfully Edited" Success Message is displayed
    Examples:
      | Value  | Description | ReportLabel | Scope       | OrderByDefault | StartDate | ReportLabelUpdate |DescriptionUpdate| ScopeUpdate | OrderByDefaultUpdate |
      | valABC | Desc12345   | labelABC    | test1,test2 | 10             | today     | labelvwXYZ        |Desc0000         | test3,test4 | 13                   |

  @CP-26843 @CP-26843-01 @mital @tm-regression @ui-tm
  Scenario: 1.0 Search database and enum tables
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on configure button and select Look Up
    Then Verify that usr is able to select the "mars-contact-record-blcrm" database
    When I select any ENUM "ENUM_DUMMY"  table
    When Click at search button
    Then the existing records for that ENUM are displayed in a grid with the following fields populated:
      | VALUE            |
      | DESCRIPTION      |
      | REPORT LABEL     |
      | SCOPE            |
      | ORDER BY DEFAULT |
      | START DATE       |
      | END DATE         |
      |[blank]|

  @CP-26843 @CP-26843-02 @mital
    #removing regression tag as it is impacting other SRE team
  Scenario Outline: 2.0 Add the ENUM record
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on configure button and select Look Up
    Then Verify that usr is able to select the "mars-contact-record-blcrm" database in look up
    When I select any ENUM "ENUM_DUMMY"  table
    When Click at search button
    When Click at add lookup button
    Then I enter values for "<Value>", "<Description>","<Scope>","<ReportLabel>","<OrderByDefault>" and "<StartDate>"
    When Click at add lookup record button
    Then Then the "Look Up Value Successfully Created" Success Message is displayed
    Examples:

      | Value  | Description | Scope    | ReportLabel | OrderByDefault | StartDate |
      | valABCD | Desc1234567  | labelxyz | test3,test4 | 10             | today     |

  @CP-26843 @CP-26843-03 @mital @tm-regression @ui-tm
  Scenario Outline: 2.0 Update the ENUM record
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I click on configure button and select Look Up
    Then Verify that usr is able to select the "mars-contact-record-blcrm" database in look up
    When I select any ENUM "ENUM_DUMMY"  table
    When Click at search button
    When Click at add lookup button
    Then I enter values for "<Value>", "<Description>","<Scope>","<ReportLabel>","<OrderByDefault>" and "<StartDate>"
    When Click at add lookup record button
    Then Then the "Look Up Value Successfully Created" Success Message is displayed
    And I click on edit button to edit look up entry
    Then I enter "<ReportLabelUpdate>","<ScopeUpdate>","<OrderByDefaultUpdate>","<DescriptionUpdate>" to update enum record
    When Click at add lookup record button
    Then Then the "Look Up Value Successfully Edited" Success Message is displayed
    Examples:
      | Value  | Description | ReportLabel | Scope       | OrderByDefault | StartDate | ReportLabelUpdate | ScopeUpdate | OrderByDefaultUpdate |"<DescriptionUpdate>"|
      | valABC | Desc12345   | labelABC    | test1,test2 | 10             | today     | labelvwXYZ        | test3,test4 | 13                   |Desc0000|