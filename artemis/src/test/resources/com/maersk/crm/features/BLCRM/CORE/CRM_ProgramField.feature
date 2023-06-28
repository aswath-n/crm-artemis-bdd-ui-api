Feature: Program Field For Contact Record UI
  #new 01/08/2019

  @CRM-1288 @CRM-1288-01 @shruti @crm-regression @ui-core
  Scenario:Validate Program field allows multi select
  Given I logged into CRM and click on initiate contact
  When  I should see following dropdown options for "contact reason" field displayed
  | Materials Request |
  Then  I should see following dropdown options for "contact action" field displayed
  | Sent Program Materials |
  And I Enter valid data
  And I click on the save button
  When I search for an existing contact by first name "Test First"
  When I search for an existing contact by last name "Test Last"
  And I click on Search Button on Search Consumer Page
  And I click on the "Test First" record link button
  Then I verify that multiple options can be selected for Program Field
    |Program A|
    |Program B|

  @CRM-1288 @CRM-1288-02 @CP-15080 @shruti @aikanysh @crm-regression @ui-core
  Scenario:Validate Program field dropdown has values (Program A , Program B & Program C)
  Given I logged into CRM and click on initiate contact
  When  I should see following dropdown options for "contact reason" field displayed
  | Materials Request |
  Then  I should see following dropdown options for "contact action" field displayed
  | Sent Program Materials |
  And I Enter valid data
  And I click on the save button
  When I search for an existing contact by first name "Test First"
  When I search for an existing contact by last name "Test Last"
  And I click on Search Button on Search Consumer Page
  And I click on the "Test First" record link button
  Then I verify dropdown values  available for Program Field
    |Program A|
    |Program B|
    |Program C|
    |Medicaid|

  #@CRM-1288 @CRM-1288-03 @shruti @crm-regression #Duplicate functionality as CRM-1288-04
  Scenario:Validate Program field is mandatory field and error is displayed if left empty
  Given I logged into CRM and click on initiate contact
  When  I should see following dropdown options for "contact reason" field displayed
  | Materials Request |
  Then  I should see following dropdown options for "contact action" field displayed
  | Sent Program Materials |
  And I Enter valid data
  And I click on the save button
  When I search for an existing contact by first name "Test First"
  When I search for an existing contact by last name "Test Last"
  And I click on Search Button on Search Consumer Page
  And I click on the "Test First" record link button
  And I scroll the Page to the Bottom
  And I click on the Close button in the bottom
  Then I verify error message "PROGRAM is required and cannot be left blank" displayed for program field

  @CRM-1288 @CRM-1288-04 @shruti @crm-regression @ui-core
  Scenario:Validate User is unable to close the contact record when Program field is left empty
  Given I logged into CRM and click on initiate contact
  When  I should see following dropdown options for "contact reason" field displayed
  | Materials Request |
  Then  I should see following dropdown options for "contact action" field displayed
  | Sent Program Materials |
  | Re-Sent Notice         |
  And I Enter valid data
  And I click on the save button
  When I search for an existing contact by first name "Harry"
  When I search for an existing contact by last name "Potter"
  And I click on Search Button on Search Consumer Page
  And I click on the "Test First" record link button
  And I click on the Contact Type "Inbound"
  And I click on the Inbound Call queue "Eligibility"
  And I click on the Contact Channel Type "Phone"
  Then I click on the contact dispotions "Complete"
  When I enter contact phone number "9632154874"
  When I click on the close button on the Header
  And I scroll the Page to the Bottom
  And I click on the Close button in the bottom
  Then I verify error message "PROGRAM is required and cannot be left blank" displayed for program field