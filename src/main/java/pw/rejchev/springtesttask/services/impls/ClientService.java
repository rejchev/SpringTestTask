package pw.rejchev.springtesttask.services.impls;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import pw.rejchev.springtesttask.entities.Client;
import pw.rejchev.springtesttask.repositories.IClientCrudRepository;
import pw.rejchev.springtesttask.services.IClientService;

import java.util.Optional;

@Getter
@Setter
@Service
@Transactional
@CacheConfig(cacheNames = "clients")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientService implements IClientService {

    final IClientCrudRepository clientRepository;

    @Autowired
    public ClientService(IClientCrudRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Client> getAll() {
        return getClientRepository().findAll();
    }

    @Override
    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public Optional<Client> findClient(String id) {
        return getClientRepository().findById(id);
    }

    @Override
    @Transactional
    @CachePut(key = "#client.id")
    public Client createClient(Client client) {
        return getClientRepository().save(client);
    }

    @Override
    @Transactional
    public Client updateClient(String id, Client client, Errors errs) {
        Optional<Client> buf;
        if((buf = findClient(id)).isEmpty())
            return null;

        Client cl = buf.get();
        if(client.getName() != null && !cl.getName().equals(client.getName()))
            cl.setName(client.getName());

        if(client.getAddress() != null && !cl.getAddress().equals(client.getAddress()))
            cl.setAddress(client.getAddress());

        if(client.getShortName() != null && !cl.getShortName().equals(client.getShortName()))
            cl.setShortName(client.getShortName());

        if(client.getOkopf() != null && !cl.getOkopf().equals(client.getOkopf()))
            cl.setOkopf(client.getOkopf());

        return createClient(cl);
    }

    @Override
    @CacheEvict(key = "#id", allEntries = true)
    public void deleteClient(String id) {
        getClientRepository().deleteById(id);
    }

    @Override
    @CachePut(key = "#query")
    public Iterable<Client> search(String query, Integer offset, Integer limit) {
        return getClientRepository().searchClients(query, offset, limit);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAllClients() {
        getClientRepository().deleteAll();
    }
}
