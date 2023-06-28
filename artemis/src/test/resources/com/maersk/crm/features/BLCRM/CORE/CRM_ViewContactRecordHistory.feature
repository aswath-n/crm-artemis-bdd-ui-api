Feature: View Contact Record History

  @CRM-225 @CRM-225-01 @muhabbat @ui-core  @crm-regression
  Scenario: Verify the columns on Contact History Page
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    And I click on expand reason button to provide new reason and action
    When  I should see following dropdown options for "contact reason" field displayed
      | Update Information Request |
    And  I choose "Updated Demographic Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When  I should see following dropdown options for "contact channel" field displayed
      | Phone |
    And I capture current contact phone number
    And I select program type "Program A"
    And I select call campaign for outbound contact type
    When I select outcome of contact "Reached Successfully" for outbound
    And I close the current Contact Record and re-initiate a new Contact Record for Outbound
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I should see Linked Contact in the Header
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify all the columns on Contact History table are present

  @CRM-225 @CRM-225-02 @muhabbat @ui-core @crm-regression
  Scenario: Verify Reason/Action displays the first captured reason/action, when there are multiple entries
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    When  I should see following dropdown options for "contact reason" field displayed
     | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    And I click on expand reason button to provide new reason and action
    When  I should see following dropdown options for "contact reason" field displayed
      | Update Information Request |
    And  I choose "Updated Demographic Information" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When  I should see following dropdown options for "contact channel" field displayed
      | Phone |
    And I capture current contact phone number
    And I select program type "Program A"
    And I select call campaign for outbound contact type
    When I select outcome of contact "Reached Successfully" for outbound
    And I close the current Contact Record and re-initiate a new Contact Record for Outbound
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I should see Linked Contact in the Header
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify Contact History has one record with "Information Request"


  @CRM-225 @CRM-225-03 @muhabbat @ui-core @crm-regression
  Scenario: Initial Contact History Display
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Update Information Request |
    And  I choose "Updated Demographic Information" option for Contact Action field
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When  I should see following dropdown options for "contact channel" field displayed
      |Phone |
    And I capture current contact phone number
    And I select program type "Program A"
    And I select call campaign for outbound contact type
    When I select outcome of contact "Reached Successfully" for outbound
    And I close the current Contact Record and re-initiate a new Contact Record for Outbound
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    And  I choose "Re-Sent Notice" option for Contact Action field
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I should see Linked Contact in the Header
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When  I should see following dropdown options for "contact channel" field displayed
      | Phone |
    And I capture current contact phone number
    And I select program type "Program A"
    When I select outcome of contact "Reached Successfully" for outbound
    And I close the current Contact Record and re-initiate a new Contact Record for Outbound
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I should see Linked Contact in the Header
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I verify latest Contact Record appears on Contact History table
    Then I verify previous Contact Record appears on Contact History table after latest record


  @CRM-225 @CRM-225-04 @muhabbat @ui-core @crm-regression
  Scenario: Sort Contact history Display
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    And  I choose "Re-Sent Notice" option for Contact Action field
    When  I should see following dropdown options for "contact type" field displayed
      | Outbound |
    When  I should see following dropdown options for "contact channel" field displayed
      |Phone |
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I capture current contact phone number
    And I select program type "Program A"
    And I select call campaign for outbound contact type
    When I select outcome of contact "Did Not Reach/Left Voicemail" for outbound
    And I close the current Contact Record and re-initiate a new Contact Record for Outbound
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I should see Linked Contact in the Header
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I click on "Contact ID" column and verify it is in descending or ascending order
    And I click on "Date" column and verify it is in descending or ascending order
    And I click on "Consumer" column and verify it is in descending or ascending order
    And I click on "Type" column and verify it is in descending or ascending order
    Then I click on "Disposition" column and verify it is in descending or ascending order


  @CRM-225 @CRM-225-05 @muhabbat @ui-core @crm-regression
  Scenario: View specified number of records at a time
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I see no more then 5 Contact history records are displayed
    When  I should see following dropdown options for "show items per page" field displayed
      | Show 10 |
    And I see no more then 10 Contact history records are displayed
    When  I should see following dropdown options for "show items per page" field displayed
      | Show 20 |
    And I see no more then 20 Contact history records are displayed
    When  I should see following dropdown options for "show items per page" field displayed
      | Show 5 |
    Then I see no more then 5 Contact history records are displayed

  @James-DoNotRun
  Scenario: Validate Inbound Contact Record Made Through API
    Given I have created a consumer using API
    And The valid consumer has contact record containing the following values:
      | contactRecordType       | Inbound    |
      | contactType             | General    |
      | createdBy               | 131        |
      | updatedBy               | 131        |
      | contactRecordStatusType | Complete   |
      | inboundCallQueue        | Enrollment |
      | contactChannelType      | Phone      |
    And The contact record has a reason with the following values:
      | contactReason  | Information Request         |
      | contactAction  | Provided Appeal Information |
      | reasonComments | No Comment                  |
    When I logged into CRM and click on initiate contact
    And I search for and access the previously created consumer
    And I search for and access the previously created contacted record
    Then I should see the inbound contact record with the previously created values

  @James-DoNotRun
  Scenario: Validate Outbound Contact Record Made Through API
    Given I have created a consumer using API
    And The valid consumer has contact record containing the following values:
      | contactRecordType       | Outbound             |
      | contactType             | General              |
      | createdBy               | 131                  |
      | updatedBy               | 131                  |
      | contactRecordStatusType | Complete             |
      | contactCampaignType     | Program Information  |
      | contactChannelType      | Phone                |
      | contactOutcome          | Reached Successfully |
    And The contact record has a reason with the following values:
      | contactReason  | Information Request         |
      | contactAction  | Provided Appeal Information |
      | reasonComments | No Comment                  |
    When I logged into CRM and click on initiate contact
    And I search for and access the previously created consumer
    And I search for and access the previously created contacted record
    Then I should see the outbound contact record with the previously created values

  @James-DoNotRun
  Scenario: Validate Contact Record and Reasons Made Through API
    Given I have created a consumer using API
    And The valid consumer has contact record containing the following values:
      | contactRecordType       | Inbound    |
      | contactType             | General    |
      | createdBy               | 131        |
      | updatedBy               | 131        |
      | contactRecordStatusType | Complete   |
      | inboundCallQueue        | Enrollment |
      | contactChannelType      | Phone      |
    And The contact record has a reason with the following values:
      | contactReason  | Information Request         |
      | contactAction  | Provided Appeal Information |
      | reasonComments | No Comment                  |
    When I logged into CRM and click on initiate contact
    And I search for and access the previously created consumer
    And I search for and access the previously created contacted record
    Then I should see the contact reasons with the previously created values

  #@CRM-705 @CRM-705-01 @James @crm-regression
  Scenario: Validate Contact Record Details
    Given I logged into CRM and click on initiate contact
    And I search for a consumer that does not exist
    And I Create a Random Consumer
    And I log the inbound contact record with the following values:
      | contactType         | Inbound     |
      | inboundCallQueue    | Enrollment  |
      | contactChannel      | Phone       |
      | phoneNumber         | storedValue |
      | contactDispositions | Complete    |
      | selectProgram       | Program A   |
    And I log the contact record reasons with the following values:
      | contactReason1  | Information Request         |
      | contactAction1  | Provided Appeal Information |
      | reasonComments1 | No Comment                  |
    And I am logged out of the system
    When I log back into CRM and click on initiate contact
    And I search for and access the previously created consumer
    And I search for and access the previously created contacted record
    Then I should see the inbound contact record with the previously created values

  #@CRM-705 @CRM-705-02 @James @crm-regression
  Scenario: Validate Contact Reasons Details
    Given I logged into CRM and click on initiate contact
    And I search for a consumer that does not exist
    And I Create a Random Consumer
    And I log the inbound contact record with the following values:
      | contactType         | Inbound     |
      | inboundCallQueue    | Enrollment  |
      | contactChannel      | Phone       |
      | phoneNumber         | storedValue |
      | contactDispositions | Complete    |
      | selectProgram       | Program A   |
    And I log the contact record reasons with the following values:
      | contactReason1  | Information Request            |
      | contactAction1  | Provided Appeal Information    |
      | reasonComments1 | No Comment                     |
      | contactReason2  | Information Request            |
      | contactAction2  | Provided Financial Information |
      | reasonComments2 | No Comment                     |
      | contactReason3  | Information Request            |
      | contactAction3  | Provided Program Information   |
      | reasonComments3 | No Comment                     |
    And I am logged out of the system
    When I log back into CRM and click on initiate contact
    And I search for and access the previously created consumer
    And I search for and access the previously created contacted record
    Then I should see the contact reasons with the previously created values

 # @CRM-705 @CRM-705-03 @James @crm-regression
  Scenario: Validate Outbound Contact Record Details
    Given I logged into CRM and click on initiate contact
    And I search for a consumer that does not exist
    And I Create a Random Consumer
    And I log the outbound contact record with the following values:
      | contactType         | Outbound             |
      | contactCampaignType | Program Information  |
      | contactChannel      | Phone                |
      | phoneNumber         | storedValue          |
      | contactDispositions | Complete             |
      | contactOutcome      | Reached Successfully |
      | selectProgram       | Program A            |
    And I log the contact record reasons with the following values:
      | contactReason1  | Information Request         |
      | contactAction1  | Provided Appeal Information |
      | reasonComments1 | No Comment                  |
    And I am logged out of the system
    When I log back into CRM and click on initiate contact
    And I search for and access the previously created consumer
    And I search for and access the previously created contacted record
    Then I should see the outbound contact record with the previously created values

  #@CRM-705 @CRM-705-04 @James @crm-regression
  Scenario: Validate Contact Record and Contact Reasons Details
    Given I logged into CRM and click on initiate contact
    And I search for a consumer that does not exist
    And I Create a Random Consumer
    And I log the outbound contact record with the following values:
      | contactType         | Outbound             |
      | contactCampaignType | Program Information  |
      | contactChannel      | Phone                |
      | phoneNumber         | storedValue          |
      | contactDispositions | Complete             |
      | contactOutcome      | Reached Successfully |
      | selectProgram       | Program A            |
    And I log the contact record reasons with the following values:
      | contactReason1  | Information Request            |
      | contactAction1  | Provided Appeal Information    |
      | reasonComments1 | No Comment                     |
      | contactReason2  | Information Request            |
      | contactAction2  | Provided Financial Information |
      | reasonComments2 | No Comment                     |
      | contactReason3  | Information Request            |
      | contactAction3  | Provided Program Information   |
      | reasonComments3 | No Comment                     |
    And I am logged out of the system
    When I log back into CRM and click on initiate contact
    And I search for and access the previously created consumer
    And I search for and access the previously created contacted record
    Then I should see the contact reasons with the previously created values

  #@CRM-1186 @CRM-1186-01 @muhabbat  @crm-regression
  Scenario: Validate Contact Record Details
    Given I logged into CRM and click on initiate contact
    And I search for a consumer that does not exist
    And I Create a Random Consumer
    And I log the inbound contact record with the following values:
      | contactType         | Inbound     |
      | inboundCallQueue    | Enrollment  |
      | contactChannel      | Phone       |
      | phoneNumber         | storedValue |
      | contactDispositions | Complete    |
      | selectProgram       | Program A   |
    And I log the contact record reasons with the following values:
      | contactReason1  | Information Request                  |
      | contactAction1  | Provided Appeal Information          |
      | reasonComments1 | Reason 1 comment                     |
      | contactReason2  | Materials Request                    |
      | contactAction2  | Re-Sent Notice                       |
      | reasonComments2 | Reason 2 comment                     |
      | contactReason3  | Complaint - Civil Rights             |
      | contactAction3  | Documented in WAHBE Ticketing System |
      | reasonComments3 | Reason 3 comment                     |
    When I re-initiate a new contact with previously created consumer to expand saved contact record
    Then I see the previously created inbound contact record

  #@CRM-1186 @CRM-1186-02 @muhabbat @crm-regression
  Scenario: Validate Outbound Contact Record Details
    Given I logged into CRM and click on initiate contact
    And I search for a consumer that does not exist
    And I Create a Random Consumer
    And I log the outbound contact record with the following values:
      | contactType         | Outbound             |
      | contactCampaignType | Program Information  |
      | contactChannel      | Phone                |
      | phoneNumber         | storedValue          |
      | contactDispositions | Complete             |
      | contactOutcome      | Reached Successfully |
      | selectProgram       | Program A            |
    And I log the contact record reasons with the following values:
      | contactReason1  | Information Request                  |
      | contactAction1  | Provided Appeal Information          |
      | reasonComments1 | Reason 1 comment                     |
      | contactReason2  | Materials Request                    |
      | contactAction2  | Re-Sent Notice                       |
      | reasonComments2 | Reason 2 comment                     |
      | contactReason3  | Complaint - Civil Rights             |
      | contactAction3  | Documented in WAHBE Ticketing System |
      | reasonComments3 | Reason 3 comment                     |
    When I re-initiate a new contact with previously created consumer to expand saved contact record
    Then I should see the previously created outbound contact record


    #Below Scenarios are for Update View General Contact Details Design story


  @CP-1154 @CP-1154-01 @vidya @ui-core @crm-regression
  Scenario: Verify the all headers order
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I will fill the required filed in create consumer page
    When I click on Create Consumer Button on Create Consumer Page
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    And I capture current contact phone number
    And I select program type "Program A"
    And I close the current Contact Record and re-initiate a new Contact Record
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case or Consumer Profile
    And I should see Linked Contact in the Header
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    Then I verify all the columns on Contact History table are present
    And I click on contactId if record type is "General"
    When I click on edit button on contact history page
    And I select reason for edit drop down and provide details to edit
    Then I will verify all section header orders
    And I will verify link section contains linked case or consumer
    And I verify whether it contains contact details and edit history tabs and only one edit button for page

  @CP-1154 @CP-1154-02 @vidya @ui-core @crm-regression
  Scenario: Verify the all headers order from Manual Contact Record Search
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I will fill the required filed in create consumer page
    When I click on Create Consumer Button on Create Consumer Page
    And I navigate to manual contact record search page
    And I search for contact record by contact id "35594"
    And I expand first contact record in search results
    Then I will verify all section header orders
    And I will verify link section contains linked case or consumer
    And I verify whether it contains contact details and edit history tabs and only one edit button for page

