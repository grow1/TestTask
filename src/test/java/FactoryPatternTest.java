import org.openqa.selenium.*;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;


public class FactoryPatternTest extends DriverManagerAndMethods {

    DriverManager driverManager;

    @BeforeTest
    public void beforeTest() {
        driverManager = DriverManagerAndMethods.getManager(DriverType.CHROME);
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = driverManager.getDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        driverManager.quitDriver();
    }

    @Test
    public void TestAutomationTask() throws InterruptedException {

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        try {
            driver.get(site);//open website
            findAndClick(language);
        }
        catch (TimeoutException e) {
            System.out.println("Page " + site + " did not load within 15 seconds!");
        }
        Thread.sleep(2000);
        findAndClick(loans);
        Thread.sleep(1000);
        findAndClick(mortgages);
        Thread.sleep(2000);
        scrollToElement(mortgagePayment);
        findAndClick(mortgagePayment);//open mortgage payment calculator
        Thread.sleep(2000);

        setSlider(100,0);
        int purchasePrice = getPurchasePriceValue();
        Assert.assertEquals(purchasePrice>0,true);//check if the first slider is working

        if(purchasePrice > 500000){
            driver.findElement(By.id("PrixProprieteMinus")).click();
        }else {
            driver.findElement(By.id("PrixProprietePlus")).click();
        }
        Thread.sleep(500);
        driver.findElement(By.id("MiseDeFondPlus")).click();
        int downPayment = getDownPaymentValue();
        Assert.assertEquals(downPayment,100000);//check if the second slider is working

        findAndClick(amortizationList);
        findAndClick(amortizationFirstValue);//choose 15 years from amortization

        driver.findElement(By.id("TauxInteret")).clear();
        driver.findElement(By.id("TauxInteret")).sendKeys("5");

        findAndClick(paymentFrequencyMenu);
        findAndClick(paymentFrequencyValue);//choose weekly payment frequency

        driver.findElement(By.id("btn_calculer")).click();//click on Calculate button
        Thread.sleep(1000);
        double calcResults = getFinalCalculations();
        Assert.assertEquals(calcResults,726.35);//check if final calculation is ok

        Thread.sleep(1500);

    }
}