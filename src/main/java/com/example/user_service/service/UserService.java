package com.example.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user_service.model.Profile;
import com.example.user_service.model.User;
import com.example.user_service.repository.ProfileRepository;
import com.example.user_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service

public class UserService {
	@Autowired
    private  UserRepository userRepository;
	@Autowired
	private ProfileRepository profileRepository;

    public User saveUser(User userObj) {
    	User user = new User();
    	user.setUserName(userObj.getUserName());
    	user.setEmail(userObj.getEmail());
    	user.setPassword(userObj.getPassword());

    	Profile profile = new Profile();
    	profile.setAge(userObj.getProfile().getAge());
    	profile.setHeight(userObj.getProfile().getHeight());
    	profile.setWeight(userObj.getProfile().getWeight());

    	// Crucial part
    	profile.setUser(user);
    	user.setProfile(profile);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUserName(userDetails.getUserName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            if (user.getProfile() != null && userDetails.getProfile() != null) {
                user.getProfile().setAge(userDetails.getProfile().getAge());
                user.getProfile().setHeight(userDetails.getProfile().getHeight());
                user.getProfile().setWeight(userDetails.getProfile().getWeight());
            }
            return userRepository.save(user);
        });
    }

	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}
	public Profile getProfileByUserId(Long userId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));
	    return user.getProfile();
	}

	public Profile updateUserProfile(Long userId, Profile updatedProfile) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    Profile profile = user.getProfile();
	    if (profile == null) {
	        profile = new Profile();
	        profile.setUser(user);
	    }

	    profile.setAge(updatedProfile.getAge());
	    profile.setHeight(updatedProfile.getHeight());
	    profile.setWeight(updatedProfile.getWeight());

	    return profileRepository.save(profile);
	}


}

