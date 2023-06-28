Feature: Authentication Functionality

  @CP-8928 @CP-8928-01 @aikanysh @ui-core @crm-regression
  Scenario: Validate Authentication Grid Fields for Baseline project are present and unchecked: consumer without case
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FirstAuthentic" and Last Name as "LastAuthentic"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the case is linked to contact
    And I verify all check boxes in authentication grid are present for consumer WITHOUT case and unchecked
    And I verify that phone number checkbox is not available for baseline authentication grid
    And I verify that "Unable to Authenticate" checkbox is available

  @CP-8928 @CP-8928-02 @aikanysh @ui-core @crm-regression
  Scenario: Validate Authentication is Successful: consumer without case
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FirstAuthentic" and Last Name as "LastAuthentic"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the unable to authenticate checkbox displayed
    Then I verify the case is linked to contact
    Then I will authenticate the the customer
    And I will verify that the "Consumer Authenticated" message will appear
    And I will verify that the The "Link" button will be enabled

  @CP-8928 @CP-8928-03 @aikanysh @ui-core @crm-regression
  Scenario: Validate consumer was authenticated which will include 1) selected person used for authentication and 2) selected boxes used for authentication.: consumer without case
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FirstAuthentic" and Last Name as "LastAuthentic"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the unable to authenticate checkbox displayed
    Then I verify the case is linked to contact
    Then I will authenticate the the customer
    And I will verify that the "Consumer Authenticated" message will appear
    And I will verify that the The "Link" button will be enabled
    Then I see Consumer Name, Type ID, SSN, DOB and Preferred Language systematically populated

  @CP-8928 @CP-8928-04 @aikanysh @ui-core @crm-regression
  Scenario: Validate Authentication Grid Fields for Baseline project are present and unchecked: consumer on case
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Emma" and Last Name as "Uncle"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the case is linked to contact
    And I verify all check boxes in authentication grid are present for consumer ON case and unchecked
    And I verify that phone number checkbox is not available for baseline authentication grid
    And I verify that "Unable to Authenticate" checkbox is available

  @CP-8928 @CP-8928-05 @aikanysh @ui-core @crm-regression
  Scenario: Validate Authentication is Successful: consumer on case
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Emma" and Last Name as "Uncle"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the unable to authenticate checkbox displayed
    Then I verify the case is linked to contact
    Then I will authenticate the the customer
    And I will verify that the "Consumer Authenticated" message will appear
    And I will verify that the The "Link" button will be enabled

  @CP-8928 @CP-8928-06 @aikanysh @ui-core @crm-regression
  Scenario: Validate consumer was authenticated which will include 1) selected person used for authentication and 2) selected boxes used for authentication.: consumer on case
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Emma" and Last Name as "Uncle"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the unable to authenticate checkbox displayed
    Then I verify the case is linked to contact
    Then I will authenticate the the customer
    And I will verify that the "Consumer Authenticated" message will appear
    And I will verify that the The "Link" button will be enabled
    Then I see Consumer Name, Type ID, SSN, DOB and Preferred Language systematically populated

  @CP-8928 @CP-8928-07 @aikanysh @ui-core @crm-regression
  Scenario: Validate consumer that consumer that was searched is the same consumer that was authenticated and linked
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "FirstAuthentic" and Last Name as "LastAuthentic"
    And I expend the Case Consumer this contact relates to in search result
    Then I verify the unable to authenticate checkbox displayed
    Then I verify auth grid consumer name "FirstAuthentic  LastAuthentic" is same as consumer name that was searched
    Then I verify the case is linked to contact
    Then I will authenticate the the customer
    And I will verify that the "Consumer Authenticated" message will appear
    And I will link the consumer
    And I verify that consumer name "FirstAuthentic  LastAuthentic" on top of active screen is the same consumer name that was in auth grid

  @CP-9483 @CP-9483-01 @CP-6042 @CP-6042-01 @moldir @ui-core @crm-regression #Fails due to CP-48272
  Scenario Outline: Validate CONSUMER_AUTHENTICATION_SAVE_EVENT is generated and sent to DPBI when Consumer is successfully authenticated in BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing CaseConsumer in "<projectName>"
    And Wait for 5 seconds
    When I navigate to the case and contact details tab
    And I will save contact record id from CASE & CONTACT DETAILS tab Contact History
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify CONSUMER_AUTHENTICATION_SAVE_EVENT response
    And Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                          | module         | eventType      | projectName |
      | CONSUMER_AUTHENTICATION_SAVE_EVENT | CONTACT_RECORD | authentication | BLCRM       |

  @CP-8889 @CP-8889-01 @paramita @crm-regression @ui-core
  Scenario: Verify Display or Conceal/Hidden Fields in the Authentication Grid in Contact record page
    Given I logged into CRM and click on initiate contact
    And I search for record by value "Emma"
    And I click first contact record in search results
    Then I see Display and Hidden fields in the Authentication Grid

  @CP-8889 @CP-8889-02 @paramita @crm-regression @ui-core
  Scenario Outline: Verify Display of 'Consumer Authenticated' button on selecting more than one Authentication Checkboxes
    Given I logged into CRM and click on initiate contact
    And I search for record by value "Emma"
    And I click first contact record in search results
    When I select first record radio button
    Then I verify display of 'Consumer Authenticated' button based on select "<number>" of checkboxes
    Examples:
      | number |
      | 1      |
      | 2      |
      | 3      |

  @CP-8889 @CP-8889-03 @paramita @crm-regression @ui-core
  Scenario: Verify 'Consumer Authenticated' button should not display less than 2 checkbox
    Given I logged into CRM and click on initiate contact
    And I search for record by value "Emma"
    And I click first contact record in search results
    When I select first record radio button
    Then I verify 'Consumer Authenticated' button should not display less than 2 checkbox
