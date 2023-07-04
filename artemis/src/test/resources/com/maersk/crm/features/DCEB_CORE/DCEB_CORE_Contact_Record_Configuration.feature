Feature: DC-EB Contact Record Configuration

  @CP-38728 @CP-38728-01 @ui-core-dc-eb @dc-eb-regression @crm-regression @aikanysh
  Scenario: Verify Channel field
    Given I logged into CRM with Superuser account and select a project "DC-EB"
    When I click on initiate contact record
    Then I will see the Channel field with the options
      | Phone      |
      | Email      |
      | Mobile App |
      | SMS Text   |
      | Web Chat   |
      | Web Portal |

  @CP-38728 @CP-38728-02 @ui-core-dc-eb @dc-eb-regression @crm-regression @aikanysh
  Scenario Outline: Verify Configure Program fields
    Given I logged into CRM with Superuser account and select a project "DC-EB"
    When I click on initiate contact record
    Then Verify I see in Active Contact page
      | GENERAL CONTACT      |
      | THIRD PARTY CONTACT  |
      | UNIDENTIFIED CONTACT |
    And I will verify the PROGRAM options "<ProgramOptions>"
    Examples:
      | ProgramOptions |
      | Medicaid       |

  @CP-38728 @CP-38728-03 @ui-core-dc-eb @dc-eb-regression @crm-regression @aikanysh
  Scenario: Verify Translation service field
    Given I logged into CRM with Superuser account and select a project "DC-EB"
    When I click on initiate contact record
    Then Verify I see in Active Contact page
    And I verify Translation Service dropdown with options below:
      | English    |
      | Other      |
      | Russian    |
      | Spanish    |
      | Vietnamese |

  @CP-38728 @CP-38728-04 @ui-core-dc-eb @dc-eb-regression @crm-regression @aikanysh
  Scenario: Verify claim ID field
  Given I logged into CRM with Superuser account and select a project "DC-EB"
    When I click on initiate contact record
    Then Verify I see in Active Contact page
    And I verify claim id is displayed

  @CP-38728 @CP-38728-05 @ui-core-dc-eb @dc-eb-regression @crm-regression @aikanysh
  Scenario: Verify Configure Disposition field
    Given I logged into CRM with Superuser account and select a project "DC-EB"
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

  @CP-38728 @CP-38728-06 @aikanysh @ui-core-dc-eb @dc-eb-regression
  Scenario: Verify Links Component for Case/Consumer linked to Contact Record DC_EB
    Given I logged into CRM with Superuser account and select a project "DC-EB"
    When I click on initiate contact record
    And I searched existing case where First Name as "Emmie" and Last Name as "Daugherty"
    Then I link the contact to an existing Case
    And I verify that Links Component's column titles

  @CP-38730 @CP-38730-01 @aikanysh @ui-core-dc-eb @dc-eb-regression
  Scenario: Configure Unidentified Contact Record: caller type
    Given I logged into CRM with Superuser account and select a project "DC-EB"
    When I click on initiate contact record
    When I click on unidentified contact record type radio button
    And I verify DC_EB Unidentified Caller Type drop down with options below:
      | Anonymous |

  @CP-38730 @CP-38730-02 @aikanysh @ui-core-dc-eb @dc-eb-regression
  Scenario: Configure Unidentified Contact Record: preff language
    Given I logged into CRM with Superuser account and select a project "DC-EB"
    When I click on initiate contact record
    When I click on unidentified contact record type radio button
    And I verify DC_EB  Unidentified Preferred Language drop down with options below:
      | English    |
      | Other      |
      | Russian    |
      | Spanish    |
      | Vietnamese |

  @CP-38729 @CP-38729-01 @aikanysh @ui-core-dc-eb @dc-eb-regression
  Scenario: Configure Third Party Contact Record: consumer type
    Given I logged into CRM with Superuser account and select a project "DC-EB"
    When I click on initiate contact record
    Then I navigate to Third Party page
    And Verify consumer Type field with the options
      | Agency   |
      | Consumer |
      | Media    |
      | Provider |

  @CP-38729 @CP-38729-02 @aikanysh @ui-core-dc-eb @dc-eb-regression
  Scenario: Configure Third Party Contact Record: preff language
    Given I logged into CRM with Superuser account and select a project "DC-EB"
    When I click on initiate contact record
    Then I navigate to Third Party page
    And I verify DC_EB  Unidentified Preferred Language drop down with options below:
      | English    |
      | Other      |
      | Russian    |
      | Spanish    |
      | Vietnamese |
