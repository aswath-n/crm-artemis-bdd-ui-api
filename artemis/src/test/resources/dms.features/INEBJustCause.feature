Feature: IN-EB Just Cause Form, Just Cause Approval and Just Cause Denial

  @CP-30100 @CP-30100-01 @CP-31757 @CP-31757-01 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Approval definition type JCAHIP
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCAHIP", Consumer ID and verify
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | memberName       |
#    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCAHIP            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data


  @CP-30100 @CP-30100-02 @CP-31757 @CP-31757-02 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Approval definition type JCAHHW
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCAHHW", Consumer ID and verify
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | memberName       |
    #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCAHHW            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data

  @CP-30100 @CP-30100-03 @CP-31757 @CP-31757-03 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Approval definition type JCAHCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCAHCC", Consumer ID and verify
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | memberName       |
     #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCAHCC            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data

  @CP-30101 @CP-30101-01 @CP-31757 @CP-31757-04 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Denial definition type JCDHIP
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCDHIP", Consumer ID and verify
      | memberName |
      #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCDHIP            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data

  @CP-30101 @CP-30101-02 @CP-31757 @CP-31757-05 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Denial definition type JCDHHW
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCDHHW", Consumer ID and verify
      | memberName |
          #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCDHHW            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data

  @CP-30101 @CP-30101-02 @CP-31757 @CP-31757-06 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Denial definition type JCDHCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCDHCC", Consumer ID and verify
      | memberName |
            #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCDHCC            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data

  @CP-29550 @CP-29550-01 @CP-32254 @CP-32254-01 @CP-31757 @CP-31757-07 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Form definition type JCFHIP with segment type LILO and active date
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have a request to create a new Enrollment
    And I send the request to create a new Enrollment
    And I have request to add other Segment to enrollment with the following values
      | consumerId      | previouslyCreated |
      | startDate       | pastDate          |
      | endDate         | futureDate        |
      | segmentTypeCode | LILO              |
    And I send the request to add other Segment to enrollment
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate get Other Enrollment Segment to get RCP value
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCFHIP", Consumer ID and verify
      | srCreateDate         |
      | rid                  |
      | memberName           |
      | grievanceNumber      |
      | grievanceDate        |
      | contactFirstName     |
      | contactLastName      |
      | contactPhone         |
      | contactEmail         |
      | addressLine1         |
      | addressLine2         |
      | addressCity          |
      | addressState         |
      | addressZip           |
      | program              |
      | currentPlanName      |
      | desiredPlanName      |
      | rcp                  |
      | reasonAndExplanation |
    #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCFHIP            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data


  @CP-29550 @CP-29550-02 @CP-32254 @CP-32254-02 @CP-31757 @CP-31757-08 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Form definition type JCFHCC with segment type LILO and active date
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have a request to create a new Enrollment
    And I send the request to create a new Enrollment
    And I have request to add other Segment to enrollment with the following values
      | consumerId      | previouslyCreated |
      | startDate       | pastDate          |
      | endDate         | todayDate         |
      | segmentTypeCode | LILO              |
    And I send the request to add other Segment to enrollment
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate get Other Enrollment Segment to get RCP value
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCFHCC", Consumer ID and verify
      | srCreateDate         |
      | rid                  |
      | memberName           |
      | grievanceNumber      |
      | grievanceDate        |
      | contactFirstName     |
      | contactLastName      |
      | contactPhone         |
      | contactEmail         |
      | addressLine1         |
      | addressLine2         |
      | addressCity          |
      | addressState         |
      | addressZip           |
      | program              |
      | currentPlanName      |
      | desiredPlanName      |
      | rcp                  |
      | reasonAndExplanation |
              #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCFHCC            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data

  @CP-29550 @CP-29550-03 @CP-32254 @CP-32254-03 @CP-31757 @CP-31757-09 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Form definition type JCFHHW with segment type LILO and active date
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have a request to create a new Enrollment
    And I send the request to create a new Enrollment
    And I have request to add other Segment to enrollment with the following values
      | consumerId      | previouslyCreated |
      | startDate       | pastDate          |
      | endDate         | futureDate        |
      | segmentTypeCode | LILO              |
    And I send the request to add other Segment to enrollment
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate get Other Enrollment Segment to get RCP value
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCFHHW", Consumer ID and verify
      | srCreateDate         |
      | rid                  |
      | memberName           |
      | grievanceNumber      |
      | grievanceDate        |
      | contactFirstName     |
      | contactLastName      |
      | contactPhone         |
      | contactEmail         |
      | addressLine1         |
      | addressLine2         |
      | addressCity          |
      | addressState         |
      | addressZip           |
      | program              |
      | currentPlanName      |
      | desiredPlanName      |
      | rcp                  |
      | reasonAndExplanation |
                #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCFHHW            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data

  @CP-29550 @CP-29550-04 @CP-32254 @CP-32254-04 @CP-31757 @CP-31757-10 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Form definition type JCFHIP with segment type LILO and inactive date
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have a request to create a new Enrollment
    And I send the request to create a new Enrollment
    And I have request to add other Segment to enrollment with the following values
      | consumerId      | previouslyCreated |
      | startDate       | futureDate        |
      | endDate         | futureDate        |
      | segmentTypeCode | LILO              |
    And I send the request to add other Segment to enrollment
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate get Other Enrollment Segment to get RCP value
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCFHIP", Consumer ID and verify
      | srCreateDate         |
      | rid                  |
      | memberName           |
      | grievanceNumber      |
      | grievanceDate        |
      | contactFirstName     |
      | contactLastName      |
      | contactPhone         |
      | contactEmail         |
      | addressLine1         |
      | addressLine2         |
      | addressCity          |
      | addressState         |
      | addressZip           |
      | program              |
      | currentPlanName      |
      | desiredPlanName      |
      | rcp                  |
      | reasonAndExplanation |
                 #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCFHIP            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data

  @CP-29550 @CP-29550-05 @CP-32254 @CP-32254-05 @api-ecms-ineb @CP-31757 @CP-31757-11 @RuslanL
  Scenario: Verify Body Data for Just Cause Form definition type JCFHCC with segment type LILO and inactive date
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have a request to create a new Enrollment
    And I send the request to create a new Enrollment
    And I have request to add other Segment to enrollment with the following values
      | consumerId      | previouslyCreated |
      | startDate       | futureDate        |
      | endDate         | futureDate        |
      | segmentTypeCode | LILO              |
    And I send the request to add other Segment to enrollment
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate get Other Enrollment Segment to get RCP value
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCFHCC", Consumer ID and verify
      | srCreateDate         |
      | rid                  |
      | memberName           |
      | grievanceNumber      |
      | grievanceDate        |
      | contactFirstName     |
      | contactLastName      |
      | contactPhone         |
      | contactEmail         |
      | addressLine1         |
      | addressLine2         |
      | addressCity          |
      | addressState         |
      | addressZip           |
      | program              |
      | currentPlanName      |
      | desiredPlanName      |
      | rcp                  |
      | reasonAndExplanation |
                   #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCFHCC            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data

  @CP-29550 @CP-29550-06 @CP-32254 @CP-32254-06 @CP-31757 @CP-31757-12 @api-ecms-ineb @RuslanL
  Scenario: Verify Body Data for Just Cause Form definition type JCFHHW with segment type LILO and inactive date
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I have a request to create a new Enrollment
    And I send the request to create a new Enrollment
    And I have request to add other Segment to enrollment with the following values
      | consumerId      | previouslyCreated |
      | startDate       | futureDate        |
      | endDate         | futureDate        |
      | segmentTypeCode | LILO              |
    And I send the request to add other Segment to enrollment
    When I have a request to create External task with the following values
      | CASE       | previouslyCreated |
      | CONSUMER   | previouslyCreated |
      | taskTypeId | 15350             |
    Then  I send the request for the External task endpoint
    And I have request to update service request with the following values
      | CurrentPlan           | random    |
      | DesiredPlan           | random    |
      | NewPlanStartDate      | todayDate |
      | RID                   | random    |
      | Grievance             | random    |
      | FromName              | random    |
      | FromPhone             | random    |
      | Email                 | random    |
      | ProgramRequired       | random    |
      | Reason                | random    |
      | Explanation           | random    |
      | DateGrievanceReceived | todayDate |
    When I send the request to update service request
    And I initiate get service request API by ID
    And I initiate get Other Enrollment Segment to get RCP value
    And I initiate Case Correspondence API for created Consumer
    And I initiate get letter body data api by type "JCFHHW", Consumer ID and verify
      | srCreateDate         |
      | rid                  |
      | memberName           |
      | grievanceNumber      |
      | grievanceDate        |
      | contactFirstName     |
      | contactLastName      |
      | contactPhone         |
      | contactEmail         |
      | addressLine1         |
      | addressLine2         |
      | addressCity          |
      | addressState         |
      | addressZip           |
      | program              |
      | currentPlanName      |
      | desiredPlanName      |
      | rcp                  |
      | reasonAndExplanation |
                   #    CP-31757
    And I have specified the following values in the request for a Just Cause Outbound Correspondence "IN-EB"
      | correspondenceDefinitionMMSCode | JCFHHW            |
      | channelType                     | Mail              |
      | language                        | English           |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | requesterId                     | 2425              |
      | requesterType                   | ConnectionPoint   |
      | address                         | random            |
    When I send the request for an Outbound Correspondence to the service
    And I retrieve the Outbound Correspondence by Correspondence ID
    And I initiate Get letter data by the retrieved NID
    And I verify get letter data response contains expected body data


  @CP-29809 @CP-29809-01 @ui-ecms-ineb @RuslanL @ECMS-SMOKE
  Scenario Outline: Verify pdf generated for Just Cause Form HIP
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I initiate Case Correspondence API for created Consumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to "Just Cause" service request page
    And I will provide following information on Service Request details page
      | rid              |
      | grievanceNumber  |
      | grievanceDate    |
      | contactName      |
      | contactPhone     |
      | contactEmail     |
      | program          |
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | reason           |
      | explanation      |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for task
    And I create an Outbound Correspondence
    And I have selected "Just Cause Form HIP - JCFHIP" as a type
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
      | 100 Main street  Canaan,NY 12029 |
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    And I click on the view icon for created OB correspondence
    Then I should see the Outbound Document is viewable for type "JCFHIP" and contains following values
      | title              |
      | correspondenceDate |
      | rid                |
      | memberName         |
      | grievanceNumber    |
      | grievanceDate      |
      | contactFirstName   |
      | contactLastName    |
      | contactPhone       |
      | contactEmail       |
      | addressLine1       |
      | addressLine2       |
      | addressCity        |
      | addressState       |
      | addressZip         |
      | currentPlanName    |
      | desiredPlanName    |
      | reason             |
      | explanation        |
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-29809 @CP-29809-02 @ui-ecms-ineb @RuslanL @ECMS-SMOKE
  Scenario Outline: Verify pdf generated for Just Cause Form HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I initiate Case Correspondence API for created Consumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to "Just Cause" service request page
    And I will provide following information on Service Request details page
      | rid              |
      | grievanceNumber  |
      | grievanceDate    |
      | contactName      |
      | contactPhone     |
      | contactEmail     |
      | program          |
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | reason           |
      | explanation      |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for task
    And I create an Outbound Correspondence
    And I have selected "Just Cause Form HCC - JCFHCC" as a type
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
      | 100 Main street  Canaan,NY 12029 |
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    And I click on the view icon for created OB correspondence
    Then I should see the Outbound Document is viewable for type "JCFHCC" and contains following values
      | title              |
      | correspondenceDate |
      | rid                |
      | memberName         |
      | grievanceNumber    |
      | grievanceDate      |
      | contactFirstName   |
      | contactLastName    |
      | contactPhone       |
      | contactEmail       |
      | addressLine1       |
      | addressLine2       |
      | addressCity        |
      | addressState       |
      | addressZip         |
      | currentPlanName    |
      | desiredPlanName    |
      | reason             |
      | explanation        |
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-29809 @CP-29809-03 @ui-ecms-ineb @RuslanL @ECMS-SMOKE
  Scenario Outline: Verify pdf generated for Just Cause Form HHW
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I initiate Case Correspondence API for created Consumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to "Just Cause" service request page
    And I will provide following information on Service Request details page
      | rid              |
      | grievanceNumber  |
      | grievanceDate    |
      | contactName      |
      | contactPhone     |
      | contactEmail     |
      | program          |
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | reason           |
      | explanation      |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for task
    And I create an Outbound Correspondence
    And I have selected "Just Cause Form HHW - JCFHHW" as a type
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
      | 100 Main street  Canaan,NY 12029 |
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    And I click on the view icon for created OB correspondence
    Then I should see the Outbound Document is viewable for type "JCFHHW" and contains following values
      | title              |
      | correspondenceDate |
      | rid                |
      | memberName         |
      | grievanceNumber    |
      | grievanceDate      |
      | contactFirstName   |
      | contactLastName    |
      | contactPhone       |
      | contactEmail       |
      | addressLine1       |
      | addressLine2       |
      | addressCity        |
      | addressState       |
      | addressZip         |
      | currentPlanName    |
      | desiredPlanName    |
      | reason             |
      | explanation        |
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |


  @CP-29981 @CP-29981-01 @CP-12307-04 @ui-ecms-ineb @RuslanL @ECMS-SMOKE
  Scenario Outline: Verify pdf generated for Just Cause Denial HIP
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I initiate Case Correspondence API for created Consumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to "Just Cause" service request page
    And I will provide following information on Service Request details page
      | rid              |
      | grievanceNumber  |
      | grievanceDate    |
      | contactName      |
      | contactPhone     |
      | contactEmail     |
      | program          |
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | reason           |
      | explanation      |
    And I click on save button in create service request page
   # Then I verify Success message is displayed for task
    And I create an Outbound Correspondence
    And I have selected "Just Cause Denial HIP - JCDHIP" as a type
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
      | 100 Main street  Canaan,NY 12029 |
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    And I click on the view icon for created OB correspondence
    Then I should see the Outbound Document is viewable for type "JCDHIP" and contains following values
      | title             |
      | memberName        |
      | correspondenceDate |
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-29981 @CP-29981-02 @ui-ecms-ineb @RuslanL @ECMS-SMOKE
  Scenario Outline: Verify pdf generated for Just Cause Denial HCC
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I initiate Case Correspondence API for created Consumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to "Just Cause" service request page
    And I will provide following information on Service Request details page
      | rid              |
      | grievanceNumber  |
      | grievanceDate    |
      | contactName      |
      | contactPhone     |
      | contactEmail     |
      | program          |
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | reason           |
      | explanation      |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for task
    And I create an Outbound Correspondence
    And I have selected "Just Cause Denial HCC - JCDHCC" as a type
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
      | 100 Main street  Canaan,NY 12029 |
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    And I click on the view icon for created OB correspondence
    Then I should see the Outbound Document is viewable for type "JCDHCC" and contains following values
      | title             |
      | memberName        |
      | correspondenceDate |
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-29981 @CP-29981-03 @ui-ecms-ineb @RuslanL @ECMS-SMOKE
  Scenario Outline: Verify pdf generated for Just Cause Denial HHW
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I initiate Case Correspondence API for created Consumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to "Just Cause" service request page
    And I will provide following information on Service Request details page
      | rid              |
      | grievanceNumber  |
      | grievanceDate    |
      | contactName      |
      | contactPhone     |
      | contactEmail     |
      | program          |
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | reason           |
      | explanation      |
    And I click on save button in create service request page
   # Then I verify Success message is displayed for task
    And I create an Outbound Correspondence
    And I have selected "Just Cause Denial HHW - JCDHHW" as a type
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
      | 100 Main street  Canaan,NY 12029 |
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    And I click on the view icon for created OB correspondence
    Then I should see the Outbound Document is viewable for type "JCDHHW" and contains following values
      | title             |
      | memberName        |
      | correspondenceDate |
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |


  @CP-29551 @CP-29551-01 @ui-ecms-ineb @RuslanL @ECMS-SMOKE
  Scenario Outline: Verify pdf generated for Just Cause Approval HIP
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I initiate Case Correspondence API for created Consumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to "Just Cause" service request page
    And I will provide following information on Service Request details page
      | rid              |
      | grievanceNumber  |
      | grievanceDate    |
      | contactName      |
      | contactPhone     |
      | contactEmail     |
      | program          |
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | reason           |
      | explanation      |
    And I click on save button in create service request page
   # Then I verify Success message is displayed for task
    And I create an Outbound Correspondence
    And I have selected "Just Cause Approval HIP - JCAHIP" as a type
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
      | 100 Main street  Canaan,NY 12029 |
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    And I click on the view icon for created OB correspondence
    Then I should see the Outbound Document is viewable for type "JCAHIP" and contains following values
      | title             |
      | currentPlanName   |
      | desiredPlanName   |
      | newPlanStartDate  |
      | memberName        |
      | correspondenceDate |
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-29551 @CP-29551-02 @ui-ecms-ineb @RuslanL @ECMS-SMOKE
  Scenario Outline: Verify pdf generated for Just Cause Approval HCC Just Cause type
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I initiate Case Correspondence API for created Consumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to "Just Cause" service request page
    And I will provide following information on Service Request details page
      | rid              |
      | grievanceNumber  |
      | grievanceDate    |
      | contactName      |
      | contactPhone     |
      | contactEmail     |
      | program          |
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | reason           |
      | explanation      |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for task
    And I create an Outbound Correspondence
    And I have selected "Just Cause Approval HCC - JCAHCC" as a type
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
      | 100 Main street  Canaan,NY 12029 |
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    And I click on the view icon for created OB correspondence
    Then I should see the Outbound Document is viewable for type "JCAHCC" and contains following values
      | title             |
      | currentPlanName   |
      | desiredPlanName   |
      | newPlanStartDate  |
      | memberName        |
      | correspondenceDate |
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |

  @CP-29551 @CP-29551-03 @ui-ecms-ineb @RuslanL @ECMS-SMOKE
  Scenario Outline: Verify pdf generated for Just Cause Approval HHW
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    And I initiate Case Correspondence API for created Consumer
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click case consumer search tab
    And I searched consumer created by api script
    And I click on the first case and consumer search result
    When I navigate to "Just Cause" service request page
    And I will provide following information on Service Request details page
      | rid              |
      | grievanceNumber  |
      | grievanceDate    |
      | contactName      |
      | contactPhone     |
      | contactEmail     |
      | program          |
      | currentPlanName  |
      | desiredPlanName  |
      | newPlanStartDate |
      | reason           |
      | explanation      |
    And I click on save button in create service request page
    #Then I verify Success message is displayed for task
    And I create an Outbound Correspondence
    And I have selected "Just Cause Approval HHW - JCAHHW" as a type
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
      | 100 Main street  Canaan,NY 12029 |
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    And I click on the view icon for created OB correspondence
    Then I should see the Outbound Document is viewable for type "JCAHHW" and contains following values
      | title             |
      | currentPlanName   |
      | desiredPlanName   |
      | newPlanStartDate  |
      | memberName        |
      | correspondenceDate |
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 100 Main street |empty| Canaan | New York | 12029   |