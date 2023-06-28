@03162019123
Feature: API-CRM: Contact Record Controller

  @contact-record-api-CRMC @API-CORE @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

 @contact-record-api-CRMC @API-CRM-SmokeTest @Sujoy @API-CORE
  Scenario Outline: Create Contact Record Health Check -- PUT app/crm/contactrecord
    Given I created a contact record using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated specific contact Search ""
    And I run Get contact records API
    Then I can verify contact records search response is "True"
    Examples:
      | projectName |
      | BLCRM       |

    # Search Contact Record is not implemented yet
#  @API-HealthCheck @API-CRM-SmokeTest @Sujoy
#  Scenario Outline: Search Contact Record Health Check -- POST app/crm/contactrecords
#    Given I initiated search contact records API
#    When I can search Contact Record by "<Node>" with value "<value>"
#    And I run the search contact records API
#    Then I can verify contact records search response is "<Success>"
#    Examples:
#      | Node                  | value  | Success |
#      | consumerFirstName     | Graham | True    |

  @contact-record-api-CRMC @API-CRM-SmokeTest @Sujoy @8106 @API-CORE
  Scenario Outline: Get Contact Record detail by Id Health Check -- GET app/crm/contactrecord/{contactId}
    Given I created a contact record using API
     When I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated specific contact Search ""
    And I run Get contact records API
    Then I can verify contact records search response is "True"
    Examples:
      |projectName|
      |BLCRM      |


 @contact-record-api-CRMC @API-CORE @API-CRM-SmokeTest @Sujoy
  Scenario Outline: Get Contact Record comments by contact Id Health Check -- GET app/crm/contactrecord/comments/{contactId}
     When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a contact record using API
    When I initiated contact reason addition
    And I add new reason "Information Request" for Contact Record Id "" with actions using API
      | Provided Appeal Information             |
    And I initiated specific contact Record Comments "" using API
    Then I run Get contact records comments using API
    Examples:
      |projectName|
      |[blank]|


  @contact-record-api-CRMC @API-CORE @API-CRM-SmokeTest @Sujoy
  Scenario Outline: Add Contact Reason/Action to Contact Record Health Check -- POST app/crm/contactrecord/contactreason
     When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a contact record using API
    And I can add the contact record reason with the following values:
      |contactReason|Information Request|
      |contactAction|Provided Appeal Information|
      |reasonComments|No Comment|
    Examples:
      |projectName|
      |[blank]|

