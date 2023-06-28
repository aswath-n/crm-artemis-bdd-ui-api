Feature: Wrap Up Time for Various Scenarios

  @CP-12664 @CP-12664-01 @asad @ui-core @crm-regression
  Scenario: User Saves Active Contact (current functionality)
    Given I logged into CRM and click on initiate contact
    When I click End Contact and click "End-Save" after few seconds
    And I will take "traceId" from logs of contact for "wrapUpTime-save"
    And I will get the Authentication token for "BLCRM" in "CRM"
    Then I verify "wrapUpTime" in the Contact Record event

  @CP-12664 @CP-12664-02 @asad @ui-core @crm-regression
  Scenario: User Ends Contact & Cancels Active Contact
    Given I logged into CRM and click on initiate contact
    When I click End Contact and click "End-Cancel" after few seconds
    And I will take "traceId" from logs of contact for "wrapUpTime-cancel"
    And I will get the Authentication token for "BLCRM" in "CRM"
    Then I verify "wrapUpTime" in the Contact Record event

  @CP-12664 @CP-12664-03 @asad @ui-core @crm-regression
  Scenario: User Cancels Active Contact
    Given I logged into CRM and click on initiate contact
    When I click End Contact and click "Cancel" after few seconds
    And I will take "traceId" from logs of contact for "wrapUpTime-cancel"
    And I will get the Authentication token for "BLCRM" in "CRM"
    Then I verify "wrapUpTime-Zero" in the Contact Record event

  @CP-12664 @CP-12664-04 @asad @ui-core @crm-regression
  Scenario: Session Times Out - User Inactivity
    Given I logged into CRM and click on initiate contact
    When I "DoNotClick" End Contact and be inactive for session to time out
    And I will take "traceId" from logs of contact for "wrapUpTime-timeout"
    And I will get the Authentication token for "BLCRM" in "CRM"
    Then I verify "wrapUpTime-Zero" in the Contact Record event

  @CP-12664 @CP-12664-05 @asad @ui-core @crm-regression
  Scenario: Session Times Out After Selecting ‘End Contact’ - User Inactivity
    Given I logged into CRM and click on initiate contact
    When I "Click" End Contact and be inactive for session to time out
    And I will take "traceId" from logs of contact for "wrapUpTime-timeout"
    And I will get the Authentication token for "BLCRM" in "CRM"
    Then I verify "wrapUpTime-timeout" in the Contact Record event