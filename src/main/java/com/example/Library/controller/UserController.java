package com.example.Library.controller;

import com.example.Library.dto.BookDto;
import com.example.Library.dto.LibraryDto;
import com.example.Library.dto.UserDto;
import com.example.Library.dto.validation.LoginValidation;
import com.example.Library.dto.validation.ValidationOrder;
import com.example.Library.entities.Book;
import com.example.Library.entities.Library;
import com.example.Library.entities.User;
import com.example.Library.mapper.BookMapper;
import com.example.Library.mapper.LibraryMapper;
import com.example.Library.mapper.UserMapper;
import com.example.Library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated(ValidationOrder.class) UserDto userDto) {
        User userEntity = UserMapper.toEntity(userDto);
        User createdUser = userService.create(userEntity);
        UserDto createdUserDTO = UserMapper.toDto(createdUser);
        return ResponseEntity.ok(createdUserDTO);
    }
    @PostMapping("/addLibraryToUser/{libraryId}/{userId}")
    public ResponseEntity<?> addLibraryToUser(@PathVariable Long libraryId,
                                              @PathVariable Long userId){
        User user=userService.addLibraryToUser(libraryId,userId);
        UserDto userDto=UserMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }
    @PostMapping("/addBookToWishList/{bookId}/{userId}")
    public ResponseEntity<?> addBookToWishList(@PathVariable Long bookId,
                                              @PathVariable Long userId){
        User user=userService.addBookToWishList(bookId,userId);
        UserDto userDto=UserMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }
    @GetMapping("/paginated-books/{userId}")
    public ResponseEntity<?> getBooksPaginated(
            @PathVariable Long userId,
            @RequestParam(name = "pageSize", required = false) Integer size,
            @RequestParam(name = "pageNumber", required = false) Integer page) {

        int pageSize = (size != null) ? size : 10;
        int pageNumber = (page != null) ? page : 0;

        Page<Book> foundBooks = userService.getBooks(userId, PageRequest.of(pageNumber, pageSize));
        Page<BookDto> bookDtos = foundBooks.map(BookMapper::toDto);

        return ResponseEntity.ok(bookDtos);
    }
    @PostMapping("/removeBookFromWishList/{bookId}/{userId}")
    public ResponseEntity<?> removeBookFromWishList(@PathVariable Long bookId,
                                                   @PathVariable Long userId){
        User user=userService.removeBookFromWishList(bookId,userId);
        UserDto userDto=UserMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }
    @PostMapping("/removeLibraryFromUser/{libraryId}/{userId}")
    public ResponseEntity<?> removeLibraryFromUser(@PathVariable Long libraryId,
                                              @PathVariable Long userId){
        User user=userService.removeLibraryFromUser(libraryId,userId);
        UserDto userDto=UserMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/paginated-search/{userId}")
    public ResponseEntity<?> findLibrariesPaginated(
            @PathVariable Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(name = "pageSize", required = false) Integer size,
            @RequestParam(name = "pageNumber", required = false) Integer page) {

        int pageSize = (size != null) ? size : 10;
        int pageNumber = (page != null) ? page : 0;

        Page<Library> foundLibraries = userService.findLibraries(userId,name, PageRequest.of(pageNumber, pageSize));
        Page<LibraryDto> libraryDtos = foundLibraries.map(LibraryMapper::toDto);

        return ResponseEntity.ok(libraryDtos);
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
