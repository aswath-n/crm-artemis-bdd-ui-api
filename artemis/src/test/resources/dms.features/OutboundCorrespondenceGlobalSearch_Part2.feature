@CP-2951
Feature: Outbound Correspondence Global Search part 2

  @CP-2951-01 @ui-ecms1 @Keerthi
  Scenario: Verify Correspondence Id text field label and numeric length
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "CORRESPONDENCE ID" field label and maxlength

  @CP-2951-02 @ui-ecms1 @Keerthi
  Scenario: Verify Notification Id text field label and numeric length
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "NOTIFICATION ID" field label and maxlength

  @CP-2951-03 @ui-ecms1 @Keerthi
  Scenario: Verify Case Id text field label and numeric length
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "CASE ID" field label and maxlength

  @CP-2951-04 @ui-ecms1 @Keerthi
  Scenario: Verify Regarding Consumer Id text field label and numeric length
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "REGARDING CONSUMER ID" field label and maxlength

  @CP-2951-05 @ui-ecms1 @Keerthi
  Scenario: Verify Recipient Consumer Id text field label and numeric length
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "RECIPIENT CONSUMER ID" field label and maxlength


  @CP-2951-06 @ui-ecms1 @Keerthi
  Scenario: Verify Request Date field label,date format and disable of request date
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "REQUEST DATE" field label and maxlength
    And I click on cancel button in Outbound search page
    And I validate Outbound Request date disabling feature


  @CP-2951-07 @ui-ecms1 @Keerthi
  Scenario: Verify Sent Date field label,date format and disable of sent date
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "SENT DATE" field label and maxlength
    And I click on cancel button in Outbound search page
    And I validate Outbound Sent date disabling feature

  @CP-2951-08 @ui-ecms1 @Keerthi
  Scenario: Verify Request Date field label dropdown values and default value
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And validate Outbound Request date default operator value is "="
    And I validate "REQUEST DATE" dropdown values
      | = |
      | > |
      | < |

  @CP-2951-09 @ui-ecms1 @Keerthi
  Scenario: Verify Sent Date field label dropdown values and default value
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And validate Outbound Sent date default operator value is "="
    And I validate "SENT DATE" dropdown values
      | = |
      | > |
      | < |

  @CP-2951-10 @ui-ecms1 @Keerthi
  Scenario: Verify search limited to records with pagination and warning message
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CHANNEL | Mail |
    Then I should see an Error Alert Message
      | Search results in excess, enter additional search criteria to limit search results |

  @CP-2951-11 @ui-ecms1 @Keerthi
  Scenario: Verify Insufficient Search Criteria Specified warning message
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "SENT DATE" field label and maxlength
    When I click on cancel button in Outbound search page
    When I click on the Outbound Correspondence global Search icon
    Then I should see an Error Alert Message
      | User must enter at least one search criteria field |

  @CP-2951-12 @ui-ecms1 @Keerthi
  Scenario: Verify No Results Found warning message
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CASEID | 9999999999 |
    Then I should see an Error Alert Message
      | No search results found |

  @CP-2951-13 @ui-ecms1 @Keerthi
  Scenario: Verify search limited to two hundred records
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CHANNEL1 | Mail  |
      | CHANNEL2 | Email |
      | CHANNEL3 | Text  |
      | CHANNEL4 | Fax   |
    Then I validate search limited to two hundred records


  @CP-2951-14.1 @ui-ecms1 @Keerthi
  Scenario: Verify CorrespondenceID equals the CorrespondenceID search value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    Then I should see that all the outbound correspondence search results have the following values
      | CORRESPONDENCEID | previouslyCreated |

  @CP-2951-14.2 @ui-ecms1 @Keerthi
  Scenario: Verify NotificationID equals the NotificationID search value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase         |
      | caseId                          | previouslyCreated |
      | firstName                       | test              |
      | lastName                        | test              |
      | channelType                     | Mail              |
      | streetAddress                   | test lane 1       |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78613             |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | NOTIFICATIONID | previouslyCreated |
    When I select the show details caret for an Outbound Correspondence
    Then I should see that all the outbound correspondence search results have the following values
      | NOTIFICATIONID | previouslyCreated |

  @CP-2951-14.3 @ui-ecms1 @Keerthi
  Scenario: Verify CASEID equals the CASEID search value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CASEID | previouslyCreated |
    Then I should see that all the outbound correspondence search results have the following values
      | CASEID | previouslyCreated |


  @CP-2951-14.4 @ui-ecms1 @Keerthi
  Scenario: Verify RegardingconsumerID equals the RegardingconsumerID search value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | REGARDINGCONSUMERID | previouslyCreated |
    Then I should see that all the outbound correspondence search results have the following values
      | REGARDINGCONSUMERID | previouslyCreated |

  @CP-2951-14.5 @CP-29810 @ui-ecms1 @Keerthi
  Scenario: Verify RequestDate equals the RequestDate search value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | REQUESTDATE | currentdate |
    Then I should see that all the outbound correspondence search results have the following values
      | REQUESTDATE | currentdate |

  @CP-2951-14.6 @ui-ecms1 @Keerthi
  Scenario: Verify SentDate equals the SentDate search value
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId     | Random |
      | ConsumerId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I update Outbound Notification Id "previouslyCreated" to status "Sent"
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | NOTIFICATIONID | previouslyCreated |
      | SENTDATE       | currentdate       |
    When I select the show details caret for an Outbound Correspondence
    Then I should see that all the outbound correspondence search results have the following values
      | SENTDATE | currentdate |
