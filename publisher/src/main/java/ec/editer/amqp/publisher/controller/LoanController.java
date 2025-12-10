package ec.editer.amqp.publisher.controller;

import ec.editer.amqp.publisher.dto.LoanDTO;
import ec.editer.amqp.publisher.dto.LoanFullDTO;
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
import java.util.List;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final ILoanService loanService;
    private final ILoanProducer loanProducer;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/all-before")
    public ResponseEntity<List<LoanFullDTO>> getAllByDate(@RequestParam String date){
        log.info("----- getAllByDate by {} -----", date);
        return ResponseEntity.ok(loanService.getAllByDates(date));
    }

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
    @PostMapping("/reverse")
    public ResponseEntity<Boolean> reverseLoan(@RequestBody HashMap<String, Object> body){
        Integer id = (Integer)body.get("idLoan");
        log.info("---> reverseLoan for {}", id);
        return loanService.getById(id)
                .map(loan -> {
                    loan.setReversed(true);
                    loanProducer.send(loanProducer.convertToLoanMessageDTO(loan));
                    return ResponseEntity.ok(loanService.updateLoan(loan));
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/end")
    public ResponseEntity<Boolean> endingLoan(@RequestBody HashMap<String, Object> body){
        Integer id = (Integer)body.get("idLoan");
        log.info("---> endingLoan for {}", id);
        return loanService.getById(id)
                .map(loan -> {
                    loan.setActive(false);
                    loanProducer.send(loanProducer.convertToLoanMessageDTO(loan));
                    return ResponseEntity.ok(loanService.updateLoan(loan));
                })
                .orElse(ResponseEntity.noContent().build());
    }
}
