package pl.pandait.panda;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

@SpringBootTest(classes = {PandaApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PandaApplicationSeleniumTest {
    
    private static WebDriver driver;
    
    @LocalServerPort
    private int port;
    
    @BeforeEach
    public void startup() throws InterruptedException, MalformedURLException {
        
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setPlatform(Platform.LINUX);
        driver = new RemoteWebDriver(new URL("http://192.168.44.44:4444/wd/hub"), capabilities);
        // Pamiętaj, że aplikacja Spring musi działać! To znaczy też musi być włączona.
        
        driver.get(String.format("http://ubuntu:%d/", port));

        //Czekamy 2 sekundy
        Thread.sleep(2000);
    }

    @Test
    public void checkIfApplicationRunCorrectly(){
        System.out.println("Uruchamiam test 1: Sprawdzenie napisu na stronie głównej");
        WebElement greetingString = driver.findElement(By.xpath("//p"));
        String napis = greetingString.getText().trim();
        assertEquals("Get your greeting here", napis);

        WebElement linkToGreetings = greetingString.findElement(By.xpath("./a"));
        linkToGreetings.click();

        WebElement helloWorldString = driver.findElement(By.xpath("//p"));
        String newPageString = helloWorldString.getText().trim();
        assertEquals("Hello, World!", newPageString);
    }

    @AfterAll
    public static void after(){
        driver.quit();
    }
}
