Feature: Authorized Representative Part 2
  @CP-31841 @CP-31841-01 @Beka @crm-regression @ui-cc
  Scenario: Potential Match Found create AR with existing SSN for BLCRM
    Given I logged into CRM and click on initiate contact
    When I searched existing case where First Name as "Marquise" and Last Name as "Goodwin"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Add button for Authorized Representative
    And I populate Authorized Representative field with following data
      | firstName | Marquise   |
      | lastName  | Goodwin    |
      | DoB       | 03/07/2016 |
      | SSN       | 177286083  |
    When I click on Save button on add primary individual page
    And I click on continue button on warning message
    Then I verify Potential Match error message is displayed

  @CP-31841 @CP-31841-02 @Beka @crm-regression @ui-cc
  Scenario: Potential Match Found create AR with existing First/LastName/DOB for BLCRM
    Given I logged into CRM and click on initiate contact
    When I searched existing case where First Name as "Marquise" and Last Name as "Goodwin"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Add button for Authorized Representative
    And I populate Authorized Representative field with following data
      | firstName | Marquise   |
      | lastName  | Goodwin    |
      | DoB       | 03/07/2016 |
      | SSN       |[blank]|
    When I click on Save button on add primary individual page
    And I click on continue button on warning message
    Then I verify "CONTINUE AND ADD NEW CONSUMER" is enabled in Create Consumer Page

  @CP-31841 @CP-31841-03 @Beka @crm-regression @ui-cc
  Scenario: Verify Potential Match AR result is displayed for BLCRM
    Given I logged into CRM and click on initiate contact
    When I searched existing case where First Name as "Marquise" and Last Name as "Goodwin"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Add button for Authorized Representative
    And I populate Authorized Representative field with following data
      | firstName | Marquise   |
      | lastName  | Goodwin    |
      | DoB       | 03/07/2016 |
      | SSN       |[blank]|
    When I click on Save button on add primary individual page
    And I click on continue button on warning message
    Then I see list of Potential Match consumers table with header
      | CONSUMER ID            |
      | EXTERNAL CONSUMER ID   |
      | CASE ID                |
      | EXTERNAL CASE ID       |
      | FULL NAME              |
      | GENDER                 |
      | DATE OF BIRTH          |
      | SSN                    |

  @CP-31841 @CP-31841-04 @Beka @crm-regression @ui-cc
  Scenario: Verify Include Role, Status, and Case Info for BLCRM
    Given I logged into CRM and click on initiate contact
    When I searched existing case where First Name as "Marquise" and Last Name as "Goodwin"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Add button for Authorized Representative
    And I populate Authorized Representative field with following data
      | firstName | Marquise   |
      | lastName  | Goodwin    |
      | DoB       | 03/07/2016 |
      | SSN       |[blank]|
    When I click on Save button on add primary individual page
    And I click on continue button on warning message
    When I click first contact record in search results
    Then the grid displaying the Consumer information will include
      | CASE ID |
      | ROLE    |
      | STATUS  |

  @CP-31841 @CP-31841-05 @Beka @crm-regression @ui-cc
  Scenario: Verify button labeled “(+)” Match Found on SSN for BLCRM
    Given I logged into CRM and click on initiate contact
    When I searched existing case where First Name as "Marquise" and Last Name as "Goodwin"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Add button for Authorized Representative
    And I populate Authorized Representative field with following data
      | firstName | Random     |
      | lastName  | Random     |
      | DoB       | 03/07/2011 |
      | SSN       | 177286083  |
    When I click on Save button on add primary individual page
    And I click on continue button on warning message
    Then I can only proceed by clicking a button labeled "(+)"

  @CP-31841 @CP-31841-06 @Beka @crm-regression @ui-cc
  Scenario: Verify Save Whatever was manually entered in Role Information Section for BLCRM
    Given I logged into CRM and click on initiate contact
    When I searched existing case where First Name as "Marquise" and Last Name as "Goodwin"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Add button for Authorized Representative
    And I populate Authorized Representative field with following data
      | firstName | Random     |
      | lastName  | Random     |
      | DoB       | 03/07/2011 |
      | SSN       | 177286083  |
    When I click on Save button on add primary individual page
    And I click on continue button on warning message
    When I click on Use This Consumer button
    Then The following field  will not changed manually entered value
      | Consumer Role          | Authorized Representative |
      | Access Type            | Full Access               |
      | Start Date             | 01/01/2019                |
      | End Date               | 01/01/2023                |
      | Receive Correspondence | Yes                       |

  @CP-32068 @CP-32068-01 @Beka @crm-regression @ui-cc
  Scenario: Consumer in the AR role who is already active on the Case
    Given I logged into CRM and click on initiate contact
    When I searched existing case where First Name as "ARavoidADD" and Last Name as "ARconsumer"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Add button for Authorized Representative
    And I populate Authorized Representative field with following data
      | firstName | NewARforTheCase |
      | lastName  | NewARforTheCase |
      | DoB       | 03/14/2005      |
      | SSN       | 887-78-8777     |
    When I click on Save button on add primary individual page
    And I click on continue button on warning message
    When I click on Use This Consumer button
    Then I see error message "This person is already active on this Case. You cannot add a person more than once on the same Case." is displayed

  @CP-32068 @CP-32068-02 @Beka @crm-regression @ui-cc-in
  Scenario: Consumer in the AR role who is already active on the Case for INEB
    Given I logged into CRM with "Service Account 1" and select a project "IN-EB"
    And I click on initiate a contact button
    And I searched customer have First Name as "Donald" and Last Name as "silvergold"
    And I link the contact to an existing Case or Consumer Profile
    And I click on the Demographic Info Tab
    And I click on the Add button for Authorized Representative
    And I populate Authorized Representative field with following data
      | firstName | Chess      |
      | lastName  | Stan       |
      | DoB       | 11/01/2021 |
      | SSN       | 434343434  |
    When I click on Save button on add primary individual page
    When I click on Use This Consumer button
    Then I see error message "This person is already active on this Case. You cannot add a person more than once on the same Case." is displayed


