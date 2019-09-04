package by.task.telegrambot.util;

import by.task.telegrambot.util.exception.NotFoundException;

public class ValidationUtil {

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg) {
        if (!found) {
            throw new NotFoundException(arg);
        }
    }

    public static void checkNotFoundWithId(boolean found, long id) {
        checkNotFound(found, "id=" + id);
    }
}
