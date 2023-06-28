Feature: Create Outbound Correspondence from Connection Point

# link section validation removed from create OB request page CP-28601
  @CP-16762 @CP-16762-01  @ui-ecms1 @James
  Scenario Outline: Verify contact record linked to OB Correspondence for consumers calling on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I navigate to the Create Outbound Correspondence page
    And I select an Outbound Correspondence Type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I verify Outbound Correspondence Successfully saved
    Then I navigate to the case and contact details
    And I click on the Outbound Correspondence Id result
    And I store Contact Record ID from Outbound Correspondence details page
    Then I should see that a link from contact record to Outbound Correspondence has been created
    Then I should see that a link from Outbound Correspondence to contact record has been created
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-16762 @CP-16762-02  @ui-ecms1 @James
  Scenario: Verify contact record linked to OB Correspondence for consumers calling NOT on a case
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And "Regarding" sections are hidden
    And "Body Data" sections are hidden
    And I have selected "Consumer - CONONLY" as a type
    When I click to save the Outbound Correspondence Request
    Then I navigate to the case and contact details
    And I click on the Outbound Correspondence Id result
    And I store Contact Record ID from Outbound Correspondence details page
    Then I should see that a link from contact record to Outbound Correspondence has been created
    Then I should see that a link from Outbound Correspondence to contact record has been created


  @CP-16762 @CP-16762-03  @ui-ecms1 @James
  Scenario: Verify contact record linked to OB Correspondence links are created
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    And I create an Outbound Correspondence
    And "Regarding" sections are hidden
    And "Body Data" sections are hidden
    And I have selected "Consumer - CONONLY" as a type
    When I click to save the Outbound Correspondence Request
    Then I navigate to the case and contact details
    And I click on the Outbound Correspondence Id result
    And I store Contact Record ID from Outbound Correspondence details page
    Then I should see that a link from contact record to Outbound Correspondence has been created
    Then I should see that a link from Outbound Correspondence to contact record has been created
    And I retrieve the "OUTBOUND_CORRESPONDENCE_SAVE_EVENT" event that is produced by trace id
    Then I should see the payload for the "LINK_EVENT_OB_CONTACTRECORD" is as expected

    ############################################################################## CP-10711 ###############################################################################################

  @CP-10711 @CP-10711-01 @ui-ecms2 @burak
  Scenario: Verify user navigated back to the CONTACT_HISTORY_PAGE after clicking back arrow on create correspondence page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I logged into CRM and click on initiate contact
    And I minimize Genesys popup if populates
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "No Keys - NOKEYS" as a type
    And I navigate away by clicking the back arrow on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Continue" button on warning message OC request
    Then I verify I navigated back to "CONTACT_HISTORY_PAGE"

  @CP-10711 @CP-10711-02 @ui-ecms2 @burak
  Scenario: Verify user navigated back to the TASK_DETAILS_PAGE after clicking back arrow on create correspondence page (from my Task - context of consumer on a case)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13241             |
    Then I send the request for the External task endpoint
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I create an Outbound Correspondence
    And I have selected "No Keys - NOKEYS" as a type
    And I navigate away by clicking the back arrow on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Continue" button on warning message OC request
    #Then I verify I navigated back to "TASK_DETAILS_PAGE"
    And I verify should I navigate to service request details
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider

  @CP-10711 @CP-10711-03 @ui-ecms2 @burak
  Scenario: Verify user navigated back to the create APPLICATION_TRACKING_PAGE after clicking back arrow on create correspondence page
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd | submittedInd | applicationSignatureDate | applicationSignatureExistsInd |
      | Medical Assistance | CURRENT YYYYMMDD        | false          | true         | CURRENT YYYYMMDD         | true                          |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  |
      | cellPhoneNumber | RANDOM PHONE |
    And I POST ATS save submit application api and store appId and response in list
    Then I get response from application ats api with status code "200" and status "success"
    And I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I create an Outbound Correspondence
    And I have selected "No Keys - NOKEYS" as a type
    And I navigate away by clicking the back arrow on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Continue" button on warning message OC request
    Then I verify I navigated back to "APPLICATION_TRACKING_PAGE"

  @CP-10711 @CP-10711-04 @ui-ecms2 @burak
  Scenario: Verify user navigated back to the TASK_DETAILS_PAGE after clicking back arrow on create correspondence page (from my Task - Active context of consumer on a case)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then If any In Progress task present then update that to Cancelled
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given I logged into CRM and click on initiate contact
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I minimize Genesys popup if populates
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 13241             |
    Then I send the request for the External task endpoint
    When I navigate to "Task Search" page
    And I will search for the task created for SR
    And I click on initiate button in task search page
    And I click on the priority in dashboard
    And I Verify Task slider is collasped
    And I create an Outbound Correspondence
    And I have selected "No Keys - NOKEYS" as a type
    And I navigate away by clicking the back arrow on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Continue" button on warning message OC request
   # Then I verify I navigated back to "TASK_DETAILS_PAGE"
    And I verify should I navigate to service request details
    And I click on the priority in dashboard
    And I Verify Task slider is Expand
    And I update the task status in task slider as "Complete"
    And I click on save in Task Slider

  @CP-10711 @CP-10711-05 @ui-ecms2 @burak
  Scenario: Verify user navigated back to the CASE_CONSUMER_DETAILS_PAGE after clicking back arrow on create correspondence page
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "No Keys - NOKEYS" as a type
    And I navigate away by clicking the back arrow on OC request
    Then I verify navigation warning message displayed on OC request
    When I click on "Continue" button on warning message OC request
    Then I verify I navigated back to "CASE_CONSUMER_DETAILS_PAGE"