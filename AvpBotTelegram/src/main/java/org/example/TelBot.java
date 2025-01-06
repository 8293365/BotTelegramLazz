package org.example;


import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TelBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private static final String botToken = "8084141518:AAG1KfsjjV2weofReaBnniG-RUiH-p40EII";
    private static final String botName = "AccesoVelozPdf_bot";

    public TelBot() {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if ("/start".equals(messageText)) {
                sendMainMenu(chatId);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            handleCallbackQuery(callbackData, chatId, messageId);
        }
    }
    //@Override
    /*
    public static String staticGetBotUsername() {
        return botName;
    }

    public String getBotUsername() {
        return botName;
    }*/

    //@Override
    public String getBotUsername() {
        return botName;
    }

    public static String staticgetBotUsername() {
        return botName;
    }

    //@Override
    public static String staticGetBotToken() {
        return botToken;
    }

    public String getBotToken() {
        return botToken;
    }
    /*
    private void sendMainMenu(long chatId) {
        String text = "Choose a product category:";
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        rows.add(createButtonRow("ProductosUrmet", "urmet"));
        rows.add(createButtonRow("ProductosComunello", "comunello"));
        rows.add(createButtonRow("Productosbft", "bft"));

        keyboardMarkup.setKeyboard(rows);
        sendMessage(chatId, text, keyboardMarkup);
    }*/

    private void sendMainMenu(long chatId) {
        String text = "Choose a product category:";

        // Create buttons
        InlineKeyboardButton urmetButton = InlineKeyboardButton.builder()
                .text("ProductosUrmet")
                .callbackData("urmet")
                .build();

        InlineKeyboardButton comunelloButton = InlineKeyboardButton.builder()
                .text("ProductosComunello")
                .callbackData("comunello")
                .build();

        InlineKeyboardButton bftButton = InlineKeyboardButton.builder()
                .text("Productosbft")
                .callbackData("bft")
                .build();

        // Create rows of buttons
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(urmetButton);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(comunelloButton);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(bftButton);

        // Combine rows into a list
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        // Create the keyboard markup
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboard);

        //InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();

        // Send the message with the inline keyboard
        sendMessage(chatId, text, keyboardMarkup);
    }


    private void handleCallbackQuery(String callbackData, long chatId, long messageId) {
        switch (callbackData) {
            case "urmet":
                editMessageText(chatId, messageId, "ProductosUrmet Categories:", createUrmetCategories());
                break;
            case "comunello":
                editMessageText(chatId, messageId, "ProductosComunello Categories:", createComunelloCategories());
                break;
            case "bft":
                editMessageText(chatId, messageId, "Productosbft Categories:", createBftCategories());
                break;
            case "back_to_main":
                sendMainMenu(chatId);
                break;
            // Handle other callback data as needed
        }
    }

    private InlineKeyboardMarkup createUrmetCategories() {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(createButtonRow("Videocitofonia", "urmet_videocitofonia"));
        rows.add(createButtonRow("Telefonia", "urmet_telefonia"));
        // Add other categories similarly
        rows.add(createButtonRow("Back to Main Menu", "back_to_main"));
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    private InlineKeyboardMarkup createComunelloCategories() {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(createButtonRow("SLIDING GATE", "comunello_sliding_gate"));
        rows.add(createButtonRow("CANTILEVER GATE", "comunello_cantilever_gate"));
        // Add other categories similarly
        rows.add(createButtonRow("Back to Main Menu", "back_to_main"));
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    private InlineKeyboardMarkup createBftCategories() {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(createButtonRow("Busqueda", "bft_busqueda"));
        rows.add(createButtonRow("Back to Main Menu", "back_to_main"));
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }

    private List<InlineKeyboardButton> createButtonRow(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        return row;
    }

    private void sendMessage(long chatId, String text, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editMessageText(long chatId, long messageId, String text, InlineKeyboardMarkup keyboardMarkup) {
        EditMessageText message = EditMessageText.builder()
                .chatId(chatId)
                .messageId((int) messageId)
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
