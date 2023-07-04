Feature: Save/Submit/View for UI Created Applications in BLATS2

  @CP-34008 @CP-34008-01 @crm-regression @ui-ats-blats2 @vinuta
  Scenario: System clears incoming application to Determining from UI when no matching applications found
    Given I logged into CRM with "Service Account 1" and select a project "BLATS2"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | DOB    | random past date |
      | GENDER | Female           |
      | SSN    | Numeric 9        |
    And I choose "CHIP" as program type
    And I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    And I click on Submit button only in Create Application Page
    And Wait for 3 seconds
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    Then I see application Status as "DETERMINING" in the create application page

  @CP-34008 @CP-34008-02 @crm-regression @ui-ats-blats2 @vinuta
  Scenario: System sets incoming application to Targets Unidentified from UI when matching applications found
    Given I logged into CRM with "Service Account 1" and select a project "BLATS2"
    And I minimize Genesys popup if populates
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | GENDER | Female           |
    And I choose "CHIP" as program type
    And I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    And I click on Submit button only in Create Application Page
    And Wait for 3 seconds
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    Then I see application Status as "DETERMINING" in the create application page
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I create duplicate application to land on member matching page
    And I choose "CHIP" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | GENDER | Female           |
    And I click on Save button on Create Application Page
    And I get application id from the Create Application Page
    And I click on Submit button only in Create Application Page
    And Wait for 3 seconds
    And I click on "GO TO MEMBER MATCHING TAB" button in Create Application Page
    And I click on member matching page back arrow to navigate to create application page
    Then I navigate to Application Tracking
    And I see application Status as "TARGETS UNIDENTIFIED" in the application information