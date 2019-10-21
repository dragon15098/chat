package repository;

import java.util.List;

import models.dto.User;

public interface UserDAO {
	User findUser(String username, String password);

	void createUser(User user);
	List<User> findUserByName(String name);
}
