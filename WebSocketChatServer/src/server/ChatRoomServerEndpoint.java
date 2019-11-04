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

import models.dto.Group;
import models.dto.GroupChatDetail;
import models.dto.MessageDTO;
import models.dto.MessageGroup;
import models.dto.MessageRespone;
import models.dto.User;
import repository.impl.GroupDAOImpl;
import repository.impl.MessageDAOImpl;
import repository.impl.MessageGroupDAOImpl;
import repository.impl.RelationshipDAOImpl;
import request.Request;

@ServerEndpoint(value = "/chatRoomServer")
public class ChatRoomServerEndpoint {
	public RelationshipDAOImpl relationshipDAOImpl;
	public MessageGroupDAOImpl messageGroupDAOImpl;
	public GroupDAOImpl groupDAOImpl;
	public MessageDAOImpl messageDAOImpl;
	// static Set<Session> users = Collections.synchronizedSet(new HashSet<>());

	public static HashMap<String, Session> userMapping = new HashMap<>();

	@OnOpen
	public void handleOpen(Session session) {
		System.out.println("Client connect");
		System.out.println("CONNECT");
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
				User dto = new User();
				dto.id = Integer.parseInt(messageFromUser.token);

				getFriendList(userSession, dto);
				getGroupList(userSession, dto);
			}
		} else {
			if ("GET_MESSAGE".equals(messageFromUser.function)) {
				loadMessage(userSession, Integer.parseInt(messageFromUser.token), messageFromUser.toUser);
			}
			if ("GET_MESSAGE_GROUP".equals(messageFromUser.function)) {
				loadMessageGroup(userSession, Integer.parseInt(messageFromUser.token), messageFromUser.toGroupUser);
			}
			if ("SEND_MESSAGE_TO_USER".equals(messageFromUser.function)) {
				MessageDTO messageDTO = createMessage(messageFromUser);
				saveMessageToDB(messageDTO);
				sendMessageToOnlineUser(messageDTO);
			}
			if ("SEND_MESSAGE_TO_GROUP".equals(messageFromUser.function)) {
				MessageGroup messageGroup = createMessageGroup(messageFromUser);
				saveMessageToDB(messageGroup);
				sendMessageToOnlineUser(messageGroup);
			}
			if ("SEARCH_FRIEND".equals(messageFromUser.function)) {
				if(groupDAOImpl!=null) {
					groupDAOImpl = new GroupDAOImpl();
				}
				List<User> users = groupDAOImpl.findUserNotInGroup(1, 1, messageFromUser.textSearch);
				sendResultSearch(userSession, users);
			}
			if ("ADD_FRIENDS_TO_GROUP".equals(messageFromUser.function)) {
				if(groupDAOImpl==null) {
					groupDAOImpl = new GroupDAOImpl();
				}
				for(Integer id : messageFromUser.friendIds) {
					GroupChatDetail groupChatDetail = new GroupChatDetail();
					groupChatDetail.groupId = messageFromUser.toGroupUser;
					groupChatDetail.userId = id;
					groupDAOImpl.addUserToGroup(groupChatDetail);
				}
				sendMessageInsertSuccess(userSession);
			}
			if ("CREATE_GROUP".equals(messageFromUser.function)) {
				String groupName = messageFromUser.groupName;
				List<Integer> listID = messageFromUser.listID;
				createGroupChat(groupName, listID);
			}
		}
	}

	private void sendMessageInsertSuccess(Session userSession) {
		MessageRespone messageRespone = new MessageRespone<>();
		messageRespone.typeRequest = "addSuccess";
		messageRespone.code = 200;
		ResponeSender.sentRespone(userSession, messageRespone);
		
	}

	private void sendResultSearch(Session userSession, List<User> users) {
		MessageRespone<List<User>> messageRespone = new MessageRespone<>();
		messageRespone.content = users;
		messageRespone.typeRequest = "getResultSearch";
		messageRespone.code = 200;
		ResponeSender.sentRespone(userSession, messageRespone);
	}

	private void loadMessageGroup(Session userSession, int parseInt, Integer toGroupUser) {
		if (messageGroupDAOImpl == null) {
			messageGroupDAOImpl = new MessageGroupDAOImpl();
		}
		List<MessageGroup> messageGroups = messageGroupDAOImpl.getMessageGroup(toGroupUser);
		MessageRespone<List<MessageGroup>> responeMessage = new MessageRespone<>();
		responeMessage.code = 200;
		responeMessage.content = messageGroups;
		responeMessage.typeRequest = "getMessageGroup";
		ResponeSender.sentRespone(userSession, responeMessage);
	}

	private void getGroupList(Session userSession, User user) {
		if (groupDAOImpl == null) {
			groupDAOImpl = new GroupDAOImpl();
		}
		List<Group> groups = groupDAOImpl.getGroupsHaveUser(user);
		MessageRespone<List<Group>> respone = new MessageRespone<>();
		respone.code = 200;
		respone.content = groups;
		respone.typeRequest = "getGroupList";
		ResponeSender.sentRespone(userSession, respone);

	}

	private void saveMessageToDB(MessageGroup messageGroup) {
		if (messageGroupDAOImpl == null) {
			messageGroupDAOImpl = new MessageGroupDAOImpl();
		}
		messageGroupDAOImpl.insert(messageGroup);
	}

	private void sendMessageToOnlineUser(MessageGroup messageGroup) {
		messageGroup.userIds.forEach(id -> {
			String idStr = id.toString();
			if (id != messageGroup.fromUserId) {
				Session sessionUserReceiver = userMapping.get(idStr);
				if (sessionUserReceiver != null) {
					if (sessionUserReceiver.isOpen()) {
						MessageRespone<String> messageRespone = new MessageRespone<>();
						messageRespone.code = 200;
						messageRespone.content = messageGroup.content;
						messageRespone.typeRequest = "getMessageGroupFromUser";
						ResponeSender.sentRespone(sessionUserReceiver, messageRespone);
					}
				}
			}
		});
	}

	private MessageGroup createMessageGroup(Request messageFromUser) {
		if (groupDAOImpl == null) {
			groupDAOImpl = new GroupDAOImpl();
		}
		MessageGroup messageGroup = new MessageGroup();
		messageGroup.fromUserId = Integer.parseInt(messageFromUser.token);
		messageGroup.userIds = groupDAOImpl.getUserIdIn(messageFromUser.toGroupUser);
		messageGroup.groupId = messageFromUser.toGroupUser;
		messageGroup.content = messageFromUser.content;
		return messageGroup;
	}

	private MessageDTO createMessage(Request messageFromUser) {
		Integer toUserId = messageFromUser.toUser;
		Integer fromUserId = Integer.parseInt(messageFromUser.token);
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.toUserId = toUserId;
		messageDTO.fromUserId = fromUserId;
		messageDTO.content = messageFromUser.content;
		return messageDTO;
	}

	private void sendMessageToOnlineUser(MessageDTO messageDTO) {
		Session sessionUserReceiver = userMapping.get(messageDTO.toUserId.toString());
		if (sessionUserReceiver != null) {
			if (sessionUserReceiver.isOpen()) {
				MessageRespone<String> messageRespone = new MessageRespone<>();
				messageRespone.code = 200;
				messageRespone.content = messageDTO.content;
				messageRespone.typeRequest = "getMessageFromUser";
				ResponeSender.sentRespone(sessionUserReceiver, messageRespone);
			}
		}
	}

	private void getFriendList(Session userSession, User dto) {
		List<User> dtos = relationshipDAOImpl.findRelationshipByUserId(dto, "");
		MessageRespone<List<User>> respone = new MessageRespone<>();
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
	
	private void createGroupChat(String groupName, List<Integer> listID) {
		if (groupDAOImpl == null) {
			groupDAOImpl = new GroupDAOImpl();
		}
		groupDAOImpl.createGroupChat(groupName, listID);
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
