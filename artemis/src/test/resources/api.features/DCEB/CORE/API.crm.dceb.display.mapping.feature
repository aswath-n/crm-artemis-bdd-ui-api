@ui-core-dc-eb
Feature: DCEB API Mapping Configuration

  @API-CP-45384 @API-CP-45384-01 @API-CRM @API-CORE @API-CRM-Regression @Araz
     # CP-45384(AC 1.0)
  Scenario Outline: BE : Display Mapping Configuration-Conditional Display Field
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiate GET ContactRecord Display Fields API and validate status code
    Then I verify the following fields "effectiveStartDate" "createdBy" "createdOn" "updatedBy" "updatedOn" in response
    Examples:
      | projectName |
      | DC-EB       |

  @API-CP-45384 @API-CP-45384-02 @API-CRM @API-CORE @API-CRM-Regression @Araz
    # CP-45384(AC 2.0)
  Scenario Outline: BE : Display Mapping Configuration-Conditional Display Field - EffectiveEndDate
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiate GET ContactRecord Display Fields API and validate status code
    And I verify the following field "effectiveEndDate" in response
    Examples:
      | projectName |
      | DC-EB       |

  @API-CP-47860 @API-CP-47860-04 @API-CRM @API-CORE @API-CRM-Regression @sang
    # CP-47860(AC 1.0)
  Scenario: Validate the migration of ENUM CONTACT RECORD ACTION TYPE to contact record display fields DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate and send GET ENUM "ENUM_CONTACT_RECORD_ACTION_TYPE" API
    And I validate the "ENUM_CONTACT_RECORD_ACTION_TYPE" scope and values have migrated to the contact record display fields mapping for "DCEB"

  @API-CP-47860 @API-CP-47860-05 @API-CRM @API-CORE @API-CRM-Regression @sang
    # CP-47860(AC 1.0)
  Scenario: Validate the migration of ENUM CONTACT RECORD ACTION REASON to contact record display fields DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate and send GET ENUM "ENUM_CONTACT_RECORD_ACTION_REASON" API
    And I validate the "ENUM_CONTACT_RECORD_ACTION_REASON" scope and values have migrated to the contact record display fields mapping for "DCEB"

  @API-CP-47860 @API-CP-47860-06 @API-CRM @API-CORE @API-CRM-Regression @sang
    # CP-47860(AC 1.0)
  Scenario: Validate the migration of ENUM CONTACT RECORD BUSINESS UNIT to contact record display fields DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate and send GET ENUM "ENUM_CONTACT_RECORD_BUSINESS_UNIT" API
    And I validate the "ENUM_CONTACT_RECORD_BUSINESS_UNIT" scope and values have migrated to the contact record display fields mapping for "DCEB"

  @API-CP-47795 @API-CP-47795-04 @API-CRM @API-CORE @API-CRM-Regression @sang
    # CP-47795(AC 1.0)
  Scenario: Validate the migration of ENUM CONSUMER TYPE to contact record display fields DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate and send GET ENUM "ENUM_CONSUMER_TYPE" API
    And I validate the "ENUM_CONSUMER_TYPE" scope and values have migrated to the contact record display fields mapping for "DCEB"

  @API-CP-47795 @API-CP-47795-05 @API-CRM @API-CORE @API-CRM-Regression @sang
    # CP-47795(AC 1.0)
  Scenario: Validate the migration of ENUM CONTACT RECORD ELECTRONIC SIGNATURE to contact record display fields DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate and send GET ENUM "ENUM_CONTACT_RECORD_ELECTRONIC_SIGNATURE" API
    And I validate the "ENUM_CONTACT_RECORD_ELECTRONIC_SIGNATURE" scope and values have migrated to the contact record display fields mapping for "DCEB"

  @API-CP-47795 @API-CP-47795-06 @API-CRM @API-CORE @API-CRM-Regression @sang
    # CP-47795(AC 1.0)
  Scenario: Validate the migration of ENUM CONTACT TRANSLATION SERVICE to contact record display fields DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate and send GET ENUM "ENUM_CONTACT_TRANSLATION_SERVICE" API
    And I validate the "ENUM_CONTACT_TRANSLATION_SERVICE" scope and values have migrated to the contact record display fields mapping for "DCEB"

  @API-CP-47794 @API-CP-47794-05 @API-CRM @API-CORE @API-CRM-Regression @sang
    # CP-47795(AC 1.0)
  Scenario: Validate the migration of ENUM PROGRAM TYPE to contact record display fields DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate and send GET ENUM "ENUM_PROGRAM_TYPE" API
    And I validate the "ENUM_PROGRAM_TYPE" scope and values have migrated to the contact record display fields mapping for "DCEB"

  @API-CP-47794 @API-CP-47794-06 @API-CRM @API-CORE @API-CRM-Regression @sang
    # CP-47795(AC 1.0)
  Scenario: Validate the migration of ENUM INBOUND CALL QUEUE to contact record display fields DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate and send GET ENUM "ENUM_INBOUND_CALL_QUEUE" API
    And I validate the "ENUM_INBOUND_CALL_QUEUE" scope and values have migrated to the contact record display fields mapping for "DCEB"

  @API-CP-47794 @API-CP-47794-07 @API-CRM @API-CORE @API-CRM-Regression @sang
    # CP-47795(AC 1.0)
  Scenario: Validate the migration of ENUM CONTACT RECORD LANGUAGE TYPE to contact record display fields DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate and send GET ENUM "ENUM_CONTACT_RECORD_LANGUAGE_TYPE" API
    And I validate the "ENUM_CONTACT_RECORD_LANGUAGE_TYPE" scope and values have migrated to the contact record display fields mapping for "DCEB"

  @API-CP-47794 @API-CP-47794-08 @API-CRM @API-CORE @API-CRM-Regression @sang
    # CP-47795(AC 1.0)
  Scenario: Validate the migration of ENUM CONTACT RECORD COMPAIGN TYPE to contact record display fields DCEB
    Given I will get the Authentication token for "DC-EB" in "CRM"
    And I initiate and send GET ENUM "ENUM_CONTACT_RECORD_COMPAIGN_TYPE" API
    And I validate the "ENUM_CONTACT_RECORD_COMPAIGN_TYPE" scope and values have migrated to the contact record display fields mapping for "DCEB"