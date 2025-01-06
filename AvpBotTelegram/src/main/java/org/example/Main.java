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