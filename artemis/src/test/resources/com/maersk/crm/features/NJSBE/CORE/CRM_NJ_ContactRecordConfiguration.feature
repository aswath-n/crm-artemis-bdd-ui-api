Feature: CRM_NJ: Contact Record Configuration Updates

  @CP-17586 @CP-16398 @CP-11448 @CP-11448-01 @CP-26304-01 @27316-1.0 @27316-2.0 @ui-core-nj @nj-regression @crm-regression @kamil @araz
    #Review - Include steps to check these values in Manual Contact Record Search Page too
  Scenario Outline: Configure Inbound Call Queue field on Active Contact
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I navigate to "<ConPage>" Contact page
    And I will see INBOUND CALL QUEUE with options
      | Account Assistance - English                           |
      | Application Processing and Premium Questions - English |
      | Broker Navigator Technical - English                   |
      | Broker Navigator - English                             |
      | Broker Support - English                               |
      | Inbound - English                                      |
      | Medicaid - English                                     |
      | New Account - English                                  |
      | Outbound Call Campiagn                                 |
      | Outbound Call - English                                |
      | Lionbridge                                             |
      | Account Assistance - Spanish                           |
      | Application Processing and Premium Questions - Spanish |
      | Broker Support - Spanish                               |
      | Inbound - Spanish                                      |
      | New Account - Spanish                                  |
      | Verification Document Submission - Spanish             |

    Examples:
      | ConPage      |
      | General      |
      | ThirdParty   |
      | Unidentified |

  @CP-26304 @CP-26304-02 @27316-1.0 @27316-2.0 @ui-core-nj @nj-regression @crm-regression @kamil @araz
  Scenario Outline: Configure Inbound Call Queue field on View/Edit Saved Contact Record
    Given I logged into CRM and select a project "NJ-SBE"
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "<conId>"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And I will see INBOUND CALL QUEUE with options
      | Account Assistance - English                           |
      | Application Processing and Premium Questions - English |
      | Broker Navigator Technical - English                   |
      | Broker Navigator - English                             |
      | Broker Support - English                               |
      | Inbound - English                                      |
      | Medicaid - English                                     |
      | New Account - English                                  |
      | Outbound Call Campiagn                                 |
      | Outbound Call - English                                |
      | Lionbridge                                             |
      | Account Assistance - Spanish                           |
      | Application Processing and Premium Questions - Spanish |
      | Broker Support - Spanish                               |
      | Inbound - Spanish                                      |
      | New Account - Spanish                                  |
      | Verification Document Submission - Spanish             |
    Examples:
      | conId   |
      | 23006   |


  @CP-11448 @CP-11448-02 @ui-core-nj @nj-regression @crm-regression @kamil
  Scenario Outline: Verify Configure Program fields
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then Verify I see in Active Contact page
      | GENERAL CONTACT      |
      | THIRD PARTY CONTACT  |
      | UNIDENTIFIED CONTACT |
    And I will verify the PROGRAM options "<ProgramOptions>"
    Examples:
      | ProgramOptions |
      | GetCovered NJ  |
      | NJ FamilyCare  |
      | Other          |


  @CP-11448 @CP-11448-03 @CP-13364 @ui-core-nj @nj-regression @crm-regression @kamil
  Scenario: Verify Configure Call Campaign field
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then Verify Call Campaign has values
      | DMI                    |
      | Manual Outbound Call   |
      | Verification Documents |


  @CP-11448 @CP-11448-04 @ui-core-nj @nj-regression @crm-regression @kamil
  Scenario: Verify Configure Disposition field
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then Verify disposition field values are exist
      | Cancelled           |
      | Complete            |
      | Dropped             |
      | Escalate            |
      | Requested Call Back |
      | Transfer            |
    Then I click on "Outbound" Contact Type
    Then Verify disposition field values are exist
      | Cancelled           |
      | Complete            |
      | Dropped             |
      | Escalate            |
      | Outbound Incomplete |
      | Requested Call Back |
      | Transfer            |


  @CP-38062 @CP-38400 @CP-17649 @CP-17600 @CP-16253 @CP-11448 @CP-11448-05 @CP-13171 @CP-21767 @CP-48048 @ui-core-nj @nj-regression @crm-regression @kamil @khazar
  Scenario: Verify Contact Reason(s) & associated Contact Action(s)
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I verify Contact Reason and associated Contact Action for NJ-SBE
      | Appeal                              |
      | Enrollment, Disenrollment, Transfer |
      | Broker Support                      |
      | Change of Email or USPS Address     |
      | Complaint                           |
      | General Program Information         |
      | New Consumer                        |
      | Referral                            |
      | Status                              |
      | Technical Assistance/Password Reset |
      | Update Application                  |
      | Verification Document Inquiry       |
      | Loss of NJFC - Transferred          |
      | Loss of NJFC - Not Transferred      |


  @CP-16324 @CP-11448 @CP-11448-06 @ui-core-nj @nj-regression @crm-regression @kamil
  Scenario: Verify Consumer Field for Third Party Contacts
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I navigate to Third Party page
    And Verify consumer Type field with the options
      | Agency                    |
      | Assister                  |
      | Authorized Representative |
      | Broker                    |
      | Carrier                   |
      | Consumer                  |
      | Media                     |
      | Navigator                 |
      | Provider                  |


  @CP-11448 @CP-11448-07 @ui-core-nj @nj-regression @crm-regression @kamil
  Scenario Outline: Verify Reason for Edit field
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I search for contact record by contact id "<conId>"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then Verify below reasons for edit values exist
      | Adding Additional Comment          |
      | Adding Contact Details             |
      | Adding Contact Reason/Action       |
      | Correcting Additional Comment      |
      | Correcting Case/Consumer Link      |
      | Correcting Contact Reason/Action   |
      | Correcting Contact Details         |
      | Correcting Disposition             |
      | Correcting Third Party Information |
    Examples:
      | conId   |
      | 28991   |

  @CP-28285 @CP-11448 @CP-11448-08 @ui-core-nj @nj-regression @crm-regression @kamil
  Scenario: Verify Outcome of Contact field
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then Verify Outcome of Contact field with the options for NJ-SBE
      | Call Skipped, Issue Resolved |
      | Did not reach/No voicemail   |
      | Did not reach/Left voicemail |
      | Invalid Phone Number         |
      | Reached Successfully         |


  @CP-11448 @CP-11448-09 @ui-core-nj @nj-regression @crm-regression @kamil
  Scenario: Verify Channel field
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I will see the Channel field with the options
      | Phone |

  @CP-10860 @CP-10860-01 @ui-core-nj @nj-regression @crm-regression @aikanysh
  Scenario: NJ: Configure Third Party Contact Record Preferred Language Values: Active contact screen
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I click on third party contact record type radio button
    And I verify Preferred Language drop down with options below:
      | English    |
      | Spanish    |
      | Mandarin   |
      | Portuguese |
      | Tagalog    |
      | Italian    |
      | Korean     |
      | Gujarati   |
      | Polish     |
      | Hindi      |
      | Arabic     |

  @CP-10860 @CP-10860-02 @ui-core-nj @nj-regression @crm-regression @aikanysh
  Scenario Outline: NJ: Configure Third Party Contact Record Preferred Language Values : Edit Screen
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Contact Record search
    And I search for contact record by contact id "<conId>"
    When I click on first Contact Record ID on Contact Record
    And I click Edit button for third party contact record
    And I verify Preferred Language drop down with options below:
      | English    |
      | Spanish    |
      | Mandarin   |
      | Portuguese |
      | Tagalog    |
      | Italian    |
      | Korean     |
      | Gujarati   |
      | Polish     |
      | Hindi      |
      | Arabic     |
    Examples:
      | conId   |
      | 18047   |

  @CP-12551 @CP-12551-01 @ui-core-nj @nj-regression @crm-regression @kamil
  Scenario: CSR User Role Cannot Edit Contact Records
    Given I logged into CRM with CSR account and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I link the contact to an Consumer Profile with CSR Role
    And I navigate to the record
    And I will verify Edit Contact button are not displayed at page level


  @CP-15982 @CP-15982-01 @CP-15982-02 @ui-core-nj @nj-regression @crm-regression @kamil
  Scenario Outline: Editing When Contact Type = Inbound/Outbound for Channel Email
    Given I logged into CRM and select a project "NJ-SBE"
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "<conId>"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    And Verify Contact Record fields editable with ContactType "<ContactType>"
    Examples:
      | conId | ContactType |
      | 14416 | Inbound     |
      | 14226 | Outbound    |


