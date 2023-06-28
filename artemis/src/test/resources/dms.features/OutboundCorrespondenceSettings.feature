Feature: Outbound Correspondence Settings

  @DMS-312-1 @DMS-312 @ui-ecms1 @James
  Scenario: Verify that when saving Outbound Correspondence Settings without a Vendor Company Name will fail with a message "VENDOR COMPANY NAME is required; it cannot be left blank"
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "vendorCompanyName" "null"
    Then Outbound Correspondence Settings save will fail with message "VENDOR COMPANY NAME is required; it cannot be left blank"

  @DMS-312-2 @DMS-312 @ui-ecms1 @James
  Scenario: Verify that when saving Outbound Correspondence Settings without a Vendor Contact Name will fail with a message "VENDOR CONTACT NAME is required; it cannot be left blank"
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "vendorContactName" "null"
    Then Outbound Correspondence Settings save will fail with message "VENDOR CONTACT NAME is required; it cannot be left blank"

  @DMS-312-3 @DMS-312 @ui-ecms1 @James
  Scenario: Verify that when saving Outbound Correspondence Settings without a Vendor Phone will fail with a message "VENDOR PHONE is required; it cannot be left blank"
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "vendorPhone" "null"
    Then Outbound Correspondence Settings save will fail with message "VENDOR PHONE is required; it cannot be left blank"

#  @DMS-312-4 @DMS-312 @ui-ecms1 @James not valid
  Scenario: Verify that when saving Outbound Correspondence Settings without a Outbound Print File Format will fail with message "OUTBOUND PRINT FILE FORMAT is required; it cannot be left blank"
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "outboundFileFormat" "null"
    Then Outbound Correspondence Settings save will fail with message "OUTBOUND PRINT FILE FORMAT is required; it cannot be left blank"

  @DMS-312-5 @DMS-312 @ui-ecms1 @James
  Scenario: Verify that when saving Outbound Correspondence Settings without a Sender Email Address along with Email Channel being enabled will fail with a message "SENDER EMAIL ADDRESS is required if Email Channel is selected"
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    And "Email" Channel is selected in Outbound Correspondence Settings
    When I save Outbound Correspondence Settings with the following fields blank; "smtpSenderEmail"
    Then Outbound Correspondence Settings save will fail with message "SENDER EMAIL ADDRESS is required if Email Channel is selected"

  @DMS-312-6 @DMS-312 @ui-ecms1 @James
  Scenario: Verify that the following fields take 40 alphanumeric characters; Vendor Company Name, Vendor Contact Name
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "40 alphanumeric" characters to following fields
      | vendorCompanyName |
      | vendorContactName |
    Then Outbound Correspondence Settings save the values successfully

#  @DMS-312-7 @DMS-312 @ui-ecms1 @James removed for CP-17532
  Scenario: Verify that the following fields take 32 alphanumeric characters; (Outbound Print File Ftp Details Section) USER, PASSWORD, FOLDER, (Inbound Print File Details) USER, PASSWORD, FOLDER
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "32 alphanumeric" characters to following fields
      | ftpUser      |
      | ftpPassword  |
      | ftpFolder    |
      | smtpUser     |
      | smtpPassword |
    Then Outbound Correspondence Settings save the values successfully

#  @DMS-312-8 @DMS-312 @ui-ecms1 @James removed for CP-17532
  Scenario: Verify that the following fields take 250 free text characters; (Outbound Print File Ftp Details Section) HOST, (Inbound Print File Details) INBOUND PDF FILE PATH, (Outbound Email SMTP Details) HOST
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "250 free text" characters to following fields
      | ftpHost  |
      | smtpHost |
    Then Outbound Correspondence Settings save the values successfully

#  @DMS-312-9 @DMS-312 @ui-ecms1 @James removed for CP-17532
  Scenario: Verify that the following fields take 4 numeric characters; (Outbound Print File Ftp Details Section) PORT, (Outbound Email SMTP Details) PORT
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "4 numeric" characters to following fields
      | ftpPort  |
      | smtpPort |
    Then Outbound Correspondence Settings save the values successfully

  @DMS-312-10 @DMS-312 @ui-ecms1 @James
  Scenario: Verify that Vendor Phone takes 10 numbers
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "10 digits" characters to following fields
      | vendorPhone |
    Then Outbound Correspondence Settings save the values successfully

  @DMS-312-11 @DMS-312 @ui-ecms1 @James
  Scenario Outline: Verify that the following fields takes only valid email addresses; Vendor Email, Sender Email Address
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    And "Email" Channel is selected in Outbound Correspondence Settings
    When I save Outbound Correspondence Settings with "<Field>" "<Value>"
    Then Outbound Correspondence Settings save the values successfully
    Examples:
      | Field           | Value                                                 |
      | vendorEmail     | !#$%&'*+-/=?^_`{}~a1.b2@maersktesting123-a1.asdf.com |
      | smtpSenderEmail | !#$%&'*+-/=?^_`{}~a1.b2@maersktesting123-a1.asdf.com |

  @DMS-312-12 @DMS-312 @ui-ecms1 @James
  Scenario Outline: Verify that the following fields will not take invalid email addresses; Vendor Email, Sender Email Address
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    And "Email" Channel is selected in Outbound Correspondence Settings
    When I save Outbound Correspondence Settings with "<Field>" "<Value>"
    Then Outbound Correspondence Settings save will fail with message "Email Address format is invalid. Please enter it in format xx@xx.xx"
    Examples:
      | Field           | Value                               |
      | vendorEmail     | .false@maersktesting1.com          |
      | vendorEmail     | false.@maersktesting1.com          |
      | vendorEmail     | false1....false@maersktesting1.com |
      | vendorEmail     | false1@.maersktesting1.com         |
      | vendorEmail     | false1@maersktesting1.com.         |
      | vendorEmail     | false1@-maersktesting1.com         |
      | vendorEmail     | false1@maersktesting1.com-         |
      | smtpSenderEmail | .false@maersktesting1.com          |
      | smtpSenderEmail | false.@maersktesting1.com          |
      | smtpSenderEmail | false1....false@maersktesting1.com |
      | smtpSenderEmail | false1@.maersktesting1.com         |
      | smtpSenderEmail | false1@maersktesting1.com.         |
      | smtpSenderEmail | false1@-maersktesting1.com         |
      | smtpSenderEmail | false1@maersktesting1.com-         |

  @DMS-312-13 @DMS-312 @ui-ecms1 @James
  Scenario: Verify that Outbound Print File Format is a drop down that contains; CSV, XML, PDF
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I select Outbound Print Field Format Dropdown
    Then I should see the following values as options;
      | CSV          |
      | XML          |
      | PDF          |
      | JSON DEFAULT |

#  @DMS-312-14 @DMS-312 @ui-ecms1 @James removed for CP-17532
  Scenario Outline: Verify that the following fields in the Outbound Print File Section are optional when saving Outbound Correspondence Settings; HOST, USER, PASSWORD, PORT, FOLDER
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "<Field>" "<Value>"
    Then Outbound Correspondence Settings save the values successfully
    Examples:
      | Field       | Value |
      | ftpHost     | null  |
      | ftpUser     | null  |
      | ftpPassword | null  |
      | ftpPort     | null  |
      | ftpFolder   | null  |

#  @DMS-312-15 @DMS-312 @ui-ecms1 @James fields deleted
  Scenario Outline: Verify that the following fields in the Inbound Print File Details Section are optional when saving Outbound Correspondence Settings; INBOUND RESPONSE FILE PATH, INBOUND PDF FILE PATH
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "<Field>" "<Value>"
    Then Outbound Correspondence Settings save the values successfully
    Examples:
      | Field                   | Value |
      | inboundFilePathResponse | null  |
      | inboundFilePathPDF      | null  |

  @DMS-312-16 @DMS-312 @ui-ecms1 @James
  Scenario Outline: Verify that the following fields in the Outbound Email SMTP Details Section are optional when saving Outbound Correspondence Settings; HOST, USER, PASSWORD, PORT
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I save Outbound Correspondence Settings with "<Field>" "<Value>"
    Then Outbound Correspondence Settings save the values successfully
    Examples:
      | Field        | Value |
      | smtpHost     | null  |
      | smtpUser     | null  |
      | smtpPassword | null  |
      | smtpPort     | null  |

  @DMS-312-17 @DMS-312 @ui-ecms1 @James
  Scenario: Verify that a successful save of Outbound Correspondence Settings will produce save successful pop up
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I edit Outbound Correspondence Settings with "vendorCompanyName" to be "random"
    Then I should see the save successful pop up message appear

#  @DMS-312-18 @DMS-312 @ui-ecms1 @James removed for CP-17532
  Scenario: Verify that a successful save of Outbound Correspondence Settings will capture; Updated Date, Updated By and display as Updated On and Updated By
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I edit Outbound Correspondence Settings with "vendorCompanyName" to be "random"
    Then Outbound Correspondence Settings the system capture; Updated Date, Updated By and display as Updated On and Updated By

  @DMS-312-19 @DMS-312 @ui-ecms1 @James
  Scenario: Verify that navigating away from Outbound Correspondence Settings Page will bring up a warning before discarding changes
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I edit Outbound Correspondence Settings with "vendorCompanyName" to be "random"
    When I attempt to navigate away from the page
    Then Outbound Correspondence Settings the system warn be before discarding changes

  @CP-7307  @ui-ecms2 @ECMS-SMOKE @James
  Scenario: Add Create Correspondence to Global Navigation
    Given I logged into the CRM Application
    When I clicked on the global navigation icon
    Then I should Create Correspondence as an option

  @CP-7251  @ui-ecms2 @ECMS-SMOKE @James
  Scenario: Verify Correspondence Channels are properly defined
   Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    Then I should see that correspondence channels are all present (Mail,Email,Text,Fax,Web Portal,Mobile App)

