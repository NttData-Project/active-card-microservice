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
    public Flux<CreditAccount> findAllByIdentifier(String identifier) {
        return creditAccountRepository.findAllByIdentifier(identifier);
    }

    @Override
    public Mono<CreditAccount> findByIdentifierAndAccount(String identifier, String account) {
        return creditAccountRepository.findByIdentifierAndAccountNumber(identifier,account);
    }

    @Override
    public Mono<Boolean> findByIdentifier(String identifier) {
        return creditAccountRepository.findByIdentifier(identifier).hasElement().flatMap(CreditAccountServiceImpl::apply);
    }

    @Override
    public Mono<CreditAccount> update(CreditAccount card, String id) {
        return creditAccountRepository.findById(id).flatMap(x->{
            x.setCvc(card.getCvc());
            x.setAccountNumber(card.getAccountNumber());
            x.setCurrency(card.getCurrency());
            x.setBalance(card.getBalance());
            x.setIdentifier(card.getIdentifier());
            return creditAccountRepository.save(x);
        });
    }

    @Override
    public Mono<Void> delete(String id) {
        return creditAccountRepository.deleteById(id);
    }

    @Override
    public Mono<CreditAccount> findCreditAccountByIdentifier(String identifier) {
        return creditAccountRepository.findByIdentifier(identifier);
    }
}
