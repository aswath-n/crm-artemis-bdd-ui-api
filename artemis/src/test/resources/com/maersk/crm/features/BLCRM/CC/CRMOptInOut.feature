Feature: Capturing Opt-in/out From Create Consumer UI

  @CP-272 @CP-272-01 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Opt-in Warning message if the contact information is not available to contact them via that channel
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    When I select one of the "<Opt-in>" options on Create Consumer Profile Page
    And I provide one piece of demographic info not related to this "<Opt-in>" channel
    Then I see a Warning message for "<Inactive Channel>" on Create Consumer Profile Page
    Examples:
      | Opt-in | Inactive Channel  |
      | Text   | Cell Phone Number |
      | Fax    | Fax Phone Number  |
      | Email  | Email Address     |
      | Phone  | Phone Number      |
      | Mail   | Mailing Address   |


  @CP-272 @CP-272-02 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Opt-in Warning message is not displayed when Active Contact channel is available
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    When I select one of the "<Opt-in>" options on Create Consumer Profile Page
    And I provide a valid data for this "<Active Channel>" on Create Consumer Profile Page
    Then I do not see any Warning message on Create Consumer Profile Page
    Examples:
      | Opt-in | Active Channel    |
      | Text   | Cell Phone Number |
      | Fax    | Fax Phone Number  |
      | Email  | Email Address     |
      | Phone  | Phone Number      |
      | Mail   | Mailing Address   |


  @CP-272 @CP-272-03 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Opt-in Warning Message if Continue is selected
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    When I select one of the "<Opt-in>" options on Create Consumer Profile Page
    And I provide one piece of demographic info not related to this "<Opt-in>" channel
    And I see a Warning message for "<Inactive Channel>" on Create Consumer Profile Page
    And I select Continue Button on Opt-in warning message
    Then I see new consumer is created and has a unique Consumer ID
    Examples:
      | Opt-in | Inactive Channel  |
      | Text   | Cell Phone Number |
      | Fax    | Fax Phone Number  |
      | Email  | Email Address     |
      | Phone  | Phone Number      |
      | Mail   | Mailing Address   |

  @CP-272 @CP-272-04 @muhabbat @crm-regression @ui-cc
  Scenario Outline: Opt-in Warning Message if Cancel is selected
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    When I select one of the "<Opt-in>" options on Create Consumer Profile Page
    And I provide one piece of demographic info not related to this "<Opt-in>" channel
    And I see a Warning message for "<Inactive Channel>" on Create Consumer Profile Page
    And I select Cancel Button on Opt-in warning message
    Then I see all required fields are present on on Create Consumer Profile Page
    Examples:
      | Opt-in | Inactive Channel  |
      | Text   | Cell Phone Number |
      | Fax    | Fax Phone Number  |
      | Email  | Email Address     |
      | Phone  | Phone Number      |
      | Mail   | Mailing Address   |


#@CP-272  @CP-272-05 @muhabbat #will be implemented as new user story excluded from CP-272
  Scenario Outline:  Warning message if the contact information is not available to contact them via that channel (inside the context of the Case) within the System
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    When I select one of the "<Opt-in>" options on Create Consumer Profile Page
    Then I see a Warning message for "<Inactive Channel>" on Create Consumer Profile Page
    Examples:
      | Opt-in | Inactive Channel            |
      | Text   | Cell Phone Number           |
      | Fax    | Fax Phone Number            |
      | Email  | Email Address               |
      | Phone  | Work/Cell/Home Phone Number |
      | Mail   | Mailing Address             |

  #@CP-272 @CP-272-06 @muhabbat #will be implemented as new user story excluded from CP-272
  Scenario Outline:  Warning message is not displayed when Active Contact channel is available (inside the context of the Case) within the System
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    And I add mandatory DOB field value on Create consumer profile UI Page
    When I select one of the "<Opt-in>" options on Create Consumer Profile Page
    And I provide a valid data for this "<Active Channel>" on Create Consumer Profile Page
    Then I do not see any Warning message on Create Consumer Profile Page
    Examples:
      | Opt-in | Active Channel              |
      | Text   | Cell Phone Number           |
      | Fax    | Fax Phone Number            |
      | Email  | Email Address               |
      | Phone  | Work/Cell/Home Phone Number |
      | Mail   | Mailing Address             |

  @CP-9251 @CP-9251-01 @chopa @crm-regression @ui-cc
  Scenario: Opt-in Check Information Display Order - Create Consumer UI
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    Then I see OptIn options displayed in the following order

  @CP-9251 @CP-9251-02 @chopa @crm-regression @ui-cc
  Scenario: Opt-in Check Information Display Order - PI UI
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "PI"
    Then I see OptIn options displayed in the following order

  @CP-9251 @CP-9251-03 @chopa @crm-regression @ui-cc
  Scenario: Opt-in Check Information Display Order - CM UI
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "CM"
    Then I see OptIn options displayed in the following order

  @CP-9251 @CP-9251-04 @chopa @crm-regression @ui-cc
  Scenario: Opt-in Check Information Display Order - AR UI
    Given I logged into CRM and click on initiate contact
    When I searched customer have First Name as "Brat" and Last Name as "Smith"
    And I link the contact to an existing Case or Consumer Profile
    And I click on demographic tab and click on add for "AR"
    Then I see OptIn options displayed in the following order