/*package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(TelBot.staticGetBotToken(), new TelBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();//it is not good for maintenance so think of smething in the future
        }

        System.out.println("The ship has sailed");

    }


}
*/
/*
package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            // Initialize the Telegram Long Polling Application
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

            // Register the bot using the token and bot instance
            String botToken = TelBot.staticGetBotToken();
            botsApplication.registerBot(botToken, new TelBot());

            System.out.println("The ship has sailed.");
        } catch (TelegramApiException e) {
            // Log the exception for better maintainability
            logger.log(Level.SEVERE, "Failed to register the Telegram bot.", e);
        }
    }
}
*/

package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            dbManager dbManager = new dbManager();
            // Initialize the Telegram Long Polling Application
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

            // Register the bot using the token and bot instance
            String botToken = TelBot.staticGetBotToken();
            TelBot telBot = new TelBot();
            botsApplication.registerBot(botToken, telBot);

            System.out.println("The ship has sailed.");

            // Continuously poll for search data
            while (true) {
                String[] searchData = telBot.getSearchData();
                String tmp = "";
                if (tmp != null && tmp!=searchData[0]) {
                //if (searchData != null && searchData[0] != null && searchData[1] != null && searchData[2] != null){
                    System.out.println("User Answer: " + searchData[0]);
                    System.out.println("Category: " + searchData[1]);
                    System.out.println("Seller: " + searchData[2]);
                    //break; // Break after processing data
                    tmp = searchData[0];
                    //heeeeey the bot needs to get the product data from the db not from the scraper
                    //message makes call-->main_checks_first_the_db-->scraping-->databse-->telbot-->answer (kinda long ngt)
                }

                // Sleep for a while before the next check
                Thread.sleep(1000);
            }

        } catch (TelegramApiException | InterruptedException e) {
            // Log the exception for better maintainability
            logger.log(Level.SEVERE, "Error occurred in Telegram bot aka 'the ship has sunk'", e);
        }
    }

        public static void getScraper(String scraper){//maybe like this


    }

    public static void pdfSender(){
        //

    }
}
*/

    public class Main implements TelBot.UserInputListener {


        private static final Logger logger = Logger.getLogger(Main.class.getName());

        static DbManager dbManager = new DbManager();

        public ComunelloWebScraper comunelloScraper = new ComunelloWebScraper();
        public bftWebScraper bftWebScraper = new bftWebScraper();
        public UrmetWebScraper urmetScraper = new UrmetWebScraper();

        @Override
        public void onUserInputReceived(String userAnswer, String category, String seller) {
            System.out.println("User Answer: " + userAnswer);
            System.out.println("Category: " + category);
            System.out.println("Seller: " + seller);


            //here we add all of the calls that we need
            TelBot.pdf_productLink = getPdf(userAnswer, category, seller);
        }


        public static void main(String[] args) {
            try {
                // Initialize the Telegram Long Polling Application
                TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();

                // Register the bot using the token and bot instance
                String botToken = TelBot.staticGetBotToken();
                TelBot telBot = new TelBot();
                botsApplication.registerBot(botToken, telBot);

                // Register the main class as a listener
                Main mainInstance = new Main();
                telBot.setUserInputListener(mainInstance);

                System.out.println("The bot is now running.");
            } catch (TelegramApiException e) {
                // Log the exception for better maintainability
                logger.log(Level.SEVERE, "Error occurred in Telegram bot", e);
            }
            dbManager.close();
        }

        public static String getPdf(String userAnswer, String category, String seller){
            String result ="";
            if (seller == "urmet"){
                org.example.bftWebScraper.getProductCode(category);
                bftScraper.getCategory(category);
                //result = bftScraper.getPdf();
            } else if (seller == "bft") {

            } else if (seller == "comunello"){}


            return result;
        }

        public static void getScraper(String scraper){//maybe like this


        }

        public static void pdfSender(){
            //

        }
    }


