package pages;
 
// Importaciones necesarias
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class BasePage {

    protected static WebDriver driver;
    /*
     * Declaración de una variable de instancia 'wait' de tipo WebDriverWait.
     * Se inicializa inmediatamente con una instancia dew WebDriverWait utilizando el 'driver' estático
     * WebDriverWait se usa para poner esperas explícitas en los elementos web
     */
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
 
    /* 
     * Configura el WebDriver para Chrome usando WebDriverManager.
     * WebDriverManager va a estar descargando y configurando automáticamente el driver del navegador
    */
    static {
        WebDriverManager.chromedriver().setup();
        //Inicializa la variable estática 'driver' con una instancia de ChromeDriver
        driver = new ChromeDriver();
    }

    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
    }

    public void waitGivenSeconds( Integer seconds ){ 
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds( seconds ));
    }
 
    public static void navigateTo(String url) {
        driver.get(url);
    }

    public static String getTitle() {
        return driver.getTitle();
    }

    public static void closeBrowser() {
        driver.quit();
    }
    
    private WebElement Find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated( locator ));
    }
 
    public void clickElement(By locator) {
        Find(locator).click();
    }

    public String getElementTxtByAttribute(By locator) {
        return Find(locator).getAttribute("value");
    }

    public String getElementTxt( By locator) {
        return Find(locator).getText();
    }

    public void write(By locator, String keysToSend) {
        Find(locator).clear();
        Find(locator).sendKeys(keysToSend);
    }

    public void selectFromDropdownByValue(By locator, String value) {
        Select dropdown = new Select(Find(locator));
 
        dropdown.selectByValue(value);
    }
 
    public void selectFromDropdownByIndex(By locator, Integer index) {
        Select dropdown = new Select(Find(locator));
 
        dropdown.selectByIndex(index);
    }

    public void selectFromDropdownByText(By locator, String valueToSelect) {
        Select dropdown = new Select(Find(locator));
 
        dropdown.selectByVisibleText(valueToSelect);
    }

    public String getDropdownSelectedOption(By locator) {
        Select dropdown = new Select(Find(locator));
        return dropdown.getFirstSelectedOption().getText();
    }
 
    public int dropdownSize(By locator) {
        Select dropdown = new Select(Find(locator));
 
        List<WebElement> dropdownOptions = dropdown.getOptions();
 
        return dropdownOptions.size();
    }

    public List<String> getDropdownValues(By locator) {
        Select dropdown = new Select(Find(locator));
 
        List<WebElement> dropdownOptions = dropdown.getOptions();
        List<String> values = new ArrayList<>();
        for (WebElement option : dropdownOptions) {
            values.add(option.getText());
        }
        return values;
    }

    public List<WebElement> getListOfElements ( By locator ) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy( locator ) );
    }

    public void selectItemInList( By locator, String elementToFind) {
        List<WebElement> elementList = getListOfElements( locator);

        for(WebElement e : elementList ) {
            if( e.getText().equals(elementToFind) ) {
                e.click();
            }
        }
    }

    public String alertActions(String action) {
        switch( action ) {
            case "accept":
                driver.switchTo().alert().accept();
                return null;
            case "dismiss":
                driver.switchTo().alert().dismiss();
                return null;
            case "getText":
                return driver.switchTo().alert().getText();
            default:
                throw new IllegalArgumentException( "Unknown action: " + action );
        }
    }

    public void scrollAndFindElement (By locator) {
        new Actions(driver)
            .scrollToElement(Find(locator ))
            .perform();
    }

    public Boolean isElementPresent(By locator) {
        return Find(locator ).isDisplayed();
    }

    public void switchToiFrame( String name ){
        driver.switchTo().frame(name);
    }

}
