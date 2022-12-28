package io.project.SchedulePMABot.service;

import io.project.SchedulePMABot.config.BotConfig;
import io.project.SchedulePMABot.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private static final String ERROR_TEXT = "Error occurred: ";
    private static final String HELP_TEXT =
            """
                    Це бот розкладу

                    Команди, які знає цей бот:

                    /start - якщо тобі хочеться, щоб він з тобою привітався

                    /schedule - підкаже розклад парів на сьогодні

                    /weekSchedule - підкаже розклад парів на тиждень

                    /examsSchedule - підкаже розклад екзаменів

                    /help - допоможе тобі розібратися з командами

                    """;


    public TelegramBot(BotConfig config){
        this.config=config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Вітання/Запуск"));
        listOfCommands.add(new BotCommand("/schedule", "Розклад"));
        listOfCommands.add(new BotCommand("/weekSchedule", "Розклад на тиждень"));
        listOfCommands.add(new BotCommand("/examsSchedule", "Розклад екзаменів"));
        listOfCommands.add(new BotCommand("/help", "Допомога"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String messageText;
        long chatId;
        if(update.hasMessage() && update.getMessage().hasText()){
            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start" ->
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                case "/help"->
                    prepareAndSendMessage(chatId);
                case "/schedule"->
                    scheduleToday(chatId);
                case "/weekSchedule"->
                    weekSchedule(chatId);
                case "/examsSchedule"->
                    examsSchedule(chatId);
                    default->
                    sendMessage(chatId, "Команда не підтримується, напиши щось нормальне");
            }
        }

    }

    private void examsSchedule(long chatId) {
//        switch (TextUtils.getSimpleDateFormat().toString()){
//            case "2-12-2022" ->
//                    sendMessage(chatId,TextUtils.FIRST_EXAM);
//            case "5-12-2022"->
//                    sendMessage(chatId,TextUtils.SECOND_EXAM);
//            case "9-12-2022"->
//                    sendMessage(chatId,TextUtils.THIRD_EXAM);
//            case "12-12-2022"->
//                    sendMessage(chatId,TextUtils.FOURTH_EXAM);
//            default ->
//                    sendMessage(chatId,TextUtils.HOLIDAY);
//        }
        sendMessage(chatId,TextUtils.EXAM_SCHEDULE);
    }

    private void weekSchedule(long chatId) {
        if (!weekOfYear()){
            sendMessage(chatId,TextUtils.WEEK_ODD);
        }else{
            sendMessage(chatId,TextUtils.WEEK_PAIR);
        }
    }
    public static String dayOfWeek(){
        Format f = new SimpleDateFormat("EEEE");
        return f.format(new Date());
    }
    private void scheduleToday(long chatId) {
        scheduleSwitch(chatId, dayOfWeek());
    }

    public static String parity(){
        String parity;
        if(weekOfYear()){
            parity = "Парний";
        }else{
            parity = "Не парний";
        }
        return parity;
    }

    private void scheduleSwitch(long chatId, String dayOfWeek) {
        switch (dayOfWeek) {
            case "понеділок" ->
                    parityOfWeek(chatId, TextUtils.GREETING_DAY_IN_SCHEDULE +
                                    TextUtils.MONDAY_ODD,
                            TextUtils.GREETING_DAY_IN_SCHEDULE+
                                    TextUtils.MONDAY_PAIR);
            case "вівторок" ->
                    parityOfWeek(chatId,TextUtils.GREETING_DAY_IN_SCHEDULE+
                                    TextUtils.TUESDAY_ODD,
                            TextUtils.GREETING_DAY_IN_SCHEDULE+
                                    TextUtils.TUESDAY_PAIR);
            case "середа" ->
                    parityOfWeek(chatId,TextUtils.GREETING_DAY_IN_SCHEDULE+
                                    TextUtils.WEDNESDAY_ODD,
                            TextUtils.GREETING_DAY_IN_SCHEDULE+
                                    TextUtils.WEDNESDAY_PAIR);
            case "четвер" ->
                    parityOfWeek(chatId,TextUtils.GREETING_DAY_IN_SCHEDULE+
                                    TextUtils.THURSDAY_ODD,
                            TextUtils.GREETING_DAY_IN_SCHEDULE+
                                    TextUtils.THURSDAY_PAIR);
            case "пятниця" ->
                    parityOfWeek(chatId,TextUtils.GREETING_DAY_IN_SCHEDULE+
                                    TextUtils.FRIDAY_ODD,
                            TextUtils.GREETING_DAY_IN_SCHEDULE+
                                    TextUtils.FRIDAY_PAIR);
            default ->
                    sendMessage(chatId, TextUtils.HOLIDAY);
        }
    }

    private void parityOfWeek(long chatId, String dayOfWeekOdd, String dayOfWeekPair) {
        if(!weekOfYear()){
            sendMessage(chatId, dayOfWeekOdd);
        }else{
            sendMessage(chatId, dayOfWeekPair);
        }
    }


    private static boolean weekOfYear(){
        Calendar cal = Calendar.getInstance();
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        return weekOfYear % 2 == 0;
    }
    private void startCommandReceived(long chatId, String name){
        String answer = "Привіт, " + name + ", радий бачити!";
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> keyboardRows = new ArrayList<>();
//        KeyboardRow row = new KeyboardRow();
//
//        row.add("/start");
//        row.add("/help");
//        row.add("/schedule");
//        row.add("/weekSchedule");
//        row.add("/examsSchedule");
//
//        keyboardRows.add(row);
//
//        replyKeyboardMarkup.setKeyboard(keyboardRows);
//        message.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(message);
        }catch (TelegramApiException e){
            log.error(ERROR_TEXT+e.getMessage());
        }
    }

//    private void sendPhoto(long chatId, String imageCaption, String imagePath) {
//        try {
//            SendPhoto sendPhoto = new SendPhoto().setPhoto(imagePath);
//            sendPhoto.setChatId(chatId);
//            sendPhoto.setCaption(imageCaption);
//            execute(sendPhoto);
//        } catch (FileNotFoundException | TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    private void prepareAndSendMessage(long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(TelegramBot.HELP_TEXT);
        executeMessage(message);
    }
    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

}
