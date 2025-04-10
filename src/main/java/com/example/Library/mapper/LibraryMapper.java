package com.example.Library.mapper;

import com.example.Library.dto.LibraryDto;
import com.example.Library.dto.LibrarySimpleDto;
import com.example.Library.dto.UserSimpleDto;
import com.example.Library.entities.Book;
import com.example.Library.entities.Library;
import com.example.Library.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class LibraryMapper {
    public static Library toEntity(LibraryDto libraryDto) {
        if (libraryDto == null) {
            return null;
        }

        Library library = new Library();
        library.setName(libraryDto.getName());
        library.setAddress(libraryDto.getAddress());
        library.setPhoneNumber(libraryDto.getPhoneNumber());

        if (libraryDto.getBooks() != null) {
            List<Book> books = libraryDto.getBooks().stream()
                    .map(BookMapper::toEntity)
                    .toList();
            library.setBooks(books);
        }
        if (libraryDto.getUsers() != null) {
            for (UserSimpleDto simpleDto : libraryDto.getUsers()) {
                User userEntity = UserMapper.toSimpleEntity(simpleDto);
                library.getUsers().add(userEntity);
                userEntity.getLibraries().add(library);
            }
        }
        return library;
    }

    public static LibraryDto toDto(Library library) {
        if (library == null) {
            return null;
        }

        LibraryDto libraryDto = new LibraryDto();
        libraryDto.setId(library.getId());
        libraryDto.setName(library.getName());
        libraryDto.setAddress(library.getAddress());
        libraryDto.setPhoneNumber(library.getPhoneNumber());

        if (library.getBooks() != null) {
            libraryDto.setBooks(library.getBooks().stream()
                    .map(BookMapper::toDto)
                    .toList());
        }
        libraryDto.setUsers(library.getUsers().stream()
                .map(UserMapper::toSimpleDto)
                .collect(Collectors.toList()));

        return libraryDto;
    }
    public static Library toSimpleEntity(LibrarySimpleDto libraryDto) {
        if (libraryDto == null) {
            return null;
        }

        Library library = new Library();
        library.setName(libraryDto.getName());
        library.setAddress(libraryDto.getAddress());
        library.setPhoneNumber(libraryDto.getPhoneNumber());
        return library;
    }

    public static LibrarySimpleDto toSimpleDto(Library library) {
        if (library == null) {
            return null;
        }
        LibrarySimpleDto libraryDto = new LibrarySimpleDto();
        libraryDto.setId(library.getId());
        libraryDto.setName(library.getName());
        libraryDto.setAddress(library.getAddress());
        libraryDto.setPhoneNumber(library.getPhoneNumber());

        return libraryDto;
    }
    public static List<UserSimpleDto> toDtoList(List<User> users) {
        return users.stream().map(UserMapper::toSimpleDto).toList();
    }
}
