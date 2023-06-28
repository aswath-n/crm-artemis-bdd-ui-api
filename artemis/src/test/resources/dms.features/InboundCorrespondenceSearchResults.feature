Feature: Inbound Correspondence Search Results

  @CP-13021 @ui-ecms1 @James @CP-13021-01
  Scenario: Verify I can search inbound Criteria is cleared
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I fill in the following search fields in Inbound Search Page
    |CID|CorrespondenceSetID|Type|Status|DateReceived|Channel|FromPhone|FromEmail|FromName|OriginalSetID|OriginalCorrespondenceID|CaseID|ConsumerID|
    When I click on Cancel button to clear out the the Inbound Search Criteria
    Then I should see all of the Inbound Search Criteria is cleared
    And the Inbound Search date received operator is "="

  @CP-13021 @ui-ecms1 @James @CP-13021-02
  Scenario: Verify I can search and clearing and searching again will show the correct results
    Given I logged into CRM
    And I click on the main navigation menu
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CASEID      | 68    |
    And I should see that all the inbound correspondence search results have the following values
      | CASEID | 68 |
    When I click on Cancel button to clear out the the Inbound Search Criteria
    And I search for an Inbound Document by the following values
      | ADDDEMOPHONENUMBER | 7034553110 |
    Then I should see that all the inbound correspondence search results have the following values
      | ADDDEMOPHONENUMBER | 7034553110 |
