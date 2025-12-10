package ec.editer.amqp.consumer.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LoanBookDTO {
    private String id;
    private Integer idBook;
    private String idPerson;
    private String date;
    private String status;
}
