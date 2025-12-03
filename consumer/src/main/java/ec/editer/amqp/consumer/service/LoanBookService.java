package ec.editer.amqp.consumer.service;

import ec.editer.amqp.consumer.dto.LoanBookDTO;
import ec.editer.amqp.consumer.model.LoanBook;
import ec.editer.amqp.consumer.repository.LoanBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LoanBookService implements ILoanBookService{
    
    private final LoanBookRepository repository;

    @Override
    public LoanBook create(LoanBook loanBook) {
        log.info("----- create new document -----");
        repository.save(loanBook);
        return null;
    }

    @Override
    public List<LoanBookDTO> getAll() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return repository.findAll().stream().map(loan -> {
            LoanBookDTO dto = new LoanBookDTO();
            dto.setIdBook(loan.getIdBook());
            dto.setId(loan.getId());
            dto.setIdPerson(loan.getIdPerson());
            dto.setValid(loan.isValid());
            dto.setReversed(loan.isReversed());
            dto.setDate(format.format(loan.getDate()));
            return dto;
        })
                .collect(Collectors.toList());
    }
}
