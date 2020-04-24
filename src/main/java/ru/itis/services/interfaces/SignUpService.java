package ru.itis.services.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.SignUpDto;
import ru.itis.repositories.UsersRepository;

public interface SignUpService {
    boolean signUp(SignUpDto signUpDto);
}
