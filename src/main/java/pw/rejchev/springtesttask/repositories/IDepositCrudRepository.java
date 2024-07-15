package pw.rejchev.springtesttask.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pw.rejchev.springtesttask.entities.Deposit;

@Repository
public interface IDepositCrudRepository extends CrudRepository<Deposit, String> {

    @Query(value = "SELECT * FROM `deposits` WHERE " +
            "deposits.bank_id LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "deposits.client_id LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "deposits.created_at LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "deposits.duration_in_month LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "deposits.rate LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "ORDER BY id LIMIT :lim OFFSET :offs", nativeQuery = true)
    Iterable<Deposit> searchDeposits(@Param("searchText") String query,
                                     @Param("offs") Integer offset,
                                     @Param("lim")Integer limit);

}
