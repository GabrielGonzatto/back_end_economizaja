package back_end_economizaja.model.receita;

import back_end_economizaja.model.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    ArrayList<Receita> findAll();

    @Query(value = "select * from receita where descricao=?1 and id_cliente=?2;", nativeQuery = true)
    ArrayList<Receita> findReceitaByName(String descricao, int idcliente);

}
