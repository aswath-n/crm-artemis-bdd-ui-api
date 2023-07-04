Feature:DC-EB Consumer: Part 1 MMIS Sends Current and Prior Medicaid IDs

  @dc-eb-event @CP-38986 @CP-38986-01 @CP-38986-02 @CP-38986-03 @muhabbat @events @dc-eb-event
  Scenario: No Matches Found for all Medicaid IDs
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "38986-02-1" with following payload
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
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | NEW_CONSUMER_BUSINESS_EVENT            |
      | NEW_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I verify NEW_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 38986-02-1.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1965-12-11                                                |
      | dataSource         | MEDICAID                                                  |
      | profileId          | MEDICAID                                                  |
    Then I verify "NEW_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT" payload captured previous id change
      | dataSource | MEDICAID |

  @CP-38986 @CP-38986-01 @CP-38986-03 @muhabbat @events
  Scenario: Match on New Medicaid ID Only
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "38986-03-1" with following payload
      | consumerRequests[0].uuid                            | random     |
      | consumerRequests[0].dataSource                      | MMIS       |
      | consumerRequests[0].firstName                       | random     |
      | consumerRequests[0].lastName                        | random     |
      | consumerRequests[0].middleName                      | random     |
      | consumerRequests[0].suffix                          | DDS        |
      | consumerRequests[0].ssn                             | random     |
      | consumerRequests[0].dateOfBirth                     | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type            | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId      | random     |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId | random     |
      | consumerRequests[0].case.externalId                 | random     |
      | consumerRequests[0].sexCode                         | Unknown    |
    When I update just created prior id "38986-03-2" case consumer
      | consumerRequests[0].uuid                                | random                                                    |
      | consumerRequests[0].priorConsumerProfile.externalId     | random                                                    |
      | consumerRequests[0].consumerProfile.externalId          | 38986-03-1.consumerRequests[0].consumerProfile.externalId |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID                                                  |
      | consumerRequests[0].consumerProfile.type                | MEDICAID                                                  |
      | consumerRequests[0].dataSource                          | 38986-03-1.consumerRequests[0].dataSource                 |
      | consumerRequests[0].firstName                           | 38986-03-1.consumerRequests[0].firstName                  |
      | consumerRequests[0].lastName                            | 38986-03-1.consumerRequests[0].lastName                   |
      | consumerRequests[0].middleName                          | 38986-03-1.consumerRequests[0].middleName                 |
      | consumerRequests[0].suffix                              | 38986-03-1.consumerRequests[0].suffix                     |
      | consumerRequests[0].ssn                                 | 38986-03-1.consumerRequests[0].ssn                        |
      | consumerRequests[0].dateOfBirth                         | 38986-03-1.consumerRequests[0].dateOfBirth                |
      | consumerRequests[0].case.externalId                     | 38986-03-1.consumerRequests[0].case.externalId            |
      | consumerRequests[0].sexCode                             | 38986-03-1.consumerRequests[0].sexCode                    |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | 38986 Match NewMedID Ct                                   |
      | consumerRequests[0].contact.addresses[0].addressZip     | 38986                                                     |
      | consumerRequests[0].contact.phones[0].phoneNumber       | 4443338986                                                |
      | consumerRequests[0].contact.emails[0].emailAddress      | 38986@cpgmail.com                                         |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT            |
      | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 38986-03-2.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1965-12-11                                                |
      | dataSource         | MEDICAID                                                  |
    Then I verify "UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT" payload captured previous id change
      | dataSource | MEDICAID |

  @CP-38986 @CP-38986-04 @CP-38986-02  @muhabbat @events @dc-eb-event
  Scenario: Match on Old Medicaid ID only - Core Consumer Attributes Same
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "38986-04-1" with following payload
      | consumerRequests[0].uuid                            | random     |
      | consumerRequests[0].dataSource                      | MMIS       |
      | consumerRequests[0].firstName                       | random     |
      | consumerRequests[0].lastName                        | random     |
      | consumerRequests[0].middleName                      | random     |
      | consumerRequests[0].suffix                          | DDS        |
      | consumerRequests[0].ssn                             | random     |
      | consumerRequests[0].dateOfBirth                     | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type            | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId      | random     |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId | random     |
      | consumerRequests[0].case.externalId                 | random     |
      | consumerRequests[0].sexCode                         | Unknown    |
    When I update just created prior id "38986-04-2" case consumer
      | consumerRequests[0].uuid                            | random                                                    |
      | consumerRequests[0].priorConsumerProfile.externalId | 38986-04-1.consumerRequests[0].consumerProfile.externalId |
      | consumerRequests[0].consumerProfile.externalId      | random                                                    |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID                                                  |
      | consumerRequests[0].consumerProfile.type            | MEDICAID                                                  |
      | consumerRequests[0].dataSource                      | 38986-04-1.consumerRequests[0].dataSource                 |
      | consumerRequests[0].firstName                       | 38986-04-1.consumerRequests[0].firstName                  |
      | consumerRequests[0].lastName                        | 38986-04-1.consumerRequests[0].lastName                   |
      | consumerRequests[0].middleName                      | 38986-04-1.consumerRequests[0].middleName                 |
      | consumerRequests[0].suffix                          | 38986-04-1.consumerRequests[0].suffix                     |
      | consumerRequests[0].ssn                             | 38986-04-1.consumerRequests[0].ssn                        |
      | consumerRequests[0].dateOfBirth                     | 38986-04-1.consumerRequests[0].dateOfBirth                |
      | consumerRequests[0].case.externalId                 | 38986-04-1.consumerRequests[0].case.externalId            |
      | consumerRequests[0].sexCode                         | 38986-04-1.consumerRequests[0].sexCode                    |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT            |
      | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 38986-04-2.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1965-12-11                                                |
      | dataSource         | MEDICAID                                                  |
    Then I verify "UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT" payload captured previous id change
      | dataSource | MEDICAID |

  @CP-38986 @CP-38986-05-1 @muhabbat @events @dc-eb-event
  Scenario: Match on Old Medicaid ID only - Core Consumer Attributes Different
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "38986-05-1-1" with following payload
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
    When I update just created prior id "38986-05-1-2" case consumer
      | consumerRequests[0].uuid                                | random                                                               |
      | consumerRequests[0].priorConsumerProfile.externalId     | 38986-05-1-1.consumerRequests[0].priorConsumerProfile.externalId     |
      | consumerRequests[0].consumerProfile.externalId          | random                                                               |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID                                                             |
      | consumerRequests[0].consumerProfile.type                | MEDICAID                                                             |
      | consumerRequests[0].dataSource                          | MMIS                                                                 |
      | consumerRequests[0].firstName                           | random                                                               |
      | consumerRequests[0].lastName                            | random                                                               |
      | consumerRequests[0].middleName                          | random                                                               |
      | consumerRequests[0].suffix                              | DDS                                                                  |
      | consumerRequests[0].ssn                                 | random                                                               |
      | consumerRequests[0].dateOfBirth                         | 1968-12-11                                                           |
      | consumerRequests[0].case.externalId                     | random                                                               |
      | consumerRequests[0].sexCode                             | Unknown                                                              |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | 38986-05-1-1.consumerRequests[0].contact.addresses[0].addressStreet1 |
      | consumerRequests[0].contact.addresses[0].addressZip     | 38986-05-1-1.consumerRequests[0].contact.addresses[0].addressZip     |
      | consumerRequests[0].contact.phones[0].phoneNumber       | 38986-05-1-1.consumerRequests[0].contact.phones[0].phoneNumber       |
      | consumerRequests[0].contact.emails[0].emailAddress      | 38986-05-1-1.consumerRequests[0].contact.emails[0].emailAddress      |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | NEW_CONSUMER_BUSINESS_EVENT            |
      | NEW_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I verify NEW_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 38986-05-1-1.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1968-12-11                                                  |
      | dataSource         | MEDICAID                                                    |
      | profileId          | MEDICAID                                                    |
    Then I verify "NEW_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT" payload captured previous id change
      | dataSource | MEDICAID |

  @CP-38986 @CP-38986-05-2 @muhabbat @events @dc-eb-event
  Scenario: Match on Old Medicaid ID only - Core Consumer Attributes Different (no contact info change)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "38986-05-2-1" with following payload
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
    When I update just created prior id "38986-05-2-2" case consumer
      | consumerRequests[0].uuid                            | random                                                           |
      | consumerRequests[0].priorConsumerProfile.externalId | 38986-05-2-1.consumerRequests[0].priorConsumerProfile.externalId |
      | consumerRequests[0].consumerProfile.externalId      | random                                                           |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID                                                         |
      | consumerRequests[0].consumerProfile.type            | MEDICAID                                                         |
      | consumerRequests[0].dataSource                      | MMIS                                                             |
      | consumerRequests[0].firstName                       | random                                                           |
      | consumerRequests[0].lastName                        | random                                                           |
      | consumerRequests[0].middleName                      | random                                                           |
      | consumerRequests[0].suffix                          | DDS                                                              |
      | consumerRequests[0].ssn                             | random                                                           |
      | consumerRequests[0].dateOfBirth                     | 1968-12-11                                                       |
      | consumerRequests[0].case.externalId                 | random                                                           |
      | consumerRequests[0].sexCode                         | Unknown                                                          |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | NEW_CONSUMER_BUSINESS_EVENT |
    Then I verify NEW_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 38986-05-2-1.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1968-12-11                                                  |
      | dataSource         | MEDICAID                                                    |
      | profileId          | MEDICAID                                                    |


  @CP-38986 @CP-38986-06-1 @muhabbat @events @dc-eb-event
  Scenario: Match on both Medicaid IDs to different consumers in the system - Core Consumer Attributes Same (contact info changed)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "38986-06-1-1" with following payload
      | consumerRequests[0].uuid                            | random     |
      | consumerRequests[0].dataSource                      | MMIS       |
      | consumerRequests[0].firstName                       | random     |
      | consumerRequests[0].lastName                        | random     |
      | consumerRequests[0].middleName                      | random     |
      | consumerRequests[0].suffix                          | DDS        |
      | consumerRequests[0].ssn                             | random     |
      | consumerRequests[0].dateOfBirth                     | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type            | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId      | random     |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId | random     |
      | consumerRequests[0].case.externalId                 | random     |
      | consumerRequests[0].sexCode                         | Unknown    |
    When I update just created prior id "38986-06-1-2" case consumer
      | consumerRequests[0].uuid                            | random     |
      | consumerRequests[0].priorConsumerProfile.externalId | random     |
      | consumerRequests[0].consumerProfile.externalId      | random     |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID   |
      | consumerRequests[0].consumerProfile.type            | MEDICAID   |
      | consumerRequests[0].dataSource                      | MMIS       |
      | consumerRequests[0].firstName                       | random     |
      | consumerRequests[0].lastName                        | random     |
      | consumerRequests[0].middleName                      | random     |
      | consumerRequests[0].suffix                          | DDS        |
      | consumerRequests[0].ssn                             | random     |
      | consumerRequests[0].dateOfBirth                     | 1969-12-11 |
      | consumerRequests[0].case.externalId                 | random     |
      | consumerRequests[0].sexCode                         | Unknown    |
    When I update just created prior id "38986-06-1-3" case consumer
      | consumerRequests[0].uuid                                | random                                                      |
      | consumerRequests[0].priorConsumerProfile.externalId     | 38986-06-1-1.consumerRequests[0].consumerProfile.externalId |
      | consumerRequests[0].consumerProfile.externalId          | 38986-06-1-2.consumerRequests[0].consumerProfile.externalId |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID                                                    |
      | consumerRequests[0].consumerProfile.type                | MEDICAID                                                    |
      | consumerRequests[0].dataSource                          | 38986-06-1-2.consumerRequests[0].dataSource                 |
      | consumerRequests[0].firstName                           | 38986-06-1-2.consumerRequests[0].firstName                  |
      | consumerRequests[0].lastName                            | 38986-06-1-2.consumerRequests[0].lastName                   |
      | consumerRequests[0].middleName                          | 38986-06-1-2.consumerRequests[0].middleName                 |
      | consumerRequests[0].suffix                              | 38986-06-1-2.consumerRequests[0].suffix                     |
      | consumerRequests[0].ssn                                 | 38986-06-1-2.consumerRequests[0].ssn                        |
      | consumerRequests[0].dateOfBirth                         | 38986-06-1-2.consumerRequests[0].dateOfBirth                |
      | consumerRequests[0].case.externalId                     | 38986-06-1-2.consumerRequests[0].case.externalId            |
      | consumerRequests[0].sexCode                             | 38986-06-1-2.consumerRequests[0].sexCode                    |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | 38986-06-12 Match BothMedID Ct                              |
      | consumerRequests[0].contact.addresses[0].addressZip     | 38986                                                       |
      | consumerRequests[0].contact.phones[0].phoneNumber       | 4443338906                                                  |
      | consumerRequests[0].contact.emails[0].emailAddress      | 38986-06@cpgmail.com                                        |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT            |
      | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 38986-06-1-2.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1965-12-11                                                  |
      | dataSource         | MEDICAID                                                    |
    Then I verify "UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT" payload captured previous id change
      | dataSource | MEDICAID |

  @CP-38986 @CP-38986-06-2 @muhabbat @events @dc-eb-event
  Scenario: Match on both Medicaid IDs to different consumers in the system - Core Consumer Attributes Same (no contact info change)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "38986-06-2-1" with following payload
      | consumerRequests[0].uuid                            | random     |
      | consumerRequests[0].dataSource                      | MMIS       |
      | consumerRequests[0].firstName                       | random     |
      | consumerRequests[0].lastName                        | random     |
      | consumerRequests[0].middleName                      | random     |
      | consumerRequests[0].suffix                          | DDS        |
      | consumerRequests[0].ssn                             | random     |
      | consumerRequests[0].dateOfBirth                     | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type            | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId      | random     |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId | random     |
      | consumerRequests[0].case.externalId                 | random     |
      | consumerRequests[0].sexCode                         | Unknown    |
    When I update just created prior id "38986-06-2-2" case consumer
      | consumerRequests[0].uuid                            | random     |
      | consumerRequests[0].priorConsumerProfile.externalId | random     |
      | consumerRequests[0].consumerProfile.externalId      | random     |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID   |
      | consumerRequests[0].consumerProfile.type            | MEDICAID   |
      | consumerRequests[0].dataSource                      | MMIS       |
      | consumerRequests[0].firstName                       | random     |
      | consumerRequests[0].lastName                        | random     |
      | consumerRequests[0].middleName                      | random     |
      | consumerRequests[0].suffix                          | DDS        |
      | consumerRequests[0].ssn                             | random     |
      | consumerRequests[0].dateOfBirth                     | 1969-12-11 |
      | consumerRequests[0].case.externalId                 | random     |
      | consumerRequests[0].sexCode                         | Unknown    |
    When I update just created prior id "38986-06-2-3" case consumer
      | consumerRequests[0].uuid                            | random                                                      |
      | consumerRequests[0].priorConsumerProfile.externalId | 38986-06-2-1.consumerRequests[0].consumerProfile.externalId |
      | consumerRequests[0].consumerProfile.externalId      | 38986-06-2-2.consumerRequests[0].consumerProfile.externalId |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID                                                    |
      | consumerRequests[0].consumerProfile.type            | MEDICAID                                                    |
      | consumerRequests[0].dataSource                      | 38986-06-2-2.consumerRequests[0].dataSource                 |
      | consumerRequests[0].firstName                       | 38986-06-2-2.consumerRequests[0].firstName                  |
      | consumerRequests[0].lastName                        | 38986-06-2-2.consumerRequests[0].lastName                   |
      | consumerRequests[0].middleName                      | 38986-06-2-2.consumerRequests[0].middleName                 |
      | consumerRequests[0].suffix                          | 38986-06-2-2.consumerRequests[0].suffix                     |
      | consumerRequests[0].ssn                             | 38986-06-2-2.consumerRequests[0].ssn                        |
      | consumerRequests[0].dateOfBirth                     | 38986-06-2-2.consumerRequests[0].dateOfBirth                |
      | consumerRequests[0].case.externalId                 | 38986-06-2-2.consumerRequests[0].case.externalId            |
      | consumerRequests[0].sexCode                         | 38986-06-2-2.consumerRequests[0].sexCode                    |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 38986-06-2-2.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1965-12-11                                                  |
      | dataSource         | MEDICAID                                                    |


  @CP-38986 @CP-38986-07-1 @muhabbat @events @dc-eb-event
  Scenario: Match on both Medicaid IDs to different consumers in the system - Core Consumer Attributes Different
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "38986-07-1-1" with following payload
      | consumerRequests[0].uuid                            | random     |
      | consumerRequests[0].dataSource                      | MMIS       |
      | consumerRequests[0].firstName                       | random     |
      | consumerRequests[0].lastName                        | random     |
      | consumerRequests[0].middleName                      | random     |
      | consumerRequests[0].suffix                          | DDS        |
      | consumerRequests[0].ssn                             | random     |
      | consumerRequests[0].dateOfBirth                     | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type            | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId      | random     |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId | random     |
      | consumerRequests[0].case.externalId                 | random     |
      | consumerRequests[0].sexCode                         | Unknown    |
    When I update just created prior id "38986-07-1-2" case consumer
      | consumerRequests[0].uuid                            | random     |
      | consumerRequests[0].priorConsumerProfile.externalId | random     |
      | consumerRequests[0].consumerProfile.externalId      | random     |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID   |
      | consumerRequests[0].consumerProfile.type            | MEDICAID   |
      | consumerRequests[0].dataSource                      | MMIS       |
      | consumerRequests[0].firstName                       | random     |
      | consumerRequests[0].lastName                        | random     |
      | consumerRequests[0].middleName                      | random     |
      | consumerRequests[0].suffix                          | DDS        |
      | consumerRequests[0].ssn                             | random     |
      | consumerRequests[0].dateOfBirth                     | 1969-12-11 |
      | consumerRequests[0].case.externalId                 | random     |
      | consumerRequests[0].sexCode                         | Unknown    |
    When I run consumers API name "38986-07-1-3" with following payload
      | consumerRequests[0].uuid                                | random                                                           |
      | consumerRequests[0].dataSource                          | MMIS                                                             |
      | consumerRequests[0].firstName                           | random                                                           |
      | consumerRequests[0].lastName                            | random                                                           |
      | consumerRequests[0].middleName                          | random                                                           |
      | consumerRequests[0].suffix                              | DDS                                                              |
      | consumerRequests[0].ssn                                 | random                                                           |
      | consumerRequests[0].dateOfBirth                         | 1999-12-11                                                       |
      | consumerRequests[0].consumerProfile.type                | MEDICAID                                                         |
      | consumerRequests[0].consumerProfile.externalId          | 38986-07-1-1.consumerRequests[0].consumerProfile.externalId      |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID                                                         |
      | consumerRequests[0].priorConsumerProfile.externalId     | 38986-07-1-2.consumerRequests[0].priorConsumerProfile.externalId |
      | consumerRequests[0].case.externalId                     | random                                                           |
      | consumerRequests[0].sexCode                             | Unknown                                                          |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | 38986-0712 Match BothMedID Ct                                    |
      | consumerRequests[0].contact.addresses[0].addressZip     | 38986                                                            |
      | consumerRequests[0].contact.phones[0].phoneNumber       | 444333890712                                                     |
      | consumerRequests[0].contact.emails[0].emailAddress      | 38986-07@cpgmail.com                                             |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT            |
      | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 38986-07-1-2.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1965-12-11                                                  |
      | dataSource         | MEDICAID                                                    |
    Then I verify "UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT" payload captured previous id change
      | dataSource | MEDICAID |

  @CP-38986 @CP-38986-07-2 @muhabbat @events @dc-eb-event
  Scenario: Match on both Medicaid IDs to different consumers in the system - Core Consumer Attributes Different (no contact info change)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "38986-07-2-1" with following payload
      | consumerRequests[0].uuid                            | random     |
      | consumerRequests[0].dataSource                      | MMIS       |
      | consumerRequests[0].firstName                       | random     |
      | consumerRequests[0].lastName                        | random     |
      | consumerRequests[0].middleName                      | random     |
      | consumerRequests[0].suffix                          | DDS        |
      | consumerRequests[0].ssn                             | random     |
      | consumerRequests[0].dateOfBirth                     | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type            | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId      | random     |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID   |
      | consumerRequests[0].priorConsumerProfile.externalId | random     |
      | consumerRequests[0].case.externalId                 | random     |
      | consumerRequests[0].sexCode                         | Unknown    |
    When I update just created prior id "38986-07-2-2" case consumer
      | consumerRequests[0].uuid                            | random     |
      | consumerRequests[0].priorConsumerProfile.externalId | random     |
      | consumerRequests[0].consumerProfile.externalId      | random     |
      | consumerRequests[0].priorConsumerProfile.type       | MEDICAID   |
      | consumerRequests[0].consumerProfile.type            | MEDICAID   |
      | consumerRequests[0].dataSource                      | MMIS       |
      | consumerRequests[0].firstName                       | random     |
      | consumerRequests[0].lastName                        | random     |
      | consumerRequests[0].middleName                      | random     |
      | consumerRequests[0].suffix                          | DDS        |
      | consumerRequests[0].ssn                             | random     |
      | consumerRequests[0].dateOfBirth                     | 1969-12-11 |
      | consumerRequests[0].case.externalId                 | random     |
      | consumerRequests[0].sexCode                         | Unknown    |
    When I run consumers API name "38986-07-2-3" with following payload
      | consumerRequests[0].uuid                                | random                                                           |
      | consumerRequests[0].dataSource                          | MMIS                                                             |
      | consumerRequests[0].firstName                           | random                                                           |
      | consumerRequests[0].lastName                            | random                                                           |
      | consumerRequests[0].middleName                          | random                                                           |
      | consumerRequests[0].suffix                              | DDS                                                              |
      | consumerRequests[0].ssn                                 | random                                                           |
      | consumerRequests[0].dateOfBirth                         | 1999-12-11                                                       |
      | consumerRequests[0].consumerProfile.type                | MEDICAID                                                         |
      | consumerRequests[0].consumerProfile.externalId          | 38986-07-2-1.consumerRequests[0].consumerProfile.externalId      |
      | consumerRequests[0].priorConsumerProfile.type           | MEDICAID                                                         |
      | consumerRequests[0].priorConsumerProfile.externalId     | 38986-07-2-2.consumerRequests[0].priorConsumerProfile.externalId |
      | consumerRequests[0].case.externalId                     | random                                                           |
      | consumerRequests[0].sexCode                             | Unknown                                                          |
      | consumerRequests[0].contact.addresses[0].addressStreet1 | 38986-0722 Match BothMedID Ct                                    |
      | consumerRequests[0].contact.addresses[0].addressZip     | 38986                                                            |
      | consumerRequests[0].contact.phones[0].phoneNumber       | 4443338907                                                       |
      | consumerRequests[0].contact.emails[0].emailAddress      | 38986-0722@cpgmail.com                                           |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_CONSUMER_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured previous id change
      | consumerIdExternal | 38986-07-2-2.consumerRequests[0].consumerProfile.externalId |
      | dateOfBirth        | 1965-12-11                                                  |
      | dataSource         | MEDICAID                                                    |