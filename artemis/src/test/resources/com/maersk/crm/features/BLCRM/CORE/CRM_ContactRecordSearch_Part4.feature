Feature: Contact Record Search Part 4

  @CP-133 @CP-133-01 @vinuta @ui-core @crm-regression
  Scenario: View read-only details of Unidentified Contact Record from Manual Contact Record Search
    Given I logged into the CRM Application
    When I navigate to manual contact record search page
    And I search for contact record by value "200007", "Unidentified Contact", "Inbound"
    And I expand first contact record in search results
    Then I see a read-only view of contact record details

  @CP-133 @CP-133-02 @vidya #@crm-regression
  Scenario: View the completed Unidentified Contact Record with the appropriated fields completed of Unidentified Contact Record from Manual Contact Record Search
    Given I logged into the CRM Application
    When I navigate to manual contact record search page
    And I search for contact record by value "20671", "Unidentified Contact", "Inbound"
    And I expand first contact record in search results
    Then I see a read-only view of contact record details
    And I verify appropriated fields headers and there values are displayed

  @CP-139 @CP-139-01 @vidya #@crm-regression
  Scenario Outline: Verification of Viewing Outbound Contact Record
    Given I logged into the CRM Application
    When I navigate to manual contact record search page
    And I search for contact record by id "<contactId>", "<contactType>"
    Then I verify the contact icon displayed is "<Icon>"
    And I expand first contact record in search results
    And I verify icon displayed in contact details tab is "<icon>"
    And I verify contact type displayed in contact details page is Outbound
    Examples:
      | Icon                 | contactId | contactType | icon                 |
      | Manual User Captured |[blank]| Outbound    | Manual User Captured |
      | System Captured      | 19080     | Outbound    | System Captured      |


    #Verify warning is Display when User attempts to return to Active Contact and has unsaved data entry
  @CP-689 @CP-689-01 @vidya  @ui-core @crm-regression
  Scenario: Verify warning is Display
    Given I logged into CRM and click on initiate contact
    When I navigate to manual contact record search page
    And I search for contact record by value "200007", "Unidentified Contact", "Inbound"
    And I expand first contact record in search results
    And I click on edit button on contact details tab
    And I select "Complete" on contact dispositions dropdown
    And I click on Active Contact widget
    Then I verify warning popup is displayed with message
    And I click on cancel button on warning message
    Then Verify should I remains on screen and the information add or update captured will not be cleared
      | Manual Contact Record Search |
    And I click on Active Contact widget
    And I click on continue button on warning message
    Then Verify should I return back to Active Contact screen

  @CP-2622 @CP-2622-01 @aikanysh @ui-core @crm-regression @CP-2409-11 @CP-2409-18
  Scenario Outline: Verify advanced search by | PhoneNumber and Channel
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by PhoneNumber under Advanced Search
    And I verify appropriate fields headers after advanced search are displayed
    Examples:
      | contactType | contactChannelType |
      | Inbound     | Phone              |

  @CP-30446 @CP-2622 @CP-2622-02 @aikanysh  @ui-core @crm-regression @crm-smoke @CP-2409-17 @45670 @45670-02
  Scenario: Verify advanced search by | Employee ID
    Given I logged into CRM and click on initiate contact
    When I navigate to manual contact record search page
    When I click on advance search icon
    Then I search by EmployeeID "281623" on manual contact record search page
    And I verify appropriate fields headers after advanced search are displayed


  @CP-2622 @CP-2622-03 @aikanysh @ui-core @crm-regression @CP-2409-11  @CP-2409-19
  Scenario Outline: Verify advanced search by | Email Address
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by EmailAddress under Advanced Search
    And I verify appropriate fields headers after advanced search are displayed
    Examples:
      | contactType | contactChannelType |
      | Inbound     | Email              |

# 	CP-23650
  @CP-2622 @CP-2622-04 @aikanysh @ui-core @crm-regression  @CP-2409-16 @CP-45670 @CP-45670-01
  Scenario Outline: Verify advanced search by | Created By
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by CreatedBy under Advanced Search
    And I verify appropriate fields headers after advanced search are displayed
    Examples:
      | contactType | contactChannelType |
      | Inbound     | Phone              |

  @CP-2622 @CP-2622-05 @aikanysh @ui-core @crm-regression  @CP-2409-13
  Scenario Outline: Verify advanced search by | Inbound Call queue
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact with "<inboundCallQueue>" option
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by Inbound Call Queue Option under Advanced Search
    And I verify search result is in access as expected
    Examples:
      | inboundCallQueue |
      | random           |

  @CP-2622 @CP-2622-06 @aikanysh @ui-core @crm-regression  @CP-2409-14
  Scenario: Verify advanced search by | Call Campaign
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

  @CP-2622 @CP-2622-07 @aikanysh @ui-core @crm-regression  @CP-2409-15
  Scenario Outline: Verify advanced search by | Outcome of Call
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I save the contact with  contactType as "<contactType>" and "<callCampaign>" option and "<outcomeOfContact>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by Outcome of Call under Advanced Search
    And I verify search result is in access as expected
    Examples:
      | contactType | callCampaign | outcomeOfContact     |
      | Outbound    | random       | Reached Successfully |


  @CP-2622 @CP-2622-08 @aikanysh @ui-core @crm-regression  @CP-2409-20
  Scenario Outline: Verify advanced search by | Third Party Organization
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>" and capture organization name
    When I add New Consumer to the record for third party
    And I save the contact as "<contactType>" and "<contactChannelType>"
    When I navigate to manual contact record search page
    When I click on advance search icon
    And I search for an existing contact record by Third Party Organization name under Advanced Search
    And I verify appropriate fields headers after advanced search are displayed
    Examples:
      | contactType | contactChannelType | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Inbound     | Phone              | random     | random    | random            | Media         | English            |

