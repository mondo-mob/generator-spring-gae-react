package threewks.framework.usermanagement.repository;

import org.springframework.contrib.gae.objectify.repository.ObjectifyStringRepository;
import org.springframework.stereotype.Repository;
import threewks.framework.usermanagement.Role;
import threewks.framework.usermanagement.model.User;

import java.util.List;

@Repository
public interface UserRepository extends ObjectifyStringRepository<User> {

    default List<User> findEnabledByRole(Role... roles) {
        return ofy().load().type(getEntityType())
            .filter("roles in", roles)
            .filter("enabled" , true)
            .list();
    }

}
