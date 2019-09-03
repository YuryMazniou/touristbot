package by.task.ontravelsolutions.bot;

import by.task.ontravelsolutions.model.City;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Bot extends TelegramLongPollingBot {

    public static void botStarter() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String cityName = message.getText();

        FirstMessageValidator messageValid = new FirstMessageValidator();
        messageValid.FMValidation(cityName);

        if (messageValid.isFirstMessage()){
            String greeting = messageValid.getJsonMessages().getString("greeting");
            sendMsg(message, greeting);
        } else {
            CityValidator validator = new CityValidator();
            validator.validation(cityName);

            if (validator.isValid()){
                City city = validator.getCityEntity();
                sendMsg(message, city.getInfo());
            } else{
                String response = validator.getJsonMessages().getString("response");
                sendMsg(message, response);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "FunnyTouristBot";
    }

    @Override
    public String getBotToken() {
        return "955845520:AAGPM8H_NZWT5pJxsz1aZ1MvsyQhTd3EmN0";
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setReplyToMessageId(message.getMessageId());

        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
