package ru.itis.services.interfaces;

import ru.itis.models.CookieValue;

public interface CookieValueService {
    CookieValue get(String cookie);
    boolean isExist(String cookie);
}
