Feature: Navigation through Active Contact Record Dashboard Tabs Part II

@CRM-1890 @CRM-1890-01 @sujoy @crm-regression @ui-core
Scenario: Verification navigate outside of the Active Contact initiates Active Contact Widget
Given I logged into CRM and click on initiate contact
When I navigate to "My Task" page
Then I verify Active Contact Widget is visible on screen
And I have navigated to active contact page clicking left panel Call icon
Then I verify Active Contact Widget is not visible on screen
And I navigate to Case Consumer Search Page
Then I verify Active Contact Widget is visible on screen
And I have navigated to active contact page clicking left panel Call icon
Then I verify Active Contact Widget is not visible on screen
And I navigate to Contact Record Search Page
Then I verify Active Contact Widget is visible on screen

@CRM-1890 @CRM-1890-02 @sujoy @crm-regression @ui-core
Scenario: Verify Active Contact Widget content
Given I logged into CRM and click on initiate contact
When I search for an existing contact by last name "Potter"
When I click on Search Button on Search Consumer Page
And I link the contact to an existing Case or Consumer Profile
And I have collected Case Consumer Name and ID from header
When I navigate to "My Task" page
Then I verify Active Contact Widget is visible on screen
And I verify Active Contact Widget Start Time is visible on screen
And I verify Active Contact Widget Timer is visible on screen
And I verify Active Contact Widget Case Consumer name is visible on screen
And I verify Active Contact Widget Case Consumer id is visible on screen

    #Commented this script because its already covered in @CRM-1890-02
  #@CRM-1890 @CRM-1890-03 @sujoy @crm-regression
Scenario: Verify Active Contact Widget content in minimize mode
Given I logged into CRM and click on initiate contact
When I search for an existing contact by last name "Test Last"
When I click on Search Button on Search Consumer Page
And I link the contact to an existing Case or Consumer Profile
And I have collected Case Consumer Name and ID from header
When I navigate to "My Task" page
And I can minimize Active Contact Widget
And I verify Active Contact Widget Start Time is visible on screen
And I verify Active Contact Widget Timer is visible on screen

@CRM-1890 @CRM-1890-04 @sujoy @crm-regression @ui-core
Scenario: Verification Navigating Back to Active Contact will not show the Active Contact Widget for Consumer
Given I logged into CRM and click on initiate contact
When I search for an existing contact by "FirstName" and value "Harry"
And I search for an existing contact by "LastName" and value "Grant"
And I click on Search Button on Search Consumer Page
And I link the contact to an existing Case or Consumer Profile
And I have collected Case Consumer Name and ID from header
Then I should see Linked Contact in the Header
When I navigate to "My Task" page
And I have navigated to active contact page clicking left panel Call icon
Then I verify Active Contact Widget is not visible on screen
And I should see the first name and Last name "Harry Grant"

@CRM-1890 @CRM-1890-05 @sujoy @crm-regression @ui-core
Scenario: Verification Navigating Back to Active Contact will not show the Active Contact Widget for Case
Given I logged into CRM and click on initiate contact
When  I select "THIRD PARTY" Contact Record Type
And I searched customer have First Name as "Emma" and Last Name as "Jones"
And I link the contact to an existing Case or Consumer Profile
And I click case consumer search tab
And I click active contact tab
And I have collected Case Consumer Name and ID from header
Then I should see Linked Contact in the Header
When I navigate to "My Task" page
And I have navigated to active contact page clicking left panel Call icon
Then I verify Active Contact Widget is not visible on screen
And I should see the first name and Last name "Emma Jones"

@CRM-1890 @CRM-1890-06 @sujoy @crm-regression @ui-core
Scenario Outline: Validation of contact details retained after Navigating Back to Active Contact from Contact Widget
Given I logged into CRM and click on initiate contact
When I enter contact details for inbound contact type with call queue "<callQueue>" contact channel type "<contactChannelType>" contact channel "<contactChannel>" program type "<programType>" and contact disposition "<contactDispostion>"
And I click case consumer search tab
When I navigate to "My Task" page
And I have navigated to active contact page clicking left panel Call icon
Then I verify inbound contact record values retained for call queue "<callQueue>" contact channel type "<contactChannelType>" contact channel "<contactChannel>" program type "<programType>" and contact disposition "<contactDispostion>"
Examples:
| contactType | callQueue   | contactChannelType | contactChannel | contactDispostion | programType |
| Inbound     | Eligibility | Phone              | 987-415-2630   | Complete          | Program A   |


@CRM-1890 @CRM-1890-07 @sujoy @crm-regression @ui-core
Scenario Outline: Validation of third party contact details are retained after Navigating Back to Active Contact from Contact Widget
Given I logged into CRM and click on initiate contact
When  I select "THIRD PARTY" Contact Record Type
And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
When I enter contact details for inbound contact type with call queue "<callQueue>" contact channel type "<contactChannelType>" contact channel "<contactChannel>" program type "<programType>" and contact disposition "<contactDispostion>"
And I click case consumer search tab
When I navigate to "My Task" page
And I have navigated to active contact page clicking left panel Call icon
Then I verify inbound contact record values retained for call queue "<callQueue>" contact channel type "<contactChannelType>" contact channel "<contactChannel>" program type "<programType>" and contact disposition "<contactDispostion>"
And I verify third party details are retained "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
Examples:
| contactType | callQueue   | contactChannelType | contactChannel | contactDispostion | programType | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
| Inbound     | Eligibility | Phone              | 987-415-2630   | Complete          | Program A   | John       | Smith     | ABC Group         | Media         | English            |

@CRM-2024 @CRM-2024-01 @muhabbat @crm-regression @ui-core
Scenario: Viewing Secondary Header Global Navigation Bar when navigating through application
Given I logged into the CRM Application
And I see Secondary Header Global Navigation bar
And I click on initiate contact record
And I see Secondary Header Global Navigation bar
When I search for an existing contact by first name "Ethan"
And I click on Search Button on Search Consumer Page
And I link the contact to an existing Case or Consumer Profile
And I should see Linked Contact in the Header
And I see Secondary Header Global Navigation bar
When I click on "Demographic Info" Tab on Contact Dashboard Page
And I see Secondary Header Global Navigation bar
And I click on "Case & Contact Details" Tab on Contact Dashboard Page
And I see Secondary Header Global Navigation bar
When I click on "Task & Service Request" Tab on Contact Dashboard Page
And I see Secondary Header Global Navigation bar
When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
And I see Secondary Header Global Navigation bar
When I navigate to active contact tab on dashboard
And I unlink previously selected Consumer from Active Contact Record
And I see Secondary Header Global Navigation bar

@CRM-2024 @CRM-2024-02 @muhabbat @crm-regression @ui-core
Scenario: Viewing Secondary Header Global Navigation Bar when navigating through application
Given I logged into the CRM Application
And I see Secondary Header Global Navigation bar
When I navigate to Case Consumer Search Page
And I see Secondary Header Global Navigation bar
When I navigate to Contact Record search
And I see Secondary Header Global Navigation bar
When I navigate to "My Task" page
And I see Secondary Header Global Navigation bar
And I verify Active Contact phone Icon is not displayed


@CRM-2024 @CRM-2024-03 @CRM-1499 @CRM-1499-02 @muhabbat @crm-regression @ui-core
Scenario: Viewing Warning Message when navigating away from Create Task Page
Given I logged into the CRM Application
When I click on Secondary Header Global Navigation bar
And I select a task to create
When I populate some new data on Create Task Page
And I initiate a contact from Secondary Header Global Navigation
Then I see Warning Message pop-up

@CRM-2024 @CRM-2024-04 @muhabbat @crm-regression @ui-core
Scenario: Viewing available options of Task Types from Create Task Page
Given I logged into the CRM Application
When I click on Secondary Header Global Navigation bar
Then I see all the tasks that can be selected

@CRM-2024 @CRM-2024-05 @muhabbat @crm-regression @ui-core
Scenario: Navigating to Create Task Page
Given I logged into the CRM Application
When I click on Secondary Header Global Navigation bar
And I select a task to create
Then I navigated to Create Task Page

@CP-11432 @CP-11432-01 @ui-cc-nj @nj-regression @mark
Scenario: Verify Demographic Info tab is not visible on case Profile
Given I logged into CRM and select a project "NJ-SBE"
When I navigate to Case Consumer Search Page
And I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click to expand on primary individual
And I click validate&link and contact details tab
Then I verify "Demographic Info" is not listed as a tab header


@CP-11477 @CP-11477-01 @ui-cc-nj @nj-regression @mark
Scenario: Verify Case Summary tabs displayed in Active Contact
Given I logged into CRM and select a project "NJ-SBE"
When I click on initiate a contact button
And I searched customer have First Name as "Queen" and Last Name as "Faith"
And I expand the first record of the search result
And I will authenticate the the customer
And I click validate and link button
Then I verify the following "tabs" are visible
  | ACTIVE CONTACT         |
  | CASE & CONTACT DETAILS |
  | TASK & SERVICE REQUEST |

  @CP-11477 @CP-11477-02 @ui-cc-nj @nj-regression @mark
Scenario: Verify Case Summary tabs displayed in Case/Consumer Search
Given I logged into CRM and select a project "NJ-SBE"
When I navigate to Case Consumer Search Page
And I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click to expand on primary individual
And I click validate&link and contact details tab
Then I verify the following "tabs" are visible
| CASE & CONTACT DETAILS | TASK & SERVICE REQUEST |

@CP-11477 @CP-11477-03 @ui-cc-nj @nj-regression @mark
Scenario: Verify Case Summary tabs displayed in Contact Record search
Given I logged into CRM and select a project "NJ-SBE"
When I navigate to Contact Record Search Page
And I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on first Contact Record ID on Contact Record
And I click on case id link under General
Then I verify the following "tabs" are visible
  | CASE & CONTACT DETAILS |
  | TASK & SERVICE REQUEST |

  @CP-11477 @CP-11477-04-01 @ui-cc-nj @nj-regression @mark
    # viewing Case/Consumer details from link component is not yet implemented for inbound/outbound correspondence
Scenario: Verify Case Summary tabs displayed in Links Component for Contact record
Given I logged into CRM and select a project "NJ-SBE"
When I navigate to Contact Record Search Page
And I searched customer have First Name as "Queen" and Last Name as "Faith"
And I click on first Contact Record ID on Contact Record
And I click the first Case ID under Links
Then I verify the following "tabs" are visible
  | CASE & CONTACT DETAILS |
  | TASK & SERVICE REQUEST |

#scenario retired
#@CP-11477 @CP-11477-04-02 @ui-cc-nj @nj-regression @mark
#    # viewing Case/Consumer details from link component is not yet implemented for inbound/outbound correspondence
#Scenario: Verify Case Summary tabs displayed in Links Component for Task
#Given I logged into CRM and select a project "NJ-SBE"
#When I click on initiate a contact button
#And I searched customer have First Name as "Queen" and Last Name as "Faith"
#And I expand the first record of the search result
#And I will authenticate the the customer
#And I click validate and link button
#When I Navigate to create task link
#And I select "Review Complaint" to create a Task
#And I click the first Case ID under Links
#Then I verify the following "tabs" are visible
#| CASE & CONTACT DETAILS | TASK & SERVICE REQUEST |