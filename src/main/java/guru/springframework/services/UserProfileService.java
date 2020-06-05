package guru.springframework.services;

import guru.springframework.domain.UserProfile2;


/**
 * Created by jt on 1/10/17.
 */
public interface UserProfileService {

    UserProfile2 getById(String id);

    UserProfile2 saveOrUpdate(UserProfile2 product);

}
