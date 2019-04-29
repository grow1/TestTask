import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class DriverManagerAndMethods {
    public enum DriverType {
        CHROME,
        FIREFOX,
        IE,
        SAFARI;
    }
    final String site = "http://ia.ca";
    final String language = "//*[@id=\"nav-primaire\"]/ul[2]/li[3]/a/span";
    final String loans = "//*[@id=\"nav-secondaire\"]/div[1]/ul/li[4]/a/span";
    final String mortgages = "//*[@id=\"nav-secondaire\"]/div[1]/ul/li[4]/ul/li[1]/section/ul/li[1]/a";
    final String mortgagePayment = "//*[@id=\"grille-zone-capacite-versements\"]/div/a[2]/div/h3";
    final String slider = "//*[@id=\"form_calculateur_versements\"]/div[2]/div/div[2]/div/div[1]/div[13]";
    final String amortizationList = "//*[@id=\"form_calculateur_versements\"]/div[4]/div[1]/div/div[2]/b";
    final String amortizationFirstValue = "//*[@id=\"form_calculateur_versements\"]/div[4]/div[1]/div/div[3]/div/ul/li[1]";
    final String paymentFrequencyMenu = "//*[@id=\"form_calculateur_versements\"]/div[5]/div[2]/b";
    final String paymentFrequencyValue = "//*[@id=\"form_calculateur_versements\"]/div[5]/div[3]/div/ul/li[4]";

    public static DriverManager getManager(DriverType type) {

        DriverManager driverManager;


  //      switch (type) {
    //        case CHROME:
                driverManager = new ChromeDriverManager();
//              break;
//            case FIREFOX:
//              driverManager = new FirefoxDriverManager();
//              break;
      //      case IE:
      //        driverManager = new IeDriverManager();
      //        break;
      //      default:
      //        driverManager = new SafariDriverManager();
      //          break;
      //           }
        return driverManager;
    }

        public static WebDriver driver;

        protected void findAndClick(String xpath){
            driver.findElement(By.xpath(xpath)).click();
        }

        protected void scrollToElement(String xpath) throws InterruptedException {
            WebElement calc = driver.findElement(By.xpath(xpath));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", calc);
            Thread.sleep(500);
        }

        public int getPurchasePriceValue(){
            int purchasePrice = Integer.parseInt(driver.findElement(By.id("PrixPropriete")).getAttribute("value"));
            return purchasePrice;
        }

        public int getDownPaymentValue(){
            int downPayment = Integer.parseInt(driver.findElement(By.id("MiseDeFond")).getAttribute("value"));
            return downPayment;
        }

        public double getFinalCalculations(){
            double calc_res = Double.parseDouble(driver.findElement(By.id("paiement-resultats")).getText().substring(1));
            return calc_res;
        }

        protected void setSlider(int x, int y) throws InterruptedException {
            WebElement slider = driver.findElement(By.xpath("//*[@id=\"form_calculateur_versements\"]/div[2]/div/div[2]/div/div[1]/div[13]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", slider);
            Thread.sleep(500);
            Actions move = new Actions(driver);
            Action action = move.dragAndDropBy(slider, x, y).build();
            action.perform();
        }
}

