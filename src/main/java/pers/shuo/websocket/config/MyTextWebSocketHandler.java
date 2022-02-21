package pers.shuo.websocket.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MyTextWebSocketHandler extends TextWebSocketHandler {
    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (session.isOpen()) {
            sessions.add(session);
        }
        sendToAll(message);
    }

    private void sendToAll(TextMessage message) throws IOException {
        for (WebSocketSession session : sessions) {
            session.sendMessage(message);
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }
}
