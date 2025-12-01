package ec.editer.amqp.publisher.controller;

import ec.editer.amqp.publisher.dto.LoanDTO;
import ec.editer.amqp.publisher.model.Book;
import ec.editer.amqp.publisher.model.Loan;
import ec.editer.amqp.publisher.model.service.IBookService;
import ec.editer.amqp.publisher.model.service.ILoanService;
import jakarta.validation.Valid;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/loanbook")
@CrossOrigin("/**")
public class LoanBookController {
    
    private final IBookService bookService;
    private final ILoanService loanService;
    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/books")
    public ResponseEntity getBooksAvailable(@RequestParam(name = "title", required = true) String title){
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }
    
    @PreAuthorize("hasAnyRole('TESTER','USER')")
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(name = "id") Integer id){
        return bookService.getBookById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/books/update")
    public ResponseEntity<Book> updateBook(@RequestBody HashMap<String, Object> body){
        Integer id = (Integer)body.get("id");       
        return bookService.getBookById(id).map(book -> {
            book.setAvailable(!book.isAvailable());
            return ResponseEntity.ok(bookService.updateBook(book));
        }).orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/loans/create")
    public ResponseEntity<Loan> registerLoan(@Valid @RequestBody LoanDTO loanRequest){        
        return loanService.register(loanRequest)
                .map(loan -> ResponseEntity.ok(loan))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/loans/delete")
    public ResponseEntity<Boolean> deleteLoanByIdPerson(@RequestBody HashMap<String, Object> body){
        String idPerson = (String)body.get("idPerson");
        log.info("---> deleteLoanByIdPerson for {}", idPerson);
        return loanService.getLastByIdPerson(idPerson)
                .map(loan -> {
                    return ResponseEntity.ok(loanService.deleteLoan(loan));
                })
                .orElse(ResponseEntity.noContent().build());
    }
}
