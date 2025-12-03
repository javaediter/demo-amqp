package ec.editer.amqp.consumer.service;

import ec.editer.amqp.consumer.model.LoanBook;
import ec.editer.amqp.message.dto.LoanMessageDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@Component
public class TransformLoan implements ITransformLoan{

    @Override
    public LoanBook transform(LoanMessageDTO loanMessageDTO) {
        log.info("----- transform {} -----", loanMessageDTO.getClass());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
        LoanBook loanBook = new LoanBook();
        loanBook.setIdBook(loanMessageDTO.getIdBook());
        loanBook.setIdPerson(loanMessageDTO.getIdPerson());
        loanBook.setReversed(loanMessageDTO.isReversed());
        loanBook.setValid(true);
            
        try {
            loanBook.setDate(format.parse(loanMessageDTO.getStrDate()));
        } catch (ParseException ex) {
            log.error(ex.getMessage());
            loanBook.setDate(new Date());
            loanBook.setValid(false);
        }
        
        return loanBook;
    }
    
}
