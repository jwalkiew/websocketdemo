package tool;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MyMessageDecoder implements Decoder.Text<MyMessage> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
	}

	@Override
	public MyMessage decode(String stringToDecode) throws DecodeException {
		JsonObject jsonObject = Json.createReader(new StringReader(stringToDecode)).readObject();
		return new MyMessage(jsonObject.getString("id"), jsonObject.getString("content"));
	}

	@Override
	public boolean willDecode(String stringToDecode) {
		if (stringToDecode == null)
			return false;

		try {
			Json.createReader(new StringReader(stringToDecode)).readObject();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
