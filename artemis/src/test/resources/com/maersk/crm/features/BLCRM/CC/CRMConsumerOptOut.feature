Feature: Consumer Opt Out Feature

  @CP-4390 @CP-4390-01.1 @asad @events
  Scenario: Capture Channel Opt in : PHONE
    Given I logged into CRM
    When I create consumer for Contact Record for Consumer "phone" Opt in
    And I initiate Consumer Events API to check from "CONSUMER_SAVE_EVENT" Consumer Opt in
    Then I validate Consumer "phone" Opt in details from "CONSUMER_SAVE_EVENT" for the Consumer

  @CP-4390 @CP-4390-01.2 @asad @events
  Scenario: Capture Channel Opt in : TEXT
    Given I logged into CRM
    When I create consumer for Contact Record for Consumer "text" Opt in
    And I initiate Consumer Events API to check from "CONSUMER_SAVE_EVENT" Consumer Opt in
    Then I validate Consumer "text" Opt in details from "CONSUMER_SAVE_EVENT" for the Consumer

  @CP-4390 @CP-4390-01.3 @asad @events
  Scenario: Capture Channel Opt in : EMAIL
    Given I logged into CRM
    When I create consumer for Contact Record for Consumer "email" Opt in
    And I initiate Consumer Events API to check from "CONSUMER_SAVE_EVENT" Consumer Opt in
    Then I validate Consumer "email" Opt in details from "CONSUMER_SAVE_EVENT" for the Consumer

  @CP-4390 @CP-4390-01.4 @asad @events
  Scenario: Capture Channel Opt in : FAX
    Given I logged into CRM
    When I create consumer for Contact Record for Consumer "fax" Opt in
    And I initiate Consumer Events API to check from "CONSUMER_SAVE_EVENT" Consumer Opt in
    Then I validate Consumer "fax" Opt in details from "CONSUMER_SAVE_EVENT" for the Consumer

  @CP-4390 @CP-4390-01.5 @asad @events
  Scenario: Capture Channel Opt in : MAIL
    Given I logged into CRM
    When I create consumer for Contact Record for Consumer "mail" Opt in
    And I initiate Consumer Events API to check from "CONSUMER_SAVE_EVENT" Consumer Opt in
    Then I validate Consumer "mail" Opt in details from "CONSUMER_SAVE_EVENT" for the Consumer
