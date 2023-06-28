Feature: Lookup Library in Mars Enrollment Service

  @API-CP-35902 @API-CP-35902-01 @API-EE @API-CRM-Regression @kursat
  Scenario Outline: Verify BLCRM enum values using lookup library in mars-enrollment-service
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated an updated enum get api for "<tableName>"
    And I run enum get api
    And I verify table "<tableName>" size is <number of rows>
    And I verify table "<tableName>" field "<field>" has "<values>"
    Examples:
      | tableName                   | number of rows | field       | values                                                                                 |
      | ENUM_ENROLLMENT_TYPE        | 3              | value       | BEHAVIORAL, DENTAL, MEDICAL                                                            |
      | ENUM_OVERRIDE_REASON        | 2              | value       | FAMILY_MOVING_TO_SAME_PLAN, GOOD_CAUSE_EXCEPTION                                       |
      | ENUM_PLAN_SELECTION_REASONS | 4              | value       | CONSUMER_CHANGED_MIND, FAMILY_PLAN, PLAN_DOES_NOT_SUPPORT_WANTED_SERVICES, ROUND_ROBIN |
      | ENUM_PLAN_SELECTION_TYPES   | 3              | reportLabel | Change, Disenrollment, New                                                             |


  @API-CP-35902 @API-CP-35902-02 @API-CP-25539 @API-CP-25539-01 @API-EE-IN @IN-EB-API-Regression @kursat @priyal
  Scenario Outline: Verify IN-EB enum values using lookup library in mars-enrollment-service
    Given I will get the Authentication token for "IN-EB" in "CRM"
    When I initiated an updated enum get api for "<tableName>"
    And I run enum get api
    And I verify table "<tableName>" size is <number of rows>
    And I verify table "<tableName>" field "<field>" has "<values>" fields value
    Examples:
      | tableName                   | number of rows | field       | values                                                                                                               |
      | ENUM_ENROLLMENT_TYPE        | 3              | value       | BEHAVIORAL, DENTAL, MEDICAL                                                                                          |
      | ENUM_OVERRIDE_REASON        | 2              | value       | FAMILY_MOVING_TO_SAME_PLAN, GOOD_CAUSE_EXCEPTION                                                                     |
      | ENUM_SUB_PROGRAM_TYPE       | 3              | value       | HealthyIndianaPlan, HoosierCareConnect, HoosierHealthwise                                                            |
      | ENUM_PLAN_SELECTION_TYPES   | 3              | reportLabel | Change, Disenrollment, New                                                                                           |
      | ENUM_PLAN_SELECTION_REASONS | 159            | value       | 01, 02, 03, 2D, 2E, 2F, 2G, 2H, 2I, 2J, 2K, AR, AS, EB, FAMILY_PLAN, FE, HA, HJ, HL, HR, HS, HT, MS, PC, ROUND_ROBIN |
