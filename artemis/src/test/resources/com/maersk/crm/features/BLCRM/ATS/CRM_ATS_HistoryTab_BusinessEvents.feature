Feature: ATS: History Tab, Displaying Business Events

  @CP-31968 @CP-31968-01 @crm-regression @ui-ats @ozgen
  Scenario: Application Cleared Event on History Tab
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    Then I logged into CRM
    When I navigate to case and consumer search page
    And I search for consumer with first name from ATS application from "API"
    And I click on Search Button
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "History" Tab on Contact Dashboard Page
    Then I verify that default values are selected to get business event for "Primary Individual"
    Then I verify "Application Cleared" business event details on History Tab

  @CP-33176 @CP-33176-01 @crm-regression @ui-ats @ozgen
  Scenario Outline: Application Expired Event on History Tab
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I clear application save submit "REQUEST" list
    And I clear application save submit "AppIDList" list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd | applicationSignatureDate | applicationSignatureExistsInd |
      | <Application Type> | CURRENT YYYYMMDD        | true         | false          | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | <Program> |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Mail |
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
 #   And I clear application save submit "REQUEST" list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | applicationId      | applicationDeadlineDate |
      | <Application Type> | <Received Date>         | true         | created previously | <DeadlineDate>          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | applicationConsumerId   |
      | applicationConsumerIdPI |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | workPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I clear application save submit "REQUEST" list
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "<status>" on the response
    Then I logged into CRM
    When I navigate to case and consumer search page
    And I search for consumer with first name from ATS application from "API"
    And I click on Search Button
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "History" Tab on Contact Dashboard Page
    Then I verify that default values are selected to get business event for "Primary Individual"
    Then I verify "Application Expired" business event details on History Tab
    Examples:
      | Application Type   | Program  | Received Date | DeadlineDate | status  |
      | Medical Assistance | Medicaid | PRIOR 45      | PRIOR 1      | Expired |
      | Long Term Care     | HCBS     | PRIOR 90      | PRIOR 1      | Expired |

  @CP-33173 @CP-33173-01 @crm-regression @ui-ats @ozgen
  Scenario: Application Program Eligibility Status Update event on History Tab
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I saved logged in user ID
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on "SUB-PROGRAM" dropdown and select the following values
      | 20 - Sub-Program b |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on save button for the Members Info Panel
    When I navigate to case and consumer search page
    And I search for consumer with first name from ATS application from "API"
    And I click on Search Button
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "History" Tab on Contact Dashboard Page
    Then I verify that default values are selected to get business event for "Primary Individual"
    Then I verify "Application Program Eligibility Status" business event details on History Tab

  @CP-33174 @CP-33174-01 @crm-regression @ui-ats @ozgen
  Scenario: Application Missing Information Business Event on History Tab
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureExistsInd | applicationSignatureDate |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | true                          | CURRENT YYYYMMDD         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    And I initiated View application api for API created applications
    And I run the View Application Details API for the Created Application
    And I verify application status as "Determining" on the response
    Then I logged into CRM
    When I navigate to case and consumer search page
    And I search for consumer with first name from ATS application from "API"
    And I click on Search Button
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "History" Tab on Contact Dashboard Page
    Then I choose consumer name value from dropdown as "Primary Individual"
    Then I verify that default values are selected to get business event for "Primary Individual"
    Then I verify "Application Missing Information" business event details on History Tab




