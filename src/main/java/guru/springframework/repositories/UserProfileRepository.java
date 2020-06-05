package guru.springframework.repositories;

import guru.springframework.domain.UserProfile2;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by xiang.zhou on 1/10/17.
 */
public interface UserProfileRepository extends CrudRepository<UserProfile2, String> {
}
