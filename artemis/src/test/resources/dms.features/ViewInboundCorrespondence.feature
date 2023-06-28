Feature: View Inbound Correspondence Summary Information

  @CP-3002-01 @CP-3002 @ECMS-SMOKE @ui-ecms1 @albert
  Scenario Outline: Verify that CID is visible for each inbound correspondence in case and contact details tab
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "<First Name>" and Last Name as "<Last Name>"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    Then I should see the CID for the correspondence as a hyperlink
    Examples:
      | First Name    | Last Name  |
      | Bruce | Wayne  |

  @CP-3002-02 @CP-3002 @ui-ecms1 @albert
  Scenario Outline: Verify that Correspondence Type is visible
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "<First Name>" and Last Name as "<Last Name>"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    Then I should see the type is visible for each correspondence
    Examples:
      | First Name    | Last Name  |
      | Bruce | Wayne  |

  @CP-3002-03 @CP-3002 @ui-ecms1 @albert
  Scenario Outline: Verify that the date inbound correspondence is received is in MM/DD/YYYY format
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "<First Name>" and Last Name as "<Last Name>"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    Then I should see the date inbound correspondence is received is in the correct format for each correspondence
    Examples:
      | First Name    | Last Name  |
      | Bruce | Wayne  |

  @CP-3002-04 @CP-3002 @ui-ecms1 @albert
  Scenario Outline: Verify that First and Last names of Case members associated are visible.
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "<First Name>" and Last Name as "<Last Name>"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    Then I should see the First and Last names of Case members associated are visible.
    Examples:
      | First Name    | Last Name  |
      | Bruce | Wayne  |

  @CP-3002-05 @CP-3002 @ui-ecms1 @albert
  Scenario Outline: Verify that if there is more than one Case member on the case, name field will display first name then ellipsis pop up with the rest
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "<First Name>" and Last Name as "<Last Name>"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    Then I should see if there is more than one Case member on the case, name field will display first name then ellipsis pop up with the rest
    Examples:
      | First Name    | Last Name  |
      | Bruce | Wayne  |

  @CP-3002-06 @CP-3002 @ui-ecms1 @albert
  Scenario Outline: Verify that initial display will show 5 most recent inbound correspondence
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "<First Name>" and Last Name as "<Last Name>"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    Then I should see that initial display will show 5 most recent inbound correspondence
    Examples:
      | First Name    | Last Name  |
      | Bruce | Wayne  |

  @CP-3002-07 @CP-3002 @ui-ecms1 @albert
  Scenario Outline: Verify that screen invokes BFF api which invokes Onbase api to retrieve the correspondence items associated with the case and consumer profile, passing either the case id or consumer id
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "<First Name>" and Last Name as "<Last Name>"
    And I link the contact to an existing Case or Consumer Profile
    When I navigate to the case and contact details tab
    Then I should see that screen invokes BFF api which invokes Onbase api to retrieve the correspondence items associated with the case and consumer profile, passing either the case id or consumer id
    Examples:
      | First Name    | Last Name  |
      | Bruce | Wayne  |

  @CP-16018-01 @CP-16018 @ui-ecms1 @Prithika
  Scenario: Verify Inbound Correspondence user ability to Edit
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I should see the cancel and save button is visible on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "pastdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button


  @CP-16018-02 @CP-16018 @ui-ecms1 @Prithika
  Scenario: Verify Inbound Correspondence user Inability to Edit
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM with "Service Account 2" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest|
    When I click on the Inbound Document CID link "firstAvailable"
    And I should not be able to invoke edit button on Inbound Settings Page

  @CP-15478 @ui-ecms2 @Prithika
  Scenario: Add Edit Permission for Inbound Correspondence to Tenant Manager Permission Functionality
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand first project to view the details
    And I navigate to project role search page
    When I enter search criteria for a role by "Role Name" value "Csr"
    And I click on search button on role search page
    Then Exact match role names are displayed in search results for role name "Csr"
    Then I click on first role to open role details page
    And I select page "inbound-correspondence" from Filter by Page dropdown
    Then Verify inbound-correspondence option is displayed in result table


  @CP-3257 @CP-3257-01 @ui-ecms2 @Prithika
  Scenario: 5.0 Option to Cancel
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the edit button on the Inbound Document Details Page is available
    Then I save the field data on inbound correspondence
    And I click on the edit button on the Inbound Document Details Page
    Then I should see the cancel and save button is visible on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "maersk Case + Consumers" via "null"
    And I click the cancel button on the Inbound Correspondence Detail Page
    Then I validate that field data did not change

  @CP-3257 @CP-3257-02 @ui-ecms2 @Prithika
  Scenario:  4.0 Warn of Unsaved Changes -click Cancel button on Warning message
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the edit button on the Inbound Document Details Page is available
    Then I save the field data on inbound correspondence
    And I click on the edit button on the Inbound Document Details Page
    Then I should see the cancel and save button is visible on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "maersk Case + Consumers" via "null"
    When I attempt to navigate away from the page
    Then I should see a warning message allowing me to either discard or cancel changes
    And I click cancel in Warning Message popup
    And I verified that user is on "Edit Inbound Correspondence Details" page title
    And I click the cancel button on the Inbound Correspondence Detail Page
    Then I validate that field data did not change

  @CP-3257 @CP-3257-03 @ui-ecms2 @Prithika
  Scenario:  4.0 Warn of Unsaved Changes -click Continue button
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "maersk Case + Consumer"
    Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I should see the edit button on the Inbound Document Details Page is available
    Then I save the field data on inbound correspondence
    And I click on the edit button on the Inbound Document Details Page
    Then I should see the cancel and save button is visible on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "maersk Case + Consumers" via "null"
    When I attempt to navigate away from the page
    Then I should see a warning message allowing me to either discard or cancel changes
    And I click continue in Warning Message popup
    And I verified that user is on "Inbound Correspondence Search" page title
    When I click on the Inbound Document CID link "firstAvailable"
    Then I validate that field data did not change

  @CP-3257 @CP-3257-04 @ui-ecms2 @Prithika
  Scenario: 3.0 Selecting Save 6.0 Verify Creation of IB Correspondence Update Event To DPBI
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I logged into CRM and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "receiveddate" field with value "currentdate" via "calendarwidget"
    And I click on Edit Inbound Correspondence details Page Save button
    And I retrieve the "INBOUND_CORRESPONDENCE_UPDATE_EVENT" event that is produced by trace id
