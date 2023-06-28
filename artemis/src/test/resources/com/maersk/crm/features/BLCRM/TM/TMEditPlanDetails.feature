@Re_Upload_File
Feature: Edit Plan - Plan Details Tab via Tenant Manager UI

  @CP-740 @CP-740-01 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Edit Buttons Viewable on Plan Details Tab
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    And I am brought to the Plan Details Screen
    Then I will see an edit button within each of the Plan Details containers
    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |


  @CP-740 @CP-740-02 @planProvider @albert @tm-smoke @tm-regression @ui-pp
  Scenario Outline: User Restricted to Editing Fields in only one Container at a time
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    And I am brought to the Plan Details Screen
    Then I will see an edit button within each of the Plan Details containers
    And I can edit fields in only one container at a time

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |


  @CP-740 @CP-740-03 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: User Restricted to Editing Fields in only one Container at a time
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    And I am brought to the Plan Details Screen
    Then I will see an edit button within each of the Plan Details containers
    And I can edit fields in only one container at a time

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |


  @CP-740 @CP-740-04 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: User Restricted to Editing Fields in only one Container at a time
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    And I am brought to the Plan Details Screen
    Then I will see an edit button within each of the Plan Details containers
    And I can edit fields in only one container at a time

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |

  @CP-740 @CP-740-05 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Updating fields in the Plan Information Container
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Plan Information container edit button
    Then I will be able to Exclude Plan From Auto-Assignment

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |

  @CP-740 @CP-740-07 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Updating fields in the Contract Information Container
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Contract Information container edit button
    Then I can update the Contract End Date

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |

  @CP-740 @CP-740-08 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Plan Contract End Date Validation - Contract Start Date Check
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Contract Information container edit button
    And I provide a Contract End Date that is not greater than the Contract Start Date
    Then I will receive the appropriate Contract Start Date error message

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |

  @CP-740 @CP-740-09 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline:  Plan Contract End Date Validation - System Date Check
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Contract Information container edit button
    And I provide a Contract End Date that precedes the System Date
    Then I will receive the appropriate Contract Start Date error message

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |

  @CP-740 @CP-740-10 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Updating fields in the Enrollment Information Container
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Enrollment Information container edit button
    Then I will be able to update the appropriate fields in the Enrollment Information container

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |

#  @CP-740 @CP-740-11 @planProvider @albert @tm-regression @ui-pp
#  Scenario Outline: Enrollment Start Date Validation               ---------> GA Regression
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
#    And I verify the file upload "<MessageType>" message
#    Then I can make a valid search of plans
#    And I can sort results by ascending or descending order
#    When I click on a Plan Name search result
#    Then I am brought to the Plan Details Screen
#    When I click on the Enrollment Information container edit button
#    And I provide an Enrollment Start Date that precedes the Contract Start Date
#    Then I will receive the appropriate Enrollment Start Date error message
#
#    Examples:
#      | FileType       | MessageType         |
#      | GA Plan Config | Upload Plan Success |

#  @CP-740 @CP-740-12 @planProvider @albert @tm-regression @ui-pp       ----------------->GA Regression script
#  Scenario Outline:  Enrollment End Date Validation
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
#    And I verify the file upload "<MessageType>" message
#    Then I can make a valid search of plans
#    And I can sort results by ascending or descending order
#    When I click on a Plan Name search result
#    Then I am brought to the Plan Details Screen
#    When I click on the Enrollment Information container edit button
#    And I provide an Enrollment End Date that is not greater then the Enrollment Start Date
#    Then I will receive the appropriate Enrollment End Date error message
#
#    Examples:
#      | FileType       | MessageType         |
#      | GA Plan Config | Upload Plan Success |
#NOTE: The Error Msg. as given in the functional test case is "Enrollment end date is not greater than the Enrollment start date"
  # But the actual Error Msg. displayed reads as "The end date cannot be in the past"

#NOTE : -13 is regularly triggering redirect to "Ooops. Something went wrong." page
  @CP-740 @CP-740-13 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Save & Success Message - Plan Information
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Plan Information container edit button
    And I input and save changes to the given Plan Information fields
    Then I see the Plan Information changes were saved
    And I am returned to the Plan Detail read-only screen
    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |

  @CP-740 @CP-740-14 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Save & Success Message - Contract Information
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Contract Information container edit button
    And I input and save changes to the given Contract Information fields
    Then I see the Contract Information changes were saved
    And I am returned to the Plan Detail read-only screen

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |

  @CP-740 @CP-740-15 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Save & Success Message - Enrollment Information
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Enrollment Information container edit button
    And I input and save changes to the given Enrollment Information fields
    Then I see the Enrollment Information changes were saved
    And I am returned to the Plan Detail read-only screen

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |
#
##NOTE :This failure is legitimate -- the cancellation of the edit is not clearing the change made to the checkbox -- see CP740-16_issueCapture_02112020.webm for documentation
#  @CP-740 @CP-740-16 @planProvider @albert @tm-regression
#  Scenario Outline: Cancel & Warning Message - Plan Information
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
#    And I verify the file upload "<MessageType>" message
#    Then I can make a valid search of plans
#    And I can sort results by ascending or descending order
#    When I click on a Plan Name search result
#    Then I am brought to the Plan Details Screen
#    When I click on the Plan Information container edit button
#    And I input and cancel changes to the given Plan Information fields
#    Then I see the Plan Information changes were cleared
#    And I am returned to the Plan Detail read-only screen
#    Examples:
#      | FileType             | MessageType         |
#      | GA Plan Config | Upload Plan Success |
#
#   #NOTE :This failure is legitimate -- the cancellation of the edit is not clearing the change made to the checkbox -- see CP740-16_issueCapture_02112020.webm for documentation
#  @CP-740 @CP-740-17 @planProvider @albert  @tm-regression
#  Scenario Outline: Cancel & Warning Message - Contract Information
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
#    And I verify the file upload "<MessageType>" message
#    Then I can make a valid search of plans
#    And I can sort results by ascending or descending order
#    When I click on a Plan Name search result
#    Then I am brought to the Plan Details Screen
#    When I click on the Contract Information container edit button
#    And I input and cancel changes to the given Contract Information fields
#    Then I see the Contract Information changes were cleared
#    Examples:
#      | FileType             | MessageType         |
#      | GA Plan Config | Upload Plan Success |
#
#   #NOTE :This failure is legitimate -- the cancellation of the edit is not clearing the change made to the checkbox -- see CP740-16_issueCapture_02112020.webm for documentation
#  @CP-740 @CP-740-18 @planProvider @albert @tm-regression
#  Scenario Outline: Cancel & Warning Message - Enrollment Information
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
#    And I verify the file upload "<MessageType>" message
#    Then I can make a valid search of plans
#    And I can sort results by ascending or descending order
#    When I click on a Plan Name search result
#    Then I am brought to the Plan Details Screen
#    When I click on the Enrollment Information container edit button
#    And I input and cancel changes to the given Enrollment Information fields
#    Then I see the Enrollment Information changes were cleared
#    And I am returned to the Plan Detail read-only screen
#    Examples:
#      | FileType             | MessageType         |
#      | GA Plan Config | Upload Plan Success |
#
##NOTES ON -19 -20 -21 : These Failures are legitimate -- Back Arrow does not return User to the Plan Detail Read Only Screen -- Back Arrow returns User to Plans Configuration Search page
#  @CP-740 @CP-740-19 @planProvider @albert @tm-regression
#  Scenario Outline: Back Arrow & Warning Message - Plan Information
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
#    And I verify the file upload "<MessageType>" message
#    Then I can make a valid search of plans
#    And I can sort results by ascending or descending order
#    When I click on a Plan Name search result
#    Then I am brought to the Plan Details Screen
#    When I click on the Plan Information container edit button
#    And I input changes to the given Plan Information fields and click the Back Arrow
#    Then I see the Plan Information changes were cleared
#    And I am returned to the Plan Detail read-only screen
#    Examples:
#      | FileType             | MessageType         |
#      | GA Plan Config | Upload Plan Success |
#
##NOTES ON -19 -20 -21 : These Failures are legitimate -- Back Arrow does not return User to the Plan Detail Read Only Screen -- Back Arrow returns User to Plans Configuration Search page
#  @CP-740 @CP-740-20 @planProvider @albert @tm-regression
#  Scenario Outline: Back Arrow & Warning Message - Contract Information
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
#    And I verify the file upload "<MessageType>" message
#    Then I can make a valid search of plans
#    And I can sort results by ascending or descending order
#    When I click on a Plan Name search result
#    Then I am brought to the Plan Details Screen
#    When I click on the Contract Information container edit button
#    And I input changes to the given Contract Information fields and click the Back Arrow
#    Then I see the Contract Information changes were cleared
#    And I am returned to the Plan Detail read-only screen
#    Examples:
#      | FileType             | MessageType         |
#      | GA Plan Config | Upload Plan Success |
#
##NOTES ON -19 -20 -21 : These Failures are legitimate -- Back Arrow does not return User to the Plan Detail Read Only Screen -- Back Arrow returns User to Plans Configuration Search page
#  @CP-740 @CP-740-21 @planProvider @albert @tm-regression
#  Scenario Outline: Back Arrow & Warning Message - Enrollment Information
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
#    And I verify the file upload "<MessageType>" message
#    Then I can make a valid search of plans
#    And I can sort results by ascending or descending order
#    When I click on a Plan Name search result
#    Then I am brought to the Plan Details Screen
#    When I click on the Enrollment Information container edit button
#    And I input changes to the given Enrollment fields and click the Back Arrow
#    Then I see the Enrollment Information changes were cleared
#    And I am returned to the Plan Detail read-only screen
#
#    Examples:
#      | FileType             | MessageType         |
#      | GA Plan Config | Upload Plan Success |

#NOTES ON -22 -23 -24 : PASSING Navigation Warning Message does not appear if user selects 'CONTACT DETAILS' or 'VALUE ADDED SERVICES' tabs
  @CP-740 @CP-740-22 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Navigate away & Warning Message - Plan Information
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Plan Information container edit button
    And I input changes to the given Plan Information fields and navigate away
    Then I will be navigated to the given page

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |


#NOTES ON -22 -23 -24 : PASSING Navigation Warning Message does not appear if user selects 'CONTACT DETAILS' or 'VALUE ADDED SERVICES' tabs
  @CP-740 @CP-740-23 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Navigate away & Warning Message - Contract Information
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Contract Information container edit button
    And I input changes to the given Contract Information fields and navigate away
    Then I will be navigated to the given page

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |


#NOTES ON -22 -23 -24 : PASSING Navigation Warning Message does not appear if user selects 'CONTACT DETAILS' or 'VALUE ADDED SERVICES' tabs
  @CP-740 @CP-740-24 @planProvider @albert @tm-regression @ui-pp
  Scenario Outline: Navigate away & Warning Message - Enrollment Information
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
    And I verify the file upload "<MessageType>" message
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Enrollment Information container edit button
    And I input changes to the given Enrollment Information fields and navigate away
    Then I will be navigated to the given page

    Examples:
      | FileType       | MessageType         |
      | GA Plan Config | Upload Plan Success |

#NOTE : Failure is Legitimate -- The Invalid Input results in a Success Message instead of an Error Message -- see CP740-25_errorSnippet
#  @CP-740 @CP-740-25 @planProvider @albert @tm-regression
#  Scenario Outline: Plan Contract End Date Validation - Contract Start Date Check
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
#    And I verify the file upload "<MessageType>" message
#    Then I can make a valid search of plans
#    And I can sort results by ascending or descending order
#    When I click on a Plan Name search result
#    Then I am brought to the Plan Details Screen
#    When I click on the Contract Information container edit button
#    And I provide a Contract End Date of incorrect length
#    Then I will receive the appropriate Invalid Format error message
#    Examples:
#      | FileType             | MessageType         |
#      | GA Plan Config | Upload Plan Success |



