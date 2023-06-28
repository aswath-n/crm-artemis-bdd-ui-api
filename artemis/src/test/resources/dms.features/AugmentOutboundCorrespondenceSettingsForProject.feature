Feature:Augment Outbound Correspondence Settings for Project

  @CP-2929-01 @CP-2929 @ui-ecms1 @Sean
  Scenario: 1.0 Validate the length of al fields
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Then Validate the all settings fields for length

  @CP-2929-02 @CP-2929 @ui-ecms1 @Sean
  Scenario Outline: Verify that Outbound  Correspondence ECMS Document is required field
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter all except doc type "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    And Click at Save button
    Then Verify that this error message is showing "Outbound Correspondence is required and cannot be left blank."
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | True                                | filename                                | test@gmail.com         | 1231231234                    | 1231231234                  | cust@gmail.com                 | www.web.com              | xyz             | JSON DEFAULT               | abcifti                                    | acvtest                                             |

  @CP-2929-03 @CP-2929 @ui-ecms1 @Sean
  Scenario Outline: Verify that Outbound  Correspondence Template is required field
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter all except doc template "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    When the settings are made available to a microservice interested in them
    And Click at Save button
    Then Verify that this error message is showing "Outbound Correspondence Template is required and cannot be left blank."
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | True                                | filename                                | test@gmail.com         | 1231231234                    | 1231231234                  | cust@gmail.com                 | www.web.com              | xyz             | JSON DEFAULT               | abcifti                                    | acvtest                                             |


  @CP-2929-04 @CP-2929 @ui-ecms1 @Sean
  Scenario Outline: 6.0 Verify that user can save settings without entering optional fields
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter mandatory values "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    And Click at Save button
    Then Verify the success message "Correspondence Configuration successfully updated."
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | False                               | filename                                | test@gmail.com         | 1231231234                    | 1231231234                  | cust@gmail.com                 | www.web.com              | xyz             | JSON DEFAULT               | null                                       | acvtest                                             |

  @CP-2929-05 @CP-2929 @ui-ecms1 @Sean
  Scenario Outline: 5.0 Verify that the Outbound Filename Prefix field can contiain letters, number, and hyphens
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter mandatory values "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    And I enter a mix of 15 letters, numbers, and hyphens in Filename Prefix field
    And Click at Save button
    Then Verify the success message "Correspondence Configuration successfully updated."
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | False                               | filename                                | test@gmail.com         | 1231231234                    | 1231231234                  | cust@gmail.com                 | www.web.com              | xyz             | JSON DEFAULT               | null                                       | acvtest                                             |

  @CP-2929-06 @CP-2929 @ui-ecms1 @Sean
  Scenario Outline: 6.0 Verify that the Outbound Filename Prefix field can not accept special character
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter mandatory values "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    And And I enter a mix of 15 special characters except hyphens
    And Click at Save button
    Then Verify that this error message is showing "Please provide valid Outbound File Name Prefix"
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | False                               | filename                                | test@gmail.com         | 1231231234                    | 1231231234                  | cust@gmail.com                 | www.web.com              | xyz             | JSON DEFAULT               | null                                       | acvtest                                             |


  @CP-2929-07 @CP-2929 @ui-ecms2 @Sean
  Scenario Outline: 7.0 Verify that all entered settings are saved successfully
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    When the settings are made available to a microservice interested in them
    And Click at Save button
    Then I should see the value that is saved for the "<SendCorrespondenceToAdultConsumersRegarding>" (boolean)
    Then I should see the value that is saved for the "<DoNotCombineRecipientsAtSameAddress>" DoNotCombineRecipientsAtSameAddress
    Then I should see the value that is saved for the "<Outbound Correspondence Filename Prefix>" FileNamePrefix
    Then I should see the value that is saved for the  "<Reply To Email Address>" (email)
    Then I should see the value that is saved for the "<Customer Service Phone Number>" (phone)
    Then I should see the value that is saved for the "<Customer Service Fax Number>" (fax)
    Then I should see the value that is saved for the "<Customer Service Email Address>" (customer email)
    Then I should see the value that is saved for the "<Customer Service Web URL>" (URL text)
    Then I should see the value that is saved for the "<Mobile App Name>" Mobile App Name
    Then I should see the value that is saved for the "<Outbound Print File Format>" format
    Then I should see the value that is saved for the "<Outbound Correspondence ECMS Document Type>" DocType
    Then I should see the value that is saved for the "<Outbound Correspondence Template ECMS Document Type>" TemplateType
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | True                                | filename                                | test@gmail.com         | 1231231234                    | 1231231234                  | cust@gmail.com                 | www.web.com              | xyz             | PDF                        | null                                       | acvtest                                             |


  @DMS-2929-08 @CP-2929 @ui-ecms2 @Sean
  Scenario Outline: 8.0 Verify that the following email address will save successfully as valid email addresses in the Reply to Email Address field if it contains special characters
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    And Click at Save button
    Then I should see the value that is saved for the  "<Reply To Email Address>" (email)
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address      | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | True                                | novel                                   | !#$%^&*a1.b2@me-a1.asdf.com | 1231231234                    | 1231231234                  | cust@gmail.com                 | www.web.com              | xyz             | JSON DEFAULT               | null                                       | acvtest                                             |

  @CP-2929-09 @CP-2929 @ui-ecms2 @Sean
  Scenario Outline: 9.0 Verify that the following reply to email addresses will save unsuccessfully if repeated
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    And Click at Save button
    Then Verify that this error message is showing "Email Address format is invalid. Please enter it in format xx@xx.xx"
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address                                   | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | True                                | novel                                   | !#$%^&*a1.b2@me-a1.asdf.com, !#$%^&*a1.b2@me-a1.asdf.com | 1231231234                    | 1231231234                  | cust@gmail.com                 | www.web.com              | xyz             | JSON DEFAULT               | null                                       | acvtest                                             |


  @CP-2929-10 @CP-2929 @ui-ecms2 @Sean
  Scenario Outline: 10.0 Verify that the following email address will save successfully as valid email addresses in the Customer Service Email Address field if it contains special characters
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    And Click at Save button
    Then I should see the value that is saved for the "<Customer Service Email Address>" (customer email)
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address      | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | True                                | novel                                   | !#$%^&*a1.b2@me-a1.asdf.com | 1231231234                    | 1231231234                  | #$%^&*a1.b2@me-a1.asdf.com     | www.web.com              | xyz             | JSON DEFAULT               | null                                       | acvtest                                             |

  @CP-2929-11 @CP-2929 @ui-ecms2 @Sean
  Scenario Outline: 11.0 Verify that the customer email address will not be saved if repeated
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    And Click at Save button
    Then Verify that this error message is showing "Email Address format is invalid. Please enter it in format xx@xx.xx"
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address      | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address                            | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | True                                | novel                                   | !#$%^&*a1.b2@me-a1.asdf.com | 1231231234                    | 1231231234                  | c!#$%^&*a1.b2@me-a1.asdf.com, !#$%^&*a1.b2@me-a1.asdf.com | www.web.com              | xyz             | JSON DEFAULT               | null                                       | acvtest                                             |

  @CP-2929-12 @CP-2929 @ui-ecms2 @Sean
  Scenario Outline: 12.0 Verify that the following web addresses will save successfully as a valid web address in the web address field
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    And Click at Save button
    Then I should see the value that is saved for the "<Customer Service Web URL>" (URL text)
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address      | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | True                                | novel                                   | !#$%^&*a1.b2@me-a1.asdf.com | 1231231234                    | 1231231234                  | #$%^&*a1.b2@me-a1.asdf.com     | 1dom2-2.com              | xyz             | JSON DEFAULT               | null                                       | acvtest                                             |

  @CP-2929-13 @CP-2929 @ui-ecms2 @Sean
  Scenario Outline: 13.0 Verify that web address will not be saved if repeated
    Given I logged into Tenant Manager and set the project context "project" value "CorrespondenceRegression"
    And I navigate to the Correspondence Outbound Configuration Screen of Project:"current"
    And Click at edit button
    Given I will enter "<SendCorrespondenceToAdultConsumersRegarding>","<DoNotCombineRecipientsAtSameAddress>","<Outbound Correspondence Filename Prefix>","<Reply To Email Address>","<Customer Service Phone Number>","<Customer Service Fax Number>","<Customer Service Email Address>","<Customer Service Web URL>","<Mobile App Name>","<Outbound Print File Format>","<Outbound Correspondence ECMS Document Type>","<Outbound Correspondence Template ECMS Document Type>" fields values
    And Click at Save button
    Then Verify that this error message is showing "Web Address format is invalid. Please enter it in format example.com or example.com/path"
    Examples:
      | SendCorrespondenceToAdultConsumersRegarding | DoNotCombineRecipientsAtSameAddress | Outbound Correspondence Filename Prefix | Reply To Email Address      | Customer Service Phone Number | Customer Service Fax Number | Customer Service Email Address | Customer Service Web URL | Mobile App Name | Outbound Print File Format | Outbound Correspondence ECMS Document Type | Outbound Correspondence Template ECMS Document Type |
      | False                                       | True                                | novel                                   | !#$%^&*a1.b2@me-a1.asdf.com | 1231231234                    | 1231231234                  | c!#$%^&*a1.b2@me-a1.asdf.com   | asd-.com, .asdf.com      | xyz             | JSON DEFAULT               | null                                       | acvtest                                             |