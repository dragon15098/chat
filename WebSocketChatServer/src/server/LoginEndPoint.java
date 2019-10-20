package server;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.dto.LoginRespone;
import models.dto.User;
import repository.impl.UserDAOImpl;

@ServerEndpoint(value = "/login")
public class LoginEndPoint {
	public UserDAOImpl userDaoImpl;

	@OnOpen
	public void handleOpen(Session session) {

	}

	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(message, User.class);
		if (userDaoImpl == null) {
			userDaoImpl = new UserDAOImpl();
		}
		User dto = userDaoImpl.findUser(user.username, user.username);
		LoginRespone loginRespone = new LoginRespone();
		if (dto != null) {
			String cookie = dto.username;
			loginRespone.status = "SUCCESS";
			loginRespone.cookie = cookie;
			userSession.getBasicRemote().sendText(mapper.writeValueAsString(loginRespone));
		} else {
			loginRespone.status = "FALSE";
			userSession.getBasicRemote().sendText(mapper.writeValueAsString(loginRespone));
		}
	}

	@OnClose
	public void handleClose(Session session) {

	}

	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}

}
