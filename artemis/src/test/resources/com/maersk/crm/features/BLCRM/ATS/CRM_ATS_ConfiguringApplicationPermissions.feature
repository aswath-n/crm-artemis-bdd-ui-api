Feature: ATS User Role and Permission Configurations

  @CP-14885 @CP-14885-01 @crm-regression @ui-ats @ozgen
  Scenario Outline: Disabled Editing, Removing App Member/Auth Rep buttons based on User Roles (NonCSR Role)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 1
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    And  I logged into CRM with "<role>" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I click application id under Application Tracking tab in the Application Information panel
    And I see Application tab is selected
    Then I verify Edit button is displayed and disabled in create application page in Application tab
    And I verify Add Application member button is displayed  and disabled in create application page in Application tab
    Then I verify set as Primary Individual button is displayed and disabled in application member panel create application page in Application tab
    Then I verify application Member Remove Primary Individual button is displayed and disabled in application member panel create application page in Application tab
    And I verify Add Authorized Representative button is displayed  and disabled in create application page in Application tab
    And I click on application member name hyperlink to go to Application details page
    And I verify Edit button and Remove button for Application Member page are displayed and disabled
    Then I click on the back arrow button on add application member page
    And I click on Added Authorized Representative
    And I click on Edit button for Authorized Representative
    And I verify Edit button for Authorized Representative page is displayed and disabled
    Then I click on the back arrow button on add application member page
    And I see Application tab is selected
    And I see Submit button is displayed and disabled in create application page in Application tab
    Examples:
      | role              |
      | Service Account 2 |

  @CP-14885 @CP-14885-02 @crm-regression @ui-ats @ozgen
  Scenario Outline: Enabled Editing, Removing App Member/Auth Rep buttons based on User Roles
    Given I logged into CRM with "<role>" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    When I click Add Authorized Representative button
    And I enter information to add "ACTIVE" Authorized Representative
    And I click save button to save Authorized Representative
    And I verify the save successfully updated message
    And I verify "Edit Primary Individual" button is displayed and clickable
    And I verify "Add Application Member" button is displayed and clickable
    And I verify "Add Authorized Representative" button is displayed and clickable
    And I click on application member name hyperlink to go to Application details page
    And I verify edit button is displayed and clickable in Add Application member
    And I verify "Remove Application Member" button is displayed and clickable
    Then I click on the back arrow button on add application member page
    And I verify "Set as Primary Individual" button is displayed and clickable
    And I click on Added Authorized Representative
    And I verify "Edit Authorized Representative" button is displayed and clickable
    And I click on back arrow button on create application page
    And I verify "Submit" button is displayed and clickable
    Examples:
      | role              |
      | Service Account 1 |
      | Service Account 5 |
      | Service Account 7 |
      | Service Tester 1  |

  @CP-19987 @CP-19987-01 @CP-23450-02 @crm-regression @ui-ats @burak
  Scenario Outline:  Verify That User is  able to update the Application Deadline Date and Not Able to Edit Members Info Panel on "Entering Data" Status with Supervisor Role
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I POST ATS save application api
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Entering Data" in the application information
    And I verify Edit button is displayed and clickable in Application Information Panel
    And I verify  the Eligibility Status of the "PI" displays as "-- --"
    And I verify I will see "APPLICANT STATUS" as "Received" for PI on Members Info Panel
    And I verify I will see "DENIAL REASONS" as "-- --" for PI on Members Info Panel
    And I verify I will see "DETERMINATION DATE" as "-- --" for PI on Members Info Panel
    And I verify I will see "START DATE" as "-- --" for PI on Members Info Panel
    And I verify I will see "END DATE" as "-- --" for PI on Members Info Panel
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Member Panel
    Examples:
      | Role              |
      | Service Account 7 |
      | Service Tester 1  |

  @CP-19987 @CP-19987-02 @CP-23450-03 @crm-regression @ui-ats-blats2 @burak
  #no longer needed for BLCRM
  Scenario Outline: Verify That User is  able to update the Application Deadline Date and Not Able to Edit Members Info Panel on "Submitted" Status with Supervisor Role
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Submitted" in the application information
    And I verify Edit button is displayed and clickable in Application Information Panel
    And I verify  the Eligibility Status of the "PI" displays as "-- --"
    And I verify I will see "APPLICANT STATUS" as "Received" for PI on Members Info Panel
    And I verify I will see "DENIAL REASONS" as "-- --" for PI on Members Info Panel
    And I verify I will see "DETERMINATION DATE" as "-- --" for PI on Members Info Panel
    And I verify I will see "START DATE" as "-- --" for PI on Members Info Panel
    And I verify I will see "END DATE" as "-- --" for PI on Members Info Panel
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Member Panel
    Examples:
      | Role              |
      | Service Account 7 |
      | Service Tester 1  |

  @CP-19987 @CP-19987-03 @CP-23450-04 @crm-regression @ui-ats @burak
  Scenario Outline: Verify That User is able to update the Application Deadline Date and Not Able to Edit Members Info Panel on "Insufficient" Status with Supervisor Role
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I POST ATS save application api
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Insufficient" in the application information
    And I verify Edit button is displayed and clickable in Application Information Panel
    And I verify  the Eligibility Status of the "PI" displays as "-- --"
    And I verify I will see "APPLICANT STATUS" as "Received" for PI on Members Info Panel
    And I verify I will see "DENIAL REASONS" as "-- --" for PI on Members Info Panel
    And I verify I will see "DETERMINATION DATE" as "-- --" for PI on Members Info Panel
    And I verify I will see "START DATE" as "-- --" for PI on Members Info Panel
    And I verify I will see "END DATE" as "-- --" for PI on Members Info Panel
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Member Panel
    Examples:
      | Role              |
      | Service Account 7 |
      | Service Tester 1  |

  @CP-19987 @CP-19987-04 @CP-23450-01 @crm-regression @ui-ats @burak @sang
  Scenario Outline: Verify That User is  able to update the Application Deadline Date and able to edit Members Info Panel on "Determining" Status with Supervisor Role
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | workPhoneNumber | RANDOM PHONE | true                  |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I POST ATS save application api
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I verify Edit button is displayed and clickable in Application Information Panel
    And I verify I will see "APPLICANT STATUS" as "Determining" for PI on Members Info Panel
    And I verify Edit button is displayed and enabled in Application Tracking Page for  Application Member Panel
    And I click Edit Button for the Members Info Panel
    And I set all the Eligibility Status of the "ALL" as "Eligible"
    And I set all the Eligibility Start Date of the Application Member as "PRESENT DATE"
    And I click on save button for the Members Info Panel
    Then I verify  the Eligibility Status of the "Primary Individual" displays as "Eligible"
    Then I verify  the Eligibility Status of the "Application Member 1" displays as "Eligible"
    Examples:
      | Role              |
      | Service Account 7 |
      | Service Tester 1  |

  @CP-19987 @CP-19987-05 @crm-regression @ui-ats @burak
  Scenario Outline: Verify That User is not able to update the Application Deadline Date on "Withdrawn" Status with Supervisor Role
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Withdrawn                  |
      | REASON                 | Not Interested in Services |
      | UPDATED BY             | 3163                       |
      | CREATED BY             | 3163                       |
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "WITHDRAWN" in the application information
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Information Tab
    Examples:
      | Role              |
      | Service Account 7 |
      | Service Tester 1  |

  @CP-19987 @CP-19987-06 @CP-23450-05 @crm-regression @ui-ats-blats2 @burak
  Scenario Outline: Verify That User is not able to update the Application Deadline Date and Not able tp edit Member Info Panel on "DUPLICATE" Status with Supervisor Role
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Duplicate |
      | UPDATED BY             | 3163      |
      | CREATED BY             | 3163      |
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "DUPLICATE" in the application information
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Information Tab
    And I verify I will see "APPLICANT STATUS" as "Duplicate" for PI on Members Info Panel
    And I verify  the Eligibility Status of the "PI" displays as "-- --"
    And I verify I will see "DENIAL REASONS" as "-- --" for PI on Members Info Panel
    And I verify I will see "DETERMINATION DATE" as "-- --" for PI on Members Info Panel
    And I verify I will see "START DATE" as "-- --" for PI on Members Info Panel
    And I verify I will see "END DATE" as "-- --" for PI on Members Info Panel
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Member Panel
    Examples:
      | Role              |
      | Service Account 7 |
      | Service Tester 1  |

  @CP-19987 @CP-19987-07 @crm-regression @ui-ats @burak
  Scenario Outline:  Verify That User is  not able to update the Application Deadline Date on "Entering Data" Status with the Non-Supervisor Role (Negative)
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | interactiveInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true           |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I POST ATS save application api
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Entering Data" in the application information
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Information Tab
    Examples:
      | Role              |
      | Service Account 1 |
      | Service Account 2 |
      | Service Account 5 |

  @CP-19987 @CP-19987-08 @crm-regression @ui-ats @burak
  Scenario Outline: Verify That User is not able to update the Application Deadline Date on "Submitted" Status with the Non-Supervisor Role (Negative)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | cellPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Information Tab
    Examples:
      | Role              |
      | Service Account 1 |
      | Service Account 2 |
      | Service Account 5 |

  @CP-19987 @CP-19987-09 @crm-regression @ui-ats @burak
  Scenario Outline:  Verify That User is not able to update the Application Deadline Date on "Insufficient" Status with Non-Supervisor Role (Negative)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I POST ATS save application api
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Insufficient" in the application information
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Information Tab
    Examples:
      | Role              |
      | Service Account 1 |
      | Service Account 2 |
      | Service Account 5 |

  @CP-19987 @CP-19987-10 @crm-regression @ui-ats @burak
  Scenario Outline: Verify That User is not  able to update the Application Deadline Date on "Determining" Status with Non-Supervisor Role (Negative)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated create application api for ats
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate create application api with a "AUTHORIZED REPRESENTATIVE"
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | submittedInd |
      | Medical Assistance | CURRENT YYYYMMDD        | true         |
    And I initiate save applications api with "AUTHORIZED REPRESENTATIVE" data for consumer 2
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 1 with program
      | Medicaid |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber  | primaryContactTypeInd |
      | workPhoneNumber | RANDOM PHONE | true                  |
    And I POST ATS save application api
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Information Tab
    Examples:
      | Role              |
      | Service Account 1 |
      | Service Account 2 |
      | Service Account 5 |

  @CP-23008 @CP-23008-01 @crm-regression @ui-ats @burak
  Scenario Outline:  Verify That User is not able to edit Application Member Panel  with  Call Center CSR Role
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | Medicaid |
    And I POST ATS save application api
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I verify Edit button is displayed and disabled in Application Tracking Page for  Application Member Panel
    Examples:
      | Role              |
      | Service Account 1 |
      | Service Account 5 |

  @CP-23008 @CP-23008-02 @CP-23008-03 @crm-regression @ui-ats @burak
  Scenario Outline:  Verify That User is  able to edit Application Member Panel with Supervisor and Eligibility Specialist Role
    Given I will get the Authentication token for "BLCRM" in "CRM"
    And I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate |
      | Medical Assistance | CURRENT YYYYMMDD        |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName |
      | RANDOM FIRST      | RANDOM LAST      |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I POST ATS save application api
    And I initiate and Post created application with update status API with following values
      | NEW APPLICATION STATUS | Determining |
      | UPDATED BY             | 3163        |
      | CREATED BY             | 3163        |
    And  I logged into CRM with "<Role>" and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    When I click on search button in search application page
    When I click on first APPLICATION ID "apiApplicationID"
    And I see application Status as "Determining" in the application information
    And I verify Edit button is displayed and enabled in Application Tracking Page for  Application Member Panel
    Examples:
      | Role              |
      | Service Account 7 |
      | Service Tester 1  |
      | Service Tester 2  |