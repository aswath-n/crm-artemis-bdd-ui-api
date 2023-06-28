Feature: Edit Contact Record History: Part 6


@CP-18285 @CP-18285-01 @ui-core @crm-regression @khazar
Scenario: Verify Field Dependencies on channel field for inbound contact type
Given I will get the Authentication token for "<projectName>" in "CRM"
Given I send API CALL for Create Consumer with Data
| consumerIdentificationNumber[0].externalConsumerId | npi:: |
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I get oneLoginJWT token
Then I send API CALL for create CONTACT Record after creating new Consumer with data
| consumerPhone             | phone::    |
| consumerEmail             | email::    |
| contactType               | General    |
| consumerFirstName         | name::     |
| consumerLastName          | name::     |
| organizationName          | name::     |
| contactTranslationService | <language> |
| contactRecordType         | Inbound    |
Then I navigate to Contact Record Search Page
Then Wait for 6 seconds
Then I will verify search with Phone number
Then I clicking on first Contact Record
When I click on edit icon the Contact Details page
And I verify fields dependencies on channel field for inbound contact type
| Email            |
| Phone            |
| IVR Self-Service |
| SMS Text         |
| Web Chat         |
| Web Portal       |
| Mobile App       |

@CP-18285 @CP-18285-02 @ui-core @crm-regression @khazar
Scenario: Verify Field Dependencies on channel field for outbound contact type
Given I will get the Authentication token for "<projectName>" in "CRM"
Given I send API CALL for Create Consumer with Data
| consumerIdentificationNumber[0].externalConsumerId | npi:: |
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I get oneLoginJWT token
Then I send API CALL for create CONTACT Record after creating new Consumer with data
| consumerPhone             | phone::    |
| consumerEmail             | email::    |
| contactType               | General    |
| consumerFirstName         | name::     |
| consumerLastName          | name::     |
| organizationName          | name::     |
| contactTranslationService | <language> |
| contactRecordType         | Outbound   |
Then I navigate to Contact Record Search Page
Then Wait for 6 seconds
Then I will verify search with Phone number
Then I clicking on first Contact Record
When I click on edit icon the Contact Details page
And I verify fields dependencies on channel field for outbound contact type
| Email            |
| Phone            |
| SMS Text         |
| Web Chat         |
| IVR Self-Service |
| Mobile App       |
| Web Portal       |

@CP-18285 @CP-18285-03 @ui-core @crm-regression @khazar
Scenario: Verify values of Disposition when Outcome of Contact is empty
Given I will get the Authentication token for "<projectName>" in "CRM"
Given I send API CALL for Create Consumer with Data
| consumerIdentificationNumber[0].externalConsumerId | npi:: |
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I get oneLoginJWT token
Then I send API CALL for create CONTACT Record after creating new Consumer with data
| consumerPhone             | phone::    |
| consumerEmail             | email::    |
| contactType               | General    |
| consumerFirstName         | name::     |
| consumerLastName          | name::     |
| organizationName          | name::     |
| contactTranslationService | <language> |
| contactRecordType         | Outbound   |
Then I navigate to Contact Record Search Page
Then Wait for 6 seconds
Then I will verify search with Phone number
Then I clicking on first Contact Record
When I click on edit icon the Contact Details page
Then I changed the Outcome of Contact to none selected
Then I validate "contactRecordStatusType" single select drop down values
| Cancelled           |
| Complete            |
| Dropped             |
| Escalate            |
| Outbound Incomplete |
| Requested Call Back |
| Transfer            |

@CP-18285 @CP-18285-04 @ui-core @crm-regression @khazar
Scenario: Verify values of Disposition when Outcome of Contact is Reached Successfully
Given I will get the Authentication token for "<projectName>" in "CRM"
Given I send API CALL for Create Consumer with Data
| consumerIdentificationNumber[0].externalConsumerId | npi:: |
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I get oneLoginJWT token
Then I send API CALL for create CONTACT Record after creating new Consumer with data
| consumerPhone             | phone::    |
| consumerEmail             | email::    |
| contactType               | General    |
| consumerFirstName         | name::     |
| consumerLastName          | name::     |
| organizationName          | name::     |
| contactTranslationService | <language> |
| contactRecordType         | Outbound   |
Then I navigate to Contact Record Search Page
Then Wait for 6 seconds
Then I will verify search with Phone number
Then I clicking on first Contact Record
When I click on edit icon the Contact Details page
Then I changed the Outcome of Contact to "Reached Successfully"
Then I validate "contactRecordStatusType" single select drop down values
| Cancelled           |
| Complete            |
| Dropped             |
| Escalate            |
| Requested Call Back |
| Transfer            |

@CP-18285 @CP-18285-05 @ui-core @crm-regression @khazar
Scenario: Verify disposition value cleared out when Outcome of Contact is changed to Reached Successfully
Given I will get the Authentication token for "<projectName>" in "CRM"
Given I send API CALL for Create Consumer with Data
| consumerIdentificationNumber[0].externalConsumerId | npi:: |
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I get oneLoginJWT token
Then I send API CALL for create CONTACT Record after creating new Consumer with data
| consumerPhone             | phone::    |
| consumerEmail             | email::    |
| contactType               | General    |
| consumerFirstName         | name::     |
| consumerLastName          | name::     |
| organizationName          | name::     |
| contactTranslationService | <language> |
| contactRecordType         | Outbound   |
Then I navigate to Contact Record Search Page
Then Wait for 6 seconds
Then I will verify search with Phone number
Then I clicking on first Contact Record
When I click on edit icon the Contact Details page
Then I select the Disposition value "Outbound Incomplete"
Then I changed the Outcome of Contact to "Reached Successfully"
Then I verify the Disposition selection is cleared

@CP-18285 @CP-18285-06 @ui-core @crm-regression @khazar
Scenario: Verify values of Disposition when Outcome of Contact is Did Not Reach
Given I will get the Authentication token for "<projectName>" in "CRM"
Given I send API CALL for Create Consumer with Data
| consumerIdentificationNumber[0].externalConsumerId | npi:: |
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I get oneLoginJWT token
Then I send API CALL for create CONTACT Record after creating new Consumer with data
| consumerPhone             | phone::    |
| consumerEmail             | email::    |
| contactType               | General    |
| consumerFirstName         | name::     |
| consumerLastName          | name::     |
| organizationName          | name::     |
| contactTranslationService | <language> |
| contactRecordType         | Outbound   |
Then I navigate to Contact Record Search Page
Then Wait for 6 seconds
Then I will verify search with Phone number
Then I clicking on first Contact Record
When I click on edit icon the Contact Details page
Then I changed the Outcome of Contact to "Invalid Phone Number"
Then I validate "contactRecordStatusType" single select drop down values
| Cancelled           |
| Complete            |
| Dropped             |
| Escalate            |
| Outbound Incomplete |
| Requested Call Back |
| Transfer            |

@CP-18285 @CP-18285-07 @ui-core @crm-regression @khazar
Scenario: Disposition list values are impacted by Contact Type:Inbound
Given I will get the Authentication token for "<projectName>" in "CRM"
Given I send API CALL for Create Consumer with Data
| consumerIdentificationNumber[0].externalConsumerId | npi:: |
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I get oneLoginJWT token
Then I send API CALL for create CONTACT Record after creating new Consumer with data
| consumerPhone             | phone::    |
| consumerEmail             | email::    |
| contactType               | General    |
| consumerFirstName         | name::     |
| consumerLastName          | name::     |
| organizationName          | name::     |
| contactTranslationService | <language> |
| contactRecordType         | Inbound    |
Then I navigate to Contact Record Search Page
Then Wait for 6 seconds
Then I will verify search with Phone number
Then I clicking on first Contact Record
When I click on edit icon the Contact Details page
Then I validate "contactRecordStatusType" single select drop down values
| Cancelled           |
| Complete            |
| Dropped             |
| Escalate            |
| Incomplete          |
| Requested Call Back |
| Transfer            |

@CP-18285 @CP-18285-08 @ui-core @crm-regression @khazar
Scenario: Disposition list values are impacted by Contact Type:Outbound
Given I will get the Authentication token for "<projectName>" in "CRM"
Given I send API CALL for Create Consumer with Data
| consumerIdentificationNumber[0].externalConsumerId | npi:: |
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
And I get oneLoginJWT token
Then I send API CALL for create CONTACT Record after creating new Consumer with data
| consumerPhone             | phone::    |
| consumerEmail             | email::    |
| contactType               | General    |
| consumerFirstName         | name::     |
| consumerLastName          | name::     |
| organizationName          | name::     |
| contactTranslationService | <language> |
| contactRecordType         | Outbound   |
Then I navigate to Contact Record Search Page
Then Wait for 6 seconds
Then I will verify search with Phone number
Then I clicking on first Contact Record
When I click on edit icon the Contact Details page
Then I validate "contactRecordStatusType" single select drop down values
| Cancelled           |
| Complete            |
| Dropped             |
| Escalate            |
| Outbound Incomplete |
| Requested Call Back |
| Transfer            |

@CP-46054 @ui-core @crm-regression @khazar
Scenario: Adding and Editing Additional Comments for BLCRM
Given I will get the Authentication token for "BLCRM" in "CRM"
Given I send API CALL for Create Consumer with Data
| consumerIdentificationNumber[0].externalConsumerId | npi:: |
Given I logged into CRM with "Service Account 1" and select a project "BLCRM"
When I click on initiate contact record
Then When I enter Search criteria fields for a newly created consumer
And I click on Search Button on Search Consumer Page
And I link the contact to an existing Case or Consumer Profile
And I choose a contact reason from reason dropdown list
Then I choose a contact action from action dropdown list
And I enter additional comments "First additional comment"
Then I click save button for reason and action
When I enter contact phone number "1327894561"
And I select contact program type as "Medicaid"
Then I click on the contact dispotions "Complete"
When I click End Contact
And I click save button Active contact
When I navigate to manual contact record search page
And I search contact record with phone number and click on the first record
When I click on edit button on contact details tab
Then I verify that saved additional comments is displayed
Then I will edit the saved additional comments and the new value is saved and displayed
