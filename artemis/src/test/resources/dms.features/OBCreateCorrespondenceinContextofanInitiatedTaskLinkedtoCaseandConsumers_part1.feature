@CP-33215
Feature:Create OB Correspondence in Context of an Initiated Task Linked to Case and Consumers - part1

  @CP-33215 @CP-33215-01 @ui-ecms1 @Keerthi
  Scenario Outline: Creating OB Correspondence with different Required Keys combinations in Context of Case/Consumer(s) that is Linked to the Task for BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13241             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    Then I verify the Required fields values are displayed on create OB correspondence request page
      | caseID      | <caseIDIsDisplayed>     |
      | consumerIDs | <consumerIDIsDisplayed> |
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    When I click to save the Outbound Correspondence Request
    Then I validate request which was send from UI contains following values
      | caseID             | <caseIDIsDisplayed>     |
      | consumerID         | <consumerIDIsDisplayed> |
      | correspondenceType | <correspondenceType>    |
    And I retrieve the Outbound Correspondence which was created
    And I validate Outbound Correspondence Get request contains following values in anchor
      | caseID     | <caseIDIsDisplayed>     |
      | consumerID | <consumerIDIsDisplayed> |
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider
    Examples:
      | correspondenceType                         | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Case Consumer - CCONLY                     | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumers - CCSONLY                   | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumer Send Immediately - CCSENDIMM | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case - CAONLY                              | true              | false                 | 100 Main street |empty| Canaan | New York | 12029   |
      | Consumer - CONONLY                         | false             | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | No Keys - NOKEYS                           | false             | false                 | 100 Main street |empty| Canaan | New York | 12029   |


  @CP-33215 @CP-33215-02 @CP-25306-05  @CP-24718-03 @CP-24718-09 @ui-ecms1 @Keerthi
  Scenario Outline: Creating OB Correspondence with different Required Keys combinations in Context of a Active contact Case/Consumer(s) that is NOT Linked to the Task for BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case that wants to send an Outbound Correspondence
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13241             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    Then I verify the Required fields values are displayed on create OB correspondence request page
      | caseID      | <caseIDIsDisplayed>     |
      | consumerIDs | <consumerIDIsDisplayed> |
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    When I click to save the Outbound Correspondence Request
    Then I validate request which was send from UI contains following values
      | caseID             | <caseIDIsDisplayed>     |
      | consumerID         | <consumerIDIsDisplayed> |
      | correspondenceType | <correspondenceType>    |
    And I retrieve the Outbound Correspondence which was created
    And I validate Outbound Correspondence Get request contains following values in anchor
      | caseID     | <caseIDIsDisplayed>     |
      | consumerID | <consumerIDIsDisplayed> |
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider
    Examples:
      | correspondenceType                         | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Case Consumer - CCONLY                     | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumers - CCSONLY                   | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumer Send Immediately - CCSENDIMM | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case - CAONLY                              | true              | false                 | 100 Main street |empty| Canaan | New York | 12029   |
      | Consumer - CONONLY                         | false             | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | No Keys - NOKEYS                           | false             | false                 | 100 Main street |empty| Canaan | New York | 12029   |


    ############################################################    INEB      ######################################################################

  @CP-33215 @CP-33215-04 @ui-ecms-ineb  @Keerthi
  Scenario Outline: Creating OB Correspondence with different Required Keys combinations in Context of Case/Consumer(s) that is Linked to the Task for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    Then I verify the Required fields values are displayed on create OB correspondence request page
      | caseID      | <caseIDIsDisplayed>     |
      | consumerIDs | <consumerIDIsDisplayed> |
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    When I click to save the Outbound Correspondence Request
    Then I validate request which was send from UI contains following values
      | caseID             | <caseIDIsDisplayed>     |
      | consumerID         | <consumerIDIsDisplayed> |
      | correspondenceType | <correspondenceType>    |
    And I retrieve the Outbound Correspondence which was created
    And I validate Outbound Correspondence Get request contains following values in anchor
      | caseID     | <caseIDIsDisplayed>     |
      | consumerID | <consumerIDIsDisplayed> |
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I select random value from Action Taken drop down from task details
    And I click on save in Task Slider
    Examples:
      | correspondenceType                         | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Case Consumer - CCONLY                     | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumers - CCSONLY                   | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumer Send Immediately - CCSENDIMM | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case - CAONLY                              | true              | false                 | 100 Main street |empty| Canaan | New York | 12029   |
      | Consumer - CONONLY                         | false             | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | No Keys - NOKEYS                           | false             | false                 | 100 Main street |empty| Canaan | New York | 12029   |



  @CP-33215 @CP-33215-05 @ui-ecms-ineb  @Keerthi
  Scenario Outline: Creating OB Correspondence with different Required Keys combinations in Context of a Active contact Case/Consumer(s) that is NOT Linked to the Task for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id for IN-EB
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    Then I verify the Required fields values are displayed on create OB correspondence request page
      | caseID      | <caseIDIsDisplayed>     |
      | consumerIDs | <consumerIDIsDisplayed> |
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    When I click to save the Outbound Correspondence Request
    Then I validate request which was send from UI contains following values
      | caseID             | <caseIDIsDisplayed>     |
      | consumerID         | <consumerIDIsDisplayed> |
      | correspondenceType | <correspondenceType>    |
    And I retrieve the Outbound Correspondence which was created
    And I validate Outbound Correspondence Get request contains following values in anchor
      | caseID     | <caseIDIsDisplayed>     |
      | consumerID | <consumerIDIsDisplayed> |
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I select random value from Action Taken drop down from task details
    And I click on save in Task Slider
    Examples:
      | correspondenceType                         | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Case Consumer - CCONLY                     | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumers - CCSONLY                   | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumer Send Immediately - CCSENDIMM | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case - CAONLY                              | true              | false                 | 100 Main street |empty| Canaan | New York | 12029   |
      | Consumer - CONONLY                         | false             | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | No Keys - NOKEYS                           | false             | false                 | 100 Main street |empty| Canaan | New York | 12029   |



  @CP-33215 @CP-33215-06 @ui-ecms-ineb  @Keerthi
  Scenario Outline: Creating OB Correspondence with different Required Keys combinations in Context of a Different Case/Consumer(s) that is NOT Linked to the Task for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then I send the request for the External task endpoint
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I have a consumer not on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    Then I verify the Required fields values are displayed on create OB correspondence request page
      | caseID      | <caseIDIsDisplayed>     |
      | consumerIDs | <consumerIDIsDisplayed> |
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    When I click to save the Outbound Correspondence Request
    Then I validate request which was send from UI contains following values
      | caseID             | <caseIDIsDisplayed>     |
      | consumerID         | <consumerIDIsDisplayed> |
      | correspondenceType | <correspondenceType>    |
    And I retrieve the Outbound Correspondence which was created
    And I validate Outbound Correspondence Get request contains following values in anchor
      | caseID     | <caseIDIsDisplayed>     |
      | consumerID | <consumerIDIsDisplayed> |
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I select random value from Action Taken drop down from task details
    And I click on save in Task Slider
    Examples:
      | correspondenceType                         | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Case Consumer - CCONLY                     | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumers - CCSONLY                   | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case Consumer Send Immediately - CCSENDIMM | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case - CAONLY                              | true              | false                 | 100 Main street |empty| Canaan | New York | 12029   |
      | Consumer - CONONLY                         | false             | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | No Keys - NOKEYS                           | false             | false                 | 100 Main street |empty| Canaan | New York | 12029   |

