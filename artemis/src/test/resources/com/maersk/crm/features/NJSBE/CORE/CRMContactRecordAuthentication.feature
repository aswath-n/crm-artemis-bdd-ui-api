@CP-9913
Feature: NJ: Configure Contact Record Authentication

  @CP-13009 @CP-9913 @CP-9913-01 @asad @ui-core-nj @crm-regression @NJ-UI-Regression #removed external case id verification as per cp 13009
  Scenario: Update New Jersey Authentication Checkboxes (nj-sbe doesnt have unable to authenticate button)
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify system displays the Home Address with Authentication checkboxes

  @CP-9913 @CP-9913-02 @asad @ui-core-nj @crm-regression @NJ-UI-Regression @ui-core-smoke
  Scenario: Update New Jersey Authentication Checkboxes
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify system displays the DOB, SSN and Phone Number with Authentication checkboxes

  @CP-9913 @CP-9913-03 @asad @ui-core-nj @crm-regression @NJ-UI-Regression
  Scenario: Business Rules for Authentication Boxes
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify display of 'Consumer Authenticated' message by selecting two checkboxes

  @CP-9913 @CP-9913-04 @asad @ui-core-nj @crm-regression @NJ-UI-Regression
  Scenario: No Data, No Checkbox
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "Tom" and Last Name as "Wills"
    And I expand the Case Consumer this contact relates to in search result
    Then I verify display of '-- --' for null values

  @CP-13009 @CP-13009-1 @ui-core-nj @crm-regression @NJ-UI-Regression @kamil
  Scenario: General - Active Contact : Case & Consumer ID not displayed with a checkbox
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    When I searched customer have First Name as "David" and Last Name as "Leisinger"
    And I expand the Case Consumer this contact relates to in search result
    Then Verify in Authentication Grid - External (GetInsured) Case & Consumer ID are not displayed with a checkbox

  @CP-9483 @CP-9483-04 @CP-6042 @CP-6042-04 @moldir @ui-core-nj @crm-regression #Fails due to CP-48272
  Scenario Outline: Validate CONSUMER_AUTHENTICATION_SAVE_EVENT is generated and sent to DPBI when Consumer is successfully authenticated in NJSBE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I searched customer have First Name as "Adriene" and Last Name as "Hollenback"
    Then I link the contact to an existing CaseConsumer in "<projectName>"
    And Wait for 5 seconds
    When I navigate to the case and contact details tab
    And I will save contact record id from CASE & CONTACT DETAILS tab Contact History
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will take trace Id for Events and "<eventType>"
    When I will initiate Event GET API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
    Then I will verify CONSUMER_AUTHENTICATION_SAVE_EVENT response
    And Verify genericFields are removed
    And I initiate subscribers POST API
    Then I provide subscriber name as "DPBI" in the body and run subscribers POST API
    Then I will check the response has event Subscriber Mapping ID for "<eventName>"
    And I initiate Subscribers Record GET API and run Subscribers Record GET API
    Then I will verify response has event Id and "<eventName>" and subscriberId
    Examples:
      |eventName                          | module         | eventType      | projectName |
      |CONSUMER_AUTHENTICATION_SAVE_EVENT | CONTACT_RECORD | authentication |  NJ-SBE      |