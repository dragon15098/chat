package server;

import java.io.IOException;

import javax.websocket.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.dto.MessageRespone;

public class ResponeSender {
	public static void sentRespone(Session session, MessageRespone messageRespone) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			session.getBasicRemote().sendText(mapper.writeValueAsString(messageRespone));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
