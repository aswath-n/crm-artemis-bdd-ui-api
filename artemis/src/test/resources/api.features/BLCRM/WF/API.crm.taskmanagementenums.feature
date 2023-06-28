Feature: Task Management Enums Validation

  @API-CP-16346 @API-CP-16346-01 @API-CP-16878 @API-CP-16878-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Action Taken enum values for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify action taken enum values for task type "Review Appeal Form"
    And I verify action taken enum values for task type "Follow-Up on Appeal"
    Examples:
      |projectName|tableName        |serviceName   |
      ||ENUM_ACTION_TAKEN|taskmanagement|

  @API-CP-16346 @API-CP-16346-02 @API-CP-16878 @API-CP-16878-02 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Action Taken enum values for NJ-SBE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify action taken enum values for task type "Review Appeal Form"
    And I verify action taken enum values for task type "NJ-Follow-Up on Appeal"
    And I verify action taken enum values for task type "Review OAL Appeal Decision"
    Examples:
      |projectName|tableName        |serviceName   |
      |NJ-SBE     |ENUM_ACTION_TAKEN|taskmanagement|

  @API-CP-16346 @API-CP-16346-03 @API-CP-16877 @API-CP-16877-01 @CP-28139 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Disposition enum values for NJ-SBE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify disposition enum values for task type "Review Appeal Form"
    And I verify disposition enum values for task type "Follow-Up on Appeal"
    And I verify disposition enum values for task type "GCNJ Resolve Appeal"
    Examples:
      |projectName|tableName        |serviceName   |
      |NJ-SBE     |ENUM_DISPOSITION |taskmanagement|

  @API-CP-15949 @API-CP-15949-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Disposition enum values for NJ-SBE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify disposition enum values for task type "Rejected Response"
    Examples:
      |projectName|tableName        |serviceName   |
      ||ENUM_DISPOSITION |taskmanagement|

  @API-CP-15949 @API-CP-15949-02 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify information type enum values for BLCRM
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify information type enum values for task type "Rejected Response"
    Examples:
      |projectName|tableName            |serviceName   |
      ||ENUM_INFORMATION_TYPE|taskmanagement|

  @API-CP-19350 @API-CP-19350-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Accessibility Needed enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13541"
      |Interpreter|
      |Hearing Impaired|
    Examples:
      |projectName|tableName                |serviceName   |
      |CoverVA    |ENUM_ACCESSIBILITY_NEEDED|taskmanagement|

  @API-CP-19321 @API-CP-19321-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Eligibility Decision enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13542"
      |Approved|
      |Denied|
      |Pending|
    Examples:
      |projectName|tableName                |serviceName   |
      |CoverVA    |ENUM_ELIGIBILITY_DECISION|taskmanagement|

  @API-CP-19319 @API-CP-19319-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Decision Source enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13474"
      | Manual         |
      | VaCMS          |
      | VaCMS + Manual |
    Examples:
      |projectName|tableName           |serviceName   |
      |CoverVA    |ENUM_DECISION_SOURCE|taskmanagement|

  @API-CP-19317 @API-CP-19317-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Outcome enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13551"
      | Final Approval   |
      | Final Denial     |
      | Manual           |
      | Pending Research |
    Examples:
      |projectName|tableName           |serviceName   |
      |CoverVA    |ENUM_OUTCOME        |taskmanagement|

  @API-CP-19317 @API-CP-19317-02 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Outcome enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13552"
      |Approved|
      |Denied|
    Examples:
      |projectName|tableName           |serviceName   |
      |CoverVA    |ENUM_OUTCOME        |taskmanagement|

  @API-CP-19323 @API-CP-19323-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Issue Type enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13521"
      |App or Case assigned to LDSS while pending (CPU Error)|
      |App Sent to Wrong LDSS (CPU Error)|
      |Application/Case Incomplete (CPU Error)|
      |Completed Case not Assigned to LDSS (CPU Error)|
      |Determination Incorrect (CPU Error)            |
      |Forms not uploaded to DMIS (CPU Error)         |
      |LDSS Comm Form Error (CPU Error)               |
      |MMIS Enrollment Incomplete (CPU Error)         |
      |MMIS Enrollment Incorrect (CPU Error)          |
      |Pending case/app not assigned to the locality (CPU Error)|
      |Review Date not updated in VaCMS (CPU Error)             |
      |VaCMS Error by CV (CPU Error)                            |
    Examples:
      |projectName|tableName           |serviceName   |
      |CoverVA    |ENUM_ISSUE_TYPE     |taskmanagement|

  @API-CP-19306 @API-CP-19306-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Returned mail reason enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13517"
      |Address Data Entry Error|
      |Consumer Deceased|
      |Consumer Moved|
      |Expired Fwd Address|
      |Invalid Address|
      |Unknown|
    Examples:
      |projectName|tableName                |serviceName   |
      |CoverVA    |ENUM_RETURNED_MAIL_REASON|taskmanagement|

  @API-CP-19307 @API-CP-19307-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Review outcome enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13494"
      |Error(s) Found|
      |No Error(s) Found|
      |Processing Delay|
      |No Processing Delay|
    Examples:
      |projectName|tableName                |serviceName   |
      |CoverVA    |ENUM_REVIEW_OUTCOME      |taskmanagement|

  @API-CP-19305 @API-CP-19305-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Request Type enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13521"
      |Report case issues|
      |Request to Assign Pending App or Case to LDSS|
      |Request to Assign Pending App or Case to LDSS due to a SNAP app|
      |Update Information on Case or Other info|
    Examples:
      |projectName|tableName           |serviceName   |
      |CoverVA    |ENUM_REQUEST_TYPE   |taskmanagement|

  @API-CP-19300 @API-CP-19300-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Pre Hearing Outcome enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13516"
      |Error(s) Found|
      |No Error(s) Found|
      |Processing Delay|
      |No Processing Delay|
    Examples:
      |projectName|tableName                |serviceName   |
      |CoverVA    |ENUM_PRE_HEARING_OUTCOME |taskmanagement|

  @API-CP-19301 @API-CP-19301-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Pre Hearing Reason enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13516"
      |Untimely Processing|
      |Coverage Type|
      |Coverage Period|
      |Denial - Over Income|
      |Denial - Incomplete/Failure to Provide|
      |Denial - Does Not Meet MAGI Group|
      |Denial - Immigration Status|
      |Denial - Duplicate Application/Coverage|
      |Case Closure - Incomplete CVIU Renewal|
      |Case Closure - No Longer Eligible|
    Examples:
      |projectName|tableName                |serviceName   |
      |CoverVA    |ENUM_PRE_HEARING_REASON  |taskmanagement|

  @API-CP-19322 @API-CP-19322-01 @API-CRM @API-CRM-Regression @API-WF @task-manag-ms-WM @vidya
  Scenario Outline: Verify Information Type enum values for CoverVA
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated enum get api for "<tableName>" and "<serviceName>"
    And I run enum get api
    Then I verify "<tableName>" enum values for task type "13514"
      |Verification Check List (VCL)|
      |Notice of Action (NOA)|
      |Extended Pend Letter|
      |Renewal Packet|
      |Other|
    Examples:
      |projectName|tableName                |serviceName   |
      |CoverVA    |ENUM_INFORMATION_TYPE |taskmanagement|