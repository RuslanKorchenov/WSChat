package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.dto.SignInDto;
import ru.itis.models.CookieValue;
import ru.itis.models.User;
import ru.itis.repositories.CookieValuesRepository;
import ru.itis.repositories.UsersRepository;
import ru.itis.services.interfaces.SignInService;

import javax.servlet.http.Cookie;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CookieValuesRepository cookieValuesRepository;

    @Override
    @Transactional
    public String signIn(SignInDto signInDto) {
        Optional<User> optionalUser = usersRepository.findByEmail(signInDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
                String value = UUID.randomUUID().toString();
                CookieValue cookieValue = CookieValue.builder()
                        .value(value)
                        .user(user)
                        .build();
                cookieValuesRepository.save(cookieValue);
                return value;
            }
            return "Incorrect password";
        }
        return "User not found";
    }
}
