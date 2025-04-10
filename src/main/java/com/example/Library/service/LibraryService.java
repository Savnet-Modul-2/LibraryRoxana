package com.example.Library.service;

import com.example.Library.dto.LibrarySimpleDto;
import com.example.Library.entities.Library;
import com.example.Library.entities.User;
import com.example.Library.mapper.LibraryMapper;
import com.example.Library.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private LibraryRepository libraryRepository;

    public Library getLibraryById(Long id) {
        return libraryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<LibrarySimpleDto> findByUserId(Long userId, Integer page, Integer size) {
        if (page != null && size != null && page >= 0 && size > 0) {
            return libraryRepository.findByUserId(userId, PageRequest.of(page, size)).stream()
                    .map(LibraryMapper::toSimpleDto)
                    .toList();
        }
        return libraryRepository.findById(userId).stream().map(LibraryMapper::toSimpleDto).toList();

    }

    public void removeFromUser(Long libraryId) {
        Library library1 = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library not found with id: " + libraryId));

        List<User> users = library1.getUsers();
        users.remove(library1);
        libraryRepository.save(library1);
    }
}
