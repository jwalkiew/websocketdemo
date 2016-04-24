package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class IndexController {

	@Context
	ServletContext context;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response index() {
		String content = null;

		try (BufferedReader buffer = new BufferedReader(
				new InputStreamReader(context.getResourceAsStream("/index.html")))) {
			content = buffer.lines().collect(Collectors.joining("\n"));
		} catch (IOException e) {
			return Response.serverError().build();
		}

		return Response.ok(content).build();
	}

	@GET
	@Path("send")
	@Produces(MediaType.TEXT_HTML)
	public Response send(@QueryParam("id") String id, @QueryParam("content") String content) {
		WebSocketClient wsc = new WebSocketClient();
		wsc.send(id, content);
		return Response.ok("Message has been sent<br />Receiver session id = " + id + "<br />Content = " + content).build();
	}
}
