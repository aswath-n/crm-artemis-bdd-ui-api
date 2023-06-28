Feature: Validate Plan Configuration

  @CP-3513 @CP-3513-08 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline:Validate the Plan county  code w.r.t DB
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    And I click on Plans Configuration tab
    When I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message

    Examples:
      | FileType   | MessageType      |
      | County DNE | County DNE Error |

  @CP-3513 @CP-3513-09 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline:Validate the Plan Zip code w.r.t DB
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    And I click on Plans Configuration tab
    When I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message

    Examples:
      | FileType     | MessageType        |
      | Zip Code DNE | Zip Code DNE Error |

  @CP-3513 @CP-3513-10 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline:Validate the City  code w.r.t DB
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    And I click on Plans Configuration tab
    When I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message

    Examples:
      | FileType | MessageType    |
      | City DNE | City DNE Error |

  @CP-3513 @CP-3513-11 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline:Validate the Contract End Date   w.r.t DB
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    And I click on Plans Configuration tab
    When I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message

    Examples:
      | FileType                                       | MessageType                                          |
      | Contract End Date Precedes Contract Start Date | Contract End Date Precedes Contract Start Date Error |

  @CP-3513 @CP-3513-12 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline:Validate the Enrollment Start Date   w.r.t DB
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    And I click on Plans Configuration tab
    When I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message

    Examples:
      | FileType                                           | MessageType                                              |
      | Enrollment Start Date Precedes Contract Start Date | Enrollment Start Date Precedes Contract Start Date Error |

  @CP-3513 @CP-3513-13 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline:Validate the Enrollment End Date   w.r.t DB
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    And I click on Plans Configuration tab
    When I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message

    Examples:
      | FileType                                           | MessageType                                              |
      | Enrollment End Date Precedes Enrollment Start Date | Enrollment End Date Precedes Enrollment Start Date Error |

#  @CP-3513 @CP-3513-14 @planProvider @albert @tm-regression @ui-pp  ------------------Implementation change according to CP-8999
  Scenario Outline:Validate the Value Added Services Sheet - Plan  code w.r.t DB
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    And I click on Plans Configuration tab
    When I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message

    Examples:
      | FileType                     | MessageType                |
      | Value Added Services Missing | Value Added Services Error |

#  @CP-3513 @CP-3513-15 @planProvider @albert @tm-regression @ui-pp ------------------Implementation change according to CP-8999
  Scenario Outline:Validate the Eligibility Limitations Sheet - Plan  code w.r.t DB
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    And I click on Plans Configuration tab
    When I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message

    Examples:
      | FileType                        | MessageType              |
      | Eligibility Limitations Missing | Eligiblity limit Missing |

#  @CP-3513 @CP-3513-16 @planProvider @albert @tm-regression @ui-pp
#  Scenario Outline: Validate the Hierarchy
#    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
#    And I verify the Go live date
#    And I navigate to Project Configuration
#    And I select Plans Management
#    Then I am on the Service Region Configuration screen
#    When I upload the service region file before to plan file
#    And I click on Plans Configuration tab
#    When I choose a file to upload in Plan Config tab "<FileType>"
#    And I attach file to Plan Config tab
#    And I Click on Plan Config tab Upload button
#    Then I verify the file upload "<MessageType>" message
#    Examples:
#      | FileType                                                          | MessageType                                              |
#      | Plan Configuration File name must start with the word Plan        | Plan Configuration File Name Error                       |
#      | Plan File Sheet Names                                             | Plan File Sheet Name Error                               |
#      | Plan File Sheet Order                                             | Plan File Sheet Order Error                              |
#      | Plan Required Information Error                                   | Contract Start Format Error                              |
#      | Plan Program comparison to Database                               | Program DNE Error                                        |
#      | Plan Sub-Program comparison to Database                           | SubProgram DNE Error                                     |
#      | Plan Service Delivery Area Comparison to Database                 | Service Region DNE Error                                 |
#      | Plan County Name Comparison to Database                           | County DNE Error                                         |
#      | Plan Zip Code Comparison to Database                              | Zip Code DNE Error                                       |
#      | Plan City Comparison to Databaset                                 | City DNE Error                                           |
#      | Plan Contract End Date Validation - Contract Start Date Check     | Contract End Date Precedes Contract Start Date Error     |
#      | Plan Enrollment Start Date Validation - Contract Start Date Check | Enrollment Start Date Precedes Contract Start Date Error |
#      | Plan Enrollment End Date Validation                               | Enrollment End Date Precedes Enrollment Start Date Error |
#      | Value Added Services Sheet - Plan Code Provided                   | Value Added Services Error                               |
#      | Eligibility Invalid Format                                        | Eligibility Format Error                                 |