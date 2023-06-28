Feature: Contact Record Search Part 6


  @CP-979 @CP-979-01 @kamil @crm-regression @ui-core
  Scenario: Verify I can find a specific Contact Record with Contract Record ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for init CONTACT with Contact Reasons
      | contactReasonActions[0].contactRecordActions[0].contactRecordActionType | Escalated-3                 |
      | contactReasonActions[0].contactRecordReasonType                         | Complaint - Account Access  |
      | contactReasonActions[0].notes                                           | Created from API automation |
    Given I logged into CRM
    When I click contact search tab for Contact Record
    And Verify Search with CONTACT ID result from Contact Record search

  @CP-979 @CP-979-02 @kamil @crm-regression @ui-core
  Scenario Outline: Verify I can find a specific Contact Record with Created On date
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    Then I search with "<CreatedOn>" than CreatedOn date
    Examples:
      | CreatedOn |
      | =         |
      | >         |
      | <         |
      | >=        |
      | <=        |


  @CP-979 @CP-979-03 @kamil @crm-regression @ui-core
  Scenario: Verify I can find a specific Contact Record with Consumer First Name
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    Then I search with First name
    And Verify result  with search First Name


  @CP-979 @CP-979-04 @kamil @crm-regression @ui-core
  Scenario: Verify I can find a specific Contact Record with Consumer Last Name
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    Then I search with Last Name
    And Verify result  with search Last Name


  @CP-979 @CP-979-05 @kamil @crm-regression @ui-core
  Scenario Outline: Verify I can find a specific Contact Record with Case ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].identificationNumberType | <ConsumerIdType> |
      | consumerIdentificationNumber[0].externalConsumerId       | npi::            |
    Given I logged into CRM
    Then I search with FirstName and ConsumerType "<ConsumerIdType>"
    And Verify result with search CaseId and ConsumerType
    Examples:
      | ConsumerIdType |
      | Medicaid       |
      | CHIP           |


  @CP-979 @CP-979-06 @kamil @crm-regression @ui-core
  Scenario: Verify CaseID length
    Given I logged into CRM and click on initiate contact
    Then I search with CaseId length of up to 30 characters
    And Verify length of CaseId not accepted


  @CP-979 @CP-979-07 @kamil @crm-regression @ui-core
  Scenario Outline: Verify Consumer Id types are displayed
    Given I logged into CRM and click on initiate contact
    Then I navigate to Contact Record Search Page and Verify that "<ConsumerIdType>" are available
    Examples:
      | ConsumerIdType |
      | Medicaid       |
      | CHIP           |
      | Internal       |


  @CP-979 @CP-979-08 @kamil @crm-regression @ui-core
  Scenario: Verify ConsumerId field length
    Given I logged into CRM and click on initiate contact
    Then I search with ConsumerId field length of up to 30 characters
    And Verify length of ConsumerId not accepted


  @CP-979 @CP-979-09 @kamil @crm-regression @ui-core
  Scenario Outline: Search with Contact Type
    Given I logged into CRM and click on initiate contact
    Then I navigate to Contact Record Search Page
    And Verify "<ContactType>" are displayed
    Examples:
      | ContactType |
      | Inbound     |
      | Outbound    |


  @CP-979 @CP-979-10 @kamil @crm-regression @ui-core
  Scenario Outline:VerContact Record Type (Drop-down: General, Third Party or Unidentified)
    Given I logged into CRM and click on initiate contact
    And I navigate to "<tabs>"
    Then will verify Contact Record types are displayed
      | Inbound  |
      | Outbound |
    Examples:
      | tabs        |
      | General     |
      | Third Party |
      | Unidenfied  |


  @CP-979 @CP-979-11 @kamil @crm-regression @ui-core
  Scenario Outline: Verify search by Contact Channel
    Given I logged into CRM and click on initiate contact
    And Verify options "<types>" for Contact Channel
    Examples:
      | types    |
      | Email    |
      | Phone    |
      | SMS Text |
      | Web Chat |


  @CP-979 @CP-979-12 @kamil @crm-regression @ui-core
  Scenario: Verify search by DISPOSITION options
    Given I logged into CRM and click on initiate contact
    Then I navigate to Contact Record Search Page
    And  Verify I will see the options for "DISPOSITION" are displayed


  @CP-979 @CP-979-13 @kamil @crm-regression @ui-core
  Scenario Outline: Verify search by Inbound Call Queue
    Given I logged into CRM and click on initiate contact
    And I navigate to "<tabs>"
    Then Will verify INBOUND CALL QUEUE types are displayed
      | Enrollment                |
      | Eligibility               |
      | General Program Questions |
    Examples:
      | tabs        |
      | General     |
      | Third Party |
      | Unidenfied  |


  @CP-979 @CP-979-14 @kamil @crm-regression @ui-core
  Scenario: Verify search by Call Campaign
    Given I logged into CRM and click on initiate contact
    Then I navigate to Contact Record Search Page
    Then I will verify Call Campaign


  @CP-979 @CP-979-15 @kamil @crm-regression @ui-core
  Scenario: Verify search by Outcome of Contact
    Given I logged into CRM and click on initiate contact
    Then I navigate to Contact Record Search Page
    Then I will verify Outcome of Contact


  @CP-979 @CP-979-16 @kamil @crm-regression @ui-core
  Scenario Outline: Verify search Created By
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for create CONTACT Record with new Consumer with Data
      | consumerIdentificationNumber[0].identificationNumberType | <ConsumerIdType> |
      | consumerIdentificationNumber[0].externalConsumerId       | npi::            |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then Verify search with Created By
    Examples:
      | ConsumerIdType |
      | MEDICAID       |


  @CP-979 @CP-979-17 @kamil @crm-regression @ui-core
  Scenario: Verify search with UserId
    Given I logged into CRM and click on initiate contact
    Then I navigate to Contact Record Search Page
    Then I will verify search with UserId


  @CP-979 @CP-979-18 @kamil @crm-regression @ui-core
  Scenario Outline: Verify search with Phone number
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].identificationNumberType | <ConsumerIdType> |
      | consumerIdentificationNumber[0].externalConsumerId       | npi::            |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone | phone:: |
      | consumerEmail | email:: |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then I will verify search with Phone number
    Examples:
      | ConsumerIdType |
      | MEDICAID       |


  @CP-979 @CP-979-19 @kamil @crm-regression @ui-core
  Scenario Outline: Verify search with Email Address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].identificationNumberType | <ConsumerIdType> |
      | consumerIdentificationNumber[0].externalConsumerId       | npi::            |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone | phone:: |
      | consumerEmail | email:: |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then I will verify search with Email Address
    Examples:
      | ConsumerIdType |
      | MEDICAID       |

  @CP-979 @CP-979-20 @kamil @crm-regression @ui-core
  Scenario: Verify length of Third Party Organization
    Given I logged into CRM and click on initiate contact
    Then I navigate to Contact Record Search Page
    Then I search with Third Party Organization length of up to 50 characters
