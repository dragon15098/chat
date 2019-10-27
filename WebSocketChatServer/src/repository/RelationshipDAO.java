package repository;

import java.util.List;

import models.dto.Relationship;
import models.dto.User;

public interface RelationshipDAO {
	List<User> findRelationshipByUserId(User user, String textSearch);

    void addFriend(Relationship relationship);

    void acceptFriend(Relationship relationship);
}
