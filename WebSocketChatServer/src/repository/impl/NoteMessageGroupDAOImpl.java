package repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import repository.NoteMessageGroupDAO;

public class NoteMessageGroupDAOImpl extends DAO implements NoteMessageGroupDAO{

	@Override
	public void saveMessage(Integer idNote) {
		String sql = "INSERT INTO note_message_group (message_group_id) VALUES (?);";
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
	public void getNoteMessage(Integer groupId) {
		// TODO Auto-generated method stub
		
	}

}
