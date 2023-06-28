Feature: ATS Adding Primary Individual Details

  @CP-13355 @CP-13355-04 @CP-14977 @CP-14977-17 @crm-regression @ui-ats @sang @CP-12703 @CP-12703-01 @CP-14977 @ats-smoke
  Scenario: Save Primary Individual With all valid field values for Medical Assistance
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I clear the program list for "PI" Application Members
    Then I choose "Medicaid" as program type
    Then I choose "CHIP" as program type
    Then I choose "Pregnancy Assistance" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME           | Alphabetic 30      |
      | MIDDLE INITIAL       | Alphabetic 1       |
      | LAST NAME            | Alphabetic 30      |
      | SUFFIX               | Alphabetic 3       |
      | DOB                  | random past date   |
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
      | SPOKEN LANGUAGE      | random dropdown    |
      | WRITTEN LANGUAGE     | random dropdown    |
      | EXTERNAL CONSUMER ID | Alpha-Numeric 15   |
      | EXTERNAL ID TYPE     | random dropdown    |
    And I choose Communication Opt In Information by the following list
      | Email | Fax | Mail | Phone | Text |
    And I select Primary Individual Application information with the following
      | CYCLE          | New     |
      | RECEIVED DATE  | current |
      | CHANNEL        | random  |
      | SIGNATURE      | Yes     |
      | SIGNATURE DATE | random  |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER        | Random Numeric 10   |
      | HOME PHONE NUMBER        | Random Numeric 10   |
      | WORK PHONE NUMBER        | Random Numeric 10   |
      | FAX NUMBER               | Random Numeric 10   |
      | EMAIL                    | TEST@TEST.COM       |
      | RESIDENCE ADDRESS LINE 1 | random              |
      | RESIDENCE ADDRESS LINE 2 | random              |
      | RESIDENCE CITY           | Metropolis          |
      | RESIDENCE STATE          | Illinois            |
      | RESIDENCE ZIP CODE       | 62960               |
      | RESIDENCE COUNTY         | Massac              |
      | MAILING ADDRESS LINE 1   | 1007 Mountain Drive |
      | MAILING ADDRESS LINE 2   | 1007 Mountain Lane  |
      | MAILING CITY             | Gotham              |
      | MAILING STATE            | New Jersey          |
      | MAILING ZIP CODE         | 53540               |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And Wait for 3 seconds
    And I verify the following saved Primary Individual application details
      | FULL NAME | DOB | SSN | AGE/GENDER | SPOKEN LANG | WRITTEN LANGUAGE | PROGRAMS | OPT INS |
    And I verify the following saved Primary Individual collapsed view application details
      | ARE YOU PREGNANT | NO. OF BABIES EXPECTED | EXPECTED DUE DATE | EXTERNAL CONSUMER ID | EXTERNAL ID TYPE |
    And I verify the following saved Primary Individual Contact Information application
      | CELL PHONE | HOME PHONE | WORK PHONE | FAX NUMBER | EMAIL | RESIDENCE ADDRESS | MAILING ADDRESS |
    And I verify the following saved Primary Individual Application Info
      | CYCLE | RECEIVED DATE | CHANNEL | APPLICATION SIGNATURE | SIGNATURE DATE |

  @CP-13355 @CP-13355-05 @CP-14977 @CP-14977-18 @crm-regression @ui-ats @sang @CP-12703 @CP-12703-02
  Scenario: Save Primary Individual With all valid field values for Long Term Care
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I clear the program list for "PI" Application Members
    Then I choose "HCBS" as program type
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME           | Alphabetic 30      |
      | MIDDLE INITIAL       | Alphabetic 1       |
      | LAST NAME            | Alphabetic 30      |
      | SUFFIX               | Alphabetic 3       |
      | DOB                  | random past date   |
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
      | SPOKEN LANGUAGE      | random dropdown    |
      | WRITTEN LANGUAGE     | random dropdown    |
      | EXTERNAL CONSUMER ID | Alpha-Numeric 15   |
      | EXTERNAL ID TYPE     | random dropdown    |
    And I choose Communication Opt In Information by the following list
      | Email | Fax | Mail | Phone | Text |
    And I select Primary Individual Application information with the following
      | CYCLE          | New     |
      | RECEIVED DATE  | current |
      | CHANNEL        | random  |
      | SIGNATURE      | Yes     |
      | SIGNATURE DATE | random  |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER        | Random Numeric 10   |
      | HOME PHONE NUMBER        | Random Numeric 10   |
      | WORK PHONE NUMBER        | Random Numeric 10   |
      | FAX NUMBER               | Random Numeric 10   |
      | EMAIL                    | TEST@TEST.COM       |
      | RESIDENCE ADDRESS LINE 1 | 1938 Sullivan Place |
      | RESIDENCE ADDRESS LINE 2 | 1938 Sullivan Lane  |
      | RESIDENCE CITY           | Metropolis          |
      | RESIDENCE STATE          | Illinois            |
      | RESIDENCE ZIP CODE       | 62960               |
      | RESIDENCE COUNTY         | Massac              |
      | MAILING ADDRESS LINE 1   | 1007 Mountain Drive |
      | MAILING ADDRESS LINE 2   | 1007 Mountain Lane  |
      | MAILING CITY             | Gotham              |
      | MAILING STATE            | New Jersey          |
      | MAILING ZIP CODE         | 53540               |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And Wait for 3 seconds
    And I verify the following saved Primary Individual application details
      | FULL NAME | DOB | SSN | AGE/GENDER | SPOKEN LANG | WRITTEN LANGUAGE | PROGRAMS | OPT INS |
    And I verify the following saved Primary Individual collapsed view application details
      | ARE YOU PREGNANT | NO. OF BABIES EXPECTED | EXPECTED DUE DATE | EXTERNAL CONSUMER ID | EXTERNAL ID TYPE |
    And I verify the following saved Primary Individual Contact Information application
      | CELL PHONE | HOME PHONE | WORK PHONE | FAX NUMBER | EMAIL | RESIDENCE ADDRESS | MAILING ADDRESS |
    And I verify the following saved Primary Individual Application Info
      | CYCLE | RECEIVED DATE | CHANNEL | APPLICATION SIGNATURE | SIGNATURE DATE |

  @CP-13355 @CP-13355-08 @CP-14977 @CP-14977-19 @crm-regression @ui-ats @sang
  Scenario: Verify Primary Individual Fields are cleared and returned to default upon clicking cancel button for Medical Assistance
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I choose "Medicaid" as program type
    Then I choose "CHIP" as program type
    Then I choose "Pregnancy Assistance" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME           | Alphabetic 30      |
      | MIDDLE INITIAL       | Alphabetic 1       |
      | LAST NAME            | Alphabetic 30      |
      | SUFFIX               | Alphabetic 3       |
      | DOB                  | random past date   |
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
      | SPOKEN LANGUAGE      | Russian            |
      | WRITTEN LANGUAGE     | Other              |
      | EXTERNAL CONSUMER ID | Alpha-Numeric 15   |
      | EXTERNAL ID TYPE     | CHIP               |
    And I choose Communication Opt In Information by the following list
      | Email | Fax | Mail | Phone | Text |
    And I select Primary Individual Application information with the following
      | CYCLE          | New     |
      | RECEIVED DATE  | current |
      | CHANNEL        | random  |
      | SIGNATURE      | Yes     |
      | SIGNATURE DATE | random  |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER        | Random Numeric 10   |
      | HOME PHONE NUMBER        | Random Numeric 10   |
      | WORK PHONE NUMBER        | Random Numeric 10   |
      | FAX NUMBER               | Random Numeric 10   |
      | EMAIL                    | TEST@TEST.COM       |
      | RESIDENCE ADDRESS LINE 1 | 1938 Sullivan Place |
      | RESIDENCE ADDRESS LINE 2 | 1938 Sullivan Lane  |
      | RESIDENCE CITY           | Metropolis          |
      | RESIDENCE STATE          | Illinois            |
      | RESIDENCE ZIP CODE       | 62960               |
      | RESIDENCE COUNTY         | Massac              |
      | MAILING ADDRESS LINE 1   | 1007 Mountain Drive |
      | MAILING ADDRESS LINE 2   | 1007 Mountain Lane  |
      | MAILING CITY             | Gotham              |
      | MAILING STATE            | New Jersey          |
      | MAILING ZIP CODE         | 53540               |
    And I click on Cancel button for Primary Individual Create Application Page
    Then I verify the Primary Individual fields are cleared for "MEDICAL ASSISTANCE"
    And I verify the primary Individual fields have returned to default
    Then I clear the program list for "PI" Application Members

  @CP-13355 @CP-13355-09 @CP-14977 @CP-14977-20 @crm-regression @ui-ats @sang
  Scenario: Verify Primary Individual Fields are cleared and returned to default upon clicking cancel button for Long Term Care
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I choose "HCBS" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME           | Alphabetic 30      |
      | MIDDLE INITIAL       | Alphabetic 1       |
      | LAST NAME            | Alphabetic 30      |
      | SUFFIX               | Alphabetic 3       |
      | DOB                  | random past date   |
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
      | SPOKEN LANGUAGE      | Russian            |
      | WRITTEN LANGUAGE     | Other              |
      | EXTERNAL CONSUMER ID | Alpha-Numeric 15   |
      | EXTERNAL ID TYPE     | CHIP               |
    And I choose Communication Opt In Information by the following list
      | Email | Fax | Mail | Phone | Text |
    And I select Primary Individual Application information with the following
      | CYCLE          | New     |
      | RECEIVED DATE  | current |
      | CHANNEL        | random  |
      | SIGNATURE      | Yes     |
      | SIGNATURE DATE | random  |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER        | Random Numeric 10   |
      | HOME PHONE NUMBER        | Random Numeric 10   |
      | WORK PHONE NUMBER        | Random Numeric 10   |
      | FAX NUMBER               | Random Numeric 10   |
      | EMAIL                    | TEST@TEST.COM       |
      | RESIDENCE ADDRESS LINE 1 | 1938 Sullivan Place |
      | RESIDENCE ADDRESS LINE 2 | 1938 Sullivan Lane  |
      | RESIDENCE CITY           | Metropolis          |
      | RESIDENCE STATE          | Illinois            |
      | RESIDENCE ZIP CODE       | 62960               |
      | RESIDENCE COUNTY         | Massac              |
      | MAILING ADDRESS LINE 1   | 1007 Mountain Drive |
      | MAILING ADDRESS LINE 2   | 1007 Mountain Lane  |
      | MAILING CITY             | Gotham              |
      | MAILING STATE            | New Jersey          |
      | MAILING ZIP CODE         | 53540               |
    And I click on Cancel button for Primary Individual Create Application Page
    Then I verify the Primary Individual fields are cleared for "LONG TERM CARE"
    And I verify the primary Individual fields have returned to default
    Then I clear the program list for "PI" Application Members

  @CP-13355 @CP-13355-14 @CP-14977 @CP-14977-21 @crm-regression @ui-ats @sang
  Scenario: Primary Individual CreatedBY and CreatedOn UI
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | PI CREATEDBY | base                  |
      | PI CREATEDON | today's date and hour |

  @CP-13355 @CP-13355-15 @crm-regression @ui-ats @sang
  Scenario: Primary Individual UpdatedOn and UpdatedBy UI
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30 |
    And I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I Verify the following from the GET Application with ApplicationId
      | PI UPDATEDBY | base                  |
      | PI UPDATEDON | today's date and hour |

  @CP-14977 @CP-14977-01 @crm-regression @ui-ats @sang
  Scenario: Verify the Dropdown values for create application PRIMARY INDIVIDUAL Medical Assistance
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I verify the PRIMARY INDIVIDUAL DETAILS dropdown values for following
      | GENDER | SPOKEN LANGUAGE | WRITTEN LANGUAGE | EXTERNAL ID TYPE | Channel |
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | GENDER           | Female |
      | ARE YOU PREGNANT | Yes    |
    Then I verify the PRIMARY INDIVIDUAL DETAILS dropdown values for following
      | BABIES EXPECTED |

  @CP-14977 @CP-14977-02 @crm-regression @ui-ats @sang
  Scenario: Verify the Dropdown values for create application PRIMARY INDIVIDUAL Long Term Care
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    Then I verify the PRIMARY INDIVIDUAL DETAILS dropdown values for following
      | GENDER | SPOKEN LANGUAGE | WRITTEN LANGUAGE | EXTERNAL ID TYPE | Channel |
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | GENDER           | Female |
      | ARE YOU PREGNANT | Yes    |
    Then I verify the PRIMARY INDIVIDUAL DETAILS dropdown values for following
      | BABIES EXPECTED |

  @CP-14977 @CP-14977-03 @crm-regression @ui-ats @sang
  Scenario: First phone number entered in CELL PHONE NUMBER Field highlights Primary Phone Identifier and only one can be captured
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
      | HOME PHONE NUMBER | Random Numeric 10 |
      | WORK PHONE NUMBER | Random Numeric 10 |
    Then I verify that "CELL PHONE" Star icon is captured and following are not captured
      | HOME PHONE | WORK PHONE |
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
      | HOME PHONE NUMBER | Random Numeric 10 |
      | WORK PHONE NUMBER | Random Numeric 10 |
    Then I verify that "CELL PHONE" Star icon is captured and following are not captured
      | HOME PHONE | WORK PHONE |

  @CP-14977 @CP-14977-04 @crm-regression @ui-ats @sang
  Scenario: First phone number entered in HOME PHONE NUMBER highlights Primary Phone Identifier and only one can be captured
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | HOME PHONE NUMBER | Random Numeric 10 |
      | CELL PHONE NUMBER | Random Numeric 10 |
      | WORK PHONE NUMBER | Random Numeric 10 |
    Then I verify that "HOME PHONE" Star icon is captured and following are not captured
      | CELL PHONE | WORK PHONE |
    When I navigate to Create "LONG TERM CARE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | HOME PHONE NUMBER | Random Numeric 10 |
      | CELL PHONE NUMBER | Random Numeric 10 |
      | WORK PHONE NUMBER | Random Numeric 10 |
    Then I verify that "HOME PHONE" Star icon is captured and following are not captured
      | CELL PHONE | WORK PHONE |

  @CP-14977 @CP-14977-05 @crm-regression @ui-ats @sang
  Scenario: First phone number entered in WORK PHONE NUMBER highlights Primary Phone Identifier and only one can be captured
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | WORK PHONE NUMBER | Random Numeric 10 |
      | HOME PHONE NUMBER | Random Numeric 10 |
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I verify that "WORK PHONE" Star icon is captured and following are not captured
      | CELL PHONE | HOME PHONE |
    When I navigate to Create "LONG TERM CARE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | WORK PHONE NUMBER | Random Numeric 10 |
      | HOME PHONE NUMBER | Random Numeric 10 |
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I verify that "WORK PHONE" Star icon is captured and following are not captured
      | CELL PHONE | HOME PHONE |

  @CP-14977 @CP-14977-06 @crm-regression @ui-ats @sang
  Scenario: Entering a value in LONG TERM CARE Residence field makes select Residence and Mailing fields required
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 2 | 1938 Sullivan Lane |
    Then I verify the field is required asterick symbol is generated next to the "RESIDENCE" Address field labels
    Then I click on Save button on Create Application Page
    And I verify the following required message is populated
      | RESIDENCE |
    And I fill in the Primary Individual Contact Information with the following values
      | MAILING ADDRESS LINE 2 | 1007 Mountain Lane |
    Then I verify the field is required asterick symbol is generated next to the "MAILING" Address field labels
    Then I click on Save button on Create Application Page
    And I verify the following required message is populated
      | MAILING |

  @CP-14977 @CP-14977-07 @crm-regression @ui-ats @sang
  Scenario: Entering a value in MEDICAL ASSISTANCE Address field makes select Residence and Mailing fields required
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 2 | 1938 Sullivan Lane |
    Then I verify the field is required asterick symbol is generated next to the "RESIDENCE" Address field labels
    Then I click on Save button on Create Application Page
    And I verify the following required message is populated
      | RESIDENCE |
    And I fill in the Primary Individual Contact Information with the following values
      | MAILING ADDRESS LINE 2 | 1007 Mountain Lane |
    Then I verify the field is required asterick symbol is generated next to the "MAILING" Address field labels
    Then I click on Save button on Create Application Page
    And I verify the following required message is populated
      | MAILING |

  @CP-14977 @CP-14977-08 @crm-regression @ui-ats @sang
  Scenario: Selecting on LONG TERM CARE Same as Residence Address checkbox populates corresponding Residence address to Mailing address
    Given I logged into CRM
    When I navigate to Create "LONG TERM CARE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 1 | 1938 Sullivan Place |
      | RESIDENCE ADDRESS LINE 2 | 1938 Sullivan Lane  |
      | RESIDENCE CITY           | Metropolis          |
      | RESIDENCE STATE          | Illinois            |
      | RESIDENCE ZIP CODE       | 62960               |
      | RESIDENCE COUNTY         | Massac              |
    And I click on the Same As Residence Address checkbox in Primary Individual Application Create
    Then I verify corresponding Residence address values are populated for Mailing Address
    And I verify the Mailing Fields are disabled and unable to be edited

  @CP-14977 @CP-14977-09 @crm-regression @ui-ats @sang
  Scenario: Selecting on MEDICAL ASSISTANCE Same as Residence Address checkbox populates corresponding values to Mailing address and disables editing of Mailing
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 1 | 1938 Sullivan Place |
      | RESIDENCE ADDRESS LINE 2 | 1938 Sullivan Lane  |
      | RESIDENCE CITY           | Metropolis          |
      | RESIDENCE STATE          | Illinois            |
      | RESIDENCE ZIP CODE       | 62960               |
      | RESIDENCE COUNTY         | Massac              |
    And I click on the Same As Residence Address checkbox in Primary Individual Application Create
    Then I verify corresponding Residence address values are populated for Mailing Address
    And I verify the Mailing Fields are disabled and unable to be edited

  @CP-14977 @CP-14977-10 @crm-regression @ui-ats @sang
  Scenario: Verify entered Numeric 10 value for phone number is in Phone Number Format in Medical Assistance Application
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
      | HOME PHONE NUMBER | Random Numeric 10 |
      | WORK PHONE NUMBER | Random Numeric 10 |
      | FAX NUMBER        | Random Numeric 10 |
    Then I verify Application Contact Info phone number value is in correct Phone Number Format

  @CP-14977 @CP-14977-11 @crm-regression @ui-ats @sang
  Scenario: Verify entered Numeric 10 value for phone number is in Phone Number Format in Long Term Care Application
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
      | HOME PHONE NUMBER | Random Numeric 10 |
      | WORK PHONE NUMBER | Random Numeric 10 |
      | FAX NUMBER        | Random Numeric 10 |
    Then I verify Application Contact Info phone number value is in correct Phone Number Format

  @CP-14977 @CP-14977-12 @crm-regression @ui-ats @sang
  Scenario: View Application tab details after verifying successfully save message for Medical Assistance Application
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I verify application icon with Primary Individual name and App Id is displayed in the Header

  @CP-14977 @CP-14977-13 @crm-regression @ui-ats @sang
  Scenario: View Application tab details after verifying successfully save message for LONG TERM CARE Application
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I verify application icon with Primary Individual name and App Id is displayed in the Header

  @CP-14977 @CP-14977-14 @crm-regression @ui-ats @sang
  Scenario: Verify Edited Application data entry is saved
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I click on the Edit button for the Primary Individual Details
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME           | Alphabetic 30      |
      | MIDDLE INITIAL       | Alphabetic 1       |
      | LAST NAME            | Alphabetic 30      |
      | SUFFIX               | Alphabetic 3       |
      | DOB                  | random past date   |
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
      | SPOKEN LANGUAGE      | random dropdown    |
      | WRITTEN LANGUAGE     | random dropdown    |
      | EXTERNAL CONSUMER ID | Alpha-Numeric 15   |
      | EXTERNAL ID TYPE     | random dropdown    |
    And I choose Communication Opt In Information by the following list
      | Email | Fax | Mail | Phone | Text |
    And I select Primary Individual Application information with the following
      | CYCLE          | New     |
      | RECEIVED DATE  | current |
      | CHANNEL        | random  |
      | SIGNATURE      | Yes     |
      | SIGNATURE DATE | random  |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER        | Random Numeric 10   |
      | HOME PHONE NUMBER        | Random Numeric 10   |
      | WORK PHONE NUMBER        | Random Numeric 10   |
      | FAX NUMBER               | Random Numeric 10   |
      | EMAIL                    | TEST@TEST.COM       |
      | RESIDENCE ADDRESS LINE 1 | random              |
      | RESIDENCE ADDRESS LINE 2 | random              |
      | RESIDENCE CITY           | Metropolis          |
      | RESIDENCE STATE          | Illinois            |
      | RESIDENCE ZIP CODE       | 62960               |
      | RESIDENCE COUNTY         | Massac              |
      | MAILING ADDRESS LINE 1   | 1007 Mountain Drive |
      | MAILING ADDRESS LINE 2   | 1007 Mountain Lane  |
      | MAILING CITY             | Gotham              |
      | MAILING STATE            | New Jersey          |
      | MAILING ZIP CODE         | 53540               |
    Then I click on Save button on Create Application Page
    And I verify the following saved Primary Individual application details
      | FULL NAME | DOB | SSN | AGE/GENDER | SPOKEN LANG | WRITTEN LANGUAGE | PROGRAMS | OPT INS |
    And I verify the following saved Primary Individual collapsed view application details
      | ARE YOU PREGNANT | NO. OF BABIES EXPECTED | EXPECTED DUE DATE | EXTERNAL CONSUMER ID | EXTERNAL ID TYPE |
    And I verify the following saved Primary Individual Contact Information application
      | CELL PHONE | HOME PHONE | WORK PHONE | FAX NUMBER | EMAIL | RESIDENCE ADDRESS | MAILING ADDRESS |
    And I verify the following saved Primary Individual Application Info
      | CYCLE | RECEIVED DATE | APPLICATION SIGNATURE | SIGNATURE DATE |

  @CP-13355 @CP-13355-16 @CP-14977 @crm-regression @ui-ats @sang @PI-REFACTORED
  Scenario: Validate unable to save without first name
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | LAST NAME | Alphabetic 30 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    And I verify the following required message is populated
      | FIRST |

  @CP-13355 @CP-13355-17 @CP-14977 @CP-14977-22 @crm-regression @ui-ats @sang @PI-REFACTORED
  Scenario: Validate unable to save without last name
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    And I verify the following required message is populated
      | LAST |

  @CP-13355 @CP-13355-18 @CP-14977 @CP-14977-23 @crm-regression @ui-ats @sang @PI-REFACTORED
  Scenario: Validate unable to save without contact information
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30 |
      | LAST NAME  | Alphabetic 30 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I click on Save button on Create Application Page
    And I verify the following required message is populated
      | CELL | HOME | WORK | FAX | RESIDENCE | MAILING |

  @CP-13355 @CP-13355-19 @CP-14977 @CP-14977-24 @crm-regression @ui-ats @sang @PI-REFACTORED
  Scenario: Validate unable to save without entering CHANNEL value
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30 |
      | LAST NAME  | Alphabetic 30 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I click on Save button on Create Application Page
    And I verify the following required message is populated
      | CHANNEL |

  @CP-13355 @CP-13355-20 @CP-14977 @CP-14977-25 @crm-regression @ui-ats @sang @PI-REFACTORED
  Scenario: Validate unable to save without any required values entered in create application
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I click on Save button on Create Application Page
    And I verify the following required message is populated
      | FIRST | LAST | CHANNEL | CELL | HOME | WORK | FAX | RESIDENCE | MAILING |

  @CP-13355 @CP-13355-21 @CP-12703 @CP-12703-03 @crm-regression @ui-ats @sang @PI-REFACTORED
  Scenario: View and verify Primary Individual details from application created by API in the application tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | 2021-06-03               |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | consumerSuffix | consumerMiddleName | dateOfBirth | ssn       | genderCode | externalConsumerId | externalConsumerIdType | writtenLanguage | spokenLanguage | expectedBabies | expectedDueDate | pregnancyInd |
      | John              | Doe              | Sr             | B                  | 1987-10-08  | 223335555 | Female     | wr35ty789mj        | CHIP                   | English         | Russian        | 2              | FUTURE DATE     | true         |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Email |
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1   | addressCity | addressState | addressZip | addressStreet2 |
      | Mailing     | 123 Auto Created | Houston     | Texas        | 77055      | Suite 2        |
    And I POST ATS save application api
    And I set Primary Individual values from the sent application request payload to be checked
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    And I verify the following saved Primary Individual application details
      | FULL NAME | DOB | SSN | AGE/GENDER | SPOKEN LANG | WRITTEN LANGUAGE | PROGRAMS | OPT INS |
    And I verify the following saved Primary Individual collapsed view application details
      | ARE YOU PREGNANT | NO. OF BABIES EXPECTED | EXPECTED DUE DATE | EXTERNAL CONSUMER ID | EXTERNAL ID TYPE |
    And I verify the following saved Primary Individual Contact Information application
      | WORK PHONE | EMAIL | RESIDENCE ADDRESS | MAILING ADDRESS |
    And I verify the following saved Primary Individual Application Info
      | CYCLE | RECEIVED DATE | CHANNEL | APPLICATION SIGNATURE | SIGNATURE DATE |
