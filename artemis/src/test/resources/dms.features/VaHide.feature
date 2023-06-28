Feature: Cover Va Tenant Configuration

  @CP-19562 @CP-19562-1 @ui-ecms-coverva @James
  Scenario: Verify Case Id and Consumer Id will not be search options in Inbound Search
    Given I logged into CRM with "Service Tester 2" and select a project "COVER-VA"
    And I click on the main navigation menu
    When I should see the Inbound Correspondence Search Icon
    Then I should see that the following items are not visible for the CoverVA tenant on the Inbound Search Screen
      | Case ID     |
      | Consumer ID |
    And I should see all the labels have following values
      | CORRESPONDENCE ID     |
      | CORRESPONDENCE SET ID |
      | TYPE                  |
      | STATUS                |
      | DATE RECEIVED         |
      | CHANNEL               |
      | FROM PHONE            |
      | FROM EMAIL ADDRESS    |
      | FROM                  |
      | ORIGIN                |
      | ORIGINAL CID          |
      | CORRESPONDENCE SET ID |

  @CP-19562 @CP-19562-2 @ui-ecms-coverva @James
  Scenario: Verify Case Id and Consumer Id will not be search columns in results for Inbound Search
    Given I logged into CRM and select a project "COVER-VA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | FROMNAME | Automation |
    Then I should see that the following items are not visible for the CoverVA tenant on the Inbound Search Screen
      | Case ID RESULTS COLUMN     |
      | Consumer ID RESULTS COLUMN |
    And I should see that the column names for the search results have the following values
      | CID      |
      | SET ID   |
      | RECEIVED |
      | TYPE     |
      | STATUS   |
      | CHANNEL  |
      | FROM     |


  @CP-22617 @CP-22617-1 @ui-ecms-coverva  @Keerthi
  Scenario: Hide Case Search Fields in Inbound Correspondence Details Case & Consumer Search
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I select "Case/Consumer" option from Create Links(s) dropdown
    Then I should see that the following items are not visible for the CoverVA tenant on the Inbound Search Screen
      | SEARCH CASE DROPDOWN |
      | CRM CASE ID          |


  @CP-23291 @CP-23291-01 @ui-ecms-coverva @RuslanL
  Scenario: Verify the system save Inbound Correspondence to Network Folder for Cover VA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Application"
    Given I logged into CRM with "Service Account 1" and select a project "CoverVA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I minimize Genesys popup if populates
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I should see that there is a "GET IMAGE LOCATION" button
    When I click on he Get Image Location button
    Then I should see "Document Path Copied to Clipboard" pop up message
    And I should be navigated to image viewer
    And I close image viewer
    Then I should see the path of the image is copied into the user's clipboard
    When  I search for that filepath network location with value as "fromRequest" to get credentials
    Then I will verify the document matches "nonprod\QE\ECMSFiles" and is Successful

  @CP-23291 @CP-23291-02 @ui-ecms-nj @RuslanL
  Scenario: Verify the system save Inbound Correspondence to Network Folder for NJ-SBE
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "NJ SBE General"
    Given I logged into CRM with "Service Account 1" and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I minimize Genesys popup if populates
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I should see that there is a "GET IMAGE LOCATION" button
    When I click on he Get Image Location button
    Then I should see "Document Path Copied to Clipboard" pop up message
    And I should be navigated to image viewer
    And I close image viewer
    Then I should see the path of the image is copied into the user's clipboard
    When  I search for that filepath network location with value as "fromRequest" to get credentials
    Then I will verify the document matches "nonprod\QE\ECMSFiles" and is successful


    ################################################### CP-37215 #########################################################

  @CP-37215 @CP-37215-1 @ui-ecms-coverva @Keerthi
  Scenario: Verify hiding of VACV Application and VACV Verification Document Type in the Inbound Correspondence Details screen
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    Then I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I minimize Genesys popup if populates
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    Then I click on the edit button on the Inbound Document Details Page
    And I validate following values unavailable in "type" dropdown in IB correspondence details page
      | VACV Application           |
      | VACV Verification Document |


  @CP-37215 @CP-37215-2 @ui-ecms-coverva @Keerthi
  Scenario: Verify VACV Application Type displayed in the Inbound Correspondence search screen
    Then I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I minimize Genesys popup if populates
    And I search for an Inbound Document by the following values
      | CORRESPONDENCETYPE | VACV Application |
    And I should see that all the inbound correspondence search results have the following values
      | CORRESPONDENCETYPE | VACV Application |

  @CP-37215 @CP-37215-3 @ui-ecms-coverva @Keerthi
  Scenario: Verify VACV Verification Document Type displayed in the Inbound Correspondence search screen
    Then I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I minimize Genesys popup if populates
    And I search for an Inbound Document by the following values
      | CORRESPONDENCETYPE | VACV Verification Document |
    And I should see that all the inbound correspondence search results have the following values
      | CORRESPONDENCETYPE | VACV Verification Document |

  @CP-37215 @CP-37215-4 @ui-ecms-coverva @Keerthi
  Scenario: Verify VACV Application and VACV Verification Document Type displayed in TM Outbound definition screen
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectCOVERVAConfig"
    When I expand first project to view the details
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I validate following IB types available in Outbound Correspondence Definition page
      | VACV Application           |
      | VACV Verification Document |

  @CP-37215 @CP-37215-5 @ui-ecms-coverva @Keerthi
  Scenario: Verify VACV Application and VACV Verification Document Type displayed in TM Inbound definition screen
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectCOVERVAConfig"
    When I expand first project to view the details
    And I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    And I click add button on Inbound Correspondence Definition
    Then I validate and click Add Rule button on Inbound Correspondence Definition
    And I add a new Inbound Correspondence Definition Task Rules with given Values
      | SUPPRESS IF SET HAS DOCUMENT OF THESE TYPES | VACV Application,VACV Verification Document |


  @CP-37215 @CP-37215-6 @ui-ecms-coverva @Keerthi
  Scenario: Verify VACV Application and VACV Verification Document Type displayed in TM Inbound Configuration screen
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectCOVERVAConfig"
    When I expand first project to view the details
    And I navigate to the Inbound Correspondence Settings Screen of Project:"current"
    And I click on edit button on Inbound Settings Page
    And I validate following IB types available in Inbound configuration page
      | VACV Application           |
      | VACV Verification Document |
