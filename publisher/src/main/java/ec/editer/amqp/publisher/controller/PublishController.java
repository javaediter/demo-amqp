package ec.editer.amqp.publisher.controller;

import ec.editer.amqp.message.dto.LoanMessageDTO;
import ec.editer.amqp.publisher.model.service.ILoanProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin("/**")
@RequestMapping("/api/pub")
public class PublishController {
    
    private final ILoanProducer loanProducer;
    
    @PostMapping("/send")
    public ResponseEntity<String> sendLoan(@RequestBody LoanMessageDTO loanMessageDTO){
        log.info("----- sendLoan for idBook {} -----", loanMessageDTO.getIdBook());
        loanProducer.send(loanMessageDTO);
        return ResponseEntity.ok().body("Loan sent!");
    }
}
