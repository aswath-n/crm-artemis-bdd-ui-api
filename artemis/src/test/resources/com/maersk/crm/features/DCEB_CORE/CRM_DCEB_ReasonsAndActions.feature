Feature: DC-EB Reasons and Actions

  @CP-46028 @CP-46028-01 @CP-48676 @CP-48676-01 @ui-core-dc-eb @dc-eb-regression @crm-regression @araz
  # CP-46028(AC 1.0) # CP-48676(AC 1.0)
  Scenario: Verify to view the Contact Reasons and associated Reason Actions available-1
    Given I logged into CRM and select a project "DC-EB"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I navigate to Contact Reason "Call Status"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Escalated to Supervisor               |
      | Misdirected Call                      |
      | No Action Taken - Agent Disconnected  |
      | No Action Taken - Caller Disconnected |
      | No Audio - Disconnected               |
      | No Response - Disconnected            |
      | Scheduled Callback                    |
    And I navigate to Contact Reason "Callback (Outbound)"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Escalated to Supervisor                  |
      | No Action Taken - Did Not Reach Consumer |
      | Outreach Follow-Up                       |
      | Resolved Inquiry                         |
      | Scheduled New Callback                   |
      | Unable to Resolve                        |
    And I navigate to Contact Reason "Complaint"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Created Review Complaint Task |
      | Escalated to Supervisor       |
      | Other - See Notes             |
      | Referred to Third Party       |
      | Repeat Callers                |
      | Resolved                      |
    And I navigate to Contact Reason "Enrollment, Disenrollment, Plan Transfer Request"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Disenrollment Request              |
      | New Health Plan Selection Complete |
      | Plan Change Request                |
      | Reinstatement Request              |
      | Retroactive Disenrollment Request  |
      | Unable to Resolve                  |
    And I navigate to Contact Reason "General Inquiry"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Billing Inquiry Resolved         |
      | Health Plan Information Provided |
      | Other - See Notes                |
      | Unable to Resolve                |

  @CP-46028 @CP-46028-02 @ui-core-dc-eb @dc-eb-regression @crm-regression @araz
    # CP-46028(AC 1.0)
  Scenario: Verify to view the Contact Reasons and associated Reason Actions available-2
    Given I logged into CRM and select a project "DC-EB"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I navigate to Contact Reason "Health Program Status"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Unable to Resolve         |
      | Verify Eligibility Status |
      | Verify Enrollment Status  |
    And I navigate to Contact Reason "Insurance Card"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Replacement Requested |
      | Unable to Resolve     |
    And I navigate to Contact Reason "MCO Program Information"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Resolved |
    And I navigate to Contact Reason "New Consumer"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Consumer Created |
    And I navigate to Contact Reason with Text "Transfer"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Transfer - ESA Office   |
      | Transfer - MCO Referral |
      | Transfer - Wrong Number |
    And I navigate to Contact Reason "Update Application"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Address Change           |
      | Coverage Change          |
      | Email Change             |
      | Other Change - See Notes |
      | Phone Number Change      |
      | Unable to Resolve        |

  @CP-46028 @CP-46028-03 @ui-core-dc-eb @dc-eb-regression @crm-regression @araz
    # CP-46028(AC 1.0)
  Scenario: Verify to view the list of Contact Reasons
    Given I logged into CRM and select a project "DC-EB"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    Then I navigate and verify Contact Reason drop down values
      | Call Status                                      |
      | Callback (Outbound)                              |
      | Complaint                                        |
      | Enrollment, Disenrollment, Plan Transfer Request |
      | General Inquiry                                  |
      | Health Program Status                            |
      | Insurance Card                                   |
      | MCO Program Information                          |
      | New Consumer                                     |
      | Transfer                                         |
      | Update Application                               |