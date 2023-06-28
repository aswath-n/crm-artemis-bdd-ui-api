Feature:834 File Processing - Staging for Inbound

  @ETL-CP-35264 @ETL-CP-37685 @ETL-CP-38510 @ETL-CP-47398-1.0 @ETL-EE-DC @DC-EB-ETL-Regression @turcan
  Scenario: 834 File Staging Validation
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "35264-1a" with data
      | SubscriberNumber         | null                        |
      | MemberMedicateID         | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-2a" with data
      | SubscriberNumber         | 1234567                     |
      | MemberMedicateID         | 1234567                     |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-3a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | SourceCode               | null                        |
    Given I create new 834 file line by name "35264-4a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | C000                        |
      | SourceCode               | X                           |
    Given I create new 834 file line by name "35264-5a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | MonthCode                | null                        |
    Given I create new 834 file line by name "35264-6a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | MonthCode                | T                           |
    Given I create new 834 file line by name "35264-7a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | B                           |
    Given I create new 834 file line by name "35264-8a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 111                         |
    Given I create new 834 file line by name "35264-9a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | CaseNumber               | null                        |
    Given I create new 834 file line by name "35264-10a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | TransactionType          | T                           |
    Given I create new 834 file line by name "35264-11a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | TransactionType          | null                        |
    Given I create new 834 file line by name "35264-12a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ProgramCode              | null                        |
    Given I create new 834 file line by name "35264-13a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ProgramCode              | 111Q                        |
    Given I create new 834 file line by name "35264-14a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | XX                          |
    Given I create new 834 file line by name "35264-15a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | PriorIdentifierNumber    | 1w345y7q                    |
    Given I create new 834 file line by name "35264-16a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | InvalidStartDateETLver      |
    Given I create new 834 file line by name "35264-17a" with data
      | EligibilityStartDate     | InvalidStartDateETLver      |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-18a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | InvalidStartDateETLver      |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-19a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | InvalidEndDateETLver        |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-20a" with data
      | LastName                 | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-21a" with data
      | FirstName                | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-22a" with data
      | MemberSSN                | 12345678                    |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-23a" with data
      | MemberSSN                | 1Y34X67E9                   |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-24a" with data
      | PhoneNumber              | 123456789                   |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-25a" with data
      | StreetAdress             | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-26a" with data
      | CityName                 | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-27a" with data
      | StateOrProvince          | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-28a" with data
      | ZipCode                  | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-29a" with data
      | ZipCode                  | 1234                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-30a" with data
      | MemberQuadrant           | NS                          |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-31a" with data
      | MemberBirthDate          | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-32a" with data
      | MemberBirthDate          | 20211318                    |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-33a" with data
      | GenderCode               | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-34a" with data
      | GenderCode               | X                           |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-35a" with data
      | LanguageCode             | XXX                         |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create new 834 file line by name "35264-36a" with data
      | EligibilityStartDate     | null                        |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create footer for 834 file with data
      | NumberOfISegment | 3 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 35264-1a  |
      | 35264-2a  |
      | 35264-3a  |
      | 35264-4a  |
      | 35264-5a  |
      | 35264-6a  |
      | 35264-7a  |
      | 35264-8a  |
      | 35264-9a  |
      | 35264-10a |
      | 35264-11a |
      | 35264-12a |
      | 35264-13a |
      | 35264-14a |
      | 35264-15a |
      | 35264-16a |
      | 35264-17a |
      | 35264-18a |
      | 35264-19a |
      | 35264-20a |
      | 35264-21a |
      | 35264-22a |
      | 35264-23a |
      | 35264-24a |
      | 35264-25a |
      | 35264-26a |
      | 35264-27a |
      | 35264-28a |
      | 35264-29a |
      | 35264-30a |
      | 35264-31a |
      | 35264-32a |
      | 35264-33a |
      | 35264-34a |
      | 35264-35a |
      | 35264-36a |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate error messages on last ETL DCEB staging table in database with data
      | 35264-1a  | Medicaid ID is required                                                                                   |
      | 35264-2a  | Medicaid ID must be numeric and 8 characters in length                                                    |
      | 35264-3a  | Enrollment Source is required                                                                             |
      | 35264-4a  | Source Code must be a valid value                                                                         |
      | 35264-5a  | MON is Required                                                                                           |
      | 35264-6a  | Month Code must be a valid value                                                                          |
      | 35264-7a  | Action Code must be a valid value                                                                         |
      | 35264-8a  | Error Code must be a valid value                                                                          |
      | 35264-9a  | Case Number is required                                                                                   |
      | 35264-10a | Invalid Transaction Type code                                                                             |
      | 35264-11a | Transaction Type is required                                                                              |
      | 35264-12a | Medicaid Recipient Program Code Required                                                                  |
      | 35264-13a | Medicaid Recipient Program Code must be a valid value                                                     |
      | 35264-14a | Medicaid HMO Disenrollment Reason Code must be a valid value                                              |
      | 35264-15a | Previous Medicaid ID must be numeric and 8 characters in length                                           |
      | 35264-16a | Text '229X9X9X' could not be parsed at index 0 position 177                                               |
      | 35264-17a | Text '229X9X9X' could not be parsed at index 0 position 237                                               |
      | 35264-18a | Text '229X9X9X' could not be parsed at index 0 position 197                                               |
      | 35264-19a | Text '229X9X9X' could not be parsed at index 0 position 217                                               |
      | 35264-20a | Member Last Name is Required                                                                              |
      | 35264-21a | Member First Name is Required                                                                             |
      | 35264-22a | SSN must be numeric and 9 characters in length                                                            |
      | 35264-23a | SSN must be numeric and 9 characters in length                                                            |
      | 35264-24a | Phone Number must be numeric and 10 characters in length                                                  |
      | 35264-25a | Address Line is required                                                                                  |
      | 35264-26a | City is required                                                                                          |
      | 35264-27a | State Code required                                                                                       |
      | 35264-28a | Zip Code must be numeric and 5 or 9 characters in length                                                  |
      | 35264-29a | Zip Code must be numeric and 5 or 9 characters in length                                                  |
      | 35264-30a | Quadrant Code must be a valid value                                                                       |
      | 35264-31a | Birth Date is required                                                                                    |
      | 35264-32a | Text '20211318' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 13 position 388 |
      | 35264-33a | Sex Code is required                                                                                      |
      | 35264-34a | Sex Code must be a valid value                                                                            |
      | 35264-35a | Invalid Language code                                                                                     |
      | 35264-36a | Text '        ' could not be parsed at index 0 position 237                                               |


  @ETL-CP-39028 @ETL-EE-DC @DC-EB-ETL-Regression @elshan
  Scenario: Additional consumer changes
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "39028-1a" with data
      | FirstName                | John                        |
      | LastName                 | Hugo                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
    Given I create footer for 834 file with data
      | NumberOfISegment | 3 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 39028-1a |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate the consumer payload with firstName and lastName
      | FirstName | John |
      | LastName  | Hugo |


  @ETL-CP-39787 @ETL-EE-DC @DC-EB-ETL-Regression @elshan
  Scenario: ETL Payload shall need to include active eligibility flag when processing DE or Recon for DCEB
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "39787-1a" with data
      | EligibilityStartDate     | yesterdayETLver |
      | EligibilityEndDate       | highDateETLver  |
      | PlanEnrollmentStartDate  | yesterdayETLver |
      | PlanEnrollmentEndDate    | highDateETLver  |
      | MaintenanceEffectiveDate | yesterdayETLver |
    Given I create footer for 834 file with data
      | NumberOfISegment | 1 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 39787-1a |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate active eligibility flag has valid value in consumer payload
      | activeEligibilityFlag | true |


  @ETL-CP-39787 @ETL-EE-DC @DC-EB-ETL-Regression @elshan
  Scenario: ETL Payload shall need to include active eligibility flag when processing DE or Recon for DCEB (eligibilityFlag has false value)
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "39787-2a" with data
      | EligibilityStartDate     | 1stDayofNextMonth |
      | EligibilityEndDate       | highDateETLver    |
      | PlanEnrollmentStartDate  | 1stDayofNextMonth |
      | PlanEnrollmentEndDate    | highDateETLver    |
      | MaintenanceEffectiveDate | 1stDayofNextMonth |
    Given I create footer for 834 file with data
      | NumberOfISegment | 1 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 39787-2a |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate error message if eligibility flag is not set to true
      | ErrorMessage | Eligibility End Date must be equal or greater than Start Date |

  @ETL-CP-47398 @ETL-CP-47398-2.0 @ETL-CP-47398-3.0 @ETL-EE-DC @DC-EB-ETL-Regression @turcan
  Scenario: [DCEB] ETL Map Disenrollment Reason From Inbound 834 to the E&E payload - CHANGE
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "47398-1" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | 9N                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-2" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | DR                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-3" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | 1F                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-4" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | 2E                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-5" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | 2J                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-6" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | 3E                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-7" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | LT                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-8" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | RH                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-9" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | EN                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-10" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | 8X                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-11" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | DA                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create new 834 file line by name "47398-12" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | DD                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create footer for 834 file with data
      | NumberOfISegment | 12 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 47398-1  |
      | 47398-2  |
      | 47398-3  |
      | 47398-4  |
      | 47398-5  |
      | 47398-6  |
      | 47398-7  |
      | 47398-8  |
      | 47398-9  |
      | 47398-10 |
      | 47398-11 |
      | 47398-12 |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate eligibility and enrollment payload in database with data
      | 47398-1  | 9N |
      | 47398-2  | DR |
      | 47398-3  | 1F |
      | 47398-4  | 2E |
      | 47398-5  | 2J |
      | 47398-6  | 3E |
      | 47398-7  | LT |
      | 47398-8  | RH |
      | 47398-9  | EN |
      | 47398-10 | 8X |
      | 47398-11 | DA |
      | 47398-12 | DD |

  @ETL-CP-38209 @ETL-CP-38209-1 @DC-EB-ETL-Regression @beka
  Scenario: DCEB ETL Staging Data Mapped to Contact Service Payload
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "38209-1" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | 9N                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create footer for 834 file with data
      | NumberOfISegment | 1 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 38209-1 |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate Contact Payload data

   @ETL-CP-36574 @ETL-CP-36574-1 @DC-EB-ETL-Regression @beka
  Scenario: DCEB  ETL will make synchronous API calls to Consumer V2, Contact and Eligibility and Enrollment micro services
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "36574-1" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | 9N                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create footer for 834 file with data
      | NumberOfISegment | 1 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 36574-1 |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate synchronous API calls to Consumer V2, Contact and Eligibility and Enrollment

  @ETL-CP-35229 @ETL-CP-35229-1 @DC-EB-ETL-Regression @beka
  Scenario: DCEB  ETL Staging Data Mapped to Eligibility and Enrollment Payload
    Given I create header for 834 file with data
      | FileType | 2439999981 |
    Given I create new 834 file line by name "35229-1" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | 9N                          |
      | ErrorCode                | 001                         |
      | TransactionType          | A                           |
    Given I create footer for 834 file with data
      | NumberOfISegment | 1 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 35229-1 |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I will map the Staging data relating to the Eligibility and Enrollment Payload