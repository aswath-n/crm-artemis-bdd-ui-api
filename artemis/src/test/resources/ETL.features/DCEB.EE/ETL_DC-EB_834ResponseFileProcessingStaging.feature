Feature:834 File Response Processing - Validations and Staging the Data

  @ETL-CP-38266 @ETL-EE-DC @DC-EB-ETL-Regression @turcan
  Scenario: 834 Response File Staging Validation
    Given I create header for 834 file with data
      | FileType | PSI.202208 |
    Given I create new 834 file line by name "38266-1a" with data
      | SubscriberNumber         | null                        |
      | MemberMedicateID         | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | TransactionType          | G                           |
      | ActionCode               | E                           |
      | ErrorCode                | 001                         |
    Given I create new 834 file line by name "38266-2a" with data
      | SubscriberNumber         | 1234567                     |
      | MemberMedicateID         | 1234567                     |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | C                           |
    Given I create new 834 file line by name "38266-3a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | SourceCode               | null                        |
      | ErrorCode                | 001                         |
      | TransactionType          | E                           |
    Given I create new 834 file line by name "38266-4a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | E                           |
      | TransactionType          | J                           |
      | SourceCode               | X                           |
      | ErrorCode                | 001                         |
    Given I create new 834 file line by name "38266-5a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | MonthCode                | null                        |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-6a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | MonthCode                | T                           |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-7a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ActionCode               | B                           |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-8a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 111                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-9a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | CaseNumber               | null                        |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-10a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | TransactionType          | T                           |
      | ErrorCode                | 001                         |
    Given I create new 834 file line by name "38266-11a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | TransactionType          | null                        |
      | ErrorCode                | 001                         |
    Given I create new 834 file line by name "38266-12a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ProgramCode              | null                        |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-13a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ProgramCode              | 111Q                        |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-14a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | DisenrollmentReasonCode  | XX                          |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-15a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | PriorIdentifierNumber    | 1w345y7q                    |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-16a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | InvalidStartDateETLver      |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-17a" with data
      | EligibilityStartDate     | InvalidStartDateETLver      |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-18a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | InvalidStartDateETLver      |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-19a" with data
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | InvalidEndDateETLver        |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-20a" with data
      | LastName                 | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-21a" with data
      | FirstName                | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-22a" with data
      | MemberSSN                | 12345678                    |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-23a" with data
      | MemberSSN                | 1Y34X67E9                   |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-24a" with data
      | PhoneNumber              | 123456789                   |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-25a" with data
      | StreetAdress             | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-26a" with data
      | CityName                 | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-27a" with data
      | StateOrProvince          | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-28a" with data
      | ZipCode                  | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-29a" with data
      | ZipCode                  | 1234                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-30a" with data
      | MemberQuadrant           | NS                          |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-31a" with data
      | MemberBirthDate          | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-32a" with data
      | MemberBirthDate          | 20211318                    |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-33a" with data
      | GenderCode               | null                        |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-34a" with data
      | GenderCode               | X                           |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-35a" with data
      | LanguageCode             | XXX                         |
      | EligibilityStartDate     | yesterdayETLver             |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create new 834 file line by name "38266-36a" with data
      | EligibilityStartDate     | null                        |
      | EligibilityEndDate       | lastDayofPresentMonthETLver |
      | PlanEnrollmentStartDate  | yesterdayETLver             |
      | PlanEnrollmentEndDate    | lastDayofPresentMonthETLver |
      | MaintenanceEffectiveDate | yesterdayETLver             |
      | ErrorCode                | 001                         |
      | TransactionType          | G                           |
    Given I create footer for 834 file with data
      | NumberOfISegment | 36 |
    And I create 834 "DC" file and send to S3 bucket "max-etl-dceb-non-prod" S3folder "QE/dailyEligible/input/" with names list with data
      | 38266-1a  |
      | 38266-2a  |
      | 38266-3a  |
      | 38266-4a  |
      | 38266-5a  |
      | 38266-6a  |
      | 38266-7a  |
      | 38266-8a  |
      | 38266-9a  |
      | 38266-10a |
      | 38266-11a |
      | 38266-12a |
      | 38266-13a |
      | 38266-14a |
      | 38266-15a |
      | 38266-16a |
      | 38266-17a |
      | 38266-18a |
      | 38266-19a |
      | 38266-20a |
      | 38266-21a |
      | 38266-22a |
      | 38266-23a |
      | 38266-24a |
      | 38266-25a |
      | 38266-26a |
      | 38266-27a |
      | 38266-28a |
      | 38266-29a |
      | 38266-30a |
      | 38266-31a |
      | 38266-32a |
      | 38266-33a |
      | 38266-34a |
      | 38266-35a |
      | 38266-36a |
    When I run talent job for environment "QE" project "DCEB" job name "DCEB_dailyEligibleJob"
    Then I validate error messages on last ETL DCEB staging table in database with data
      | 38266-1a  | Medicaid ID is required                                                                                   |
      | 38266-2a  | Medicaid ID must be numeric and 8 characters in length                                                    |
      | 38266-3a  | Enrollment Source is required                                                                             |
      | 38266-4a  | Source Code must be a valid value                                                                         |
      | 38266-5a  | MON is Required                                                                                           |
      | 38266-6a  | Month Code must be a valid value                                                                          |
      | 38266-7a  | Action Code must be a valid value                                                                         |
      | 38266-8a  | Error Code must be a valid value                                                                          |
      | 38266-9a  | Case Number is required                                                                                   |
      | 38266-10a | Invalid Transaction Type code                                                                             |
      | 38266-11a | Transaction Type is required                                                                              |
      | 38266-12a | Medicaid Recipient Program Code Required                                                                  |
      | 38266-13a | Medicaid Recipient Program Code must be a valid value                                                     |
      | 38266-14a | Medicaid HMO Disenrollment Reason Code must be a valid value                                              |
      | 38266-15a | Previous Medicaid ID must be numeric and 8 characters in length                                           |
      | 38266-16a | Text '229X9X9X' could not be parsed at index 0 position 177                                               |
      | 38266-17a | Text '229X9X9X' could not be parsed at index 0 position 237                                               |
      | 38266-18a | Text '229X9X9X' could not be parsed at index 0 position 197                                               |
      | 38266-19a | Text '229X9X9X' could not be parsed at index 0 position 217                                               |
      | 38266-20a | Member Last Name is Required                                                                              |
      | 38266-21a | Member First Name is Required                                                                             |
      | 38266-22a | SSN must be numeric and 9 characters in length                                                            |
      | 38266-23a | SSN must be numeric and 9 characters in length                                                            |
      | 38266-24a | Phone Number must be numeric and 10 characters in length                                                  |
      | 38266-25a | Address Line is required                                                                                  |
      | 38266-26a | City is required                                                                                          |
      | 38266-27a | State Code required                                                                                       |
      | 38266-28a | Zip Code must be numeric and 5 or 9 characters in length                                                  |
      | 38266-29a | Zip Code must be numeric and 5 or 9 characters in length                                                  |
      | 38266-30a | Quadrant Code must be a valid value                                                                       |
      | 38266-31a | Birth Date is required                                                                                    |
      | 38266-32a | Text '20211318' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 13 position 388 |
      | 38266-33a | Sex Code is required                                                                                      |
      | 38266-34a | Sex Code must be a valid value                                                                            |
      | 38266-35a | Invalid Language code                                                                                     |
      | 38266-36a | Text '        ' could not be parsed at index 0 position 237                                               |