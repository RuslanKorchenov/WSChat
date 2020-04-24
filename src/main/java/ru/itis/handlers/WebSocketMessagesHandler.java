package ru.itis.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.dto.MessageDto;
import ru.itis.models.Message;
import ru.itis.services.interfaces.ChatRoomService;
import ru.itis.services.interfaces.MessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    private static final Map<String, List<WebSocketSession>> sessions = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private MessageService messageService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageText = (String) message.getPayload();
        MessageDto messageDto = objectMapper.readValue(messageText, MessageDto.class);

        messageService.saveMessage(messageDto);
        if(!sessions.containsKey(messageDto.getRoomToken())) {
            sessions.put(messageDto.getRoomToken(), new ArrayList<>());
        }
        if(!sessions.get(messageDto.getRoomToken()).contains(session)) {
            sessions.get(messageDto.getRoomToken()).add(session);
        }
//        if (!sessions.containsKey(messageDto.getUserId())) {
//            sessions.put(messageDto.getUserId(), session);
//        }

        for (WebSocketSession currentSession : sessions.get(messageDto.getRoomToken())) {
            currentSession.sendMessage(message);
        }
    }
}
