package com.demo.app.active.repositories;

import com.demo.app.active.entities.CreditAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CreditAccountRepository extends ReactiveMongoRepository<CreditAccount,String> {
    Mono<CreditAccount> findByIdentifier(String identifier);
    Flux<CreditAccount> findAllByIdentifier(String identifier);
    Mono<CreditAccount> findByIdentifierAndAccountNumber(String identifier, String account);
}
