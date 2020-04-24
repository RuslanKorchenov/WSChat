package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.SignInDto;
import ru.itis.services.interfaces.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInController {
    @Autowired
    private SignInService signInService;

    @GetMapping("/signIn")
    public String signIn(){
        return "signIn";
    }

    @PostMapping("/signIn")
    public String signIn(SignInDto signInDto, HttpServletResponse response, Model model) {
        String cookieVal = signInService.signIn(signInDto);

        if(cookieVal.equals("Incorrect password") || cookieVal.equals("User not found")) {
            model.addAttribute("error", cookieVal);
            return "signIn";
        }
        Cookie cookie = new Cookie("Authentication", cookieVal);
        response.addCookie(cookie);
        return "redirect:/rooms";
    }
}
