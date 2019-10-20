package repository;

import models.dto.User;

public interface UserDAO {
	User findUser(String username, String password);

	void createUser(User user);
}
