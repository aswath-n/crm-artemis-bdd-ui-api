Feature: Contact Record Search Part 3

  @CP-2409 @CP-2409-01 @aikanysh @ui-core @crm-regression
  Scenario: Verify manual contact record search | Contact Record ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone | phone:: |
      | consumerEmail | email:: |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then I will verify search with Phone number
    And I verify appropriate fields headers after advanced search are displayed

  @CP-2409 @CP-2409-02 @aikanysh  @ui-core @crm-regression
  Scenario: Verify manual contact record search | Date
    Given I logged into CRM
    When I navigate to manual contact record search page
    When I click on advance search icon
    Then Select by previous date on Manual Contact Record Search page
    And I verify search result is in access as expected

  @CP-2409 @CP-2409-03 @aikanysh @ui-core @crm-regression @ui-core-smoke
  Scenario Outline: Verify manual contact record search | First Name
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by "<criteria>"
    And I verify appropriate fields headers after advanced search are displayed
    Examples:
      | contactType | contactChannelType | criteria  |
      | Inbound     | Phone              | firstName |

  @CP-2409 @CP-2409-04 @aikanysh @ui-core @crm-regression @ui-core-smoke
  Scenario Outline: Verify manual contact record search | Last Name
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by "<criteria>"
    And I verify appropriate fields headers after advanced search are displayed
    Examples:
      | contactType | contactChannelType | criteria |
      | Inbound     | Phone              | lastName |

  @CP-2409 @CP-2409-05 @CP-2409-06 @ui-core @aikanysh @crm-regression
  Scenario Outline: Verify manual contact record search | Internal CASE ID
    Given I logged into CRM
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by "<criteria>"
    And I verify search result is in access as expected
    Examples:
      | contactType | contactChannelType | criteria       |
      | Inbound     | Phone              | internalCaseId |

  @CP-2409 @CP-2409-08 @aikanysh @ui-core @crm-regression
  Scenario Outline: Verify manual contact record search | Internal Consumer Id
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by "<criteria>"
    And I verify appropriate fields headers after advanced search are displayed
    Examples:
      | contactType | contactChannelType | criteria           |
      | Inbound     | Phone              | internalConsumerId |

  @CP-2409 @CP-2409-09 @aikanysh @ui-core @crm-regression
  Scenario Outline: Verify manual contact record search | Contact Type
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by "<criteria>"
    And I verify search result is in access as expected
    Examples:
      | contactType | contactChannelType | criteria    |
      | Inbound     | Phone              | contactType |

  @CP-2409 @CP-2409-10 @aikanysh @ui-core @crm-regression
  Scenario: Verify manual contact record search | Type (Third Party)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I send API CALL for create CONTACT Record after creating new Consumer with data
      | consumerPhone     | phone::     |
      | consumerEmail     | email::     |
      | contactType       | Third Party |
      | consumerFirstName | name::      |
      | consumerLastName  | name::      |
      | organizationName  | name::      |
      | consumerType      | Media       |
    Given I logged into CRM
    Then I navigate to Contact Record Search Page
    Then I will verify search with Phone number
    And I verify appropriate fields headers after advanced search are displayed

  @CP-2409 @CP-2409-12 @aikanysh @ui-core @crm-regression
  Scenario Outline: Verify manual contact record search | Dispostion
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by "<criteria>"
    And I verify search result is in access as expected
    Examples:
      | contactType | contactChannelType | criteria    |
      | Inbound     | Phone              | disposition |

    #Failing due to defect : CP-9561
  @CP-670 @CP-670-03 @paramita @regression @ui-core @crm @crm-regression
  Scenario: Verify warning message to Display when User attempts to navigate Create Task screen with unsaved data entry - EDIT functionality
    Given I logged into CRM
    When I navigate to manual contact record search page
    And I search for contact record by value " ", "Unidentified Contact", "Inbound"
    And I expand first contact record in search results
    And I click on edit button on contact details tab
    And I select "Complete" on contact dispositions dropdown
    When I click to navigate "General" task page
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Manual Contact Record Search |
    When I click to navigate "General" task page
    And I click on continue button on warning message
    Then I should return back to Create Task screen

  @CP-1436 @CP-1436-01 @aikanysh @ui-core @crm-regression
  Scenario Outline: Verify advanced search by | Third Party First & Last Name
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I add New Consumer to the record for third party
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by Third Party First Name & Last Name under Advanced Search
    And I verify appropriate fields headers after advanced search are displayed
    Examples:
      | contactType | contactChannelType | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Inbound     | Phone              | random     | random    | random            | Media         | English            |

  @CP-22281 @CO-22281-01 @chopa @ui-cc @crm-regression
  Scenario: Allow 2-Character Names in Consumer Searches
    Given I logged into CRM and click on initiate contact
    When I pass 2 characters in "First name" and "Last Name" fields to verify search execution
    When I pass 1 character in "First name" and "Last Name" fields to verify error message
    When I navigate to manual contact record search page
    When I pass 2 characters in "First name" and "Last Name" fields to verify search execution
    When I pass 1 character in "First name" and "Last Name" fields to verify error message
    When I navigate to case and consumer search page
    When I pass 2 characters in "First name" and "Last Name" fields to verify search execution
    When I pass 1 character in "First name" and "Last Name" fields to verify error message
    When I navigate to "Task Search" page
    When I click on advance search icon
    When I pass 2 characters in "First name" and "Last Name" fields to verify search execution
    When I pass 1 character in "First name" and "Last Name" fields to verify error message

  @CP-22281 @CO-22281-02 @chopa @ui-cc-cover-va @crm-regression
  Scenario: Allow 2-Character Names in Consumer Searches in CoverVA
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    When I pass 2 characters in "First name" and "Last Name" fields to verify search execution
    When I pass 1 character in "First name" and "Last Name" fields to verify error message
    When I navigate to manual contact record search page
    When I pass 2 characters in "First name" and "Last Name" fields to verify search execution
    When I pass 1 character in "First name" and "Last Name" fields to verify error message
    When I navigate to case and consumer search page
    When I pass 2 characters in "First name" and "Last Name" fields to verify search execution
    When I pass 1 character in "First name" and "Last Name" fields to verify error message
    When I navigate to "Task Search" page
    And I click on Call Managment window
    When I click on advance search icon
    When I pass 2 characters in "First name" and "Last Name" fields to verify search execution
    When I pass 1 character in "First name" and "Last Name" fields to verify error message