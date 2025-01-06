package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class UrmetWebScraper {
    private static final String BASE_URL = "https://www.urmet.com";
    private static final String CATEGORY_PAGE_URL = "/Telephony";
    private static final String PRODUCTS_PAGE_URL = BASE_URL + "/en-us/Professional/Products"+ CATEGORY_PAGE_URL +"/See-all-the-products";

    public static void main(String[] args) {
        String productCode = "1375/836"; // Replace with the desired product code
        WebDriver driver = new ChromeDriver(getChromeOptions());

        try {
            String pdfLink = findProductPdfLink(driver, productCode);
            if (pdfLink != null) {
                System.out.println("PDF Link: " + pdfLink);
            } else {
                System.out.println("PDF datasheet not found for product code: " + productCode);
            }
        } finally {
            driver.quit();
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        return options;
    }

    public static String findProductPdfLink(WebDriver driver, String productCode) {
        driver.get(PRODUCTS_PAGE_URL);
        handleOverlay(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));


        try {
            // Extract links from the specified class on the main page
            ByChained chain = new ByChained( By.cssSelector(".shades-content-menu.d-flex-wrap.mg-up-56.gap-16.jst-cnt.mg-lr-40 a"),By.tagName("href"));
            List<WebElement> categoryLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    //chain));
                    By.cssSelector(".shades-content-menu.d-flex-wrap.mg-up-56.gap-16.jst-cnt.mg-lr-40 a")));
            List<String> listy = new ArrayList<>();

            System.out.println("Number of category links found: " + categoryLinks.size());
            for (WebElement link : categoryLinks) {
                System.out.println(link.getDomAttribute("href"));
                listy.add(link.getDomAttribute("href"));
            }

            for (WebElement categoryLink : categoryLinks) {
                //String categoryHref = categoryLink.getDomAttribute("href");

                System.out.println(categoryLinks.indexOf(categoryLink));
                //System.out.println(listy.get(i).getDomAttribute("href"));

                if (listy.get(categoryLinks.indexOf(categoryLink)) != null) {

                    driver.get(BASE_URL + listy.get(categoryLinks.indexOf(categoryLink)));
                    //categoryLink.click();
                    System.out.println(driver.getCurrentUrl());
                    handleOverlay(driver);

                    // Load all products on the category page
                    if  (   loadAllProducts(driver)  ==  1) {
                        System.out.println("Exception encountered");
                    } else if ( loadAllProducts(driver)  ==  2) {
                        if(categoryLinks.indexOf(categoryLink) < categoryLinks.size()) {    continue;
                        }   else  {
                            System.out.println("number 2");
                            break;  }
                    }

                    // Wait for product links to be present
                    List<WebElement> productLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.className("product-card")));
                    System.out.println("Number of products found in category: " + productLinks.size());

                    for (WebElement productLink : productLinks) {
                        //so here i check each of the "product-card"
                        System.out.println(productLink.findElement(By.tagName("a")).getDomAttribute("href"));
                        String productHref = productLink.findElement(By.tagName("a")).getDomAttribute("href");

                        //anyway i have to improve the if statement to try to compensate for the incorrect product name statement(OR JUST USE A PROPER FUNCTION YEAH???)
                        if (productLink.findElement(By.cssSelector(".paragraph-medium.paragraph-xl.txt-upper.txt-c-blue-400.mg-up-32.mg-dw-24")).getText().contains(productCode)) {
                            System.out.println("found it");

                            productLink.findElement(By.className("product-card-image")).click();
                            handleOverlay(driver);

                            try {
                                WebElement pdfLinkElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                                        By.cssSelector(".link.link-regular.link-light.txt-underlined")));
                                return pdfLinkElement.getDomAttribute("href");
                            } catch (TimeoutException e) {
                                System.out.println("PDF link not found on the product page.");
                                return null;
                            }

                            //}
                        }
                    }
                }
                WebDriverWait waity = new WebDriverWait(driver, Duration.ofSeconds(5));
                //waity.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("")));
                waity.withTimeout(Duration.ofSeconds(2));
            }
        } catch (TimeoutException e) {
            System.out.println("TimeoutException: Elements not found. Page source:");
            System.out.println(driver.getPageSource());
            throw e;
        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementReferenceException: Element became stale. Retrying...");
            // Implement retry logic if necessary
        }

        System.out.println("Product with code \"" + productCode + "\" not found.");
        return null;
    }

    private static void handleOverlay(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement overlay = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("onetrust-banner-sdk")));
            WebElement rejectButton = overlay.findElement(By.id("onetrust-reject-all-handler"));
            rejectButton.click();
            Thread.sleep(500);
        } catch (TimeoutException e) {
            System.out.println("No overlay detected within the specified wait time.");
        } catch (Exception e) {
            System.out.println("An error occurred while handling the overlay: " + e.getMessage());
        }
    }

    private static int loadAllProducts(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            while (true) {
                // Check if the "Show more" button is present and visible
                WebElement showMoreButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("a.btn.btn-medium.btn-white-outlined.mg-up-16[onclick='viewOtherProducts();']")));

                if (showMoreButton.isDisplayed()) {
                    // Scroll to the "Show more" button and click it
                    js.executeScript("arguments[0].scrollIntoView(true);", showMoreButton);

                    showMoreButton.click();
                    // Wait for new products to load
                    Thread.sleep(1000);
                }else {
                    // Exit the loop if the button is not visible
                    return 0;//it has finished
                }
            }
        } catch (TimeoutException e) {
            //this clunky if statement kinda works, good enough
            if(driver.findElement(By.id("noProducts")).getText().contains("no"))   {  return 2;   }//empty page
            System.out.println("No 'Show more' button found or all products are loaded.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while loading all products: " + e.getMessage());
        }
        return 0;//exception output
    }
}