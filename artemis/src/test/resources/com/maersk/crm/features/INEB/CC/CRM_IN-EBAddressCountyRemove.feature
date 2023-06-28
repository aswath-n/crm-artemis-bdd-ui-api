Feature: Remove County from Address Display in Auth Grid - General/Third Party

  @CP-33554-01 @aswath
  Scenario: Login to Artemis
    Given I logged into application


  @CP-33554 @CP-33554-02 @muhabbat @crm-regression @ui-cc-in
  Scenario: IN-EB: Suppress County on Auth Grid Address Display - Third Party Contact
    Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
    And I minimize Genesys popup if populates
    And I click on initiate a contact button
    And I click on third party contact record type radio button
    When I enter Search criteria fields for a new consumer from Third  with firstName "Larouge" and lastName "Larouge"
    And I click on Search Button on Search Consumer Page
    And I expend the Case Consumer this contact relates to in search result
    Then I validate the address on authentication grid displayed without County "Mailing Address - 999 Main Ave Albion, AZ 22222 - 2222"

 # @CP-34131 @CP-34131-01 @muhabbat @crm-regression @ui-cc-in
 # Scenario: IN-EB: Suppress County on Auth Grid Address Display - Third Party Contact
   # Given I logged into CRM with "Service Tester 2" and select a project "IN-EB"
  #  And I navigate to Manual Consumer search page
   # And I searched customer have First Name as "Larouge" and Last Name as "Larouge"
   # And I expend the Case/Consumer this contact relates to in search result
   # Then I validate the address on authentication grid displayed without County "Mailing Address - 999 Main Ave Albion, AZ 22222 - 2222"

  #muted due to duplicate

