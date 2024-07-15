package pw.rejchev.springtesttask.services;

import pw.rejchev.springtesttask.entities.Bank;

import java.util.Optional;

public interface IBankService {

    Iterable<Bank> getAll();

    Optional<Bank> getBank(Integer bic);

    Bank createBank(Bank bank);

    Bank updateBank(Integer bic, Bank bank);

    void deleteBank(Integer bic);

    Iterable<Bank> search(String query, Integer offset, Integer limit);

    void clean();
}
