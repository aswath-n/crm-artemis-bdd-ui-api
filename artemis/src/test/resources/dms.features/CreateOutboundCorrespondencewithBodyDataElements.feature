@CP-10592
Feature: Outbound Correspondence Request with Body data elements

  @CP-10592-01  @ui-ecms1 @Keerthi
  Scenario: Validate the Body data elements heading and label names
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I have selected an Outbound Correspondence through the UI "C2AnSNE5lA - 6eUAamS1zP" as type
    And I validate Body Data Elements heading
    Then I validate Body Data elements labels
      | ST_1_REQ_AM_FN   |
      | NUM_1_REQ_AM_FN  |
      | DATE_1_REQ_AM_FN |
      | CB_1_REQ_AM_FN   |
      | LT_1_REQ_AM_FN   |
      | LT_2_FN          |


  @CP-10592-02  @ui-ecms1 @Keerthi
  Scenario: Validate the Body data elements data types and maxlength
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I have selected an Outbound Correspondence through the UI "C2AnSNE5lA - 6eUAamS1zP" as type
    And I validate Body Data elements data types and maxlength

  @CP-10592-03  @ui-ecms1 @Keerthi
  Scenario: Validate the error messages for the required body data elements
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I have selected an Outbound Correspondence through the UI "C2AnSNE5lA - 6eUAamS1zP" as type
    And I validate the error messages for the required body data elements
      | ST_1_REQ_AM_FN is required   |
      | Num_1_REQ_AM_FN is required  |
      | Date_1_REQ_AM_FN is required |
      | CB_1_REQ_AM_FN is required   |
      | LT_1_REQ_AM_FN is required   |


  @CP-10592-04  @ui-ecms1 @Keerthi
  Scenario: Validate multiple occurences of the Body data elements
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I have selected an Outbound Correspondence through the UI "C2AnSNE5lA - 6eUAamS1zP" as type
    And I Validate "5" Body data elements multiple occurences

  @CP-10592-05  @ui-ecms1 @Keerthi
  Scenario: Validate deletion of multiple occurenes  of the Body data elements
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I have selected an Outbound Correspondence through the UI "C2AnSNE5lA - 6eUAamS1zP" as type
    And I Validate "5" Body data elements multiple deletions

  @CP-10592-06  @ui-ecms1 @Keerthi
  Scenario: Validate the Cancel functionality for the Body data elements
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I have selected an Outbound Correspondence through the UI "C2AnSNE5lA - 6eUAamS1zP" as type
    And I enter data in body data elements
    And I validate Cancel functionality

  @CP-10592-07  @ui-ecms2 @Keerthi
  Scenario: Validate the save functionality for the Body data elements
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    And I clicked on Third Party Contact
    Then I entered required data in Third party contact page
      | FirstName | Random |
      | LastName  | Random |
    And I have selected an Outbound Correspondence through the UI "C2AnSNE5lA - 6eUAamS1zP" as type
    And I enter data in body data elements
    And I enter data in send To section
      | AddressLine1 | Random |
      | AddressLine2 | Random |
      | City         | Random |
      | State        | Random |
      | Zipcode      | Random |
    Then I validate Save functionality

  @CP-10596-01 @ui-ecms2 @Keerthi
  Scenario: Validate Body Data When Viewing Correspondence Request
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have specified the following values in the request for an Outbound Correspondence
      | correspondenceDefinitionMMSCode | OnlyMail          |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    And I validate Body Data Elements structure in OB correspondence details page

    # OB functionality not implemented in CoverVa
  #@CP-25376 @CP-25376-01 @api-ecms-coverva @James
  Scenario: Verify notification status is Assembled when Channel is Mail and Send Immediately is True
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the notification was changed to "Assembled" from notification status history

    # OB functionality not implemented in CoverVa
  #@CP-25376 @CP-25376-02 @api-ecms-coverva @James
  Scenario: Verify notification is assembled and stored in onbase as a VACV Outbound Correspondence
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the notification was changed to "Assembled" from notification status history
    And I should see the "VACV Outbound Correspondence" has been uploaded with the previously created notification Id

    # OB functionality not implemented in CoverVa
  #@CP-25376 @CP-25376-03 @api-ecms-coverva @James
  Scenario: Verify notification status is error when French language is not a word document
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "FRENCH" language
    When I Create an Outbound Correspondence Request of "FRENCH" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the status of the notification and correspondence is "Error"

  @CP-25376 @CP-25376-04 @api-ecms-ineb @James
  Scenario: Verify in IN-EB notification status is Assembled when Channel is Mail and Send Immediately is True
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the status of the notification and correspondence is "Assembled"

  @CP-25376 @CP-25376-05 @api-ecms-ineb @James
  Scenario: Verify in IN-EB notification is assembled and stored in onbase as a INEB Outbound Correspondence
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the status of the notification and correspondence is "Assembled"
    And I should see the "INEB Outbound Correspondence" has been uploaded with the previously created notification Id

  @CP-25376 @CP-25376-06 @api-ecms-ineb @James
  Scenario: Verify in IN-EB notification status is error when French language is not a word document
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "FRENCH" language
    When I Create an Outbound Correspondence Request of "FRENCH" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the status of the notification and correspondence is "Error"

    # OB functionality not implemented in CoverVa
  #@CP-24510 @CP-24510-01 @api-ecms-coverva @James
  Scenario: Verify notification status is Exported when Channel is Mail and Send Immediately is True
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the notification was changed to "Assembled" from notification status history
    And I should see the status of the notification and correspondence is "Exported"

# OB functionality not implemented in CoverVa
  #@CP-24510 @CP-24510-02 @api-ecms-coverva @James
  Scenario: Verify notification is Exported and stored in onbase as a VACV Outbound Correspondence
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the notification was changed to "Assembled" from notification status history
    And I should see a file with the name of previously created notification Id is placed in the configured resource for "CoverVA" tenant

  @CP-24510 @CP-24510-03 @api-ecms-ineb @James
  Scenario: Verify in IN-EB notification status is Assembled when Channel is Mail and Send Immediately is True
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    And I should see the status of the notification and correspondence is "Assembled"

  @CP-2674 @CP-2674-01 @api-ecms-ineb @James
  Scenario: Verify in IN-EB able to generate a word template
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the status of the notification and correspondence is "Assembled"
    And I should see the "INEB Outbound Correspondence" has been uploaded with the previously created notification Id

  @CP-2674 @CP-2674-02 @api-ecms-ineb @James
  Scenario: Verify in IN-EB will not generate a word template if not a word document
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "FRENCH" language
    When I Create an Outbound Correspondence Request of "FRENCH" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the status of the notification and correspondence is "Error"

  @CP-2674 @CP-2674-03 @api-ecms-ineb @James
  Scenario: Verify INEB request with no notification Id will return error from assemble Outbound Correspondence end point
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have request to assemble an Outbound Correspondence with the following values
      | notificationId | null                       |
      | template       | previouslyCreated Template |
    When I attempt to assemble the Outbound Correspondence
    Then I should see the assemble Outbound Correspondence return an error

  @CP-2674 @CP-2674-04 @api-ecms-ineb @James
  Scenario: Verify INEB request with template that is not a word document will return error from assemble Outbound Correspondence end point
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have request to assemble an Outbound Correspondence with the following values
      | notificationId | previouslyCreated NID        |
      | template       | Template not a word document |
    When I attempt to assemble the Outbound Correspondence
    Then I should see the assemble Outbound Correspondence return an error

  @CP-30999 @CP-30999-1.1 @api-ecms-ineb @Keerthi
  Scenario: Verify in IN-EB notification status is Assembled when Channel is Mail, Send Immediately is True and PDF is generated
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    Then I should see the ObjectReceivedOn,UpdatedBy,UpdatedOn,notification and correspondence status as "Requested"
    Then I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the ObjectReceivedOn,UpdatedBy,UpdatedOn,notification and correspondence status as "Assembled"

  @CP-30999 @CP-30999-2.1 @api-ecms-ineb @Keerthi
  Scenario: Verify PDF generator end point with empty notificationid in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I send api call to generate pdf with "empty" notificationid and "currentuser" userid

  @CP-30999 @CP-30999-3.1 @api-ecms-ineb @Keerthi
  Scenario: Verify PDF generator end point with empty userid in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I send api call to generate pdf with "previouslycreated" notificationid and "empty" userid

  @CP-30999 @CP-30999-4.1 @api-ecms-ineb @Keerthi
  Scenario: Verify PDF generator end point with empty notificationid and userid in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I send api call to generate pdf with "empty" notificationid and "empty" userid

  @CP-30999 @CP-30999-5.1 @api-ecms-ineb @Keerthi
  Scenario: Verify PDF generator end point with null notificationid in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I send api call to generate pdf with "null" notificationid and "currentuser" userid

  @CP-30999 @CP-30999-6.1 @api-ecms-ineb @Keerthi
  Scenario: Verify PDF generator end point with null userid in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I send api call to generate pdf with "previouslycreated" notificationid and "null" userid

  @CP-30999 @CP-30999-7.1 @api-ecms-ineb @Keerthi
  Scenario: Verify PDF generator end point with null notificationid and userid in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I send api call to generate pdf with "null" notificationid and "null" userid

  @CP-30999 @CP-30999-8.1 @api-ecms-ineb @Keerthi
  Scenario: Verify PDF generator end point with invalid notificationid in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I send api call to generate pdf with "invalid" notificationid and "currentuser" userid

  @CP-30999 @CP-30999-9.1 @CP-32142-1 @api-ecms-ineb @Keerthi
  Scenario: Verify PDF generator end point with failure to generate PDF in IN-EB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "BENGALI" language
    When I Create an Outbound Correspondence Request of "BENGALI" language to be sent immediately
   # Then I should see the ObjectReceivedOn,UpdatedBy,UpdatedOn,notification and correspondence status as "Requested"
    Then I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the ObjectReceivedOn,UpdatedBy,UpdatedOn,notification and correspondence status as "Error"

  @CP-30999 @CP-30999-1.2 @API-ECMS @Keerthi
  Scenario: Verify in BLCRM notification status is Assembled when Channel is Mail, Send Immediately is True and PDF is generated
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    Then I should see the ObjectReceivedOn,UpdatedBy,UpdatedOn,notification and correspondence status as "Requested"
    Then I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the ObjectReceivedOn,UpdatedBy,UpdatedOn,notification and correspondence status as "Assembled"

  @CP-30999 @CP-30999-2.2 @API-ECMS  @Keerthi
  Scenario: Verify PDF generator end point with empty notificationid in BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then I send api call to generate pdf with "empty" notificationid and "currentuser" userid

  @CP-30999 @CP-30999-3.2  @API-ECMS @Keerthi
  Scenario: Verify PDF generator end point with empty userid in BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then I send api call to generate pdf with "previouslycreated" notificationid and "empty" userid

  @CP-30999 @CP-30999-4.2  @API-ECMS @Keerthi
  Scenario: Verify PDF generator end point with empty notificationid and userid in BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then I send api call to generate pdf with "empty" notificationid and "empty" userid

  @CP-30999 @CP-30999-5.2  @API-ECMS @Keerthi
  Scenario: Verify PDF generator end point with null notificationid in BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then I send api call to generate pdf with "null" notificationid and "currentuser" userid

  @CP-30999 @CP-30999-6.2  @API-ECMS @Keerthi
  Scenario: Verify PDF generator end point with null userid in BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then I send api call to generate pdf with "previouslycreated" notificationid and "null" userid

  @CP-30999 @CP-30999-7.2 @API-ECMS @Keerthi
  Scenario: Verify PDF generator end point with null notificationid and userid in BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then I send api call to generate pdf with "null" notificationid and "null" userid

  @CP-30999 @CP-30999-8.2  @API-ECMS @Keerthi
  Scenario: Verify PDF generator end point with invalid notificationid in BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then I send api call to generate pdf with "invalid" notificationid and "currentuser" userid

  @CP-30999 @CP-30999-9.2  @CP-32142-2 @API-ECMS @Keerthi
  Scenario: Verify PDF generator end point with failure to generate PDF in BLCRM
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "BENGALI" language
    When I Create an Outbound Correspondence Request of "BENGALI" language to be sent immediately
    Then I should see the ObjectReceivedOn,UpdatedBy,UpdatedOn,notification and correspondence status as "Requested"
    Then I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    Then I should see the ObjectReceivedOn,UpdatedBy,UpdatedOn,notification and correspondence status as "Error"

  @CP-10592 @CP-10592-08 @API-ECMS @Keerthi
  Scenario: Verify body data elements with valid schema from api in blcrm
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence with body data elements schema as "validschema"

  @CP-10592 @CP-10592-09 @API-ECMS @Keerthi
  Scenario: Verify body data elements with invalid object type from api in blcrm
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence with body data elements schema as "invalidobjecttype"

  @CP-10592 @CP-10592-10 @API-ECMS @Keerthi
  Scenario: Verify body data elements with invalid data type from api in blcrm
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence with body data elements schema as "invaliddatatype"

  @CP-10592 @CP-10592-11 @API-ECMS @Keerthi
  Scenario: Verify body data elements with invalid short text length from api in blcrm
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence with body data elements schema as "invalidshorttextlength"

  @CP-10592 @CP-10592-12 @API-ECMS @Keerthi
  Scenario: Verify body data elements with missing required elements from api in blcrm
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an Outbound Correspondence with body data elements schema as "missingrequiredbodydataelements"

  @CP-10592 @CP-10592-13 @api-ecms-ineb @Keerthi
  Scenario: Verify body data elements with valid schema from api in ineb
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Correspondence with body data elements schema as "validschema"

  @CP-10592 @CP-10592-14 @api-ecms-ineb @Keerthi
  Scenario: Verify body data elements with invalid object type from api in ineb
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Correspondence with body data elements schema as "invalidobjecttype"

  @CP-10592 @CP-10592-15 @api-ecms-ineb @Keerthi
  Scenario: Verify body data elements with invalid data type from api in ineb
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Correspondence with body data elements schema as "invaliddatatype"

  @CP-10592 @CP-10592-16 @api-ecms-ineb @Keerthi
  Scenario: Verify body data elements with invalid short text length from api in ineb
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Correspondence with body data elements schema as "invalidshorttextlength"

  @CP-10592 @CP-10592-17 @api-ecms-ineb @Keerthi
  Scenario: Verify body data elements with missing required elements from api in ineb
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Correspondence with body data elements schema as "missingrequiredbodydataelements"

  @CP-31002  @CP-31002-01 @CP-27875-1 @ui-ecms2  @Keerthi
  Scenario Outline: Verify in BLCRM notification status is Assembled when Channel is Mail and Send Immediately is True from CP
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I have selected an Outbound Correspondence through the UI "Test Generation - sendNow" as type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
   # And I should see an indicator appear in the center of the screen
    And I verify Outbound Correspondence Success Message
    #And I should see an indicator appear in the center of the screen
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  Canaan,NY 12029 |
    Then I verify "Correspondence assembled successfully" message in Outbound Correspondence details page
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence status is "Assembled"
    Then I verify the correspondence status reason is ""
    Then I validated for "Assembled" notification status
    And there is a Notification Object Uploaded onto Onbase for each notification
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |  | Canaan | New York | 12029   |

  @CP-31002 @CP-31002-02 @CP-27875-2 @ui-ecms2  @Keerthi
  Scenario Outline: Verify in BLCRM notification status is Error when Channel is Mail and Send Immediately is True from CP
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I logged into CRM
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I have selected an Outbound Correspondence through the UI "Test Generation - sendNow" as type
    And I have selected language for Outbound Correspondence type through the UI as "Bengali"
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
   # And I should see an indicator appear in the center of the screen
    #And I verify Outbound Correspondence Success Message
    #And I should see an indicator appear in the center of the screen
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  Canaan,NY 12029 |
    Then I verify "Error assembling correspondence" message in Outbound Correspondence details page
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence status is "Error"
    Then I verify the correspondence status reason is "Assembly Error"
    Then I validated for "Error" notification status
    And I validated for "Assembly Error" notification status reason
    And there is a no Notification Object Uploaded onto Onbase for each notification
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |   | Canaan | New York | 12029   |

  @CP-31002 @CP-31002-03 @CP-27875-3 @ui-ecms-ineb  @Keerthi
  Scenario Outline: Verify in IN-EB notification status is Assembled when Channel is Mail and Send Immediately is True from CP
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I have selected an Outbound Correspondence through the UI "Test Generation - sendNow" as type
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
   # And I should see an indicator appear in the center of the screen
    And I verify Outbound Correspondence Success Message
    #And I should see an indicator appear in the center of the screen
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  Canaan,NY 12029 |
    Then I verify "Correspondence assembled successfully" message in Outbound Correspondence details page
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence status is "Assembled"
    Then I verify the correspondence status reason is ""
    Then I validated for "Assembled" notification status
    And there is a Notification Object Uploaded onto Onbase for each notification
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |          | Canaan | New York | 12029   |

  @CP-31002 @CP-31002-04 @CP-27875-4 @ui-ecms-ineb  @Keerthi
  Scenario Outline: Verify in INEB notification status is Error when Channel is Mail and Send Immediately is True from CP
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I have selected an Outbound Correspondence through the UI "Test Generation - sendNow" as type
    And I have selected language for Outbound Correspondence type through the UI as "Bengali"
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
   # And I should see an indicator appear in the center of the screen
    #And I verify Outbound Correspondence Success Message
    #And I should see an indicator appear in the center of the screen
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  Canaan,NY 12029 |
    Then I verify "Error assembling correspondence" message in Outbound Correspondence details page
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    When I click on the "first available" Outbound Correspondence
    Then I verify the correspondence status is "Error"
    Then I verify the correspondence status reason is "Assembly Error"
    Then I validated for "Error" notification status
    And I validated for "Assembly Error" notification status reason
    And there is a no Notification Object Uploaded onto Onbase for each notification
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |          | Canaan | New York | 12029   |


  @CP-29152 @CP-29152-01 @api-ecms-ineb @James
  Scenario: Verify in IN-EB ObjectReceivedOn date is updated
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the status of the notification and correspondence is "Assembled"
    Then I verify the following values in the correspondence notification response
      | objectReceivedOn | updatedFromAssembly |

  @CP-29152 @CP-29152-02 @ui-ecms-ineb @James
  Scenario: Verify in IN-EB ObjectReceivedOn date is updated and verify in ui
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    And I should see the status of the notification and correspondence is "Assembled"
    And I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And I store the current count of windows open
    And I click on a view icon of "download icon" Outbound Search Results
    Then I should see the document viewer has opened

  @CP-30810 @CP-30810-01 @API-ECMS @Keerthi
  Scenario: Verify in BLCRM ObjectReceivedOn date is updated
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    Then I should see the status of the notification and correspondence is "Assembled"
    Then I verify the following values in the correspondence notification response
      | objectReceivedOn | updatedFromAssembly |

  @CP-30810 @CP-30810-02 @ui-ecms2 @Keerthi
  Scenario: Verify in BLCRM ObjectReceivedOn date is updated and verify in ui
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have an Outbound Definition that has Channel is Mail plus Send Immediately is True of "English" language
    When I Create an Outbound Correspondence Request of "English" language to be sent immediately
    And I send api call to generate pdf with "previouslycreated" notificationid and "currentuser" userid
    And I retrieve the Outbound Correspondence details again
    And I should see the status of the notification and correspondence is "Assembled"
    And I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And I store the current count of windows open
    And I click on a view icon of "download icon" Outbound Search Results
    Then I should see the document viewer has opened