package com.demo.app.active.services.impl;

import com.demo.app.active.entities.CreditAccount;
import com.demo.app.active.exceptions.customs.CustomNotFoundException;
import com.demo.app.active.repositories.CreditAccountRepository;
import com.demo.app.active.services.CreditAccountService;
import com.demo.app.active.utils.DateProcess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Calendar;

@Service
public class CreditAccountServiceImpl implements CreditAccountService {

    private final CreditAccountRepository creditAccountRepository;

    private final WebClient webClientMovement;


    public CreditAccountServiceImpl(CreditAccountRepository creditAccountRepository, WebClient.Builder webClientMovement,
                                    @Value("${movement}") String movementUrl) {
        this.creditAccountRepository = creditAccountRepository;
        this.webClientMovement = webClientMovement.baseUrl(movementUrl).build();
    }

    private static Mono<? extends Boolean> apply(Boolean x) {
        return Boolean.TRUE.equals(x) ? Mono.just(true) : Mono.just(false);
    }

    @Override
    public Flux<CreditAccount> findAll() {
        return creditAccountRepository.findAll();
    }

    @Override
    public Mono<CreditAccount> save(CreditAccount card) {
        return webClientMovement.get().uri("/idCreditAccount/" + card.getIdentifier())
                .retrieve().bodyToMono(BigDecimal.class).flatMap(cc ->
                {
                    Boolean result = false;
                    Calendar paymentDate = Calendar.getInstance();
                    Calendar today = Calendar.getInstance();
                    Calendar cutDate = Calendar.getInstance();

                    paymentDate.setTime(DateProcess.updateDate(card.getPaymentDate(), 1));
                    cutDate.setTime(DateProcess.updateDate(card.getCutoffDate(),1));

                    result = DateProcess.dateCompare(paymentDate.getTime(), today.getTime());
                    return result == true ? Mono.error(new CustomNotFoundException("El cliente tiene deudas")) :
                    cc.compareTo(new BigDecimal(0)) > 0 ?
                            creditAccountRepository.save(card) : Mono.error(new CustomNotFoundException("El cliente tiene deudas"));
                });
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
        return creditAccountRepository.findByIdentifierAndAccountNumber(identifier, account);
    }

    @Override
    public Mono<Boolean> findByIdentifier(String identifier) {
        return creditAccountRepository.findByIdentifier(identifier).hasElement().flatMap(CreditAccountServiceImpl::apply);
    }

    @Override
    public Mono<CreditAccount> update(CreditAccount card, String id) {
        return creditAccountRepository.findById(id).flatMap(x -> {
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
