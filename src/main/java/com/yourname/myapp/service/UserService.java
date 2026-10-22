package com.yourname.myapp.service;

import com.yourname.myapp.dto.CreateUserRequest;
import com.yourname.myapp.dto.UserResponse;
import com.yourname.myapp.exception.DuplicateResourceException;
import com.yourname.myapp.exception.ResourceNotFoundException;
import com.yourname.myapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import com.yourname.myapp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserResponse createUser(CreateUserRequest req) {
        if (userRepo.existsByEmail(req.email())) {
            throw new DuplicateResourceException("Email already exists");
        }
        User user = new User();
        user.setName(req.name());
        user.setEmail(req.email());
        return UserResponse.from(userRepo.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(UserResponse::from).toList();
    }

    public UserResponse getUserById(Long id) {
        return userRepo.findById(id)
                .map(UserResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id))
            throw new ResourceNotFoundException("User not found");
        userRepo.deleteById(id);
    }
}
