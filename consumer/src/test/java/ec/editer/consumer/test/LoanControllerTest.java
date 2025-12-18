package ec.editer.consumer.test;

import ec.editer.amqp.consumer.controller.LoanController;
import ec.editer.amqp.consumer.dto.LoanBookDTO;
import ec.editer.amqp.consumer.service.ILoanBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

@SpringBootTest(classes = {LoanController.class})
public class LoanControllerTest {

    @Autowired
    private LoanController controller;

    @MockitoBean
    private ILoanBookService service;

    @Test
    public void getAllTest(){
        // Arrange
        Pageable pageable = PageRequest.of(0, 5, Sort.by("date").descending());
        Mockito.when(service.getAll(pageable)).thenReturn(List.of());

        // Act
        ResponseEntity<List<LoanBookDTO>> response = controller.getAll(0, 5);

        // Assertions
        Assertions.assertEquals(0, response.getBody().size());
        Mockito.verify(service).getAll(pageable);
    }
}
