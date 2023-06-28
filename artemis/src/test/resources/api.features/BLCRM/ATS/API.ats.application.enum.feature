@ats-enum
Feature: API: Verify Enum values for application

#Lookup APIs are updated as per CP-37461, CP-35898
  @ATS-API-Smoke @ATS-API-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @API-CP-12606 @API-ATS @API-CRM-Regression @munavvar
  Scenario: Order by default values check and validation
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_PROGRAMS" and bservice "applicationdata"
    Then The call returns list of values for application intake "ENUM_PROGRAMS" as
      | orderbyDefault | value                | description          | reportLabel          | scope              |
      | 1              | Medicaid             | Medicaid             | Medicaid             | Medical Assistance |
      | 2              | CHIP                 | CHIP                 | CHIP                 | Medical Assistance |
      | 3              | Pregnancy Assistance | Pregnancy Assistance | Pregnancy Assistance | Medical Assistance |
      | 4              | HCBS                 | HCBS                 | HCBS                 | Long Term Care     |


  @API-CP-13253 @API-CP-25058 @API-CP-17541 @API-ATS @API-CRM-Regression @munavvar
  Scenario: Define and Configure Application Types for Intake
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_APPLICATION_TYPES" and bservice "applicationdata"
    Then The call returns list of values for application intake "ENUM_APPLICATION_TYPES" as
      | orderbyDefault | value              | description        | reportLabel        | scope                                                                                                                       |
      | 1              | Medical Assistance | Medical Assistance | Medical Assistance | {"daysToCompleteApplication":45,"daysToTerminateApplication":10,"daysToSubtractFromDeadlineDateForMissingInfoCompletion":7} |
      | 2              | Long Term Care     | Long Term Care     | Long Term Care     | {"daysToCompleteApplication":90,"daysToTerminateApplication":10,"daysToSubtractFromDeadlineDateForMissingInfoCompletion":4} |


  @API-CP-13326 @API-ATS @API-CRM-Regression @munavvar
  Scenario: Define and Configure Application Cycle for Intake
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_APPLICATION_CYCLES" and bservice "applicationdata"
    Then The call returns list of values for application intake "ENUM_APPLICATION_CYCLES" as
      | orderbyDefault | value   | description | reportLabel | scope                              |
      | 1              | New     | New         | New         | Medical Assistance, Long Term Care |
      | 2              | Renewal | Renewal     | Renewal     | Medical Assistance                 |

  @API-CP-16696 @API-ATS @API-CRM-Regression @ozgen
  Scenario: Define Application Status Change Reasons
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_APPLICATION_STATUS_CHANGE_REASON" and bservice "applicationdata"
    Then The call returns list of values for application intake "ENUM_APPLICATION_STATUS_CHANGE_REASON" as
      | orderbyDefault | value                      | description                | reportLabel                | scope |
      | 1              | Already Receiving Services | Already Receiving Services | Already Receiving Services | null  |
      | 2              | Not Interested in Services | Not Interested in Services | Not Interested in Services | null  |


  @API-CP-19981 @API-ATS @API-CRM-Regression @ozgen
  Scenario: Define Application Priorities
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_APPLICATION_PRIORITY" and bservice "applicationdata"
    Then The call returns list of values for application intake "ENUM_APPLICATION_PRIORITY" as
      | orderbyDefault | value     | description | reportLabel | scope |
      | 1              | Normal    | Normal      | Normal      | null  |
      | 2              | High      | High        | High        | null  |
      | 3              | Expedited | Expedited   | Expedited   | null  |

  @API-CP-22975 @API-ATS @API-CRM-Regression @ozgen
  Scenario: Define and Configure Eligibility Outcome
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_ELIGIBILITY" and bservice "applicationdata"
    Then The call returns list of values for application intake "ENUM_ELIGIBILITY" as
      | orderbyDefault | value        | description  | reportLabel  | scope                                                                                       |
      | 1              | Pending      | Pending      | Pending      | {"appliesToPrograms":["Medicaid", "CHIP", "Pregnancy Assistance", "HCBS"]}                  |
      | 2              | Eligible     | Eligible     | Eligible     | {"appliesToPrograms":["Medicaid", "CHIP", "Pregnancy Assistance", "HCBS"],"isFinal":"true"} |
      | 3              | Not Eligible | Not Eligible | Not Eligible | {"appliesToPrograms":["Medicaid", "CHIP", "Pregnancy Assistance", "HCBS"],"isFinal":"true"} |
      | 4              | Wait List    | Wait List    | Wait List    | {"appliesToPrograms":["HCBS"]}                                                              |

  @API-CP-22976 @API-ATS @API-CRM-Regression @ozgen
  Scenario: Define and Configure Eligibility Denial Reasons
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_ELIGIBILITY_DENIAL_REASONS" and bservice "applicationdata"
    Then The call returns list of values for application intake "ENUM_ELIGIBILITY_DENIAL_REASONS" as
      | orderbyDefault | value                 | description           | reportLabel           | scope |
      | 1              | Already Eligible      | Already Eligible      | Already Eligible      | null  |
      | 2              | Immigration Status    | Immigration Status    | Immigration Status    | null  |
      | 3              | Medicaid Eligible     | Medicaid Eligible     | Medicaid Eligible     | null  |
      | 4              | Non-Compliance        | Non-Compliance        | Non-Compliance        | null  |
      | 5              | Over Age              | Over Age              | Over Age              | null  |
      | 6              | Over Income           | Over Income           | Over Income           | null  |
      | 7              | Other Insurance       | Other Insurance       | Other Insurance       | null  |
      | 8              | Out of State Resident | Out of State Resident | Out of State Resident | null  |

  @API-CP-27016 @API-ATS @API-CRM-Regression @vinuta
  Scenario: Missing Information Status Enum
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_MISSING_INFORMATION_STATUS" and bservice "missinginformation"
    Then The call returns list of values for application intake "ENUM_MISSING_INFORMATION_STATUS" as
      | orderbyDefault | value                  | description            | reportLabel            | scope |
      | 1              | PENDING                | PENDING                | PENDING                | null  |
      | 2              | REVIEW                 | REVIEW                 | REVIEW                 | null  |
      | 3              | PENDING - INSUFFICIENT | PENDING - INSUFFICIENT | PENDING - INSUFFICIENT | null  |
      | 4              | SATISFIED              | SATISFIED              | SATISFIED              | null  |
      | 5              | DISREGARDED            | DISREGARDED            | DISREGARDED            | null  |
      | 6              | VOID                   | VOID                   | VOID                   | null  |

  @API-CP-19901 @API-ATS @API-CRM-Regression @burak
  Scenario: Missing Information Entity Enum
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_RECORD_TYPE" and bservice "missinginformation"
    Then The call returns list of values for application intake "ENUM_RECORD_TYPE" as
      | orderbyDefault | value                        | description                  | reportLabel                  | scope       |
      | 1              | Application                  | Application                  | Application                  | Application |
      | 2              | Application Consumer         | Application Consumer         | Application Consumer         | Application |
      | 3              | Application Consumer Income  | Application Consumer Income  | Application Consumer Income  | Application |
      | 4              | Application Consumer Program | Application Consumer Program | Application Consumer Program | Application |
      | 5              | Appeal                       | Appeal                       | Appeal                       | Appeal      |

  @API-CP-19903 @API-ATS @API-CRM-Regression @burak
  Scenario: Missing Information Attribute Class Enum
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_RECORD_CLASS" and bservice "missinginformation"
    Then The call returns list of values for application intake "ENUM_RECORD_CLASS" as
      | orderbyDefault | value        | description  | reportLabel  | scope |
      | 1              | Data         | Data         | Data         | null  |
      | 2              | Verification | Verification | Verification | null  |

  @API-CP-19904 @API-CP-29204 @API-ATS @API-CRM-Regression @burak
  Scenario: Missing Information Attribute Enum
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_MISSING_INFO_TYPE" and bservice "missinginformation"
    Then The call returns list of values for application intake "ENUM_MISSING_INFO_TYPE" as
      | orderbyDefault | value                    | description              | reportLabel              | scope                                                                                                                                     |
      | 1              | Application Signed       | Application Signed       | Application Signed       | {"Host": "Application","Dependent":"Application Consumer","Class":"Data","Program":"Medicaid, CHIP, HCBS, Pregnancy Assistance"}          |
      | 2              | Residence County         | Residence County         | Residence County         | {"Host": "Application","Dependent":"Application Consumer","Class":"Data","Program":"Medicaid, CHIP"}                                      |
      | 3              | Citizenship Verification | Citizenship Verification | Citizenship Verification | {"Host": "Application Consumer","Dependent":"Application Consumer","Class":"Verification","Program":"Medicaid, CHIP"}                     |
      | 4              | DOB                      | DOB                      | DOB                      | {"Host": "Application Consumer","Dependent":"Application Consumer","Class":"Data","Program":"CHIP, Pregnancy Assistance"}                 |
      | 5              | First Name               | First Name               | First Name               | {"Host": "Application Consumer","Dependent":"Application Consumer","Class":"Data","Program":"Medicaid, CHIP, HCBS, Pregnancy Assistance"} |
      | 6              | Last Name                | Last Name                | Last Name                | {"Host": "Application Consumer","Dependent":"Application Consumer","Class":"Data","Program":"Medicaid, CHIP, HCBS, Pregnancy Assistance"} |
      | 7              | Gender                   | Gender                   | Gender                   | {"Host": "Application Consumer","Dependent":"Application Consumer","Class":"Data","Program":"Medicaid, CHIP, Pregnancy Assistance"}       |
      | 8              | SSN                      | SSN                      | SSN                      | {"Host": "Application Consumer","Dependent":"Application Consumer","Class":"Data","Program":"Medicaid, CHIP, Pregnancy Assistance"}       |

  @API-CP-28735 @API-ATS @API-CRM-Regression @burak
  Scenario: Research Reason Enum
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_RESEARCH_REASON" and bservice "applicationdata"
    Then The call returns list of values for application intake "ENUM_RESEARCH_REASON" as
      | orderbyDefault | value                    | description              | reportLabel              | scope |
      | 1              | Insufficient Information | Insufficient Information | Insufficient Information | null  |
      | 2              | Document Misclassified   | Document Misclassified   | Document Misclassified   | null  |
      | 3              | Multiple Matches         | Multiple Matches         | Multiple Matches         | null  |

  @API-CP-31501 @API-CP-31501-01 @API-ATS @API-CRM-Regression @vinuta
  Scenario: Restrict addition / removal of applicants when matchingType is caseConsumer for a tenant
    When I will get the Authentication token for "BLCRM" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_APPLICATION_STATUS" and bservice "applicationdata"
    Then Call returns list of values for application status "ENUM_APPLICATION_STATUS" as
      | value         | description   | reportLabel   | scope                                                                                                                                                                                                                    |
      | Determining   | Determining   | Determining   | {"allowedBusinessActions":["CanEdit","CanWithdraw","CanUpdateDeadlineDate","CanCreateMissingInformation","CanSatisfyMissingInformation","CanDisregardMissingInformation","CanAddConsumers","CanDeleteConsumers"]} |
      | Entering Data | Entering Data | Entering Data | {"allowedBusinessActions":["CanEdit", "CanWithdraw", "CanUpdateDeadlineDate", "CanCreateMissingInformation", "CanSatisfyMissingInformation", "CanDisregardMissingInformation", "CanAddConsumers", "CanDeleteConsumers"]} |
      | Insufficient  | Insufficient  | Insufficient  | {"allowedBusinessActions":["CanEdit", "CanWithdraw", "CanUpdateDeadlineDate", "CanCreateMissingInformation", "CanSatisfyMissingInformation", "CanDisregardMissingInformation", "CanAddConsumers", "CanDeleteConsumers"]} |
      | Submitted     | Submitted     | Submitted     | {"allowedBusinessActions":["CanEdit", "CanWithdraw", "CanUpdateDeadlineDate", "CanCreateMissingInformation", "CanSatisfyMissingInformation", "CanDisregardMissingInformation", "CanAddConsumers", "CanDeleteConsumers"]} |

  @API-CP-31501 @API-CP-31501-02 @API-ATS @API-CRM-Regression @vinuta
  #Enable this test case when multi-tenant is implemented by inserting new tenant name
  Scenario: No Restriction on addition / removal of applicants when matchingType is not caseConsumer for a tenant
    When I will get the Authentication token for "BLATS2" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_APPLICATION_STATUS" and bservice "applicationdata"
    Then Call returns list of values for application status "ENUM_APPLICATION_STATUS" as
      | value         | description   | reportLabel   | scope                                                                                                                                                                                                                      |
      | Determining   | Determining   | Determining   | {"allowedBusinessActions":["CanEdit","CanWithdraw","CanUpdateDeadlineDate","CanCreateMissingInformation","CanSatisfyMissingInformation","CanDisregardMissingInformation","CanAddConsumers","CanDeleteConsumers"]}          |
      | Entering Data | Entering Data | Entering Data | {"allowedBusinessActions":["CanEdit", "CanWithdraw", "CanUpdateDeadlineDate", "CanCreateMissingInformation", "CanSatisfyMissingInformation", "CanDisregardMissingInformation", "CanAddConsumers", "CanDeleteConsumers"]} |
      | Insufficient  | Insufficient  | Insufficient  | {"allowedBusinessActions":["CanEdit", "CanWithdraw", "CanUpdateDeadlineDate", "CanCreateMissingInformation", "CanSatisfyMissingInformation", "CanDisregardMissingInformation", "CanAddConsumers", "CanDeleteConsumers"]} |
      | Submitted     | Submitted     | Submitted     | {"allowedBusinessActions":["CanEdit", "CanWithdraw", "CanUpdateDeadlineDate", "CanCreateMissingInformation", "CanSatisfyMissingInformation", "CanDisregardMissingInformation", "CanAddConsumers", "CanDeleteConsumers"]} |

  @API-CP-34278 @API-ATS @API-CRM-Regression @vinuta
  Scenario: Define and Configure Application Access Values for Auth Rep
    When I will get the Authentication token for "BLCRM" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_APPLICATION_ACCESS_VALUES" and bservice "applicationdata"
    Then The call returns list of values for application intake "ENUM_APPLICATION_ACCESS_VALUES" as
      | orderbyDefault | value                  | description            | reportLabel            | scope          |
      | 1              | Full Access            | Full Access            | Full Access            | Access Type    |
      | 2              | Partial Access         | Partial Access         | Partial Access         | Access Type    |
      | 3              | Receive in Addition To | Receive in Addition To | Receive in Addition To | Correspondence |
      | 4              | Receive in Place Of    | Receive in Place Of    | Receive in Place Of    | Correspondence |
      | 5              | Do Not Receive         | Do Not Receive         | Do Not Receive         | Correspondence |

  @API-CP-16845 @API-CP-35548 @API-CP-35548-01 @API-ATS @API-CRM-Regression @vinuta
  Scenario: Define ENUM table for Sub-Programs for Application Intake
    When I will get the Authentication token for "BLCRM" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_SUB_PROGRAMS" and bservice "applicationdata"
    Then Call returns list of values for application status "ENUM_SUB_PROGRAMS" as
      | orderbyDefault | value | description        | reportLabel        | scope                                                                    |
      | 1              | 10    | 10 - Sub-Program a | 10 - Sub-Program a | {"applicationType": "Medical Assistance","programs":["Medicaid","CHIP"]} |
      | 2              | 20    | 20 - Sub-Program b | 20 - Sub-Program b | {"applicationType": "Medical Assistance","programs":["Medicaid"]}        |
      | 3              | 30    | 30 - Sub-Program c | 30 - Sub-Program c | {"applicationType": "Medical Assistance","programs":["Medicaid"]}        |
      | 2              | 40    | 40 - Sub-Program d | 40 - Sub-Program d | {"applicationType": "Medical Assistance","programs":["CHIP"]}            |
    #  | 1              | 50    | 50 - Sub-Program e | 50 - Sub-Program e | {"applicationType": "Long Term Care","programs":["Medicaid"]}            |
    #  | 2              | 60    | 60 - Sub-Program f | 60 - Sub-Program f | {"applicationType": "Long Term Care","programs":["Medicaid"]}            |
      | 1              | 50    | 50 - Sub-Program e | 50 - Sub-Program e | {"applicationType": "Long Term Care","programs":["HCBS"]}                |


  @API-CP-37035 @API-CP-37035-01 @API-ATS @API-CRM-Regression @vinuta
  Scenario Outline: Restrict addition / removal of applicants when matchingType is caseConsumer for a tenant
    When I will get the Authentication token for "BLCRM" in "CRM"
    Given I initialize and send application scope API for status "<value>"
    Then the call returns list of values for application scope as "<scope>"
    Examples:
      | value                | scope                                                                                                                                                                                                                                        |
      | Determining          | {\"allowedBusinessActions\":[\"CanEdit\",\"CanWithdraw\",\"CanUpdateDeadlineDate\",\"CanCreateMissingInformation\",\"CanSatisfyMissingInformation\",\"CanDisregardMissingInformation\"]}                                                     |
      | Entering Data        | {\"allowedBusinessActions\":[\"CanEdit\", \"CanWithdraw\", \"CanUpdateDeadlineDate\", \"CanCreateMissingInformation\", \"CanSatisfyMissingInformation\", \"CanDisregardMissingInformation\", \"CanAddConsumers\", \"CanDeleteConsumers\"]} |
      | Insufficient         | {\"allowedBusinessActions\":[\"CanEdit\", \"CanWithdraw\", \"CanUpdateDeadlineDate\", \"CanCreateMissingInformation\", \"CanSatisfyMissingInformation\", \"CanDisregardMissingInformation\", \"CanAddConsumers\", \"CanDeleteConsumers\"]} |
      | Submitted            | {\"allowedBusinessActions\":[\"CanEdit\", \"CanWithdraw\", \"CanUpdateDeadlineDate\", \"CanCreateMissingInformation\", \"CanSatisfyMissingInformation\", \"CanDisregardMissingInformation\", \"CanAddConsumers\", \"CanDeleteConsumers\"]} |
      | Targets Unidentified | {\"allowedBusinessActions\":[\"CanEdit\",\"CanWithdraw\",\"CanUpdateDeadlineDate\",\"CanCreateMissingInformation\",\"CanSatisfyMissingInformation\",\"CanDisregardMissingInformation\",\"CanAddConsumers\",\"CanDeleteConsumers\"]}          |

  @API-CP-35898 @API-ATS @API-CRM-Regression @vinuta
  Scenario: Verify Transition to using Lookup Library in mars-applicationdata-services
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_PROGRAMS"
    Then The call returns list of values for application intake "ENUM_PROGRAMS" as
      | orderbyDefault | value                | description          | reportLabel          | scope              |
      | 1              | Medicaid             | Medicaid             | Medicaid             | Medical Assistance |
      | 2              | CHIP                 | CHIP                 | CHIP                 | Medical Assistance |
      | 3              | Pregnancy Assistance | Pregnancy Assistance | Pregnancy Assistance | Medical Assistance |
      | 4              | HCBS                 | HCBS                 | HCBS                 | Long Term Care     |

  @API-CP-36262 @API-CP-36262-01 @API-ATS @API-CRM-Regression @ozgen
  Scenario: Define Enum Table for MI Help Text
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I send Get request for ATS Lookup API with tableName "ENUM_MI_HELP_TEXT" and bservice "missinginformation"
    Then Call returns list of values for application status "ENUM_MI_HELP_TEXT" as
      | value                    | description              | reportLabel              | scope  |
      | Application Signed       | Application Signed       | Application Signed       | {"Following documents will be needed to satisfy the missing information":["Scanned copy of the application form with the application signature and signature date filled in to be mailed or uploaded to the portal"]} |
      | Citizenship Verification | Citizenship Verification | Citizenship Verification | {"Following documents will be needed to satisfy the missing information - US Citizen or National":["Certificate of U.S. citizenship (N-560, N-561) *","U.S. Passport book or card* (acceptable even if expired)","Certified Transcript of Birth","Valid driver’s license with photo or detailed description","School photo ID","U.S. Military card or draft record /Military dependent’s ID card /U.S. Coast Guard Merchant Mariner card","Federal, state or local government issued photo and/or description ID card ","Certificate of degree of Indian blood","U.S. Native American/Alaska native tribal document with photo or other identifying information","Final Adoption Decree Children under 16 who cannot supply any of the above items may submit their birth certificate","An affidavit containing the applicant’s name, DOB, and place of birth signed by someone who attests under penalty of perjury to the applicant’s citizenship. This statement may come from a physician or midwife or from an adoption agency. Affidavit does not need to be notarized.","NOTE: Front of the Passport Card is required"]} |
      | DOB                      | DOB                      | DOB                      | {"Following documents will be needed to satisfy the missing information":["Birth Certificate","Passport","Social Security Card","Marriage Certificate","Divorce Decree","Employer Identification Card","High School Diploma","College Diploma"]} |
      | First Name               | First Name               | First Name               | {"Following documents will be needed to satisfy the missing information":["Birth Certificate","Passport","Social Security Card","Marriage Certificate","Divorce Decree","Employer Identification Card","High School Diploma","College Diploma"]} |
      | Gender                   | Gender                   | Gender                   | {"Following documents will be needed to satisfy the missing information":["Signed statement from individual’s doctor","Amended birth certificate","Letter from SSA","Driver’s License","Passport"]} |
      | Last Name                | Last Name                | Last Name                | {"Following documents will be needed to satisfy the missing information":["Birth Certificate","Passport","Social Security Card","Marriage Certificate","Divorce Decree","Employer Identification Card","High School Diploma","College Diploma"]} |
      | Residence County         | Residence County         | Residence County         | {"Following documents will be needed to satisfy the missing information":["Government ID card with current address","NYS Driver’s License issued within past six (6) months","School Record showing current address","Property tax records or mortgage statement","Utility bill within the last six (6) months (gas, electric, cable, water/sewer)","Letter/lease/rent/receipt with current home address from landlord*","Postmarked, non-window envelope*","Postcard or magazine with name, current address, and date printed on (cannot be a stick-on label)*","Correspondence from a government agency","*NOTE: Letters, postmarked envelopes, postcards, magazines, and correspondence from a government agency must be addressed to the residential address to be acceptable. These are not acceptable if the return address is the residential address."]} |
      | SSN                      | SSN                      | SSN                      | {"Following documents will be needed to satisfy the missing information":["Social Security Card","Letter from Social Security Administration stating ineligible for SSN based on immigration status","Attestation of Social Security Number","ATIN (Adoption Taxpayer Identification Number)"]} |