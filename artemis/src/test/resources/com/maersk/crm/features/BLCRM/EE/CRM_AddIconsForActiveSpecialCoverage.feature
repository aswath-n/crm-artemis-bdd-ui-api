@CP-7962
Feature: Verify Add Icons for Special Coverage
# removed regression tag as these scenarios need to add API steps for test data creation
    @CP-7962 @CP-7962-01 @ui-ee-removed @crm-regression @Olga
  Scenario:Verify green outlined white box around each Special Coverage type is displayed
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Ann" and Last Name as "Evans"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I verify if the consumer "has" Special Coverage segment to be displayed
    Then I verify green outlined white box around each Special Coverage type is "displayed"

     @CP-7962 @CP-7962-02 @ui-ee-removed @crm-regression @Olga
    Scenario: Verify green outlined white box around each Special Coverage type not displayed
      Given I logged into CRM and click on initiate contact
      When I searched customer have First Name as "FnLKPro" and Last Name as ""
      And I link the contact to an existing Case or Consumer Profile
      When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
      And I verify if the consumer "doesn't have" Special Coverage segment to be displayed
      Then I verify green outlined white box around each Special Coverage type is "not displayed"


      @CP-7962 @CP-7962-03 @ui-ee-removed @crm-regression @Olga
    Scenario: Verify green outlined white box around each Special Coverage type is still displayed after I collapse the consumer
      Given I logged into CRM and click on initiate contact
      When I searched customer have First Name as "Ann" and Last Name as "Evans"
      And I link the contact to an existing Case or Consumer Profile
      When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
      And I verify if the consumer "has" Special Coverage segment to be displayed
      Then I verify green outlined white box around each Special Coverage type is "displayed"
      And I collapse and re-expand the "special coverage" section
      Then I verify green outlined white box around each Special Coverage type is "displayed"
      And I collapse and re-expand the "consumer" section
      Then I verify green outlined white box around each Special Coverage type is "displayed"

     @CP-7962 @CP-7962-04 @ui-ee-removed @crm-regression @Olga
    Scenario: Verify add Icons Special coverage are desplayied if the Special coverage fields have data
      Given I logged into CRM and click on initiate contact
      When I searched customer have First Name as "Ann" and Last Name as "Evans"
      And I link the contact to an existing Case or Consumer Profile
      When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
      And I verify if the consumer "has" Special Coverage segment to be displayed
      And I view a special coverage section
      And I verify just the special coverage segment is shown which has data


       @CP-4778 @CP-4778-01 @ui-ee @crm-regression @Olga
     Scenario: Verification Special Coverage Section is displayed for all consumers
       Given I logged into CRM and click on initiate contact
       When I searched customer have First Name as "Ann" and Last Name as "Evans"
       And I link the contact to an existing Case or Consumer Profile
       When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
       Then I verify "Special Coverage Section" is displayed for all cunsumers on Program & Benefit Info Page

       @CP-4778 @CP-4778-02 @ui-ee @crm-regression @Olga
    Scenario: Verification Special Coverage Section is collapsed by default
      Given I logged into CRM and click on initiate contact
      When I searched customer have First Name as "Ann" and Last Name as "Evans"
      And I link the contact to an existing Case or Consumer Profile
      When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
      Then I verify "Special Coverage Section" is displayed for all cunsumers on Program & Benefit Info Page
      And I verify "Special Coverage Section" is collapsed by default

      @CP-4778 @CP-4778-03 @ui-ee @crm-regression @Olga
    Scenario: Verify Carrot to Collapse/Expand Current Special Coverage Section
      Given I logged into CRM and click on initiate contact
      When I searched customer have First Name as "Ann" and Last Name as "Evans"
      And I link the contact to an existing Case or Consumer Profile
      When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
      Then I verify "Special Coverage Carrot symbol" is displayed for all cunsumers on Program & Benefit Info Page

     @CP-4778 @CP-4778-04 @ui-ee @crm-regression @Olga
    Scenario:Verify -Expanded - Data Elements - Special Coverage Section-Waiver Segment
      Given I logged into CRM and click on initiate contact
      When I searched customer have First Name as "Ann" and Last Name as "Evans"
      And I link the contact to an existing Case or Consumer Profile
      When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
      And I expand "special coverage" section for every consumer
     Then I verify bellow fields are displayed on "Special Coverage Waiver" section:
           |CODE      |
           |COUNTY    |
           |START DATE|
           |END DATE  |

     @CP-4778 @CP-4778-05 @ui-ee @crm-regression @Olga
    Scenario:  Verify -Expanded - Data Elements - Special Coverage Section-3rd party Segment
      Given I logged into CRM and click on initiate contact
      When I searched customer have First Name as "Ann" and Last Name as "Evans"
      And I link the contact to an existing Case or Consumer Profile
      When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
      And I expand "special coverage" section for every consumer
      Then I verify bellow fields are displayed on "Special Coverage Other Insurance" section:
        |SOURCE      |
        |CODE        |
        |START DATE  |
        |END DATE    |
        |GROUP NUMBER|
        |NAME        |

   @CP-4778 @CP-4778-06 @ui-ee @crm-regression @Olga
  Scenario: Verify -Expanded - Data Elements - Special Coverage Section-Medicare Segment
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Ann" and Last Name as "Evans"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I expand "special coverage" section for every consumer
    Then I verify bellow fields are displayed on "Special Coverage Medicare" section:
          |PART A START DATE|
          |PART A END DATE  |
          |PART B START DATE|
          |PART B END DATE  |
          |PART D START DATE|
          |PART D END DATE  |

  @CP-4778 @CP-4778-07 #@ui-ee @crm-regression @Olga
  Scenario:  Verify -Expanded - Data Elements - Special Coverage Section-LTC
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Ann" and Last Name as "Evans"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I expand "special coverage" section for every consumer
    Then I verify bellow fields are displayed on "Special Coverage LTC" section:
      |COVERAGE CODE  |
      |START DATE     |
      |END DATE       |
      |PROVIDER NAME  |
      |PROVIDER ID/NPI|

   @CP-4778 @CP-4778-08 #@ui-ee @crm-regression @Olga
   Scenario: Verify -Expanded - Data Elements - Special Coverage Section-Hospice
     Given I logged into CRM and click on initiate contact
     When I searched customer have First Name as "Ann" and Last Name as "Evans"
     And I link the contact to an existing Case or Consumer Profile
     When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
     And I expand "special coverage" section for every consumer
     Then I verify bellow fields are displayed on "Special Coverage Hospice" section:
       |INDICATOR |
       |START DATE|
       |END DATE  |

   @CP-4778 @CP-4778-09 #@ui-ee @crm-regression @Olga
   Scenario:  Verify -Expanded - Data Elements - Special Coverage Section-Facility/Placement
     Given I logged into CRM and click on initiate contact
     When I searched customer have First Name as "Ann" and Last Name as "Evans"
     And I link the contact to an existing Case or Consumer Profile
     When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
     And I expand "special coverage" section for every consumer
     Then I verify bellow fields are displayed on "Special Coverage Facility/Placement" section:
       |CODE       |
       |COUNTY CODE|
       |START DATE |
       |END DATE   |


  @CP-4778 @CP-4778-10 #@ui-ee @crm-regression @Olga
  Scenario: Verify Special Coverage Section Sort Order
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Ann" and Last Name as "Evans"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    Then I verify "Order of Special Coverage" is displayed for all cunsumers on Program & Benefit Info Page

  @CP-4778 @CP-4778-11 #@ui-ee @crm-regression @Olga
  Scenario: Verify Special Coverage Section Labels - Current Special Coverage if start date is past and end date is more or equal to current date
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Ann" and Last Name as "Evans"
    And I link the contact to an existing Case or Consumer Profile
    When I click on "Program & Benefit Info" Tab on Contact Dashboard Page
    And I expand "special coverage" section for every consumer
    Then I verify the combination of Start Date and End Date is correct






