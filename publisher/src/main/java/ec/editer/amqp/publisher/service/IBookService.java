package ec.editer.amqp.publisher.service;

import ec.editer.amqp.publisher.dto.BookDTO;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Edison Teran
 */
public interface IBookService {
    List<BookDTO> getBooksByTitle(String title);
    Optional<BookDTO> getBookById(Integer id);
    BookDTO updateBook(BookDTO bookDTO);
}
