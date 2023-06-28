Feature: Inbound Correspondence Search UI

  @CP-2949 @ui-ecms2 @ECMS-SMOKE @James @CP-2949-01
  Scenario: Verify I can search inbound Correspondence by cid
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    Then I should see that all the inbound correspondence search results have the following values
      | CID | fromRequest |

  @CP-2949 @ui-ecms2 @James @CP-2949-02
  Scenario: Verify I can search inbound Correspondence by Case ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that will receive an Inbound Correspondence
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | maersk Case + Consumer |
      | CaseID       | PreviouslyCreated       |
      | Channel      | MAIL                    |
      | Status       | COMPLETE                |
      | ProcessType  | INBOUND CORRESPONDENCE  |
      | ScannedOn    | 11/08/2021 12:00:00 AM  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CASEID | previouslyCreated |
    Then I should see that all the inbound correspondence search results have the following values
      | CASEID | previouslyCreated |

  @CP-2949 @ui-ecms2 @James @CP-2949-03
  Scenario: Verify I can search inbound Correspondence by From Phone
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType    | maersk Case + Consumer |
      | FromPhoneNumber | PreviouslyCreated       |
      | Channel         | MAIL                    |
      | Status          | COMPLETE                |
      | ProcessType     | INBOUND CORRESPONDENCE  |
      | ScannedOn       | 11/08/2021 12:00:00 AM  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I retrieve the Details for the "InboundDocument" Inbound Correspondence from api
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I search for an Inbound Document by the following values
      | ADDDEMOPHONENUMBER | previouslyCreated |
    Then I should see that all the inbound correspondence search results have the following values
      | ADDDEMOPHONENUMBER | previouslyCreated |

  @CP-2949 @ui-ecms2 @James @CP-2949-04
  Scenario: Verify I can search inbound Correspondence by From Email Address
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I search for an Inbound Document by the following values
      | FROMEMAIL | AUTOMATION@maersk2020.COM |
    Then I should see that all the inbound correspondence search results have the following values
      | FROMEMAIL | AUTOMATION@maersk2020.COM |

  @CP-2949 @ui-ecms2 @James @CP-2949-05
  Scenario: Verify I can search inbound Correspondence by From Name
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | FROMNAME | SANTA |
    Then I should see that all the inbound correspondence search results have the following values
      | FROMNAME | SANTA |

  @CP-2949 @ui-ecms2 @James @CP-2949-06
  Scenario: Verify I can search inbound Correspondence by Original Correspondence ID
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I search for an Inbound Document by the following values
      | CSETID | 22152 |
    Then I should see that all the inbound correspondence search results have the following values
      | CSETID | 22152 |

#  @CP-2949 @ui-ecms1 @James @CP-2949-07 scenario longer valid
  Scenario: Verify I can search inbound Correspondence by Original Correspondence SET ID
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I search for an Inbound Document by the following values
      | ORIGINALCSETID | 22030 |
    Then I should see that all the inbound correspondence search results have the following values
      | ORIGINALCSETID | 22030 |

  @CP-2949 @ui-ecms1 @James @CP-2949-08
  Scenario: Verify I can search inbound Correspondence by Consumer ID
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I search for an Inbound Document by the following values
      | CONSUMERID | 96012 |
    Then I should see that all the inbound correspondence search results have the following values
      | CONSUMERID | 96012 |


