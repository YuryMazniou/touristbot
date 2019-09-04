package by.task.telegrambot;

import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.util.List;

import static by.task.telegrambot.util.JacksonObjectMapper.getMapper;

public class JsonUtil {
    public static <T> List<T> readValues(String json, Class<T> clazz) {
        ObjectReader reader = getMapper().readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }
}
