Feature: Navigation through Active Contact Record Dashboard Tabs Part I

  @CRM-230 @CRM-230-01 @muhabbat @crm-regression @ui-core
  Scenario: Verify Contact Record is present when moved to different Tabs
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by first name "Ethan"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I should see Linked Contact in the Header
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I click on "Active Contact" Tab on Contact Dashboard Page
    And I click on "Outbound" type of call option in "Contact Type" dropdown
    And I click on "Case & Contact Details" Tab on Contact Dashboard Page
    When I click on "Active Contact" Tab on Contact Dashboard Page
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I click on "Active Contact" Tab on Contact Dashboard Page
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    When I click on "Active Contact" Tab on Contact Dashboard Page

  @CRM-230 @CRM-230-02 @muhabbat @crm-regression @ui-core
  Scenario: Verify Contact Reason, Action, Additional Comments are present through all Tabs
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    And  I choose "Sent Program Materials" option for Contact Action field
#    And I click on the Additional Comments
    And I Enter "Valid Additional Comment Text" as additional Comments
    And I should be able to save the additional comments
    When I search for an existing contact by first name "Ethan"
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    Then I should see Linked Contact in the Header
    When I click on "Demographic Info" Tab on Contact Dashboard Page
    And I verify values for Reason Action and Additional Comments are present
    And I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I verify values for Reason Action and Additional Comments are present
    When I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I verify values for Reason Action and Additional Comments are present
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then  I verify values for Reason Action and Additional Comments are present

  @CRM-876 @CRM-876-01 @aswath @ui-core
  Scenario: Verify the slide bar menu options
    Given I logged into the CRM Application
    Then Verify Active, Case, Contact task management navigation is displayed on the left slide bar menu

  @CRM-876 @CRM-876-02 @aswath @ui-core
  Scenario: Validation of slider bar menu in all the four active contact tabs
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to active contact tab on dashboard
    Then Verify Active, Case, Contact task management navigation is displayed on the left slide bar menu
    And I navigate to Contact Info Page
    Then Verify Active, Case, Contact task management navigation is displayed on the left slide bar menu
    And I navigate to case details tab on dashboard
    Then Verify Active, Case, Contact task management navigation is displayed on the left slide bar menu
    #Functionality is under construction
    #And I navigate to Task & Service details tab
    #Then Verify Active, Case, Contact task management navigation is displayed on the left slide bar menu
    #And I navigate to Program & Benefit Info details tab
    #Then Verify Active, Case, Contact task management navigation is displayed on the left slide bar menu


  @CRM-1891 @CRM-1891-01 @shruti @crm-regression @ui-core
  Scenario: Verify phone icon is not displayed when contact record is not initiated
    Given I logged into CRM
    Then I verify active contact record link is not displayed
    When I initiate contact
    Then I verify active contact record link is displayed

  @CRM-1891 @CRM-1891-02 @shruti @crm-regression @ui-core
  Scenario Outline: Validation of contact details retained
    Given I logged into CRM and click on initiate contact
    When I enter contact details for inbound contact type with call queue "<callQueue>" contact channel type "<contactChannelType>" contact channel "<contactChannel>" program type "<programType>" and contact disposition "<contactDispostion>"
    And I click case consumer search tab
    And I click active contact tab
    Then I verify inbound contact record values retained for call queue "<callQueue>" contact channel type "<contactChannelType>" contact channel "<contactChannel>" program type "<programType>" and contact disposition "<contactDispostion>"
    Examples:
      | contactType | callQueue   | contactChannelType | contactChannel | contactDispostion | programType |
      | Inbound     | Eligibility | Phone              | 987-415-2630   | Complete          | Program A   |

  @CRM-1891 @CRM-1891-03 @shruti @crm-regression @ui-core
  Scenario Outline: Validation of third party contact details are retained
    Given I logged into CRM and click on initiate contact
    When  I select "THIRD PARTY" Contact Record Type
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I enter contact details for inbound contact type with call queue "<callQueue>" contact channel type "<contactChannelType>" contact channel "<contactChannel>" program type "<programType>" and contact disposition "<contactDispostion>"
    And I click case consumer search tab
    And I click active contact tab
    Then I verify inbound contact record values retained for call queue "<callQueue>" contact channel type "<contactChannelType>" contact channel "<contactChannel>" program type "<programType>" and contact disposition "<contactDispostion>"
    And I verify third party details are retained "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    Examples:
      | contactType | callQueue   | contactChannelType | contactChannel | contactDispostion | programType | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Inbound     | Eligibility | Phone              | 987-415-2630   | Complete          | Program A   | John       | Smith     | ABC Group         | Media         | English            |


  @CRM-1891 @CRM-1891-04 @shruti @crm-regression @ui-core
  Scenario Outline: Validation of Unidentified Contact contact details are retained
    Given I logged into CRM and click on initiate contact
    When  I select "UNIDENTIFIED CONTACT" Contact Record Type
    And I enter unidentified contact details caller type "" and preferred language "<Preferred Language>"
    And I click case consumer search tab
    And I click active contact tab
    Then I verify unidentified contact details are retained caller type "<callerType>" and preferred language "<Preferred Language>"
    Examples:
      | callerType | Preferred Language |
      | Anonymous  | English            |

  @CRM-1891 @CRM-1891-05 @shruti @crm-regression @ui-core
  Scenario: Verify Linked details are retained -third party
    Given I logged into CRM and click on initiate contact
    When  I select "THIRD PARTY" Contact Record Type
    And I searched customer have First Name as "Emma" and Last Name as "Jones"
    And I link the contact to an existing Case or Consumer Profile
    And I click case consumer search tab
    And I click active contact tab
    Then I verify linked information is retained

  @CRM-1891 @CRM-1891-06 @shruti @crm-regression @ui-core
  Scenario: Verify Linked details are retained -general
    Given I logged into CRM and click on initiate contact
    And I searched customer have First Name as "Emma" and Last Name as "Jones"
    And I link the contact to an existing Case or Consumer Profile
    And I click case consumer search tab
    And I click active contact tab
    Then I verify linked information is retained

  @CRM-1891 @CRM-1891-07 @shruti @crm-regression @ui-core
  Scenario: Verify Warning is displayed when Reason/Actions are not saved
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
      | Re-Sent Notice         |
    And I Enter valid data
    And I click case consumer search tab
    Then I verify warning is displayed with message "Your Contact Reasons, Actions, Comments and/or Additional Comments will not be saved."


  @CRM-1891 @CRM-1891-08 @shruti @crm-regression @ui-core
  Scenario: Verify Warning is displayed when Additional Comments are not saved
    Given I logged into CRM and click on initiate contact
#    And I click on the Additional Comments
    And I Enter Valid  additional Comments
    And I click case consumer search tab
    Then I verify warning is displayed with message "Your Contact Reasons, Actions, Comments and/or Additional Comments will not be saved."


  @CRM-1891 @CRM-1891-09 @shruti @crm-regression @ui-core
  Scenario: Verify Warning is not displayed when Additional Comments are saved
    Given I logged into CRM and click on initiate contact
#    And I click on the Additional Comments
    And I Enter Valid  additional Comments
    Then I should be able to save the additional comments
    And I click case consumer search tab
    Then I verify warning message is not displayed

  @CRM-1891 @CRM-1891-10 @shruti @crm-regression @ui-core
  Scenario: Verify Warning is not displayed when Reason/Actions are saved
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
      | Re-Sent Notice         |
    And I Enter valid data
    And I click on the save button
    When I click case consumer search tab
    Then I verify warning message is not displayed


