Feature: Contact Record Search Part 5

  @CP-1436 @CP-1436-1 @sanglee @crm-regression @ui-core
  Scenario Outline: Search Contact Record by Third Party First & Last Name
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record type radio button
    And I add New Consumer to the record for third party
    And I choose a contact reason from reason dropdown list
    And I choose a contact action from action dropdown list
    And I click save button for reason and action
    And I Enter Thirty character Third Party  "<First Name>", "<Last Name>" with mandatory "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    And I enter contact details for inbound contact type with call queue "<callQueue>" contact channel type "<contactChannelType>" contact channel "<contactChannel>" program type "<programType>" and contact disposition "<contactDispostion>"
    And I end the current call
    And I click on save button to wrap up contact record
    When I navigate to Contact Record Search Page
    Then I search for the Third Party "<First Name>" and "<Last Name>" in the advance search parameter
    And I should be able to view Third party "<First Name>" and "<Last Name>" as part of in the expanded view
    And I should be able view all expanded third party parameters
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language | callQueue   | contactChannelType | contactChannel | programType | contactDispostion |
      | random30   | random30  | ABC Group         | Media         | English            | Eligibility | Phone              | 987-415-2630   | Program A   | Complete          |

  @CP-1436 @CP-1436-2 @sanglee @crm-regression @ui-core
  Scenario Outline: Search Contact Record by Third party name
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record type radio button
    And I add New Consumer to the record for third party
    And I choose a contact reason from reason dropdown list
    And I choose a contact action from action dropdown list
    And I click save button for reason and action
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    And I enter contact details for inbound contact type with call queue "<callQueue>" contact channel type "<contactChannelType>" contact channel "<contactChannel>" program type "<programType>" and contact disposition "<contactDispostion>"
    And I end the current call
    And I click on save button to wrap up contact record
    When I navigate to Contact Record Search Page
    Then I search for the Third Party "<Organization Name>" in the advance search parameter
    And I should be able to view Third Party "<Organization Name>" as part of in the expanded view
    And I should be able view all expanded third party parameters
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language | callQueue   | contactChannelType | contactChannel | programType | contactDispostion |
      | Emily      | Jones     | Crocus Group      | Media         | English            | Eligibility | Phone              | 987-415-2630   | Program A   | Complete          |
