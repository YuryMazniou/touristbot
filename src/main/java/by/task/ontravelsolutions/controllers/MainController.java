package by.task.ontravelsolutions.controllers;

import by.task.ontravelsolutions.model.City;
import by.task.ontravelsolutions.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    private static final String RESPONSE_OK = "{\"status\":\"ok\"}";
    private static final String RESPONSE_FAIL = "{\"status\":\"fail\", \"message\":\"something goes wrong\"}";

    @Autowired
    private CityRepository cityRepository;

    @RequestMapping(value = "/getall", produces = "application/json;charset=utf-8")
    public List<City> getAll(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return cityRepository.findAll();
    }

    @RequestMapping(value = "/save", produces = "application/json;charset=utf-8")
    public String save(
            HttpServletResponse response,
            @RequestParam String cityName,
            @RequestParam String cityInfo) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        City city = new City(cityName, cityInfo);
        cityRepository.save(city);

        return RESPONSE_OK;
    }

    @RequestMapping(value = "/update", produces = "application/json; charset=utf-8")
    public String update(
            HttpServletResponse response,
            @RequestParam Integer id,
            @RequestParam String cityName,
            @RequestParam String cityInfo) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        City city = cityRepository.findById(id).orElse(null);
        if(city!=null){
            City cityUpdate=new City(id,cityName,cityInfo);
            cityRepository.save(cityUpdate);
            return RESPONSE_OK;
        }
        return RESPONSE_FAIL;
    }

    @RequestMapping(value = "/delete", produces = "application/json; charset=utf-8")
    public String delete(HttpServletResponse response, @RequestParam Integer id) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        City city = cityRepository.findById(id).orElse(null);
        if(city!=null){
            cityRepository.deleteById(id);
            return RESPONSE_OK;
        }
        return RESPONSE_FAIL;
    }
}
