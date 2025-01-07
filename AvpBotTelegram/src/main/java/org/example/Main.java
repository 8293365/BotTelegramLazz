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

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
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
                }

                // Sleep for a while before the next check
                Thread.sleep(1000);
            }

        } catch (TelegramApiException | InterruptedException e) {
            // Log the exception for better maintainability
            logger.log(Level.SEVERE, "Error occurred in Telegram bot aka 'the ship has sunk'", e);
        }
    }
}
