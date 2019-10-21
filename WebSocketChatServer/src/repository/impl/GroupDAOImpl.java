package repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.dto.Group;
import models.dto.GroupChatDetail;
import models.dto.User;
import repository.GroupDAO;

public class GroupDAOImpl extends DAO implements GroupDAO {

	@Override
	public List<Group> getGroupsHaveUser(User user) {
		String selectSql = "SELECT * FROM group_chat WHERE id in (SELECT group_id FROM group_chat_detail WHERE user_id = ?);";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = getConnection().prepareStatement(selectSql);
			preparedStatement.setInt(1, user.id);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Group> result = new ArrayList<>();
			while (resultSet.next()) {
				Group group = new Group();
				group.id = resultSet.getInt(1);
				group.content = resultSet.getString(2);
				result.add(group);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Integer> getUserIdIn(Integer groupId) {
		String selectSql = "SELECT user_id FROM group_chat_detail WHERE group_id = ?;";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = getConnection().prepareStatement(selectSql);
			preparedStatement.setInt(1, groupId);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Integer> result = new ArrayList<>();
			while (resultSet.next()) {
				result.add(resultSet.getInt(1));
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void addUserToGroup(GroupChatDetail groupChatDetail) {
		String insertSql = "INSERT INTO group_chat_detal (group_id, user_id) VALUES (?, ?)";
		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, groupChatDetail.groupId);
			preparedStatement.setInt(2, groupChatDetail.userId);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					groupChatDetail.id = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeUserFromGroup(Integer groupId, Integer userId) {
		String insertSql = "DELETE FROM group_chat_detail WHERE group_id = ? AND user_id;";
		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(insertSql);
			preparedStatement.setInt(1, groupId);
			preparedStatement.setInt(2, userId);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}	
	}

}
