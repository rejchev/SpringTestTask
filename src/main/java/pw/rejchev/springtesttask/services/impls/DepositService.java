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
import pw.rejchev.springtesttask.entities.Deposit;
import pw.rejchev.springtesttask.repositories.IDepositCrudRepository;
import pw.rejchev.springtesttask.services.IDepositService;

import java.util.Optional;

@Getter
@Setter
@Service
@Transactional
@CacheConfig(cacheNames = "deposits")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositService implements IDepositService {

    final IDepositCrudRepository depositCrudRepository;

    @Autowired
    public DepositService(IDepositCrudRepository depositCrudRepository) {
        this.depositCrudRepository = depositCrudRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Deposit> getAll() {
        return getDepositCrudRepository().findAll();
    }

    @Override
    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public Optional<Deposit> findDepositById(String id) {
        return getDepositCrudRepository().findById(id);
    }


    @Override
    @Transactional
    @CachePut(key = "#deposit.id")
    public Deposit createDeposit(Deposit deposit) {
        return getDepositCrudRepository().save(deposit);
    }

    @Override
    @Transactional
    public Deposit updateDeposit(String id, Deposit deposit) {
        Optional<Deposit> buf;

        if((buf = findDepositById(id)).isEmpty())
            return null;

        Deposit depo = buf.get();

        if(deposit.getBank() != null && !depo.getBank().getBic().equals(deposit.getBank().getBic()))
            depo.setBank(deposit.getBank());

        if(deposit.getClient() != null && !depo.getClient().getId().equals(deposit.getClient().getId()))
            depo.setClient(deposit.getClient());

        if(deposit.getCreatedAt() != null && !depo.getCreatedAt().equals(deposit.getCreatedAt()))
            depo.setCreatedAt(deposit.getCreatedAt());

        if(deposit.getRate() != null && !depo.getRate().equals(deposit.getRate()))
            depo.setRate(deposit.getRate());

        if(deposit.getDurationInMonth() != null && !depo.getDurationInMonth().equals(deposit.getDurationInMonth()))
            depo.setDurationInMonth(deposit.getDurationInMonth());

        return createDeposit(depo);
    }

    @Override
    @CacheEvict(key = "#id", allEntries = true)
    public void deleteDeposit(String id) {
        getDepositCrudRepository().deleteById(id);
    }

    @Override
    @CachePut(key = "#query")
    public Iterable<Deposit> search(String query, Integer offset, Integer limit) {
        return getDepositCrudRepository().searchDeposits(query, offset, limit);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAllDeposits() {
        getDepositCrudRepository().deleteAll();
    }
}
