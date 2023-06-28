Feature: Add Notes Component

  @API-CP-29784 @API-CP-29784-01 @API-CC-IN @API-CC @API-CRM-Regression @Beka
  Scenario: Save a note for a Case
    When I will get the Authentication token for "IN-EB" in "CRM"
    When I send request for case note API using following data
      | caseId     | 2504      |
      | consumerId | 4606      |
      | noteText   | test text |
      | createdBy  | 7450      |
      | updatedBy  | 7450      |
    Then I verify response contains following data
      | status     | success   |
      | caseId     | 2504      |
      | consumerId | 4606      |
      | createdBy  | 7450      |
      | updatedBy  | 7450      |
      | noteText   | test text |

  @API-CP-29784 @API-CP-29784-02 @API-CC-IN @API-CC @API-CRM-Regression @Beka
  Scenario: Save a note for a consumer no on a case
    When I will get the Authentication token for "IN-EB" in "CRM"
    When I send request for case note API using following data
      | caseId     |[blank]|
      | consumerId | 1581      |
      | noteText   | test text |
      | createdBy  | 7450      |
      | updatedBy  | 7450      |
    Then I verify response contains following data
      | status     | success   |
      | caseId     | null      |
      | consumerId | 1581      |
      | createdBy  | 7450      |
      | updatedBy  | 7450      |
      | noteText   | test text |

  @API-CP-29784 @API-CP-29784-03 @API-CC-IN @API-CC @API-CRM-Regression @Beka
  Scenario: Return the Note for Consumer level
    When I will get the Authentication token for "IN-EB" in "CRM"
    When I send request for case note API using following data
      | caseId     |[blank]|
      | consumerId | 1581      |
      | noteText   | test text |
      | createdBy  | 7450      |
      | updatedBy  | 7450      |
    When I send request for cases note API using following data to get notes
      | consumerId | 1581      |
    Then I should get response with all notes
      | status     | success   |
      | caseId     | null      |
      | consumerId | 1581      |
      | createdBy  | 7450      |
      | updatedBy  | 7450      |
      | noteText   | test text |

  @API-CP-29784 @API-CP-29784-04 @API-CC-IN @API-CC @API-CRM-Regression @Beka
  Scenario: Return the Note for Case level
    When I will get the Authentication token for "IN-EB" in "CRM"
    When I send a request to get note from case level
      | caseId | 2504      |
    Then I should get response with all notes
      | status     | success   |
      | caseId     | 2504     |
      | consumerId | 4606      |
      | createdBy  | 7450      |
      | updatedBy  | 7450      |
      | noteText   | test text |



