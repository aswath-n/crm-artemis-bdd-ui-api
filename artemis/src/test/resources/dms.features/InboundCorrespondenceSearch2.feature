Feature: Inbound Correspondence Search UI Part 2


  @CP-2949 @ui-ecms1 @James @CP-2949-09
  Scenario: Verify I should see that the column names for the search results have the following values
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I search for an Inbound Document by the following values
      | CASEID | 68 |
    Then I should see that the column names for the search results have the following values
      | CID           |
      | SET ID        |
      | DATE RECEIVED |
      | TYPE          |
      | STATUS        |
      | CASE ID       |
      | CONSUMER ID   |
      | CHANNEL       |
      | FROM          |

  @CP-2949 @ui-ecms1 @James @CP-2949-10
  Scenario: Verify I should see labels in the Inbound Correspondence Search
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    Then I should see all the labels have following values
      | CORRESPONDENCE ID     |
      | CORRESPONDENCE SET ID |
      | TYPE                  |
      | STATUS                |
      | DATE RECEIVED         |
      | CHANNEL               |
      | FROM PHONE            |
      | FROM EMAIL ADDRESS    |
      | FROM                  |
      | ORIGIN                |
      | ORIGINAL CID          |
      | ORIGINAL SET ID       |
      | CASE ID               |
      | CONSUMER ID           |
    And I verify channel drop down value in the Inbound Correspondence Search
      | Mail  |
      | Email |
      | Fax   |
      | Web   |
    And I verify the status drop down value in the Inbound Correspondence Search
      | Received            |
      | Barcode             |
      | Failed to Send      |
      | Awaiting Indexing   |
      | Document Separation |
      | Transmitting        |
      | Complete            |
      | Rescan Requested    |
      | Rescanned           |

  @CP-2949 @ui-ecms1 @James @CP-2949-11
  Scenario: Verify I should see an Error Alert Message when I click on search button without criteria
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I click search button on Inbound Correspondence Search Page
    Then I should see an Error Alert Message
      | User must enter at least one search criteria field |

  @CP-2949 @ui-ecms1 @James @CP-2949-12
  Scenario: Verify I can search inbound Correspondence by Case ID and CONSUMER ID
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CASEID     | 68  |
      | CONSUMERID | 287 |
    Then I should see that all the inbound correspondence search results have the following values
      | CASEID     | 68  |
      | CONSUMERID | 287 |

  @CP-2949 @ui-ecms1 @James @CP-2949-13
  Scenario: Verify I can search inbound Correspondence by Case ID and phone number
    Given I logged into CRM
    And I click on the main navigation menu
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CASEID             | 68         |
      | ADDDEMOPHONENUMBER | 7034553110 |
    Then I should see that all the inbound correspondence search results have the following values
      | CASEID             | 68         |
      | ADDDEMOPHONENUMBER | 7034553110 |

  @CP-2949 @ui-ecms1 @James @CP-2949-14
  Scenario: Verify I can search inbound Correspondence by Case ID and email address
    Given I logged into CRM
    And I click on the main navigation menu
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CASEID    | 68                         |
      | FROMEMAIL | AUTOMATION@maersk2020.COM |
    Then I should see that all the inbound correspondence search results have the following values
      | CASEID    | 68                         |
      | FROMEMAIL | AUTOMATION@maersk2020.COM |

  @CP-2949 @ui-ecms1 @James @CP-2949-15
  Scenario: Verify I can search inbound Correspondence by Case ID and email address
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CASEID   | 68    |
      | FROMNAME | SANTA |
    Then I should see that all the inbound correspondence search results have the following values
      | CASEID   | 68    |
      | FROMNAME | SANTA |

  @CP-2949 @ui-ecms1 @James @CP-2949-16
  Scenario: Verify I can search inbound Correspondence by Case ID and correspondence set id
    Given I logged into CRM
    And I click on the main navigation menu
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CASEID | 68    |
      | CSETID | 22152 |
    Then I should see that all the inbound correspondence search results have the following values
      | CASEID | 68    |
      | CSETID | 22152 |

#  @CP-2949 @ui-ecms1 @James @CP-2949-17 no longer a valild scenario
  Scenario: Verify I can search inbound Correspondence by Case ID and original set id
    Given I logged into CRM
    And I click on the main navigation menu
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CASEID         | 68    |
      | ORIGINALCSETID | 22030 |
    Then I should see that all the inbound correspondence search results have the following values
      | CASEID         | 68    |
      | ORIGINALCSETID | 22030 |

  @CP-2949 @ui-ecms1 @James @CP-2949-18
  Scenario: Verify I can search inbound Correspondence by date received
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | DATERECEIVED | TODAY |
    Then I should see that all the inbound correspondence search results have the following values
      | DATERECEIVED | TODAY |


  @CP-2949 @ui-ecms1 @James @CP-2949-19
  Scenario: Verify I should see an Error Alert When there are over 500 records
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | BEFOREDATERECEIVED | BEFORETODAY |
    Then I should see an Error Alert Message
      | Search results in excess, enter additional search criteria to limit search results |

  @CP-2949 @ui-ecms1 @James @CP-2949-20
  Scenario: Verify I should see an Error when nothing is found
    Given I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | FROMNAME | NEVERUSETHISNAME |
    Then I should see an Error Alert Message
      | No search results found |
