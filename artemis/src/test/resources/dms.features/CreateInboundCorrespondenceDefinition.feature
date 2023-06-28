Feature: Create Inbound Correspondence Definition

  @CP-3815 @CP-3815-01 @ui-ecms1 @burak
  Scenario: Verify ADD button navigates to the ADD correspondence Screen
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    And I should see the add an Inbound Correspondence Definition button
    And I click add button on Inbound Correspondence Definition
    Then I verify I navigate to Inbound Correspondence Definition Details Screen

  @CP-3815 @CP-3815-02 @ui-ecms1 @burak
  Scenario: Verify the Name is required fields in the Inbound Correspondence Definition Screen
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    When I add a new Inbound Correspondence Definition without a "Name"
    Then I should see a failure message for the required : "Name"

  @CP-3815 @CP-3815-03 @ui-ecms1 @burak
  Scenario Outline: Verify that Barcode and Description fields are optional to add a Inbound Correspondence Definition
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    When I add a new Inbound Correspondence Definition without a "<Field>"
    Then I should the Correspondence Definition was successfully added
    Examples:
      | Field       |
      | Barcode     |
      | Description |

  @CP-3815 @CP-3815-04 @CP-36281 @CP-36281-02 @ui-ecms1 @burak #Fails due to the CP-36700
  Scenario Outline: Verify that Name can accommodate 50 and Description can accommodate 100 characters in the Inbound Correspondence Definition Screen and accepts Special characters
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    And I click add button on Inbound Correspondence Definition
    When I add a "<Field>" to the Inbound Correspondence Definition containing "<Length>" free text characters
    And I fill fields in create Inbound Correspondence screen
    And I click save button on create Inbound Correspondence screen
    #And I click on the back arrow button on Inbound Correspondence details
    Then I should be able to all "<Length>" characters accommodated in the "<Field>" field for Inbound Correspondence Definition
    Examples:
      | Field       | Length |
      | Name        | 50     |
      | Description | 100    |

  @CP-3815 @CP-3815-05 @ui-ecms2 @burak #Fails due to the CP-36700
  Scenario Outline: Verify that Barcode fields accepts accepts up to 4 characters in the Inbound Correspondence Definition Screen
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    And I click add button on Inbound Correspondence Definition
    When I add a Barcode to the Inbound Correspondence Definition containing "<Length>" free "<Format>" characters
    And I fill fields in create Inbound Correspondence screen
    And I click save button on create Inbound Correspondence screen
    And I click on the back arrow button on Inbound Correspondence details
    Then I should see Inbound Correspondence Barcode is "<ExpectedLength>" characters displayed
    Examples:
      | Length | Format  | ExpectedLength |
      | 5      | Numeric | 4              |
      | 4      | Numeric | 4              |

  @CP-3815 @CP-3815-06 @ui-ecms2 @burak #Fails due to the CP-36700
  Scenario: Verify that Barcode fields accepts only numeric value in the Inbound Correspondence Definition Screen
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    And I click add button on Inbound Correspondence Definition
    When I add a Barcode to the Inbound Correspondence Definition containing "4" free "AlphaNumeric" characters
    And I fill fields in create Inbound Correspondence screen
    And I click save button on create Inbound Correspondence screen
    And I click on the back arrow button on Inbound Correspondence details
    Then I verify "Barcode" field displays as "--" on the Inbound Correspondence Definition List Page

  @CP-3815 @CP-3815-07 @ui-ecms2 @burak
  Scenario: Verify that a successful stores the new definition including CreatedBy user and CreatedOn timestamp
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    And I add a new Inbound Correspondence Definition with Random Values
    And I will get the Authentication token for "<projectName>" in "CRM"
    And I get the Inbound Correspondence Definitions for the "9493" project
    And I verify the Inbound Correspondence Definition Fields



    ############################################################### CP-36768 ###############################################################################

  @CP-36768 @CP-36768-01 @ui-ecms2 @burak
  Scenario: Verify the Name value must exactly match name in OnBase static message displays
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    When I navigate to the Inbound Correspondence Definitions Screen of Project:"current"
    And I click add button on Inbound Correspondence Definition
    And I verify the Name value must exactly match name in OnBase static message displays

    ############################################################### CP-5794 ##############################################################################

  @CP-5794 @CP-5794-01 @API-ECMS @burak
  Scenario Outline: Verify able to Store and Retrieve the Inbound Correspondence Definition
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I want to create a random Inbound Correspondence Definition for the "CorrespondenceRegression" project
    And I retrieve the previously created Inbound Correspondence Definition by "<Field>"
    And I verify the Inbound Correspondence Definition Fields which retrieved by post
    Examples:
      | Field   |
      | Name    |
      | Barcode |
      | ID      |

  @CP-5794 @CP-5794-02 @API-ECMS @burak
  Scenario Outline: Verify that Barcode and Description fields are optional to add a Inbound Correspondence Definition (Backend)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I add a new Inbound Correspondence Definition without a "<Field>" for the "CorrespondenceRegression" project
    And I verify the Success message as Inbound Correspondence Definition created
    Examples:
      | Field       |
      | Barcode     |
      | Description |

  @CP-5794 @CP-5794-03 @API-ECMS @burak
  Scenario Outline: Verify that Name can accommodate 50 and Description can accommodate 100 characters for the Inbound Correspondence Definition (Backend)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I add a "<Field>" to the Inbound Correspondence Definition containing "<Length>" free text characters
    And I post Inbound Correspondence Definition Details for the "CorrespondenceRegression" project
    And I retrieve the previously created Inbound Correspondence Definition by "ID"
    And I verify the Inbound Correspondence Definition Fields which retrieved by post
    Examples:
      | Field       | Length |
      | Name        | 50     |
      | Description | 100    |

  @CP-5794 @CP-5794-04 @API-ECMS @burak
  Scenario Outline: Verify the Bad Request When Name exceeds 50 and Description exceeds 100 characters for the Inbound Correspondence Definition
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I add a "<Field>" to the Inbound Correspondence Definition containing "<Length>" free text characters
    And I post Inbound Correspondence Definition Details for the "CorrespondenceRegression" project
    And I retrieve the previously created Inbound Correspondence Definition by "ID"
    Then I validate I get 400 Bad Request for Inbound Correspondence Definition
    Examples:
      | Field       | Length |
      | Name        | 51     |
      | Description | 101    |

  @CP-5794 @CP-5794-05 @API-ECMS @burak
  Scenario: Verify that Barcode fields accepts accepts up to 4 characters
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I add a Barcode to the Inbound Correspondence Definition containing "5" free "Numeric" characters
    And I post Inbound Correspondence Definition Details for the "CorrespondenceRegression" project
    And I validate I get 400 Bad Request for Inbound Correspondence Definition

  @CP-5794 @CP-5794-06 @API-ECMS @burak
  Scenario: Verify that Barcode fields accepts accepts only Numeric Characters
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I add a Barcode to the Inbound Correspondence Definition containing "4" free "AlphaNumeric" characters
    And I post Inbound Correspondence Definition Details for the "CorrespondenceRegression" project
    Then I validate I get 400 Bad Request for Inbound Correspondence Definition