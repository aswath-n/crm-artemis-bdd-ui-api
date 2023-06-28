@CP-21592
Feature: Display All Keyword Record Data in Metadata Section

  @CP-21592-01 @ui-ecms2 @Prithika
  Scenario: Create Inbound Correspondence record from Onbase using API and validate the Metadata Information in CP UI
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType         | maersk Case + Consumer |
      | CaseID               | 2205                    |
      | ConsumerID           | 3335,17519              |
      | ProcessType          | INBOUND CORRESPONDENCE  |
      | FromEmailAddress     | Test@AUTOMATION.com     |
      | FromPhoneNumber      | 5566466808              |
      | Status               | COMPLETE                |
      | Channel              | MAIL                    |
      | MAIL From Address    | 1234 St1                |
      | MAIL From            | Tester                  |
      | MAIL MessageID       | 123                     |
      | MAIL Subject         | Test                    |
      | MAIL Bcc             | Test@test.com           |
      | MAIL Cc              | Test@test.com           |
      | MAIL Cc Address      | T1234 St1               |
      | MAIL Certified       | 12333                   |
      | MAIL State           | 12333                   |
      | MAIL To              | Tester B                |
      | MAIL To Address      | 234 St1                 |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I logged into CRM
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click MetaDataTab On Inbound Correspondence Detail Page
    And I retrieve MetaData Information
    Then I verify MetaData Information matches with Inbound document created

 #CoverVA Tenant
  @CP-21592-02 @ui-ecms-coverva @Prithika
  Scenario: Create Inbound Correspondence record from Onbase using API and validate the Metadata Information in CP UI
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType                | VACV Newborn Form                  |
      | Channel                     | Web Portal                         |
      | VACV Newman Extract Status  | READY                              |
      | ProcessType                 | INBOUND CORRESPONDENCE             |
      | Start Date                  | 2021-11-22                         |
      | Residence Address Line 1    | 1234 St1                           |
      | Residence Address Line 2    | 1234 line2                         |
      | Residence Address City      | ABC                                |
      | Residence Address State     | VA                                 |
      | Residence Address Zip       | 89768                              |
      | Lives In Household          | 4                                  |
      | Address Line 1              | Address Test Line 1                |
      | Address Line 2              | Address Test Line 2                |
      | Address City                | ABCCITY                            |
      | Address State               | VA                                 |
      | Address Zip                 | 89768                              |
      | VACV Language               | ENG                                |
      | Home Phone Number           | 3604125022                         |
      | Provider Name               | VIRGINIA HOSPITAL CENTER ARLINGTON |
      | NPI                         | 1234                               |
      | Submitter Name              | TesterABC                          |
      | Submitter Title             | Mr                                 |
      | Work Phone Number           | 3100487097                         |
      | Email Address               | test@test.com                      |
      | FIPS                        | 9544                               |
      | VACV LDSS Locality          | William County                     |
      | Source                      | System                             |
      | External Case ID            | 1234                               |
    And I have the request contains Meta Data Records for Document Type "VACV Newborn Form" with random data instances for the following
      | VACVNewborn        | 3 |
      | VACVNewbornParents | 0 |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    Given I logged into CRM and select a project "COVER-VA"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | InboundDocument |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click MetaDataTab On Inbound Correspondence Detail Page
    And I retrieve MetaData Information
    And I retrieve MetaData Records
    Then I verify MetaData Information matches with Inbound document created
    Then I verify MetaData Records matches with Inbound document created

  @CP-17034 @ui-ecms-coverva @Prithika
  Scenario:Add metadataRecords to DPBI inbound correspondence save event
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a request to create an Inbound Document using the endpoint for Digital with the following values
      | documentType             | VACV Newborn Form       |
      | ProcessType              | INBOUND CORRESPONDENCE  |
      | Channel                  | MAIL                    |
      | Residence Address Line 1 | 1192 Arin Day Dr        |
      | Residence Address Line 2 | Country RD              |
      | Residence Address City   | Reston                  |
      | Residence Address State  | VA                      |
      | Residence Address Zip    | 29019                   |
      | Address Line 1           | 1192 Arin Day Dr        |
      | Address Line 2           | Country RD              |
      | Address City             | Reston                  |
      | Address State            | VA                      |
      | Address Zip              | 29019                   |
      | Home Phone Number        | 123-456-4444            |
      | Email Address            | a@amail.com             |
    And I have the following metaDataRecords to add to the request for document type "VACV Newborn Form Member"
      | First Name               | John             |
      | Last Name                | Doe              |
      | Middle Initial           | F                |
      | DOB                      | 2/27/1980        |
      | Is Primary               | YES              |
      | SSN                      | 123-45-0000      |
      | External Consumer ID     | MED0102030405    |
      | Relationship             | Father           |
    When I send the request for the Inbound Correspondence using the endpoint for Digital
    And I have a request to send Events - VACV Translation Request from Inbound Document Request
    Then I retrieve payload from get event by traceId for event name "INBOUND_CORRESPONDENCE_INDEXED_EVENT"
    Then I verify payload data matches inbound request