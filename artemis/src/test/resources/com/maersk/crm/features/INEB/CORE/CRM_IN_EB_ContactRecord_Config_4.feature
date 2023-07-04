Feature: IN-EB Contact Record Configurations Part Four

  @CP-23482 @CP-23482-01 @khazar @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario Outline: IN-EB: Program dependency on reason/action
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I navigate "<ContactType>" contact type
    And I select "All" program types
    And I verify Contact Reason and associated Contact Action
      | Complaint                                      |
      | Enrollment                                     |
      | HCC Outbound                                   |
      | HIP 2.0                                        |
      | Inquiry AE/OE                                  |
      | Inquiry Application/Eligibility                |
      | Inquiry Billing                                |
      | Inquiry Covered Benefits                       |
      | Inquiry Disenrollment                          |
      | Inquiry Guardianship/Authorized Representative |
      | Inquiry General Information                    |
      | Inquiry Health Plan Contact Information        |
      | Inquiry PMP                                    |
      | Inquiry Replacement Card                       |
      | Just Cause                                     |
      | Member Information Update                      |
      | Other                                          |
      | Right Choices Program                          |
    Examples:
      | ContactType  |
      | General      |
      | Third Party  |
      | Unidentified |

  @CP-23482 @CP-23482-02 @khazar @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario Outline: IN-EB: Program dependency on reason/action for multiple program selection
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I navigate "<ContactType>" contact type
    And I select "HIP,HCC" program types
    And I verify Contact Reason and associated Contact Action dependency with program selection
    And I select "HHW,Traditional Medicaid" program types
    And I verify Contact Reason and associated Contact Action dependency with program selection
    Examples:
      | ContactType  |
      | General      |
      | Third Party  |
      | Unidentified |

  @CP-23482 @CP-23482-03 @khazar @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario Outline: IN-EB: Suppress reason if not related to program selection
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I navigate "<ContactType>" contact type
    And I select "HIP,HHW" program types
    And I verify Contact Reason suppress depending on program selection
    And I select "HHW,HCC" program types
    And I verify Contact Reason suppress depending on program selection
    And I select "HIP,Traditional Medicaid" program types
    And I verify Contact Reason suppress depending on program selection
    Examples:
      | ContactType  |
      | General      |
      | Third Party  |
      | Unidentified |

  @CP-23482 @CP-23482-04 @khazar @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario Outline: IN-EB: Validate error message when trying to select reason without program
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I navigate "<ContactType>" contact type
    When I try to select any reason from dropdown
    Then I verify error message for action selection reason without program
    Examples:
      | ContactType  |
      | General      |
      | Third Party  |
      | Unidentified |

  @CP-23482 @CP-23482-05 @khazar @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario Outline: IN-EB: Unsaved reason/action clears when program selection changes
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I navigate "<ContactType>" contact type
    And I select "HCC,HHW" program types
    When I select reason and action
    And I select "HIP,Traditional Medicaid" program types
    Then I verify reason and action dropdowns are cleared
    Examples:
      | ContactType  |
      | General      |
      | Third Party  |
      | Unidentified |

  @CP-25003 @CP-25003-01 @khazar @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario:  Configure Systematic Program Selection Based On Inbound Call Queue - Active Contact
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I validate systematic program selection based on inbound call queue
      | UAT_CARE_ENG_Q | HCC |
      | UAT_CARE_SPA_Q | HCC |
      | UAT_HIP_ENG_Q  | HIP |
      | UAT_HIP_SPA_Q  | HIP |
      | UAT_HOOS_ENG_Q | HHW |
      | UAT_HOOS_SPA_Q | HHW |
      | UAT_INS_ENG_Q  | HHW |
      | UAT_INS_SPA_Q  | HHW |
      | UAT_KIDS       | HHW |

  @CP-25003 @CP-25003-02 @khazar @ui-core-in-eb @crm-regression @IN-EB-UI-Regression
  Scenario: Configure Systematic Program Selection Based On Inbound Call Queue - Edit Page
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    Then I navigate to Contact Record Search Page
    And I search for contact record by contact id "2242"
    When I click on first Contact Record ID on Contact Record
    When I click on edit button on contact details tab
    Then I validate systematic program selection based on inbound call queue
      | UAT_CARE_ENG_Q | HCC |
      | UAT_CARE_SPA_Q | HCC |
      | UAT_HIP_ENG_Q  | HIP |
      | UAT_HIP_SPA_Q  | HIP |
      | UAT_HOOS_ENG_Q | HHW |
      | UAT_HOOS_SPA_Q | HHW |
      | UAT_INS_ENG_Q  | HHW |
      | UAT_INS_SPA_Q  | HHW |
      | UAT_KIDS       | HHW |

  @CP-23470 @CP-23470-01 @CP-37099-01 @CP-37099-02 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @khazar
  Scenario Outline: IN-EB: Default Action Values for Reasons
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I navigate "<ContactType>" contact type
    And I select "All" program types
    And I verify Contact Reason and default Contact Action
      | Complaint                                      |
      | Enrollment                                     |
      | HCC Outbound                                   |
      | HIP 2.0                                        |
      | Inquiry AE/OE                                  |
      | Inquiry Application/Eligibility                |
      | Inquiry Billing                                |
      | Inquiry Covered Benefits                       |
      | Inquiry Disenrollment                          |
      | Inquiry Guardianship/Authorized Representative |
      | Inquiry General Information                    |
      | Inquiry Health Plan Contact Information        |
      | Inquiry PMP                                    |
      | Inquiry Replacement Card                       |
      | Just Cause                                     |
      | Other                                          |
      | Member Information Update                      |
      | Right Choices Program                          |
      | Transfer                                       |
    Examples:
      | ContactType  |
      | General      |
      | Third Party  |
      | Unidentified |

  @CP-37099 @CP-37099-03 @ui-core-in-eb @crm-regression @IN-EB-UI-Regression @khazar
  Scenario Outline: IN-EB: Default Action Values that Cannot Unselect
    Given I logged into CRM with Superuser account and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I navigate "<ContactType>" contact type
    And I select "All" program types
    And I verify cannot unselect some default Contact Action
      | Enrollment                                     |
      | Inquiry AE/OE                                  |
      | Inquiry Guardianship/Authorized Representative |
      | Inquiry General Information                    |
      | Inquiry Health Plan Contact Information        |
      | Right Choices Program                          |
    Examples:
      | ContactType  |
      | General      |
      | Third Party  |
      | Unidentified |