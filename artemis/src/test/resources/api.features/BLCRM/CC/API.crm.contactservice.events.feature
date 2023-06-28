Feature: CRM-Contact Service Related Events

#  @CP-4832  @CP-4832-01 @ozgen @events @events-cc
#     # it is failing due to CP-8717, CP-8122 sometimes it is capturing first address information
#  Scenario Outline:Validation of Address Save Event
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    When I click on Add new address button on Contact Info Tab Page
#    Then I add new address for address events to verify details
#    And I will take trace Id for Address save Events and "<eventType>"
#    When I initiate Event POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    Then I verify that payload contains event details
#    And I verify that payload contains address details
#    And I initiate subscribers POST API
#    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
#    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
#    And I initiate Subscribers Record GET API and run Subscribers Record GET API
#    Then I will verify response has event Id and "<eventName>" and subscriberId
#    Examples:
#      | eventName          | module   | eventType | projectName |
#      | ADDRESS_SAVE_EVENT | CONTACTS | contact   |[blank]|
#
#  @CP-4832  @CP-4832-02 @ozgen  @events @events-cc
#  Scenario Outline:Validation of Address Update Event
#    # it is failing due to CP-8717
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    When I click on Add new address button on Contact Info Tab Page
#    When I add a new "active" "Physical" address to a consumer profile
#    Then I click on newly created address to navigate edit address page
#    Then I edit address information for address update event
#    Then I choose reason for inactivation and save edited address
#    And I will take trace Id for Address update Events and "<eventType>"
#    When I initiate Event POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    Then I verify that payload contains event details
#    And I verify that payload contains updated address details
#    And I initiate subscribers POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
#    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
#    And I initiate Subscribers Record GET API and run Subscribers Record GET API
#    Then I will verify response has event Id and "<eventName>" and subscriberId
#    Examples:
#      | eventName            | module   | eventType | projectName |
#      | ADDRESS_UPDATE_EVENT | CONTACTS | contact   |[blank]|
#
#
#  @CP-4832  @CP-4832-03 @ozgen  @events @events-cc
#  Scenario Outline:Validation of Phone Save Event
#    # it is failing due to CP-8717,CP-8773 comments has defect CP-8424
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    And I click on the Add Phone Number button on Contact Info Page
#    Then I add new phone number for phone events to verify details
#    And I will take trace Id for Phone save Events and "<eventType>"
#    When I initiate Event POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    Then I verify that payload contains event details
#    And I verify that payload contains phone number details
#    And I initiate subscribers POST API
#    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
#    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
#    And I initiate Subscribers Record GET API and run Subscribers Record GET API
#    Then I will verify response has event Id and "<eventName>" and subscriberId
#    Examples:
#      | eventName        | module   | eventType | projectName |
#      | PHONE_SAVE_EVENT | CONTACTS | contact   |[blank]|
#
#  @CP-4832  @CP-4832-04 @ozgen  @events @events-cc
#  Scenario Outline:Validation of Phone Update Event
#    # it is failing due to CP-8717, comments has defect CP-8424
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    And I click on the Add Phone Number button on Contact Info Page
#    Then I add new phone number for phone events to verify details
#    And I expend current phone number record and navigated to Edit Phone Number Page
#    Then I edit previously saved phone number for phone update events to verify details
#    And I will take trace Id for Phone update Events and "<eventType>"
#    When I initiate Event POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    Then I verify that payload contains event details
#    And I verify that payload contains updated phone number details
#    And I initiate subscribers POST API
#    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
#    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
#    And I initiate Subscribers Record GET API and run Subscribers Record GET API
#    Then I will verify response has event Id and "<eventName>" and subscriberId
#    Examples:
#      | eventName          | module   | eventType | projectName |
#      | PHONE_UPDATE_EVENT | CONTACTS | contact   |[blank]|
#
#
#  @CP-4832  @CP-4832-05 @ozgen  @events @events-cc
#  Scenario Outline:Validation of Email Save Event
#    #it is failing due to CP-8717
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    When I click on the Add button for Email Address
#    Then I add new email for email events to verify details and click on save
#    And I will take trace Id for Email save Events and "<eventType>"
#    When I initiate Event POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    Then I verify that payload contains event details
#    And I verify that payload contains email address details
#    And I initiate subscribers POST API
#    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
#    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
#    And I initiate Subscribers Record GET API and run Subscribers Record GET API
#    Then I will verify response has event Id and "<eventName>" and subscriberId
#    Examples:
#      | eventName        | module   | eventType | projectName |
#      | EMAIL_SAVE_EVENT | CONTACTS | contact   |[blank]|
#
#
#  @CP-4832  @CP-4832-06 @ozgen  @events @events-cc
#  Scenario Outline:Validation of Email Update Event
#    #it is failing due to CP-8717
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    When I click on the Add button for Email Address
#    Then I add new email for email events to verify details and click on save
#    Then I click latest email address to edit email details
#    Then I edit already created email for address update event and click on save
#    And I will take trace Id for Email update Events and "<eventType>"
#    When I initiate Event POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    Then I verify that payload contains event details
#    And I verify that payload contains updated email address details
#    And I initiate subscribers POST API
#    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
#    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
#    And I initiate Subscribers Record GET API and run Subscribers Record GET API
#    Then I will verify response has event Id and "<eventName>" and subscriberId
#    Examples:
#      | eventName          | module   | eventType | projectName |
#      | EMAIL_UPDATE_EVENT | CONTACTS | contact   |[blank]|
#
#  @CP-4832  @CP-4832-07 @ozgen @events @events-cc @events-cc
#  Scenario Outline:Validation of Address Save Event API for Date time Fields Format
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    When I click on Add new address button on Contact Info Tab Page
#    Then I add new address for address events to verify details
#    And I will take trace Id for Address save Events and "<eventType>"
#    When I initiate Event POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    And I will verify that payload contains date time fields
#
#    Examples:
#      | eventName          | module   | eventType | projectName |
#      | ADDRESS_SAVE_EVENT | CONTACTS | contact   |[blank]|
#
#  @CP-4832  @CP-4832-08 @ozgen @events @events-cc
#  Scenario Outline:Validation of Address Update Event API for Date time Fields Format
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    When I click on Add new address button on Contact Info Tab Page
#    When I add a new "active" "Physical" address to a consumer profile
#    Then I click on newly created address to navigate edit address page
#    Then I edit address information for address update event
#    Then I choose reason for inactivation and save edited address
#    And I will take trace Id for Address update Events and "<eventType>"
#    When I initiate Event POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    And I will verify that payload contains date time fields
#    And I will verify that payload contains updated date time fields
#    Examples:
#      | eventName            | module   | eventType | projectName |
#      | ADDRESS_UPDATE_EVENT | CONTACTS | contact   |[blank]|
#
#  @CP-4832  @CP-4832-09 @ozgen @events @events-cc @events-cc
#  Scenario Outline:Validation of Phone Save Event API for Date time Fields Format
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    And I click on the Add Phone Number button on Contact Info Page
#    Then I add new phone number for phone events to verify details
#    And I will take trace Id for Phone save Events and "<eventType>"
#    When I initiate Event POST API
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    And I will verify that payload contains date time fields
#    Examples:
#      | eventName        | module   | eventType | projectName |
#      | PHONE_SAVE_EVENT | CONTACTS | contact   |[blank]|
#
#  @CP-4832  @CP-4832-10 @ozgen  @events @events-cc
#  Scenario Outline:Validation of Phone Update Event API for Date time Fields Format
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    And I click on the Add Phone Number button on Contact Info Page
#    Then I add new phone number for phone events to verify details
#    And I expend current phone number record and navigated to Edit Phone Number Page
#    Then I edit previously saved phone number for phone update events to verify details
#    And I will take trace Id for Phone update Events and "<eventType>"
#    When I initiate Event POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    And I will verify that payload contains date time fields
#    And I will verify that payload contains updated date time fields
#    Examples:
#      | eventName          | module   | eventType | projectName |
#      | PHONE_UPDATE_EVENT | CONTACTS | contact   |[blank]|
#
#
#  @CP-4832  @CP-4832-11 @ozgen @events @events-cc
#  Scenario Outline:Validation of Email Save Event API for Date time Fields Format
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    When I click on the Add button for Email Address
#    Then I add new email for email events to verify details and click on save
#    And I will take trace Id for Email save Events and "<eventType>"
#    When I initiate Event POST API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    And I will verify that payload contains date time fields
#    And I will verify that payload contains effective start and end date time fields
#    Examples:
#      | eventName        | module   | eventType | projectName |
#      | EMAIL_SAVE_EVENT | CONTACTS | contact   |[blank]|
#
#
#  @CP-4832  @CP-4832-12 @ozgen  @events @events-cc
#  Scenario Outline:Validation of Email Update Event API for Date time Fields Format
#    Given I logged into CRM and click on initiate contact
#    When I add New Consumer to the record
#    And I navigate to Contact Info Page
#    When I click on the Add button for Email Address
#    Then I add new email for email events to verify details and click on save
#    Then I click latest email address to edit email details
#    Then I edit already created email for address update event and click on save
#    And I will take trace Id for Email update Events and "<eventType>"
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    When I initiate Event POST API
#    And I provide the "<eventName>" and "<module>" and "<correlation>" in the body
#    Then I will run the Event POST API and get the payload with "<eventName>" and "<module>" and "<correlation>"
#    And I will verify that payload contains date time fields
#    And I will verify that payload contains updated date time fields
#    Examples:
#      | eventName          | module   | eventType | projectName |
#      | EMAIL_UPDATE_EVENT | CONTACTS | contact   |[blank]|

  @CP-4832  @CP-4832-01 @beka @events @events-cc
  Scenario: Validation of consumer/address/phone/email Save Event
    Given I will get the Authentication token for "CoverVA" in "CRM"
    When I initiated Create Consumer API v1
    When Create new consumer using v1 API with following data
      | FirstName | random |
      | LastName  | random |
      | DoB       | random |
      | address   | yes    |
      | phone     | yes    |
      | email     | yes    |
    When I GET events by correlationID for new created consumer
    Then I will verify event published for following events
      | CONSUMER_SAVE_EVENT  |
      | NEW_CONSUMER_PROFILE |
      | ADDRESS_SAVE_EVENT   |
      | NEW_ADDRESS          |
      | PHONE_SAVE_EVENT     |
      | NEW_PHONE            |
      | EMAIL_SAVE_EVENT     |
    Then I will verify consumer save event payload data
    Then I will verify address save event payload data
    Then I will verify phone save event payload data
    Then I will verify email save event payload data

  @CP-4832  @CP-4832-02 @beka @events @events-cc
  Scenario: Validation of address update Event from UI
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Create Consumer API v1
    When Create new consumer using v1 API with following data
      | FirstName | random |
      | LastName  | random |
      | DoB       | random |
      | address   | yes    |
      | phone     | yes    |
      | email     | yes    |
    When I GET events by correlationID for new created consumer
    When I logged into CRM
    And I click on initiate a contact button
    When I search created consumer by internal ID
    And I link the contact to an existing Case
    And I click on the Demographic Info Tab
    When I click on newly created address to navigate edit address page
    When I will update "All address fields" and click on save button
    When I GET events by correlationID for new created consumer
    Then I will verify address update event payload contains updated "All address fields"

  @CP-4832  @CP-4832-03 @beka @events @events-cc
  Scenario: Validation of phone update Event from UI
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Create Consumer API v1
    When Create new consumer using v1 API with following data
      | FirstName | random |
      | LastName  | random |
      | DoB       | random |
      | address   | yes    |
      | phone     | yes    |
      | email     | yes    |
    When I GET events by correlationID for new created consumer
    When I logged into CRM
    And I click on initiate a contact button
    When I search created consumer by internal ID
    And I link the contact to an existing Case
    And I click on the Demographic Info Tab
    When I click on existing phone number
    When I update all fields for existing phone and click save
    When I GET events by correlationID for new created consumer
    Then I will verify phone update event payload contains updated fields

  @CP-4832  @CP-4832-04 @beka @events @events-cc
  Scenario: Validation of email update Event from UI
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Create Consumer API v1
    When Create new consumer using v1 API with following data
      | FirstName | random |
      | LastName  | random |
      | DoB       | random |
      | address   | yes    |
      | phone     | yes    |
      | email     | yes    |
    When I GET events by correlationID for new created consumer
    When I logged into CRM
    And I click on initiate a contact button
    When I search created consumer by internal ID
    And I link the contact to an existing Case
    And I click on the Demographic Info Tab
    When I click on existing email
    When I update all fields for existing email and click save
    When I GET events by correlationID for new created consumer
    Then I will verify email update event payload contains updated fields

