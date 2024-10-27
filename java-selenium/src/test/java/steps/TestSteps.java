package steps;

import org.testng.Assert;

import io.cucumber.java.en.*;
import pages.AutomationPage;


public class TestSteps {

    AutomationPage test = new AutomationPage();
    
    @Given("we are on the automation practice page")
    public void iNavigateTo() {
        test.goToPage();
        Assert.assertEquals(test.getWindowTitle(), "Demo Web");
    }
    
    @Then("we go to the pool section")
    public void goToPool() {
        test.goToPool();
    }

    @And("^we select the year ([0-9]+)$")
    public void selectYear(Integer year) {
        String anio = Integer.toString(year);
        test.selectYearInput(anio);
    }

    @Then("^we select the user (.*)$")
    public void selectUser(String user) {
        test.selectUserDropdown(user);
    }

    @And("^we select the week (.*)$")
    public void selectWeek(String week) throws InterruptedException  {
        test.selectWeekDropdown(week);
        Thread.sleep(2000);
        test.selectWeekDropdown(week);
        Assert.assertEquals(test.getWeekDropdownValue(), week);
    }

    @Then("we select our pool options")
    public void selectPoolOptions() {
        test.selectPoolOptions();
    }

    @And("we save the pool configuration")
    public void savePool() {
        test.savePool();
    }

    @Then("^we verify the username (.*) is in the dialog$")
    public void verifyUsername(String user) {
        String dialog_name = test.verifyUsername(user);
        Assert.assertTrue(dialog_name.contains(user));
    }

    @And("we save the pool settings")
    public void savePoolSettings() {
        test.savePoolSettings();
    }
    
    @And("we verified the pool is saved successfully")
    public void poolSavedSuccessfully() throws InterruptedException {
        String title = test.getSavedPoolTitle();
        Assert.assertTrue(title.contains("Listo"));
        test.clickOkModal();
    }
    
    @And("we verified username is missing text")
    public void verifyUsernameMissing() throws InterruptedException {
        String title = test.getPoolModalText();
        Assert.assertTrue(title.contains("Olvidaste seleccionar usuario"));
    }

    @Then("we fill almost all of our pool options")
    public void unfilledPoolOptions() {
        test.selectPoolOptions("unfilled");
    }

    @And("we verified unfulfilled options text")
    public void verifyUnfulfilledOptions() throws InterruptedException {
        String text = test.getPoolModalText();
        Assert.assertTrue(text.contains("Te faltan partidos por llenar"));
    }

    @Then("we retry send the pool configuration with same user")
    public void retryPoolSameUser() {
        test.selectPoolOptions();
        test.savePool();
        test.savePoolSettings();
    }

    @And("we verify the error message")
    public void verifyErrorMessage() throws InterruptedException {
        String text = test.getPoolModalText();
        Assert.assertTrue(text.contains("Hoja de quiniela duplicada para esta jornada"));
    }

    @When("we go to my score section")
    public void goToScore() {
        test.goToScore();
    }

    @Then("we verify my score is correct")
    public void verifyUserScore() {
        int table_value = test.returnTotalTableScore();
        int total_score = test.totalScore();
        // System.out.println("Table value: " + table_value);
        // System.out.println("Total score: " + total_score);
        Assert.assertTrue(total_score == table_value);
    }

    @And("we search the data")
    public void clickSearch() {
        test.clickSearch();
    }
}


