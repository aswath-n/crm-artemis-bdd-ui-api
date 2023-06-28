@link_MS
Feature: API-LINK-Service: Link service in CRM

@API-CP-136 @API-CP-136-01 @API-AUX @API-PP-Regression @aswath @link-library-AS
Scenario Outline: Verify Contact record linked to the Consumer
  When I will get the Authentication token for "<projectName>" in "CRM"
Given I created a consumer to link contact record
#When I will get the Authentication token for "<projectName>" in "CRM"
And I initiated Create Contact Records API
And I can provide advanced contact details information:
| contactRecordType     | random   |
| contactType           | General  |
| preferredLanguageCode | English  |
| inboundCallQueue      | random   |
| contactChannelType    | Phone    |
| linkRefType           | consumer |
| consumerType          | consumer |
Then I can run create contact records API
And I initiated Link API
Then I provide details link the "<linkTo>" "<InternalRefType>" to "<ExternalRefType>"
Then I can run link API
Then I can verify that link the "<InternalRefType>" to "<ExternalRefType>"
Examples:
| projectName |InternalRefType|ExternalRefType|linkTo   |
|[blank]|contact_record |consumer       |Cont2Cons|
|[blank]|consumer       |contact_record |Cons2Cont|

@contact-record-api-CRMC @API-CP-136-02 @API-AUX @API-CORE @API-PP-Regression @aswath @API-CRM-SmokeTest @link-library-AS @api-smoke-devops
Scenario Outline: Verify Contactoact record linked to the Consumer
When I will get the Authentication token for "<projectName>" in "CRM"
Given I created a case consumer to link contact record
And I initiated Link API
Then I provide details link the "<linkTo>" "<InternalRefType>" to "<ExternalRefType>"
Then I can run link API
Then I can verify that link the "<InternalRefType>" to "<ExternalRefType>"
Examples:
| projectName |InternalRefType|ExternalRefType|linkTo  |
|[blank]|contact_record |case           |Cont2Case|
|[blank]|case           |contact_record |Case2Cont|

@API-CP-136 @API-CP-136-03 @API-PP-Regression @aswath @API-AUX @link-library-AS
Scenario Outline: Verify Contactoact record linked to the Consumer
When I will get the Authentication token for "<projectName>" in "CRM"
Given I created a case consumer to link contact record
And I created a Task to link case
And I initiated Link API
Then I provide details link the "<linkTo>" "<InternalRefType>" to "<ExternalRefType>"
Then I can run link API
Then I can verify that link the "<InternalRefType>" to "<ExternalRefType>"
Examples:
| projectName |InternalRefType|ExternalRefType|linkTo   |
|[blank]|task           |case           |Task2Case|
|[blank]|case           |task           |Case2Task|

@API-CP-136 @API-CP-136-04 @API-AUX @API-PP-Regression @aswath @link-library-AS
Scenario Outline: Verify Contactoact record linked to the Consumer
When I will get the Authentication token for "<projectName>" in "CRM"
Given I created a consumer to link contact record
And I created a Task to link case
And I initiated Link API
Then I provide details link the "<linkTo>" "<InternalRefType>" to "<ExternalRefType>"
Then I can run link API
Then I can verify that link the "<InternalRefType>" to "<ExternalRefType>"
Examples:
| projectName |InternalRefType|ExternalRefType|linkTo   |
|[blank]|task           |consumer       |Task2Cons|
|[blank]|consumer       |task           |Cons2Task|

@API-CP-136 @API-CP-136-05 @API-PP-Regression @API-AUX @aswath @link-library-AS
Scenario Outline: Verify Contact record linked to the Consumer
Given I will get the Authentication token for "<projectName>" in "CRM"
And I initiated Create Contact Records API
And I can provide advanced contact details information:
| contactRecordType     | random   |
| contactType           | General  |
| preferredLanguageCode | English  |
| inboundCallQueue      | random   |
| contactChannelType    | Phone    |
| linkRefType           | consumer |
| consumerType          | consumer |
Then I can run create contact records API
And I created a Task to link case
And I initiated Link API
Then I provide details link the "<linkTo>" "<InternalRefType>" to "<ExternalRefType>"
Then I can run link API
Then I can verify that link the "<InternalRefType>" to "<ExternalRefType>"
Examples:
| projectName |InternalRefType|ExternalRefType|linkTo   |
|[blank]|contact_record |task           |Cont2Task|
|[blank]|task           |contact_record |Task2Cont|

@API-CP-136 @API-CP-136-06 @API-AUX @API-PP-Regression @aswath @API-CRM-SmokeTest @link-library-AS
Scenario Outline: Verify Contact record linked to the Consumer
Given I will get the Authentication token for "<projectName>" in "CRM"
And I initiated Create Contact Records API
And I can provide advanced contact details information:
| contactRecordType     | random   |
| contactType           | General  |
| preferredLanguageCode | English  |
| inboundCallQueue      | random   |
| contactChannelType    | Phone    |
| linkRefType           | consumer |
| consumerType          | consumer |
Then I can run create contact records API
And I created a Task to link case
And I initiated Link API
Then I provide details link the "<linkTo>" "<InternalRefType>" to "<ExternalRefType>"
Then I can run link API
Then I can verify that link the "<InternalRefType>" to "<ExternalRefType>"
And I initate linked contact record API
And I can run linked contact record API
Then I verify the linked contact record API
Examples:
| projectName |InternalRefType|ExternalRefType|linkTo   |
|[blank]|contact_record |task           |Cont2Task|


