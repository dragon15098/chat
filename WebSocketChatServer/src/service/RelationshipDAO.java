package service;

import java.util.List;

import models.dto.UserDTO;

public interface RelationshipDAO {
	List<UserDTO> findRelationshipByUserId(UserDTO user);
}
