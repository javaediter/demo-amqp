package ec.editer.amqp.publisher.service;

import ec.editer.amqp.publisher.model.Book;
import ec.editer.amqp.publisher.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edison Teran
 */
@RequiredArgsConstructor
@Service
public class BookService implements IBookService{
    
    private final BookRepository bookRepository;

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findAllByTitleContaining(title);
    }

    @Override
    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

}
