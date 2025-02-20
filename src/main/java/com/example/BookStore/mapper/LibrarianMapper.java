package com.example.BookStore.mapper;

import com.example.BookStore.dto.LibrarianDto;
import com.example.BookStore.dto.LibraryDto;
import com.example.BookStore.entities.Librarian;
import com.example.BookStore.entities.Library;

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
            library.setLibrarian(librarian);
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

        return librarianDto;
    }

}
