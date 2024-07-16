package back_end_economizaja.model.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    ArrayList<Cliente> findAll();

    @Query(value = "select * from cliente where id=?1;", nativeQuery = true)
    Cliente findById(int idcliente);

    @Query(value = "select * from cliente where email=?1;", nativeQuery = true)
    Cliente findByEmailAndPassword(String email);

    @Query(value = "select primeiro_nome from cliente where id=?1;", nativeQuery = true)
    String findNameClienteById(int id);
}
