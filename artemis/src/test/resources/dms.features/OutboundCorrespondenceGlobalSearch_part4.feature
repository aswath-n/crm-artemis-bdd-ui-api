Feature: Outbound Correspondence Global Search part 4

  @CP-9624-01 @ui-ecms2
  Scenario: Outbound Correspondence Search with a Mail, Email, and Text notification - Verify that if multiple notifications for a single correspondence exist that Mail will be the image that is shown when the correspondence view image icon is clicked
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Mail,Email,Text"
    Given I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should see the Mail notification is viewed


  @CP-9624-02 @ui-ecms2
  Scenario: Outbound Correspondence Search Verify that if multiple notifications for a single correspondence exist that Email will be the image that is shown when there is no Mail and the correspondence view image icon is clicked
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Email,Text"
    Given I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should see the Email notification is viewed

  @CP-9624-03 @ui-ecms2
  Scenario: Outbound Correspondence Search Verify that if multiple notifications for a single correspondence exist that Text will be the image that is shown when there is no Mail and no Email and the correspondence view image icon is clicked
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have an Outbound Correspondence with a notification for "Text"
    Given I logged into CRM
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | previouslyCreated |
    And there is a Notification Object Uploaded onto Onbase for each notification
    And I click on the view icon for the correspondence "previouslyCreated"
    Then I should see the Text notification is viewed

  @CP-9624-04 @ui-ecms2
  Scenario: Outbound Correspondence Search Verify that no icon is visible when Outbound Correspondence has not been uploaded
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    And I have a valid Outbound Correspondence request
    And I send the request for an Outbound Correspondence to the service
    Given I logged into CRM
    And I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    And I enter correspondence ID to search for Outbound Correspondence
      | randomCorrespondence |
    Then I should see there is no view icon for the Outbound Correspondence

  @CP-3341-01 @CP-3341 @API-ECMS @James
  Scenario Outline: Verify channel search for OB Correspondence from api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to search Outbound Correspondence for the following values
      | <search> | <channel> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <search> | <channel> |
    Examples:
      | search  | channel |
      | channel | Mail    |
      | channel | Text    |
      | channel | Fax     |
      | channel | Email   |

  @CP-3341-02 @CP-3341 @API-ECMS @James
  Scenario Outline: Verify language search for OB Correspondence from api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to search Outbound Correspondence for the following values
      | <search> | <language> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <search> | <language> |
    Examples:
      | search   | language |
      | language | English  |
      | language | Spanish  |

  @CP-3341-03 @CP-3341 @API-ECMS @James
  Scenario Outline: Verify status search for OB Correspondence from api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to search Outbound Correspondence for the following values
      | <search> | <status> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <search> | <status> |
    Examples:
      | search | status    |
      | status | Requested |
      | status | Sent      |
      | status | Error     |
      | status | Assembled |
      | status | Exported  |

  @CP-3341-04 @CP-3341 @API-ECMS @James
  Scenario Outline: Verify notification status search for OB Correspondence from api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to search Outbound Correspondence for the following values
      | <search> | <notificationStatus> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <search> | <notificationStatus> |
    Examples:
      | search             | notificationStatus |
      | notificationStatus | Requested          |
      | notificationStatus | Sent               |
      | notificationStatus | Error              |
      | notificationStatus | Assembled          |
      | notificationStatus | Exported           |

  @CP-3341-05 @CP-3341 @API-ECMS @James
  Scenario Outline: Verify notificationStatusReason search for OB Correspondence from api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to search Outbound Correspondence for the following values
      | <search> | <notificationStatusReason> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <search> | <notificationStatusReason> |
    Examples:
      | search                   | notificationStatusReason |
      | notificationStatusReason | Undeliverable            |
      | notificationStatusReason | No Valid Destination     |
      | notificationStatusReason | Requested in Error       |

  @CP-3341-06 @CP-3341 @API-ECMS @James
  Scenario Outline: Verify correspondenceStatusReason search for OB Correspondence from api
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I have a request to search Outbound Correspondence for the following values
      | <search> | <correspondenceStatusReason> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <search> | <correspondenceStatusReason> |
    Examples:
      | search                     | correspondenceStatusReason |
      | correspondenceStatusReason | Undeliverable              |
      | correspondenceStatusReason | No Valid Destination       |
      | correspondenceStatusReason | Requested in error         |

  @CP-3341-09 @CP-3341 @ui-ecms2 @James
  Scenario Outline: Verify ob search by correspondence status from connection point
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | <search> | <status> |
    When I select the show details caret for an Outbound Correspondence
    Then I should see that all the outbound correspondence search results have the following values
      | <search> | <status> |
    Examples:
      | search | status    |
      | STATUS | Requested |
      | STATUS | Error     |

  @CP-3341-10 @CP-3341 @ui-ecms2 @James
  Scenario:Verify ob search results will have an elipsis for ob with multiple channels and recipients
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have an existing Outbound Correspondence with multiple consumer Ids and channels
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | CORRESPONDENCEID | previouslyCreated |
    Then I should see that there is an ellipsis in OB search results for when there are multiple channels and recipients



  @CP-3341-07 @CP-3341 @ui-ecms2 @James
  Scenario Outline: Verify ob search by channel from connection point
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | <search> | <channel> |
    When I select the show details caret for an Outbound Correspondence
    Then I should see that all the outbound correspondence search results have the following values
      | <search> | <channel> |
    Examples:
      | search  | channel |
      | CHANNEL | Mail    |
      | CHANNEL | Text    |
      | CHANNEL | Fax     |
      | CHANNEL | Email   |

  @CP-3341-08 @CP-3341 @ui-ecms2 @James
  Scenario Outline: Verify ob search by language from connection point
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | <search> | <language> |
    When I select the show details caret for an Outbound Correspondence
    Then I should see that all the outbound correspondence search results have the following values
      | <search> | <language> |
    Examples:
      | search   | language |
      | LANGUAGE | English  |
      | LANGUAGE | Spanish  |

