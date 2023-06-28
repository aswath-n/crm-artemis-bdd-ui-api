@API-Contacts
Feature: API: Contact Controller

  @contacts-api-CC @API-CC @API-CRM-SmokeTest @API-CRM-Regression @hereabc
  Scenario: Get Autentication token for entire feature
    When I will get the Authentication token for "BLCRM" in "CRM"

  @contacts-api-CC @API-CRM-763 @API-CC @API-CRM-Regression @Sujoy @hereabc
  Scenario Outline: Add new Email using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated consumer search API for Contacts
    When I get uiid and correlationId id from contacts search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated add new Contact using API
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    Then I run get contacts detail using API
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | associatedCaseMember | emailAddress       | startDate | endDate | projectName |
      | Lola              | Zcxc             | Consumer        | Dfgdfg Dfgdfg        | muhabbat@blcrm.com |           |         |             |

  @contacts-api-CC @API-CRM-763 @CRM-763-02 @API-CC @API-CRM-Regression  @Shruti @1012
  Scenario Outline:Add new random email to a random consumer
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I run get contacts detail using API
    Examples:
      | consumerFirstName | externalRefType | associatedCaseMember | emailAddress | startDate | endDate | projectName |
      |                   | Consumer        |                      | {random}     |           |         |             |

  @contacts-api-CC @API-CRM-763 @CRM-763-03 @API-CC @API-CRM-Regression @Shruti @1012
  Scenario Outline:Validate status as of email address with different start and end dates
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I verify "<success>" details using API
    Examples:
      | consumerFirstName | externalRefType | associatedCaseMember | emailAddress | addressZip | phoneNumber | startDate | endDate  | success | projectName |
      | {random}          | Consumer        |                      | {random}     | {random}   | {random}    | today     | tomorrow | True    |             |
      | {random}          | Consumer        |                      | {random}     | {random}   | {random}    | yesterday | tomorrow | True    |             |

  @contacts-api-CC @API-CRM-763 @CRM-763-06 @API-CC @API-CRM-Regression @Shruti @1015
  Scenario Outline:Verify Audit Trail for newly added email to a consumer
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I verify Email address Audit Trail
    Examples:
      | consumerFirstName | externalRefType | associatedCaseMember | emailAddress | addressZip | phoneNumber | startDate | endDate  | success | projectName |
      | {random}          | Consumer        |                      | {random}     | {random}   | {random}    | today     | tomorrow | True    |             |


#    # -- The following test case is temporary disabled until API logics allows for validation --
#  @API-CRM-760 @API-CRM @API-CRM-Regression @API-Contacts @Sujoy
#  Scenario Outline: Add new Phone blank field using API
#    Given I initiated consumer search API for Contacts
#    When I get uiid and correlationId id from contacts search "<consumerFirstName>" and "<consumerLastName>"
#    And I initiated add new Contact using API
#    And I added blank phone information
#    And I can run add contacts using API
#    And I initiated specific contacts detail "<externalRefType>"
#    And I run get contacts detail using API
#    Then I verify "<success>" details using API
#    Examples:
#      | consumerFirstName | consumerLastName | externalRefType  |  success |
#      |    Graham          | Gooch             |  Consumer        |  False   |

  @API-CRM-761 @API-CRM @API-CC @API-CRM-Regression @Sujoy
  Scenario Outline: View phone number(s) as consumer Contact using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated consumer search API for Contacts
    When I get uiid and correlationId id from contacts search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated specific contacts detail "<externalRefType>"
    And I run get contacts detail using API
    Then I can verify consumer phone detail "<success>" and count greater than zero using API
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | phoneNumber | phoneType | associatedCaseMember | comments       | startDate | endDate  | success | updatedComments     | updatedPhoneType | projectName |
      | Lola              | Zcxc             | Consumer        |             | Cell      |                      | Test Comments1 | today     | tomorrow | True    | updated Case member | Work             |             |


  @contacts-api-CC @API-CRM-757-02 @API-CC @API-CRM-Regression  @muhabbat
  Scenario Outline: Add new Address using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated consumer search API for Contacts
    When I get uiid and correlationId id from contacts search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated add new Contact using API
    And I added address information "<externalRefType>","<address1>","<address2>","<city>","<county>","<state>","<zip>","<zipFour>","<type>","<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    When I will get the Authentication token for "<projectName>" in "CRM"
    When I run get contacts detail using API
    Then I verify "<success>" details using API
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | address1 | address2 | city          | county      | state | zip      | zipFour  | type    | startDate | endDate  | success | projectName |
      | Lola              | Zcxc             | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | today     | tomorrow | True    |             |
      | Lola              | Zcxc             | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | yesterday | tomorrow | True    |             |
#      | Eric              | Consumer        | {random} || SomeTest City | Test County | Georgia | {random} | {random} | Mailing  | tomorrow  | yesterday | False   |
#      | Eric              | Consumer        ||          | SomeTest City | Test County | Georgia | {random} || Physical | today     | tomorrow  | False   |
#      | Eric              | Consumer        | {random} || 111111111     | Test County | Georgia | {random} | {random} | Mailing  | today     | tomorrow  | False   |
#      | Eric              | Consumer        | {random} || SomeTest City | 22222222    | Georgia | {random} || Physical | tomorrow  | today     | False   |
#      | Eric              | Consumer        | {random} || SomeTest City | Test County | 33333   | {random} | {random} | Mailing  | today     | tomorrow  | False   |
#      | Eric              | Consumer        | {random} || SomeTest City | Test County | Georgia | SomeZIp  || Physical | today     | tomorrow  | False   |


  @contacts-api-CC @API-CRM-757-07 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline:Validate status as of Address with different start and end dates
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    And I added address information "<externalRefType>","<address1>","<address2>","<city>","<county>","<state>","<zip>","<zipFour>","<type>","<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I verify "<success>" details using API
    Examples:
      | consumerFirstName | externalRefType | address1 | address2 | city          | county      | state | zip      | zipFour  | type    | startDate | endDate  | success | projectname |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | today     | tomorrow | True    |             |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | yesterday | tomorrow | True    |             |
#      | {random}          | Consumer        | {random} || SomeTest City | Test County | Georgia | {random} | {random} | Mailing | tomorrow  | yesterday | False   |


  @contacts-api-CC @API-CRM-757-09 @API-CC @API-CRM-Regression @muhabbat
  Scenario Outline:Verify Audit Trail for newly added address to a consumer
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I added address information "<externalRefType>","<address1>","<address2>","<city>","<county>","<state>","<zip>","<zipFour>","<type>","<startDate>" and "<endDate>"
    And I can run add contacts using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    And I initiated specific contacts detail "<externalRefType>"
    Then I verify Address Audit Trail
    Examples:
      | consumerFirstName | externalRefType | address1 | address2 | city          | county      | state | zip      | zipFour  | type    | startDate | endDate  | success | projectName |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | today     | tomorrow | True    |             |

  @contacts-api-CC @API-CRM-758 @API-CC @API-CRM-Regression @Sujoy
  Scenario Outline: View Consumer Address list using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated consumer search API for Contacts
    When I get uiid and correlationId id from contacts search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated Consumer Type Search vai API with "<externalRefType>" type and id "<externalRefid>"
    And I run get contacts detail using API
    Then I verify "<success>" details using API
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | externalRefid | success | projectName |
      | Lola              | Zcxc             | Consumer        |               | True    |             |

  @contacts-api-CC @API-CRM-759 @API-CC @API-CRM-Regression @Sujoy
  Scenario Outline: Update Existing Address using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated consumer search API for Contacts
    When I get uiid and correlationId id from contacts search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated Consumer Type Search vai API with "<externalRefType>" type and id "<externalRefid>"
    And I run get contacts detail using API
    And I get latest address information of a the consumer
    And I initiated add new Contact using API
    And I updated existing address information "<address1>","<address2>","<city>","<county>","<state>","<zip>","<zipFour>","<type>","<startDate>" and "<endDate>"
    And I can run add contacts using API
    Then I can verify Address Updates
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | externalRefid | address1 | address2 | city     | county   | state    | zip      | zipFour  | type | startDate | endDate  | success | projectname |
      | Lola              | Zcxc             | Consumer        |               | {random} | {random} | {random} | {random} | {random} | {random} | {random} |      | today     | tomorrow | True    |             |

  @contacts-api-CC @API-CRM-764 @CRM-764-01 @API-CC @API-CRM-Regression @Shruti
  Scenario Outline:Verify Active emails are displayed first followed by inactive emails
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress1>", "<start date1>" and "<end date1>"
    And I can run add contacts using API
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress2>", "<start date2>" and "<end date2>"
    And I can run add contacts using API
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress3>", "<start date3>" and "<end date3>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    Then I verify active emails fetched first followed by inactive emails

    Examples:
      | consumerFirstName | consumerLastName | externalRefType | associatedCaseMember | emailAddress1 | addressZip1 | phoneNumber1 | start date1 | end date1 | emailAddress2 | addressZip2 | phoneNumber2 | start date2 | end date2 | emailAddress3 | addressZip3 | phoneNumber3 | start date3 | end date3 | projectName |
      | {random}          | {random}         | Consumer        |                      | {random}      | {random}    | {random}     | past        | past      | {random}      | {random}    | {random}     | past        | future    | {random}      | {random}    | {random}     | past        | future    |             |

  @contacts-api-CC @API-CRM-765 @API-CRM-765-01 @API-CC @API-CRM-Regression @Shruti @1029
  Scenario Outline:Validate update to an existing email address
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress>", "<start date>" and "<end date>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    Then I run get contacts detail using API
    When I initiated add new Contact using API
    And I updated email information "<newEmailAddress>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    Then I verify updated Email address detail using API "<newEmailAddress>"

    Examples:
      | consumerFirstName | consumerLastName | externalRefType | associatedCaseMember | emailAddress | addressZip | phoneNumber | start date | end date | status | newEmailAddress        | projectName |
      | {random}          | {random}         | Consumer        |                      | {random}     | {random}   | {random}    | today      | future   | active | updatedEmail@gmail.com |             |

  @contacts-api-CC @API-CRM-765 @CRM-765-02 @API-CC @API-CRM-Regression @Shruti
  Scenario Outline:Verify Audit Trail for Updated Email
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress1>", "<start date1>" and "<end date1>"
    And I can run add contacts using API
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress2>", "<start date2>" and "<end date2>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    Then I verify Updated on field is populated
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | associatedCaseMember | emailAddress1 | addressZip1 | phoneNumber1 | start date1 | end date1 | emailAddress2 | addressZip2 | phoneNumber2 | start date2 | end date2 | projectName |
      | {random}          | {random}         | Consumer        |                      | {random}      | {random}    | {random}     | past        | future    | {random}      | {random}    | {random}     | today       | future    |             |

# The request structure has changed and th
  #@API-CRM-762 @API-CRM @API-CRM-Regression @API-Contacts @Sujoy
  #Scenario Outline: Update Existing Phone Number using API
   # Given I initiated consumer search API for Contacts
   # When I get uiid and correlationId id from contacts search "<consumerFirstName>" and "<consumerLastName>"
   # And I initiated Consumer Type Search vai API with "<externalRefType>" type and id "<externalRefid>"
   # And I run get contacts detail using API
   # And I get latest phone information of a the consumer
   # And I initiated add new Contact using API
    #And I updated existing phone information "<phoneNumber>","<phoneType1>","<phoneType2>","<comments>","<startDate>" and "<endDate>"
    #And I can run add contacts using API
   #Then I can verify Address Updates
    #Examples:
     # | consumerFirstName | consumerLastName | externalRefType |  externalRefid | phoneNumber | phoneType1 | phoneType2| comments      | startDate | endDate  | success |
      #|    Graham         | Gooch            | Consumer        || {random}    | {random}   | {random}  | Test Comments ||          | True    |

  @contacts-api-CC @API-CRM-1047 @API-CRM-1047-01 @API-CC @API-CRM-Regression  @Shilpa
  Scenario Outline: Add new Phone using API for a case member using API
    #Given I initiated Create case member API for contacts
      # When I can provide case member information for contact
      #| consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip | consumerRole |
      #| Consumer     | {random}          | {random}         | today-9000          | {random}    | Male               | today-50           | 45    | Child        | Member       |
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Case for Contacts Validations
    When I run Case Loader API for Create New Case and Customer with below detail for Contacts Validations:
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip | consumerRole |
      | Consumer     | {random}          | {random}         | 1999-02-11          | {random}    | Male       | today-50           | 45     | Child        | Member       |
    #Given I initiated get case member API for contact "45"
    Given I initiated get case member API for contact "3349" in ContactController
    And I initiated add new Contact using API
    And I added phone information "<externalRefType>", "<phoneNumber>", "<phoneType>", "<associatedCaseMember>", "<comments>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    And I run get contacts detail using API
    Then I verify "<success>" details using API
    And I verify added phone details are matched
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | phoneNumber | phoneType | associatedCaseMember | comments       | startDate | endDate  | success | projectName |
      | Lola              | Zcxc             | Case            |             | Home      |                      | Test Comments1 | today     | tomorrow | True    |             |


  @contacts-api-CC @API-CRM-1047 @API-CRM-1047-02 @API-CC @API-CRM-Regression @API-Contacts @Shruti
  Scenario Outline: Verify update associated Case member to an existing phone number using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Case for Contacts Validations
    When I run Case Loader API for Create New Case and Customer with below detail for Contacts Validations:
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip | consumerRole |
      | Consumer     | {random}          | {random}         | 1999-02-11          | {random}    | Male       | today-50           |        | Child        | Member       |
    Then I will create a new case for case loader case creation for Contacts Validations
    #Given I initiated get case member API for contact ""
    Given I initiated get case member API for contact "" in ContactController
    And I initiated add new Contact using API
    And I added phone information "<externalRefType>", "<phoneNumber>", "<phoneType>", "<associatedCaseMember>", "<comments>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    And I run get contacts detail using API
    Then I verify "<success>" details using API
    And I verify added phone details are matched
    Given I initiated Create case member API for contacts
    When I can provide case member information for contact
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | consumerGenderCode | effectiveStartDate | caseId | relationShip | consumerRole |
      | Consumer     | {random}          | {random}         | today-9000          | {random}    | Male               | today-50           |        | Child        | Member       |
    Given I initiated get case member API for contact ""
    And I initiated add new Contact using API
    And I updated phone information "<externalRefType>", "<phoneNumber>", "<updatedPhoneType>", "<associatedCaseMember>" and "<updatedComments>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    And I run get contacts detail using API
    And I verify updated phone details are matched

    Examples:
      | consumerFirstName | consumerLastName | externalRefType | phoneNumber | phoneType | associatedCaseMember | comments       | startDate | endDate  | success | updatedComments     | projectName |
      | Lola              | Zcxc             | Case            |             | Cell      | Home                 | Test Comments1 | today     | tomorrow | True    | updated Case member |             |

  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @caseConsumerAPI @contacts-api-CC @API-CRM-762 @API-CRM-1047 @API-CRM @API-CRM-Regression @API-Contacts @Shruti
  Scenario Outline: Verify User is allowed to update existing Phone number Information- Start date, End Date, Phone Type & Phone number
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Case for Contacts Validations
    When I run Case Loader API for Create New Case and Customer with below detail for Contacts Validations:
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip | consumerRole |
      | Consumer     | {random}          | {random}         | 1999-02-11          | {random}    | Male       | today-50           |        | Child        | Member       |
    Then I will create a new case for case loader case creation for Contacts Validations
    Given I initiated get case member API for contact ""
    And I initiated add new Contact using API
    And I added phone information "<externalRefType>", "<phoneNumber>", "<phoneType>", "<associatedCaseMember>", "<comments>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    And I run get contacts detail using API
    And I verify added phone details are matched
    And I initiated add new Contact using API
    And I updated phone information "<externalRefType>", "<phoneNumber>", "<updatedPhoneType>", "<associatedCaseMember>" and "<updatedComments>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    And I run get contacts detail using API
    And I verify updated phone details are matched "<updatedComments>" and "<updatedPhoneType>"

    Examples:
      | consumerFirstName | consumerLastName | externalRefType | phoneNumber | phoneType | associatedCaseMember | comments       | startDate | endDate  | success | updatedComments     | updatedPhoneType | projectName |
      | Lola              | Zcxc             | Case            |             | Cell      |                      | Test Comments1 | today     | tomorrow | True    | updated Case member | Work             |             |


  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @caseConsumerAPI @contacts-api-CC @API-CRM-946 @API-CRM-946-01 @API-CRM @API-CRM-Regression @API-Contacts @Shruti
  Scenario Outline: Verify Error is displayed when same phone type is added for Case using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Case for Contacts Validations
    When I run Case Loader API for Create New Case and Customer with below detail for Contacts Validations:
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip | consumerRole |
      | Consumer     | {random}          | {random}         | 1999-02-11          | {random}    | Male       | today-50           |        | Child        | Member       |
    Then I will create a new case for case loader case creation for Contacts Validations
    Given I initiated get case member API for contact ""
    And I initiated add new Contact using API
    And I added phone information "<externalRefType>", "<phoneNumber>", "<phoneType>", "<associatedCaseMember>", "<comments>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    And I run get contacts detail using API
    Then I verify "<success>" details using API
    And I verify added phone details are matched
    When I initiated add new Contact using API
    And I added phone information "<externalRefType>", "<phoneNumber>", "<phoneType>", "<associatedCaseMember>", "<comments>", "<startDate>" and "<endDate>"
    Then I can run add contacts using API and verify error message "Update Cannot be Processed, There is an existing active Phone Number with the selected Type for the selected Consumer"
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | phoneNumber | phoneType | associatedCaseMember | comments       | startDate | endDate  | success | updatedComments     | projectName |
      |                   |                  | Case            |             | Cell      |                      | Test Comments1 | today     | tomorrow | True    | updated Case member |             |


  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @caseConsumerAPI @contacts-api-CC @API-CRM-946 @API-CRM-946-02 @API-CRM @API-CRM-Regression @API-Contacts @Shruti
  Scenario Outline: Verify Error is displayed when same phone type is added for Consumer using API
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    And I added phone information "<externalRefType>", "<phoneNumber>", "<phoneType>", "<associatedCaseMember>", "<comments>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    And I run get contacts detail using API
    Then I verify "<success>" details using API
    And I verify added phone details are matched
    When I initiated add new Contact using API
    And I added phone information "<externalRefType>", "<phoneNumber>", "<phoneType>", "<associatedCaseMember>", "<comments>", "<startDate>" and "<endDate>"
    Then I can run add contacts using API and verify error message "Update Cannot be Processed, There is an existing active Phone Number with the selected Type for the selected Consumer"


    Examples:
      | consumerFirstName | consumerLastName | externalRefType | phoneNumber | phoneType | associatedCaseMember | comments       | startDate | endDate  | success | updatedComments     |
      | Joan              | Riley            | Consumer        |             | Cell      |                      | Test Comments1 | today     | tomorrow | True    | updated Case member |


  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @caseConsumerAPI @contacts-api-CC @API-CRM-1330 @CRM-1330-01 @API-CRM @API-CRM-Regression @API-Contacts @Sujoy
  Scenario Outline:Validate status as of email address with different start and end dates
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    And I added email information "<externalRefType>", "<associatedCaseMember>", "<emailAddress>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    Then I verify Email address status "<Status>" using API

    Examples:
      | consumerFirstName | externalRefType | associatedCaseMember | emailAddress          | startDate | endDate   | Status   | projectName |
      | {random}          | Consumer        |                      | testFuture@test.com   | tomorrow  |           | Future   |             |
      | {random}          | Consumer        |                      | testInactive@test.com | yesterday | yesterday | Inactive |             |
      | {random}          | Consumer        |                      | testInactive@test.com | yesterday |           | Active   |             |
      | {random}          | Consumer        |                      | testInactive@test.com | yesterday | today     | Active   |             |
      | {random}          | Consumer        |                      | testInactive@test.com | today     | today     | Active   |             |
      | {random}          | Consumer        |                      | testInactive@test.com | today     | tomorrow  | Active   |             |


  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @caseConsumerAPI @contacts-api-CC @API-CRM-1331 @API-CRM-1331-01 @API-CRM @API-CRM-Regression @API-Contacts @Shruti
  Scenario Outline: Validate status as "Future" for Phone Number with different future start date -Case
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated Case Loader API for Create New Case for Contacts Validations
    When I run Case Loader API for Create New Case and Customer with below detail for Contacts Validations:
      | consumerType | consumerFirstName | consumerLastName | consumerDateOfBirth | consumerSSN | GenderCode | effectiveStartDate | caseId | relationShip | consumerRole |
      | Consumer     | {random}          | {random}         | 1999-02-11          | {random}    | Male       | today-50           |        | Child        | Member       |
    Then I will create a new case for case loader case creation for Contacts Validations
    Given I initiated get case member API for contact ""
    And I initiated add new Contact using API
    And I added phone information "<externalRefType>", "<phoneNumber>", "<phoneType>", "<associatedCaseMember>", "<comments>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    And I run get contacts detail using API
    Then I verify "<success>" details using API
    And I verify phone status as "<phoneStatus>"

    Examples:
      | consumerFirstName | consumerLastName | externalRefType | phoneNumber | phoneType | associatedCaseMember | comments       | startDate | endDate | success | updatedComments     | phoneStatus | projectName |
      |                   |                  | Case            |             | Cell      |                      | Test Comments1 | tomorrow  |         | True    | updated Case member | Future      |             |

  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @caseConsumerAPI @contacts-api-CC @API-CRM-1331 @API-CRM-1331-02 @API-CRM @API-CRM-Regression @API-Contacts @Shruti
  Scenario Outline: Validate status as "Future" for Phone Number with different future start date -Consumer
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    And I added phone information "<externalRefType>", "<phoneNumber>", "<phoneType>", "<associatedCaseMember>", "<comments>", "<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    And I run get contacts detail using API
    Then I verify "<success>" details using API
    And I verify phone status as "<phoneStatus>"
    Examples:
      | externalRefType | phoneNumber | phoneType | associatedCaseMember | comments       | startDate | endDate | success | phoneStatus | projectName |
      | Consumer        |             | Cell      |                      | Test Comments1 | tomorrow  |         | True    | Future      |             |


  @auxiliaryService @search-services-AS @event-api-AS @link-library-AS @lookup-api-AS @caseConsumerAPI @contacts-api-CC @API-CRM-1328 @API-CRM-1328-01 @API-CRM @API-CRM-Regression @API-Contacts @Sujoy
  Scenario Outline:Validate status as of address with different start and end dates
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    And I added address information "<externalRefType>","<address1>","<address2>","<city>","<county>","<state>","<zip>","<zipFour>","<type>","<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    Then I verify Address status "<Status>" using API

    Examples:
      | consumerFirstName | externalRefType | address1 | address2 | city          | county      | state | zip      | zipFour  | type    | startDate | endDate   | Status   | projectName |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | tomorrow  | tomorrow  | Future   |             |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | today     | tomorrow  | Active   |             |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | yesterday | yesterday | Inactive |             |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | yesterday |           | Active   |             |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | yesterday | today     | Active   |             |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | today     | today     | Active   |             |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | today     | tomorrow  | Active   |             |

  @API-CRM-455 @API-CRM-455-01 @sanglee @API-CRM-Regression @API-CC
  Scenario Outline: Update Existing Address using API and verify with get call
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I initiated consumer search API for Contacts
    When I get uiid and correlationId id from contacts search "<consumerFirstName>" and "<consumerLastName>"
    And I initiated Consumer Type Search vai API with "<externalRefType>" type and id "<externalRefid>"
    And I run get contacts detail using API
    And I get latest address information of a the consumer
    And I initiated add new Contact using API
    And I updated existing address information "<address1>","<address2>","<city>","<county>","<state>","<zip>","<zipFour>","<type>","<startDate>" and "<endDate>"
    And I can run add contacts using API
    Then I can verify updated address by getting the address from consumer ID "<externalRefid>"
    Examples:
      | consumerFirstName | consumerLastName | externalRefType | externalRefid | address1 | address2 | city     | county   | state    | zip      | zipFour  | type | startDate | endDate  |
      | muhabbat          | Consent          | Consumer        |               | {random} | {random} | {random} | {random} | {random} | {random} | {random} |      | today     | tomorrow |

  @api-smoke-devops
  Scenario Outline: Validate status as of Address with (for DevOps smoke)
    When I will get the Authentication token for "<projectName>" in "CRM"
    Given I created a consumer for contact using API
    And I collected some consumer related data from consumer Controller
    And I initiated add new Contact using API
    And I added address information "<externalRefType>","<address1>","<address2>","<city>","<county>","<state>","<zip>","<zipFour>","<type>","<startDate>" and "<endDate>"
    And I can run add contacts using API
    And I initiated specific contacts detail "<externalRefType>"
    When I will get the Authentication token for "<projectName>" in "CRM"
    Then I verify "<success>" details using API
    Examples:
      | consumerFirstName | externalRefType | address1 | address2 | city          | county      | state | zip      | zipFour  | type    | startDate | endDate  | success | projectname |
      | {random}          | Consumer        | {random} |          | SomeTest City | Test County | GA    | {random} | {random} | Mailing | today     | tomorrow | True    |             |
