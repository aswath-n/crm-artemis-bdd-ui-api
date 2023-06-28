@CP-24346
Feature: Process outbound correspondence Response files from Print Vendor for INEB and BLCRM

  @CP-24346-1.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-1
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated   |
      | pdfStatus         | Mailed              |
      | pdfReasonText     | pdf reason text     |
      | addressStatus     | OK                  |
      | addressReasonText | address Reason Text |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Sent            |
      | Status Reason    |[blank]|
      | ErrorDetail      | pdf reason text |
      | objectReceivedOn | null            |
      | Updated Date     | current         |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-2.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-2
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated |
      | pdfStatus         | Mailed            |
      | pdfReasonText     | pdf reason text   |
      | addressStatus     | Replaced          |
      | addressReasonText |[blank]|
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Sent            |
      | Status Reason    |[blank]|
      | ErrorDetail      | pdf reason text |
      | objectReceivedOn | null            |
      | Updated Date     | current         |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-3.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-3
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated   |
      | pdfStatus         | Assembled           |
      | pdfReasonText     |[blank]|
      | addressStatus     | OK                  |
      | addressReasonText | address Reason Text |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Sent                |
      | Status Reason    |[blank]|
      | ErrorDetail      | address Reason Text |
      | objectReceivedOn | null                |
      | Updated Date     | current             |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-4.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-4
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated |
      | pdfStatus         | Assembled         |
      | pdfReasonText     |[blank]|
      | addressStatus     | Replaced          |
      | addressReasonText |[blank]|
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Sent    |
      | Status Reason    |[blank]|
      | ErrorDetail      |[blank]|
      | objectReceivedOn | null    |
      | Updated Date     | current |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"


  @CP-24346-5.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-5
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated     |
      | pdfStatus         | Assembled             |
      | pdfReasonText     | Requested not to mail |
      | addressStatus     | Proposed              |
      | addressReasonText |[blank]|
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                 |
      | Status Reason    | Invalid address       |
      | ErrorDetail      | Requested not to mail |
      | objectReceivedOn | null                  |
      | Updated Date     | current               |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-6.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-6
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated       |
      | pdfStatus         | Assembled               |
      | pdfReasonText     | Template not recognized |
      | addressStatus     | Invalid                 |
      | addressReasonText | addressReasonText       |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                   |
      | Status Reason    | Invalid address         |
      | ErrorDetail      | Template not recognized |
      | objectReceivedOn | null                    |
      | Updated Date     | current                 |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-7.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-7
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated           |
      | pdfStatus         | Error                       |
      | pdfReasonText     | Missing data element <name> |
      | addressStatus     | OK                          |
      | addressReasonText | addressReasonText           |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                       |
      | Status Reason    | Assembly error              |
      | ErrorDetail      | Missing data element <name> |
      | objectReceivedOn | null                        |
      | Updated Date     | current                     |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-8.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-8
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated           |
      | pdfStatus         | Error                       |
      | pdfReasonText     | Missing data element <name> |
      | addressStatus     | Invalid                     |
      | addressReasonText | addressReasonText           |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                       |
      | Status Reason    | Assembly error              |
      | ErrorDetail      | Missing data element <name> |
      | objectReceivedOn | null                        |
      | Updated Date     | current                     |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-9.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-9
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated           |
      | pdfStatus         | Error                       |
      | pdfReasonText     | Missing data element <name> |
      | addressStatus     | Proposed                    |
      | addressReasonText | addressReasonText           |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                       |
      | Status Reason    | Assembly error              |
      | ErrorDetail      | Missing data element <name> |
      | objectReceivedOn | null                        |
      | Updated Date     | current                     |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-10.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-10
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated           |
      | pdfStatus         | Error                       |
      | pdfReasonText     | Missing data element <name> |
      | addressStatus     | Replaced                    |
      | addressReasonText | addressReasonText           |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                       |
      | Status Reason    | Assembly error              |
      | ErrorDetail      | Missing data element <name> |
      | objectReceivedOn | null                        |
      | Updated Date     | current                     |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-11.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-11
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated                               |
      | pdfStatus         | Assembled                                       |
      | pdfReasonText     | Invalid number of occurrences of element <name> |
      | addressStatus     | Returned                                        |
      | addressReasonText | Refused                                         |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Returned                                        |
      | Status Reason    | Refused                                         |
      | ErrorDetail      | Invalid number of occurrences of element <name> |
      | objectReceivedOn | null                                            |
      | Updated Date     | current                                         |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-12.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-12
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated    |
      | pdfStatus         | Mailed               |
      | pdfReasonText     | Missing data element |
      | addressStatus     | Returned             |
      | addressReasonText | addressReasonText    |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Returned             |
      | Status Reason    | Undeliverable        |
      | ErrorDetail      | Missing data element |
      | objectReceivedOn | null                 |
      | Updated Date     | current              |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

  @CP-24346-13.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify INEB Notification Record is updated when Mail House Response Object Job runs combo-13
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    Then I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have specified the following values in the request for an Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | default           |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated                             |
      | pdfStatus         | Error                                         |
      | pdfReasonText     | Invalid data value <value> for element <name> |
      | addressStatus     | Returned                                      |
      | addressReasonText |[blank]|
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-ineb-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "INEB"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Returned                                      |
      | Status Reason    | Undeliverable                                 |
      | ErrorDetail      | Invalid data value <value> for element <name> |
      | objectReceivedOn | null                                          |
      | Updated Date     | current                                       |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-ineb-non-prod" folder "ECMS/Archive/"

###################################################### BLCRM ##############################################################

  @CP-16460-1.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-1
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated   |
      | pdfStatus         | Mailed              |
      | pdfReasonText     | pdf reason text     |
      | addressStatus     | OK                  |
      | addressReasonText | address Reason Text |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Sent            |
      | Status Reason    |[blank]|
      | ErrorDetail      | pdf reason text |
      | objectReceivedOn | null            |
      | Updated Date     | current         |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-2.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-2
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated |
      | pdfStatus         | Mailed            |
      | pdfReasonText     | pdf reason text   |
      | addressStatus     | Replaced          |
      | addressReasonText |[blank]|
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Sent            |
      | Status Reason    |[blank]|
      | ErrorDetail      | pdf reason text |
      | objectReceivedOn | null            |
      | Updated Date     | current         |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-3.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-3
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated   |
      | pdfStatus         | Assembled           |
      | pdfReasonText     |[blank]|
      | addressStatus     | OK                  |
      | addressReasonText | address Reason Text |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Sent                |
      | Status Reason    |[blank]|
      | ErrorDetail      | address Reason Text |
      | objectReceivedOn | null                |
      | Updated Date     | current             |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-4.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-4
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated |
      | pdfStatus         | Assembled         |
      | pdfReasonText     |[blank]|
      | addressStatus     | Replaced          |
      | addressReasonText |[blank]|
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Sent    |
      | Status Reason    |[blank]|
      | ErrorDetail      |[blank]|
      | objectReceivedOn | null    |
      | Updated Date     | current |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-5.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-5
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated     |
      | pdfStatus         | Assembled             |
      | pdfReasonText     | Requested not to mail |
      | addressStatus     | Proposed              |
      | addressReasonText |[blank]|
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                 |
      | Status Reason    | Invalid address       |
      | ErrorDetail      | Requested not to mail |
      | objectReceivedOn | null                  |
      | Updated Date     | current               |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-6.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-6
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated       |
      | pdfStatus         | Assembled               |
      | pdfReasonText     | Template not recognized |
      | addressStatus     | Invalid                 |
      | addressReasonText | addressReasonText       |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                   |
      | Status Reason    | Invalid address         |
      | ErrorDetail      | Template not recognized |
      | objectReceivedOn | null                    |
      | Updated Date     | current                 |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-7.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-7
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated           |
      | pdfStatus         | Error                       |
      | pdfReasonText     | Missing data element <name> |
      | addressStatus     | OK                          |
      | addressReasonText | addressReasonText           |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                       |
      | Status Reason    | Assembly error              |
      | ErrorDetail      | Missing data element <name> |
      | objectReceivedOn | null                        |
      | Updated Date     | current                     |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"


  @CP-16460-8.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-8
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated           |
      | pdfStatus         | Error                       |
      | pdfReasonText     | Missing data element <name> |
      | addressStatus     | Invalid                     |
      | addressReasonText | addressReasonText           |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                       |
      | Status Reason    | Assembly error              |
      | ErrorDetail      | Missing data element <name> |
      | objectReceivedOn | null                        |
      | Updated Date     | current                     |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-9.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-9
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated           |
      | pdfStatus         | Error                       |
      | pdfReasonText     | Missing data element <name> |
      | addressStatus     | Proposed                    |
      | addressReasonText | addressReasonText           |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                       |
      | Status Reason    | Assembly error              |
      | ErrorDetail      | Missing data element <name> |
      | objectReceivedOn | null                        |
      | Updated Date     | current                     |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-10.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-10
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated           |
      | pdfStatus         | Error                       |
      | pdfReasonText     | Missing data element <name> |
      | addressStatus     | Replaced                    |
      | addressReasonText | addressReasonText           |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Error                       |
      | Status Reason    | Assembly error              |
      | ErrorDetail      | Missing data element <name> |
      | objectReceivedOn | null                        |
      | Updated Date     | current                     |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-11.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-11
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated                               |
      | pdfStatus         | Assembled                                       |
      | pdfReasonText     | Invalid number of occurrences of element <name> |
      | addressStatus     | Returned                                        |
      | addressReasonText | Refused                                         |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Returned                                        |
      | Status Reason    | Refused                                         |
      | ErrorDetail      | Invalid number of occurrences of element <name> |
      | objectReceivedOn | null                                            |
      | Updated Date     | current                                         |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-12.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-12
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated    |
      | pdfStatus         | Mailed               |
      | pdfReasonText     | Missing data element |
      | addressStatus     | Returned             |
      | addressReasonText | addressReasonText    |
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Returned             |
      | Status Reason    | Undeliverable        |
      | ErrorDetail      | Missing data element |
      | objectReceivedOn | null                 |
      | Updated Date     | current              |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"

  @CP-16460-13.1 @CORRESPONDENCE_ETL_REGRESSION @Keerthi
  Scenario: Verify BLCRM Notification Record is updated when Mail House Response Object Job runs combo-13
    Given I have cleaned up the target folder from previous "mailhouseResponseJob" runs
    And I will get the Authentication token for "BLCRM" in "CRM"
    And I have an existing Outbound Correspondence with regarding values consisting of the following
      | CaseId | Random |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I have a json file named after the notification and updated with following values
      | nid               | previouslyCreated                             |
      | pdfStatus         | Error                                         |
      | pdfReasonText     | Invalid data value <value> for element <name> |
      | addressStatus     | Returned                                      |
      | addressReasonText |[blank]|
    And I upload the "mailhouseResponseJob" file to aws bucket "max-etl-baseline-non-prod" folder "ECMS/to_Max/Daily/"
    When I run the "mailhouseResponseJob" in project "BLCRM"
    Then I initiated GET outbound Correspondence API with "previouslyCreated" CId
    Then I verify the following values in the correspondence notification response
      | Status           | Returned                                      |
      | Status Reason    | Undeliverable                                 |
      | ErrorDetail      | Invalid data value <value> for element <name> |
      | objectReceivedOn | null                                          |
      | Updated Date     | current                                       |
    Then I verify the "previouslyCreated" file that was been created in the aws bucket "max-etl-baseline-non-prod" folder "ECMS/Archive/"