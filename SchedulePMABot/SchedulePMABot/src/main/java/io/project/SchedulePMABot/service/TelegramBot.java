package io.project.SchedulePMABot.service;

import io.project.SchedulePMABot.config.BotConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.File;
import java.io.FileNotFoundException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    static final String ERROR_TEXT = "Error occurred: ";
    static final String HELP_TEXT =
            """
                    Це бот розкладу

                    Команди, які знає цей бот:

                    /start - якщо тобі хочеться, щоб він з тобою привітався

                    /schedule - підкаже розклад парів на сьогодні

                    /weekSchedule - підкаже розклад парів на тиждень

                    /examsSchedule - підкаже розклад екзаменів

                    /help - допоможе тобі розібратися з командами\s

                    """;

    public TelegramBot(BotConfig config){
        this.config=config;
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
    }

    private void weekSchedule(long chatId) {

    }

    private void scheduleToday(long chatId) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Format f = new SimpleDateFormat("EEEE");
        String dayOfWeek = f.format(new Date());
        String parity;
        if(weekOfYear()){
            parity = "Парний";
        }else{
            parity = "Не парний";
        }
        scheduleSwitch(chatId, cal, sdf, dayOfWeek, parity);
    }

    private void scheduleSwitch(long chatId, Calendar cal, SimpleDateFormat sdf, String dayOfWeek, String parity) {
        switch (dayOfWeek) {
            case "понеділок" ->
                    parityOfWeek(chatId, "Сьогодні - " + dayOfWeek + "\n\n" + sdf.format(cal.getTime()) + "\n\n" + "Тиждень - " + parity +"\n\n"+
                            "Розклад:\n\n" +
                            "Пара 3 - \n\n" +
                            "Пара 4 - \n\n" +
                            "Пара 5 - \n\n", "Сьогодні - " + dayOfWeek + "\n\n" + " " + sdf.format(cal.getTime()) + "Тиждень - " + parity +"\n\n"+
                            "Розклад:\n\n" +
                            "Пара 3 - \n\n" +
                            "Пара 4 - \n\n" +
                            "Пара 5 - \n\n");
            case "вівторок" ->
                    parityOfWeek(chatId, "Сьогодні - " + dayOfWeek + "\n\n" + sdf.format(cal.getTime()) + "\n\n" + "Тиждень - " + parity +"\n\n"+
                            "Розклад:\n\n" +
                            "Пара 2 - \n\n" +
                            "Пара 3 - \n\n" +
                            "Пара 4 - \n\n" +
                            "Пара 5 - \n\n", "Сьогодні - " + dayOfWeek + "\n\n" + " " + sdf.format(cal.getTime()) + "Тиждень - " + parity +"\n\n"+
                            "Розклад:\n\n" +
                            "Пара 2 - \n\n" +
                            "Пара 3 - \n\n" +
                            "Пара 4 - \n\n" +
                            "Пара 5 - \n\n");
            case "середа" ->
                    parityOfWeek(chatId, "Сьогодні - " + dayOfWeek + "\n\n" + sdf.format(cal.getTime()) + "\n\n" + "Тиждень - " + parity +"\n\n"+
                            "Розклад:\n\n" +
                            "Пара 1 - \n\n" +
                            "Пара 2 - \n\n" +
                            "Пара 3 - \n\n" +
                            "Пара 4 - \n\n", "Сьогодні - " + dayOfWeek + "\n\n" + " " + sdf.format(cal.getTime()) + "Тиждень - " + parity +"\n\n"+
                            "Розклад:\n\n" +
                            "Пара 1 - \n\n" +
                            "Пара 2 - \n\n" +
                            "Пара 3 - \n\n" +
                            "Пара 4 - \n\n");
            case "четвер" ->
                    parityOfWeek(chatId, "Сьогодні - " + dayOfWeek + "\n\n" + sdf.format(cal.getTime()) + "\n\n" + "Тиждень - " + parity +"\n\n"+
                            "Розклад:\n\n" +
                            "Пара 1 - \n\n" +
                            "Пара 2 - \n\n" +
                            "Пара 3 - \n\n", "Сьогодні - " + dayOfWeek + "\n\n" + " " + sdf.format(cal.getTime()) + "Тиждень - " + parity +"\n\n"+
                            "Розклад:\n\n" +
                            "Пара 1 - \n\n" +
                            "Пара 2 - \n\n" +
                            "Пара 3 - \n\n");
            case "пятниця" -> parityOfWeek(chatId,
                    "Сьогодні - " + dayOfWeek + "\n\n" + sdf.format(cal.getTime()) + "\n\n" + "Тиждень - " + parity +"\n\n"+
                            "Розклад:\n\n" +
                            "Пара 1 - \n\n" +
                            "Пара 2 - \n\n" +
                            "Пара 3 - \n\n",
                    "Сьогодні - " + dayOfWeek + "\n\n" + " " + sdf.format(cal.getTime()) + "Тиждень - " + parity +"\n\n"+
                            "Розклад:\n\n" +
                            "Пара 2 - \n\n" +
                            "Пара 3 - \n\n");
            default -> sendMessage(chatId, "Сьогодні вихідний");
        }
    }

    private void parityOfWeek(long chatId, String dayOfWeekOdd, String dayOfWeekPair) {
        if(!weekOfYear()){
            sendMessage(chatId,
                    dayOfWeekOdd);
        }else{
            sendMessage(chatId,
                    dayOfWeekPair);
        }
    }


    private boolean weekOfYear(){
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
        try {
            execute(message);
        }catch (TelegramApiException e){
            log.error(ERROR_TEXT+e.getMessage());
        }
    }

    private void sendPhoto(long chatId, String imageCaption, String imagePath) {
        try {
            SendPhoto sendPhoto = new SendPhoto().setPhoto(imagePath);
            sendPhoto.setChatId(chatId);
            sendPhoto.setCaption(imageCaption);
            execute(sendPhoto);
        } catch (FileNotFoundException | TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

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
