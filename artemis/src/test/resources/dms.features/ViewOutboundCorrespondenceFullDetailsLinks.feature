Feature: View Outbound Correspondence Full Detail Link

  @CP-3141 @CP-3141-01@ui-ecms2 @umid
  Scenario: Verify Outbound Correspondence link to case id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I click on the correspondence id of the Outbound Correspondence "Previously Created"
    Then I should see the Link to the case has the proper values for "PreviouslyCreated" Case

  @CP-3141 @CP-3141-02 @ui-ecms2 @umid
  Scenario: Verify Outbound Correspondence link to consumer id
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I click on the correspondence id of the Outbound Correspondence "Previously Created"
    Then I should see the Link to the consumer has the proper values for "PreviouslyCreated" Consumer


  @CP-3365 @CP-3365-01 @ui-ecms2 @kamil
  Scenario Outline: Verify link to case is created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    And I logged into CRM
    And I click case consumer search tab
    And I search for the "CaseId" with value "7347"
    And I search for the Outbound Correspondence "7347"
    And I navigate to the case and contact details tab
    Then I initiated get Api request with CaseId "7347" for Outbound Correspondence Link
    Then I run get Api request for Outbound Correspondence Link
    Then Verify response id for Outbound Correspondence Link
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    Examples:
      | eventName  | projectName |
      | LINK_EVENT | BLCRM       |


  @CP-3365 @CP-3365-02 @ui-ecms2 @kamil
  Scenario Outline: Verify link to consumer is created
    Given I have requested to Create Outbound Correspondence request with a "CaseId" of value "7347" and "ConsumerId" of value "17500"
    Given I logged into CRM and click on initiate contact
    And I search for the "ConsumerId" with value "17500"
    Then I link the contact to an existing Case
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiated get Api request with CaseId "7347" for Outbound Correspondence Link
    Then I run get Api request for Outbound Correspondence Link
    Then Verify response id for Outbound Correspondence Link
    And I initiated Subscribers POST API
    And I will provide the subscriber name as "DPBI" in body
    When I run the Subscribers POST API
    Then I will check the response has event Subscriber Mapping Id for "<eventName>"
    Examples:
      | eventName  | projectName |
      | LINK_EVENT | BLCRM       |


