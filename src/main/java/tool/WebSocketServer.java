package tool;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

@ServerEndpoint(value = "/actions", decoders = { MyMessageDecoder.class }, encoders = { MyMessageEncoder.class })
public class WebSocketServer {

	private static final Logger LOG = Logger.getLogger(WebSocketServer.class);

	@OnOpen
	public void open(Session session) {
		LOG.info("SERVER [session id = " + session.getId() + "]: Connection open");
	}

	@OnClose
	public void close(Session session) {
		LOG.info("SERVER [session id = " + session.getId() + "]: Connection close");
	}

	@OnError
	public void onError(Throwable error) {
		LOG.info("SERVER: Connection error: " + error.getMessage());
	}

	@OnMessage
	public void handleMessage(MyMessage myMessage, Session session) {
		String messageId = myMessage.getId();
		String messageContent = myMessage.getContent();
		LOG.info("SERVER [session id = " + session.getId() + "]: Received message with content: " + messageContent);

		for (Session openSession : session.getOpenSessions()) {
			if (messageId.equals(openSession.getId()))
				try {
					openSession.getBasicRemote().sendText(messageContent);
				} catch (IOException e) {
					LOG.error(e);
				}
		}
	}
}
