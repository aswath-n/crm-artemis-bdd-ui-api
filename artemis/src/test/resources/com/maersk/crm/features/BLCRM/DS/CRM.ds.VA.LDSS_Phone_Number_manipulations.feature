Feature: VA LDSS Phone Number Manipulations

  @CP-19178 @CP-19188 @ui-pp @planProvider @sobir
  Scenario: CP-19178 VA LDSS Phone Number Lookup Table Loading
            CP-19188 VA LDSS API Search for PHONE NUMBERS FOR A GIVEN ZIPCODE
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    Given I get the jwt token TM with GET method
    When I send "LDSSDirectoryFile" obtained from DMAS and update a LDSS Phone Number Lookup table
    Then I will verify corresponding Agency Phone Number by provided zip with data
      | 20151 | 7876756767 |
      | 20152 | 7696713543 |
    When I send "LDSSDirectoryFile_Delete" obtained from DMAS and update a LDSS Phone Number Lookup table
    Then I will verify corresponding Agency Phone Number by provided zip with data
      |20121	| is deleted|



  @CP-30267 @ui-pp @planProvider @mital
  Scenario:Verify geolocation value when searched by zip code in provider search
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    Given I get the jwt token TM with GET method
    When I send "LDSSDirectoryFile_Insert" obtained from DMAS and update a LDSS Phone Number Lookup table
    Then I will verify corresponding geolocation for the following zip code
      |23601	|	1234567899|
      | 23605	|	7579263613|
      |23607	|	7579263611|
      |23608	|	7579263610|
      |23602	|	7579263612|
    When I send "LDSSDirectoryFile_Update" obtained from DMAS and update a LDSS Phone Number Lookup table
    Then I will verify corresponding geolocation for the following zip code
      |23601	|	1234567800|
      | 23605	|	7579263617|
      |23607	|	7579263622|
      |23608	|	7579263611|
      |23602	|	7579263615|


