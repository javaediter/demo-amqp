package ec.editer.amqp.publisher;

import ec.editer.amqp.publisher.controller.BookController;
import ec.editer.amqp.publisher.dto.BookDTO;
import ec.editer.amqp.publisher.service.IBookService;
import ec.editer.amqp.publisher.service.ILoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BookController.class, BCryptPasswordEncoder.class})
class PublisherApplicationTests {
    @MockitoBean
    private IBookService bookService;
    
    @MockitoBean
    private ILoanService loanService;
    
    @Autowired
    private BookController loanBookController;
    
    @Test
    public void getBookByIdFoundTest(){
        //Arrange
        BookDTO book = new BookDTO();
        book.setId(1);
        book.setTitle("Programming with Java");
        book.setIsbn("001-1001");
        book.setYear(2025);
        book.setAuthors("Many people");
        book.setAvailable(true);
        Optional<BookDTO> opt = Optional.of(book);
        when(bookService.getBookById(1)).thenReturn(opt);
        
        //Act
        ResponseEntity<BookDTO> response = loanBookController.getBookById(1);
        
        //Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getTitle(), book.getTitle());
        
        verify(bookService).getBookById(anyInt());
    }
    
    @Test
    public void getBookByIdNotFoundTest(){
        //Arrange
        Optional<BookDTO> opt = Optional.empty();
        when(bookService.getBookById(10)).thenReturn(opt);
        
        //Act
        ResponseEntity<BookDTO> response = loanBookController.getBookById(1);
        System.out.println("response: " + response.getBody());
        
        //Assert
        assertNotNull(response);
        assertNull(response.getBody());
        
        verify(bookService).getBookById(anyInt());
    }
}
