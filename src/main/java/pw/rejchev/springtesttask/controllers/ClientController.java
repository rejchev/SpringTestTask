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
import pw.rejchev.springtesttask.entities.Client;
import pw.rejchev.springtesttask.services.IClientService;

import java.util.Optional;

@Getter
@RestController
@RequestMapping("/api/clients")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ClientController {
    
    final IClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Client>> getClient(@PathVariable String id) {
        return ResponseEntity.ok(getClientService().findClient(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<Client>> getClients() {
        return ResponseEntity.ok(getClientService().getAll());
    }


    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody @Validated Client client, Errors errors) {

        if(errors.hasErrors())
            return ResponseEntity.badRequest().body(errors.getAllErrors());

        return ResponseEntity.ok(getClientService().createClient(client));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(
            @PathVariable String id,
            @RequestBody @Validated Client bank,
            Errors errors) {

        if(errors.hasErrors())
            return ResponseEntity.badRequest().body(errors.getAllErrors());

        Client result;
        if((result = getClientService().updateClient(id, bank, errors)) != null)
            return ResponseEntity.ok(result);

        return ResponseEntity.badRequest().body(errors.getAllErrors());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable String id) {

        getClientService().deleteClient(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Client>> searchClients(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(required = false, defaultValue = "30") @PositiveOrZero Integer limit) {
        return ResponseEntity.ok(getClientService().search(search, offset, limit));
    }
    
}
