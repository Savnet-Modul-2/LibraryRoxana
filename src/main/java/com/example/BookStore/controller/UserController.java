package com.example.BookStore.controller;

import com.example.BookStore.dto.UserDto;
import com.example.BookStore.entities.User;
import com.example.BookStore.mapper.UserMapper;
import com.example.BookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        User userEntity = userMapper.toEntity(userDto);
        User createdUser = userService.create(userEntity);
        UserDto createdUserDTO = userMapper.toDto(createdUser);
        return ResponseEntity.ok(createdUserDTO);
    }
    @PostMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String code) {
        userService.verify(email, code);
        return ResponseEntity.ok("Cont verificat cu succes!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDTO, @PathVariable Long id) {
        User userEntity = userMapper.toEntity(userDTO);
        User userUpdate = userService.updateUser(userEntity, id);
        UserDto updatedUserDTO = userMapper.toDto(userUpdate);

        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
