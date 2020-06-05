package guru.springframework.services;

import guru.springframework.domain.UserProfile2;
import guru.springframework.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by xiang.zhou on 1/10/17.
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile2 getById(String id) {
        return userProfileRepository.findById(id).orElse(null);
    }

    @Override
    public UserProfile2 saveOrUpdate(UserProfile2 userProfile) {
        userProfileRepository.save(userProfile);
        return userProfile;
    }

}
