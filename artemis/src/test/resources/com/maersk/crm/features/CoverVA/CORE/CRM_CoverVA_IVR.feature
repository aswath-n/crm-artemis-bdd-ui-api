Feature: CoverVA IVR Contact Record

  @CP-28532 @CP-28532-01 @CP-27449 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: VA: IVR authenticated contact record created - enhancement
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Contact Records IVR API for "<projectName>"
    And I can provide IVR contact record details "<consumerFName>", "<consumerLName>", "<consumerDOB>", "<consumerSSN>" "<consumerPhone>" and "<interactionID>"
    Then I can run IVR create contact records API
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I search for IVR created contact record with contactRecordID
    Then I verify phoneNumber, interactionID, consumerName, and rest of details for IVR contact record
    Examples:
      | projectName | consumerFName | consumerLName | consumerDOB | consumerSSN | consumerPhone | interactionID |
      | CoverVA     | Aika          | Begi          | 12021931    | 123123654   | random        |[blank]|

  @CP-27498 @CP-27497 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: VA: Prevent User From Editing IVR Self Service-Created Contact Record
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Contact Records IVR API for "<projectName>"
    And I can provide IVR contact record details "<consumerFName>", "<consumerLName>", "<consumerDOB>", "<consumerSSN>" "<consumerPhone>" and "<interactionID>"
    Then I can run IVR create contact records API
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I search for IVR created contact record with contactRecordID
    And I verify Edit Button is not displayed
    Examples:
      | projectName | consumerFName | consumerLName | consumerDOB | consumerSSN | consumerPhone | interactionID |
      | CoverVA     | Aika          | Begi          | 12021931    | 123123654   | random        |[blank]|


  @CP-27165 @CP-27165-1.0 @CP-27165-2.0 @CP-27165-3.0 @CP-27165-4.0 @araz @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario: VA: Voice Portal - Update Consumer
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated Contact Records IVR API for "CoverVA"
    And I provide IVR contact record details
      | 7325123512334    |
      | 2325123512355    |
      | 1234 Elite Str   |
      | Apt 4            |
      | Vancouver        |
      | British Columbia |
      | Physical         |
      | 80016            |
    Then I can run IVR create contact records API
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    Then I minimize Genesys popup if populates
    When I navigate to manual contact record search page
    And I search for IVR created contact record with contactRecordID
    Then I verify VaCMS, MMIS and address for IVR contact record
    And I navigate to manual contact record search page
    Then  I provide IVR contact record details
      | 33251235123529   |
      | 53251235123524   |
      | 4400 S Monaco St |
      | Apt 115          |
      | Richmond         |
      | British Columbia |
      | Physical         |
      | 98008            |
    And I can run IVR create contact records API
    And I search for IVR created contact record with contactRecordID
    Then I verify VaCMS, MMIS and address for IVR contact record

  @CP-26810 @CP-26810-01 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: VA: Voice Portal - CP Consumer Match logic found no match - Create Consumer
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Contact Records IVR API for "<projectName>"
    And I can provide random IVR contact record details for new consumer creation "<consumerFName>", "<consumerLName>", "<consumerDOB>", "<consumerSSN>" "<consumerPhone>" and "<externalID>"
    Then I can run IVR create contact records API
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I search for IVR created contact record with contactRecordID
    Then I verify consumerFName, consumerLName, consumerDOB, consumerSSN and external ID for newly created consumer
    Examples:
      | projectName | consumerFName | consumerLName | consumerDOB | consumerSSN | consumerPhone | externalID    |
      | CoverVA     | random         | random       | 12021931      | random      | random        | random        |


  @CP-27512 @CP-27512-01 @aikanysh @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: VA: Voice Portal - Apply IVR Self Service icon to Contact Search and Contact History
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Contact Records IVR API for "<projectName>"
    And I can provide IVR contact record details "<consumerFName>", "<consumerLName>", "<consumerDOB>", "<consumerSSN>" "<consumerPhone>" and "<interactionID>"
    Then I can run IVR create contact records API
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I search for IVr created contact recod and verify correct icon is displayed
    Examples:
      | projectName | consumerFName | consumerLName | consumerDOB | consumerSSN | consumerPhone | interactionID |
      | CoverVA     | Aika          | Begi          | 12021931    | 123123654   | random        |[blank]|

  @CP-33902 @CP-33902-01 @araz @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: VA: IVR authenticated Created By User Info Displayed
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated Contact Records IVR API for "<projectName>"
    And I can provide IVR contact record details "<consumerFName>", "<consumerLName>", "<consumerDOB>", "<consumerSSN>" "<consumerPhone>" and "<interactionID>"
    Then I can run IVR create contact records API
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I search for IVR created contact record with contactRecordID
    Then I verify contact record information as created by userID and userName
    Examples:
      | projectName | consumerFName | consumerLName | consumerDOB | consumerSSN | consumerPhone | interactionID |
      | CoverVA     | Aika          | Begi          | 12021931    | 123123654   | random        |[blank]|

  @CP-33902 @CP-33902-02 @araz @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: VA: IVR authenticated Received By User Info Displayed
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated Contact Records IVR API for "<projectName>"
    And I can provide IVR contact record details "<consumerFName>", "<consumerLName>", "<consumerDOB>", "<consumerSSN>" "<consumerPhone>" and "<interactionID>"
    Then I can run IVR create contact records API
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I search for IVR created contact record with contactRecordID
    Then I verify contact record information as received by userID and userName
    Examples:
      | projectName | consumerFName | consumerLName | consumerDOB | consumerSSN | consumerPhone | interactionID |
      | CoverVA     | Aika          | Begi          | 12021931    | 123123654   | random        |[blank]|

  @CP-33902 @CP-33902-03 @araz @ui-core-cover-va @crm-regression @CoverVA-UI-Regression
  Scenario Outline: VA: IVR authenticated: Verify that Received By User Info not same with Created By User for IVR call
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated Contact Records IVR API for "<projectName>"
    And I can provide IVR contact record details "<consumerFName>", "<consumerLName>", "<consumerDOB>", "<consumerSSN>" "<consumerPhone>" and "<interactionID>"
    Then I can run IVR create contact records API
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I navigate to manual contact record search page
    And I search for IVR created contact record with contactRecordID
    Then I verify created by info not same with received by for IVR call
    Examples:
      | projectName | consumerFName | consumerLName | consumerDOB | consumerSSN | consumerPhone | interactionID |
      | CoverVA     | Aika          | Begi          | 12021931    | 123123654   | random        |[blank]|

