package com.silveo.webrise.controller;

import com.silveo.webrise.model.dto.UserCreateRequest;
import com.silveo.webrise.model.dto.UserResponse;
import com.silveo.webrise.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService UserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserCreateRequest body) {
        log.info("Request to create new user. RequestId: {}", body.getRequestId());
        return UserService.create(body);
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) { return UserService.get(id); }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id,
                               @Valid @RequestBody UserCreateRequest body) {
        log.info("Request to update user {}. RequestId: {}", id, body.getRequestId());
        return UserService.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        UserService.delete(id);
        log.info("Request to delete user {}.", id);
    }
}
