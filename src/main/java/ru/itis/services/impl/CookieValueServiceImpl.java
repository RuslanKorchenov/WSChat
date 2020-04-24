package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.CookieValue;
import ru.itis.repositories.CookieValuesRepository;
import ru.itis.services.interfaces.CookieValueService;

import java.util.Optional;

@Service
public class CookieValueServiceImpl implements CookieValueService {
    @Autowired
    private CookieValuesRepository cookieValuesRepository;

    @Override
    public CookieValue get(String cookie) {
        Optional<CookieValue> optionalCookieValue = cookieValuesRepository.findByValue(cookie);
        return optionalCookieValue.orElse(null);
    }

    @Override
    public boolean isExist(String cookie) {
        Optional<CookieValue> optionalCookieValue = cookieValuesRepository.findByValue(cookie);
        return optionalCookieValue.isPresent();
    }
}
