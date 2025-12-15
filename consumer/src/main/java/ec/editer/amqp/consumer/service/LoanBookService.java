package ec.editer.amqp.consumer.service;

import ec.editer.amqp.consumer.dto.LoanBookDTO;
import ec.editer.amqp.consumer.model.LoanBook;
import ec.editer.amqp.consumer.repository.LoanBookRepository;
import ec.editer.amqp.message.dto.LoanMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private final LoanBookRepository repository;

    @Override
    public LoanBook create(LoanMessageDTO loanMessageDTO) {
        log.info("----- create new loan for idBook {} -----", loanMessageDTO.getIdBook());
        LoanBook loanBook = new LoanBook();
        loanBook.setIdBook(loanMessageDTO.getIdBook());
        loanBook.setIdPerson(loanMessageDTO.getIdPerson());
        loanBook.setStatus(loanMessageDTO.getStatus());

        try {
            loanBook.setDate(FORMAT_DATE.parse(loanMessageDTO.getStrDate()));
        } catch (ParseException ex) {
            log.error(ex.getMessage());
            loanBook.setDate(new Date());
            loanBook.setStatus("FAILED");
        }
        repository.save(loanBook);
        return null; //It is necessary for the Integration Flow handle pipe
    }

    @Override
    public List<LoanBookDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable).stream().map(loan -> {
            LoanBookDTO dto = new LoanBookDTO();
            dto.setIdBook(loan.getIdBook());
            dto.setId(loan.getId());
            dto.setIdPerson(loan.getIdPerson());
            dto.setStatus(loan.getStatus());
            dto.setDate(FORMAT_DATE.format(loan.getDate()));
            return dto;
        })
                .collect(Collectors.toList());
    }
}
