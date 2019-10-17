package repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.dto.UserDTO;
import repository.RelationshipDAO;

public class RelationshipDAOImpl extends DAO implements RelationshipDAO {

	@Override
	public List<UserDTO> findRelationshipByUserId(UserDTO user) {
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
				List<UserDTO> userDTOs = new ArrayList<>();
				while(result.next()) {
					UserDTO userDTO = new UserDTO();
					userDTO.id = result.getInt(1);
					userDTO.username = result.getString(2);
					userDTO.firstName = result.getString(4);
					userDTO.lastName = result.getString(5);
					userDTOs.add(userDTO);
				}
				return userDTOs;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
		
	}

}
