@ProjectCreation_TM
Feature: API-Tenant Manager: Project Controller

  @tenantManagerAPI @tm-api-TM @auxiliaryService @event-api-AS @lookup-api-AS @API-CRM-290 @API-Project @Sujoy #@API-TM @API-TM-Regression
  Scenario Outline: Create Project with blank fields using API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Project API
    When I can provide all blank project information
    Then I run the create empty project API
    Examples:
      | projectName |
      | [blank]     |

  @tenantManagerAPI @tm-api-TM @auxiliaryService @search-services-AS @API-CRM-292 @API-TM @API-TM-Regression @API-Project @Sujoy
  Scenario Outline: Validate Project Lists using API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Get Project List API
    When I can provide all blank project information
    And I run the get project list API
    Then I can search the project by project name to validate is success
    Examples:
      | projectName |
      | [blank]     |

  @tenantManagerAPI @tm-api-TM @auxiliaryService @search-services-AS @event-api-AS @lookup-api-AS @API-CRM-291 @API-Project @Sujoy #@API-TM @API-TM-Regression
  Scenario Outline: Validate Duplicate Project is not created by API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Project API
    When I can provide project information to create a "<Provisioning>" project on "<state>"
    And I run the create project API
    Then I can search the project by project name to validate is "<success>"
    When I initiated Create Project API
    And I run the create project API again
    Then I can verify the project error message on API response
    Examples:
      | state | Provisioning | success | projectName |
      | MN    | Active       | TRUE    | [blank]     |
      | AL    | InActive     | TRUE    | [blank]     |
      | ND    | Pending      | TRUE    | [blank]     |

  @tenantManagerAPI @tm-api-TM @API-CRM-289-1 @API-CRM-290 @API-Project @Sujoy #@API-TM @API-TM-Regression
  Scenario Outline: Create Project with end date greater than today using API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Project API
    When I can provide project information to create a "<Provisioning>" project on "<state>" with end date greater than today
    And I run the create project API
    Then I can search the project by project name to validate is "<success>"
    Examples:
      | state | Provisioning | success | projectName |
      | MN    | Active       | TRUE    | [blank]     |
      | AL    | InActive     | TRUE    | [blank]     |
      | ND    | Pending      | TRUE    | [blank]     |

  @tenantManagerAPI @tm-api-TM @auxiliaryService @search-services-AS @event-api-AS @lookup-api-AS @API-CRM-289-2 @API-CRM-290 @API-Project @Sujoy #@API-TM @API-TM-Regression
  Scenario Outline: Create Project using API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Project API
    When I can provide project information to create a "<Provisioning>" project on "<state>"
    And I run the create project API
    Then I can search the project by project name to validate is "<success>"
    Examples:
      | state | Provisioning | success | projectName |
      | AK    | Active       | TRUE    | [blank]     |
      | AL    | InActive     | TRUE    | [blank]     |
      | AR    | Pending      | TRUE    | [blank]     |
      | AS    | Active       | TRUE    | [blank]     |
      | AZ    | InActive     | TRUE    | [blank]     |
      | CA    | Pending      | TRUE    | [blank]     |
      | CO    | Active       | TRUE    | [blank]     |
      | CT    | InActive     | TRUE    | [blank]     |
      | DC    | Pending      | TRUE    | [blank]     |
      | DE    | Active       | TRUE    | [blank]     |
      | FL    | InActive     | TRUE    | [blank]     |
      | FM    | Pending      | TRUE    | [blank]     |
      | GA    | Active       | TRUE    | [blank]     |
      | GU    | InActive     | TRUE    | [blank]     |
      | HI    | Pending      | TRUE    | [blank]     |
      | IA    | Active       | TRUE    | [blank]     |
      | ID    | InActive     | TRUE    | [blank]     |
      | IL    | Pending      | TRUE    | [blank]     |
      | IN    | Active       | TRUE    | [blank]     |
      | KS    | InActive     | TRUE    | [blank]     |
      | KY    | Pending      | TRUE    | [blank]     |
      | LA    | Active       | TRUE    | [blank]     |
      | MA    | InActive     | TRUE    | [blank]     |
      | MD    | Pending      | TRUE    | [blank]     |
      | ME    | Active       | TRUE    | [blank]     |
      | MH    | InActive     | TRUE    | [blank]     |
      | MI    | Pending      | TRUE    | [blank]     |
      | MN    | Active       | TRUE    | [blank]     |
      | MO    | InActive     | TRUE    | [blank]     |
      | MP    | Pending      | TRUE    | [blank]     |
      | MS    | Active       | TRUE    | [blank]     |
      | MT    | InActive     | TRUE    | [blank]     |
      | NC    | Pending      | TRUE    | [blank]     |
      | ND    | Active       | TRUE    | [blank]     |
      | NE    | InActive     | TRUE    | [blank]     |
      | NH    | Pending      | TRUE    | [blank]     |
      | NJ    | Active       | TRUE    | [blank]     |
      | NM    | InActive     | TRUE    | [blank]     |
      | NV    | Pending      | TRUE    | [blank]     |
      | NY    | Active       | TRUE    | [blank]     |
      | OH    | InActive     | TRUE    | [blank]     |
      | OK    | Pending      | TRUE    | [blank]     |
      | OR    | Active       | TRUE    | [blank]     |
      | PA    | InActive     | TRUE    | [blank]     |
      | PR    | Pending      | TRUE    | [blank]     |
      | PW    | Active       | TRUE    | [blank]     |
      | RI    | InActive     | TRUE    | [blank]     |
      | SC    | Pending      | TRUE    | [blank]     |
      | SD    | Active       | TRUE    | [blank]     |
      | TN    | InActive     | TRUE    | [blank]     |
      | TX    | Pending      | TRUE    | [blank]     |
      | UT    | Active       | TRUE    | [blank]     |
      | VA    | InActive     | TRUE    | [blank]     |
      | VI    | Pending      | TRUE    | [blank]     |
      | VT    | Active       | TRUE    | [blank]     |
      | WA    | InActive     | TRUE    | [blank]     |
      | WI    | Pending      | TRUE    | [blank]     |
      | WV    | Active       | TRUE    | [blank]     |
      | WY    | InActive     | TRUE    | [blank]     |

  @tenantManagerAPI @tm-api-TM @auxiliaryService @search-services-AS @lookup-api-AS @API-CRM-294 @API-TM @API-TM-Regression @API-Project @Sujoy
  Scenario Outline: Search Projects using API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Search Project API
    When I can Search Project by "<Node>" with value "<value>"
    And I run the search project API
    Then I can verify "<Node>" with value "<value>" on project API response will be "<success>"
    Examples:
      | Node            | value         | success | projectName |
      | state           | ND            | True    | [blank]     |
      | state           | Nn            | True    | [blank]     |
      | programName     | test          | True    | [blank]     |
      | programName     | sadklfjsfsk   | True    | [blank]     |
      | projectName     | Child         | True    | [blank]     |
      | projectName     | sdkfljkljsd   | True    | [blank]     |
      | stateAgencyName | CHIPeb        | True    | [blank]     |
      | stateAgencyName | sdkjfsdklfjds | True    | [blank]     |

  @tenantManagerAPI @tm-api-TM @auxiliaryService @search-services-AS @lookup-api-AS @API-CRM-295 @API-TM @API-TM-Regression @API-Project @Sujoy
  Scenario Outline: Search Projects using API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Search Project API
    When I can Search Project by "<Node1>" with value "<value1>", "<Node2>" with value "<value2>", "<Node3>" with value "<value3>" and "<Node4>" with value "<value4>"
    And I run the search project API
    Then I can verify on project search API response will be "<success>"
    Examples:
      | Node1       | value1  | Node2 | value2  | Node3       | value3   | Node4           | value4               | success | projectName |
      | projectName | Child   | state | MD      | programName | [blank]  | stateAgencyName | [blank]              | True    | [blank]     |
      | projectName | [blank] | state | CA      | programName | Medicaid | stateAgencyName | [blank]              | True    | [blank]     |
      | projectName | Child   | state | [blank] | programName | Medicaid | stateAgencyName | [blank]              | True    | [blank]     |
      | projectName | Child   | state | [blank] | programName | [blank]  | stateAgencyName | Illonis State Agency | True    | [blank]     |
      | projectName | [blank] | state | CA      | programName | [blank]  | stateAgencyName | Illonis State Agency | True    | [blank]     |
      | projectName | [blank] | state | [blank] | programName | Medicaid | stateAgencyName | Illonis State Agency | True    | [blank]     |
      | projectName | Child   | state | CA      | programName | Medicaid | stateAgencyName | [blank]              | True    | [blank]     |
      | projectName | Child   | state | CA      | programName | [blank]  | stateAgencyName | Illonis State Agency | True    | [blank]     |
      | projectName | Child   | state | [blank] | programName | Medicaid | stateAgencyName | Illonis State Agency | True    | [blank]     |
      | projectName | [blank] | state | CA      | programName | Medicaid | stateAgencyName | Illonis State Agency | True    | [blank]     |
      | projectName | Child   | state | CA      | programName | Medicaid | stateAgencyName | Illonis State Agency | True    | [blank]     |
      | projectName | sdsdd   | state | CA      | programName | Medicaid | stateAgencyName | Illonis State Agency | True    | [blank]     |
      | projectName | Child   | state | TT      | programName | Medicaid | stateAgencyName | Illonis State Agency | True    | [blank]     |
      | projectName | Child   | state | CA      | programName | Medicare | stateAgencyName | Illonis State Agency | True    | [blank]     |
      | projectName | Child   | state | CA      | programName | Medicaid | stateAgencyName | Texas State Agency   | True    | [blank]     |


  @tenantManagerAPI @tm-api-TM @auxiliaryService @search-services-AS @lookup-api-AS@API-CRM-291 @API-TM @API-TM-Regression @API-Project @Sujoy
  Scenario Outline: Search a project by Project ID using API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Search Project API
    When I can Search Project by "<Node>" with value "<value>"
    And I run the search project API
    And I can get ProjectID
    Given I initiated Search Project API By Project ID ""
    And I run the search project API By ProjectID
    Then I can verify Project ID on project search API response will be "<success>"
    Examples:
      | Node  | value | success | projectName |
      | state | Az    | True    | [blank]     |


  @API-CRM-291 @API-TM @API-TM-Regression @API-Project @Sujoy @tenantManagerAPI  @tm-api-TM
  Scenario Outline: Get approver detail if a project using API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated project approver API by project name "<projectName>"
    When I can get the project approver detail
    Then I can verify get user approver detail API response will be "<success>"
    Examples:
      | projectName | success |
      | BLCRM       | True    |

#@API-CRM-2174 @API-TM @API-TM-Regression @API-Project @Sujoy @tenantManagerAPI  @tm-api-TM
  #CRM api payload changed
  Scenario Outline: Create a User session record each time a user logs into CRM using API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated project user activity
    When I can define user activity for project using api
    Then I can verify the the user activity session using API
    Examples:
      | projectName |
      | [blank]     |

#@API-CP-138-02 @API-CRM-Login @API-CRM @API-Project @aswath @tenantManagerAPI @tm-api-TM not in used
  Scenario Outline: Fetch logged in user ID using Apigee API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated apigee api
    When I can define apigee using api
    And I run initiate apigee API
    Then I verify apigee API successfully
    Then I verify user details retrieved by get api
    Examples:
      | projectName |
      | [blank]     |

  @GA-API-CP-7497 @GA-API-CP-7497-1 @API-TM @tenant-manager @GA-API-TM-Regression @Vidya
    #GA configuration is updated--need refactor with updated
  Scenario Outline: Verify Project GET API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated project GET API for "<projectId>"
    When I run the project GET API
    Then I will verify "<prjName>","<prjState>","<prgName>","<contractId>","<stateAgencyName>","<startDate>","<endDate>","<goLiveDate>","<provStatus>","<timeZone>" fields values in project response
    Examples:
      | projectId | projectName | prjName          | prjState | prgName             | contractId | stateAgencyName                              | startDate  | endDate    | goLiveDate | provStatus | timeZone |
      | GACRM     | [blank]     | Georgia-Families | GA       | Medicaid/CHIP/Other | 07XX       | Georgia Department of Community Health (DCH) | 2015-07-03 | 2026-04-02 | 2024-04-02 | Active     | Eastern  |

  @GA-API-CP-7497 @GA-API-CP-7497-2 @API-TM @tenant-manager @GA-API-TM-Regression @Vidya
  Scenario Outline: Verify Project GET API
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated project GET API for "<projectId>"
    When I run the project GET API
    Then I will verify the Contact Details for "<Role>","<fName>","<mName>","<lName>","<phone>","<email>" in project response
    Examples:
      | projectId | projectName | Role             | fName    | mName   | lName    | phone      | email                        |
      | GACRM     | [blank]     | Account Manager  | Adeline  | [blank] | Pierre   | 6789926241 | AdelineBPierre@maersk.com   |
      | GACRM     | [blank]     | Contact          | Jeffrey  | [blank] | Hines    | 4045758008 | JefferyHines@maersk.com     |
      | GACRM     | [blank]     | Contact          | Octavius | [blank] | Robinson | 4045758074 | OctaviusRobinson@maersk.com |
      | GACRM     | [blank]     | Account Approver | Donna    | [blank] | Herren   | 7032518502 | DonnaLHerren@maersk.com     |

  @API-CP-13407 @API-TM @API-TM-Regression @mital
  Scenario Outline: Verify Refresh API as a Background Process
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated refresh API for "<projectName>"
    When I run the project GET API
    Then I can verify Refresh API response will be "<success>"
    Examples:
      | projectName | success |
      | Regression  | True    |

  @API-CP-12564 @API-CP-12564-01 @API-TM @API-TM-Regression @mital
  Scenario Outline: Verify if CRM returns correct display label when passed a userId
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated displayname API for "<projectName>"
    When I get the userID from "<firstName>" and "<lastName>"
    And User send Api call with payload "apiTenantManagerDisplayLabelwithUserID" to return display label using userID
      | firstName   | <firstName>   |
      | projectName | <projectName> |
    Then I can verify Refresh API response will be "<success>"
    And I verify response returns valid "<firstName>" and "<lastName>" values
    Examples:
      | projectName | success | firstName | lastName   |
      | BLCRM       | True    | Service   | AccountOne |
      | IN-EB       | True    | Service   | AccountOne |
      | CoverVA     | True    | Service   | AccountOne |
      | NJ-SBE      | True    | Service   | AccountOne |


  @API-CP-12564 @API-CP-12564-02 @mital #@API-TM @API-TM-Regression
  Scenario Outline: Verify if CRM returns correct display label when passed a project name + user firstName (for system users)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated displayname API for "<projectName>"
    And User send Api call with payload "apiTenantManagerDisplayLabelwithUserID" to return display label
      | firstName   | <firstName>   |
      | projectName | <projectName> |
    Then I can verify Refresh API response will be "<success>"
    And I verify response returns valid "<firstName>" and "<lastName>" values
    Examples:
      | projectName | success | firstName         | lastName |
      | BLCRM       | True    | Servicedemo       | null     |
      | IN-EB       | True    | Task Mgmt Service | null     |
      | CoverVA     | True    | Task Mgmt Service | null     |
      | NJ-SBE      | True    | Task Mgmt Service | null     |

  @API-CP-35974 @API-CP-35974-01 @tenantManagerAPI @tm-api-TM @API-TM @API-TM-Regression @mital
  Scenario Outline: Validate project name can not contain Spaces and special character
    Given I will get the Authentication token for "<projectName>" in "Tenant Manager"
    When I initiated Create Project API
    Then User send Api call with payload "apiUpdateProjectDetails" for project details Update
      | projectName | <projectNameValue> |
    Then I verify response contain error message for Invalid Project name
    Examples:
      | projectNameValue    |
      | TestProject123 temp |
      | TestProject123#@%$  |

