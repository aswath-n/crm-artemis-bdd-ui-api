Feature: Case/Consumer Alert Upload

  @CP-33885 @API-CP-28719 @API-CP-28265 @API-CP-28266 @API-CP-28265-01 @API-CP-28266-01 @API-CP-28668 @API-CP-28668-01 @API-CC @API-CRM-Regression @muhabbat
  Scenario: Upload Alert Excel file
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert services API request for upload file
    And I run Case Consumer Alert API with "" file format
    Then I validate Alert Response for successful upload

  @API-CP-28719 @API-CP-28265 @API-CP-28265-02 @CP-31216 @CP-31122 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Upload Alert other than excel format file
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert services API request for upload file
    And I run Case Consumer Alert API with "<fileName>" file format
    Then Alert upload Error in response is returned with message "<message>"
    Examples:
      | fileName            | message              |
      | unexpectedFormatDoc | File must be a .xlsx |
      | unexpectedFormatTxt | File must be a .xlsx |

  @CP-27128 @CP-27128-01 @API-CP-28719 @API-CP-27128 @API-CP-27128-01 @API-CP-28669 @API-CP-28669-01 @API-CP-28265 @API-CP-28265-03 @API-CP-28668 @API-CP-28668-02 @CP-31216 @CP-31122 @CP-31216-1 @CP-31122-1 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Upload Alert file - missing column name validation
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert services API request for upload file
    And I run Case Consumer Alert API with "<fileName>" file format
    Then Alert upload Error in response is returned with message "<message>"
    Examples:
      | fileName                  | message                                                                                                                                                              |
      | missingAlertIdColumn      | File must include the following columns: A: Alert Id, B: Case Type, C: Case Id, D: Consumer Type, E: Consumer Id, F: Alert Text, G: Start Date, H: End Date, I: Type |
      | missingCaseIdColumn       | File must include the following columns: A: Alert Id, B: Case Type, C: Case Id, D: Consumer Type, E: Consumer Id, F: Alert Text, G: Start Date, H: End Date, I: Type |
      | missingConsumerIdColumn   | File must include the following columns: A: Alert Id, B: Case Type, C: Case Id, D: Consumer Type, E: Consumer Id, F: Alert Text, G: Start Date, H: End Date, I: Type |
      | missingAlertTextColumn    | File must include the following columns: A: Alert Id, B: Case Type, C: Case Id, D: Consumer Type, E: Consumer Id, F: Alert Text, G: Start Date, H: End Date, I: Type |
      | missingStartDateColumn    | File must include the following columns: A: Alert Id, B: Case Type, C: Case Id, D: Consumer Type, E: Consumer Id, F: Alert Text, G: Start Date, H: End Date, I: Type |
      | missingEndDateColumn      | File must include the following columns: A: Alert Id, B: Case Type, C: Case Id, D: Consumer Type, E: Consumer Id, F: Alert Text, G: Start Date, H: End Date, I: Type |
      | missingTypeColumn         | File must include the following columns: A: Alert Id, B: Case Type, C: Case Id, D: Consumer Type, E: Consumer Id, F: Alert Text, G: Start Date, H: End Date, I: Type |
      | missingCaseTypeColumn     | File must include the following columns: A: Alert Id, B: Case Type, C: Case Id, D: Consumer Type, E: Consumer Id, F: Alert Text, G: Start Date, H: End Date, I: Type |
      | missingConsumerTypeColumn | File must include the following columns: A: Alert Id, B: Case Type, C: Case Id, D: Consumer Type, E: Consumer Id, F: Alert Text, G: Start Date, H: End Date, I: Type |

  @API-CP-28719 @API-CP-28265 @API-CP-28265-04 @CP-31216 @CP-31122 @CP-31216-2 @CP-31122-2 @API-CC @API-CRM-Regression @muhabbat
  Scenario: Upload Alert file - unique file name validation
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert services API request for upload file
    And I run Case Consumer Alert API with "duplicateName" file format
    Then Alert upload Error in response is returned with message "File already uploaded. File Name must be unique."

  @CP-27128 @CP-27128-02 @API-CP-28719 @API-CP-27128 @API-CP-27128-01 @API-CP-28669 @API-CP-28669-02 @API-CP-28265 @API-CP-28265-05 @API-CP-28668 @API-CP-28668-03 @CP-31216 @CP-31122 @CP-31216-3 @CP-31122-3 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Upload Alert file - required fields population validation
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert services API request for upload file
    And I run Case Consumer Alert API with "<fileName>" file format
    Then Alert upload Error in response is returned with message "<message>"
    Examples:
      | fileName                    | message                                                                                                               |
      | missingFieldsCaseConsIds    | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |
      | missingFieldAlertText       | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |
      | missingFieldStartDate       | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |
      | missingFieldType            | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |
      | missingFieldCaseTypeNew     | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |
      | missingFieldConsumerTypeNew | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |

  @CP-27128 @CP-27128-03 @CP-33885 @API-CP-28719 @API-CP-27128 @API-CP-27128-01 @API-CP-28669 @API-CP-28669-03 @API-CP-28266 @API-CP-28266-02 @CP-31216 @CP-31122 @CP-31216-4 @CP-31122-4 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Upload Alert file - validations against field format in each column
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert services API request for upload file
    And I run Case Consumer Alert API with "<fileName>" file format
    Then Alert upload Error in response is returned with message "<message>"
    Examples:
      | fileName             | message                                                                                                               |
      | notNumericAlertId    | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |
      | notNumericCaseId     | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |
      | notNumericConsumerId | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |
      | notFormatedStartDate | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |
      | notFormatedEndDate   | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |
      | 1001AlertText        | Some Alerts failed to process. Please download the Failure file in your Upload History grid to review and take action |

  @CP-29752 @CP-29752-01 @crm-regression @chopa
  Scenario: Verify download indicator for the Alert Success column is disabled
    Given I logged into CRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Case Consumer Alert API request
    And I run Case Consumer Alert API with "missingFieldType" file format
    When I am navigated to Case Alert Upload screen after clicking on Alert tab
    Then I verify download indicator for the Alert Success column is disabled

  @CP-29752 @CP-29752-02 @crm-regression @chopa
  Scenario: Verify download indicator for the Alert Failure column is disabled
    Given I logged into CRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated Case Consumer Alert API request
    And I run Case Consumer Alert API with "" file format
    When I am navigated to Case Alert Upload screen after clicking on Alert tab
    Then I verify download indicator for the Alert Failure column is disabled

  @API-@CP-33595 @API-@CP-33595-1 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify POST app/crm/filetransaction list of alertFile Transaction
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert filetransaction API
    And I run filetransaction API with payload
      | uploadedBy          |[blank]|
      | uploadedOnStartDate |[blank]|
      | uploadedOnEndDate   |[blank]|
    Then I validate response contains following data
      | status | success |

  @API-@CP-33595 @API-@CP-33595-3 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify uploadedBy filetransaction contains in response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert filetransaction API
    And I run filetransaction API with payload
      | uploadedBy          | 3455 |
      | uploadedOnStartDate |[blank]|
      | uploadedOnEndDate   |[blank]|
    Then I validate response contains following data
      | status     | success |
      | uploadedBy | 3455    |

  @API-@CP-33595 @API-@CP-33595-2 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify alertFileTransactionId is sorted by descending order
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert filetransaction API
    And I run filetransaction API with payload
      | uploadedBy          |[blank]|
      | uploadedOnStartDate |[blank]|
      | uploadedOnEndDate   |[blank]|
    Then I validate alertFileTransactionId is sorted by descending order

  @API-@CP-33690 @API-@CP-33690-1 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify records contains in response
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert records API
    And I run records API with payload
      | alertId      | 1 |
      | caseId       |[blank]|
      | caseType     |[blank]|
      | consumerId   |[blank]|
      | consumerType |[blank]|
    Then I validate response contains following data
      | status  | success |
      | alertId | 1       |

  @API-@CP-33690 @API-@CP-33690-2 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify records contains in response consumerId and consumerType
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert records API
    And I run records API with payload
      | alertId      |[blank]|
      | caseId       |[blank]|
      | caseType     |[blank]|
      | consumerId   | x558621  |
      | consumerType | Medicaid |
    Then I validate response contains following data
      | status       | success  |
      | consumerId   | x558621  |
      | consumerType | Medicaid |

  @API-@CP-33690 @API-@CP-33690-3 @API-CC @API-CRM-Regression @Beka
  Scenario Outline: Verify records
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert records API
    And I run records API with payload
      | alertId      |[blank]|
      | caseId       | <caseORconsumer Id>      |
      | caseType     | <CaseORconsumer id Type> |
      | consumerId   |[blank]|
      | consumerType |[blank]|
    Then I validate response contains following data
      | status   | success                  |
      | caseId   | <caseORconsumer Id>      |
      | caseType | <CaseORconsumer id Type> |
    Examples:
      | CaseORconsumer id Type | caseORconsumer Id |
      | Medicaid               | 346364            |
      | CHIP                   | 45678234          |


  @API-@CP-25831 @API-@CP-25831-1 @API-CC @API-CRM-Regression @Beka
  Scenario: Upload Case/Consumer Alert Template
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated alerts API
    When I update existing alert using following data
      | alertId            | 18           |
      | alertText          | dsgasdhasdhg |
      | type               | ACTIONABLE   |
      | effectiveStartDate | 2021-01-01   |
      | effectiveEndDate   | 2024-01-01   |
      | consumerId         | 130390       |
      | consumerIdType     | internal     |
      | caseId             | 70755        |
      | caseIdType         | internal     |
    When I initiated alert records API
    And I run records API with payload
      | alertId      | 18 |
      | caseId       |[blank]|
      | caseType     |[blank]|
      | consumerId   |[blank]|
      | consumerType |[blank]|
    Then I verify alert record updated with following data
      | alertId            | 18           |
      | alertText          | dsgasdhasdhg |
      | effectiveStartDate | 2021-01-01   |
      | effectiveEndDate   | 2024-01-01   |

  @API-@CP-33372 @API-@CP-33372-1 @API-CC @API-CRM-Regression @Beka
  Scenario: Verify records API is returning all alerts related to consumerID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated alert records API
    And I run records API with payload
      | alertId      |[blank]|
      | caseId       |[blank]|
      | caseType     |[blank]|
      | consumerId   | 19564    |
      | consumerType | internal |
    Then I validate response contains following data
      | status       | success  |
      | consumerId   | 19564    |
      | consumerType | internal |
