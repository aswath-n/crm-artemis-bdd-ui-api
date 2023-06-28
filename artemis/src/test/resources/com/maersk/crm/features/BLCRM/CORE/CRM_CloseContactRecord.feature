Feature: Close Contact Record
# refactored TC

  @CRM-304 @CRM-304-01  @shilpa  @crm-smoke @crm-regression @ui-core
  Scenario:Validate the Contact Record is Closed by clicking on the close  button by providing all the required details
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
      | Re-Sent Notice         |
    And I Enter valid data
    And I click on the save button
    When I search for an existing contact by first name "Harry"
    When I search for an existing contact by last name "Potter"
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
#    And I click on the Contact Type "Inbound"
    And I click on the Inbound Call queue "Eligibility"
    And I click on the Contact Channel Type "Phone"
    Then I click on the contact dispotions "Complete"
    When I enter contact phone number "9632154874"
    And I select program type "Program A"
    When I click on the close button on the Header
    And I scroll the Page to the Bottom
    And I click on the Close button in the bottom
    Then I verify that dashboard page is displayed

  @CRM-304 @CRM-304-02 @shilpa @crm-regression @ui-core
  Scenario:Verify the timer is stopped when contact is ended
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
      | Re-Sent Notice         |
    And I Enter valid data
    And I click on the save button
    When I search for an existing contact by first name "Harry"
    When I search for an existing contact by last name "Potter"
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    And I click on the Contact Type "Inbound"
    And I click on the Inbound Call queue "Eligibility"
    And I click on the Contact Channel Type "Phone"
    Then I click on the contact dispotions "Complete"
    When I enter contact phone number "9632154874"
    And I select program type "Program A"
    When I click on the close button on the Header
    Then The Timer should be stopped

  #@CRM-653 @CRM-653-08 @vinuta
  #this TC is redundant due to the changed functionality
  #created a duplicate with the current flow , this can be deleted
  Scenario:Validate the Close button for Outbound contact by providing all the options
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
    And I Enter valid data
    And I click on the save button
    When I search for an existing contact by first name "Harry"
    When I search for an existing contact by last name "Potter"
    And I click on Search Button on Search Consumer Page
    And I click on the "Harry" record link button
    And I click on "Outbound" type of call option in "Contact Type" dropdown
    And I should see following dropdown options for "outcome of contact" field displayed
      | Did Not Reach/Left Voicemail |
    Then I should see following dropdown options for "contact disposition" field displayed
      | Outbound Incomplete |
    And I click on the Contact Channel Type "Phone"
  #  And I click on the Preffered lanaguage "English"
  #And I click on Consumer Authenticated
    And I scroll the Page to Reasons and comments
    When I click on the close button on the Header
    And I scroll the Page to the Bottom
    And I click on the Close button in the bottom
    Then I should see the Hamburger Menu Displayed


  @CRM-653 @CRM-653-08 @vinuta @crm-regression @ui-core
  Scenario:Validate the Close button for Outbound contact by providing all the options
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
      | Re-Sent Notice         |
    And I Enter valid data
    And I click on the save button
    When I search for an existing contact by first name "Harry"
    When I search for an existing contact by last name "Potter"
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case or Consumer Profile
    And I click on the Contact Type "Outbound"
    And I should see following dropdown options for "outcome of contact" field displayed
      | Did Not Reach/Left Voicemail |
    Then I should see following dropdown options for "contact disposition" field displayed
      | Outbound Incomplete |
    And I click on the Contact Channel Type "Phone"
    Then I click on the contact dispotions "Outbound Incomplete"
    When I enter contact phone number "9632154874"
    And I select program type "Program A"
    When I click on the close button on the Header
    And I click on the Close button in the bottom
    Then I verify that dashboard page is displayed

  @CRM-1088 @CRM-1088-01 @vinuta @crm-regression @ui-core
  Scenario: Validate phone number field when Contact Channel 'Phone' or 'SMS Text'
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
    And I Enter valid data
    And I click on the save button
    When I click on the close button on the Header
    And I scroll the Page to the Bottom
    When I click on the Contact Channel Type "Phone"
    Then I verify phone number field is displayed as mandatory
    When I click on the Contact Channel Type "SMS Text"
    Then I verify phone number field is displayed as mandatory
    Then I verify the format of phone number field

  @CRM-1088 @CRM-1088-02 @vinuta @crm-regression @ui-core
  Scenario: Validate email field when Contact Channel 'Webchat' or 'Email'
    Given I logged into CRM and click on initiate contact
    And I scroll the Page to the Bottom
    When I click on the Contact Channel Type "Email"
    Then I verify email field is displayed as mandatory
    When I click on the Contact Channel Type "Web Chat"
    Then I verify email field is displayed as mandatory
    Then I verify the format of email field