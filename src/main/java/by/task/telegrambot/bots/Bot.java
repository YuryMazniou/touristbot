package by.task.telegrambot.bots;

import by.task.telegrambot.model.City;
import by.task.telegrambot.repository.CityRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private final CityRepository cityRepository;

    public Bot(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String response;
        String message = update.getMessage().getText();
        System.out.println(update.toString());
        System.out.println(message);
        City city = cityRepository.getByCityName(message);
        if (city==null){
            response="Такого города нет в моей базе данных,попробуйте снова";
        }
        else {
            response = city.getInfo();
        }
        sendMsg(update.getMessage().getChatId().toString(), response);
    }

    @Override
    public String getBotUsername() {
        return "FunnyTouristBot";
    }

    @Override
    public String getBotToken() {
        return "955845520:AAGPM8H_NZWT5pJxsz1aZ1MvsyQhTd3EmN0";
    }

    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
