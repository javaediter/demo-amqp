package ec.editer.amqp.publisher.service;

public interface IBookProducer {
    void sendCountNotAvailableBook(Long count);
}
