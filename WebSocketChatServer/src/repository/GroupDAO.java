package repository;

import java.util.List;

import models.dto.Group;
import models.dto.User;

public interface GroupDAO {
	List<Group> getGroupsHaveUser(User user);
	List<Integer> getUserIdIn(Integer groupId);
}
