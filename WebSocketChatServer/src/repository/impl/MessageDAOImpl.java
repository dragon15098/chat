package repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.dto.MessageDTO;
import repository.MessageDAO;

public class MessageDAOImpl extends DAO implements MessageDAO{

	@Override
	public void insertMessage(MessageDTO message) {
		String insertSql = "INSERT INTO message (from_user_id, to_user_id, content) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = getConnection().prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, message.fromUserId);
			preparedStatement.setInt(2, message.toUserId);
			preparedStatement.setString(3, message.content);
		   int affectedRows = preparedStatement.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating user failed, no rows affected.");
	        }
			 try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                message.id = (generatedKeys.getLong(1));
		            }
		            else {
		                throw new SQLException("Creating user failed, no ID obtained.");
		            }
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<MessageDTO> getMessageFromDB(Integer fromUserId, Integer toUserId, Integer groupId) {
		if(groupId== null) {
			String selectSql = "SELECT * FROM message WHERE (from_user_id = ? AND to_user_id = ?) OR (from_user_id = ? AND to_user_id = ?)";
			try {
				PreparedStatement preparedStatement = getConnection().prepareStatement(selectSql);
				preparedStatement.setInt(1, fromUserId);
				preparedStatement.setInt(2, toUserId);
				preparedStatement.setInt(3, toUserId);
				preparedStatement.setInt(4, fromUserId);
				ResultSet resultSet = preparedStatement.executeQuery();
				List<MessageDTO> messages = new ArrayList<>();
				while(resultSet.next()) {
					MessageDTO messageDTO = new MessageDTO();
					messageDTO.id = (long) resultSet.getInt(1);
					messageDTO.fromUserId = resultSet.getInt(2);
					messageDTO.toUserId = resultSet.getInt(3);
					messageDTO.content = resultSet.getString(4);
					messages.add(messageDTO);
					
				}
				return messages;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return null;
	}

}
