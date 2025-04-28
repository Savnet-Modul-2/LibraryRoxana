package com.example.Library.controller;

import com.example.Library.dto.TagDto;
import com.example.Library.entities.Tag;
import com.example.Library.mapper.TagMapper;
import com.example.Library.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/createTag/{librarianId}/{bookId}")
    public ResponseEntity<?> create(@PathVariable Long librarianId,
                                    @PathVariable Long bookId,
                                    @RequestBody TagDto tag) {
        Tag tagEntity = TagMapper.toEntity(tag);
        Tag tagCreated = tagService.addTagToBook(librarianId, bookId, tagEntity);
        TagDto tagDto = TagMapper.toDto(tagCreated);
        return ResponseEntity.ok(tagDto);
    }

    @GetMapping("/paginated-search/{bookId}")
    public ResponseEntity<?> findTagsPaginated(
            @PathVariable Long bookId,
            @RequestParam(name = "pageSize", required = false) Integer size,
            @RequestParam(name = "pageNumber", required = false) Integer page) {

        int pageSize = (size != null) ? size : 10;
        int pageNumber = (page != null) ? page : 0;

        Page<Tag> foundTags = tagService.findTag(bookId, PageRequest.of(pageNumber, pageSize));
        Page<TagDto> tagDtos = foundTags.map(TagMapper::toDto);

        return ResponseEntity.ok(tagDtos);
    }

    @PutMapping("updateTag/{tagId}/{librarianId}")
    public ResponseEntity<?> updateTag(@PathVariable Long tagId,
                                       @RequestBody TagDto tag,
                                       @PathVariable Long librarianId) {
        Tag tagEntity = TagMapper.toEntity(tag);
        Tag updatedTag = tagService.update(tagEntity, tagId, librarianId);
        TagDto tagDto = TagMapper.toDto(updatedTag);
        return ResponseEntity.ok(tagDto);
    }

    @DeleteMapping("delete/{tagId}/{bookId}")
    public ResponseEntity<?> deleteTag(@PathVariable Long tagId,
                                       @PathVariable Long bookId) {
        tagService.delete(tagId, bookId);
        return ResponseEntity.ok().build();
    }
}
