Feature: Outbound Correspondence Global Search part 5

  @CP-16667 @ui-ecms2 @Prithika
  Scenario: Verify Warning Message For Search Results That Display More than 200 results
    And I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | REQUESTDATE | olddate |
    Then I should get pop up with error message "Search results in excess, enter additional search criteria to limit search results"


  @CP-28565-1 @ui-ecms2 @Keerthi
  Scenario: Verify ob global search correspondence status dropdown values from cp
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "CORRESPONDENCE STATUS" dropdown values
      | Requested |
      | On Hold   |
      | Assembled |
      | Exported  |
      | Sent      |
      | Canceled  |
      | Returned  |
      | Error     |


  @CP-28565-1.1 @ui-ecms2 @Keerthi
  Scenario: Verify ob global search correspondence status reason dropdown values from cp
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "CORRESPONDENCE STATUS REASON" dropdown values
      | State requested                    |
      | User requested                     |
      | Volume control                     |
      | Channel issues                     |
      | Destination issues                 |
      | Template issues                    |
      | Vendor issues                      |
      | Undeliverable                      |
      | Refused                            |
      | Destination Agent Unresponsive     |
      | Destination Agent Rejected Message |
      | Destination Invalid                |
      | Change of Address                  |
      | Mailbox Full                       |
      | Requested in error                 |
      | Unresolvable error                 |
      | No Valid Destination               |
      | Recipient No Longer Active         |
      | Consumer Request                   |
      | No longer appropriate              |
      | Export Error                       |
      | Assembly Error                     |
      | Invalid Address                    |
      | No default recipient available     |


  @CP-28565-2 @ui-ecms2 @Keerthi
  Scenario: Verify ob global search notification status dropdown values from cp
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "NOTIFICATION STATUS" dropdown values
      | Requested |
      | On Hold   |
      | Assembled |
      | Exported  |
      | Sent      |
      | Canceled  |
      | Returned  |
      | Error     |
      | Precluded |


  @CP-28565-2.1 @ui-ecms2 @Keerthi
  Scenario: Verify ob global search notification status reason dropdown values from cp
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    And I validate "NOTIFICATION STATUS REASON" dropdown values
      | State requested                    |
      | User requested                     |
      | Volume control                     |
      | Channel issues                     |
      | Destination issues                 |
      | Template issues                    |
      | Vendor issues                      |
      | Undeliverable                      |
      | Refused                            |
      | Destination agent unresponsive     |
      | Destination agent rejected message |
      | Destination invalid                |
      | Change of Address                  |
      | Mailbox full                       |
      | No Valid Destination               |
      | Recipient No Longer Active         |
      | Consumer Request                   |
      | No Longer Appropriate              |
      | Requested in Error                 |
      | Export Error                       |
      | Assembly Error                     |
      | Invalid Address                    |
      | Superseded by Events               |

  @CP-28565-3 @ui-ecms2 @Keerthi
  Scenario: Verify Cancel button for Invalid Search Combination
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | STATUS                     | Returned       |
      | CORRESPONDENCESTATUSREASON | Volume control |
      | NOTIFICATIONSTATUS         | On Hold        |
      | NOTIFICATIONSTATUSREASON   | Refused        |
    Then I click on cancel button in Outbound search page
    Then The UI will blank out the values in the search fields
      | CORRESPONDENCE STATUS        |
      | CORRESPONDENCE STATUS REASON |
      | NOTIFICATION STATUS          |
      | NOTIFICATION STATUS REASON   |


  @CP-28565-4.1 @ui-ecms2 @Keerthi
  Scenario: Verify Generic Warning Message for Invalid correspondence Search Combination-1
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | STATUS                       | On Hold                            |
      | CORRESPONDENCESTATUSREASON1  | Undeliverable                      |
      | CORRESPONDENCESTATUSREASON2  | Refused                            |
      | CORRESPONDENCESTATUSREASON3  | Destination Agent Unresponsive     |
      | CORRESPONDENCESTATUSREASON4  | Destination Agent Rejected Message |
      | CORRESPONDENCESTATUSREASON5  | Destination Invalid                |
      | CORRESPONDENCESTATUSREASON6  | Change of Address                  |
      | CORRESPONDENCESTATUSREASON7  | Mailbox Full                       |
      | CORRESPONDENCESTATUSREASON9  | Requested in error                 |
      | CORRESPONDENCESTATUSREASON10 | Unresolvable error                 |
      | CORRESPONDENCESTATUSREASON11 | No Valid Destination               |
      | CORRESPONDENCESTATUSREASON12 | Recipient No Longer Active         |
      | CORRESPONDENCESTATUSREASON13 | Consumer Request                   |
      | CORRESPONDENCESTATUSREASON14 | No longer appropriate              |
      | CORRESPONDENCESTATUSREASON16 | Export Error                       |
      | CORRESPONDENCESTATUSREASON17 | Assembly Error                     |
      | CORRESPONDENCESTATUSREASON18 | Invalid Address                    |
    Then I should see an Error Alert Message
      | No search results found |

  @CP-28565-4.2 @ui-ecms2 @Keerthi
  Scenario: Verify Generic Warning Message for Invalid correspondence Search Combination-2
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | STATUS                       | Returned                   |
      | CORRESPONDENCESTATUSREASON1  | State requested            |
      | CORRESPONDENCESTATUSREASON2  | User requested             |
      | CORRESPONDENCESTATUSREASON3  | Volume control             |
      | CORRESPONDENCESTATUSREASON4  | Channel issues             |
      | CORRESPONDENCESTATUSREASON5  | Destination issues         |
      | CORRESPONDENCESTATUSREASON6  | Template issues            |
      | CORRESPONDENCESTATUSREASON7  | Vendor issues              |
      | CORRESPONDENCESTATUSREASON9  | Requested in error         |
      | CORRESPONDENCESTATUSREASON10 | Unresolvable error         |
      | CORRESPONDENCESTATUSREASON11 | No Valid Destination       |
      | CORRESPONDENCESTATUSREASON12 | Recipient No Longer Active |
      | CORRESPONDENCESTATUSREASON13 | Consumer Request           |
      | CORRESPONDENCESTATUSREASON14 | No longer appropriate      |
      | CORRESPONDENCESTATUSREASON16 | Export Error               |
      | CORRESPONDENCESTATUSREASON17 | Assembly Error             |
      | CORRESPONDENCESTATUSREASON18 | Invalid Address            |
    Then I should see an Error Alert Message
      | No search results found |


  @CP-28565-4.3 @ui-ecms2 @Keerthi
  Scenario: Verify Generic Warning Message for Invalid correspondence Search Combination-3
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | STATUS                       | Canceled                           |
      | CORRESPONDENCESTATUSREASON1  | Undeliverable                      |
      | CORRESPONDENCESTATUSREASON2  | Refused                            |
      | CORRESPONDENCESTATUSREASON3  | Destination Agent Unresponsive     |
      | CORRESPONDENCESTATUSREASON4  | Destination Agent Rejected Message |
      | CORRESPONDENCESTATUSREASON5  | Destination Invalid                |
      | CORRESPONDENCESTATUSREASON6  | Change of Address                  |
      | CORRESPONDENCESTATUSREASON7  | Mailbox Full                       |
      | CORRESPONDENCESTATUSREASON8  | State requested                    |
      | CORRESPONDENCESTATUSREASON9  | User requested                     |
      | CORRESPONDENCESTATUSREASON10 | Volume control                     |
      | CORRESPONDENCESTATUSREASON11 | Channel issues                     |
      | CORRESPONDENCESTATUSREASON12 | Destination issues                 |
      | CORRESPONDENCESTATUSREASON13 | Template issues                    |
      | CORRESPONDENCESTATUSREASON14 | Vendor issues                      |
      | CORRESPONDENCESTATUSREASON16 | Export Error                       |
      | CORRESPONDENCESTATUSREASON17 | Assembly Error                     |
      | CORRESPONDENCESTATUSREASON18 | Invalid Address                    |
    Then I should see an Error Alert Message
      | No search results found |


  @CP-28565-4.4 @ui-ecms2 @Keerthi
  Scenario: Verify Generic Warning Message for Invalid correspondence Search Combination-4
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | STATUS                       | Error                              |
      | CORRESPONDENCESTATUSREASON1  | Undeliverable                      |
      | CORRESPONDENCESTATUSREASON2  | Refused                            |
      | CORRESPONDENCESTATUSREASON3  | Destination Agent Unresponsive     |
      | CORRESPONDENCESTATUSREASON4  | Destination Agent Rejected Message |
      | CORRESPONDENCESTATUSREASON5  | Destination Invalid                |
      | CORRESPONDENCESTATUSREASON6  | Change of Address                  |
      | CORRESPONDENCESTATUSREASON7  | Mailbox Full                       |
      | CORRESPONDENCESTATUSREASON9  | Requested in error                 |
      | CORRESPONDENCESTATUSREASON10 | Unresolvable error                 |
      | CORRESPONDENCESTATUSREASON11 | No Valid Destination               |
      | CORRESPONDENCESTATUSREASON12 | Recipient No Longer Active         |
      | CORRESPONDENCESTATUSREASON13 | Consumer Request                   |
      | CORRESPONDENCESTATUSREASON14 | No longer appropriate              |
      | CORRESPONDENCESTATUSREASON15 | State requested                    |
      | CORRESPONDENCESTATUSREASON16 | User requested                     |
      | CORRESPONDENCESTATUSREASON17 | Volume control                     |
      | CORRESPONDENCESTATUSREASON18 | Channel issues                     |
      | CORRESPONDENCESTATUSREASON19 | Destination issues                 |
      | CORRESPONDENCESTATUSREASON20 | Template issues                    |
      | CORRESPONDENCESTATUSREASON21 | Vendor issues                      |
    Then I should see an Error Alert Message
      | No search results found |

  @CP-28565-4.5 @ui-ecms2 @Keerthi
  Scenario: Verify Generic Warning Message for Invalid correspondence Search Combination-5
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | STATUS1                      | Requested                          |
      | STATUS2                      | Assembled                          |
      | STATUS3                      | Exported                           |
      | STATUS4                      | Sent                               |
      | CORRESPONDENCESTATUSREASON1  | Undeliverable                      |
      | CORRESPONDENCESTATUSREASON2  | Refused                            |
      | CORRESPONDENCESTATUSREASON3  | Destination Agent Unresponsive     |
      | CORRESPONDENCESTATUSREASON4  | Destination Agent Rejected Message |
      | CORRESPONDENCESTATUSREASON5  | Destination Invalid                |
      | CORRESPONDENCESTATUSREASON6  | Change of Address                  |
      | CORRESPONDENCESTATUSREASON7  | Mailbox Full                       |
      | CORRESPONDENCESTATUSREASON9  | Requested in error                 |
      | CORRESPONDENCESTATUSREASON10 | Unresolvable error                 |
      | CORRESPONDENCESTATUSREASON11 | No Valid Destination               |
      | CORRESPONDENCESTATUSREASON12 | Recipient No Longer Active         |
      | CORRESPONDENCESTATUSREASON13 | Consumer Request                   |
      | CORRESPONDENCESTATUSREASON14 | No longer appropriate              |
      | CORRESPONDENCESTATUSREASON15 | State requested                    |
      | CORRESPONDENCESTATUSREASON16 | User requested                     |
      | CORRESPONDENCESTATUSREASON17 | Volume control                     |
      | CORRESPONDENCESTATUSREASON18 | Channel issues                     |
      | CORRESPONDENCESTATUSREASON19 | Destination issues                 |
      | CORRESPONDENCESTATUSREASON20 | Template issues                    |
      | CORRESPONDENCESTATUSREASON21 | Vendor issues                      |
      | CORRESPONDENCESTATUSREASON23 | Export Error                       |
      | CORRESPONDENCESTATUSREASON24 | Assembly Error                     |
      | CORRESPONDENCESTATUSREASON25 | Invalid Address                    |
    Then I should see an Error Alert Message
      | No search results found |


  @CP-28565-4.6 @ui-ecms2 @Keerthi
  Scenario: Verify Generic Warning Message for Invalid Notification Search Combination-1
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | NOTIFICATIONSTATUS         | On Hold                            |
      | NOTIFICATIONSTATUSREASON1  | Undeliverable                      |
      | NOTIFICATIONSTATUSREASON2  | Refused                            |
      | NOTIFICATIONSTATUSREASON3  | Destination agent unresponsive     |
      | NOTIFICATIONSTATUSREASON4  | Destination agent rejected message |
      | NOTIFICATIONSTATUSREASON5  | Destination invalid                |
      | NOTIFICATIONSTATUSREASON6  | Change of Address                  |
      | NOTIFICATIONSTATUSREASON7  | Mailbox full                       |
      | NOTIFICATIONSTATUSREASON8  | No Valid Destination               |
      | NOTIFICATIONSTATUSREASON9  | Recipient No Longer Active         |
      | NOTIFICATIONSTATUSREASON10 | Consumer Request                   |
      | NOTIFICATIONSTATUSREASON11 | No Longer Appropriate              |
      | NOTIFICATIONSTATUSREASON12 | Requested in Error                 |
      | NOTIFICATIONSTATUSREASON13 | Superseded by Events               |
      | NOTIFICATIONSTATUSREASON14 | Export Error                       |
      | NOTIFICATIONSTATUSREASON15 | Assembly Error                     |
      | NOTIFICATIONSTATUSREASON16 | Invalid Address                    |
    Then I should see an Error Alert Message
      | No search results found |

  @CP-28565-4.7 @ui-ecms2 @Keerthi
  Scenario: Verify Generic Warning Message for Invalid Notification Search Combination-2
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | NOTIFICATIONSTATUS         | Returned                   |
      | NOTIFICATIONSTATUSREASON1  | State requested            |
      | NOTIFICATIONSTATUSREASON2  | User requested             |
      | NOTIFICATIONSTATUSREASON3  | Volume control             |
      | NOTIFICATIONSTATUSREASON4  | Channel issues             |
      | NOTIFICATIONSTATUSREASON5  | Destination issues         |
      | NOTIFICATIONSTATUSREASON6  | Template issues            |
      | NOTIFICATIONSTATUSREASON7  | Vendor issues              |
      | NOTIFICATIONSTATUSREASON8  | No Valid Destination       |
      | NOTIFICATIONSTATUSREASON9  | Recipient No Longer Active |
      | NOTIFICATIONSTATUSREASON10 | Consumer Request           |
      | NOTIFICATIONSTATUSREASON11 | No Longer Appropriate      |
      | NOTIFICATIONSTATUSREASON12 | Requested in Error         |
      | NOTIFICATIONSTATUSREASON13 | Superseded by Events       |
      | NOTIFICATIONSTATUSREASON14 | Export Error               |
      | NOTIFICATIONSTATUSREASON15 | Assembly Error             |
      | NOTIFICATIONSTATUSREASON16 | Invalid Address            |
    Then I should see an Error Alert Message
      | No search results found |

  @CP-28565-4.8 @ui-ecms2 @Keerthi
  Scenario: Verify Generic Warning Message for Invalid Notification Search Combination-3
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | NOTIFICATIONSTATUS         | Canceled                           |
      | NOTIFICATIONSTATUSREASON1  | State requested                    |
      | NOTIFICATIONSTATUSREASON2  | User requested                     |
      | NOTIFICATIONSTATUSREASON3  | Volume control                     |
      | NOTIFICATIONSTATUSREASON4  | Channel issues                     |
      | NOTIFICATIONSTATUSREASON5  | Destination issues                 |
      | NOTIFICATIONSTATUSREASON6  | Template issues                    |
      | NOTIFICATIONSTATUSREASON7  | Vendor issues                      |
      | NOTIFICATIONSTATUSREASON8  | Export Error                       |
      | NOTIFICATIONSTATUSREASON9  | Assembly Error                     |
      | NOTIFICATIONSTATUSREASON10 | Invalid Address                    |
      | NOTIFICATIONSTATUSREASON11 | Undeliverable                      |
      | NOTIFICATIONSTATUSREASON12 | Refused                            |
      | NOTIFICATIONSTATUSREASON13 | Destination agent unresponsive     |
      | NOTIFICATIONSTATUSREASON14 | Destination agent rejected message |
      | NOTIFICATIONSTATUSREASON15 | Destination invalid                |
      | NOTIFICATIONSTATUSREASON16 | Change of Address                  |
      | NOTIFICATIONSTATUSREASON17 | Mailbox full                       |
    Then I should see an Error Alert Message
      | No search results found |

  @CP-28565-4.9 @ui-ecms2 @Keerthi
  Scenario: Verify Generic Warning Message for Invalid Notification Search Combination-4
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | NOTIFICATIONSTATUS         | Error                              |
      | NOTIFICATIONSTATUSREASON1  | State requested                    |
      | NOTIFICATIONSTATUSREASON2  | User requested                     |
      | NOTIFICATIONSTATUSREASON3  | Volume control                     |
      | NOTIFICATIONSTATUSREASON4  | Channel issues                     |
      | NOTIFICATIONSTATUSREASON5  | Destination issues                 |
      | NOTIFICATIONSTATUSREASON6  | Template issues                    |
      | NOTIFICATIONSTATUSREASON7  | Vendor issues                      |
      | NOTIFICATIONSTATUSREASON8  | No Valid Destination               |
      | NOTIFICATIONSTATUSREASON9  | Recipient No Longer Active         |
      | NOTIFICATIONSTATUSREASON10 | Consumer Request                   |
      | NOTIFICATIONSTATUSREASON11 | No Longer Appropriate              |
      | NOTIFICATIONSTATUSREASON12 | Requested in Error                 |
      | NOTIFICATIONSTATUSREASON13 | Superseded by Events               |
      | NOTIFICATIONSTATUSREASON14 | Undeliverable                      |
      | NOTIFICATIONSTATUSREASON15 | Refused                            |
      | NOTIFICATIONSTATUSREASON16 | Destination agent unresponsive     |
      | NOTIFICATIONSTATUSREASON17 | Destination agent rejected message |
      | NOTIFICATIONSTATUSREASON18 | Destination invalid                |
      | NOTIFICATIONSTATUSREASON19 | Change of Address                  |
      | NOTIFICATIONSTATUSREASON20 | Mailbox full                       |
    Then I should see an Error Alert Message
      | No search results found |

  @CP-28565-4.10 @ui-ecms2 @Keerthi
  Scenario: Verify Generic Warning Message for Invalid Notification Search Combination-5
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | NOTIFICATIONSTATUS1        | Requested                          |
      | NOTIFICATIONSTATUS2        | Assembled                          |
      | NOTIFICATIONSTATUS3        | Precluded                          |
      | NOTIFICATIONSTATUS4        | Exported                           |
      | NOTIFICATIONSTATUS5        | Sent                               |
      | NOTIFICATIONSTATUSREASON1  | State requested                    |
      | NOTIFICATIONSTATUSREASON2  | User requested                     |
      | NOTIFICATIONSTATUSREASON3  | Volume control                     |
      | NOTIFICATIONSTATUSREASON4  | Channel issues                     |
      | NOTIFICATIONSTATUSREASON5  | Destination issues                 |
      | NOTIFICATIONSTATUSREASON6  | Template issues                    |
      | NOTIFICATIONSTATUSREASON7  | Vendor issues                      |
      | NOTIFICATIONSTATUSREASON8  | No Valid Destination               |
      | NOTIFICATIONSTATUSREASON9  | Recipient No Longer Active         |
      | NOTIFICATIONSTATUSREASON10 | Consumer Request                   |
      | NOTIFICATIONSTATUSREASON11 | No Longer Appropriate              |
      | NOTIFICATIONSTATUSREASON12 | Requested in Error                 |
      | NOTIFICATIONSTATUSREASON13 | Superseded by Events               |
      | NOTIFICATIONSTATUSREASON14 | Undeliverable                      |
      | NOTIFICATIONSTATUSREASON15 | Refused                            |
      | NOTIFICATIONSTATUSREASON16 | Destination agent unresponsive     |
      | NOTIFICATIONSTATUSREASON17 | Destination agent rejected message |
      | NOTIFICATIONSTATUSREASON18 | Destination invalid                |
      | NOTIFICATIONSTATUSREASON19 | Change of Address                  |
      | NOTIFICATIONSTATUSREASON20 | Mailbox full                       |
      | NOTIFICATIONSTATUSREASON21 | Export Error                       |
      | NOTIFICATIONSTATUSREASON22 | Assembly Error                     |
      | NOTIFICATIONSTATUSREASON23 | Invalid Address                    |
    Then I should see an Error Alert Message
      | No search results found |

  @CP-28565-5.1 @ui-ecms2 @Keerthi
  Scenario: Verify valid correspondence Search Combination for On Hold status
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | STATUS                      | On Hold            |
      | CORRESPONDENCESTATUSREASON1 | State requested    |
      | CORRESPONDENCESTATUSREASON2 | User requested     |
      | CORRESPONDENCESTATUSREASON3 | Volume control     |
      | CORRESPONDENCESTATUSREASON4 | Channel issues     |
      | CORRESPONDENCESTATUSREASON5 | Destination issues |
      | CORRESPONDENCESTATUSREASON6 | Template issues    |
      | CORRESPONDENCESTATUSREASON7 | Vendor issues      |
    Then I should see that all the outbound correspondence search results have the following values
      | STATUS | On Hold |

  @CP-28565-5.2 @ui-ecms2 @Keerthi
  Scenario: Verify valid correspondence Search Combination for Returned status
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | STATUS                      | Returned                           |
      | CORRESPONDENCESTATUSREASON1 | Undeliverable                      |
      | CORRESPONDENCESTATUSREASON2 | Refused                            |
      | CORRESPONDENCESTATUSREASON3 | Destination Agent Unresponsive     |
      | CORRESPONDENCESTATUSREASON4 | Destination Agent Rejected Message |
      | CORRESPONDENCESTATUSREASON5 | Destination Invalid                |
      | CORRESPONDENCESTATUSREASON6 | Change of Address                  |
      | CORRESPONDENCESTATUSREASON7 | Mailbox Full                       |
    Then I should see that all the outbound correspondence search results have the following values
      | STATUS | Returned |

  @CP-28565-5.3 @ui-ecms2 @Keerthi
  Scenario: Verify valid correspondence Search Combination for Canceled status
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | STATUS                      | Canceled                   |
      | CORRESPONDENCESTATUSREASON1 | Requested in error         |
      | CORRESPONDENCESTATUSREASON2 | Unresolvable error         |
      | CORRESPONDENCESTATUSREASON3 | No Valid Destination       |
      | CORRESPONDENCESTATUSREASON4 | Recipient No Longer Active |
      | CORRESPONDENCESTATUSREASON5 | Consumer Request           |
      | CORRESPONDENCESTATUSREASON6 | No longer appropriate      |
    Then I should see that all the outbound correspondence search results have the following values
      | STATUS | Canceled |

  @CP-28565-5.4 @ui-ecms2 @Keerthi
  Scenario: Verify valid correspondence Search Combination for Error status
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    When I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values Without Wait
      | STATUS                      | Error           |
      | CORRESPONDENCESTATUSREASON1 | Export Error    |
      | CORRESPONDENCESTATUSREASON2 | Assembly Error  |
      | CORRESPONDENCESTATUSREASON3 | Invalid Address |
    Then I should see that all the outbound correspondence search results have the following values
      | STATUS | Error |

  @CP-28565-5.5 @ui-ecms2 @Keerthi
  Scenario Outline: Verify valid correspondence Search for Requested,Assembled,Exported and Sent status
    Given I logged into CRM
    And I minimize Genesys popup if populates
    When I click on the main navigation menu
    And I click on the Outbound Correspondence Search icon
    When I search for an Outbound Correspondence by the following values
      | <search> | <value> |
    Then I should see that all the outbound correspondence search results have the following values
      | <search> | <value> |
    Examples:
      | search | value     |
      | STATUS | Requested |
      | STATUS | Assembled |
      | STATUS | Exported  |
      | STATUS | Sent      |
