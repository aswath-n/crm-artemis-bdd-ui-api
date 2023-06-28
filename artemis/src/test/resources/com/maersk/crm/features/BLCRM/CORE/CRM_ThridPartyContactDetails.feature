Feature: Third Party Contact Record Details

  @CP-147 @CP-147-01 @asad @crm-regression @ui-core
  Scenario Outline: General Viewing
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I add New Consumer to the record for third party
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by Third Party First Name & Last Name under Advanced Search
    And I click on first Contact Record ID on Contact Record
    Then I verify it will open in read-only view
    Examples:
      | contactType | contactChannelType | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Inbound     | Phone              | random     | random    | random            | Media         | English            |

  @CP-147 @CP-147-02 @asad @crm-regression @ui-core
  Scenario Outline: Viewing Inbound Third Party Contact Record
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I add New Consumer to the record for third party
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by Third Party First Name & Last Name under Advanced Search
    And I click on first Contact Record ID on Contact Record
    Then I am able to view the completed "Inbound" third party Contact Record
    Examples:
      | contactType | contactChannelType | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Inbound     | Phone              | random     | random    | random            | Agency        | English            |

  @CP-147 @CP-147-03 @asad @crm-regression @ui-core
  Scenario Outline: Viewing Outbound Third Party Contact Record
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I add New Consumer to the record for third party
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by Third Party First Name & Last Name under Advanced Search
    And I click on first Contact Record ID on Contact Record
    Then I am able to view the completed "Outbound" third party Contact Record
    Examples:
      | contactType | contactChannelType | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Outbound    | Phone              | random     | random    | random            | Agency        | English            |
