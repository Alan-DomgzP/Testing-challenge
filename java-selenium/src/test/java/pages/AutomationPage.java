package pages;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class AutomationPage extends BasePage {

    private By pool_btn = By.xpath("//span[normalize-space()='Llenar Quiniela']");
    private By year_input = By.xpath("//input[@id='year']");
    private By week_dropdown = By.xpath("//select[@id='week']");
    private By user_dropdown = By.xpath("//select[@id='user']"); 

    private By td_teams_table = By.xpath("//tr/td[2]");
    private By save_btn = By.xpath("//div[@id='saveForecast']");

    private By modal_title = By.xpath("//h2[@id='swal2-title']");
    private By save_pool_settings_btn = By.xpath("//button[normalize-space()='Sí, guardar']");
    private By saved_pool_title = By.xpath("//h2[@id='swal2-title']");
    private By pool_modal_text = By.xpath("(//div[@id='swal2-html-container'])[1]");
    private By ok_modal_btn = By.xpath("//button[normalize-space()='OK']");

    private By my_score_btn = By.xpath("//span[normalize-space()='Mi Puntaje']");
    private By td_score_table = By.xpath("//tr/td[3]");
    private By total_score_lbl = By.xpath("//span[starts-with(normalize-space(), 'Puntaje Total:')]");
    private By search_btn = By.xpath("//div[@id='loadBoard']");

    
    public AutomationPage() {
        super(driver);
    }

    public void goToPage() {
        navigateTo("https://mean-machine-qa.vercel.app/");
    }

    public String getWindowTitle() {
        return getTitle();
    }

    public void goToPool() {
        clickElement(pool_btn);
    }

    public void selectYearInput(String year) {
        write(year_input, year);
    }
    
    public void selectUserDropdown( String option ) {
        selectFromDropdownByText(user_dropdown, option);
    }

    public void selectWeekDropdown(String week) {
        selectFromDropdownByText(week_dropdown, week);
    }

    public String getWeekDropdownValue() {
        return getDropdownSelectedOption(week_dropdown);
    }

    public void selectPoolOptions() {
        List<WebElement> tds = getListOfElements(td_teams_table);
        Random random = new Random();
        String[] classNames = {"homeTeam", "radioToolbar", "awayTeam"};
        
        for (WebElement td : tds) {
            int randomChoice = random.nextInt(classNames.length);
            try {
                WebElement elementToClick = td.findElement(By.className(classNames[randomChoice]));
                elementToClick.click();

            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    public void selectPoolOptions(String option) {
        List<WebElement> tds = getListOfElements(td_teams_table);
        Random random = new Random();
        String[] classNames = {"homeTeam", "radioToolbar", "awayTeam"};

        if(option.equals("unfilled")) {
            tds.remove(tds.size() - 1);
        }
        
        for (WebElement td : tds) {
            int randomChoice = random.nextInt(classNames.length);
            try {
                WebElement elementToClick = td.findElement(By.className(classNames[randomChoice]));
                elementToClick.click();

            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    public void savePool() {
        clickElement(save_btn);
    }

    public String verifyUsername(String user) {        
        return getElementTxt(modal_title);
    }

    public void savePoolSettings() {
        clickElement(save_pool_settings_btn);
    }

    public String getSavedPoolTitle() throws InterruptedException {
        Thread.sleep(2000);
        return getElementTxt(saved_pool_title);
    }
    
    public String getPoolModalText() throws InterruptedException {
        Thread.sleep(2000);
        return getElementTxt(pool_modal_text);
    }

    public void clickOkModal() {
        clickElement(ok_modal_btn);
    }

    public void goToScore() {
        clickElement(my_score_btn);
    }

    public Integer returnTotalTableScore() {
        List<WebElement> tds = getListOfElements(td_score_table);
        int count = 0;
        
        for (WebElement td : tds) {

            try {
                List<WebElement> spans = td.findElements(By.tagName("span"));

                String text1 = spans.get(0).getText();
                String text2 = spans.get(2).getText();

                if (text1.equals(text2)) {
                    count++;
                }
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
        return count;
    }

    public Integer totalScore() {
        return Integer.parseInt(getElementTxt(total_score_lbl).split(":")[1].trim());
    }

    public void clickSearch() {
        clickElement(search_btn);
    }
}