package server;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.dto.MessageRespone;
import models.dto.User;
import repository.impl.UserDAOImpl;

@ServerEndpoint(value = "/register")
public class RegisterEndPoint {
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
		userDaoImpl.createUser(user);
		if (user.id != null) {
			MessageRespone messageRespone = new MessageRespone<>();
			messageRespone.code = 200;
			messageRespone.typeRequest = "register";
			ResponeSender.sentRespone(userSession, messageRespone);
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
