@CP-10760
Feature: Pass null instead of empty strings in Contacts events

  @API-CC @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @CP-10760 @CP-10760-01.0 @asad @events @events-cc
  Scenario Outline: Pass null in the event payload for ADDRESS_SAVE_EVENT
    Given I logged into CRM
    When I create a consumer for event check
    And I add new address to the created consumer for event check
    And I will take correlation Id for "ADDRESS_SAVE_EVENT" for "addressType" no empty strings event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "ADDRESS_SAVE_EVENT" is created with no empty string fields in payload for "address" event
    Examples:
      | projectName |
      |[blank]|

  @CP-10760 @CP-10760-01.1 @asad @events @events-cc
  Scenario Outline: Pass null in the event payload for ADDRESS_UPDATE_EVENT
    Given I logged into CRM
    When I create a consumer for event check
    And I update address of the created consumer for event check
    And I will take correlation Id for "ADDRESS_UPDATE_EVENT" for "addressType" no empty strings event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "ADDRESS_UPDATE_EVENT" is created with no empty string fields in payload for "address" event
    Examples:
      | projectName |
      |[blank]|

  @CP-10760 @CP-10760-01.2 @asad @events @events-cc
  Scenario Outline: Pass null in the event payload for PHONE_SAVE_EVENT
    Given I logged into CRM
    When I create a consumer for event check
    And I add new phone to the created consumer for event check
    And I will take correlation Id for "PHONE_SAVE_EVENT" for "phoneType" no empty strings event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "PHONE_SAVE_EVENT" is created with no empty string fields in payload for "phone" event
    Examples:
      | projectName |
      |[blank]|

  @CP-10760 @CP-10760-01.3 @asad @events @events-cc
  Scenario Outline: Pass null in the event payload for PHONE_UPDATE_EVENT
    Given I logged into CRM
    When I create a consumer for event check
    And I update phone of the created consumer for event check
    And I will take correlation Id for "PHONE_UPDATE_EVENT" for "phoneType" no empty strings event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "PHONE_UPDATE_EVENT" is created with no empty string fields in payload for "phone" event
    Examples:
      | projectName |
      |[blank]|

  @CP-10760 @CP-10760-01.4 @asad @events @events-cc
  Scenario Outline: Pass null in the event payload for EMAIL_SAVE_EVENT
    Given I logged into CRM
    When I create a consumer for event check
    And I add new email to the created consumer for event check
    And I will take correlation Id for "EMAIL_SAVE_EVENT" for "emailType" no empty strings event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "EMAIL_SAVE_EVENT" is created with no empty string fields in payload for "email" event
    Examples:
      | projectName |
      |[blank]|

  @CP-10760 @CP-10760-01.5 @asad @events
  Scenario Outline: Pass null in the event payload for EMAIL_UPDATE_EVENT
    Given I logged into CRM
    When I create a consumer for event check
    And I update email of the created consumer for event check
    And I will take correlation Id for "EMAIL_UPDATE_EVENT" for "emailType" no empty strings event check
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I will verify an "EMAIL_UPDATE_EVENT" is created with no empty string fields in payload for "email" event
    Examples:
      | projectName |
      |[blank]|

  @CP-10760 @CP-10760-01.6 @asad @events @events-cc
  Scenario Outline: Pass null in the event payload for Contact Events from Case Loader
    Given I Initiated Case Loader API for no empty string check
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I create new consumer using case loader for no empty string check
    Then I will verify an "ADDRESS_SAVE_EVENT" is created with no empty string fields in payload for "address" event
    Then I will verify an "PHONE_SAVE_EVENT" is created with no empty string fields in payload for "phone" event
    Then I will verify an "EMAIL_SAVE_EVENT" is created with no empty string fields in payload for "email" event
    Examples:
      | projectName |
      |[blank]|


  @CP-14574 @CP-14574-01 @API-CC @API-CRM-Regression @kamil
  Scenario Outline:Verify primary Contact Email updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cl" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::   |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::  |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email:: |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary |
      | caseLoaderRequest[0].case.consumers[0].correlationId                                      | npi::   |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | cl.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | cl.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Update Primary Contact info API
    Then I send api call to Update Primary Contact info
      | caseId                          | c.object.result[0].cases.caseId |
      | caseContacts.email.emailAddress | updatedPrimary@gmail.com        |
    Then Send api call with CaseId to Contact Primary API
    And Verify response primary Contact Email updated with "updatedPrimary@gmail.com" email
    Examples:
      | projectName |
      |[blank]|


  @CP-14574 @CP-14574-02 @API-CC @API-CRM-Regression @kamil @events-cc
  Scenario Outline:Verify EMAIL_SAVE_EVENT for primary contact
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cl" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::   |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::  |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email:: |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::   |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | cl.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | cl.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Update Primary Contact info API
    Then I send api call to Update Primary Contact info
      | caseId                          | c.object.result[0].cases.caseId |
      | caseContacts.email.emailAddress | <email>                         |
    Then Send api call with CaseId to Contact Primary API
    And Verify response primary Contact Email updated with "<email>" email
    And I initiate the Events APi
    Then User send Api call to get "EMAIL_SAVE_EVENT"
    And Wait for 10 seconds
    Then Verify "EMAIL_SAVE_EVENT" response payload
    Examples:
      | projectName | email                    |
      |[blank]| updatedprimary@gmail.com |


  @CP-14574 @CP-14574-03 @API-CC @API-CRM-Regression @kamil
  Scenario Outline:Verify EMAIL_UPDATE_EVENT for primary contact
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cl" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::   |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::   |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::  |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::  |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email:: |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::   |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | cl.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | cl.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I search for Contact Primary with CaseId
    Then I initiate the Update Primary Contact info API
    Then Sending api call for Update Primary Contact info
      | caseId                          | c.caseId |
      | caseContacts.email.emailAddress | <email>  |
    And I initiate the Events APi
    Then User send Api call to get "EMAIL_UPDATE_EVENT"
    And Wait for 10 seconds
    Then Verify "EMAIL_UPDATE_EVENT" response payload
    Examples:
      | projectName | email                |
      |[blank]| newPrimary@gmail.com |


  @CP-10268 @CP-10268-01 @API-CC @API-CRM-Regression @kamil
  Scenario Outline:Verify Primary Active Phone information updated with different phoneType
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.primaryIndicator                    | bool::true  |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneNumber                         | phone::     |
      | caseLoaderRequest[0].case.consumers[0].correlationId                                      | npi::       |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneType                           | <phonetype> |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::       |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::       |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::      |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::      |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary     |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::       |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Update Primary Contact info API
    Then I send api call to Update Primary Phone info
      | caseId                         | c.object.result[0].cases.caseId |
      | caseContacts.phone.phoneNumber | <phoneNumber>                   |
    Then Send api call with CaseId to Contact Primary API
    And Verify response primary Contact Phone updated with "<phoneNumber>" phone number with "<phonetype>" phone type
    Examples:
      | projectName | phonetype | phoneNumber | callName |
      |[blank]| Home      | 5551555555  | cl5      |
      |[blank]| Cell      | 7458641254  | cl6      |
      |[blank]| Work      | 7895247851  | cl7      |
      |[blank]| null::    | 4578548547  | cl8      |


  @CP-10268 @CP-10268-02 @API-CC @API-CRM-Regression @kamil @events-cc
  Scenario Outline:Verify PHONE_SAVE_EVENT for primary contact
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "cl4" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.primaryIndicator                    | bool::true  |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneNumber                         | phone::     |
      | caseLoaderRequest[0].case.consumers[0].correlationId                                      | npi::       |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneType                           | <phonetype> |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::       |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::       |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::      |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::      |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary     |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::       |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | cl4.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | cl4.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Update Primary Contact info API
    And Wait for 5 seconds
    Then I send api call to Update Primary Phone info
      | caseId                         | c.object.result[0].cases.caseId |
      | caseContacts.phone.phoneNumber | 1487424645                       |
    Then Send api call with CaseId to Contact Primary API
    And Verify response primary Contact Phone updated with "1487424645" phone number with "<phonetype>" phone type
    And I initiate the Events APi
    Then User send Api call to get "PHONE_SAVE_EVENT"
    And Wait for 10 seconds
    Then Verify "PHONE_SAVE_EVENT" response payload with "<phonetype>" phone Type
    Examples:
      | projectName | phonetype |
      |[blank]| Cell      |


  @CP-10268 @CP-10268-03 @API-CC @API-CRM-Regression @kamil @events-cc
  Scenario Outline:Verify PHONE_UPDATE_EVENT for primary contact
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.primaryIndicator                    | bool::true  |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneNumber                         | phone::     |
      | caseLoaderRequest[0].case.consumers[0].correlationId                                      | npi::       |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneType                           | <phonetype> |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::       |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::       |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::      |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::      |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary     |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::       |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I search for Contact Primary with CaseId for Phone update
    Then I initiate the Update Primary Contact info API
    Then I send api call to Update Primary Phone info
      | caseId                         | c.caseId      |
      | caseContacts.phone.phoneNumber | <phoneNumber> |
    And I initiate the Events APi
    Then User send Api call to get "PHONE_UPDATE_EVENT"
    And Wait for 10 seconds
    Then Verify "PHONE_UPDATE_EVENT" response payload with "<phonetype>" phone Type
    Examples:
      | projectName | phonetype | callName | phoneNumber |
      |[blank]| Cell      | cl1      | 5454754554  |
      |[blank]| Fax       | cl2      | 5454895554  |


  @CP-10268 @CP-10268-04 @API-CC @API-CRM-Regression @kamil @events-cc
  Scenario Outline:Verify PHONE_SAVE_EVENT for primary contact with FAX Phone Type should be generated as Home
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<callName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.primaryIndicator                    | bool::true  |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneNumber                         | phone::     |
      | caseLoaderRequest[0].case.consumers[0].correlationId                                      | npi::       |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneType                           | <phonetype> |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::       |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::       |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::      |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::      |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email::     |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary     |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::       |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <callName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <callName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I search for Contact Primary with CaseId for Phone update
    Then I verify that "c.caseContacts.phone == null::"
    Then I initiate the Update Primary Contact info API
    Then I send api call to Update Primary Phone info
      | caseId                         | c.caseId      |
      | caseContacts.phone.phoneNumber | <phoneNumber> |
    Then I search again for Contact Primary with CaseId for Phone update
    And I initiate the Events APi
    Then User send Api call to get "PHONE_SAVE_EVENT"
    And Wait for 10 seconds
    Then Verify "PHONE_SAVE_EVENT" response payload with "<phonetype>" phone Type
    Examples:
      | projectName | phonetype | callName | phoneNumber |
      |[blank]| Fax       | cl3      | phone::     |

  @CP-2017 @CP-2017-01 @API-CC @API-CRM-Regression @kamil
  Scenario Outline:Verify Primary indicator for Phone Type Cell
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<requestName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::        |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::       |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::       |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email::      |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary      |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneType                           | Cell         |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.primaryIndicator                    | bool::<bool> |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::        |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <requestName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <requestName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | c.object.result[0].cases.caseId |
      | channels[0] | Mail                            |
      | channels[1] | Text                            |
      | channels[2] | Email                           |
      | channels[3] | Fax                             |
    And I verify response for Phone Type Cell primaryFlag is "<bool>"
    Examples:
      | projectName | requestName | bool  |
      |[blank]| pi          | false |
      |[blank]| pi1         | true  |


    #phone returning null when phoneType Fax
  @CP-2017 @CP-2017-02 @API-CC @API-CRM-Regression @kamil
  Scenario Outline:Verify Primary indicator for Phone Type Fax
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<requestName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::        |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::       |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::       |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email::      |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary      |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneType                           | Fax          |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.primaryIndicator                    | bool::<bool> |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::        |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <requestName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <requestName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | c.object.result[0].cases.caseId |
      | channels[0] | Mail                            |
      | channels[1] | Text                            |
      | channels[2] | Email                           |
      | channels[3] | Fax                             |
    And I verify response for Phone Type Fax primaryFlag is "<bool>"
    Examples:
      | projectName | requestName | bool  |
      |[blank]| pi3         | false |
      |[blank]| pi4         | true  |


  @CP-2017 @CP-2017-03 @API-CC @API-CRM-Regression @kamil
  Scenario Outline:Verify Primary indicator for Email
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<requestName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::        |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::       |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::       |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.primaryIndicator                    | bool::<bool> |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email::      |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary      |
      | caseLoaderRequest[0].case.consumers[0].contacts.phone.phoneType                           | Cell         |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::        |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <requestName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <requestName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | c.object.result[0].cases.caseId |
      | channels[0] | Mail                            |
      | channels[1] | Text                            |
      | channels[2] | Email                           |
      | channels[3] | Fax                             |
    And I verify response for Active Emails primaryFlag is "<bool>"
    Examples:
      | projectName | requestName | bool  |
      |[blank]| pi5         | false |
      |[blank]| pi6         | true  |


  @CP-2017 @CP-2017-04 @API-CC @API-CRM-Regression @kamil
  Scenario Outline:Verify Primary indicator for Address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with name "<requestName>" payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::        |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::        |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name::       |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name::       |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.primaryIndicator                    | bool::<bool> |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailAddress                        | email::      |
      | caseLoaderRequest[0].case.consumers[0].contacts.email.emailType                           | Primary      |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressStreet1                    | address::    |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.addressStreet2                    | address::    |
      | caseLoaderRequest[0].case.consumers[0].contacts.address.primaryIndicator                  | bool::<bool> |
      | caseLoaderRequest[0].case.caseIdentificationNumber                                        | npi::        |
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With
      | consumerFirstName | <requestName>.caseLoaderRequest[0].case.consumers[0].consumerFirstName |
      | consumerLastName  | <requestName>.caseLoaderRequest[0].case.consumers[0].consumerLastName  |
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | c.object.result[0].cases.caseId |
      | channels[0] | Mail                            |
      | channels[1] | Text                            |
      | channels[2] | Email                           |
      | channels[3] | Fax                             |
    And I verify response for Address primaryFlag is "<bool>"
    Examples:
      | projectName | requestName | bool  |
      |[blank]| pi7         | false |
      |[blank]| pi8         | true  |