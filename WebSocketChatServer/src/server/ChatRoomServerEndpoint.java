package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Request;
import models.dto.MessageDTO;
import models.dto.MessageRespone;
import models.dto.UserDTO;
import service.impl.MessageDAOImpl;
import service.impl.RelationshipDAOImpl;

@ServerEndpoint(value = "/chatRoomServer")
public class ChatRoomServerEndpoint {
	public RelationshipDAOImpl relationshipDAOImpl;

	public MessageDAOImpl messageDAOImpl;
	// static Set<Session> users = Collections.synchronizedSet(new HashSet<>());

	public static HashMap<String, Session> userMapping = new HashMap<>();

	@OnOpen
	public void handleOpen(Session session) {
	}

	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Request messageFromUser = mapper.readValue(message, Request.class);
		if (userMapping.get(messageFromUser.token) == null) {
			userMapping.put(messageFromUser.token, userSession);
			if (relationshipDAOImpl == null) {
				relationshipDAOImpl = new RelationshipDAOImpl();
			}
			if (messageFromUser.function.equals("START_CONNECTION")) {
				UserDTO dto = new UserDTO();
				dto.id = 1;
				List<UserDTO> dtos = relationshipDAOImpl.findRelationshipByUserId(dto);
				MessageRespone<List<UserDTO>> respone = new MessageRespone<>();
				respone.code = 200;
				respone.content = dtos;
				respone.typeRequest = "getRelationship";
				userSession.getBasicRemote().sendText(mapper.writeValueAsString(respone));

				// send back message
				List<MessageDTO> messages = getMessageFromDB(dto.id, null);
				MessageRespone<List<MessageDTO>> responeMessage = new MessageRespone<>();
				responeMessage.code = 200;
				responeMessage.content = messages;
				responeMessage.typeRequest = "getMessage";
				userSession.getBasicRemote().sendText(mapper.writeValueAsString(responeMessage));
			}

		} else {
			if (messageFromUser.function.endsWith("GET_MESSAGE")) {
				// send back message
				List<MessageDTO> messages = getMessageFromDB(Integer.parseInt(messageFromUser.toUser), null);
				MessageRespone<List<MessageDTO>> responeMessage = new MessageRespone<>();
				responeMessage.code = 200;
				responeMessage.content = messages;
				responeMessage.typeRequest = "getMessage";
				userSession.getBasicRemote().sendText(mapper.writeValueAsString(responeMessage));
			} else {
				Integer toUserId = Integer.parseInt(messageFromUser.toUser);
				Integer fromUserId = Integer.parseInt(messageFromUser.token);
				MessageDTO messageDTO = new MessageDTO();
				messageDTO.toUserId = toUserId;
				messageDTO.fromUserId = fromUserId;
				messageDTO.content = messageFromUser.content;
				saveMessageToDB(messageDTO);
				Session sessionUserReceipt = userMapping.get("2");
				if (sessionUserReceipt != null) {
					if (sessionUserReceipt.isOpen()) {
						sessionUserReceipt.getBasicRemote().sendText(messageFromUser.content);
					}
				}

			}
		}
	}

	private List<MessageDTO> getMessageFromDB(Integer userId, Integer groupId) {
		if (messageDAOImpl == null) {
			messageDAOImpl = new MessageDAOImpl();
		}
		return messageDAOImpl.getMessageFromDB(userId, groupId);

	}

	private void saveMessageToDB(MessageDTO message) {
		if (messageDAOImpl == null) {
			messageDAOImpl = new MessageDAOImpl();
		}
		// messageDAOImpl.insertMessage(message);
	}

	@OnClose
	public void handleClose(Session session) {
		System.out.println("handle Close");
	}

	@OnError
	public void handleError(Throwable t) {
		System.out.println("handle Error");
		t.printStackTrace();
	}

}
