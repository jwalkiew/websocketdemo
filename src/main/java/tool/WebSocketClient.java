package tool;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.EncodeException;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.log4j.Logger;

@ClientEndpoint(decoders = { MyMessageDecoder.class }, encoders = { MyMessageEncoder.class })
public class WebSocketClient {

	private final static Logger LOG = Logger.getLogger(WebSocketClient.class);

	private final String uri = "ws://localhost:8080/actions";

	private Session session;

	public WebSocketClient() {
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			session = container.connectToServer(this, new URI(uri));
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		LOG.info("S-CLIENT: session id = " + session.getId());
		this.session = session;
	}

	public void send(String id, String content) {
		MyMessage myMessage = new MyMessage(id, content);

		try {
			session.getBasicRemote().sendObject(myMessage);
			LOG.info("Send message with content: " + myMessage.getContent() + " and id = " + myMessage.getId());
		} catch (IOException | EncodeException e) {
			LOG.error(e);
			e.printStackTrace();
		}
	}
}
