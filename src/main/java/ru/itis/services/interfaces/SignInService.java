package ru.itis.services.interfaces;

import ru.itis.dto.SignInDto;

import java.util.Optional;

public interface SignInService {
    String signIn(SignInDto signInDto);
}
