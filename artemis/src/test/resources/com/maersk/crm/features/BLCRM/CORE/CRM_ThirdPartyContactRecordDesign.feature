Feature: Third Party Contact Record Design

  @CP-344 @CP-344-01 @ozgen @crm-regression @crm-smoke @ui-core
  Scenario: Verification of Third Party Details Header
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I verify that third party details header is displayed
    And I verify that third party details header is changed as "THIRD PARTY - CONSUMER IN CONTACT"


  @CP-344 @CP-344-02 @ozgen @crm-regression @ui-core
 Scenario Outline:Validation of Link icon when add a new consumer and link it to Contact
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for third party
    And I verify that after linking the consumer links section is added and has an icon consistent with other sections
    Examples:
      | First Name  | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Ellie       | Smith     | ABC Group         | Media         | English            |


  @CP-344 @CP-344-03 @ozgen @crm-regression @ui-core
  Scenario Outline:Verification linked Case or Consumer Profile is displayed in the new 'Links' section
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for third party
    Then I will verify that linked Case or Consumer Profile added to link section
    Examples:
      | First Name  | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Ellie       | Smith     | ABC Group         | Media         | English            |

@CP-344 @CP-344-04 @ozgen @crm-regression @ui-core
  Scenario Outline:Validation of 'Linked - Third Party Contact' section which is removed from the Third Party Contact Record
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for third party
    And I will verify that "Linked - Third Party Contact" section is not displayed on Third Party Contact Record
  Examples:
    | First Name  | Last Name | Organization Name | Consumer Type | Preferred Language |
    | Ellie       | Smith     | ABC Group         | Media         | English            |


  @CP-344 @CP-344-05 @ozgen @crm-regression @ui-core
  Scenario Outline:Verification of 'Third Party - Consumer Contacted About' section
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for third party
    And I will verify that "THIRD PARTY - CONSUMER CONTACTED ABOUT" section is displayed
    Examples:
      | First Name  | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Ellie       | Smith     | ABC Group         | Media         | English            |

  @CP-344 @CP-344-06 @ozgen @crm-regression @ui-core
  Scenario: Verification new header for case/Consumer search is added
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I verify that third party details header is displayed
    And I verify that there are three new Radio Buttons at the top of the head
    And I will verify that "CASE / CONSUMER SEARCH" section is added

    