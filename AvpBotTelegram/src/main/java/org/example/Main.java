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


}*/
package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;


public class Main {
    public static void main(String[] args) {

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(TelBot.staticGetBotToken(), new TelBot());
            System.out.println("MyAmazingBot successfully started!");
            Thread.currentThread().join();
            //do I make a function for each webscraper or do i arrange it in some other way?
            //the function should just be an empty function call to each of the other Webscraper classes which are selected
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    }
