Feature: Smoke Test

  @CP-45716 @CP-45716-01 @ui-core @crm-regression @ui-core-smoke @araz
  Scenario Outline: Create Contact Record - Smoke testing
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi"
    And I link the contact to an existing Case or Consumer Profile
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I enter First Name as "fromCaseLoaderApi" and Last Name as "fromCaseLoaderApi" in manual contact record search page
    Examples:
      | contactType | contactChannelType |
      | Inbound     | Phone              |