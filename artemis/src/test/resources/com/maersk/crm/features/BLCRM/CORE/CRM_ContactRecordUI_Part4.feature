Feature: Contact Record UI Part 4


@CP-1453 @CP-1453-01 @aikanysh @crm-regression
Scenario: Cancel Creation of Contact Record: "Cancel" Button Availability
Given I logged into CRM and click on initiate contact
And I searched existing case where First Name as "Lisa" and Last Name as "John"
Then I link the contact to an existing Case
And I verify Cancel Button is available on active contact screen

@CP-1453 @CP-1453-02 @aikanysh @crm-regression
Scenario: Cancel Creation of Contact Record: Display Warning Message
Given I logged into CRM and click on initiate contact
And I searched existing case where First Name as "Lisa" and Last Name as "John"
Then I link the contact to an existing Case
And I verify Cancel Button is available on active contact screen
And I verify when I click cancel button the warning message "If you continue, the Contact Record will not be saved.” displayed

@CP-1453 @CP-1453-03-01 @aikanysh @umid @crm-regression @CP-15547 @CP-15547-01 @CP-15547-03
Scenario Outline: Cancel Creation of Contact Record: Contact Record Not Viewable from Contact Record Search
Given I logged into CRM and click on initiate contact
When I searched customer have First Name as "Lisa" and Last Name as "John"
And I link the contact to an existing Case or Consumer Profile
And I click cancel button and click continue on warning message
And I will take trace Id for Events and "<eventType>" with index "5"
When I will initiate Event GET API
When I will get the Authentication token for "<projectName>" in "CRM"
And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
And I will save contact record id from CONTACT_RECORD_SAVE_EVENT
And I will verify contact record status is "Invalid"
When I navigate to manual contact record search page
When I click on advance search icon
And I search for an existing contact record by above ContactRecordId under Advanced Search
And I verify no results are found
Examples:
| eventName                        | module               | eventType|
|CONTACT_RECORD_UPDATE_EVENT       | CONTACT_RECORD       | contactrecord|

@CP-1453 @CP-1453-03-02 @aikanysh @crm-regression
Scenario Outline: Cancel Creation of Contact Record: Contact Record Not Viewable from Case/Consumer Contact History
Given I logged into CRM and click on initiate contact
When I searched customer have First Name as "Lisa" and Last Name as "John"
And I link the contact to an existing Case or Consumer Profile
And I click cancel button and click continue on warning message
And I will take trace Id for Events and "<eventType>" with index "5"
When I will initiate Event GET API
When I will get the Authentication token for "<projectName>" in "CRM"
And I will run the Event GET API and get the payload with "<eventName>" and "<module>" and "<correlation>"
And I will save contact record id from CONTACT_RECORD_SAVE_EVENT
When I searched customer have First Name as "Lisa" and Last Name as "John"
And I link the contact to an existing Case or Consumer Profile
And I click on the Case Contact Details Tab
Then I should not see Contact Id Present on Case & Contact Details Page
Examples:
| eventName                        | module               | eventType|
|CONTACT_RECORD_UPDATE_EVENT       | CONTACT_RECORD       | contactrecord|

@CP-15080 @CP-15080-01 @aikanysh @ui-core @crm-regression
Scenario: Contact Action values when Contact Reason is Enrollment
Given I logged into CRM and click on initiate contact
When  I should see following dropdown options for "contact reason" field displayed
| Enrollment |
Then  I should see following dropdown options for "contact action" field displayed
| New Enrollment |
| Plan Change |

@CP-22159 @CP-22159-01 @ui-core @crm-regression @khazar
Scenario: Validation of user role being read only text
Given I logged into CRM
Then I verify user role is read only text