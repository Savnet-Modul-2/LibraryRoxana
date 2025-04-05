package com.example.Library.unitTests.repository;

import com.example.Library.entities.Librarian;
import com.example.Library.repository.LibrarianRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class LibrarianRepositoryTests {
    @Autowired
    private LibrarianRepository librarianRepository;
    private Librarian testLibrarian;

    @BeforeEach
    public void setUp() {
        testLibrarian = new Librarian();
        testLibrarian.setEmail("test@gmail.com");
        testLibrarian.setPassword("roxana");
    }

    @AfterEach
    public void tearDown() {
        librarianRepository.deleteAll();
    }

    @Test
    public void givenEmail_FindByEmail_ReturnLibrarian() {
        String testEmail = "test@gmail.com";

        librarianRepository.save(testLibrarian);
        Librarian foundLibrarian = librarianRepository.findByEmail(testEmail).orElse(null);

        Assertions.assertThat(foundLibrarian).isEqualTo(testLibrarian);
    }

    @Test
    public void givenNothing_FindByEmail_ReturnNull() {
        String testEmail = "test@gmail.com";

        Librarian foundLibrarian = librarianRepository.findByEmail(testEmail).orElse(null);

        Assertions.assertThat(foundLibrarian).isNull();
    }

    @Test
    public void givenEmailAndPassword_findByEmailAndPasswordAndVerifiedAccountTrue_ReturnLibrarian() {
        String email=testLibrarian.getEmail();
        String password=testLibrarian.getPassword();
        librarianRepository.save(testLibrarian);
        Librarian foundLibrarian = librarianRepository.findByEmailAndPasswordAndVerifiedAccountTrue(email,password).orElse(null);

        Assertions.assertThat(foundLibrarian).isNull();
    }

    @Test
    public void givenNothing_findByEmailAndPasswordAndVerifiedAccountTrue_ReturnNull() {
        String testEmail = "test@gmail.com";
        String password="roxana";

        Librarian foundLibrarian = librarianRepository.findByEmailAndPasswordAndVerifiedAccountTrue(testEmail,password).orElse(null);

        Assertions.assertThat(foundLibrarian).isNull();
    }

    @Test
    public void givenNothing_findByLibraryId_ReturnNull() {
        Long libraryId=1L;

        Librarian foundLibrarian = librarianRepository.findByLibraryId(libraryId).orElse(null);

        Assertions.assertThat(foundLibrarian).isNull();
    }
}
