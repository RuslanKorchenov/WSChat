package ru.itis.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.WebUtils;
import ru.itis.services.interfaces.CookieValueService;

import javax.servlet.http.Cookie;
import java.util.Map;

@Component
public class AuthHandshakeHandler implements HandshakeHandler {
    @Autowired
    private CookieValueService cookieValuesService;

    private DefaultHandshakeHandler defaultHandshakeHandler = new DefaultHandshakeHandler();

    @Override
    public boolean doHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws HandshakeFailureException {
        ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
        Cookie cookie = WebUtils.getCookie(request.getServletRequest(), "Authentication");
        if (cookie != null) {
            if (cookieValuesService.isExist(cookie.getValue())) {
                return defaultHandshakeHandler.doHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, map);
            }
        }
        serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
        return false;

    }
}
