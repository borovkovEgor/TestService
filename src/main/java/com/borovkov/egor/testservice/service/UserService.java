package com.borovkov.egor.testservice.service;

import com.borovkov.egor.testservice.exception.UserNotFoundException;
import com.borovkov.egor.testservice.model.User;
import com.borovkov.egor.testservice.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user) {
        log.info("Saving new user with id={}", user.getId());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        log.info("Updating user with id={}", id);

        User oldUser = getUserById(id);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setAge(user.getAge());
        oldUser.setEmail(user.getEmail());

        return userRepository.save(oldUser);
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with id={}", id);
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id={%d} not found", id)));
    }
}
