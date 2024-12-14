package org.example;

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