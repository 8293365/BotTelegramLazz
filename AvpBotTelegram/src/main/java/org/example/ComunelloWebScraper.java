package org.example;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComunelloWebScraper {

    private static final String BASE_URL = "https://www.comunello.com";
    private static final String PRODUCTS_PAGE_URL = BASE_URL + "/gate/products/cantilever-gate/";
    private static final String[] CATEGORIES_URL = {"/sliding-gate","cantilever-gate","telescopic-gate","swing-gate",
        "pedestrian-gate","bifolding-gate","rising-hinges","steel-doors-and-windows","sliding-door","folding-doors"};

    public ComunelloWebScraper(){
        return;
    }

    private static String getCategory(int category){
        return CATEGORIES_URL[category];}

    public static void main(String[] args) {
        String productName = "CG-348-M20"; // Replace with the desired product name
        WebDriver driver = new ChromeDriver();

        try {
            String pdfLink = findProductPdfLink(driver, productName);
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
        driver.get(PRODUCTS_PAGE_URL);

        // Handle the overlay
        handleOverlay(driver);

        By select = By.cssSelector(".wpb_wrapper a");
        List<WebElement> productTypes = driver.findElements(select);

        // Use a Set to track unique href attributes and collect unique WebElements
        Set<String> uniqueHrefs = new HashSet<>();
        List<String> validUrls = new ArrayList<>();

        // Filter valid URLs and skip unwanted pages
        for (WebElement element : productTypes) {
            String href = element.getDomAttribute("href");
            String text = element.getText().toLowerCase();

            if (href != null && !text.contains("bullet") && !text.contains("integrator") && uniqueHrefs.add(href)) {
                validUrls.add(href);
            } else {
                System.out.println("Skipping unwanted page: " + (href != null ? href : text));
            }
        }

        // Navigate to each valid URL
        for (String url : validUrls) {
            try {
                System.out.println("Navigating to: " + url);
                driver.get(url);

                // Handle overlay if present
                handleOverlay(driver);

                // Look for the product
                boolean productFound = false;
                List<WebElement> productLinks = driver.findElements(By.cssSelector("a"));
                for (WebElement link : productLinks) {
                    if (link.getText().toLowerCase().contains(productName.toLowerCase())) {
                        productFound = true;
                        link.click();

                        // Ensure the page has loaded
                        new WebDriverWait(driver, Duration.ofSeconds(2))
                                .until(ExpectedConditions.urlContains(productName.toLowerCase().replace(".", "-")));

                        // Look for PDF links
                        List<WebElement> pdfLinks = driver.findElements(By.cssSelector("a[href$='.pdf']"));
                        if (!pdfLinks.isEmpty()) {
                            return pdfLinks.get(0).getDomAttribute("href");
                        } else {
                            System.out.println("PDF link not found on the product page.");
                            return null;
                        }
                    }
                }

                if (!productFound) {
                    System.out.println("Product \"" + productName + "\" not found on this page.");
                }
            } catch (StaleElementReferenceException e) {
                System.err.println("StaleElementReferenceException encountered. Skipping this URL: " + url);
            }
        }

        System.out.println("Product \"" + productName + "\" not found on the products page.");
        return null;
    }

    public static void handleOverlay(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement overlay = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("iubenda-cs-banner")));
            WebElement rejectButton = overlay.findElement(By.cssSelector(".iubenda-cs-reject-btn.iubenda-cs-btn-primary"));
            rejectButton.click();
            Thread.sleep(1000); // Wait for the overlay to disappear
        } catch (Exception e) {
            System.out.println("No overlay detected or failed to close it: " + e.getMessage());
        }
    }
}
