package pw.rejchev.springtesttask.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pw.rejchev.springtesttask.entities.Bank;

@Repository
public interface IBankCrudRepository extends CrudRepository<Bank, Integer> {

    @Query("SELECT b FROM Bank b WHERE " +
            "LOWER(b.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(CONCAT(b.bic, '')) LIKE LOWER(CONCAT('%', :searchText, '%')) ORDER BY b.bic LIMIT :lim OFFSET :offs")
    Iterable<Bank> searchBanks(@Param("searchText") String query,
                           @Param("offs") Integer offset,
                           @Param("lim")Integer limit);
}
