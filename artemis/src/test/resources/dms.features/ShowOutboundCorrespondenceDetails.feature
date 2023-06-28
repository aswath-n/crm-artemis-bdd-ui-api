Feature: Show Outbound Correspondence Details

  @CP-3161-01 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: Created On (MM/DD/YYYY HH:MM XM date/time the Correspondence request was initially created)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field      |
      | previouslyCreated | Created On |

  @CP-3161-02 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: verify Created By ob summary
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field      |
      | previouslyCreated | Created by |

  @CP-3161-03 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: Verify Status Reason on ob summary
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field         |
      | previouslyCreated | Status Reason |

  @CP-3161-04 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: Verify Recipient(s) on ob summary
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field     |
      | previouslyCreated | Recipient |

  @CP-3161-05 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: Notifications for the Correspondence - Channel
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field   |
      | previouslyCreated | Channel |

  @CP-3161-06 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: Notifications for the Correspondence - Language
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field    |
      | previouslyCreated | Language |

  @CP-3161-07 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: Notifications for the Correspondence - Destination
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field       |
      | previouslyCreated | Destination |

  @CP-3161-08 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: Notifications for the Correspondence - Status
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field  |
      | previouslyCreated | Status |

  @CP-3161-09 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: Notifications for the Correspondence - Status Date (MM/DD/YYYY)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field       |
      | previouslyCreated | Status Date |

  @CP-3161-10 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: Notifications for the Correspondence - NID (Notification ID)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field |
      | previouslyCreated | NID   |

  @CP-3161-11 @CP-3161 @ui-ecms1 @albert
  Scenario Outline: Notifications for the Correspondence - View Icon
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    When I navigate to the case and contact details tab
    When I select the show details caret for an Outbound Correspondence by "<CID>"
    Then I will see the "<Field>" , "<CID>" is present
    Examples:
      | CID               | Field     |
      | previouslyCreated | View Icon |

