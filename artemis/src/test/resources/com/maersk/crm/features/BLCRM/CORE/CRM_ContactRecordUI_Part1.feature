Feature: Contact Record UI Part 1

  @CRM-229 @CRM-229-01 @muhabbat @ui-core @crm-regression @ui-core-smoke
  Scenario: Auto captured information on Contact Record UI Page
    Given I logged into CRM and click on initiate contact
    When I verify Contact Start Date is captured
    And I verify Contact Start Time is captured
#    And I verify Contact Duration has no value while in progress
    Then I verify Contact Duration Time is captured

  @CRM-229 @CRM-229-02 @muhabbat @ui-core @crm-regression @ui-core-smoke
  Scenario: Verification mandatory fields on Contact Record UI
    Given I logged into CRM and click on initiate contact
    When I verify all mandatory fields on Contact record UI Page are labeled and displayed
    And I click on Close Button on Contact record UI Page
    Then I see fill out mandatory fields warning on Contact record UI Page


  @CRM-229 @CRM-229-03 @muhabbat @ui-core @crm-regression
  Scenario: Verification optional fields on Contact Record UI
    Given I logged into CRM and click on initiate contact
    And I enter value into mandatory First and Last name fields on Contact record UI Page
    And I click on Search Button on Search Consumer Page
    Then I verify all non-mandatory fields are not labeled

  @CRM-229 @CRM-229-04 @muhabbat @ui-core @crm-regression @crm-smoke
  Scenario: Existing Case/Consumer linking
    Given I logged into CRM and click on initiate contact
    And I search for an existing contact by criteria
    Then I link the contact to an existing Case or Consumer Profile

  @CRM-229 @CRM-229-05 @muhabbat @ui-core @crm-regression @crm-smoke
  Scenario: New Case/Consumer linking
    Given I logged into CRM and click on initiate contact
    When I enter Search criteria fields for a new consumer
    And I click on Search Button on Search Consumer Page
    And I am able to see Add Consumer button and I click on it to navigate to Create Consumer UI
    When I populate Create Consumer fields for a new consumer
    When I click on Create Consumer Button on Create Consumer Page
    And I am navigated to active contact page
    Then I see the Contact Record linked to the new Case/Consumer Profile

  @CRM-229  @CRM-229-06 @muhabbat @ui-core @crm-regression
  Scenario: View Case/Consumer Unique ID After Linking
    Given I logged into CRM and click on initiate contact
    And I search for an existing contact by first name and SSN
    Then I link the contact to an existing Case or Consumer Profile
    Then I can view the Contact Record's Case/Consumer Unique IDs

  @CRM-229 @CRM-229-07 @muhabbat @ui-core @crm-regression
  Scenario: Verification of Consumer Name fields accept up to 15 characters of alphabet only
    Given I logged into CRM and click on initiate contact
    And I see "firstName" field accept only letters
      | 3!@#$%H%^&*()_+=e[{]}]\|;:'"r<>?,./mione |
    Then I see "firstName" field accept only 50 characters
    And I see "lastName" field accept only letters
      | 3!@#$%He%^&*()_+=e[{]}]\|;:'"r<>?,./mione |
    Then I see "lastName" field accept only 50 characters

  #This scenario is not valid since Release 10.0. Consumer Type field is moved to General Contact section
  # @CRM-229 @CRM-229-08
  Scenario: Verification of values in Consumer Type field
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "consumer type" field displayed
      | Authorized representative |
      | Member                    |
      | Non-Member                |
      | Primary Individual        |
      | Unverified Contact        |


  @CRM-229 @CRM-229-09 @muhabbat @ui-core @crm-regression
  Scenario: Verification of values in Contact Type field
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact type" field displayed
      | Inbound  |
      | Outbound |

  @CRM-229 @CRM-229-10 @CP-15080 @aikanysh @muhabbat @ui-core @crm-regression
  Scenario: Verification of values in Contact Channel field
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact channel" field displayed
      | Email    |
      | Phone    |
      | SMS Text |
      | Web Chat |
      | Web Portal   |
      | Mobile App |
      | IVR Self-Service |



  #This scenario is not valid since Release 10.0. Preferred Language field is moved to General Contact section
  # @CRM-229 @CRM-229-11
  Scenario: Verification of values in Preferred Language field
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "preferred language" field displayed
      | English |
      | Spanish |

  @CRM-229  @CRM-229-12 @muhabbat @ui-core @crm-regression
  Scenario: Verification of values in Reason section
    Given I logged into CRM and click on initiate contact
    When  I should see following options for "reason" section displayed

  @CRM-229 @CRM-229-12-02 @muhabbat @ui-core @crm-regression
  Scenario: Verification of values in Contact Reason field
    Given I logged into CRM and click on initiate contact
    And I click on "Inbound" type of call option in "Contact Type" dropdown
    When  I should see following dropdown options for "contact reason" field displayed
      | Other                      |
      | Information Request        |
      | Materials Request          |
      | Update Information Request |

  @CRM-229 @CRM-229-14 @muhabbat @ui-core @crm-regression
  Scenario: Checking Contact Action values when Contact Reason is Request for Information
    Given I logged into CRM and click on initiate contact
    When I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    Then I should see following dropdown options for "contact action" field displayed
      | Provided Case Status/Information        |
      | Provided Eligibility Status/Information |
      | Provided Enrollment Status/Information  |
      | Provided Financial Information          |

  @CRM-229 @CRM-229-15 @muhabbat @ui-core @crm-regression
  Scenario: Contact Action values when Contact Reason is Request to Update Information
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Update Information Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Updated Demographic Information |
      | Updated Eligibility Information |
      | Updated Enrollment Information  |


  @CRM-229 @CRM-229-16 @muhabbat @ui-core @crm-regression
  Scenario: Contact Action values when Contact Reason is Request Materials
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Materials Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Sent Program Materials |
      | Re-Sent Notice         |

  @CRM-229 @CRM-229-16-2 @muhabbat @vinuta @ui-core @CRM-653 @CRM-653-06 @crm-regression
  Scenario: Contact Action values when Contact Reason is Missing Information Request
    Given I logged into CRM and click on initiate contact
    And I click on "Outbound" type of call option in "Contact Type" dropdown
    When  I should see following dropdown options for "contact reason" field displayed
      | Missing Information Request |
    Then  I should see following dropdown options for "contact action" field displayed
      | Requested and Updated Authorized Representative Information |
      | Requested and Updated Case Member Information               |
      | Requested and Updated Demographic Information               |

  @CRM-229 @CRM-229-17 @muhabbat @ui-core @crm-regression
  Scenario: Verification of text filed when Contact Reason is Other
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Other |
    And I should see Contact Action Dropdown disabled
    Then I see "otherReason" field accept only 50 characters

  @CRM-229 @CRM-229-18 @muhabbat  @ui-core @crm-regression @crm-smoke
  Scenario: Reset Button Functionality
    Given I logged into CRM and click on initiate contact
    When I populate Search criteria fields for a new consumer
    And I click on reset button on Contact Record UI
    Then I see Search criteria fields have no value

#  @CRM-229 @CRM-229-19 @muhabbat @crm-regression # not applicable anymore
#  Scenario: Cancel Button Functionality
#    Given I logged into CRM and click on initiate contact
#    When I enter Consumer details "Ethan" on contact record UI
#    And I click on cancel button and see a message
#    #And I confirm cancellation on the message
#    Then I should be navigated to initial landing page

  @CRM-229  @CRM-229-20 @muhabbat @ui-core @crm-regression
  Scenario: Close Button Functionality
    Given I logged into CRM and click on initiate contact
    When  I should see following dropdown options for "contact reason" field displayed
      | Information Request |
    And  I choose "Provided Financial Information" option for Contact Action field
    And I search for an existing contact by first name and SSN
    When I link the contact to an existing Case or Consumer Profile
    And I close active contact after selecting all mandatory fields
    Then I see initiate a contact button on CRM Dashboard








