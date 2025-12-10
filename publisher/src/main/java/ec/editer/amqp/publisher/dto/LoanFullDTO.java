package ec.editer.amqp.publisher.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LoanFullDTO {
    private Integer id;
    private String idPerson;
    private String firstName;
    private String lastName;
    private String date;
    private String status;
    private BookDTO book;
}
