package pw.rejchev.springtesttask.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pw.rejchev.springtesttask.entities.Client;

@Repository
public interface IClientCrudRepository extends CrudRepository<Client, String> {

    @Query(value = "SELECT * FROM `clients` WHERE " +
            "clients.address LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "clients.name LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "clients.okopf LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "clients.short_name LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "ORDER BY id LIMIT :lim OFFSET :offs", nativeQuery = true)
    Iterable<Client> searchClients(@Param("searchText") String query,
                                    @Param("offs") Integer offset,
                                    @Param("lim")Integer limit);
}
