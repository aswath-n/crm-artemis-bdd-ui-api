Feature: User Will Validate All Correspondence Destinations

  @CP-3202-1  @CP-3202 @Sean @ui-ecms1
  Scenario Outline: Validating create correspondence without filling address line 1
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I verify error is displayed for the Address Line one  as I save
    Examples:
      | address2 | CITY   | State    | zipcode |
      | 302      | Reston | Virginia | 20190   |


  @CP-3202-2 @CP-43469 @CP-3202 @CP-43469-1 @Sean  @ui-ecms1
  Scenario Outline: Validating create correspondence options successfully keeping address line 2 blank,City with 45 characters and Zipcode 9 digits
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Case Consumer Send Immediately - CCSENDIMM" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62,NY 20190-5896 |
    Examples:
      | address1        | address2 | CITY                                          | State    | zipcode   |
      | 123 Main street |          | (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62 | New York | 201905896 |

  @CP-3202-2 @CP-43469 @CP-3202 @CP-43469-2 @Sean  @ui-ecms1
  Scenario Outline: Validating create correspondence with City > 45 characters and Zipcode with hyphen > 9 digits
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Case Consumer Send Immediately - CCSENDIMM" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62,NY 20190-5896 |
    Examples:
      | address1        | address2 | CITY                                                         | State    | zipcode        |
      | 100 Main street |          | (!SYc5^3Q^@$((*B**dnqG%5Fr)(c24)m!xUC4C&m*K62_extracharcters | New York | 201-9058967568 |

  @CP-3202-3 @CP-43469 @CP-3202 @CP-43469-3 @Sean @ui-ecms1
  Scenario Outline: Validating create correspondence without city name
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I try to save but expect to get error message on city filed as I save

    Examples:
      | address1        | address2 | State    | zipcode |
      | 123 main street | 302      | Virginia | 20190   |

  @CP-3202-4  @CP-3202 @Sean @ui-ecms1
  Scenario Outline: Validating create correspondence without state name
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I expect to get error message on state filed

    Examples:
      | address1        | address2 | CITY   | zipcode |
      | 123 main street | 302      | Reston | 20190   |


  @CP-3202-5 @CP-43469  @CP-3202  @CP-43469-4 @Sean  @ui-ecms1
  Scenario Outline: validating create correspondence without zipcode
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I expect to get error message on on Zipcode as I save
    Examples:
      | address1        | address2 | CITY   | State    |
      | 123 main street | 302      | Reston | Virginia |

  @CP-43469 @CP-43469-5 @Keerthi  @ui-ecms1
  Scenario Outline: validating create correspondence with invalid zipcode
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I expect to get error message on invalid Zipcode format as I save
    Examples:
      | address1        | address2 | CITY   | State    | zipcode |
      | 123 main street | 302      | Reston | Virginia | 123456  |

  @CP-43469 @CP-43469 @CP-43469-6 @Keerthi  @ui-ecms1
  Scenario Outline: Validating create correspondence options successfully with City < 45 characters and Zipcode field 5 characters (BLCRM)
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Case Consumer Send Immediately - CCSENDIMM" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I click to save the Outbound Correspondence Request
    Then I should be navigated to View Outbound Correspondence details
      | 100 Main street  Alpharetta,GA 30005 |
    Examples:
      | address1        | CITY       | State   | zipcode |
      | 100 Main street | Alpharetta | Georgia | 30005   |


  @CP-43469 @CP-43469-7 @Keerthi  @ui-ecms1
  Scenario Outline: Validating create correspondence options successfully with Zipcode field with invalid characters
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Case Consumer - CCONLY" as a type
    And I select any value from Consumer(s) dropdown
    And I scroll down and check the first recipient
    And I select mail opt in checkbox for the first recipient and click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I expect to get error message on on Zipcode as I save
    Examples:
      | address1        | CITY   | State    | zipcode   |
      | 123 main street | Reston | Virginia | @#$ @#abc |


  @CP-3202-6  @CP-3202 @Sean  @ui-ecms1
  Scenario Outline: validating create correspondence without filling email_address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Send grid - emailOnly" as a type
    And I scroll down and check the first recipient
    Then I select email in check Box click other
    Then I verify by entering valid "<emailaddress>"
    Then I expect to get blank email format message on emailfield as I save
    Examples:
      | emailaddress |
      |              |

  @CP-3202-7  @CP-3202 @Sean  @ui-ecms1
  Scenario Outline: validating create correspondence with filling out with wrong format email address
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Send grid - emailOnly" as a type
    And I scroll down and check the first recipient
    Then I select email in check Box click other
    Then I verify by entering valid "<emailaddress>"
    Then I expect to get wrong email format message on emailfield as I save
    Examples:
      | emailaddress      |
      | 1213sds.gmail.com |


  @CP-3202-8  @CP-3202 @Sean  @ui-ecms1
  Scenario Outline: validating create correspondence with No phone number for text Channel
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "Text - textOnly" as a type
    And I scroll down and check the first recipient
    Then I select Text opt in check box click other
    Then I enter valid phoneNumber "<phoneNumber>"
    Then I expect to get error message on Text Channel as I save
    Examples:
      | phoneNumber |
      |             |


  @CP-3202-9   @CP-3202 @Sean  @ui-ecms1
  Scenario Outline: validating create correspondence options without faxNumber
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "All Channels Regarding Case Id Only - allChCase" as a type
    And I scroll down and check the first recipient
    Then I select Fax in check box, select other
    Then I verify by entering valid data in the Fax filed "<faxNumber>" at Correspondence
    Then I expect to get error message on Fax field as I save
    Examples:
      | faxNumber |
      |           |


  @CP-3202-10   @CP-3202 @Sean  @ui-ecms1
  Scenario Outline: validating create correspondence options successfully
    Given I will get the Authentication token for "<projectName>" in "CRM"
    And I have a consumer on a case that wants to send an Outbound Correspondence
    Then I logged into CRM with "Service User 1" and select a project "BLCRM"
    And I minimize Genesys popup if populates
    And I click on initiate contact record
    Then When I enter Search criteria fields for a newly created consumer
    And I click on Search Button on Search Consumer Page
    And I link the contact to an existing Case with consumer id
    And I create an Outbound Correspondence
    And I have selected "All Channels Regarding Case Id Only - allChCase" as a type
    And I scroll down and check the first recipient
    Then I select Fax in check box, select other
    Then I verify by entering valid data in the Fax filed "<faxNumber>" at Correspondence
    Then I select email in check Box click other
    Then I verify by entering valid "<emailaddress>"
    Then I select mail opt in checkbox click other
    Then I verify by entering valid data in the address field "<address1>" at Correspondence
    Then I verify by entering valid data in the address field 2 "<address2>" at Correspondence
    Then I verify by entering valid data in the City field "<CITY>" at Correspondence
    Then I verify by entering valid data in the state field "<State>" at Correspondence
    Then I verify by entering valid data in the zip code "<zipcode>" at Correspondence
    Then I select Text opt in check box click other
    Then I enter valid phoneNumber "<phoneNumber>"
    Then I verify Successfully saved OutboundCorrespondence
    Examples:
      | address1        | address2 | CITY   | State    | phoneNumber | emailaddress          | zipcode | faxNumber  |
      | 123 main street | 302      | Reston | Virginia | 7038555030  | sthorson120@gmail.com | 20190   | 4154380923 |



