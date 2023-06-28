Feature:Role Drop Down

#Need to Handle add role to Project Via TDM
@CRM-1197 @CRM-1197-01 @shruti
 Scenario Outline: Setting up mutliple roles for a user
    Given I logged into Tenant Manager Project list page
#    When I click on Create a New Project
#    And I enter valid Details and save the Project
    When I search for a project by "project" value "TXCRM"
    When I expend the Project to view the details
   # And I add Account Manager and Approver roles to new project
    And I click on roles tab
    Then I click on add role button on role list page
    When I populate data on project role page "<roleName1>","<roleDesc>","<startDate>","<endDate>"
    And I click on Save button on project role page
    Then I click on add role button on role list page
    When I populate data on project role page "<roleName2>","<roleDesc>","<startDate>","<endDate>"
    And I click on Save button on project role page
    And I click on User List Menu
    And I click on add new user button on add new user page
    When I should be navigated to User Details page
    And I set maersk employee to yes
    And I enter max id "<maxID>"
    And I click on add max id button
    And I create User with given data "<maxID>","<isMaxEmp>","<Start Date>","<End Date>"
    And I assign role to User with given data "<roleName1>","<activeStatus>"
    And I click add role button to assign
    And I assign role to User with given data "<roleName2>","<activeStatus>"
    And I click add role button to assign
    And I click on save a user button
    Then I click on Yes and I am navigated back to User List Page
    And I should see a pop-up that user is created successfully
      Examples:
        |roleName1  |roleName2|roleDesc|startDate|maxID           | isMaxEmp |Start Date | End Date |activeStatus |inactiveStatus|
        |Csr1       |Csr2     |{random}|today    |Scv_mars_user_1| 'Yes'    | ''         | ''      |Active       |Inactive      |




  @CRM-1197 @CRM-1197-02 @shruti  @CRM-1196
  Scenario Outline: Verify active roles are enabled  in the application
    Given I logged into CRM with "<UserName>", "<Password>" and "<ProjectID>"
    When I expand active roles list
    Then I verify that all active "<ActiveRoles>" displayed for the user

    Examples:
      | UserName        | Password | ProjectID | ActiveRoles |
      | Scv_mars_user_1 | 6Y88Uwcw | 89 TXCRM  | Csr1,Csr2   |


  @CRM-1197 @CRM-1197-03 @shruti
  Scenario Outline: Verify Roles are selected
    Given I logged into CRM with "<UserName>", "<Password>" and "<ProjectID>"
    When I expand active roles list
    And I select a role "<Role1>" from role list
    Then I verify that "<Role1>" is selected
    When I expand active roles list
    And I select a role "<Role2>" from role list
    Then I verify that "<Role2>" is selected

    Examples:
      | UserName        | Password | ProjectID | Role1 | Role2 |
      | Scv_mars_user_1 | 6Y88Uwcw | 89 TXCRM  | Csr1  | Csr2  |

  @CRM-1197 @CRM-1197-04 @shruti
  Scenario Outline: Verify Role 1 is selected when the user clicks on cancel
    Given I logged into CRM with "<UserName>", "<Password>" and "<ProjectID>"
    When I expand active roles list
    And I select a role "<Role1>" from role list
    Then I verify that "<Role1>" is selected
    When I click on initiate contact record
    And I expand active roles list
    And I select a role "<Role2>" from role list
    Then I verify warning popup displayed with message "<Message>"
    When I click cancel button on role change warning pop up
    Then I verify that "<Role1>" is selected

    Examples:
      | UserName        | Password | ProjectID | Role1 | Role2 | Message                                                                                                                       |
      | Scv_mars_user_1 | 6Y88Uwcw | 89 TXCRM  | Csr1  | Csr2  | There are unsaved changes on this page. These changes will be lost if you continue with this action. Do you want to continue? |

  @CRM-1197 @CRM-1197-05 @shruti
  Scenario Outline: Verify Role 2 is selected when the user clicks on continue
    Given I logged into CRM with "<UserName>", "<Password>" and "<ProjectID>"
    When I expand active roles list
    And I select a role "<Role1>" from role list
    Then I verify that "<Role1>" is selected
    When I click on initiate contact record
    And I expand active roles list
    And I select a role "<Role2>" from role list
    When I click continue button on role change warning pop up
    Then I verify that "<Role2>" is selected

    Examples:
      | UserName        | Password | ProjectID | Role1 | Role2 |
      | Scv_mars_user_1 | 6Y88Uwcw | 89 TXCRM  | Csr1  | Csr2  |


  @CRM-1197 @CRM-1197-06 @shruti
  Scenario Outline: Verify Role 1 is selected when the user clicks on cancel
    Given I logged into CRM with "<UserName>", "<Password>" and "<ProjectID>"
    When I expand active roles list
    And I select a role "<Role1>" from role list
    Then I verify that "<Role1>" is selected
    When I click on initiate contact record
    And I expand active roles list
    And I select a role "<Role2>" from role list
    Then I verify warning popup displayed with message "<Message>"
    Examples:
      | UserName        | Password | ProjectID | Role1 | Role2 | Message                                                                                                                       |
      | Scv_mars_user_1 | 6Y88Uwcw | 89 TXCRM  | Csr1  | Csr2  | There are unsaved changes on this page. These changes will be lost if you continue with this action. Do you want to continue? |

  @CRM-1196 @CRM-1196-02 @shruti
  Scenario Outline: Verify default role is displayed  when user logs in
    Given I logged into CRM with "<UserName>", "<Password>" and "<ProjectID>"
    Then I verify "<defaultrole>" is displayed for the session
    Examples:
      | UserName        | Password | ProjectID | defaultrole |
      | Scv_mars_user_1 | 6Y88Uwcw | 89 TXCRM  | Csr1        |
