package com.example.Library.mapper;

import com.example.Library.dto.LibrarianDto;
import com.example.Library.dto.LibrarianSimpleDto;
import com.example.Library.entities.Librarian;
import com.example.Library.entities.Library;

public class LibrarianMapper {
    public static Librarian toEntity(LibrarianDto librarianDto) {
        if (librarianDto == null) {
            return null;
        }

        Librarian librarian = new Librarian();
        librarian.setName(librarianDto.getName());
        librarian.setEmail(librarianDto.getEmail());
        librarian.setPassword(librarianDto.getPassword());
        librarian.setVerifiedAccount(librarianDto.getVerifiedAccount());
        librarian.setVerificationCode(librarianDto.getVerificationCode());
        librarian.setVerificationCodeExpiration(librarianDto.getVerificationCodeExpiration());

        if (librarianDto.getLibrary() != null) {
            Library library = LibraryMapper.toEntity(librarianDto.getLibrary());
            librarian.setLibrary(library);
        }

        if (librarian.getTags() != null) {
            librarianDto.setTags(librarian.getTags().stream()
                    .map(TagMapper::toSimpleDto)
                    .toList());
        }

        return librarian;
    }


    public static LibrarianDto toDto(Librarian librarian) {
        if (librarian == null) {
            return null;
        }

        LibrarianDto librarianDto = new LibrarianDto();
        librarianDto.setId(librarian.getId());
        librarianDto.setName(librarian.getName());
        librarianDto.setEmail(librarian.getEmail());
        librarianDto.setPassword(librarian.getPassword());
        librarianDto.setVerifiedAccount(librarian.getVerifiedAccount());
        librarianDto.setVerificationCode(librarian.getVerificationCode());
        librarianDto.setVerificationCodeExpiration(librarian.getVerificationCodeExpiration());

        if (librarian.getLibrary() != null) {
            librarianDto.setLibrary(LibraryMapper.toDto(librarian.getLibrary()));
        }

        if (librarian.getTags() != null) {
            librarianDto.setTags(librarian.getTags().stream()
                    .map(TagMapper::toSimpleDto)
                    .toList());
        }

        return librarianDto;
    }
    public static Librarian toSimpleEntity(LibrarianSimpleDto librarianDto) {
        if (librarianDto == null) {
            return null;
        }

        Librarian librarian = new Librarian();
        librarian.setName(librarianDto.getName());
        librarian.setEmail(librarianDto.getEmail());
        librarian.setPassword(librarianDto.getPassword());
        return librarian;
    }


    public static LibrarianSimpleDto toSimpleDto(Librarian librarian) {
        if (librarian == null) {
            return null;
        }

        LibrarianSimpleDto librarianDto = new LibrarianSimpleDto();
        librarianDto.setId(librarian.getId());
        librarianDto.setName(librarian.getName());
        librarianDto.setEmail(librarian.getEmail());
        librarianDto.setPassword(librarian.getPassword());

        return librarianDto;
    }
}
