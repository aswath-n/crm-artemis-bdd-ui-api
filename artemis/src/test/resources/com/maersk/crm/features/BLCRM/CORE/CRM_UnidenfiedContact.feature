Feature: Unidentified Contact Record

#  @CRM-691 @CRM-691-01 @shruti
#  Scenario: Verify Fields are unavailable
#     Given I logged into CRM and click on initiate contact
#     When I select "Unidentified contact" Contact Record Type
#     Then I verify the following fields are unavailable
#
#  @CRM-691 @CRM-691-02 @shruti
#  Scenario: Verify Caller type, Contact Type , Contact Dispositions are defaulted
#    Given I logged into CRM and click on initiate contact
#    When I select "Unidentified contact" Contact Record Type
#    Then I verify the default value for "Caller Type" "Contact Type" "Disposition"
#
#  @CRM-691 @CRM-691-03 @shruti
#    Given I logged into CRM and click on initiate contact
#    When I select "Unidentified contact" Contact Record Type
#    Then I verify the link button is not displayed
#
#  @CRM-691 @CRM=691-04 @shruti
#  Scenario:Verify error is displayed for mandatory fields
#    Given I logged into CRM and click on initiate contact
#    When I select dropdown for Unidentified contact
#    And I click on close contact record
#    Then I verify the mandatory fields error displayed


  @CRM-1189 @CRM-1189-01 @CRM-1293 @CRM-1293-03 @muhabbat @crm-regression @ui-core
  Scenario: Verify Preferred Language & Consumer Type Fields are part of Unidentified Details section of Unidentified Contact
     Given I logged into CRM and click on initiate contact
     When I select "UNIDENTIFIED CONTACT" Contact Record Type
     Then I see Caller Type is default to "Anonymous" and Preferred language dropdown are displayed

  @CRM-1293-01 @CRM-1293 @aswath @crm-regression @ui-core
  Scenario: To Verify the 'Unidentified' contact record type
    Given I logged into CRM and click on initiate contact
    Then I select "UNIDENTIFIED CONTACT" Contact Record Type
    #And I click on Contact record Type
    #Then I verify the Contact record Type

  @CRM-1293-02 @CRM-1293 @aswath @crm-regression @ui-core
  Scenario: To verify the field label Caller Type
    Given I logged into CRM and click on initiate contact
    When I select "UNIDENTIFIED CONTACT" Contact Record Type
   # Then I verify the field Caller Type is present Aika


  @CRM-691 @CRM=691-04 @shruti
#  Scenario:Verify error is displayed for mandatory fields
#    Given I logged into CRM and click on initiate contact
#    When I select dropdown for Unidentified contact

  @CRM-704 @CRM=704-01 @shruti @crm-regression @ui-core
 Scenario Outline:Verify Edited By & Edited On are displayed for Unidentified Contact Record
    Given I logged into CRM and click on initiate contact
    Then I select "UNIDENTIFIED CONTACT" Contact Record Type
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
      | Re-Sent Notice         |
    And I Enter valid data
    And I click on the save button
    When I enter unidentified cotact details preferred language "<preferred language>", contact type "<contact type>", inbound call queue "<inbound call queue>" and contactType channel "<contact channel>"
    And I click on the close button on the Header
    And I click save button on unidentified contact page
    And I navigate to Contact Record search
    And I click advance search button on contact record search
    And I enter search field "<contact channel>" as "" and click on search
    And I edit first record from contact record search results
    And I click on edit icon the Contact Details page
    And I select reason for edit as "<Edit Reason>" in contact history page
    And I click on save button on the Contact History page
    And I enter search field "<Contact Info>" as "" and click on search
    And I edit first record from contact record search results
    Then I verify Contact Edit Details are captured "<Edited By>" and "<Edit Reason>"

    Examples:
    |preferred language|contact type|inbound call queue         |contact channel|Edit Reason                              |Edited By       |
    |English           |Inbound     |Enrollment                 |Phone          |Adding Additional Comment                |Service Account1|
    |Russian           |Inbound     |Eligibility                |Email          | Adding Missing Contact Reason/Action    |Service Account1|
    |Other             |Inbound     |General Program Questions  |SMS Text       |Correcting Additional Comment            |Service Account1|
    |Spanish           |Inbound     |Enrollment                 |Web Chat       |Correcting Contact Reason/Action         |Service Account1|
    |Vietnamese        |Inbound     |Enrollment                 |Phone          |Adding Additional Comment                |Service Account1|

  @CP-1156 @CP-1156-01 @vidya @crm-regression @ui-core
  Scenario Outline: Verification of headers order
    Given I logged into CRM and click on initiate contact
    Then I select "UNIDENTIFIED CONTACT" Contact Record Type
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
      | Re-Sent Notice         |
    And I Enter valid data
    And I click on the save button
    When I enter unidentified cotact details preferred language "<preferred language>", contact type "<contact type>", inbound call queue "<inbound call queue>" and contactType channel "<contact channel>"
    And I click on the close button on the Header
    And I click save button on unidentified contact page
    And I navigate to Contact Record search
    And I search for contact record by type "Unidentified Contact"
    And I expand first contact record in search results
    And I edit the record
    And I will click on search button on contact record search page
    And I expand first contact record in search results
    Then I will check all headers are in order
    And I will verify Contact Details and Edit History tab present
    And I will verify Edit Contact button displayed at page level

    Examples:
      |preferred language|contact type|inbound call queue         |contact channel|
      |English           |Inbound     |Enrollment                 |Phone          |