/*
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
import java.util.List;

public class TelBot implements LongPollingSingleThreadUpdateConsumer {
//this is all for today, have fun tomorrow moron
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

    private void handleCallbackQuery(String callbackData, long chatId, long messageId) {
        if ("urmet".equals(callbackData)) {
            editMessageText(chatId, messageId, "Urmet Categories:", createUrmetCategories());
        } else if ("comunello".equals(callbackData)) {
            editMessageText(chatId, messageId, "Comunello Categories:", createComunelloCategories());
        } else if ("bft".equals(callbackData)) {
            editMessageText(chatId, messageId, "BFT Categories:", createBftCategories());
        } else if ("back_to_main".equals(callbackData)) {
            sendMainMenu(chatId);
        }
    }

    private InlineKeyboardMarkup createUrmetCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Videocitofonia", "Telefonia", "Smart Home", "Videosorveglianza", "Antintrusione", "Antincendio", "Controllo Accessi", "Serie Civile", "Sistemi di distribuzione", "Back to Main Menu"},
                new String[]{"urmet_videocitofonia", "urmet_telefonia", "urmet_smart_home", "urmet_videosorveglianza", "urmet_antintrusione", "urmet_antincendio", "urmet_controllo_accessi", "urmet_serie_civile", "urmet_sistemi_di_distribuzione", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createComunelloCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Sliding Gate", "Cantilever Gate", "Telescopic Gate", "Swing Gate", "Pedestrian Gate", "Bifolding Gate", "Rising Hinges", "Steel Doors and Windows", "Sliding Doors", "Folding Door", "Back to Main Menu"},
                new String[]{"comunello_sliding_gate", "comunello_cantilever_gate", "comunello_telescopic_gate", "comunello_swing_gate", "comunello_pedestrian_gate", "comunello_bifolding_gate", "comunello_rising_hinges", "comunello_steel_doors_and_windows", "comunello_sliding_doors", "comunello_folding_door", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createBftCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Busqueda", "Back to Main Menu"},
                new String[]{"bft_busqueda", "back_to_main"}
        );
    }

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
*/
/*
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
import java.util.List;

public class TelBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private static final String botToken = "8084141518:AAG1KfsjjV2weofReaBnniG-RUiH-p40EII";
    private static final String botName = "AccesoVelozPdf_bot";

    private String userAnswer = null; // To store the user's answer

    public TelBot() {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            // Process user text input
            handleUserAnswer(chatId, messageText);
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();

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

    public String getUserAnswer() {
        return userAnswer; // Method to get the user's last answer
    }

    private void sendMainMenu(long chatId) {
        String text = "Choose a product category:";
        InlineKeyboardMarkup keyboardMarkup = createInlineKeyboardMarkup(
                new String[]{"Productos Urmet", "Productos Comunello", "Productos bft"},
                new String[]{"urmet", "comunello", "bft"}
        );
        sendMessage(chatId, text, keyboardMarkup);
    }

    private void handleCallbackQuery(String callbackData, long chatId, long messageId) {
        if ("urmet".equals(callbackData)) {
            editMessageText(chatId, messageId, "Urmet Categories:", createUrmetCategories());
        } else if ("comunello".equals(callbackData)) {
            editMessageText(chatId, messageId, "Comunello Categories:", createComunelloCategories());
        } else if ("bft".equals(callbackData)) {
            editMessageText(chatId, messageId, "BFT Categories:", createBftCategories());
        } else if ("back_to_main".equals(callbackData)) {
            sendMainMenu(chatId);
        }
    }

    private void handleUserAnswer(long chatId, String messageText) {
        userAnswer = messageText; // Store the user's answer
        sendMessage(chatId, "You said: " + userAnswer, null); // Echo the answer back to the user
    }

    private InlineKeyboardMarkup createUrmetCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Videocitofonia", "Telefonia", "Smart Home", "Videosorveglianza", "Antintrusione", "Antincendio", "Controllo Accessi", "Serie Civile", "Sistemi di distribuzione", "Back to Main Menu"},
                new String[]{"urmet_videocitofonia", "urmet_telefonia", "urmet_smart_home", "urmet_videosorveglianza", "urmet_antintrusione", "urmet_antincendio", "urmet_controllo_accessi", "urmet_serie_civile", "urmet_sistemi_di_distribuzione", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createComunelloCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Sliding Gate", "Cantilever Gate", "Telescopic Gate", "Swing Gate", "Pedestrian Gate", "Bifolding Gate", "Rising Hinges", "Steel Doors and Windows", "Sliding Doors", "Folding Door", "Back to Main Menu"},
                new String[]{"comunello_sliding_gate", "comunello_cantilever_gate", "comunello_telescopic_gate", "comunello_swing_gate", "comunello_pedestrian_gate", "comunello_bifolding_gate", "comunello_rising_hinges", "comunello_steel_doors_and_windows", "comunello_sliding_doors", "comunello_folding_door", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createBftCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Busqueda", "Back to Main Menu"},
                new String[]{"bft_busqueda", "back_to_main"}
        );
    }




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
}*/






















/*


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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private static final String botToken = "8084141518:AAG1KfsjjV2weofReaBnniG-RUiH-p40EII";
    private static final String botName = "AccesoVelozPdf_bot";
    protected static String userAnswer;
    protected static String category;
    protected static String seller;

    // Map to track user states
    private final Map<Long, String> userStates = new HashMap<>();

    public TelBot() {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if ("/start".equals(messageText)) {
                userStates.put(chatId, "MAIN_MENU");
                sendMainMenu(chatId);
            } else if ("WAITING_FOR_CATEGORY_SELECTION".equals(userStates.get(chatId))) {
                handleUserAnswer(chatId);//, messageText);
            } else {
                sendEcho(chatId, "Please use the buttons to navigate.");
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
                new String[]{"Productos Urmet", "Productos Comunello", "Productos BFT"},
                new String[]{"urmet", "comunello", "bft"}
        );
        sendMessage(chatId, text, keyboardMarkup);
    }


    private void handleCallbackQuery(String callbackData, long chatId, long messageId) {
        if ("urmet".equals(callbackData)) {
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            editMessageText(chatId, messageId, "Urmet Categories:", createUrmetCategories());
        } else if ("comunello".equals(callbackData)) {
            editMessageText(chatId, messageId, "Comunello Categories:", createComunelloCategories());
        } else if ("bft".equals(callbackData)) {
            editMessageText(chatId, messageId, "BFT Categories:", createBftCategories());
        } else if (callbackData.startsWith("urmet_")) {
            category = callbackData.replace("urmet_", "");
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            seller = "urmet";
            sendMessage(chatId, "Selected category: " + category + ". Please type your answer.");
        } else if (callbackData.startsWith("bft_")) {
            category = callbackData.replace("bft_", "");
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            seller = "bft";
            sendMessage(chatId, "Selected category: " + category + ". Please type your answer.");
        } else if (callbackData.startsWith("comunello_")) {
            category = callbackData.replace("comunello_", "");
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            seller = "comunello";
            sendMessage(chatId, "Selected category: " + category + ". Please type your answer.");
        } else if ("back_to_main".equals(callbackData)) {
            userStates.put(chatId, "MAIN_MENU");
            sendMainMenu(chatId);
        }
    }

    private void handleUserAnswer(long chatId) {
        sendMessage(chatId, "You entered: " + this.userAnswer);
        userStates.put(chatId, "MAIN_MENU"); // Reset state after handling input
        sendMainMenu(chatId); // Return to the main menu
    }

    private InlineKeyboardMarkup createUrmetCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Videocitofonia", "Telefonia", "Smart Home", "Videosorveglianza", "Antintrusione", "Antincendio", "Controllo Accessi", "Serie Civile", "Sistemi di distribuzione", "Back to Main Menu"},
                new String[]{"urmet_videocitofonia", "urmet_telefonia", "urmet_smart_home", "urmet_videosorveglianza", "urmet_antintrusione", "urmet_antincendio", "urmet_controllo_accessi", "urmet_serie_civile", "urmet_sistemi_di_distribuzione", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createComunelloCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Sliding Gate", "Cantilever Gate", "Telescopic Gate", "Swing Gate", "Pedestrian Gate", "Bifolding Gate", "Rising Hinges", "Steel Doors and Windows", "Sliding Doors", "Folding Door", "Back to Main Menu"},
                new String[]{"comunello_sliding_gate", "comunello_cantilever_gate", "comunello_telescopic_gate", "comunello_swing_gate", "comunello_pedestrian_gate", "comunello_bifolding_gate", "comunello_rising_hinges", "comunello_steel_doors_and_windows", "comunello_sliding_doors", "comunello_folding_door", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createBftCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Busqueda", "Back to Main Menu"},
                new String[]{"bft_busqueda", "back_to_main"}
        );
    }

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

    private void sendMessage(long chatId, String text) {
        sendMessage(chatId, text, null);
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

    private void sendEcho(long chatId, String text) {
        sendMessage(chatId, text, null);
    }

    public String[] getSearchData(){
        try {
            String[] data = new String[3];
            if (userAnswer != null && category != null && seller != null) {
                data[0] = userAnswer;
                data[1] = category;
                data[2] = seller;
            } else {
                return null;
            }
            return data;
            //some function must be added to make a secure "fetch of the strings"
        } catch (RuntimeException e) {
            System.out.println("something's happened");
            throw new RuntimeException(e);
        }
    }
}
*/

























































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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private static final String botToken = "8084141518:AAG1KfsjjV2weofReaBnniG-RUiH-p40EII";
    private static final String botName = "AccesoVelozPdf_bot";
    private static String userAnswer;
    private static String category;
    private static String seller;

    // Map to track user states
    private final Map<Long, String> userStates = new HashMap<>();

    public TelBot() {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public synchronized void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if ("/start".equals(messageText)) {
                userStates.put(chatId, "MAIN_MENU");
                sendMainMenu(chatId);
            } else if ("WAITING_FOR_CATEGORY_SELECTION".equals(userStates.get(chatId))) {
                userAnswer = messageText; // Save user input
                sendMessage(chatId, "You entered: " + userAnswer);
                userStates.put(chatId, "MAIN_MENU"); // Reset state
                sendMainMenu(chatId);
            } else {
                sendEcho(chatId, "Please use the buttons to navigate.");
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

    public static String staticGetBotUsername() {
        return botName;
    }

    private void sendMainMenu(long chatId) {
        String text = "Choose a product category:";
        InlineKeyboardMarkup keyboardMarkup = createInlineKeyboardMarkup(
                new String[]{"Productos Urmet", "Productos Comunello", "Productos BFT"},
                new String[]{"urmet", "comunello", "bft"}
        );
        sendMessage(chatId, text, keyboardMarkup);
    }
/*
    private void handleCallbackQuery(String callbackData, long chatId, long messageId) {
        if (callbackData.startsWith("urmet_")) {
            category = callbackData.replace("urmet_", "");
            seller = "urmet";
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            sendMessage(chatId, "Selected category: " + category + ". Please type your answer.");
        } else if (callbackData.startsWith("comunello_")) {
            category = callbackData.replace("comunello_", "");
            seller = "comunello";
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            sendMessage(chatId, "Selected category: " + category + ". Please type your answer.");
        } else if (callbackData.startsWith("bft_")) {
            category = callbackData.replace("bft_", "");
            seller = "bft";
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            sendMessage(chatId, "Selected category: " + category + ". Please type your answer.");
        } else if ("back_to_main".equals(callbackData)) {
            userStates.put(chatId, "MAIN_MENU");
            sendMainMenu(chatId);
        }
    }
*/

    private void handleCallbackQuery(String callbackData, long chatId, long messageId) {
        if ("urmet".equals(callbackData)) {
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            editMessageText(chatId, messageId, "Urmet Categories:", createUrmetCategories());
        } else if ("comunello".equals(callbackData)) {
            editMessageText(chatId, messageId, "Comunello Categories:", createComunelloCategories());
        } else if ("bft".equals(callbackData)) {
            editMessageText(chatId, messageId, "BFT Categories:", createBftCategories());
        } else if (callbackData.startsWith("urmet_")) {
            category = callbackData.replace("urmet_", "");
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            seller = "urmet";
            sendMessage(chatId, "Selected category: " + category + ". Please type your answer.");
        } else if (callbackData.startsWith("bft_")) {
            category = callbackData.replace("bft_", "");
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            seller = "bft";
            sendMessage(chatId, "Selected category: " + category + ". Please type your answer.");
        } else if (callbackData.startsWith("comunello_")) {
            category = callbackData.replace("comunello_", "");
            userStates.put(chatId, "WAITING_FOR_CATEGORY_SELECTION");
            seller = "comunello";
            sendMessage(chatId, "Selected category: " + category + ". Please type your answer.");
        } else if ("back_to_main".equals(callbackData)) {
            userStates.put(chatId, "MAIN_MENU");
            sendMainMenu(chatId);
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


    private void handleUserAnswer(long chatId) {
        sendMessage(chatId, "You entered: " + this.userAnswer);
        userStates.put(chatId, "MAIN_MENU"); // Reset state after handling input
        sendMainMenu(chatId); // Return to the main menu
    }

    private InlineKeyboardMarkup createUrmetCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Videocitofonia", "Telefonia", "Smart Home", "Videosorveglianza", "Antintrusione", "Antincendio", "Controllo Accessi", "Serie Civile", "Sistemi di distribuzione", "Back to Main Menu"},
                new String[]{"urmet_videocitofonia", "urmet_telefonia", "urmet_smart_home", "urmet_videosorveglianza", "urmet_antintrusione", "urmet_antincendio", "urmet_controllo_accessi", "urmet_serie_civile", "urmet_sistemi_di_distribuzione", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createComunelloCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Sliding Gate", "Cantilever Gate", "Telescopic Gate", "Swing Gate", "Pedestrian Gate", "Bifolding Gate", "Rising Hinges", "Steel Doors and Windows", "Sliding Doors", "Folding Door", "Back to Main Menu"},
                new String[]{"comunello_sliding_gate", "comunello_cantilever_gate", "comunello_telescopic_gate", "comunello_swing_gate", "comunello_pedestrian_gate", "comunello_bifolding_gate", "comunello_rising_hinges", "comunello_steel_doors_and_windows", "comunello_sliding_doors", "comunello_folding_door", "back_to_main"}
        );
    }

    private InlineKeyboardMarkup createBftCategories() {
        return createInlineKeyboardMarkup(
                new String[]{"Busqueda", "Back to Main Menu"},
                new String[]{"bft_busqueda", "back_to_main"}
        );
    }



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

    private void sendMessage(long chatId, String text) {
        sendMessage(chatId, text, null);
    }

    private void sendEcho(long chatId, String text) {
        sendMessage(chatId, text, null);
    }

    // Synchronized method to retrieve the data
    public synchronized String[] getSearchData() {
        return new String[]{userAnswer, category, seller};
    }
}




