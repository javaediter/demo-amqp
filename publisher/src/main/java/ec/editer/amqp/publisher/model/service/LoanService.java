package ec.editer.amqp.publisher.model.service;

import ec.editer.amqp.publisher.dto.LoanDTO;
import ec.editer.amqp.publisher.model.Book;
import ec.editer.amqp.publisher.model.Loan;
import ec.editer.amqp.publisher.repository.BookRepository;
import ec.editer.amqp.publisher.repository.LoanRepository;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LoanService implements ILoanService{
    
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
                        return Optional.of(loanRepository.save(loan));
                    })
                    .orElse(Optional.empty());
        }catch(Exception ex){
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Loan> getLastByIdPerson(String idPerson) {
        return loanRepository.findTopByIdPersonAndReversedOrderByIdDesc(idPerson, false);
    }

    @Transactional(rollbackFor = {IOException.class})
    @Override
    public boolean deleteLoan(Loan loan) {
        try{
            Book book = loan.getBook();
            book.setAvailable(true);
            bookRepository.save(book);
            loan.setReversed(true);
            loanRepository.save(loan);
            return true;
        }catch(Exception ex){
            log.error(ex.getMessage());
            return false;
        }
    }

}
