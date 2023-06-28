Feature: System Store Outbound Correspondence Request with Body Data

  @API-CP-3907 @API-CP-3907-01 @API-ECMS @umid
  Scenario: Store Body Data Outbound Correspondence Request with Body Data
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given that I have created random Outbound Correspondence through the Post Correspondence endpoint with Body Data
    And I call letter data by NID to get the full response
    And I verify created Body Data "full" that was sent

  @API-CP-3907 @API-CP-3907-02 @API-ECMS @umid
  Scenario: Store Body Data Outbound Correspondence Request with Body Data and no bodySubsets
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given that I have created random Outbound Correspondence through the Post Correspondence endpoint with Body Data and no bodySubsets
    And I call letter data by NID to get the full response
    And I verify created Body Data "NoBodySubsets" that was sent

  @API-CP-3907 @API-CP-3907-03@API-ECMS @umid
  Scenario: Store Body Data Outbound Correspondence Request with Body Data and no bodySubsetItems
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Given that I have created random Outbound Correspondence through the Post Correspondence endpoint with Body Data and no bodySubsetItems
    And I call letter data by NID to get the full response
    And I verify created Body Data "NoBodySubsetItems" that was sent

  @API-CP-3907 @API-CP-3907-04 @API-ECMS @umid
  Scenario: Store Body Data Outbound Correspondence Request with Body Data and recip as null
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Given that I have created random Outbound Correspondence through the Post Correspondence endpoint with Body Data and recipients as null
    And I invoke the endpoint to retrieve destinations for the recipients on the Outbound Correspondence Request
    And I call letter data by NID to get the full response
    And I verify created Body Data "full" that was sent
