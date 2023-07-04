Feature: NJ-SBE IVR APIs


  @contact-record-api-CRMC @CP-40007 @CP-40007-01 @API-CORE @API-CRM-Regression @sang
  Scenario: Verify Contact Record and Case Id link for mars crm link api
    Given I will get the Authentication token for "NJ-SBE" in "CRM"
    And I initiate IVR calldata api with the following and send POST
      | tenant | consumerFirstName | consumerLastName | consumerSSN | consumerDateOfBirth | caseIdType | caseID       | createdOn | others              |
      | NJ-SBE | David             | Leisinger        | 2473        | 03171988            | Medicaid   | SBE100000189 | CURRENT   | njsbe random filled |
    And I initiate cases external api with the following and send PUT
      | externalCaseId | createdBy |
      | SBE100000189   | 3472      |
    And I initiated casemember api with "ID FROM EXTERNAL" and send GET
    When I initiate crm link api with the following and send PUT
      | correlationId   | internalId        | externalId      | createdOn | updatedOn | effectiveStartDate | uiid   |
      | exInteractionId | from external api | from get caseId | CURRENT   | CURRENT   | CURRENT            | random |
    Then I verify the successful contact record to consumer link in the LINK EVENT history
