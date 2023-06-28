Feature: User Will Validate Correspondence mail Destination City and Zipcode fields


  @CP-43469 @CP-43469-ineb1 @ui-ecms-ineb @keerthi
  Scenario Outline: Validating create correspondence successfully with City 45 characters and Zipcode 9 digits (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case Consumer Send Immediately - CCSENDIMM" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62,NY 20190-5896 |
    Examples:
      | address1        | address2 | CITY                                          | State    | zipcode   |
      | 123 main street |          | (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62 | New York | 201905896 |

  @CP-43469 @CP-43469-ineb2 @ui-ecms-ineb @keerthi
  Scenario Outline: Validating create correspondence with City > 45 characters and Zipcode with hyphen > 9 digits (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    And I have selected "Case Consumer Send Immediately - CCSENDIMM" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62,NY 20190-5896 |
    Examples:
      | address1        | address2 | CITY                                                         | State    | zipcode        |
      | 100 Main street |          | (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62_extracharcters | New York | 201-9058967568 |

  @CP-43469 @CP-43469-ineb3 @ui-ecms-ineb @keerthi
  Scenario Outline: Validating create correspondence options successfully with City < 45 characters and Zipcode field 5 characters (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case Consumer Send Immediately - CCSENDIMM" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  Alpharetta,GA 30005 |
    Examples:
      | address1        | CITY       | State   | zipcode |
      | 100 Main street | Alpharetta | Georgia | 30005   |


  @CP-43469 @CP-43469-ineb4 @ui-ecms-ineb @keerthi
  Scenario Outline: Validating create correspondence without city name (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I try to save but expect to get error message on city filed as I save

    Examples:
      | address1        | address2 | State    | zipcode |
      | 123 main street | 302      | Virginia | 20190   |

  @CP-43469 @CP-43469-ineb5 @ui-ecms-ineb @keerthi
  Scenario Outline: validating create correspondence without zipcode (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I expect to get error message on on Zipcode as I save
    Examples:
      | address1        | address2 | CITY   | State    |
      | 123 main street | 302      | Reston | Virginia |

  @CP-43469 @CP-43469-ineb6 @ui-ecms-ineb @keerthi
  Scenario Outline: validating create correspondence with invalid zipcode format (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I expect to get error message on invalid Zipcode format as I save
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 123 main street | 302      | Reston | Virginia | 123456  |

  @CP-43469 @CP-43469-ineb7 @ui-ecms-ineb @keerthi
  Scenario Outline: validating create correspondence with invalid zipcode digits (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on Case Consumer Search page
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I click on the first case and consumer search result
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I expect to get error message on on Zipcode as I save
    Examples:
      | address1        | address2 | CITY   | State    | zipcode  |
      | 123 main street | 302      | Reston | Virginia | @#$ @#abc |

  @CP-43469 @CP-43469-dceb1 @ui-ecms-dceb @keerthi
  Scenario Outline: Validating create correspondence successfully with City 45 characters and Zipcode 9 digits (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I run reevaluatepi API for V_2
    Then I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to manual case and consumer search page
    When I search just created consumer by external consumer ID
    When I navigate to case view page by clicking on DC caseId
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I verify Successfully saved OutboundCorrespondence
    Examples:
      | address1        | address2 | CITY                                          | State    | zipcode   |
      | 123 main street |          | (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62 | Virginia | 201905896 |

  @CP-43469  @CP-43469-dceb2 @ui-ecms-dceb @keerthi
  Scenario Outline: Validating create correspondence with City > 45 characters and Zipcode with hyphen > 9 digits (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I run reevaluatepi API for V_2
    Then I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to manual case and consumer search page
    When I search just created consumer by external consumer ID
    When I navigate to case view page by clicking on DC caseId
    And I create an Outbound Correspondence
    And I have selected "Case Consumer Send Immediately - CCSENDIMM" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62,NY 20190-5896 |
    Examples:
      | address1        | address2 | CITY                                                         | State    | zipcode        |
      | 100 Main street |          | (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62_extracharcters | New York | 201-9058967568 |

  @CP-43469  @CP-43469-dceb3 @ui-ecms-dceb @keerthi
  Scenario Outline: Validating create correspondence options successfully with City < 45 characters and Zipcode field 5 characters (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I run reevaluatepi API for V_2
    Then I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to manual case and consumer search page
    When I search just created consumer by external consumer ID
    When I navigate to case view page by clicking on DC caseId
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I verify Successfully saved OutboundCorrespondence
    Examples:
      | address1        | CITY   | State    | zipcode |
      | 123 main street | Reston | Virginia | 58965   |


  @CP-43469  @CP-43469-dceb4 @ui-ecms-dceb @keerthi
  Scenario Outline: Validating create correspondence without city name (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I run reevaluatepi API for V_2
    Then I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to manual case and consumer search page
    When I search just created consumer by external consumer ID
    When I navigate to case view page by clicking on DC caseId
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I try to save but expect to get error message on city filed as I save

    Examples:
      | address1        | address2 | State    | zipcode |
      | 123 main street | 302      | Virginia | 20190   |

  @CP-43469  @CP-43469-dceb5 @ui-ecms-dceb @keerthi
  Scenario Outline: validating create correspondence without zipcode (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I run reevaluatepi API for V_2
    Then I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to manual case and consumer search page
    When I search just created consumer by external consumer ID
    When I navigate to case view page by clicking on DC caseId
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I expect to get error message on on Zipcode as I save
    Examples:
      | address1        | address2 | CITY   | State    |
      | 123 main street | 302      | Reston | Virginia |

  @CP-43469  @CP-43469-dceb6 @ui-ecms-dceb @keerthi
  Scenario Outline: validating create correspondence with invalid zipcode format (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I run reevaluatepi API for V_2
    Then I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to manual case and consumer search page
    When I search just created consumer by external consumer ID
    When I navigate to case view page by clicking on DC caseId
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I expect to get error message on invalid Zipcode format as I save
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 123 main street | 302      | Reston | Virginia | 123456  |

  @CP-43469  @CP-43469-dceb7 @ui-ecms-dceb @keerthi
  Scenario Outline: validating create correspondence with invalid zipcode digits (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "39968-01" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MMIS     |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    And I run reevaluatepi API for V_2
    Then I logged into CRM and select a project "DC-EB"
    And I minimize Genesys popup if populates
    When I navigate to manual case and consumer search page
    When I search just created consumer by external consumer ID
    When I navigate to case view page by clicking on DC caseId
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I expect to get error message on on Zipcode as I save
    Examples:
      | address1        | address2 | CITY   | State    | zipcode  |
      | 123 main street | 302      | Reston | Virginia | @#$ @#abc |

