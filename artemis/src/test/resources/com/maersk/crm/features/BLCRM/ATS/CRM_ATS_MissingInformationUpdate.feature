Feature: ATS Missing Information Update

  @CP-25818 @CP-25818-01 @crm-regression @ui-ats @sang
  Scenario: Add 500 max char to the comment box for an existing null comment Missing Info save record
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
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "EDIT COMMENT" from the User Icon menu for Missing Info Save
    And I enter "MAX 500 CHAR COUNT" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify "MAX 500 CHAR COUNT" comment box for successful Missing Info entity save

  @CP-25818 @CP-25818-02 @crm-regression @ui-ats @sang
  Scenario: Add additional char to a filled in comment box for an existing Missing Info save record with max of 500 chars
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
    And I click on Missing Info comment field to enter "200 CHAR COUNT"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "EDIT COMMENT" from the User Icon menu for Missing Info Save
    And I enter "Additional 300 characters" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify "MAX 500 CHAR COUNT" comment box for successful Missing Info entity save

  @CP-25818 @CP-25818-03 @crm-regression @ui-ats @sang
  Scenario: Missing Information Edit Comment Cancel button closes edit comment fill in box
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
    And I click on Missing Info comment field to enter "RANDOM TEN"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "EDIT COMMENT" from the User Icon menu for Missing Info Save
    And I Click on "CANCEL" for Missing Info Details panel EDIT COMMENT
    And I verify Missing Info Panel EDIT COMMENT section has collapsed
    Then I verify "RANDOM TEN" comment box for successful Missing Info entity save

  @CP-25818 @CP-25818-04 @crm-regression @ui-ats @sang
  Scenario: Verify Updated On and Updated By for Editing Missing Info Comment to an existing MI save
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName | entityRecordType     | status  | attributeValue |
      | Data        | SSN           | Application Consumer | PENDING | RANDOM         |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "EDIT COMMENT" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I verify the "LABELS" in expanded Missing Information save
      | MI ID# | CREATED BY | CREATED ON | UPDATED BY | UPDATED ON |
    And I verify the "UPDATE ON AND BY" in expanded Missing Information save
      | CREATED BY | CREATED ON | UPDATED BY | UPDATED ON |

  @CP-25818 @CP-25818-05 @crm-regression @ui-ats @sang
  Scenario: Verify edit comment adding more then 500 max char to the comment box saves up to only 500 max char count in MI comment
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "EDIT COMMENT" from the User Icon menu for Missing Info Save
    And I enter "MORE THAN MAX 500 CHAR" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify "MAX 500 CHAR COUNT" comment box for successful Missing Info entity save

  @CP-25818 @CP-25818-06 @crm-regression @ui-ats @sang
  Scenario: Select to save edit comment twice for Missing info save
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "EDIT COMMENT" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify "RANDOM TEN" comment box for successful Missing Info entity save
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "EDIT COMMENT" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN EDIT AGAIN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify "RANDOM TEN EDIT AGAIN" comment box for successful Missing Info entity save

  @CP-25818 @CP-25818-07 @crm-regression @ui-ats @sang
  Scenario: Select to cancel edit comment twice for Missing info save
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
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
    And I click on Missing Info comment field to enter "RANDOM TEN"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "EDIT COMMENT" from the User Icon menu for Missing Info Save
    And I Click on "CANCEL" for Missing Info Details panel EDIT COMMENT
    And I verify Missing Info Panel EDIT COMMENT section has collapsed
    Then I verify "RANDOM TEN" comment box for successful Missing Info entity save
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "EDIT COMMENT" from the User Icon menu for Missing Info Save
    And I Click on "CANCEL" for Missing Info Details panel EDIT COMMENT
    And I verify Missing Info Panel EDIT COMMENT section has collapsed
    Then I verify "RANDOM TEN" comment box for successful Missing Info entity save

  @CP-23513 @CP-23513-01 @crm-regression @ui-ats @sang
  Scenario: Verify dropdown menu disabled and Satisfied displayed in green and Updated labels replaced by Completed By
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    Then I saved logged in user ID
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
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
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify the Missing Info user icon is disabled
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS    |
      | SATISFIED |
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I verify the "SATISFY LABELS" in expanded Missing Information save
      | MI ID# | CREATED BY | CREATED ON | COMPLETED BY | COMPLETED ON |
    And I verify the "VALUES" in expanded Missing Information save
      | CREATED BY | CREATED ON | COMPLETED BY | COMPLETED ON |

  @CP-23513 @CP-23513-02 @crm-regression @ui-ats @sang
  Scenario: Save Satisfy record with comment and verify the comment is appended above the existing comment
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
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
    And I click on Missing Info comment field to enter "200 CHAR COUNT"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify Satisfy-Disregarded missing Info Comment is appended above the existing comment

  @CP-23513 @CP-23513-03 @crm-regression @ui-ats @sang
  Scenario: Click on Cancel button for Satisfy option in Missing Info panel
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
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
    And I click on Missing Info comment field to enter "200 CHAR COUNT"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I Click on "CANCEL" for Missing Info Details panel EDIT COMMENT
    And I verify Missing Info Panel EDIT COMMENT section has collapsed

  @CP-23513 @CP-23513-04 @crm-regression @ui-ats @sang
  Scenario: Missing Info Satisfy navigate Cancel and navigate away
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
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
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I click on application tracking tab to navigate to Application Tracking page
    And verify the "If you continue, all the captured information will be lost" warning message
    When I click Cancel button inside the warning message
    And I navigate to application tab page
    And I verify I am on application details tab by clicking on continue

  @CP-23837 @CP-23837-01 @CP-23837-03 @CP-23837-06 @crm-regression @ui-ats @burak
  Scenario: Verify max 500 characters, dropdown menu disabled and DISREGARDED displayed in black and Updated labels replaced by Completed By
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to Create "MEDICAL ASSISTANCE" application page
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
    And I click on Missing Info comment field to enter "RANDOM TEN"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM 489 AGAIN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS      |
      | DISREGARDED |
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I verify the "DISREGARDED LABELS" in expanded Missing Information save
      | MI ID# | CREATED BY | CREATED ON | COMPLETED BY | COMPLETED ON |
    And I verify the "VALUES" in expanded Missing Information save
      | CREATED BY | CREATED ON | COMPLETED BY | COMPLETED ON |

  @CP-23837 @CP-23837-07 @crm-regression @ui-ats @burak
  Scenario: Save Disregard record with comment and verify the comment is appended above the existing comment
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
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
    And I click on Missing Info comment field to enter "200 CHAR COUNT"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify Satisfy-Disregarded missing Info Comment is appended above the existing comment

  @CP-23837 @CP-23837-02 @crm-regression @ui-ats @burak
  Scenario: Verify Disregarded MI entity Displays in Order
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | SSN | DOB | First Name |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    Then I verify the order for the created MI entities on Missing Information Tab

  @CP-23837 @CP-23837-04 @crm-regression @ui-ats @burak
  Scenario: Verify after  User clicking cancel still on the MI tab for DISREGARD button
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
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
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "CANCEL" for Missing Info Details panel EDIT COMMENT
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |
    And I verify user stays on Application Missing Info tab

  @CP-23837 @CP-23837-05 @crm-regression @ui-ats @burak
  Scenario: Verify When user remains on the Missing Information Tab when Clicks cancel on Warning Message
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to Create "MEDICAL ASSISTANCE" application page
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
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    When I click on application tracking tab to navigate to Application Tracking page
    And I verify "If you continue, all the captured information will be lost" warning message in Application Missing Info tab
    And I click "Cancel" on warning message for Application Missing Information Tab
    And I verify user stays on Application Missing Info tab

  @CP-23837 @CP-23837-05.2 @crm-regression @ui-ats @burak
  Scenario: Verify When changes are not saved when User clicks continue on Warning Message for Missing Information Tab
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to Create "MEDICAL ASSISTANCE" application page
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
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    When I click on application tracking tab to navigate to Application Tracking page
    And I verify "If you continue, all the captured information will be lost" warning message in Application Missing Info tab
    And I click "Continue" on warning message for Application Missing Information Tab
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    And I Verify Missing Information Save "ROW ONE" for following data
      | STATUS  |
      | PENDING |

  @CP-23837 @CP-23837-08 @crm-regression @ui-ats @burak
  Scenario: Verify User is not able to DISREGARD without entering new comment
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
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I verify "Comment Section is required and cannot be left blank" warning message in Application Missing Info tab

  @CP-26833 @CP-26833-01 @crm-regression @ui-ats @burak
  Scenario: Verify Filtering Only One Status and Displaying Order as Reverse Chronological on Missing Information Panel
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | 2021-06-03               |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status  | comments |
      | SSN           | Appeal           | PENDING | 3        |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status  | comments |
      | SSN           | Appeal           | PENDING | 2        |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status  | comments |
      | SSN           | Appeal           | PENDING | 1        |
    And I POST ATS save missing information api
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    And I click Missing Info Filter Column
    When I click "PENDING" button on Missing Info Filter Column
    Then I should see following Missing Information Status Entities only
      | PENDING |
    Then I verify MI entities order as reverse chronological using "Comments"

  @CP-26833 @CP-26833-02 @crm-regression @ui-ats @burak
  Scenario: Verify Filtering More than One Status on Missing Information Panel
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | 2021-06-03               |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status      |
      | SSN           | Appeal           | DISREGARDED |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status    |
      | SSN           | Appeal           | SATISFIED |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status  |
      | SSN           | Appeal           | PENDING |
    And I POST ATS save missing information api
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    And I click Missing Info Filter Column
    When I click "DISREGARDED" button on Missing Info Filter Column
    Then I should see following Missing Information Status Entities only
      | DISREGARDED |
    When I click "SATISFIED" button on Missing Info Filter Column
    Then I should see following Missing Information Status Entities only
      | SATISFIED | DISREGARDED |
    When I click "PENDING" button on Missing Info Filter Column
    Then I should see following Missing Information Status Entities only
      | PENDING | SATISFIED | DISREGARDED |

  @CP-26833 @CP-26833-03 @CP-26833-04 @crm-regression @ui-ats @burak
  Scenario: Verify Clear Button and Filter Column  Text on Filter Column
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | Renewal          | Phone     | true                          | 2021-06-03               |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status      |
      | SSN           | Appeal           | DISREGARDED |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status    |
      | SSN           | Appeal           | SATISFIED |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | attributeName | entityRecordType | status  |
      | SSN           | Appeal           | PENDING |
    And I POST ATS save missing information api
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify I see following text "Filter Column" when I hover over the Filter Column Button
    And I click Missing Info Filter Column
    When I click "DISREGARDED" button on Missing Info Filter Column
    When I click "SATISFIED" button on Missing Info Filter Column
    Then I should see following Missing Information Status Entities only
      | SATISFIED | DISREGARDED |
    When I click "CLEAR" button on Missing Info Filter Column
    And I click Missing Info Filter Column
    And I verify All the checkboxes selected is removed on Filter Column
    Then I should see following Missing Information Status Entities only
      | PENDING | SATISFIED | DISREGARDED |

  @CP-39069 @CP-39069-01 @crm-regression @ui-ats @ozgen
  Scenario: Verification of MI update status as Red font Review
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
  #  And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated create missing information api for ats using "MI" service
    And I initiate create missing information api for "PRIMARY INDIVIDUAL" using "MI" service
      | recordClass | attributeName            | entityRecordType             | status  | attributeValue | comments |
      | Data        | Citizenship Verification | Application Consumer Program | PENDING | RANDOM         | RANDOM   |
    And I provide the following request parameters to store a Missing Information Dependent Entity using "MI" service
      | dependentType      |
      | PRIMARY INDIVIDUAL |
    And I POST ATS save missing information api
    Then I get response from missing information ats api with status code "200"
    And I get Missing Info Details from the "Save" API response
    And I initiate update missing information API with following values
      | COMMENTS   | random |
      | STATUS     | REVIEW |
      | UPDATED BY | 3499   |
    When I get Missing Info Details from the "Update" API response
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify that Review status is getting displayed as Red

  @CP-37858 @CP-37858-01 @crm-regression @ui-ats @vinuta
  Scenario: System automatically satisfies missing information - First & Last Name
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationDeadlineDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      |[blank]|                  | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    And I verify the number of missing info Rows are "2"
    And I Verify Missing Information Save "ROW ONE" for following data
      | CATEGORY             | TYPE      | STATUS  |
      | Application Consumer | Last Name | PENDING |
    And I Verify Missing Information Save "ROW TWO" for following data
      | CATEGORY             | TYPE       | STATUS  |
      | Application Consumer | First Name | PENDING |
    When I click on application tab page after creating application
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Random 5 |
      | LAST NAME  | Random 5 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I click on Save button on Create Application Page
    And Wait for 8 seconds
    And I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify the number of missing info Rows are "2"
    And I Verify Missing Information Save "ROW ONE" for following data
      | CATEGORY             | TYPE      | STATUS    |
      | Application Consumer | Last Name | SATISFIED |
    And I Verify Missing Information Save "ROW TWO" for following data
      | CATEGORY             | TYPE       | STATUS    |
      | Application Consumer | First Name | SATISFIED |
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I verify the "VALUES" in expanded Missing Information save
      | COMPLETED BY | COMPLETED ON |
    And I click on "ROW TWO CARROT" button for Missing Info in Application Missing Info tab
    And I verify the "VALUES" in expanded Missing Information save
      | COMPLETED BY ATS | COMPLETED ON |

  @CP-37858 @CP-37858-02 @crm-regression @ui-ats @vinuta
  Scenario: System automatically satisfies MI - Gender
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationDeadlineDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given  I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on application tab page after creating application
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Gender |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I verify the number of missing info Rows are "1"
    And I Verify Missing Information Save "ROW ONE" for following data
      | CATEGORY             | TYPE   | STATUS  |
      | Application Consumer | Gender | PENDING |
    When I click on application tab page after creating application
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | GENDER | Male |
    And I click on Save button on Create Application Page
    And Wait for 8 seconds
    And I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify the number of missing info Rows are "1"
    And I Verify Missing Information Save "ROW ONE" for following data
      | CATEGORY             | TYPE   | STATUS    |
      | Application Consumer | Gender | SATISFIED |
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I verify the "VALUES" in expanded Missing Information save
      | COMPLETED BY ATS | COMPLETED ON |

  @CP-37858 @CP-37858-03 @crm-regression @ui-ats @vinuta
  Scenario Outline: System automatically satisfies MI - SSN,DOB
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationDeadlineDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | CURRENT YYYYMMDD        | true                          | CURRENT YYYYMMDD         | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn   | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | <dob>       | <ssn> | Male       |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given  I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    And I verify the number of missing info Rows are "1"
    And I Verify Missing Information Save "ROW ONE" for following data
      | CATEGORY             | TYPE   | STATUS  |
      | Application Consumer | <type> | PENDING |
    When I click on application tab page after creating application
    And I click on the Edit button for the Primary Individual Details
    And I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | <type> | <value> |
    And I click on Save button on Create Application Page
    And Wait for 8 seconds
    And I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify the number of missing info Rows are "1"
    And I Verify Missing Information Save "ROW ONE" for following data
      | CATEGORY             | TYPE   | STATUS    |
      | Application Consumer | <type> | SATISFIED |
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I verify the "VALUES" in expanded Missing Information save
      | COMPLETED BY ATS | COMPLETED ON |
    Examples:
      | dob        | ssn        | type | value            |
      | RANDOM DOB |[blank]| SSN  | Numeric 9        |
      |[blank]| RANDOM SSN | DOB  | random past date |

  @CP-37858 @CP-37858-04 @crm-regression @ui-ats @vinuta
  Scenario: System automatically satisfies missing information - Application Signature
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationDeadlineDate | applicationSignatureExistsInd | applicationSignatureDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | CURRENT YYYYMMDD        | false                         |[blank]| true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    When I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    And I verify the number of missing info Rows are "1"
    And I Verify Missing Information Save "ROW ONE" for following data
      | CATEGORY    | TYPE               | STATUS  |
      | Application | Application Signed | PENDING |
    When I click on application tab page after creating application
    And I click on the Edit button for the Primary Individual Details
    And I select Primary Individual Application information with the following
      | SIGNATURE      | Yes    |
      | SIGNATURE DATE | random |
    And I click on Save button on Create Application Page
    And Wait for 8 seconds
    And I click on missing information tab to navigate to Missing Information page from Application Tracking Page
    Then I verify the number of missing info Rows are "1"
    And Wait for 5 seconds
    And I Verify Missing Information Save "ROW ONE" for following data
      | CATEGORY    | TYPE               | STATUS    |
      | Application | Application Signed | SATISFIED |
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I verify the "VALUES" in expanded Missing Information save
      | COMPLETED BY ATS | COMPLETED ON |