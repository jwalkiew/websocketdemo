package tool;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MyMessageEncoder implements Encoder.Text<MyMessage> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
	}

	@Override
	public String encode(MyMessage myMessage) throws EncodeException {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("id", myMessage.getId());
		builder.add("content", myMessage.getContent());
		return builder.build().toString();
	}

}
