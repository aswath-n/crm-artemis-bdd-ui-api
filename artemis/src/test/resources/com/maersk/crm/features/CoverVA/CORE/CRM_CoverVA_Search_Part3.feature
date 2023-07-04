Feature: CoverVA: Contact Record Search Part 3


  @CP-22331 @CP-22331-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh
  Scenario: VA: Add Business Unit Field to Contact Search & Search Result
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I navigate to Contact Record search
    Then Clicking Advanced Search on Contact Record search in CoverVa
    And I verify "Business Unit" dropdown has following options
      | CVCC |
      | CVIU |

  @CP-27302 @CP-27302-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @aikanysh
  Scenario Outline: VA: Add Business Unit to Search Results
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I searched customer have First Name as "Kamil" and Last Name as "Shikh"
    And I link the contact to an existing Case or Consumer Profile
    Then I click on Call Managment button
    Then I save the contact without Application ID with BusinessUnit Drop Down "<busninesdrpDwn>"
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature
    And I verify "BUSINESS UNIT" column exists for contact record search result
    Examples:
      | busninesdrpDwn     |
      |    CVCC            |

  @CP-25848 @CP-25848-01 @CP-25848-02 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: CoverVA: Permit Display of 100 Records
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I search contact "John" record by name and I will see warning message results exceed 100 records
    Then I verify 20 records listed in each page of results by default
    And I verify the default 20 in dropdown
    And I verify the number 100 of search results


  @CP-25848 @CP-25848-03 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: CoverVA: Update search result set as 20 per page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I search contact "John" record by name and I will see warning message results exceed 100 records
    And I verify the number 20 of search results for each page when you navigate back
    Then I verify all display numbers in dropdown

  @CP-32958 @CP-32958-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Replace Contact Action Enum - Active Contact -CVCC
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify contact action dropdown values
      | Escalated to Supervisor                |
      | Extended Pend Letter                   |
      | Generated Requested Form(s)            |
      | LDSS Communication Form                |
      | NOA Translation Request submitted      |
      | Other Translation Request submitted    |
      | Provided Info for PHE Unwinding        |
      | Re-Generated NOA                       |
      | Re-Generated VCL                       |
      | Referred to CoverVa.org for Self Print |
      | Sent Certificate of Coverage           |
      | VCL Translation Request submitted      |
    Examples:
      | business unit | contact reason |
      | CVCC          | Correspondence |


  @CP-32958 @CP-32958-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Replace Contact Action Enum - Active Contact -CVIU
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify contact action dropdown values
      | CVIU Communication Form                |
      | Escalated to Supervisor                |
      | Extended Pend Letter                   |
      | Generated Requested Form(s)            |
      | LDSS Communication Form                |
      | Re-Generated NOA                       |
      | Re-Generated VCL                       |
      | Referred to CoverVA.org for Self Print |

    Examples:
      | business unit | contact reason        |
      | CVIU - DJJ    | Correspondence - CVIU |
      | CVIU - DOC    | Correspondence - CVIU |
      | CVIU - Jails  | Correspondence - CVIU |
      | CVIU - Other  | Correspondence - CVIU |

  @CP-32958 @CP-32958-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Replace Contact Action Enum -CVCC - Edit Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact and click "Yes" to electronic signature for COVER-VA
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify contact action dropdown values
      | Escalated to Supervisor                |
      | Extended Pend Letter                   |
      | Generated Requested Form(s)            |
      | LDSS Communication Form                |
      | NOA Translation Request submitted      |
      | Other Translation Request submitted    |
      | Provided Info for PHE Unwinding        |
      | Re-Generated NOA                       |
      | Re-Generated VCL                       |
      | Referred to CoverVa.org for Self Print |
      | Sent Certificate of Coverage           |
      | VCL Translation Request submitted      |
    Examples:
      | business unit | contact reason |
      | CVCC          | Correspondence |

  @CP-32958 @CP-32958-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline: Replace Contact Action Enum - CVIU - Edit Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched customer have First Name as "Aika" and Last Name as "Begi"
    And I link the contact to an existing Case or Consumer Profile
    When I save the contact record with business unit "CVIU - DJJ"
    When I navigate to manual contact record search page
    And I searching for contact with electronic signature and choosing first record
    When I click on edit icon the Contact Details page
    When I select business unit "<business unit>" and contact reason "<contact reason>"
    And I verify contact action dropdown values
      | CVIU Communication Form                |
      | Escalated to Supervisor                |
      | Extended Pend Letter                   |
      | Generated Requested Form(s)            |
      | LDSS Communication Form                |
      | Re-Generated NOA                       |
      | Re-Generated VCL                       |
      | Referred to CoverVA.org for Self Print |

    Examples:
      | business unit | contact reason        |
      | CVIU - DJJ    | Correspondence - CVIU |
