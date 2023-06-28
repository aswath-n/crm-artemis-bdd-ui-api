@CP-22889
Feature: Validate Endpoint to Retrieve Inbound Correspondence Edit History to Type

# BLCRM tenant
  @CP-22889 @CP-22889-1 @ui-ecms2 @Keerthi
  Scenario: Validate Edit History to Type Endpoint for BLCRM tenant
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | fileType     | pdf                     |
      | documentType | maersk Case + Consumer |
      | Language     | SPANISH                 |
      | Channel      | EMAIL                   |
      | ProcessType  | INBOUND CORRESPONDENCE  |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "maersk Case" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    And I Validate Endpoint to Retrieve Inbound Correspondence Edit History to Type for "InboundDocument"
      | fieldLabel    | Type                    |
      | previousValue | maersk Case + Consumer |
      | updatedValue  | maersk Case            |
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "maersk Eligibility" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    And I Validate Endpoint to Retrieve Inbound Correspondence Edit History to Type for "InboundDocument"
      | fieldLabel    | Type                |
      | previousValue | maersk Case        |
      | updatedValue  | maersk Eligibility |

    # coverVA tenant
  @CP-22889 @CP-22889-2  @ui-ecms-coverva  @Keerthi
  Scenario: Validate Edit History to Type Endpoint for coverVA tenant
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | VACV Newborn Form      |
      | Channel      | WEB PORTAL             |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM with "Service Tester 2" and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "VACV Appeal" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    And I Validate Endpoint to Retrieve Inbound Correspondence Edit History to Type for "InboundDocument"
      | fieldLabel    | Type              |
      | previousValue | VACV Newborn Form |
      | updatedValue  | VACV Appeal       |
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "VACV Application Renewal" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    And I Validate Endpoint to Retrieve Inbound Correspondence Edit History to Type for "InboundDocument"
      | fieldLabel    | Type                     |
      | previousValue | VACV Appeal              |
      | updatedValue  | VACV Application Renewal |

    # NJSBE tenant
  @CP-22889 @CP-22889-3  @ui-ecms-nj   @Keerthi
  Scenario: Validate Edit History to Type Endpoint for NJ-SBE tenant
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType | NJ SBE Appeal Form     |
      | Channel      | MAIL                   |
      | Status       | COMPLETE               |
      | FromName     | AUTOMATION             |
      | ProcessType  | INBOUND CORRESPONDENCE |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Then I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "NJ SBE Application" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    And I Validate Endpoint to Retrieve Inbound Correspondence Edit History to Type for "InboundDocument"
      | fieldLabel    | Type               |
      | previousValue | NJ SBE Appeal Form |
      | updatedValue  | NJ SBE Application |
    And I click on the edit button on the Inbound Document Details Page
    Then I update the Inbound Correspondence "type" field with value "NJ SBE General" via "null"
    And I click on Edit Inbound Correspondence details Page Save button
    And I Validate Endpoint to Retrieve Inbound Correspondence Edit History to Type for "InboundDocument"
      | fieldLabel    | Type               |
      | previousValue | NJ SBE Application |
      | updatedValue  | NJ SBE General     |

