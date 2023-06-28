@plan_edit_contact
Feature: View Plan Details After Search via Tenant Manager UI

  @CP-942 @CP-942-01 @planProvider @tm-regression @aswath @ui-pp
  Scenario: Display of Service Region Information
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
#    When I upload the Service Region file
    Then I upload the plan file for service regions "File Normal"
    And I attach file to Service regions tab
    And I click on the upload button on service config
#    Then I click on Plans Configuration tab
#    When I upload the Plan Configuration file
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "BLCRM Plan config"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen

  @CP-942 @CP-942-02 @CP-942-03 @planProvider @tm-smoke @tm-regression @aswath @ui-pp
  Scenario: Display of Plan Information
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    Given I get the jwt token TM
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
#    When I upload the Service Region file
    Then I upload the plan file for service regions "File Normal"
    And I attach file to Service regions tab
    And I click on the upload button on service config
#    Then I click on Plans Configuration tab
#    When I upload the Plan Configuration file
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "BLCRM Plan config"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
  Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
#    And I am brought to the Plan Details Screen
    Then I see three tabs dividing the Plan Details screen
    Then I am on the Plan Details Tab by default


#  @CP-942 @CP-942-03 @planProvider @tm-regression @aswath @ui-pp
#  Scenario: Default Tab View
#    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
#    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
#    And I navigate to Project Configuration
#    And I select Plans Management
#    Then I am on the Service Region Configuration screen
##    When I upload the Service Region file
#    Then I upload the plan file for service regions "File Normal"
#    And I attach file to Service regions tab
#    And I click on the upload button on service config
##    Then I click on Plans Configuration tab
##    When I upload the Plan Configuration file
#    And I click on PLANS CONFIG Tab
#    Then I choose a file to upload in Plan Config tab "BLCRM Plan config"
#    And I attach file to Plan Config tab
#    And I Click on Plan Config tab Upload button
#    Then I can make a valid search of plans
#    And I can sort results by ascending or descending order
#    When I click on a Plan Name search result
#  #  And I am brought to the Plan Details Screen
#    And I see three tabs dividing the Plan Details screen
#    Then I am on the Plan Details Tab by default

  @CP-942 @CP-942-04 @planProvider @tm-regression @aswath @ui-pp
  Scenario: Plan Details Tab - Plan Information Container
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
#    When I upload the Service Region file
    Then I upload the plan file for service regions "File Normal"
    And I attach file to Service regions tab
    And I click on the upload button on service config
#    Then I click on Plans Configuration tab
#    When I upload the Plan Configuration file
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "BLCRM Plan config"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
#    And I am brought to the Plan Details Screen
    Then I see the appropriate fields in the Plan Information container

  @CP-942 @CP-942-05 @planProvider @tm-regression @aswath @ui-pp
  Scenario: Plan Details Tab - Contract Information Container
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
#    When I upload the Service Region file
    Then I upload the plan file for service regions "File Normal"
    And I attach file to Service regions tab
    And I click on the upload button on service config
#    Then I click on Plans Configuration tab
#    When I upload the Plan Configuration file
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "BLCRM Plan config"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
#    And I am brought to the Plan Details Screen
    Then I see the appropriate fields in the Contract Information container

  @CP-942 @CP-942-06 @planProvider @tm-regression @aswath @ui-pp
  Scenario: Plan Details Tab - Enrollment Information Container
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
#    When I upload the Service Region file
    Then I upload the plan file for service regions "File Normal"
    And I attach file to Service regions tab
    And I click on the upload button on service config
#    Then I click on Plans Configuration tab
#    When I upload the Plan Configuration file
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "BLCRM Plan config"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
#    And I am brought to the Plan Details Screen
    Then I see the appropriate fields in the Enrollment Information container

  @CP-942 @CP-942-07 @planProvider @tm-regression @aswath @ui-pp
  Scenario: Plan Details Tab - Enrollment Information Container
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
#    When I upload the Service Region file
    Then I upload the plan file for service regions "File Normal"
    And I attach file to Service regions tab
    And I click on the upload button on service config
#    Then I click on Plans Configuration tab
#    When I upload the Plan Configuration file
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "BLCRM Plan config"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
#    And I am brought to the Plan Details Screen
    And I click on the Contact Details tab
    And I am brought to the Contact Details screen
    Then I see the appropriate fields in the Plan Mailing Address container

  @CP-942 @CP-942-08 @planProvider @tm-regression @aswath @ui-pp
  Scenario: Plan Details Tab - Contact Information Container
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager and set the project context "project" value "SelectBLCRMConfig"
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
#    When I upload the Service Region file
    Then I upload the plan file for service regions "File Normal"
    And I attach file to Service regions tab
    And I click on the upload button on service config
#    Then I click on Plans Configuration tab
#    When I upload the Plan Configuration file
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "BLCRM Plan config"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
#    And I am brought to the Plan Details Screen
    And I click on the Contact Details tab
    And I am brought to the Contact Details screen
    Then I see the appropriate fields in the Contact Information container