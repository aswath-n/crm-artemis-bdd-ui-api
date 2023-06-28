Feature: Outbound Correspondence Languages

  @DMS-180 @ui-ecms1 @ECMS-SMOKE @James
  Scenario: Verify list of correspondence languages
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    Then I should see all the correspondence languages properly configured

  @DMS-181-1 @DMS-181 @ECMS-SMOKE @ui-ecms1 @James
  Scenario Outline: Verify that languages are selectable and deselectable for the project
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I edit Outbound Correspondence Settings with "<Field>" to be "<Value>"
    Then I should see the "<Field>" has a "<Value>" value in Outbound Correspondence Settings
    Examples:
      | Field         | Value   |
      | otherLanguage | English |
      | otherLanguage | Spanish |

  @DMS-181-2 @DMS-181 @ui-ecms1 @James
  Scenario: Verify that I can type part of a language and autocomplete feature will give list of suggestions based on what  I type
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I edit Outbound Correspondence Settings with "otherLanguageAuto" to be "English"
    Then I should see the "autoComplete" is displayed in Outbound Correspondence Settings

  @DMS-181-3 @DMS-181 @ui-ecms1 @James
  Scenario Outline: Verify that default language can be specified
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I edit Outbound Correspondence Settings with "<Field>" to be "<Value>"
    Then I should see the "<Field>" has a "<Value>" value in Outbound Correspondence Settings
    Examples:
      | Field           | Value   |
      | defaultLanguage | English |

  @DMS-181-4 @DMS-181 @ui-ecms1 @James
  Scenario: Verify that I can type part of a language and autocomplete feature will give list of suggestions based on what I type
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I edit Outbound Correspondence Settings with "otherLanguageDropdown" to be "Eng"
    Then I should see the "autoComplete" is displayed in Outbound Correspondence Settings

#  @DMS-181-5 @DMS-181 @ui-ecms1 @James
#  Scenario: Verify that I cannot choose a language twice
#    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
#    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
#    When I edit Outbound Correspondence Settings with "defaultLanguage" to be "English"
#    Then I should not see the "English" is displayed in otherLanguage dropdown box

  @DMS-181-6 @DMS-181 @ui-ecms1 @James
  Scenario: Verify that I cannot choose the same language as default language and other language
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I edit Outbound Correspondence Settings with "otherLanguage2" to be "Spanish"
    When I edit Outbound Correspondence Settings with "defaultLanguage" to be "Spanish"
    Then I should see the Outbound Correspondence Settings fail to save with error message "Please select different language as it is already present in Other language"

  @DMS-181-7 @DMS-181 @ui-ecms1 @James
  Scenario: Verify that I can save my selections
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I edit Outbound Correspondence Settings with "vendorCompanyName" to be "random"
    Then I should see the save successful pop up message appear

  @DMS-181-8 @DMS-181 @ui-ecms2 @James
  Scenario: Verify that navigating away from Outbound Correspondence Settings Page will bring up a warning before discarding changes
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    And I edit Outbound Correspondence Settings with "vendorCompanyName" to be "random"
    When I attempt to navigate away from the page
    Then Outbound Correspondence Settings the system warn be before discarding changes

  @CP-19604 @CP-19604-01 @ui-ecms2 @burak
  Scenario Outline: Verify Braille English is shown as one of the Written language options
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Outbound Correspondence Settings Screen of Project:"current"
    When I edit Outbound Correspondence Settings with "otherLanguage" to be "<Language>"
    Then I should see the save successful pop up message appear
    And I click on edit button on Outbound Settings Page
    Then I should see the "otherLanguage" has a "<Language>" value in Outbound Correspondence Settings
    And I remove "<Language>" from Other Language dropdown in OB Correspondence Configuration
    Then I should see the save successful pop up message appear
    Examples:
      | Language        |
      | Braille English |
      | Braille Spanish |

  @CP-19604 @CP-19604-02 @API-ECMS @burak
  Scenario Outline: Verify able to create correspondence with Braille English preferred Language
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I send the custom request Outbound Correspondence to the service with the following values
      | correspondenceDefinitionMMSCode | braille     |
      | CaseID                          | 802         |
      | channelType                     | Mail        |
      | city                            | cedar park  |
      | state                           | TX          |
      | zipcode                         | 78641       |
      | streetAddress                   | test lane 1 |
      | language                        | <Language>  |
    Then I should see a success status in the response
    Examples:
      | Language        |
      | Braille English |
      | Braille Spanish |

  @CP-19604 @CP-19604-03 @API-ECMS @burak
  Scenario: Verify the language Creating a Data Object for Print Vendor- Braille English
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | mailDestination | random active address |
      | writtenLanguage | Braille English       |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | BrailleEng        |
      | language                        | Braille English   |
      | caseId                          | previouslyCreated |
      | writtenLanguage                 | Braille English   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I should see the language used for all notifications that were created is "Braille English" in the letter data that is generated

  @CP-19604 @CP-19604-4 @API-ECMS @burak
  Scenario: Verify the language Creating a Data Object for Print Vendor- Braille Spanish
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence with the following values
      | consumerRole    | Primary Individual    |
      | mailDestination | random active address |
      | writtenLanguage | Braille Spanish       |
    When I have specified the following values in the request for an Outbound Correspondence without recipients in the request
      | correspondenceDefinitionMMSCode | BrailleSpa        |
      | language                        | Braille English   |
      | caseId                          | previouslyCreated |
      | writtenLanguage                 | Braille Spanish   |
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I should see the language used for all notifications that were created is "Braille Spanish" in the letter data that is generated

  @CP-19604 @CP-19604-05 @ui-ecms2 @burak
  Scenario: Verify Braille Spanish is shown as one of the Written language options for Consumer
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I logged into CRM and click on initiate contact
    And I searched case and consumer created by api
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on first consumerID for Primary Individual
    Then I verify written languages configured
      | written_languages                                                        |
      | Braille English,Braille Spanish,English,Other,Russian,Spanish,Vietnamese |

  @CP-19604 @CP-19604-6 @ui-ecms2 @burak
  Scenario: Verify written languages configured for Create On Consumer profile
    Given I logged into CRM
    When I click on initiate contact record
    When I populate Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I verify written languages configured
      | written_languages                                                        |
      | Braille English,Braille Spanish,English,Other,Russian,Spanish,Vietnamese |