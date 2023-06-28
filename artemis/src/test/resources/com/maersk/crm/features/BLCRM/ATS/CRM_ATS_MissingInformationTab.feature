Feature: ATS Missing Information Tab

  @CP-15328 @CP-15328-01 @crm-regression @ui-ats @burak
  Scenario: Verify Application Tracking tab Application Information Panel Labels for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL                 | random |
      | EXTERNAL APPLICATION ID | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on missing information tab to navigate to Missing Information page
    And I see columns displayed in this order in the Application Information panel for Missing Information Tab
      | APPLICATION ID | APPLICATION CODE | EXTERNAL APP ID | PRIORITY | DEADLINE DATE | SIGNATURE DATE | APPLICATION TYPE | CYCLE | RECEIVED DATE | RECEIVED LANGUAGE | CHANNEL | CREATE DATE | LAST UPDATED DATE |

  @CP-15328 @CP-15328-02 @crm-regression @ui-ats @burak
  Scenario: Verify Application Tracking tab Application Information Panel Labels for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL                 | random |
      | EXTERNAL APPLICATION ID | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on missing information tab to navigate to Missing Information page
    And I see columns displayed in this order in the Application Information panel for Missing Information Tab
      | APPLICATION ID | APPLICATION CODE | EXTERNAL APP ID | PRIORITY | DEADLINE DATE | SIGNATURE DATE | APPLICATION TYPE | CYCLE | RECEIVED DATE | RECEIVED LANGUAGE | CHANNEL | CREATE DATE | LAST UPDATED DATE |

  @CP-15328 @CP-15328-03 @crm-regression @ui-ats @burak
  Scenario: Verify correct Application values inside Application Info panel in Missing Information Tab for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CYCLE                   | New              |
      | RECEIVED DATE           | current          |
      | CHANNEL                 | random           |
      | SIGNATURE               | Yes              |
      | SIGNATURE DATE          | random           |
      | PRIORITY                | random           |
      | EXTERNAL APPLICATION ID | Alpha-Numeric 36 |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on missing information tab to navigate to Missing Information page
    Then I verify the Application panel values for "MEDICAL ASSISTANCE" in the Missing Information tab

  @CP-15328 @CP-15328-04 @crm-regression @ui-ats @burak
  Scenario: Verify correct Application values inside Application Info panel in Missing Information Tab for LONG TERM CARE
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CYCLE                   | New              |
      | RECEIVED DATE           | current          |
      | CHANNEL                 | random           |
      | SIGNATURE               | Yes              |
      | SIGNATURE DATE          | random           |
      | PRIORITY                | random           |
      | EXTERNAL APPLICATION ID | Alpha-Numeric 36 |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on missing information tab to navigate to Missing Information page
    Then I verify the Application panel values for "LONG TERM CARE" in the Missing Information tab

  @CP-15328 @CP-15328-05 @crm-regression @ui-ats @burak
  Scenario: Application Tracking Tab Back Arrow From application creation clears Application for Missing Information Tab
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on missing information tab to navigate to Missing Information page
    And I click on the back arrow in header row next to the icon Primary Individual name and application id on Missing Information Panel
    Then I verify application is cleared from view for Missing Information Panel

  @CP-21815 @CP-21815-01 @crm-regression @ui-ats @sang
  Scenario: Missing information Details panel labels and button
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    When I click on missing information tab to navigate to Missing Information page
    Then I verify the labels in the Missing information Details panel labels and button

  @CP-21815 @CP-21815-02 @crm-regression @ui-ats @sang
  Scenario: Verify Category dropdown values in Missing Information Add
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | CLICK |
    Then I verify the following values for "CATEGORY" dropdown values for Missing Info add
      |[blank]| Application | Application Consumer | Application Consumer Income | Application Consumer Program |

  @CP-21815 @CP-21815-03 @crm-regression @ui-ats @sang
  Scenario: Verify TYPE dropdown values for category value Application selected in Missing Information Add
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | CLICK |
    Then I verify the following values for "TYPE" dropdown values for Missing Info add
      |[blank]| Application Signed | Residence County |

  @CP-21815 @CP-21815-04 @crm-regression @ui-ats @sang
  Scenario: Verify TYPE dropdown values for category value Application Consumer selected in Missing Information Add
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | CLICK |
    Then I verify the following values for "TYPE" dropdown values for Missing Info add
      |[blank]| Citizenship Verification | DOB | First Name | Last Name | Gender | SSN |

  @CP-21815 @CP-21815-05 @crm-regression @ui-ats @sang
  Scenario: Verify FROM and FOR dropdown values for Application Category with multi applicant consumers
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME     | Random 5     |
      | MIDDLE INITIAL | Alphabetic 1 |
      | LAST NAME      | Random 5     |
      | SUFFIX         | Alphabetic 3 |
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | MI         | RANDOM 1 |
      | LAST NAME  | Random 5 |
      | SUFFIX     | RANDOM 3 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    Then I verify the following values for "FROM" dropdown values for Missing Info add
      |[blank]| PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    Then I verify the following values for "NEED FOR" dropdown values for Missing Info add
      |[blank]| PRIMARY | APP MEM ONE |

  @CP-21815 @CP-21815-06 @crm-regression @ui-ats @sang
  Scenario: Verify FOR and FROM dropdown values for Application Consumer with multi applicant consumers
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME     | Random 5     |
      | MIDDLE INITIAL | Alphabetic 1 |
      | LAST NAME      | Random 5     |
      | SUFFIX         | Alphabetic 3 |
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | MI         | RANDOM 1 |
      | LAST NAME  | Random 5 |
      | SUFFIX     | RANDOM 3 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY | APP MEM ONE |
    Then I verify the following values for "FROM" dropdown values for Missing Info add
      |[blank]| PRIMARY | APP MEM ONE |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    Then I verify the following values for "NEED FOR" dropdown values for Missing Info add
      |[blank]| PRIMARY | APP MEM ONE |

  @CP-21815 @CP-21815-07 @crm-regression @ui-ats @sang
  Scenario: Missing Info Details Application Type Successful save to PENDING status without comments
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    Then I verify "EMPTY" comment box for successful Missing Info entity save
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |

  @CP-21815 @CP-21815-08 @crm-regression @ui-ats @sang
  Scenario: Missing Info Details Application Consumer Type Successful to PENDING STATUS save with max filled comments
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | SSN |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on Missing Info comment field to enter "MAX 500 CHAR COUNT"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    Then I verify "MAX 500 CHAR COUNT" comment box for successful Missing Info entity save
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |

  @CP-21815 @CP-21815-09 @crm-regression @ui-ats @sang
  Scenario: Missing Info Details panel Cancel button clears selected values
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "CANCEL" button for Missing Info in Application Missing Info tab
    Then I verify Missing Info save panel disappears and values have been cleared

  @CP-21815 @CP-21815-10 @crm-regression @ui-ats @sang
  Scenario: Save Missing Information record Fail generates Is Required message for TYPE Category FOR NEED FROM
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    Then I verify the required error message for dropdown values for failed Missing Info Save

  @CP-21525 @CP-21525-01 @crm-regression @ui-ats @sang
  Scenario: View successful saved Missing Info With 64 Max Character Consumer Name for FROM and NEED FOR dropdowns
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME     | Alphabetic 30 |
      | MIDDLE INITIAL | Alphabetic 1  |
      | LAST NAME      | Alphabetic 30 |
      | SUFFIX         | Alphabetic 3  |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | CATEGORY    | TYPE               | FROM    | NEED FOR | STATUS  |
      | Application | Application Signed | PRIMARY | PRIMARY  | PENDING |

  @CP-21525 @CP-21525-02 @crm-regression @ui-ats @sang
  Scenario: View Multiple Saved NEED FOR Applicant Consumers using hover over icon
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And click on save button for add application member
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | SSN |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY | APP MEM ONE | APP MEM TWO |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I verify saved Missing Info NEED FOR consumer applicants by hovering over by the icon next to NEED FOR value

  @CP-21525 @CP-21525-03 @CP-25057 @CP-25057-04 @crm-regression @ui-ats @sang
  Scenario: View Missing Information Details dropdown menu for User Actions
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | SSN |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    Then I verify the following values for "USER ICON" dropdown values for Missing Info add
      | EDIT COMMENT | SATISFY | DISREGARD |

  @CP-21525 @CP-21525-04 @crm-regression @ui-ats @sang @ats-smoke
  Scenario: Saved Missing Information Display expanded View
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | SSN |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I verify the "LABELS" in expanded Missing Information save
      | MI ID# | CREATED BY | CREATED ON | UPDATED BY | UPDATED ON |
    And I verify the "VALUES" in expanded Missing Information save
      | CREATED BY | CREATED ON | UPDATED BY | UPDATED ON |
    And I save the following Missing Information data value for verification
      | MI ID# |
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then I initiated retrieve Missing Information API for "UI CREATED" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify Application UI created "MI ID" with missing information API

  @CP-21525 @CP-21525-05 @CP-25701 @CP-25701-01 @crm-regression @ui-ats @sang
  Scenario: Verify Pagination for Missing Info Details panel for multiple Type and multiple From MI save
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | EMAIL | TEST@TEST.COM |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Citizenship Verification | DOB | First Name | Last Name | Gender | SSN |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY | APP MEM ONE |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on Missing Info comment field to enter "Typed in for test"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    Then I verify Missing Information Details panel Pagination with Multiple saved data values

  @CP-25701 @CP-25701-02 @crm-regression @ui-ats @sang
  Scenario: Verify Type and IDs when saving Multiple Missing Information Records during Create
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | SSN | Gender | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I save the following Missing Information data value for verification
      | MI ID# |
    And I click on "ROW TWO CARROT" button for Missing Info in Application Missing Info tab
    And I save the following Missing Information data value for verification
      | MI ID# |
    And I click on "ROW THREE CARROT" button for Missing Info in Application Missing Info tab
    And I save the following Missing Information data value for verification
      | MI ID# |
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then I initiated retrieve Missing Information API for "UI CREATED" Application using "Application Service"
    When I run the Retrieve missing information API using "Application Service"
    Then I verify Application UI created "THREE MI ID" with missing information API

  @CP-31419 @CP-31419-01 @crm-regression @ui-ats @Prithika
  Scenario: 1.0 Restrict creation of duplicate MI item for an application consumer
    Given  I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | MI         | RANDOM 1 |
      | LAST NAME  | Random 5 |
      | SUFFIX     | RANDOM 3 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer|
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer|
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE ONLY" button for Missing Info in Application Missing Info tab
    Then I verify Missing Info duplicate record error is displayed
    And I verify the number of missing info Rows are "1"

  @CP-31419 @CP-31419-02 @crm-regression @ui-ats @Prithika
  Scenario: 1.1 Allow creation of dependent for an existing MI item Z( application consumer level / category)
    Given  I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | MI         | RANDOM 1 |
      | LAST NAME  | Random 5 |
      | SUFFIX     | RANDOM 3 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer|
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer|
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | APP MEM ONE |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I verify the number of missing info Rows are "2"

  @CP-31419 @CP-31419-03 @crm-regression @ui-ats @Prithika
  Scenario: 1.2 Allow creation of new MI item ( application consumer level / category)-Disregarded
    Given  I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | MI         | RANDOM 1 |
      | LAST NAME  | Random 5 |
      | SUFFIX     | RANDOM 3 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer|
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS      |
      | DISREGARDED |
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer|
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I verify the number of missing info Rows are "2"

  @CP-31419 @CP-31419-04 @crm-regression @ui-ats @Prithika
  Scenario: 1.2 Allow creation of new MI item ( application consumer level / category)-Satisfied
    Given  I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | MI         | RANDOM 1 |
      | LAST NAME  | Random 5 |
      | SUFFIX     | RANDOM 3 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer|
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS    |
      | SATISFIED |
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer|
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I verify the number of missing info Rows are "2"

  @CP-31419 @CP-31419-05 @crm-regression @ui-ats @Prithika
  Scenario: 2.0 Restrict creation of duplicate MI item for an application and its dependent (application level/ category)
    Given  I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | MI         | RANDOM 1 |
      | LAST NAME  | Random 5 |
      | SUFFIX     | RANDOM 3 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE ONLY" button for Missing Info in Application Missing Info tab
    Then I verify Missing Info duplicate record error is displayed
    And I verify the number of missing info Rows are "1"

  @CP-31419 @CP-31419-06 @crm-regression @ui-ats @Prithika
  Scenario: 2.1 Allow creation of dependent for an existing MI item( application level / category)
    Given  I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | MI         | RANDOM 1 |
      | LAST NAME  | Random 5 |
      | SUFFIX     | RANDOM 3 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | APP MEM ONE |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I verify the number of missing info Rows are "2"

  @CP-31419 @CP-31419-07 @crm-regression @ui-ats @Prithika
  Scenario: 2.2 Allow creation of new MI item( application level / category)-Disregarded
    Given  I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    When I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | MI         | RANDOM 1 |
      | LAST NAME  | Random 5 |
      | SUFFIX     | RANDOM 3 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS      |
      | DISREGARDED |
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I verify the number of missing info Rows are "2"

  @CP-31419 @CP-31419-08 @crm-regression @ui-ats @Prithika
  Scenario: 2.2 Allow creation of new MI item( application level / category)-Satisfied
    Given  I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | Random 5 |
      | MI         | RANDOM 1 |
      | LAST NAME  | Random 5 |
      | SUFFIX     | RANDOM 3 |
    And click on save button for add application member
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS      |
      | SATISFIED   |
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Application Signed |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I verify the number of missing info Rows are "2"

  @CP-34428 @CP-34428-01  @crm-regression @ui-ats @Prithika
  Scenario: Remove creation of MI Item from the interactive flag = false
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn           |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      |cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | ssn           | dateOfBirth |
      |[blank]|                  | DUPLICATE SSN |[blank]|
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "INSUFFICIENT" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I see application Status as "INSUFFICIENT" in the missing information page
    And I verify the number of missing info Rows are "0"
    And I navigate to application tab page from missing info tab
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5         |
      | LAST NAME  | Random 5         |
      | DOB        | random past date |
    And I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I click on member matching page back arrow to navigate to create application page
    When I click on application tracking tab to navigate to Application Tracking page
    And I see application Status as "TARGETS UNIDENTIFIED" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I see application Status as "TARGETS UNIDENTIFIED" in the missing information page
    And I verify the number of missing info Rows are "0"

  @CP-34428 @CP-34428-02  @crm-regression @ui-ats @Prithika
  Scenario: Remove creation of MI Item from the interactive flag = true
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true          |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 9 |
      | LAST NAME  | Random 9 |
      | SSN        | Numeric 9 |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with applicationConsumerEmail
      | emailAddress           |
      | automation@created.com |
    And I initiate save applications api consumer 0 with applicationConsumerAddress
      | addressType | addressStreet1 | addressStreet2 | addressCity | addressState | addressZip | addressCounty |
      | Residential | 9 Metro Ave    | 2nd apt        | Herndon     | Virginia     | 20171      | Loudoun       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and run Get Application Links Call
    And I verify that there is no "Member Matching" task is created
    And I will get "Task" ID for "Review Incomplete Application" type from the application links response
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "ENTERING DATA" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I see application Status as "ENTERING DATA" in the missing information page
    And I verify the number of missing info Rows are "0"
    And I navigate to application tab page from missing info tab
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5         |
      | LAST NAME  | Random 5         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
    And I click on Save button on Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
  #  And I see I navigated to Member Matching page
    And I click on member matching page back arrow to navigate to create application page
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify I am on the Application Tracking Page
    And I see application Status as "DETERMINING" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I see application Status as "DETERMINING" in the missing information page
    And I verify the number of missing info Rows are "1"