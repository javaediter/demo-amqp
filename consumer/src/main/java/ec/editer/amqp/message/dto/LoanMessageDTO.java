package ec.editer.amqp.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Edison Teran
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanMessageDTO {
    private Integer idBook;
    private String idPerson;
    private String strDate;
    private String status;
}
