package models.dto;

import java.sql.Timestamp;

public class MessageDTO {
	public Long id;
	public Integer fromUserId;
	public Integer toUserId;
	public String content;
	public Timestamp sendDate;
	public String getSendDateStr() {
		return sendDate.toString();
	}
	
}
