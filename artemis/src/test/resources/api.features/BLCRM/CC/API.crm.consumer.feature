Feature: API: Consumer Controller

#  #    # -- The following test case needs to redesign for Case creation without static case Id. Creation of Case and consumer currently not implemented in CRM.  --
# # -- API validation logic is not implemented. --
  @case-consumer-api-CC @API-CRM-300 @API-CRM-Regression @API-CC @Sujoy @api-smoke-devops
  Scenario Outline: Create Consumer with mandatory fields using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Consumer API
    When I can provide consumer information with "<consumerFirstName>" "<consumerLastName>" "<phoneNumber>" and "<addressZip>"
    And I can run create consumer API
    Then I can verify consumer consumerLastName with value "<consumerLastName>" on API response
    Examples:
      | consumerFirstName | consumerLastName | phoneNumber | addressZip | projectName |
      | {random}          | {random}         | {random}    | {random}   |[blank]|

  @case-consumer-api-CC @API-CRM-300 @API-CRM @API-CRM-Regression @API-CC @Sujoy
  Scenario Outline: Verify there is only one unique customer id using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Consumer Search API
    When I can search consumer by "<Node>" with value "<value>"
    And I run the consumer search API
    Then I can verify consumer consumerId on API response
    Examples:
      | Node              | value | projectName |
      | consumerFirstName | Test  |[blank]|

  @case-consumer-api-CC @API-CRM-482 @API-CRM @API-CRM-Regression @API-CC @Sujoy
  Scenario Outline: Search Consumer using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Consumer Search API
    When I can search consumer by "<Node>" with value "<value>"
    And I run the consumer search API
    Then I can verify consumer "<Node>" with value "<value>" on API response
    Examples:
      | Node              | value     | projectName |
      | consumerSSN       | 435366536 |[blank]|
      | consumerFirstName | Graham    |[blank]|
      | consumerLastName  | Gooch     |[blank]|


#    # -- The following test case needs to redesign for Case creation without static case Id. Creation of Case and consumer currently not implemented in CRM.  --
  @case-consumer-api-CC @API-CRM-482-01  @API-CC @API-CRM-Regression
  Scenario Outline: Validate duplicate Consumer is not created using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Create Consumer API
    When I can provide same consumer information that is created earlier
    And I can run try to create duplicate consumer via API
    Then I can verify on consumer search API response
    Examples:
      | projectName |
      |[blank]|

  #    # -- The following test case needs to redesign for Case creation without static case Id. Creation of Case and consumer currently not implemented in CRM.  --
  @case-consumer-api-CC @API-CRM-482 @API-CC  @API-CRM-Regression @Sujoy
  Scenario Outline: Create Consumer for all US states using API
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Create Consumer API
    When I can provide consumer address State with "<State>"
    And I can run create consumer API
    Then I can verify on consumer search API response
    Examples:
      | State | projectName |
      | AK    |[blank]|
      | AL    |[blank]|
      | AR    |[blank]|
      | AS    |[blank]|
      | AZ    |[blank]|
      | CO    |[blank]|
      | CT    |[blank]|
      | DC    |[blank]|
      | DE    |[blank]|
      | FL    |[blank]|
      | FM    |[blank]|
      | GA    |[blank]|
      | GU    |[blank]|
      | HI    |[blank]|
      | IA    |[blank]|
      | ID    |[blank]|
      | IL    |[blank]|
      | IN    |[blank]|
      | KS    |[blank]|
      | KY    |[blank]|
      | LA    |[blank]|
      | MA    |[blank]|
      | MD    |[blank]|
      | ME    |[blank]|
      | MH    |[blank]|
      | MI    |[blank]|
      | MN    |[blank]|
      | MO    |[blank]|
      | MP    |[blank]|
      | MS    |[blank]|
      | MT    |[blank]|
      | NC    |[blank]|
      | ND    |[blank]|
      | NE    |[blank]|
      | NH    |[blank]|
      | NJ    |[blank]|
      | NM    |[blank]|
      | NV    |[blank]|
      | NY    |[blank]|
      | OH    |[blank]|
      | OK    |[blank]|
      | OR    |[blank]|
      | PA    |[blank]|
      | PR    |[blank]|
      | PW    |[blank]|
      | RI    |[blank]|
      | SC    |[blank]|
      | SD    |[blank]|
      | TN    |[blank]|
      | TX    |[blank]|
      | UT    |[blank]|
      | VA    |[blank]|
      | VI    |[blank]|
      | VT    |[blank]|
      | WA    |[blank]|
      | WI    |[blank]|
      | WV    |[blank]|
      | WY    |[blank]|


#  @case-consumer-api-CC  @API-CRM-1897 @API-CC @API-CRM-Regression @Shruti #retired due to CP-1435 implementation
#  Scenario Outline: Verify exact match is displayed for search criteria
#    Given I initiated Create Consumer API
#    When I will get the Authentication token for "<projectName>" in "CRM"
#    When I create consumers with following information
#      | consumerFirstName | consumerLastName | consumerSSN | consumerDateOfBirth |
#      | Martha            | Rich             | 334561234   | 10-18-1988          |
#      | Martha            | Geremy           | 212456789   | 11-19-1968          |
#      | Mary              | William          | 114561222   | 10-18-1994          |
#    Given I initiated Consumer Search API
#    When I can search consumer by consumer first name as "" consumer last name as "" consumer ssn as "" and consumer date of birth as "1988-10-18"
#    And I run the consumer search API
#    Then I verify all consumers retrieved with "consumerDateOfBirth" as "1988-10-18" on API response
#    When I can search consumer by consumer first name as "Mar" consumer last name as "Rich" consumer ssn as "" and consumer date of birth as ""
#    And I run the consumer search API
#    Then I verify all consumers retrieved with "consumerLastName" as "Rich" on API response
#    Then I verify all consumers retrieved with "consumerFirstName" as "Mar" on API response
#    Examples:
#      | projectName |
#      |[blank]|


  @API-CP-274 @API-CP-274-01 @API-CC @API-CRM-Regression  @aikanysh
  Scenario Outline: Verify consumer is successfully created with "Paperlerss" option
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Create Consumer API
    When I create consumers with following information and correspondence preference info
      | consumerFirstName | consumerLastName | consumerSSN | consumerDateOfBirth | communicationPreferences |
      | random            | random           | random      | 1990-10-10          | CORRESPONDENCE_PAPERLESS |
    Given I initiated Consumer Search API
    When I can search consumer by "<Node>" with above created values "<value>"
    And I run the consumer search API
    Then I can verify consumer "<Node>" with above created value "<value>" on API response
    And I can verify found consumer has communicationPreferences marked as "CORRESPONDENCE_PAPERLESS"
    Examples:
      | Node              | value | projectName |
      | consumerSSN       |[blank]|             |
      | consumerFirstName |[blank]|             |
      | consumerLastName  |[blank]|             |

  @API-CP-274 @API-CP-274-02 @events @API-CRM-Regression @aikanysh @events-cc
  Scenario: Verify Consumer_Save_Event is successfully published with "Paperlerss" option
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Create Consumer API
    When I create consumers with following information and correspondence preference info
      | consumerFirstName | consumerLastName | consumerSSN | consumerDateOfBirth | communicationPreferences |
      | random            | random           | random      | 1990-10-10          | CORRESPONDENCE_PAPERLESS |
    Then I will verify an "CONSUMER_SAVE_EVENT" for DPBI is created for above scenario


  @api-smoke-devops
  Scenario: Verify consumer is successfully searched
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Create Consumer API
    When I create consumers with following information and correspondence preference info
      | consumerFirstName | consumerLastName | consumerSSN | consumerDateOfBirth | communicationPreferences |
      | random            | random           | random      | 1990-10-10          | CORRESPONDENCE_PAPERLESS |
    Given I initiated Consumer Search API
    When I can search consumer by "consumerSSN" with above created values ""
    And I run the consumer search API
    Then I can verify consumer "consumerSSN" with above created value "" on API response


  @API-CP-1435 @API-CP-1435-1.01 @consumer-api-CC @API-CC @API-CRM-Regression @JP
  Scenario: Verify consumer already exist error when creation of Consumer profile
    #Create a consumer and verify the error with same data set.
    Given I initiated Create Consumer API
    When I will get the Authentication token for "" in "CRM"
    When I can provide consumer information with "{random}" "{random}" "{random}" and "{random}"
    And I can run create consumer API
    Then I can run try to create duplicate consumer via API
    #Create another consumer and try to  verify error message if only ssn and external id is changed.
    Given I initiated Create Consumer API
    When I will get the Authentication token for "" in "CRM"
    When I can provide consumer information with "{random}" "{random}" "{random}" and "{random}"
    And I can run create consumer API
    Then I verify error message during create new consumer with already existing consumer data except below fields
      | consumerSSN | consumerExternalID |
      | {random}    | {random}           |

  @API-CP-1435 @API-CP-1435-1.02 @consumer-api-CC @API-CC @API-CRM-Regression @JP
  Scenario: Verify consumer already exist error when creation of Consumer profile with name and external ID
    #Create another consumer and try to  verify error message if only first name, last name and external id is changed.
    Given I initiated Create Consumer API
    When I will get the Authentication token for "" in "CRM"
    When I can provide consumer information with "{random}" "{random}" "{random}" and "{random}"
    And I can run create consumer API
    Then I verify error message during create new consumer with already existing consumer data except below fields
      | consumerFirstName | consumerLastName | consumerExternalID |
      | {random}          | {random}         | {random}           |

  @API-CP-1435 @API-CP-1435-1.03 @consumer-api-CC @API-CC @API-CRM-Regression @JP
  Scenario: Verify consumer already exist error when creation of Consumer profile with name, ssn and DOB
     #Create another consumer and try to  verify error message if only first name, last name, DOB and external id is changed.
    Given I initiated Create Consumer API
    When I will get the Authentication token for "" in "CRM"
    When I can provide consumer information with "{random}" "{random}" "{random}" and "{random}"
    And I can run create consumer API
    Then I verify error message during create new consumer with already existing consumer data except below fields
      | consumerFirstName | consumerLastName | consumerSSN | consumerDOB |
      | {random}          | {random}         | {random}    | {random}    |

#  Muting this scenario outline due to revert of this functionality that was depending on Validation library.
#  Validation Library will need additional changes and after that Functionality will be activated. Ref CP-37991
#  @API-CP-26826 @API-CP-26826-01 @consumer-api-CC @API-CC @API-CRM-Regression @muhabbat
#  Scenario Outline: Verify consumer is not created with unexpected SSN
#    When I will get the Authentication token for "BLCRM" in "CRM"
#    Given I initiated Create Consumer API
#    When I can provide random consumer information with SSN "<consumerSSN>" randomly
#    Then I verify new consumer is not created with unexpected SSN field parameter "<consumerSSN>"
#    Examples:
#      | consumerSSN |
#      | 34535       |
#      | asdfs33     |
#      | 1           |
#      | 12345678    |
#      | 1-=09       |
#      | random      |
