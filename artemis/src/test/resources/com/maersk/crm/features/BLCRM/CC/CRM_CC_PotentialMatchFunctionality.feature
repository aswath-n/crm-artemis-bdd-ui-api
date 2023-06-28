Feature: Validate all potential math functionality


  @CP-17989 @CP-17989-06 @CP-23889 @CP-23889-02 @Beka @ui-cc @crm-regression
  Scenario: Verify potential matches presented to me
    Given I logged into CRM
    And I click on Case Consumer Search page
    And I search for consumer have First Name as "Umesh" and Last Name as "Kumar"
    And I click on Search Button
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I populate email and phone number and click on create consumer button
    Then Verify potential matches "Umesh  Kumar" presented and I able to click and navigate to consumer profile

  @CP-17989 @CP-17989-05  @CP-19028 @CP-19028-0 @chopa @ui-cc @crm-regression
  Scenario Outline: Verify CONTINUE AND ADD NEW CONSUMER is displayed after FN, LN and DOB Match
    Given I logged into CRM and click on initiate contact
    When I enter First Name as <firstName>, Middle Initial as <middleName>, Last Name as <lastName>, SSN as <ssn>, Date Of Birth as <DOB>, Unique ID as <ID>  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I verify the case is linked to contact
    Then I verify value of First Name as "Kamil", Last Name as "Shikh" and Date Of Birth as "06/06/1986" in Search Results
    Then I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add phone number to an existing consumer
    When I click on Create Consumer Button
    And I click on continue button on warning message
    And I verify Potential Match error message is displayed
    Then I verify "CONTINUE AND ADD NEW CONSUMER" is enabled in Create Consumer Page
    Examples:
      | firstName | middleName | lastName | ssn | DOB          | ID |
      | 'Kamil'   | ''         | 'Shikh'  | ''  | '06/06/1986' | '' |

  @CP-17989 @CP-17989-04 @CP-30136 @CP-30136-01 @Beka @crm-regression @ui-cc
  Scenario: Apply Potential Match to Third Party C&C Search - Create Consumer for BLCRM
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record in active contact
    When I searched existing from third party search case where First Name as "Jon" and Last Name as "Nicholas"
    And I populate "12021923" and "997779900" for existing consumer
    And I click on Add Consumer button
    When I populate the rest fields and try to create consumer "BLCRM" to trigger potential match functionality
    Then  I will be presented with Consumer options that match the data I entered per

  @CP-17989 @CP-17989-03 @CP-30136 @CP-30136-02 @Beka @crm-regression @ui-cc
  Scenario: Permit Linking of Potential Match for BLCRM
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record in active contact
    When I searched existing from third party search case where First Name as "Jon" and Last Name as "Nicholas"
    And I populate "12021923" and "997779900" for existing consumer
    And I click on Add Consumer button
    When I populate the rest fields and try to create consumer "BLCRM" to trigger potential match functionality
    Then I am able to link the selected matching Consumer to the Contact Record "BLCRM"

  @CP-17989 @CP-17989-02 @CP-30136 @CP-30136-03 @Beka @crm-regression @ui-cc
  Scenario: Verify ability to create consumer if not exact match consumer exist - Create Consumer for BLCRM
    Given I logged into CRM and click on initiate contact
    And I click on third party contact record in active contact
    When I searched existing from third party search case where First Name as "Jon" and Last Name as "Nicholas"
    And I populate "12021923" and "" for existing consumer
    And I click on Add Consumer button
    When I populate the rest fields and try to create consumer "BLCRM" to trigger potential match functionality
    Then I verify "CONTINUE AND ADD NEW CONSUMER" is enabled in Create Consumer Page

  @CP-17989 @CP-17989-01 @CP-33556 @CP-33556-01 @Beka @ui-cc @crm-regression
  Scenario: Verify potential matches header from create consumer page
    Given I logged into CRM
    And I click on Case Consumer Search page
    And I search for consumer have First Name as "Umesh" and Last Name as "Kumar"
    And I click on Search Button
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I populate email and phone number and click on create consumer button
    Then I see list of Potential Match consumers table with header
      | CONSUMER ID          |
      | EXTERNAL CONSUMER ID |
      | CASE ID              |
      | EXTERNAL CASE ID     |
      | ROLE                 |
      | FULL NAME            |
      | GENDER               |
      | DATE OF BIRTH        |
      | SSN                  |
      | STATUS               |