Feature: CoverVA Contact Record Configurations Part 12


  @CP-29802 @CP-36211 @CP-36211-01 @CP-29802-01 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline:  Conditionally Display Facility Fields in Active Contact for General, ThirdParty and Unidentified contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "<CR type>" contact record type
    And Verify that facility name and facility type displayed
      | CVIU - DJJ   |
      | CVIU - DOC   |
      | CVIU - Jails |
      | CVIU - Other |

    Examples:
      | CR type      |
      | General      |
      | ThirdParty   |
      | Unidentified |

  @CP-29802 @CP-39468 @CP-36211 @CP-36211-02 @CP-29802-1.1 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline:  Facility Name and Facility Type fields in Active Contact for General, ThirdParty and Unidentified contact
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "<CR type>" contact record type
    Then I navigate to business unit "CVIU - DJJ"
    And Verify the facility name field values in dropdown
      | Accomack County Jail                        |
      | Alexandria City Jail                        |
      | Arlington County Jail                       |
      | Blue Ridge Regional Jail (BRRJ)- Amherst    |
      | Blue Ridge Regional Jail (BRRJ)- Bedford    |
      | Blue Ridge Regional Jail (BRRJ)- Campbell   |
      | Blue Ridge Regional Jail (BRRJ)- Halifax    |
      | Blue Ridge Regional Jail (BRRJ)- Lynchburg  |
      | Botetourt County Jail                       |
      | Bristol City Jail                           |
      | Charlotte County Jail                       |
      | Chesapeake City Jail                        |
      | Chesterfield County Jail                    |
      | Culpeper County Jail                        |
      | Danville City Jail                          |
      | Fairfax County Jail                         |
      | Franklin County Jail                        |
      | Gloucester County Jail                      |
      | Hampton City Jail                           |
      | Rockingham/Harrisonburg Regional Jail       |
      | Southampton County Jail                     |
      | Southwest Virginia Regional Jail - Abington |
      | Southwest Virginia Regional Jail - Duffield |
      | Southwest Virginia Regional Jail - Haysi    |
      | Southwest Virginia Regional Jail - Tazewell |
      | Sussex County Jail                          |
      | Virginia Beach City Jail                    |
      | Virginia Peninsula Regional Jail            |
      | Western Tidewater Regional Jail             |
      | Hampton Roads Regional Jail                 |
      | Henrico County Jail (East)                  |
      | Henrico County Jail (West)                  |
      | Henry County Jail                           |
      | Lancaster County Jail                       |
      | Loudoun County Jail                         |
      | Martinsville City Jail                      |
      | Meherrin River Reg Jail (Alberta)           |
      | Meherrin River Reg Jail (Boydton)           |
      | Middle Peninsula Regional Jail              |
      | Middle River Regional Jail                  |
      | Montgomery County Jail                      |
      | New River Valley Regional Jail              |
      | Newport News City Jail                      |
      | Norfolk City Jail                           |
      | Northern Neck Regional Jail                 |
      | Northwestern Regional Jail                  |
      | Page County Jail                            |
      | Pamunkey Regional Jail                      |
      | Piedmont Regional Jail                      |
      | Pittsylvania County Jail                    |
      | Prince William/Manassas Regional Jail       |
      | Rappahannock Regional Jail                  |
      | Richmond City Jail                          |
      | Riverside Regional Jail                     |
      | Roanoke City Jail                           |
      | Roanoke County Jail                         |
      | Rockbridge Regional Jail                    |
      | Southside Regional Jail                     |
      | Augusta Correctional Center                 |
      | Baskerville Correctional Center             |
      | Beaumont Correctional Center                |
      | Bland Correctional Center                   |
      | Buckingham Correctional Center              |
      | Caroline Correctional Unit                  |
      | Coffeewood Correctional Center              |
      | Deerfield Correctional Center               |
      | Dillwyn Correctional Center                 |
      | Greensville Correctional Center             |
      | Green Rock Correctional Center              |
      | Halifax Correctional Unit                   |
      | Haynesville Correctional Center             |
      | Indian Creek Correctional Center            |
      | Keen Mountain Correctional Center           |
      | Lawrenceville Correctional Center           |
      | Lunenburg Correctional Center               |
      | Nottoway Correctional Center                |
      | Patrick Henry Correctional Unit             |
      | Pocahontas State Correctional Center        |
      | Red Onion State Prison                      |
      | River North Correctional Center             |
      | Rustburg Correctional Unit                  |
      | St. Brides Correctional Center              |
      | State Farm Correctional Center              |
      | State Farm Work Center                      |
      | Sussex I State Prison                       |
      | Virginia Correctional Center for Women      |
      | Wallens Ridge State Prison                  |
      | Wise Correctional Unit                      |
      | Department of Juvenile Justice              |
      | Facility-Other                              |

    Examples:
      | CR type      |
      | General      |
      | ThirdParty   |
      | Unidentified |

  @CP-29802 @CP-29802-1.2 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline:  The Facility Type field is a read only field with the Facility Type enum options-Active Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "<CR type>" contact record type
    Then I navigate to business unit "CVIU - DJJ"
    Then I verify facility type is read only field

    Examples:
      | CR type      |
      | General      |
      | ThirdParty   |
      | Unidentified |

  @CP-29802 @CP-39468 @CP-39468-01 @CP-39851 @CP-39851-01 @CP-36498 @CP-36498-01 @CP-38925 @CP-29802-2.0 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline:  Systematically Set Facility Type when Facility Name Selected-Active Page-Local Jail
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "<CR type>" contact record type
    Then I navigate to business unit "CVIU - DJJ"
    And Verify the facility name with associated facility type "Local Jail" local
      | Accomack County Jail                         |
      | Albermarle County Jail                       |
      | Alexandria City Jail                         |
      | Alexandria Detention Center                  |
      | Alleghany County Jail                        |
      | Amherst County Jail                          |
      | Arlington County Jail                        |
      | Augusta County Jail                          |
      | Bedford County Jail                          |
      | Blue Ridge Regional Jail (BRRJ)- Amherst     |
      | Blue Ridge Regional Jail (BRRJ)- Bedford     |
      | Blue Ridge Regional Jail (BRRJ)- Campbell    |
      | Blue Ridge Regional Jail (BRRJ)- Halifax     |
      | Blue Ridge Regional Jail (BRRJ)- Lynchburg   |
      | Botetourt County Jail                        |
      | Bristol City Jail                            |
      | Brunswick County Jail                        |
      | Campbell County Detention Center             |
      | Caroline Detention Facility                  |
      | Carroll County Jail                          |
      | Charles City County Jail                     |
      | Charlotte County Jail                        |
      | Charlottesville City Jai                     |
      | Chesapeake City Jail                         |
      | Chesterfield County Jail                     |
      | Clarke County Jail                           |
      | Clifton Forge Sheriff’s Office               |
      | Colonial Heights Sheriff’s Office            |
      | Covington City Sheriff’s Office              |
      | Craig County Sheriff’s Office                |
      | Culpeper County Jail                         |
      | Cumberland County Jail                       |
      | Danville City Farm                           |
      | Danville City Jail                           |
      | Department of Corrections (DOC)              |
      | Dickinson County Jail                        |
      | Dinwiddie County Jail                        |
      | Emporia City Jail                            |
      | Essex County VA Jail                         |
      | Falls Church City Jail                       |
      | Fairfax County Jail                          |
      | Floyd County Jail                            |
      | Fluvanna County Jail                         |
      | Franklin City Police Station                 |
      | Franklin County Jail                         |
      | Frederick County Jail                        |
      | Fredericksburg City Jail                     |
      | Galax City Jail                              |
      | Gloucester County Jail                       |
      | Goochland County Jail                        |
      | Grayson County Jail                          |
      | Greene County Jail                           |
      | Greensville County Jail                      |
      | Hampton City Jail                            |
      | Highland County Jail                         |
      | Hopewell City Jail                           |
      | James City County Jail                       |
      | Lee County Jail                              |
      | Louisa County Jail                           |
      | Mathews County Jail                          |
      | Northampton County Jail                      |
      | Northumberland County Jail                   |
      | Northwestern Regional Adult Detention Center |
      | Orange County Jail                           |
      | Portsmouth City Jail                         |
      | Prince William/Manassas Regional ADC         |
      | Radford City Police Department               |
      | Richmond City Jail                           |
      | Roanoke City Jail                            |
      | Scott County Jail                            |
      | Shenandoah County Jail                       |
      | Southampton County Jail                      |
      | Southwest Virginia Regional Jail - Abington  |
      | Southwest Virginia Regional Jail - Duffield  |
      | Southwest Virginia Regional Jail - Haysi     |
      | Southwest Virginia Regional Jail - Tazewell  |
      | Staunton City Jail                           |
      | Surry County Jail                            |
      | Sussex County Jail                           |
      | Tazewell County Jail                         |
      | Virginia Beach Correctional Center           |
      | Warren County Jail                           |
      | Waynesboro City Jail                         |
      | Westmoreland County Jail                     |
      | Williamsburg City Jail                       |
      | Wise County Jail                             |
    Examples:
      | CR type |
      | General |

  @CP-29802 @CP-41218 @CP-41218-01 @CP-39468 @CP-39468-02 @CP-39851 @CP-39851-02 @CP-36498 @CP-36498-02 @CP-38925 @CP-29802-2.0 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline:  Systematically Set Facility Type when Facility Name Selected-Active Page-Regional Jail
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "<CR type>" contact record type
    Then I navigate to business unit "CVIU - DJJ"
    And Verify the facility name with associated facility type "Regional Jail" all
      | Albemarle-Charlottesville Regional Jail             |
      | Alleghany/Covington Regional Jail                   |
      | Blue Ridge Regional Jail (BRRJ)- Amherst            |
      | Blue Ridge Regional Jail (BRRJ)- Bedford            |
      | Blue Ridge Regional Jail (BRRJ)- Campbell           |
      | Blue Ridge Regional Jail (BRRJ)- Halifax            |
      | Blue Ridge Regional Jail (BRRJ)- Lynchburg          |
      | Central Virginia Regional Jail                      |
      | Eastern Shore Regional Jail (Northhampton County)   |
      | Hampton Roads Regional Jail                         |
      | Haysi Regional Jail                                 |
      | Henry County Jail                                   |
      | Henrico County Regional Jail (East)                 |
      | Henrico County Regional Jail (West)                 |
      | Meherrin River Reg Jail (Alberta)                   |
      | Meherrin River Reg Jail (Boydton)                   |
      | Middle Peninsula Regional Jail                      |
      | Middle River Regional Jail                          |
      | Montgomery County Jail                              |
      | New River Valley Regional Jail                      |
      | Newport News City Jail                              |
      | Norfolk City Jail                                   |
      | Northern Neck Regional Jail                         |
      | Pamunkey Regional Jail                              |
      | Piedmont Regional Jail                              |
      | Rappahannock Regional Jail                          |
      | Rappahannock - Shanandoah - Warrenton Regional Jail |
      | Riverside Regional Jail                             |
      | Roanoke County Jail                                 |
      | Rockingham/Harrisonburg Regional Jail               |
      | Riverside Regional Jail                             |
      | Rockbridge Regional Jail                            |
      | Southside Regional Jail                             |
      | Southwest Virginia Regional Jail - Abington         |
      | Southwest Virginia Regional Jail - Duffield         |
      | Southwest Virginia Regional Jail - Haysi            |
      | Southwest Virginia Regional Jail - Tazewell         |
      | Virginia Peninsula Regional Jail                    |
      | Warren County RSW Regional Jail                     |
      | Western Tidewater Regional Jail                     |
      | Western Virginia Regional Jail                      |
    Examples:
      | CR type |
      | General |

  @CP-29802 @CP-41218 @CP-41218-02 @CP-36498 @CP-39468 @CP-39468-03 @CP-39851 @CP-39851-03 @CP-36498-03 @CP-38925 @CP-29802-2.0 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline:  Systematically Set Facility Type when Facility Name Selected-Active Page-Department of Corrections
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    When I click on initiate contact record
    And I minimize Genesys popup if populates
    When I navigate to "<CR type>" contact record type
    Then I navigate to business unit "CVIU - DJJ"
    And Verify the facility name with associated facility type "Department of Corrections" all
      | Appalachian Detention Center           |
      | Augusta Correctional Center            |
      | Baskerville Correctional Center        |
      | Beaumont Correctional Center           |
      | Bland Correctional Center              |
      | Brunswick Work Center                  |
      | Buckingham Correctional Center         |
      | Caroline Correctional Unit             |
      | Caroline Correctional Unit 2           |
      | Central VA Correctional Unit #13       |
      | Coffeewood Correctional Center         |
      | Cold Springs Correctional Unit 10      |
      | Cold Springs Detention Center          |
      | Deep Meadow Correctional Center        |
      | Deerfield Correctional Center          |
      | Dillwyn Correctional Center            |
      | Greensville Correctional Center        |
      | Harrisonburg Diversion Center          |
      | Green Rock Correctional Center         |
      | Halifax Correctional Unit              |
      | Haynesville Correctional Center        |
      | Indian Creek Correctional Center       |
      | James River Correctional Center        |
      | Keen Mountain Correctional Center      |
      | Lawrenceville Correctional Center      |
      | Marion Correctional Center             |
      | Marion Correctional Treatment Center   |
      | Lunenburg Correctional Center          |
      | Nottoway Correctional Center           |
      | Nottoway Work Center                   |
      | Patrick Henry Correctional Unit        |
      | Pocahontas State Correctional Center   |
      | Powhatan Correctional Center           |
      | Red Onion State Prison                 |
      | River North Correctional Center        |
      | Rustburg Correctional Unit             |
      | Stafford Diversion Center              |
      | St. Brides Correctional Center         |
      | State Farm Correctional Center         |
      | Sussex I State Prison                  |
      | Sussex II State Prison                 |
      | Virginia Correctional Center for Women |
      | Wallens Ridge State Prison             |
      | Wise Correctional Unit                 |
    Examples:
      | CR type |
      | General |

  @CP-29802 @CP-36498 @CP-36498-04 @CP-39468 @CP-39468-04 @CP-39851 @CP-39851-04 @CP-38925 @CP-29802-2.0 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario Outline:  Systematically Set Facility Type when Facility Name Selected-Active Page -Department of Juvenile Justice
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    When I navigate to "<CR type>" contact record type
    Then I navigate to business unit "CVIU - DJJ"
    And Verify the facility name with associated facility type "Department of Juvenile Justice" all
      | Department of Juvenile Justice |
    Examples:
      | CR type |
      | General |

  @CP-29802 @CP-29802-4.0 @CP-29802-5.0 @ui-core-cover-va @api-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: Contact_record_update_event
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the Contact Record with Facility name "Accomack County Jail"
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page new
    Then I update the Contact Record with Facility name "Baskerville Correctional Center"
    And I select reason for edit "Correcting Contact Details" drop down and provide details to edit for facility Name
    When I will get the Authentication token for "CoverVA" in "CRM"
    And I will take trace Id for Contact Record update Event "CONTACT_RECORD_SAVE_EVENT" and number 5
    When I will initiate Event GET API
    And I run the Event GET API and get the payload for eventName "CONTACT_RECORD_UPDATE_EVENT" and verify the payload
    Then I verify that payload contains facilityNAME "Baskerville Correctional Center" and FacilityType "Department of Corrections"

  @CP-34992 @CP-34992-1.0 @CP-34992-1.1 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: Conditionally Require Facility Name Based on Contact Reason selection
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the Contact Record without facility name to observe the warning error message
    And I verify facilityName is required field on certain contactReason

  @CP-34992 @CP-34992-1.0 @CP-34992-1.1 @ui-core-cover-va @crm-regression @CoverVA-UI-Regression @araz
  Scenario: Conditionally Require Facility Name Based on Contact Reason selection-Edit Page
    Given I logged into CRM with Call Center Supervisor account and select a project "COVER-VA"
    And I minimize Genesys popup if populates
    When I click on initiate contact record
    Then I searched customer have First Name as "Araz" and Last Name as "Ismayilov"
    And I link the contact to an existing Case or Consumer Profile
    Then I save the Contact Record with no Facility name chosen
    And I navigate to manual contact record search page
    Then I searching for contact with electronic signature and choosing first record
    And I click on edit icon the Contact Details page new
    Then I save the Contact Record without facility name to observe the warning error message on edit page
    And I verify facilityName is required field on certain contactReason