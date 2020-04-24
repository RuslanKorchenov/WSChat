package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.RoomDto;
import ru.itis.models.Room;
import ru.itis.models.User;
import ru.itis.services.interfaces.ChatRoomService;
import ru.itis.services.interfaces.CookieValueService;
import ru.itis.services.interfaces.MessageService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ChatController {
    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private CookieValueService cookieValueService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/rooms")
    public ModelAndView getRooms(@CookieValue(value = "Authentication", required = false) String cookie) {
        ModelAndView modelAndView = new ModelAndView();
        ru.itis.models.CookieValue cookieValue = cookieValueService.get(cookie);
        if (cookieValue != null) {
            modelAndView.addObject("rooms", chatRoomService.getRooms());
            modelAndView.setViewName("rooms");
            return modelAndView;
        }
        modelAndView.setViewName("signIn");
        return modelAndView;
    }

    @PostMapping("/rooms")
    public String createRoom(@CookieValue(value = "Authentication", required = false) String cookie, RoomDto roomDto) {
        ru.itis.models.CookieValue cookieValue = cookieValueService.get(cookie);
        if (cookieValue != null) {
            chatRoomService.createRoom(roomDto);
            return "redirect:/rooms";
        }
        return "redirect:/signIn";
    }

    @GetMapping("/rooms/{room}")
    public ModelAndView getChat(@CookieValue(value = "Authentication", required = false) String cookie,
                                @PathVariable(value = "room") String roomToken) {
        ModelAndView modelAndView = new ModelAndView();
        ru.itis.models.CookieValue cookieValue = cookieValueService.get(cookie);
        if (cookieValue != null) {
//            chatRoomService.addIdToRoom(roomToken, cookieValue.getUser().getId());
            modelAndView.addObject("room", chatRoomService.getRoom(roomToken));
            modelAndView.addObject("user", cookieValue.getUser());
//            System.out.println(chatRoomService.getRoom(roomToken).getToken());
            modelAndView.setViewName("chat");
            return modelAndView;
        }
        modelAndView.setViewName("signIn");
        return modelAndView;
    }
}
