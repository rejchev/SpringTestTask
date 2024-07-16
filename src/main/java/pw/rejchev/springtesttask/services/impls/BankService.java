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
import pw.rejchev.springtesttask.entities.Bank;
import pw.rejchev.springtesttask.repositories.IBankCrudRepository;
import pw.rejchev.springtesttask.services.IBankService;

import java.util.Optional;

@Getter
@Setter
@Service
@Transactional
@CacheConfig(cacheNames = {"banks"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankService implements IBankService {

    final IBankCrudRepository bankRepository;

    @Autowired
    public BankService(IBankCrudRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Bank> getAll() {
        return getBankRepository().findAll();
    }

    @Override
    @Cacheable(key = "#bic")
    @Transactional(readOnly = true)
    public Optional<Bank> getBank(Integer bic) {
        return getBankRepository().findById(bic);
    }

    @Override
    @CachePut(key = "#bank.bic")
    public Bank createBank(Bank bank) {
        return getBankRepository().save(bank);
    }

    @Override
    public Bank updateBank(Integer bic, Bank bank) {

        Optional<Bank> buf;
        if((buf = getBank(bic)).isEmpty())
            return null;

        Bank b = buf.get();

        if(bank.getName() != null && !bank.getName().equals(b.getName()))
            b.setName(bank.getName());

        return createBank(b);
    }

    @Override
    @CacheEvict(key = "#bic", allEntries = true)
    public void deleteBank(Integer bic) {
        getBankRepository().deleteById(bic);
    }

    @Override
    @CachePut(key = "#query")
    public Iterable<Bank> search(String query, Integer offset, Integer limit) {
        return getBankRepository().searchBanks(query, offset, limit);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void clean() {
        getBankRepository().deleteAll();
    }
}
