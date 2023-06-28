Feature: ATS Second Tenant BLATS2 Member Matching Page

  @CP-19558 @CP-19558-01 @crm-regression @ui-ats-blats2 @sang
  Scenario: Member Matching Display Applications in Order
    Given I will get the Authentication token for "<projectName>" in "CRM"
     #50 pts matching score
    And I POST 1 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    #90 pts matching score
    And I POST 1 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE SSN |
    #100 pts matching score
    And I POST 1 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | consumerSuffix | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | API            | A                  |
    #Inbound Application
    And I POST 1 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | consumerSuffix | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | API            | A                  |
    And I set Application Ids created in a list to be used for in ATS UI functionality
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter the "LAST" Application ID from the multiple API created for duplicate applications and click on the retrived HyperLink
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then the Applications will be displayed in order of the Score highest to lowest left to right on the UI

  @CP-19558 @CP-19558-02 @crm-regression @ui-ats-blats2 @sang
  Scenario: Member Matching Display Multiple Applications with same score in order
    Given I will get the Authentication token for "<projectName>" in "CRM"
    #50 pts matching score
    And I POST 3 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    #100 pts matching score
    And I POST 3 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | consumerSuffix | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | API            | A                  |
    #Inbound Application
    And I POST 1 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | consumerSuffix | consumerMiddleName |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | API            | A                  |
    And I set Application Ids created in a list to be used for in ATS UI functionality
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter the "LAST" Application ID from the multiple API created for duplicate applications and click on the retrived HyperLink
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then the Applications with the SAME score will be displayed based on the Application ID in Chronological Order from left to right

  @CP-19553 @CP-19553-01 @CP-19557 @CP-19557-01 @crm-regression @ui-ats-blats2 @sang
  Scenario: Display Potential Application matches while viewing Member Matching through Task
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I POST 1 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And I POST 1 "LONG TERM CARE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I POST 2 "LTC SUBMITTED APP STATUS" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I set Application Ids created in a list to be used for in ATS UI functionality
    And I initiate and run Get Application Links Call
    And I will get "Task" ID for "Member Matching" type from the application links response
    When If any In Progress task present then update that to Cancelled
    And I logged into CRM
    When I navigate to "Task Search" page
    And I searched ats task using taskId from "API"
    And I click on search button on task search page
    And I verified that task type is "Member Matching"
    Then I click on initiate button in task search page
    And Verify PDF viewer is not initiated
    Then I verified that user is navigated to "Member Matching" page
    Then I verify following details on Member Matching Page for "MEMBER MATCHING"
      | NAME | APPLICANT STATUS | ADDRESS LINE ONE | ADDRESS LINE TWO | APP ID | SSN | LTC PROGRAMS |
    And I will update the following information in task slider
      | status          | Cancel              |
      | reasonForCancel | Created Incorrectly |
    And I click on save in Task Slider
    Then I verify task save success message

  @CP-19553 @CP-19553-02 @CP-19557 @CP-19557-02 @crm-regression @ui-ats-blats2 @sang
  Scenario: Display Potential Application matches while viewing Member matching through Submit button in Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I POST 1 "MA WITH MEDICAID" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And I POST 1 "MEDICAL ASSISTANCE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       |
    And I POST 2 "MA SUBMITTED APP STATUS" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           | genderCode |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN | Male       |
    And I set Application Ids created in a list to be used for in ATS UI functionality
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter the "LAST" Application ID from the multiple API created for duplicate applications and click on the retrived HyperLink
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I verify following details on Member Matching Page for "MEMBER MATCHING"
      | NAME | APPLICANT STATUS | ADDRESS LINE ONE | ADDRESS LINE TWO | APP ID | SSN | MA PROGRAMS |

  @CP-19557 @CP-19557-03 @crm-regression @ui-ats-blats2 @sang
  Scenario: Display No Matches Found in Member matching Screen when no application match
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName         | consumerLastName         | dateOfBirth | ssn        |
      | RANDOM FIRST FOR NO MATCH | RANDOM LAST FOR NO MATCH | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I verify "No Matches Found" is displayed in member matching page in Application

  @CP-19557 @CP-19557-04 @crm-regression @ui-ats-blats2 @sang
  Scenario: Display non matching Application consumers for existing Applications
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Pregnancy Assistance |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 3
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save submit application api and store appId and response in list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Pregnancy Assistance |
    And I POST ATS save submit application api and store appId and response in list
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I see non matching consumers listed in chronological order after the matching consumer

  @CP-19557 @CP-19557-05 @crm-regression @ui-ats-blats2 @sang
  Scenario: Identify Data that has not matched for each Application Consumer and MAX full name character contains sixty-four letters
    Given I will get the Authentication token for "BLATS2" in "CRM"
    And I POST 1 "MA WITH MEDICAID" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName   | consumerLastName   | dateOfBirth | ssn           | consumerSuffix | consumerMiddleName |
      | MAX CHARACTER FIRST | MAX CHARACTER LAST | RANDOM DOB  | DUPLICATE SSN | API            | A                  |
    And I POST 1 "MEDICAL ASSISTANCE" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn        |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | RANDOM SSN |
    And I POST 1 "MA SUBMITTED APP STATUS" Application API with Primary Individual with same programs and Residential address plus following consumer data
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I set Application Ids created in a list to be used for in ATS UI functionality
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter the "LAST" Application ID from the multiple API created for duplicate applications and click on the retrived HyperLink
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I identify Data that has not matched for each Application Consumer is marked by red text and a symbol

  @CP-22620 @CP-22620-01 @crm-regression @ui-ats-blats2 @sang
  Scenario: Display potential matches for more than one inbound application matches on an existing application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE SSN |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 2
      | consumerFirstName | consumerLastName |
      | DUPLICATE FIRST   | DUPLICATE LAST   |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 3
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save submit application api and store appId and response in list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I POST ATS save submit application api and store appId and response in list
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    Then I verify existing applicant potential matches when "ONE INBOUND CONSUMER" matches

  @CP-22620 @CP-22620-02 @crm-regression @ui-ats-blats2 @sang
  Scenario: Two Application Members on the INBOUND Application match one member on the Existing Application
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I POST ATS save submit application api and store appId and response in list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save submit application api and store appId and response in list
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    Then I verify existing applicant potential matches when "SINGLE EXISTING MATCH TWO" matches

  @CP-22620 @CP-22620-03 @crm-regression @ui-ats-blats2 @sang
  Scenario: Multiple matching consumers on the existing application with the same score in chronological order
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 2
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 3
      | consumerFirstName | consumerLastName | dateOfBirth   |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB |
    And I POST ATS save submit application api and store appId and response in list
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth   | ssn           |
      | DUPLICATE FIRST   | DUPLICATE LAST   | DUPLICATE DOB | DUPLICATE SSN |
    And I initiate save applications api consumer 0 with program
      | Pregnancy Assistance |
    And I POST ATS save submit application api and store appId and response in list
    Given I logged into CRM
    And I saved logged in user ID
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    And I select on the hyperlink result from the created application Id result
    And I navigate to application tab page
    And I get application id from the Create Application Page
    Then I click on Submit button only in Create Application Page
    Then I verify existing applicant potential matches when "SAME SCORE APPLICANTS" matches

  @CP-15811 @CP-15811-01  @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Inbound Application Information in Member Matching page for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 1 | random 41  |
      | RESIDENCE ADDRESS LINE 2 | random 41  |
      | RESIDENCE CITY           | Metropolis |
      | RESIDENCE STATE          | Illinois   |
      | RESIDENCE ZIP CODE       | 62960      |
      | RESIDENCE COUNTY         | Massac     |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page

    And I see I navigated to Member Matching page
    Then I verify following details on Member Matching Page for "Application Details"
      | Application ID | Application Status | Address Line 1 and 2 | City State Zip |
    And I click on member matching page back arrow to navigate to create application page
    Then I verify "MEDICAL ASSISTANCE" Application Page header is displayed

  @CP-15811 @CP-15811-02 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Inbound Application Information for null address details in Member Matching page for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I verify "Address" details displayed as '-- --' on Member Matching Page
    And I click on member matching page back arrow to navigate to create application page
    Then I verify "MEDICAL ASSISTANCE" Application Page header is displayed

  @CP-15811 @CP-15811-03 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Inbound Application Information in Member Matching page for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    And I fill in the Primary Individual Contact Information with the following values
      | RESIDENCE ADDRESS LINE 1 | random 41  |
      | RESIDENCE ADDRESS LINE 2 | random 41  |
      | RESIDENCE CITY           | Metropolis |
      | RESIDENCE STATE          | Illinois   |
      | RESIDENCE ZIP CODE       | 62960      |
      | RESIDENCE COUNTY         | Massac     |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    Then I verify following details on Member Matching Page for "Application Details"
      | Application ID | Application Status | Address Line 1 and 2 | City State Zip |
    And I click on member matching page back arrow to navigate to create application page
    Then I verify "LONG TERM CARE" Application Page header is displayed

  @CP-15811 @CP-15811-04 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Inbound Application Information for null address details in Member Matching page for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    Then I verify "Address" details displayed as '-- --' on Member Matching Page
    And I click on member matching page back arrow to navigate to create application page
    Then I verify "LONG TERM CARE" Application Page header is displayed

  @CP-18108 @CP-18108-01 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Inbound Application CONSUMER information details in Member Matching Page for Medical Assistance (choosing only 1 of the program types)
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME     | Alphabetic 30    |
      | MIDDLE INITIAL | Alphabetic 1     |
      | LAST NAME      | Alphabetic 30    |
      | SUFFIX         | Alphabetic 3     |
      | DOB            | random past date |
      | SSN            | Numeric 9        |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I choose "Medicaid" as program type
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | MI         | RANDOM 1         |
      | LAST NAME  | RANDOM 30        |
      | SUFFIX     | RANDOM 3         |
      | DOB        | random past date |
      | SSN        | Numeric 9        |
    And Select the "Medicaid" Program(s) for application member
    And click on save button for add application member
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I get application id from the Create Application Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I Verify the following from the GET Application with ApplicationId
      | PI ID  | applicationConsumerId |
      | APP ID | applicationConsumerId |
    Then I verify following details on Member Matching Page for "PI Details"
      | FULL NAME | SSN | DOB |
    Then I verify following details on Member Matching Page for "Application Member Details"
      | FULL NAME | SSN | DOB |
    Then I verify following Program(s) displayed in Alphabetical order on the Member Matching Consumer Details for "PI"
      | Medicaid |
    Then I verify following Program(s) displayed in Alphabetical order on the Member Matching Consumer Details for "Application Member"
      | Medicaid |
    Then I verify I see the Primary Individual Indicator for the associated Primary Individual Record

  @CP-18108 @CP-18108-02 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Inbound Application CONSUMER information details in Member Matching Page for Medical Assistance (choosing only 2 of the program types)
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME     | Alphabetic 30    |
      | MIDDLE INITIAL | Alphabetic 1     |
      | LAST NAME      | Alphabetic 30    |
      | SUFFIX         | Alphabetic 3     |
      | DOB            | random past date |
      | GENDER         | Male             |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I choose "CHIP" as program type
    Then I choose "Pregnancy Assistance" as program type
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | MI         | RANDOM 1         |
      | LAST NAME  | RANDOM 30        |
      | SUFFIX     | RANDOM 3         |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And Select the "Medicaid" Program(s) for application member
    And Select the "CHIP" Program(s) for application member
    And click on save button for add application member
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I get application id from the Create Application Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I Verify the following from the GET Application with ApplicationId
      | PI ID  | applicationConsumerId |
      | APP ID | applicationConsumerId |
    Then I verify following details on Member Matching Page for "PI Details"
      | FULL NAME | DOB |
    Then I verify following details on Member Matching Page for "Application Member Details"
      | FULL NAME | SSN | DOB |
    Then I verify following Program(s) displayed in Alphabetical order on the Member Matching Consumer Details for "PI"
      | CHIP | Pregnancy Assistance |
    Then I verify following Program(s) displayed in Alphabetical order on the Member Matching Consumer Details for "Application Member"
      | Medicaid | CHIP |
    Then I verify I see the Primary Individual Indicator for the associated Primary Individual Record

  @CP-18108 @CP-18108-03 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Inbound Application CONSUMER information details in Member Matching Page for Medical Assistance (choosing all 3 program types)
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME     | Alphabetic 30    |
      | MIDDLE INITIAL | Alphabetic 1     |
      | LAST NAME      | Alphabetic 30    |
      | SUFFIX         | Alphabetic 3     |
      | DOB            | random past date |
      | GENDER         | Male             |
      | SSN            | Numeric 9        |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I choose "Medicaid" as program type
    Then I choose "CHIP" as program type
    Then I choose "Pregnancy Assistance" as program type
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | MI         | RANDOM 1         |
      | LAST NAME  | RANDOM 30        |
      | SUFFIX     | RANDOM 3         |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And Select the "Medicaid" Program(s) for application member
    And Select the "CHIP" Program(s) for application member
    And Select the "Pregnancy Assistance" Program(s) for application member
    And click on save button for add application member
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I get application id from the Create Application Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I Verify the following from the GET Application with ApplicationId
      | PI ID  | applicationConsumerId |
      | APP ID | applicationConsumerId |
    Then I verify following details on Member Matching Page for "PI Details"
      | FULL NAME | SSN | DOB |
    Then I verify following details on Member Matching Page for "Application Member Details"
      | FULL NAME | SSN | DOB |
    Then I verify following Program(s) displayed in Alphabetical order on the Member Matching Consumer Details for "PI"
      | CHIP | Medicaid | Pregnancy Assistance |
    Then I verify following Program(s) displayed in Alphabetical order on the Member Matching Consumer Details for "Application Member"
      | CHIP | Medicaid | Pregnancy Assistance |
    Then I verify I see the Primary Individual Indicator for the associated Primary Individual Record

  @CP-18108 @CP-18108-04 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify undefined Inbound Application CONSUMER information details displays as "-- --" in Member Matching Page for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Random 5         |
      | LAST NAME  | Random 5         |
      | DOB        | random past date |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | MI         | RANDOM 1  |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    Then I verify "PI" details displayed as '-- --' on Member Matching Page
    Then I verify "Application Member" details displayed as '-- --' on Member Matching Page

  @CP-18108 @CP-18108-05 @crm-regression @ui-ats-blats2 @burak @sang
  Scenario: Verify Inbound Application CONSUMER information details in Member Matching Page for  LONG TERM CARE
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME     | Alphabetic 30    |
      | MIDDLE INITIAL | Alphabetic 1     |
      | LAST NAME      | Alphabetic 30    |
      | SUFFIX         | Alphabetic 3     |
      | DOB            | random past date |
      | GENDER         | Male             |
      | SSN            | Numeric 9        |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    Then I choose "HCBS" as program type
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30        |
      | MI         | RANDOM 1         |
      | LAST NAME  | RANDOM 30        |
      | SUFFIX     | RANDOM 3         |
      | DOB        | random past date |
      | GENDER     | Male             |
      | SSN        | Numeric 9        |
    And Select the "HCBS" Program(s) for application member
    And click on save button for add application member
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I get application id from the Create Application Page
    When I initiate the View Application Details API for the Created Application
    And I run the View Application Details API for the Created Application
    And I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I Verify the following from the GET Application with ApplicationId
      | PI ID  | applicationConsumerId |
      | APP ID | applicationConsumerId |
    Then I verify following details on Member Matching Page for "PI Details"
      | FULL NAME | SSN | DOB |
    Then I verify following details on Member Matching Page for "Application Member Details"
      | FULL NAME | SSN | DOB |
    Then I verify following Program(s) displayed in Alphabetical order on the Member Matching Consumer Details for "PI"
      | HCBS |
    Then I verify following Program(s) displayed in Alphabetical order on the Member Matching Consumer Details for "Application Member"
      | HCBS |
    Then I verify I see the Primary Individual Indicator for the associated Primary Individual Record

  @CP-18108 @CP-18108-06 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify undefined Inbound Application CONSUMER information details displays as "-- --" in Member Matching Page for LONG TERM CARE
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | MI         | RANDOM 1  |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    Then I verify "PI" details displayed as '-- --' on Member Matching Page
    Then I verify "Application Member" details displayed as '-- --' on Member Matching Page

  @CP-18108 @CP-18108-07 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Inbound Application CONSUMERS sorted as expected  in Member Matching Page for Medical Assistance
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | MI         | RANDOM 1  |
      | LAST NAME  | RANDOM 30 |
    And I click on Set PI checkbox in add application member page
    And click on save button for add application member
    And I select continue to change application member to Primary Individual
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    And I verify Application Members sorted on Member Matching Page as expected

  @CP-18108 @CP-18108-08 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Inbound Application CONSUMERS sorted as expected  in Member Matching Page for Long Term Care
    Given I logged into CRM
    And I navigate to Create "LONG TERM CARE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | MI         | RANDOM 1  |
      | LAST NAME  | RANDOM 30 |
    And I click on Set PI checkbox in add application member page
    And click on save button for add application member
    And I select continue to change application member to Primary Individual
    And I click on Submit button and continue button for application without Programs
    And I see I navigated to Member Matching page
    And I verify Application Members sorted on Member Matching Page as expected

  @CP-27353 @CP-27353-02 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Cancel Button for Send Application For Research
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I click on the Research button in the Application Member Matching Page
    And I select following reason for sending application to Research
      | Insufficient Information |
      | Document Misclassified   |
      | Multiple Matches         |
    And I enter notes as "RANDOM 250" Characters for Send Application For Research Panel
    And I click cancel button for Send Application For Research Panel
    And I see I navigated to Member Matching page

  @CP-27353 @CP-27353-03 @CP-22277 @CP-22277-03 @crm-regression @ui-ats-blats2 @burak
  Scenario: Verify Member Matching Back Arrow Warning message & Continue and Cancel Buttons
    Given I logged into CRM
    And I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    Then I click on Submit button only in Create Application Page
    And I see I navigated to Member Matching page
    Then I click on the Research button in the Application Member Matching Page
    And I enter notes as "RANDOM 250" Characters for Send Application For Research Panel
    And I click on member matching page back arrow to navigate to create application page
    And I verify Back Arrow warning warning message displayed on Member Matching Page
    And I click "CANCEL" button inside the warning message on Member Matching Page
    And I see I navigated to Member Matching page
    And I select following reason for sending application to Research
      | Insufficient Information |
      | Document Misclassified   |
    And I click on member matching page back arrow to navigate to create application page
    And I verify Back Arrow warning warning message displayed on Member Matching Page
    And I click "Continue" button inside the warning message on Member Matching Page
    Then I verify "MEDICAL ASSISTANCE" Application Page header is displayed