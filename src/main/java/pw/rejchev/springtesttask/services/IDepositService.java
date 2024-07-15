package pw.rejchev.springtesttask.services;

import pw.rejchev.springtesttask.entities.Deposit;

import java.util.Optional;

public interface IDepositService {

    Iterable<Deposit> getAll();

    Optional<Deposit> findDepositById(String id);

    Deposit createDeposit(Deposit deposit);

    Deposit updateDeposit(String id, Deposit deposit);

    void deleteDeposit(String id);

    Iterable<Deposit> search(String query, Integer offset, Integer limit);

    void deleteAllDeposits();
}
