
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

//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
//import org.telegram.telegrambots.bots.LongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TelBot1 implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private static final String botToken = "8084141518:AAG1KfsjjV2weofReaBnniG-RUiH-p40EII";
    private static final String botName = "AccesoVelozPdf_bot";

    public TelBot1() {
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

    public static String staticGetBotToken() {
        return botToken;
    }

    public String getBotToken() {
        return botToken;
    }

    public static String staticGetBotUsername() {
        return botName;
    }

    public String getBotUsername() {
        return botName;
    }



    private void sendMainMenu(long chatId) {
        String text = "Choose a product category:";
        InlineKeyboardMarkup keyboardMarkup = createInlineKeyboardMarkup(
                new String[]{"Productos Urmet", "Productos Comunello", "Productos bft"},
                new String[]{"urmet", "comunello", "bft"}
        );
        sendMessage(chatId, text, keyboardMarkup);
    }




    /*
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
    }*/

    private void handleCallbackQuery(String callbackData, long chatId, long messageId) {
        if (callbackData.startsWith("urmet_")) {
            if (callbackData.startsWith("urmet_busqueda")) {
                String productName = extractProductName(callbackData);
                // Handle productName as needed
            } else {
                editMessageText(chatId, messageId, "Urmet Subcategories:", createUrmetSubCategories(callbackData.replace("urmet_", "")));
            }
        } else if (callbackData.startsWith("comunello_")) {
            if (callbackData.startsWith("comunello_busqueda")) {
                String productName = extractProductName(callbackData);
                // Handle productName as needed
            } else {
                editMessageText(chatId, messageId, "Comunello Subcategories:", createComunelloSubCategories(callbackData.replace("comunello_", "")));
            }
        } else if ("bft_busqueda".equals(callbackData)) {
            // Handle BFT-specific logic
        } else if ("back_to_main".equals(callbackData)) {
            sendMainMenu(chatId);
        }
    }

    private String extractProductName(String callbackData) {
        // Extract the product name from callback data, assuming format like "urmet_busqueda_SomeProductName"
        return callbackData.substring(callbackData.lastIndexOf('_') + 1);
    }











    private InlineKeyboardMarkup createUrmetCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Videocitofonia", "Telefonia", "Smart Home", "Videosorveglianza", "Antintrusione", "Antincendio", "Controllo Accessi", "Serie Civile", "Sistemi di distribuzione", "Back to Main Menu"},
                new String[]{"urmet_videocitofonia", "urmet_telefonia", "urmet_smart_home", "urmet_videosorveglianza", "urmet_antintrusione", "urmet_antincendio", "urmet_controllo_accessi", "urmet_serie_civile", "urmet_sistemi_di_distribuzione", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createUrmetSubCategories(String callerButton) {
        return createInlineKeyboardMarkup(
                new String[]{"Busqueda (" + callerButton + ")", "Back to Main Menu"},
                new String[]{"urmet_busqueda_" + callerButton, "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createComunelloCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Sliding Gate", "Cantilever Gate", "Telescopic Gate", "Swing Gate", "Pedestrian Gate", "Bifolding Gate", "Rising Hinges", "Steel Doors and Windows", "Sliding Doors", "Folding Door", "Back to Main Menu"},
                new String[]{"comunello_sliding_gate", "comunello_cantilever_gate", "comunello_telescopic_gate", "comunello_swing_gate", "comunello_pedestrian_gate", "comunello_bifolding_gate", "comunello_rising_hinges", "comunello_steel_doors_and_windows", "comunello_sliding_doors", "comunello_folding_door", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createComunelloSubCategories(String callerButton) {
        return createInlineKeyboardMarkup(
                new String[]{"Busqueda (" + callerButton + ")", "Back to Main Menu"},
                new String[]{"comunello_busqueda_" + callerButton, "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createBftCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Busqueda", "Back to Main Menu"},
                new String[]{"bft_busqueda", "back_to_main"}
        );
    }

/*
    private InlineKeyboardMarkup createInlineKeyboardMarkup(String[] buttonTexts, String[] callbackData) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (int i = 0; i < buttonTexts.length; i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(buttonTexts[i])
                    .callbackData(callbackData[i])
                    .build();
            rows.add(List.of(button));
        }
        return InlineKeyboardMarkup.builder().keyboard(rows).build();
    }*/

    private InlineKeyboardMarkup createInlineKeyboardMarkup(String[] buttonTexts, String[] callbackData) {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        for (int i = 0; i < buttonTexts.length; i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(buttonTexts[i])
                    .callbackData(callbackData[i])
                    .build();

            InlineKeyboardRow row = new InlineKeyboardRow();
            row.add(button);

            rows.add(row);
        }
        return InlineKeyboardMarkup.builder().keyboard(rows).build();
    }






/*
    private InlineKeyboardMarkup createUrmetCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Videocitofonia", "Telefonia","Smart Home","Videosorveglianza","Antintrusione","Antincendio","Controllo Accessi","Serie Civile","Sistemi di distribuzione", "Back to Main Menu"},
                new String[]{"urmet_videocitofonia", "urmet_telefonia","urmet_Smart Home","urmet_Videosorveglianza","urmet_Antintrusione","urmet_Antincendio","urmet_Controllo Accessi","urmet_Serie Civile","urmet_Sistemi di distribuzione", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createUrmetSubCategories(){
        return createInlineKeyboardMarkup(
                new String[]{"Busqueda", "Back to Main Menu"},
                new String[]{"Urmet_busqueda", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createComunelloCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"sliding gate", "cantilever gate","telescopic gate","swing gate","pedestrian gate","bifolding gate","rising hinges","steel doors and windows","sliding doors","folding door", "Back to Main Menu"},
                new String[]{"comunello_sliding_gate", "comunello_cantilever_gate","comunello_telescopic gate","comunello_swing gate","comunello_pedestrian gate","comunello_bifolding gate","comunello_rising hinges","comunello_steel doors and windows","comunello_sliding doors","comunello_folding door", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createComunelloSubCategories(){
        return createInlineKeyboardMarkup(
                new String[]{"Busqueda", "Back to Main Menu"},
                new String[]{"Comunello_busqueda", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createBftCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Busqueda", "Back to Main Menu"},
                new String[]{"bft_busqueda", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createInlineKeyboardMarkup(String[] buttonTexts, String[] callbackData) {
        List<InlineKeyboardRow> keyboard = new ArrayList<>();
        for (int i = 0; i < buttonTexts.length; i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(buttonTexts[i])
                    .callbackData(callbackData[i])
                    .build();
            InlineKeyboardRow row = new InlineKeyboardRow();
            row.add(button);
            keyboard.add(row);
        }
        return new InlineKeyboardMarkup(keyboard);
    }*/

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
/*
    public static void main(String[] args) {
        TelBot1 bot = new TelBot1();
        long sampleChatId = 123456789L;
        long sampleMessageId = 111;

        // Simulate sending the main menu
        bot.sendMainMenu(sampleChatId);

        // Simulate handling a callback query for 'urmet' category
        bot.handleCallbackQuery("urmet", sampleChatId, sampleMessageId);

        // Simulate handling a callback query to go back to the main menu
        bot.handleCallbackQuery("back_to_main", sampleChatId, sampleMessageId);
    }
*/
    /*
    public static void main(String[] args) {
        try {
            // Initialize the Telegram Bots API
            TelBot1 botsApi = new TelBot1(DefaultBotSession.class);

            // Register your bot
            botsApi.registerBot(new TelBot1());

            System.out.println("Bot started successfully.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }*/
}
