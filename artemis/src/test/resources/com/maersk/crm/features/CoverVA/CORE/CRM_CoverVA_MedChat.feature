Feature: CoverVA: Med Chat


  @CP-2609 @CP-2609-01 @CP-17642 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: Verify View Chat Transcript Link to Contact Record
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated Med Chat Create Contact Records API
    And User send Api call with name "md" payload "medChatApiCreateContactRecord" for Med Chat
      | transcriptId       | c6dca9be-ca1b-c201-c36b-39fa19a69379 |
      | contactChannelType | Web Chat                             |
      | consumerEmail      | email::                              |
      | consumerPhone      | null::                               |
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Contact Record search
    Then I search for newly created Contact Record ID "c.contactRecordId"
    And I click on first Contact Record ID on Contact Record
    Then Verify Contact Details have email "md.consumerEmail" and link "VIEW CHAT TRANSCRIPT"

  @CP-2609 @CP-2609-02 @CP-17642 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @kamil
  Scenario: Verify Link Navigates to MedChat PDF
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated Med Chat Create Contact Records API
    And User send Api call with name "md1" payload "medChatApiCreateContactRecord" for Med Chat
      | transcriptId       | c6dca9be-ca1b-c201-c36b-39fa19a69379 |
      | contactChannelType | Web Chat                             |
      | consumerEmail      | email::                              |
      | consumerPhone      | null::                               |
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Contact Record search
    Then I search for newly created Contact Record ID "c.contactRecordId"
    And I click on first Contact Record ID on Contact Record
    Then Verify after clicking VIEW CHAT TRANSCRIPT button Link Navigates to MedChat PDF
