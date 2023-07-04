Feature: Validation of New Renewal SR

# refactoring 19-08-2021 by priyal
  @CP-22666 @CP-22666-01 @CP-22877 @CP-22877-01 @CP-22537 @CP-22537-02 @25708 @25708-03 @CP-19571 @CP-19571-05 @CP-18086 @CP-18086-01 @CP-43009-02 @CP-39671-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan @vidya @priyal
  Scenario: Verify required/optional fields for Renewal SR in Cover-VA
    #This scenario has to be updated completely after finding all app renewal sr user stories
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I click on save button on task edit page
    Then I verify task mandatory fields error message "APPLICATION TYPE "
    Then I verify task mandatory fields error message "APPLICATION ID "
    Then I verify task mandatory fields error message "MY WORKSPACE DATE "
    And I click on cancel button on create task type screen
    And I click on cancel button on warning message
    Then I verify text box Date and Time field value and error message for following fields
      | Application Id                    |
      | My Workspace Date                 |
      | Ldss Received Date                |
      | Expedited                         |
      | Hpe                               |
      | Closed Renewal                    |
      | Application Received Date         |
      | Application Signature Date        |
      | Application Update Date           |
    And I verify "applicationType" single select drop down value
      | Ex Parte Renewal - CVIU  |
      | MAGI - CPU               |
      | MAGI - PW                |
      | Renewal Application CVIU |
    Then I verify text box Date and Time field value and error message for following fields
    #two fields are commented out because they are disabled in create task page
      | Number Of Applicants In Household |
   #   | Number Of Approved Applicants     |
      | VaCMS Case ID                     |
      | Vcl Due Date                      |
   #   | Vcl Sent Date                     |
      | Mi Received Date                  |
      | Case Worker First Name            |
      | Case Worker Last Name             |
    And I verify "channel" single select drop down value
      | CommonHelp |
      | FFM-D      |
      | FFM-R      |
      | FFM-R&D    |
      | LDSS       |
      | Paper      |
      | RDE        |
    And I verify "decisionSource" single select drop down value
      | Manual         |
      | VaCMS          |
      | VaCMS + Manual |
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
    And I verify "denialReason" multi select drop down value
      | Auto-Denied (Self-Direct)                                        |
      | Does not meet a full benefit alien status                        |
      | Does not meet a MAGI covered group/opted out of plan 1st         |
      | Duplicate Application in VaCMS                                   |
      | Failed to Provide Information necessary to determine eligibility |
      | Filed In Error/Re-Registered                                     |
      | Invalid Signature                                                |
      | Over Income                                                      |
      | Voluntarily Withdrew                                             |
    And I verify "locality" single select drop down value
      | Accomack         |
      | Albemarle        |
      | Alexandria       |
      | Alleghany        |
      | Amelia           |
      | Amherst          |
      | Appomattox       |
      | Arlington        |
      | Augusta          |
      | Bath             |
      | Bedford County   |
      | Bland            |
      | Botetourt        |
      | Bristol          |
      | Brunswick        |
      | Buchanan         |
      | Buckingham       |
      | Buena Vista      |
      | Campbell         |
      | Caroline         |
      | Carroll          |
      | Charles City     |
      | Charlotte        |
      | Charlottesville  |
      | Chesapeake       |
      | Chesterfield     |
      | Clarke           |
      | Clifton Forge    |
      | Colonial Heights |
      | CoverVA          |
      | Covington        |
      | Craig            |
      | Culpeper         |
      | Cumberland       |
      | Danville         |
      | Dickenson        |
      | Dinwiddie        |
      | Emporia          |
      | Essex            |
      | Fairfax City     |
      | Fairfax County   |
      | Falls Church     |
      | Fauquier         |
      | Floyd            |
      | Fluvanna         |
      | Franklin City    |
      | Franklin County  |
      | Frederick        |
      | Fredericksburg   |
      | Galax            |
      | Giles            |
      | Gloucester       |
      | Goochland        |
      | Grayson          |
      | Greene           |
      | Greensville      |
      | Halifax          |
      | Hampton          |
      | Hanover          |
      | Harrisonburg     |
      | Henrico          |
      | Henry            |
      | Highland         |
      | Hopewell         |
      | Isle of Wright   |
      | James City Co.   |
      | King & Queen     |
      | King George      |
      | King William     |
      | Lancaster        |
      | Lee              |
      | Lexington        |
      | Loudoun          |
      | Louisa           |
      | Lunenburg        |
      | Lynchburg        |
      | Madison          |
      | Manassas City    |
      | Manassas Park    |
      | Martinsville     |
      | Mathews          |
      | Mecklenburg      |
      | Middlesex        |
      | Montgomery       |
      | Nelson           |
      | New Kent         |
      | Newport News     |
      | Norfolk          |
      | Northampton      |
      | Northumberland   |
      | Norton           |
      | Nottoway         |
      | Orange           |
      | Page             |
      | Patrick          |
      | Petersburg       |
      | Pittsylvania     |
      | Poquoson         |
      | Portsmouth       |
      | Powhatan         |
      | Prince Edward    |
      | Prince George    |
      | Prince William   |
      | Pulaski          |
      | Radford          |
      | Rappahannock     |
      | Richmond City    |
      | Richmond County  |
      | Roanoke          |
      | Roanoke County   |
      | Rockbridge       |
      | Rockingham       |
      | Russell          |
      | Salem            |
      | Scott            |
      | Shenandoah       |
      | Smyth            |
      | Southampton      |
      | Spotsylvania     |
      | Stafford         |
      | Staunton         |
      | Suffolk          |
      | Surry            |
      | Sussex           |
      | Tazewell         |
      | Virginia Beach   |
      | Warren           |
      | Washington       |
      | Waynesboro       |
      | Westmoreland     |
      | Williamsburg     |
      | Winchester       |
      | Wise             |
      | Wythe            |
      | York             |
    And I verify "inboundCorrespondenceType" multi select drop down value
      | Authorized Representative Verification |
      | Birth Verification                     |
      | Citizenship Documents                  |
      | Drivers License                        |
      | Earned Income                          |
      | Health Insurance Documents             |
      | Identity Verification                  |
      | Medical ID Card                        |
      | Non-Medical ID Card                    |
      | Other                                  |
      | Self Employment Income                 |
      | SSN Documents                          |
      | Unearned Income                        |
    And I verify "reason" single select drop down value
      | Add a Person      |
      | Case Change       |
      | Non MAGI - ABD    |
      | Re-registration   |
      | Renewal           |
      | Requested by LDSS |
      | RMC Completed     |
      | SNAP/TANF         |
      | Spenddown         |
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
      | Clifton Forge Sheriff's Office                   |
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
      | Salem Sheriff's Office                           |
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
    And Close the soft assertions

    # Refactoring by priyal on 30-08-2021
#  @CP-22666 @CP-22666-02 @CP-22904 @CP-22904-01 @CP-21547 @CP-21547-01 @CP-19479 @CP-19479-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan @vidya
 #commented out due to CP-25501 because we dont see Cancelled option on Process application task
  Scenario: Create Renewal SR with required/optional fields in Cover-VA and End Renewal SR when Process Application Task is cancelled
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
#    And I click on save button on task edit page
    And I will provide following information before creating task
      | applicationType           | Ex Parte Renewal - CVIU |
      | externalApplicationId     | random                  |
      | myWorkSpaceDate           | today                   |
      | ldssReceivedDate          | today                   |
      | applicationSignatureDate  | today                   |
      | applicationReceivedDate   | today                   |
      | applicationUpdateDate     | today                   |
      | noOfApplicantsInHousehold | 1                       |
      | noOfApprovedApplicants    | 1                       |
      | channel                   | Paper                   |
      | hpe                       | true                    |
      | closedRenewal             | true                    |
      | expedited                 | true                    |
      | missingInfoRequired       | Yes                     |
      | miReceivedDate            | today                   |
      | applicationStatus         | Data Collection         |
      | externalCaseId            | 123654789               |
      | caseWorkerFirstName       | Ruslan                  |
      | caseWorkerLastName        | Agaev                   |
  #    | locality                  | Westmoreland            |
   #   | reason                    | Case Change             |
      | facilityName              | Roanoke City Jail       |
      | facilityType              | Regional Jail           |
      | InbDocType                | Drivers License         |
      | vclDueDate                | today                   |
      | vclSentDate               | today                   |
  #  And I click on save button in create service request page
    And I click on save button on task edit page
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | today             |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has my work space date which we provide the search parameter
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Cancelled           |
      | reasonForCancel   | Created Incorrectly |
      | actionTakenSingle | Sent NOA            |
    And I click on save button on task edit page
    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Renewal SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Cancelled"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22666 @CP-22666-03 @CP-22904 @CP-22904-02 @CP-21547 @CP-21547-02 @CP-23433 @CP-23433-05 @CP-19443 @CP-19443-04 @CP-23725 @CP-23725-04 @CP-22740 @CP-22740-03 @CP-18086 @CP-18086-02 @CP-21023 @CP-21023-02 @CP-42776 @CP-43442-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @ruslan @priyal @chandrakumar
  Scenario: Verify system is Populating Application Type on Process Application Task based on Renewal SR value
    When I will get the Authentication token for "<projectName>" in "CoverVA"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | Renewal Application CVIU |
      | externalApplicationId    | random                   |
      | myWorkSpaceDate          | +1                       |
      | applicationReceivedDate  | today                    |
      | channel                  | CommonHelp               |
      | applicationSignatureDate | today                    |
      | renewalDate              | today                    |
      | facilityName             | Bristol City Jail        |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | +1                |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has my work space date which we provide the search parameter
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" task fields are displayed
    And I click on edit button on view task page
    Then I verify "disposition" field does not contains value on "edi" page
      | Denied |
    And I will update the following information in edit task page
      | status            | Complete             |
      | reasonForEdit     | Corrected Data Entry |
      | actionTakenSingle | Transferred to LDSS  |
      | disposition       | Referred             |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Renewal SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    And I click on edit service request button
    Then I verify "disposition" single select drop down value
      | Approved      |
      | Auto-Approved |
      | Auto-Denied   |
      | Cancelled     |
      | Denied/Closed |
      | Referred      |
      | Reinstated and Approved   |
    And I verify "applicationType" single select drop down value
      | Ex Parte Renewal - CVIU  |
      | MAGI - CPU               |
      | MAGI - PW                |
      | Renewal Application CVIU |
      | Renewal Application CPU  |
    Then I verify text box Date and Time field value and error message for following fields
      | Application Id |
    And I will update the following information in edit task page
      | facilityName | Department of Juvenile Justice |
    And I will verify the following Facility Name and Facility Type set to "Department of Juvenile Justice"
      | Department of Juvenile Justice |
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22904 @CP-22904-03 @CP-21547 @CP-21547-03 @CP-23433 @CP-23433-03 @CP-19443 @CP-19443-05 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
  Scenario: Verify Process Application Task to Priority = 1 for Application type = MAGI - PW in App Renewals SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - PW  |
      | externalApplicationId    | random     |
      | myWorkSpaceDate          | -1         |
      | channel                  | CommonHelp |
      | applicationSignatureDate | today      |
      | renewalDate              | today      |
      | applicationReceivedDate  | today      |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -1                |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has my work space date which we provide the search parameter
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" task fields are displayed
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete             |
      | reasonForEdit     | Corrected Data Entry |
      | actionTakenSingle | Sent NOA             |
      | disposition       | Auto-Denied          |
    And I click on save button on task edit page
  #  Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Renewal SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Auto-Denied"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22904 @CP-22904-04 @CP-21547 @CP-21547-04 @CP-23433 @CP-23433-04 @CP-19443 @CP-19443-06 @CP-19441 @CP-19441-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @priyal
  Scenario: Verify Process Application Task to Priority = 2 for Application type != MAGI - PW in App Renewals SR
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - CPU |
      | externalApplicationId    | random     |
      | myWorkSpaceDate          | -2         |
      | channel                  | CommonHelp |
      | renewalDate              | today      |
      | applicationSignatureDate | today      |
      | applicationReceivedDate  | today      |
    And I click on save button in create service request page
 #   Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -2                |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    Then I verify task search result has my work space date which we provide the search parameter
    And In search result click on task id to navigate to view page
    Then I verify "Renewal SR" link section has all the business object linked : "Task"
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" task fields are displayed
    Then I verify "Process Application" link section has all the business object linked : "Service Request"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete             |
      | reasonForEdit     | Corrected Data Entry |
      | actionTakenSingle | Sent NOA             |
      | disposition       | Approved             |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Renewal SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22905 @CP-22905-01 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya
    #commented out @CP-22908 @CP-22908-01 because due to CP-25501 we can not see Cancelled option systematically create tasks
  Scenario: Validate Final Application Review task is created when Execute Wait Step is over and cancel functionality of Final Application Review task
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - CPU |
      | externalApplicationId    | random     |
      | myWorkSpaceDate          | -31        |
      | missingInfoRequired      | Yes        |
      | vclDueDate               | -2         |
      | channel                  | CommonHelp |
      | applicationSignatureDate | today      |
      | applicationReceivedDate  | today      |
      | renewalDate              | today      |
      | miReceivedDate           | today      |
      | InbDocType               | Earned Income,Other |
    When I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    And I expend first Task from the list by clicking in Task ID
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete             |
      | reasonForEdit     | Corrected Data Entry |
      | actionTakenSingle | Sent VCL             |
      | disposition       | Pending MI           |
    And I click on save button on task edit page
  #  Then I verify Success message is displayed for task update
    And I click id of "Renewal SR" in Links section
    And I click id of "Final Application Review" in Links section
    Then I verify "Final Application Review" task fields are displayed
 #   And I click on edit button on view task page
 #   And I will update the following information in edit task page
 #     | status          | Cancelled                                                |
 #     | reasonForCancel | Created Incorrectly                                      |
 #     | reasonForEdit   | Corrected Data Entry                                     |
 #     | actionTaken     | Obtained - Inbound Verif Docs                            |
 #     | taskInfo        | Testing Cancel functionality of Final Application Review |
 #   And I click on save button on task edit page
 #   Then I verify Success message is displayed for task update
 #   Then I verify should I navigated to view task page
 #   And I click id of "Application Renewal SR" in Links section
 #   Then I verify the sr status is updated to close and disposition is set to "Cancelled"
 #   When I will get the Authentication token for "CoverVA" in "CRM"
 #   And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
 #   And I run bpm process get api by sr id
 #   Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22905 @CP-22905-03 @CP-30301 @CP-30301-09 @CP-43007 @CP-43007-01 @CP-43007-03 @CP-43239 @CP-43239-02 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @vidya @ruslan
  Scenario: Validate Final Application Review task is not created when Execute Wait Step is not over
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - PW           |
      | missingInfoRequired      | Yes                 |
      | externalApplicationId    | random              |
      | myWorkSpaceDate          | +4                  |
      | vclDueDate               | -1                  |
      | channel                  | CommonHelp          |
      | applicationSignatureDate | today               |
      | applicationReceivedDate  | today               |
      | renewalDate              | today               |
      | miReceivedDate           | today               |
      | InbDocType               | Earned Income       |
    When I click on save button in create service request page
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch    | true              |
      | dateOfContact    | +4                |
      | applicationId    | getFromCreatePage |
      | assignmentStatus | Assigned          |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "true" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I click on cancel button on task search page
    And I perform a Task Search by following fields
      | advanceSearch    | true              |
      | dateOfContact    | +4                |
      | applicationId    | getFromCreatePage |
      | assignmentStatus | Unassigned        |
    And I click on search button on task search page
    Then I verify request payload "assignedFlag" contains assignedFlag "false" and serviceRequestInd "false"
    Then I verify No Records Available message is displayed
    And I click on cancel button on task search page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | +4                |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I verify the updated information in view task details page
    And I click id of "Process Application" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete             |
      | reasonForEdit     | Corrected Data Entry |
      | actionTakenSingle | Sent VCL             |
      | disposition       | Pending MI           |
    And I click on save button on task edit page
 #   Then I verify Success message is displayed for task update
    And I click id of "Renewal SR" in Links section
    Then I verify "Final Application Review" task is not created
    And Close the soft assertions

  @CP-22907 @CP-22907-01 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Renewal SR Ends and disposition = Approved when Final Application Review Task is complete
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - PW           |
      | externalApplicationId    | random              |
      | myWorkSpaceDate          | -31                 |
      | missingInfoRequired      | Yes                 |
      | vclDueDate               | -2                  |
      | channel                  | CommonHelp          |
      | applicationSignatureDate | today               |
      | renewalDate              | today               |
      | applicationReceivedDate  | today               |
      | miReceivedDate           | today               |
      | InbDocType               | Earned Income,Other |
    And I click on save button in create service request page
  #  Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -31               |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete             |
      | reasonForEdit     | Corrected Data Entry |
      | actionTakenSingle | Sent VCL             |
      | disposition       | Pending MI           |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    And I click id of "Renewal SR" in Links section
    And I click id of "Final Application Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                |
      | reasonForEdit | Corrected Data Entry    |
      | actionTaken   | Escalated to Supervisor |
      | disposition   | Approved                |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Renewal SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22907 @CP-22907-02 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Renewal SR Ends and disposition = Auto-Approved when Final Application Review Task is complete
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | MAGI - CPU          |
      | externalApplicationId    | random              |
      | myWorkSpaceDate          | -31                 |
      | missingInfoRequired      | Yes                 |
      | vclDueDate               | -2                  |
      | channel                  | CommonHelp          |
      | applicationSignatureDate | today               |
      | applicationReceivedDate  | today               |
      | renewalDate              | today               |
      | miReceivedDate           | today               |
      | InbDocType               | Earned Income,Other |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -31               |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete             |
      | reasonForEdit     | Corrected Data Entry |
      | actionTakenSingle | Sent VCL             |
      | disposition       | Pending MI           |
    And I click on save button on task edit page
  #  Then I verify Success message is displayed for task update
    And I click id of "Renewal SR" in Links section
    And I click id of "Final Application Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                                                    |
      | reasonForEdit | Corrected Data Entry                                                        |
      | taskInfo      | Validate Renewal SR ends when Final Application Review Task is complete |
      | actionTaken   | Obtained - Electronic Sources                                               |
      | disposition   | Auto-Approved                                                               |
    And I click on save button on task edit page
#    Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Renewal SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Auto-Approved"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22907 @CP-22907-03 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario: Validate Renewal SR Ends and disposition = Referred when Final Application Review Task is complete
    Given I will get the Authentication token for "CoverVA" in "CRM"
    Given I logged into CRM with "Service Account 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | Ex Parte Renewal - CVIU |
      | externalApplicationId    | random                  |
      | myWorkSpaceDate          | -31                     |
      | missingInfoRequired      | Yes                     |
      | vclDueDate               | -1                      |
      | channel                  | CommonHelp              |
      | renewalDate              | today                   |
      | applicationSignatureDate | today                   |
      | applicationReceivedDate  | today                   |
      | miReceivedDate           | today                   |
      | InbDocType               | Earned Income,Other     |
      | facilityName             | Bristol City Jail       |
    And I click on save button in create service request page
  #  Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -31               |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And In search result click on task id to navigate to view page
    And I click id of "Process Application" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status            | Complete                                                                    |
      | reasonForEdit     | Corrected Data Entry                                                        |
      | taskInfo          | Validate Renewal SR ends when Final Application Review Task is complete |
      | actionTakenSingle | Sent VCL                                                                    |
      | disposition       | Pending MI                                                                  |
    And I click on save button on task edit page
 #   Then I verify Success message is displayed for task update
    And I click id of "Renewal SR" in Links section
    And I click id of "Final Application Review" in Links section
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status        | Complete                                                                    |
      | reasonForEdit | Corrected Data Entry                                                        |
      | taskInfo      | Validate Renewal SR ends when Final Application Review Task is complete |
      | actionTaken   | Obtained - Outbound Call                                                    |
      | disposition   | Referred                                                                    |
    And I click on save button on task edit page
 #   Then I verify Success message is displayed for task update
    Then I verify should I navigated to view task page
    And I click id of "Renewal SR" in Links section
    Then I verify the sr status is updated to close and disposition is set to "Referred"
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-22909 @CP-22909-01 @CP-22910 @CP-22910-01 @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Systematically created Verification Document for Renewal SR and link to business object consumer
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I have a Inbound Document that with the Inbound Document Type of "VACV Verification Document"
    When I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on Create Link button and choose task or sr link
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I verify task search results are displayed
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I click on refresh button
    And I verify that Inbound Correspondence Page has all "Renewal SR" linked objects
    And I click id of "Verification Document" in link section of inbound page
    Then I verify "Verification Document" task fields are displayed
    Then I verify "Verification Document" link section has all the business object linked
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          | Renewal SR | Closed | today      ||         ||        ||            | false         ||            ||          ||           ||

  @CP-22909 @CP-22909-02 @CP-22910 @CP-22910-02 @CP-19571 @CP-19571-02 @CP-18086 @CP-18086-03 @CP-21023 @CP-21023-03 @CP-19484 @CP-19484-05 @priyal @vidya @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA  #will fail due to CP-43405
  Scenario: Systematically created Verification Document for Renewal SR and link to business object consumer and Inbound document
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | Ex Parte Renewal - CVIU |
      | externalApplicationId    | random                  |
      | myWorkSpaceDate          | -2                      |
      | missingInfoRequired      | Yes                     |
      | vclDueDate               | -1                      |
      | channel                  | CommonHelp              |
      | renewalDate              | today                   |
      | applicationSignatureDate | today                   |
      | applicationReceivedDate  | today                   |
      | miReceivedDate           | today                   |
      | InbDocType               | Earned Income,Other     |
      | facilityName             | Bristol City Jail       |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I have a Inbound Document that with the Inbound Document Type of "VACV Verification Document"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on Create Link button and choose task or sr link
    And I perform a Task Search by following fields
      | advanceSearch | true              |
      | dateOfContact | -2                |
      | applicationId | getFromCreatePage |
    And I click on search button on task search page
    And I verify save task search section is displayed
    And I click on Link button in task search
    And I click on refresh button
    And I verify that Inbound Correspondence Page has all "Renewal SR" linked objects
    And I click id of "Verification Document" in link section of inbound page
    Then I verify "Verification Document" task fields are displayed
    Then I verify "Verification Document" link section has all the business object linked : "Service Request,Consumer,Inbound Correspondence"
    And I click id of "Renewal SR" in Links section
    Then I will verify "Renewal SR" Details view SR details page
    And I click on edit service request button
    And I verify "applicationType" single select drop down value
      | Ex Parte Renewal - CVIU  |
      | MAGI - CPU               |
      | MAGI - PW                |
      | Renewal Application CVIU |
      | Renewal Application CPU  |
    And I will update the following information in edit task page
      | reasonForEdit             | Corrected Data Entry   |
      | status                    | Closed                 |
    #  | applicationReceivedDate   | today                          |
    #  | channel                   | CommonHelp                     |
      | missingInfoRequired       | No                     |
      | applicationStatus         | Medicare Review Needed |
      | noOfApplicantsInHousehold | 1                      |
      | disposition               | Cancelled              |
    And I click on save button on task edit page
    Then I verify the updated information in view sr details page
    Then I verify "Process Application" task status is updated to "Cancelled" on the link section
    Then I verify "Verification Document" task status is updated to "Cancelled" on the link section
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions

  @CP-19441 @CP-19441-01 @CP-19484 @CP-19484-04 @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA @priyal #will fail due to CP-43405
  Scenario Outline: Create Renewal SR and link to consumer and Task systematically
    Given I will get the Authentication token for "CoverVA" in "CRM"
    And I send API CALL for Create Consumer with Data
      | consumerIdentificationNumber[0].externalConsumerId | npi:: |
    And I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    And I navigate to case and consumer search page
    And I searched consumer created by api script
    And I click the first consumer id from the search results
    And I click on "Task & Service Request" Tab on Contact Dashboard Page
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | taskInfo                 | Test CP-19441           |
      | applicationType          | Ex Parte Renewal - CVIU |
      | externalApplicationId    | random                  |
      | myWorkSpaceDate          | today                   |
      | channel                  | CommonHelp              |
      | renewalDate              | today                   |
      | applicationSignatureDate | today                   |
      | applicationReceivedDate  | today                   |
      | miReceivedDate           | today                   |
      | facilityName             | Bristol City Jail       |
    And I click on save button in create service request page
#    Then I verify Success message is displayed for SR
    And I Verify user is navigate back to Task and service Request Page
    And I navigate to newly created SR in Task & Service Request Tab
    And I navigate to view SR details page by clicking on sr id
    Then I verify "Renewal SR" link section has all the business object linked : "Task,Consumer"
    And I click id of "Process Application" in Links section
    Then I verify "Process Application" link section has all the business object linked : "Service Request,Consumer"
    And I click on edit button on view task page
    And I will update the following information in edit task page
      | status          | OnHold              |
      | reasonForOnHold | Missing Information |
      | assignee        | Service TesterTwo   |
    And I click on save button on task edit page
    And I have a Inbound Document that with the Inbound Document Type of "VACV Verification Document"
    And I click on the main navigation menu
    And I should see the Inbound Correspondence Search Icon
    And I search for an Inbound Document by the following values
      | CID | fromRequest |
    When I click on the Inbound Document CID link "firstAvailable"
    And I click on Create Link button and choose task or sr link
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And I click on Link button in task search
    And I click on refresh button
    And I verify that Inbound Correspondence Page has all "Renewal SR" linked objects
    And I click id of "Verification Document" in link section of inbound page
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on cancel button on task slider
    When I click Continue button inside the warning message
    And In search result click on task id to navigate to view page
    And I click id of "Renewal SR" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Cancelled            |
    And I click on save button on task edit page
    Then I verify "Process Application" task status is updated to "Cancelled" on the link section
    Then I verify "Verification Document" task status is updated to "Cancelled" on the link section
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy | createdOn |contactReason|
      ||          | Renewal SR | Closed | today      ||         ||        ||            | false         ||            ||          ||           ||

  @CP-22740 @CP-22740-04 @CP-22739 @CP-22739-03 @CP-22739-04 @CP-19484 @CP-19484-06 @priyal @chandrakumar @crm-regression @CoverVA-UI-Regression @ui-wf-coverVA
  Scenario Outline: Validate process application Application type matches value Expedited Application - CVIU
    When I will get the Authentication token for "<projectName>" in "CoverVA"
    Given I logged into CRM with "Service Tester 2" and select a project "CoverVA"
    And I minimize Genesys popup if populates
    When I navigate to "Renewal SR" service request page
    And I will provide following information before creating task
      | applicationType          | Ex Parte Renewal - CVIU |
      | externalApplicationId    | random                  |
      | myWorkSpaceDate          | today                   |
      | priority                 | 3                       |
      | channel                  | CommonHelp              |
      | applicationStatus        | Medicare Review Needed  |
      | applicationSignatureDate | today                   |
      | applicationReceivedDate  | today                   |
      | facilityName             | Bristol City Jail       |
      | renewalDate              | today                   |
      | miReceivedDate           | today                   |
    And I click on save button in create service request page
  #  Then I verify Success message is displayed for SR
    When I navigate to "Task Search" page
    And I populate required fields to do task search "<taskId>","<taskType>","<srType>","<status>","<statusDate>","<Priority>","<dueDate>","<searchCase>","<caseId>","<searchConsumer>","<consumerId>","<advanceSearch>","<consumerFN>","<consumerLN>","<source>","<assignee>","<createdBy>","<createdOn>","<contactReason>"
    And I click on search button on task search page
    And I click task id to get the results in descending order in Task Search
    And In search result click on task id to navigate to view page
    Then I will verify "Renewal SR" Details view SR details page
    And I click on edit service request button
    And I will update the following information in edit task page
      | myWorkSpaceDate           | -3                      |
      | reasonForEdit             | Entered Additional Info |
      | applicationReceivedDate   | today                   |
      | channel                   | CommonHelp              |
      | noOfApplicantsInHousehold | 1                       |
      | missingInfoRequired       | No                      |
 #     | caseWorkerFirstName       | Random                  |
 #     | caseWorkerLastName        | Random                  |
 #     | locality                  | Westmoreland            |
 #     | reason                    | Renewal                 |
    And I click on save button on task edit page
    Then I will verify "Renewal SR" Details view SR details page
    And I click id of "Process Application" in Links section
    When I navigate to "Task Search" page
    And I will search with taskId
    And I verify latest task displayed in "Task Search" page
    And I click on initiate button in task search page
    And Verify task slider is displayed
    And I click on cancel button on task slider
    When I click Continue button inside the warning message
    And In search result click on task id to navigate to view page
    And I click id of "Renewal SR" in Links section
    And I click on edit service request button
    And I will update the following information in edit task page
      | status        | Closed               |
      | reasonForEdit | Corrected Data Entry |
      | disposition   | Cancelled            |
    And I click on save button on task edit page
    Then I verify "Process Application" task status is updated to "Cancelled" on the link section
    And Wait for 5 seconds
    And I initiated bpm process get api by service request id for process name "CoverVA_ApplicationRenewal_V2"
    And I run bpm process get api by sr id
    Then I verify all tokens should be terminated in the process
    And Close the soft assertions
    Examples:
      | taskId | taskType | srType     | status | statusDate | Priority | dueDate | searchCase | caseId | searchConsumer | consumerId | advanceSearch | consumerFN | consumerLN | source | assignee | createdBy         | createdOn |contactReason|
      ||          | Renewal SR | Open   || 3        ||            ||                || true          ||            ||          | Service TesterTwo | today     ||
