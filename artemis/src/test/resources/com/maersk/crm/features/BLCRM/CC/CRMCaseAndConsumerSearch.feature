Feature: Validation of Case and consumer search Page


    #Verify warning is Display when User attempts to return to Active Contact and has unsaved data entry
  @CP-689 @CP-689-05 @vidya @crm-regression
  Scenario: Verify warning is Display
    Given I logged into CRM and click on initiate contact
    When I navigate to case and consumer search page
    And I search for record by value "usha"
    And I expand first contact record in search results on case and consumer search page
    And I click on edit button on contact details tab
    And I select "Other" on preferred language dropdown
    And I click on Active Contact widget
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      |Case And Consumer Search|
    And I click on Active Contact widget
    And I click on continue button on warning message
    Then Verify should I return back to Active Contact screen



   #Failing due to Defect -logged CP-9561
   #Verify warning is Display when User attempts to return to Create Task with unsaved data entry for ADD functionality
  @CP-670 @CP-670-01 @paramita @regression @crm @crm-regression
  Scenario: Verify warning message is Display when User attempts to Create Task with unsaved data entry
    Given I logged into CRM
    When I navigate to case and consumer search page
    And I search for record by value "Emma"
    And I click first contact record in search results on case and consumer search page
    And I click on Add button on DemographicInfo tab
    And I add all mandatory details of primary individual
    When I click to navigate "General" task page
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      |PRIMARY INDIVIDUALS|
    When I navigate to given "General" task page
    Then I verify warning popup is displayed with message
    And I click on continue button on warning message
    Then I should return back to Create Task screen

  @CP-11150 @CP-11150-03 @vidya @crm-regression @ui-wf
  Scenario: View Task Details when clicked on Task ID from Task Summary page
    Given I logged into CRM
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Mike" and Last Name as "Pond"
    When I expand the record to navigate Case Consumer Record from manual view
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I navigate to newly created task in Task & Service Request Tab
    And I expend first Task from the list by clicking in Task ID
    And I will click on back arrow on view task page
    Then I Verify user is navigate back to Task and service Request Page



  #Failing due to Defect -logged CP-9561
  #Verify warning is Display when User attempts to navigate Create Task screen with unsaved data entry for EDIT functionality
  @CP-670 @CP-670-02 @paramita @regression @crm @crm-regression
  Scenario: Verify warning message is Display when User attempts to Create Task with unsaved data entry
    Given I logged into CRM
    When I navigate to case and consumer search page
    And I search for record by value "Emma"
    And I click first contact record in search results on case and consumer search page
    And I click on primary individual record on DemographicInfo tab
    And I update "Other" on preferred language dropdown
    When I click to navigate "General" task page
    Then I verify update warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      |PRIMARY INDIVIDUALS|
    When I click to navigate "General" task page
    Then I verify update warning popup is displayed with message
    And I click on continue button on warning message
    Then I should return back to Create Task screen

  @CP-19028 @CP-19028-01 @chopa @ui-cc @crm-regression
  Scenario Outline: Verify CONTINUE AND ADD NEW CONSUMER is not displayed after FN, LN and DOB Match update
    Given I logged into CRM and click on initiate contact
    When I enter First Name as <firstName>, Middle Initial as <middleName>, Last Name as <lastName>, SSN as <ssn>, Date Of Birth as <DOB>, Unique ID as <ID>  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I verify the case is linked to contact
    Then I verify value of First Name as "Kamil", Last Name as "Shikh" and Date Of Birth as "06/06/1986" in Search Results
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add phone number to an existing consumer
    When I click on Create Consumer Button
    And I click on continue button on warning message
    When I update DOB of the consumer in Create Consumer Page
    Then I verify "CONTINUE AND ADD NEW CONSUMER" is disabled in Create Consumer Page
    Examples:
      | firstName    | middleName | lastName   | ssn   | DOB          | ID   |
      | 'Kamil'      | ''         | 'Shikh'    | ''    | '06/06/1986' | ''   |

  @CP-25112 @CP-25112-01 @chopa @crm-regression @ui-cc
  Scenario: Verify consumer is inactive in Active Search BLCRM
    Given I logged into CRM and click on initiate contact
    #When I click on initiate contact record
    When I searched customer have First Name as "Rich" and Last Name as "Beer"
    And I verify search result for consumer is inactive

  @CP-25112 @CP-25112-02 @chopa @crm-regression @ui-cc
  Scenario: Verify consumer is inactive in Manual Search BLCRM
    Given I logged into CRM and click on initiate contact
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Rich" and Last Name as "Beer"
    And I verify search result for consumer is inactive

  @CP-25112 @CP-25112-03 @chopa @crm-regression @ui-cc-in
  Scenario: Verify consumer is inactive in Active Search IN-EB
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I click on initiate contact record
    When I searched customer have First Name as "Test" and Last Name as "Fsdfsdf"
    And I verify search result for consumer is inactive

  @CP-25112 @CP-25112-04 @chopa @crm-regression @ui-cc-in
  Scenario: Verify consumer is inactive in Manual Search IN-EB
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Test" and Last Name as "Fsdfsdf"
    And I verify search result for consumer is inactive

  @CP-25112 @CP-25112-05 @chopa @crm-regression @ui-cc
  Scenario: Verify consumer is inactive in Active Search CoverVA
    Given I logged into CRM with "Service Tester 3" and select a project "CoverVA"
    When I click on initiate contact record
    When I searched customer have First Name as "Rich" and Last Name as "Beer"
    And I verify search result for consumer is inactive

  @CP-25112 @CP-25112-06 @chopa @crm-regression @ui-cc
  Scenario: Verify consumer is inactive in Manual Search CoverVA
    Given I logged into CRM with "Service Tester 3" and select a project "CoverVA"
    When I navigate to case and consumer search page
    When I searched customer have First Name as "Rich" and Last Name as "Beer"
    And I verify search result for consumer is inactive

  @CP-23889 @CP-23889-01 @Beka @ui-cc @crm-regression
  Scenario:  Verify I do not see a carat expansion/Authentication Grid with the option to link
    Given I logged into CRM
    And I click on Case Consumer Search page
    And I search for consumer have First Name as "Umesh" and Last Name as "Kumar"
    And I click on Search Button
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I populate email and phone number and click on create consumer button
    Then I do not see a carat expansion-Authentication Grid with the option to link

  @CP-29234 @CP-29234-01 @Beka @crm-regression @ui-cc
  Scenario: Verify Authentication Data Point: Phone for consumer with phone type cell
    Given I logged into CRM and click on initiate contact
    And I search for record by value "JaredqrWTy"
    And I click first contact record in search results
    Then Verify Phone Number "cell" will be selectable as an authentication data point

  @CP-29234 @CP-29234-02 @Beka @crm-regression @ui-cc
  Scenario: Verify Authentication Data Point: Phone for consumer with phone type home
    Given I logged into CRM and click on initiate contact
    And I search for record by value "Sdfasdfasdga"
    And I click first contact record in search results
    Then Verify Phone Number "home" will be selectable as an authentication data point

  @CP-29234 @CP-29234-03 @Beka @crm-regression @ui-cc
  Scenario: Verify Authentication Data Point: Phone for consumer with phone type work
    Given I logged into CRM and click on initiate contact
    And I search for record by value "JaredEQOxJ"
    And I click first contact record in search results
    Then Verify Phone Number "work" will be selectable as an authentication data point

  @CP-29234 @CP-29234-04 @Beka @crm-regression @ui-cc
  Scenario: Verify priority of phone type if consumer has active home phone
    Given I logged into CRM and click on initiate contact
    And I search for record by value "JaredsHhLu"
    And I click first contact record in search results
    Then Verify Phone Number "home" will be selectable as an authentication data point

  @CP-29234 @CP-29234-05 @Beka @crm-regression @ui-cc
  Scenario: Verify priority of phone type if consumer has inactive home phone and active cell
    Given I logged into CRM and click on initiate contact
    And I search for record by value "JaredvlQiE"
    And I click first contact record in search results
    Then Verify Phone Number "cell" will be selectable as an authentication data point

  @CP-36577 @CP-36577-01 @ui-cc-dc @crm-regression @DC-CC-UI-Regression @chopa
  Scenario: DC-EB: Configure Consumer Search Fields
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I verify "StateID" field accepts 30 characters
    And I verify "SSN" field accepts 9 characters
    And I verify "Name" field accepts 60 characters
    And I verify "DOB" field accepts 8 characters

  @CP-36577 @CP-36577-02 @ui-cc-dc @crm-regression @DC-CC-UI-Regression @chopa
  Scenario: DC-EB: Configure Consumer Search Fields - Advanced Search
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I click on advance search icon
    And I verify "Address" field accepts 50 characters
    And I verify "Zip" field accepts 5 characters
    And I verify "Phone" field accepts 10 characters
    And I verify "Email" field accepts 30 characters
    And I verify "ConsumerID" field accepts 30 characters

  @CP-36577 @CP-36577-03 @CP-36578 @CP-36578-01 @ui-cc-dc @crm-regression @DC-CC-UI-Regression @chopa
  Scenario Outline: DC-EB: General Consumer Search: Search Input Parameters
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "<field>" with value "<value>"
    Then I verify search result displays "<field>" with value "<value>"
    Examples:
      |field      |value       |
      |StateID    |M1161       |
      |SSN        |660000011   |
      |Name       |Lee,Lilly   |
      |DOB        |06/18/1985  |
      |CaseID     |371548325121|

  @CP-36577 @CP-36577-04 @CP-36578 @CP-36578-02 @ui-cc-dc @crm-regression @DC-CC-UI-Regression @chopa
  Scenario Outline: DC-EB: Advanced Consumer Search: Advanced Search Input Parameters
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I click on advance search icon
    And I search for consumer by "<field>" with value "<value>"
    Then I verify search result displays "<field>" with value "<value>"
    Examples:
      |field         |value              |
      |Address       |8888 Parker Dr     |
      |City,Zip      |Denver,11111-1111  |
      |Phone         |(444) 444-1133     |
      |Email         |cholpon@yahoo.com  |

  @CP-36577 @CP-36577-05 @CP-40420 @CP-40420-01 @ui-cc-dc @crm-regression @DC-CC-UI-Regression @chopa
  Scenario: DC-EB: Search by Search By dropdown
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I click on advance search icon
    And I verify Search By IDs dropdown options are displayed

  @CP-36577 @CP-36577-06 @CP-40420 @CP-40420-02 @ui-cc-dc @crm-regression @DC-CC-UI-Regression @chopa
  Scenario: DC-EB: Search Parameter Field Level Error Validation
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I enter invalid value for "SSN" to verify Error Message
    And I enter invalid value for "DOB" to verify Error Message
    And I click on advance search icon
    And I enter invalid value for "Address" to verify Error Message
    And I enter invalid value for "City" to verify Error Message
    And I enter invalid value for "Zip" to verify Error Message
    And I enter invalid value for "Phone" to verify Error Message
    And I enter invalid value for "Email" to verify Error Message

  @CP-36578 @CP-36578-03 @CP-40421 @CP-40421-01 @ui-cc-dc @crm-regression @DC-CC-UI-Regression @chopa
  Scenario: DC-EB: Validate existing Consumer's DOB is masked by default on Search Result after providing DOB
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "DOB" with value "06/18/1985"
    And I verify "DOB" field is in "**/**/****" format

  @CP-36578 @CP-36578-04 @CP-40421 @CP-40421-02 @ui-cc-dc @crm-regression @DC-CC-UI-Regression @chopa
  Scenario: DC-EB: Validate existing Consumer's SSN is masked by default on Search Result after providing SSN
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "SSN" with value "660000011"
    And I verify "SSN" field is in "***-**-****" format

  @CP-36578 @CP-36578-05 @CP-40421 @CP-40421-03 @ui-cc-dc @crm-regression @DC-CC-UI-Regression @chopa
  Scenario: DC-EB: Validate field headers are displayed in search result
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    And I search for consumer by "SSN" with value "660000011"
    And I verify field headers after search are displayed