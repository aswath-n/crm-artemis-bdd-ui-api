Feature: ATS Application Missing Info and Eligibility Related Events

  @CP-23000 @CP-23000-01 @ats-events @burak
  Scenario Outline: Validation of APPLICATION_CONSUMER_ELIGIBILITY_SAVE_EVENT when setting Eligibility to Pending
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false        | false          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
      | Pregnancy Assistance |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Medicaid |
      | Pregnancy Assistance |
    And I POST ATS save application api
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
    Then I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    And I verify "6" "APPLICATION_CONSUMER_ELIGIBILITY_SAVE_EVENT" generated and verify details
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                                   | module      | eventType     | projectName |
      | APPLICATION_CONSUMER_ELIGIBILITY_SAVE_EVENT | APPLICATION | STATUS UPDATE |[blank]|

  @CP-24082 @CP-24082-01 @ats-events @vinuta
  Scenario Outline: Request MISSING_INFORMATION_ITEM_SAVE_EVENT when the Application Member's Missing Information is created
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationCycle | channelId |
      | Long Term Care  | CURRENT YYYYMMDD        | New              | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
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
    And I click on Missing Info comment field to enter "Random 30"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I save the following Missing Information data value for verification
      | MI ID# |
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "MISSING_INFORMATION_ITEM_SAVE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                           | module                   | eventType                | projectName |
      | MISSING_INFORMATION_ITEM_SAVE_EVENT | MISSING_INFORMATION_ITEM | Missing Information Item | BLCRM       |

  @CP-24082 @CP-24082-02 @ats-events @vinuta
  Scenario Outline: Request multiple MISSING_INFORMATION_ITEM_SAVE_EVENT when the Application Member's Missing Information is created for multiple From
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Citizenship Verification |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY | APP MEM ONE |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on Missing Info comment field to enter "RANDOM 30"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "ROW ONE CARROT" button for Missing Info in Application Missing Info tab
    And I save the following Missing Information data value for verification
      | MI ID# |
    And I click on "ROW TWO CARROT" button for Missing Info in Application Missing Info tab
    And I save the following Missing Information data value for verification
      | MI ID# |
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "<numberOfEvents>" "<eventName>" generated and verify details
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                           | module                   | eventType                | projectName | numberOfEvents |
      | MISSING_INFORMATION_ITEM_SAVE_EVENT | MISSING_INFORMATION_ITEM | Missing Information Item | BLCRM       | 2              |

  @CP-24082 @CP-24082-03 @ats-events @vinuta
  Scenario Outline: Request multiple MISSING_INFORMATION_ITEM_SAVE_EVENT when the Application Member's Missing Information is created for multiple Type
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle | channelId |
      | Medical Assistance | CURRENT YYYYMMDD        | New              | Phone     |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB | First Name | Last Name | Gender | SSN |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | APP MEM ONE |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on Missing Info comment field to enter "Typed in for test"
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "<numberOfEvents>" "<eventName>" generated and verify details
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                           | module                   | eventType                | projectName | numberOfEvents |
      | MISSING_INFORMATION_ITEM_SAVE_EVENT | MISSING_INFORMATION_ITEM | Missing Information Item | BLCRM       | 5              |

  @CP-24083 @CP-24083-01 @ats-events @vinuta
  Scenario Outline: Request MISSING_INFORMATION_ITEM_UPDATE_EVENT when MI is Satisfied
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate |
      | Long Term Care  | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Citizenship Verification |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "SATISFY" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "<numberOfEvents>" "<eventName>" generated and verify details
    Then I verify "MISSING_INFORMATION_ITEM_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                             | module                   | eventType                  | projectName | numberOfEvents |
      | MISSING_INFORMATION_ITEM_UPDATE_EVENT | MISSING_INFORMATION_ITEM | Missing Information Update | BLCRM       | 1              |

  @CP-24083 @CP-24083-02 @ats-events @vinuta
  Scenario Outline: Request MISSING_INFORMATION_ITEM_UPDATE_EVENT when MI is Disregarded
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
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | Citizenship Verification |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "DISREGARD" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN EDIT AGAIN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "<numberOfEvents>" "<eventName>" generated and verify details
    Then I verify "MISSING_INFORMATION_ITEM_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                             | module                   | eventType                  | projectName | numberOfEvents |
      | MISSING_INFORMATION_ITEM_UPDATE_EVENT | MISSING_INFORMATION_ITEM | Missing Information Update | BLCRM       | 1              |

  @CP-24083 @CP-24083-03 @ats-events @vinuta
  Scenario Outline: Request MISSING_INFORMATION_ITEM_UPDATE_EVENT when MI comment is updated
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
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I navigate to application tab page
    And I gather application consumer full name data information for Missing Info services
    When I click on missing information tab to navigate to Missing Information page
    And I click on ADD MISSING INFO Button in Application Missing Info Tab
    And I select "CATEGORY" dropdown in MISSING INFO DETAILS panel to select
      | Application Consumer |
    And I select "TYPE" dropdown in MISSING INFO DETAILS panel to select
      | DOB |
    And I select "FROM" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I select "NEED FOR" dropdown in MISSING INFO DETAILS panel to select
      | PRIMARY |
    And I click on "SAVE" button for Missing Info in Application Missing Info tab
    And I click on "USER ICON" button for Missing Info in Application Missing Info tab
    And I click on the "EDIT COMMENT" from the User Icon menu for Missing Info Save
    And I enter "RANDOM TEN EDIT AGAIN" character comments in to the edit comment box in Missing Info Panel
    And I Click on "SAVE" for Missing Info Details panel EDIT COMMENT
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "<numberOfEvents>" "<eventName>" generated and verify details
    Then I verify "MISSING_INFORMATION_ITEM_UPDATE_EVENT" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                             | module                   | eventType                  | projectName | numberOfEvents |
      | MISSING_INFORMATION_ITEM_UPDATE_EVENT | MISSING_INFORMATION_ITEM | Missing Information Update | BLCRM       | 1              |

  @CP-23099 @CP-23099-01 @ats-events @sang
  Scenario Outline: Verify successful Eligibility update event for Eligible status update
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on save button for the Members Info Panel
    And I will take trace Id for Events and "<eventType>" and initiate Event GET API
    And I will run the Event GET API for application events "<eventName>" and "<module>" and "<correlation>"
    Then I verify "<eventName>" details in the event payload
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API for ATS
    Then I will verify response has event Id and "<eventName>" and subscriberId for ATS
    Examples:
      | eventName                                     | module      | eventType          | projectName |
      | APPLICATION_CONSUMER_ELIGIBILITY_UPDATE_EVENT | APPLICATION | Eligibility update | BLCRM       |