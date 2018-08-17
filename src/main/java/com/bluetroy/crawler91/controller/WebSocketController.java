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
public class WebSocketController {
    private Session session;

    @OnOpen
    public void open(Session session) {
        log.info("webSocket open");
        this.session = session;
    }

    @OnMessage
    public void message(String message) throws IOException {
        log.info("send message " + message);
        session.getBasicRemote().sendText(message);
    }

    public void send(String method, Object object) throws IOException {
        message(new JsonResponse(method, object).get());
    }
}
