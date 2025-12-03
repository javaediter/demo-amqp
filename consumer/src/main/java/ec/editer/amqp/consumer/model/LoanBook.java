package ec.editer.amqp.consumer.model;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Edison Teran
 */
@Data
@Document(collection = "loanBooks")
public class LoanBook {
    
    @Id
    private String id;
    private Integer idBook;
    private String idPerson;
    private Date date;
    private boolean reversed;
    private boolean valid;
}
