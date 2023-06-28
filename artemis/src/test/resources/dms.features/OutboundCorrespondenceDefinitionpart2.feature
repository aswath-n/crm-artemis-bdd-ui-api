Feature: Outbound Correspondence Definition part2

  @CP-41713 @CP-41713-01 @ui-ecms2 @Keerthi
  Scenario:  Validate System dropdown includes CP as option (BLCRM)
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | External Template ID |
    Then I "validate" following fields in OB channel definition Templates grid for External Template ID option
      | External ID | Random             |
      | System      | SendGrid,Twilio,CP |
      | Add         | enable             |
      | Cancel      | enable             |

  @CP-41713 @CP-41713-02 @ui-ecms2 @Keerthi
  Scenario: Validate delete and download Button Available When System is CP (BLCRM)
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add a Correspondence Channel Definition with the channel type of "Email"
    And I "click" Add Template button options
      | Upload Template File |
    Then I select "MultiPageDocumenMail.pdf" file for upload and validate add template
    Then I select following fields in OB channel definition Templates grid
      | Language  | English                     |
      | StartDate | Current_SysDatePlusOneMonth |
      | EndDate   | Current_SysDatePlusOneMonth |
    Then I validate following fields in OB channel definition Templates grid
      | Download | enabled |
      | Delete   | enabled |

  @CP-41713 @CP-41713-03 @ui-ecms2 @Keerthi
  Scenario: Validate System is automatically set to CP (BLCRM)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Then I have active languages for the project "BLCRM"
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | Upload Template File |
    Then I select "MultiPageDocumenMail.pdf" file for upload and validate add template
    Then I validate following fields in OB channel definition Templates grid
      | Template File Name | 1234MultiPageDocumenMail.pdf |
      | System             | CP                           |
      | External ID        | random                       |
      | Version            | 1                            |


  @CP-41713 @CP-41713-1.1 @ui-ecms-ineb @Keerthi
  Scenario:  Validate System dropdown includes CP as option (IN-EB)
    Given I logged into Tenant Manager and set the project context "project" value "IN-EB"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | External Template ID |
    Then I "validate" following fields in OB channel definition Templates grid for External Template ID option
      | External ID | Random             |
      | System      | SendGrid,Twilio,CP |
      | Add         | enable             |
      | Cancel      | enable             |

  @CP-41713 @CP-41713-2.1 @ui-ecms-ineb @Keerthi
  Scenario: Validate delete and download Button Available When System is CP (IN-EB)
    Given I logged into Tenant Manager and set the project context "project" value "IN-EB"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add a Correspondence Channel Definition with the channel type of "Email"
    And I "click" Add Template button options
      | Upload Template File |
    Then I select "MultiPageDocumenMail.pdf" file for upload and validate add template
    Then I select following fields in OB channel definition Templates grid
      | Language  | English                     |
      | StartDate | Current_SysDatePlusOneMonth |
      | EndDate   | Current_SysDatePlusOneMonth |
    Then I validate following fields in OB channel definition Templates grid
      | Download | enabled |
      | Delete   | enabled |

  @CP-41713 @CP-41713-3.1 @ui-ecms-ineb @Keerthi
  Scenario: Validate System is automatically set to CP (IN-EB)
    Given I will get the Authentication token for "IN-EB" in "CRM"
    Then I have active languages for the project "IN-EB"
    Given I logged into Tenant Manager and set the project context "project" value "IN-EB"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | Upload Template File |
    Then I select "MultiPageDocumenMail.pdf" file for upload and validate add template
    Then I validate following fields in OB channel definition Templates grid
      | Template File Name | 1234MultiPageDocumenMail.pdf |
      | System             | CP                           |
      | External ID        | random                       |
      | Version            | 1                            |


  @CP-41713 @CP-41713-1.2 @ui-ecms-dceb @Keerthi
  Scenario:  Validate System dropdown includes CP as option (DC-EB)
    Given I logged into Tenant Manager and set the project context "project" value "DC-EB"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | External Template ID |
    Then I "validate" following fields in OB channel definition Templates grid for External Template ID option
      | External ID | Random             |
      | System      | SendGrid,Twilio,CP |
      | Add         | enable             |
      | Cancel      | enable             |

  @CP-41713 @CP-41713-2.2 @ui-ecms-dceb @Keerthi
  Scenario: Validate delete and download Button Available When System is CP (DC-EB)
    Given I logged into Tenant Manager and set the project context "project" value "DC-EB"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I select an Outbound Correspondence Definition with no Channels
    When I add a Correspondence Channel Definition with the channel type of "Email"
    And I "click" Add Template button options
      | Upload Template File |
    Then I select "MultiPageDocumenMail.pdf" file for upload and validate add template
    Then I select following fields in OB channel definition Templates grid
      | Language  | English                     |
      | StartDate | Current_SysDatePlusOneMonth |
      | EndDate   | Current_SysDatePlusOneMonth |
    Then I validate following fields in OB channel definition Templates grid
      | Download | enabled |
      | Delete   | enabled |

  @CP-41713 @CP-41713-3.2 @ui-ecms-dceb @Keerthi
  Scenario: Validate System is automatically set to CP (DC-EB)
    Given I will get the Authentication token for "DC-EB" in "CRM"
    Then I have active languages for the project "DC-EB"
    Given I logged into Tenant Manager and set the project context "project" value "DC-EB"
    And I navigate to the Correspondence Definitions Screen of Project:"current"
    And I view "first" IB Correspondence Definition type
    And Click at Add Channel button
    And I "click" Add Template button options
      | Upload Template File |
    Then I select "MultiPageDocumenMail.pdf" file for upload and validate add template
    Then I validate following fields in OB channel definition Templates grid
      | Template File Name | 1234MultiPageDocumenMail.pdf |
      | System             | CP                           |
      | External ID        | random                       |
      | Version            | 1                            |


