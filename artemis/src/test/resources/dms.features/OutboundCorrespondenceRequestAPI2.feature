@CP-24608
Feature: Outbound Correspondence Requests API Updates 2 Part B

  @CP-24608-18  @API-ECMS @Prithika
  Scenario:6.1 If the Channel is for the Mail Channel, a delivery line (street, PO Box, etc.), a city, a state, and a zip code must all be provided
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CAONLY      |
      | CaseID                          | 802         |
      | channelType                     | Mail        |
      | streetAddress                   | test lane 1 |
      | city                            | cedar park  |
      | zipcode                         | 78613       |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                                                                |
      | message | If the Channel is for the Mail Channel, a delivery line (street, PO Box, etc.), a city, a state, and a zip code must all be provided |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CAONLY      |
      | CaseID                          | 802         |
      | channelType                     | Mail        |
      | streetAddress                   | test lane 1 |
      | city                            | cedar park  |
      | state                           | TX          |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                                                                |
      | message | If the Channel is for the Mail Channel, a delivery line (street, PO Box, etc.), a city, a state, and a zip code must all be provided |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608     |
      | CaseID                          | 802         |
      | channelType                     | Mail        |
      | streetAddress                   | test lane 1 |
      | state                           | TX          |
      | zipcode                         | 78613       |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                                                                |
      | message | If the Channel is for the Mail Channel, a delivery line (street, PO Box, etc.), a city, a state, and a zip code must all be provided |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608    |
      | CaseID                          | 802        |
      | channelType                     | Mail       |
      | city                            | cedar park |
      | state                           | TX         |
      | zipcode                         | 78613      |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                                                                |
      | message | If the Channel is for the Mail Channel, a delivery line (street, PO Box, etc.), a city, a state, and a zip code must all be provided |

  @CP-24608-19  @API-ECMS @Prithika
  Scenario: 6.2 If a state is provided it must be a valid 2 character postal state or territory abbreviation
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CAONLY      |
      | CaseID                          | 802         |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | 12          |
      | zipcode                         | 78613       |
      | streetAddress                   | test lane 1 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                    |
      | message | If a state is provided it must be a valid 2 digit postal state or territory abbreviation |

  @CP-24608-20 @API-ECMS @Prithika
  Scenario: 6.3 If a zip code is provided it must be a valid 5 digit zip code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CAONLY      |
      | CaseID                          | 802         |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | TX          |
      | zipcode                         | 12          |
      | streetAddress                   | test lane 1 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                                     |
      | message | If zipcode is provided then this should be in one of the following format: xxxxx, xxxxxxxxx or xxxxx-xxxx |

  @CP-24608-21 @API-ECMS @Prithika
  Scenario:6.5 If an email address is provided it must conform to the format defined in the Create Correspondence screen specification
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase      |
      | CaseID                          | 802            |
      | channelType                     | Email          |
      | emailAddress                    | test.gmail@com |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                |
      | message | Invalid Email Format |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase                                                       |
      | CaseID                          | 802                                                             |
      | channelType                     | Email                                                           |
      | emailAddress                    | testsdfgsdfgsdfgsdfgsdfgggggggggggggrestteesggggggggg.gmail#com |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                |
      | message | Invalid Email Format |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase |
      | CaseID                          | 802       |
      | channelType                     | Email     |
      | emailAddress                    |           |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                |
      | message | Invalid Email Format |

  @CP-24608-22 @API-ECMS @Prithika
  Scenario: 6.7 If a text phone number is provided it must be a 10 digit number that begins with 2 thru 9
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase |
      | CaseID                          | 802       |
      | channelType                     | Text      |
      | textNumber                      | 1299699   |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR               |
      | message | Invalid Text Number |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase  |
      | CaseID                          | 802        |
      | channelType                     | Text       |
      | textNumber                      | 1234567890 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR               |
      | message | Invalid Text Number |

  @CP-24608-23 @API-ECMS @Prithika
  Scenario: 6.9 If a fax phone number is provided it must be a 10 digit number that begins with 2 thru 9
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase |
      | CaseID                          | 802       |
      | channelType                     | Fax       |
      | faxNumber                       | 1299699   |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR              |
      | message | Invalid Fax Number |
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | allChCase  |
      | CaseID                          | 802        |
      | channelType                     | Fax        |
      | faxNumber                       | 1234567890 |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR              |
      | message | Invalid Fax Number |

  @CP-24608-24  @API-ECMS @Prithika
  Scenario: 7.0 Body data may be provided optionally
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CONSONLY |
      | regardingConsumerId             | 1,4,3    |
      | CaseID                          | empty    |
    Then I should see a success status in the response
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CONSONLY |
      | regardingConsumerId             | 1,4,3    |
      | CaseID                          | empty    |
      | bodyData                        | null     |
    Then I should see a success status in the response

  @CP-24608-25 @API-ECMS @Prithika
  Scenario: 7.1 If body data is provided (i.e. is not null) it must conform to the body data structure defined for the Correspondence Type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | TestBD                                                 |
      | regardingConsumerId             | 1,4,3                                                  |
      | CaseID                          | empty                                                  |
      | bodyData                        | FirstName-String&Age-Number&Date-Date&Married-Checkbox |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                         |
      | message | Body data schema validation error: [FirstNAme: is missing but it is required] |

  @CP-24608-26 @API-ECMS @Prithika
  Scenario: 7.1 If body data is provided (i.e. is not null) it must conform to the body data structure defined for the Correspondence Type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | TestBD                                                 |
      | regardingConsumerId             | 1,4,3                                                  |
      | CaseID                          | empty                                                  |
      | bodyData                        | FirstNAme-String&Age-String&Date-Number&Married-String |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                                                                                                                                                                           |
      | message | Body data schema validation error: [FirstNAme: string found, array expected, Age: string found, number expected, Date: integer found, string expected, Married: string found, boolean expected] |

  @CP-24608-27  @API-ECMS @Prithika
  Scenario: 7.1 If body data is provided (i.e. is not null) it must conform to the body data structure defined for the Correspondence Type
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | TestBD                                                |
      | regardingConsumerId             | 1,4,3                                                 |
      | CaseID                          | empty                                                 |
      | bodyData                        | FirstNAme-Array&Age-Number&Date-Date&Married-Checkbox |
    Then I should see a success status in the response

  @CP-24608-28  @API-ECMS @Prithika
  Scenario: 7.2 If body data is provided and the Correspondence Type does not have a body data structure defined this is ok
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CONSONLY  |
      | regardingConsumerId             | 1,4,3     |
      | CaseID                          | empty     |
      | bodyData                        | firstName |
    Then I should see a success status in the response

  @CP-24608-29  @API-ECMS @Prithika
  Scenario: 8.0 The user ID submitting the request must be provided (in the requesterId element)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CONSONLY |
      | regardingConsumerId             | 1,4,3    |
      | CaseID                          | empty    |
      | requesterId                     | empty    |
    Then I should see failed status and the reason for the failure as follows
      | status  | ERROR                                               |
      | message | The user ID submitting the request must be provided |

  @CP-24608-30  @API-ECMS @Prithika
  Scenario: 9.0 Created/Updated By/On
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CONSONLY |
      | regardingConsumerId             | 1,4,3    |
      | CaseID                          | empty    |
      | requesterId                     | 1066     |
    Then I should see a success status in the response
    Then I verify response has the created by and on values from above request

  @CP-24608-31 @API-ECMS @Prithika
  Scenario: 10.0 If contactId is passed the system creates external links between the Contact and Outbound Correspondence in the Contact and Correspondence services
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608        |
      | anchor                          | empty          |
      | firstName                       | test           |
      | lastName                        | test           |
      | channelType                     | Email          |
      | emailAddress                    | test@gmail.com |
      | contactId                       | 2108           |
    And I get the Links for Correspondence response
    And I verify Link is created for correspondence and Contact Id

  @CP-24608-32 @API-ECMS @Prithika
  Scenario: 11.0 If taskId is passed the system creates links between the Task and Outbound Correspondence in the Task and Correspondence services
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | CP24608        |
      | anchor                          | empty          |
      | firstName                       | test           |
      | lastName                        | test           |
      | channelType                     | Email          |
      | emailAddress                    | test@gmail.com |
      | taskId                          | 2010           |
    And I get the Links for Correspondence response
    And I verify Link is created for correspondence and Task Id

  @CP-14054-01 @API-ECMS @Prithika
  Scenario: Baseline: Welcome Letter 2 (WL2) Template - Language English
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | WL2               |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | channelType                     | Mail              |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78641             |
      | streetAddress                   | test lane 1       |
      | language                        | English           |
    And I verify the response status code 201 and status "success"

  @CP-14054-02 @API-ECMS @Prithika
  Scenario: Baseline: Welcome Letter 2 (WL2) Template - Language Spanish
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | WL2               |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | channelType                     | Mail              |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78641             |
      | streetAddress                   | test lane 1       |
      | language                        | Spanish           |
    And I verify the response status code 201 and status "success"

  @CP-11069 @API-ECMS @Prithika
  Scenario: Configure Welcome Letter 2 Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to retrieve a Inbound Correspondence Definition by mmsCode "WL2"
    And I verify the correspondence definition has the following values
      | MMSID                | WL2                                                                                  |
      | CORRESPONDENCENAME   | Welcome Letter 2 Baseline                                                            |
      | STATEID              |                                                                                      |
      | USERMAYREQUEST       | false                                                                                |
      | STARTDATE            | past Date                                                                            |
      | END DATE             | null                                                                                 |
      | IBCORRESPONDENCETYPE | maersk Returned Mail                                                                |
      | DESCRIPTION          | A baseline proof of concept for triggering System Event Driven letters - foster care |
      | END REASON           | empty                                                                                |
    And I verify the correspondence definition has the following required Key
      | REQUIREDKEYVALUE | Consumer ID |
    And I verify the correspondence definition has the following Channel Values
      | CHANNELTYPE               | Mail            |
      | SEND IMMEDIATELY CHECKBOX | false           |
      | MANDATORY CHECKBOX        | false           |
      | STARTDATE                 | pastDate        |
      | END DATE                  | null            |
      | END REASON                | empty           |
      | SENDER EMAIL              |                 |
      | LANGUAGE                  | English,Spanish |

  @CP-14050-01 @API-ECMS @Prithika
  Scenario: Baseline: Welcome Letter 1 (WL1) Template - Language English
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | WL1               |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | channelType                     | Mail              |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78641             |
      | streetAddress                   | test lane 1       |
      | language                        | English           |
    And I verify the response status code 201 and status "success"

  @CP-14050-02 @API-ECMS @Prithika
  Scenario: Baseline: Welcome Letter 1 (WL1) Template - Language Spanish
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | WL1               |
      | caseId                          | previouslyCreated |
      | regardingConsumerId             | previouslyCreated |
      | channelType                     | Mail              |
      | city                            | cedar park        |
      | state                           | TX                |
      | zipcode                         | 78641             |
      | streetAddress                   | test lane 1       |
      | language                        | Spanish           |
    And I verify the response status code 201 and status "success"

  @CP-14056 @CP-14056-1 @API-ECMS @Prithika
  Scenario: Configure Welcome Letter 1 Correspondence
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to retrieve a Inbound Correspondence Definition by mmsCode "WL1"
    And I verify the correspondence definition has the following values
      | MMSID                | WL1                                                                                      |
      | CORRESPONDENCENAME   | Welcome Letter 1 Baseline                                                                |
      | STATEID              |                                                                                          |
      | USERMAYREQUEST       | false                                                                                    |
      | STARTDATE            | past Date                                                                                |
      | END DATE             | null                                                                                     |
      | IBCORRESPONDENCETYPE | maersk Returned Mail                                                                    |
      | DESCRIPTION          | A baseline proof of concept for triggering System Event Driven letters - non foster care |
      | END REASON           | empty                                                                                    |
    And I verify the correspondence definition has the following required Key
      | REQUIREDKEYVALUE | Case ID |
    And I verify the correspondence definition has the following Channel Values
      | CHANNELTYPE               | Mail            |
      | SEND IMMEDIATELY CHECKBOX | false           |
      | MANDATORY CHECKBOX        | false           |
      | STARTDATE                 | pastDate        |
      | END DATE                  | null            |
      | END REASON                | empty           |
      | SENDER EMAIL              |                 |
      | LANGUAGE                  | English,Spanish |


  @CP-10594-06  @ui-ecms2 @Prithika
  Scenario: Tenant Manager: Allow Configuring Outbound Correspondence Type to Require Approval-true
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand first project to view the details
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I click on given CorrespondenceDefinition ID "0"
    And I successfully update approval required checkbox to "true"
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to retrieve a Inbound Correspondence Definition by mmsCode "0"
    And I verify the correspondence definition has approval Required Value
      | APPROVAL REQUIRED | true |

  @CP-10594-07  @ui-ecms2 @Prithika
  Scenario: Tenant Manager: Allow Configuring Outbound Correspondence Type to Require Approval-false
    Given I logged into Tenant Manager Project list page
    When I search for a project by "project" value "SelectBLCRMConfig"
    And I expand first project to view the details
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    When I click on given CorrespondenceDefinition ID "0"
    And I successfully update approval required checkbox to "false"
    And I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to retrieve a Inbound Correspondence Definition by mmsCode "0"
    And I verify the correspondence definition has approval Required Value
      | APPROVAL REQUIRED | false |
