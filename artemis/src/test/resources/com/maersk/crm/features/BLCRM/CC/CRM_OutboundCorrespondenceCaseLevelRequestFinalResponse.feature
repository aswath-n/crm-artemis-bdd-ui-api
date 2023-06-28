Feature: Outbound Correspondence Request - Final Response - Case Level request

  @API-CP-885 @API-CP-885-01 @API-CC @API-CRM-Regression @umid
  Scenario:Verify Primary indicator for recipient
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 8544  |
      | channels[0] | Mail  |
      | channels[1] | Text  |
      | channels[2] | Email |
      | channels[3] | Fax   |
    And I verify NONdefault "true" and usability flags for channels "Mail,Email,Fax,Text"

  @API-CP-885 @API-CP-885-02 @API-CC @API-CRM-Regression @umid
  Scenario:Determine Channel and details Mail
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 8544 |
      | channels[0] | Mail |
    And I verify NONdefault "true" and usability flags for channels "Mail"

  @API-CP-885 @API-CP-885-03 @API-CC @API-CRM-Regression @umid
  Scenario:Determine Channel and details Text
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 8544 |
      | channels[0] | Text |
    And I verify NONdefault "true" and usability flags for channels "Text"

  @API-CP-885 @API-CP-885-04 @API-CC @API-CRM-Regression @umid
  Scenario:Determine Channel and details Email
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 8544  |
      | channels[0] | Email |
    And I verify NONdefault "true" and usability flags for channels "Email"

  @API-CP-885 @API-CP-885-05 @API-CC @API-CRM-Regression @umid
  Scenario:Determane Channel and details Fax
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 8544 |
      | channels[0] | Fax  |
    And I verify NONdefault "true" and usability flags for channels "Fax"

  @API-CP-885 @API-CP-885-06 @API-CC @API-CRM-Regression @umid
  Scenario:Determine Channel and details with multiple channels
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 8544  |
      | channels[0] | Mail  |
      | channels[2] | Email |
    And I verify NONdefault "true" and usability flags for channels "Mail,Email"

  @API-CP-885 @API-CP-885-07 @API-CC @API-CRM-Regression @umid
  Scenario:Determine List of Recipients
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 8544  |
      | channels[0] | Mail  |
      | channels[1] | Text  |
      | channels[2] | Email |
      | channels[3] | Fax   |
    And I verify NONdefault "true" and usability flags for channels "Mail,Email,Fax,Text"

  @API-CP-885 @API-CP-885-08 @API-CC @API-CRM-Regression @umid
  Scenario:Determine only Default List of Recipients #1
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 8544  |
      | channels[0] | Mail  |
      | channels[1] | Text  |
      | channels[2] | Email |
      | channels[3] | Fax   |
      | includeNonDefaultRecipients | False   |
    And I verify NONdefault "false" and usability flags for channels "Mail,Email,Fax,Text"

  @API-CP-885 @API-CP-885-09 @API-CC @API-CRM-Regression @umid
  Scenario:Validate destinations are not usable when consumers on case have no ACTIVE phone, email, mail, fax
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 98187   |
      | channels[0] | Mail    |
      | channels[1] | Text    |
      | channels[2] | Email   |
      | channels[3] | Fax     |
    And I verify channels are not usable "Mail,Email,Fax,Text"

  @API-CP-885 @API-CP-885-10 @API-CC @API-CRM-Regression @umid
  Scenario:Validate destinations are not usable when consumers on case have no phone, email, mail, fax
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 14744|
      | channels[0] | Mail    |
      | channels[1] | Text    |
      | channels[2] | Email   |
      | channels[3] | Fax     |
    And I verify channels are not usable "Mail,Email,Fax,Text"

  @API-CP-885 @API-CP-885-11 @API-CC @API-CRM-Regression @umid
  Scenario:Validate No recipients when Case has no ACTIVE Consumers
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 11487 |
      | channels[0] | Mail                           |
      | channels[1] | Text                           |
      | channels[2] | Email                          |
      | channels[3] | Fax                            |
    And I verify no recipients are returned in the response

  @API-CP-2016 @API-CP-2016-01 @API-CC @API-CRM-Regression @umid
  Scenario:Determine only Default List of Recipients for Mail
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 135499|
      | channels[0] | Mail  |
      | channels[1] | Text  |
      | channels[2] | Email |
      | channels[3] | Fax   |
      | includeNonDefaultRecipients | False   |
    And I verify channel "Mail" is not usable with "Paperless Preference"

  @API-CP-2016 @API-CP-2016-02 @API-CC @API-CRM-Regression @umid
  Scenario:Determine only Default List of Recipients #2
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
    Then I send API call for Correspondence
      | caseId      | 141570|
      | channels[0] | Mail  |
    And I verify channel "Mail" is usable

    #muted due functionality changed as part of CP-9941 when address object is not null if Paperless Preference is ON
#  @API-CP-2016 @API-CP-2016-03 @API-CC @API-CRM-Regression @umid
#  Scenario:Validate multiple destinations with Paperless with non-default recipients
#    Given I will get the Authentication token for "<projectName>" in "CRM"
#    Then I initiate the Case Correspondence API
#    Then I send API call for Correspondence
#      | caseId      | 141576 |
#      | channels[0] | Mail   |
#      | channels[1] | Tex    |
#      | channels[2] | Email  |
#      | channels[3] | Fax    |
#    And I verify "Mail" channel is usable oly for consumer without "Paperless Preference"

  @API-CP-2016 @API-CP-2016-04 @API-CC @API-CRM-Regression @umid
  Scenario:Validate single destination with Paperless with non-default recipients
    Given I will get the Authentication token for "<projectName>" in "CRM"
    Then I initiate the Case Correspondence API
      Then I send API call for Correspondence
      | caseId      | 141570|
      | channels[0] | Mail  |
    And I verify channel "Mail" is usable

