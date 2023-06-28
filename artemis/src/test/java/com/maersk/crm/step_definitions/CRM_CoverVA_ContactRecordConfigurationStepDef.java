package com.maersk.crm.step_definitions;

import com.maersk.crm.pages.crm.*;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.CRMUtilities;
import com.maersk.crm.utilities.ApiBase;
import com.maersk.crm.pages.crm.CRMActiveContactPage;
import com.maersk.crm.pages.crm.CRMContactRecordUIPage;
import com.maersk.crm.utilities.Driver;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.core.exception.CucumberException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.*;

import static com.maersk.crm.utilities.BrowserUtils.waitFor;
import static org.testng.Assert.*;


public class CRM_CoverVA_ContactRecordConfigurationStepDef extends BrowserUtils {


    CRMContactRecordUIPage contactRecord = new CRMContactRecordUIPage();
    CRMActiveContactPage activeContact = new CRMActiveContactPage();
    CRMContactHistoryPage contactHistoryPage = new CRMContactHistoryPage();
    CRMContactRecordUIPage crmContactRecordUIPage = new CRMContactRecordUIPage();
    CRM_ViewContactRecordHistoryStepDef viewContactRecordHistory = new CRM_ViewContactRecordHistoryStepDef();

    Actions actions = new Actions(Driver.getDriver());


    @When("I will see CoverVA {string} with options")
    public void i_will_see_CoverVA_with_options(String drop, List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Appeals Escalation – English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Appeals Escalation – Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound – CVIU – English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound – CVIU – Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Apply – English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Apply – Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Existing – English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Existing – Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Status/Changes – English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Status/Changes – Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Supervisor/Escalation – CVCC – English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Supervisor/Escalation – CVCC – Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Supervisor/Escalation – CVIU – English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Supervisor/Escalation – CVIU – Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else {
                throw new CucumberException(drop + "option " + opt + " Not found");
            }
        }
    }

    @Then("Verify Cover VA Call Campaign has values")
    public void verify_Cover_VA_Call_Campaign_has_values(List<String> call) {
        for (String opt : call) {
            if (opt.equalsIgnoreCase("Appeals Voicemail - English")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Appeals Voicemail - Spanish")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("DMI")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Outbound Calls - CVCC - English")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Outbound Calls - CVCC - Spanish")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Outbound Calls - CVIU - English")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Outbound Calls - CVIU - Spanish")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Voicemail - CVCC - English")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Voicemail - CVCC - Spanish")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Voicemail - CVIU - English")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Voicemail - CVIU - Spanish")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else {
                throw new CucumberException("Call Campaign Option " + opt + " Not found");
            }
        }
    }

    @Then("I will see Cover VA Channel field with the options")
    public void i_will_see_Cover_VA_Channel_field_with_the_options(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Phone")) {
                selectDropDown(activeContact.channelField, opt);
            } else if (opt.equalsIgnoreCase("Web Chat")) {
                selectDropDown(activeContact.channelField, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @When("I will verify Cover VA PROGRAM options {string}")
    public void i_will_verify_Cover_VA_PROGRAM_options(String options) {
        selectDropDown(activeContact.programs, options);
    }


    @When("I verify Contact Reason for Cover VA {string} and associated Contact Action")
    public void i_verify_Contact_Reason_for_Cover_VA_and_associated_Contact_Action(String opt) {
        if (opt.equalsIgnoreCase("Appeal")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Conducted Pre-Hearing Conference");
            activeContact.createdPreHearingConferenceTask.click();
            activeContact.createdAppealsServiceRequest.click();
            activeContact.providedAppealsStatus.click();
            activeContact.withdrewAppeal.click();
            activeContact.escalatedToSuper.click();
            activeContact.appealsSummary.click();
        } else if (opt.equalsIgnoreCase("Application/Renewal Status - CVCC")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to CPU");
            activeContact.escalatedToLocality.click();
            activeContact.escalatedToSuper.click();
            activeContact.noActionTakenUnableToAuthenticate.click();
            activeContact.providedApplicationStatus.click();
            activeContact.providedRenewalStatus.click();
            activeContact.systemIssuesReadVaCMSDownScript.click();
        } else if (opt.equalsIgnoreCase("Application/Renewal Status - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to CPU");
            activeContact.escalatedToCVIUEligibility.click();
            activeContact.escalatedToSuper.click();
            activeContact.noActionTakenUnableToAuthenticate.click();
            activeContact.providedApplicationStatus.click();
            activeContact.providedRenewalStatus.click();
            activeContact.systemIssuesReadVaCMSDownScript.click();
        } else if (opt.equalsIgnoreCase("Callback (Outbound)")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");
            activeContact.noActionDidNotReachConsumer.click();
            activeContact.resolvedInquiry.click();
            activeContact.unabletoResolveInquiry.click();
        } else if (opt.equalsIgnoreCase("Change Request")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to CPU");
            activeContact.adressChange.click();
            activeContact.escalatedToSuper.click();
            activeContact.noActionTakenUnableToAuthenticate.click();
            activeContact.reportMyChange.click();
            activeContact.systemIssuesReadVaCMSDownScript.click();
        } else if (opt.equalsIgnoreCase("Complaint")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to DMAS");
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("Referred to LDSS").click();
            activeContact.action("Referred to MCO").click();
            activeContact.action("Referred to Recipient Helpline").click();
            activeContact.resolved.click();
            activeContact.submittedForProcessing.click();
            activeContact.withdrewComplaint.click();
        } else if (opt.equalsIgnoreCase("Correspondence")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "CVIU Communication Form");
            activeContact.escalatedToSuper.click();
            activeContact.extendedPendLetter.click();
            activeContact.generatedRequestedForms.click();
            activeContact.LDSSCommunicationForm.click();
            activeContact.referredToCovervaorgForSelfPrint.click();
            activeContact.ReGeneratedVCL.click();
            activeContact.ReGeneratedNOA.click();
        } else if (opt.equalsIgnoreCase("FAMIS Member Services")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Answered General Inquiry");
            activeContact.escalatedToSuper.click();
            activeContact.FAMISSelectInquiryReferredtoCoverVAWebsite.click();
            activeContact.FAMISSelectInquiryReferredtoDMASUnit.click();
            activeContact.provided1095BInfo.click();
            activeContact.providedBenefitsStatus.click();
            activeContact.providedCoverageInformation.click();
            activeContact.providedDentalInfo.click();
            activeContact.providedEnrollmentStatus.click();
            activeContact.providedInstructionsonHowtoCancelFAMISCoverage.click();
            activeContact.providedParticipatingProviders.click();
            activeContact.referredToMCO.click();
            activeContact.referredtoRecipientHelpLine.click();
            activeContact.regenerated1095BInfo.click();
        } else if (opt.equalsIgnoreCase("General Inquiry")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");
            activeContact.providedLDSSOfficeInfo.click();
            activeContact.providedProgramInfo.click();
            activeContact.transferredCCCPlusHelp.click();
            activeContact.transferredtoEnterCallCenter.click();
            activeContact.transferredtoMCO.click();
            activeContact.transferredtoMCOHelp.click();
            activeContact.transferredtoMedicare.click();
            activeContact.transferredtoProviderHelp.click();
            activeContact.transferredtoRecipientHelp.click();
        } else if (opt.equalsIgnoreCase("ID Card Request")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");
            activeContact.generatedNewMedicaidIDCardinMMIS.click();
            activeContact.referredToMCO.click();
        } else if (opt.equalsIgnoreCase("Medicaid Member Services")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Answered General Inquiry");
            activeContact.escalatedToSuper.click();
            activeContact.providedBenefitStatus.click();
            activeContact.providedCoverageInformation.click();
            activeContact.providedEnrollmentStatus.click();
            activeContact.providedInstructionsonHowtoCancelMedicaidCoverage.click();
            activeContact.providedParticipatingProviders.click();
            activeContact.referredToMCO.click();
            activeContact.referredtoRecipientHelpLine.click();
        } else if (opt.equalsIgnoreCase("Member Follow-Up (Outbound)")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Requested Required Information");
            activeContact.receivedRequiredInformation.click();
            activeContact.escalatedToSuper.click();
            activeContact.noActionDidNotReachConsumer.click();
        } else if (opt.equalsIgnoreCase("New Application - CVCC")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Application Incomplete");
            activeContact.completedTelephonicApplication.click();
            activeContact.createdProcessApplicationServiceRequest.click();
            activeContact.escalatedtoCPU.click();
            activeContact.escalatedToLocality.click();
            activeContact.escalatedToSuper.click();
            activeContact.referredtoEnterpriseCallCenter.click();
            activeContact.systemIssuesReadVaCMSDownScript.click();
            activeContact.updatedApplication.click();
            activeContact.withdrewApplication.click();
        } else if (opt.equalsIgnoreCase("New Application - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Application Incomplete");
            activeContact.completedTelephonicApplication.click();
            activeContact.createdProcessApplicationServiceRequest.click();
            activeContact.escalatedtoCVIUEligibility.click();
            activeContact.escalatedToSuper.click();
            activeContact.reEntry.click();
            activeContact.systemIssuesReadVaCMSDownScript.click();
            activeContact.updatedApplication.click();
            activeContact.withdrewApplication.click();
        } else if (opt.equalsIgnoreCase("Newborn Notification")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");
        } else if (opt.equalsIgnoreCase("Pre-Hearing Conference (Inbound/Outbound)")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Completed PHC - Resolved");
            activeContact.CompletedPHCReferredtoDMASAppeals.click();
            activeContact.escalatedToSuper.click();
            activeContact.UnabletoReachScheduledOutboundCall.click();
        } else if (opt.equalsIgnoreCase("Renewal - CVCC")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Completed Telephonic Renewal");
            activeContact.escalatedtoCPU.click();
            activeContact.escalatedToLocality.click();
            activeContact.escalatedToSuper.click();
            activeContact.noActionTakenUnableToAuthenticate.click();
            activeContact.renewalIncomplete.click();
            activeContact.systemIssuesReadVaCMSDownScript.click();
            activeContact.action("Provided Renewal Information").click();
        } else if (opt.equalsIgnoreCase("Renewal - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Completed Telephonic Renewal");
            activeContact.escalatedtoCPU.click();
            activeContact.escalatedToLocality.click();
            activeContact.escalatedToSuper.click();
            activeContact.noActionTakenUnableToAuthenticate.click();
            activeContact.renewalIncomplete.click();
            activeContact.systemIssuesReadVaCMSDownScript.click();
            activeContact.action("Provided Renewal Information").click();
        } else if (opt.equalsIgnoreCase("Silent/No Consumer")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "No Audio - Disconnected");
            activeContact.noResponseDisconnected.click();
            activeContact.scheduledCallBack.click();
            activeContact.escalatedToSuper.click();
        } else if (opt.equalsIgnoreCase("Verification Information")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to CPU");
            activeContact.escalatedToLocality.click();
            activeContact.escalatedToSuper.click();
            activeContact.noActionTakenUnableToAuthenticate.click();
            activeContact.systemIssuesReadVaCMSDownScript.click();
            activeContact.updatedContactInformation.click();
            activeContact.updatedDemographicInformation.click();
            activeContact.updatedHouseholdInformation.click();
            activeContact.updatedIncomeInformation.click();
        } else if (opt.equalsIgnoreCase("Expedited Applications - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Application Incomplete");
            activeContact.action("Completed Telephonic Application").click();
            activeContact.action("Created Process Application Service Request").click();
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("Escalated to CVIU Eligibility").click();
            activeContact.action("Re-Entry").click();
            activeContact.action("System Issues - Read VaCMS Down Script").click();
            activeContact.action("Updated Application").click();
            activeContact.action("Withdrew Application").click();
        } else if (opt.equalsIgnoreCase("Pre-Release Application - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Application Incomplete");
            activeContact.action("Completed Telephonic Application").click();
            activeContact.action("Created Process Application Service Request").click();
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("Escalated to CVIU Eligibility").click();
            activeContact.action("Re-Entry").click();
            activeContact.action("System Issues - Read VaCMS Down Script").click();
            activeContact.action("Updated Application").click();
            activeContact.action("Withdrew Application").click();
        } else if (opt.equalsIgnoreCase("Re-Entry Application - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Application Incomplete");
            activeContact.action("Completed Telephonic Application").click();
            activeContact.action("Created Process Application Service Request").click();
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("Escalated to CVIU Eligibility").click();
            activeContact.action("Re-Entry").click();
            activeContact.action("System Issues - Read VaCMS Down Script").click();
            activeContact.action("Updated Application").click();
            activeContact.action("Withdrew Application").click();
        } else if (opt.equalsIgnoreCase("Emergency Services - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Application Incomplete");
            activeContact.action("Completed Telephonic Application").click();
            activeContact.action("Created Process Application Service Request").click();
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("Escalated to CVIU Eligibility").click();
            activeContact.action("Re-Entry").click();
            activeContact.action("System Issues - Read VaCMS Down Script").click();
            activeContact.action("Updated Application").click();
            activeContact.action("Withdrew Application").click();
        } else if (opt.equalsIgnoreCase("Callback (Outbound) - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");
            activeContact.action("No Action - Did Not Reach Consumer").click();
            activeContact.action("Resolved Inquiry").click();
            activeContact.action("Unable to Resolve Inquiry").click();
        } else if (opt.equalsIgnoreCase("Change Request - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to CPU");
            activeContact.action("Escalated to Locality").click();
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("No Action Taken - Unable to Authenticate").click();
            activeContact.action("Report My Change").click();
            activeContact.action("System Issues - Read VaCMS Down Script").click();
        } else if (opt.equalsIgnoreCase("Complaint - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to DMAS");
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("Referred to LDSS").click();
            activeContact.action("Referred to MCO").click();
            activeContact.action("Referred to Recipient Helpline").click();
            activeContact.action("Resolved").click();
            activeContact.action("Submitted for Processing").click();
            activeContact.action("Withdrew Complaint").click();
        } else if (opt.equalsIgnoreCase("Correspondence - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "CVIU Communication Form");
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("Extended Pend Letter").click();
            activeContact.action("Generated Requested Form(s)").click();
            activeContact.action("LDSS Communication Form").click();
            activeContact.action("Re-Generated NOA").click();
            activeContact.action("Re-Generated VCL").click();
            activeContact.action("Referred to CoverVA.org for Self Print").click();
        } else if (opt.equalsIgnoreCase("General Inquiry - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");
            activeContact.action("Provided LDSS Office Information").click();
            activeContact.action("Provided Program Information").click();
            activeContact.action("Transferred to External Entity").click();

        } else if (opt.equalsIgnoreCase("ID Card Request - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");
            activeContact.action("Generated New Medicaid ID Card in MMIS").click();
            activeContact.action("Referred to MCO").click();

        } else if (opt.equalsIgnoreCase("Medicaid Member Services - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Answered General Inquiry");
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("Provided Benefit Status").click();
            activeContact.action("Provided Coverage Information").click();
            activeContact.action("Provided Enrollment Status").click();
            activeContact.action("Provided Instructions on How to Cancel Medicaid Coverage").click();
            activeContact.action("Provided Participating Providers").click();
            activeContact.action("Referred to MCO").click();
            activeContact.action("Referred to Recipient Help Line").click();
        } else if (opt.equalsIgnoreCase("Newborn Notification - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");

        } else if (opt.equalsIgnoreCase("Silent/No Consumer - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");
            activeContact.action("No Audio - Disconnected").click();
            activeContact.action("No Response - Disconnected").click();
            activeContact.action("Scheduled Callback").click();

        } else if (opt.equalsIgnoreCase("Verification Information - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to CPU");
            activeContact.action("Escalated to CVIU").click();
            activeContact.action("Escalated to Locality").click();
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("No Action Taken - Unable to Authenticate").click();
            activeContact.action("System Issues - Read VaCMS Down Script").click();
            activeContact.action("Updated Contact Information").click();
            activeContact.action("Updated Demographic Information").click();
            activeContact.action("Updated Household Information").click();
            activeContact.action("Updated Income Information").click();
        } else if (opt.equalsIgnoreCase("HIPAA Violation - CVIU")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVIU");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");
            activeContact.action("Referred to FFM").click();
            activeContact.action("Referred to LDSS").click();
            activeContact.action("Referred to MCO").click();
        } else if (opt.equalsIgnoreCase("HIPAA Violation - CVCC")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Escalated to Supervisor");
            activeContact.action("Referred to FFM").click();
            activeContact.action("Referred to LDSS").click();
            activeContact.action("Referred to MCO").click();
        } else if (opt.equalsIgnoreCase("Emergency Services - CVCC")) {
            selectDropDown(activeContact.businessUnitDropdwn, "CVCC");
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Application Incomplete");
            activeContact.action("Complete Telephonic Application").click();
            activeContact.action("Created Process Application").click();
            activeContact.action("Escalated to CPU Eligibility").click();
            activeContact.action("Escalated to Supervisor").click();
            activeContact.action("Re-Entry").click();
            activeContact.action("System Issues - Read VaCMS Down Script").click();
            activeContact.action("Updated Application").click();
            activeContact.action("Withdrew Application").click();
        }
    }

    @When("I verify Cover VA Unidentified Preferred Language drop down with options below:")
    public void i_verify_Cover_VA_Unidentified_Preferred_Language_drop_down_with_options_below(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Amharic")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Arabic")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Bassa")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Bengali")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Chinese (Traditional)")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("English")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Farsi")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("French")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("German")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Hindi")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Ibo")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Korean")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Russian")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Spanish")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Tagalog")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Urdu")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Vietnamese")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Yoruba")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Other")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @When("I collapse widget to display reason\\/action")
    public void i_collapse_widget_to_display_reason_action() {
        activeContact.collapseExpandWidget.click();

    }


    @When("I will see CoverVA General & Third Party {string} with options")
    public void i_will_see_CoverVA_General_Third_Party_with_options(String drop, List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Appeals Escalation - English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Appeals Escalation - Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound - CVIU - English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound - CVIU - Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Apply - English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Apply - Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Existing - English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Existing - Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Status/Changes - English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Inbound Status/Changes - Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Supervisor/Escalation - CVCC - English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Supervisor/Escalation - CVCC - Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Supervisor/Escalation - CVIU - English")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else if (opt.equalsIgnoreCase("Supervisor/Escalation - CVIU - Spanish")) {
                selectDropDown(activeContact.inboundCallQueue, opt);
            } else {
                throw new CucumberException(drop + "option " + opt + " Not found");
            }
        }
    }

    @Then("Verify Cover VA General & Third Party Call Campaign has values")
    public void verify_Cover_VA_General_Third_Party_Call_Campaign_has_values(List<String> call) {
        for (String opt : call) {
            if (opt.equalsIgnoreCase("Appeals Voicemail - English")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Appeals Voicemail - Spanish")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("DMI")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Outbound Calls - CVCC - English")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Outbound Calls - CVCC - Spanish")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Outbound Calls - CVIU - English")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Outbound Calls - CVIU - Spanish")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Voicemail - CVCC - English")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Voicemail - CVCC - Spanish")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Voicemail - CVIU - English")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else if (opt.equalsIgnoreCase("Voicemail - CVIU - Spanish")) {
                selectDropDown(activeContact.contactCallCampaign, opt);
            } else {
                throw new CucumberException("Call Campaign Option " + opt + " Not found");
            }
        }
    }

    @When("I will verify Cover VA General & Third Party PROGRAM options {string}")
    public void i_will_verify_Cover_VA_General_Third_Party_PROGRAM_options(String options) {
        selectDropDown(activeContact.programs, options);
    }

    @Then("Verify consumer Type field for Cover VA with the options")
    public void verify_consumer_Type_field_for_Cover_VA_with_the_options(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("Agency")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Assister")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Authorized Representative")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("DOC")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Hospital")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Juvenile Justice")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("LDSS")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Media")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Navigator")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Provider")) {
                selectDropDown(activeContact.consumerType, opt);
            } else if (opt.equalsIgnoreCase("Regional/Local Jails")) {
                selectDropDown(activeContact.consumerType, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @Then("I verify Preferred Language drop down for Cover VA with options below:")
    public void i_verify_Preferred_Language_drop_down_for_Cover_VA_with_options_below(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("English")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Spanish")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Korean")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Vietnamese")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Chinese (Traditional)")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Tagalog")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Amharic")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("French")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Russian")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Arabic")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Urdu")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Hindi")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Farsi")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Bengali")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("German")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Bassa")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Ibo")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Yoruba")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Other")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else if (opt.equalsIgnoreCase("Burmese")) {
                selectDropDown(activeContact.preferredLanguageCode, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @When("I verify Cover VA Unidentified Caller Type drop down with options below:")
    public void i_verify_Cover_VA_Unidentified_Caller_Type_drop_down_with_options_below(List<String> options) {
        for (String opt : options) {
            if (opt.equalsIgnoreCase("VA Citizen")) {
                selectDropDown(activeContact.callerType, opt);
            } else if (opt.equalsIgnoreCase("Non-Citizen")) {
                selectDropDown(activeContact.callerType, opt);
            } else if (opt.equalsIgnoreCase("Anonymous")) {
                selectDropDown(activeContact.callerType, opt);
            } else {
                throw new CucumberException("Option " + opt + " Not found");
            }
        }
    }

    @Then("I verify Case ID and Consumer Role column headers are not displayed for consumer")
    public void i_verify_Case_ID_and_Consumer_Role_column_headers_are_not_displayed_for_consumer() {
        waitFor(1);
        assertFalse(activeContact.caseId.isDisplayed());
        waitFor(1);
        assertFalse(activeContact.consumerRole.isDisplayed());
    }

    @When("I verify Contact Reason for Cover VA are associated with Business Unit {string}")
    public void i_verify_Contact_Reason_for_Cover_VA_are_associated_with_Business_Unit(String businessUnit, List<String> reasons) {
        selectDropDown(activeContact.businessUnitDropdwn, businessUnit);
        activeContact.contactRes.click();
        waitFor(2);
        for (int i = 0; i < reasons.size(); i++) {
            assertEquals(reasons.get(i), activeContact.contactReasons.get(i).getText(), reasons.get(i) + " Not Found or Not in the order");
        }
    }


    @When("I verify Contact Reason for Cover VA {string} and associated Contact Action for Business Unit {string}")
    public void i_verify_Contact_Reason_for_Cover_VA_and_associated_Contact_Action_for_Business_Unit(String opt, String businessUnit) {
        selectDropDown(activeContact.businessUnitDropdwn, businessUnit);
        if (opt.equalsIgnoreCase("Appeals")) {
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Disconnected");
            activeContact.action("Escalated").click();
            activeContact.action("Incomplete").click();
            activeContact.action("Resolved").click();
            activeContact.action("Escalated").click();
            activeContact.action("Unresolved").click();
        } else if (opt.equalsIgnoreCase("Eligibility")) {
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Disconnected");
            activeContact.action("Escalated").click();
            activeContact.action("Incomplete").click();
            activeContact.action("Resolved").click();
            activeContact.action("Escalated").click();
            activeContact.action("Unresolved").click();
        } else if (opt.equalsIgnoreCase("Medicaid")) {
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Disconnected");
            activeContact.action("Escalated").click();
            activeContact.action("Incomplete").click();
            activeContact.action("Resolved").click();
            activeContact.action("Escalated").click();
            activeContact.action("Unresolved").click();
        } else if (opt.equalsIgnoreCase("Member Information")) {
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Disconnected");
            activeContact.action("Escalated").click();
            activeContact.action("Incomplete").click();
            activeContact.action("Resolved").click();
            activeContact.action("Escalated").click();
            activeContact.action("Unresolved").click();
        } else if (opt.equalsIgnoreCase("New/Existing Applications")) {
            selectDropDown(activeContact.contactRes, opt);
            selectDropDown(activeContact.contactAct, "Disconnected");
            activeContact.action("Escalated").click();
            activeContact.action("Incomplete").click();
            activeContact.action("Resolved").click();
            activeContact.action("Escalated").click();
            activeContact.action("Unresolved").click();
        }

    }

    @When("I select channel {string}")
    public void i_select_channel(String option) {
        selectDropDown(activeContact.channelField, option);
    }

    @When("I verify there is NO {string} business unit")
    public void i_verify_there_is_NO_business_unit(String webChat) {
        activeContact.businessUnitDropdwn.click();
        assertTrue(!isElementDisplayed(activeContact.businessUnitWebChat));
    }
    @When("I select channel type as web chat")
    public void i_select_channel_type_as_webchat() {
        selectDropDown(contactRecord.contactChannelType, "Web Chat");
        //selectDropDown(crmContactRecordUIPage.contactChannelType, "Web Chat");
    }

    @And("And I click on {string} in CoverVa active contact page")
    public void andIClickOnInCoverVaActiveContactPage(String type) {
        waitFor(1);
        switch (type) {
            case "BUSINESS UNIT":
                activeContact.businessUnitDropdwn.click();
                break;
            case "CONTACT REASON":
                contactHistoryPage.lstEditContactReason.click();
                break;
            case "CONTACT ACTION":
                contactHistoryPage.expendcontactActionDropDown.click();
                break;
            default:
                fail("Type did not match case");
        }
    }

    @Then("I verify the following values are displayed as options in CoverVA Business Unit")
    public void iVerifyTheFollowingValuesAreDisplayedAsOptionsInCoverVABusinessUnit(List<String> data) {
        waitFor(2);
        List<WebElement> bUnitWebEleList = Driver.getDriver().findElements(By.xpath("//div[@id='menu-businessUnitSelect']/div/following-sibling::div/following-sibling::div/ul/li"));
        List<String> bUnitValueTextList = new ArrayList<>();
        bUnitWebEleList.forEach(each -> bUnitValueTextList.add(each.getText()));
        assertTrue(bUnitValueTextList.containsAll(data), "Business Unit dropdown did not contain expected CVCC-PW  CVCC-Nav/Asst Value. Actual List: " + bUnitValueTextList);
    }

    @And("And I click on {string} in CoverVa active contact page and select the following values")
    public void andIClickOnInCoverVaActiveContactPageAndSelectTheFollowingValues(String type, List<String> data) {
        waitFor(1);
        switch (type) {
            case "CONTACT REASON":
                contactHistoryPage.lstEditContactReason.click();
                waitFor(2);
                if ("FAMIS Member Services".equals(data.get(0)) || "Medicaid Member Services".equals(data.get(0))) {
                    WebElement famisMedicaidOption = Driver.getDriver().findElement(By.xpath("//li[contains(text(), '" + data.get(0) + "')]"));
                    actions.moveToElement(famisMedicaidOption).click(famisMedicaidOption).build().perform();
                } else if ("New Application Pre-Hearing Conference (Inbound/Outbound)".equals(data.get(0))) {
                    WebElement newAppPreHearing = Driver.getDriver().findElement(By.xpath("//li[contains(text(),'New Application Pre-Hearing Conference')]"));
                    actions.moveToElement(newAppPreHearing).click(newAppPreHearing).build().perform();
                }else if ("Renewal Pre-Hearing Conference (Inbound/Outbound)".equals(data.get(0))) {
                    WebElement newAppPreHearing = Driver.getDriver().findElement(By.xpath("//li[contains(text(),'Renewal Pre-Hearing Conference')]"));
                    actions.moveToElement(newAppPreHearing).click(newAppPreHearing).build().perform();
                } else {
                    WebElement dataOption = Driver.getDriver().findElement(By.xpath("//li[contains(text(),'"+data.get(0)+"')]"));
                    actions.moveToElement(dataOption).click(dataOption).build().perform();
                }
                break;
            case "CONTACT ACTION":
                contactHistoryPage.expendcontactActionDropDown.click();
                break;
            default:
                fail("Type did not match case");
        }
        waitFor(1);
    }

    @Then("I verify {string} is {string} as a value in the Contact Action dropdown list")
    public void iVerifyIsAsAValueInTheContactActionDropdownList(String value, String type) {
        waitFor(1);
        contactHistoryPage.expendcontactActionDropDown.click();
        switch (type) {
            case "NOT DISPLAYED":
                List<String> bUnitValue = new ArrayList<>();
                activeContact.contactActionValuesList.forEach(each -> bUnitValue.add(each.getText()));
                assertFalse(bUnitValue.contains(value), "Business Unit dropdown contains : " + value + ". Actual List: " + bUnitValue);
                break;
            case "DISPLAYED":
                List<String> bUnitValueTwo = new ArrayList<>();
                activeContact.contactActionValuesList.forEach(each -> bUnitValueTwo.add(each.getText()));
                assertTrue(bUnitValueTwo.contains(value), "Business Unit dropdown contains : " + value + ". Actual List: " + bUnitValueTwo);
                break;
        }
        Driver.getDriver().findElement(By.xpath("//body")).click();
        waitFor(1);
    }

    @Then("I verify {string} is {string} as a value in the Contact Reason dropdown list")
    public void iVerifyIsAsAValueInTheContactReasonDropdownList(String value, String type) {
        waitFor(1);
        contactHistoryPage.lstEditContactReason.click();
        switch (type) {
            case "NOT DISPLAYED":
                List<String> actualReasonsList = new ArrayList<>();
                activeContact.contactReasonValuesList.forEach(each -> actualReasonsList.add(each.getText()));
                assertFalse(actualReasonsList.contains(value), "Business Unit dropdown contains : " + value + ". Actual List: " + actualReasonsList);
                break;
            case "DISPLAYED":
                List<String> actualReasonsListTwo = new ArrayList<>();
                activeContact.contactReasonValuesList.forEach(each -> actualReasonsListTwo.add(each.getText()));
                if ("Application Status CPU and Renewal Status CPU".equals(value)) {
                    List<String> duoExpectedValues = new ArrayList<>(Arrays.asList("Application Status - CPU", "Renewal Status - CPU"));
                    assertTrue(actualReasonsListTwo.containsAll(duoExpectedValues), "Business Unit dropdown did not contain : " + value + ". Actual List: " + actualReasonsListTwo);
                } else {
                    assertTrue(actualReasonsListTwo.contains(value), "Business Unit dropdown did not contain : " + value + ". Actual List: " + actualReasonsListTwo);
                }
                break;
        }
        Driver.getDriver().findElement(By.xpath("//body")).click();
        waitFor(1);
    }

    @Then("I verify {string} Business Unit with Contact Reason {string} with the following Action values")
    public void iVerifyBusinessUnitWithContactReasonWithTheFollowingActionValues(String bUnit, String reasonValue, List<String> data) {
        switch (bUnit) {
            case "CPU":
                viewContactRecordHistory.iSelectBusinessUnitForCoverVA("CPU");
                List<String> reason = new ArrayList<>();
                reason.add(reasonValue);
                andIClickOnInCoverVaActiveContactPageAndSelectTheFollowingValues("CONTACT REASON", reason);
                viewContactRecordHistory.verify_contact_action_values(data);
                break;
            case "CVCC":
                viewContactRecordHistory.iSelectBusinessUnitForCoverVA("CVCC");
                List<String> reasonTwo = new ArrayList<>();
                reasonTwo.add(reasonValue);
                andIClickOnInCoverVaActiveContactPageAndSelectTheFollowingValues("CONTACT REASON", reasonTwo);
                viewContactRecordHistory.verify_contact_action_values(data);
                break;
            case "CVIU-DJJ CVIU-DOC CVIU-Jails and CVIU-Other":
                List<String> buisnessUnitList = new ArrayList<>(Arrays.asList("CVIU - DJJ", "CVIU - DOC", "CVIU - Jails", "CVIU - Other"));
                for (String each : buisnessUnitList) {
                    viewContactRecordHistory.iSelectBusinessUnitForCoverVA(each);
                    List<String> reasonThree = new ArrayList<>();
                    reasonThree.add(reasonValue);
                    andIClickOnInCoverVaActiveContactPageAndSelectTheFollowingValues("CONTACT REASON", reasonThree);
                    viewContactRecordHistory.verify_contact_action_values(data);
                }
                break;
            case "ALL":
                List<String> allBuisnessUnitList = new ArrayList<>(Arrays.asList("CPU", "CVCC", "CVCC-Nav/Asst", "CVCC-PW", "CVIU - DJJ", "CVIU - DOC", "CVIU - Jails", "CVIU - Other"));
                for (String each : allBuisnessUnitList) {
                    viewContactRecordHistory.iSelectBusinessUnitForCoverVA(each);
                    List<String> reasonFour = new ArrayList<>();
                    reasonFour.add(reasonValue);
                    andIClickOnInCoverVaActiveContactPageAndSelectTheFollowingValues("CONTACT REASON", reasonFour);
                    viewContactRecordHistory.verify_contact_action_values(data);
                }
                break;
            case "CVCC CVCC-PW CVCC-Nav/Asst":
                List<String> cvccBusinessUnit = new ArrayList<>(Arrays.asList("CVCC", "CVCC-Nav/Asst", "CVCC-PW"));
                for (String each : cvccBusinessUnit) {
                    viewContactRecordHistory.iSelectBusinessUnitForCoverVA(each);
                    List<String> reasonFour = new ArrayList<>();
                    reasonFour.add(reasonValue);
                    andIClickOnInCoverVaActiveContactPageAndSelectTheFollowingValues("CONTACT REASON", reasonFour);
                    viewContactRecordHistory.verify_contact_action_values(data);
                }
                break;
            default:
                fail("Mismatch in provided switch key");
        }
    }

    @Then("I verify for {string} CoverVa business unit Contact Reason does not display {string}")
    public void iVerifyForCoverVaBusinessUnitContactReasonDoesNotDisplay(String reasonType, String reasonValue) {
        if ("ALL".equals(reasonType)) {
            List<String> allBusinessUnitList = new ArrayList<>(Arrays.asList("CPU", "CVCC", "CVCC-Nav/Asst", "CVCC-PW", "CVIU - DJJ", "CVIU - DOC", "CVIU - Jails", "CVIU - Other"));
            for (String each : allBusinessUnitList) {
                actions.sendKeys(Keys.ESCAPE).build().perform();
                selectDropDown(activeContact.businessUnitDropdwn, each);
                iVerifyIsAsAValueInTheContactReasonDropdownList(reasonValue,"NOT DISPLAYED");
            }
        }else if("CVCC CVCC-PW and CVCC-Nav/Asst".equals(reasonType)){
            List<String> cvccBusinessUnit = new ArrayList<>(Arrays.asList("CVCC", "CVCC-Nav/Asst", "CVCC-PW"));
            for (String each : cvccBusinessUnit) {
                actions.sendKeys(Keys.ESCAPE).build().perform();
                selectDropDown(activeContact.businessUnitDropdwn, each);
                iVerifyIsAsAValueInTheContactReasonDropdownList(reasonValue,"NOT DISPLAYED");
            }
        }else {
            fail("Provided Reason did not match");
        }
    }

}
