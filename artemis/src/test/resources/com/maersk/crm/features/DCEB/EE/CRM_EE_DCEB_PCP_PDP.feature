Feature: DC EB: PCP and PDP for Plan Change and Enroll

  @CP-40356 @CP-40356-01 @CP-40028 @CP-40028-01 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario Outline: Verify Start with Plan: Select Plan, Select PDP, Select PCP
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <consumerId> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | yes                     |
      | otherSegments                | [blank]                 |
      | isEnrollemntRequired         | no                      |
      | recordId                     | 25                      |
      | eligibilityRecordId          | 25                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofLastMonth       |
      | eligibilityStartDate         | 1stDayofLastMonth       |
      | eligibilityEndDate           | highDate-DC             |
      | txnStatus                    | Accepted                |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | DCHF                    |
      | coverageCode                 | 130                     |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<consumerId>" for update Enrollment information
      | [0].consumerId         | <consumerId>.consumerId |
      | [0].planId             | 145                     |
      | [0].planCode           | 081080400               |
#      | [0].startDate          | fdnxtmth::          |
      | [0].startDate          | fdofmnth::              |
      | [0].endDate            | highdatedc::            |
      | [0].programTypeCode    | MEDICAID                |
      | [0].subProgramTypeCode | DCHF                    |
      | [0].serviceRegionCode  | Northeast               |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<consumerId>" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION      |
      | [0].startDate    | fdofmnth::              |
#      | [0].startDate    | fdnxtmth::             |
      | [0].planId       | delete::                |
      | [0].planCode     | 081080400               |
      | [0].consumerId   | <consumerId>.consumerId |
      | [0].enrollmentId | c.data[0].enrollmentId  |
      | [0].status       | SUBMITTED_TO_STATE      |
      | [0].txnStatus    | SUBMITTED_TO_STATE      |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | no                      |
      | isEnrollemntRequired         | yes                     |
      | isEnrollmentProviderRequired | no                      |
      | recordId                     | 15                      |
      | enrollmentRecordId           | 15                      |
#      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate-DC             |
      | anniversaryDate              | anniversaryDate         |
      | eligibilityStartDate         | [blank]                 |
      | programCode                  | MEDICAID                |
      | planCode                     | 081080400               |
      | planId                       | 145                     |
      | subProgramTypeCode           | DCHF                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<consumerId>.firstName",Last Name as "<consumerId>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "Plan Change" Button for a consumer
    And I select "MEDSTAR FAMILY CHOICE" from Available Plans
#    1.0 Search and Select Plan (regression) CP-40028
#    1.0 Search and Select Plan (regression) CP-40356
    And I click on Search PCP and PDP button on Plan Option
#    2.0 Search PCP CP-40028
#    2.0 Search PCP CP-40356
#    We can't verify the Provider address details value because sometimes it's comes and sometimes it's not come will verify manually
    Then I verify fields on provider search page with data
#      | STREET ADDRESS      | 22 main st    |
#      | CITY                | Washington DC |
#      | ZIP                 | 20510         |
#      | DISTANCE (mi)       | 10            |
      | PROVIDER TYPE | MEDICAL |
    Then I verify search and cancel buttons are displayed in provider search page
    Then I verify provider type dropdown has below options
      | Medical |
      | Dental  |
#    3.0 Change to PDP CP-40356
#    3.0 View PCP Results CP-40028
    And I select "<providerType>" option from provider type dropdown
    And I clear and fill provider search form with data
      | streetAddress | <streeAddress> |
      | city          | Washington DC  |
      | zip           | <zipCode>      |
      | distance      | 10             |
    And I click on search button
#    4.0 View PDP Results, CP-40356
#    3.0 View PCP Results, CP-40028
    Then I verify provider search results table fields with data
      | PROVIDER NAME    | is displayed    |
      | PROVIDER ADDRESS | is displayed    |
      | LANGUAGE         | is displayed    |
      | SPECIALTY        | is displayed    |
      | LAST UPDATED     | is displayed    |
      | CHECKMARK        | is displayed    |
      | DISTANCE         | ascending order |
#    5.0 Select PCP Only, CP-40028
#    8.0 Select PDP, CP-40356
    Then Save First Provider name on enrollment update page
    And I click the first checkmark button
    Then I verify the following fields under Selected Providers will be displayed: "Provider Name", "Provider Type", "NPI"
#    6.0 Search PCP (Default Distance 10 miles), CP-40356
#    5.0 Search PDP (Default Distance 10 miles), CP-40028
    And I click on Search PCP and PDP button on Plan Option
#    We can't verify the Provider address details value because sometimes it's comes and sometimes it's not come I will verify manually
    Then I verify fields on provider search page with data
#      | STREET ADDRESS      | 22 main st            |
#      | CITY                | Washington DC         |
#      | ZIP                 | 20510                 |
#      | DISTANCE (mi)       | 10                    |
      | PROVIDER TYPE | <defaultProviderType> |
    Then I verify search and cancel buttons are displayed in provider search page
    And I clear and fill provider search form with data
      | streetAddress | <providerAddress> |
      | city          | Washington DC     |
      | zip           | <zip>             |
      | distance      | 10                |
    And I click on search button
#    6.0 View PDP, CP-40028
#    7.0 View PCP, CP-40356
    Then I verify provider search results table fields with data
      | PROVIDER NAME    | is displayed    |
      | PROVIDER ADDRESS | is displayed    |
      | LANGUAGE         | is displayed    |
      | SPECIALTY        | is displayed    |
      | LAST UPDATED     | is displayed    |
      | CHECKMARK        | is displayed    |
      | DISTANCE         | ascending order |
#    5.0 Select PDP, CP-40028
#    8.0 Select PCP, CP-40356
    Then Save First Provider name on enrollment update page
    And I click the first checkmark button
    Then I verify the following fields value under Selected Providers will be displayed
      | PROVIDER NAME | is displayed |
      | PROVIDER TYPE | is displayed |
      | NPI           | is displayed |
#    8.0 Select Channel, CP-40028
#    9.0 Select Channel, CP-40356
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
#    9.0 Submit Selected Plan and Provider(s), CP-40028
#    10.0 Submit Selected Plan and Provider(s), CP-40356
    And I verify program & benefit info page for consumer first name "<consumerId>.firstName" and last name "<consumerId>.lastName" with data
      | CURRENT ENROLLMENT.SELECTION STATUS | Disenroll Requested          |
      | CURRENT ENROLLMENT.START DATE       | 1stDayofPresentMonthUIver    |
#      | CURRENT ENROLLMENT.END DATE         | lastDayofNextMonthUIver      |
      | CURRENT ENROLLMENT.END DATE         | dayBeforeCutOffDateDCHFUIver |
      | CURRENT ENROLLMENT.CHANNEL          | Phone                        |
      | CURRENT ENROLLMENT.PLAN NAME        | AMERIHEALTH CARITAS DC       |
      | CURRENT ENROLLMENT.PCP NAME         | -- --                        |
      | CURRENT ENROLLMENT.PDP NAME         | -- --                        |
      | FUTURE ENROLLMENT.SELECTION STATUS  | Selection Made               |
#      | FUTURE ENROLLMENT.START DATE        | 1stDayof2MonthsFromNowUIver  |
      | FUTURE ENROLLMENT.START DATE        | cutOffDateDCHFUIver          |
      | FUTURE ENROLLMENT.END DATE          | highDateUIver-DC             |
      | FUTURE ENROLLMENT.CHANNEL           | Phone                        |
      | FUTURE ENROLLMENT.PLAN NAME         | MEDSTAR FAMILY CHOICE        |
      | FUTURE ENROLLMENT.PCP NAME          | Not Null                     |
      | FUTURE ENROLLMENT.PDP NAME          | Not Null                     |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT   |
      | consumerId    | <consumerId>.consumerId |
      | consumerFound | true                    |
      | DPBI          | [blank]                 |
    Examples:
      | consumerId | providerType | defaultProviderType | zip   | zipCode | streeAddress     | providerAddress  |
      | 40356-01   | D            | MEDICAL             | 20017 | 20010   | 106 Irving St NW | ABC10 Address    |
      | 40356-02   | MEDICAL      | D                   | 20010 | 20017   | ABC10 Address    | 106 Irving St NW |

  @CP-40824 @CP-40824-01 @CP-40029 @CP-40029-01 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario Outline: Verify Start with Provider: Select PDP Then PCP or PCP then PDP
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <consumerId> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | yes                     |
      | otherSegments                | [blank]                 |
      | isEnrollemntRequired         | no                      |
      | recordId                     | 25                      |
      | eligibilityRecordId          | 25                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofLastMonth       |
      | eligibilityStartDate         | 1stDayofLastMonth       |
      | eligibilityEndDate           | highDate-DC             |
      | txnStatus                    | Accepted                |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | DCHF                    |
      | coverageCode                 | 130                     |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Then I send API call with name "<consumerId>a" for update Enrollment information
      | [0].consumerId         | <consumerId>.consumerId |
      | [0].planId             | 145                     |
      | [0].planCode           | 081080400               |
#      | [0].startDate          | fdnxtmth::          |
      | [0].startDate          | fdofmnth::              |
      | [0].endDate            | highdatedc::            |
      | [0].programTypeCode    | MEDICAID                |
      | [0].subProgramTypeCode | DCHF                    |
      | [0].serviceRegionCode  | Northeast               |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I send API call with name "<consumerId>a" for process Outbound Update
      | [0].channel      | SYSTEM_INTEGRATION      |
      | [0].startDate    | fdofmnth::              |
#      | [0].startDate    | fdnxtmth::             |
      | [0].planId       | delete::                |
      | [0].planCode     | 081080400               |
      | [0].consumerId   | <consumerId>.consumerId |
      | [0].enrollmentId | c.data[0].enrollmentId  |
      | [0].status       | SUBMITTED_TO_STATE      |
      | [0].txnStatus    | SUBMITTED_TO_STATE      |
    And Wait for 3 seconds
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | no                      |
      | isEnrollemntRequired         | yes                     |
      | isEnrollmentProviderRequired | no                      |
      | recordId                     | 15                      |
      | enrollmentRecordId           | 15                      |
#      | enrollmentStartDate          | 1stDayofNextMonth   |
      | enrollmentStartDate          | 1stDayofPresentMonth    |
      | enrollmentEndDate            | highDate-DC             |
      | anniversaryDate              | anniversaryDate         |
      | eligibilityStartDate         | [blank]                 |
      | programCode                  | MEDICAID                |
      | planCode                     | 081080400               |
      | planId                       | 145                     |
      | subProgramTypeCode           | DCHF                    |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<consumerId>.firstName",Last Name as "<consumerId>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
#    1.0 Click PCP/PDP SELECT Button, CP-40824, CP-40029
    And I click "pcp pdp select" Button for a consumer
    Then I verify fields on provider search page with data
      | FIRST NAME    | [blank] |
      | LAST NAME     | [blank] |
      | GROUP NAME    | [blank] |
      | PHONE NUMBER  | [blank] |
      | PROVIDER TYPE | MEDICAL |
    Then I verify search and cancel buttons are displayed in provider search page
    Then I verify provider type dropdown has below options
      | Medical |
      | Dental  |
#    1.1 Change Provider Type From Medical to Dental, CP-40824
#    View PCP Search Results, CP-40029
#    2.0 View PDP Search Results, CP-40824
#    2.1 View Selected PCP’s Search Results, 40029
    And I select "<providerType>" option from provider type dropdown
    And I clear and fill provider search form with data
      | First Name | <firstName> |
    And I click on search button
    Then I verify provider search results table fields with data
      | NAME         | descending order                                                                                                                      |
      | ADDRESS      | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon, HANDICAP INDICATOR, ADDITIONAL ADDRESS INDICATOR |
      | PLAN NAME    | is displayed                                                                                                                          |
      | LANGUAGE     | is displayed                                                                                                                          |
      | SPECIALTY    | is displayed                                                                                                                          |
      | LAST UPDATED | is displayed                                                                                                                          |
      | CHECKMARK    | is displayed                                                                                                                          |
#    2.1 View Selected PDP Details, CP-40824
#    2.2 View Selected PCP Details, CP-40029
    Then Save First Provider name on enrollment update page
    Then I click on first found provider and verify Provider Details with data
      | PROVIDER DETAILS.PROVIDER NAME   | is displayed |
      | PROVIDER DETAILS.PROVIDER GENDER | is displayed |
      | PROVIDER DETAILS.NPI             | is displayed |
      | SPECIALITY                       | is displayed |
      | LANGUAGES                        | is displayed |
      | HOSPITAL AFFILIATION             | is displayed |
      | AGE RANGE                        | is displayed |
#    3.0 Selecting a PDP, CP-40824
#    3.0 Selecting a PCP, CP-40029
    And I click the Select Provider button on Provider Details Page
    Then I verify the following fields under Selected Providers will be displayed: "Provider Name", "Provider Type", "NPI"
#    4.0 Select SEARCH PCP Button-Default Distance Search (10 miles), CP-40824, CP-40029
    And I click on Search PCP and PDP button on Plan Option
#    We can't verify the Provider address details value because sometimes it's comes and sometimes it's not come I will verify manually
    Then I verify fields on provider search page with data
#      | STREET ADDRESS      | 22 main st            |
#      | CITY                | Washington DC         |
#      | ZIP                 | 20510                 |
#      | DISTANCE (mi)       | 10                    |
#      | SPECIALTY           |[blank]|
#      | LANGUAGE            | ENGLISH               |
#      | PROVIDER GENDER     |[blank]|
      | AGE RANGE           | [blank]               |
      | PROVIDER TYPE       | <defaultProviderType> |
      | HANDICAP ACCESSIBLE | [blank]               |
    Then I verify search and cancel buttons are displayed in provider search page
    And I clear and fill provider search form with data
      | streetAddress | <streeAddress> |
      | city          | Washington DC  |
      | zip           | <zip>          |
      | distance      | 10             |
    And I click on search button
#    5.0 View PCP Search Results, CP-40824
#    5.1 View PCP Search Results, CP-40824
#    5.0 View PDP Search Results, CP-40029
#    5.1 View Selected PDP’s Search Results, CP-40029
    Then I verify provider search results table fields with data
      | PROVIDER NAME    | is displayed                                                                                                                          |
      | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon, HANDICAP INDICATOR, ADDITIONAL ADDRESS INDICATOR |
      | LANGUAGE         | is displayed                                                                                                                          |
      | SPECIALTY        | is displayed                                                                                                                          |
      | LAST UPDATED     | is displayed                                                                                                                          |
      | CHECKMARK        | is displayed                                                                                                                          |
      | DISTANCE         | ascending order                                                                                                                       |
#    5.2 View Selected PCP Details, CP-40824
#    5.2 View Selected PDP Details, CP-40029
    Then Save First Provider name on enrollment update page
    Then I click on first found provider and verify Provider Details with data
      | PROVIDER DETAILS.PROVIDER NAME   | is displayed |
      | PROVIDER DETAILS.PROVIDER GENDER | is displayed |
      | PROVIDER DETAILS.NPI             | is displayed |
      | SPECIALITY                       | is displayed |
      | LANGUAGES                        | is displayed |
      | HOSPITAL AFFILIATION             | is displayed |
      | AGE RANGE                        | is displayed |
#    6.0 Selecting a PCP, CP-40824
#    6.0 Selecting a PDP, CP-40029
    And I click the Select Provider button on Provider Details Page
    Then I verify the following fields value under Selected Providers will be displayed
      | PROVIDER NAME | is displayed |
      | PROVIDER TYPE | is displayed |
      | NPI           | is displayed |
#    7.0 Select Channel, CP-40824
#    7.0 Select Channel, CP-40029
    And I select a reason from drop down on Enrollment Update page
    And I click submit button on enrollment update
#    8.0 Submit Selected Plan and Provider(s), CP-40824
#    8.0 Submit Selected Provider(s), CP-40029
    And I verify program & benefit info page for consumer first name "<consumerId>.firstName" and last name "<consumerId>.lastName" with data
      | CURRENT ENROLLMENT.SELECTION STATUS | Disenroll Requested          |
      | CURRENT ENROLLMENT.START DATE       | 1stDayofPresentMonthUIver    |
#      | CURRENT ENROLLMENT.END DATE         | lastDayofNextMonthUIver     |
      | CURRENT ENROLLMENT.END DATE         | dayBeforeCutOffDateDCHFUIver |
      | CURRENT ENROLLMENT.CHANNEL          | Phone                        |
      | CURRENT ENROLLMENT.PLAN NAME        | AMERIHEALTH CARITAS DC       |
      | CURRENT ENROLLMENT.PCP NAME         | -- --                        |
      | CURRENT ENROLLMENT.PDP NAME         | -- --                        |
      | FUTURE ENROLLMENT.SELECTION STATUS  | Selection Made               |
#      | FUTURE ENROLLMENT.START DATE        | 1stDayofNextMonthUIver      |
      | FUTURE ENROLLMENT.START DATE        | cutOffDateDCHFUIver          |
      | FUTURE ENROLLMENT.END DATE          | highDateUIver-DC             |
      | FUTURE ENROLLMENT.CHANNEL           | Phone                        |
      | FUTURE ENROLLMENT.PLAN NAME         | MEDSTAR FAMILY CHOICE        |
      | FUTURE ENROLLMENT.PCP NAME          | Not Null                     |
      | FUTURE ENROLLMENT.PDP NAME          | Not Null                     |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT   |
      | consumerId    | <consumerId>.consumerId |
      | consumerFound | true                    |
      | DPBI          | [blank]                 |
    Examples:
      | consumerId | providerType | defaultProviderType | zip   | streeAddress        | firstName |
      | 40824-01   | D            | MEDICAL             | 20001 | 2542 Georgia Ave NW | Mital     |
      | 40824-02   | MEDICAL      | D                   | 20010 | 106 Irving St NW    | Saul      |

  @CP-40824 @CP-40824-02 @CP-40029 @CP-40029-02 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @priyal
  Scenario Outline: Verify Start with Provider: Select PCP Then PDP and Select PDP Then PCP
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <consumerId> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | yes                     |
      | otherSegments                | [blank]                 |
      | isEnrollemntRequired         | no                      |
      | recordId                     | 25                      |
      | eligibilityRecordId          | 25                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofLastMonth       |
      | eligibilityStartDate         | 1stDayofLastMonth       |
      | eligibilityEndDate           | highDate-DC             |
      | txnStatus                    | Accepted                |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | DCHF                    |
      | coverageCode                 | 130                     |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<consumerId>.firstName",Last Name as "<consumerId>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
#    1.0 Click PCP/PDP SELECT Button, CP-40824, CP-40029
    And I click "pcp pdp select" Button for a consumer
    Then I verify fields on provider search page with data
      | FIRST NAME    | [blank] |
      | LAST NAME     | [blank] |
      | GROUP NAME    | [blank] |
      | PHONE NUMBER  | [blank] |
      | PROVIDER TYPE | MEDICAL |
    Then I verify search and cancel buttons are displayed in provider search page
    Then I verify provider type dropdown has below options
      | Medical |
      | Dental  |
#    1.1 Change Provider Type From Medical to Dental, CP-40824
#    View PCP Search Results, CP-40029
#    2.0 View PDP Search Results, CP-40824
#    2.1 View Selected PCP’s Search Results, 40029
    And I select "<providerType>" option from provider type dropdown
    And I clear and fill provider search form with data
      | First Name | <firstName> |
    And I click on search button
    Then I verify provider search results table fields with data
      | NAME         | descending order                                                                                                                      |
      | ADDRESS      | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon, HANDICAP INDICATOR, ADDITIONAL ADDRESS INDICATOR |
      | PLAN NAME    | is displayed                                                                                                                          |
      | LANGUAGE     | is displayed                                                                                                                          |
      | SPECIALTY    | is displayed                                                                                                                          |
      | LAST UPDATED | is displayed                                                                                                                          |
      | CHECKMARK    | is displayed                                                                                                                          |
#    2.1 View Selected PDP Details, CP-40824
#    2.2 View Selected PCP Details, CP-40029
    Then Save First Provider name on enrollment update page
    Then I click on first found provider and verify Provider Details with data
      | PROVIDER DETAILS.PROVIDER NAME   | is displayed |
      | PROVIDER DETAILS.PROVIDER GENDER | is displayed |
      | PROVIDER DETAILS.NPI             | is displayed |
      | SPECIALITY                       | is displayed |
      | LANGUAGES                        | is displayed |
      | HOSPITAL AFFILIATION             | is displayed |
      | AGE RANGE                        | is displayed |
#    3.0 Selecting a PDP, CP-40824
#    3.0 Selecting a PCP, CP-40029
    And I click the Select Provider button on Provider Details Page
    Then I verify the following fields under Selected Providers will be displayed: "Provider Name", "Provider Type", "NPI"
#    4.0 Select SEARCH PCP Button-Default Distance Search (10 miles), CP-40824, CP-40029
    And I click on Search PCP and PDP button on Plan Option
#    We can't verify the Provider address details value because sometimes it's comes and sometimes it's not come I will verify manually
    Then I verify fields on provider search page with data
#      | STREET ADDRESS      | 22 main st            |
#      | CITY                | Washington DC         |
#      | ZIP                 | 20510                 |
#      | DISTANCE (mi)       | 10                    |
#      | SPECIALTY           |[blank]|
#      | LANGUAGE            | ENGLISH               |
#      | PROVIDER GENDER     |[blank]|
      | AGE RANGE           | [blank]               |
      | PROVIDER TYPE       | <defaultProviderType> |
      | HANDICAP ACCESSIBLE | [blank]               |
    Then I verify search and cancel buttons are displayed in provider search page
    And I clear and fill provider search form with data
      | streetAddress | <streeAddress> |
      | city          | Washington DC  |
      | zip           | <zip>          |
      | distance      | 10             |
    And I click on search button
#    5.0 View PCP Search Results, CP-40824
#    5.1 View PCP Search Results, CP-40824
#    5.0 View PDP Search Results, CP-40029
#    5.1 View Selected PDP’s Search Results, CP-40029
    Then I verify provider search results table fields with data
      | PROVIDER NAME    | is displayed                                                                                                                          |
      | PROVIDER ADDRESS | validate STREET ADDRESS, CITY, STATE, ZIP, PHONE NUMBER, Accepting New Patient Icon, HANDICAP INDICATOR, ADDITIONAL ADDRESS INDICATOR |
      | LANGUAGE         | is displayed                                                                                                                          |
      | SPECIALTY        | is displayed                                                                                                                          |
      | LAST UPDATED     | is displayed                                                                                                                          |
      | CHECKMARK        | is displayed                                                                                                                          |
      | DISTANCE         | ascending order                                                                                                                       |
#    5.2 View Selected PCP Details, CP-40824
#    5.2 View Selected PDP Details, CP-40029
    Then Save First Provider name on enrollment update page
    Then I click on first found provider and verify Provider Details with data
      | PROVIDER DETAILS.PROVIDER NAME   | is displayed |
      | PROVIDER DETAILS.PROVIDER GENDER | is displayed |
      | PROVIDER DETAILS.NPI             | is displayed |
      | SPECIALITY                       | is displayed |
      | LANGUAGES                        | is displayed |
      | HOSPITAL AFFILIATION             | is displayed |
      | AGE RANGE                        | is displayed |
#    6.0 Selecting a PCP, CP-40824
#    6.0 Selecting a PDP, CP-40029
    And I click the Select Provider button on Provider Details Page
    And Wait for 5 seconds
    Then I verify the following fields value under Selected Providers will be displayed
      | PROVIDER NAME | is displayed |
      | PROVIDER TYPE | is displayed |
      | NPI           | is displayed |
#    7.0 Select Channel, CP-40824
#    7.0 Select Channel, CP-40029
    And I click submit button on enrollment update
#    8.0 Submit Selected Plan and Provider(s), CP-40824
#    8.0 Submit Selected Provider(s), CP-40029
    And I verify program & benefit info page for consumer first name "<consumerId>.firstName" and last name "<consumerId>.lastName" with data
      | FUTURE ENROLLMENT.SELECTION STATUS | Selection Made        |
      | FUTURE ENROLLMENT.START DATE       | cutOffDateDCHFUIver   |
      | FUTURE ENROLLMENT.END DATE         | highDateUIver-DC      |
      | FUTURE ENROLLMENT.CHANNEL          | Phone                 |
      | FUTURE ENROLLMENT.PLAN NAME        | MEDSTAR FAMILY CHOICE |
      | FUTURE ENROLLMENT.PCP NAME         | Not Null              |
      | FUTURE ENROLLMENT.PDP NAME         | Not Null              |
    Then I will verify business events are generated with data
      | eventName     | ENROLLMENT_SAVE_EVENT   |
      | consumerId    | <consumerId>.consumerId |
      | consumerFound | true                    |
      | DPBI          | [blank]                 |
    Examples:
      | consumerId | providerType | defaultProviderType | zip   | streeAddress        | firstName |
      | 40824-03   | D            | MEDICAL             | 20001 | 2542 Georgia Ave NW | Mital     |
      | 40824-04   | MEDICAL      | D                   | 20010 | 106 Irving St NW    | Saul      |

  @CP-40075 @CP-40075-02 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @deepa
  Scenario Outline: Verify Start with Provider: Select PCP Then PDP and Select PDP Then PCP
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <consumerId> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | yes                     |
      | otherSegments                | [blank]                 |
      | isEnrollemntRequired         | no                      |
      | recordId                     | 25                      |
      | eligibilityRecordId          | 25                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofLastMonth       |
      | eligibilityStartDate         | 1stDayofLastMonth       |
      | eligibilityEndDate           | highDate-DC             |
      | txnStatus                    | Accepted                |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | DCHF                    |
      | coverageCode                 | 130                     |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<consumerId>.firstName",Last Name as "<consumerId>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "pcp pdp select" Button for a consumer
    When I fill out fields on provider search page with data
      | FIRST NAME | Deepa |
      | LAST NAME  | Deep  |
    And I click on search button
    Then I verify provider search results table fields with data
      | EPSDT                | is displayed |
      | HOSPITAL AFFILIATION | is displayed |
    Examples:
      | consumerId |
      | 40075-02   |

  @CP-40026 @CP-40026-1 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @deepa
  Scenario Outline: Return INDIVIDUAL or GROUP Medical Provider as PCP in UI and Return INDIVIDUAL or GROUP Dental Provider as PDP in UI
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | <consumerId> |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | <consumerId>.consumerId |
      | isEligibilityRequired        | yes                     |
      | otherSegments                | [blank]                 |
      | isEnrollemntRequired         | no                      |
      | recordId                     | 25                      |
      | eligibilityRecordId          | 25                      |
      | isEnrollmentProviderRequired | no                      |
      | enrollmentStartDate          | 1stDayofLastMonth       |
      | eligibilityStartDate         | 1stDayofLastMonth       |
      | eligibilityEndDate           | highDate-DC             |
      | txnStatus                    | Accepted                |
      | programCode                  | MEDICAID                |
      | subProgramTypeCode           | DCHF                    |
      | coverageCode                 | 130                     |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "<consumerId>.firstName",Last Name as "<consumerId>.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I click "pcp pdp select" Button for a consumer
    Then I verify provider type dropdown has below options
      | Medical |
      | Dental  |
    And Wait for 3 seconds
    And I select "<providerType>" option from provider type dropdown
    When I fill out fields on provider search page with data
      | GROUP NAME | <groupname> |
    And I click on search button
    Then I verify provider search results table fields with data
      | NAME | <groupname> |
    Examples:
      | consumerId | providerType | groupname                |
      | 40026-1    | MEDICAL      | WOMENS HEALTH NEW CENTER |
      | 40026-2    | D            | THERAN MEDICAL GROUP LLC |
      | 40026-3    | D            | Family Care Center DC    |

  @CP-43192 @CP-43192-1 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @deepa
  Scenario Outline: UI Update to Display Sub-Program in Start with Plan
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 43192 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 43192.consumerId     |
      | isEligibilityRequired        | yes                  |
      | otherSegments                | [blank]              |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | eligibilityRecordId          | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | coverageCode                 | <coverageCode>       |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "43192.firstName",Last Name as "43192.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "ENROLL" button is displayed
    And I click "Enroll" Button for a consumer
    And I select "MEDSTAR FAMILY CHOICE" from Available Plans
    And I click on Search PCP and PDP button on Plan Option
    And I clear and fill provider search form with data
      | zip      | <zipCode> |
      | distance | 10        |
    And I click on search button
    Then I verify provider search results table fields with data
      | PROVIDER NAME    | Danielle F salaraz |
      | PROVIDER ADDRESS | <address>          |
    Examples:
      | subProgramTypeCode | coverageCode | zipCode | address                                                           |
      | DCHF               | 130          | 20007   | 3800 Reservoir Road 100, Washington DC - 20007 - call202 111 1111 |
      | Alliance           | 470          | 20010   | 110 Irving St NW, 200, Washington DC - 20010 - PH 202 111 1111    |

  @CP-43192 @CP-43192-2 @ui-ee-dc @crm-regression @DC-EB-UI-Regression @deepa
  Scenario Outline: UI Update to Display Sub-Program in Start with Provider
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Given I created a consumer with population type "DC-EB-CONSUMER-below60" with data
      | saveConsumerInfo | 43192 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 43192.consumerId     |
      | isEligibilityRequired        | yes                  |
      | otherSegments                | [blank]              |
      | isEnrollemntRequired         | no                   |
      | recordId                     | 25                   |
      | eligibilityRecordId          | 25                   |
      | isEnrollmentProviderRequired | no                   |
      | enrollmentStartDate          | 1stDayofLastMonth    |
      | eligibilityStartDate         | 1stDayofLastMonth    |
      | eligibilityEndDate           | highDate-DC          |
      | txnStatus                    | Accepted             |
      | programCode                  | MEDICAID             |
      | subProgramTypeCode           | <subProgramTypeCode> |
      | coverageCode                 | <coverageCode>       |
    And I run create Eligibility and Enrollment API
    And Wait for 3 seconds
    Given I logged into CRM with "Service Account 1" and select a project "DC-EB"
    And I minimize Genesys popup if populates
    And I navigate to Manual Consumer search page
    And I search for customer with First Name as "43192.firstName",Last Name as "43192.lastName" in name
    And I click the first consumer id from the search results
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "ENROLL" button is displayed
    And I click "Enroll" Button for a consumer
    And I click on search provider
    And I fill provider search form with data
      | First Name | Danielle |
      | Last Name  | salaraz  |
    And I click on search button
    Then I verify provider search results table fields with data
      | PROVIDER NAME    | Danielle F salaraz |
      | PROVIDER ADDRESS | <address>          |
    Examples:
      | subProgramTypeCode | coverageCode | address                                                           |
      | DCHF               | 130          | 3800 Reservoir Road 100, Washington DC - 20007 - call202 111 1111 |
      | Alliance           | 470          | 110 Irving St NW, 200, Washington DC - 20010 - PH 202 111 1111    |