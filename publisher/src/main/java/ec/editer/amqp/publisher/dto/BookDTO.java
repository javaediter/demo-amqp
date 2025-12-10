package ec.editer.amqp.publisher.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Integer id;
    private String title;
    private String isbn;
    private String authors;
    private Integer year;
    private boolean available;
}
