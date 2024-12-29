package ba.edu.ibu.inventorymanagementwebapp.core.service;

import ba.edu.ibu.inventorymanagementwebapp.core.model.User;
import ba.edu.ibu.inventorymanagementwebapp.core.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
