@CP-1058
Feature: API: Phone and Email Events check Feature

  @CP-1058 @CP-1058-01.0 @asad @events @events_smoke_level_two @events-cc
  Scenario: verify PHONE_SAVE_EVENT from create consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I add new phone to the created consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "PHONE_SAVE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "PHONE_SAVE_EVENT" is as expected
##  I search the Get Events endpoint for "([^"]*)" business event name
##    And I will take correlation Id for CC "PHONE_SAVE_EVENT" for "phoneType" event check
##    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "PHONE_SAVE_EVENT" is created with fields in payload for "phone" event
#    Then I will verify subscriber received "PHONE_SAVE_EVENT" event for "DPBI" is created
#    Then I should see the payload for the DPBI "CONSUMER_UPDATE_EVENT" is as expected


  @CP-1058 @CP-1058-01.1 @asad @events @events-cc
  Scenario: verify PHONE_SAVE_EVENT from search consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I click on Case Consumer Search page
    And I search created consumer for event check
    And I add new phone to the searched consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "PHONE_SAVE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "PHONE_SAVE_EVENT" is as expected
#    And I will take correlation Id for CC "PHONE_SAVE_EVENT" for "phoneType" event check
#    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "PHONE_SAVE_EVENT" is created with fields in payload for "phone" event
#    Then I will verify subscriber received "PHONE_SAVE_EVENT" event for "DPBI" is created

  @CP-1058 @CP-1058-02.0 @asad @events @events_smoke_level_two @events-cc
  Scenario: verify PHONE_UPDATE_EVENT from create consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I update phone of the created consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "PHONE_SAVE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "PHONE_SAVE_EVENT" is as expected
#    And I will take correlation Id for "PHONE_UPDATE_EVENT" with "phoneType"
#    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "PHONE_UPDATE_EVENT" is created with fields in payload for "phone" event
#    Then I will verify subscriber received "PHONE_UPDATE_EVENT" event for "DPBI" is created

  @CP-1058 @CP-1058-02.1 @asad @events @events-cc
  Scenario: verify PHONE_UPDATE_EVENT from search consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I click on Case Consumer Search page
    And I search created consumer for event check
    And I update phone of the searched consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "PHONE_SAVE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "PHONE_SAVE_EVENT" is as expected
#    And I will take correlation Id for "PHONE_UPDATE_EVENT" with "phoneType"
#    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "PHONE_UPDATE_EVENT" is created with fields in payload for "phone" event
#    Then I will verify subscriber received "PHONE_UPDATE_EVENT" event for "DPBI" is created

  @CP-1058 @CP-1058-03.0 @asad @events @events_smoke_level_two @events-cc
  Scenario: verify EMAIL_SAVE_EVENT from create consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I add new email to the created consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "EMAIL_SAVE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "EMAIL_SAVE_EVENT" is as expected
#    And I will take correlation Id for CC "EMAIL_SAVE_EVENT" for "emailType" event check
#    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "EMAIL_SAVE_EVENT" is created with fields in payload for "email" event
#    Then I will verify subscriber received "EMAIL_SAVE_EVENT" event for "DPBI" is created

  @CP-1058 @CP-1058-03.1 @asad @events @events-cc @events-cc
  Scenario: verify EMAIL_SAVE_EVENT from search consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I click on Case Consumer Search page
    And I search created consumer for event check
    And I add new email to the searched consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "EMAIL_SAVE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "EMAIL_SAVE_EVENT" is as expected
#    And I will take correlation Id for CC "EMAIL_SAVE_EVENT" for "emailType" event check
#    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "EMAIL_SAVE_EVENT" is created with fields in payload for "email" event
#    Then I will verify subscriber received "EMAIL_SAVE_EVENT" event for "DPBI" is created

  @CP-1058 @CP-1058-04.0 @asad @events  @events_smoke_level_two @events-cc
  Scenario: verify EMAIL_UPDATE_EVENT from create consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I update email of the created consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "EMAIL_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "EMAIL_UPDATE_EVENT" is as expected
#    And I will take correlation Id for "EMAIL_UPDATE_EVENT" with "emailType"
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    Then I will verify CC "EMAIL_UPDATE_EVENT" is created with fields in payload for "email" event
#    Then I will verify subscriber received "EMAIL_UPDATE_EVENT" event for "DPBI" is created


  @CP-1058 @CP-1058-041 @asad @events @events-cc
  Scenario: verify EMAIL_UPDATE_EVENT from search consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I click on Case Consumer Search page
    And I search created consumer for event check
    And I update email of the searched consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "EMAIL_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "EMAIL_UPDATE_EVENT" is as expected
#    And I will take correlation Id for "EMAIL_UPDATE_EVENT" with "emailType"
#    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "EMAIL_UPDATE_EVENT" is created with fields in payload for "email" event
#    Then I will verify subscriber received "EMAIL_UPDATE_EVENT" event for "DPBI" is created

  @CP-1058 @CP-1058-05.0 @asad @events @events_smoke_level_two @events-cc
  Scenario: verify ADDRESS_SAVE_EVENT from create consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I add new address to the created consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "ADDRESS_SAVE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "ADDRESS_SAVE_EVENT" is as expected
#    And I will take correlation Id for CC "ADDRESS_SAVE_EVENT" for "addressType" event check
#    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "ADDRESS_SAVE_EVENT" is created with fields in payload for "address" event
#    Then I will verify subscriber received "ADDRESS_SAVE_EVENT" event for "DPBI" is created

  @CP-1058 @CP-1058-05.1 @asad @events @events-cc
  Scenario: verify ADDRESS_SAVE_EVENT from search consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I click on Case Consumer Search page
    And I search created consumer for event check
    And I add new address to the searched consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "ADDRESS_SAVE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "ADDRESS_SAVE_EVENT" is as expected
#    And I will take correlation Id for CC "ADDRESS_SAVE_EVENT" for "addressType" event check
#    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "ADDRESS_SAVE_EVENT" is created with fields in payload for "address" event
#    Then I will verify subscriber received "ADDRESS_SAVE_EVENT" event for "DPBI" is created

  @CP-1058 @CP-1058-06.0 @asad @events @events_smoke_level_two @events-cc
  Scenario: verify ADDRESS_UPDATE_EVENT from create consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I update address of the created consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "ADDRESS_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "ADDRESS_UPDATE_EVENT" is as expected
#    And I will take correlation Id for "ADDRESS_UPDATE_EVENT" with "addressType"
#    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "ADDRESS_UPDATE_EVENT" is created with fields in payload for "address" event
#    Then I will verify subscriber received "ADDRESS_UPDATE_EVENT" event for "DPBI" is created

  @CP-1058 @CP-1058-06.1 @asad @events @events-cc
  Scenario: verify ADDRESS_UPDATE_EVENT from search consumer
    Given I logged into CRM
    When I create a consumer for event check
    And I click on Case Consumer Search page
    And I search created consumer for event check
    And I update address of the searched consumer for event check
    When I will get the Authentication token for "" in "CRM"
    And I retrieve the "ADDRESS_UPDATE_EVENT" DPBI event that is produced by trace id
    Then I should see the payload for the DPBI "ADDRESS_UPDATE_EVENT" is as expected
#    And I will take correlation Id for "ADDRESS_UPDATE_EVENT" with "addressType"
#    When I will get the Authentication token for "" in "CRM"
#    Then I will verify CC "ADDRESS_UPDATE_EVENT" is created with fields in payload for "address" event
#    Then I will verify subscriber received "ADDRESS_UPDATE_EVENT" event for "DPBI" is created