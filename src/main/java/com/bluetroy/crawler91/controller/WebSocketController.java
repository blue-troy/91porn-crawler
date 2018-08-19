package com.bluetroy.crawler91.controller;

import com.bluetroy.crawler91.vo.JsonResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-16
 * Time: 下午3:59
 */
@Component
@Log4j2
@ServerEndpoint(value = "/websocket")
public class WebSocketController<T> {
    private static Session SESSION;

    @OnMessage
    public void message(String message) throws IOException {
        log.info("send message " + message);
        SESSION.getBasicRemote().sendText(message);
    }

    @OnOpen
    public void open(Session session) {
        if (SESSION != session) {
            log.info(session + " open");
            SESSION = session;
        }
    }

    public void send(String method, T t) throws IOException {
        message(new JsonResponse(method, t).get());
    }
}
