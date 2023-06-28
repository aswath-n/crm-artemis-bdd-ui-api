@regionUpload
Feature: Tenant Manager: Region Management

  @planProvider @planManagement @CP-705 @CP-705-01 @tm-regression @aswath @ui-pp
  Scenario Outline: --------
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I verify the "<PlanType>" page fields

    Examples:
      | PlanType |
      | Service  |
      | Plan     |

  @planProvider @planManagement @CP-705 @CP-705-02 @CP-2453 @CP-2453-01 @CP-3376 @CP-3376-01 @tm-regression @aswath @ui-pp
  Scenario Outline: Verification of Plan Config File Upload function for different files
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I click on the upload button on service config
    Then I verify the file upload "<MessageType>" message

    Examples:
      | FileType        | MessageType    |
      | File Subprogram | Upload Success |
      | File Failure    | Upload Failed  |
      | File Success    | Upload Success |
      | File Extension  | File Extension |
      | No File         | No File        |


  @planProvider @planManagement @CP-705 @CP-705-03 @tm-regression @aswath @ui-pp
  Scenario Outline:Verification of Cancel & Warning Message
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I Click on Service regions tab Cancel button
    Then I verify the file upload "<Message Type>" message

    Examples:
      | FileType     | Message Type |
      | File Success | Warning      |

  @planProvider @planManagement @CP-705 @CP-705-04 @tm-regression @aswath @ui-pp
  Scenario Outline:Verification of Continue button in Warning Message Popup when click on Cancel button
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I Click on Service regions tab Cancel button
    And I click on the continue button on warning pop up
    Then I verify remain on the Service region configuration Page and the previously chosen file Should be cleared

    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-705 @CP-705-05 @tm-smoke @tm-regression @aswath @ui-pp
  Scenario Outline:Verification of Cancel button in Warning Message Popup when click on Cancel button
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I Click on Service regions tab Cancel button
    And I click on the cancel button on warning pop up
    Then I verify remain on the Service region configuration Page and the previously chosen file Should not be cleared

    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-705 @CP-705-06 @tm-regression @aswath @ui-pp
  Scenario Outline:Verification of Back Arrow icon functionality
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I click on the back arrow button
    Then I verify the file upload "<Message Type>" message

    Examples:
      | FileType     | Message Type |
      | File Success | Warning      |

  @planProvider @planManagement @CP-705 @CP-705-07 @tm-regression @aswath @ui-pp
  Scenario Outline:Verification of Continue button in Warning Message Popup on clicked of back arrow
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I click on the back arrow button
    And I click on the continue button on warning pop up
    Then I verify Project List Page is displayed

    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-705 @CP-705-08 @tm-regression @aswath @ui-pp
  Scenario Outline:Verification of Cancel button present in Back Arrow & Warning Message Popup
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I click on the back arrow button
    And I click on the cancel button on warning pop up
    Then I verify remain on the Service region configuration Page and the previously chosen file Should not be cleared

    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-705 @CP-705-09 @tm-regression @aswath @ui-pp
  Scenario:Validation of Back Arrow without selecting any file
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on the back arrow button
    Then I verify Project List Page is displayed

  @planProvider @planManagement @CP-705 @CP-705-10 @tm-regression @aswath @ui-pp
  Scenario Outline:Verification of Navigate away & Warning Message
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I click on the task type link
    Then I verify the file upload "<Message Type>" message

    Examples:
      | FileType     | Message Type |
      | File Success | Warning      |

  @planProvider @planManagement @CP-705 @CP-705-11 @tm-regression @aswath @ui-pp
  Scenario Outline:Verification of Continue button in Warning Message Popup when navigate away
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I click on the task type link
    And I click on the continue button on warning pop up
    Then I verify should I navigate to page for which I selected

    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-705 @CP-705-12 @tm-regression @aswath @ui-pp
  Scenario Outline:Verification of Cancel button in Warning Message Popup when navigate away
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I click on the task type link
    And I click on the cancel button on warning pop up
    Then I verify remain on the Service region configuration Page and the previously chosen file Should not be cleared

    Examples:
      | FileType     |
      | File Success |