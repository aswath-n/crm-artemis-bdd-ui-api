package com.maersk.crm.utilities;

import java.util.*;

/**
 * Created by KAMIL SHIKHSAFIYEV ON 4/26/2020
 */
public class Api_Storage {

    private static Api_Storage instance;
    private List<Api_Body> producedEvents = new ArrayList<>();
    private LinkedHashMap<String, LinkedList<Api_Body>> consumedEvents = new LinkedHashMap<>();
    private String lastApiEventConsumed;
    private String lastApiEventProduced;


    private Api_Storage() {
        instance = this;
    }

    public static Api_Storage getInstance() {
        return instance == null ? new Api_Storage() : instance;
    }

    public static void reset() {
        instance = new Api_Storage();
    }

    public Api_Body getProduced(String eventName) {
        return producedEvents.stream()
                .filter(event -> event.getName().equalsIgnoreCase(eventName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produced event is not found by name: " + eventName));
    }

    public String addProduced(Api_Body event) {
        producedEvents.add(event);
        setLastApiEventProduced(event.getName());
        return event.getName();
    }

    public String addConsumed(Api_Body event) {
        List<Api_Body> events = consumedEvents.computeIfAbsent(event.getName(), k -> new LinkedList<>());
        events.add(event);
        setLastApiEventConsumed(event.getName());
        return event.getName();
    }


    public Api_Body getConsumed(String eventName) {
        return consumedEvents.values().stream()
                .flatMap(Collection::stream)
                .filter(e -> e.getName().equals(eventName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Consumed event is not found by name: " + eventName));
    }

    public Api_Body findEvent(String eventName) {
        String name = eventName.equalsIgnoreCase("p") ? getLastApiEventProduced() :
                eventName.equalsIgnoreCase("c") ? getLastApiEventConsumed() : eventName;

        Api_Body produced = null;
        Api_Body consumed = null;

        try {
            produced = getProduced(name);
        } catch (RuntimeException ignored) {
        }
        try {
            consumed = getConsumed(name);
        } catch (RuntimeException ignored) {
        }

        if (consumed != null && produced != null) {
            throw new RuntimeException("Found produced and consumed events with the same name: " + eventName);
        } else if (consumed == null && produced == null) {
            throw new RuntimeException("Event not found: " + eventName);
        }

        return produced != null ? produced : consumed;
    }

    public Api_Body getLastConsumed(){
        return this.getConsumed(getLastApiEventConsumed());
    }
    public String getLastApiEventProduced() {
        return lastApiEventProduced;
    }

    public void setLastApiEventProduced(String lastApiEventProduced) {
        this.lastApiEventProduced = lastApiEventProduced;
    }

    public String getLastApiEventConsumed() {
        return lastApiEventConsumed;
    }

    public void setLastApiEventConsumed(String lastApiEventConsumed) {
        this.lastApiEventConsumed = lastApiEventConsumed;
    }

}
