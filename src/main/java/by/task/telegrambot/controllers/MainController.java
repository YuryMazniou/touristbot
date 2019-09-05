package by.task.telegrambot.controllers;
import by.task.telegrambot.model.City;
import by.task.telegrambot.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static by.task.telegrambot.util.ValidationUtil.checkNotFound;
import static by.task.telegrambot.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = MainController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {
    private static Logger log = LoggerFactory.getLogger(MainController.class);

    static final String REST_URL="/cities";

    private final CityRepository cityRepository;

    public MainController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public List<City> getAll() {
        log.info("get all cities");
        return cityRepository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<City> create(@RequestBody City city) {
        log.info("create city");
        Assert.notNull(city, "meal must not be null");
        City created=cityRepository.save(city);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{id}")
    public City get(@PathVariable Long id) {
        log.info("get  city by id={}",id);
        return checkNotFound(cityRepository.findById(id).orElse(null),"Нет такого id="+id+" в базе данных");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestBody City newCity, @PathVariable Long id) {
        log.info("update city by id={}",id);
        City city= checkNotFound(cityRepository.findById(id).orElse(null),"Нет такого id="+id+" в базе данных");
        city.setCity(newCity.getCity());
        city.setInfo(newCity.getInfo());
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("delete city by id={}",id);
        checkNotFoundWithId(cityRepository.delete(id)!=0,id);
    }
}
