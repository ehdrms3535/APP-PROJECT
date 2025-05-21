package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository repo;

    public AuthService(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public boolean register(String username, String password) {
        if (repo.findByUsername(username).isPresent()) return false;

        User user = new User(username, password);
        User saved = repo.save(user);

        System.out.println("✅ 회원 저장됨: " + saved.getUsername());
        return true;
    }

    public boolean login(String username, String password) {
        return repo.findByUsername(username)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public boolean verify(String username, String password) {
        Optional<User> userOpt = repo.findByUsername(username);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user = userOpt.get();
        return user.getPassword().equals(password);
    }
}
