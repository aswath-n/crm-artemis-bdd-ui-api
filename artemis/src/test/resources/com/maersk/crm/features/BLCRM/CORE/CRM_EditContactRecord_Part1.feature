Feature: Edit Contact Record History: Part 1

  #muting below scenarios since it is covered by more recent story @CP-14476
  @CRM-633 @CRM-633-01 @shruti #@ui-core @crm-regression
  Scenario Outline: Verify User is able to edit previously captured Reasons/Actions & Comments on the Contact History page-01
    Given I logged into CRM choosing project "6203 BLCRM" click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact email as "<Email>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    #And I link the contact to an existing Case or Consumer Profile "consumerId"
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
#    And I select the latest contact id with type "General"
    And I select the latest contact record with type "General"
    And I click on edit icon the Contact Details page
    And I edit the saved contact reason for "<Contact Reason>" and "<ContactReason Comments>" on Edit Contact History Page
      | Re-Sent Notice |
    Then I verify reason and actions are edited "<Contact Reason>", "<ContactReason Comments>"
      | Re-Sent Notice |
    Examples:
      | Program   | ContactChannelType | ContactType | Email           | Contact Reason    | Contact Action | ContactReason Comments |
      | Program A | Web Chat           | Inbound     | abctest@xyz.com | Materials Request | Re-Sent Notice | Updated                |

    #muting below scenarios since it is covered by more recent story @CP-14476
  @CRM-633 @CRM-633-02 @shruti #@ui-core @crm-regression
  Scenario Outline: Verify Reason for Edit Field is displayed and the dropdown values 01
    Given I logged into CRM choosing project "6203 BLCRM" click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact email as "<Email>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    #And I link the contact to an existing Case or Consumer Profile "consumerId"
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
#    And I select the latest contact id with type "General"
    And I select the latest contact record with type "General"
    And I click on edit icon the Contact Details page
    Then I verify Reason for Edit Field is displayed
    And I verify the dropdown values for the Reason For Edit Field
      | Adding Additional Comment             |
      | Adding Missing Contact Reason/Action  |
      | Correcting Additional Comment         |
      | Correcting Consumer Profile/Case Link |
      | Correcting Contact Reason/Action      |
      | Updating Contact Record Disposition   |
      | Correcting Contact Details            |
      | Correcting Third Party Information    |

    Examples:
      | Program   | ContactChannelType | ContactType | Email           | Contact Reason    | Contact Action | ContactReason Comments |
      | Program A | Web Chat           | Inbound     | abctest@xyz.com | Materials Request | Re-Sent Notice | Updated                |

#muting below scenarios since it is covered by more recent story @CP-8290
  @CRM-633 @CRM-633-03 @shruti #@ui-core @crm-regression
  Scenario Outline: Verify Reason for Edit Field is displayed and the dropdown values
    Given I logged into CRM choosing project "6203 BLCRM" click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact email as "<Email>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
   # And I link the contact to an existing Case or Consumer Profile "consumerId"
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
#    And I select the latest contact id with type "General"
    And I select the latest contact record with type "General"
    And I click on edit icon the Contact Details page
    #And I click on the Additional Comments on edit contact history page
    And I Enter Valid  additional Comments "comments" on edit contact history page
    And I click on Save button for additional comments on edit contact history page
    And I verify Additional comments "comments" on edit contact history page
    And I select reason for edit as "<Edit Reason>" in contact history page
    And I click on save button on the Contact History page
#    And I select the latest contact id with type "General"
    #And I select the latest contact record with type "Outbound"
    Then I verify Contact Edit Details are captured "<Edited By>" and "<Edit Reason>"


    Examples:
      | Program   | ContactChannelType | ContactType | Email           | Contact Reason    | Contact Action | ContactReason Comments | Edit Reason               | Edited By |
      | Program A | Web Chat           | Inbound     | abctest@xyz.com | Materials Request | Re-Sent Notice | Updated                | Adding Additional Comment |[blank]|

  #muting below scenarios since it is covered by more recent story @CP-14476
  @CRM-633 @CRM-633-04 @shruti #@ui-core @crm-regression
  Scenario Outline: Verify User is able to edit previously captured Reasons/Actions & Comments on the Contact History page
    Given I logged into CRM choosing project "6203 BLCRM" click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact email as "<Email>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
   # And I link the contact to an existing Case or Consumer Profile "consumerId"
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
#    And I select the latest contact id with type "General"
    And I select the latest contact record with type "General"
    And I click on edit icon the Contact Details page
    And I edit the saved contact reason for "<Contact Reason>" and "<ContactReason Comments>" on Edit Contact History Page
      | Re-Sent Notice |
    Then I verify reason and actions are edited "<Contact Reason>", "<ContactReason Comments>"
      | Re-Sent Notice |
    And I select reason for edit as "<Edit Reason>" in contact history page
    When I click on save button on the Contact History page
    #Then I verify contact record exist with the reason "<Edit Reason>"
    Then Verify contact record has reason "<Edit Reason>"

    Examples:
      | Program   | ContactChannelType | ContactType | Email           | Contact Reason    | Contact Action | ContactReason Comments | Edit Reason               |
      | Program A | Web Chat           | Inbound     | abctest@xyz.com | Materials Request | Re-Sent Notice | Updated                | Adding Additional Comment |


#    Blocked due the CP-23684 CP-23351
  @CRM-285 @CRM-285-01 @vinuta  @ui-core @crm-regression
  Scenario Outline: Verify User is able to edit previously captured contact disposition on the Contact History page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the latest contact record with type "General"
    And I click on edit icon the Contact Details page
    And I enter contact phone number "3334445566"
    Then I select "<Program>" as contact program type
    And I edit the saved contact disposition to "<Contact Disposition>" on Edit Contact History Page
    Then I verify contact disposition is edited to "<Contact Disposition>"
    And I verify Contact Edit Details are captured "<Edited By>" and "<Edit Reason>"

    Examples:
      | Program   | ContactChannelType | Contact Disposition | Email           | Edit Reason            | Edited By          |
      | Program A | Web Chat           | Complete            | abctest@xyz.com | Correcting Disposition | Service AccountOne |

  @CRM-1200 @CRM-1200-01 @aswath @ui-core @crm-regression
  Scenario Outline: Verify hover text of the Manual and campaign icon outbound and inbound web chat on the Contact History page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | <contact type> |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact email as "<Email>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify all the hover text on Contact icon Case "<ContactChannelType>"
    Examples:
      | Program   | ContactChannelType | contact type | Email            |
      | Program A | Web Chat           | Outbound     | Outbound@xyz.com |
      | Program A | Web Chat           | Inbound      | Inbound@xyz.com  |


  @CRM-1200 @CRM-1200-02 @aswath @ui-core @crm-regression
  Scenario Outline: Verify hover text of the Manual and campaign icon outbound and inbound phone on the Contact History page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | <contact type> |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact phone number "<Phone>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify all the hover text on Contact icon Case "<ContactChannelType>"

    Examples:
      | Program   | ContactChannelType | contact type | Phone      |
      | Program A | Phone              | Outbound     | 4282929292 |
      | Program A | Phone              | Inbound      | 4282929292 |

  @CRM-1200 @CRM-1200-03 @aswath @ui-core @crm-regression
  Scenario Outline: Verify hover text of the Manual and campaign icon outbound and inbound SMS Text on the Contact History page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | <contact type> |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact phone number "<Phone>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify all the hover text on Contact icon Case "<ContactChannelType>"

    Examples:
      | Program   | ContactChannelType | contact type | Phone      |
      | Program A | SMS Text           | Outbound     | 4282920092 |
      | Program A | SMS Text           | Inbound      | 4282920092 |


  @CRM-1200 @CRM-1200-04 @aswath  @ui-core @crm-regression
  Scenario Outline: Verify hover text of the Manual and campaign icon outbound and inbound Email on the Contact History page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | <contact type> |
    And I click on the Contact Channel Type "<ContactChannelType>"
    When I enter contact email as "<Email>"
    And I select contact program type as "<Program>"
    And I close the current Contact Record and re-initiate a new Contact Record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify all the hover text on Contact icon Case "<ContactChannelType>"
    Examples:
      | Program   | ContactChannelType | contact type | Email            |
      | Program A | Email              | Outbound     | Outbound@xyz.com |
      | Program A | Email              | Inbound      | Inbound@xyz.com  |

  @CP-457 @CP-457-01 @ozgen @ui-core @crm-regression
  Scenario Outline: Verification of Edited By Field when Update Existing Contact Record for General
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    Then I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    Then I link the contact to an existing Case with consumer id
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the latest contact record with type "General"
    And I click on edit icon the Contact Details page
    And I enter contact phone number "3334445566"
    Then I select "<Program>" as contact program type
    And I edit the saved contact disposition to "<Contact Disposition>" on Edit Contact History Page
#    And I verify that edited By field is displayed with "<Edited By>" value
    And I verify that edited By field is displayed
    Examples:
      | Program   | ContactChannelType | Contact Disposition | Email           | Edit Reason            | Edited By               |
      | Program A | Web Chat           | Complete            | abctest@xyz.com | Correcting Disposition | 1066 Service AccountOne |

  @CP-457 @CP-457-02 @ozgen  @ui-core @crm-regression
  Scenario: Verification of User's First and Last Name who made edit for General
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    And I close the current Contact Record and re-initiate a new Contact Record
    And I search for a consumer that I just created
    And I link the consumer to authenticate the consumer
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I click on to latest contact id to see contact record details
    And I verify the contact record type
    Then I edit contact record by choosing reason for edit
    Then I click on to latest contact id to see contact record details
    And I verify that User's First and Last Name who made edit is displayed

  @CP-457 @CP-457-03 @ozgen @ui-core @crm-regression
  Scenario: Verification of Length of User's First and Last Name who made edit for General
    Given I logged into CRM and click on initiate contact
    When I add New Consumer to the record
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    And I close the current Contact Record and re-initiate a new Contact Record
    And I search for a consumer that I just created
    And I link the consumer to authenticate the consumer
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I click on to latest contact id to see contact record details
    And I verify the contact record type
    Then I edit contact record by choosing reason for edit
    Then I click on to latest contact id to see contact record details
    And I verify that User's First and Last Name who made edit is more than 15 characters

  @CP-457 @CP-457-04 @ozgen @ui-core @crm-regression
  Scenario Outline: Verification of Edited By Field when Update Existing Contact Record for Third Party Contact
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for third party
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    And I close the current Contact Record and re-initiate a new Contact Record
    When I click on third party contact record type radio button
    Then I search for a consumer that I just created for third party and link the consumer
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I click on to latest contact id to see contact record details
    And I verify the contact record type
    Then I edit contact record by choosing reason for edit
    Then I click on to latest contact id to see contact record details
    And I verify that edited By field is displayed

    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Ellie      | Smith     | ABC Group         | Media         | English            |

  @CP-457 @CP-457-05 @ozgen @ui-core @crm-regression
  Scenario Outline: Verification of User's First and Last Name who made edit for Third Party Contact
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for third party
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    And I close the current Contact Record and re-initiate a new Contact Record
    When I click on third party contact record type radio button
    Then I search for a consumer that I just created for third party and link the consumer
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I click on to latest contact id to see contact record details
    And I verify the contact record type
    Then I edit contact record by choosing reason for edit
    Then I click on to latest contact id to see contact record details
    And I verify that User's First and Last Name who made edit is displayed

    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Ellie      | Smith     | ABC Group         | Media         | English            |

  @CP-457 @CP-457-06 @ozgen @ui-core @crm-regression
  Scenario Outline: Verification of Length of User's First and Last Name who made edit for Third Party Contact
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for third party
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I enter additional comments "First saved reason and action"
    Then I click save button for reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "Program A" as contact program type
    And I close the current Contact Record and re-initiate a new Contact Record
    When I click on third party contact record type radio button
    Then I search for a consumer that I just created for third party and link the consumer
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I click on to latest contact id to see contact record details
    And I verify the contact record type
    Then I edit contact record by choosing reason for edit
    Then I click on to latest contact id to see contact record details
    And I verify that User's First and Last Name who made edit is more than 15 characters

    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language |
      | Ellie      | Smith     | ABC Group         | Media         | English            |


  @CP-462 @CP-462-01 @asad @ui-core @crm-regression
  Scenario: View Edit Information on Contact Record Details
    Given I logged into CRM
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "367220"
    When I click on first Contact Record ID on Contact Record
    Then I am able to select to view the full Edit History for the Contact Record History

  @CP-462 @CP-462-02 @asad @ui-core @crm-regression
  Scenario: View Edit History
    Given I logged into CRM
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "367220"
    When I click on first Contact Record ID on Contact Record
    Then I will see a comprehensive detailed view of edit information of contact record History

  @CP-2660 @CP-2660-01 @ui-core @crm-regression @aikanysh
  Scenario Outline: Display Contact Wrap-up Time in Saved Contact Record: End-Save
    Given I logged into CRM and click on initiate contact
    When I click End Contact and click "End-Save" after few seconds
    And I will take "traceId" from logs of contact for "wrapUpTime-save"
    And I will get the Authentication token for "BLCRM" in "CRM"
    Then I get and save contact record id from data object
    When I navigate to manual contact record search page
    And I search for  "<contactRecordID>" in Manual Contact Record Search
    And I click on found contact record and verify wrap-up time is displayed for "End-Save"
    Examples:
      | contactRecordID |
      | random          |


  @CP-1027 @CP-1027-02 @kamil @ui-core @crm-regression
  Scenario: View Edit History: Verify 5 record count for Pagination
    Given I logged into CRM and click on initiate contact
    When I navigate to Contact Record search
    And I searched customer with Contact Record ID "137371"
    When Click on first Contact Record ID on Contact Record
    Then I navigated to the Edit History tab
    Then Verify maximum five records for each Pagination are displayed


  @CP-1027 @CP-1027-01 @kamil @ui-core @crm-regression
  Scenario Outline: View Edit History information for each edit made to the Contact Record are displayed
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I Enter mandatory Third Party Details "<First Name>", "<Last Name>", "<Organization Name>", "<Consumer Type>" and "<Preferred Language>"
    When I add New Consumer to the record for third party
    And I choose a contact reason from reason dropdown list
    Then I choose a contact action from action dropdown list
    And I save created reason and action
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    And I click on the Contact Channel Type "Phone"
    And I enter contact phone number "3334445566"
    Then I select "<Program A>" as contact program type
    And I close the current Contact Record and re-initiate a new Contact Record
    When I click on third party contact record type radio button
    Then I search for a consumer that I just created for third party and link the consumer
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I click on to latest contact id to see contact record details
    And I verify the contact record type
    When I click on edit button on contact details tab
    And I unlink existing consumer and linking consumer fn "Bruce" and ln "Wayne"
    And I select reason for edit as "Correcting Case/Consumer Link" in contact history page
    And I click on Save button in Edit screen
    Then I navigated to the Edit History tab and clicking Continue
    Then I will see a comprehensive detailed view information for each edit made to the Contact Record
    And I verify the value for Edited On is in correct format
    And I verify Edited By has the correct user ID & user name
    Then Verify EDIT History with Reason for Edit "Correcting Case/Consumer Link"
    And I verify Field Label has value Consumer ID with previous value updated value
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type | Preferred Language | Program A |
      | Ellie      | Smith     | ABC Group         | Media         | English            | Program A |


