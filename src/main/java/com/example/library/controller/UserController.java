package com.example.library.controller;

import com.example.library.dto.UserDto;
import com.example.library.entities.User;
import com.example.library.mapper.UserMapper;
import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        User userEntity = UserMapper.toEntity(userDto);
        User createdUser = userService.create(userEntity);
        UserDto createdUserDTO = UserMapper.toDto(createdUser);
        return ResponseEntity.ok(createdUserDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getById(@PathVariable Long userId) {
        User foundUser = userService.getById(userId);
        return ResponseEntity.ok(UserMapper.toDto(foundUser));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDTO, @PathVariable Long id) {
        User userEntity = UserMapper.toEntity(userDTO);
        User userUpdate = userService.update(userEntity, id);
        UserDto updatedUserDTO = UserMapper.toDto(userUpdate);

        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam String email, @RequestParam String code) {
        userService.verify(email, code);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        User userToLogin = UserMapper.toEntity(userDto);
        User user = userService.login(userToLogin.getEmail(), userToLogin.getPassword());
        return ResponseEntity.ok(UserMapper.toDto(user));
    }
}
