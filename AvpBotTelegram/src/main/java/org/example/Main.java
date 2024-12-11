package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import slf4j
public class Main {
    public static void main(String[] args) {
        try {
            String botToken = "8084141518:AAG1KfsjjV2weofReaBnniG-RUiH-p40EII";
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(botToken, new TelBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}