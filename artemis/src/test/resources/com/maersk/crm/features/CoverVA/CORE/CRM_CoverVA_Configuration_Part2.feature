Feature: CoverVA Contact Record Configurations Part 2

  @CP-38518 @CP-38503 @CP-22100 @CP-19131 @CP-21279 @CP-17374 @CP-17374-08 @CP-17374-09 @CP-21427 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: CoverVA: Configure Contact Record Drop-downs - Gen, Third party, Unidentified: Reason&Action #CP-19131 is the most recent one
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I verify Contact Reason for Cover VA "<ContactReasons>" and associated Contact Action
    Examples:
      | ContactReasons                            |
      | Silent/No Consumer                        |
      | Silent/No Consumer - CVIU                 |
      | Appeal                                    |
      | Application/Renewal Status – CVCC         |
      | Application/Renewal Status – CVIU         |
      | Callback (Outbound)                       |
      | Change Request                            |
      | Complaint                                 |
      | Correspondence                            |
      | FAMIS Member Services                     |
      | General Inquiry                           |
      | ID Card Request                           |
      | Medicaid Member Services                  |
      | Member Follow-Up (Outbound)               |
      | New Application – CVCC                    |
      | New Application – CVIU                    |
      | Newborn Notification                      |
      | Pre-Hearing Conference (Inbound/Outbound) |
      | Renewal – CVCC                            |
      | Renewal – CVIU                            |
      | Silent/No Consumer                        |
      | Verification Information                  |
      | Expedited Applications - CVIU             |
      | Pre-Release Application - CVIU            |
      | Re-Entry Application - CVIU               |
      | Emergency Services - CVIU                 |
      | Callback (Outbound) - CVIU                |
      | Change Request - CVIU                     |
      | Complaint - CVIU                          |
      | Correspondence - CVIU                     |
      | General Inquiry - CVIU                    |
      | ID Card Request - CVIU                    |
      | Medicaid Member Services - CVIU           |
      | Newborn Notification - CVIU               |
      | Silent/No Consumer - CVIU                 |
      | Verification Information - CVIU           |
      | HIPAA Violation - CVIU                    |
      | HIPAA Violation - CVCC                    |
      | Emergency Services - CVCC                 |