Feature: Outbound Correspondence is in Process of Being Created

  @CP-24670 @CP-24670-01  @ui-ecms2 @RuslanL
  Scenario Outline: Verify indicator appears when Outbound Correspondence is in Process of Being Created context of active consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I choose "No Keys - NOKEYS" as a correspondence type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
    #And I should see an indicator appear in the center of the screen
    And I verify Outbound Correspondence Success Message
    And I should not see an indicator anymore
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-24670 @CP-24670-02  @ui-ecms2 @RuslanL
  Scenario: Verify indicator appears when Outbound Correspondence is in Process of Being Created context of active consumer not on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I choose "Consumer - CONONLY" as a correspondence type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click existing address
    Then I click to save the Outbound Correspondence Request
   # And I should see an indicator appear in the center of the screen
     And I verify Outbound Correspondence Success Message
    And I should not see an indicator anymore

  @CP-24670 @CP-24670-03  @ui-ecms2 @RuslanL
  Scenario Outline: Verify indicator appears when Outbound Correspondence is in Process of Being Created context of consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I choose "No Keys - NOKEYS" as a correspondence type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
   # And I should see an indicator appear in the center of the screen
     And I verify Outbound Correspondence Success Message
    And I should not see an indicator anymore
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-24670 @CP-24670-04   @ui-ecms2 @RuslanL
  Scenario: Verify indicator appears when Outbound Correspondence is in Process of Being Created context of consumer not on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I choose "Consumer - CONONLY" as a correspondence type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click existing address
    Then I click to save the Outbound Correspondence Request
    And I should see an indicator appear in the center of the screen
    And I verify Outbound Correspondence Success Message
    And I should not see an indicator anymore

  @CP-24670 @CP-24670-05   @ui-ecms2 @RuslanL
  Scenario Outline: Verify indicator appears when Outbound Correspondence is in Process of Being Created context of Case-Related task
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "My Task" page
    And I will search for the task with status "In-Progress"
    And I click on the priority in dashboard
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I choose "No Keys - NOKEYS" as a correspondence type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
   # And I should see an indicator appear in the center of the screen
     And I verify Outbound Correspondence Success Message
    And I should not see an indicator anymore
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-24670 @CP-24670-06   @ui-ecms2 @RuslanL
  Scenario: Verify indicator appears when Outbound Correspondence is in Process of Being Created context of Consumer-Related task
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I navigate to "My Task" page
    And I will search for the task with status "In-Progress"
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I choose "No Keys - NOKEYS" as a correspondence type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click existing address
    Then I click to save the Outbound Correspondence Request
    #And I should see an indicator appear in the center of the screen
    And I verify Outbound Correspondence Success Message
    And I should not see an indicator anymore