@EE-HealthCheck
Feature: API: Enrollment  Health Check Feature

  @API-Enrollment @API-CRM @eligibility-enrollment-ms-EE @API-CRM-SmokeTest @API-HealthCheck
    Scenario Outline: Verify Enrollment create endpoint status is success
      Given I created a consumer for enrollment record
    When I will get the Authentication token for "<projectName>" in "CRM"
      When I initiated Enrollment Create API
      And I run the Enrollment Create API for "MEDICAL"
      Then I will verify the Enrollment Medical plan data
    Examples:
      |projectName|
      ||

  @API-Enrollment @API-CRM @eligibility-enrollment-ms-EE @API-CRM-SmokeTest @API-HealthCheck
    Scenario Outline:Get All or Future or Active Enrollments Records by Consumer Id -- GET /mars/eb/enrollments/{consumerId}
      Given I initiated get enrollment by consumer id "40301"
    When I will get the Authentication token for "<projectName>" in "CRM"
      When I run get enrollment api
      Then I verify enrollment records are displayed for the consumer "40301"
    Examples:
      |projectName|
      ||


  @API-Enrollment @API-CRM @eligibility-enrollment-ms-EE @API-CRM-SmokeTest @API-HealthCheck @API-TM
    Scenario Outline:Get Enrollments Benefit Status by Consumer Id -- GET /mars/eb/enrollments/{consumerId}/benefitstatues
      Given I initiated get benefit status by consumer id "38307"
    When I will get the Authentication token for "<projectName>" in "CRM"
      When I run get enrollment api
      Then I verify benefit status records are displayed for the consumer "38307"
    Examples:
      |projectName|
      ||


  @API-Enrollment @API-CRM @eligibility-enrollment-ms-EE @API-CRM-SmokeTest @API-HealthCheck @API-TM
    Scenario Outline:Start BPM Process -- GET mars/eb/bpmn/eligibility/start/{consumerId}
      Given I initiated BPM Process for the consumer "38307"
    When I will get the Authentication token for "<projectName>" in "CRM"
      When I start the BPM process
      Then I verify the BPM process status is "200"
    Examples:
      |projectName|
      ||
