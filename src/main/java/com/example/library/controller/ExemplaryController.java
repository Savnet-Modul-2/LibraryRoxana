package com.example.library.controller;

import com.example.library.dto.CreatedExemplaryDto;
import com.example.library.dto.ExemplaryDto;
import com.example.library.entities.Exemplary;
import com.example.library.mapper.ExemplaryMapper;
import com.example.library.service.ExemplaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exemplars")
public class ExemplaryController {
    @Autowired
    private ExemplaryService exemplaryService;

    @PostMapping
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

        List<ExemplaryDto> exemplars = exemplaryService.findByBookId(bookId, page, size);
        return ResponseEntity.ok(exemplars);
    }

    @DeleteMapping("/{exemplaryId}")
    public ResponseEntity<?> deleteExemplary(@PathVariable Long exemplaryId) {
        exemplaryService.delete(exemplaryId);
        return ResponseEntity.ok().build();
    }
}
