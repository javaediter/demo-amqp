package ec.editer.amqp.publisher.controller;

import ec.editer.amqp.publisher.dto.BookDTO;
import ec.editer.amqp.publisher.service.IBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private final IBookService bookService;
    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getBooksAvailable(@RequestParam(name = "title", required = true) String title){
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }
    
    @PreAuthorize("hasAnyRole('TESTER','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(name = "id") Integer id){
        return bookService.getBookById(id)
                .map(bookDTO -> ResponseEntity.ok(bookDTO))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<BookDTO> updateBook(@RequestBody HashMap<String, Object> body){
        Integer id = (Integer)body.get("id");       
        return bookService.getBookById(id)
                .map(bookDTO -> {
            bookDTO.setAvailable(!bookDTO.isAvailable());
            return ResponseEntity.ok(bookService.updateBook(bookDTO));
        }).orElse(ResponseEntity.notFound().build());
    }
}
