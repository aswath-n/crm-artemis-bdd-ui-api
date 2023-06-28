Feature: API: Create New Case from Case Loader Feature


  @asad @API-CP-61-01 @API-Case-Check @API-CRM @API-CRM-Regression @API-CP-61
  Scenario Outline: Required Case Information
    Given I initiated Case Loader API for Create New Case
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I run Case Loader API for Create New Case and Customer
    Then I will create a new case for case loader case creation
    Examples:
      |projectName|
      |[blank]|

  @asad @API-CP-61-02 @API-Case-Check @API-CRM @API-CRM-Regression @API-CP-61
  Scenario Outline: Systematically Link New Consumer to New Case
    Given I initiated Case Loader API for Create New Case
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I run Case Loader API for Create New Case and Customer
    Then The consumer will automatically be linked to the provided Case
    Examples:
      |projectName|
      |[blank]|

  @asad @API-CP-61-03 @API-Case-Check @API-CRM @API-CRM-Regression @API-CP-61
  Scenario Outline: Systematically Link Existing Consumer to New Case
    Given I initiated Case Loader API for Create New Case and Existing Consumer
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I run Case Loader API for Create New Case and Existing Consumer
    Then The consumer will automatically be linked to the provided Case
    Examples:
      |projectName|
      |[blank]|

  @asad @API-CP-61-04 @API-Case-Check @API-CRM @API-CRM-Regression @API-CP-61
  Scenario Outline: Set Primary Individual
    Given I initiated Case Loader API for Create New Case
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I run Case Loader API for Create New Case and Customer
    Then The Consumer will have a Consumer Role of Primary Individual
    Examples:
      |projectName|
      |[blank]|

  @asad @API-CP-61-05 @API-Case-Check @API-CRM @API-CRM-Regression @API-CP-61
  Scenario Outline: Case is Searchable
    Given I initiated Case Loader API for Create New Case
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I run Case Loader API for Create New Case and Customer
    Then The user should find the newly created case with associated consumer profiles and case details
    Examples:
      |projectName|
      |[blank]|

  @asad @API-CP-61-06 @API-Case-Check @API-CRM @API-CRM-Regression @API-CP-61  @CP-11786 @CP-11786-10 @events-cc
  Scenario Outline: Publish an event for DPBI to consume
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Case
    When I run Case Loader API for Create New Case and Customer
    Then I will publish an "CONSUMER_SAVE_EVENT" event for DPBI to consume for reporting
    Examples:
      |projectName|
      |[blank]|

  @asad @API-CP-61-07 @API-Case-Check @API-CRM @API-CRM-Regression @API-CP-61
  Scenario Outline: Record Created Date and Created By fields
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Case
    When I run Case Loader API for Create New Case and Customer
    Then I will record the date the records were created
    Examples:
      |projectName|
      |[blank]|

