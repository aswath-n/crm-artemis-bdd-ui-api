Feature: CRM-Contact Related Events

  @CP-4740  @CP-4740-01 @CP-11998 @ozgen @crm-core-events  #will fail due to CP-12243
  Scenario Outline:Validation of Consumer Authentication Save Event
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on Unlink Contact Button on Active Contact Page
    And I search for a consumer that I just created
    And I link the consumer to authenticate the consumer
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    And I will verify consumer authentication details
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
    |eventName                                |module         |     eventType     |projectName|
    |CONSUMER_AUTHENTICATION_SAVE_EVENT       |CONTACT_RECORD |    authentication ||


  @CP-4740  @CP-4740-02 @CP-11998 @ozgen @crm-core-events @events_smoke_level_two  #will fail due to cp-12329
  Scenario Outline:Validation Contact Record Save Event
    Given I logged into CRM and click on initiate contact
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    And I will verify contact record save event details
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      |eventName                                |module         |     eventType     |projectName|
      |CONTACT_RECORD_SAVE_EVENT                |CONTACT_RECORD |     contactrecord ||


  @CP-4740  @CP-4740-03 @CP-11998 @ozgen @crm-core-events @events_smoke_level_two   #will fail due to CP-11958, now it is passing null values for updatedBy and updatedOn
  Scenario Outline: Validation of Contact Record Reason Save Event API
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    Then I verify that payload contains contact record reason save event details
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
      Examples:
    |eventName                           |             module             |       eventType    |projectName|
    |CONTACT_RECORD_REASON_SAVE_EVENT    |            CONTACT_RECORD      |     contactreason  ||


  @CP-4740  @CP-4740-04 @CP-11998 @ozgen @crm-core-events @events_smoke_level_two  #done #will fail due to CP-12096, CP-12328
  Scenario Outline: Validation of Contact Record Reason Update Event API
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "Second saved reason and action"
    Then I click save button for reason and action
    Then I click the edit button to edit reason, action, and comment "Third updated reason and action"
    And I will take trace Id for Update Events and "<eventType>" "<eventName>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    Then I verify that payload contains contact record reason update event details
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
     |eventName                                  |module          |     eventType             |projectName|
     |CONTACT_RECORD_REASON_UPDATE_EVENT         |CONTACT_RECORD  |     contactreason         ||


  @CP-4740  @CP-4740-05 @CP-11998 @ozgen @crm-core-events @events_smoke_level_two
  Scenario Outline: Validation of Contact Record Comment Save Event API
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I Enter Valid  additional Comments
    And I click on save button for saving additional comment
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    Then I verify that payload contains contact record comment save event details
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
     |eventName                                 |    module       |      eventType            |projectName|
     |CONTACT_RECORD_COMMENT_SAVE_EVENT         |CONTACT_RECORD   |     comment               ||

  @CP-4740  @CP-4740-06 @CP-11998 @ozgen @crm-core-events @events_smoke_level_two  #fail due to CP-12096, CP-12246
  Scenario Outline: Validation of Contact Record Comment Update Event API
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I Enter Valid  additional Comments
    And I click on save button for saving additional comment
    And I edit the previously saved Additional comments
    And I will take trace id for contact record comment update event "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    Then I verify that payload contains contact record comment update event details
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                              | module             | eventType        |projectName|
      |CONTACT_RECORD_COMMENT_UPDATE_EVENT     | CONTACT_RECORD     | comment          ||

  @CP-4740  @CP-4740-07.1 @CP-11998 @ozgen @crm-core-events @events_smoke_level_two  #will fail due to cp-12258
  Scenario Outline: Validation of Contact Record Update Event API by Saving
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    And I select call campaign for outbound contact type
    When I select outcome of contact "Reached Successfully" for outbound
    And I end the current call
    And I wait for wrapup time
    And I choose contact disposition and click on save button to save contact record
    And I will take trace Id for Contact Record update Event "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    And I will verify common contact record update event details
    And I will verify contact record update event details for saving contact record
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                              | module               | eventType        |projectName|
      |CONTACT_RECORD_UPDATE_EVENT             | CONTACT_RECORD       | contactrecord    |BLCRM      |

  @CP-4740  @CP-4740-07.3 @CP-11998 @ozgen @crm-core-events
  Scenario Outline: Validation of Contact Record Update Event API for Saving Third Party Contact Record
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    And I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action for third party"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Inbound |
    And I click on the Contact Channel Type "Email"
    And I enter contact email as "emma@test.com"
    Then I select "<Program B>" as contact program type
    And I end the current call
    And I choose contact disposition and click on save button to save contact record
    And I will take trace Id for Contact Record update Event "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    And I will verify payload contains third party contact record update event details
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                              | module               | eventType        |projectName|  First Name  | Last Name | Organization Name | Consumer Type | Preferred Language |
      |  CONTACT_RECORD_UPDATE_EVENT           |CONTACT_RECORD        |contactrecord     ||  Emma        | Jackie    | Test Group        | Agency        | Russian            |


  @CP-4740  @CP-4740-07.2 @CP-11998 @ozgen @crm-core-events  # will fail due to CP-12041, need to refactor
  Scenario Outline: Validation of Contact Record Update Event API by Editing
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    And I select call campaign for outbound contact type
    When I select outcome of contact "Reached Successfully" for outbound
    And I close the current Contact Record and re-initiate a new Contact Record for Outbound
    And I search for a consumer that I just created
    And I link the consumer to authenticate the consumer
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I click on to latest contact id to see contact record details and edit contact record
    And I will take trace Id for Contact Record update Event for editing "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    And I will verify common contact record update event details
    And I will verify contact record update event details by updating
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                              | module               | eventType        |projectName|
      |CONTACT_RECORD_UPDATE_EVENT             | CONTACT_RECORD       | contactrecord    ||

  @CP-4740  @CP-4740-07.3 @CP-11998 @ozgen @crm-core-events
  Scenario Outline: Validation of Contact Record Update Event API for Saving Third Party Contact Record
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    And I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action for third party"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Inbound |
    And I click on the Contact Channel Type "Email"
    And I enter contact email as "emma@test.com"
    Then I select "Program B" as contact program type
    And I end the current call
    And I wait for wrapup time
    And I choose contact disposition and click on save button to save contact record
    And I will take trace Id for Contact Record update Event "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    And I will verify payload contains third party contact record update event details
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                              | module               | eventType        |projectName|  First Name  | Last Name | Organization Name | Consumer Type | Preferred Language |
      |  CONTACT_RECORD_UPDATE_EVENT           |CONTACT_RECORD        |contactrecord     ||  Emma        | Jackie    | Test Group        | Agency        | Russian            |


  @CP-4740 @CP-4740-08 @CP-11998 @ozgen @crm-core-events
  Scenario Outline:Validation of Consumer Authentication Save Event for Date time Fields
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on Unlink Contact Button on Active Contact Page
    And I search for a consumer that I just created
    And I link the consumer to authenticate the consumer
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will verify that payload contains save event date time fields
    Then Verify genericFields are removed

    Examples:
      |eventName                                |module         |     eventType     |projectName|
      |CONSUMER_AUTHENTICATION_SAVE_EVENT       |CONTACT_RECORD |    authentication ||


  @CP-4740  @CP-4740-09 @CP-11998 @ozgen @crm-core-events #will fail due to cp-12329
  Scenario Outline:Validation Contact Record Save Event for Date time Fields
    Given I logged into CRM and click on initiate contact
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will verify that payload contains save event date time fields
    Then Verify genericFields are removed

    Examples:
      |eventName                                |module         |     eventType     |projectName|
      |CONTACT_RECORD_SAVE_EVENT                |CONTACT_RECORD |     contactrecord ||


  @CP-4740  @CP-4740-10 @CP-11998 @ozgen @crm-core-events  #will fail due to cp-11958
  Scenario Outline: Validation of Contact Record Reason Save Event API for Date time Fields
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will verify that payload contains save event date time fields
    Then Verify genericFields are removed
    Examples:
      |eventName                           |             module             |       eventType    |projectName|
      |CONTACT_RECORD_REASON_SAVE_EVENT    |            CONTACT_RECORD      |     contactreason  ||


  @CP-4740  @CP-4740-11 @CP-11998 @ozgen @crm-core-events
  Scenario Outline: Validation of Contact Record Reason Update Event API for Date time Fields
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "Second saved reason and action"
    Then I click save button for reason and action
    Then I click the edit button to edit reason, action, and comment "Third updated reason and action"
    And I will take trace Id for Update Events and "<eventType>" "<eventName>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will verify that payload contains updated date time fields
    Then Verify genericFields are removed
    Examples:
      |eventName                                  |module          |     eventType             |projectName|
      |CONTACT_RECORD_REASON_UPDATE_EVENT         |CONTACT_RECORD  |     contactreason         ||


  @CP-4740  @CP-4740-12 @CP-11998 @ozgen @crm-core-events
  Scenario Outline: Validation of Contact Record Comment Save Event API for Date time Fields
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on Unlink Contact Button on Active Contact Page
    And I search for a consumer that I just created
    And I link the consumer to authenticate the consumer
    And I Enter Valid  additional Comments
    And I click on save button for saving additional comment
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will verify that payload contains save event date time fields
    Then Verify genericFields are removed
    Examples:
      |eventName                                 |    module       |      eventType            |
      |CONTACT_RECORD_COMMENT_SAVE_EVENT         |CONTACT_RECORD   |     comment               |


  @CP-4740  @CP-4740-13 @CP-11998 @ozgen @crm-core-events #fail due to CP-12246
  Scenario Outline: Validation of Contact Record Comment Update Event API for Date time Fields
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I click on Unlink Contact Button on Active Contact Page
    And I search for a consumer that I just created
    And I link the consumer to authenticate the consumer
    And I Enter Valid  additional Comments
    And I click on save button for saving additional comment
    And I edit the previously saved Additional comments
    And I will take trace id for contact record comment update event "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will verify that payload contains updated date time fields
    Then Verify genericFields are removed
    Examples:
      | eventName                              | module             | eventType        |projectName|
      |CONTACT_RECORD_COMMENT_UPDATE_EVENT     | CONTACT_RECORD     | comment          ||


  @CP-4740  @CP-4740-14 @CP-11998 @ozgen @crm-core-events
    #will fail due to CP-10109
  Scenario Outline: Validation of Contact Record Update Event API for Date time Fields
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    And I select call campaign for outbound contact type
    When I select outcome of contact "Reached Successfully" for outbound
    And I end the current call
    And I choose contact disposition and click on save button to save contact record
    And I will take trace Id for Contact Record update Event "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will verify that payload contains updated date time fields
    Then Verify genericFields are removed
    Examples:
      | eventName                              | module               | eventType        |projectName|
      |CONTACT_RECORD_UPDATE_EVENT             | CONTACT_RECORD       | contactrecord    ||


   @CP-339 @CP-339-01-01 @CP-11998 @ozgen @moldir @crm-core-events
   Scenario Outline: BLCRM - Capture WrapUp time for General Contact Record with Contact Record Update Event
     Given I logged into CRM and click on initiate contact
     When I add New Consumer to the record
     Then I choose contact action and contact reason in "<projectName>" project
     And I enter additional comments "First saved reason and action for BLCRM"
     Then I click save button for reason and action
     When  I should see following dropdown options for "contact type" field displayed
       | Outbound |
     And I click on the Contact Channel Type "Phone"
     And I enter contact phone number "3334445566"
     Then I select "Program A" as contact program type
     And I click on the contact dispotions "Transfer"
     And I end the current call
     And I wait for wrapup time
     And I click on save button to wrap up contact record
     When I will get the Authentication token for "<projectName>" in "CRM"
     And I will take trace Id for Contact Record update Event "<eventType>"
     When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
     And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
     And I verify that payload contains event details
     And I will verify that payload contains wrap up time and "<Contact Type>" fields
     And I will verify that payload contains updated date time fields
     Then Verify genericFields are removed
     And I initiate subscribers POST API
     Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
     Then I will check the response has event Subscriber Mapping ID for "<eventName>"
     And I initiate Subscribers Record GET API and run Subscribers Record GET API
     Then I will verify response has event Id and "<eventName>" and subscriberId

     Examples:
       | eventName                              | module               | eventType        | Contact Type |projectName|
       |CONTACT_RECORD_UPDATE_EVENT             | CONTACT_RECORD       | contactrecord    | General      | BLCRM     |

  @CP-339 @CP-339-01-02 @CP-11998 @ozgen @moldir @crm-core-events
  Scenario Outline: NJSBE - Capture WrapUp time for General Contact Record with Contact Record Update Event
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    And I searched customer have First Name as "Skyler" and Last Name as "Quinn"
    Then I link the contact to an existing CaseConsumer in "<projectName>"
    Then I choose contact action and contact reason in "<projectName>" project
    And I enter additional comments "First saved reason and action for NJ-SBE"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "NJ FamilyCare" as contact program type
    And I click on the contact dispotions "Transfer"
    And I end the current call
    And I wait for wrapup time
    And I click on save button to wrap up contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will take trace Id for Contact Record update Event "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I verify that payload contains event details
    And I will verify that payload contains wrap up time and "<Contact Type>" fields
    And I will verify that payload contains updated date time fields
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId

    Examples:
      | eventName                              | module               | eventType        | Contact Type |projectName|
      |CONTACT_RECORD_UPDATE_EVENT             | CONTACT_RECORD       | contactrecord    | General      |NJ-SBE      |

  @CP-339 @CP-339-01-03 @CP-11998 @ozgen @moldir @crm-core-events
  Scenario Outline: IN-EB - Capture WrapUp time for General Contact Record with Contact Record Update Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send API CALL for Create CaseConsumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing CaseConsumer in "<projectName>"
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "HCC" as contact program type
    Then I choose contact action and contact reason in "<projectName>" project
    And I fill Call Summary field with "INEB Call summary" and save
    Then I click save button for reason and action
    And I click on the contact dispotions "Transfer"
    And I end the current call
    And I wait for wrapup time
    And I click on save button to wrap up contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will take trace Id for Contact Record update Event "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I verify that payload contains event details
    And I will verify that payload contains wrap up time and "<Contact Type>" fields
    And I will verify that payload contains updated date time fields
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId

    Examples:
      | eventName                              | module               | eventType        | Contact Type |projectName|
      |CONTACT_RECORD_UPDATE_EVENT             | CONTACT_RECORD       | contactrecord    | General      |IN-EB      |

  @CP-339 @CP-339-01-04 @CP-11998 @ozgen @moldir @crm-core-events
  Scenario Outline: CoverVA - Capture WrapUp time for General Contact Record with Contact Record Update Event
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing CaseConsumer in "<projectName>"
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Medicaid" as contact program type
    Then I choose contact action and contact reason in "<projectName>" project
    Then I enter ApplicationId for CoverVA
    And I enter additional comments "First saved reason and action for C0verVA"
    And I click on the contact dispotions "Transfer"
    And I end the current call
    And I wait for wrapup time
    And I click on save button to wrap up contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will take trace Id for Contact Record update Event "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I verify that payload contains event details
    And I will verify that payload contains wrap up time and "<Contact Type>" fields
    And I will verify that payload contains updated date time fields
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId

    Examples:
      | eventName                              | module               | eventType        | Contact Type |projectName |
      |CONTACT_RECORD_UPDATE_EVENT             | CONTACT_RECORD       | contactrecord    | General      |CoverVA     |

  @CP-339 @CP-339-02 @CP-11998 @ozgen @moldir @crm-core-events
  Scenario Outline: Capture WrapUp time for Third Party Contact Record with Contact Record Update Event
    Given I logged into CRM and click on initiate contact
    Then I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for third party
    Then I choose contact action and contact reason in "<projectName>" project
    And I enter additional comments "First saved reason and action for 3rd party BLCRM"
    Then I click save button for reason and action
    When  I should see following dropdown options for "<contact Type>" field displayed
      | Outbound |
    And I capture current contact phone number
    And I select contact program type as "<Program>"
    And I click on the contact dispotions "Complete"
    And I end the current call
    And I wait for wrapup time
    And I click on save button to wrap up contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will take trace Id for Contact Record update Event "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I verify that payload contains event details
    And I will verify that payload contains wrap up time and "<Contact Type>" fields
    And I will verify that payload contains updated date time fields
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId

    Examples:
      | eventName                        | module               | eventType        |First Name | Last Name  | Organization Name | Consumer Type  | Preferred Language | Program    | Contact Type |projectName|
      |CONTACT_RECORD_UPDATE_EVENT       | CONTACT_RECORD       | contactrecord    | Ellie     | Smith      | DEF Group         | Agency         | Spanish            | Program C  |Third Party   |BLCRM      |

  @CP-339 @CP-339-03 @CP-11998 @ozgen @moldir @crm-core-events
  Scenario Outline: Capture WrapUp time for Unidentified Contact Record with Contact Record Update Event
    Given I logged into CRM and click on initiate contact
    When  I select "UNIDENTIFIED CONTACT" Contact Record Type
    And I enter unidentified contact details caller type "" and preferred language "<Preferred Language>"
    Then I choose contact action and contact reason in "<projectName>" project
    And I enter additional comments "First saved reason and action for Unidentified Contact"
    Then I click save button for reason and action
    And I enter contact phone number "3334445566"
    And I select contact program type as "Program A"
    And I end the current call
    And I wait for wrapup time
    And I click on save button to wrap up contact record
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will take trace Id for Contact Record update Event for Unidentified Record "<eventType>"
    When I will initiate Event GET API
     When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I verify that payload contains event details
    And I will verify that payload contains wrap up time and "<Contact Type>" fields
    And I will verify that payload contains updated date time fields
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId

    Examples:
      | eventName                              | module               | eventType        | Preferred Language |Contact Type          |projectName|
      |CONTACT_RECORD_UPDATE_EVENT             | CONTACT_RECORD       | contactrecord    | Russian            |Unidentified Contact  |BLCRM      |

  @CP-11998  @CP-11998-01 @kamil @crm-core-events @events_smoke_level_two #fail due CP-13687
  Scenario Outline: Verify CONTACT_RECORD_COMMENT_SAVE_EVENT generic fields are removed
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I Enter Valid  additional Comments
    And I click on save button for saving additional comment
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      |eventName                                 |    module       |      eventType            |projectName|
      |CONTACT_RECORD_COMMENT_SAVE_EVENT         |CONTACT_RECORD   |     comment               ||

  @CP-11998  @CP-11998-02 @kamil @crm-core-events @events_smoke_level_two #fail due CP-13687
  Scenario Outline:Verify CONTACT_RECORD_COMMENT_UPDATE_EVENT generic fields are removed
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I Enter Valid  additional Comments
    And I click on save button for saving additional comment
    And I edit the previously saved Additional comments
    And I will take trace id for contact record comment update event "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                              | module             | eventType        |projectName|
      |CONTACT_RECORD_COMMENT_UPDATE_EVENT     | CONTACT_RECORD     | comment          ||

  @CP-1453 @CP-1453-1-01 @aikanysh @crm-core-events
  Scenario Outline: Cancel Creation of Contact Record: Generate CONTACT_RECORD_UPDATE event
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lisa" and Last Name as "John"
    And I link the contact to an existing Case or Consumer Profile
    And I click cancel button and click continue on warning message
    And I will take trace Id for Events and "<eventType>" with index "5"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    And I will verify common contact record update event details
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId

    Examples:
      | eventName                        | module               | eventType|
      |CONTACT_RECORD_UPDATE_EVENT       | CONTACT_RECORD       | contactrecord|

  @CP-1453 @CP-1453-1-02 @CP-11700 @CP-11700-05 @aikanysh @crm-core-events
  Scenario Outline: Cancel Creation of Contact Record: Generate UNLINK_EVENT
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lisa" and Last Name as "John"
    And I link the contact to an existing Case
    And I click cancel button and click continue on warning message
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two unlink event from contact record and case payloads has correct data
    And I will get "UNLINKCRToConsumer" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    Then I verify two unlink event from contact record and consumer payloads has correct data
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    And I initiated Subscribers Record GET API
    And I run the Subscribers Record GET API
    Then I verify response has event id and "<eventName>" and subscriberId
    Examples:
      | eventName    | correlationId | projectName |
      | UNLINK_EVENT | UNLINKCRToCase||




  @CP-11699 @CP-11699-01 @crm-core-events @umid
  Scenario Outline: Trigger Contact Record Reason Update Event API
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "Second saved reason and action"
    Then I click save button for reason and action
    Then I click the edit button to edit reason, action, and comment "Third updated reason and action"
    And I will take trace Id for Update Events and "<eventType>" "<eventName>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    Then I verify that payload contains contact record reason update event details
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      |eventName                                  |module          |     eventType             |projectName|
      |CONTACT_RECORD_REASON_UPDATE_EVENT         |CONTACT_RECORD  |     contactreason         ||




  @CP-13484 @CP-13484-01 @crm-core-events @kamil
  Scenario Outline: Validation of Contact Record Reason Save will include the Effective Start and End Dates as a null
    Given I logged into CRM and click on initiate contact
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I save created reason and action
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    Then I verify payload contains "CONTACT_RECORD_REASON_SAVE_EVENT" details
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      |eventName                           |             module             |       eventType    |projectName|
      |CONTACT_RECORD_REASON_SAVE_EVENT    |            CONTACT_RECORD      |     contactreason  ||



  @CP-13484 @CP-13484-02 @crm-core-events @kamil
  Scenario Outline: Validation Contact Record Reason Update will include the Effective Start and End Dates
    Given I logged into CRM and click on initiate contact
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I save created reason and action
    Then I click the edit button to edit reason, action, and comment "Third updated reason and action"
    And I will take trace Id for Update Events and "<eventType>" "<eventName>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    Then I verify payload contains "CONTACT_RECORD_REASON_UPDATE_EVENT" details
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      |eventName                                  |module          |     eventType             |projectName|
      |CONTACT_RECORD_REASON_UPDATE_EVENT         |CONTACT_RECORD  |     contactreason         ||

  @CP-15547 @CP-15547-06 @crm-core-events @umid
  Scenario Outline: Contacts with Cancelled Disposition Included in Contact Search Results
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Lisa" and Last Name as "John"
    And I link the contact to an existing Case or Consumer Profile
    When  I should see following dropdown options for "contact reason" field displayed
      | Enrollment |
    And  I choose "New Enrollment" option for Contact Action field
    And I click on the save button
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When I enter contact phone number "1327894561"
    And I select contact program type as "Program A"
    Then Verify disposition field values are exist
      |Cancelled|
    When I click End Contact
    And I click save button Active contact
    And I will take trace Id for Events and "<eventType>" with index "7"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will save contact record id from CONTACT_RECORD_SAVE_EVENT
    And I will verify contact record status is "Cancelled"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by above ContactRecordId under Advanced Search
    And I verify contact disposition is edited to "Cancelled"
    Examples:
      | eventName                        | module               | eventType
      |CONTACT_RECORD_UPDATE_EVENT       | CONTACT_RECORD       | contactrecord


  @CP-2656 @CP-2656-01 @basha @events @crm-core-events
  Scenario Outline: Create Incomplete Contact Record Task without populating phone value
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    When I searched customer have First Name as "Lisa" and Last Name as "John"
    And I link the contact to an existing Case or Consumer Profile
    When I initiate logout button
    And I see Warning Message pop-up
    When I click on continue button on warning message
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will save contact record id from CONTACT_RECORD_SAVE_EVENT
    When I log back into CRM
    And I minimize Genesys popup if populates
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by above ContactRecordId under Advanced Search
    And I verify contact disposition is edited to "Incomplete"
    And Click on first Contact Record ID on Contact Record
    And I verify disposition value "Incomplete" in wrap up and close section
    Examples:
      | eventName                        | module               | eventType|
      |CONTACT_RECORD_SAVE_EVENT         | CONTACT_RECORD       | contactrecord|


  @CP-2656 @CP-2656-02 @basha @events @crm-core-events
  Scenario Outline: Create Incomplete Contact Record Task with populating phone value
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    When I searched customer have First Name as "Lisa" and Last Name as "John"
    And I link the contact to an existing Case or Consumer Profile
    And I enter contact phone number "<phone>"
    And I click on the contact dispotions "<disposition>"
    When I initiate logout button
    And I see Warning Message pop-up
    When I click on continue button on warning message
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will save contact record id from CONTACT_RECORD_SAVE_EVENT
    When I log back into CRM
    And I minimize Genesys popup if populates
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by above ContactRecordId under Advanced Search
    And I verify contact disposition is edited to "<disposition>"
    And Click on first Contact Record ID on Contact Record
    And I verify disposition value "<disposition>" in wrap up and close section
    And I verify phone value "<phone>" in contact details section
    And I verify Task Name "<taskName>" in Links section
    And I click Task Id in Links section
    And I verify data in Task Details section
      | taskType         |Incomplete Contact Record|
      | assignee         |Service AccountOne|
    Examples:
      | eventName                        | module               | eventType    |phone        |disposition|taskName                 |projectName|
      |CONTACT_RECORD_SAVE_EVENT         | CONTACT_RECORD       | contactrecord|9876543212   |Incomplete |Incomplete Contact Record||


  @CP-2656 @CP-2656-03 @basha @events @crm-core-events
  Scenario Outline: verify Incomplete Task NOT Created
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    When I initiate logout button
    And I see Warning Message pop-up
    When I click on continue button on warning message
    And I will take trace Id for Contact Record update Event after the logout "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    And I will save contact record id from CONTACT_RECORD_SAVE_EVENT
    And I will verify contact record status is "<disposition>"
    When I log back into CRM
    And I minimize Genesys popup if populates
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by above ContactRecordId under Advanced Search
    And I verify contact disposition is edited to "<disposition>"
    And Click on first Contact Record ID on Contact Record
    And I verify disposition value "<disposition>" in wrap up and close section
    And I verify Links section is empty
    Examples:
      | eventName                        | module               | eventType    |disposition|projectName|
      |CONTACT_RECORD_UPDATE_EVENT       | CONTACT_RECORD       | contactrecord|Incomplete ||

  @CP-20997 @CP-20997-02 @aikanysh  @crm-core-events
  Scenario Outline:Validate Application ID as part of CONTACT_RECORD_SAVE_EVENT for Cover-VA
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I verify that payload contains event details
    And I will verify contact record save event details for Cover VA has ApplicationID field
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      |eventName                                |module         |     eventType     |projectName|
      |CONTACT_RECORD_SAVE_EVENT                |CONTACT_RECORD |     contactrecord ||


  @CP-4896 @CP-4896-04 @CP-4896-05 @kamil @crm-core-events
  Scenario Outline: Verify CONTACT_RECORD_SAVE_EVENT generated and sent to DPBI with the contactTranslationService field
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | <contact type> |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact phone number "<Phone>"
    And I select contact program type as "<Program>"
    Then Verify disposition field values are exist
      | <disposition> |
    When I click End Contact
    And I click save button Active contact
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                 | module         | eventType     | Phone      | disposition | projectName | ContactChannelType | contact type | Program   |
      | CONTACT_RECORD_SAVE_EVENT | CONTACT_RECORD | contactrecord | 9876543212 | Complete    || Phone              | Inbound      | Program A |


  @CP-4896 @CP-4896-05 @CP-4896-06 @kamil @crm-core-events
  Scenario Outline: Verify CONTACT_RECORD_UPDATE_EVENT  generated and sent to DPBI with the contactTranslationService field
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone     | phone:: |
      | consumerEmail     | email:: |
      | contactType       | General |
      | consumerFirstName | name::  |
      | consumerLastName  | name::  |
      | organizationName  | name::  |
      | consumerType      | Media   |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then Wait for 6 seconds
    Then I will verify search with Phone number
    Then I clicking on first Contact Record
    Then Verify I am able to edit information captured in the Translation Service
    Then I verify Reason for Edit dropdown populated with values
      | Adding Contact Details |
    And I click on Save button in Edit screen
    Then I will take trace Id for Contact Update Events and "<eventType>"
    When I will initiate Event GET API
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                   | module         | eventType     |projectName|
      | CONTACT_RECORD_UPDATE_EVENT | CONTACT_RECORD | contactrecord ||

  @CP-18548 @CP-18548-01 @aikanysh @events
  Scenario Outline: Generate DPBI Event for Editing Unidentified Contact Record Disposition
    Given I logged into CRM
    When I create Unidentified Contact Record for Contact Record Edit
    And I click contact search tab for Contact Record Edit
    Then I edit disposition for above contact record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I will take trace Id for Event with exact match on "Correcting Disposition"
    When I will initiate Event GET API
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                   | module         | eventType     |projectName|
      | CONTACT_RECORD_UPDATE_EVENT | CONTACT_RECORD | contactrecord ||


  @CP-22329 @CP-22329-01 @CP-22329-04 @crm-core-events @kamil @events
  Scenario Outline: Verify Business Unit Field payload on Contact Record Save Event
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    Then I save the contact without Application ID with BusinessUnit Drop Down "<busninesdrpDwn>"
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                 | module         | eventType     | busninesdrpDwn |
      | CONTACT_RECORD_SAVE_EVENT | CONTACT_RECORD | contactrecord | CVCC           |
      | CONTACT_RECORD_SAVE_EVENT | CONTACT_RECORD | contactrecord | CVIU           |


  @CP-22329 @CP-22329-05 @crm-core-events @kamil @events
  Scenario Outline: Verify Business Unit Field payload on Contact Record Update Event
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I verify Case ID and Consumer Role column headers are not displayed for consumer
    Then I click on Call Managment button
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    When I updating text in the Application ID field on Edit Contact Record
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I will take trace Id for Event with exact match on "Correcting Contact Details"
    When I will initiate Event GET API
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      | eventName                   | module         |
      | CONTACT_RECORD_UPDATE_EVENT | CONTACT_RECORD |
