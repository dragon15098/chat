package service.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.dto.UserDTO;
import service.UserDAO;

public class UserDAOImpl extends DAO implements UserDAO {

	@Override
	public UserDTO findUser(String username, String password) {
		String sqlSelect = "SELECT * FROM APP_USER WHERE user_name = ? AND pass_word = ?";
		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSelect);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			UserDTO dto = new UserDTO();
			dto.username = resultSet.getString(1);
			return dto;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
