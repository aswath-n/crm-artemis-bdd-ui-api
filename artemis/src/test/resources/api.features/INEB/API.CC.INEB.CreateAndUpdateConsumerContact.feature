Feature: INEB Create and update consumer contact info

  @API-CP-15572 @API-CP-15572-01 @API-CC-IN @API-CC @API-CRM-Regression @Beka
  Scenario: Exact Match Found - Do Not Create New Phone
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API request
    And I create a case consumer in INEB with phone number "4435778899" type "Home"
    When I will update phone record for this consumer with "same exact match number and type" "4435778899"
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With first name and Last name
    Then I will verify "new phone record not created for this consumer"

  @API-CP-15572 @API-CP-15572-02 @API-CC-IN @API-CC @API-CRM-Regression @Beka
  Scenario: Create New Phone Information if update request has not exact phone number
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API request
    And I create a case consumer in INEB with phone number "4435778899" type "Home"
    When I will update phone record for this consumer with "same type and different number" "4438987765"
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With first name and Last name
    Then I will verify "new phone record got created and existing phone with same type is end dated "

  @API-CP-15572 @API-CP-15572-03 @API-CC-IN @API-CC @API-CRM-Regression @Beka
  Scenario: Null Contact Info - Do Not Create New Phone
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API request
    And I create a case consumer in INEB with phone number "4435778899" type "Home"
    When I will update phone record for this consumer with "same exact match number and type" "null"
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With first name and Last name
    Then I will verify "existing number not updated to null and no phones record created"

  @API-CP-15572 @API-CP-15572-04 @API-CC-IN @API-CC @API-CRM-Regression @Beka
  Scenario: Verify Record Updated On date captured when phone record is updated
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated Case Loader API request
    And I create a case consumer in INEB with phone number "4435778899" type "Home"
    When I will update "phoneSource" phone record for this consumer
    Then I initiate the Consumer search API
    And I send API call for searching the Consumer With first name and Last name
    Then I will verify "Updated On date is capturet as curent date"
