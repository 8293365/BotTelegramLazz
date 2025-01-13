package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class
bftWebScraper {
    //string controlled by the telegrambotsClass???
    //protected static String PRODUCT_STRING_URL =  "/gate/products/cantilever-gate/";

    private static final String BASE_URL = "https://www.bft-automation.com/es_EC/centro-de-asistencia/descargar/";//"https://www.bft-automation.com/es_EC/";
    //EVEN BETTER WE ACTUALLY HAVE A USABLE PAGE FOR DIRECT ACCESS TO THE PDF MANUALS **totally not cheating**
    //"https://www.bft-automation.com/es_EC/centro-de-asistencia/descargar/"--> the forbidden page


    /*
     *       BASICALLY THE BIG IDEA HERE IS TO USE THE SEARCH BAR IN ORDER TO FIND THE PRODUCT, SINCE I'M TOO LAZY TO MAKE ANOTHER SERIOUS
     *       ATTEMPT AT A DECENT JOB, SO AND ALSO SINCE BFT WRITES THE PRODUCT CODE IN THE DIRECT PAGE I CAN COPY IT TOO EZ
     *       ALSO:
     *       plus: there is no form to submit to get the pdf only a "normal link"
     *       !plus:there is no .pdf link, and it also leads to another page, precision might be reduced since we just use the search bar but
     *       whathever, we select anyway the first result
     *
     *
     *
     */

    public bftWebScraper(){
        return;
    }

    public String search(String args){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        // Replace with the desired product name
        WebDriver driver = new ChromeDriver(chromeOptions);

        try {
            String pdfLink = findProductPdfLink(driver, args.toUpperCase());
            if (pdfLink != null) {
                driver.quit();
                return pdfLink;
            } else {
                driver.quit();
                return ("PDF datasheet not found for product: " + args);
            }
        } finally {
            driver.quit();
            return null;
        }
    }

    public static void main(String[] args) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);//beba z-wave driver
        String productName = "mitto"; // Replace with the desired product name//phobos ac a25 120v
        WebDriver driver = new ChromeDriver(chromeOptions);

        try {
            String pdfLink = findProductPdfLink(driver, productName.toUpperCase());
            if (pdfLink != null) {
                System.out.println("PDF Link: " + pdfLink);
            } else {
                System.out.println("PDF datasheet not found for product: " + productName);
            }
        } finally {
            driver.quit();
        }
    }

    public static String findProductPdfLink(WebDriver driver, String productName) {
        // Navigate to the main products page
        driver.get(BASE_URL);

        //handles the thingy thing, cookie banner?
        handleOverlay(driver);

        By select = By.cssSelector(".tx-solr-q.form-control.js-solr-q.tx-solr-suggest.tx-solr-suggest-focus");

        // MOOOORRRRRRRRRROOOOOOOOOOOOOOONNNNNN YOU FORGOT YOU HAD THE findElementSSSS it was returning a list, wake up idiot
        WebElement checkInput = driver.findElement(select);
        checkInput.clear();
        checkInput.sendKeys(productName);

        //handleOverlay(driver);

        //this should start the search
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(100));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tx-solr-submit.btn.btn-primary.text-uppercase")));
        element.click();

        driver.findElement(By.cssSelector(".tx-solr-submit.btn.btn-primary.text-uppercase")).click();

        new WebDriverWait(driver, Duration.ofMillis(100));
        //we now actually know that css selector can be used in place of classname (for compund classes) when someone is too azy to use the xpath and write 10 character too many
        //damn... the cssSelector wasn't working 'cause it was missing the '.' at the first positoin of the string, wow
        //once again i end up not using the chained filter lol
        //By filter = new ByChained(By.cssSelector(".btn.btn-primary.text-uppercase.has-icon.open-pdf"), By.xpath("//a[@data-event-label='*MANUALE*']"));
        List<WebElement> elements  = driver.findElements(By.cssSelector(".btn.btn-primary.text-uppercase.has-icon.open-pdf"));
        //System.out.println(elements.size());

        //List<WebElement> productLinks = driver.findElements(By.cssSelector("a"));
        for (WebElement link : elements) {
            //apparently i had to remove the spaces long from before but i was too lazy
            if (link.getDomAttribute("data-event-action").replaceAll("\\s","").equals(productName.replaceAll("\\s","")))
            {

                if(link.getDomAttribute("data-event-label").contains("MANUALE")) {//for some reason it does not work when they are with a && in the same declaration, boh
                    //let's jus say that the product code is still in production
                    //System.out.println(getProductCode(productName));
                    return link.getDomAttribute("href");
                }
                //HERE A CALL COULD BE ADDED WHERE IT GETS WITH ANOTHER FUNCTION THE PRODUCT CODE
                //https://www.bft-automation.com/es_EC/producto/phobos-ac-a25-120-v/
                //                                                  ^YOU PUT HERE ANY RIGHT NAME AND THEN IF IT DOES NOT GIVE AN ERROR THEN IT'S THE CORRECT ONE
            }
            //is this if statement pointless?? maybe
            else if(link.getDomAttribute("data-event-action").replaceAll("\\s","").contains(productName.replaceAll("\\s","")))
            {
                //return link.getDomAttribute("href");// it still does not work AHHHHHHHHHHHHH
                if(link.getDomAttribute("data-event-label").contains("MANUALE")) {//for some reason it does not work when they are with a && in the same declaration, boh
                    return link.getDomAttribute("href");
                }
            }
        }
        if(elements.getFirst() != null) {
            System.out.println("el modelo no ha sido encontrado, puede que el nombre no sea correcto o que el sitio no tenga màs el modelo buscado");
            return elements.getFirst().getDomAttribute("href");
        }else{
            return null;}

    }

    public static void handleOverlay(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(100));
            WebElement overlay = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CybotCookiebotDialog")));
            WebElement rejectButton = overlay.findElement(By.id("CybotCookiebotDialogBodyButtonDecline"));
            rejectButton.click();
            Thread.sleep(100); // Wait for the overlay to disappear
        } catch (Exception e) {
            System.out.println("No overlay detected or failed to close it: " + e.getMessage());
        }
    }

    public static String getProductCode(/*WebDriver driver, */String ProductName){
        final String PRODUCT_PAGE = "https://www.bft-automation.com/es_EC/producto/" + ProductName.replaceAll("\\s","-") + "/";
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        WebDriver searcher = new ChromeDriver(chromeOptions);
        try {
            System.out.println("https://www.bft-automation.com/es_EC/producto/" + ProductName.replaceAll("\\s","-") + "/");
            searcher.get(PRODUCT_PAGE);
            new WebDriverWait(searcher, Duration.ofMillis(100));
            handleOverlay(searcher);
            new WebDriverWait(searcher, Duration.ofMillis(100));
            if (searcher.findElement(By.className("subheader")).getText().equals("Error 404")) {
                System.out.println("AAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                searcher.close();
                return "1";
            }else{
                System.out.println(searcher.findElement(By.cssSelector(".d-block.font-heading")).getText().substring(19));
                return searcher.findElement(By.cssSelector(".d-block.font-heading")).getText().substring(19);
            }

        }catch(Exception e) {
            System.out.println("Something's happened & i don't know what" + e.getMessage());
        }
        searcher.close();
        return "1";
    }

}
