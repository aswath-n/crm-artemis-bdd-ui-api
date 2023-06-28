Feature: API: Consumer Search Feature

  @API-CC @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @API-CP-57 @API-CP-57-01 @API-CC @API-CRM-Regression @kamil
  Scenario:Verify Matching more than one SSN
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | RandomTest     |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | RandomLastNAme |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | 111222333      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | 087878541      |
    Then Verify validationErrorCode message from response when Matching more than one SSN
      | More than one consumer found for the provided SSN ConsumerIds |

  @API-CP-57 @API-CP-57-02 @API-CC @API-CRM-Regression @kamil
  Scenario:Verify More than one external ID match
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | RandomTest     |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | RandomLastNAme |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | 111222         |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | Medicaid       |
    Then Verify Case Loader API validationErrorCode message from response
      | More than one customer found for the provided customer identification number |

  @API-CP-57 @API-CP-57-03 @API-CC @API-CRM-Regression @kamil
  Scenario:Verify Matching on SSN mismatch on external Id
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | RandomTest     |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | RandomLastNAme |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | 444559431      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | npi::          |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | Medicaid       |
    Then Verify Case Loader API validationErrorCode message from response
      | Match on SSN.  Mismatch on External Consumer ID |

  @API-CP-57 @API-CP-57-04 @API-CC @API-CRM-Regression @kamil
  Scenario:Verify Match on FL+LN+DOB mismatch on external Id
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerDateOfBirth                                      | 1990-12-01 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | Juda       |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | Demo       |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | npi::      |
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                              | ssn::      |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | Medicaid   |
    Then Verify Case Loader API validationErrorCode message from response
      | Match on consumerFirstName + consumerLastName +DOB.  Mismatch on External Consumer ID |

  @API-CP-57 @API-CP-57-05 @API-CC @API-CRM-Regression @kamil
  Scenario:Verify More than 1 consumer with matching FL+LN+DOB
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerDateOfBirth                                      | 1945-06-30 |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                        | Tomaria    |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                         | Kith       |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId       | 111222     |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].identificationNumberType | Medicaid   |
    Then Verify Case Loader API validationErrorCode message from response
      | More than one customer found for the provided customer identification number |


  @API-CP-57 @API-CP-57-06 @API-CC @API-CRM-Regression @kamil
  Scenario:Verify All values are unique and no error
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Loader API request
    And User send Api call with payload "apiCaseLoader" for CaseLoader
      | caseLoaderRequest[0].case.consumers[0].consumerSSN                                        | ssn::  |
      | caseLoaderRequest[0].case.consumers[0].consumerIdentificationNumber[0].externalConsumerId | npi::  |
      | caseLoaderRequest[0].case.consumers[0].consumerFirstName                                  | name:: |
      | caseLoaderRequest[0].case.consumers[0].consumerLastName                                   | name:: |
    Then Verify Case Loader API message from response
      | The processing is successfully completed. Please look at the EVENTS table for any error(s) |

  @API-CP-345 @API-CP-345 @asad @API-CC @API-CRM-Regression
  Scenario: Search Consumers API for case and consumer profile search
    Given I initiated Consumer Search API for profile search
    When I will get the Authentication token for "" in "CRM"
    When I can search consumer by "consumerFirstName" with value "joan" for profile search
    And I run the consumer search API for profile search
    Then I can verify case consumer details are available based on search paramters for case consumer search
