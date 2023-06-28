package com.maersk.crm.utilities;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/20/2020
 */
public class EventBuilder {
    private String body;
    private String name;

    public EventBuilder body(String body) {
        this.body = body;
        return this;
    }

    public EventBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Api_Body build() {
        return new Api_Body(name, body);
    }
}
