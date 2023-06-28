package com.maersk.dms.runners;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"pretty",
                "html:target/cucumber-report",
                "json:target/cucumber.json"
        },
        tags = "@CP-7307",  //@ECMS-REGRESSION
        features = {"src/test/resources/dms.features"},
        glue = "com/maersk/dms/steps",
        dryRun = false
)
public class DmsRunner extends AbstractTestNGCucumberTests {


}
