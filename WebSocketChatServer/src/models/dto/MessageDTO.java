package models.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageDTO implements Serializable {
	public Long id;
	public Integer fromUserId;
	public Integer toUserId;
	public String content;
	public Timestamp sendDate;
	
}
