Feature: Contact Record UI Part 5

@CRM-1045-02-c @CRM-1045 @vinuta @ui-core @crm-regression
Scenario: Updates to outcome of contact values when contact disposition is Dropped
Given I logged into CRM and click on initiate contact
When I click on "Outbound" type of call option in "Contact Type" dropdown
And I should see following dropdown options for "contact disposition" field displayed
| Dropped |
Then I should see following dropdown options for "outcome of contact" field displayed
| Did Not Reach/Left Voicemail |
| Did Not Reach/No Voicemail   |
| Invalid Phone Number         |
| Reached Successfully         |

@CRM-1045-02-d @CRM-1045 @vinuta @ui-core @crm-regression
Scenario: Updates to outcome of contact values when contact disposition is Escalate
Given I logged into CRM and click on initiate contact
When I click on "Outbound" type of call option in "Contact Type" dropdown
And I should see following dropdown options for "contact disposition" field displayed
| Escalate |
Then I should see following dropdown options for "outcome of contact" field displayed
| Did Not Reach/Left Voicemail |
| Did Not Reach/No Voicemail   |
| Invalid Phone Number         |
| Reached Successfully         |

@CRM-1045-02-e @CRM-1045 @vinuta @ui-core @crm-regression
Scenario: Updates to outcome of contact values when contact disposition is Requested Call Back
Given I logged into CRM and click on initiate contact
When I click on "Outbound" type of call option in "Contact Type" dropdown
And I should see following dropdown options for "contact disposition" field displayed
| Requested Call Back |
Then I should see following dropdown options for "outcome of contact" field displayed
| Did Not Reach/Left Voicemail |
| Did Not Reach/No Voicemail   |
| Invalid Phone Number         |
| Reached Successfully         |

@CRM-1045-02-f @CRM-1045 @vinuta @ui-core @crm-regression
Scenario: Updates to outcome of contact values when contact disposition is Transfer
Given I logged into CRM and click on initiate contact
When I click on "Outbound" type of call option in "Contact Type" dropdown
And I should see following dropdown options for "contact disposition" field displayed
| Transfer |
Then I should see following dropdown options for "outcome of contact" field displayed
| Did Not Reach/Left Voicemail |
| Did Not Reach/No Voicemail   |
| Invalid Phone Number         |
| Reached Successfully         |

@CRM-1045-03-a @CRM-1045 @vinuta @ui-core @crm-regression
Scenario: Updates to contact disposition values when outcome of contact is Reached Successfully
Given I logged into CRM and click on initiate contact
When I click on "Outbound" type of call option in "Contact Type" dropdown
And I should see following dropdown options for "outcome of contact" field displayed
| Reached Successfully |
Then I should see following dropdown options for "contact disposition" field displayed
| Complete            |
| Dropped             |
| Escalate            |
| Requested Call Back |
| Transfer            |

@CRM-1045-03-b @CRM-1045 @vinuta @ui-core @crm-regression
Scenario: Updates to contact disposition values when outcome of contact is Did Not Reach/Left Voicemail
Given I logged into CRM and click on initiate contact
When I click on "Outbound" type of call option in "Contact Type" dropdown
And I should see following dropdown options for "outcome of contact" field displayed
| Did Not Reach/Left Voicemail |
Then I should see following dropdown options for "contact disposition" field displayed
| Outbound Incomplete |

@CRM-1045-03-b @CRM-1045 @vinuta @ui-core @crm-regression
Scenario: Updates to contact disposition values when outcome of contact is Did Not Reach/No Voicemail
Given I logged into CRM and click on initiate contact
When I click on "Outbound" type of call option in "Contact Type" dropdown
And I should see following dropdown options for "outcome of contact" field displayed
| Did Not Reach/No Voicemail |
Then I should see following dropdown options for "contact disposition" field displayed
| Outbound Incomplete |

@CRM-1045-03-c @CRM-1045 @vinuta @ui-core @crm-regression
Scenario: Updates to contact disposition values when outcome of contact is Did Not Reach/No Voicemail
Given I logged into CRM and click on initiate contact
When I click on "Outbound" type of call option in "Contact Type" dropdown
And I should see following dropdown options for "outcome of contact" field displayed
| Invalid Phone Number |
Then I should see following dropdown options for "contact disposition" field displayed
| Outbound Incomplete |