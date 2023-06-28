Feature: Search Providers Results View

  @CP-94-1 @crm-regression @CP-94 @sean @ui-pp
  Scenario Outline: : 1.0 View search results
    Given I logged into the CRM Application
    Given I am on the search screen
    When  I search by "<FirstName>" firstname
    When I search by "<LastName>" lastname
    Then  I click on Search button
    Then I will see these columns: "NAME", "PROVIDER ADDRESS", "PLAN NAME", "PROGRAM NAME", "PROVIDER TYPE", "SPECIALTY" and "LAST UPDATED"
#    implementation change

      Examples:
        | FirstName | LastName |
        | Richard   | Mills    |

#  @CP-94-2 @crm-regression @Sean @CP-94 @ui-pp               ---->Implementation change
  Scenario Outline: 2.0 Order of multiple Provider Locations Returned
    Given I logged into the CRM Application
    Given I am on the search screen
    When  I search by "<FirstName>" firstname
    When  I search by "<LastName>" lastname
    Then  I click on Search button
    When I click the additional address icon
    Then I will see the list of locations
    Examples:
      | FirstName | LastName    |
      | Richard   | SCHLOSSBERG |

#  @CP-94-3  @crm-regression @Sean @CP-94 @ui-pp          ---->Implementation change
  Scenario Outline: 3.0 Additional addresses icon
    Given I logged into the CRM Application
    Given I am on the search screen
    When  I search by "<FirstName>" firstname
    When  I search by "<LastName>" lastname
    Then  I click on Search button
    Then I will see an icon with the number of additional addresses that match the search criteria
    Examples:
      | FirstName | LastName    |
      | Richard   | SCHLOSSBERG |

#  @CP-94-4  @crm-regression @crm-smoke @Sean @CP-94 @ui-pp         ---->Implementation change
    Scenario Outline: 4.0 View additional addresses
    Given I logged into the CRM Application
    Given I am on the search screen
    When  I search by "<FirstName>" firstname
    When  I search by "<LastName>" lastname
    Then  I click on Search button
    When I click the additional address icon
    Then I will see the list of locations with address and phone no
    Examples:
      | FirstName | LastName    |
      | Richard   | SCHLOSSBERG |

  @CP-94-5  @crm-regression @crm-smoke @Sean @CP-94 @ui-pp
  Scenario Outline: 6.0 Expanded View elements
    Given I logged into the CRM Application
    Given I am on the search screen
    When  I search by "<FirstName>" firstname
    When  I search by "<LastName>" lastname
    Then  I click on Search button
    When I select the carrot to the left of Provider Name
    Then I will see these elements: "LANGUAGE", "PCP INDICATOR", "OB INDICATOR", "GENDER SERVED", "PROVIDER GENDER", "PATIENT AGE RANGE" and "NPI"
    #"PATIENT MIN AGE", "PATIENT MAX AGE" removed as per new implementation changes
    Examples:
      | FirstName | LastName    |
      | Richard   | SCHLOSSBERG |

  @CP-94-6  @crm-regression @Sean @CP-94 @ui-pp
  Scenario: 9.0 Message for > 100 results returned
    Given I logged into the CRM Application
    Given I am on the search screen
    When  I entered Char "An" in FirstName
    And I click on "Advanced Search" button on provider search page
    Then  I click on Search button
    Then I will see a message "Returning 100 of X providers found, use more search criteria for more precise results."

  @CP-94-7  @crm-regression @Sean @CP-94 @ui-pp
  Scenario Outline: 10.0 Cancel button clears search results
    Given I logged into the CRM Application
    Given I am on the search screen
    When  I search by "<FirstName>" firstname
    When I click the cancel button
    Then the criteria entered will be cleared and I will remain on the same screen "<FirstName>"
    Examples:
      | FirstName |
      | Richard   |

    @CP-94-8 @CP-11194 @CP-11194-02 @crm-regression @Sean @CP-94 @ui-pp
    Scenario: 8.0 Paginate through search results
    Given I logged into the CRM Application
    Given I am on the search screen
    When  I entered Char "An" in FirstName
      When I fill out fields on provider search page with data
        | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Then  I click on Search button
    Then I will see 20 provider records in the page of the 100 results returned
    Then  I will have the option to select 20, 40, or 60 per page from the 100 results returned
      Then I will verify pagination works correctly


  @CP-94-9 @crm-regression @Sean @CP-94 @ui-pp
  Scenario: 7.0 Sort search results
    Given I logged into the CRM Application
    Given I am on the search screen
    When  I entered Char "An" in FirstName
    When I fill out fields on provider search page with data
      | PLAN NAME | AMERIGROUP COMMUNITY CARE |
    Then  I click on Search button
    Then I will be able to sort the search results by each column header in ascending or descending order