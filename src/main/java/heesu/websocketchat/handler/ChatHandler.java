package heesu.websocketchat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class ChatHandler extends TextWebSocketHandler {
    private static Set<WebSocketSession> users = new LinkedHashSet<WebSocketSession>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if(users.size()>=5){
            WebSocketSession old = users.iterator().next();
            old.sendMessage(new TextMessage("채팅이 종료되었습니다."));
            users.remove(users.iterator().next());
        }
        boolean isSessionAlive = false;

        for (WebSocketSession user : users) {
            if(user.getId().equals(session.getId())){
                isSessionAlive = true;
                break;
            }
        }
        if(isSessionAlive){
            for (WebSocketSession user : users) {
                user.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.add(session);
        session.sendMessage(new TextMessage(session.getId() + "님이 입장하셨습니다."));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        users.remove(session);
        session.sendMessage(new TextMessage(session.getId() + "님이 퇴장하셨습니다."));
    }
}
