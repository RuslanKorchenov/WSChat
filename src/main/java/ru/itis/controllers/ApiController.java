package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.MessageDto;
import ru.itis.services.interfaces.ChatRoomService;
import ru.itis.services.interfaces.MessageService;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/api/messages")
    public ResponseEntity<List<MessageDto>> getMessages(@RequestParam("token") String token) {
        List<MessageDto> messages = messageService.getAll(token);
        return ResponseEntity.ok(messages);
    }
}
