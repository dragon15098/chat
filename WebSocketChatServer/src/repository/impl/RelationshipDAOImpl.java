package repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.dto.Relationship;
import models.dto.User;
import repository.RelationshipDAO;

public class RelationshipDAOImpl extends DAO implements RelationshipDAO {

	@Override
	public List<User> findRelationshipByUserId(User user) {
		if(user.id!=null) {
			String query = "SELECT * FROM relationship r JOIN app_user u  WHERE (r.frist_user_id = ? OR r.second_user_id = ?) AND r.status = 1 ";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = getConnection().prepareStatement(query);
				preparedStatement.setInt(1, user.id);
				preparedStatement.setInt(2, user.id);
				ResultSet resultSet = preparedStatement.executeQuery();
				List<Integer> friendIds = new ArrayList<>(); 
				while(resultSet.next()) {
					int fristUserId = resultSet.getInt(2);
					int secondUserId = resultSet.getInt(3);
					if(fristUserId != user.id) {
						friendIds.add(fristUserId);
					}
					else if(secondUserId != user.id) {
						friendIds.add(secondUserId);	
					}
				}
				StringBuilder builder = new StringBuilder();

				for( int i = 0 ; i < friendIds.size(); i++ ) {
				    builder.append("?,");
				}

				String queryGetInfo = "select * from app_user where id in (" 
				               + builder.deleteCharAt( builder.length() -1 ).toString() + ")";
				PreparedStatement ps = getConnection().prepareStatement(queryGetInfo);
				int index = 1;
				for(Integer id : friendIds) {
					ps.setInt(index++, id);
				}
				ResultSet result = ps.executeQuery();
				List<User> Users = new ArrayList<>();
				while(result.next()) {
					User User = new User();
					User.id = result.getInt(1);
					User.username = result.getString(2);
					User.fristname = result.getString(4);
					User.lastname = result.getString(5);
					Users.add(User);
				}
				return Users;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
		
	}

	  @Override
	    public void addFriend(Relationship relationship) {
	        String insert = "INSERT INTO relationship (from_user_id, to_user_id, status) VALUES (?, ?, ?)";
	        try {
	            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
	            preparedStatement.setInt(1, relationship.fromUserId);
	            preparedStatement.setInt(1, relationship.toUserId);
	            preparedStatement.setInt(1, 0);
	            int affectedRows = preparedStatement.executeUpdate();
	            if (affectedRows == 0) {
	                throw new SQLException("Creating user failed, no rows affected.");
	            }

	            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    relationship.id = generatedKeys.getInt(1);
	                } else {
	                    throw new SQLException("Creating failed, no ID obtained.");
	                }
	            }
	        } catch (SQLException ex) {
	        	  ex.printStackTrace();
	        }

	    }

	    @Override
	    public void acceptFriend(Relationship relationship) {
	        String updateSQL = "UPDATE relationship SET status = 1 WHERE id = ?";
	        try {
	            PreparedStatement preparedStatement = getConnection().prepareStatement(updateSQL, Statement.RETURN_GENERATED_KEYS);
	            preparedStatement.setInt(1, relationship.id);
	            int affectedRows = preparedStatement.executeUpdate();
	            if (affectedRows == 1) {
	                relationship.status = 1;
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }

	    }
	
}
