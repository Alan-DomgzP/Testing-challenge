package runner;

import steps.Utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import pages.BasePage;

import java.io.IOException;


@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources", 
    glue = "steps",
    plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" },
    tags="@test_case_5"
)
 
public class TestRunner {

    @BeforeClass
    public static void setUpAllureProperties() throws IOException {
        Utils.validateAndCreateDirectories();
    }
    
    @AfterClass
    public static void cleanDriver() throws IOException {
        String reportLocation = Utils.getLastReportFolderName("reports");
        Utils.moveAndDeleteAllureReports(reportLocation);
        BasePage.closeBrowser();
    }
}
