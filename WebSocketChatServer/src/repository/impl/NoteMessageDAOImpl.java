package repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.dto.MessageDTO;
import repository.NoteMessageDAO;

public class NoteMessageDAOImpl extends DAO implements NoteMessageDAO {

	@Override
	public void saveMessage(Integer idNote) {
		String sql = "INSERT INTO note_message (message_id) VALUES (?);";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idNote);
			int rows = preparedStatement.executeUpdate();
			if (rows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MessageDTO> getNoteMessage(Integer fromUser, Integer toUser) {
		String sql = "SELECT * FROM note_message nm JOIN message m ON nm.message_id = m.id WHERE( from_user_id = ? and to_user_id = ? )or (from_user_id = ? and to_user_id = ?)";
		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
			preparedStatement.setInt(1, fromUser);

			preparedStatement.setInt(2, toUser);

			preparedStatement.setInt(3, toUser);

			preparedStatement.setInt(4, fromUser);

			ResultSet resultSet = preparedStatement.executeQuery();
			List<MessageDTO> dtos = new ArrayList<>();
			while (resultSet.next()) {
				MessageDTO dto = new MessageDTO();
				dto.fromUserId = resultSet.getInt("from_user_id");
				dto.toUserId = resultSet.getInt("to_user_id");
				dto.content = resultSet.getString("content");
				dtos.add(dto);
			}
			return dtos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
