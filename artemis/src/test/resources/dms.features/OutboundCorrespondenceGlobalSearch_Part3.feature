Feature: Outbound Correspondence Global Search part 3

  @CP-28565 @CP-28565-01 @API-ECMS @RuslanL
  Scenario Outline: Verify the search results in response when providing correct Search Combination for Correspondence Status On Hold
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to search Outbound Correspondence for the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Examples:
      | searchStatus | status  | searchStatusReason         | correspondenceStatusReason |
      | status       | On Hold | correspondenceStatusReason | State requested            |
      | status       | On Hold | correspondenceStatusReason | User requested             |
      | status       | On Hold | correspondenceStatusReason | Volume control             |
      | status       | On Hold | correspondenceStatusReason | Channel issues             |
      | status       | On Hold | correspondenceStatusReason | Destination issues         |
      | status       | On Hold | correspondenceStatusReason | Template issues            |
      | status       | On Hold | correspondenceStatusReason | Vendor issues              |

  @CP-28565 @CP-28565-02 @API-ECMS @RuslanL
  Scenario Outline: Verify the search results in response when providing correct Search Combination for Correspondence Status Returned
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to search Outbound Correspondence for the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Examples:
      | searchStatus | status   | searchStatusReason         | correspondenceStatusReason         |
      | status       | Returned | correspondenceStatusReason | Undeliverable                      |
      | status       | Returned | correspondenceStatusReason | Refused                            |
      | status       | Returned | correspondenceStatusReason | Destination agent unresponsive     |
      | status       | Returned | correspondenceStatusReason | Destination agent rejected message |
      | status       | Returned | correspondenceStatusReason | Destination invalid                |
      | status       | Returned | correspondenceStatusReason | Change of Address                  |
      | status       | Returned | correspondenceStatusReason | Mailbox full                       |

  @CP-28565 @CP-28565-03 @API-ECMS @RuslanL
  Scenario Outline: Verify the search results in response when providing correct Search Combination for Correspondence Status Canceled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to search Outbound Correspondence for the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Examples:
      | searchStatus | status   | searchStatusReason         | correspondenceStatusReason |
      | status       | Canceled | correspondenceStatusReason | Superseded by Events       |
      | status       | Canceled | correspondenceStatusReason | Requested in Error         |
      | status       | Canceled | correspondenceStatusReason | No Valid Destination       |
      | status       | Canceled | correspondenceStatusReason | Recipient No Longer Active |
      | status       | Canceled | correspondenceStatusReason | Consumer Request           |
      | status       | Canceled | correspondenceStatusReason | No Longer Appropriate      |

  @CP-28565 @CP-28565-04 @API-ECMS @RuslanL
  Scenario Outline: Verify the search results in response when providing correct search combination for Correspondence Status Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to search Outbound Correspondence for the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Examples:
      | searchStatus | status | searchStatusReason         | correspondenceStatusReason |
      | status       | Error  | correspondenceStatusReason | Export Error               |
      | status       | Error  | correspondenceStatusReason | Assembly Error             |
      | status       | Error  | correspondenceStatusReason | Invalid address            |

  @CP-28565 @CP-28565-05 @API-ECMS @RuslanL
  Scenario Outline: Verify the search results in response when providing correct Search Combination for Notification Status On Hold
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to search Outbound Correspondence for the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Examples:
      | searchStatus       | status  | searchStatusReason       | correspondenceStatusReason |
      | notificationStatus | On Hold | notificationStatusReason | State requested            |
      | notificationStatus | On Hold | notificationStatusReason | User requested             |
      | notificationStatus | On Hold | notificationStatusReason | Volume control             |
      | notificationStatus | On Hold | notificationStatusReason | Channel issues             |
      | notificationStatus | On Hold | notificationStatusReason | Destination issues         |
      | notificationStatus | On Hold | notificationStatusReason | Template issues            |
      | notificationStatus | On Hold | notificationStatusReason | Vendor issues              |

  @CP-28565 @CP-28565-06 @API-ECMS @RuslanL
  Scenario Outline: Verify the search results in response when providing correct Search Combination for Notification Status Returned
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to search Outbound Correspondence for the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Examples:
      | searchStatus       | status   | searchStatusReason       | correspondenceStatusReason         |
      | notificationStatus | Returned | notificationStatusReason | Undeliverable                      |
      | notificationStatus | Returned | notificationStatusReason | Refused                            |
      | notificationStatus | Returned | notificationStatusReason | Destination agent unresponsive     |
      | notificationStatus | Returned | notificationStatusReason | Destination agent rejected message |
      | notificationStatus | Returned | notificationStatusReason | Destination invalid                |
      | notificationStatus | Returned | notificationStatusReason | Change of Address                  |
      | notificationStatus | Returned | notificationStatusReason | Mailbox full                       |

  @CP-28565 @CP-28565-07 @API-ECMS @RuslanL
  Scenario Outline: Verify the search results in response when providing correct Search Combination for Notification Status Canceled
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to search Outbound Correspondence for the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Examples:
      | searchStatus       | status   | searchStatusReason       | correspondenceStatusReason |
      | notificationStatus | Canceled | notificationStatusReason | Requested in Error         |
      | notificationStatus | Canceled | notificationStatusReason | No Valid Destination       |
      | notificationStatus | Canceled | notificationStatusReason | Recipient No Longer Active |
      | notificationStatus | Canceled | notificationStatusReason | Consumer Request           |
      | notificationStatus | Canceled | notificationStatusReason | No Longer Appropriate      |
      | notificationStatus | Canceled | notificationStatusReason | Superseded by Events       |

  @CP-28565 @CP-28565-08 @API-ECMS @RuslanL
  Scenario Outline: Verify the search results in response when providing correct search combination for Notification Status Error
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a request to search Outbound Correspondence for the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Then I should see that the results from Outbound Correspondence will only contain the following values
      | <searchStatus>       | <status>                     |
      | <searchStatusReason> | <correspondenceStatusReason> |
    Examples:
      | searchStatus       | status | searchStatusReason       | correspondenceStatusReason |
      | notificationStatus | Error  | notificationStatusReason | Export Error               |
      | notificationStatus | Error  | notificationStatusReason | Assembly Error             |
      | notificationStatus | Error  | notificationStatusReason | Invalid Address            |


#  below scenarios commented out as ac 3.0 was removed from story
#  @CP-28565 @CP-28565-09 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects invalid search combination for Correspondence Status On Hold
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "invalid" search combination for "Correspondence" Status "On Hold"
#
#  @CP-28565 @CP-28565-10 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects invalid search combination for Correspondence Status On Hold
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "invalid" search combination for "Correspondence" Status "Returned"
#
#  @CP-28565 @CP-28565-11 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects invalid search combination for Correspondence Status On Hold
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "invalid" search combination for "Correspondence" Status "Canceled"
#
#  @CP-28565 @CP-28565-12 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects invalid search combination for Correspondence Status On Hold
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "invalid" search combination for "Correspondence" Status "Error"
#
#  @CP-28565 @CP-28565-13 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects invalid search combination for any multiple Correspondence Statuses
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "invalid" search combination for "Correspondence" Status "multiple"
#
#  @CP-28565 @CP-28565-14 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects invalid search combination for Notification Status On Hold
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "invalid" search combination for "Notification" Status "On Hold"
#
#  @CP-28565 @CP-28565-15 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects invalid search combination for Notification Status On Hold
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "invalid" search combination for "Notification" Status "Returned"
#
#  @CP-28565 @CP-28565-16 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects invalid search combination for Notification Status On Hold
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "invalid" search combination for "Notification" Status "Canceled"
#
#  @CP-28565 @CP-28565-17 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects invalid search combination for Notification Status On Hold
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "invalid" search combination for "Notification" Status "Error"
#
#  @CP-28565 @CP-28565-18 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects invalid search combination for any multiple Notification Statuses
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "invalid" search combination for "Notification" Status "multiple"
#
#  @CP-28565 @CP-28565-19 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects valid search combination for any multiple Correspondence Statuses
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "valid" search combination for "Correspondence" Status "multiple"
#
#  @CP-28565 @CP-28565-20 @API-ECMS @RuslanL
#  Scenario: Verify response when user selects valid search combination for any multiple Notification Statuses
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    And I verify all possible "valid" search combination for "Notification" Status "multiple"





