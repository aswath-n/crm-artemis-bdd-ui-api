Feature: Contact Record Search Part 1

  @CP-132 @CP-132-01 @asad @ui-core @crm-regression
  Scenario: Search Criteria for Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I am able to search using the following basic search parameters for Contact Record

  @CP-132 @CP-132-01.1 @asad @ui-core @crm-regression
  Scenario: Advanced Search Parameters for Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I select Advanced Search and able to search using the following additional parameters for Contact Record

#  @CP-132 @CP-132-01.2 @asad @ui-core @crm-regression not valid any more
  Scenario: Field Level Validation for Contact Record
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I validate the fields if I have entered a search parameter in the incorrect format for Contact Record

  @CP-132 @CP-132-02 @asad @ui-core @crm-regression
  Scenario: Search Found values for Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I enter search parameters and validate the results for Contact Record

  @CP-132 @CP-132-03.0 @asad @ui-core @crm-regression
  Scenario: No Blank Searches for Contact Record
    Given I logged into CRM
    When I click contact search tab for Contact Record
    And I have not entered at least one search parameter and click search
    Then I will be provided an Error Message "Please enter the search parameters" for Contact Record

  @CP-132 @CP-132-03.2 @asad @ui-core @crm-regression
  Scenario: No Search Results for Contact Record
    Given I logged into CRM
    When I click contact search tab for Contact Record
    And I enter search parameters that return null results for Contact Record
    Then I will receive the following message "No Records Available" for Contact Record

  @CP-132 @CP-132-04 @asad @ui-core @crm-regression
  Scenario: Exact Match Criteria for Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I enter search parameters and validate the results if the information I have entered is an exact match for Contact Record

  @CP-132 @CP-132-05 @asad @ui-core @crm-regression
  Scenario: Contains Match Criteria for Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I enter search parameters and validate search results if the information I have entered matches information for Contact Record

  @CP-132 @CP-132-06 @asad @ui-core @crm-regression
  Scenario: Search Condition validation for Contact Record
    Given I logged into CRM
    When I create 3 consumers for Contact Record
    When I click contact search tab for Contact Record
    Then I enter more than one search criteria and validate all matching results for Contact Record

  @CP-135 @CP-135-01.0 @asad @ui-core @crm-regression
  Scenario: View Search Results - First Glance for Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I search and will be able to view the results for Contact Record

  @CP-135 @CP-135-01.1 @asad @ui-core @crm-regression
  Scenario: View Search Results - Expanded View for Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    Then I search and expand to see specific Search Result for Contact Record

  @CP-135 @CP-135-01.2 @asad @ui-core @crm-regression
  Scenario: Initial Search Result Sort Order for Contact Record
    Given I logged into CRM
    When I create 3 consumers for Contact Record
    When I click contact search tab for Contact Record
    Then I search and will be able to view the Search Results sorted in descending order by Contact Record ID for Contact Record

  @CP-135 @CP-135-01.3 @asad @ui-core @crm-regression
  Scenario: Sort Search Results for Contact Record
    Given I logged into CRM
    When I create 3 consumers for Contact Record
    When I click contact search tab for Contact Record
    Then I search and will be able to sort each column of Search Results in ascending or descending order for Contact Record