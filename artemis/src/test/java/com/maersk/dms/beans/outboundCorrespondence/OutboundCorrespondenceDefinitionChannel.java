package com.maersk.dms.beans.outboundCorrespondence;

import com.github.javafaker.Faker;
import com.maersk.crm.beans.CreateAContactBean;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.maersk.crm.utilities.World.getWorld;

public class OutboundCorrespondenceDefinitionChannel {
    public String channelType;
    public String startDate;
    public String endDate;
    public String endReason;
    public String senderEmailId;
    public boolean sendImmediately;
    public boolean mandatory;
    public boolean includeOnWeb;
    public String[] channelDefinitionElements;
    public String notificationPurpose;

    public enum channel {
        MAIL("Mail"), TEXT("Text"),
        EMAIL("Email"), FAX("Fax");
        private String channel;

        public String getChannel() {
            return channel;
        }

        private channel(String channel) {
            this.channel = channel;
        }
    }

    public enum notificationPurposes{
        ADVANCE("Advance Notice"),ATTACHED("See Attached"),MAIN("Main Content");
        private String notificationPurpose;

        notificationPurposes(String notificationPurpose) {
            this.notificationPurpose=notificationPurpose;
        }

        public String getNotificationPurpose(){
            return notificationPurpose;
        }

    }

    public void createRandomValuesChannel() {
        Faker faker = new Faker();
        int ch = faker.random().nextInt(1, 4);
        switch (ch) {
            case 1:
                this.channelType = channel.MAIL.getChannel();
                break;
            case 2:
                this.channelType = channel.TEXT.getChannel();
                break;
            case 3:
                this.channelType = channel.EMAIL.getChannel();
                break;
            case 4:
                this.channelType = channel.FAX.getChannel();
                break;

        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate now = LocalDate.now();
        this.startDate = dtf.format(now);
        this.endDate = dtf.format(now);
        this.endReason = RandomStringUtils.randomAlphanumeric(50);
        this.senderEmailId = RandomStringUtils.random(5, true, false) + "@maerskTest" + RandomStringUtils.random(7, true, false) + ".com";
        this.sendImmediately = System.currentTimeMillis() % 2 == 0;
        this.mandatory = System.currentTimeMillis() % 2 == 0;
        this.includeOnWeb = System.currentTimeMillis() % 2 == 0;
        int nf = faker.random().nextInt(1, 3);
        switch (nf) {
            case 1:
                this.notificationPurpose = notificationPurposes.MAIN.notificationPurpose;
                break;
            case 2:
                this.notificationPurpose = notificationPurposes.ATTACHED.notificationPurpose;
                break;
            case 3:
                this.notificationPurpose = notificationPurposes.ADVANCE.notificationPurpose;
                break;

        }

    }

    public Map<String, String> returnValues() {
        Map<String, String> outBoundCorrespondenceDefinitionChannel = new HashMap<>();
        Field[] randomValues = getWorld().outboundCorrespondenceDefinitionChannel.get().getClass().getDeclaredFields();
        for (Field field : randomValues) {
            try {
                outBoundCorrespondenceDefinitionChannel.put(field.getName(), field.get(this).toString());
            } catch (NullPointerException nool) {
                outBoundCorrespondenceDefinitionChannel.put(field.getName(), "null");
            } catch (Exception e) {
                continue;
            }
        }
        return outBoundCorrespondenceDefinitionChannel;
    }
}
