package repository;

import java.util.List;

import models.dto.Group;
import models.dto.GroupChatDetail;
import models.dto.User;

public interface GroupDAO {
	List<Group> getGroupsHaveUser(User user);
	List<Integer> getUserIdIn(Integer groupId);
	List<User> findUserNotInGroup(Integer groupId, Integer userId, String textSearch);
	List<User> findUserInGroup(Integer groupId);
	void addUserToGroup(GroupChatDetail groupChatDetail);
	void removeUserFromGroup(Integer groupId, Integer userId);
}
