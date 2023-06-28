@planUpload
Feature: Plan Config File Upload function

  @CP-706 @CP-706-02 @CP-796 @CP-796-01 @CP-1605 @CP-1605-01 @tm-regression @vidya @aswath @ui-pp
  Scenario Outline: Verification of Plan Config File Upload function for different files
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Given I upload the service region file before to plan file
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message
    Examples:
      | FileType                      | MessageType              |
      | File Success                  | Upload                   |
      | File Extension                | File Extension           |
#      | File Failure Column Missing   | Column Missing           |
#      | File Failure EL-PlanCode      | EL plan code Missing     |
      | File Failure Mandatory Fields | Mandatory Field Missing  |
      | No File                       | No File                  |
      | File Size                     | File Size                |
      | File Name                     | File Name                |
      | File Sheet Name               | File Sheet Name          |
      | File Sheet order              | File Sheet order         |
#      | File Failure EL Type          | Eligiblity limit Missing |

  @planProvider @planManagement @CP-706 @CP-706-03 @tm-regression @vidya @ui-pp
  Scenario Outline:Verification of Cancel & Warning Message
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Cancel button
    Then I verify the file upload "<Message Type>" message
    Examples:
      | FileType     | Message Type |
      | File Success | Warning      |

  @planProvider @planManagement @CP-706 @CP-706-04 @tm-regression @vidya @ui-pp
  Scenario Outline:Verification of Continue button in Warning Message Popup when click on Cancel button
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Cancel button
    And I click on the continue button on warning pop up
    Then I verify remain on the Plan configuration Page and the previously chosen file Should be cleared
    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-706 @CP-706-05 @tm-regression @vidya @ui-pp
  Scenario Outline:Verification of Cancel button in Warning Message Popup when click on Cancel button
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Cancel button
    And I click on the cancel button on warning pop up
    Then I verify remain on the Plan configuration Page and the previously chosen file Should not be cleared
    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-706 @CP-706-06 @tm-regression @vidya @ui-pp
  Scenario Outline:Verification of Back Arrow icon functionality
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I click on the back arrow button
    Then I verify the file upload "<Message Type>" message
    Examples:
      | FileType     | Message Type |
      | File Success | Warning      |

  @planProvider @planManagement @CP-706 @CP-706-07 @tm-regression @vidya @ui-pp
  Scenario Outline:Verification of Continue button in Warning Message Popup on clicked of back arrow
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I click on the back arrow button
    And I click on the continue button on warning pop up
    Then I verify Project List Page is displayed
    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-706 @CP-706-08 @tm-regression @vidya @ui-pp
  Scenario Outline:Verification of Cancel button present in Back Arrow & Warning Message Popup
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I click on the back arrow button
    And I click on the cancel button on warning pop up
    Then I verify remain on the Plan configuration Page and the previously chosen file Should not be cleared
    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-706 @CP-706-09 @tm-regression @vidya @ui-pp
  Scenario:Validation of Back Arrow without selecting any file
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    And I click on the back arrow button
    Then I verify Project List Page is displayed

  @planProvider @planManagement @CP-706 @CP-706-10 @tm-regression @vidya @ui-pp
  Scenario Outline:Verification of Navigate away & Warning Message
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I click on the task type link
    Then I verify the file upload "<Message Type>" message
    Examples:
      | FileType     | Message Type |
      | File Success | Warning      |

  @planProvider @planManagement @CP-706 @CP-706-11 @tm-regression @vidya @ui-pp
  Scenario Outline:Verification of Continue button in Warning Message Popup when navigate away
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I click on the task type link
    And I click on the continue button on warning pop up
    Then I verify should I navigate to page for which I selected
    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-706 @CP-706-12 @tm-smoke @tm-regression @vidya @ui-pp
  Scenario Outline:Verification of Cancel button in Warning Message Popup when navigate away
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I click on the task type link
    And I click on the cancel button on warning pop up
    Then I verify remain on the Plan configuration Page and the previously chosen file Should not be cleared
    Examples:
      | FileType     |
      | File Success |

  @planProvider @planManagement @CP-3376 @CP-3376-02 @tm-smoke @tm-regression @aswath @ui-pp
  Scenario Outline: Verification of Service File not Uploaded function for past go live date
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I update change the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I click on the upload button on service config
    Then I verify the file upload "<MessageType>" message
    Examples:
      | FileType     | MessageType       |
      | File Success | File not uploaded |

  @planProvider @planManagement @CP-3376 @CP-3376-03 @tm-regression  @aswath
  Scenario Outline: Verification of Plan File not Uploaded function for past go live date
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I update change the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message
    Examples:
      | FileType     | MessageType       |
      | File Success | File not uploaded |


  @planProvider @planManagement @CP-3376 @CP-3376-04 @tm-regression  @aswath @ui-pp
  Scenario Outline: Verification of Plan Config File Upload function for error message prior to service region file
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectFromConfig"
    And I expand a random project to view the details
    And I verify the Go live date
    Then I upload the service region file before to plan file
    And I click on the configuration icon for the project
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I click on the configuration icon for the project
    And I will upload plan config the without service region
    Then I verify the file upload "<MessageType>" message

    Examples:
      | FileType     | MessageType             |
      | File Success | Uploaded Service Region |

#  @GA-CP-5145 @GA-CP-5145-01 @ga-tm-Regression @aswath @ui-pp
  Scenario Outline: Plan and service configuration for the Georiga project
    Given I logged into Tenant Manager and set the project context "project" value "SelectGAProject"
    And I verify the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the GA service region file before to plan file
    And I click on the Plans Management
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<MessageType>" message
    Examples:
      | FileType       | MessageType         |
      | GA Plan config | Upload Plan Success |

  @CP-2145 @CP-2145-1.1 @CP-2145-1.2 @CP-2145-1.3 @CP-2145-1.4 @CP-2145-1.5 @ga-tm-Regression @ui-pp @sobir
  Scenario Outline: Baseline Validate Service Region Configuration File Upon Upload Prior to Project Go-Live
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand first project to view the details
    And I update change the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<FileType>"
    And I attach file to Service regions tab
    And I click on the upload button on service config
    Then I verify the file upload "<MessageType>" message
    Examples:
      | FileType                           | MessageType                     |
      # 1.1 Validation of Program Type in Service Region File (REQUIRED FIELD)
      | File missing Program Type          | Program Type Req Error          |
      # 1.2 Validation of Sub-Program Type in Service Region File (OPTIONAL FIELD)
      | File missing Sub Program Type      | Upload Success                  |
      # 1.3 Validation of Service Delivery Area in Service Region File (REQUIRED FIELD)
      | File missing Service Delivery Area | Service Delivery Area Req Error |
      # 1.4 Service Region File Extension Error
      | File Extension                     | File Extension                  |
      # 1.5 Unique Row Error
      | File Duplicate Rows                | Duplicate rows Req Error        |
      # Recover to normal file after testing, needs to upload Plan config file too
      | File Normal                        | Upload Plan                     |

  @CP-20760 @ga-tm-Regression @ui-pp @sobir
  Scenario Outline: Baseline Enhancements for Plan Configuration File - Story #2
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand first project to view the details
    And I update change the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "<serviceRegionFile>"
    And I attach file to Service regions tab
    And I click on the upload button on service config
    Then I verify the file upload "<serviceRegionMessage>" message
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "<planConfigFile>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload "<planConfigMessage>" message
    Examples:
      | serviceRegionFile | planConfigFile                                             | serviceRegionMessage | planConfigMessage   |
      | File Normal       | Plan Configuation file missing optional columns            | Upload Success       | Upload Plan Success |
      | File Normal       | Plan Configuation file missing Value Added Services tab    | Upload Success       | Upload Plan Success |
      | File Normal       | Plan Configuation file missing Eligibility Limitations tab | Upload Success       | Upload Plan Success |




  @CP-22230 @CP-22230-01 @ineb-tm-Regression @ui-pp @mital
 Scenario:  Verify that the data contained in Service Region configuration file is loaded
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectINEBConfig"
    And I expand first project to view the details
    And I update change the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "INEB Service Config"
    And I attach file to Service regions tab
    And I click on the upload button on service config
    Then I verify the file upload "Upload Success" message
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "INEB Plan Config"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload " Upload Plan Success " message
    Then I select service region Config Tab
    Then I click on fields and verify results
      |service region name|
      |county name and code|
      |zip code            |

  @CP-22230 @CP-22230-02 @ineb-tm-Regression @ui-pp @mital
Scenario:  Verify that the data contained in Plan configuration file is loaded
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectINEBConfig"
    And I expand first project to view the details
    And I update change the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "INEB Service Config"
   And I attach file to Service regions tab
   And I click on the upload button on service config
    Then I verify the file upload "Upload Success" message
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "INEB Plan Config"
   And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload " Upload Plan Success " message
    Then I click on fields and verify results
      |Plan Name|
      |Plan Code|
      |Service Region         |
      |Program Type          |
      |Sub Program Type       |
      |County-Plan Config                |
      |Zip Code-Plan Config         |

  @CP-22230 @CP-22230-03 @ineb-tm-Regression @ui-pp @mital
Scenario:  Verify that the data contained in Plan configuration file is loaded (Individual Record)
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectINEBConfig"
    And I expand first project to view the details
    And I update change the Go live date
    And I click on the configuration icon for the project
    And I click on the Plans Management
    Then I upload the plan file for service regions "INEB Service Config"
    And I attach file to Service regions tab
    And I click on the upload button on service config
    Then I verify the file upload "Upload Success" message
    And I click on PLANS CONFIG Tab
    Then I choose a file to upload in Plan Config tab "INEB Plan Config"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I verify the file upload " Upload Plan Success " message
    Then I can make a valid search of plans
    When I click on a Plan Name search result
    Then I am navigated to the Plan Details Screen
    Then I verify information is displayed in plan details panel
   Then I verify information is displayed in contact details panel

