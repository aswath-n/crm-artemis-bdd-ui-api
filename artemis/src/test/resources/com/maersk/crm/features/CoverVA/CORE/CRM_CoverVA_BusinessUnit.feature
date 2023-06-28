Feature: CoverVA Business Unit and associated Reasons and Actions

  @CP-27387 @CP-27387-1.0 @Araz @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario:Business Unit Systematically Selected Based on Inbound Call Queue
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I click on the Contact Type "Inbound"
    And I click on the Inbound Call queue "CoverVA_CVCC_AppealsVoicemail_Eng01_Voice"
    Then I Verify that corresponded Business Unit value "CVCC" is displayed after selecting inbound call queue value

  @CP-27387 @CP-27387-1.1 @CP-27387-3.0 @Araz @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario:Inbound Call Queues and Corresponding Business Unit and
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I click on the Contact Type "Inbound"
    And I verify that following Inbound Call Queue and Corresponding CVIU Business Unit Value is not displayed
      | CoverVA_CVIU_Eng01_Voice                     |
      | CoverVA_CVIU_Outbound_Eng01_Voice            |
      | CoverVA_CVIU_Outbound_Spa01_Voice            |
      | CoverVA_CVIU_Spa02_Voice                     |
      | CoverVA_CVIU_SupervisorEscaltion_Eng01_Voice |
      | CoverVA_CVIU_SupervisorEscaltion_Spa02_Voice |
      | CoverVA_CVIU_Voicemail_Eng01_Voice           |
      | CoverVA_CVIU_Voicemail_Spa01_Voice           |
    Then I verify that following Inbound Call Queue and Corresponding CVCC Business Unit Value is displayed
      | CoverVA_CVCC_AppealsEscalation_Eng01_Voice   |
      | CoverVA_CVCC_AppealsEscalation_Spa01_Voice   |
      | CoverVA_CVCC_AppealsVoicemail_Eng01_Voice    |
      | CoverVA_CVCC_AppealsVoicemail_Spa01_Voice    |
      | CoverVA_CVCC_Apply_Eng01_Voice               |
      | CoverVA_CVCC_Apply_Spa02_Voice               |
      | CoverVA_CVCC_ExistingCustomer_Eng01_Voice    |
      | CoverVA_CVCC_ExistingCustomer_Spa02_Voice    |
      | CoverVA_CVCC_Outbound_Eng01_Voice            |
      | CoverVA_CVCC_Outbound_Spa01_Voice            |
      | CoverVA_CVCC_StatusChange_Eng01_Voice        |
      | CoverVA_CVCC_StatusChange_Spa02_Voice        |
      | CoverVA_CVCC_SupervisorEscaltion_Eng01_Voice |
      | CoverVA_CVCC_SupervisorEscaltion_Spa02_Voice |
      | CoverVA_CVCC_Voicemail_Eng01_Voice           |
      | CoverVA_CVCC_Voicemail_Spa01_Voice           |

  @CP-27387 @CP-27387-2.0 @CP-27387-3.0 @Araz @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario:Business Unit Selection is Editable
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I click on the Contact Type "Inbound"
    And I click on the Inbound Call queue "CoverVA_CVCC_AppealsVoicemail_Eng01_Voice"
    Then I am able to change the systematically chosen Business Unit value
      | CVCC         |
      | CVIU - DJJ   |
      | CVIU - DOC   |
      | CVIU - Jails |
      | CVIU - Other |
    And I click on the Inbound Call queue "CoverVA_CVCC_AppealsVoicemail_Eng01_Voice"
    And I click on the Inbound Call queue "CoverVA_CVIU_SupervisorEscaltion_Spa02_Voice"
    Then I verify that business unit dropdown is blank after Change a CVCC Inbound Call Queue to a CVIU Call Queue

  @CP-27387 @CP-27387-3.1 @Araz @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario:Change your Inbound Call Queue from a CVCC value to another CVCC value, then the Business Unit selection, which would be CVCC
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I click on the Contact Type "Inbound"
    Then I verify that following Inbound Call Queue and Corresponding CVCC Business Unit Value is displayed
      | CoverVA_CVCC_AppealsEscalation_Eng01_Voice |
      | CoverVA_CVCC_AppealsEscalation_Spa01_Voice |
    And I verify that changing Inbound Call Queue from a CVCC value to another CVCC value then the Business Unit selection would be CVCC

  @CP-33226 @CP-33226-01 @Araz @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline:The following Business Unit, Contact Reasons and Contact Actions shall be displayed in the respective dropdowns-CVCC
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    When I navigate to "<CR type>" contact record type
    And I select Business Unit "CVCC" and contact reasons "Correspondence"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to Supervisor             |
      | Extended Pend Letter                |
      | Generated Requested Form(s)         |
      | LDSS Communication Form             |
      | Provided Info for PHE Unwinding     |
      | Referred to                         |
      | Re-Generated VCL                    |
      | Re-Generated NOA                    |
      | NOA Translation Request submitted   |
      | VCL Translation Request submitted   |
      | Other Translation Request submitted |
      | Sent Certificate of Coverage        |
    And I select Business Unit "CVCC" and contact reasons "Appeal"
    Then Verify that contact actions have the respective dropdowns
      | Appeals Summary                     |
      | Escalated to Supervisor             |
      | Withdrew Appeal                     |
      | Conducted Pre-Hearing Conference    |
      | Created Pre-Hearing Conference Task |
      | Created Appeals Service Request     |
      | Provided Appeals Status             |
    And I select Business Unit "CVCC" and contact reasons "Application/Renewal Status - CVCC"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Application Status              |
      | Provided Renewal Status                  |
      | System Issues - Read VaCMS Down Script   |
    And I select Business Unit "CVCC" and contact reasons "Callback (Outbound)"
    Then Verify that contact actions have the respective dropdowns
      | Resolved Inquiry                   |
      | Unable to Resolve Inquiry          |
      | Escalated to Supervisor            |
      | No Action - Did Not Reach Consumer |
    And I select Business Unit "CVCC" and contact reasons "Change Request"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Report My Change                         |
      | System Issues - Read VaCMS Down Script   |
    And I select Business Unit "CVCC" and contact reasons "Complaint"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to DMAS              |
      | Escalated to Supervisor        |
      | Resolved                       |
      | Submitted for Processing       |
      | Withdrew Complaint             |
      | Referred to LDSS               |
      | Referred to MCO                |
      | Referred to Recipient Helpline |
    And I select Business Unit "CVCC" and contact reasons "Emergency Services - CVCC"
    Then Verify that contact actions have the respective dropdowns
      | Application Incomplete                 |
      | Complete Telephonic Application        |
      | Created Process Application            |
      | Escalated to CPU Eligibility           |
      | Escalated to Supervisor                |
      | Re-Entry                               |
      | System Issues - Read VaCMS Down Script |
      | Updated Application                    |
      | Withdrew Application                   |
    And I select Business Unit "CVCC" and contact reasons "FAMIS Member Services"
    Then Verify that contact actions have the respective dropdowns
      | Answered General Inquiry                              |
      | Escalated to Supervisor                               |
      | FAMIS Select Inquiry - Referred to CoverVA Website    |
      | FAMIS Select Inquiry - Referred to DMAS Unit          |
      | Provided Benefits Status                              |
      | Provided Coverage Information                         |
      | Provided Enrollment Status                            |
      | Provided Instructions on How to Cancel FAMIS Coverage |
      | Provided Participating Providers                      |
      | Referred to MCO                                       |
      | Referred to Recipient Help Line                       |
      | Regenerated 1095-B Form                               |
      | Provided 1095-B Information                           |
      | Provided Dental Information                           |
    And I select Business Unit "CVCC" and contact reasons "General Inquiry"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to Supervisor               |
      | Provided Program Information          |
      | Provided LDSS Office Information      |
      | Transferred to MCO                    |
      | Transferred to MCO Helpline           |
      | Transferred to Recipient Helpline     |
      | Transferred to Provider Helpline      |
      | Transferred to Medicare               |
      | Transferred to Enterprise Call Center |
      | Transferred to CCC Plus Helpline      |
    And I select Business Unit "CVCC" and contact reasons "HIPAA Violation - CVCC"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to Supervisor |
      | Referred to MCO         |
      | Referred to LDSS        |
      | Referred to FFM         |
    And I select Business Unit "CVCC" and contact reasons "ID Card Request"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to Supervisor                |
      | Generated New Medicaid ID Card in MMIS |
      | Referred to MCO                        |
    And I select Business Unit "CVCC" and contact reasons "Medicaid Member Services"
    Then Verify that contact actions have the respective dropdowns
      | Answered General Inquiry                                 |
      | Escalated to Supervisor                                  |
      | Provided Benefit Status                                  |
      | Provided Coverage Information                            |
      | Provided Enrollment Status                               |
      | Provided Instructions on How to Cancel Medicaid Coverage |
      | Provided Participating Providers                         |
      | Referred to MCO                                          |
      | Referred to Recipient Help Line                          |
      | Regenerated 1095-B Form                                  |
      | Provided 1095-B Information                              |
      | Provided CCC Plus Information                            |
      | Provided Dental Information                              |
    And I select Business Unit "CVCC" and contact reasons "Member Follow-Up (Outbound)"
    Then Verify that contact actions have the respective dropdowns
      | Requested Required Information     |
      | Received Required Information      |
      | Escalated to Supervisor            |
      | No Action - Did Not Reach Consumer |
    And I select Business Unit "CVCC" and contact reasons "New Application - CVCC"
    Then Verify that contact actions have the respective dropdowns
      | Application Incomplete                      |
      | Completed Telephonic Application            |
      | Created Process Application Service Request |
      | Escalated to CPU                            |
      | Escalated to Locality                       |
      | Escalated to Supervisor                     |
      | Referred to Enterprise Call Center          |
      | System Issues - Read VaCMS Down Script      |
      | Updated Application                         |
      | Withdrew Application                        |
    And I select Business Unit "CVCC" and contact reasons "Newborn Notification"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to Supervisor |
      | Submitted Deeming Form  |
    And I select Business Unit "CVCC" and contact reasons "Pre-Hearing Conference (Inbound/Outbound)"
    Then Verify that contact actions have the respective dropdowns
      | Completed PHC - Resolved                  |
      | Completed PHC - Referred to DMAS Appeals  |
      | Escalated to Supervisor                   |
      | Unable to Reach - Scheduled Outbound Call |
    And I select Business Unit "CVCC" and contact reasons "Renewal - CVCC"
    Then Verify that contact actions have the respective dropdowns
      | Completed Telephonic Renewal             |
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Renewal Incomplete                       |
      | System Issues - Read VaCMS Down Script   |
    And I select Business Unit "CVCC" and contact reasons "Reported Fraud"
    Then Verify that contact actions have the respective dropdowns
      | Provided Recipient Fraud Information |
      | Provided Provider Fraud Information  |
    And I select Business Unit "CVCC" and contact reasons "Silent/No Consumer"
    Then Verify that contact actions have the respective dropdowns
      | No Audio - Disconnected    |
      | No Response - Disconnected |
      | Scheduled Callback         |
      | Escalated to Supervisor    |
    And I select Business Unit "CVCC" and contact reasons "Verification Information"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | System Issues - Read VaCMS Down Script   |
      | Updated Contact Information              |
      | Updated Demographic Information          |
      | Updated Household Information            |
      | Updated Income Information               |
    Examples:
      | CR type      |
      | General      |
      | ThirdParty   |
      | Unidentified |

  @CP-33226 @CP-33226-02 @Araz @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline:The following Business Unit, Contact Reasons and Contact Actions shall be displayed in the respective dropdowns-CVIU
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I navigate to business unit "<Bus type>"
    And I navigate to Contact Reason "Correspondence - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | CVIU Communication Form     |
      | Escalated to Supervisor     |
      | Extended Pend Letter        |
      | Generated Requested Form(s) |
      | LDSS Communication Form     |
      | Re-Generated NOA            |
      | Re-Generated VCL            |
      | Referred to                 |
    And I navigate to Contact Reason "Appeal"
    Then Verify that contact actions have the respective dropdowns
      | Appeals Summary                     |
      | Escalated to Supervisor             |
      | Withdrew Appeal                     |
      | Conducted Pre-Hearing Conference    |
      | Created Pre-Hearing Conference Task |
      | Created Appeals Service Request     |
      | Provided Appeals Status             |
    And I navigate to Contact Reason "Application/Renewal Status - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to CPU                         |
      | Escalated to CVIU Eligibility            |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Application Status              |
      | Provided Renewal Status                  |
      | System Issues - Read VaCMS Down Script   |
    And I navigate to Contact Reason "Callback (Outbound) - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Resolved Inquiry                   |
      | Unable to Resolve Inquiry          |
      | Escalated to Supervisor            |
      | No Action - Did Not Reach Consumer |
    And I navigate to Contact Reason "Change Request - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Report My Change                         |
      | System Issues - Read VaCMS Down Script   |
    And I navigate to Contact Reason "Complaint - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to DMAS              |
      | Escalated to Supervisor        |
      | Resolved                       |
      | Submitted for Processing       |
      | Withdrew Complaint             |
      | Referred to LDSS               |
      | Referred to MCO                |
      | Referred to Recipient Helpline |
    And I navigate to Contact Reason "Emergency Services - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Application Incomplete                      |
      | Completed Telephonic Application            |
      | Created Process Application Service Request |
      | Escalated to CVIU Eligibility               |
      | Escalated to Supervisor                     |
      | Re-Entry                                    |
      | System Issues - Read VaCMS Down Script      |
      | Updated Application                         |
      | Withdrew Application                        |
    And I navigate to Contact Reason "Expedited Applications - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Application Incomplete                      |
      | Completed Telephonic Application            |
      | Created Process Application Service Request |
      | Escalated to CVIU Eligibility               |
      | Escalated to Supervisor                     |
      | Re-Entry                                    |
      | System Issues - Read VaCMS Down Script      |
      | Updated Application                         |
      | Withdrew Application                        |
    And I navigate to Contact Reason "FAMIS Member Services"
    Then Verify that contact actions have the respective dropdowns
      | Answered General Inquiry                              |
      | Escalated to Supervisor                               |
      | FAMIS Select Inquiry - Referred to CoverVA Website    |
      | FAMIS Select Inquiry - Referred to DMAS Unit          |
      | Provided Benefits Status                              |
      | Provided Coverage Information                         |
      | Provided Enrollment Status                            |
      | Provided Instructions on How to Cancel FAMIS Coverage |
      | Provided Participating Providers                      |
      | Referred to MCO                                       |
      | Referred to Recipient Help Line                       |
      | Regenerated 1095-B Form                               |
      | Provided 1095-B Information                           |
      | Provided Dental Information                           |
    And I navigate to Contact Reason "General Inquiry - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to Supervisor          |
      | Provided Program Information     |
      | Provided LDSS Office Information |
      | Transferred to External Entity   |
    And I navigate to Contact Reason "HIPAA Violation - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to Supervisor |
      | Referred to MCO         |
      | Referred to LDSS        |
      | Referred to FFM         |
    And I navigate to Contact Reason "ID Card Request - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to Supervisor                |
      | Generated New Medicaid ID Card in MMIS |
      | Referred to MCO                        |
    And I navigate to Contact Reason "Medicaid Member Services - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Answered General Inquiry                                 |
      | Escalated to Supervisor                                  |
      | Provided Benefit Status                                  |
      | Provided Coverage Information                            |
      | Provided Enrollment Status                               |
      | Provided Instructions on How to Cancel Medicaid Coverage |
      | Provided Participating Providers                         |
      | Referred to MCO                                          |
      | Referred to Recipient Help Line                          |
    And I navigate to Contact Reason "Member Follow-Up (Outbound)"
    Then Verify that contact actions have the respective dropdowns
      | Requested Required Information     |
      | Received Required Information      |
      | Escalated to Supervisor            |
      | No Action - Did Not Reach Consumer |
    And I navigate to Contact Reason "New Application - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Application Incomplete                      |
      | Completed Telephonic Application            |
      | Created Process Application Service Request |
      | Escalated to CVIU Eligibility               |
      | Escalated to Supervisor                     |
      | Re-Entry                                    |
      | System Issues - Read VaCMS Down Script      |
      | Updated Application                         |
      | Withdrew Application                        |
    And I navigate to Contact Reason "Newborn Notification - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to Supervisor |
      | Submitted Deeming Form  |
    And I navigate to Contact Reason "Pre-Hearing Conference (Inbound/Outbound)"
    Then Verify that contact actions have the respective dropdowns
      | Completed PHC - Resolved                  |
      | Completed PHC - Referred to DMAS Appeals  |
      | Escalated to Supervisor                   |
      | Unable to Reach - Scheduled Outbound Call |
    And I navigate to Contact Reason "Pre-Release Application - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Application Incomplete                      |
      | Completed Telephonic Application            |
      | Created Process Application Service Request |
      | Escalated to CVIU Eligibility               |
      | Escalated to Supervisor                     |
      | Re-Entry                                    |
      | System Issues - Read VaCMS Down Script      |
      | Updated Application                         |
      | Withdrew Application                        |
    And I navigate to Contact Reason "Re-Entry Application - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Application Incomplete                      |
      | Completed Telephonic Application            |
      | Created Process Application Service Request |
      | Escalated to CVIU Eligibility               |
      | Escalated to Supervisor                     |
      | Re-Entry                                    |
      | System Issues - Read VaCMS Down Script      |
      | Updated Application                         |
      | Withdrew Application                        |
    And I navigate to Contact Reason "Renewal - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Completed Telephonic Renewal             |
      | Escalated to CVIU Eligibility            |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Renewal Incomplete                       |
      | System Issues - Read VaCMS Down Script   |
    And I navigate to Contact Reason "Reported Fraud"
    Then Verify that contact actions have the respective dropdowns
      | Provided Recipient Fraud Information |
      | Provided Provider Fraud Information  |
    And I navigate to Contact Reason "Silent/No Consumer - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | No Audio - Disconnected    |
      | No Response - Disconnected |
      | Scheduled Callback         |
      | Escalated to Supervisor    |
    And I navigate to Contact Reason "Verification Information - CVIU"
    Then Verify that contact actions have the respective dropdowns
      | Escalated to CPU                         |
      | Escalated to CVIU                        |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | System Issues - Read VaCMS Down Script   |
      | Updated Contact Information              |
      | Updated Demographic Information          |
      | Updated Household Information            |
      | Updated Income Information               |
    Examples:
      | Bus type     |
      | CVIU - DOC   |
      | CVIU - DJJ   |
      | CVIU - Jails |
      | CVIU - Other |

  @CP-35243 @CP-35243-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Contact Reason and Action values remain when switching from CVIU unit to CVIU and clears when CVCC is selected in Cover VA
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I select Business unit "CVIU - DJJ" for coverVA
    And I select contact Reason "RANDOM" and Contact Action "RANDOM"
    And I select Business unit "CVIU - DOC" for coverVA
    And I verify the selected Contact Reason and Contact Action values "REMAIN THE SAME"
    And I select Business unit "CVIU - Jails" for coverVA
    And I verify the selected Contact Reason and Contact Action values "REMAIN THE SAME"
    And I select Business unit "CVIU - Other" for coverVA
    And I verify the selected Contact Reason and Contact Action values "REMAIN THE SAME"
    And I select Business unit "CVCC" for coverVA
    And I verify the selected Contact Reason and Contact Action values "HAS CLEARED FROM SELECTION"

  @CP-35243 @CP-35243-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify CVCC Contact Reason and Action values clears when CVIU Business unit is selected in Cover VA
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I select Business unit "CVCC" for coverVA
    And I select contact Reason "RANDOM" and Contact Action "RANDOM"
    And I select Business unit "CVIU - DOC" for coverVA
    And I verify the selected Contact Reason and Contact Action values "HAS CLEARED FROM SELECTION"

  @CP-42767 @CP-42767-01 @CP-42766 @CP-42766-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Two new values CVCCPW and CVCCNavAsst are added to the Business Unit dropdown in CoverVa
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And And I click on "BUSINESS UNIT" in CoverVa active contact page
    Then I verify the following values are displayed as options in CoverVA Business Unit
      | CVCC-PW | CVCC-Nav/Asst | CPU |

  @CP-42767 @CP-42767-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Provided Info for PHE Unwinding should no longer be displayed when CVCC CVCCPW or CVCCNavAsst is selected and Correspondence is chosen as the contact reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select Business unit "CVCC" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | Correspondence |
    Then I verify "Provided Info for PHE Unwinding" is "NOT DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVCC-PW" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | Correspondence |
    Then I verify "Provided Info for PHE Unwinding" is "NOT DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVCC-Nav/Asst" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | Correspondence |
    Then I verify "Provided Info for PHE Unwinding" is "NOT DISPLAYED" as a value in the Contact Action dropdown list

  @CP-42767 @CP-42767-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Provided Info for PHE Unwinding should be displayed when CVCC CVCCPW or CVCCNavAsst is selected and FAMIS Member Services is chosen as the contact reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select Business unit "CVCC" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | FAMIS Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVCC-PW" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | FAMIS Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVCC-Nav/Asst" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | FAMIS Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list

  @CP-42767 @CP-42767-04 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Provided Info for PHE Unwinding should be displayed when CVCC CVCCPW or CVCCNavAsst is selected and Medicaid Member Services is chosen as the contact reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select Business unit "CVCC" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | Medicaid Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVCC-PW" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | Medicaid Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVCC-Nav/Asst" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | Medicaid Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list

  @CP-42767 @CP-42767-05 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Provided Info for PHE Unwinding should be displayed when CVIUDOC CVIUDJJ CVIUJails or CVIUOther is selected and FAMIS Member Services is chosen as the contact reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select Business unit "CVIU - DOC" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | FAMIS Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVIU - DJJ" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | FAMIS Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVIU - Jails" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | FAMIS Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVIU - Other" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | FAMIS Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list

  @CP-42767 @CP-42767-06 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Provided Info for PHE Unwinding should be displayed when CVIUDOC CVIUDJJ CVIUJails or CVIUOther is selected and Medicaid Member Services is chosen as the contact reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select Business unit "CVIU - DOC" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | Medicaid Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVIU - DJJ" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | Medicaid Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVIU - Jails" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | Medicaid Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list
    And I select Business unit "CPU" for coverVA
    And I select Business unit "CVIU - Other" for coverVA
    And And I click on "CONTACT REASON" in CoverVa active contact page and select the following values
      | Medicaid Member Services |
    Then I verify "Provided Info for PHE Unwinding" is "DISPLAYED" as a value in the Contact Action dropdown list

  @CP-42362 @CP-42362-01 @CP-46000 @CP-46000-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
    # CP-46000(AC 1.0)
  Scenario:Business Unit Selections to the Contact Record
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And And I click on "BUSINESS UNIT" in CoverVa active contact page
    Then I verify the following values are displayed as options in CoverVA Business Unit
      | CPU | CVCC | CVCC-Nav/Asst | CVCC-PW | CVIU - DJJ | CVIU - DOC | CVIU - Jails | CVIU - Other |

  @CP-46000 @CP-46000-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
    # CP-46000(AC 1.0)
  Scenario: VA: Display Cancelled Contact Record Reason-Edit Page Page
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the contact without Application ID with BusinessUnit Drop Down "CVCC"
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record

  @CP-42362 @CP-42362-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario:The following Contact Reasons and Contact Actions shall be displayed for CVCC-01
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I select Business unit "CVCC" for coverVA
    And I navigate to Contact Reason "Correspondence"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Escalated to Supervisor                  |
      | Extended Pend Letter                     |
      | Generated Requested Form(s)              |
      | HIPAA Letter                             |
      | LDSS Communication Form                  |
      | No Action Taken - Unable to Authenticate |
      | NOA Translation Request submitted        |
      | Other Translation Request submitted      |
      | Received Verification Checklist          |
      | Re-Generated VCL                         |
      | Referred to CoverVa.org for Self Print   |
      | Re-Generated NOA                         |
      | VCL Translation Request submitted        |
      | Sent Certificate of Coverage             |
    And I navigate to Contact Reason "Appeal"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Appeals Summary                          |
      | Escalated to Supervisor                  |
      | Withdrew Appeal                          |
      | Conducted Pre-Hearing Conference         |
      | Created Pre-Hearing Conference Task      |
      | Created Appeals Service Request          |
      | Provided Appeals Status                  |
      | No Action Taken - Unable to Authenticate |
    And I navigate to Contact Reason "Application Status - CVCC"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Application Status              |
      | System Issues - Read VaCMS Down Script   |
    And I navigate to Contact Reason "Callback (Outbound)"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Resolved Inquiry                         |
      | Unable to Resolve Inquiry                |
      | Escalated to Supervisor                  |
      | No Action - Did Not Reach Consumer       |
      | No Action Taken - Unable to Authenticate |
    And I navigate to Contact Reason "Change Request"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Address Change                           |
      | Escalated to CPU                         |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Report My Change                         |
      | System Issues - Read VaCMS Down Script   |
    And I navigate to Contact Reason "Complaint"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Escalated to DMAS                        |
      | Escalated to Supervisor                  |
      | Resolved                                 |
      | Submitted for Processing                 |
      | Withdrew Complaint                       |
      | Referred to LDSS                         |
      | Referred to MCO                          |
      | Referred to Recipient Helpline           |
      | No Action Taken - Unable to Authenticate |
    And I navigate to Contact Reason "Emergency Services - CVCC"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Application Incomplete                   |
      | Complete Telephonic Application          |
      | Created Process Application              |
      | Escalated to CPU Eligibility             |
      | Escalated to Supervisor                  |
      | Re-Entry                                 |
      | System Issues - Read VaCMS Down Script   |
      | Updated Application                      |
      | Withdrew Application                     |
      | No Action Taken - Unable to Authenticate |
    And I navigate to Contact Reason "FAMIS Member Services"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Answered General Inquiry                              |
      | Changed FAMIS MCO                                     |
      | Escalated to Supervisor                               |
      | FAMIS Select Inquiry - Referred to CoverVA Website    |
      | FAMIS Select Inquiry - Referred to DMAS Unit          |
      | No Action Taken - Unable to Authenticate              |
      | Provided Benefits Status                              |
      | Provided Coverage Information                         |
      | Provided Enrollment Status                            |
      | Provided Instructions on How to Cancel FAMIS Coverage |
      | Provided Participating Providers                      |
      | Provided Info for PHE Unwinding                       |
      | Referred to MCO                                       |
      | Referred to Other Entity                              |
      | Referred to Recipient Help Line                       |
      | Regenerated 1095-B Form                               |
      | Provided 1095-B Information                           |
      | Provided Dental Information                           |
    And I navigate to Contact Reason "General Inquiry"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Escalated to Supervisor                  |
      | Provided Program Information             |
      | Provided LDSS Office Information         |
      | Transferred to Another Entity            |
      | Transferred to CVIU                      |
      | Transferred to MCO                       |
      | Transferred to MCO Helpline              |
      | Transferred to Recipient Helpline        |
      | Transferred to Provider Helpline         |
      | Transferred to Medicare                  |
      | Transferred to Enterprise Call Center    |
      | Transferred to CCC Plus Helpline         |
      | No Action Taken - Unable to Authenticate |

  @CP-42362 @CP-42362-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario:The following Contact Reasons and Contact Actions shall be displayed for CVCC-02
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I select Business unit "CVCC" for coverVA
    And I navigate to Contact Reason "HIPAA Violation - CVCC"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Escalated to Supervisor                  |
      | Referred to MCO                          |
      | Referred to LDSS                         |
      | Referred to FFM                          |
      | Potential HIPAA                          |
      | Outbound Call Made                       |
      | No Action Taken - Unable to Authenticate |
    And I navigate to Contact Reason "ID Card Request"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Escalated to Supervisor                  |
      | Generated New Medicaid ID Card in MMIS   |
      | Referred to MCO                          |
      | No Action Taken - Unable to Authenticate |
    And I navigate to Contact Reason "Medicaid Member Services"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Answered General Inquiry                                 |
      | Escalated to Supervisor                                  |
      | No Action Taken - Unable to Authenticate                 |
      | Provided Info for PHE Unwinding                          |
      | Provided Benefit Status                                  |
      | Provided Coverage Information                            |
      | Provided Enrollment Status                               |
      | Provided Instructions on How to Cancel Medicaid Coverage |
      | Provided Participating Providers                         |
      | Referred to MCO                                          |
      | Referred to Recipient Help Line                          |
      | Regenerated 1095-B Form                                  |
      | Provided 1095-B Information                              |
      | Provided CCC Plus Information                            |
      | Provided Dental Information                              |
      | Referred to Managed Care Helpline                        |
      | Transferred to Another Entity                            |
    And I navigate to Contact Reason "Member Follow-Up (Outbound)"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Requested Required Information           |
      | Received Required Information            |
      | Escalated to Supervisor                  |
      | No Action - Did Not Reach Consumer       |
      | No Action Taken - Unable to Authenticate |
    And I navigate to Contact Reason "New Application - CVCC"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Application Incomplete                      |
      | Completed Telephonic Application            |
      | Created Process Application Service Request |
      | Escalated to CPU                            |
      | Escalated to Locality                       |
      | Escalated to Supervisor                     |
      | No Action Taken - Unable to Authenticate    |
      | Referred to Enterprise Call Center          |
      | System Issues - Read VaCMS Down Script      |
      | Updated Application                         |
      | Withdrew Application                        |
    And I navigate to Contact Reason "Newborn Notification"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Escalated to Supervisor                  |
      | Submitted Deeming Form                   |
      | No Action Taken - Unable to Authenticate |
    And I navigate to Contact Reason "Pre-Hearing Conference (Inbound/Outbound)"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Completed PHC - Resolved                  |
      | Completed PHC - Referred to DMAS Appeals  |
      | Escalated to Supervisor                   |
      | Unable to Reach - Scheduled Outbound Call |
      | No Action Taken - Unable to Authenticate  |
    And I navigate to Contact Reason "Renewal - CVCC"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Completed Telephonic Renewal             |
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Renewal Incomplete                       |
      | System Issues - Read VaCMS Down Script   |
    And I navigate to Contact Reason "Reported Fraud"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Provided Recipient Fraud Information     |
      | Provided Provider Fraud Information      |
      | No Action Taken - Unable to Authenticate |
    And I navigate to Contact Reason "Silent/No Consumer"
    Then I verify Contact Action drop down value with associated Contact Reason
      | No Audio - Disconnected                  |
      | No Response - Disconnected               |
      | Scheduled Callback                       |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | No Action Taken - Caller Disconnected    |
    And I navigate to Contact Reason "Verification Information"
    Then I verify Contact Action drop down value with associated Contact Reason
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | System Issues - Read VaCMS Down Script   |
      | Updated Contact Information              |
      | Updated Demographic Information          |
      | Updated Household Information            |
      | Updated Income Information               |

  @CP-42766 @CP-42766-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Contact Reason Application Renewal Status CVIU  should no longer be displayed when CPU CVCC CVIU-DJJ CVIU-DOC CVIU-Jails or CVIU-Other is selected as Buisness Unit
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select Business unit "CPU" for coverVA
    Then I verify "Application/Renewal Status-CVIU" is "NOT DISPLAYED" as a value in the Contact Reason dropdown list
    And I select Business unit "CVCC" for coverVA
    Then I verify "Application/Renewal Status-CVIU" is "NOT DISPLAYED" as a value in the Contact Reason dropdown list
    And I select Business unit "CVIU - DJJ" for coverVA
    Then I verify "Application/Renewal Status-CVIU" is "NOT DISPLAYED" as a value in the Contact Reason dropdown list
    And I select Business unit "CVIU - DOC" for coverVA
    Then I verify "Application/Renewal Status-CVIU" is "NOT DISPLAYED" as a value in the Contact Reason dropdown list
    And I select Business unit "CVIU - Jails" for coverVA
    Then I verify "Application/Renewal Status-CVIU" is "NOT DISPLAYED" as a value in the Contact Reason dropdown list
    And I select Business unit "CVIU - Other" for coverVA
    Then I verify "Application/Renewal Status-CVIU" is "NOT DISPLAYED" as a value in the Contact Reason dropdown list

  @CP-42766 @CP-42766-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Application Status CPU and Renewal Status CPU values should be displayed when CPU CVCC CVIU-DJJ CVIU-DOC CVIU-Jails and CVIU-Other
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I select Business unit "CPU" for coverVA
    Then I verify "Application Status CPU and Renewal Status CPU" is "DISPLAYED" as a value in the Contact Reason dropdown list
    And I select Business unit "CVCC" for coverVA
    Then I verify "Application Status CPU and Renewal Status CPU" is "DISPLAYED" as a value in the Contact Reason dropdown list
    And I select Business unit "CVIU - DJJ" for coverVA
    Then I verify "Application Status CPU and Renewal Status CPU" is "DISPLAYED" as a value in the Contact Reason dropdown list
    And I select Business unit "CVIU - DOC" for coverVA
    Then I verify "Application Status CPU and Renewal Status CPU" is "DISPLAYED" as a value in the Contact Reason dropdown list
    And I select Business unit "CVIU - Jails" for coverVA
    Then I verify "Application Status CPU and Renewal Status CPU" is "DISPLAYED" as a value in the Contact Reason dropdown list
    And I select Business unit "CVIU - Other" for coverVA
    Then I verify "Application Status CPU and Renewal Status CPU" is "DISPLAYED" as a value in the Contact Reason dropdown list

  @CP-42766 @CP-42766-04 @CP-43398 @CP-43398-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Contact Action selections for CPU Business Unit with Contact Action Application Status CPU
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "CPU" Business Unit with Contact Reason "Application Status - CPU" with the following Action values
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Application Status              |
      | Provided Info for PHE Unwinding          |
      | System Issues - Read VaCMS Down Script   |

  @CP-42766 @CP-42766-05 @CP-43398 @CP-43398-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Contact Action selections for CPU Business Unit with Contact Action Renewal Status CPU
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "CPU" Business Unit with Contact Reason "Renewal Status - CPU" with the following Action values
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Renewal Status                  |
      | Provided Info for PHE Unwinding          |
      | System Issues - Read VaCMS Down Script   |

  @CP-42766 @CP-42766-06 @CP-43398 @CP-43398-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Contact Action selections for CVCC Business Unit with Contact Action Application Status CVCC
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "CVCC" Business Unit with Contact Reason "Application Status - CVCC" with the following Action values
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Application Status              |
      | Provided Info for PHE Unwinding          |
      | System Issues - Read VaCMS Down Script   |

  @CP-42766 @CP-42766-07 @CP-43398 @CP-43398-04 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Contact Action selections for CVCC Business Unit with Contact Action Renewal Status CVCC
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "CVCC" Business Unit with Contact Reason "Renewal Status - CVCC" with the following Action values
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Renewal Status                  |
      | Provided Info for PHE Unwinding          |
      | System Issues - Read VaCMS Down Script   |

  @CP-42766 @CP-42766-08 @CP-43398 @CP-43398-05 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Contact Action selections for CVIU DJJ CVIU DOC CVIU Jails and CVIU Other Business Unit with Contact Action Application Status CVIU
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "CVIU-DJJ CVIU-DOC CVIU-Jails and CVIU-Other" Business Unit with Contact Reason "Application Status - CVIU" with the following Action values
      | Escalated to CPU                         |
      | Escalated to CVIU Eligibility            |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Application Status              |
      | Provided Info for PHE Unwinding          |
      | System Issues - Read VaCMS Down Script   |

  @CP-42766 @CP-42766-09 @CP-43398 @CP-43398-06 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Contact Action selections for CVIU-DJJ CVIU-DOC CVIU-Jails and CVIU-Other Business Unit with Contact Renewal Status CVIU
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "CVIU-DJJ CVIU-DOC CVIU-Jails and CVIU-Other" Business Unit with Contact Reason "Renewal Status - CVIU" with the following Action values
      | Escalated to CPU                         |
      | Escalated to CVIU Eligibility            |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Renewal Status                  |
      | Provided Info for PHE Unwinding          |
      | System Issues - Read VaCMS Down Script   |

  @CP-42363 @CP-42363-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Business Unit CVCC Contact Reason PCS Dissatisfied Outreach and associated Contact Actions
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "CVCC" Business Unit with Contact Reason "PCS Dissatisfied Outreach" with the following Action values
      | Contact Made – Issue Resolved            |
      | Contact Made – Left V/M                  |
      | Unable to Communicate – No V/M Available |

  @CP-42486 @CP-42486-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @Araz
  Scenario: New queues listed below and will be added to ENUM_INBOUND_CALL_QUEUE
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    And I click on the Contact Type "Inbound"
    Then I verify newly added Inbound Call Queue dropdown values
      | CoverVA_CVCC_NewAppPW_Eng01_Voice         |
      | CoverVA_CVCC_NewAppPW_Spa02_Voice         |
      | CoverVA_CVCC_AppStatusPW_Eng01_Voice      |
      | CoverVA_CVCC_AppStatusPW_Spa01_Voice      |
      | CoverVA_CVCC_ExistBenePW_Eng01_Voice      |
      | CoverVA_CVCC_ExistBenePW_Spa01_Voice      |
      | CoverVA_CVCC_NewAppAssist_Eng01_Voice     |
      | CoverVA_CVCC_NewAppAssist_Eng01_Voice     |
      | CoverVA_CVCC_AppStatusAssist_Eng01_Voice  |
      | CoverVA_CVCC_AppStatusAssist_Spa02_Voice  |
      | CoverVA_CVCC_ExistBeneAssist_Eng01_Voice  |
      | CoverVA_CVCC_ExistBeneAssist_Spa02_Voice  |
      | CoverVA_CVCC_RenewalStatus_Eng01_Voice    |
      | CoverVA_CVCC_RenewalStatus_Sap02_Voice    |
      | CoverVA_CVCC_PWOutbound_Eng01_Voice       |
      | CoverVA_CVCC_PWOutbound_Spa02_Voice       |
      | CoverVA_CVCC_RenewalVoicemail_Eng01_Voice |
      | CoverVA_CVCC_RenewalVoicemail_Spa01_Voice |
      | CoverVA_CVCC_Voicemail_PW_Eng01_Voice     |
      | CoverVA_CVCC_Voicemail_PW_Spa02_Voice     |
      | CoverVA_CVCC_Voicemail_Assist_Eng01_Voice |
      | CoverVA_CVCC_Voicemail_Assist_Spa02_Voice |

  @CP-43392 @CP-43392-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify all CoverVA Business Unit with Contact Reason New Application Pre-Hearing Conference (Inbound/Outbound) and associated Contact Actions
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "ALL" Business Unit with Contact Reason "New Application Pre-Hearing Conference (Inbound/Outbound)" with the following Action values
      | Completed PHC - Referred to DMAS Appeals  |
      | Completed PHC - Resolved                  |
      | Escalated to Supervisor                   |
      | No Action Taken - Unable to Authenticate  |
      | Unable to Reach - Scheduled Outbound Call |

  @CP-43392 @CP-43392-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify all CoverVA Business Unit with Contact Reason Renewal Pre-Hearing Conference (Inbound/Outbound) and associated Contact Actions
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "ALL" Business Unit with Contact Reason "Renewal Pre-Hearing Conference (Inbound/Outbound)" with the following Action values
      | Completed PHC - Referred to DMAS Appeals  |
      | Completed PHC - Resolved                  |
      | Escalated to Supervisor                   |
      | No Action Taken - Unable to Authenticate  |
      | Unable to Reach - Scheduled Outbound Call |

  @CP-43392 @CP-43392-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify all CoverVA Business Unit does not display Pre-Hearing Conference (Inbound/Outbound) as a contact reason
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify for "ALL" CoverVa business unit Contact Reason does not display "Pre-Hearing Conference (Inbound/Outbound)"

  @CP-47284 @CP-47284-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Renewal Status CVCC is not displayed as a Contact Reason for CVCC CVCCPW and CVCCNavAsst Business Unit
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify for "CVCC CVCC-PW and CVCC-Nav/Asst" CoverVa business unit Contact Reason does not display "Renewal Status - CVCC"

  @CP-47284 @CP-47284-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Renewal Status CoverVa is displayed as a Contact Reason for CVCC CVCCPW and CVCCNavAsst Business Unit along with associated Contact Action values
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "CVCC CVCC-PW CVCC-Nav/Asst" Business Unit with Contact Reason "Renewal Status - CoverVA" with the following Action values
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Renewal Status                  |
      | System Issues - Read VaCMS Down Script   |

  @CP-47284 @CP-47284-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
  Scenario: Verify Renewal Status Non-CoverVA is displayed as a Contact Reason for CVCC CVCCPW and CVCCNavAsst Business Unit along with associated Contact Action values
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "CVCC CVCC-PW CVCC-Nav/Asst" Business Unit with Contact Reason "Renewal Status - Non-CoverVA" with the following Action values
      | Escalated to CPU                         |
      | Escalated to Locality                    |
      | Escalated to Supervisor                  |
      | No Action Taken - Unable to Authenticate |
      | Provided Renewal Status                  |
      | System Issues - Read VaCMS Down Script   |

  @CP-43443 @CP-43443-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
    # CP-43443(AC 1.0)
  Scenario Outline: “Provided Info for PHE Unwinding” as a Contact Action for New Application
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "<CR type>" contact record type
    Then I verify "CVCC" Business Unit with Contact Reason "New Application" with the following Action values
      | Provided Info for PHE Unwinding |
    Then I verify "CPU" Business Unit with Contact Reason "New Application" with the following Action values
      | Provided Info for PHE Unwinding |
    Then I verify "CVIU-DJJ CVIU-DOC CVIU-Jails and CVIU-Other" Business Unit with Contact Reason "New Application" with the following Action values
      | Provided Info for PHE Unwinding |
    Examples:
      | CR type    |
      | General    |
      | ThirdParty |

  @CP-43443 @CP-43443-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
    # CP-43443(AC 2.0)
  Scenario Outline: “Provided Info for PHE Unwinding” as a Contact Action for Renewal
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "<CR type>" contact record type
    Then I verify "CVCC" Business Unit with Contact Reason "Renewal" with the following Action values
      | Provided Info for PHE Unwinding |
    Then I verify "CPU" Business Unit with Contact Reason "Renewal" with the following Action values
      | Provided Info for PHE Unwinding |
    Then I verify "CVIU-DJJ CVIU-DOC CVIU-Jails and CVIU-Other" Business Unit with Contact Reason "Renewal" with the following Action values
      | Provided Info for PHE Unwinding |
    Examples:
      | CR type    |
      | General    |
      | ThirdParty |

  @CP-47390 @CP-47390-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
    # CP-47390(AC 1.0, AC 1.1, AC 2.0)
  Scenario: Verify contact reason PW Outbound with corresponding action values and save enables Application Fields for General Contact Record
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I verify "CPU" Business Unit with Contact Reason "PW Outbound" with the following Action values
      | Contact Successful                       |
      | Escalated to Supervisor                  |
      | No Action - Did Not Reach Consumer       |
      | No Action Taken - Unable to Authenticate |
      | Unsuccessful                             |
    And I verify Application ID field is displayed in Contact Details panel after saving PW Outbound with a corresponding contact action

  @CP-47390 @CP-47390-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
    # CP-47390(AC 1.0, AC 1.1, AC 2.0)
  Scenario: Verify contact reason PW Outbound with corresponding action values and save enables Application Fields for Third Party Contact Record
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "ThirdParty" contact record type
    Then I verify "CPU" Business Unit with Contact Reason "PW Outbound" with the following Action values
      | Contact Successful                       |
      | Escalated to Supervisor                  |
      | No Action - Did Not Reach Consumer       |
      | No Action Taken - Unable to Authenticate |
      | Unsuccessful                             |
    And I verify Application ID field is displayed in Contact Details panel after saving PW Outbound with a corresponding contact action

  @CP-47390 @CP-47390-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @sang
    # CP-47390(AC 1.0, AC 1.1)
  Scenario: Verify contact reason PW Outbound with corresponding contact action
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "Unidentified" contact record type
    Then I verify "CPU" Business Unit with Contact Reason "PW Outbound" with the following Action values
      | Contact Successful                       |
      | Escalated to Supervisor                  |
      | No Action - Did Not Reach Consumer       |
      | No Action Taken - Unable to Authenticate |
      | Unsuccessful                             |



