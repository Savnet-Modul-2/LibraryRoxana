package com.example.BookStore.controller;

import com.example.BookStore.dto.CreatedExemplaryDto;
import com.example.BookStore.dto.ExemplaryDto;
import com.example.BookStore.entities.Exemplary;
import com.example.BookStore.mapper.ExemplaryMapper;
import com.example.BookStore.service.ExemplaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exemplars")
public class ExemplaryController {
    @Autowired
    private ExemplaryService exemplaryService;

    @PostMapping()
    public ResponseEntity<List<ExemplaryDto>> createExemplars(@RequestBody CreatedExemplaryDto createdExemplaryDto) {
        List<Exemplary> exemplars = exemplaryService.create(
                createdExemplaryDto.getPublisher(),
                createdExemplaryDto.getMaxReservationDays(),
                createdExemplaryDto.getBookId(),
                createdExemplaryDto.getCount()
        );

        List<ExemplaryDto> exemplarsDto = ExemplaryMapper.toDtoList(exemplars);

        return ResponseEntity.ok(exemplarsDto);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ExemplaryDto>> getExemplarsByBook(
            @PathVariable Long bookId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<ExemplaryDto> exemplars = exemplaryService.findExemplarsByBookId(bookId, page, size);
        return ResponseEntity.ok(exemplars);
    }

    @DeleteMapping("/{exemplaryId}")
    public ResponseEntity<?> deleteExemplary(@PathVariable Long exemplaryId) {
        exemplaryService.deleteExemplary(exemplaryId);
        return ResponseEntity.ok().build();
    }


}
