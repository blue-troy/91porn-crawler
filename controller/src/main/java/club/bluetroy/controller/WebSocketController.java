package club.bluetroy.controller;

import club.bluetroy.crawler.Adviser;
import club.bluetroy.vo.JsonResponse;
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
@Log4j2
@Component
@ServerEndpoint(value = "/websocket")
public class WebSocketController implements Adviser {
    private static Session SESSION;

    @OnOpen
    public void open(Session session) {
        if (SESSION != session) {
            log.info(session + " open");
            SESSION = session;
        }
    }

    @Override
    public <T> void message(String method, T t) throws IOException {
        message(new JsonResponse(method, t).get());
    }

    @Override
    @OnMessage
    public void message(String message) throws IOException {
        log.info("message message " + message);
        SESSION.getBasicRemote().sendText(message);
    }
}
