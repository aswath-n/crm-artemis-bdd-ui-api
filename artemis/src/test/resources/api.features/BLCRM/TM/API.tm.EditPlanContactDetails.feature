@API-TM-Edit-Plan
Feature: API TM Edit plan contact details

  @planProviderAPI @API-TM @API-PP-Regression @aswath @plan-manag-ms-PP @API-PP-SmokeTest
  Scenario: Getting JWT token
    Given I get the jwt token

  @API-CP-1970 @API-CP-1970-01 @API-PP-Regression @API-PP-SmokeTest @kamil
  Scenario Outline: Search with Plan Name and verify Plan details
#    Given I get the jwt token
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I initiated Plan Management for service region API
    Given I get the jwt token
    Given I upload the success service region and plan file
    Given I initiated Plan Management for Plan Search
    And User send Api call with payload "planSearch" for Plan search
      | planSearch.planName | AMERIGROUP COMMUNITY CARE |
    And I verify the response status code 200 and status "success"
    Then Verify Plan details response for "AMERIGROUP COMMUNITY CARE"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-1970 @API-CP-1970-02  @API-PP-Regression @API-PP-SmokeTest @kamil
  Scenario Outline: Update Plan: Plan Mailing Containor
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I upload the success service region and plan file
    Given I initiated Plan Management for Plan Update
    And User send Api call with payload "planUpdate" for Plan Update
      | plan.planContact.addressLine1 | 600 N Surf   |
      | plan.planContact.addressLine2 | 7220 n Damen |
      | plan.planContact.zipCode      | 30001        |
    And I verify the response status code 200 and status "success"
    Then Verify that "c.status == success"
    And Verify that "c.message == Fields successfully updated"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-1970 @API-CP-1970-03  @API-PP-Regression @kamil
  Scenario Outline: Update Plan: First and LastName in Contact Information Container
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I upload the success service region and plan file
    Given I initiated Plan Management for Plan Update
    And User send Api call with payload "planUpdate" for Plan Update
      | plan.planContact.planContactFirstName | Dave     |
      | plan.planContact.planContactLastName  | Atkinson |
    And I verify the response status code 200 and status "success"
    Then Verify that "c.status == success"
    And Verify that "c.message == Fields successfully updated"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-1970 @API-CP-1970-04  @API-PP-Regression @API-PP-SmokeTest @kamil
  Scenario Outline: Update Plan: Whole Contact Information Container
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I upload the success service region and plan file
    Given I initiated Plan Management for Plan Update
    And User send Api call with payload "planUpdate" for Plan Update
      | plan.planContact.planContactFirstName  | Dave                                                |
      | plan.planContact.planContactLastName   | Atkinson                                            |
      | plan.planContact.memberServiceNum1     | 8008889966                                          |
      | plan.planContact.memberServiceNum2     | 6210001166                                          |
      | plan.planContact.memberServiceNum3     | 6111110011                                          |
      | plan.planContact.memberServiceNum4     | 7739558874                                          |
      | plan.planContact.memberServiceNum5     | 7733211236                                          |
      | plan.planContact.plancontactphonenum   | 3320001122                                          |
      | plan.planContact.providerServicesNum1  | 7736665555                                          |
      | plan.planContact.providerServicesNum2  | 3125558899                                          |
      | plan.planContact.providerServicesNum3  | 8886679596                                          |
      | plan.fileExchangeLocation              | /Health_Services/GA_Families/Provider/GF_Amerigroup |
      | plan.planContact.memberServicesWebsite | www.myamerigroup.com/ga                             |
      | plan.planContact.planContactEmail      | DaveAtkinson@amerigroup.com                         |
    And I verify the response status code 200 and status "success"
    Then Verify that "c.status == success"
    And Verify that "c.message == Fields successfully updated"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-1970 @API-CP-1970-05 @API-PP-Regression @kamil
  Scenario Outline: Search with Plan Name and Update fields  in the Plan Mailing Address Container
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I upload the success service region and plan file
    Given I initiated Plan Management for Plan Search
    And User send Api call with payload "planSearch" for Plan search
      | planSearch.planName | AMERIGROUP COMMUNITY CARE |
    And I verify the response status code 200 and status "success"
    Then Verify Plan details response for "AMERIGROUP COMMUNITY CARE"
    Given I initiated Plan Management for Plan Update
    And User send Api call with payload "planUpdate" for Plan Update
      | plan.planContact.planContactFirstName  | Dave                                                |
      | plan.planContact.planContactLastName   | Atkinson                                            |
      | plan.planContact.memberServiceNum1     | 8008889966                                          |
      | plan.planContact.memberServiceNum2     | 6210001166                                          |
      | plan.planContact.memberServiceNum3     | 6111110011                                          |
      | plan.planContact.memberServiceNum4     | 7739558874                                          |
      | plan.planContact.memberServiceNum5     | 7733211236                                          |
      | plan.planContact.plancontactphonenum   | 3320001122                                          |
      | plan.planContact.providerServicesNum1  | 7736665555                                          |
      | plan.planContact.providerServicesNum2  | 3125558899                                          |
      | plan.planContact.providerServicesNum3  | 8886679596                                          |
      | plan.fileExchangeLocation              | /Health_Services/GA_Families/Provider/GF_Amerigroup |
      | plan.planContact.memberServicesWebsite | www.myamerigroup.com/ga                             |
      | plan.planContact.planContactEmail      | DaveAtkinson@amerigroup.com                         |
    And I verify the response status code 200 and status "success"
    Then Verify that "c.status == success"
    And Verify that "c.message == Fields successfully updated"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-1970 @API-CP-1970-06  @API-PP-Regression @kamil
  Scenario Outline: Update Plan: Contact Information Container only MEMBER SERVICES PHONES
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I upload the success service region and plan file
    Given I initiated Plan Management for Plan Update
    And User send Api call with payload "planUpdate" for Plan Update
      | plan.planContact.memberServiceNum1 | 8008889966 |
      | plan.planContact.memberServiceNum2 | 6210001166 |
      | plan.planContact.memberServiceNum3 | 6111110011 |
      | plan.planContact.memberServiceNum4 | 7739558874 |
      | plan.planContact.memberServiceNum5 | 7733211236 |
    And I verify the response status code 200 and status "success"
    Then Verify that "c.status == success"
    And Verify that "c.message == Fields successfully updated"
    Examples:
      | projectName |
      |[blank]|


  @API-CP-1970 @API-CP-1970-07  @API-PP-Regression @kamil
  Scenario Outline: Update Plan: Address Line1 in Plan Mailing Containor
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    Given I upload the success service region and plan file
    Given I initiated Plan Management for Plan Update
    And User send Api call with payload "planUpdate" for Plan Update
      | plan.planContact.addressLine1 | 600 N Surf |
    And I verify the response status code 200 and status "success"
    Then Verify that "c.status == success"
    And Verify that "c.message == Fields successfully updated"
    Examples:
      | projectName |
      |[blank]|


  @Event-CP-1970 @Event-CP-1970-08 @planProvider-events @events @kamil @events_smoke_level_two @pp-events
  Scenario Outline: Publish an event for DPBI to consume
    Given I logged into Tenant Manager and set the project context "project" value "SelectFromConfig"
    And I verify the Go live date
    And I navigate to Project Configuration
    And I select Plans Management
    Then I am on the Service Region Configuration screen
    When I upload the service region file before to plan file
    And I click on Plans Configuration tab
    When I choose a file to upload in Plan Config tab "<FileType>"
    And I attach file to Plan Config tab
    And I Click on Plan Config tab Upload button
    Then I can make a valid search of plans
    And I can sort results by ascending or descending order
    When I click on a Plan Name search result
    Then I am brought to the Plan Details Screen
    When I click on the Enrollment Information container edit button
    And I input and save changes to the given Enrollment Information fields
    Then I see the Enrollment Information changes were saved
    When I will get the Authentication token for "<projectName>" in "Tenant Manager"
    And I will get "<correlationId>" from traceId and pass it as end point
    And I initiated Event GET API
    Then I will send API call to get Event
    And I verify response for Event details
    And I initiated Subscribers POST API
    And I send Api call "empty" for subsciber name
      | subscriberName | DPBI |
    Then I will verify the response has event "<eventName>" with Subscriber Mapping Id
    And Send API call to get Subscribers Record
    Then I verify the response has event "<eventName>" with SubscriberId
    Examples:
      | FileType       | eventName         | correlationId  | projectName |
      | BLCRM Plan Config | PLAN_UPDATE_EVENT | pcpRequiredInd |[blank]|

