package repository;

import java.util.List;

import models.dto.MessageDTO;

public interface NoteMessageDAO {
	public void saveMessage(Integer idNote);
	List<MessageDTO> getNoteMessage(Integer fromUser, Integer toUser);
} 
