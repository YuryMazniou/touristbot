package by.task.ontravelsolutions.bot;

import by.task.ontravelsolutions.model.City;
import by.task.ontravelsolutions.repository.CityRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class CityValidator {

    private boolean valid;
    private JSONObject jsonMessages;
    private City cityEntity;

    @Autowired
    private CityRepository cityRepository;

    public CityValidator() {
        jsonMessages = new JSONObject();
        valid = true;
    }

    public void validation(String cityName){

        City city = cityRepository.getByCityName(cityName);

        if (city == null){
            jsonMessages.put("response", "Извините ,такого города нет в списке...Попробуйте еще раз");
            valid = false;
        } else {
            cityEntity = city;
        }
        jsonMessages.put("status", valid ? "verified" : "failed");
    }


    public boolean isValid() {
        return valid;
    }

    public JSONObject getJsonMessages() {
        return jsonMessages;
    }

    public City getCityEntity() {
        return cityEntity;
    }
}
