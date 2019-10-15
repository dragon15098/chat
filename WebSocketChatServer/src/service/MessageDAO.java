package service;

import java.util.List;

import models.dto.MessageDTO;

public interface MessageDAO {
	void insertMessage(MessageDTO messageDao);
	List<MessageDTO> getMessageFromDB(Integer userId, Integer groupId);
}
