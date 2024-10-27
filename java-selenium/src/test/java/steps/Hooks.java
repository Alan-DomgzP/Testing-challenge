package steps;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import pages.BasePage;

public class Hooks extends BasePage {
    
    public Hooks() {
        super(driver);
    }

    @After
    public void tearDown(Scenario scenario) throws IOException {
        // if( scenario.isFailed() ) {
        scenario.log("Scenario " + scenario.getStatus());
        // FOR ALLURE REPORTS
        scenario.log("For more information, please refer to the report: " + Utils.getLastReportFolderName("reports") );
        File screenshotFile = takeScreenShot( scenario.getName() + "_" + scenario.getStatus() );
        Allure.addAttachment("Screenshot ", FileUtils.openInputStream( screenshotFile ) );

        // FOR CUCUMBER REPORTS
        final byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "Screenshot of the error");
        // }
    }

    public static File takeScreenShot ( String screenshotName ) {
        
        File file = ( (TakesScreenshot) driver ).getScreenshotAs( OutputType.FILE );
        String srcPath = System.getProperty( "user.dir" );
        String screenshotPath = "";

        try {
            screenshotPath = srcPath + File.separator + Utils.getLastReportFolderName("screenshots") + File.separator + screenshotName.replace(" ", "_") + ".png";
            FileUtils.copyFile( file, new File( screenshotPath ) );
            System.out.println( "Screenshot at: " + screenshotPath );
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

}
