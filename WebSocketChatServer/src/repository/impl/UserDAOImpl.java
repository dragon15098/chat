package repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<User> findUserByName(String name) {
		String selectSql = "SELECT * FROM app_user WHERE user_name LIKE ? OR frist_name LIKE ? OR last_name LIKE ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = getConnection().prepareStatement(selectSql);
			preparedStatement.setString(1, "%" + name + "%");
			preparedStatement.setString(2, "%" + name + "%");
			preparedStatement.setString(3, "%" + name + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			List<User> result = new ArrayList<User>();
			while(resultSet.next()) {
				User user = new User();
				user.id= resultSet.getInt(1);
				user.username= resultSet.getString(1);
				user.fristname= resultSet.getString(2);
				user.lastname= resultSet.getString(3);
				result.add(user);
			}
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
