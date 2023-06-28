Feature: Contact Details Panel

  @CP-45711 @CP-45711-01 @ui-core @crm-regression @sang
  # CP-45711(AC 1.0)
  Scenario: Verify Contact Details Dropdown values for Inbound Contact Type
    Given I logged into CRM and click on initiate contact
    And I validate the following default values are displayed for CONTACT DETAILS
      | CONTACT TYPE | CHANNEL |
      | Inbound      | Phone   |
    Then I validate the "CONTACT TYPE" select dropdown with the following select values in the Contact Details panel
      | Inbound | Outbound |
    Then I validate the "CHANNEL" select dropdown with the following select values in the Contact Details panel
      | Email | Mobile App | Phone | SMS Text | Web Chat | Web Portal |
    Then I validate the "INBOUND CALL QUEUE" select dropdown with the following select values in the Contact Details panel
      |  | Claims | Eligibility | Enrollment | General Program Questions |
    Then I validate the "PROGRAM" select dropdown with the following select values in the Contact Details panel
      | Medicaid | Program A | Program B | Program C |
    Then I validate the "TRANSLATION SERVICE" select dropdown with the following select values in the Contact Details panel
      |  | English | Other | Russian | Spanish | Vietnamese |

  @CP-45711 @CP-45711-02 @ui-core @crm-regression @sang
    # CP-45711(AC 1.0)
  Scenario: Verify Contact Details Dropdown values for Outbound Contact Type
    Given I logged into CRM and click on initiate contact
    And I click on the Contact Type "Outbound"
    And I validate the following default values are displayed for CONTACT DETAILS
      | CONTACT TYPE | CHANNEL |
      | Outbound      | Phone   |
    Then I validate the "CALL CAMPAIGN" select dropdown with the following select values in the Contact Details panel
      |  | Enrollment Reminder | Payment Reminder | Program Information |
    Then I validate the "CHANNEL" select dropdown with the following select values in the Contact Details panel
      | Email | Mobile App | Phone | SMS Text | Web Chat | Web Portal |
    Then I validate the "OUTCOME OF CONTACT" select dropdown with the following select values in the Contact Details panel
      |  | Did Not Reach/Left Voicemail | Did Not Reach/No Voicemail | Invalid Phone Number | Reached Successfully |
    Then I validate the "PROGRAM" select dropdown with the following select values in the Contact Details panel
      | Medicaid | Program A | Program B | Program C |
    Then I validate the "TRANSLATION SERVICE" select dropdown with the following select values in the Contact Details panel
      |  | English | Other | Russian | Spanish | Vietnamese |

