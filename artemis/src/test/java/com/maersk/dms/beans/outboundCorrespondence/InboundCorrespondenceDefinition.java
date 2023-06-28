package com.maersk.dms.beans.outboundCorrespondence;

import com.github.javafaker.Faker;
import com.maersk.crm.utilities.BrowserUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.maersk.crm.utilities.World.getWorld;

public class InboundCorrespondenceDefinition {
    public String createdBy;
    public String createdDatetime;
    public String description;
    public String name;
    public String barcode;
    public String ID;
 //   public InboundCorrespondenceTaskRule inboundCorrespondenceTaskRule;
    //    public Response response;


    public void createRandomValues() {
        Faker faker = new Faker();
        this.barcode = RandomStringUtils.randomNumeric(4);
        this.name = RandomStringUtils.randomAscii(20);
        if(this.name.contains("\"")){
            this.name.replace("\\", RandomStringUtils.randomAlphabetic(1));
        }
        if(name.contains("\"")){
            this.name.replace("\"", RandomStringUtils.randomAlphabetic(1));
        }
        this.description = RandomStringUtils.randomAlphanumeric(40);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        this.createdBy = "Service Account1";
        this.createdDatetime = now.toString();

        returnValues();
    }

    public Map<String, String> returnValues() {
        Map<String, String> inboundCorrespondenceDefinition = new HashMap<>();
        Field[] randomValues = getWorld().inboundCorrespondenceDefinition.get().getClass().getDeclaredFields();
        for (Field field : randomValues) {
            try {
                inboundCorrespondenceDefinition.put(field.getName(), field.get(this).toString());
            } catch (NullPointerException nool) {
                inboundCorrespondenceDefinition.put(field.getName(), "null");
            } catch (Exception e) {
                continue;
            }
        }
        return inboundCorrespondenceDefinition;
    }
}