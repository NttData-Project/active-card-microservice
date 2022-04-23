package com.demo.app.active.services.impl;

import com.demo.app.active.entities.CreditAccount;
import com.demo.app.active.repositories.CreditAccountRepository;
import com.demo.app.active.services.CreditAccountService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditAccountServiceImpl implements CreditAccountService {

    private final CreditAccountRepository creditAccountRepository;

    public CreditAccountServiceImpl(CreditAccountRepository creditAccountRepository) {
        this.creditAccountRepository = creditAccountRepository;
    }

    private static Mono<? extends Boolean> apply(Boolean x) {
        return Boolean.TRUE.equals(x)?Mono.just(true):Mono.just(false);
    }

    @Override
    public Flux<CreditAccount> findAll() {
        return creditAccountRepository.findAll();
    }

    @Override
    public Mono<CreditAccount> save(CreditAccount card) {
        return creditAccountRepository.save(card);
    }

    @Override
    public Flux<CreditAccount> saveAll(Flux<CreditAccount> cards) {
        return creditAccountRepository.saveAll(cards);
    }

    @Override
    public Flux<CreditAccount> findAllByDni(String dni) {
        return creditAccountRepository.findAllByDni(dni);
    }

    @Override
    public Mono<CreditAccount> findByDniAndAccount(String dni, String account) {
        return creditAccountRepository.findByDniAndAccountNumber(dni,account);
    }

    @Override
    public Mono<Boolean> findByDni(String dni) {
        return creditAccountRepository.findByDni(dni).hasElement().flatMap(CreditAccountServiceImpl::apply);
    }

    @Override
    public Mono<CreditAccount> update(CreditAccount card, String id) {
        return creditAccountRepository.findById(id).flatMap(x->{
            x.setCvc(card.getCvc());
            x.setAccountNumber(card.getAccountNumber());
            x.setCurrency(card.getCurrency());
            x.setBalance(card.getBalance());
            x.setDni(card.getDni());
            return creditAccountRepository.save(x);
        });
    }

    @Override
    public Mono<Void> delete(String id) {
        return creditAccountRepository.deleteById(id);
    }
}
