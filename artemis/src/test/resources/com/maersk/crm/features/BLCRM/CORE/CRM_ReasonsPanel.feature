Feature: Active Contact Reasons Actions Comments

  @CP-45711 @CP-45711-03 @ui-core @crm-regression @sang
  # CP-45711(AC 1.0)
  Scenario: Verify BLCRM Contact Reasons dropdown values
    Given I logged into CRM and click on initiate contact
    Then I validate the "CONTACT REASONS" select dropdown with the following select values in the REASONS panel
      | Complaint - Account Access     |
      | Complaint - Benefits Issues    |
      | Complaint - Civil Rights       |
      | Complaint - Customer Service   |
      | Enrollment                     |
      | Enrollment - Family Dental     |
      | Enrollment - Pediatric Dental  |
      | Enrollment - QHP/QDP           |
      | Enrollment - QHP/QDP-APTC      |
      | Enrollment - WAH/Medicaid      |
      | Information Request            |
      | Materials Request              |
      | Missing Information Request    |
      | Other                          |
      | Update Info - Family Dental    |
      | Update Info - Pediatric Dental |
      | Update Info - QHP/QDP          |
      | Update Info - QHP/QDP-APTC     |
      | Update Info - WAH/Medicaid     |
      | Update Information Request     |
