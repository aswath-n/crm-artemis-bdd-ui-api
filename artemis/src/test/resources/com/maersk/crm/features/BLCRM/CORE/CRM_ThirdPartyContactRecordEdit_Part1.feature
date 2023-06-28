Feature: Edit Third Party Contact Record Part 1


  @CRM-1297 @CRM-1297-01 @shruti @crm-regression @ui-core @sang
  Scenario Outline: Verify Edit Third party Contact Record - Correcting Third Party Information - Outbound
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME         | John      |
      | LAST NAME          | Smith     |
      | ORGANIZATION NAME  | ABC Group |
      | CONSUMER TYPE      | Media     |
      | PREFERRED LANGUAGE | English   |
    And I select "Program A" "Web Chat" "Outbound" "abctest@xyz.com" for active contact selection and save contact record
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the "previously" ui created contact record
    When I click on edit icon the Contact Details page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME        | <First Name>        |
      | LAST NAME         | <Last Name>         |
      | ORGANIZATION NAME | <Organization Name> |
      | CONSUMER TYPE     | <Consumer Type>     |
    And I select the following Reasons for Edit values in the Wrap up and Close panel for Contact Record edit page
      | Correcting Third Party Information |
    And I click on save button in edit third party contact record
    And I Verify edited "THIRD PARTY PANEL" field values for contact record
      | FIRST NAME        | <First Name>        |
      | LAST NAME         | <Last Name>         |
      | ORGANIZATION NAME | <Organization Name> |
      | CONSUMER TYPE     | <Consumer Type>     |
    And I verify the edited reasons with on and by are displayed in the Contact Edit Reasons panel
      | EDITED ON         | EDITED BY               | REASON FOR EDIT                    |
      | Current DATE TIME | 8369 Service AccountOne | Correcting Third Party Information |
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type |
      | Bruce      | Wayne     | DEF Group         | Agency        |

  @CRM-1297 @CRM-1297-02 @shruti @crm-regression @ui-core @sang
  Scenario Outline: Verify Edit Third party Contact Record - Correcting Contact Details - Outbound
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME         | John      |
      | LAST NAME          | Smith     |
      | ORGANIZATION NAME  | ABC Group |
      | CONSUMER TYPE      | Media     |
      | PREFERRED LANGUAGE | English   |
    And I select "Program A" "Web Chat" "Outbound" "abctest@xyz.com" for active contact selection and save contact record
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the "previously" ui created contact record
    When I click on edit icon the Contact Details page
    And I Enter the following Contact Details field values in the Contact Record Edit page
      | CHANNEL   | EMAIL   | PROGRAM   | TRANSLATION SERVICE   |
      | <CHANNEL> | <EMAIL> | <PROGRAM> | <TRANSLATION SERVICE> |
    And I select the following Reasons for Edit values in the Wrap up and Close panel for Contact Record edit page
      | Correcting Contact Details |
      | Adding Contact Details     |
    And I click on save button in edit third party contact record
    And I Verify edited "CONTACT DETAILS PANEL" field values for contact record
      | CHANNEL             | <CHANNEL>             |
      | EMAIL               | <EMAIL>               |
      | PROGRAM             | <PROGRAM>             |
      | TRANSLATION SERVICE | <TRANSLATION SERVICE> |
    And I verify the edited reasons with on and by are displayed in the Contact Edit Reasons panel
      | EDITED ON         | EDITED BY               | MULTI REASON FOR EDIT                              |
      | Current DATE TIME | 8369 Service AccountOne | Correcting Contact Details, Adding Contact Details |
    Examples:
      | CHANNEL | EMAIL        | PROGRAM  | TRANSLATION SERVICE |
      | Email   | edit@xyz.com | Medicaid | Spanish             |

  @CRM-1297 @CRM-1297-03 @shruti @crm-regression @ui-core @sang
  Scenario Outline: Verify Edit Third party Details- Reason/Actions-outbound
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME         | John      |
      | LAST NAME          | Smith     |
      | ORGANIZATION NAME  | ABC Group |
      | CONSUMER TYPE      | Media     |
      | PREFERRED LANGUAGE | English   |
    And I select "Program A" "Web Chat" "Outbound" "abctest@xyz.com" for active contact selection and save contact record
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the "previously" ui created contact record
    When I click on edit icon the Contact Details page
    And I edit saved contact record Reason and Action with the following
      | CONTACT REASON | <CONTACT REASON> |
      | CONTACT ACTION | <CONTACT ACTION> |
    And I select the following Reasons for Edit values in the Wrap up and Close panel for Contact Record edit page
      | Correcting Contact Reason/Action |
    And I click on save button in edit third party contact record
    And I Verify edited "CONTACT REASON AND ACTION PANEL" field values for contact record
      | CONTACT REASON | <CONTACT REASON> |
      | CONTACT ACTION | <CONTACT ACTION> |
    And I verify the edited reasons with on and by are displayed in the Contact Edit Reasons panel
      | EDITED ON         | EDITED BY               | REASON FOR EDIT                  |
      | Current DATE TIME | 8369 Service AccountOne | Correcting Contact Reason/Action |
    Examples:
      | CONTACT REASON | CONTACT ACTION |
      | Enrollment     | Plan Change    |

  @CRM-1297 @CRM-1297-04 @shruti @crm-regression @ui-core @sang
  Scenario Outline: Verify Edit Third party Contact Record -Third party Details -Inbound
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME         | John      |
      | LAST NAME          | Smith     |
      | ORGANIZATION NAME  | ABC Group |
      | CONSUMER TYPE      | Media     |
      | PREFERRED LANGUAGE | English   |
    And I select "Program A" "Web Chat" "Inbound" "abctest@xyz.com" for active contact selection and save contact record
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the "previously" ui created contact record
    When I click on edit icon the Contact Details page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME        | <First Name>        |
      | LAST NAME         | <Last Name>         |
      | ORGANIZATION NAME | <Organization Name> |
      | CONSUMER TYPE     | <Consumer Type>     |
    And I select the following Reasons for Edit values in the Wrap up and Close panel for Contact Record edit page
      | Correcting Third Party Information |
    And I click on save button in edit third party contact record
    And I Verify edited "THIRD PARTY PANEL" field values for contact record
      | FIRST NAME        | <First Name>        |
      | LAST NAME         | <Last Name>         |
      | ORGANIZATION NAME | <Organization Name> |
      | CONSUMER TYPE     | <Consumer Type>     |
    And I verify the edited reasons with on and by are displayed in the Contact Edit Reasons panel
      | EDITED ON         | EDITED BY               | REASON FOR EDIT                    |
      | Current DATE TIME | 8369 Service AccountOne | Correcting Third Party Information |
    Examples:
      | First Name | Last Name | Organization Name | Consumer Type |
      | Bruce      | Wayne     | DEF Group         | Agency        |

  @CRM-1297 @CRM-1297-05 @shruti @crm-regression @ui-core @sang
  Scenario Outline: Verify Edit Third party Contact Record - Correcting Contact Details - Inbound
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME         | John      |
      | LAST NAME          | Smith     |
      | ORGANIZATION NAME  | ABC Group |
      | CONSUMER TYPE      | Media     |
      | PREFERRED LANGUAGE | English   |
    And I select "Program A" "Web Chat" "Inbound" "abctest@xyz.com" for active contact selection and save contact record
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the "previously" ui created contact record
    When I click on edit icon the Contact Details page
    And I Enter the following Contact Details field values in the Contact Record Edit page
      | CHANNEL   | EMAIL   | PROGRAM   | TRANSLATION SERVICE   |
      | <CHANNEL> | <EMAIL> | <PROGRAM> | <TRANSLATION SERVICE> |
    And I select the following Reasons for Edit values in the Wrap up and Close panel for Contact Record edit page
      | Correcting Contact Details |
      | Adding Contact Details     |
    And I click on save button in edit third party contact record
    And I Verify edited "CONTACT DETAILS PANEL" field values for contact record
      | CHANNEL             | <CHANNEL>             |
      | EMAIL               | <EMAIL>               |
      | PROGRAM             | <PROGRAM>             |
      | TRANSLATION SERVICE | <TRANSLATION SERVICE> |
    And I verify the edited reasons with on and by are displayed in the Contact Edit Reasons panel
      | EDITED ON         | EDITED BY               | MULTI REASON FOR EDIT                              |
      | Current DATE TIME | 8369 Service AccountOne | Correcting Contact Details, Adding Contact Details |
    Examples:
      | CHANNEL | EMAIL        | PROGRAM  | TRANSLATION SERVICE |
      | Email   | edit@xyz.com | Medicaid | Spanish             |

  @CRM-1297 @CRM-1297-03 @shruti @crm-regression @ui-core @sang
  Scenario Outline: Verify Edit Third party Details- Reason/Actions-Inbound
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given  I created a consumer with population type "MEDICAID-GENERAL" and following
      | consumerRole     | Primary Individual |
      | saveConsumerInfo | QW1                |
    Given I logged into CRM and click on initiate contact
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I store "CONTACT ID AND NAME OF CONTACT" for use in verification
    When I click on "Active Contact" Tab on Contact Dashboard Page
    And I enter Third Party Details Panel with the following field values
      | FIRST NAME         | John      |
      | LAST NAME          | Smith     |
      | ORGANIZATION NAME  | ABC Group |
      | CONSUMER TYPE      | Media     |
      | PREFERRED LANGUAGE | English   |
    And I select "Program A" "Web Chat" "Inbound" "abctest@xyz.com" for active contact selection and save contact record
    And I click on initiate a contact button
    When I click on third party contact record type radio button
    And I search for the created Case Consumer in the Third Party tab With "First and Last names"
    And I link the "First" consumer searched from the third party tab to the active contact
    When I click on "Case & Contact Details" Tab on Contact Dashboard Page
    And I select the "previously" ui created contact record
    When I click on edit icon the Contact Details page
    And I edit saved contact record Reason and Action with the following
      | CONTACT REASON | <CONTACT REASON> |
      | CONTACT ACTION | <CONTACT ACTION> |
    And I select the following Reasons for Edit values in the Wrap up and Close panel for Contact Record edit page
      | Correcting Contact Reason/Action |
    And I click on save button in edit third party contact record
    And I Verify edited "CONTACT REASON AND ACTION PANEL" field values for contact record
      | CONTACT REASON | <CONTACT REASON> |
      | CONTACT ACTION | <CONTACT ACTION> |
    And I verify the edited reasons with on and by are displayed in the Contact Edit Reasons panel
      | EDITED ON         | EDITED BY               | REASON FOR EDIT                  |
      | Current DATE TIME | 8369 Service AccountOne | Correcting Contact Reason/Action |
    Examples:
      | CONTACT REASON | CONTACT ACTION |
      | Enrollment     | Plan Change    |
