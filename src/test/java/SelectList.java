import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SelectList {


    WebDriver driver;
    WebDriverWait wait;

    By cookieAccept = By.cssSelector("#cn-accept-cookie");
    By cookieAcceptFakeStore = By.cssSelector(".woocommerce-store-notice__dismiss-link");

    @BeforeEach
    public void driverSetup()
    {
        String fakeStore = "https://fakestore.testelka.pl/";
        String fakeStoreWindsurfing = "https://fakestore.testelka.pl/product-category/windsurfing/";
        String blog = "https://testelka.pl/blog/";
        String allegro = "https://allegro.pl/";

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
//        driver.navigate().to(allegro);
//        driver.manage().addCookie(new Cookie("gdpr_permisson_given","1"));
//        driver.navigate().refresh();

        driver.navigate().to(fakeStoreWindsurfing);
        driver.findElement(cookieAcceptFakeStore).click();

    }

    @AfterEach
    public void driverClose()
    {
        driver.close();
        driver.quit();
    }

    //Allegro
    @Test
    public void selectElement() {
            WebElement productCategories = driver.findElement(By.cssSelector("[data-role='filters-dropdown-toggle']"));
            Select categoriesDropdown = new Select(productCategories);
            categoriesDropdown.selectByIndex(3);
            categoriesDropdown.selectByValue("/kategoria/uroda");
            categoriesDropdown.selectByVisibleText("Zdrowie");
            Boolean isMultiple = categoriesDropdown.isMultiple();
            //categoriesDropdown.deselectByIndex(3);
            List<WebElement> options = categoriesDropdown.getOptions();
            List<WebElement> selectedOptions =categoriesDropdown.getAllSelectedOptions();
            WebElement firstSelectedOption = categoriesDropdown.getFirstSelectedOption();
        }

      //w3school
     @Test
     public  void selectElementMultipleSelect() {
        driver.navigate().to("https://www.w3schools.com/code/tryit.asp?filename=FZKB5XSEVTU8");
        driver.findElement(By.cssSelector("button.w3-bar-item")).click();
        driver.switchTo().frame("iframeResult");

        WebElement dropdownElement = driver.findElement(By.cssSelector("select"));
        Select dropdown = new Select(dropdownElement);
        Boolean isMultiple = dropdown.isMultiple();
        dropdown.selectByIndex(1);
        dropdown.selectByIndex(3);

        dropdown.deselectByIndex(1);
        dropdown.deselectAll();
     }

     @Test
     public void selectElementPractice() {
        driver.navigate().to("http://automationpractice.com/index.php?controller=contact");
        WebElement dropdownElement = driver.findElement(By.cssSelector("#id_contact"));
        Select dropdown = new Select(dropdownElement);
        Boolean isMultiple = dropdown.isMultiple();
        dropdown.selectByIndex(1);
        dropdown.selectByValue("Webmaster");
     }

//    Skorzystaj z kategorii Windsurfing.
//    Napisz dwa testy: jeden dla sortowania po cenie od najniższej do najwyższej, drugi od najwyższej do najniższej.
//            Założenie: wiesz jaka jest najniższa i najwyższa cena.
//    W tym zadaniu w asercji posługuj się stringami (nie musisz parsować stringów do typu liczbowego).

     @Test
     public void selectTheLowPrice() {
        WebElement dropdownElement = driver.findElement(By.cssSelector("select[name='orderby']"));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText("Sortuj wg ceny: od najniższej");
        String priceTheLowest = driver.findElement(By.cssSelector("span[class='price']>span")).getText();
        Assertions.assertEquals("2900,00 zł",priceTheLowest, "Wrong sort for the lowest price");
     }

    @Test
    public void selectTheHighestPrice() {
        WebElement dropdownElement = driver.findElements(By.cssSelector("select[name='orderby']")).get(0); // jak mamy takie dwa podobne
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue("price-desc");
        String priceTheHighest = driver.findElement(By.cssSelector("span[class='price']>span")).getText();
        Assertions.assertEquals("5 399,00 zł",priceTheHighest, "Wrong sort for the lowest price");
    }

    @Test
    public void priceOrderAscTest() {
        WebElement orderBy = driver.findElements(By.cssSelector("select[name='orderby']")).get(0);
        Select orderByDropdown = new Select(orderBy);
        orderByDropdown.selectByValue("price");
        WebElement firstPriceElement = driver.findElements(By.cssSelector("span.price")).get(0);
        Assertions.assertEquals("2900, 00 zł", firstPriceElement.getText(), "First price is not the lowest price");
    }

    @Test
    public void priceOrderDescTest() {
        WebElement orderBy = driver.findElements(By.cssSelector("select[name='orderby']")).get(0);
        Select orderByDropdown = new Select(orderBy);
        orderByDropdown.selectByValue("price-desc");
        WebElement firstPriceElement = driver.findElements(By.cssSelector("span.price")).get(0);
        Assertions.assertEquals("5900, 00 zł", firstPriceElement.getText(), "First price is not the lowest price");
    }


}
