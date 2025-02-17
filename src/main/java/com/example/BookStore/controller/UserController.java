package com.example.BookStore.controller;

import com.example.BookStore.dto.UserDto;
import com.example.BookStore.entities.User;
import com.example.BookStore.mapper.UserMapper;
import com.example.BookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        User userEntity = UserMapper.toEntity(userDto);
        User createdUser = userService.create(userEntity);
        UserDto createdUserDTO = UserMapper.toDto(createdUser);
        return ResponseEntity.ok(createdUserDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto){
        User userToLogin=UserMapper.toEntity(userDto);
        User user=userService.login(userToLogin.getEmail(),userToLogin.getPassword());
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String code) {
        userService.verify(email, code);
        return ResponseEntity.ok("Cont verificat cu succes!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDTO, @PathVariable Long id) {
        User userEntity = UserMapper.toEntity(userDTO);
        User userUpdate = userService.updateUser(userEntity, id);
        UserDto updatedUserDTO = UserMapper.toDto(userUpdate);

        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
