Feature: Inbound Correspondence stored in S3 Buckets

  @CP-17552  @ui-ecms-nj  @James @CP-17552-01
  Scenario: Verify NJ-SBE Inbound Documents are automatically deleted after 5 minutes
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | NJ SBE General         |
      | Channel      | Mail                   |
      | Status       | COMPLETE               |
      | Origin       | WEB PORTAL             |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Given I logged into CRM and select a project "NJ-SBE"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | InboundDocument |
    And I click on the Inbound Document CID link "firstAvailable"
    When I click on the get image button on the Inbound Document Details Page
    And I paste the path into the note field to verify it on the Inbound Document Details Page
    And I wait for five minutes after clicking on get image button on the Inbound Document Details Page
    Then I should see that the Inbound Document is no longer available in the Simple Storage Service bucket

  @CP-20294 @ui-ecms-coverva @James @CP-20294-01
  Scenario: Verify CoverVA Inbound Documents are automatically deleted after 5 minutes
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Appeal"
    And I logged into CRM with "Service Tester 2" and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    When I search for an Inbound Document by the following values
      | CID | fromRequest |
    And I click on the Inbound Document CID link "firstAvailable"
    When I click on the get image button on the Inbound Document Details Page
    And I paste the path into the note field to verify it on the Inbound Document Details Page
    And I wait for five minutes after clicking on get image button on the Inbound Document Details Page
    Then I should see that the Inbound Document is no longer available in the Simple Storage Service bucket
