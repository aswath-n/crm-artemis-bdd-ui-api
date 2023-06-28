package com.maersk.dms.beans.outboundCorrespondence;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.maersk.crm.utilities.World.getWorld;

public class OutboundCorrespondenceDefinition {
    public String ID;
    public String Name;
    public String description;
    public String startDate;
    public String endDate;
    public String stateID;

    public String endReason;
    public boolean userMayRequest;
    public String bodyDataSoource;
//    public Response response;
    public String CreatedBy;
    public String[] correspondenceRequiredKeys;
    public String createdDatetime;
    public String reasonForEnding;
    public String correspondenceId;
    public String inboundCorrespondenceType;

    public void createRandomValues() {
        Faker faker = new Faker();
        this.ID = RandomStringUtils.randomAlphanumeric(10);
        this.Name = RandomStringUtils.randomAlphanumeric(10);
        this.description = RandomStringUtils.randomAlphanumeric(40);
        this.endReason = RandomStringUtils.randomAlphanumeric(50);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        this.startDate = dtf.format(now);
        this.endDate = dtf.format(LocalDateTime.now().plusMonths(faker.random().nextInt(1, 36)));
        this.stateID = RandomStringUtils.randomAlphanumeric(10);
        this.bodyDataSoource = "https://mars-ecmcor-letterdataservice-test-dev.apps.non-prod.pcf-maersk.com/app/eb/letterdata";
        this.CreatedBy = "Service Account1";
        this.inboundCorrespondenceType = "maersk Case + Consumer";
        this.correspondenceRequiredKeys = new String[0];

        returnValues();
    }

    public Map<String, String> returnValues() {
        Map<String, String> outBoundCorrespondenceDefinition = new HashMap<>();
        Field[] randomValues = getWorld().outboundCorrespondenceDefinition.get().getClass().getDeclaredFields();
        for (Field field : randomValues) {
            try {
                outBoundCorrespondenceDefinition.put(field.getName(), field.get(this).toString());
            } catch (NullPointerException nool) {
                outBoundCorrespondenceDefinition.put(field.getName(), "null");
            } catch (Exception e) {
                continue;
            }
        }
        return outBoundCorrespondenceDefinition;
    }
}
