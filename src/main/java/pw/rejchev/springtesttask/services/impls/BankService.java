package pw.rejchev.springtesttask.services.impls;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Transactional(readOnly = true)
    @Cacheable(value = "BankService::getBank", key = "#id")
    public Optional<Bank> getBank(Integer id) {
        return getBankRepository().findById(id);
    }

    @Override
    @Transactional
    @Cacheable(value = "BankService::getBank", key = "#bank.bic", condition = "#bank.bic != null")
    public Bank createBank(Bank bank) {
        return getBankRepository().save(bank);
    }

    @Override
    @Transactional
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
    public void deleteBank(Integer id) {
        getBankRepository().deleteById(id);
    }

    @Override
    public Iterable<Bank> search(String query, Integer offset, Integer limit) {
        return getBankRepository().searchBanks(query, offset, limit);
    }

    @Override
    public void clean() {
        getBankRepository().deleteAll();
    }


}
