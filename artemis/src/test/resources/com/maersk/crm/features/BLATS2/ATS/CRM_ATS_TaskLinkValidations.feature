Feature: ATS Task Link Validations for BLATS2

  @CP-15566 @CP-15566-01 @crm-regression @ui-ats-blats2 @burak
  Scenario: Display linked Applications on Application Tracking Tab
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS    | Duplicate |
      | UPDATED BY                | 3163      |
      | CREATED BY                | 3163      |
      | DUPLICATED APPLICATION ID | -1        |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS    | Duplicate |
      | UPDATED BY                | 3163      |
      | CREATED BY                | 3163      |
      | DUPLICATED APPLICATION ID | -2        |
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I verify The Link Header is displayed on Application Tracking Tab
    And I verify the following fields for Links Tab
      | ICON        | ID | NAME        | TYPE                   | STATUS DATE  | STATUS    |
      | Application | -2 | Application | Medical Assistance New | CURRENT DATE | Submitted |
      | Application | -1 | Application | Medical Assistance New | CURRENT DATE | Submitted |
    Then I click the first ID on Links Panel on Application Tracking Tab
    And I verify I navigated to duplicated application page
    And I Verify that user is back to Create Application page

  @CP-15566 @CP-15566-02 @crm-regression @ui-ats-blats2 @burak
  Scenario: Display linked Applications on Application Tracking Tab Pagination
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS    | Duplicate |
      | UPDATED BY                | 3163      |
      | CREATED BY                | 3163      |
      | DUPLICATED APPLICATION ID | -1        |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS    | Duplicate |
      | UPDATED BY                | 3163      |
      | CREATED BY                | 3163      |
      | DUPLICATED APPLICATION ID | -2        |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS    | Duplicate |
      | UPDATED BY                | 3163      |
      | CREATED BY                | 3163      |
      | DUPLICATED APPLICATION ID | -3        |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS    | Duplicate |
      | UPDATED BY                | 3163      |
      | CREATED BY                | 3163      |
      | DUPLICATED APPLICATION ID | -4        |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS    | Duplicate |
      | UPDATED BY                | 3163      |
      | CREATED BY                | 3163      |
      | DUPLICATED APPLICATION ID | -5        |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS    | Duplicate |
      | UPDATED BY                | 3163      |
      | CREATED BY                | 3163      |
      | DUPLICATED APPLICATION ID | -6        |
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I verify The Link Header is displayed on Application Tracking Tab
    Then I verify there is "5" linked entity displays on Link Panel
    And I click on the pagination dropdown on Link Panel and change pagination as "Show 10"
    Then I verify there is "6" linked entity displays on Link Panel