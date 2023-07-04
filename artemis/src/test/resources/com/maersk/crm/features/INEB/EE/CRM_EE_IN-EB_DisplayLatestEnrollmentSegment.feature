Feature: Display Latest Enrollment Segment with Current Eligibility

  @CP-33276 @CP-33276-01 @ui-ee-in @crm-regression @IN-EB-UI-Regression @mital
  Scenario Outline: Display Prior Enrollment Segment When Eligibility is Current
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Given I created a consumer with population type "IN-EB-CONSUMER" with data
      | saveConsumerInfo | 33276-1 |
    Given I initiated Eligibility and Enrollment Create API
    And I provide the enrollment and eligibility information to create enrollment
      | consumerId                   | 33276-1.consumerId       |
      | isEligibilityRequired        | yes                     |
      | otherSegments                |[blank]|
      | isEnrollemntRequired         | yes                     |
      | recordId                     | 21                      |
      | eligibilityRecordId          | 21                      |
      | isEnrollmentProviderRequired | yes                     |
      | providerNpi                  | 1921987974              |
      | providerFirstName            | Steven                  |
      | providerLastName             | Gerrard                 |
      | providerMiddleName           | M                       |
      | enrollmentStartDate          | <eligibilityStartDate>  |
      | enrollmentEndDate            | yesterday    |
      | eligibilityStartDate         | <eligibilityStartDate>  |
      | eligibilityEndDate           | <eligibilityEndDate>    |
      | selectionReason              | <enrollmentEndReason>   |
      | planEndDateReason            | test                    |
      | txnStatus                    | Accepted                |
      | programCode                  | <programCode>           |
      | subProgramTypeCode           | <subProgramTypeCode>    |
      | eligibilityStatusCode        | <eligibilityStatusCode> |
      | categoryCode                 | <categoryCode>          |
      | coverageCode                 | cc01                    |
      | planCode                     | 455701400               |
    And User provide other enrollment segments details
      | recordId          | 21        |
      | genericFieldText3 | MEDICAL   |
      | genericFieldText1 | O         |
      | genericFieldText2 | A         |
      | segmentTypeCode   | OTHER_TXN |
    And User provide other enrollment segments details
      | recordId          | 21                    |
      | consumerId        | 33276-1.consumerId     |
      | startDate         | 1stDayof2MonthsBefore |
      | endDate           | lastDayofPresentMonth |
      | genericFieldText1 | O                     |
      | genericFieldDate1 | 1stDayofPresentMonth  |
      | segmentTypeCode   | OPEN_ENROLLMENT       |
    And I run create Eligibility and Enrollment API
    Given I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "33276-1.firstName" and Last Name as "33276-1.lastName"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify program & benefit info page for consumer first name "33276-1.firstName" and last name "33276-1.lastName" with data
#     1.0 Prior enrollment segment display
      | PRIOR ENROLLMENT DETAILS.PLAN NAME               | ANTHEM HIP            |
      | PRIOR ENROLLMENT DETAILS.ENROLLMENT END REASON   | <enrollmentEndReason> |
      | PRIOR ENROLLMENT DETAILS.ENROLLMENT START DATE   | <enrollmentStartDate> |
      | PRIOR ENROLLMENT DETAILS.ENROLLMENT END DATE     | <enrollmentEndDate>   |
      | PRIOR ENROLLMENT DETAILS.PLAN CHANGE BUTTON      | HIDDEN             |
      | PRIOR ENROLLMENT DETAILS.DISREGARD BUTTON        | HIDDEN             |
      | PRIOR ENROLLMENT DETAILS.EDIT BUTTON             | HIDDEN             |
    Examples:
      | eligibilityStartDate | eligibilityEndDate      |enrollmentStartDate |enrollmentEndDate|enrollmentEndReason| programCode | subProgramTypeCode | categoryCode | eligibilityStatusCode |
      | 1stDayofPresentMonth | lastDayOfThePresentYear |firstDayofPresntMonth| yesterday      |test               | R           | HoosierHealthwise  | 10           | V                     |

  @CP-43525 @CP-43525-03 @ui-ee-in @crm-regression @burak
  Scenario: Verify Prior Eligibility and Enrollment Details (IN-EB)
    ## Verify able to expand carrots when there is Prior Eligibility and Enrollment
    Given I will get the Authentication token for "IN-EB" in "CRM"
    And I logged into CRM and select a project "IN-EB"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I searched consumer created through api with First Name as "Emanuel" and Last Name as "Volkman"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I expand "PRIOR ELIGIBILITY" section for every consumer
    And I expand "PRIOR ENROLLMENT" section for every consumer
    Then I verify program & benefit info page for consumer first name "Emanuel" and last name "Volkman" with data
#     3.0 Prior Eligibility segment display
      | PRIOR ELIGIBILITY DETAILS.CONSUMER POPULATION    | HHW-Mandatory               |
      | PRIOR ELIGIBILITY DETAILS.ELIGIBILITY START DATE | 03/01/2023                  |
      | PRIOR ELIGIBILITY DETAILS.ELIGIBILITY END DATE   | 03/31/2023                  |
      | PRIOR ELIGIBILITY DETAILS.ELIGIBILITY END REASON | 01 - Approved Changee       |
      | PRIOR ENROLLMENT DETAILS.PLAN NAME               | MANAGED HEALTH SERVICES HCC |
      | PRIOR ENROLLMENT DETAILS.ENROLLMENT END REASON   | Test                        |
      | PRIOR ENROLLMENT DETAILS.ENROLLMENT START DATE   | 03/01/2023                  |
      | PRIOR ENROLLMENT DETAILS.ENROLLMENT END DATE     | 03/31/2023                  |