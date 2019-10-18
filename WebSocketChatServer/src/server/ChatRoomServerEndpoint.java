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

import models.dto.MessageDTO;
import models.dto.MessageRespone;
import models.dto.UserDTO;
import repository.impl.MessageDAOImpl;
import repository.impl.RelationshipDAOImpl;
import request.Request;

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
		// chua co connect trong map
		if (userMapping.get(messageFromUser.token) == null) {
			userMapping.put(messageFromUser.token, userSession);
			if (relationshipDAOImpl == null) {
				relationshipDAOImpl = new RelationshipDAOImpl();
			}
			// tao connection
			if ("START_CONNECTION".equals(messageFromUser.function)) {
				UserDTO dto = new UserDTO();
				dto.id = Integer.parseInt(messageFromUser.token);

				getFriendList(userSession, dto);
				// loadMessage(userSession,
				// Integer.parseInt(messageFromUser.token),messageFromUser.toUser);
			}

		} else {
			if ("GET_MESSAGE".equals(messageFromUser.function)) {
				loadMessage(userSession, Integer.parseInt(messageFromUser.token), messageFromUser.toUser);
			}
			if ("SEND_MESSAGE".equals(messageFromUser.function)) {
				Integer toUserId = messageFromUser.toUser;
				Integer fromUserId = Integer.parseInt(messageFromUser.token);
				MessageDTO messageDTO = new MessageDTO();
				messageDTO.toUserId = toUserId;
				messageDTO.fromUserId = fromUserId;
				messageDTO.content = messageFromUser.content;
				saveMessageToDB(messageDTO);
				Session sessionUserReceiver = userMapping.get(messageDTO.toUserId.toString());
				if (sessionUserReceiver != null) {
					if (sessionUserReceiver.isOpen()) {
						sendMessageToOnlineUser(sessionUserReceiver, messageFromUser.content);
					}
				}
			}
		}
	}

	private void sendMessageToOnlineUser(Session receiverSession, String message) {
		MessageRespone<String> messageRespone = new MessageRespone<>();
		messageRespone.code = 200;
		messageRespone.content = message;
		messageRespone.typeRequest = "getMessageFromUser";
		ResponeSender.sentRespone(receiverSession, messageRespone);
	}

	private void getFriendList(Session userSession, UserDTO dto) {
		List<UserDTO> dtos = relationshipDAOImpl.findRelationshipByUserId(dto);
		MessageRespone<List<UserDTO>> respone = new MessageRespone<>();
		respone.code = 200;
		respone.content = dtos;
		respone.typeRequest = "getRelationship";
		ResponeSender.sentRespone(userSession, respone);
	}

	private void loadMessage(Session userSession, Integer fromUserId, Integer toUserId) {
		// send back message
		List<MessageDTO> messages = getMessageFromDB(fromUserId, toUserId, null);
		MessageRespone<List<MessageDTO>> responeMessage = new MessageRespone<>();
		responeMessage.code = 200;
		responeMessage.content = messages;
		responeMessage.typeRequest = "getMessage";
		ResponeSender.sentRespone(userSession, responeMessage);
	}

	private List<MessageDTO> getMessageFromDB(Integer fromUserId, Integer toUderId, Integer groupId) {
		if (messageDAOImpl == null) {
			messageDAOImpl = new MessageDAOImpl();
		}
		return messageDAOImpl.getMessageFromDB(fromUserId, toUderId, groupId);

	}

	private void saveMessageToDB(MessageDTO message) {
		if (messageDAOImpl == null) {
			messageDAOImpl = new MessageDAOImpl();
		}
		messageDAOImpl.insertMessage(message);
	}

	@OnClose
	public void handleClose(Session session) {
		userMapping.entrySet().removeIf(entry -> (session.equals(entry.getValue()))); 

	}

	@OnError
	public void handleError(Throwable t) {
		System.out.println("handle Error");
		t.printStackTrace();
	}

}
