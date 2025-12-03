package ec.editer.amqp.publisher.controller;

import ec.editer.amqp.publisher.dto.LoanDTO;
import ec.editer.amqp.publisher.model.Loan;
import ec.editer.amqp.publisher.service.ILoanProducer;
import ec.editer.amqp.publisher.service.ILoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/loans")
@CrossOrigin("/**")
public class LoanController {

    private final ILoanService loanService;
    private final ILoanProducer loanProducer;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<Loan> registerLoan(@Valid @RequestBody LoanDTO loanRequest){        
        return loanService.register(loanRequest)
                .map(loan -> {
                    loanProducer.send(loanProducer.convertToOkLoanMessageDTO(loanRequest));
                    return ResponseEntity.ok(loan);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteLoanByIdPerson(@RequestBody HashMap<String, Object> body){
        String idPerson = (String)body.get("idPerson");
        log.info("---> deleteLoanByIdPerson for {}", idPerson);
        return loanService.getLastByIdPerson(idPerson)
                .map(loan -> {
                    loanProducer.send(loanProducer.convertToLoanMessageDTO(loan));
                    return ResponseEntity.ok(loanService.deleteLoan(loan));
                })
                .orElse(ResponseEntity.noContent().build());
    }
}
