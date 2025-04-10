package com.example.Library.controller;

import com.example.Library.dto.LibrarySimpleDto;
import com.example.Library.dto.ReviewDto;
import com.example.Library.dto.UserDto;
import com.example.Library.dto.validation.LoginValidation;
import com.example.Library.dto.validation.ValidationOrder;
import com.example.Library.entities.Library;
import com.example.Library.entities.Review;
import com.example.Library.entities.User;
import com.example.Library.mapper.LibraryMapper;
import com.example.Library.mapper.ReviewMapper;
import com.example.Library.mapper.UserMapper;
import com.example.Library.service.LibraryService;
import com.example.Library.service.ReviewService;
import com.example.Library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LibraryService libraryService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated(ValidationOrder.class) UserDto userDto) {
        User userEntity = UserMapper.toEntity(userDto);
        User createdUser = userService.create(userEntity);
        UserDto createdUserDTO = UserMapper.toDto(createdUser);
        return ResponseEntity.ok(createdUserDTO);
    }

    @PostMapping("/add-library-to-User/{libraryId}/{userId}")
    public ResponseEntity<?> addLibraryToUser(@PathVariable Long libraryId,
                                              @PathVariable Long userId) {
        User user = userService.getById(userId);
        Library library = libraryService.getLibraryById(libraryId);
        user.getLibraries().add(library);
        library.getUsers().add(user);
        return ResponseEntity.ok(LibraryMapper.toDto(library));
    }


    @GetMapping("/librariesPaginated/{userId}")
    public ResponseEntity<List<LibrarySimpleDto>> getLibrariesPaginated(@PathVariable Long userId,
                                                                        @RequestParam(required = false) Integer page,
                                                                        @RequestParam(required = false) Integer size) {
        List<LibrarySimpleDto> libraries = libraryService.findByUserId(userId, page, size);
        return ResponseEntity.ok(libraries);
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
    public ResponseEntity<?> updateUser(@RequestBody @Validated(ValidationOrder.class) UserDto userDTO,
                                        @PathVariable Long id) {
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

    @DeleteMapping("/{libraryId}")
    public ResponseEntity<?> deleteLibrary(@PathVariable Long libraryId) {
        libraryService.removeFromUser(libraryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam String email, @RequestParam String code) {
        userService.verify(email, code);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated(LoginValidation.class) UserDto userDto) {
        User userToLogin = UserMapper.toEntity(userDto);
        User user = userService.login(userToLogin.getEmail(), userToLogin.getPassword());
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PostMapping("/resend-code/{userId}")
    public ResponseEntity<?> resendVerification(@PathVariable Long userId) {
        User user = userService.resendVerificationEmail(userId);
        return ResponseEntity.ok().build();
    }
}
