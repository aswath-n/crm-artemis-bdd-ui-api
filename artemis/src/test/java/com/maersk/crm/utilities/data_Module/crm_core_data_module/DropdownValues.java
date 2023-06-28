package com.maersk.crm.utilities.data_Module.crm_core_data_module;

public class DropdownValues {

    private DropdownValues() {
    }

    public static final String DMAS_APPEAL = "DMAS_APPEAL";
    public static final String REQUEST_COMPLETE_DMAS = "REQUEST_COMPLETE_DMAS";
    public static final String ESCELATED_TO_DMAS_maersk = "ESCELATED_TO_DMAS_maersk";
    public static final String CASE_CORRECTION_INCORRECT_ALIGN_STATUS = "CASE_CORRECTION_INCORRECT_ALIGN_STATUS ";
    public static final String CASE_CORRECTION_INCORRECT_DOCUMENT_PROCESSING = "CASE_CORRECTION_INCORRECT_DOCUMENT_PROCESSING";
    public static final String CASE_CORRECTION_INCORRECT_INCOME_EVALUATION = "CASE_CORRECTION_INCORRECT_INCOME_EVALUATION";
    public static final String CASE_CORRECTION_MISSING_VCL = "CASE_CORRECTION_MISSING_VCL";
    public static final String OTHER = "OTHER";
    public static final String TIMELINESS = "TIMELINESS";
    public static final String APPROVED = "APPROVED";
    public static final String DENIED = "DENIED";
    public static final String PENDING = "PENDING";
    public static final String CHANGE_RE_ENTRY = "Change/Re-entry";
    public static final String ASSIGNED_APP_CASE_TO_LDSS = "Assigned App/Case to LDSS";
    public static final String EMAILED_REQUESTED_DOCS = "Emailed Requested Docs";
    public static final String ESCALATED_TO_SUPERVISOR = "Escalated to Supervisor";
    public static final String GENERATED_MANUAL_OB_CORR = "Generated Manual OB Corr";
    public static final String MAILED_REQUESTED_DOCS = "Mailed Requested Docs";
    public static final String NONE = "None";
    public static final String OUTBOUND_CALL_DID_NOT_REACH = "Outbound Call - Did Not Reach";
    public static final String OUTBOUND_CALL_REACHED = "Outbound Call - Reached";
    public static final String PROVIDED_CLARIFICATION_RESPONSE = "Provided Clarification/Response";
    public static final String TRANSFERRED_TO_CVIU = "Transferred to CVIU";
    public static final String UPDATED_APPLICATION = "Updated Application";
    public static final String UPDATED_CASE = "Updated Case";
    public static final String IDR_RESOLVED_LETTER_GENERATED = "IDR Resolved Letter Generated";
    public static final String IDR_UNRESOLVED_LETTER_GENERATED = "IDR Unresolved Letter Generated";
    public static final String NO_ACTION_TAKEN = "No Action Taken";


    public static void main(String[] args) {
        String str =
        "Assigned App/Case to LDSS"+"\n"+
        "Emailed Requested Docs"+"\n"+
        "Escalated to Supervisor"+"\n"+
        "Generated Manual OB Corr"+"\n"+
        "Mailed Requested Docs"+"\n"+
        "None"+"\n"+
        "Outbound Call - Did Not Reach"+"\n"+
        "Outbound Call - Reached"+"\n"+
        "Provided Clarification/Response"+"\n"+
        "Transferred to CVIU"+"\n"+
        "Updated Application"+"\n"+
        "Updated Case"+"\n";

        System.out.println(str.toUpperCase().replaceAll(" ", "_").replaceAll("/", "_").replaceAll("-", ""));
    }


    //CONTACT REASON DD
    public static final String COVERAGE_CORRECTION = "Coverage Correction";
    public static final String FACILITY_CHANGE = "Facility Change";
    public static final String PRE_RELEASE = "Pre-Release";
    public static final String REPORTING_NEWBORN = "Reporting Newborn";
    public static final String REPORTING_PREGNANCY_REQUESTED = "Reporting Pregnancy Requested";
    public static final String ACCOMACK_COUNTY_JAIL = "Accomack County Jail";
    public static final String ALBEMARLE_CHARLOTTESVILLE_REGIONAL_JAIL = "Albemarle/Charlottesville Regional Jail";
    public static final String ALEXANDRIA_CITY_JAIL = "Alexandria City Jail ";
    public static final String ALLEGHANY_COUNTY_JAIL = "Alleghany County Jail";
    public static final String ARLINGTON_COUNTY_JAIL = "Arlington County Jail";
    public static final String AUGUSTA_CORRECTIONAL_CENTER = "Augusta Correctional Center";
    public static final String BASKERVILLE_CORRECTIONAL_CENTER = "Baskerville Correctional Center";
    public static final String BEAUMONT_CORRECTIONAL_CENTER = "Beaumont Correctional Center";
    public static final String BLAND_CORRECTIONAL_CENTER = "Bland Correctional Center";
    public static final String BLUE_RIDGE_REGIONAL_JAIL_BRRJ_AMHERST = "Blue Ridge Regional Jail (BRRJ)- Amherst";
    public static final String BLUE_RIDGE_REGIONAL_JAIL_BRRJ_BEDFORD = "Blue Ridge Regional Jail (BRRJ)- Bedford";
    public static final String LUE_RIDGE_REGIONAL_JAIL_BRRJ_CAMPBELL = "lue Ridge Regional Jail (BRRJ)- Campbell";
    public static final String BLUE_RIDGE_REGIONAL_JAIL_BRRJ_HALIFAX = "Blue Ridge Regional Jail (BRRJ)- Halifax";
    public static final String BLUE_RIDGE_REGIONAL_JAIL_BRRJ_LYNCHBURG = "Blue Ridge Regional Jail (BRRJ)- Lynchburg";
    public static final String BOTETOURT_COUNTY_JAIL = "Botetourt County Jail ";
    public static final String BRISTOL_COUNTY_JAIL = "Bristol County Jail ";
    public static final String BUCKINGHAM_CORRECTIONAL_CENTER = "Buckingham Correctional Center";
    public static final String CAROLINE_CORRECTIONAL_UNIT = "Caroline Correctional Unit";
    public static final String CHARLOTTE_COUNTY_JAIL = "Charlotte County Jail ";
    public static final String CHESAPEAKE_CITY_JAIL = "Chesapeake City Jail ";
    public static final String CHESTERFIELD_COUNTY_JAIL = "Chesterfield County Jail";
    public static final String COFFEEWOOD_CORRECTIONAL_CENTER = "Coffeewood Correctional Center";
    public static final String CULPEPER_COUNTY_JAIL = "Culpeper County Jail ";
    public static final String DANVILLE_CITY_JAIL = "Danville City Jail";
    public static final String DANVILLE_CITY_JAIL_FARM = "Danville City Jail Farm";
    public static final String DEERFIELD_CORRECTIONAL_CENTER = "Deerfield Correctional Center";
    public static final String DILLWYN_CORRECTIONAL_CENTER = "Dillwyn Correctional Center";
    public static final String EASTERN_SHORE_REGIONAL_JAIL_NORTHAMPTON_COUNTY = "Eastern Shore Regional Jail (Northampton County)";
    public static final String FAIRFAX_COUNTY_JAIL = "Fairfax County Jail ";
    public static final String FAUQUIER_COUNTY_JAIL = "Fauquier County Jail";
    public static final String FLUVANNA_CORRECTIONAL_CENTER = "Fluvanna Correctional Center";
    public static final String FRANKLIN_COUNTY_JAIL = "Franklin County Jail";
    public static final String GLOUCESTER_COUNTY_JAIL = "Gloucester County Jail ";
    public static final String GREEN_ROCK_CORRECTIONAL_CENTER = "Green Rock Correctional Center";
    public static final String GREENSVILLE_CORRECTIONAL_CENTER = "Greensville Correctional Center";
    public static final String HALIFAX_CORRECTIONAL_UNIT = "Halifax Correctional Unit";
    public static final String HAMPTON_CITY_JAIL = "Hampton City Jail";
    public static final String HAMPTON_ROADS_REGIONAL_JAIL = "Hampton Roads Regional Jail";
    public static final String HAYNESVILLE_CORRECTIONAL_CENTER = "Haynesville Correctional Center";
    public static final String HENRICO_COUNTY_JAIL_EAST = "Henrico County Jail (East)";
    public static final String ENRICO_COUNTY_JAIL_WEST = "Henrico County Jail (West)";
    public static final String HENRY_COUNTY_JAIL = "Henry County Jail";
    public static final String INDIAN_CREEK_CORRECTIONAL_CENTER = "Indian Creek Correctional Center";
    public static final String KEEN_MOUNTAIN_CORRECTIONAL_CENTER = "Keen Mountain Correctional Center";
    public static final String LAWRENCEVILLE_CORRECTIONAL_CENTER = "Lawrenceville Correctional Center";
    public static final String LOUDOUN_COUNTY_JAIL = "Loudoun County Jail";
    public static final String LUNENBURG_CORRECTIONAL_CENTER = "Lunenburg Correctional Center";
    public static final String MARTINSVILLE_CITY_JAIL = "Martinsville City Jail";
    public static final String MEHERRIN_RIVER_REGIONAL_JAIL_ALBERTA = "Meherrin River Regional Jail (Alberta)";
    public static final String MEHERRIN_RIVER_REGIONAL_JAIL_BOYDTON = "Meherrin River Regional Jail (Boydton)";
    public static final String MIDDLE_PENINSULA_REGIONAL_SECURITY_CENTER = "Middle Peninsula Regional Security Center";
    public static final String MIDDLE_RIVER_REGIONAL_JAIL = "Middle River Regional Jail";
    public static final String MONTGOMERY_COUNTY_JAIL = "Montgomery County Jail";
    public static final String NEW_RIVER_VALLEY_REGIONAL_JAIL = "New River Valley Regional Jail";
    public static final String NEWPORT_NEWS_CITY_JAIL = "Newport News City Jail";
    public static final String NORFOLK_CITY_JAIL = "Norfolk City Jail";
    public static final String NORTHERN_NECK_REGIONAL_JAIL = "Northern Neck Regional Jail";
    public static final String NORTHWESTERN_REGIONAL_ADC = "Northwestern Regional ADC";
    public static final String NOTTOWAY_CORRECTIONAL_CENTER = "Nottoway Correctional Center";
    public static final String PAGE_COUNTY_JAIL = "Page County Jail";
    public static final String PAMUNKEY_REGIONAL_JAIL = "Pamunkey Regional Jail";
    public static final String PATRICK_COUNTY_JAIL = "Patrick County Jail";
    public static final String PATRICK_HENRY_CORRECTIONAL_UNIT = "Patrick Henry Correctional Unit";
    public static final String PIEDMONT_REGIONAL_JAIL = "Piedmont Regional Jail";
    public static final String PITTSYLVANIA_COUNTY_JAIL = "Pittsylvania County Jail";
    public static final String POCAHONTAS_STATE_CORRECTIONAL_CENTER = "Pocahontas State Correctional Center";
    public static final String PORTSMOUTH_CITY_JAIL = "Portsmouth City Jail";
    public static final String PRINCE_WILLIAM_MANASSAS_REGIONAL_JAIL = "Prince William/Manassas Regional Jail";
    public static final String RAPPAHANNOCK_REGIONAL_JAIL = "Rappahannock Regional Jail";
    public static final String RED_ONION_STATE_PRISON = "Red Onion State Prison";
    public static final String RICHMOND_CITY_JAIL = "Richmond City Jail";
    public static final String RIVER_NORTH_CORRECTIONAL_CENTER = "River North Correctional Center";
    public static final String RIVERSIDE_REGIONAL_JAIL = "Riverside Regional Jail";
    public static final String ROANOKE_CITY_JAIL = "Roanoke City Jail";
    public static final String ROANOKE_COUNTY_JAIL = "Roanoke County Jail";
    public static final String ROCKBRIDGE_REGIONAL_JAIL = "Rockbridge Regional Jail";
    public static final String ROCKINGHAM_HARRISONBURG_REGIONAL_JAIL = "Rockingham/Harrisonburg Regional Jail";
    public static final String RSW_REGIONAL_JAIL = "RSW Regional Jail";
    public static final String RUSTBURG_CORRECTIONAL_UNIT = "Rustburg Correctional Unit";
    public static final String SOUTHAMPTON_COUNTY_JAIL = "Southampton County Jail ";
    public static final String SOUTHSIDE_REGIONAL_JAIL = "Southside Regional Jail";
    public static final String SOUTHWEST_VIRGINIA_REGIONAL_JAIL_ABINGTON = "Southwest Virginia Regional Jail - Abington";
    public static final String SOUTHWEST_VIRGINIA_REGIONAL_JAIL_DUFFIELD = "Southwest Virginia Regional Jail - Duffield";
    public static final String SOUTHWEST_VIRGINIA_REGIONAL_JAIL_HAYSI = "Southwest Virginia Regional Jail - Haysi";
    public static final String SOUTHWEST_VIRGINIA_REGIONAL_JAIL_TAZEWELL = "Southwest Virginia Regional Jail - Tazewell";
    public static final String ST_BRIDES_CORRECTIONAL_CENTER = "St. Brides Correctional Center";
    public static final String STATE_FARM_CORRECTIONAL_CENTER = "State Farm Correctional Center";
    public static final String STATE_FARM_WORK_CENTER = "State Farm Work Center";
    public static final String SUSSEX_COUNTY_JAIL = "Sussex County Jail";
    public static final String SUSSEX_I_STATE_PRISON = "Sussex I State Prison";
    public static final String VIRGINIA_BEACH_CITY_JAIL = "Virginia Beach City Jail";
    public static final String VIRGINIA_CORRECTIONAL_CENTER_FOR_WOMEN = "Virginia Correctional Center for Women";
    public static final String VIRGINIA_PENINSULA_REGIONAL_JAIL = "Virginia Peninsula Regional Jail";
    public static final String WALLENS_RIDGE_STATE_PRISON = "Wallens Ridge State Prison";
    public static final String WESTERN_TIDEWATER_REGIONAL_JAIL = "Western Tidewater Regional Jail";
    public static final String WESTERN_VA_REGIONAL_JAIL = "Western VA Regional Jail";
    public static final String WISE_CORRECTIONAL_UNIT = "Wise Correctional Unit";


    //FACILITY NAME DD
    public static final String DEPARTMENT_OF_CORRECTIONS = "Department of Corrections";
    public static final String DEPARTMENT_OF_JUVENILE_JUSTICE = "Department of Juvenile Justice";
    public static final String LOCAL_JAIL = "Local Jail";
    public static final String REGIONAL_JAIL = "Regional Jail";
}
