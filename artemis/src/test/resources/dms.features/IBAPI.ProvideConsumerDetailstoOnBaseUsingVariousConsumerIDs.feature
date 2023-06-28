@CP-26478
Feature: Provide Consumer Details to OnBase Using Various Consumer IDs for BLCRM,INEB and CoverVA

# BLCRM tenant
  @CP-26478 @CP-26478-1-1 @API-ECMS @Keerthi
  Scenario: validate the successful request and response with Medicaid as type having single match for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I initiate post request with "MEDICAID" as type and "PREVIOUSLY_CREATED" as value
      | ConsumerOnly | PREVIOUSLY_CREATED |
      | ConsumerName | PREVIOUSLY_CREATED |

  @CP-26478 @CP-26478-1-2 @API-ECMS @Keerthi
  Scenario: validate the successful request and response with Chip as type having single match for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer not on a case with "CHIP" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I initiate post request with "CHIP" as type and "PREVIOUSLY_CREATED" as value
      | ConsumerOnly | PREVIOUSLY_CREATED |
      | ConsumerName | PREVIOUSLY_CREATED |

  @CP-26478 @CP-26478-2-1 @API-ECMS @Keerthi
  Scenario: validate the Request was invalid failure response with combination1 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "CHIP" as type, "" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-2-2 @API-ECMS @Keerthi
  Scenario: validate the Request was invalid failure response with combination2 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "MEDICAID" as type, "" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-2-3 @API-ECMS @Keerthi
  Scenario: validate the Request was invalid failure response with combination3 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "" as type, "1234567890" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-2-4 @API-ECMS @Keerthi
  Scenario: validate the Request was invalid failure response with combination4 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "" as type, "" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-3-1 @API-ECMS @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination1 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "CHIP" as type, "null" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-2 @API-ECMS @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination2 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "MEDICAID" as type, "null" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-3 @API-ECMS @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination3 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "null" as type, "null" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-4 @API-ECMS @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination4 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "Medicaidasdsjh" as type, "12345" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-5 @API-ECMS @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination5 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "chipasdsjh" as type, "12345" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-4-1 @API-ECMS @Keerthi
  Scenario: validate the External Consumer ID was found on multiple consumers failure response with combination1 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "MEDICAID" as type, "x649941" as value and "External Consumer ID was found on multiple consumers" as failure message

  @CP-26478 @CP-26478-4-2 @API-ECMS @Keerthi
  Scenario: validate the External Consumer ID was found on multiple consumers failure response with combination2 for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate invalid post request with "CHIP" as type, "x649941" as value and "External Consumer ID was found on multiple consumers" as failure message

    # INEB tenant
  @CP-26478 @CP-26478-1-1.1  @api-ecms-ineb @Keerthi
  Scenario: validate the successful request and response with Medicaid as type having single match for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer not on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I initiate post request with "MEDICAID" as type and "PREVIOUSLY_CREATED" as value
      | ConsumerOnly | PREVIOUSLY_CREATED |
      | ConsumerName | PREVIOUSLY_CREATED |

  @CP-26478 @CP-26478-2-2.1 @api-ecms-ineb @Keerthi
  Scenario: validate the Request was invalid failure response with combination1 for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate invalid post request with "MEDICAID" as type, "" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-2-2.2 @api-ecms-ineb @Keerthi
  Scenario: validate the Request was invalid failure response with combination3 for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate invalid post request with "" as type, "1234567890" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-2-2.3 @api-ecms-ineb @Keerthi
  Scenario: validate the Request was invalid failure response with combination4 for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate invalid post request with "" as type, "" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-3-3.1 @API-ECMS @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination1 for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate invalid post request with "MEDICAID" as type, "null" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-3.2 @api-ecms-ineb @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination2 for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate invalid post request with "null" as type, "12875874h7" as value and "External Consumer ID was not found" as failure message


  @CP-26478 @CP-26478-3-3.3 @api-ecms-ineb @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination3 for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate invalid post request with "null" as type, "null" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-3.4 @api-ecms-ineb @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination4 for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate invalid post request with "medicaidxcxcx" as type, "null" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-4-4.1 @api-ecms-ineb @Keerthi
  Scenario: validate the External Consumer ID was found on multiple consumers failure response with combination1 for INEB
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate invalid post request with "MEDICAID" as type, "x628365" as value and "External Consumer ID was found on multiple consumers" as failure message


    ################################## CP-42174 #################################

  @CP-42174 @CP-42174.1  @api-ecms-ineb @Keerthi
  Scenario: validate the External Consumer ID for consumer on single active case
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I have a consumer on a case with "MEDICAID" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I initiate post request with "MEDICAID" as type and "PREVIOUSLY_CREATED" as value
      | CaseID       | PREVIOUSLY_CREATED |
      | ConsumerID   | PREVIOUSLY_CREATED |
      | ConsumerName | PREVIOUSLY_CREATED |

  @CP-42174 @CP-42174.2  @api-ecms-ineb @Keerthi
  Scenario: validate the External Consumer ID for consumer on multiple active cases
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate post request with "MEDICAID" as type and "1145868656" as value
      | ConsumerID   | 148873                                                      |
      | ConsumerName | mr testdodwithtesterfirstten A testdodwithtesterlastnine jr |

  @CP-42174 @CP-42174.3  @api-ecms-ineb @Keerthi
  Scenario: validate the External Consumer ID for consumer on multiple inactive cases
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate post request with "MEDICAID" as type and "x176911" as value
      | ConsumerID   | 4722                   |
      | ConsumerName | Mrs Test M lniRXyq Esq |

  @CP-42174 @CP-42174.4  @api-ecms-ineb @Keerthi
  Scenario: validate the External Consumer ID for consumer on active and inactive cases
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I initiate post request with "MEDICAID" as type and "x760599" as value
      | ConsumerID   | 650                       |
      | ConsumerName | Mrs FnWzkDQ R LncdoPZ Esq |

# CoverVA tenant
  @CP-26478 @CP-26478-1-1.1  @api-ecms-coverva @Keerthi
  Scenario: validate the successful request and response with MMIS as type having single match for COVERVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a consumer not on a case with "MMIS" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I initiate post request with "MMIS" as type and "PREVIOUSLY_CREATED" as value
      | ConsumerOnly | PREVIOUSLY_CREATED |
      | ConsumerName | PREVIOUSLY_CREATED |

  @CP-26478 @CP-26478-1-1.2  @api-ecms-coverva @Keerthi
  Scenario: validate the successful request and response with offender as type having single match for COVERVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a consumer not on a case with "Offender" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I initiate post request with "Offender" as type and "PREVIOUSLY_CREATED" as value
      | ConsumerOnly | PREVIOUSLY_CREATED |
      | ConsumerName | PREVIOUSLY_CREATED |

  @CP-26478 @CP-26478-1-1.3  @api-ecms-coverva @Keerthi
  Scenario: validate the successful request and response with vacms as type having single match for COVERVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a consumer not on a case with "VaCMS" as externalConsumerIdType and "RANDOM" as externalConsumerIdValue
    Then I initiate post request with "VaCMS" as type and "PREVIOUSLY_CREATED" as value
      | ConsumerOnly | PREVIOUSLY_CREATED |
      | ConsumerName | PREVIOUSLY_CREATED |

  @CP-26478 @CP-26478-2-2.1  @api-ecms-coverva @Keerthi
  Scenario: validate the Request was invalid failure response with combination1 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "MMIS" as type, "" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-2-2.2  @api-ecms-coverva @Keerthi
  Scenario: validate the Request was invalid failure response with combination2 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "VaCMS" as type, "" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-2-2.3  @api-ecms-coverva @Keerthi
  Scenario: validate the Request was invalid failure response with combination3 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "Offender" as type, "" as value and "Request was invalid" as failure message


  @CP-26478 @CP-26478-2-2.4  @api-ecms-coverva @Keerthi
  Scenario: validate the Request was invalid failure response with combination4 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "" as type, "1234567890" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-2-2.5  @api-ecms-coverva @Keerthi
  Scenario: validate the Request was invalid failure response with combination5 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "" as type, "" as value and "Request was invalid" as failure message

  @CP-26478 @CP-26478-3-3.1  @api-ecms-coverva @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination1 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "MMIS" as type, "null" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-3.2  @api-ecms-coverva @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination2 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "VaCMS" as type, "null" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-3.3  @api-ecms-coverva @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination3 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "Offender" as type, "null" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-3.4  @api-ecms-coverva @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination4 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "null" as type, "null" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-3.5  @api-ecms-coverva @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination5 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "Offenderxvcv" as type, "12554" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-3.6  @api-ecms-coverva @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination6 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "VaCMSxcvcxv" as type, "12554" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-3-3.7  @api-ecms-coverva @Keerthi
  Scenario: validate the External Consumer ID was not found failure response with combination7 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "MMIScxvcv" as type, "12554" as value and "External Consumer ID was not found" as failure message

  @CP-26478 @CP-26478-4-4.1  @api-ecms-coverva @Keerthi
  Scenario: validate the External Consumer ID was found on multiple consumers failure response with combination1 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "MMIS" as type, "67890" as value and "External Consumer ID was found on multiple consumers" as failure message

  @CP-26478 @CP-26478-4-4.2  @api-ecms-coverva @Keerthi
  Scenario: validate the External Consumer ID was found on multiple consumers failure response with combination2 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "VACMS" as type, "67890" as value and "External Consumer ID was found on multiple consumers" as failure message

  @CP-26478 @CP-26478-4-4.3  @api-ecms-coverva @Keerthi
  Scenario: validate the External Consumer ID was found on multiple consumers failure response with combination3 for CoverVA
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Then I initiate invalid post request with "Offender" as type, "67890" as value and "External Consumer ID was found on multiple consumers" as failure message

