package com.example.Library.mapper;

import com.example.Library.dto.LibrarySimpleDto;
import com.example.Library.dto.UserDto;
import com.example.Library.dto.UserSimpleDto;
import com.example.Library.entities.Book;
import com.example.Library.entities.Library;
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
        if (userDTO.getLibraries() != null) {
            for (LibrarySimpleDto simpleDto : userDTO.getLibraries()) {
                Library libraryEntity = LibraryMapper.toSimpleEntity(simpleDto);
                user.getLibraries().add(libraryEntity);
                libraryEntity.getUsers().add(user);
            }
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

        userDTO.setLibraries(user.getLibraries().stream()
                .map(LibraryMapper::toSimpleDto)
                .collect(Collectors.toList()));
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
    public static List<LibrarySimpleDto> toDtoList(List<Library> libraries) {
        return libraries.stream().map(LibraryMapper::toSimpleDto).toList();
    }
}
