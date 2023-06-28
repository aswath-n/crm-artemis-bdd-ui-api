Feature: Creating Outbound Correspondence request

  @CP-3173 @CP-3173-01  @ui-ecms1 @RuslanL
  Scenario: Verify label, format and behavior on Outbound Correspondence request context of active consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have random valid data for an Outbound Correspondence Definition
    When I send the Outbound Correspondence Definition to the server with random valid data and required Keys
      | Case ID     |
      | Consumer ID |
    And I add channels to created Outbound Correspondence Definition
      | Mail  |
      | Email |
      | Text  |
      | Fax   |
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I verify label, format and behavior for
      | Language    |
      | Type        |
      | Regarding   |
      | Case ID     |
      | Consumer ID |
      | Send to     |
      | Recipients  |

  @CP-3173 @CP-3173-02  @ui-ecms1 @RuslanL
  Scenario Outline: Verify system will capture Created On and Created By context of active consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I capture title of the page
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I verify Outbound Correspondence Successfully saved
    And I return to the page from where I navigated to create OC
    Then I navigate to the case and contact details
    And I expand first outbound correspondence
    And I verify system captured Created On and Created By
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |


  @CP-3173 @CP-3173-03  @ui-ecms1 @RuslanL
  Scenario: Verify warning message on OC request context of active consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I capture title of the page
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    When I navigate away by clicking the back arrow on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Cancel" button on warning message OC request
    Then I navigated back to OC request page
    When I navigate away by clicking the back arrow on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Continue" button on warning message OC request
    And I return to the page from where I navigated to create OC

  @CP-3173 @CP-3173-04  @ui-ecms1 @RuslanL
  Scenario: Verify Cancel button functionality on OC request context of active consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I capture title of the page
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    When I click Cancel button on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Cancel" button on warning message OC request
    Then I navigated back to OC request page
    When I click Cancel button on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Continue" button on warning message OC request
    And I return to the page from where I navigated to create OC

  @CP-3173 @CP-3173-05  @ui-ecms1 @RuslanL
  Scenario: Verify label, format and behavior on Outbound Correspondence request context of consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have random valid data for an Outbound Correspondence Definition
    When I send the Outbound Correspondence Definition to the server with random valid data and required Keys
      | Case ID     |
      | Consumer ID |
    And I add channels to created Outbound Correspondence Definition
      | Mail  |
      | Email |
      | Text  |
      | Fax   |
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case ID and consumer search result
    And I create an Outbound Correspondence
    And I verify label, format and behavior for
      | Language    |
      | Type        |
      | Regarding   |
      | Case ID     |
      | Consumer ID |
      | Send to     |
      | Recipients  |

  @CP-3173 @CP-3173-06  @ui-ecms1 @RuslanL
  Scenario Outline: Verify system will capture Created On and Created By context of consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case ID and consumer search result
    And I capture title of the page
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I verify Outbound Correspondence Successfully saved
    And I return to the page from where I navigated to create OC
    Then I navigate to the case and contact details
    And I expand first outbound correspondence
    And I verify system captured Created On and Created By
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |


  @CP-3173 @CP-3173-07  @ui-ecms2 @RuslanL
  Scenario: Verify warning message on OC request context of consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case ID and consumer search result
    And I capture title of the page
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    When I navigate away by clicking the back arrow on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Cancel" button on warning message OC request
    Then I navigated back to OC request page
    When I navigate away by clicking the back arrow on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Continue" button on warning message OC request
    And I return to the page from where I navigated to create OC

  @CP-3173 @CP-3173-08  @ui-ecms2 @RuslanL
  Scenario: Verify Cancel button functionality on OC request context of consumer on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case ID and consumer search result
    And I capture title of the page
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    When I click Cancel button on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Cancel" button on warning message OC request
    Then I navigated back to OC request page
    When I click Cancel button on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Continue" button on warning message OC request
    And I return to the page from where I navigated to create OC

  @CP-28869 @CP-28869-01 @API-ECMS @RuslanL
  Scenario Outline: Verify the system does not allow invalid data in anchor for OB Correspondence API Requests
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | <MMSCode>             |
      | language                        | English               |
      | caseId                          | <caseId>              |
      | regardingConsumerId             | <regardingConsumerId> |
      | requesterId                     | 2425                  |
      | requesterType                   | ConnectionPoint       |
      | channelType                     | Mail                  |
      | address                         | random                |
    When I send the request for an Outbound Correspondence to the service
    Then I should see failed status and the reason for the failure in the response
    Examples:
      | MMSCode    | caseId            | regardingConsumerId |
      | CaseConReq | previouslyCreated | null                |
      | CaseConReq | null              | previouslyCreated   |
      | CaseReq    | null              | previouslyCreated   |
      | ConReq     | previouslyCreated | null                |
      | CaseReq    | previouslyCreated | previouslyCreated   |
      | ConReq     | previouslyCreated | previouslyCreated   |
      | NoReqKeys  | previouslyCreated | previouslyCreated   |
      | NoReqKeys  | null              | previouslyCreated   |
      | NoReqKeys  | previouslyCreated | null                |
      | CaseConReq | previouslyCreated | Random,Random       |
      | ConReq     | previouslyCreated | Random,Random       |
      | ConsReq    | previouslyCreated | Random,Random       |
      | CaseConReq | previouslyCreated | invalid             |
      | ConReq     | null              | invalid             |
      | CasConsReq | previouslyCreated | invalid;invalid     |
      | ConsReq    | null              | invalid;invalid     |


  @CP-28869 @CP-28869-02 @API-ECMS @RuslanL @ECMS-SMOKE
  Scenario Outline: Verify the system only allow required Data in anchor for OB Correspondence API Requests
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | <MMSCode>             |
      | language                        | English               |
      | caseId                          | <caseId>              |
      | regardingConsumerId             | <regardingConsumerId> |
      | requesterId                     | 2425                  |
      | requesterType                   | ConnectionPoint       |
      | channelType                     | Mail                  |
      | address                         | random                |
    When I send the request for an Outbound Correspondence to the service
    Then I should see a success status in the response
    Examples:
      | MMSCode  | caseId            | regardingConsumerId |
      | CCONLY   | previouslyCreated | previouslyCreated   |
      | CAONLY   | previouslyCreated | null                |
      | CONONLY  | null              | previouslyCreated   |
      | NOKEYS   | null              | null                |
      | CCSONLY  | previouslyCreated | Random,Random       |
      | CONSONLY | null              | Random,Random       |


  @CP-28869 @CP-28869-03 @ui-ecms2 @RuslanL
  Scenario Outline: Verify Regarding section only include the required keys that are configured for that type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    And I verify the following fields is displayed on create OB correspondence request page
      | caseID     | <caseIDIsDisplayed>     |
      | consumerID | <consumerIDIsDisplayed> |
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
    Examples:
      | correspondenceType     | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Case Consumer - CCONLY | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Case - CAONLY          | true              | false                 | 100 Main street |empty| Canaan | New York | 12029   |
      | Consumer - CONONLY     | false             | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | No Keys - NOKEYS       | false             | false                 | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-28869 @CP-28869-04 @ui-ecms2 @RuslanL
  Scenario Outline: Verify if correspondence type is defined to has only one Consumer ID, then the UI only allows for one Consumer ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    And I verify the following fields is displayed on create OB correspondence request page
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
    Examples:
      | correspondenceType     | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Case Consumer - CCONLY | true              | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | Consumer - CONONLY     | false             | true                  | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-28869 @CP-28869-05 @ui-ecms2 @RuslanL
  Scenario Outline: Verify case ID in request and response in context of case, when correspondence type is defined to has only Consumer ID or without required keys
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    And I verify the following fields is displayed on create OB correspondence request page
      | caseID     | <caseIDIsDisplayed>     |
      | consumerID | <consumerIDIsDisplayed> |
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
    Examples:
      | correspondenceType | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Consumer - CONONLY | false             | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | No Keys - NOKEYS   | false             | false                 | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-28869 @CP-28869-06 @ui-ecms2 @RuslanL
  Scenario Outline: Verify case ID in request and response in context of active contact on a case, when correspondence type is defined to has only Consumer ID or without required keys
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "<correspondenceType>" as a type
    And I verify the following fields is displayed on create OB correspondence request page
      | caseID     | <caseIDIsDisplayed>     |
      | consumerID | <consumerIDIsDisplayed> |
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
    Examples:
      | correspondenceType | caseIDIsDisplayed | consumerIDIsDisplayed | address1        | address2 | CITY   | State    | zipcode |
      | Consumer - CONONLY | false             | true                  | 100 Main street |empty| Canaan | New York | 12029   |
      | No Keys - NOKEYS   | false             | false                 | 100 Main street |empty| Canaan | New York | 12029   |
