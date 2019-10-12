package server;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Message;

@ServerEndpoint(value = "/chatRoomServer")
public class ChatRoomServerEndpoint {

	//static Set<Session> users = Collections.synchronizedSet(new HashSet<>());
	
	public static HashMap<String, Session> userMapping = new HashMap<>();
	@OnOpen
	public void handleOpen(Session session) {
	}

	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Message messageFromUser = mapper.readValue(message, Message.class);
		if(userMapping.get(messageFromUser.token)==null) {
			userMapping.put(messageFromUser.token, userSession);
		}
		Session sessionUserReceipt = userMapping.get("2");
		if(sessionUserReceipt!=null) {
			if(sessionUserReceipt.isOpen()) {
				sessionUserReceipt.getBasicRemote().sendText(messageFromUser.context);
			}
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
