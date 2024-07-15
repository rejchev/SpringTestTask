package pw.rejchev.springtesttask.controllers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pw.rejchev.springtesttask.entities.Bank;
import pw.rejchev.springtesttask.services.IBankService;

import java.util.Optional;

@Getter
@RestController
@RequestMapping("/api/banks")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class BankController {

    static Logger logger = LoggerFactory.getLogger(BankController.class);

    final IBankService bankService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Bank>> getBank(@PathVariable @Min(0) @Max(999999999) Integer id) {

        logger.debug("Getting bank with id {}", id);

        return ResponseEntity.ok(getBankService().getBank(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<Bank>> getBanks() {

        logger.debug("Getting banks (service state: {})", bankService);

        return ResponseEntity.ok(getBankService().getAll());
    }


    @PostMapping
    public ResponseEntity<?> createBank(@RequestBody @Validated Bank bank, Errors errors) {

        if(errors.hasErrors())
            return ResponseEntity.badRequest().body(errors.getAllErrors());

        logger.debug("Creating bank {}", bank);

        return ResponseEntity.ok(getBankService().createBank(bank));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBank(
            @PathVariable @Min(0) @Max(999999999) Integer id,
            @RequestBody @Validated Bank bank,
            Errors errors) {

        if(errors.hasErrors())
            return ResponseEntity.badRequest().body(errors.getAllErrors());

        logger.debug("Updating bank with id {} as {}", id, bank);

        Bank result;
        if((result = getBankService().updateBank(id, bank)) != null)
            return ResponseEntity.ok(result);

        logger.debug("Updating bank failed with id {} ({})", id, bank);

        return ResponseEntity.badRequest().body(bank);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Bank> deleteBank(@PathVariable @Min(0) @Max(999999999) Integer id) {

        getBankService().deleteBank(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Bank>> searchBanks(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(required = false, defaultValue = "30") @PositiveOrZero Integer limit) {

        logger.debug("search: {}; offs: {}; lim: {}", search, offset, limit);

        return ResponseEntity.ok(getBankService().search(search, offset, limit));
    }
}
