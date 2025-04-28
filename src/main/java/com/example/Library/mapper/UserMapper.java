package com.example.Library.mapper;

import com.example.Library.dto.UserDto;
import com.example.Library.dto.UserSimpleDto;
import com.example.Library.entities.Library;
import com.example.Library.entities.Review;
import com.example.Library.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toEntity(UserDto userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setYearOfBirth(userDTO.getYearOfBirth());
        user.setGender(userDTO.getGender());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());
        user.setCountry(userDTO.getCountry());
        user.setVerifiedAccount(userDTO.getVerifiedAccount());
        user.setVerificationCode(userDTO.getVerificationCode());
        user.setVerificationCodeExpiration(userDTO.getVerificationCodeExpiration());

        List<Library> libraries = userDTO.getFavoriteLibraries().stream()
                .map(LibraryMapper::toSimpleEntity)
                .toList();
        user.setFavoriteLibraries(libraries);

        if (userDTO.getReviews() != null) {
            List<Review> reviews = userDTO.getReviews().stream()
                    .map(ReviewMapper::toSimpleEntity)
                    .toList();
            user.setReviews(reviews);
        }
        return user;
    }

    public static UserDto toDto(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setYearOfBirth(user.getYearOfBirth());
        userDTO.setGender(user.getGender());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPassword(user.getPassword());
        userDTO.setCountry(user.getCountry());
        userDTO.setVerifiedAccount(user.getVerifiedAccount());
        userDTO.setVerificationCode(user.getVerificationCode());
        userDTO.setVerificationCodeExpiration(user.getVerificationCodeExpiration());

        userDTO.setFavoriteLibraries(user.getFavoriteLibraries().stream()
                .map(LibraryMapper::toSimpleDto)
                .toList());
        if (user.getReviews() != null) {
            userDTO.setReviews(user.getReviews().stream()
                    .map(ReviewMapper::toSimpleDto)
                    .toList());
        }

        if (user.getFavoriteBooks() != null) {
            userDTO.setBooks(user.getFavoriteBooks().stream()
                    .map(BookMapper::toSimpleDto)
                    .toList());
        }

        return userDTO;
    }

    public static User toSimpleEntity(UserSimpleDto userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setYearOfBirth(userDTO.getYearOfBirth());
        user.setGender(userDTO.getGender());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());
        user.setCountry(userDTO.getCountry());
        return user;
    }

    public static UserSimpleDto toSimpleDto(User user) {
        UserSimpleDto userDTO = new UserSimpleDto();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setYearOfBirth(user.getYearOfBirth());
        userDTO.setGender(user.getGender());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPassword(user.getPassword());
        userDTO.setCountry(user.getCountry());
        return userDTO;
    }
}
