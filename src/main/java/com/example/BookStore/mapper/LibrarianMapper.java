package com.example.BookStore.mapper;

import com.example.BookStore.dto.LibrarianDto;
import com.example.BookStore.entities.Librarian;
import com.example.BookStore.entities.Library;
public class LibrarianMapper {
    public static Librarian toEntity(LibrarianDto librarianDto){
        Librarian librarian=new Librarian();
        librarian.setName(librarianDto.getName());
        librarian.setEmail(librarianDto.getEmail());
        librarian.setPassword(librarianDto.getPassword());
        librarian.setVerifiedAccount(librarianDto.getVerifiedAccount());
        librarian.setVerificationCode(librarianDto.getVerificationCode());
        librarian.setVerificationCodeExpiration(librarianDto.getVerificationCodeExpiration());
        if (librarianDto.getLibraryId() != null) {
            Library library = new Library();
            library.setId(librarianDto.getLibraryId());
            library.setLibrarian(librarian);
            librarian.setLibrary(library);
        }
        return  librarian;
    }

    public static LibrarianDto toDto(Librarian librarian){
        LibrarianDto librarianDto=new LibrarianDto();
        librarianDto.setId(librarian.getId());
        librarianDto.setName(librarian.getName());
        librarianDto.setEmail(librarian.getEmail());
        librarianDto.setPassword(librarian.getPassword());
        librarianDto.setVerifiedAccount(librarian.getVerifiedAccount());
        librarianDto.setVerificationCode(librarian.getVerificationCode());
        librarianDto.setVerificationCodeExpiration(librarian.getVerificationCodeExpiration());
        if (librarian.getLibrary() != null) {
            librarianDto.setLibraryId(librarian.getLibrary().getId());
        }
        return  librarianDto;
    }
}
