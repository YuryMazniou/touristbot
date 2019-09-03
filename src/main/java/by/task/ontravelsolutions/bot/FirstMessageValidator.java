package by.task.ontravelsolutions.bot;

import org.json.JSONObject;

public class FirstMessageValidator {
    private boolean firstMessage;
    private JSONObject jsonMessages;

    public FirstMessageValidator() {
        firstMessage = false;
        jsonMessages = new JSONObject();
    }

    public void FMValidation(String message){
        if (message.equals("/start")){
            firstMessage = true;
            jsonMessages.put("greeting", "Приветсвую вас! Мое имя FunnyTelegramBot!" +
                    " Если вы введете название города ,я смогу посоветовать место-где можно хорошо отдохнуть");
        }
    }

    public boolean isFirstMessage() {
        return firstMessage;
    }

    public JSONObject getJsonMessages() {
        return jsonMessages;
    }
}
