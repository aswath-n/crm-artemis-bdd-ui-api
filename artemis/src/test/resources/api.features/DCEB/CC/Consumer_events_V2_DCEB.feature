Feature: Consumer events V2 DCEB

  @CP-41489 @CP-41489-01 @beka @events @events-cc @dc-eb-event
  Scenario: Validation of consumer v2 update
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41489-01" with following payload
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
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | CASE_V2_SAVE_EVENT          |
      | CONSUMER_V2_SAVE_EVENT      |
      | NEW_CONSUMER_BUSINESS_EVENT |
    And I initiate consumers V2 API
    When I update just created "41489-01" case consumer
      | consumerRequests[0].firstName | Beka    |
      | consumerRequests[0].lastName  | Berdiev |
    When I GET events by UUID using event correlation API
    Then I will verify consumer update response contains following data
      | firstName | Beka    |
      | lastName  | Berdiev |

  @CP-41509 @CP-41509-01 @beka @events @events-cc @dc-eb-event
  Scenario: Validation of case v2 update events
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41509-01" with following payload
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
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | CASE_V2_SAVE_EVENT          |
      | CONSUMER_V2_SAVE_EVENT      |
      | NEW_CONSUMER_BUSINESS_EVENT |
    And I initiate consumers V2 API
    When I update just created "41509-01" case consumer
      | consumerRequests[0].case.caseAttributes[0].value.firstName | Beka    |
      | consumerRequests[0].case.caseAttributes[0].value.lastName  | Berdiev |
    When I GET events by UUID using event correlation API
    Then I will verify case update event response contains following data
      | firstName | Beka    |
      | lastName  | Berdiev |

  @CP-41508 @CP-41508-01 @CP-41488 @chopa @events @events-cc @dc-eb-event
  Scenario: Validation of consumer and case v2 save events
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41508-01" with following payload
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
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | CONSUMER_V2_SAVE_EVENT |
    Then I will verify consumer save event payload data
    Then I will verify event published for following events
      | CASE_V2_SAVE_EVENT |
    Then I will verify consumer save event payload data

  @CP-41583 @CP-41583-01 @CP-47685 @CP-47685-01 @beka @events @dc-eb-event
  Scenario: Publish new Consumer Business Event for V2 MMIS profile
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41583-01" with following payload
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
      | consumerRequests[0].linkedObjects.externalLinkId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | CASE_V2_SAVE_EVENT          |
      | CONSUMER_V2_SAVE_EVENT      |
      | NEW_CONSUMER_BUSINESS_EVENT |
    Then I will verify NEW_CONSUMER_BUSINESS_EVENT payload contains following data
      | eventName              | NEW_CONSUMER_BUSINESS_EVENT |
      | eventId                | not null                    |
      | module                 | Consumer-v2                 |
      | status                 | SUCCESS                     |
      | correlationId          | justCreated                 |
      | message                | null                        |
      | createdOn              | current date                |
      | createdBy              | 3493                        |
      #response data inside payload object
      | projectName            | DC-EB                       |
      | action                 | Create                      |
      | recordType             | ConsumerBusinessEvent       |
      | Consumer ID (internal) | justCreated                 |
      | consumerName           | justCreated                 |
      | dataSource             | MEDICAID                    |
      | consumerIdExternal     | justCreated                 |
      | channel                | SYSTEM_INTEGRATION          |
      | linkedObjects          | not null                    |

  @CP-41583 @CP-41583-02 @beka @events @dc-eb-event
  Scenario: Publish new Consumer Business Event for V2 MCO profile
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41583-02" with following payload
      | consumerRequests[0].uuid                       | random   |
      | consumerRequests[0].dataSource                 | MCO      |
      | consumerRequests[0].firstName                  | random   |
      | consumerRequests[0].lastName                   | random   |
      | consumerRequests[0].middleName                 | random   |
      | consumerRequests[0].suffix                     | DDS      |
      | consumerRequests[0].ssn                        | random   |
      | consumerRequests[0].dateOfBirth                | random   |
      | consumerRequests[0].consumerProfile.type       | MCO      |
      | consumerRequests[0].consumerProfile.externalId | random   |
      | consumerRequests[0].case.type                  | MEDICAID |
      | consumerRequests[0].case.externalId            | random   |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | CASE_V2_SAVE_EVENT          |
      | CONSUMER_V2_SAVE_EVENT      |
      | NEW_CONSUMER_BUSINESS_EVENT |
    Then I will verify NEW_CONSUMER_BUSINESS_EVENT payload contains following data
      | eventName              | NEW_CONSUMER_BUSINESS_EVENT |
      | eventId                | not null                    |
      | module                 | Consumer-v2                 |
      | status                 | SUCCESS                     |
      | correlationId          | justCreated                 |
      | message                | null                        |
      | createdOn              | current date                |
      | createdBy              | 3493                        |
      #response data inside payload object
      | projectName            | DC-EB                       |
      | action                 | Create                      |
      | recordType             | ConsumerBusinessEvent       |
      | Consumer ID (internal) | justCreated                 |
      | consumerName           | justCreated                 |
      | dataSource             | MCO                         |
      | consumerIdExternal     | justCreated                 |
      | channel                | SYSTEM_INTEGRATION          |

  @CP-41584 @CP-41584-01 @CP-47685 @CP-47685-02 @beka @events @dc-eb-event
  Scenario: UPDATE_CONSUMER_BUSINESS_EVENT for V2 MMIS profile AC 1.0
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41584-01" with following payload
      | consumerRequests[0].uuid                         | random     |
      | consumerRequests[0].dataSource                   | MMIS       |
      | consumerRequests[0].firstName                    | random     |
      | consumerRequests[0].lastName                     | random     |
      | consumerRequests[0].middleName                   | random     |
      | consumerRequests[0].suffix                       | DDS        |
      | consumerRequests[0].ssn                          | random     |
      | consumerRequests[0].dateOfBirth                  | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type         | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId   | random     |
      | consumerRequests[0].linkedObjects.externalLinkId | random     |
      | consumerRequests[0].case.type                    | MEDICAID   |
      | consumerRequests[0].case.externalId              | random     |
      | consumerRequests[0].sexCode                      | Unknown    |
    When I update just created "41584-01" case consumer
      | consumerRequests[0].firstName                       | Beka       |
      | consumerRequests[0].lastName                        | Berdiev    |
      | consumerRequests[0].dateOfBirth                     | 1965-10-11 |
      | consumerRequests[0].sexCode                         | Male       |
      | consumerRequests[0].linkedObjects[0].externalLinkId | 6534       |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | CASE_V2_SAVE_EVENT             |
      | CONSUMER_V2_SAVE_EVENT         |
      | NEW_CONSUMER_BUSINESS_EVENT    |
      | CONSUMER_V2_UPDATE_EVENT       |
      | CASE_V2_UPDATE_EVENT           |
      | UPDATE_CONSUMER_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT payload captured change
      | consumerName    | Beka Berdiev |
      | dateOfBirth     | 1965-10-11   |
      | sexCode         | Male         |
      | externalLinkId  | 6534         |

  @CP-41584 @CP-41584-02 @beka @events @dc-eb-event
  Scenario: UPDATE_CONSUMER_BUSINESS_EVENT for V2 AC 1.1 change attributes
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "41584-02" with following payload
      | consumerRequests[0].uuid                       | random     |
      | consumerRequests[0].dataSource                 | MMIS       |
      | consumerRequests[0].firstName                  | random     |
      | consumerRequests[0].lastName                   | random     |
      | consumerRequests[0].middleName                 | random     |
      | consumerRequests[0].suffix                     | DDS        |
      | consumerRequests[0].ssn                        | random     |
      | consumerRequests[0].dateOfBirth                | 1965-12-11 |
      | consumerRequests[0].consumerProfile.type       | MEDICAID   |
      | consumerRequests[0].consumerProfile.externalId | random     |
      | consumerRequests[0].case.type                  | MEDICAID   |
      | consumerRequests[0].case.externalId            | random     |
      | consumerRequests[0].sexCode                    | Unknown    |
    When I update just created "41584-02" case consumer
      | consumerRequests[0].consumerProfile.externalId | UpdateMed002     |
      | consumerRequests[0].case.externalId            | UpdateMedCase002 |
      | consumerRequests[0].ssn                        | 564546456        |
      | consumerRequests[0].ethnicityCode              | H                |
      | consumerRequests[0].raceCode                   | Hispanic         |
      | consumerRequests[0].dateOfDeath                | 2023-01-02       |
      | consumerRequests[0].residencyStatus            | Resident         |
      | consumerRequests[0].pregnancyInd               | true             |
      | consumerRequests[0].pregnancyDueDate           | 2023-09-09       |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | CASE_V2_SAVE_EVENT             |
      | CONSUMER_V2_SAVE_EVENT         |
      | NEW_CONSUMER_BUSINESS_EVENT    |
      | CONSUMER_V2_UPDATE_EVENT       |
      | CASE_V2_UPDATE_EVENT           |
      | UPDATE_CONSUMER_BUSINESS_EVENT |
    Then I verify UPDATE_CONSUMER_BUSINESS_EVENT has updatet data
      | consumerIdExternal | UpdateMed002     |
      | caseIdExternal     | UpdateMedCase002 |
      | ssn                | 564546456        |
      | ethnicityCode      | H                |
      | raceCode           | Hispanic         |
      | dateOfDeath        | 2023-01-02       |
      | residencyStatus    | Resident         |
      | pregnancyInd       | true             |
      | pregnancyDueDate   | 2023-09-09       |

  @CP-41586 @CP-41586-01 @CP-47701 @CP-47701-01 @beka @events @dc-eb-event
  Scenario: Verify new Address Phone Email Business Event published when MMIS created
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "441586-01" with following payload
      | consumerRequests[0].uuid                         | random   |
      | consumerRequests[0].dataSource                   | MMIS     |
      | consumerRequests[0].firstName                    | random   |
      | consumerRequests[0].lastName                     | random   |
      | consumerRequests[0].middleName                   | random   |
      | consumerRequests[0].suffix                       | DDS      |
      | consumerRequests[0].ssn                          | random   |
      | consumerRequests[0].dateOfBirth                  | random   |
      | consumerRequests[0].consumerProfile.type         | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId   | random   |
      | consumerRequests[0].linkedObjects.externalLinkId | random   |
      | consumerRequests[0].case.type                    | MEDICAID |
      | consumerRequests[0].case.externalId              | random   |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | NEW_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I will verify NEW_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT payload contains following data
      | eventName              | NEW_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
      | eventId                | not null                               |
      | module                 | Consumer-v2                            |
      | status                 | SUCCESS                                |
      | correlationId          | justCreated                            |
      | message                | null                                   |
      | createdOn              | current date                           |
      | createdBy              | 3493                                   |
      #response data inside payload object
      | projectName            | DC-EB                                  |
      | action                 | Create                                 |
      | recordType             | ContactsBusinessEvent                  |
      | Consumer ID (internal) | justCreated                            |
      | consumerName           | justCreated                            |
      | dataSource             | MEDICAID                               |
      | channel                | SYSTEM_INTEGRATION                     |
      | businessEvent          | NEW_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
      | addresses              | not null                               |
      | phones                 | not null                               |
      | emails                 | not null                               |
      | linkedObjects          | not null                               |

  @CP-44747 @CP-44747-01 @beka @events @dc-eb-event
  Scenario: Verify Update Address Phone Email when update consumer reported contacts
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "44747-01" with following payload
      | consumerRequests[0].uuid                         | random   |
      | consumerRequests[0].dataSource                   | MMIS     |
      | consumerRequests[0].firstName                    | random   |
      | consumerRequests[0].lastName                     | random   |
      | consumerRequests[0].middleName                   | random   |
      | consumerRequests[0].suffix                       | DDS      |
      | consumerRequests[0].ssn                          | random   |
      | consumerRequests[0].dateOfBirth                  | random   |
      | consumerRequests[0].consumerProfile.type         | MEDICAID |
      | consumerRequests[0].consumerProfile.externalId   | random   |
      | consumerRequests[0].case.type                    | MEDICAID |
      | consumerRequests[0].case.externalId              | random   |
    When I initiate contacts V2 API
    When I call consumer reported API with following payload
      | searchConsumerProfile.profileId                         | just created        |
#OptIn objects used to set opt true OR false for address/phone/email
    # address
      | consumerAttributes.optInAddress.optInAddress            | true                |
      | consumerAttributes.optInAddress.addressStreet1          | Test Street 1       |
      | consumerAttributes.optInAddress.addressStreet2          | Test Street 2       |
      | consumerAttributes.optInAddress.addressCity             | New York            |
      | consumerAttributes.optInAddress.addressState            | VA                  |
      | consumerAttributes.optInAddress.addressZip              | 78678               |
      | consumerAttributes.optInAddress.addressZipFour          | 7897                |
    # phone
      | consumerAttributes.optInPhone.optInPhone                | true                |
      | consumerAttributes.optInPhone.phoneNumber               | 5556664444          |
    # email
      | consumerAttributes.optInEmail.optInEmail                | true                |
      | consumerAttributes.optInEmail.emailAddress              | test@email.org      |
# set do not call
      | consumerAttributes.doNotCall                            | false               |
# set LANGUAGE PREFERENCE
      | consumerAttributes.languageCodes[0].valuePairIdCommPref | LANGUAGE_PREFERENCE |
      | consumerAttributes.languageCodes[0].preference          | 1                   |
      | consumerAttributes.languageCodes[0].value               | SPANISH             |
# set Mailing address OR inactivate it by switching inactiveInd to true
      | consumerAttributes.addresses[0].addressType             | Mailing             |
      | consumerAttributes.addresses[0].addressStreet1          | Mailing add1 12     |
      | consumerAttributes.addresses[0].addressStreet2          | Mailing add2 12     |
      | consumerAttributes.addresses[0].addressCity             | Durbin              |
      | consumerAttributes.addresses[0].addressState            | FL                  |
      | consumerAttributes.addresses[0].addressZip              | 32342               |
      | consumerAttributes.addresses[0].addressZipFour          | 1234                |
      | consumerAttributes.addresses[0].inactiveInd             | false               |
# set Phones OR inactivate it by switching inactiveInd to true
      | consumerAttributes.phones[0].phoneNumber                | 6456345564          |
      | consumerAttributes.phones[0].preference                 | 1                   |
      | consumerAttributes.phones[0].phoneType                  | Cell                |
      | consumerAttributes.phones[0].inactiveInd                | false               |
# set Emails OR inactivate it by switching inactiveInd to true
      | consumerAttributes.emails[0].emailAddress               | emailTest@yahoo.com |
      | consumerAttributes.emails[0].emailType                  | Personal            |
      | consumerAttributes.emails[0].preference                 | 1                   |
      | consumerAttributes.emails[0].inactiveInd                | false               |
      | dataSource                                              | System              |
      | createdBy                                               | autoScript          |
      | uuid                                                    | random              |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I will verify UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT payload contains following data
      | eventName              | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
      | eventId                | not null                                  |
      | module                 | Consumer-v2                               |
      | status                 | SUCCESS                                   |
      | correlationId          | justCreated                               |
      | message                | null                                      |
      | createdOn              | current date                              |
      | createdBy              | autoScript                                |
      #response data inside payload object
      | projectName            | DC-EB                                     |
      | action                 | Update                                    |
      | recordType             | ContactsBusinessEvent                     |
      | Consumer ID (internal) | justCreated                               |
      | consumerName           | justCreated                               |
      | dataSource             | Consumer Reported                         |
      | channel                | CP                                        |
      | businessEvent          | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
      #contacts events objects
      | addressType            | Mailing                                   |
      | addressStreet1         | Mailing add1 12                           |
      | addressStreet2         | Mailing add2 12                           |
      | addressCity            | Durbin                                    |
      | addressState           | FL                                        |
      | addressZip             | 32342                                     |
      | addressZipFour         | 1234                                      |
      | phoneNumber            | 6456345564                                |
      | phoneType              | Cell                                      |
      | emailAddress           | emailTest@yahoo.com                       |
      | emailType              | Personal                                  |

  @CP-44747 @CP-44747-02 @beka @events @dc-eb-event
  Scenario Outline: Verify if UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT will be published if user change contacts independently
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "44747-02" with following payload
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
    When I initiate contacts V2 API
    When I call consumer reported API with following payload
      | searchConsumerProfile.profileId                         | just created        |
#OptIn objects used to set opt true OR false for address/phone/email
    # address
      | consumerAttributes.optInAddress.optInAddress            | <optInAddress>      |
      | consumerAttributes.optInAddress.addressStreet1          | Test Street 1       |
      | consumerAttributes.optInAddress.addressStreet2          | Test Street 2       |
      | consumerAttributes.optInAddress.addressCity             | New York            |
      | consumerAttributes.optInAddress.addressState            | VA                  |
      | consumerAttributes.optInAddress.addressZip              | 78678               |
      | consumerAttributes.optInAddress.addressZipFour          | 7897                |
    # phone
      | consumerAttributes.optInPhone.optInPhone                | <optInPhone>        |
      | consumerAttributes.optInPhone.phoneNumber               | 5556664444          |
    # email
      | consumerAttributes.optInEmail.optInEmail                | <optInEmail>        |
      | consumerAttributes.optInEmail.emailAddress              | test@email.org      |
# set do not call
      | consumerAttributes.doNotCall                            | <doNotCall>         |
# set LANGUAGE PREFERENCE
      | consumerAttributes.languageCodes[0].valuePairIdCommPref | LANGUAGE_PREFERENCE |
      | consumerAttributes.languageCodes[0].preference          | 1                   |
      | consumerAttributes.languageCodes[0].value               | SPANISH             |
# set Mailing address OR inactivate it by switching inactiveInd to true
      | consumerAttributes.addresses[0].addressType             | Mailing             |
      | consumerAttributes.addresses[0].addressStreet1          | Mailing add1 12     |
      | consumerAttributes.addresses[0].addressStreet2          | Mailing add2 12     |
      | consumerAttributes.addresses[0].addressCity             | Durbin              |
      | consumerAttributes.addresses[0].addressState            | FL                  |
      | consumerAttributes.addresses[0].addressZip              | 32342               |
      | consumerAttributes.addresses[0].addressZipFour          | 1234                |
      | consumerAttributes.addresses[0].inactiveInd             | <inactiveIndAdr>    |
# set Phones OR inactivate it by switching inactiveInd to true
      | consumerAttributes.phones[0].phoneNumber                | 6456345564          |
      | consumerAttributes.phones[0].preference                 | 1                   |
      | consumerAttributes.phones[0].phoneType                  | Cell                |
      | consumerAttributes.phones[0].inactiveInd                | <inactiveIndPhone>  |
# set Emails OR inactivate it by switching inactiveInd to true
      | consumerAttributes.emails[0].emailAddress               | emailTest@yahoo.com |
      | consumerAttributes.emails[0].emailType                  | Personal            |
      | consumerAttributes.emails[0].preference                 | 1                   |
      | consumerAttributes.emails[0].inactiveInd                | <inactiveIndEmail>  |
      | dataSource                                              | System              |
      | createdBy                                               | autoScript          |
      | uuid                                                    | random              |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Examples:
      | optInAddress | optInPhone | optInEmail | doNotCall | inactiveIndAdr | inactiveIndPhone | inactiveIndEmail |
      | true         | false      | false      | false     | true           | true             | true             |
      | false        | true       | false      | false     | true           | true             | true             |
      | false        | false      | true       | false     | true           | true             | true             |
      | false        | false      | false      | true      | true           | true             | true             |
      | false        | false      | false      | false     | false          | true             | true             |
      | false        | false      | false      | false     | true           | false            | true             |
      | false        | false      | false      | false     | true           | true             | false            |

  @CP-48704 @CP-48704-01 @chopa @events @dc-eb-event
  Scenario: Verify active contact consumers pass record id and type = CONTACT_RECORD in the API call as linkedObjects
    Given I logged into CRM and select a project "DC-EB"
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate consumers V2 API
    When I run consumers API name "48704-01" with following payload
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
    When I click on initiate contact record
    When I navigate to manual case and consumer search page
    And I minimize Genesys popup if populates
    When I search just created consumer by external consumer ID
    And I navigate to consumer profile page by clicking on stateId
    When I click on add "Language" button on consumer profile page
    Then I input all required fields for "Text" in Correspondence Preference Page
    And I click on save button
    When I initiate contacts V2 API
    When I call consumer reported API with following payload
      | searchConsumerProfile.profileId                         | just created        |
      | consumerAttributes.phones[0].phoneNumber                | 6456345564          |
      | consumerAttributes.phones[0].preference                 | 1                   |
      | consumerAttributes.phones[0].phoneType                  | Cell                |
      | consumerAttributes.phones[0].inactiveInd                | false               |
    When I GET events by UUID using event correlation API
    Then I will verify event published for following events
      | UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT |
    Then I will verify UPDATE_ADDRESS_PHONE_EMAIL_BUSINESS_EVENT payload contains following data
      | linkedObjects          | not null                               |



