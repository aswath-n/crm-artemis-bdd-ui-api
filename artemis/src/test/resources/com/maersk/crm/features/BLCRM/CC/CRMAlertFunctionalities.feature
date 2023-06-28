Feature: Alert validations

  @CP-25964 @CP-25964-01 @CP-29775-01 @chopa @ui-cc @crm-regression            #failing due to CP-31669
  Scenario: Verify Alert Banner is displayed in Case Summary Page Active Alert
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Pete" and Last Name as "Nate"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page

  @CP-25964 @CP-25964-02 @CP-29775-02 @chopa @ui-cc @crm-regression           #failing due to CP-31669
  Scenario: Verify Alert Banner is displayed in Case Summary Page Past Alert
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "David" and Last Name as "Bennet"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Alert Banner is displayed with text "No Active Alerts" on Case Summary Page

  @CP-25964 @CP-25964-03 @chopa @ui-cc @crm-regression
  Scenario: Verify Alert Banner is displayed in Case Summary Page Future Alert
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Cholpon" and Last Name as "Erkinova"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Alert Banner is displayed with text "No Active Alerts" on Case Summary Page

  @CP-25964 @CP-25964-04 @CP-29775-02 @chopa @ui-cc @crm-regression
  Scenario: Verify Alert Banner is not displayed in Case Summary for consumer that dont have alerts
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FnnHWDj" and Last Name as "LnkESuL"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Alert Banner is not displayed on Case Summary Page

  @CP-29175 @CP-29175-01 @CP-25832 @CP-25832-03 @chopa @ui-cc @crm-regression
  Scenario: Verify "View All Alert" button is displayed in Case Summary
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Xyz" and Last Name as "Shh"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify "View All Alerts" button is displayed on case summary page
    When I click on the Demographic Info Tab
    Then I verify "View All Alerts" button is displayed on case summary page

  @CP-29175 @CP-29175-02 @chopa @ui-cc @crm-regression
  Scenario: Verify Header text "Alerts" and Close X displayed in View All Alerts slider
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Xyz" and Last Name as "Shh"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on View All Alerts button
    Then I verify header text and close button are displayed

  @CP-29175 @CP-29175-03 @chopa @ui-cc @crm-regression
  Scenario: Verify Active Present Alert is displayed in View All Alerts Tab
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FnrLmdl" and Last Name as "LnAyHJW"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on View All Alerts button
    Then I verify active alert with text "Active Alert Banner" is displayed

  @CP-29175 @CP-29175-04 @chopa @ui-cc @crm-regression
  Scenario: Verify Inactive Past Alert is displayed in View All Alerts Tab
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FnrLmdl" and Last Name as "LnAyHJW"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on View All Alerts button
    Then I verify active alert with text "Inactive Past Alert Banner" is displayed

  @CP-29175 @CP-29175-05 @chopa @ui-cc @crm-regression
  Scenario: Verify Inactive Future Alert is displayed in View All Alerts Tab
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FnrLmdl" and Last Name as "LnAyHJW"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on View All Alerts button
    Then I verify active alert with text "Inactive Future Alert Banner" is displayed

  @CP-28681 @CP-28681-01 @chopa @ui-cc @crm-regression
  Scenario: Verify Alert Banner is displayed in Consumer Profile Active Alert
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Irene" and Last Name as "Glass"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page

  @CP-28681 @CP-28681-02 @chopa @ui-cc @crm-regression
  Scenario: Verify Alert Banner is displayed in Consumer Profile Past Alert
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Fatima" and Last Name as "Felish"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Alert Banner is displayed with text "No Active Alerts" on Case Summary Page

  @CP-28681 @CP-28681-03 @chopa @ui-cc @crm-regression
  Scenario: Verify Alert Banner is displayed in Consumer Profile Future Alert
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Rachel" and Last Name as "Red"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify Alert Banner is displayed with text "No Active Alerts" on Case Summary Page

  @CP-28681 @CP-28681-04 @CP-25832 @CP-25832-04 @chopa @ui-cc @crm-regression
  Scenario: Verify "View All Alert" button is displayed in Consumer Profile
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Rachel" and Last Name as "Red"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify "View All Alerts" button is displayed on case summary page
    When I click on the Demographic Info Tab
    Then I verify "View All Alerts" button is displayed on case summary page

  @CP-31567 @CP-31567-01 @chopa @ui-cc @crm-regression
  Scenario: Verify Create Alerts page is present
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "John" and Last Name as "Thomos"
    And I link the contact to an existing Case or Consumer Profile
    And I click on Alert on Hamburder button
    And I verify Create Alert page is displayed

  @CP-31567 @CP-31567-02 @chopa @ui-cc @crm-regression
  Scenario: Verify following fields are displayed in Create Alerts page
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "John" and Last Name as "Thomos"
    And I link the contact to an existing Case or Consumer Profile
    And I click on Alert on Hamburder button
    And I verify the following fields are displayed in Create Alert page
    And I verify Type contains following values
      | STATIC     |
      | ACTIONABLE |

  @CP-32487 @CP-32487-01 @CP-25832 @CP-25832-01 @chopa @ui-cc @crm-regression
  Scenario: Verify Alert Banner is displayed While User Navigates to All Tabs - Case Summary
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Pete" and Last Name as "Nate"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page
    When I click on the Demographic Info Tab
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page
    When I click on "History" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page

  @CP-32487 @CP-32487-02 @CP-25832 @CP-25832-02 @chopa @ui-cc @crm-regression
  Scenario: Verify Alert Banner is displayed While User Navigates to All Tabs - Consumer Profile
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Red" and Last Name as "Cooks"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page
    When I click on the Demographic Info Tab
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page
    When I click on "History" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Second Active Alert" on Case Summary Page

#  @CP-29543 @CP-29543-01 @ui-cc @Beka @crm-regression                        # muted, reason: Alert can be stored at the CASE level but only be queried at the COSUMER level
#  Scenario: Verify Add Tool Tip to Alerts
#    Given I logged into CRM
#    And I click on Alert on Hamburder button
#    And I successfully create an "Active" Alert for consumer
#    And I will click on create link button on manual create alert page
#    When I search consumer has first name as "Dfghdfthert"
#    And I click on search button on manual create alert page
#    And I expend the Case Consumer this contact relates to in search result
#    When I select 'Link to Case Only' checkbox
#    Then I see LINK CASE button is displayed on the page
#    When I click the Link Case button
#    When I click on Save button
#    And I click on initiate a contact button
#    When I search consumer has first name as "Dfghdfthert"
#    And I link the contact to an existing Case or Consumer Profile
#    When I click on View All Alerts button
#    Then I verify start and end date when I hover over on calendar icon

  @CP-28677 @CP-28677-01 @chopa @ui-cc @crm-regression
  Scenario: Verify Hamburger menu is present for Alerts with type ACTIONABLE
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FnaaiEl" and Last Name as "LnqUbsK"
    And I link the contact to an existing Case or Consumer Profile
    When I click on View All Alerts button
    And I verify Hamburger menu is displayed for ACTIONABLE alerts

  @CP-28677 @CP-28677-02 @chopa @ui-cc @crm-regression
  Scenario: Verify Hamburger menu is not present for Alerts with type STATIC
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Kate" and Last Name as "Loll"
    And I link the contact to an existing Case or Consumer Profile
    When I click on View All Alerts button
    And I verify Hamburger menu is not displayed for STATIC alerts

  @CP-28677 @CP-28677-03 @chopa @ui-cc @crm-regression
  Scenario: Verify Inactivate option is displayed for ACTIONABLE alerts
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FnaaiEl" and Last Name as "LnqUbsK"
    And I link the contact to an existing Case or Consumer Profile
    When I click on View All Alerts button
    And I click on Hamburger menu
    Then I verify option Inactivate is displayed

  @CP-28677 @CP-28677-04 @chopa @ui-cc @crm-regression
  Scenario: Verify an Active Alert's border turns grey after clicking Inactivate
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FnaaiEl" and Last Name as "LnqUbsK"
    And I link the contact to an existing Case or Consumer Profile
    When I click on View All Alerts button
    And I click on Hamburger menu
    Then I click on "Inactivate" option for alert
    And I verify the the border of alert turns "grey"
    And I click on Hamburger menu
    Then I click on "Activate" option for alert
    And I verify the the border of alert turns "red"

  @CP-28677 @CP-28677-05 @chopa @ui-cc @crm-regression
  Scenario: Verify an Inactive Past Alert's border turns red after clicking Inactivate
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Anya" and Last Name as "Swift"
    And I link the contact to an existing Case or Consumer Profile
    When I click on View All Alerts button
    And I verify the the border of alert turns "grey"
    And I click on Hamburger menu
    Then I click on "Activate" option for alert
    And I verify the the border of alert turns "red"
    And I click on Hamburger menu
    Then I click on "Inactivate" option for alert

  @CP-28677 @CP-28677-06 @chopa @ui-cc @crm-regression
  Scenario: Verify an Inactive Future Alert's border turns red after clicking Activate
    Given I logged into CRM
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create a case for consumer using Case Loader API for demographic member for "Primary Individual"
    And I search consumer with first name and last name for demographic member
    And I link the contact to an existing Case or Consumer Profile
    And I click on Alert on Hamburder button
    And I successfully create an "Future" Alert for consumer
    When I click on Save button
    When I click on View All Alerts button
    And I verify the the border of alert turns "orange"
    And I click on Hamburger menu
    Then I click on "Activate" option for alert
    And I verify the the border of alert turns "red"

  @CP-31777 @CP-31777-01 @chopa @ui-cc @crm-regression
  Scenario: Verify option Edit is displayed in View All Alerts Slider for Actionable Alerts
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Chelsey" and Last Name as "Fuki"
    And I link the contact to an existing Case or Consumer Profile
    When I click on View All Alerts button
    And I click on Hamburger menu
    Then I verify option Edit is displayed

  @CP-31777 @CP-31777-02 @chopa @ui-cc @crm-regression
  Scenario: Verify option Edit is not displayed in View All Alerts Slider for Static Alerts
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Brown" and Last Name as "Kah"
    And I link the contact to an existing Case or Consumer Profile
    When I click on View All Alerts button
    Then I verify option Edit is not displayed

  @CP-31737 @CP-31737-01 @ui-cc @chopa @crm-regression
  Scenario: Verify View All Alerts Slider - Edit Alert Details
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Aldi" and Last Name as "Testing"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    When I click on View All Alerts button
    And I click on Hamburger menu
    Then I click on "Edit" option for alert
    Then I verify I am on Alert Details screen

  @CP-31737 @CP-31737-02 @ui-cc @chopa @crm-regression
  Scenario: Verify Recently Created Alerts - Edit Alert Details
    Given I logged into CRM
    When I navigate to Alert Upload screen
    Then I click on Recently Created tab
    And I click on first Alert ID
    Then I verify I am on Alert Details screen

  @CP-31737 @CP-31737-03 @ui-cc @chopa @crm-regression
  Scenario: Verify View All Alerts Slider - Inactivate existing alert
    Given I logged into CRM
    And I click on initiate a contact button
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button
    And I click on Alert on Hamburder button
    And I successfully create an "Active" Alert for consumer
    When I click on Save button
    When I click on View All Alerts button
    And I click on Hamburger menu
    And I click on inactivate Alert button on Alert Details Page
    And I verify the the border of alert turns "grey"

  @CP-33877 @CP-33877-01 @Beka @ui-cc @crm-regression
  Scenario:   Display Alert on View or Edit Contact - Case Alerts Present
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "HOOkX" and Last Name as "lnEBKbh"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    When I click on the Contact Info Tab
    Then I verify "View All Alerts" button is displayed on case summary page

  @CP-33877 @CP-33877-02 @Beka @ui-cc @crm-regression
  Scenario:  Display Alert on View or Edit Contact - Consumer Alerts Present
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "AlertBanerContac" and Last Name as "name"
    And I link the contact to an existing Case or Consumer Profile
    When I click on the Demographic Info Tab
    Then I verify "View All Alerts" button is displayed on case summary page

  @CP-31127 @CP-31127-01 @crm-regression @ui-cc @chopa
  Scenario: View Alert that was created using CHIP type Case ID
    Given I logged into CRM and click on initiate contact
    Then I search with  Search Case "CHIP" and Case ID "65927893"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify Alert Banner is displayed with text "alert for CHIP type" on Case Summary Page

  @CP-31127 @CP-31127-02 @crm-regression @ui-cc @chopa
  Scenario: View Alert that was created using Medicaid type Case ID
    Given I logged into CRM and click on initiate contact
    Then I search with  Search Case "Medicaid" and Case ID "Vj7ANiKEE"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify Alert Banner is displayed with text "alert for Medicaid type" on Case Summary Page

  @CP-31127 @CP-31127-03 @crm-regression @ui-cc @chopa
  Scenario: View Alert that was created using Internal type Case ID
    Given I logged into CRM and click on initiate contact
    Then I search with  Search Case "Internal" and Case ID "97450"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify Alert Banner is displayed with text "alert for Internal type" on Case Summary Page

#  @CP-42351 @CP-42351-01 @crm-regression @ui-cc-dc @chopa                             #muted, reason: alert can be stored at the CASE level but only be queried at the COSUMER level
#  Scenario: Verify newly Active Alert is created from Profile Info Page
#    Given I logged into CRM and select a project "DC-EB"
#    When I navigate to manual case and consumer search page
#    And I search for consumer by "StateID" with value "M1161"
#    When I click on the crm case id
#    And I click on Alert on Hamburder button
#    And I successfully create an "Active" Alert for consumer
#    And I will click on create link button on manual create alert page
#    When I searched customer have First Name as "Lilly" and Last Name as "Lee" in DCEB
#    And I expend the Case Consumer this contact relates to in search result
#    When I select 'Link to Case Only' checkbox
#    Then I see LINK CASE button is displayed on the page
#    When I click the Link Case button
#    When I click on Save button
#    Then I verify Alert Banner is displayed with text "Sample Alert for linked consumer" on Case Summary Page

  @CP-42351 @CP-42351-02 @crm-regression @ui-cc-dc @chopa
  Scenario: Verify Active Alert is displayed in all contact tabs
    Given I logged into CRM and select a project "DC-EB"
    When I navigate to manual case and consumer search page
    And I search for consumer by "StateID" with value "456789098765"
    When I click on the crm case id
    Then I verify Alert Banner is displayed with text "Sample Alert for linked consumer" on Case Summary Page
    When I click on the Demographic Info Tab
    Then I verify Alert Banner is displayed with text "Sample Alert for linked consumer" on Case Summary Page
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Sample Alert for linked consumer" on Case Summary Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Sample Alert for linked consumer" on Case Summary Page
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Sample Alert for linked consumer" on Case Summary Page
    When I click on "History" Tab on Contact Dashboard Page
    Then I verify Alert Banner is displayed with text "Sample Alert for linked consumer" on Case Summary Page