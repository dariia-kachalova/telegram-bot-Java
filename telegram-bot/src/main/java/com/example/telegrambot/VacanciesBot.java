package com.example.telegrambot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class VacanciesBot extends TelegramLongPollingBot {

    //5632626290:AAHPhniTreatS2wQ1JQvSXy-__WoaOHADR4
    public VacanciesBot() {
        super("5632626290:AAHPhniTreatS2wQ1JQvSXy-__WoaOHADR4");
    }

    @Override
    public void onUpdateReceived(Update update) {
       try {
           if (update.getMessage() != null) {
               handleStartCommand(update);
           }
           if (update.getCallbackQuery() != null) {
               String callbackData = update.getCallbackQuery().getData();

               if ("showJuniorVacancies".equals(callbackData)) {
                   showJuniorVacancies(update);

               } else if (callbackData.startsWith("vacancyId=")){
                   String id = callbackData.split("=")[1];
                   showVacancyDescription(id, update);
               }

              if ("showMiddleVacancies".equals(callbackData)) {
                   showMiddleVacancies(update);
              // } else if (callbackData.startsWith("vacancyId=")){
                  // String id = callbackData.split("=")[1];
                //   showVacancyDescription(id, update);
               }

               if ("showSeniorVacancies".equals(callbackData)) {
                   showSeniorVacancies(update);
             //  } else if (callbackData.startsWith("vacancyId=")){
              //     String id = callbackData.split("=")[1];
              //     showVacancyDescription(id, update);
               }
           }
       }
           catch (Exception e){
               throw new RuntimeException("Can't send message to user", e);
           }

    }
    private void showVacancyDescription(String id, Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText("Vacancy description for vacancy with id=" +id);
        execute(sendMessage);
    }
    private void showJuniorVacancies(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose the vacancy: ");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getJuniorVacanciesMenu());
        execute(sendMessage);
    }

    private void showMiddleVacancies(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose the vacancy: ");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getMiddleVacanciesMenu());
        execute(sendMessage);
    }

    private void showSeniorVacancies(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Please choose the vacancy: ");
        sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setReplyMarkup(getSeniorVacanciesMenu());
        execute(sendMessage);
    }
    private ReplyKeyboard getJuniorVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton maVacancy = new InlineKeyboardButton();
        maVacancy.setText("Junior Java developer at MA");
        maVacancy.setCallbackData("vacancyId=1");
        row.add(maVacancy);

        InlineKeyboardButton googleVacancy = new InlineKeyboardButton();
        googleVacancy.setText("Junior Java dev at Google");
        googleVacancy.setCallbackData("vacancyId=2");
        row.add(googleVacancy);


        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));

        return keyboard;
    }

    private ReplyKeyboard getMiddleVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton maVacancy = new InlineKeyboardButton();
        maVacancy.setText("Middle Java developer at MA");
        maVacancy.setCallbackData("vacancyId=3");
        row.add(maVacancy);

        InlineKeyboardButton googleVacancy = new InlineKeyboardButton();
        googleVacancy.setText("Middle Java dev at Google");
        googleVacancy.setCallbackData("vacancyId=4");
        row.add(googleVacancy);


        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));

        return keyboard;
    }

    private ReplyKeyboard getSeniorVacanciesMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton maVacancy = new InlineKeyboardButton();
        maVacancy.setText("Senior Java developer at MA");
        maVacancy.setCallbackData("vacancyId=5");
        row.add(maVacancy);

        InlineKeyboardButton googleVacancy = new InlineKeyboardButton();
        googleVacancy.setText("Senior Java dev at Google");
        googleVacancy.setCallbackData("vacancyId=6");
        row.add(googleVacancy);


        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));

        return keyboard;
    }

    private void handleStartCommand(Update update){
        String text = update.getMessage().getText();
        System.out.println("Received text is "+ text);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Welcome to the Vacancy Bot. Please, choose your title: ");
        sendMessage.setReplyMarkup(getStartMenu());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ReplyKeyboard getStartMenu() {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton junior = new InlineKeyboardButton();
        junior.setText("Junior");
        junior.setCallbackData("showJuniorVacancies");
        row.add(junior);

        InlineKeyboardButton middle = new InlineKeyboardButton();
        middle.setText("Middle");
        middle.setCallbackData("showMiddleVacancies");
        row.add(middle);

        InlineKeyboardButton senior = new InlineKeyboardButton();
        senior.setText("Senior");
        senior.setCallbackData("showSeniorVacancies");
        row.add(senior);


        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row));

        return keyboard;
    }

    @Override
    public String getBotUsername() {
        return "Dariia Vacancies Bot";
    }
}
