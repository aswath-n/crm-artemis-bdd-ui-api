Feature: CreateTaskFields Validation for CoverVA

  @CP-19291 @CP-19291-01 @CP-34498 @CP-34498-14 @CP-38195 @CP-38195-01-10 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Verification all fields and mandatory fields on  DMAS to CVIU CC Escalation create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to CVIU CC Escalation" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DUE DATE "
    And I verify "actionTakenSingle" single select drop down value
      | Disputed RICKI (maersk)             |
      | Escalated to CoverVA (DMAS)          |
      | Request Complete (maersk)           |
      | Request in Progress (maersk)        |
      | Returning - Sent in Error (maersk)  |
      | Unable to Complete Request (maersk) |
    Then I verify "contactReason" single select drop down value
      | Consumer Complaint     |
      | Customer Service Issue |
      | HIPPA                  |
      | Other                  |
      | PINK                   |
      | SLA Not Met            |
      | Worker Error           |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | MMIS Member ID       |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    And Close the soft assertions

  @CP-19295 @CP-19295-01 @CP-34498 @CP-34498-13 @CP-38195 @CP-38195-01-09 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Verification all fields and mandatory fields on DMAS To CVIU Eligibility Escalation create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to CVIU Eligibility Escalation" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DUE DATE "
    And I verify "actionTakenSingle" single select drop down value
      | Disputed RICKI (maersk)             |
      | Escalated to CoverVA (DMAS)          |
      | Request Complete (maersk)           |
      | Request in Progress (maersk)        |
      | Returning - Sent in Error (maersk)  |
      | Unable to Complete Request (maersk) |
    Then I verify "contactReason" single select drop down value
      | Consumer Complaint     |
      | Customer Service Issue |
      | HIPPA                  |
      | Other                  |
      | PINK                   |
      | SLA Not Met            |
      | Worker Error           |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | MMIS Member ID       |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    And Close the soft assertions

  @CP-19158 @CP-19158-01 @CP-34498 @CP-34498-15 @CP-38195 @CP-38195-01-11 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Verification all fields and mandatory fields on DMAS to QA Escalation create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I navigate to "DMAS to QA Escalation" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status | Complete |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DUE DATE "
    And I verify "actionTakenSingle" single select drop down value
      | Disputed RICKI (maersk)             |
      | Escalated to CoverVA (DMAS)          |
      | Request Complete (maersk)           |
      | Request in Progress (maersk)        |
      | Returning - Sent in Error (maersk)  |
      | Unable to Complete Request (maersk) |
    Then I verify "contactReason" single select drop down value
      | HIPPA        |
      | Other        |
      | SLA Not Met  |
      | Worker Error |
    Then I verify text box Date and Time field value and error message for following fields
      | VaCMS Case ID        |
      | MMIS Member ID       |
      | Application Id       |
      | CP contact record Id |
      | CP SR Id             |
      | CP Task Id           |
      | Due Date             |
    And Close the soft assertions

  @CP-19144 @CP-19144-01 @CP-34498 @CP-34498-18 @CP-38195 @CP-38195-01-13 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Verification all fields and mandatory fields on DMAS to Mailroom Escalation create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "DMAS to Mailroom Escalation" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DUE DATE "
    And I verify "actionTakenSingle" single select drop down value
      | Escalated to CoverVA (DMAS)|
      | Request Complete (maersk)|
      | Request in Progress (maersk)|
      | Returning - Sent in Error (maersk)|
      | Unable to Complete Request (maersk)|
    Then I verify "contactReason" single select drop down value
      | HIPPA|
      | Late Mailing|
      | Other|
      | SLA Not Met|
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |MMIS Member ID|
      |Application Id|
      |CP contact record Id|
      |CP SR Id|
      |CP Task Id|
      |Due Date|
    And Close the soft assertions

  @CP-19280 @CP-19280-01 @CP-37688 @CP-37688-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification all fields and mandatory fields on Newborn Inquiries create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Newborn Inquiries" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status |Complete|
    And I click on save button on task edit page
    And I verify "ASSIGNEE BUSINESS UNIT" filed is not mandatory for the task
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify "actionTaken" multi select drop down value
      | Escalated to DMAS|
      | Other|
      | Reclassified Document Type|
      | Uploaded to DMIS|
    Then I verify "contactReason" single select drop down value
      | Eligibility Status|
      | Enrollment Status|
      | Other|
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |MMIS Member ID|
    And I select "Newborn Inquiries" option in task type drop down
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And Get the task type information of "Newborn Inquiries" for project ""
    Given I initiated active business units and teams api
    When I provide taskTypeId for active business units and teams "singleVal"
    And I can run active bu and teams api
    Then I get business unit names from business units and teams response api
    Then I verify Assignee Business Unit dropdown displays only BU that are associated to the task type
    And Close the soft assertions

  @CP-18989 @CP-18989-01 @CP-34498 @CP-34498-08 @CP-38195 @CP-38195-01-06 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Verification all fields and mandatory fields on CVIU Eligibility to DMAS Escalation create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "CVIU Eligibility to DMAS Escalation" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify task mandatory fields error message "APPLICATION ID "
    And I verify task mandatory fields error message "DUE DATE "
    And I verify "actionTakenSingle" single select drop down value
      | Escalated to DMAS (maersk)|
      | Request Complete (DMAS)|
      | Request in Progress (DMAS)|
      | Returning - Sent in Error (DMAS)|
      | Unable to Complete Request (DMAS)|
    Then I verify "contactReason" single select drop down value
      | Coverage Correction (DMAS) |
      | Coverage Correction (HPE)|
      | ECPR Review|
      | Emergency Services|
      | Medicare Review|
      | Other|
      | SAVE/SVES Request|
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |MMIS Member ID|
      |Application Id|
      |CP contact record Id|
      |CP SR Id|
      |CP Task Id|
      |Due Date|
    And Close the soft assertions

  @CP-18023 @CP-18023-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification all fields and mandatory fields on HPE Inquiries create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "HPE Inquiries" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ACTION TAKEN "
    And I verify task mandatory fields error message "CONTACT REASON"
    And I verify "actionTaken" multi select drop down value
      | Escalated to DMAS|
      | Other|
      | Reclassified Document Type|
      | Uploaded to DMIS|
    Then I verify "contactReason" single select drop down value
      | Eligibility Status|
      | Enrollment Status|
      | Other|
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |MMIS Member ID|
    And Close the soft assertions

  @CP-19193 @CP-19193-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verification all fields and mandatory fields on Review Appeal Notification create task page
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Review Appeal Notification" task page
    And I will check action taken is not mandatory when task status is not complete
    And I will provide following information before creating task
      | status |Complete|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "RECEIVED DATE "
    And I verify task mandatory fields error message "DISPOSITION"
    Then I verify "disposition" single select drop down value
      | Appeal Request|
      | Appeal Withdrawal|
      | Fair Hearing Decision|
      |Other                 |
      |Pre-Hearing Conference|
    Then I verify text box Date and Time field value and error message for following fields
      |VaCMS Case ID|
      |Received Date|
    And Close the soft assertions

  @CP-22664 @CP-22664-01 @CP-18879 @CP-18879-02 @CP-19570 @CP-19570-01 @CP-25708-03 @CP-18083 @CP-18083-01 @CP-39041-01 @CP-43009-03 @CP-39671-03 @CP-47703 @CP-47703-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan @priyal
  Scenario: Verify required/optional fields for Application SR in Cover-VA
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    When I navigate to "Application SR" service request page
    And I will provide following information before creating task
      | missingInfoRequired | Yes |
    And I click on save button on task edit page
    And I verify "MI RECEIVED DATE" field is optional
    And I will provide following information before creating task
      | missingInfoRequired | No |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "APPLICATION TYPE "
    Then I verify task mandatory fields error message "APPLICATION ID "
    Then I verify task mandatory fields error message "CHANNEL "
    And I scroll up the page
    And I verify "applicationType" single select drop down value
      | Emergency Medicaid Services Application - CVIU |
      | Expedited Application - CVIU                   |
      | MAGI - CPU                                     |
      | MAGI - PW                                      |
      | MAGI Standard Application - CVIU               |
      | Pre-Release Application - CVIU                 |
      | Re-Entry Application - CVIU                    |
    And I verify "channel" single select drop down value
      | CommonHelp |
      | FFM-D      |
      | FFM-R      |
      | FFM-R&D    |
      | LDSS       |
      | Paper      |
      | RDE        |
    Then I verify "Decision Source" field is disable and cleared out
    And I verify "missingInfoRequired" single select drop down value
      | No  |
      | Yes |
    And I verify "applicationStatus" single select drop down value
      | Appeal                                       |
      | Approved                                     |
      | Assigned to ARC (Advanced Resolution Center) |
      | Auto-Approved (Self Direct)                  |
      | Auto-Denied                                  |
      | Coverage Correction Completed                |
      | Coverage Correction Needed                   |
      | Data Collection                              |
      | DC Ready                                     |
      | Denied                                       |
      | Documents Processed                          |
      | Documents Received                           |
      | ECPR Tool                                    |
      | Extend and Pend Letter                       |
      | File Clearance                               |
      | Inbound Call                                 |
      | Manual Enrollment Tool                       |
      | Medicare Review Completed                    |
      | Medicare Review Needed                       |
      | Outbound Call                                |
      | Pending Direction                            |
      | Pre-Hearing Conference                       |
      | Reassigned to CPU                            |
      | Research                                     |
      | Returned Mail                                |
      | Sent to LDSS in Error                        |
      | System Issue                                 |
      | Timestamp                                    |
      | VCL (Verification Checklist)                 |
    Then I verify "Locality" field is disable and cleared out
    Then I verify "Document Type" field is disable and cleared out
    Then I verify "Transfer Reason" field is disable and cleared out
    Then I verify "VCL Sent Date" field is disable and cleared out
    And I verify "facilityType" single select drop down value
      | Department of Corrections      |
      | Department of Juvenile Justice |
      | Local Jail                     |
      | Regional Jail                  |
    And I verify "facilityName" single select drop down value
      | Accomack County Jail                             |
      | Albemarle/Charlottesville Regional Jail          |
      | Albermarle County Jail                           |
      | Alexandria City Jail                             |
      | Alexandria Detention Center                      |
      | Alleghany County Jail                            |
      | Alleghany/Covington Regional Jail                |
      | Amelia County Jail                               |
      | Amherst County Jail                              |
      | Appalachian Detention Center                     |
      | Appomattox County Jail                           |
      | Arlington County Jail                            |
      | Augusta Correctional Center                      |
      | Augusta County Jail                              |
      | Baskerville Correctional Center                  |
      | Beaumont Correctional Center                     |
      | Bedford County Jail                              |
      | Bland Correctional Center                        |
      | Blue Ridge Regional Jail (BRRJ) - Amherst        |
      | Blue Ridge Regional Jail (BRRJ) - Bedford        |
      | Blue Ridge Regional Jail (BRRJ) - Campbell       |
      | Blue Ridge Regional Jail (BRRJ) - Halifax        |
      | Blue Ridge Regional Jail (BRRJ) - Lynchburg      |
      | Botetourt County Jail                            |
      | Bristol City Jail                                |
      | Brunswick County Jail                            |
      | Brunswick Work Center                            |
      | Buckingham Correctional Center                   |
      | Campbell County Detention Center                 |
      | Caroline Correctional Unit                       |
      | Caroline Correctional Unit #2                    |
      | Caroline Detention Facility                      |
      | Carroll County Jail                              |
      | Central VA Correctional Unit #13                 |
      | Central Virginia Regional Jail                   |
      | Charles City County Jail                         |
      | Charlotte County Jail                            |
      | Charlottesville City Jail                        |
      | Chesapeake City Jail                             |
      | Chesterfield County Jail                         |
      | Chesterfield Women’s Diversion Center            |
      | Clarke County Jail                               |
      | Clifton Forge Sheriff’s Office                   |
      | Coffeewood Correctional Center                   |
      | Cold Springs Correctional Unit 10                |
      | Cold Springs Detention Center                    |
      | Colonial Heights Sheriff’s Office                |
      | Covington City Sheriff’s Office                  |
      | Craig County Sheriff’s Office                    |
      | Culpeper County Jail                             |
      | Cumberland County Jail                           |
      | Danville City Jail                               |
      | Deep Meadow Correctional Center                  |
      | Deerfield Correctional Center                    |
      | Department of Corrections (DOC)                  |
      | Department of Juvenile Justice                   |
      | Dickinson County Jail                            |
      | Dillwyn Correctional Center                      |
      | Dinwiddie County Jail                            |
      | Eastern Shore Regional Jail (Northampton County) |
      | Emporia City Jail                                |
      | Essex County VA Jail                             |
      | Fairfax County Jail                              |
      | Falls Church City Jail                           |
      | Fauquier County Sheriff's Office                 |
      | Floyd County Jail                                |
      | Fluvanna Correctional Center for Women           |
      | Fluvanna County Jail                             |
      | Franklin City Police Station                     |
      | Franklin County Jail                             |
      | Frederick County Jail                            |
      | Fredericksburg City Jail                         |
      | Galax City Jail                                  |
      | Gloucester County Jail                           |
      | Goochland County Jail                            |
      | Grayson County Jail                              |
      | Green Rock Correctional Center                   |
      | Greene County Jail                               |
      | Greensville Correctional Center                  |
      | Greensville County Jail                          |
      | Halifax Correctional Unit                        |
      | Halifax County Jail                              |
      | Hampton City Jail                                |
      | Hampton Roads Regional Jail                      |
      | Hanover County Jail                              |
      | Harrisonburg Diversion Center                    |
      | Haynesville Correctional Center                  |
      | Haysi Regional Jail                              |
      | Henrico County Regional Jail (East)              |
      | Henrico County Regional Jail (West)              |
      | Henry County Jail                                |
      | Highland County Jail                             |
      | Hopewell City Jail                               |
      | Indian Creek Correctional Center                 |
      | James City County Jail                           |
      | James River Correctional Center                  |
      | Keen Mountain Correctional Center                |
      | King George County Sheriff's Office              |
      | Lancaster County Sheriff's Office                |
      | Lawrenceville Correctional Center                |
      | Lee County Jail                                  |
      | Loudoun County Jail                              |
      | Louisa County Jail                               |
      | Lunenburg Correctional Center                    |
      | Lynchburg City Jail                              |
      | Madison County Sheriff's Office                  |
      | Manassas Park City Police Station                |
      | Marion Correctional Center                       |
      | Marion Correctional Treatment Center             |
      | Martinsville City Sheriff's Office               |
      | Mathews County Jail                              |
      | Meherrin River Regional Jail (Alberta)           |
      | Meherrin River Regional Jail (Boydton)           |
      | Middle Peninsula Regional Jail                   |
      | Middle River Regional Jail                       |
      | Montgomery County Jail                           |
      | Nelson County Sheriff's Office                   |
      | New Kent County Jail                             |
      | New River Valley Regional Jail                   |
      | Newport News City Jail                           |
      | Norfolk City Jail                                |
      | Northampton County Jail                          |
      | Northern Neck Regional Jail                      |
      | Northumberland County Jail                       |
      | Northwestern Regional Adult Detention Center     |
      | Nottoway Correctional Center                     |
      | Nottoway Work Center                             |
      | Orange County Jail                               |
      | Page County Sheriff's Office                     |
      | Pamunkey Regional Jail                           |
      | Patrick County Sheriff's Office                  |
      | Patrick Henry Correctional Unit                  |
      | Peumansend Creek Regional Jail                   |
      | Piedmont Regional Jail                           |
      | Pittsylvania County Sheriff's Office             |
      | Pocahontas State Correctional Center             |
      | Poquoson City Sheriff's Office                   |
      | Portsmouth City Jail                             |
      | Powhatan Correctional Center                     |
      | Prince William/Manassas Regional ADC             |
      | Radford City Police Department                   |
      | Rappahannock - Shanandoah - Warrenton Regional Jail|
      | Rappahannock Regional Jail                       |
      | Red Onion State Prison                           |
      | Richmond City Jail                               |
      | River North Correctional Center                  |
      | Riverside Regional Jail                          |
      | Roanoke City Jail                                |
      | Roanoke County Jail                              |
      | Rockbridge Regional Jail                         |
      | Rockingham/Harrisonburg Regional Jail            |
      | Rustburg Correctional Unit                       |
      | Salem Sheriff’s Office                           |
      | Scott County Jail                                |
      | Shenandoah County Jail                           |
      | Southampton County Jail                          |
      | Southside Regional Jail                          |
      | Southwest Virginia Regional Jail - Abington      |
      | Southwest Virginia Regional Jail - Duffield      |
      | Southwest Virginia Regional Jail - Haysi         |
      | Southwest Virginia Regional Jail - Tazewell      |
      | St. Brides Correctional Center                   |
      | Stafford Diversion Center                        |
      | State Farm Correctional Center                   |
      | Staunton City Jail                               |
      | Surry County Jail                                |
      | Sussex County Jail                               |
      | Sussex I State Prison                            |
      | Sussex II State Prison                           |
      | Tazewell County Jail                             |
      | Virginia Beach Correctional Center               |
      | Virginia Correctional Center for Women           |
      | Virginia Peninsula Regional Jail                 |
      | Wallens Ridge State Prison                       |
      | Warren County Jail                               |
      | Warren County RSW Regional Jail                  |
      | Waynesboro City Jail                             |
      | Western Tidewater Regional Jail                  |
      | Western Virginia Regional Jail                   |
      | Westmoreland County Jail                         |
      | Williamsburg City Jail                           |
      | Wise Correctional Unit                           |
      | Wise County Jail                                 |
      | York-Poquoson Sheriff's Office                   |
    Then I verify text box Date and Time field value and error message for following fields
      | Application Id                    |
      | My Workspace Date                 |
      | Ldss Received Date                |
      | Expedited                         |
      | Hpe                               |
      | Application Received Date         |
      | Application Signature Date        |
      | Application Update Date           |
      | Number Of Applicants In Household |
      | VaCMS Case ID                     |
      | Vcl Due Date                      |
      | Mi Received Date                  |
      | Case Worker First Name            |
      | Case Worker Last Name             |
    Then I verify "Number Of Approved Applicants" field is disable and cleared out
    And Close the soft assertions

  @CP-24822 @CP-24822-01 @CP-24822-02 @CP-24822-03 @CP-24821-01 @CP-34498 @CP-34498-20 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil @ruslan
  Scenario: Verify when I set the Action Taken to “Submitted Ticket” Task as Complete VCCC Response Date field becomes enabled/disabled
    When I will get the Authentication token for "CoverVA" in "CRM"
    When If any In Progress task present then update that to Cancelled
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "VaCMS System Error" task page
    And I will provide following information before creating task
      | actionTakenSingle|Submitted Ticket|
      | status           |Complete        |
    And I click on save button on task edit page
    Then I verify "VCCC Response Date" field is enable and required
    And I will provide following information before creating task
      | actionTakenSingle|Returned|
    Then I verify "VCCC Response Date" field is disable and cleared out
    And I will provide following information before creating task
      | status                 | Created           |
      | externalApplicationId  | random            |
      | documentDueDate        | today             |
      | businessUnitAssigneeTo | CPU New Application|
      | assignee               | Service TesterTwo |
    And I click on save button in create task page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I click on search button on task search page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I will update the following information in task slider
      | status             | Complete        |
      | actionTakenSingle  | Submitted Ticket|
      | disposition        | Resolved        |
    And I click on save in Task Slider
    Then Verify I will see error message "Please correct the Action Taken or provide the Date Sent to VCCC on the task"
    Then I click on cancel button on task slider
    And I click on continue on task details warning window
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status           | Complete             |
      | actionTknSingle  | Submitted Ticket     |
      | disposition      | Resolved             |
      | vcccResponseDate | today                |
      | reasonForEdit    | Corrected Data Entry |
      | dataSentToVcc    | today                |
    And I click on save button on task edit page
    And Close the soft assertions

  @CP-24821 @CP-24821-02 @CP-24821-03 @CP-30301 @CP-30301-04 @CP-34498 @CP-34498-19 @CP-30081 @CP-30081-08 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @kamil @ruslan
  Scenario: Verify VA: Require Date Sent to VCCC Field if Action Taken = Submitted Ticket
    When I will get the Authentication token for "CoverVA" in "CRM"
    When If any In Progress task present then update that to Cancelled
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "VaCMS System Error" task page
    And I will provide following information before creating task
      | actionTakenSingle|Returned|
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    Then I verify "VCCC Response Date" field is disable and cleared out
    And I will provide following information before creating task
      | actionTakenSingle      | Returned          |
      | businessUnitAssigneeTo | CPU New Application|
      | assignee               | Service TesterTwo |
    Then I verify "VCCC Response Date" field is disable and cleared out
    And I will provide following information before creating task
      | externalApplicationId | random            |
      | documentDueDate       | today             |
      | status                | Created           |
    And I click on save button in create task page
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I perform a Task Search by following fields
      | assignmentStatus | Unassigned |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I perform a Task Search by following fields
      | assignmentStatus | Assigned |
    And I click on search button on task search page
    And I initiated search records API
    Then I verify staffAssignedTo field returns "staffAssignedTo"
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | Complete             |
      | assignee        ||
      | actionTknSingle | Submitted Ticket     |
      | disposition     | Resolved             |
      | reasonForEdit   | Corrected Data Entry |
      | dataSentToVcc   | today                |
    And I click on save button on task edit page
    Then I verify "VCCC Response Date" field is enable and required
    And Close the soft assertions

  @CP-47535 @CP-47535-01 @CP-48064 @CP-48064-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan
  Scenario: Verify a new Consumer Data Request Escalation task
    When I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Consumer Data Request Escalation" task page
    Then I verify "businessUnitAssignedTo" field does not contains value on "create" page
      | Appeals     |
      | PHC Renewal |
    And I will provide following information before creating task
      | status          | Complete |
      | documentDueDate | today    |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    Then I verify "Application Id" field is enabled
    Then I verify "Contact Reason" field is enabled
    And I will provide following information before creating task
      | status                 | Created |
      | businessUnitAssigneeTo | CPU PW  |
    And I click on save button in create task page
    And I will get "Created" from traceId and pass it as end point
    And I initiated Event GET API
    And I run the Event GET API and get the payload
    When I navigate to "Task Search" page
    Then I will search for newly created task on Task Search
    And I click on search button on task search page
    And In search result click on task id to navigate to view page
    And I click on edit button on view task page
    Then I verify "Application Id" field is enabled
    Then I verify "Contact Reason" field is enabled
    Then I verify "businessUnitAssignedTo" field does not contains value on "edit" page
      | Appeals     |
      | PHC Renewal |
    And I will update the following information in edit task page
      | businessUnitAssigneeTo |   |
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "ASSIGNEE BUSINESS UNIT "
    And I will update the following information in edit task page
      | status                 | Complete             |
      | businessUnitAssigneeTo | CPU PW               |
      | reasonForEdit          | Corrected Data Entry |
      | actionTakenSingle      | None                 |
      | disposition            | Unresolved           |
    And I click on save button on task edit page
    Then I verify should I navigated to view task page
    And I verify the below task details are displayed in my task
    And Close the soft assertions