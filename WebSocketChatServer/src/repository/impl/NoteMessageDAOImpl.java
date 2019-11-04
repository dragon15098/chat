package repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

}
