package com.maersk.crm.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "json:target/cucumber-parallel/cucumber.json"
        },
        tags = "@invalidTagPassedIntoCommandLine",
        features = {"src/test/resources/com/maersk/crm/features", "src/test/resources/api.features", "src/test/resources/db.features", "src/test/resources/dms.features", "src/test/resources/ETL.features"},
        glue = {"com.maersk.crm.api_step_definitions", "com.maersk.crm.step_definitions", "com.maersk.dms.step_definitions", "com.maersk.dms.steps", "com.maersk.crm.etl_step_definitions"},
        dryRun = false
)
public class AutomationRunner {
}