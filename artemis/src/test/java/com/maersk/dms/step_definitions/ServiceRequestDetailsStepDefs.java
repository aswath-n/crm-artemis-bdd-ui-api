package com.maersk.dms.step_definitions;

import com.github.javafaker.Faker;
import com.maersk.crm.utilities.BrowserUtils;
import com.maersk.crm.utilities.World;
import com.maersk.dms.pages.ServiceRequestDetailsPage;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ServiceRequestDetailsStepDefs {
    final ThreadLocal<Faker> faker = ThreadLocal.withInitial(Faker::new);
    final ThreadLocal<Random> random = ThreadLocal.withInitial(Random::new);
    final ThreadLocal<BrowserUtils> browserUtils= ThreadLocal.withInitial(BrowserUtils::new);
    ServiceRequestDetailsPage serviceRequestPage = new ServiceRequestDetailsPage();

    public void fillOutServiceRequestWithRandomData(Map<String, String> dataTable) {
        List<String> listOfPlans = Arrays.asList("Anthem", "CareSource", "MDWise", "Managed Health Services (MHS)", "United Healthcare");
        List<String> listOfPrograms = Arrays.asList("Healthy Indiana Plan", "Hoosier Care Connect", "Hoosier Healthwise");
        for (String field : dataTable.keySet()) {
            BrowserUtils.waitFor(1);
            switch (field) {
                case "rid":
                    String rid = 7 + browserUtils.get().getRandomNumber(11);
                    serviceRequestPage.rid.sendKeys(rid);
                    World.save.get().put("rid", rid);
                    break;
                case "grievanceNumber":
                    String grievanceNumber = 8 + browserUtils.get().getRandomNumber(9);
                    serviceRequestPage.grievance.sendKeys(grievanceNumber);
                    World.save.get().put("grievanceNumber", grievanceNumber);
                    break;
                case "grievanceDate":
                    String grievanceDate = BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().minusDays(random.get().nextInt(10)).toString());
                    serviceRequestPage.dateReceivedGrievance.sendKeys(grievanceDate);
                    World.save.get().put("grievanceDate", grievanceDate);
                    break;
                case "contactName":
                    String firstName = faker.get().name().firstName();
                    String lastName = faker.get().name().lastName();
                    World.save.get().put("contactFirstName", firstName);
                    World.save.get().put("contactLastName", lastName);
                    serviceRequestPage.contactName.sendKeys(firstName + " " + lastName);
                    break;
                case "contactPhone":
                    String phoneNumber = 7 + browserUtils.get().getRandomNumber(9);
                    serviceRequestPage.contactPhone.sendKeys(phoneNumber);
                    World.save.get().put("contactPhone", phoneNumber);
                    break;
                case "contactEmail":
                    String email = faker.get().internet().emailAddress();
                    serviceRequestPage.contactEmail.sendKeys(email);
                    World.save.get().put("contactEmail", email);
                    break;
                case "program":
                    String program = listOfPrograms.get(random.get().nextInt(listOfPrograms.size()));
                    browserUtils.get().selectDropDown(serviceRequestPage.programDropdown, program);
                    World.save.get().put("program", program);
                    break;
                case "currentPlanName":
                    browserUtils.get().selectDropDown(serviceRequestPage.currentPlanDropdown, listOfPlans.get(random.get().nextInt(listOfPlans.size())));
                    World.save.get().put("currentPlanName", serviceRequestPage.currentPlanSelectedValue.getAttribute("value"));
                    break;
                case "desiredPlanName":
                    browserUtils.get().selectDropDown(serviceRequestPage.desiredPlanDropdown, listOfPlans.get(random.get().nextInt(listOfPlans.size())));
                    World.save.get().put("desiredPlanName", serviceRequestPage.desiredPlanSelectedValue.getAttribute("value"));
                    break;
                case "reason":
                    serviceRequestPage.reasonDropdown.click();
                    int reasonIndex = random.get().nextInt(serviceRequestPage.listOfReasons.size());
                    serviceRequestPage.listOfReasons.get(reasonIndex).click();
                    browserUtils.get().waitForVisibility(serviceRequestPage.reasonDropdown, 3);
                    World.save.get().put("reason", "[X] " + serviceRequestPage.reasonDropdown.getText());
                    break;
                case "explanation":
                    String explanation = browserUtils.get().getRandomString(20);
                    serviceRequestPage.explanation.sendKeys(explanation);
                    World.save.get().put("explanation", explanation);
                    break;
                case "newPlanStartDate":
                    String newPlanStartDate = BrowserUtils.convertyyyyMMddToMMddyyyy(LocalDate.now().plusDays(random.get().nextInt(10)).toString());
                    serviceRequestPage.newPlanStartDate.sendKeys(newPlanStartDate);
                    World.save.get().put("newPlanStartDate", newPlanStartDate);
                    break;
            }
        }
    }
}
