Feature: Store Application or Case Details request/responses from VaCMS

  @API-CP-27576 @CP-26926-01 @API-CP-27576-01 @API-CC-VA @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline: Verify successful responses from VaCMS
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I initiate Case Details request and responses from VaCMS
    Then I run Case Details request and responses with "<systemName>", "<vaCMSNum>", "<numQualifier>"
    Then I validate successful response containing expected parameters "<systemName>", "<vaCMSNum>", "<numQualifier>", "<responseCode>", "<responseStatus>", "<responseDescription>", "<maProgStatus>"
    Examples:
      | systemName      | vaCMSNum   | numQualifier | responseCode | responseStatus | responseDescription                       | maProgStatus |
      | DMASmaerskIVR  | T13001461  | A            | IN001        | Success        | Success                                   | Denied       |
      | DMASmaerskIVR  | T13000846  | A            | IN001        | Success        | Success                                   | Denied       |
      | DMASmaerskIVR  | T13167612  | A            | IN001        | Success        | Success                                   | Pending      |
      | DMASmaerskIVR  | T13163215  | A            | IN001        | Success        | Success                                   | Approved     |
      | DMASmaerskIVR  | 113169012  | C            | IN001        | Success        | Success                                   | Approved     |
      | DMASmaerskIVR  | 113163215  | C            | IN001        | Success        | Success                                   | Approved     |


  @API-CP-27576 @API-CP-27576-02 @API-CC-VA @API-CRM-Regression @muhabbat
  Scenario Outline: Verify failed responses from VaCMS
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I initiate Case Details request and responses from VaCMS
    Then I run Case Details request and responses with "<systemName>", "<vaCMSNum>", "<numQualifier>"
    Then I validate Failed response containing expected parameters "<systemName>", "<vaCMSNum>", "<numQualifier>", "<responseCode>", "<responseStatus>", "<responseDescription>"
    Examples:
      | systemName      | vaCMSNum   | numQualifier | responseCode | responseStatus | responseDescription                       |
      | DMASmaerskIVR  | T13086124  | A            | IN002        | Failure        | Application Number doesn't exist in VaCMS |
      | DMASmaerskIVR  | TS13086124 | A            | IN003        | Failure        | Invalid Application Number                |
      | DMASmaerskIVR  | T13160627  | A            | IN004        | Failure        | Not a Medicaid Application Number         |
      | wrongSystemName | T13160627  | A            | IN009        | Failure        | Invalid System Name                       |
      | DMASmaerskIVR  | 114047889  | C            | IN005        | Failure        | Case Number does not exist in VaCMS       |
      | DMASmaerskIVR  | 1T3140613  | C            | IN006        | Failure        | Invalid Case Number                       |
      | DMASmaerskIVR  | 113140613  | C            | IN007        | Failure        | Not a Medicaid Case Number                |
      | wrongSystemName | 113140613  | C            | IN009        | Failure        | Invalid System Name                       |
