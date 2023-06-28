Feature: API: IVR Health Check Feature

  #Failing currently
  @crmCoreAPI @ivr-api-CRMC @API-IVR-CTI_HealthCheck @Shruti
  Scenario Outline: IVR authentication -- POST
    Given I initiated ivr authentication API
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I can provide ivr authentication information "<SSN>" and "<DOB>"
    Then I verify the response status code for ivr api
    Examples:
      | SSN       | DOB      |projectName|
      | 435366536 | 11271998 |[blank]|


  @crmCoreAPI @ivr-api-CRMC @API-IVR-CTI_HealthCheck @Shruti @R01312019
  Scenario Outline: IVR authentication -- POST
    Given I initiated ivr authentication API
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I can provide ivr authentication information "<SSN>" and "<DOB>"
    Then I verify the response body has "<status>", "<SSN>" and "<DOB>"
    Examples:
      | SSN       | DOB        | status  |projectName|
      | 435366536 | 11/27/1998 | Success |[blank]|


