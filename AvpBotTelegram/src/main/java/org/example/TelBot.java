package org.example;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
//SLF4J stands for Simple Logging Facade for Java


public class TelBot implements LongPollingSingleThreadUpdateConsumer {

    private final static String token = "8084141518:AAG1KfsjjV2weofReaBnniG-RUiH-p40EII";
    private final static String botName ="AccesoVelozPdf_bot";


    private TelegramClient telegramClient;

    public TelBot(String botToken){
        telegramClient = new OkHttpTelegramClient(botToken);
    }
    public TelBot(){
        telegramClient = new OkHttpTelegramClient(TelBot.staticGetBotToken());
    }

    @Override
    public void consume(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    /*
    update.getMessage().getFrom()
    //@Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();

        System.out.println(user.getFirstName() + " wrote " + msg.getText());
    }
    */


    //@Override
    public static String staticGetBotUsername() {
        return botName;
    }

    public String getBotUsername() {
        return botName;
    }

    //@Override
    public static String staticGetBotToken() {
        return token;
    }

    public String getBotToken() {
        return token;
    }


}