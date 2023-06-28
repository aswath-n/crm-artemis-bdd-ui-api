Feature: Inbound Correspondence Search UI (NJ)

  @CP-NJ2949 @ui-ecms-nj @James @CP-NJ2949-01
  Scenario: Verify I can search inbound Correspondence by cid
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    Then I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    Then I should see that all the inbound correspondence search results have the following values
      | CID | fromRequest |


  @CP-NJ2949 @ui-ecms-nj @James @CP-NJ2949-02
  Scenario: Verify I should see that the column names for the search results have the following values
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    Then I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    Then I should see that the column names for the search results have the following values
      | CID | SET ID | DATE RECEIVED | TYPE | STATUS | CASE ID | CONSUMER ID | CHANNEL | FROM |

  @CP-NJ2949 @ui-ecms-nj @James @CP-NJ2949-03
  Scenario: Verify I should see an Error Alert Message when I click on search button without criteria
    Given I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I click search button on Inbound Correspondence Search Page
    Then I should see an Error Alert Message
      | User must enter at least one search criteria field |


  @CP-NJ2949 @ui-ecms-nj @James @CP-NJ2949-04
  Scenario: Verify I can search inbound Correspondence by date received
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    Then I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | DATERECEIVED | TODAY |
    Then I should see that all the inbound correspondence search results have the following values
      | DATERECEIVED | TODAY |


  @CP-NJ2949 @ui-ecms-nj @James @CP-NJ2949-05
  Scenario: Verify I should see an Error Alert When there are over 500 records
    Given I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | BEFOREDATERECEIVED | BEFORETODAY |
    Then I should see an Error Alert Message
      | Search results in excess, enter additional search criteria to limit search results |

  @CP-NJ2949 @ui-ecms-nj @James @CP-NJ2949-06
  Scenario: Verify I should see an Error when nothing is found
    Given I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | FROMNAME | NEVERUSETHISNAME |
    Then I should see an Error Alert Message
      | No search results found |

  @CP-NJ13273 @CP-NJ13273-01 @ui-ecms-nj @Sang
  Scenario Outline: Verify the created BLCRM document type is not retrieved when obtaining documents in NJ Search with CID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "<Document Types>"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    Then I should see an Error Alert Message
      | Invalid Document Id |

    Examples:
      | projectName | Document Types  |
      | projectName | maersk Unknown |


  @CP-NJ13273 @CP-NJ13273-02 @ui-ecms-nj @Sang
  Scenario Outline: Verify the created Blcrm document type is not retrieved when obtaining documents in NJ Search with date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "<Document Types>"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | DATERECEIVED | TODAY |
    And I get a list of the Inbound Correspondence Search CIDs
    Then I should not see the "created" CId in the result list
    Examples:
      | projectName | Document Types          |
      | projectName | maersk Case + Consumer |


  @CP-NJ13273 @CP-NJ13273-03 @ui-ecms-nj @Sang
  Scenario Outline: Verify the created BLCRM document type is not retrieved when searching for Inbound in the NJ Contact and Details tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document Type of "<Document Types>" linked to a following case number "611"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on initiate contact record
    And I searched customer have First Name as "David" and Last Name as "Leis"
    And I link the contact to an existing Case
    When I navigate to the case and contact details tab
    And I get a list of the Inbound Correspondence Search CIDs in the Case Contact Details Tab
    Then I should not see the "created" CId in the Contact and Details Tab Inbound CId list

    Examples:
      | projectName | Document Types      |
      | projectName | maersk Application |

  @CP-NJ29148 @CP-NJ29148-01 @ui-ecms-nj @RuslanL
    Scenario: Verify Case and Consumer option not available in the Create Links(s) dropdown
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    And I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | DATERECEIVED | TODAY |
    And I click on a CID of "first available" Inbound Search Results
    Then I verify Case and Consumer option not available in the Create Links(s) dropdown
