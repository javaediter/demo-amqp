package ec.editer.amqp.publisher.service;

import ec.editer.amqp.publisher.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {
    private final BookRepository bookRepository;
    private final IBookProducer bookProducer;

    @Scheduled(cron = "#{environment.getProperty('cron.job')}")
    public void countAvailableBooks(){
        log.info("----- countAvailableBooks {} -----", new Date());
        long countAvailable = bookRepository.countByAvailable(true);
        long countNotAvailable = bookRepository.countByAvailable(false);
        log.info("----- available books {} -----", countAvailable);
        log.info("----- not available books {} -----", countNotAvailable);
        bookProducer.sendCountNotAvailableBook(countAvailable);
    }
}
