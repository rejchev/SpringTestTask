package pw.rejchev.springtesttask.controllers;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pw.rejchev.springtesttask.entities.Bank;
import pw.rejchev.springtesttask.entities.Client;
import pw.rejchev.springtesttask.entities.Deposit;
import pw.rejchev.springtesttask.entities.dto.DepositDto;
import pw.rejchev.springtesttask.services.IBankService;
import pw.rejchev.springtesttask.services.IClientService;
import pw.rejchev.springtesttask.services.IDepositService;

import java.io.IOException;
import java.util.Optional;

@Getter
@RestController
@RequestMapping("/api/deposits")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class DepositController {

    final IDepositService depositService;

    final IBankService bankService;

    final IClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Deposit>> getBank(@PathVariable String id) {
        return ResponseEntity.ok(getDepositService().findDepositById(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<Deposit>> getBanks() {
        return ResponseEntity.ok(getDepositService().getAll());
    }


    @PostMapping
    public ResponseEntity<?> createDeposit(@RequestBody @Validated DepositDto deposit, Errors errors) {

        if(errors.hasErrors())
            return ResponseEntity.badRequest().body(errors.getAllErrors());

        Optional<Bank> bank;
        if((bank = bankService.getBank(Integer.valueOf(deposit.getBankId()))).isEmpty())
            throw new IllegalArgumentException();

        Optional<Client> client;
        if((client = clientService.findClient(deposit.getClientId())).isEmpty())
            throw new IllegalArgumentException();

        return ResponseEntity.ok(getDepositService().createDeposit(
                Deposit
                .builder()
                .id("")
                .bank(bank.get())
                .client(client.get())
                .createdAt(deposit.getCreatedAt())
                .rate(deposit.getRate())
                .durationInMonth(deposit.getDurationInMonth())
                .build()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDeposit(
            @PathVariable String id,
            @RequestBody @Validated Deposit deposit,
            Errors errors) {

        if(errors.hasErrors())
            return ResponseEntity.badRequest().body(errors.getAllErrors());

        ResponseEntity<?> response = ResponseEntity.badRequest()
                .body(deposit);

        Deposit result;
        if((result = getDepositService().updateDeposit(id, deposit)) != null)
            response = ResponseEntity.ok(result);

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Deposit> deleteDeposit(@PathVariable String id) {

        getDepositService().deleteDeposit(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Deposit>> getBanks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(required = false, defaultValue = "30") @PositiveOrZero Integer limit) {
        return ResponseEntity.ok(getDepositService().search(search, offset, limit));
    }
}
