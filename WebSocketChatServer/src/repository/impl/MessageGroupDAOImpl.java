/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.dto.MessageGroup;
import repository.MessageGroupDAO;

/**
 *
 * @author 503
 */
public class MessageGroupDAOImpl extends DAO implements MessageGroupDAO {

	@Override
	public void insert(MessageGroup messageGroup) {
		String insert = "INSERT INTO message_group (from_user_id, group_id, content) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = getConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, messageGroup.fromUserId);
			preparedStatement.setInt(2, messageGroup.groupId);
			preparedStatement.setString(3, messageGroup.content);
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					messageGroup.id = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating failed, no ID obtained.");
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<MessageGroup> getMessageGroup(Integer groupId) {
		String sqlSelect = "SELECT * FROM message_group WHERE group_id = ?";
		try {
			PreparedStatement preparedStatement = getConnection().prepareStatement(sqlSelect);
			preparedStatement.setInt(1, groupId);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<MessageGroup> result = new ArrayList<>();
			while (resultSet.next()) {
				MessageGroup messageGroup = new MessageGroup();
				messageGroup.id = resultSet.getInt(1);
				messageGroup.fromUserId = resultSet.getInt(2);
				messageGroup.groupId = resultSet.getInt(3);
				messageGroup.content = resultSet.getString(4);
				result.add(messageGroup);
			}
			return result;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
