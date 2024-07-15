package pw.rejchev.springtesttask.services;

import org.springframework.validation.Errors;
import pw.rejchev.springtesttask.entities.Client;

import java.util.Optional;

public interface IClientService {

    Iterable<Client> getAll();

    Optional<Client> findClient(String id);

    Client createClient(Client client);

    Client updateClient(String id, Client client, Errors errs);

    void deleteClient(String id);

    Iterable<Client> search(String query, Integer offset, Integer limit);

    void deleteAllClients();
}
