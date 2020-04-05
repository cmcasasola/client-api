package pe.com.home.clientapi.repository;

import org.springframework.data.repository.CrudRepository;
import pe.com.home.clientapi.model.entity.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
