Feature: DC-EB Consumer: Part 2 MMIS Sends Current and Prior Medicaid IDs

  @dc-eb-event @CP-47977 @CP-47977-01-01 @muhabbat @events @dc-eb-event
  Scenario: Match on both Medicaid IDs for a single consumer (repeat request) - Full match
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "47977-01-01-1" with following payload
      | consumerRequests[0].uuid                                | random     |
      | consumerRequests[0].dataSource                          | MMIS       |
      | consumerRequests[0].firstName                           | random     |
      | consumerRequests[0].lastName                            | random     |
      | consumerRequests[0].middleName                          | random     |
      | consumerRequests[0].suffix                              | DDS        |
      | consumerRequests[0].ssn                                 | random     |
      | consumerRequests[0].dateOfBirth                         | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type                | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId          | random     |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId     | random     |
      | consumerRequests[0].case.externalId                     | random     |
      | consumerRequests[0].sexCode                             | Unknown    |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | random     |
      | consumerRequests[0].contact.addresses[0].addressZip     | random     |
      | consumerRequests[0].contact.phones[0].phoneNumber       | random     |
      | consumerRequests[0].contact.emails[0].emailAddress      | random     |
    When I update just created prior id "47977-01-01-2" case consumer
      | consumerRequests[0].uuid                                | random                                                                |
      | consumerRequests[0].priorConsumerProfile.externalId     | 47977-01-01-1.consumerRequests[0].priorConsumerProfile.externalId     |
      | consumerRequests[0].consumerProfile.externalId          | 47977-01-01-1.consumerRequests[0].consumerProfile.externalId          |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID                                                              |
      | consumerRequests[0].consumerProfile.type                | MEDICAID                                                              |
      | consumerRequests[0].dataSource                          | 47977-01-01-1.consumerRequests[0].dataSource                          |
      | consumerRequests[0].firstName                           | 47977-01-01-1.consumerRequests[0].firstName                           |
      | consumerRequests[0].lastName                            | 47977-01-01-1.consumerRequests[0].lastName                            |
      | consumerRequests[0].middleName                          | 47977-01-01-1.consumerRequests[0].middleName                          |
      | consumerRequests[0].suffix                              | 47977-01-01-1.consumerRequests[0].suffix                              |
      | consumerRequests[0].ssn                                 | 47977-01-01-1.consumerRequests[0].ssn                                 |
      | consumerRequests[0].dateOfBirth                         | 47977-01-01-1.consumerRequests[0].dateOfBirth                         |
      | consumerRequests[0].case.externalId                     | 47977-01-01-1.consumerRequests[0].case.externalId                     |
      | consumerRequests[0].sexCode                             | 47977-01-01-1.consumerRequests[0].sexCode                             |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | 47977-01-01-1.consumerRequests[0].contact.addresses[0].addressStreet1 |
      | consumerRequests[0].contact.addresses[0].addressZip     | 47977-01-01-1.consumerRequests[0].contact.addresses[0].addressZip     |
      | consumerRequests[0].contact.phones[0].phoneNumber       | 47977-01-01-1.consumerRequests[0].contact.phones[0].phoneNumber       |
      | consumerRequests[0].contact.emails[0].emailAddress      | 47977-01-01-1.consumerRequests[0].contact.emails[0].emailAddress      |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 47977-01-01-1.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1968-12-11                                                |
      | dataSource         | MEDICAID                                                  |
      | profileId          | MEDICAID                                                  |


  @dc-eb-event @CP-47977 @CP-47977-01-02 @muhabbat @events @dc-eb-event
  Scenario: Match on both Medicaid IDs for a single consumer - Not matching on Contact info
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "47977-01-02-1" with following payload
      | consumerRequests[0].uuid                                | random     |
      | consumerRequests[0].dataSource                          | MMIS       |
      | consumerRequests[0].firstName                           | random     |
      | consumerRequests[0].lastName                            | random     |
      | consumerRequests[0].middleName                          | random     |
      | consumerRequests[0].suffix                              | DDS        |
      | consumerRequests[0].ssn                                 | random     |
      | consumerRequests[0].dateOfBirth                         | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type                | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId          | random     |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId     | random     |
      | consumerRequests[0].case.externalId                     | random     |
      | consumerRequests[0].sexCode                             | Unknown    |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | random     |
      | consumerRequests[0].contact.addresses[0].addressZip     | random     |
      | consumerRequests[0].contact.phones[0].phoneNumber       | random     |
      | consumerRequests[0].contact.emails[0].emailAddress      | random     |
    When I update just created prior id "47977-01-02-2" case consumer
      | consumerRequests[0].uuid                                | random                                                            |
      | consumerRequests[0].priorConsumerProfile.externalId     | 47977-01-02-1.consumerRequests[0].priorConsumerProfile.externalId |
      | consumerRequests[0].consumerProfile.externalId          | 47977-01-02-1.consumerRequests[0].consumerProfile.externalId      |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID                                                          |
      | consumerRequests[0].consumerProfile.type                | MEDICAID                                                          |
      | consumerRequests[0].dataSource                          | 47977-01-02-1.consumerRequests[0].dataSource                      |
      | consumerRequests[0].firstName                           | 47977-01-02-1.consumerRequests[0].firstName                       |
      | consumerRequests[0].lastName                            | 47977-01-02-1.consumerRequests[0].lastName                        |
      | consumerRequests[0].middleName                          | 47977-01-02-1.consumerRequests[0].middleName                      |
      | consumerRequests[0].suffix                              | 47977-01-02-1.consumerRequests[0].suffix                          |
      | consumerRequests[0].ssn                                 | 47977-01-02-1.consumerRequests[0].ssn                             |
      | consumerRequests[0].dateOfBirth                         | 47977-01-02-1.consumerRequests[0].dateOfBirth                     |
      | consumerRequests[0].case.externalId                     | 47977-01-02-1.consumerRequests[0].case.externalId                 |
      | consumerRequests[0].sexCode                             | 47977-01-02-1.consumerRequests[0].sexCode                         |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | 47977 Match NewMedID Ct                                           |
      | consumerRequests[0].contact.addresses[0].addressZip     | 47977                                                             |
      | consumerRequests[0].contact.phones[0].phoneNumber       | 4443347977                                                        |
      | consumerRequests[0].contact.emails[0].emailAddress      | 47977@cpgmail.com                                                 |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT            |
      | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 47977-01-02-1.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1968-12-11                                                   |
      | dataSource         | MEDICAID                                                     |
      | profileId          | MEDICAID                                                     |
    Then I verify "UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT" payload captured previous id change
      | dataSource | MEDICAID |

  @dc-eb-event @CP-47977 @CP-47977-01-03 @muhabbat @events @dc-eb-event
  Scenario: Match on both Medicaid IDs for a single consumer - New/Changed Consumer Info
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "47977-01-03-1" with following payload
      | consumerRequests[0].uuid                                | random     |
      | consumerRequests[0].dataSource                          | MMIS       |
      | consumerRequests[0].firstName                           | random     |
      | consumerRequests[0].lastName                            | random     |
      | consumerRequests[0].middleName                          | random     |
      | consumerRequests[0].suffix                              | DDS        |
      | consumerRequests[0].ssn                                 | random     |
      | consumerRequests[0].dateOfBirth                         | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type                | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId          | random     |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId     | random     |
      | consumerRequests[0].case.externalId                     | random     |
      | consumerRequests[0].sexCode                             | Unknown    |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | random     |
      | consumerRequests[0].contact.addresses[0].addressZip     | random     |
      | consumerRequests[0].contact.phones[0].phoneNumber       | random     |
      | consumerRequests[0].contact.emails[0].emailAddress      | random     |
    When I update just created prior id "47977-01-03-2" case consumer
      | consumerRequests[0].uuid                            | random                                                            |
      | consumerRequests[0].priorConsumerProfile.externalId | 47977-01-03-1.consumerRequests[0].priorConsumerProfile.externalId |
      | consumerRequests[0].consumerProfile.externalId      | 47977-01-03-1.consumerRequests[0].consumerProfile.externalId      |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID                                                          |
      | consumerRequests[0].consumerProfile.type            | MEDICAID                                                          |
      | consumerRequests[0].dataSource                      | MMIS                                                              |
      | consumerRequests[0].firstName                       | random                                                            |
      | consumerRequests[0].lastName                        | random                                                            |
      | consumerRequests[0].middleName                      | random                                                            |
      | consumerRequests[0].suffix                          | DDS                                                               |
      | consumerRequests[0].ssn                             | random                                                            |
      | consumerRequests[0].dateOfBirth                     | 1968-12-11                                                        |
      | consumerRequests[0].case.externalId                 | random                                                            |
      | consumerRequests[0].sexCode                         | Unknown                                                           |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 47977-01-03-1.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1968-12-11                                                   |
      | dataSource         | MEDICAID                                                     |
      | profileId          | MEDICAID                                                     |

  @dc-eb-event @CP-47977 @CP-47977-02-01 @muhabbat @events @dc-eb-event
  Scenario: Match on both Medicaid IDs for a single consumer (IDs are reversed) - Full match
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "47977-02-01-1" with following payload
      | consumerRequests[0].uuid                                | random     |
      | consumerRequests[0].dataSource                          | MMIS       |
      | consumerRequests[0].firstName                           | random     |
      | consumerRequests[0].lastName                            | random     |
      | consumerRequests[0].middleName                          | random     |
      | consumerRequests[0].suffix                              | DDS        |
      | consumerRequests[0].ssn                                 | random     |
      | consumerRequests[0].dateOfBirth                         | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type                | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId          | random     |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId     | random     |
      | consumerRequests[0].case.externalId                     | random     |
      | consumerRequests[0].sexCode                             | Unknown    |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | random     |
      | consumerRequests[0].contact.addresses[0].addressZip     | random     |
      | consumerRequests[0].contact.phones[0].phoneNumber       | random     |
      | consumerRequests[0].contact.emails[0].emailAddress      | random     |
    When I update just created prior id "47977-02-01-2" case consumer
      | consumerRequests[0].uuid                                | random                                                                |
      | consumerRequests[0].priorConsumerProfile.externalId     | 47977-02-01-1.consumerRequests[0].consumerProfile.externalId          |
      | consumerRequests[0].consumerProfile.externalId          | 47977-02-01-1.consumerRequests[0].priorConsumerProfile.externalId     |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID                                                              |
      | consumerRequests[0].consumerProfile.type                | MEDICAID                                                              |
      | consumerRequests[0].dataSource                          | 47977-02-01-1.consumerRequests[0].dataSource                          |
      | consumerRequests[0].firstName                           | 47977-02-01-1.consumerRequests[0].firstName                           |
      | consumerRequests[0].lastName                            | 47977-02-01-1.consumerRequests[0].lastName                            |
      | consumerRequests[0].middleName                          | 47977-02-01-1.consumerRequests[0].middleName                          |
      | consumerRequests[0].suffix                              | 47977-02-01-1.consumerRequests[0].suffix                              |
      | consumerRequests[0].ssn                                 | 47977-02-01-1.consumerRequests[0].ssn                                 |
      | consumerRequests[0].dateOfBirth                         | 47977-02-01-1.consumerRequests[0].dateOfBirth                         |
      | consumerRequests[0].case.externalId                     | 47977-02-01-1.consumerRequests[0].case.externalId                     |
      | consumerRequests[0].sexCode                             | 47977-02-01-1.consumerRequests[0].sexCode                             |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | 47977-02-01-1.consumerRequests[0].contact.addresses[0].addressStreet1 |
      | consumerRequests[0].contact.addresses[0].addressZip     | 47977-02-01-1.consumerRequests[0].contact.addresses[0].addressZip     |
      | consumerRequests[0].contact.phones[0].phoneNumber       | 47977-02-01-1.consumerRequests[0].contact.phones[0].phoneNumber       |
      | consumerRequests[0].contact.emails[0].emailAddress      | 47977-02-01-1.consumerRequests[0].contact.emails[0].emailAddress      |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 47977-02-01-1.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1968-12-11                                                   |
      | dataSource         | MEDICAID                                                     |
      | profileId          | MEDICAID                                                     |


  @dc-eb-event @CP-47977 @CP-47977-02-02 @muhabbat @events @dc-eb-event
  Scenario: Match on both Medicaid IDs for a single consumer (IDs are reversed) - Not matching on Contact info
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "47977-02-02-1" with following payload
      | consumerRequests[0].uuid                                | random     |
      | consumerRequests[0].dataSource                          | MMIS       |
      | consumerRequests[0].firstName                           | random     |
      | consumerRequests[0].lastName                            | random     |
      | consumerRequests[0].middleName                          | random     |
      | consumerRequests[0].suffix                              | DDS        |
      | consumerRequests[0].ssn                                 | random     |
      | consumerRequests[0].dateOfBirth                         | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type                | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId          | random     |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId     | random     |
      | consumerRequests[0].case.externalId                     | random     |
      | consumerRequests[0].sexCode                             | Unknown    |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | random     |
      | consumerRequests[0].contact.addresses[0].addressZip     | random     |
      | consumerRequests[0].contact.phones[0].phoneNumber       | random     |
      | consumerRequests[0].contact.emails[0].emailAddress      | random     |
    When I update just created prior id "47977-02-02-2" case consumer
      | consumerRequests[0].uuid                                | random                                                            |
      | consumerRequests[0].priorConsumerProfile.externalId     | 47977-02-02-1.consumerRequests[0].consumerProfile.externalId      |
      | consumerRequests[0].consumerProfile.externalId          | 47977-02-02-1.consumerRequests[0].priorConsumerProfile.externalId |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID                                                          |
      | consumerRequests[0].consumerProfile.type                | MEDICAID                                                          |
      | consumerRequests[0].dataSource                          | 47977-02-02-1.consumerRequests[0].dataSource                      |
      | consumerRequests[0].firstName                           | 47977-02-02-1.consumerRequests[0].firstName                       |
      | consumerRequests[0].lastName                            | 47977-02-02-1.consumerRequests[0].lastName                        |
      | consumerRequests[0].middleName                          | 47977-02-02-1.consumerRequests[0].middleName                      |
      | consumerRequests[0].suffix                              | 47977-02-02-1.consumerRequests[0].suffix                          |
      | consumerRequests[0].ssn                                 | 47977-02-02-1.consumerRequests[0].ssn                             |
      | consumerRequests[0].dateOfBirth                         | 47977-02-02-1.consumerRequests[0].dateOfBirth                     |
      | consumerRequests[0].case.externalId                     | 47977-02-02-1.consumerRequests[0].case.externalId                 |
      | consumerRequests[0].sexCode                             | 47977-02-02-1.consumerRequests[0].sexCode                         |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | 47977 Match NewMedID Ct                                           |
      | consumerRequests[0].contact.addresses[0].addressZip     | 47977                                                             |
      | consumerRequests[0].contact.phones[0].phoneNumber       | 4443347977                                                        |
      | consumerRequests[0].contact.emails[0].emailAddress      | 47977@cpgmail.com                                                 |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT            |
      | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 47977-02-02-1.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1968-12-11                                                   |
      | dataSource         | MEDICAID                                                     |
      | profileId          | MEDICAID                                                     |
    Then I verify "UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT" payload captured previous id change
      | dataSource | MEDICAID |

  @dc-eb-event @CP-47977 @CP-47977-02-03 @muhabbat @events @dc-eb-event
  Scenario: Match on both Medicaid IDs for a single consumer (IDs are reversed) - New/Changed Consumer Info
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "47977-02-03-1" with following payload
      | consumerRequests[0].uuid                                | random     |
      | consumerRequests[0].dataSource                          | MMIS       |
      | consumerRequests[0].firstName                           | random     |
      | consumerRequests[0].lastName                            | random     |
      | consumerRequests[0].middleName                          | random     |
      | consumerRequests[0].suffix                              | DDS        |
      | consumerRequests[0].ssn                                 | random     |
      | consumerRequests[0].dateOfBirth                         | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type                | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId          | random     |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId     | random     |
      | consumerRequests[0].case.externalId                     | random     |
      | consumerRequests[0].sexCode                             | Unknown    |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | random     |
      | consumerRequests[0].contact.addresses[0].addressZip     | random     |
      | consumerRequests[0].contact.phones[0].phoneNumber       | random     |
      | consumerRequests[0].contact.emails[0].emailAddress      | random     |
    When I update just created prior id "47977-02-03-2" case consumer
      | consumerRequests[0].uuid                            | random                                                            |
      | consumerRequests[0].priorConsumerProfile.externalId | 47977-02-03-1.consumerRequests[0].consumerProfile.externalId      |
      | consumerRequests[0].consumerProfile.externalId      | 47977-02-03-1.consumerRequests[0].priorConsumerProfile.externalId |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID                                                          |
      | consumerRequests[0].consumerProfile.type            | MEDICAID                                                          |
      | consumerRequests[0].dataSource                      | MMIS                                                              |
      | consumerRequests[0].firstName                       | random                                                            |
      | consumerRequests[0].lastName                        | random                                                            |
      | consumerRequests[0].middleName                      | random                                                            |
      | consumerRequests[0].suffix                          | DDS                                                               |
      | consumerRequests[0].ssn                             | random                                                            |
      | consumerRequests[0].dateOfBirth                     | 1968-12-11                                                        |
      | consumerRequests[0].case.externalId                 | random                                                            |
      | consumerRequests[0].sexCode                         | Unknown                                                           |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 47977-02-03-1.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1968-12-11                                                   |
      | dataSource         | MEDICAID                                                     |
      | profileId          | MEDICAID                                                     |
