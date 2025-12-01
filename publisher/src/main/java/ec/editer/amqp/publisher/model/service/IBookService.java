package ec.editer.amqp.publisher.model.service;

import ec.editer.amqp.publisher.model.Book;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Edison Teran
 */
public interface IBookService {
    List<Book> getBooksByTitle(String title);
    Optional<Book> getBookById(Integer id);
    Book updateBook(Book book);
}
