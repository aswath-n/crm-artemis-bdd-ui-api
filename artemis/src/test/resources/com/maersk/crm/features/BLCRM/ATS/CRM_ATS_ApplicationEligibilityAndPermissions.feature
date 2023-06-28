Feature: ATS Application Eligibility and Related Permissions to Edit

  @CP-22989 @CP-22989-01 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Application Members Eligibility Status is Pending When Applicant Status is set to "Determining" UI Medical Assistace
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I choose "Medicaid" as program type
    Then I choose "CHIP" as program type
    Then I choose "Pregnancy Assistance" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30    |
      | LAST NAME  | Alphabetic 30    |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME        | RANDOM 30        |
      | LAST NAME         | RANDOM 30        |
      | DOB               | random past date |
      | GENDER            | Female           |
      | ARE YOU PREGNANT  | YES              |
      | NO. OF BABIES     | 2                |
      | EXPECTED DUE DATE | RANDOM           |
      | SSN               | Numeric 9        |
    And Select the "CHIP" Program(s) for application member
    And Select the "Medicaid" Program(s) for application member
    And Select the "Pregnancy Assistance" Program(s) for application member
    And click on save button for add application member
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    Then I click on Save button on Create Application Page
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    When I click on application tracking tab to navigate to Application Tracking page
    And I see application Status as "Determining" in the application information
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify  the Eligibility Status of the "Application Member 1" displays as "Pending"
    Then I verify there is no "Eligibility Status" displays for the AUTHORIZED REPRESENTATIVE

  @CP-22989 @CP-22989-02 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Application Members Eligibility Status is "-- --"  When Applicant Status is set to "Determining" while There is no Program Selected UI Medical Assistace
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30 |
      | LAST NAME  | Alphabetic 30 |
      | DOB        | random past date |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    Then I click on Save button on Create Application Page
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    When I click on application tracking tab to navigate to Application Tracking page
    And I see application Status as "Determining" in the application information
    Then I verify I will see "ELIGIBILITY STATUS" as "-- --" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "-- --" for AM on Members Info Panel
    Then I verify there is no "Eligibility Status" displays for the AUTHORIZED REPRESENTATIVE

  @CP-22989 @CP-22989-03 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Application Members Eligibility Status is Pending When Applicant Status is set to "Determining" UI Long Term Care
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "LONG TERM CARE" application page
    Then I choose "HCBS" as program type
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30    |
      | LAST NAME  | Alphabetic 30    |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    When I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME        | RANDOM 30        |
      | LAST NAME         | RANDOM 30        |
      | DOB               | random past date |
      | GENDER            | Female           |
      | ARE YOU PREGNANT  | YES              |
      | NO. OF BABIES     | 2                |
      | EXPECTED DUE DATE | RANDOM           |
      | SSN               | Numeric 9        |
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    When I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    Then I click on Save button on Create Application Page
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    When I click on the New button in the Application Member Matching Page
    When I click on application tracking tab to navigate to Application Tracking page
    And I see application Status as "Determining" in the application information
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel
    Then I verify  the Eligibility Status of the "Application Member 2" displays as "-- --"
    Then I verify there is no "Eligibility Status" displays for the AUTHORIZED REPRESENTATIVE

  @CP-22998 @CP-22998-08 @crm-regression @ui-ats @burak
  Scenario: Verify AUTHORIZED REPRESENTATIVE(S) panel is not displayed When there is no Active Authorized Representative in Application tracking Tab
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | FIRST NAME | RANDOM 30  |
      | LAST NAME  | RANDOM 30  |
      | START DATE | 02/02/2021 |
      | END DATE   | 02/03/2021 |
    Then I click save button to save Authorized Representative
    When I click on application tracking tab to navigate to Application Tracking page
    Then I verify AUTHORIZED REPRESENTATIVE(S) panel is not displayed and application does not have AUTHORIZED REPRESENTATIVE(S)

  @CP-25950 @CP-25950-01 @crm-regression @ui-ats @burak
  Scenario:  Verify Applicant Status is Not Applying and Eligibility Status is null when edit to Not Applying for Status is DETERMINING LONG TERM CARE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 1 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I see applicant status for all applicants as "Determining" in the create application page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel
    Then I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I deselect "HCBS" program type
    Then I choose "NOT APPLYING" as program type
    And  I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I will see "APPLICANT STATUS" as "Not Applying" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "-- --" for PI on Members Info Panel
    Then I verify I will see "PROGRAMS APPLIED FOR" as "-- --" for PI on Members Info Panel
    Then I verify I will see "DETERMINATION DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "START DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "END DATE" as "-- --" for PI on Members Info Panel

  @CP-25950 @CP-25950-02 @crm-regression @ui-ats @burak
  Scenario:  Verify Applicant Status is Not Applying and Eligibility Status is null when edit to Not Applying for Status is DETERMINING MEDICAL ASSISTANCE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I initiate save applications api consumer 1 with program
      | CHIP |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I see applicant status for all applicants as "Determining" in the create application page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel
    Then I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I deselect "Medicaid" program type
    Then I choose "NOT APPLYING" as program type
    And  I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I will see "APPLICANT STATUS" as "Not Applying" for PI on Members Info Panel
    Then I verify  the Eligibility Status of the "Primary Individual" displays as "-- --"
    Then I verify I will see "PROGRAMS APPLIED FOR" as "-- --" for PI on Members Info Panel
    Then I verify I will see "DETERMINATION DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "START DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "END DATE" as "-- --" for PI on Members Info Panel

  @CP-25950 @CP-25950-03 @crm-regression @ui-ats @burak
  Scenario: Verify Applicant Status is Determining and Eligibility Status is Pending when edit to apply for Programs for Status is DETERMINING Long Term Care
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode | notApplyingIndicator |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       | true                 |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode | notApplyingIndicator |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       | true                 |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    Then I verify I will see "APPLICANT STATUS" as "Not Applying" for AM on Members Info Panel
    Then I verify I will see "APPLICANT STATUS" as "Not Applying" for AM on Members Info Panel
    Then I verify  the Eligibility Status of the "Primary Individual" displays as "-- --"
    Then I verify  the Eligibility Status of the "Application Member 1" displays as "-- --"
    Then I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I deselect "Not Applying" program type
    Then I choose "HCBS" as program type
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    And I deselect "Not Applying" program type for Application Member
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify Applicant status "Determining" for PI and AM on application tracking page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel
    Then I verify I will see "PROGRAMS APPLIED FOR" as "HCBS" for PI on Members Info Panel
    Then I verify I will see "DETERMINATION DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "START DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "END DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "PROGRAMS APPLIED FOR" as "HCBS" for AM on Members Info Panel
    Then I verify I will see "DETERMINATION DATE" as "-- --" for AM on Members Info Panel
    Then I verify I will see "START DATE" as "-- --" for AM on Members Info Panel
    Then I verify I will see "END DATE" as "-- --" for AM on Members Info Panel

  @CP-25950 @CP-25950-04 @crm-regression @ui-ats @burak
  Scenario: Verify Applicant Status is Determining and Eligibility Status is Pending when edit to apply for Programs for Status is DETERMINING MEDICAL ASSISTANCE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode | notApplyingIndicator |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       | true                 |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode | notApplyingIndicator |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       | true                 |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    Then I verify I will see "APPLICANT STATUS" as "Not Applying" for AM on Members Info Panel
    Then I verify I will see "APPLICANT STATUS" as "Not Applying" for AM on Members Info Panel
    Then I verify all the Eligibility Status of the Application Member displays as "-- --"
    Then I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    And I deselect "Not Applying" program type
    Then I choose "Medicaid" as program type
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    And I deselect "Not Applying" program type for Application Member
    And Select the "CHIP" Program(s) for application member
    And click on save button for add application member
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify Applicant status "Determining" for PI and AM on application tracking page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel
    Then I verify I will see "PROGRAMS APPLIED FOR" as "Medicaid" for PI on Members Info Panel
    Then I verify I will see "DETERMINATION DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "START DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "END DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "PROGRAMS APPLIED FOR" as "CHIP" for AM on Members Info Panel
    Then I verify I will see "DETERMINATION DATE" as "-- --" for AM on Members Info Panel
    Then I verify I will see "START DATE" as "-- --" for AM on Members Info Panel
    Then I verify I will see "END DATE" as "-- --" for AM on Members Info Panel

  @CP-25950 @CP-25950-05 @crm-regression @ui-ats @burak
  Scenario: Verify Applicant Status is Updating Received to Determining when edit to apply for Programs for Status is DETERMINING MEDICAL ASSISTANCE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    Then I verify I will see "APPLICANT STATUS" as "Received" for PI on Members Info Panel
    Then I verify I will see "APPLICANT STATUS" as "Received" for AM on Members Info Panel
    Then I verify all the Eligibility Status of the Application Member displays as "-- --"
    Then I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    Then I choose "Medicaid" as program type
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    And Select the "CHIP" Program(s) for application member
    And click on save button for add application member
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify Applicant status "Determining" for PI and AM on application tracking page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel

  @CP-25950 @CP-25950-06 @crm-regression @ui-ats @burak
  Scenario: Verify Applicant Status is Updating Received to Determining when edit to apply for Programs for Status is DETERMINING LONG TERM CARE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    Then I verify I will see "APPLICANT STATUS" as "Received" for PI on Members Info Panel
    Then I verify I will see "APPLICANT STATUS" as "Received" for AM on Members Info Panel
    Then I verify all the Eligibility Status of the Application Member displays as "-- --"
    Then I navigate to application tab page
    And I click on the Edit button for the Primary Individual Details
    Then I choose "HCBS" as program type
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify Applicant status "Determining" for PI and AM on application tracking page
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for AM on Members Info Panel

  @CP-22991 @CP-22991-01 @crm-regression @ui-ats @sang
  Scenario: Verify Edit Eligibility status dropdown values for Eligibility Status and Denial Reasons
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType     | applicationReceivedDate | submittedInd |
      | Medical Assistance  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    Then I Select "Eligibility Status" to verify the following dropdown values
      | Pending | Eligible | Not Eligible |
    Then I Select "Denial Reasons" to verify the following dropdown values
      | Already Eligible | Immigration Status | Medicaid Eligible | Non-Compliance | Other Insurance | Out of State Resident | Over Age | Over Income |

  @CP-22991 @CP-22991-02 @crm-regression @ui-ats @sang
  Scenario: Verify Edit Eligibility field is cleared from view after clicking on cancel
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on the cancel button in the select reason
    Then I verify Eligibility edit field has cleared from view

  @CP-22991 @CP-22991-03 @crm-regression @ui-ats @sang
  Scenario: Verify Application Tracking Members Info Panel Edit Eligibility navigate away cancel and continue
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on the back arrow button
    And I click Cancel button inside the warning message
    Then I verify I am on the Application Tracking Page
    And I click on the back arrow button
    Then I click Continue button inside the warning message
    Then the UI will display the SEARCH APPLICATION screen.

  @CP-22991 @CP-22991-04 @CP-25380 @CP-25380-03 @crm-regression @ui-ats @sang
  Scenario: Verify Determiniation Date is defaulted to current Date for updating status and cannot not be set to future date
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    Then I verify I will see "DETERMINATION DATE" as "CURRENT DATE" for PI on Members Info Panel
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | FUTURE DATE |
    And I click on save button for the Members Info Panel
    Then I verify Members Info "FUTURE DETERMINATION DATE" warning message with following
      | Date cannot be in the future |
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    Then I verify I will see "DETERMINATION DATE" as "CURRENT DATE" for PI on Members Info Panel
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | FUTURE DATE |
    And I click on save button for the Members Info Panel
    Then I verify Members Info "FUTURE DETERMINATION DATE" warning message with following
      | Date cannot be in the future |

  @CP-22991 @CP-22991-05 @CP-25380 @CP-25380-07 @crm-regression @ui-ats @sang
  Scenario: Verify required validation Eligible and Not Eligible save field required message
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on save button for the Members Info Panel
    Then I verify Members Info "START DATE REQUIRED" warning message with following
      | ELIGIBILITY START DATE is required and cannot be left blank |
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on save button for the Members Info Panel
    Then I verify Members Info "DENIAL REASON REQUIRED" warning message with following
      | DENIAL REASON(S) is required and cannot be left blank |

  @CP-22991 @CP-22991-06 @crm-regression @ui-ats @sang
  Scenario: Verify successful Eligibility save for Eligible with updated on and by
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on save button for the Members Info Panel
    And I get application id from the Create Application Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify the Updated On and Updated By for program "ELIGIBILITY" save

  @CP-22991 @CP-22991-07 @crm-regression @ui-ats @sang
  Scenario: Verify successful Eligibility save for Not Eligible with updated on and by
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on save button for the Members Info Panel
    And I get application id from the Create Application Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    Then I verify the Updated On and Updated By for program "NOT ELIGIBILITY" save

  @CP-25380 @CP-25380-01 @crm-regression @ui-ats @vinuta
  Scenario: Verify determination, start & end Date are null when Eligibility status is Pending
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    Then I verify I will see "ELIGIBILITY STATUS" as "Pending" for PI on Members Info Panel
    Then I verify I will see "DETERMINATION DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "START DATE" as "-- --" for PI on Members Info Panel
    Then I verify I will see "END DATE" as "-- --" for PI on Members Info Panel
    And I click Edit Button for the Members Info Panel
    Then I verify the following fields are not editable on Members Info Panel
      | DETERMINATION DATE |
      | START DATE         |
      | END DATE           |

  @CP-25380 @CP-25380-02 @CP-25380-05 @CP-25380-06 @crm-regression @ui-ats @vinuta
  Scenario: Verify Eligibility status can be changed to Eligible or Not Eligible only & End Date is enabled only when start date is entered
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 1 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    Then I verify the following fields are not editable on Members Info Panel
      | END DATE |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "END DATE" dropdown and select the following values
      | FUTURE DATE |
    And I click on save button for the Members Info Panel
    And I click Edit Button for the Members Info Panel
    Then I Select "Eligibility Status" to verify the following dropdown values
      | Eligible | Not Eligible |
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "END DATE" dropdown and select the following values
      | FUTURE DATE |
    And I click on "SUB-PROGRAM" dropdown and select the following values
      |50 - Sub-Program e|
    And I click on save button for the Members Info Panel
    And I click Edit Button for the Members Info Panel
    Then I Select "Eligibility Status" to verify the following dropdown values
      | Eligible | Not Eligible |

  @CP-25380 @CP-25380-04 @CP-25380-08 @crm-regression @ui-ats @vinuta
  Scenario: Verify Determination Date can be edited manually for updating status & end date is optional
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    Then I verify I will see "DETERMINATION DATE" as "CURRENT DATE" for PI on Members Info Panel
    And I click on save button for the Members Info Panel
    Then I verify I will see "END DATE" as "-- --" for PI on Members Info Panel
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    Then I verify I will see "DETERMINATION DATE" as "CURRENT DATE" for PI on Members Info Panel
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | PAST DATE |
    And I click on save button for the Members Info Panel
    Then I verify I will see "END DATE" as "-- --" for PI on Members Info Panel
    And I click Edit Button for the Members Info Panel
    Then I verify I will see "DETERMINATION DATE" as "PAST DATE" for PI on Members Info Panel

  @CP-34035 @CP-34035-01 @crm-regression @ui-ats @chandrakumar
  Scenario: Camunda: Verify Application Status is moved to complete Status from Determining status for Long Term Care application
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "HCBS" as program type
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
    When I click on Save button on Create Application Page
    And I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I see application Status as "Determining" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I verify the missing information details status as "Pending"
    When I click on application tracking tab to navigate to Application Tracking page
    And I click Edit Button for the Members Info Panel
    Then I Select "Eligibility Status" to verify the following dropdown values
      | Pending | Eligible | Not Eligible | Wait List|
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on save button for the Members Info Panel
    And I navigate to application tab page
    When I click on application tracking tab to navigate to Application Tracking page
    And I see application Status as "Complete" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I verify the missing information details status as "Void"

  @CP-34035 @CP-34035-02 @crm-regression @ui-ats @chandrakumar
  Scenario: Camunda: Verify Application Status is moved to complete Status from Determining status for Medical Assistance application
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Medicaid" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
    When I click on Save button on Create Application Page
    And I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I see application Status as "Determining" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I verify the missing information details status as "Pending"
    When I click on application tracking tab to navigate to Application Tracking page
    And I click Edit Button for the Members Info Panel
    Then I Select "Eligibility Status" to verify the following dropdown values
      | Pending | Eligible | Not Eligible |
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on save button for the Members Info Panel
    And I navigate to application tab page
    When I click on application tracking tab to navigate to Application Tracking page
    And I see application Status as "Complete" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I verify the missing information details status as "Void"

  @CP-34035 @CP-34035-03 @crm-regression @ui-ats @chandrakumar
  Scenario: Camunda: Verify Application Status is moved to complete Status from Determining status for Medical Assistance application with multiple programs
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Medicaid" as program type
    Then I choose "CHIP" as program type
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
    When I click on Save button on Create Application Page
    And I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I see application Status as "Determining" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I verify the missing information details status as "Pending"
    When I click on application tracking tab to navigate to Application Tracking page
    And I click Edit Button for the Members Info Panel
    Then I Select "Eligibility Status" to verify the following dropdown values
      | Pending | Eligible | Not Eligible |
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "ELIGIBILITY STATUS" dropdown for second row and select the following values
      | Eligible |
    And I click on "START DATE" dropdown for second row and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown for second row and select the following values
      | CURRENT DATE |
    And I click on save button for the Members Info Panel
    And I navigate to application tab page
    When I click on application tracking tab to navigate to Application Tracking page
    And I see application Status as "Complete" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I verify the missing information details status as "Void"

  @CP-34035 @CP-34035-04 @CP-34035-05 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Camunda: Verify Application Status is moved to complete Status from Determining status for an application created through UI with multiple members
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "<programTypeName>" as program type
    Then I fill in the following "<applicationType>" Primary Individual with the following values
      | GENDER               | Female             |
      | ARE YOU PREGNANT     | Yes                |
      | NO. BABIES EXPECTED  | 8                  |
      | EXPECTED DUE DATE    | random future date |
      | SSN                  | Numeric 9          |
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    Then I get application id from the Create Application Page
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME           | RANDOM 30        |
      | MI                   | RANDOM 1         |
      | LAST NAME            | RANDOM 30        |
      | SUFFIX               | RANDOM 3         |
      | DOB                  | random past date |
      | GENDER               | Female           |
      | ARE YOU PREGNANT     | YES              |
      | NO. OF BABIES        | 2                |
      | EXPECTED DUE DATE    | RANDOM           |
      | SSN                  | Numeric 9        |
      | EXTERNAL CONSUMER ID | RANDOM           |
      | EXTERNAL ID TYPE     | random dropdown  |
    And Select the "<programTypeName>" Program(s) for application member
    And click on save button for add application member
    And I click Add Authorized Representative button
    Then I fill in the following Authorized Representative values
      | AUTH TYPE                    | Other         |
      | FIRST NAME                   | RANDOM 30     |
      | MI                           | RANDOM 1      |
      | LAST NAME                    | RANDOM 30     |
      | ORGANIZATION NAME            | RANDOM 5      |
      | ID NUMBER                    | Numeric 5     |
      | AUTHORIZATION SIGNATURE DATE | RANDOM PAST   |
      | START DATE                   | RANDOM PAST   |
      | END DATE                     | RANDOM FUTURE |
      | ACCESS TYPE                  | RANDOM        |
      | CORRESPONDENCE               | Do Not Receive        |
      | AUTHORIZED                   | Yes           |
      | AUTH STATUS                  | SET BY DATES  |
    Then I click on Save button on Create Application Page
    And I navigate to Application Tracking
    And I see application Status as "Entering Data" in the application information
    And I navigate to application tab page
    And I click on Submit button only in Create Application Page
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
    And I see application Status as "Determining" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I verify the missing information details status as "Pending"
    When I click on application tracking tab to navigate to Application Tracking page
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "ELIGIBILITY STATUS" dropdown for first row and select the following values
      | Eligible |
    And I click on "START DATE" dropdown for first row and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown for first row and select the following values
      | CURRENT DATE |
    And I click on "SUB-PROGRAM" dropdown for first row and select the following values
      | <subProgram> |
    And I click on save button for the Members Info Panel
    And I navigate to application tab page
    When I click on application tracking tab to navigate to Application Tracking page
    And I see application Status as "Complete" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I verify the missing information details status as "Void"
    Examples:
      | applicationType    | programTypeName | subProgram|
      | LONG TERM CARE     | HCBS            |  50 - Sub-Program e         |
      | MEDICAL ASSISTANCE | Medicaid        |  30 - Sub-Program c         |

  @CP-34035 @CP-34035-06 @CP-34035-07 @crm-regression @ui-ats @chandrakumar
  Scenario Outline: Camunda : Verify Application Status is moved to complete Status from Determining status for medical assistance application created through API
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType   | applicationReceivedDate | submittedInd |
      | <applicationType> | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | <programTypeName> |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I verify the missing information details status as "Pending"
    When I click on application tracking tab to navigate to Application Tracking page
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Not Eligible |
    And I click on "DENIAL REASONS" dropdown and select the following values
      | Over Age |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on "DETERMINATION DATE" dropdown and select the following values
      | CURRENT DATE |
    And I click on save button for the Members Info Panel
    And I navigate to application tab page
    When I click on application tracking tab to navigate to Application Tracking page
    And I see application Status as "Complete" in the application information
    When I click on missing information tab to navigate to Missing Information page
    And I verify the missing information details status as "Void"
    Examples:
      | applicationType    | programTypeName |
      | Long Term Care     | HCBS            |
      | Medical Assistance | Medicaid        |

  @CP-35771 @CP-35771-01 @crm-regression @ui-ats @ozgen
  Scenario: View Sub-Program Types based on Medicaid Application Program
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    Then I verify I will see "DETERMINATION DATE" as "CURRENT DATE" for PI on Members Info Panel
    And I click on save button for the Members Info Panel
    Then I verify Members Info "SUB-PROGRAM REQUIRED" warning message with following
      | SUB PROGRAM is required and cannot be left blank |
    Then I Select "Sub-Program" to verify the following dropdown values
      | 10 - Sub-Program a | 20 - Sub-Program b | 30 - Sub-Program c |
    And I click on "SUB-PROGRAM" dropdown and select the following values
      | 30 - Sub-Program c |
    And I click on save button for the Members Info Panel
    Then I verify I will see "SUB-PROGRAM" as "30 - Sub-Program c" for PI on Members Info Panel

  @CP-35771 @CP-35771-02 @crm-regression @ui-ats @ozgen
  Scenario: View Sub-Program Types based on CHIP Application Program
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789017677  | true                  |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    Then I verify I will see "DETERMINATION DATE" as "CURRENT DATE" for PI on Members Info Panel
    And I click on save button for the Members Info Panel
    Then I verify Members Info "SUB-PROGRAM REQUIRED" warning message with following
      | SUB PROGRAM is required and cannot be left blank |
    Then I Select "Sub-Program" to verify the following dropdown values
      | 10 - Sub-Program a | 40 - Sub-Program d |
    And I click on "SUB-PROGRAM" dropdown and select the following values
      | 10 - Sub-Program a |
    And I click on save button for the Members Info Panel
    Then I verify I will see "SUB-PROGRAM" as "10 - Sub-Program a" for PI on Members Info Panel

  @CP-35771 @CP-35771-03 @crm-regression @ui-ats @ozgen
  Scenario: View Sub-Program Types based on HCBS Application Program
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789017677  | true                  |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    Then I verify I will see "DETERMINATION DATE" as "CURRENT DATE" for PI on Members Info Panel
    And I click on save button for the Members Info Panel
    Then I verify Members Info "SUB-PROGRAM REQUIRED" warning message with following
      | SUB PROGRAM is required and cannot be left blank |
    Then I Select "Sub-Program" to verify the following dropdown values
      | 50 - Sub-Program e |
    And I click on "SUB-PROGRAM" dropdown and select the following values
      | 50 - Sub-Program e |
    And I click on save button for the Members Info Panel
    Then I verify I will see "SUB-PROGRAM" as "50 - Sub-Program e" for PI on Members Info Panel

  @CP-35771 @CP-35771-04 @crm-regression @ui-ats @ozgen
  Scenario: Saving without selecting Sub Programs when no subprograms configured for Pregnancy Assistance Application Program
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789017677  | true                  |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click Edit Button for the Members Info Panel
    And I click on "ELIGIBILITY STATUS" dropdown and select the following values
      | Eligible |
    Then I verify that sub-program dropdown is not clickable
    And I click on "START DATE" dropdown and select the following values
      | CURRENT DATE |
    Then I verify I will see "DETERMINATION DATE" as "CURRENT DATE" for PI on Members Info Panel
    And I click on save button for the Members Info Panel
    Then I verify I will see "SUB-PROGRAM" as "-- --" for PI on Members Info Panel







