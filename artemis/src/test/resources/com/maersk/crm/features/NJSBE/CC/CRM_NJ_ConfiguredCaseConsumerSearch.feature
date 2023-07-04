Feature:Hide Case/Consumer search fields on Contact Record & Manual case/Consumer Search screens & Relax validations

  @CP-11276 @CP-11276-01 @ui-cc-nj @nj-regression @muhabbat
  Scenario: NJ-SBE: Active Contact case consumer search - verification Email, MI, ConsumerId and ConsumerIdType are not displayed
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    And I click on advance search icon
    Then I don't see unexpected "fields" on NJ-SBE search consumer component
      | Email          |
      | MI             |
      | ConsumerID     |
      | ConsumerIDType |

  @CP-11276 @CP-11276-02 @ui-cc-nj @nj-regression @muhabbat
  Scenario: NJ-SBE:  Manual case consumer search - verification Email, MI, ConsumerId and ConsumerIdType are not displayed
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to Manual Consumer search page
    And I click on advance search icon
    Then I don't see unexpected "fields" on NJ-SBE search consumer component
      | Email          |
      | MI             |
      | ConsumerID     |
      | ConsumerIDType |

  @CP-11276 @CP-11276-03 @ui-cc-nj @nj-regression @muhabbat
  Scenario: NJ-SBE: Active Contact case consumer - validate all expected fields
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    Then I see expected "fields" on NJ-SBE search consumer component

  @CP-11276 @CP-11276-04 @ui-cc-nj @nj-regression @muhabbat
  Scenario: NJ-SBE: Manual case consumer search - validate all expected fields
    Given I logged into CRM and select a project "NJ-SBE"
    When I navigate to Manual Consumer search page
    Then I see expected "fields" on NJ-SBE search consumer component

  @CP-11276 @CP-11276-05 @ui-cc-nj @nj-regression @muhabbat
  Scenario: NJ-SBE: Active Contact case consumer Advanced seach - validate all expected fields
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    And I click on advance search icon
    Then I see expected "advance fields" on NJ-SBE search consumer component

  @CP-11276 @CP-11276-06 @ui-cc-nj @nj-regression @muhabbat
  Scenario: NJ-SBE: Manual case consumer Advanced search - validate all expected fields
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to Manual Consumer search page
    And I click on advance search icon
    Then I see expected "advanced fields" on NJ-SBE search consumer component


  @CP-11276 @CP-11276-07 @ui-cc-nj @nj-regression @muhabbat
  Scenario: NJ-SBE:  Manual case consumer search - verification advanced search demographic field are not displayed
    Given I logged into CRM and select a project "NJ-SBE"
    And I minimize Genesys popup if populates
    When I navigate to Manual Consumer search page
    And I click on advance search icon
    Then I don't see unexpected "advance fields" on NJ-SBE search consumer component
      | State    |
      | City     |
      | Address1 |
      | Address2 |
      | County   |
      | Zip      |

  @CP-11276 @CP-11276-07 @ui-cc-nj @nj-regression @muhabbat
  Scenario: NJ-SBE: Active Contact case consumer search - verification advanced search demographic field are not displayed
    Given I logged into CRM and select a project "NJ-SBE"
    When I click on initiate contact record
    And I click on advance search icon
    Then I don't see unexpected "advance fields" on NJ-SBE search consumer component
      | State    |
      | City     |
      | Address1 |
      | Address2 |
      | County   |
      | Zip      |

