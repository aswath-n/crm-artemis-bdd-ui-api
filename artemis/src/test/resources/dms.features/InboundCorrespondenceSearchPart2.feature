Feature: Inbound Correspondence Search UI Part 2



  @CP-13273 @CP-13273-01 @ui-ecms1  @Sang
  Scenario Outline: Verify the created NJ document type is not retrieved when obtaining documents in BLCRM Search with CID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "<Document Types>"
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    Then I should see an Error Alert Message
      | Invalid Document Id |

    Examples:
      | projectName | Document Types |
      | NJ-SBE | NJ SBE General |
      | CoverVA | VACV Appeal    |

  @CP-13273 @CP-13273-02 @ui-ecms1  @Sang
  Scenario Outline: Verify the created NJ document type is not retrieved when obtaining documents in BLCRM Search with date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "<Document Types>"
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | DATERECEIVED | TODAY |
    And I get a list of the Inbound Correspondence Search CIDs
    Then I should not see the "created" CId in the result list

    Examples:
      | projectName | Document Types |
      | NJ-SBE  | NJ SBE General |

  @CP-13273 @CP-13273-03 @ui-ecms1  @Sang
  Scenario Outline: Verify the created NJ document type is not retrieved when searching for Inbound in the BLCRM Contact and Details tab
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document Type of "<Document Types>" linked to a following case number "7347"
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    When I searched customer have First Name as "Bruce" and Last Name as "Wayne"
    And I link the contact to an existing Case
    When I navigate to the case and contact details tab
    And I get a list of the Inbound Correspondence Search CIDs in the Case Contact Details Tab
    Then I should not see the "created" CId in the Contact and Details Tab Inbound CId list

    Examples:
      | projectName | Document Types |
      | NJ-SBE   | NJ SBE General |

  @CP-8959 @CP-8959-1 @ui-ecms1 @James
  Scenario: Verify Inbound Search results have view icons
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | 30182  |
    Then I should see that all the inbound correspondence search results have the following values
      | ViewIcon | PresentForEach |

  @CP-8959 @CP-8959-2 @ui-ecms1 @James
  Scenario: Verify that Inbound Correspondence Image viewable from any search result
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on a CID of "first available" Inbound Search Results
    And I click on the "view icon" in the Inbound Correspondence Details page
    Then I should see the Inbound Document is viewable

  @CP-8957 @CP-8957-1 @ui-ecms1 @James
  Scenario: Verify that it is possible to select more than one CORRESPONDENCETYPE
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CORRESPONDENCETYPE1 | maersk Fax Cover Sheet |
      | CORRESPONDENCETYPE2 | maersk Envelope        |
    Then I should see that I have selected the following values for CORRESPONDENCETYPE
      | CORRESPONDENCETYPE1 | maersk Fax Cover Sheet |
      | CORRESPONDENCETYPE2 | maersk Envelope        |

  @CP-8957 @CP-8957-2 @ui-ecms1 @James
  Scenario: Verify that I can search by more than one CORRESPONDENCETYPE
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CORRESPONDENCETYPE1 | maersk Fax Cover Sheet |
      | CORRESPONDENCETYPE2 | maersk Envelope        |
    Then I should see that all the inbound correspondence search results have the following values
      | CORRESPONDENCETYPE1 | maersk Fax Cover Sheet |
      | CORRESPONDENCETYPE2 | maersk Envelope        |

  @CP-8956 @CP-8956-1 @ui-ecms1 @James
  Scenario: Verify that it is possible to select more than one STATUS
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | STATUS1 | Barcode      |
      | STATUS2 | Transmitting |
    Then I should see that I have selected the following values for STATUS
      | STATUS1 | Barcode      |
      | STATUS2 | Transmitting |

  @CP-8956 @CP-8956-2 @ui-ecms1 @James
  Scenario: Verify that I can search by more than one STATUS
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | STATUS1 | Barcode      |
      | STATUS2 | Transmitting |
    Then I should see that all the inbound correspondence search results have the following values
      | STATUS1 | Barcode      |
      | STATUS2 | Transmitting |

  @CP-21065 @CP-21065-1 @ui-ecms-coverva @James
  Scenario: Verify clipboard is cleared out
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    Given I logged into CRM with "Service Tester 2" and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    And I click on the get image button on the Inbound Document Details Page
    And I navigate back to the Inbound Correspondence Search results
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    And I click on the clear button on Inbound Document search
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    Then I should see the clipboard has been cleared out

  @CP-19238 @ui-ecms1 @James @CP-19238-1
  Scenario: Verify status drop down values
    Given I logged into CRM
    And I click on the main navigation menu
    When I should see the Inbound Correspondence Search Icon
    Then I verify the status drop down value in the Inbound Correspondence Search
      | Received | Barcode | Failed to Send | Awaiting Indexing | Document Separation | Transmitting | Complete | Rescan Requested | Rescanned |
