Feature: ATS Not Applying and Program Selection Functionality

  @CP-15816 @CP-15816-01 @CP-15816-02 @crm-regression @ui-ats @burak
  Scenario: Verify Programs Check Boxes are disabled for the  for Medical Assistance
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
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    And I choose "Not Applying" as program type
    Then I verify "Program" check box are disabled
    Then I deselect "Not Applying" program type
    And I choose "Medicaid" as program type
    Then I verify "NOT APPLYING" check box are disabled
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    And I choose "Not Applying" as program type
    Then I verify "Program" check box are disabled
    Then I deselect "Not Applying" program type
    And I choose "Medicaid" as program type
    Then I verify "NOT APPLYING" check box are disabled
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-01.1 @CP-15816-02.1 @crm-regression @ui-ats @burak
  Scenario: Verify Programs Check Boxes are disabled for the  for Long Term Care
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
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I choose "NOT APPLYING" as program type
    Then I verify "HCBS" check box are disabled
    Then I deselect "NOT APPLYING" program type
    And  I choose "HCBS" as program type
    Then I verify "NOT APPLYING" check box are disabled
    Then I click on Save button on Create Application Page
    And I click on + Add Application Member button
    Then I choose "NOT APPLYING" as program type
    Then I verify "HCBS" check box are disabled
    Then I deselect "NOT APPLYING" program type
    And  I choose "HCBS" as program type
    Then I verify "NOT APPLYING" check box are disabled
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-03 @CP-15816-08 @crm-regression @ui-ats @burak
  Scenario: Programs Check Boxes are disabled after deselecting Not Applying for Entering Data Status MEDICAL ASSISTANCE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | Phone     | true           |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Entering Data" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I choose "Not Applying" as program type
    Then I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    And Select the "Not Applying" Program(s) for application member
    And click on save button for add application member
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "Not Applying" program type
    Then I verify all the program type checkboxes are enabled and clickable for "MEDICAL ASSISTANCE"
    And I choose "Medicaid" as program type
    And I choose "CHIP" as program type
    And I choose "Pregnancy Assistance" as program type
    Then I verify "Not Applying" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I deselect "Not Applying" program type for Application Member
    Then I verify all the program type checkboxes are enabled and clickable for "MEDICAL ASSISTANCE" for Application Member
    And Select the "Medicaid" Program(s) for application member
    And Select the "CHIP" Program(s) for application member
    And Select the "PREGNANCY ASSISTANCE" Program(s) for application member
    Then I verify "Not Applying" check box are disabled for Application Member
    And click on save button for add application member
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I will see "Applicant Status" as "Received" for PI on Members Info Panel
    Then I verify the programs are listed and displayed stacked in Alphabetical order
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-08.1 @crm-regression @ui-ats @burak
  Scenario: Programs Check Boxes are disabled after deselecting Not Applying for Entering Data Status one program MEDICAL ASSISTANCE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | Phone     | true           |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Entering Data" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I choose "Not Applying" as program type
    Then I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    And Select the "Not Applying" Program(s) for application member
    And click on save button for add application member
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "Not Applying" program type
    Then I verify all the program type checkboxes are enabled and clickable for "MEDICAL ASSISTANCE"
    And I choose "Medicaid" as program type
    Then I verify "Not Applying" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I deselect "Not Applying" program type for Application Member
    Then I verify all the program type checkboxes are enabled and clickable for "MEDICAL ASSISTANCE" for Application Member
    And Select the "Medicaid" Program(s) for application member
    Then I verify "Not Applying" check box are disabled for Application Member
    And click on save button for add application member
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I will see "Applicant Status" as "Received" for PI on Members Info Panel
    Then I verify I will see "PROGRAMS APPLIED FOR" as "Medicaid" for PI on Members Info Panel
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-03.1 @CP-15816-08.01 @crm-regression @ui-ats @burak
  Scenario: Verify Programs Check Boxes are disabled after deselecting Not Applying for Entering Data Status LONG TERM CARE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | channelId | interactiveInd |
      | Long Term Care  | CURRENT YYYYMMDD        | Phone     | true           |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Entering Data" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I choose "Not Applying" as program type
    Then I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    And Select the "Not Applying" Program(s) for application member
    And click on save button for add application member
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "Not Applying" program type
    Then I verify all the program type checkboxes are enabled and clickable for "LONG TERM CARE"
    And I choose "HCBS" as program type
    Then I verify "Not Applying" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I deselect "Not Applying" program type for Application Member
    Then I verify all the program type checkboxes are enabled and clickable for "LONG TERM CARE" for Application Member
    And Select the "HCBS" Program(s) for application member
    Then I verify "Not Applying" check box are disabled for Application Member
    And click on save button for add application member
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I will see "Applicant Status" as "Received" for PI on Members Info Panel
    Then I verify Long Term Care "HCBS" is listed in Application Tracking page Members Info panel
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-03.2 @CP-15816-06 @crm-regression @ui-ats @burak
  Scenario: Verify Not Applying Status and Programs Check Boxes are disabled after deselecting Not Applying for Determining Status MEDICAL ASSISTANCE
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
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I choose "Not Applying" as program type
    Then I click on Save button on Create Application Page
    And I click on Submit button and continue button for application without Programs
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
 #   And I click on member matching page back arrow to navigate to create application page
 #   And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I will see "Applicant Status" as "Not Applying" for PI on Members Info Panel
    Then I verify I will see "PROGRAMS APPLIED FOR" as "-- --" for PI on Members Info Panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "Not Applying" program type
    Then I verify all the program type checkboxes are enabled and clickable for "MEDICAL ASSISTANCE"
    Then I choose "Medicaid" as program type
    Then I verify "Not Applying" check box are disabled
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-03.3 @CP-15816-06.1 @crm-regression @ui-ats @burak
  Scenario: Verify Not Applying status and  Programs Check Boxes are enabled after deselecting Not Applying for Determining Status LONG TERM CARE
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
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I choose "Not Applying" as program type
    Then I click on Save button on Create Application Page
    And I click on Submit button and continue button for application without Programs
    And I click on "GO TO APPLICATION TAB" button in Create Application Page
  #  And I click on member matching page back arrow to navigate to create application page
   # And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I will see "Applicant Status" as "Not Applying" for PI on Members Info Panel
    Then I verify I will see "PROGRAMS APPLIED FOR" as "-- --" for PI on Members Info Panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "Not Applying" program type
    Then I verify all the program type checkboxes are enabled and clickable for "LONG TERM CARE"
    Then I choose "HCBS" as program type
    Then I verify "Not Applying" check box are disabled
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-03.4 @crm-regression @ui-ats @burak
  Scenario: Verify Programs Check Boxes are disabled for Insufficient Status Medical Assistance
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I POST ATS save application api
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Insufficient" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I fill in the following "MEDICAL ASSISTANCE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30 |
      | LAST NAME  | Alphabetic 30 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I choose "Not Applying" as program type
    Then I click on Save button on Create Application Page
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "Not Applying" program type
    Then I verify all the program type checkboxes are enabled and clickable for "MEDICAL ASSISTANCE"
    Then I choose "Medicaid" as program type
    Then I verify "Not Applying" check box are disabled
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-03.5 @crm-regression @ui-ats @burak
  Scenario: Verify Programs Check Boxes are disabled for Insufficient Status LONG TERM CARE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I POST ATS save application api
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Insufficient" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I fill in the following "LONG TERM CARE" Primary Individual with the following values
      | FIRST NAME | Alphabetic 30 |
      | LAST NAME  | Alphabetic 30 |
    And I fill in the Primary Individual Contact Information with the following values
      | CELL PHONE NUMBER | Random Numeric 10 |
    And I select Primary Individual Application information with the following
      | CHANNEL | random |
    Then I choose "Not Applying" as program type
    Then I click on Save button on Create Application Page
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "Not Applying" program type
    Then I verify all the program type checkboxes are enabled and clickable for "LONG TERM CARE"
    Then I choose "HCBS" as program type
    Then I verify "Not Applying" check box are disabled
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-04 @CP-15816-05 @crm-regression @ui-ats @burak
  Scenario: Edit Application Consumer FROM a Program selection to Not Applying for Entering Data Status MEDICAL ASSISTANCE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | Phone     | true           |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
      | Pregnancy Assistance |
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Medicaid |
      | Pregnancy Assistance |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Entering Data" in the application information
    Then I verify the programs are listed and displayed stacked in Alphabetical order
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "All" program type
    Then I choose "Not Applying" as program type
    Then I verify "PROGRAMS" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I deselect "All" program type for Application Member
    And Select the "Not Applying" Program(s) for application member
    Then I verify "Programs" check box are disabled for Application Member
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-04.1 @CP-15816-05.1 @crm-regression @ui-ats @burak
  Scenario: Edit Application Consumer FROM a Program selection to Not Applying for Entering Data Status LONG TERM CARE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | channelId | interactiveInd |
      | Long Term Care  | CURRENT YYYYMMDD        | Phone     | true           |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 1 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Entering Data" in the application information
    Then I verify Long Term Care "HCBS" is listed in Application Tracking page Members Info panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "HCBS" program type
    Then I choose "Not Applying" as program type
    Then I verify "HCBS" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I deselect "HCBS" program type for Application Member
    And Select the "Not Applying" Program(s) for application member
    Then I verify "HCBS" check box are disabled for Application Member
    Then I clear the program list for "All" Application Members

 # @CP-15816 @CP-15816-04.2 @CP-15816-07 @crm-regression @ui-ats @burak
  #there will be specific scenario for Determining
  Scenario: Edit Application Consumer FROM a Program selection to Not Applying for Determining Status MEDICAL ASSISTANCE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | channelId | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | Phone     | true         |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
      | Pregnancy Assistance |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "All" program type
    Then I choose "Not Applying" as program type
    Then I verify "Program" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I will see "Applicant Status" as "Not Applying" for PI on Members Info Panel
    Then I verify I will see "PROGRAMS APPLIED FOR" as "-- --" for PI on Members Info Panel
    Then I clear the program list for "All" Application Members

#  @CP-15816 @CP-15816-04.3 @CP-15816-07.1  @crm-regression @ui-ats @burak
   #there will be specific scenario for Determining
  Scenario: Edit Application Consumer FROM a Program selection to Not Applying for Determining Status LONG TERM CARE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | channelId | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | Phone     | true         |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male       |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "HCBS" program type
    Then I choose "Not Applying" as program type
    Then I verify "HCBS" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application tracking tab to navigate to Application Tracking page
    Then I verify I will see "Applicant Status" as "Not Applying" for PI on Members Info Panel
    Then I verify I will see "PROGRAMS APPLIED FOR" as "-- --" for PI on Members Info Panel
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-04.4 @crm-regression @ui-ats @burak
  Scenario: Edit Application Consumer FROM a Program selection to Not Applying for Insufficient Status MEDICAL ASSISTANCE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
      | Pregnancy Assistance |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Insufficient" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "ALL" program type
    Then I choose "NOT APPLYING" as program type
    Then I verify "PROGRAMS" check box are disabled
    Then I clear the program list for "All" Application Members

  @CP-15816 @CP-15816-04.5 @crm-regression @ui-ats @burak
  Scenario: Edit Application Consumer FROM a Program selection to Not Applying for Insufficient Status LONG TERM CARE
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | submittedInd |
      | Long Term Care  | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Insufficient" in the application information
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "HCBS" program type
    Then I choose "Not Applying" as program type
    Then I verify "HCBS" check box are disabled
    Then I clear the program list for "All" Application Members

  @CP-25991 @CP-25991-01 @crm-regression @ui-ats @burak
  Scenario: Restrict Update going Programs to Not Applying and NULL when Status is Determining for PI on MEDICAL ASSISTANCE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        |  true        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I verify I will see "APPLICANT STATUS" as "Determining" for PI on Members Info Panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "CHIP" program type
    Then I deselect "MEDICAID" program type
    Then I choose "NOT APPLYING" as program type
    And  I click on Save button on Create Application Page
    And I verify "Must have at least one Application Member applying for Coverage, your changes will not be saved" warning message displayed in create application page
    And I click on continue button for your changes will not be saved
    Then I verify programs applied for field contains "CHIP" program for "PRIMARY INDIVIDUAL"
    Then I verify programs applied for field contains "Medicaid" program for "PRIMARY INDIVIDUAL"
    Then I verify that it is present on the create application page
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "CHIP" program type
    Then I deselect "MEDICAID" program type
    And  I click on Save button on Create Application Page
    And I verify "Must have at least one Application Member applying for Coverage, your changes will not be saved" warning message displayed in create application page
    And I click on continue button for your changes will not be saved
    Then I verify programs applied for field contains "CHIP" program for "PRIMARY INDIVIDUAL"
    Then I verify programs applied for field contains "Medicaid" program for "PRIMARY INDIVIDUAL"
    Then I verify that it is present on the create application page
    Then I clear the program list for "All" Application Members

  @CP-25991 @CP-25991-02 @crm-regression @ui-ats @burak
  Scenario: Restrict Update going Programs to Not Applying when Status is Determining for LONG TERM CARE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Long Term Care     | CURRENT YYYYMMDD        |  true        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I verify I will see "APPLICANT STATUS" as "Determining" for PI on Members Info Panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "HCBS" program type
    Then I choose "NOT APPLYING" as program type
    And  I click on Save button on Create Application Page
    And I verify "Must have at least one Application Member applying for Coverage, your changes will not be saved" warning message displayed in create application page
    And I click on continue button for your changes will not be saved
    Then I verify programs applied for field contains "HCBS" program for "PRIMARY INDIVIDUAL"
    Then I verify that it is present on the create application page
    And I click on the Edit button for the Primary Individual Details
    Then I deselect "HCBS" program type
    And  I click on Save button on Create Application Page
    And I verify "Must have at least one Application Member applying for Coverage, your changes will not be saved" warning message displayed in create application page
    And I click on continue button for your changes will not be saved
    Then I verify programs applied for field contains "HCBS" program for "PRIMARY INDIVIDUAL"
    Then I verify that it is present on the create application page
    Then I clear the program list for "All" Application Members

  @CP-25991 @CP-25991-03 @crm-regression @ui-ats @burak
  Scenario:  Restrict Update going Programs to Not Applying and NULL when Status is Determining for AM on MEDICAL ASSISTANCE
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
    And I initiate save applications api consumer 1 with program
      | CHIP |
      | Medicaid |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I verify I will see "APPLICANT STATUS" as "Determining" for AM on Members Info Panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I deselect "CHIP" program type for Application Member
    Then I deselect "MEDICAID" program type for Application Member
    Then Select the "NOT APPLYING" Program(s) for application member
    And  I click on Save button on Create Application Page
    And I verify "Must have at least one Application Member applying for Coverage, your changes will not be saved" warning message displayed in create application page
    And I click on continue button for application without Programs
    Then I verify user is on the create Application Member Page
    And I click on the back arrow button on add application member page
    Then I verify programs applied for field contains "CHIP" program for "APPLICATION MEMBER"
    Then I verify programs applied for field contains "Medicaid" program for "APPLICATION MEMBER"
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I deselect "CHIP" program type for Application Member
    Then I deselect "Medicaid" program type for Application Member
    Then Select the "NOT APPLYING" Program(s) for application member
    And  I click on Save button on Create Application Page
    And I verify "Must have at least one Application Member applying for Coverage, your changes will not be saved" warning message displayed in create application page
    And I click on continue button for application without Programs
    Then I verify user is on the create Application Member Page
    And I click on the back arrow button on add application member page
    Then I verify programs applied for field contains "CHIP" program for "APPLICATION MEMBER"
    Then I verify programs applied for field contains "Medicaid" program for "APPLICATION MEMBER"
    Then I clear the program list for "All" Application Members

  @CP-25991 @CP-25991-04 @crm-regression @ui-ats @burak
  Scenario:  Restrict Update going Programs to Not Applying and NULL when Status is Determining for AM on LONG TERM CARE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Long Term Care | CURRENT YYYYMMDD        | true         |
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
    And I initiate save applications api consumer 1 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I verify I will see "APPLICANT STATUS" as "Determining" for AM on Members Info Panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I deselect "HCBS" program type for Application Member
    And  I click on Save button on Create Application Page
    And I verify "Must have at least one Application Member applying for Coverage, your changes will not be saved" warning message displayed in create application page
    And I click on continue button for application without Programs
    Then I verify user is on the create Application Member Page
    And I click on the back arrow button on add application member page
    Then I verify programs applied for field contains "HCBS" program for "APPLICATION MEMBER"
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I deselect "HCBS" program type for Application Member
    Then Select the "NOT APPLYING" Program(s) for application member
    And  I click on Save button on Create Application Page
    And I verify "Must have at least one Application Member applying for Coverage, your changes will not be saved" warning message displayed in create application page
    And I click on continue button for application without Programs
    Then I verify user is on the create Application Member Page
    And I click on the back arrow button on add application member page
    Then I verify programs applied for field contains "HCBS" program for "APPLICATION MEMBER"
    Then I clear the program list for "All" Application Members

  @CP-25991 @CP-25991-05 @crm-regression @ui-ats @burak
  Scenario: Restrict Update Programs when Eligibility Status is Eligible for MEDICAL ASSISTANCE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        |  true        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with program
      | MEDICAID |
    And I initiate save applications api consumer 1 with program
      | PREGNANCY ASSISTANCE |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I click Edit Button for the Members Info Panel
    And I set all the Eligibility Status of the "ALL" as "Eligible"
    And I set all the Eligibility subprograms of "ALL" as "RANDOM"
    Then I set all the Eligibility Start Date of the Application Member as "PAST"
    Then I set all the Eligibility End Date of the Application Member as "FUTURE"
    Then I click on save button for the Members Info Panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I verify "PROGRAMS" check box are disabled
    Then I verify "Not Applying" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I verify "PROGRAMS" check box are disabled for Application Member
    Then I verify "Not Applying" check box are disabled for Application Member
    Then I clear the program list for "All" Application Members

  @CP-25991 @CP-25991-06 @crm-regression @ui-ats @burak
  Scenario: Restrict Update Programs when Eligibility Status is Eligible for LONG TERM CARE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Long Term Care     | CURRENT YYYYMMDD        |  true        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 1 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I click Edit Button for the Members Info Panel
    And I set all the Eligibility Status of the "ALL" as "Eligible"
    And I set all the Eligibility subprograms of "ALL" as "RANDOM"
    Then I set all the Eligibility Start Date of the Application Member as "PAST"
    Then I set all the Eligibility End Date of the Application Member as "FUTURE"
    Then I click on save button for the Members Info Panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I verify "HCBS" check box are disabled
    Then I verify "Not Applying" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I verify "HCBS" check box are disabled for Application Member
    Then I verify "Not Applying" check box are disabled for Application Member
    Then I clear the program list for "All" Application Members

  @CP-25991 @CP-25991-07 @crm-regression @ui-ats @burak
  Scenario: Restrict Update Programs when Eligibility Status is Not-Eligible for MEDICAL ASSISTANCE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with program
      | MEDICAID |
    And I initiate save applications api consumer 1 with program
      | PREGNANCY ASSISTANCE |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I click Edit Button for the Members Info Panel
    And I set all the Eligibility Status of the "ALL" as "Not Eligible"
    And I set all the Denial Reasons of the "ALL" as "Medicaid Eligible"
    Then I set all the Eligibility Start Date of the Application Member as "PAST"
    Then I set all the Eligibility End Date of the Application Member as "FUTURE"
    Then I click on save button for the Members Info Panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I verify "PROGRAMS" check box are disabled
    Then I verify "Not Applying" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I verify "PROGRAMS" check box are disabled for Application Member
    Then I verify "Not Applying" check box are disabled for Application Member
    Then I clear the program list for "All" Application Members

  @CP-25991 @CP-25991-08 @crm-regression @ui-ats @burak
  Scenario: Restrict Update Programs when Eligibility Status is Not-Eligible for LONG TERM CARE
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Long Term Care | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with consumerOptInInformation
      | Phone |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 5789087677  | true                  |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Male      |
    And I initiate save applications api consumer 0 with program
      | HCBS |
    And I initiate save applications api consumer 1 with program
      | HCBS |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    Given I logged into CRM with "Service Account 7" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I click Edit Button for the Members Info Panel
    And I set all the Eligibility Status of the "ALL" as "Not Eligible"
    And I set all the Denial Reasons of the "ALL" as "Medicaid Eligible"
    Then I set all the Eligibility Start Date of the Application Member as "PAST"
    Then I set all the Eligibility End Date of the Application Member as "FUTURE"
    Then I click on save button for the Members Info Panel
    And I click application id under Application Tracking tab in the Application Information panel
    And I click on the Edit button for the Primary Individual Details
    Then I verify "HCBS" check box are disabled
    Then I verify "Not Applying" check box are disabled
    And  I click on Save button on Create Application Page
    And I click on application member name hyperlink to go to Application details page
    And I click on the edit button for Add application Member
    Then I verify "HCBS" check box are disabled for Application Member
    Then I verify "Not Applying" check box are disabled for Application Member
    Then I clear the program list for "All" Application Members
