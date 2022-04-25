package com.demo.app.active.services;

import com.demo.app.active.entities.CreditAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditAccountService {
    Flux<CreditAccount> findAll();
    Mono<CreditAccount> save(CreditAccount card);
    Flux<CreditAccount> saveAll(Flux<CreditAccount> cards);
    Flux<CreditAccount> findAllByIdentifier(String identifier);
    Mono<CreditAccount> findByIdentifierAndAccount(String identifier, String account);
    Mono<Boolean> findByIdentifier(String identifier);
    Mono<CreditAccount> update(CreditAccount card,String id);
    Mono<Void> delete(String id);
}
