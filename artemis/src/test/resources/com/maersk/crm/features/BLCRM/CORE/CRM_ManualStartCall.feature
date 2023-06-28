Feature: Validate the Start Call


  @CRM-223 @CRM-223-02 @crm-regression @shilpa @ui-core
  Scenario: Verify by clicking on a new Contact Record
    Given I logged into CRM and click on initiate contact
    Then I should see the Inbound Contact text should be present

  @CRM-223 @CRM-223-03 @crm-regression @shilpa @ui-core
  Scenario:Validate the Contact Record the User should be able to enter the Information
    Given I logged into CRM and click on initiate contact
    When I search for an existing contact by first name "Test First"
    When I search for an existing contact by last name "Test Last"
    And I click on Search Button on Search Consumer Page
    Then I see all search results according to the first name "Test First"

  @CRM-223 @CRM-223-04 @crm-regression @shilpa @ui-core
  Scenario:Validate the header time is displayed as per the local time
    Given I logged into CRM and click on initiate contact
    Then I verify Contact Start Time is captured

  @CRM-223 @CRM-223-05 @crm-regression @shilpa @ui-core
  Scenario: Verify by clicking on a new Contact Record
    Given I logged into CRM and click on initiate contact
    Then I should see the Phone text should be present

  @CRM-223 @CRM-223-06-01 @crm-regression @shilpa @ui-core
  Scenario:Verify the Contact Record should have userId
    Given I logged into CRM and click on initiate contact
    Then I should see the User ID should be displayed

  @CRM-223 @CRM-223-06-02 @crm-regression @shilpa @ui-core
  Scenario:Verify the User Name should be present in the Header
    Given I logged into CRM and click on initiate contact
    Then I should see the Username should be displayed "Scv_mars_user_1"



