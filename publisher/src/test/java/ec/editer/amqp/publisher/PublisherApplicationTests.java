package ec.editer.amqp.publisher;

import ec.editer.amqp.publisher.controller.LoanBookController;
import ec.editer.amqp.publisher.model.Book;
import ec.editer.amqp.publisher.model.service.IBookService;
import ec.editer.amqp.publisher.model.service.ILoanService;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = {LoanBookController.class, BCryptPasswordEncoder.class})
class PublisherApplicationTests {
@MockitoBean
    private IBookService bookService;
    
    @MockitoBean
    private ILoanService loanService;
    
    @Autowired
    private LoanBookController loanBookController;
    
    @Test
    public void getBookByIdFoundTest(){
        //Arrange
        Book book = new Book();
        book.setId(1);
        book.setTitle("Programming with Java");
        book.setIsbn("001-1001");
        book.setYear(2025);
        book.setAuthors("Many people");
        book.setAvailable(true);
        Optional<Book> opt = Optional.of(book);
        when(bookService.getBookById(1)).thenReturn(opt);
        
        //Act
        ResponseEntity<Book> response = loanBookController.getBookById(1);
        
        //Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTitle().equals(book.getTitle()));
        
        verify(bookService).getBookById(anyInt());
    }
    
    @Test
    public void getBookByIdNotFoundTest(){
        //Arrange
        Optional<Book> opt = Optional.empty();
        when(bookService.getBookById(10)).thenReturn(opt);
        
        //Act
        ResponseEntity<Book> response = loanBookController.getBookById(1);
        System.out.println("response: " + response.getBody());
        
        //Assert
        assertNotNull(response);
        assertNull(response.getBody());
        
        verify(bookService).getBookById(anyInt());
    }
}
