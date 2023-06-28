@CP-17532
Feature:Augment Outbound Correspondence Settings for Project part2

  @CP-17532-01  @ui-ecms1 @Keerthi
  Scenario:Validate the TTY Number field
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    #When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Then I entered "1234567890" data in the TTY Number field
    And Click at Save button
    Then Verify the success message "Correspondence Configuration successfully updated."
    And Click at edit button
    Then I validate "1234567890" data in the TTY Number field

  @CP-17532-02  @ui-ecms1 @Keerthi
  Scenario:Validate the TTY Number field warning message
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    #When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Then I validate warning message for TTY Number field format

  @CP-17532-03  @ui-ecms1 @Keerthi
  Scenario: Validate the OUTBOUND PRINT FILE FTP DETAILS are removed
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
   # When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    Then I validate OUTBOUND PRINT FILE FTP DETAILS are removed

  @CP-17532-04  @ui-ecms1 @Keerthi
  Scenario: Validate the SMTP Email User field with valid data
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
   # When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Then I validate SMTP Email "user" field with data "Test@user@#$%^$"
    Then Verify the success message "Correspondence Configuration successfully updated."


  @CP-17532-05  @ui-ecms1 @Keerthi
  Scenario: Validate the SMTP Email User field with invalid data
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
   # When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Then I validate SMTP Email "user" field with data "Test@user  @#$%^$"
    Then Verify the success message "Correspondence Configuration successfully updated."
    And Click at edit button
    Then I validate SMTP Email "user" field with invalid data


  @CP-17532-06  @ui-ecms2 @Keerthi
  Scenario: Validate the SMTP Email Password field with valid data
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
   # When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Then I validate SMTP Email "password" field with data "Test@user@#$%^$"
    Then Verify the success message "Correspondence Configuration successfully updated."


  @CP-17532-07  @ui-ecms2 @Keerthi
  Scenario: Validate the SMTP Email Password field with invalid data
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    #When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Then I validate SMTP Email "password" field with data "Test@  user@#$%^$"
    Then Verify the success message "Correspondence Configuration successfully updated."
    And Click at edit button
    Then I validate SMTP Email "password" field with invalid data

  @CP-17532-08  @ui-ecms2 @Keerthi
  Scenario: Validate the Receipent Setting label spelling
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    #When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    Then I validate Receipent Setting field label

  @CP-17532-09  @ui-ecms2 @Keerthi
  Scenario: Validate the update By field value
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    #When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    And Click at Save button
    Then I validate update By field value

  @CP-17532-10  @ui-ecms2 @Keerthi
  Scenario: Validate the update On field value
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    #When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    And Click at Save button
    Then I validate update On field value

  @CP-17532-11  @ui-ecms2 @Keerthi
  Scenario:Validate the TTY Number field
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
   # When I search for a project by "program" value "Baseline Program"
    When I expend a Project to view the details
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Then I entered "" data in the TTY Number field
    And Click at Save button
    Then Verify the success message "Correspondence Configuration successfully updated."
    And Click at edit button
    Then I validate "" data in the TTY Number field