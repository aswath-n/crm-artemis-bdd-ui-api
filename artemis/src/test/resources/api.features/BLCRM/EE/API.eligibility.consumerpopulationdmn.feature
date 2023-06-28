Feature: API: Consumer Population DMN
# Commenting the regresssion tag for this feature as it is redundant
  @API-CP-8369 @API-CP-8369-01 #@API-EE-REMOVED @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Population is New born
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | longTermCareInfo, |
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | planId                       | 2077              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB       | Pregnancy Indicator | Pregnancy Due Date | Service Region | Program Type | PopulationType |
      | 10 months | false               | null               | Atlanta        | M            | NEWBORN        |
      | 0 days    | true                | null               | Southwest      | H            | NEWBORN        |
      | 364 days  | false               | 1 months           | Southwest      | H            | NEWBORN        |
      | 12 months | false               | null               | East           | F            | NONE           |
      | 366 days  | false               | null               | Central        | P            | NONE           |
      | 13 months | true                | null               | Central        | P            | NONE           |


#     @API-CP-8369 @API-CP-8369-02 @API-EE @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
#      Scenario Outline: Verify Population is Pregnant -women
#       Given I will get the Authentication token for "<projectName>" in "CRM"
#      Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
#          And I initiated Eligibility and Enrollment Create API
#       And I provide the enrollment and eligibility information to create enrollment
#         |isEligibilityRequired|yes|
#         |otherSegments        |[blank]|
#         |isEnrollemntRequired |no |
#         |recordId             |1  |
#         |isEnrollmentProviderRequired|no|
#         |enrollmentStartDate         ||
#         |categoryCode                |78AidCode|
#         |eligibilityStartDate        |1stDayofNextMonth|
#         |txnStatus                  |Accepted|
#         |programCode     |H       |
#         | subProgramTypeCode           | MEDICAIDMCHB         |
#       And I run create Eligibility and Enrollment API
#     And I initiated get benefit status by consumer id ""
#     And I run get enrollment api
#     Then I verify benefit status records are displayed for the consumer ""
#     And I verify benefit status records are displayed with population "<PopulationType>"
#
#    Examples:
#      |DOB           |Pregnancy Indicator| Pregnancy Due Date     | Service Region      | Program Type|PopulationType       |
#      |92 years      |true               |[blank]|       Atlanta       |    M        |   PREGNANT-WOMEN    |
#      |30 years      |false              | 2 months               |       East          |    F        |   PREGNANT-WOMEN    |
#      |1188 months   |false              | 8 months               |       Central       |    P        |   PREGNANT-WOMEN    |
#      |1189 months   |true               | 2 months               |       East          |    M        |   NONE              |
#      |9 months      | true              | null                   |       East          |    H        |   PREGNANT-WOMEN    |
#      |11 months     | false             | 2 months               |       East          |    F        |   PREGNANT-WOMEN    |

  @API-CP-8369 @API-CP-8369-03 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Population is Foster Care - With AidCat code
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | 789AidCode        |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    And  I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB      | Service Region | Other Segments | Cate Code | Pregnancy Indicator | Pregnancy Due Date | PopulationType |
      | 17 years | Atlanta        | facilityInfo   |[blank]| false               | null               | FOSTER-CARE    |
      | 19 years | Atlanta        | facilityInfo   |[blank]| false               | null               | NONE           |
      | 20 years | Atlanta        | Financial Info |[blank]| false               | null               | NONE           |

  @API-CP-8369 @API-CP-8369-04 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Population is Foster Care - With Facility code
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | facilityInfo      |
      | isEnrollemntRequired         | no                |
      | recordId                     | 1                 |
      | isEnrollmentProviderRequired | no                |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | 78AidCode         |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | programCode                  | F                 |
      | subProgramTypeCode           | FOSTERCARE        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    And  I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB      | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | PopulationType |
      | 17 years | Atlanta        |[blank]| 789AidCode | false               | null               | FOSTER-CARE    |
      | 18 years | East           |[blank]| 789AidCode | false               | 3 months           | FOSTER-CARE    |
      | 20 years | North          | financialInfo  | 789AidCode | false               | null               | NONE           |
      | 19 years | Central        |[blank]| 789AidCode | false               | null               | NONE           |


#Redundant
  #@API-CP-8369 @API-CP-8369-05 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
#  Scenario Outline: Verify Population Heirarchy
#    Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
#   When I create eligibility record for bpm process with other segments "<Other Segments>" and "<Cate Code>"
#    And I initiated BPM Process for the consumer ""
#   When I will get the Authentication token for "<projectName>" in "CRM"
#    When I start the BPM process
#    And I initiated get benefit status by consumer id ""
#    And I run get enrollment api
#    And  I verify benefit status records are displayed with population "<PopulationType>"
#
#        Examples:
#      |DOB                            | Service Region      | Other Segments                    |Cate Code   |Pregnancy Indicator|Pregnancy Due Date|PopulationType   |
#      |11 months                      |       Atlanta       |  facilityInfo                 |[blank]|false              | null             | NEWBORN         |
#      |11 months                      |    Atlanta          | facilityInfo                  |[blank]|true               | null             | FOSTER-CARE     |
#      |11 months                      |    Atlanta          |[blank]|  789AidCode|true               | null             | FOSTER-CARE     |
#      |18 years                       |  Central            |  facilityInfo                 |[blank]|true               | null             | FOSTER-CARE     |
#      |19 years                       |    East             | financialInfo , facilityInfo  |[blank]| false             |3 months          | PREGNANT-WOMEN  |
#      |45 years                       |   East             | medicareInfo,thirdPartyInsuranceInfo |[blank]|false              |null              | MEDICAID-GENERAL|
#      |99 years                       |   North             |[blank]| 789AidCode |false              |null              | NONE            |

  @API-CP-68 @API-CP-69 @API-CP-68-01 #@API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify GENERAL POPULATION with Program type M
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | planId                       | 2077              |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    And  I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB      | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | PopulationType   |
      | 45 years | East           |[blank]| null       |[blank]| null               | MEDICAID-GENERAL |
      | 99 years | Central        |[blank]| null       |[blank]| null               | MEDICAID-GENERAL |
      | 45 years | Atlanta        |[blank]| 789AidCode |[blank]| null               | MEDICAID-GENERAL |


  @API-CP-68 @API-CP-68-02 #@API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify GENERAL POPULATION with Program type M with Special population
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes               |
      | otherSegments                | waiverInfo        |
      | isEnrollemntRequired         | yes               |
      | recordId                     | 0                 |
      | isEnrollmentProviderRequired | yes               |
      | enrollmentStartDate          | 1stDayofNextMonth |
      | eligibilityStartDate         | 1stDayofNextMonth |
      | txnStatus                    | Accepted          |
      | planId                       | 2077              |
      | programCode                  | M                 |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    And  I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB      | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | PopulationType |
      | 45 years | East           |[blank]| null       |[blank]| null               | NONE           |
      | 99 years | Central        |[blank]| null       |[blank]| null               | NONE           |
      | 45 years | Atlanta        |[blank]| 789AidCode |[blank]| null               | NONE           |
      | 45 years | East           | financialInfo  |[blank]|                     | null               | NONE           |


  @API-CP-10836 @API-CP-10836-01.01 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify GENERAL POPULATION For Current Eligibility- with special population
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And  I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB       | Service Region | Other Segments | Cate Code | Pregnancy Indicator | Pregnancy Due Date | PopulationType   |
      | 45 years  | East           |[blank]| null      | false               | null               | MEDICAID-GENERAL |
      | 99 years  | Southeast      |[blank]| null      | false               | null               | MEDICAID-GENERAL |
      | 100 years | Atlanta        |[blank]| null      | false               | null               | NONE             |


  @API-CP-10836 @API-CP-10836-01 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify GENERAL POPULATION For Current Eligibility
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null                                                     |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |

      | txnStatus                    | Accepted                                                 |
      | programCode                  | M                                                        |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And  I verify benefit status records are displayed with population "<PopulationType>"
    Examples:
      | DOB       | Service Region | Other Segments | Cate Code | Pregnancy Indicator | Pregnancy Due Date | PopulationType |
      | 13 months | East           | financialInfo  |[blank]|                     | null               | NONE           |

  @API-CP-10836 @API-CP-10836-01.01 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify GENERAL POPULATION For Current Eligibility with special segments
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    #Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | financialInfo        |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | M                    |

    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And  I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB       | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | PopulationType   |
      | 10 years  | East           |[blank]| null       | false               | null               | MEDICAID-GENERAL |
      | 19 years  | Southeast      |[blank]| 123AidCode | false               | null               | MEDICAID-GENERAL |
      | 11 years  | Atlanta        |[blank]| 789Aidcode | false               | null               | NONE             |
      | 13 months | East           | financialInfo  |[blank]|                     | null               | NONE             |
     # |1 years                       |   East             |[blank]| null       |     false         |    null          | NONE            |
  @API-CP-10836 @API-CP-10836-02 @API-EE-REMOVEd  @shruti
  Scenario Outline: Verify  POPULATION Type = None For Current Eligibility -
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    #Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | 789AidCo             |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | FOSTERCARE           |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And  I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB       | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | PopulationType |
      | 10 years  | East           |[blank]| null       | false               | null               | NONE           |
      | 19 years  | Southeast      |[blank]| 123AidCode | false               | null               | NONE           |
      | 11 years  | Atlanta        |[blank]| 789Aidcode | false               | null               | NONE           |
      | 13 months | East           | financialInfo  |[blank]|                     | null               | NONE           |

  @API-CP-10836 @API-CP-10836-03 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify  POPULATION Type = FosterCare For Current Eligibility -
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    #Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | 789AidCode           |
      | eligibilityStartDate         | 1stDayofPresentMonth |

      | txnStatus                    | Accepted             |
      | programCode                  | F                    |
      | subProgramTypeCode           | FOSTERCARE           |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And  I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB       | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | PopulationType |
      | 19 years  | Southeast      |[blank]|            | false               | null               | NONE           |
      | 11 years  | Atlanta        |[blank]| 789Aidcode | false               | null               | FOSTER-CARE    |
      | 13 months | East           | financialInfo  |[blank]|                     | null               | FOSTER-CARE    |


  @API-CP-10836 @API-CP-10836-04 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify  POPULATION Type = Pregnant For Current Eligibility -
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | 78AidCode            |
      | eligibilityStartDate         | 1stDayofPresentMonth |

      | txnStatus                    | Accepted             |
      | programCode                  | H                    |
      | subProgramTypeCode           | MEDICAIDMCHB         |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And  I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB       | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | PopulationType |
      | 2 years   | Southeast      |[blank]|            | true                |[blank]| PREGNANT-WOMEN |
      | 11 years  | Atlanta        |[blank]| 789Aidcode | false               |[blank]| PREGNANT-WOMEN |
      | 13 months | East           | financialInfo  |[blank]| true                |[blank]| PREGNANT-WOMEN |


  @API-CP-10836 @API-CP-10836-05 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify  POPULATION Type = Newborn For Current Eligibility -
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    #Given I created a consumer with population type "NEWBORN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | 78AidCode            |
      | eligibilityStartDate         | 1stDayofPresentMonth |

      | txnStatus                    | Accepted             |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And  I verify benefit status records are displayed with population "<PopulationType>"

    Examples:
      | DOB              | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | PopulationType |
      | 1 day            | Southeast      |[blank]|            | false               | null               | NEWBORN        |
      | 11 months 29days | Atlanta        |[blank]| 789Aidcode | false               | null               | NEWBORN        |
      | 13 months        | East           | financialInfo  |[blank]| false               |[blank]| NONE           |

  @API-CP-11054 @API-CP-11054-01 @shruti
  Scenario Outline: Verify Population is New born with Program Type =H For New Eligibility
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 0                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | planId                       | 2077                                                     |
      | programCode                  | H                                                        |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with programpopulation as "<Program Population>"

    Examples:
      | DOB       | Pregnancy Indicator | Pregnancy Due Date | Service Region | Program Type | Program Population |
      | 10 months | false               | null               | Atlanta        | M            | NONE               |

  @API-CP-11054 @AP-CP-71 @API-CP-11054-02 #@API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Population is New born for Program type = P For New Eligibility
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofNextMonth                                        |
      | eligibilityStartDate         | 1stDayofNextMonth                                        |
      | txnStatus                    | Accepted                                                 |
      | planId                       | 2077                                                     |
      | programCode                  | P                                                        |
      | programTypeCode              | CHIP                                                     |
      | subProgramTypeCode           | PEACHCAREGF                                              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with programpopulation as "<Program Population>"

    Examples:
      | DOB       | Pregnancy Indicator | Pregnancy Due Date | Service Region | Program Population |
      | 10 months | false               | null               | Atlanta        | Medicaid-Newborn   |
      | 364 days  | true                | null               | Central        | Medicaid-Newborn   |
      | 0 days    | false               | 2 months           | East           | Medicaid-Newborn   |
      | 366 days  | false               | null               | Southwest      | NONE               |
      | 364 days  | true                | null               | Invalid        | NONE               |


  @API-CP-11054  @AP-CP-71 @API-CP-11054-03 #@API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Population is New born for Program type = P for New Retroactive Eligibility
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    When I create eligibility record for bpm process with "1stDayofNextMonth" timeframe
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | yes                                                      |
      | recordId                     | 3                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          | 1stDayofPresentMonth                                     |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | planId                       | 2077                                                     |
      | programCode                  | P                                                        |
      | programTypeCode              | CHIP                                                     |
      | subProgramTypeCode           | PEACHCAREGF                                              |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    And I verify benefit status records are displayed with programpopulation as "<Program Population>"

    Examples:
      | DOB       | Pregnancy Indicator | Pregnancy Due Date | Service Region | Program Population |
      | 10 months | false               | null               | Atlanta        | CHIP-Newborn       |
      | 364 days  | true                | null               | Central        | CHIP-Newborn       |
      | 0 days    | false               | 2 months           | East           | CHIP-Newborn       |
      | 366 days  | false               | null               | Southwest      | NONE               |
      | 364 days  | true                | null               | Invalid        | NONE               |

  @API-CP-11054 @API-CP-11054-03 #@API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Population is Pregnant -women for H
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | 78AidCode                                                |
      | eligibilityStartDate         | 1stDayofPresentMonth                                     |
      | txnStatus                    | Accepted                                                 |
      | programCode                  | H                                                        |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed with programpopulation as "<Program Population>"

    Examples:
      | DOB         | Pregnancy Indicator | Pregnancy Due Date | Service Region | Program Type | Program Population      |
      | 92 years    | true                |[blank]| Atlanta        | M            | Medicaid-Pregnant Women |
      | 30 years    | false               | 2 months           | East           | F            | Medicaid-Pregnant Women |
      | 1188 months | false               | 8 months           | Central        | P            | Medicaid-Pregnant Women |
      | 1189 months | true                | 2 months           | East           | M            | NONE                    |
      | 9 months    | true                | null               | East           | H            | Medicaid-Pregnant Women |
      | 11 months   | false               | 2 months           | East           | F            | Medicaid-Pregnant Women |


  @API-CP-11054 @API-CP-11054-04 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Population is Pregnant -women for H with special population
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    And I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                                                      |
      | otherSegments                | waiverInfo,thirdPartyInsuranceInfo,specialPopulationInfo |
      | isEnrollemntRequired         | no                                                       |
      | recordId                     | 1                                                        |
      | isEnrollmentProviderRequired | no                                                       |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | 78AidCode                                                |
      | eligibilityStartDate         | 1stDayofNexttMonth                                       |
      | programCode                  | H                                                        |
      | subProgramTypeCode           | MEDICAIDMCHB                                             |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then  I verify benefit status records are displayed with programpopulation as "<Program Population>"

    Examples:
      | DOB       | Pregnancy Indicator | Pregnancy Due Date | Service Region | Program Type | Program Population      |
      | 99 years  | true                |[blank]| Atlanta        | M            | Medicaid-Pregnant Women |
      | 11 months | false               | 2 months           | East           | F            | Medicaid-Pregnant Women |
      | 55 years  | false               | 2 months           | East           | F            | Medicaid-Pregnant Women |

  @API-CP-11054 @API-CP-11054-05 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify Program POPULATION = Medicaid General For Retroactive Eligibility -
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    When I create eligibility record for bpm process with "1stDayofNextMonth" timeframe
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | 789AidCo             |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | programCode                  | M                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then I verify benefit status records are displayed for the consumer ""
    Then  I verify benefit status records are displayed with programpopulation as "<Program Population>"

    Examples:
      | DOB      | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | Program Population          |
      | 10 years | East           |[blank]| null       | false               | null               | Medicaid-General Population |
      | 19 years | Southeast      |[blank]| 123AidCode | false               | null               | Medicaid-General Population |
      | 2 months | Southeast      |[blank]| 123AidCode | false               | null               | Medicaid-Newborn            |

  @API-CP-11054 @API-CP-11054-06 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify  POPULATION Type = No Program For Current Eligibility -
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    When I create eligibility record for bpm process with "1stDayofNextMonth" timeframe
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | 789AidCo             |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | O                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then  I verify benefit status records are displayed with programpopulation as "<Program Population>"

    Examples:
      | DOB      | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | Program Population |
      | 10 years | East           |[blank]| null       | false               | null               | NONE               |
      | 19 years | Southeast      |[blank]| 123AidCode | false               | null               | NONE               |


  @API-CP-11054 @API-CP-11054-07 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario Outline: Verify  POPULATION Type = No Population For Current Eligibility -
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I created a consumer with "<DOB>" ,"<Pregnancy Indicator>", "<Pregnancy Due Date>", "<Service Region>"
    When I create eligibility record for bpm process with "1stDayofNextMonth" timeframe
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | 789AidCo             |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | future               |
      | txnStatus                    | Accepted             |
      | programCode                  | O                    |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then  I verify benefit status records are displayed with programpopulation as "<Program Population>"

    Examples:
      | DOB       | Service Region | Other Segments | Cate Code  | Pregnancy Indicator | Pregnancy Due Date | PopulationType | Program Population |
      | 101 years | Invalid        |[blank]| null       | false               | null               | NONE           | NONE               |
      | 109 years | Invalid        |[blank]| 123AidCode | false               | null               | NONE           | NONE               |


  @API-CP-8368 @API-CP-8368-01 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify  Program Type =CHIP for Current Eligibility End Date = future
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | 789AidCo             |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 1                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | eligibilityEndDate           | futureDate           |
      | txnStatus                    | Accepted             |
      | programCode                  | P                    |
      | programTypeCode              | CHIP                 |
      | subProgramTypeCode           | PEACHCAREGF          |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then  I verify benefit status records displays Program Type as "CHIP" and Timeframe as "Active"


  @API-CP-8368 @API-CP-8368-02 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify  Program Type =CHIP for Current Eligibility-startDate = past  End Date =null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | 789AidCo             |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | P                    |
      | programTypeCode              | CHIP                 |
      | subProgramTypeCode           | PEACHCAREGF          |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then  I verify benefit status records displays Program Type as "CHIP" and Timeframe as "Active"


  @API-CP-8368 @API-CP-8368-02.02 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify  Program Type =CHIP for Current Eligibility- Start Date =current date, End Date = Future
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes                  |
      | otherSegments                | 789AidCo             |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 3                    |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null                 |
      | eligibilityStartDate         | 1stDayofPresentMonth |
      | txnStatus                    | Accepted             |
      | programCode                  | P                    |
      | programTypeCode              | CHIP                 |
      | subProgramTypeCode           | PEACHCAREGF          |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then  I verify benefit status records displays Program Type as "CHIP" and Timeframe as "Active"

  @API-CP-8368 @API-CP-8368-03 @API-EE-REMOVEd @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify  Program Type =CHIP for Future Eligibility- End Date =null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes              |
      | otherSegments                | 789AidCo         |
      | isEnrollemntRequired         | no               |
      | recordId                     | 1                |
      | isEnrollmentProviderRequired | no               |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null             |
      | eligibilityStartDate         | 1stDayoNextMonth |
      | txnStatus                    | Accepted         |
      | programCode                  | P                |
      | programTypeCode              | CHIP             |
      | subProgramTypeCode           | PEACHCAREGF      |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then  I verify benefit status records displays Program Type as "CHIP" and Timeframe as "FUTURE"


  @API-CP-8368 @API-CP-8368-04 @API-EE-REMOVED @API-CRM-Regression @eligibility-enrollment-ms-EE @shruti
  Scenario: Verify  Program Type =CHIP for Future Eligibility- End Date =greater than current date
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer with population type "PREGNANT-WOMEN"
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | isEligibilityRequired        | yes              |
      | otherSegments                | 789AidCo         |
      | isEnrollemntRequired         | no               |
      | recordId                     | 1                |
      | isEnrollmentProviderRequired | no               |
      | enrollmentStartDate          |[blank]|
      | categoryCode                 | null             |
      | eligibilityStartDate         | 1stDayoNextMonth |
      | txnStatus                    | Accepted         |
      | programCode                  | P                |
      | programTypeCode              | CHIP             |
      | subProgramTypeCode           | PEACHCAREGF      |
    And I run create Eligibility and Enrollment API
    And I initiated get benefit status by consumer id ""
    And I run get enrollment api
    Then  I verify benefit status records displays Program Type as "CHIP" and Timeframe as "FUTURE"




    