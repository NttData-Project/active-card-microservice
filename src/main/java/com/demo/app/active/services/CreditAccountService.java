package com.demo.app.active.services;

import com.demo.app.active.entities.CreditAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditAccountService {
    Flux<CreditAccount> findAll();
    Mono<CreditAccount> save(CreditAccount card);
    Flux<CreditAccount> saveAll(Flux<CreditAccount> cards);
    Flux<CreditAccount> findAllByDni(String dni);
    Mono<CreditAccount> findByDniAndAccount(String dni,String account);
    Mono<Boolean> findByDni(String dni);
    Mono<CreditAccount> update(CreditAccount card,String id);
    Mono<Void> delete(String id);
}
