package repository;

import models.dto.UserDTO;

public interface UserDAO {
	UserDTO findUser(String username, String password);
}
