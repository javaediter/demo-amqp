package ec.editer.amqp.publisher.service;

import ec.editer.amqp.publisher.dto.BookDTO;
import ec.editer.amqp.publisher.dto.LoanDTO;
import ec.editer.amqp.publisher.dto.LoanFullDTO;
import ec.editer.amqp.publisher.enums.Status;
import ec.editer.amqp.publisher.model.Book;
import ec.editer.amqp.publisher.model.Loan;
import ec.editer.amqp.publisher.repository.BookRepository;
import ec.editer.amqp.publisher.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class LoanService implements ILoanService{

    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @Transactional(rollbackFor = {IOException.class})
    @Override
    public Optional<Loan> register(LoanDTO loanRequest) {
        try{
            return bookRepository.findById(loanRequest.getIdBook())
                    .map(book -> {
                        book.setAvailable(false);
                        bookRepository.save(book);                        
                        Loan loan = new Loan();
                        loan.setBook(book);
                        loan.setDate(new Date());
                        loan.setIdPerson(loanRequest.getIdPerson());
                        loan.setFirstName(loanRequest.getFirstName());
                        loan.setLastName(loanRequest.getLastName());
                        loan.setReversed(false);
                        loan.setActive(true);
                        return Optional.of(loanRepository.save(loan));
                    })
                    .orElse(Optional.empty());
        }catch(Exception ex){
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Loan> getById(Integer id) {
        return loanRepository.findById(id);
    }

    @Transactional(rollbackFor = {IOException.class})
    @Override
    public boolean updateLoan(Loan loan) {
        try{
            Book book = loan.getBook();
            book.setAvailable(true);
            bookRepository.save(book);
            loanRepository.save(loan);
            return true;
        }catch(Exception ex){
            log.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public List<LoanFullDTO> getAllByDates(String date) {
        try{
            Date endDate = convertStringToDate(date);
            return loanRepository.findAllByDateLessThanEqual(endDate)
                    .stream()
                    .map(entity -> {
                        BookDTO bookDTO = new BookDTO();
                        bookDTO.setId(entity.getBook().getId());
                        bookDTO.setAvailable(entity.getBook().isAvailable());
                        bookDTO.setYear(entity.getBook().getYear());
                        bookDTO.setIsbn(entity.getBook().getIsbn());
                        bookDTO.setTitle(entity.getBook().getTitle());
                        bookDTO.setAuthors(entity.getBook().getAuthors());

                        LoanFullDTO fullDTO = new LoanFullDTO();
                        fullDTO.setBook(bookDTO);
                        fullDTO.setId(entity.getId());
                        fullDTO.setDate(convertDateToString(entity.getDate()));
                        fullDTO.setStatus(entity.isReversed() ? Status.REVERSED.name() : entity.isActive() ? Status.CREATED.name() : Status.ENDED.name());
                        fullDTO.setIdPerson(entity.getIdPerson());
                        fullDTO.setFirstName(entity.getFirstName());
                        fullDTO.setLastName(entity.getLastName());

                        return fullDTO;
                    })
                    .collect(Collectors.toList());
        }catch (Exception ex){
            log.error(ex.getMessage());
            return List.of();
        }
    }

    private Date convertStringToDate(String date) throws Exception{
        return FORMAT_DATE.parse(date);
    }

    private String convertDateToString(Date date){
        return FORMAT_DATE.format(date);
    }
}
