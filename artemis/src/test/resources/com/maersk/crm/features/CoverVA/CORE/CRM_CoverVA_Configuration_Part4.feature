Feature: CoverVA Contact Record Configurations Part 4


  @CP-38198 @CP-38198-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Verify additional options added in the Contact Action dropdown active contact-Contact Reason HIPAA Violation
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify additional contact action  drop down values
      | Outbound Call Made |
      | Potential HIPAA    |
    Examples:
      | business unit | contact reason         |
      | CVCC          | HIPAA Violation - CVCC |
      | CVIU - DJJ    | HIPAA Violation - CVIU |

  @CP-38198 @CP-38198-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Verify additional options added in the Contact Action dropdown active contact-Contact Reason ‘Medicaid Member Services
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify additional contact action  drop down values
      | Referred to Managed Care Helpline |
      | Transferred to Another Entity     |
    Examples:
      | business unit | contact reason           |
      | CVCC          | Medicaid Member Services |

  @CP-38198 @CP-38198-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Verify additional options added in the Contact Action dropdown active contact -Contact Reason FAMIS Member Services
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify additional contact action  drop down values
      | Changed FAMIS MCO        |
      | Referred to Other Entity |
    Examples:
      | business unit | contact reason        |
      | CVCC          | FAMIS Member Services |

  @CP-38198 @CP-38198-04 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Verify additional options added in the Contact Action dropdown active contact - Contact Reason General Inquiry
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify additional contact action  drop down values
      | Transferred to CVIU           |
      | Transferred to Another Entity |
    Examples:
      | business unit | contact reason  |
      | CVCC          | General Inquiry |

  @CP-38198 @CP-38198-05 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Verify additional options added in the Contact Action dropdown active contact - Contact Reason Silent/No Consumer
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify additional contact action  drop down values
      | No Action Taken - Caller Disconnected |
    Examples:
      | business unit | contact reason     |
      | CVCC          | Silent/No Consumer |

  @CP-38198 @CP-38198-06 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Verify additional options added in the Contact Action dropdown active contact - Contact Reason Correspondence
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify additional contact action  drop down values
      | Received Verification Checklist |
      | HIPAA Letter                    |
    Examples:
      | business unit | contact reason |
      | CVCC          | Correspondence |

  @CP-38198 @CP-38198-07 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Verify additional options added in the Contact Action dropdown active contact - Contact Reason Correspondence
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify additional contact action  drop down values
      | HIPAA Letter |
    Examples:
      | business unit | contact reason |
      | CVIU - DJJ    | Correspondence |

  @CP-38198 @CP-38198-08 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Verify additional options added in the Contact Action dropdown active contact - Contact Reasons any
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify additional contact action  drop down values
      | No Action Taken - Unable to Authenticate |
    Examples:
      | business unit | contact reason         |
      | CVCC          | HIPAA Violation - CVCC |
      | CVIU - DJJ    | HIPAA Violation - CVIU |

  @CP-38198 @CP-38198-09 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Verify additional options added in the Contact Action dropdown active contact - General Inquiry as the Contact Reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify additional contact action  drop down values
      | Transferred to CVCC |
    Examples:
      | business unit | contact reason         |
      | CVIU - DJJ    | General Inquiry - CVIU |

