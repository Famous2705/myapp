package com.yourname.myapp.service;

import com.yourname.myapp.dto.CreateUserRequest;
import com.yourname.myapp.dto.UserResponse;
import com.yourname.myapp.exception.DuplicateResourceException;
import com.yourname.myapp.exception.ResourceNotFoundException;
import com.yourname.myapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import com.yourname.myapp.model.User;
import org.springframework.cglib.core.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserResponse register(CreateUserRequest req) {
        if (userRepo.existsByEmail(req.email())) {
            throw new DuplicateResourceException("Email already exists");
        }
        User user = new User();
        user.setEmail(req.email());
        user.setPassWord(req.password());
        return UserResponse.from(userRepo.save(user));
    }

    public UserResponse getById(Long id) {
        return userRepo.findById(id)
                .map(UserResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResponse getByEmail(String email) {
        return userRepo.findByEmail(email)
                .map(UserResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Page<UserResponse> getAll(Pageable pageable) {
        return userRepo.findAll(pageable)
                .map(UserResponse::from);
    }

    public UserResponse modify(Long id, CreateUserRequest request){
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setEmail(request.email());

        return UserResponse.from(userRepo.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id))
            throw new ResourceNotFoundException("User not found");
        userRepo.deleteById(id);
    }
}
