package repository;

import java.util.List;

import models.dto.MessageDTO;

public interface MessageDAO {
	void insertMessage(MessageDTO messageDao);
	List<MessageDTO> getMessageFromDB(Integer fromUserId, Integer toUserId, Integer groupId);
}
