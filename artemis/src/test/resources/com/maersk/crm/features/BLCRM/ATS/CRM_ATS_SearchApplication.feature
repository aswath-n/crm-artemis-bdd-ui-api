Feature: ATS Search Application Page

  @CP-12701 @CP-12701-01 @crm-regression @ui-ats @munavvar
  Scenario: Navigate to Search Application screen
    Given I logged into CRM
    Then I will see a sub-menu as "SEARCH APPLICATION" under "APPLICATION TRACKING"

  @CP-12701 @CP-12701-02 @CP-36056 @CP-36056-01 @crm-regression @ui-ats @munavvar @priyal
  Scenario: Navigate to Search Application Page and verify header with its name and Verify warning message displayed or not for 200 results
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then the UI will display the SEARCH APPLICATION screen.
    And I search for a specific application in Application Tracking search by
      | STATUS    | APPLICATION CYCLE |
      | Duplicate | Renewal           |
    When I click on search button in search application page
    Then I see Warning pop-up message not displayed when recods less then 200
    And I verify that latest application is listed as first
    Then I verify "lessThan" 200 records on Application Search page
    And I click on cancel button in Search Application page
    And I search for a specific application in Application Tracking search by
      | STATUS    |
      | Duplicate |
    When I click on search button in search application page
    Then I should get pop up with error message "Search results in excess of 200 results, please enter additional search criteria to limit search results"
    And I verify that latest application is listed as first
    Then I verify "equal" 200 records on Application Search page

  @CP-12701 @CP-12701-03 @crm-regression @ui-ats @munavvar
  Scenario: Verify navigation to search application and Search Application Page fields displayed
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then I verify Search Application basic page fields
    And I verify options for dropdown "STATUS" are present
      | Approved to Post | Closed | Complete | Conflicts | Determining | Duplicate | Entering Data | Expired | Incomplete | Insufficient | Not Applying | Posted | Ready to Post | Received | Review | Reviewing Targets | Submitted | Targets Confirmed | Targets Identified | Targets Unidentified | Withdrawn |
    And I verify options for dropdown "APPLICATION CYCLE" are present
      | New | Renewal |
    And I verify options for dropdown "PROGRAM" are present
      | Medicaid | CHIP | Pregnancy Assistance | HCBS |
    And I verify options for dropdown "CHANNEL" are present
      | Email | Fax | Interface | Mail | Phone | Web | Web Chat |

  @CP-12701 @CP-12701-04 @crm-regression @ui-ats @munavvar
  Scenario Outline: Search Application - application id field with different inputs
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then I provide application id as "<applicationId>" and it should accept it(shouldn't be empty): "<isAccepted>"
    And I print description for test case "<description>"
    Examples:
      | applicationId         | isAccepted | description                                 |
      | abcsfsdfs%$           | false      | application id field should be only numeric |
      | 12345345a             | false      | application id field should be only numeric |
      | 123456789123456789121 | false      | the max length for application id is 20     |
      | 549                   | true       | positive scenario                           |

  @CP-12701 @CP-12701-05 @crm-regression @ui-ats @munavvar
  Scenario Outline: Search Application - first name field with different inputs
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then I provide first name as "<firstName>" and it should accept it(shouldn't be empty): "<isAccepted>"
    And I print description for test case "<description>"
    Examples:
      | firstName                           | isAccepted | description                               |
      | 12345                               | false      | first name cannot be numeric              |
      | %$&#                                | false      | first name cannot have special characters |
      | Qwertyuiopasdfghjklzxcvbnmqwertyuia | false      | the max length for first name  is 30      |
      | John                                | true       | positive scenario                         |

  @CP-12701 @CP-12701-06 @crm-regression @ui-ats @munavvar
  Scenario Outline: Search Application - last name field with different inputs
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then I provide last name as "<lastName>" and it should accept it(shouldn't be empty): "<isAccepted>"
    And I print description for test case "<description>"
    Examples:
      | lastName                            | isAccepted | description                              |
      | 12345                               | false      | last name cannot be numeric              |
      | %$&#                                | false      | last name cannot have special characters |
      | Qwertyuiopasdfghjklzxcvbnmqwertyuia | false      | the max length for first name  is 30     |
      | Doe                                 | true       | positive scenario                        |

  @CP-12701 @CP-12701-07 @crm-regression @ui-ats @munavvar
  Scenario Outline: Search Application - applicant date of birth field with different inputs
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then I provide applicant date of birth as "<dob>" and it should accept it(shouldn't be empty): "<isAccepted>"
    And I print description for test case "<description>"
    Examples:
      | dob        | isAccepted | description                                      |
      | 00000000   | false      | invalid date input foramt - cannot be 00/00/0000 |
      | 12abc#$8   | false      | invalid date format                              |
      | 01/01/5000 | false      | cannot be in the future                          |
      | 03261996   | true       | positive scenario                                |

  @CP-12701 @CP-12701-08 @crm-regression @ui-ats @munavvar
  Scenario: Search Application - search button and cancel button exist
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then I verify search and cancel buttons are displayed in search application page

  @CP-12701 @CP-12701-09 @crm-regression @ui-ats @munavvar
  Scenario: Search Application - cancel button should clear inputs
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | APPLICATION ID | FIRST NAME | LAST NAME | APPLICANT DOB | STATUS   | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 121            | John       | Doe       | 03/26/1996    | Complete | New               | Medicaid | Email   |
    When I click on cancel button in Search Application page
    Then all search inputs in search application page should be cleared up


    #Note: the result of this test case will depend on data in the system
  @CP-12701 @CP-12701-10 @crm-regression @ui-ats @munavvar
  Scenario: Verify navigation to search application and performed search with cancel
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | LAST NAME | PROGRAM  |
      | Last      | Medicaid |
    When I click on search button in search application page
    Then I can see more than "0" rows as search result in Application Tracking Search
    When I click on cancel button in Search Application page
    Then The search result in Search Application page should be clear

  @CP-12702 @CP-12702-01 @crm-regression @ui-ats @munavvar
  Scenario: Verify navigation to search application and performed empty search
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    When I search for a specific application in Application Tracking search by
      | APPLICATION ID | FIRST NAME |
      |[blank]|            |
    When I click on search button in search application page
    Then I should get pop up with error message "Enter search parameters."

  #Note: the result of this test case will depend on data in the system
  @CP-12702 @CP-12702-02 @crm-regression @ui-ats @munavvar
  Scenario: Verify navigation to search application and performed search
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | APPLICATION ID | FIRST NAME | PROGRAM  |
      | 630            | Marta      | Medicaid |
#       example of dynamic search with different keys
#      | APPLICATION ID | FIRST NAME | LAST NAME | APPLICANT DOB | STATUS   | APPLICATION CYCLE | PROGRAM  | CHANNEL |
#      | 121            | John       | Doe       | 03/26/1996    | Complete | New               | Medicaid | Email   |
    When I click on search button in search application page
    Then I can see more than "0" rows as search result in Application Tracking Search
    Then I see this results are part of the search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME  | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 630            | Werderssaq | Marta      | 09-03-1998      | Entering Data | Renewal           | Medicaid | Phone   |

  @CP-12702 @CP-12702-03 @crm-regression @ui-ats @munavvar
  Scenario: Search application and performed search 10 rows displayed
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | FIRST NAME |
      | Las        |
    When I click on search button in search application page
    Then I see first "10" rows

  @CP-12702 @CP-12702-04 @crm-regression @ui-ats @munavvar
  Scenario: Search application and performed search newest first
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | FIRST NAME |
      | Smi        |
    When I click on search button in search application page
    Then I verify that latest application is listed as first

  @CP-12702 @CP-12702-05 @crm-regression @ui-ats @munavvar
  Scenario: Search application and performed no search results
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    When I search for a specific application in Application Tracking search by
      | APPLICATION ID | FIRST NAME |
      | 9999999        | John       |
    When I click on search button in search application page
    Then I validate no search result message

  @CP-13634 @CP-13634-01 @CP-34251 @CP-34251-01 @CP-12704 @CP-12704-01 @crm-regression @ui-ats @munavvar @ats-smoke
  Scenario: Field associated with retrieval
    Given I logged into CRM
    When I navigate to Create "MEDICAL ASSISTANCE" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "Pregnancy Assistance" as program type
    Then I click on Save button on Create Application Page
    And I verify Application created Success Message and Store Application ID value created
    And I click on + Add Application Member button
    Then I fill in the following application member values
      | FIRST NAME | RANDOM 30 |
      | LAST NAME  | RANDOM 30 |
    And click on save button for add application member
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created application Id in the search application page Search menu
    When I click on search button in search application page
    Then I see first "2" rows
    Then I see  results in given order in the search results in Application Tracking Search:
      | APPLICATION ID | APPLICATION CODE | LAST NAME | FIRST NAME | APPLICANT DOB | STATUS | APPLICATION CYCLE | APPLICATION RECEIVED DATE | PROGRAM | CHANNEL | EXTERNAL APP ID |

  @CP-13634 @CP-13634-02 @crm-regression @ui-ats @munavvar
  Scenario:  Displaying multiple individuals in a case
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | LAST NAME |
      | Akabirova |
    When I click on search button in search application page
    Then I see first "4" rows
    Then I see this results are part of the search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS    | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 743            | Akabirova | Munavvar   | 05-05-1995      | Submitted | New               | Medicaid | Fax     |
      | 743            | Akabirova | Sophia     | -- --           | Submitted | New               | -- --    | Fax     |
      | 743            | Akabirova | Madina     | -- --           | Submitted | New               | -- --    | Fax     |
      | 743            | Akabirova | Yulduz     | -- --           | Submitted | New               | -- --    | Fax     |

  @CP-13634 @CP-13634-03 @crm-regression @ui-ats @munavvar
  Scenario: Retrieving by single field criterion
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | LAST NAME |
      | Patel     |
    When I click on search button in search application page
    Then I see first "3" rows
    Then I see this results are part of the search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM | CHANNEL |
      | 1167           | Patel     | Vishal     | -- --           | Entering Data | New               | -- --   | Web     |
      | 1166           | Patel     | Ramesh     | -- --           | Entering Data | Renewal           | -- --   | Phone   |
      | 1165           | Patel     | Vijay      | -- --           | Entering Data | New               | -- --   | Email   |

  @CP-13634 @CP-13634-04 @crm-regression @ui-ats @munavvar
  Scenario: Retrieving by multiple field criteria
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | FIRST NAME | LAST NAME |
      | Juan       | Herrera   |
    When I click on search button in search application page
    Then I see first "2" rows
    Then I see this results are part of the search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM | CHANNEL   |
      | 1169           | Herrera   | Juanita    | -- --           | Entering Data | New               | -- --   | Phone     |
      | 248            | Herrera   | Juan       | 05-05-1994      | Entering Data | New               | HCBS    | Interface |

  @CP-13750 @CP-13750-01 @crm-regression @ui-ats @munavvar
  Scenario: Verify search displayed excessive results (max query time)
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | FIRST NAME |
      | john       |
    When I click on search button in search application page
    Then I should get pop up with error message "Search results in excess of 200 results, please enter additional search criteria to limit search results"

  @CP-13750 @CP-13750-02 @CP-13750-03 @crm-regression @ui-ats @munavvar
  Scenario: Verify  Search returning more than one page of records
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | FIRST NAME | PROGRAM  |
      | Ana        | Medicaid |
    When I click on search button in search application page
    When I can see more than "10" rows as search result in Application Tracking Search
    Then I see forward Arrow & number of pages for retrieved results is displayed at the bottom of the result list

  @CP-13750 @CP-13750-03.1 @crm-regression @ui-ats @munavvar
  Scenario: Verify Option to select number of results per page
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | FIRST NAME | STATUS        | PROGRAM |
      | Sa         | Entering Data | CHIP    |
    When I click on search button in search application page
    Then I can see more than "10" rows as search result in Application Tracking Search
  #  Then I see Item Count per page dropdown is displayed at the bottom of the result list
    And I select Item Count in search result page as "show 20"
    Then I can see more than "20" rows as search result in Application Tracking Search
    And I select Item Count in search result page as "show 40"
    Then I can see more than "40" rows as search result in Application Tracking Search

  @CP-13750 @CP-13750-04 @crm-regression @ui-ats @munavvar
  Scenario: Verify Search result retrievel is displaying by partial parameters
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | FIRST NAME | LAST NAME |
      | Li         | Li        |
    When I click on search button in search application page
    Then I see this results are part of the search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 1652           | Abdulina  | Lilia      | 1955-09-08      | Entering Data | New               | CHIP     | Fax     |
      | 1651           | Litherin  | Lillanna   | 1993-09-08      | Entering Data | New               | HCBS     | Mail    |
      | 1650           | Lilly     | Lillian    | 1998-09-09      | Entering Data | New               | Medicaid | Email   |

  @CP-13750 @CP-13750-05 @crm-regression @ui-ats @munavvar
  Scenario: Verify Search result retrieval is displaying by Default sort (newest-oldest)
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | FIRST NAME | LAST NAME |
      | La         | Las       |
    When I click on search button in search application page
    Then I verify that latest application is listed as first


  @CP-13750 @CP-13750-06 @crm-regression @ui-ats @munavvar
  Scenario: Verify Search result row has Option to sort results by column
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | FIRST NAME | LAST NAME |
      | Lil        | Li        |
    When I click on search button in search application page
    When I sorted in the results in the application tracking search by column "APPLICATION ID"
    Then I see this results in search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 1790           | Lio       | Lili       | 2000-12-15      | Entering Data | New               | -- --    | Email   |
      | 1650           | Lilly     | Lillian    | 1998-09-09      | Entering Data | New               | Medicaid | Email   |
      | 1652           | Abdulina  | Lilia      | 1955-09-08      | Entering Data | New               | CHIP     | Fax     |
      | 1651           | Litherin  | Lillanna   | 1993-09-08      | Entering Data | New               | HCBS     | Mail    |
    When I sorted in the results in the application tracking search by column "APPLICATION ID"
    Then I see this results in search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 1650           | Lilly     | Lillian    | 1998-09-09      | Entering Data | New               | Medicaid | Email   |
      | 1651           | Litherin  | Lillanna   | 1993-09-08      | Entering Data | New               | HCBS     | Mail    |
      | 1652           | Abdulina  | Lilia      | 1955-09-08      | Entering Data | New               | CHIP     | Fax     |
      | 1790           | Lio       | Lili       | 2000-12-15      | Entering Data | New               | -- --    | Email   |
    When I sorted in the results in the application tracking search by column "LAST NAME"
    Then I see this results in search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 1651           | Litherin  | Lillanna   | 1993-09-08      | Entering Data | New               | HCBS     | Mail    |
      | 1790           | Lio       | Lili       | 2000-12-15      | Entering Data | New               | -- --    | Email   |
      | 1650           | Lilly     | Lillian    | 1998-09-09      | Entering Data | New               | Medicaid | Email   |
      | 1652           | Abdulina  | Lilia      | 1955-09-08      | Entering Data | New               | CHIP     | Fax     |
    When I sorted in the results in the application tracking search by column "FIRST NAME"
    Then I see this results in search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 1650           | Lilly     | Lillian    | 1998-09-09      | Entering Data | New               | Medicaid | Email   |
      | 1651           | Litherin  | Lillanna   | 1993-09-08      | Entering Data | New               | HCBS     | Mail    |
      | 1652           | Abdulina  | Lilia      | 1955-09-08      | Entering Data | New               | CHIP     | Fax     |
      | 1790           | Lio       | Lili       | 2000-12-15      | Entering Data | New               | -- --    | Email   |

    When I sorted in the results in the application tracking search by column "APPLICANT DOB"
    Then I see this results in search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 1652           | Abdulina  | Lilia      | 1955-09-08      | Entering Data | New               | CHIP     | Fax     |
      | 1651           | Litherin  | Lillanna   | 1993-09-08      | Entering Data | New               | HCBS     | Mail    |
      | 1650           | Lilly     | Lillian    | 1998-09-09      | Entering Data | New               | Medicaid | Email   |
      | 1790           | Lio       | Lili       | 2000-12-15      | Entering Data | New               | -- --    | Email   |
    When I sorted in the results in the application tracking search by column "STATUS"
    Then I see this results in search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 1790           | Lio       | Lili       | 2000-12-15      | Entering Data | New               | -- --    | Email   |
      | 1650           | Lilly     | Lillian    | 1998-09-09      | Entering Data | New               | Medicaid | Email   |
      | 1652           | Abdulina  | Lilia      | 1955-09-08      | Entering Data | New               | CHIP     | Fax     |
      | 1651           | Litherin  | Lillanna   | 1993-09-08      | Entering Data | New               | HCBS     | Mail    |
    When I sorted in the results in the application tracking search by column "APPLICATION CYCLE"
    Then I see this results in search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 1790           | Lio       | Lili       | 2000-12-15      | Entering Data | New               | -- --    | Email   |
      | 1650           | Lilly     | Lillian    | 1998-09-09      | Entering Data | New               | Medicaid | Email   |
      | 1652           | Abdulina  | Lilia      | 1955-09-08      | Entering Data | New               | CHIP     | Fax     |
      | 1651           | Litherin  | Lillanna   | 1993-09-08      | Entering Data | New               | HCBS     | Mail    |
    When I sorted in the results in the application tracking search by column "PROGRAM"
    Then I see this results in search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 1650           | Lilly     | Lillian    | 1998-09-09      | Entering Data | New               | Medicaid | Email   |
      | 1651           | Litherin  | Lillanna   | 1993-09-08      | Entering Data | New               | HCBS     | Mail    |
      | 1652           | Abdulina  | Lilia      | 1955-09-08      | Entering Data | New               | CHIP     | Fax     |
      | 1790           | Lio       | Lili       | 2000-12-15      | Entering Data | New               | -- --    | Email   |
    When I sorted in the results in the application tracking search by column "CHANNEL"
    Then I see this results in search results in Application Tracking Search:
      | APPLICATION ID | LAST NAME | FIRST NAME | APPLICATION DOB | STATUS        | APPLICATION CYCLE | PROGRAM  | CHANNEL |
      | 1651           | Litherin  | Lillanna   | 1993-09-08      | Entering Data | New               | HCBS     | Mail    |
      | 1652           | Abdulina  | Lilia      | 1955-09-08      | Entering Data | New               | CHIP     | Fax     |
      | 1790           | Lio       | Lili       | 2000-12-15      | Entering Data | New               | -- --    | Email   |
      | 1650           | Lilly     | Lillian    | 1998-09-09      | Entering Data | New               | Medicaid | Email   |

  @CP-23901 @CP-23901-01 @crm-regression @ui-ats @burak
  Scenario: Verify the warning message for External Application ID which contains spaces
    And  I logged into CRM and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | EXTERNAL APPLICATION ID |
      | 23    312    21         |
    When I click on search button in search application page
    Then I verify Warning text displayed for "EXTERNAL APPLICATION ID" field

  @CP-23901  @CP-23901-02 @crm-regression @ui-ats @burak
  Scenario: Verify the External Application ID search results will display same as it was entered into the search field.
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationDeadlineDate | externalApplicationId  |
      | Long Term Care  | CURRENT YYYYMMDD        | CURRENT YYYYMMDD        | RANDOM EXTERNAL APP ID |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And  I logged into CRM and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I entered created External Application ID to search field
    When I click on search button in search application page
    Then I verify the search result "External Application ID" field matches with the search parameter

  @CP-23901  @CP-23901-03 @crm-regression @ui-ats @burak
  Scenario: Verify the External Application ID search results will display in reverse chronological order using the Application ID
    Given I will get the Authentication token for "<projectName>" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationDeadlineDate | externalApplicationId                |
      | Long Term Care  | CURRENT YYYYMMDD        | CURRENT YYYYMMDD        | 124EWSDJI@#*!@$&HWBFBEWRY@&#@$HWEBDS |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType | applicationReceivedDate | applicationDeadlineDate | externalApplicationId                |
      | Long Term Care  | CURRENT YYYYMMDD        | CURRENT YYYYMMDD        | 124EWSDJI@#*!@$&HWBFBEWRY@&#@$HWEBDS |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And  I logged into CRM and select a project "BLCRM"
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | EXTERNAL APPLICATION ID              |
      | 124EWSDJI@#*!@$&HWBFBEWRY@&#@$HWEBDS |
    When I click on search button in search application page
    Then I verify that latest application is listed as first

  @CP-31937 @CP-31937-01 @crm-regression @ui-ats @sang
  Scenario: Verify new CASE ID and CASE TYPE Label is present and default CASE TYPE is Internal when searching application with Case Id criteria
    Given I logged into CRM
    When I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then I verify the new search fields CASE ID and CASE TYPE is present
    And I search for a specific application in Application Tracking search by
      | CASE ID |
      | 4624    |
    Then I verify the "CASE TYPE" field value is "Internal"

  @CP-31937 @CP-31937-02 @crm-regression @ui-ats @sang
  Scenario: Verify search result from Search Application page with case Id search
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle |
      | Medical Assistance | CURRENT YYYYMMDD        | New              |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Female     |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Female     |
    And I initiate save applications api consumer 1 with program
      | CHIP |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the Application Consumer ID ,Consumer Role Type and User ID from API response
    Then I initiate link case api 0
      | ROLE       | PRIMARY INDIVIDUAL |
      | actionType | CREATE_NEW         |
    Then I initiate link case api 1
      | ROLE       | APPLICATION MEMBER |
      | actionType | CREATE_NEW         |
    And I run the link case api
    And I capture the case id from link case api
    And I capture the consumer id from link case api
    Given I logged into CRM
    When I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | CASE ID              |
      | AUTO CREATED CASE ID |
    When I click on search button in search application page
    Then I verify the search result "CASE ID" field matches with the search parameter

  @CP-31937 @CP-31937-03 @crm-regression @ui-ats @sang
  Scenario: Verify no search result message for CASE ID search that does not have an application link
    Given I logged into CRM
    When I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | CASE ID |
      | 2       |
    When I click on search button in search application page
    Then I validate no search result message

  @CP-34251 @CP-34251-02 @crm-regression @ui-ats @burak
  Scenario: Verify search result from Search Application page with application code search
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle |
      | Medical Assistance | CURRENT YYYYMMDD        | New              |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Female     |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Female     |
    And I initiate save applications api consumer 1 with program
      | CHIP |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the application GUID from API response
    And I logged into CRM
    When I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | APPLICATION CODE             |
      | API CREATED APPLICATION CODE |
    When I click on search button in search application page
    Then I verify the search result "APPLICATION CODE" field matches with the search parameter

  @CP-34251 @CP-34251-03 @crm-regression @ui-ats @burak
  Scenario: Verify there is no search result from Search Application page with invalid application code search
    Given I will get the Authentication token for "BLCRM" in "CRM"
    When I initiated create application api for ats
    And I initiate save applications api with application level key values
      | applicationType    | applicationReceivedDate | applicationCycle |
      | Medical Assistance | CURRENT YYYYMMDD        | New              |
    And I initiate save applications api with "PRIMARY INDIVIDUAL" data for consumer 0
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Female     |
    And I initiate save applications api consumer 0 with program
      | CHIP |
    And I initiate save applications api consumer 0 with applicationConsumerPhone
      | phoneType       | phoneNumber | primaryContactTypeInd |
      | workPhoneNumber | 2789087677  | true                  |
    And I initiate create application api with a "APPLICATION MEMBER"
    And I initiate save applications api with "APPLICATION MEMBER" data for consumer 1
      | consumerFirstName | consumerLastName | dateOfBirth | ssn        | genderCode |
      | RANDOM FIRST      | RANDOM LAST      | RANDOM DOB  | RANDOM SSN | Female     |
    And I initiate save applications api consumer 1 with program
      | CHIP |
    And I POST ATS save application api
    Then I get response from application ats api with status code "200" and status "success"
    And I get the application GUID from API response
    And I logged into CRM
    When I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I enter created Application ID in the search menu
    And I get the application GUID from API response
    And I search for a specific application in Application Tracking search by
      | APPLICATION CODE                |
      | RANDOM CREATED APPLICATION CODE |
    When I click on search button in search application page
    Then I validate no search result message

  @CP-34252 @CP-34252-09 @CP-34252-10 @CRM-Regression @chandrakumar
  Scenario Outline: Search the application using search API passing application code as a search parametr when application created through UI
    Given I will get the Authentication token for "BLCRM" in "CRM"
    Given I logged into CRM
    When I navigate to Create "<applicationType>" application page
    And I provide mandatory primary individual details on Create Application Page
    Then I choose "<programTypeName>" as program type
    Then I fill in the following "<applicationType>" Primary Individual with the following values
      | GENDER              | Female             |
      | ARE YOU PREGNANT    | Yes                |
      | NO. BABIES EXPECTED | 8                  |
      | EXPECTED DUE DATE   | random future date |
      | SSN                 | Numeric 9          |
    Then I click on Save button on Create Application Page
    Then I verify Application created Success Message and Store Application ID value created
    Then I get application id from the Create Application Page
    When I click on application tracking tab to navigate to Application Tracking page
    And I get the applicaion code from Application Tracking Page
    And I click on application tab page after creating application
    Then I click on Submit button only in Create Application Page
    And I click on back arrow button on create application page
    When I initiated search application api for ats
    And I search for the created application using search api by "APPLICATION CODE" for ats
    Then I verify matching records for ats by "APPLICATION CODE" from the search response
    Examples:
      | applicationType    | programTypeName |
      | LONG TERM CARE     | HCBS            |
      | MEDICAL ASSISTANCE | Medicaid        |

  @CP-12704 @CP-12704-02 @crm-regression @ui-ats @vinuta
  Scenario Outline: Verify Search by date range for Application Received Date
    Given I logged into CRM
    When I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then I verify Search Application basic page fields
    When I search for a specific application in Application Tracking search by
      | DATE RANGE TYPE           | APP RECEIVED DATE FROM   | APP RECEIVED DATE TO   |
      | Application Received Date | <APP RECEIVED DATE FROM> | <APP RECEIVED DATE TO> |
    And I click on search button in search application page
    Then I verify the search result "APPLICATION RECEIVED DATE" field matches with the search parameter
    When I sorted in the results in the application tracking search by column "APPLICATION RECEIVED DATE"
    Then I verify the search result "APPLICATION RECEIVED DATE" field matches with the search parameter
    Examples:
      | APP RECEIVED DATE FROM | APP RECEIVED DATE TO |
      | TODAY-1                | TODAY                |
      | TODAY                  |[blank]|
      |[blank]| TODAY                |

  @CP-12704 @CP-12704-03 @crm-regression @ui-ats @vinuta
  Scenario: Verify No Search result when there are no applications for the given application received date range
    Given I logged into CRM
    When I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then I verify Search Application basic page fields
    When I search for a specific application in Application Tracking search by
      | DATE RANGE TYPE           | APP RECEIVED DATE FROM | APP RECEIVED DATE TO |
      | Application Received Date | FUTURE                 | FUTURE               |
    And I click on search button in search application page
    Then I validate no search result message

  @CP-12704 @CP-12704-04 @crm-regression @ui-ats @vinuta
  Scenario: Validation for ApplicationReceivedDateFrom and ApplicationReceivedDateTo on Application Search Page
    Given I logged into CRM
    When I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    Then I verify Search Application basic page fields
    When I search for a specific application in Application Tracking search by
      | DATE RANGE TYPE           | APP RECEIVED DATE FROM | APP RECEIVED DATE TO |
      | Application Received Date | FUTURE                 | TODAY                |
    And I click on search button in search application page
    Then I verify error displayed under app received date to field
    When I search for a specific application in Application Tracking search by
      | APP RECEIVED DATE FROM | APP RECEIVED DATE TO |
      | FUTURE                 |[blank]|
    And I click on search button in search application page
    Then I verify error displayed under app received date from field

  @CP-35675 @CP-35675-01 @crm-regression @ui-ats @vinuta
  Scenario:Highlight Selected Search Results' Item When Navigating Back to Application Search Results
    Given I logged into CRM
    And I navigate to "APPLICATION TRACKING" and then to "SEARCH APPLICATION" in the left menu
    And I search for a specific application in Application Tracking search by
      | APPLICATION ID |
      | 2              |
    When I click on search button in search application page
    When I click on first APPLICATION ID "2"
    And I click on the back arrow in header row on Application Tracking page
    Then I verify I am on Search Application page
    And I verify previously selected row is highlighted
