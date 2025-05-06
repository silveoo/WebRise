package com.silveo.webrise.service;

import com.silveo.webrise.exception.NotFoundException;
import com.silveo.webrise.exception.UserAlreadyExistsException;
import com.silveo.webrise.model.User;
import com.silveo.webrise.model.dto.UserCreateRequest;
import com.silveo.webrise.model.dto.UserResponse;
import com.silveo.webrise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserResponse create(UserCreateRequest dto) {
        if(userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Couldn't create user because of existing email. RequestId: {}", dto.getRequestId());
            throw new UserAlreadyExistsException(dto.getEmail());
        }
        User user = userRepository.save(User.builder()
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .createdAt(LocalDateTime.now())
                .build());
        log.info("Created user {}. RequestId: {}", user.getId(), dto.getRequestId());
        return map(user);
    }

    public UserResponse get(Long id) {
        return map(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", id)));
    }

    public UserResponse update(Long id, UserCreateRequest dto) {
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", id));
        if (!u.getEmail().equals(dto.getEmail()) &&
                userRepository.existsByEmail(dto.getEmail())) {
                log.warn("Couldn't update user because of existing email. RequestId: {}", dto.getRequestId());
                throw new UserAlreadyExistsException(dto.getEmail());
        }
        u.setEmail(dto.getEmail());
        u.setFullName(dto.getFullName());
        log.info("Updated user {}. RequestId: {}", u.getId(), dto.getRequestId());
        return map(u);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("Deleted user {}.", id);
    }

    private static UserResponse map(User u) {
        return new UserResponse(u.getId(), u.getEmail(), u.getFullName(), u.getCreatedAt());
    }
}
