package ec.editer.amqp.publisher.service;

import ec.editer.amqp.publisher.dto.BookDTO;
import ec.editer.amqp.publisher.model.Book;
import ec.editer.amqp.publisher.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BookService implements IBookService{
    
    private final BookRepository bookRepository;

    @Override
    public List<BookDTO> getBooksByTitle(String title) {
        return bookRepository.findAllByTitleContaining(title).stream()
                .map(book -> {
                    BookDTO dto = new BookDTO();
                    BeanUtils.copyProperties(book, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDTO> getBookById(Integer id) {
        log.info("----- getBookById for {} -----", id);
        return bookRepository.findById(id)
                .map(book -> {
            BookDTO dto = new BookDTO();
            BeanUtils.copyProperties(book, dto);
            return dto;
        });
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        log.info("----- updateBook for id {} and authors {} -----", bookDTO.getId(), bookDTO.getAuthors());
        Book book = new Book();
        BeanUtils.copyProperties(bookDTO, book);
        bookRepository.save(book);
        return bookDTO;
    }

}
