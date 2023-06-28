Feature: ATS Application Business Events

  @CP-31925 @CP-31925-03 @ats-events @chandrakumar
  Scenario Outline:Validation of NEW_APPLICATION event for an application created by UI
    Given I logged into CRM
    When I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    When I click on application tracking tab to navigate to Application Tracking page
    Then I get the application deadline date from application tracking page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "NEW_APPLICATION" details in the event payload

    Examples:
      | eventName       | module      | eventType   | projectName | applicationType    |
      | NEW_APPLICATION | APPLICATION | application |[blank]| LONG TERM CARE     |
      | NEW_APPLICATION | APPLICATION | application |[blank]| MEDICAL ASSISTANCE |

  @CP-32130 @CP-32130-09 @CP-32130-10 @ats-events @chandrakumar
  Scenario Outline:Validation of APPLICATION_CLEARED event for an application created through UI
    Given I logged into CRM
    When I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "<programTypeName>" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | GENDER              | Female             |
      | ARE YOU PREGNANT    | Yes                |
      | NO. BABIES EXPECTED | 8                  |
      | EXPECTED DUE DATE   | random future date |
      | SSN                 | Numeric 9          |
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    Then I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify the response includes "APPLICATION_CLEARED" events for "<applicationType>" appplication
    Examples:
      | eventName           | module      | eventType | projectName | applicationType    | programTypeName |
      | APPLICATION_CLEARED | APPLICATION | Case      |[blank]| LONG TERM CARE     | HCBS            |
      | APPLICATION_CLEARED | APPLICATION | Case      |[blank]| MEDICAL ASSISTANCE | Medicaid        |

  @CP-32130 @CP-32130-11 @CP-32130-12 @ats-events @chandrakumar
  Scenario Outline:Validation of APPLICATION_CLEARED event for an application created through UI with multiple members
    Given I logged into CRM
    When I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "<programTypeName>" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | GENDER              | Female             |
      | ARE YOU PREGNANT    | Yes                |
      | NO. BABIES EXPECTED | 8                  |
      | EXPECTED DUE DATE   | random future date |
      | SSN                 | Numeric 9          |
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    Then I get application id from the Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    And Select the "<programTypeName>" Program(s) for application member
    And click on save button for add application member
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify the response includes "APPLICATION_CLEARED" events for "<applicationType>" appplication
    Examples:
      | eventName           | module      | eventType | projectName | applicationType    | programTypeName |
      | APPLICATION_CLEARED | APPLICATION | Case      |[blank]| LONG TERM CARE     | HCBS            |
      | APPLICATION_CLEARED | APPLICATION | Case      |[blank]| MEDICAL ASSISTANCE | Medicaid        |

  @CP-32140 @CP-32140-09 @CP-32140-10 @ats-events @chandrakumar
  Scenario Outline:Validation of APPLICATION_WITHDRAWN event for an application created through UI with multiple members
    Given I logged into CRM
    When I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "<programTypeName>" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
      | EXTERNAL CONSUMER ID | Alpha-Numeric 15   |
      | EXTERNAL ID TYPE     | random dropdown    |
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    Then I get application id from the Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    And Select the "<programTypeName>" Program(s) for application member
    And click on save button for add application member
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | Other         |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | ORGANIZATION NAME            | RANDOM 5      |
      | ID NUMBER                    | Numeric 5     |
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST   |
      | START DATE                   | RANDOM PAST   |
      | END DATE                     | RANDOM FUTURE |
      | ADDRESS LINE 1               | ALPHA NUMERIC |
      | ADDRESS LINE 2               | ALPHA NUMERIC |
      | CITY                         | RANDOM        |
      | STATE                        | RANDOM        |
      | ZIP CODE                     | RANDOM        |
      | ACCESS TYPE                  | RANDOM        |
      | CORRESPONDENCE               | RANDOM        |
      | AUTHORIZED                   | Yes           |
      | AUTH STATUS                  | SET BY DATES  |
    Then I click on Save button on Create Application Page
    And I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_WITHDRAWN" details in the event payload
    Examples:
      | eventName             | module      | eventType     | projectName | applicationType    | programTypeName |
      | APPLICATION_WITHDRAWN | APPLICATION | STATUS UPDATE | BLCRM       | LONG TERM CARE     | HCBS            |
      | APPLICATION_WITHDRAWN | APPLICATION | STATUS UPDATE | BLCRM       | MEDICAL ASSISTANCE | Medicaid        |

  @CP-32140 @CP-32140-11 @CP-32140-12 @ats-events @chandrakumar
  Scenario Outline:Validation of APPLICATION_WITHDRAWN event for an application created through UI
    Given I logged into CRM
    When I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "<programTypeName>" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
      | EXTERNAL CONSUMER ID | Alpha-Numeric 15   |
      | EXTERNAL ID TYPE     | random dropdown    |
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    Then I verify I see Withdraw button displayed in Application Information panel
    When  I click withdraw dropdown to withdraw the application from the Application Tracking tab
    Then I see APPLICATION WITHDRAW REASON in Application Information panel
    Then I  select "Already Receiving Services" from select reason dropdown
    When I click save button in the select reason
    Then I verify I see warning message "Are you sure you want to Withdraw this Application?"
    When I click Continue button inside the warning message
    When I get the case id from the application tracking page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_WITHDRAWN" details in the event payload
    Examples:
      | eventName             | module      | eventType     | projectName | applicationType    | programTypeName |
      | APPLICATION_WITHDRAWN | APPLICATION | STATUS UPDATE |[blank]| LONG TERM CARE     | HCBS            |
      | APPLICATION_WITHDRAWN | APPLICATION | STATUS UPDATE |[blank]| MEDICAL ASSISTANCE | Medicaid        |

  @CP-32135 @CP-32135-09 @ats-events @chandrakumar
  Scenario Outline:Validation of APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE event for an application created through UI
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "<programTypeName>" as program type
    Then I choose "CHIP" as program type
    Then I fill in the following "<applicationType>" Primary Individual with the following values
      | GENDER              | Female             |
      | ARE YOU PREGNANT    | Yes                |
      | NO. BABIES EXPECTED | 8                  |
      | EXPECTED DUE DATE   | random future date |
      | SSN                 | Numeric 9          |
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    Then I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "ELIGIBILITY STATUS" dropdown for second row and select the following values
      | Eligible |
    And I click on "SUB-PROGRAM" dropdown for second row and select the following values
      | 20 - Sub-Program b |
    And I click on "START DATE" dropdown for second row and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown for second row and select the following values
      | CURRENT DATE |
    And I click on save button for the Members Info Panel
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE" details in the event payload
    Examples:
      | eventName                                     | module      | eventType          | projectName | applicationType    | programTypeName |
      | APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE | APPLICATION | Eligibility update |[blank]| MEDICAL ASSISTANCE | Medicaid        |

  @CP-32135 @CP-32135-10 @CP-32135-11 @ats-events @chandrakumar
  Scenario Outline:Validation of APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE event for an application created through UI with multiple members
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "<programTypeName>" as program type
    Then I fill in the following "<applicationType>" Primary Individual with the following values
      | GENDER              | Female             |
      | ARE YOU PREGNANT    | Yes                |
      | NO. BABIES EXPECTED | 8                  |
      | EXPECTED DUE DATE   | random future date |
      | SSN                 | Numeric 9          |
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    Then I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    Then I verify I see Withdraw button displayed in Application Information panel
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on save button for the Members Info Panel
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE" details in the event payload
    Examples:
      | eventName                                     | module      | eventType          | projectName | applicationType    | programTypeName |
      | APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE | APPLICATION | Eligibility update |[blank]| LONG TERM CARE     | HCBS            |
      | APPLICATION_PROGRAM_ELIGIBILITY_STATUS_UPDATE | APPLICATION | Eligibility update |[blank]| MEDICAL ASSISTANCE | Medicaid        |
