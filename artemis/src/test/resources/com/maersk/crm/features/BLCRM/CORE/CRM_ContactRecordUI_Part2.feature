Feature: Contact Record UI Part 2


    #By Vinuta, Validate Error Message when no search criteria given
  @CRM-575-01 @CRM-575 @vinuta @ui-core @crm-regression
  Scenario: Validate Error Message when no search criteria given
    Given I logged into CRM and click on initiate contact
    When I do not enter any search parameters
    Then I see notification to fill search criteria

  #By Vinuta, Validate field level errors
  @CRM-575-02 @CRM-575 @vinuta @ui-core @crm-regression
  Scenario Outline: Validate search section for field-level validations on ssn field
    Given I logged into CRM and click on initiate contact
    When I enter First Name as <firstName>, Middle Initial as <middleName>, Last Name as <lastName>, SSN as <ssn>, Date Of Birth as <DOB>, Unique ID as <ID>  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I see SSN field validation displayed
    Examples:
      | firstName | middleName | lastName | ssn   | DOB | ID |
      | ''        | ''         | ''       | '123' | ''  | '' |

  #By Vinuta, Validate field level errors
  @CRM-575-03 @CRM-575 @vinuta @ui-core @crm-regression
  Scenario Outline: Validate search section for field-level validations on DB field
    Given I logged into CRM and click on initiate contact
    When I enter First Name as <firstName>, Middle Initial as <middleName>, Last Name as <lastName>, SSN as <ssn>, Date Of Birth as <DOB>, Unique ID as <ID>  on Contact Record
    And I click on Search Button on Search Consumer Page
    Then I see DOB field validation displayed
    Examples:
      | firstName | middleName | lastName | ssn | DOB  | ID |
      | ''        | ''         | ''       | ''  | '13' | '' |

  #By Vinuta, Validate errors for reason & comments section
  @CRM-575-04 @CRM-575 @vinuta @ui-core @crm-regression
  Scenario: Validate Contact Reason,Action, Comments error message
    Given I logged into CRM and click on initiate contact
#    When I click on Reasons dropdown
    And I click on the save button
#    And I click on the Additional Comments
    And I should be able to save the additional comments
    Then I should receive error to fill out required fields on reasons and comments

  @CRM-653-01-a @CRM-653 @CRM-1045-01-a @CRM-1045 @vinuta  @ui-core @crm-regression
  Scenario: Validate Call Campaign Reference values for Outbound Contact Type
    Given I logged into CRM and click on initiate contact
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then I should see following dropdown options for "call campaign reference" field displayed
      | Enrollment Reminder |
      | Payment Reminder    |
      | Program Information |

  @CRM-653-01-b @CRM-653 @CRM-1045-01-b @CRM-1045 @vinuta @ui-core @crm-regression
  Scenario: Validate Outcome of Contact values for Outbound Contact Type
    Given I logged into CRM and click on initiate contact
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then I should see following dropdown options for "outcome of contact" field displayed
      | Did Not Reach/Left Voicemail |
      | Did Not Reach/No Voicemail   |
      | Invalid Phone Number         |
      | Reached Successfully         |

  @CRM-653-02 @CRM-653 @vinuta @ui-core @crm-regression
  Scenario: Validate Outcome of Contact values for Outbound Contact Type
    Given I logged into CRM and click on initiate contact
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then I should see following dropdown options for "contact disposition" field displayed
      | Complete            |
      | Dropped             |
      | Escalate            |
      | Outbound Incomplete |
      | Requested Call Back |
      | Transfer            |

  # Feel this is redundant need to check with Vinuta if it can be deleted
  # @CRM-653-05 @CRM-653 @vinuta
  Scenario: Validate Contact Reason values for Outbound Contact Type
    Given I logged into CRM and click on initiate contact
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    Then I should see following dropdown options for "contact reason" field displayed
      | Information Request         |
      | Materials Request           |
      | Missing Information Request |
      | Other                       |
      | Update Information Request  |

  @CRM-653-03 @CRM-653 @CRM-1045-02-a @CRM-1045 @vinuta @ui-core @crm-regression
  Scenario: Updates to outcome of contact values when contact disposition is Complete
    Given I logged into CRM and click on initiate contact
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    And I should see following dropdown options for "contact disposition" field displayed
      | Complete |
    Then I should see following dropdown options for "outcome of contact" field displayed
      | Reached Successfully |

  @CRM-653-04 @CRM-653 @CRM-1045-02-b @CRM-1045 @vinuta @ui-core @crm-regression
  Scenario: Updates to outcome of contact values when contact disposition is Outbound Incomplete
    Given I logged into CRM and click on initiate contact
    When I click on "Outbound" type of call option in "Contact Type" dropdown
    And I should see following dropdown options for "contact disposition" field displayed
      | Outbound Incomplete |
    Then I should see following dropdown options for "outcome of contact" field displayed
      | Did Not Reach/Left Voicemail |
      | Did Not Reach/No Voicemail   |
      | Invalid Phone Number         |

