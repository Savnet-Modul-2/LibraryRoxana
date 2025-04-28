package com.example.Library.mapper;

import com.example.Library.dto.ReviewDto;
import com.example.Library.dto.TagDto;
import com.example.Library.dto.TagSimpleDto;
import com.example.Library.entities.Book;
import com.example.Library.entities.Librarian;
import com.example.Library.entities.Review;
import com.example.Library.entities.Tag;
import com.example.Library.service.TagService;

import java.time.LocalDate;

public class TagMapper {
    public static Tag toEntity(TagDto tagDto){
        Tag tag=new Tag();
        tag.setName(tagDto.getName());
        tag.setDescription(tagDto.getDescription());
        tag.setCreatedTime(LocalDate.now());
        tag.setLibrarian(LibrarianMapper.toSimpleEntity(tagDto.getLibrarian()));
        tag.setBook(BookMapper.toSimpleEntity(tagDto.getBook()));

        if (tagDto.getBook() != null && tagDto.getBook().getId() != null) {
            Book book = new Book();
            book.setId(tagDto.getBook().getId());
            book.setIsbn(tagDto.getBook().getIsbn());
            tag.setBook(book);
        }

        if (tagDto.getLibrarian() != null && tagDto.getLibrarian().getId() != null) {
            Librarian librarian = new Librarian();
            librarian.setId(tagDto.getLibrarian().getId());
            librarian.setName(tagDto.getLibrarian().getName());
            tag.setLibrarian(librarian);
        }

        return tag;
    }
    public static TagDto toDto(Tag tag){
        TagDto tagDto=new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        tagDto.setDescription(tag.getDescription());
        tagDto.setCreatedTime(LocalDate.now());
        tagDto.setCreatedTime(LocalDate.now());
        if (tag.getBook() != null) {
            tagDto.setBook(BookMapper.toSimpleDto(tag.getBook()));
        }
        if (tag.getLibrarian() != null) {
            tagDto.setLibrarian(LibrarianMapper.toSimpleDto(tag.getLibrarian()));
        }
        return tagDto;
    }
    public static TagSimpleDto toSimpleDto(Tag tag){
        TagSimpleDto tagDto=new TagSimpleDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        tagDto.setDescription(tag.getDescription());
        tagDto.setCreatedTime(LocalDate.now());
        return tagDto;
    }
}
