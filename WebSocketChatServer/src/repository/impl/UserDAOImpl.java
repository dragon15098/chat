package repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.dto.User;
import repository.UserDAO;

public class UserDAOImpl extends DAO implements UserDAO {

	@Override
	public User findUser(String username, String password) {
		String sqlSelect = "SELECT * FROM APP_USER WHERE user_name = ? AND pass_word = ?";
		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSelect);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			User dto = new User();
			dto.username = resultSet.getString(1);
			return dto;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void createUser(User user) {
		String insert = "INSERT INTO app_user (username, password, fristname, lastname) VALUES (?,?,?,?) ";

		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(insert,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.username);
			preparedStatement.setString(2, user.password);
			preparedStatement.setString(3, user.fristname);
			preparedStatement.setString(4, user.lastname);
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating failed, no rows affected.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.id = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
