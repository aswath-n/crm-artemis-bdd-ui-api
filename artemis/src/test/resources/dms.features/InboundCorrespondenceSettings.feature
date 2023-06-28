Feature: Inbound Correspondence Settings

  @CP-19460 @CP-19460-1 @ui-ecms2 @James
  Scenario: Verify Web Chat is an option on Inbound Settings Page
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Settings Screen of Project:"current"
    Then I should see "WEB CHAT" is an option on Inbound Correspondence Settings Page

  @CP-19460 @CP-19460-2 @ui-ecms2 @James
  Scenario: Verify Web Chat is found in channel Inbound Search
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    And I click on edit button on Inbound Settings Page
    When I click to "enable" the "WEB CHAT" channel option in Inbound Settings Page
    And I click the Inbound Settings Page Save button
    Then I should see the "WEB CHAT" channel option is "enabled"

  @CP-19460 @CP-19460-3 @ui-ecms2 @James
  Scenario: Verify Web Chat is an option on Outbound Settings Page
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    Then I should see "WEB CHAT" is an option on Outbound Correspondence Settings Page

  @CP-19460 @CP-19460-4 @ui-ecms2 @James
  Scenario: Verify Web Chat is found in channel Outbound Search
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    And I click on edit button on Outbound Settings Page
    When I click to "enable" the "WEB CHAT" channel option in Outbound Settings Page
    And I click the Outbound Settings Page Save button
    Then I should see the "WEB CHAT" channel option is "enabled"

  @CP-18380 @CP-18380-1 @ui-ecms2 @James
  Scenario: Verify BLCRM Inbound Configuration
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Inbound Correspondence Settings Screen of Project:"current"
    And I click on edit button on Inbound Settings Page
    When I click to "enable" the "WEB CHAT" channel option in Inbound Settings Page
    When I click to "enable" the "MAIL" channel option in Inbound Settings Page
    When I click to "enable" the "FAX" channel option in Inbound Settings Page
    When I click to "enable" the "EMAIL" channel option in Inbound Settings Page
    When I click to "enable" the "MOBILE APP" channel option in Inbound Settings Page
    When I click to "enable" the "WEB PORTAL" channel option in Inbound Settings Page
    And I click the Inbound Settings Page Save button
    Then I should see the "WEB CHAT" channel option is "enabled"
    Then I should see the "MAIL" channel option is "enabled"
    Then I should see the "FAX" channel option is "enabled"
    Then I should see the "EMAIL" channel option is "enabled"
    Then I should see the "MOBILE APP" channel option is "enabled"
    Then I should see the "WEB PORTAL" channel option is "enabled"

  @CP-18381 @CP-18381-1 @ui-ecms-nj @James
  Scenario: Verify NJ-SBE Inbound Configuration
    Given I logged into Tenant Manager and set the project context "project" value "SelectNJSBEConfig"
    And I navigate to the Inbound Correspondence Settings Screen of Project:"current"
    And I click on edit button on Inbound Settings Page
    When I click to "enable" the "WEB CHAT" channel option in Inbound Settings Page
    When I click to "enable" the "MAIL" channel option in Inbound Settings Page
    When I click to "enable" the "FAX" channel option in Inbound Settings Page
    When I click to "enable" the "EMAIL" channel option in Inbound Settings Page
    When I click to "enable" the "MOBILE APP" channel option in Inbound Settings Page
    When I click to "enable" the "WEB PORTAL" channel option in Inbound Settings Page
    And I click the Inbound Settings Page Save button
    Then I should see the "WEB CHAT" channel option is "enabled"
    Then I should see the "MAIL" channel option is "enabled"
    Then I should see the "FAX" channel option is "enabled"
    Then I should see the "EMAIL" channel option is "enabled"
    Then I should see the "MOBILE APP" channel option is "enabled"
    Then I should see the "WEB PORTAL" channel option is "enabled"

  @CP-18382 @CP-18382-1 @ui-ecms-coverva @James
  Scenario: Verify COVERVA Inbound Configuration
    Given I logged into Tenant Manager and set the project context "project" value "SelectCOVERVAConfig"
    And I navigate to the Inbound Correspondence Settings Screen of Project:"current"
    And I click on edit button on Inbound Settings Page
    When I click to "enable" the "WEB CHAT" channel option in Inbound Settings Page
    When I click to "enable" the "MAIL" channel option in Inbound Settings Page
    When I click to "enable" the "FAX" channel option in Inbound Settings Page
    When I click to "enable" the "EMAIL" channel option in Inbound Settings Page
    When I click to "enable" the "MOBILE APP" channel option in Inbound Settings Page
    When I click to "enable" the "WEB PORTAL" channel option in Inbound Settings Page
    And I click the Inbound Settings Page Save button
    Then I should see the "WEB CHAT" channel option is "enabled"
    Then I should see the "MAIL" channel option is "enabled"
    Then I should see the "FAX" channel option is "enabled"
    Then I should see the "EMAIL" channel option is "enabled"
    Then I should see the "MOBILE APP" channel option is "enabled"
    Then I should see the "WEB PORTAL" channel option is "enabled"

#  @CP-18395 @CP-18395-1 @ui-ecms2 @James NO LONGER A VALID PROJECT
  Scenario: Verify GF Inbound Configuration
    Given I logged into Tenant Manager and set the project context "project" value "SelectGAProject"
    And I navigate to the Inbound Correspondence Settings Screen of Project:"current"
    And I click on edit button on Inbound Settings Page
    When I click to "enable" the "WEB CHAT" channel option in Inbound Settings Page
    When I click to "enable" the "MAIL" channel option in Inbound Settings Page
    When I click to "enable" the "FAX" channel option in Inbound Settings Page
    When I click to "enable" the "EMAIL" channel option in Inbound Settings Page
    When I click to "enable" the "MOBILE APP" channel option in Inbound Settings Page
    When I click to "enable" the "WEB PORTAL" channel option in Inbound Settings Page
    And I click the Inbound Settings Page Save button
    Then I should see the "WEB CHAT" channel option is "enabled"
    Then I should see the "MAIL" channel option is "enabled"
    Then I should see the "FAX" channel option is "enabled"
    Then I should see the "EMAIL" channel option is "enabled"
    Then I should see the "MOBILE APP" channel option is "enabled"
    Then I should see the "WEB PORTAL" channel option is "enabled"

  @CP-5557 @CP-5557-1 @ui-ecms2 @James
  Scenario: Verify Inbound Settings navigation link
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Settings Screen of Project:"current"
    Then I should see I have navigated to Inbound Inbound Correspondence Settings Screen

  @CP-5557 @CP-5557-2 @ui-ecms2 @James
  Scenario: Verify Inbound Settings labels
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Settings Screen of Project:"current"
    Then I verify the labels in the Inbound Correspondence Settings Screen


  @CP-5557 @CP-5557-3 @ui-ecms2 @James
  Scenario: Verify Inbound Settings can save settings
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    And I click on edit button on Inbound Settings Page
    When I click to "enable" the "WEB CHAT" channel option in Outbound Settings Page
    And I click the Outbound Settings Page Save button
    Then I should see the "WEB CHAT" channel option is "enabled"

  @CP-5557 @CP-5557-4 @ui-ecms2 @James
  Scenario: Verify Inbound Settings can cancel settings
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Inbound Correspondence Settings Screen of Project:"current"
    And I click on edit button on Inbound Settings Page
    When I click to "enable" the "WEB CHAT" channel option in Outbound Settings Page
    And I click the cancel button on the Inbound Settings Page
    Then I should see a warning message allowing me to either discard or cancel changes - DMS

  @CP-5557 @CP-5557-5 @ui-ecms2 @James
  Scenario: Verify Inbound Settings end point
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Inbound Correspondence Settings Screen of Project:"current"
    And I click on edit button on Inbound Settings Page
    When I click to "enable" the "WEB CHAT" channel option in Outbound Settings Page
    And I click the cancel button on the Inbound Settings Page
    Then I should see a warning message allowing me to either discard or cancel changes - DMS

