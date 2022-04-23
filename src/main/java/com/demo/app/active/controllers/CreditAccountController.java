package com.demo.app.active.controllers;

import com.demo.app.active.entities.CreditAccount;
import com.demo.app.active.services.CreditAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/creditAccount")
@Tag(name = "Test APIs", description = "Test APIs for demo purpose")
public class CreditAccountController {

    private final CreditAccountService cardService;


    public CreditAccountController(CreditAccountService cardService) {
        this.cardService = cardService;
    }


    @GetMapping
    public ResponseEntity<Flux<CreditAccount>> findAll(){
        return ResponseEntity.ok(cardService.findAll());
    }

    @GetMapping("/all/dni/{dni}")
    public Flux<CreditAccount> findAllByDni(@PathVariable String dni){
        return cardService.findAllByDni(dni);
    }

    @GetMapping("/dni/{dni}")
    public Mono<Boolean> findByDni(@PathVariable String dni){
        return cardService.findByDni(dni);
    }

    @GetMapping("/dni/{dni}/account/{account}")
    public Mono<CreditAccount> findByDniAndAccount(@PathVariable String dni,@PathVariable String account){
        return cardService.findByDniAndAccount(dni,account);
    }

    @PostMapping
    public ResponseEntity<Mono<CreditAccount>> save(@RequestBody CreditAccount card){
        return ResponseEntity.ok(cardService.save(card));
    }
    @PostMapping("/all")
    public ResponseEntity<Flux<CreditAccount>> saveAll(@RequestBody Flux<CreditAccount> cards){
        return ResponseEntity.ok(cardService.saveAll(cards));
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<CreditAccount>> update(@RequestBody CreditAccount card, @PathVariable String id){
        return cardService.update(card,id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        return cardService.delete(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
