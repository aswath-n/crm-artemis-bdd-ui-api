@validatePlanConfig
Feature: Validate Plan Configuration File Upon Upload

  @CP-3513 @CP-3513-01 @planProvider @albert @ui-pp
  Scenario Outline: Basic Search Parameters
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
      | FileType                   | MessageType                   |
      | Plan Name Absent           | Plan Name Req Error           |
      | Plan Code Absent           | Plan Code Req Error           |
      | Program Type Absent        | Program Type Req Error        |
      | Contract Start Date Absent | Contract Start Date Req Error |
      | All Required Fields Absent | All Req Fields Error          |

  @CP-3513 @CP-3513-02 @planProvider @albert @ui-pp
  Scenario Outline: Validate the field length / format of the listed fields
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
      | FileType                        | MessageType                   |
      | Plan Name Invalid Format        | Plan Name Format Error        |
      | Plan Code Invalid Format        | Plan Code Format Error        |
      | Program Type Invalid Format     | Program Type Format Error     |
      | Contract Start Invalid Format   | Contract Start Format Error   |
      | Plan Short Name Invalid Format  | Plan Short Name Format Error  |
      | SubProgram Type Invalid Format  | SubProgram Type Format Error  |
      | Contract End Invalid Format     | Contract End Format Error     |
#      | Enrollment Start Invalid Format | Enrollment Start Format Error |              -----------Implementation Change according to CP-8999
#      | Eligibility Invalid Format      | Eligibility Format Error      |


  @CP-3513 @CP-3513-03 @planProvider @albert @ui-pp
  Scenario Outline: Validate the unique plan code
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
      | FileType                | MessageType                   |
      | Plan Code Duplicate Row | Plan Code Duplicate Row Error |

  @CP-3513 @CP-3513-04 @planProvider @albert @ui-pp
  Scenario Outline: Validate the  program  code w.r.t DB
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
      | FileType    | MessageType       |
      | Program DNE | Program DNE Error |

  @CP-3513 @CP-3513-05 @planProvider @albert @ui-pp
  Scenario Outline: Validate the  Sub-program  code w.r.t DB
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
      | FileType       | MessageType          |
      | SubProgram DNE | SubProgram DNE Error |

  @CP-3513 @CP-3513-07 @planProvider @albert @ui-pp
  Scenario Outline: Validate the Service Delivery Area   code w.r.t DB
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
      | FileType           | MessageType              |
      | Service Region DNE | Service Region DNE Error |


  @CP-3513 @CP-3513-08 @planProvider @albert @ui-pp
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

  @CP-3513 @CP-3513-09 @planProvider @albert @ui-pp
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

  @CP-3513 @CP-3513-10 @planProvider @albert @ui-pp
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

  @CP-3513 @CP-3513-11 @planProvider @albert @ui-pp
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

  @CP-3513 @CP-3513-12 @planProvider @albert @ui-pp
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

  @CP-3513 @CP-3513-13 @planProvider @albert @ui-pp
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

#  @CP-3513 @CP-3513-14 @planProvider @albert @ui-pp  --------------------> Repeating scenario || Implementation change according to CP-8999
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

#  @CP-3513 @CP-3513-15 @planProvider @albert @ui-pp --------------------> Repeating scenario || Implementation change according to CP-8999
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

  @CP-3513 @CP-3513-16 @planProvider @albert @ui-pp
  Scenario Outline: Validate the Hierarchy
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
      | FileType                                                          | MessageType                                              |
      | Plan Configuration File name must start with the word Plan        | Plan Configuration File Name Error                       |
      | Plan File Sheet Names                                             | Plan File Sheet Name Error                               |
      | Plan File Sheet Order                                             | Plan File Sheet Order Error                              |
      | Plan Required Information Error                                   | Contract Start Format Error                              |
      | Plan Program comparison to Database                               | Program DNE Error                                        |
      | Plan Sub-Program comparison to Database                           | SubProgram DNE Error                                     |
      | Plan Service Delivery Area Comparison to Database                 | Service Region DNE Error                                 |
      | Plan County Name Comparison to Database                           | County DNE Error                                         |
      | Plan Zip Code Comparison to Database                              | Zip Code DNE Error                                       |
      | Plan City Comparison to Database                                 | City DNE Error                                           |
      | Plan Contract End Date Validation - Contract Start Date Check     | Contract End Date Precedes Contract Start Date Error     |
      | Plan Enrollment Start Date Validation - Contract Start Date Check | Enrollment Start Date Precedes Contract Start Date Error |
      | Plan Enrollment End Date Validation                               | Enrollment End Date Precedes Enrollment Start Date Error |
#      | Value Added Services Sheet - Plan Code Provided                   | Value Added Services Error                               |               --------------------> Implementation change according to CP-8999
#      | Eligibility Invalid Format                                        | Eligibility Format Error                                 |               --------------------> Implementation change according to CP-8999

  @CP-20538 @CP-20538-01 @CP-20538-02 @CP-43052 @CP-43052-01 @CP-43052-02 @planProvider @mital @ui-pp   #This story CP-43052 is an update to the old functionality
  Scenario: Validate the Program Type and Subprogram type when region and plan files are uploaded
    Given I logged into Tenant Manager and set the project context "project" value "BLCRM"
    And I update change the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    Then I upload the plan file for service regions "Service Region SubProgram Type with Special Characters BLCRM"
    And I attach file to Service regions tab
    And I click on the upload button on service config
    Then I verify the file upload "Upload Success" message
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "Plan SubProgram Type with Special Characters BLCRM"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "Upload Plan Success" message
    When I click on Program Type
    Then I view Program Types in specific format
    When I click on Subprogram Type
    Then I view Subprogram Types in specific format