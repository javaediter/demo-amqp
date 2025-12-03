package ec.editer.amqp.consumer.controller;

import ec.editer.amqp.consumer.dto.LoanBookDTO;
import ec.editer.amqp.consumer.service.ILoanBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/loans")
@CrossOrigin("/**")
public class LoanController {

    private final ILoanBookService loanBookService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<LoanBookDTO>> getAll(){
        return ResponseEntity.ok(loanBookService.getAll());
    }
}
