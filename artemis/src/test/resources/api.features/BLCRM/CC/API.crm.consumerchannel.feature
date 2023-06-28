Feature: API: Consumer Channel Opt-In/Out Feature

  @API-CC @API-CRM-SmokeTest @API-CRM-Regression
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @API-CP-1997 @API-CP-1997-01 @asad @API-CC @API-CRM-REGRESSION
  Scenario: Determine Usable Channels: OPT IN and Request has >1 channel
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Correspondence API for Consumer Channel
    When I run Case Correspondence API for Consumer Channel
    Then I verify the opt-in channels consumed in the response

  @API-CP-1997 @API-CP-1997-02 @asad @API-CC @API-CRM-REGRESSION
  Scenario: Determine Usable Channels: OPT OUT and Request has >1 channel
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Correspondence API for Consumer Channel
    When I run Case Correspondence API for Consumer Channel
    Then I verify the opt-out channels consumed in the response

  @API-CP-1997 @API-CP-1997-03 @asad @API-CC @API-CRM-REGRESSION
  Scenario: Determine Usable Channels: OPT OUT and Request has 1 channel
    When I will get the Authentication token for "" in "CRM"
    Given I initiated Case Correspondence API for Consumer Channel
    When I run Case Correspondence API for Consumer Channel
    Then I verify the opt-out channels consumed in the response

################################################################## muted due to new functionality
 # @API-CP-1997 @API-CP-1997-04 @asad @API-CC @API-CRM-REGRESSION
  #Scenario: When Channel is NOT usable do not include contact details
    #When I will get the Authentication token for "" in "CRM"
    #Given I initiated Case Correspondence API for Consumer Channel
    #When I run Case Correspondence API for Consumer Channel
    #Then I verify the channel contact details when channel is NOT usable
