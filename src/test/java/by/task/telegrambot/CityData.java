package by.task.telegrambot;

import by.task.telegrambot.model.City;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static by.task.telegrambot.TestUtil.readListFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;

public class CityData {
    public static final City CITY1=new City(100000,"Москва","Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))");
    public static final City CITY2=new City(100001,"Минск","Зайдите на Зыбу,там весело)))");

    public static ResultMatcher contentJson(City ... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, City.class), List.of(expected));
    }

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
