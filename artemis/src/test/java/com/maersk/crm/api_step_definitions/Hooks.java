package com.maersk.crm.api_step_definitions;

import com.maersk.crm.utilities.APIClassUtil;
import io.cucumber.java.Scenario;
import io.cucumber.java.*;

public class Hooks {
    public static final ThreadLocal<String> scenarioName = ThreadLocal.withInitial(String::new);
    @Before
    public void setUp(Scenario scenario) {
        System.out.println(scenario.getSourceTagNames()+ " - This is Scenario Tag at the begining___");
        System.out.println(scenario.getName()+" - This is Scenario Name at the begining__");
        scenarioName.set(scenario.getName());
        boolean isTmProject = false;
        for (String tag : scenario.getSourceTagNames()) {

            if (tag.startsWith("@API-TM")) {
                isTmProject = true;
                break;
            }
        }
        if (isTmProject)
            APIClassUtil.projectToConsider.set("tm");
        else
            APIClassUtil.projectToConsider.set("crm");
    }
    @After
    public void tearDown(Scenario scenario) {
        //nothing here as intended
    }

}
